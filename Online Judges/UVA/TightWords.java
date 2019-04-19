import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.BufferedWriter;

class Main {

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		while (reader.hasNext()) {
			int numDigits = reader.nextInt();
			int numLength = reader.nextInt();
			
			if(numDigits <= 1){
				printer.println("100.00000");
				continue;
			}
			
			double[][] dp = new double[numLength + 1][numDigits+1];

			for (int i = 0; i <= numDigits; i++) {
				dp[1][i] = 1;
			}

			for (int currentLength = 2; currentLength <= numLength; currentLength++) {
				for (int endDigit = 0; endDigit <= numDigits; endDigit++) {
					if (endDigit == 0) {
						dp[currentLength][endDigit] = dp[currentLength-1][endDigit + 1]
								+ dp[currentLength - 1][endDigit];
					} else if (endDigit == numDigits) {
						dp[currentLength][endDigit] = dp[currentLength-1][endDigit - 1] + dp[currentLength-1][endDigit];
					} else {
						dp[currentLength][endDigit] = dp[currentLength-1][endDigit - 1] + dp[currentLength-1][endDigit]
								+ dp[currentLength - 1][endDigit + 1];
					}
				}
			}
			
			double sum = 0;
			for(int endDigit = 0; endDigit <= numDigits; endDigit++){
				sum += dp[numLength][endDigit];
			}
			
			double denominator = Math.pow(numDigits+1, numLength);
			
			DecimalFormat f = new DecimalFormat("0.00000");
			
			printer.println(f.format(sum/denominator*100));
			
		}

		reader.close();
		printer.close();

	}

}
