package mainLib;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Currency implements Runnable {
	private String name;
	private ArrayList<Double> currentValue;
	private double bidValue;
	private double askValue;
	private double max24hValue;
	private double min24hValue;
	private double volume;
	private boolean checkable = true;
	
	public boolean isCheckable() {
		return checkable;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	public double getBidValue() {
		return bidValue;
	}

	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}

	public double getAskValue() {
		return askValue;
	}

	public void setAskValue(double askValue) {
		this.askValue = askValue;
	}

	public double getMax24hValue() {
		return max24hValue;
	}

	public void setMax24hValue(double max24hValue) {
		this.max24hValue = max24hValue;
	}

	public double getMin24hValue() {
		return min24hValue;
	}

	public void setMin24hValue(double min24hValue) {
		this.min24hValue = min24hValue;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Currency(String name){
		this.name = name;
		this.currentValue = new ArrayList<Double>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfMeasurement(){
		return currentValue.size();
	}
	
	public double getValue(int i) {
		return currentValue.get(i);
	}
	
	public ArrayList<Double> getValueList(){
		return currentValue;
	}

	public void setValue(double value) {
		this.currentValue.add(value);
	}

	
	public void run(){
		String address = "https://www.okex.com/api/v1/ticker.do?symbol=" + name + "_btc";
		URL a = null;
		HttpsURLConnection connection = null;
		try{
			a = new URL(address);
			connection = (HttpsURLConnection) a.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.setRequestMethod("GET");
			connection.connect();
			
			StringBuffer buffer = new StringBuffer();
			InputStream in = connection.getInputStream();
			byte b = (byte)in.read();
			while(b!=-1){
				buffer.append((char)b);
				b = (byte)in.read();
			}
			int indexOfCVOCC = buffer.indexOf("last") + 7;
			int indexOfMin = buffer.indexOf("low") + 6;
			int indexOfMax = buffer.indexOf("high") + 7;
			int indexOfBid = buffer.indexOf("buy") + 6;
			int indexOfAsk = buffer.indexOf("sell") + 7;
			int indexOfVolume = buffer.indexOf("vol") + 6;
			
			String currentValueStr = buffer.substring(indexOfCVOCC, indexOfCVOCC + 10);
			String minStr =  buffer.substring(indexOfMin, indexOfMin + 10);
			String maxStr =  buffer.substring(indexOfMax, indexOfMax + 10);
			String bidStr =  buffer.substring(indexOfBid, indexOfBid + 10);
			String askStr =  buffer.substring(indexOfAsk, indexOfAsk + 10);
			String volumeStr =  buffer.substring(indexOfVolume, indexOfVolume + 10);
			
			if(currentValue.size() > 0 && currentValue.get(currentValue.size() - 1) != Double.parseDouble(currentValueStr) || currentValue.size() == 0){
				this.currentValue.add(Double.parseDouble(currentValueStr));
				setCheckable(true);
			}
			this.min24hValue = Double.parseDouble(minStr);
			this.max24hValue = Double.parseDouble(maxStr);
			this.bidValue = Double.parseDouble(bidStr);
			this.askValue = Double.parseDouble(askStr);
			this.volume = Double.parseDouble(volumeStr);
			
			//System.out.println(toStringFull());
			
			in.close();
			
		}catch(Exception e){
			//e.printStackTrace();
			Thread.currentThread().interrupt();
		}finally{
			connection.disconnect();
		}
	}	
	
	@Override
	public String toString(){
		if(currentValue.isEmpty()) return null;
		return name + "(" + currentValue.size() + "): " + currentValue.get(currentValue.size() - 1);
	}
	
	public String toStringFull(){
		return name +" - last: " + currentValue + ", bid: " + bidValue + ", ask: " + askValue + ", vol: " + volume + ", max: " + max24hValue + ", min: " + min24hValue;
	}
}
