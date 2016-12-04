package Bartinator.OrderModule;

import Bartinator.Model.Order;
import Bartinator.Model.ReceiptItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Martin on 02-12-2016.
 */
public class Printer {

	public static List<String> makeReceipt(List<Order> orders) {
		List<String> list = new ArrayList<>();
		double priceSum = 0;
		for(Order order : orders) {
			Collections.addAll(list, order.toString().split(String.format("%n")));
			priceSum += order.getTotalPrice();
		}

		list.add("     --=-==-======-==-=--");
		list.add(String.format("%-23s%5.2f", "Total:", priceSum));
		list.add(String.format("%-23s%5.2f", "25% Moms:", priceSum/5));
		return list;
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
								for(ReceiptItem item : order.getReceipt()) {
									middle += String.format(template, item.getAmount(), item.getProductName(), item.getPrice()+item.getAmount());
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
