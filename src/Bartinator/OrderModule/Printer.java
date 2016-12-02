package Bartinator.OrderModule;

import Bartinator.Model.Order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 02-12-2016.
 */
public class Printer {

	public static List<String> makeReceipt(List<Order> orders) {
		List<String> list = new ArrayList<>();
		int priceSum = 0;
		for(Order order : orders){
			addOrderToList(list, order);
			priceSum += order.getTotalPrice()*100;
		}

		list.add("              -==========-");
		list.add(String.format("%-34s %5.2f", "Total:", priceSum/100.0));
		list.add(String.format("%-34s %5.2f", "25% Moms:", priceSum/500.0));
		return list;
	}

	private static void addOrderToList(List<String> list, Order order) {
		list.add("Salesperson: "+order.getBartenderName());
		list.addAll(order.getReceipt());
		list.add("              ------------");
		list.add(String.format("%-34s %5.2f", "Subtotal:", order.getTotalPrice()));
		list.add(String.format("%-34s %5.2f", "25% Moms:", order.getTotalPrice()*0.2));
		list.add("");
		list.add(String.format("%-34s %5.2f", order.getPaymentType(), order.getTotalPrice()+order.getChange()));
		list.add(String.format("%-34s %5.2f", "Change", order.getChange()));
		list.add("");
	}


	/**
	 * Tager en masse ting og kværner dem lidt rundt og spytter en html fil ud.
	 * @param orders
	 * @param day
	 * @param sumTotal
	 * @return
	 */
	public String htmlIt(List<Order> orders, LocalDate day, double sumTotal){
		Path file = new File(getClass().getClassLoader().getResource("exported.html").getFile()).toPath();
		String htmlTemplate = "";
		try {
			BufferedReader reader = Files.newBufferedReader(file);
			String line = reader.readLine();
			while (line != null) {
				int i, j;
				i=line.indexOf("{");
				if(i!=-1){
					j=line.indexOf("}");
					if(j!=-1){
						String key = line.substring(i+1,j);
						if(key.equals("date")){
							line = String.format("%s%s%s", line.substring(0, i), day.toString(), line.substring(j + 1));
						} else if(key.equals("sumtotal")) {
							line = String.format("%s%.2f%s", line.substring(0, i), sumTotal, line.substring(j + 1));
						} else if(key.equals("tax")) {
							line = String.format("%s%.2f%s", line.substring(0, i), sumTotal * 0.2, line.substring(j + 1));
						} else if(key.startsWith("product")) {
							String before = line.substring(0, i);
							String after = line.substring(j+1);
							String template = key.substring(7);
							String middle = "";
							for(Order order : orders){
								for(String entry : order.getReceipt()) {
									middle += String.format(template, entry.substring(0, 2), entry.substring(5, entry.length()-5-7), entry.substring(entry.length()-7));
								}
							}
							line = before+middle+after;
						}
					}
				}
				htmlTemplate += line;
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlTemplate;
	}

	public static void exportReceiptToPDF(List<String> receipt){

	}
}
