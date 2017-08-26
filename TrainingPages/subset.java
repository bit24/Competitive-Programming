import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 ID: eric.ca1
 LANG: JAVA
 TASK: subset
 */
public class subset {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("subset.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("subset.out")));
		int number = Integer.parseInt(reader.readLine());
		reader.close();
		
		int sumToMake = (number + 1)*(number)/2;
		if(sumToMake % 2 != 0){
			printer.println(0);
			printer.close();
			System.exit(0);
		}
		long answer = waysToMakeNum(sumToMake/2, number);
		if(answer % 2 != 0){
			printer.println(0);
		}
		else{
			printer.println(answer/2);
		}
		printer.close();
	}
	
	public static long waysToMakeNum(int sum, int numbersToUse){
		
		long[] data = new long[sum+1];
		
		int currentSum = 0;
		for(int currentNumberToUse = 1; currentNumberToUse <= numbersToUse; currentNumberToUse++){
			currentSum += currentNumberToUse;
			if(currentSum > sum){
				currentSum = sum;
			}
			for(int sumToMake = currentSum; sumToMake >= currentNumberToUse; sumToMake--){
				long currentValue = data[sumToMake];
				
				int remainder = sumToMake - currentNumberToUse;
				
				if(remainder == 0){
					data[sumToMake] = currentValue + 1;
					continue;
				}
				
				long remainderValue = data[remainder];
				
				
				data[sumToMake] = currentValue + remainderValue;

			}
		}
		return data[sum];
	}

}
