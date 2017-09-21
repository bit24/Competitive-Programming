import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class GangsOfInstanbullCowstantinople {

	public static void main(String[] args) throws IOException {
		new GangsOfInstanbullCowstantinople().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("gangs.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("gangs.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		inputData.nextToken();
		int nG = Integer.parseInt(inputData.nextToken());

		int[] size = new int[nG];
		for (int i = 0; i < nG; i++) {
			size[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		if (nG == 1) {
			printer.println("YES");
			printer.println(size[0]);
			for (int i = 0; i < size[0]; i++) {
				printer.println(1);
			}
			printer.close();
			return;
		}
		if (nG == 2) {
			if (size[0] <= size[1]) {
				printer.println("NO");
			} else {
				printer.println("YES");
				printer.println(size[0] - size[1]);
				for (int i = 0; i < size[0]; i++) {
					printer.println(1);
				}
				for (int i = 0; i < size[1]; i++) {
					printer.println(2);
				}
			}
			printer.close();
			return;
		}

		PriorityQueue<KI> processQ = new PriorityQueue<KI>(Collections.reverseOrder());

		for (int i = 1; i < nG; i++) {
			processQ.add(new KI(size[i], i));
		}

		while (processQ.size() > 2) {
			KI max = processQ.remove();
			KI sec = processQ.remove();

			int delta = sec.k - (processQ.peek().k - 1);
			max.k -= delta;
			sec.k -= delta;
			if (max.k > 0) {
				processQ.add(max);
			}
			if (sec.k > 0) {
				processQ.add(sec);
			}
		}
		int pFirst = 0;
		if (processQ.size() == 2) {
			pFirst = processQ.remove().k - processQ.remove().k;
		} else if (processQ.size() == 1) {
			pFirst = processQ.remove().k;
		}
		if (pFirst >= size[0]) {
			printer.println("NO");
			printer.close();
			return;
		}

		printer.println("YES");
		int pEnd = size[0] - pFirst;
		printer.println(pEnd);
		for (int i = 0; i < pFirst; i++) {
			printer.println(1);
		}
		size[0] = 0;

		int sum = pFirst;
		TreeSet<KI> maxQ = new TreeSet<KI>();
		for (int i = 1; i < nG; i++) {
			maxQ.add(new KI(size[i], i));
			sum += size[i];
		}

		int fG = 0;
		int fC = pFirst;

		int nA = 0;
		while (sum > 0) {
			while (size[nA] == 0) {
				nA++;
			}
			KI largest = maxQ.last();
			if (fG != nA && fC > 0 && largest.k * 2 > sum - 2) {

				if (fG == largest.i) {
					if (size[fG] == 1) {
						maxQ.remove(largest);
					}
					fC++;
					size[fG]--;
				} else {
					if (fC == 0) {
						fG = largest.i;
						fC = 1;
						if (size[fG] == 1) {
							maxQ.remove(largest);
						}
						size[fG]--;
					} else {
						fC--;
						sum -= 2;
						maxQ.remove(largest);
						if (--size[largest.i] > 0) {
							largest.k--;
							maxQ.add(largest);
						}
					}
				}
				printer.println(largest.i + 1);
			} else {

				maxQ.remove(new KI(size[nA], nA));

				if (fG == nA) {
					if (size[nA] == 1) {
						maxQ.remove(new KI(size[nA] + fC, nA));
					}
					fC++;
					size[nA]--;
				} else {
					if (fC == 0) {
						fG = nA;
						fC = 1;
						if (size[nA] == 1) {
							maxQ.remove(new KI(size[nA], nA));
						}
						size[nA]--;
					} else {
						fC--;
						sum -= 2;
						maxQ.remove(new KI(size[nA], nA));
						if (--size[nA] > 0) {
							maxQ.add(new KI(size[nA], nA));
						}
					}
				}
				printer.println(nA + 1);
			}
		}

		for (int i = 0; i < pEnd; i++) {
			printer.println(1);
		}
		printer.close();
	}

	class KI implements Comparable<KI> {
		int k;
		int i;

		KI(int k, int i) {
			this.k = k;
			this.i = i;
		}

		public int compareTo(KI o) {
			return k < o.k ? -1 : k == o.k ? Integer.compare(i, o.i) : 1;
		}
	}

}
