import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
 LANG: JAVA
 TASK: fact4
 */
public class fact4 {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("fact4.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
		int toFactorial = Integer.parseInt(reader.readLine().trim());
		reader.close();
		
		long result = 1;
		for(int currentNumber = 2; currentNumber <= toFactorial; currentNumber++){
			result*= currentNumber;
			String sResult = String.valueOf(result);
			int endIndex = 0;
			for(endIndex = sResult.length()-1; endIndex >= 0; endIndex--){
				if(sResult.charAt(endIndex) != '0'){
					break;
				}
			}
			if(endIndex < 15){
				result = Long.parseLong((sResult.substring(0, endIndex+1)));
			}
			else{
				result = Long.parseLong((sResult.substring(endIndex-14, endIndex+1)));
				System.out.print("*");
			}
			System.out.println(result);
		}
		String sResult = String.valueOf(result);
		int endIndex = 0;
		for(endIndex = sResult.length()-1; endIndex >= 0; endIndex--){
			if(sResult.charAt(endIndex) != '0'){
				break;
			}
		}
		
		printer.println(sResult.charAt(endIndex));
		printer.close();
		System.out.println(System.currentTimeMillis()-startTime);
	}

}
