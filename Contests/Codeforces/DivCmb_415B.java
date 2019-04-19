import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_415B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numP = Integer.parseInt(inputData.nextToken());
		double height = Integer.parseInt(inputData.nextToken());

		double tarArea = height / 2.0 / numP;
		double totArea = height / 2;
		double lCut = 0;

		// (cutP/height)^2*totArea - i*tarArea = tarArea
		for (int i = 0; i < numP - 1; i++) {
			lCut = Math.sqrt((tarArea + i * tarArea) / totArea * height * height);
			printer.println(lCut);
		}
		printer.close();
	}

}
