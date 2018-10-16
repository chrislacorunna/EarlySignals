package mainLib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextPane;

public class DataDownloader {
	
	
	public static void download(CurrencyList currencyList) {			
		ExecutorService executor = Executors.newScheduledThreadPool(20);
		System.out.println("---------" + Thread.activeCount() + "---------" + currencyList.getList().get(109));
		for(int i = 0; i < currencyList.getList().size(); i++){
			executor.execute(currencyList.getList().get(i));
		}
		try{
			Thread.sleep(20000);
		}catch(Exception e){
			e.printStackTrace();
		}
		executor.shutdownNow();
	}

	public static void download(CurrencyList currencyList, JTextPane panel) {	
		ExecutorService executor = Executors.newScheduledThreadPool(20);
		
		for(int i = 0; i < currencyList.getList().size(); i++){
			executor.execute(currencyList.getList().get(i));
		}
		try{
			Thread.sleep(20000);
		}catch(Exception e){
			e.printStackTrace();
		}
		executor.shutdownNow();
		
		String paneText = panel.getText() + "\nDownloading data";
		panel.setText(paneText);
	}
	
	public static int numberOfCurrencies(){
		int numberOfCurrencies = 0;
		try{
			Scanner scanner = new Scanner(new File("currencyNames/currencyNames.txt"));
			while(scanner.hasNextLine()){
				String name = scanner.nextLine();
				numberOfCurrencies++;
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return numberOfCurrencies;
	}
	
	public static void howMuchValuesDownloadedForCurrencies(CurrencyList cur){
		ArrayList<Integer> list = new ArrayList();
		for(int i = 1; i<cur.getList().size(); i++){
			if(!list.contains(cur.getList().get(i).getValueList().size())){
				list.add(cur.getList().get(i).getValueList().size());
				list.add(0);
			}
		for(int j = 0; j < cur.getList().size(); j++){
			for(int k = 0; k<list.size(); k+=2){
				if(cur.getList().get(j).getValueList().size() == list.get(k)){
					list.set((k+1), list.get(k+1)+1);
				}
			}
		}
		for(int k = 0; k<list.size(); k = k+2){
			System.out.println(list.get(k+1) + "walut ma długość " + list.get(k));
		}
		}
	}
	
	public static void shortenString(String oldString){
		String[] lines = oldString.split("\r\n|\r|\n");
		if(lines.length > 20){
			oldString = oldString.substring(oldString.indexOf("\n"));
		}
		System.out.println(oldString.indexOf("\n"));
	}
}


