import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Tourist {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tourist.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("tourist.out")));
		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int N = Integer.parseInt(inputData.nextToken());
			int K = Integer.parseInt(inputData.nextToken());
			long V = Long.parseLong(inputData.nextToken());
			String[] names = new String[N];
			for (int i = 0; i < N; i++) {
				names[i] = reader.readLine();
			}

			long seen = K * (V - 1L) % N;
			StringBuilder output = new StringBuilder();
			output.append("Case #" + cT + ":");

			int nxt = (int) seen;

			if (nxt + K > N) {
				int end = (nxt + K) % N;
				for (int i = 0; i < end; i++) {
					output.append(" ");
					output.append(names[i]);
				}
			}

			for (int i = 0; i < K && nxt < N; i++, nxt++) {
				output.append(" ");
				output.append(names[nxt]);
			}
			printer.println(output.toString());
		}
		reader.close();
		printer.close();
	}
}