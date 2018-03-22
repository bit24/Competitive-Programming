import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CowJog {

	public static void main(String[] args) throws IOException {
		new CowJog().execute();
	}

	int nC;
	Pair[] cows;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowjog.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nC = Integer.parseInt(inputData.nextToken());
		long fT = Integer.parseInt(inputData.nextToken());

		cows = new Pair[nC];
		for (int i = 0; i < nC; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int p = Integer.parseInt(inputData.nextToken());
			int s = Integer.parseInt(inputData.nextToken());
			cows[i] = new Pair(p, p + s * fT);
		}
		reader.close();

		Arrays.sort(cows);

		TreeMap<Long, Integer> map = new TreeMap<>();
		int req = 1;
		add(map, cows[0].b);
		for (int i = 1; i < nC; i++) {
			Entry<Long, Integer> lwr = map.lowerEntry(cows[i].b);
			if (lwr == null) {
				req++;
			} else if (lwr.getValue() > 1) {
				map.put(lwr.getKey(), lwr.getValue() - 1);
			} else {
				map.remove(lwr.getKey());
			}
			add(map, cows[i].b);
		}
		printer.println(req);
		printer.close();
	}

	void add(TreeMap<Long, Integer> map, Long addend) {
		Integer cnt = map.get(addend);
		map.put(addend, cnt == null ? 1 : cnt + 1);
	}

	void remove(TreeMap<Long, Integer> map, Long subtrahend) {
		Integer cnt = map.get(subtrahend);
		if (cnt != null) {
			if (cnt == 1) {
				map.remove(subtrahend);
			} else {
				map.put(subtrahend, cnt - 1);
			}
		}
	}

	class Pair implements Comparable<Pair> {
		int a;
		long b;

		Pair(int a, long b) {
			this.a = a;
			this.b = b;
		}

		public int compareTo(Pair o) {
			return a < o.a ? -1 : (a == o.a ? (b < o.b ? -1 : (b == o.b ? 0 : 1)) : 1);
		}
	}
}
