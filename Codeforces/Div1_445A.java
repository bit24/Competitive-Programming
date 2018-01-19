import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_445A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nL = Integer.parseInt(reader.readLine());
		int[] logs = new int[nL + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nL; i++) {
			logs[i] = Integer.parseInt(inputData.nextToken());
		}
		boolean[] lVis = new boolean[nL + 1];

		int nR = 1;
		for (int i = 1; i <= nL; i++) {
			if(lVis[logs[i]]) {
				nR++;
			}
			else {
				lVis[logs[i]] = true;
			}
		}
		printer.println(nR);
		printer.close();
	}

}
