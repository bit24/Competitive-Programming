import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_493A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int len = Integer.parseInt(inputData.nextToken());
		long rCost = Integer.parseInt(inputData.nextToken());
		long fCost = Integer.parseInt(inputData.nextToken());
		String data = reader.readLine();
		long gCnt = 0;
		for (int i = 0; i < len; i++) {
			if (data.charAt(i) == '0') {
				if (i == 0 || data.charAt(i - 1) == '1') {
					gCnt++;
				}
			}
		}
		if (gCnt == 0) {
			printer.println(0);
		} else {
			if (rCost < fCost) {
				printer.println((gCnt - 1) * rCost + fCost);
			} else {
				printer.println(gCnt * fCost);
			}
		}
		printer.close();
	}
}
