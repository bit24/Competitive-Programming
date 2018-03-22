import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class GreedyGiftTakers {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("greedy.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("greedy.out")));
		int nC = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int[] cut = new int[nC];
		for (int i = 0; i < nC; i++) {
			cut[i] = nC - 1 - Integer.parseInt(inputData.nextToken());
		}
		reader.close();
		int low = 1;
		int high = nC;
		sLoop:
		while (low != high) {
			int mid = (low + high) / 2;
			int[] cnt = new int[nC];
			for (int i = 0; i < mid; i++) {
				cnt[cut[i]]++;
			}
			int pSum = 0;
			for (int i = 0; i < mid; i++) {
				pSum += cnt[i];
				if (pSum > i) {
					high = mid;
					continue sLoop;
				}
			}
			low = mid + 1;
		}
		printer.println(nC - low);
		printer.close();
	}

}
