package mainLib;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTextPane;

public class Calculator {
		
	public void searchForPump(CurrencyList currencyList, JTextPane panel){
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		for(int i = 0; i < currencyList.getList().size(); i++){
			String returned = panel.getText();
			int strengthOfPump = isReallyHigher(currencyList.getList().get(i).getValueList(), 20);
			if(currencyList.getList().get(i).getNumberOfMeasurement() > 15){
				if(strengthOfPump == 1){
					//System.out.println("Słaba pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis()));
					returned += "\nSłaba pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis());
					currencyList.getList().get(i).setCheckable(false);
				}
				else if(strengthOfPump == 2){
					//System.out.println("Srednia pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis()));
					returned += "\nŚrednia pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis());
					currencyList.getList().get(i).setCheckable(false);
				}
				else if(strengthOfPump == 3){
					//System.out.println("Silna pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis()));
					returned += "\nMocna pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis());
					currencyList.getList().get(i).setCheckable(false);
				}
				else if(strengthOfPump == 4){
					//System.out.println("Ogromna pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis()));
					returned += "\nBardzo mocna pompa na " + currencyList.getList().get(i).getName() + " o godzinie: " + sdf.format(System.currentTimeMillis());
					currencyList.getList().get(i).setCheckable(false);
				}
			}
			panel.setText(returned);
			
		}
	}
	
	//sprawdzam czy wartość x ostatnich pomiarów jest odpowiednio dla 
	//każdego z nich większa od 3 poprzedzających tą wartość pomiarów
	private int isReallyHigher(ArrayList<Double> list, Integer number){
		if(list.size() - number < 1) return -1;
		
		Integer counter = 0;
		for(int i = list.size() - 1; i > list.size() - number; i--){
				if(list.get(i) > 1.010*list.get(i - 1)) counter += 4;
				else if(list.get(i) > 1.005*list.get(i - 1)) counter += 3;
				else if(list.get(i) > 1.001*list.get(i - 1)) counter += 2;
		}
		
		if(list.get(list.size() - 1) > 1.08 * list.get(list.size() - number - 1)) counter += 6;
		else if(list.get(list.size() - 1) > 1.05 * list.get(list.size() - number - 1)) counter += 4;
		
		for(int i = 0; i<number; i++){
			
		}
		
		Double counterx = counter.doubleValue();
		Double numberx = number.doubleValue();
		//currently 56 is max, 21 should be min acceptable
		//System.out.println(counterx / (numberx * 5.0 + 6.0));
		if(counterx / (numberx * 5.0 + 6.0) > 0.75) return 4; //okrutnie przesilna
		if(counterx / (numberx * 5.0 + 6.0) > 0.6) return 3; // silna
		if(counterx / (numberx * 5.0 + 6.0) > 0.45) return 2; //średnia
		if(counterx / (numberx * 5.0 + 6.0) > 0.35) return 1; //słaba
		
	

		return -1;
	}
}
