import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class CowPhotography {

	static int[][] ind;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("photo.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("photo.out")));
		int nE = Integer.parseInt(reader.readLine());
		int[][] order = new int[5][nE];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < nE; j++) {
				order[i][j] = Integer.parseInt(reader.readLine().trim());
			}
		}
		reader.close();

		int[] allID = Arrays.copyOf(order[0], nE);
		Arrays.sort(allID);
		TreeMap<Integer, Integer> remap = new TreeMap<Integer, Integer>();
		int[] bMap = new int[nE];
		for (int i = 0; i < nE; i++) {
			remap.put(allID[i], i);
			bMap[i] = allID[i];
		}

		ind = new int[5][nE];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < nE; j++) {
				ind[i][remap.get(order[i][j])] = j;
			}
		}

		Integer[] rcn = new Integer[nE];
		for (int i = 0; i < nE; i++) {
			rcn[i] = i;
		}
		Arrays.sort(rcn, detOrder);
		for (int i = 0; i < nE; i++) {
			printer.println(bMap[rcn[i]]);
		}
		printer.close();
	}

	static Comparator<Integer> detOrder = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			int less = 0;
			for (int i = 0; i < 5; i++) {
				if (ind[i][o1] < ind[i][o2]) {
					less++;
				}
			}
			return less >= 3 ? -1 : 1;
		}
	};

}
