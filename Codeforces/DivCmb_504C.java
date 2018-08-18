import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DivCmb_504C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());
		String line = reader.readLine();

		int nO = 0;
		int nC = 0;

		ArrayList<Character> ans = new ArrayList<>();

		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '(') {
				if (nO < K / 2) {
					ans.add('(');
					nO++;
				}
			} else {
				if(nC < nO) {
					ans.add(')');
					nC++;
				}
			}
		}
		for(Character c : ans) {
			printer.print(c);
		}
		printer.println();
		printer.close();
	}
}
