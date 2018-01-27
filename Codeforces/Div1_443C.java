import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_443C {

	public static void main(String[] args) throws IOException {
		new Div1_443C().execute();
	}

	int n;
	int k;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		k = Integer.parseInt(inputData.nextToken());

		TreeSet<Comp> cSet = new TreeSet<Comp>();
		for (int i = 0; i < n; i++) {
			Comp nC = new Comp();
			nC.cnt = 1;

			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < k; j++) {
				nC.max[j] = nC.min[j] = Integer.parseInt(inputData.nextToken());
			}

			Comp lower = cSet.lower(nC);
			while (lower != null) {
				if (oLap(lower, nC)) {
					cSet.remove(lower);
					nC = merge(lower, nC);
					lower = cSet.lower(nC);
				} else {
					break;
				}
			}
			
			Comp higher = cSet.higher(nC);
			while(higher != null) {
				if(oLap(nC, higher)) {
					cSet.remove(higher);
					nC = merge(nC, higher);
					higher = cSet.higher(nC);
				}
				else {
					break;
				}
			}
			cSet.add(nC);
			printer.println(cSet.last().cnt);
		}
		printer.close();
	}

	class Comp implements Comparable<Comp> {
		int[] max = new int[k];
		int[] min = new int[k];
		int cnt;

		public int compareTo(Comp o) {
			return Integer.compare(min[0], o.min[0]);
		}
	}

	boolean oLap(Comp a, Comp b) {
		for (int i = 0; i < k; i++) {
			if (a.max[i] > b.min[i]) {
				return true;
			}
		}
		return false;
	}

	Comp merge(Comp a, Comp b) {
		Comp nC = new Comp();

		for (int i = 0; i < k; i++) {
			nC.max[i] = Math.max(a.max[i], b.max[i]);
			nC.min[i] = Math.min(a.min[i], b.min[i]);
		}

		nC.cnt = a.cnt + b.cnt;
		return nC;
	}
}