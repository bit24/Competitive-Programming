import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_422B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int pLen = Integer.parseInt(inputData.nextToken());
		int tLen = Integer.parseInt(inputData.nextToken());

		String p = reader.readLine();
		String t = reader.readLine();

		int minC = pLen + 1;
		ArrayList<Integer> sol = new ArrayList<Integer>();

		for (int i = 0; i + pLen <= tLen; i++) {
			int cCost = 0;
			ArrayList<Integer> cSol = new ArrayList<Integer>();
			for (int j = 0; j < pLen; j++) {
				if (p.charAt(j) != t.charAt(i + j)) {
					cCost++;
					cSol.add(j + 1);
				}
			}
			if (cCost < minC) {
				minC = cCost;
				sol = cSol;
			}
		}
		printer.println(minC);

		for (int i : sol) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}

}
