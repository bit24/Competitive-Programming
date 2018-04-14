import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DivCmb_474C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long nR = Integer.parseInt(inputData.nextToken());
		long d = Integer.parseInt(inputData.nextToken());

		long cNum = 1;

		ArrayList<Long> output = new ArrayList<>();
		
		for (int cI = 32; cI >= 1; cI--) {
			if (nR >= (1L << cI) - 1) {
				
				for(int i = 0; i < cI; i++) {
					output.add(cNum);
				}
				
				nR -= (1L << cI) - 1;
				cNum += d;
			}
		}
		
		while(nR > 0) {
			nR--;
			output.add(cNum);
			cNum += d;
		}
		printer.println(output.size());
		for(long i : output) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}
}
