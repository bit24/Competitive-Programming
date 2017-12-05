import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class beekeeper {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		while (true) {
			int nC = Integer.parseInt(reader.readLine());
			if (nC == 0) {
				break;
			}
			String best = "";
			int bCnt = -1;
			for (int i = 0; i < nC; i++) {
				String nxt = reader.readLine();
				int cCnt = 0;
				for (int j = 1; j < nxt.length(); j++) {
					if (nxt.charAt(j - 1) == nxt.charAt(j) && (nxt.charAt(j) == 'a' || nxt.charAt(j) == 'e'
							|| nxt.charAt(j) == 'i' || nxt.charAt(j) == 'o' || nxt.charAt(j) == 'u' || nxt.charAt(j) == 'y')) {
						cCnt++;
					}
				}
				if(cCnt > bCnt) {
					best = nxt;
					bCnt = cCnt;
				}
			}
			printer.println(best);
		}
		printer.close();
	}
}
