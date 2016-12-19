import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
ID: eric.ca1
LANG: JAVA
TASK: money
*/
public class money {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("money.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("money.out")));
		String[] inputData = reader.readLine().split(" ");
		int numCoins = Integer.parseInt(inputData[0]);
		int amountToMake = Integer.parseInt(inputData[1]);
		int[] coinValues = new int[numCoins+1];
		int coinValueArrayIterator = 1;
		
		String inputString = reader.readLine();
		while(inputString != null){
			inputData = inputString.split(" ");
			for(String currentString : inputData){
				coinValues[coinValueArrayIterator++] = Integer.parseInt(currentString);
			}
			inputString = reader.readLine();
		}
		
		reader.close();
		
		long[][] dataArray = new long[numCoins+1][];
		dataArray[0] = new long[amountToMake+1];
		dataArray[0][0] = 1;
		
		for(int currentCoin = 1; currentCoin < coinValues.length; currentCoin++){
			dataArray[currentCoin] = new long[amountToMake+1];
			for(int amount = 0; amount <= amountToMake; amount++){
				long subValue = dataArray[currentCoin-1][amount];
				if(amount - coinValues[currentCoin] >= 0){
					subValue += dataArray[currentCoin][amount - coinValues[currentCoin]];
				}
				dataArray[currentCoin][amount] = subValue;
			}
		}
		
		
		printer.println(dataArray[numCoins][amountToMake]);
		printer.close();
	}

}
