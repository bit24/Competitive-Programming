import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FreshChocolateSolver {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("chocolate.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("chocolate.out")));

		int numT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numG = Integer.parseInt(inputData.nextToken());
			int numP = Integer.parseInt(inputData.nextToken());

			inputData = new StringTokenizer(reader.readLine());
			if (numP == 2) {
				int numC = 0;
				for (int i = 0; i < numG; i++) {
					if (Integer.parseInt(inputData.nextToken()) % 2 == 0) {
						numC++;
					}
				}
				printer.println("Case #" + cT + ": " + (numC + (numG - numC + 1) / 2));
			} else if (numP == 3) {
				int numC = 0;
				int num1 = 0;
				for (int i = 0; i < numG; i++) {
					int nInt = Integer.parseInt(inputData.nextToken());
					if (nInt % 3 == 0) {
						numC++;
					} else if (nInt % 3 == 1) {
						num1++;
					}
				}
				int num2 = numG - numC - num1;
				int numS = Math.min(num1, num2);
				printer.println("Case #" + cT + ": " + (numC + numS + (num1 - numS + 2) / 3 + (num2 - numS + 2) / 3));
			} else if (numP == 4) {
				int numC = 0;
				int num1 = 0;
				int num2 = 0;
				for (int i = 0; i < numG; i++) {
					int nInt = Integer.parseInt(inputData.nextToken());
					if (nInt % 4 == 0) {
						numC++;
					} else if (nInt % 4 == 1) {
						num1++;
					} else if (nInt % 4 == 2) {
						num2++;
					}
				}
				int num3 = numG - numC - num1 - num2;
				int ans = numC;
				int set1 = Math.min(num1, num3);
				ans += set1;
				num1 -= set1;
				num3 -= set1;
				ans += num2 / 2;
				num2 = num2 & 1;

				if (num2 > 0) {
					if (num3 > 0) {
						if (num3 >= 2) {
							num3 -= 2;
							ans++;
						}
						ans += (num3 + 3) / 4;
					}
					else if(num1 > 0){
						if(num1 >= 2){
							num1 -= 2;
							ans++;
						}
						ans += (num1 + 3) / 4;
					}
					else{
						ans++;
					}
				}
				else{
					if (num3 > 0) {
						ans += (num3 + 3) / 4;
					}
					else if(num1 > 0){
						ans += (num1 + 3) / 4;
					}
				}
				printer.println("Case #" + cT + ": " + ans);
			}
		}
		printer.close();
	}

}
