package mainLib;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CurrencyList {

	private ArrayList<Currency> list;

	public ArrayList<Currency> getList() {
		return list;
	}

	public void setList(ArrayList<Currency> list) {
		this.list = list;
	}
	
	public CurrencyList(){
		list = new ArrayList();
		int numberOfCurrencies = DataDownloader.numberOfCurrencies();
		try{
			Scanner scanner = new Scanner(new File("currencyNames/currencyNames.txt"));
			for(int i = 0; i < numberOfCurrencies; i++){
				Currency tmpCurrency = new Currency(scanner.nextLine());
				list.add(tmpCurrency);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		String retStr = new String();
		for(int i = 0; i < DataDownloader.numberOfCurrencies(); i++){
			retStr += getList().get(i).toStringFull() + "\n";
		}
		return retStr;
	}
}
