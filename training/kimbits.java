import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
 LANG: JAVA
 TASK: kimbits
 */
public class kimbits {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("kimbits.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		
		String[] inputData = reader.readLine().split(" ");
		int bitLength = Integer.parseInt(inputData[0]);
		int onesLeft = Integer.parseInt(inputData[1]);
		long nthNumber = Long.parseLong(inputData[2]);
		reader.close();
		
		int currentIndex = 0;
		long currentNumber = nthNumber;
		String bits = new String();
		
		
		for(currentIndex = 0; currentIndex < bitLength; currentIndex++){
				int bValue = 0;
				int spotsLeft = bitLength-currentIndex-1;
				if(onesLeft < spotsLeft){
					bValue = onesLeft;
				}
				else{
					bValue = spotsLeft;
				}
				
				long spotsChooseB = combinationsFunc(spotsLeft, bValue);
				if(currentNumber > spotsChooseB){
					currentNumber -= spotsChooseB;
					onesLeft--;
					bits += "1";
				}
				else{
					bits += "0";
				}
				
		}
		
		printer.println(bits);
		printer.close();
		
		System.out.println(System.currentTimeMillis()-startTime);
	}
	
	
	public static long combinationsFunc(int a, int b){
		long value = 0;
		for(int i = b; i >= 0; i--){
			value += combinations(a, i);
		}
		return value;
	}
	
	
	public static long combinations(int a, int b){
		if(a == 0 || b == 0){
			return 1;
		}
		double value = 1;
		for(int i = 1; i <= b; i++){
			value *= ((a+1-i)/(double)i);
		}
		return Math.round(value);
	}

}
