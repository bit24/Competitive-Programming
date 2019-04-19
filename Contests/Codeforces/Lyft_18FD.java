import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Lyft_18FD {

	public static void main(String[] args) throws IOException {
		new Lyft_18FD().main();
	}

	int[] nxt;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nQ = Integer.parseInt(reader.readLine());

		int last = 0;
		for (int i = 0; i < nQ; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("1")) {
				int l = Integer.parseInt(inputData.nextToken()) ^ last;
				int r = Integer.parseInt(inputData.nextToken()) ^ last;
				int x = Integer.parseInt(inputData.nextToken()) ^ last;
				if (l > r) {
					int t = l;
					l = r;
					r = t;
				}
				checkAdd(l, r, x);
			} else {
				int l = Integer.parseInt(inputData.nextToken()) ^ last;
				int r = Integer.parseInt(inputData.nextToken()) ^ last;
				if (l > r) {
					int t = l;
					l = r;
					r = t;
				}
				int ans = query(l, r);
				printer.println(ans);
				if (ans == -1) {
					last = 1;
				} else {
					last = ans;
				}
			}
		}
		printer.close();
	}

	TreeMap<Integer, Integer> setI = new TreeMap<>();
	TreeMap<Integer, Integer> preXor = new TreeMap<>();

	int nS = 0;
	ArrayList<Integer>[] sets = new ArrayList[400_005];

	void merge(int set1, int set2, int p1, int p2, int c) {
		if (sets[set1].size() < sets[set2].size()) {
			int temp = set1;
			set1 = set2;
			set2 = temp;
			temp = p1;
			p1 = p2;
			p2 = temp;
		}

		int xorMod = preXor.get(p1) ^ preXor.get(p2) ^ c;

		for (int i : sets[set2]) {
			preXor.put(i, preXor.get(i) ^ xorMod);
			setI.put(i, set1);
			sets[set1].add(i);
		}
	}

	int query(int l, int r) {
		l--;
		Integer setL = setI.get(l);
		Integer setR = setI.get(r);

		if (setL == null || !setL.equals(setR)) {
			return -1;
		}
		return preXor.get(l) ^ preXor.get(r);
	}

	void checkAdd(int l, int r, int c) {
		if (query(l, r) == -1) {
			addI(l, r, c);
		}
	}

	// assumes valid
	void addI(int l, int r, int c) {
		l--;
		Integer setL = setI.get(l);
		if (setL == null) {
			setL = init(l);
		}
		Integer setR = setI.get(r);
		if (setR == null) {
			setR = init(r);
		}

		merge(setL, setR, l, r, c);
	}

	int init(int p) {
		setI.put(p, nS);
		preXor.put(p, 0);
		sets[nS] = new ArrayList<>();
		sets[nS].add(p);
		return nS++;
	}

}
