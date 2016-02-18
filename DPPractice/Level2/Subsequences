import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.BufferedWriter;

class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int tC = Integer.parseInt(reader.readLine());
		
		while(tC-- > 0){
			String big = reader.readLine();
			String small = reader.readLine();
			BigInteger[][] dp = new BigInteger[small.length()+1][big.length()+1];
			
			for(int i = 0; i <= small.length(); i++){
				dp[i][0] = BigInteger.ZERO;
			}
			
			for(int i = 0; i <= big.length(); i++){
				dp[0][i] = BigInteger.ONE;
			}
			
			for(int i = 1; i <= small.length(); i++){
				for(int j = 1; j <= big.length(); j++){
					dp[i][j] = dp[i][j-1];
					if(small.charAt(i-1) == big.charAt(j-1)){
						dp[i][j] = dp[i][j].add(dp[i-1][j-1]);
					}
				}
			}
			printer.println(dp[small.length()][big.length()]);
			
		}
		reader.close();
		printer.close();
		
	}
	
}
