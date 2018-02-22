import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div1_431B {

	ArrayList<Person>[] vChanged = new ArrayList[200_001];
	ArrayList<Person>[] hChanged = new ArrayList[200_001];

	public static void main(String[] args) throws IOException {
		new Div1_431B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nP = Integer.parseInt(inputData.nextToken());
		int w = Integer.parseInt(inputData.nextToken());
		int h = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i <= 200_000; i++) {
			vChanged[i] = new ArrayList<>();
			hChanged[i] = new ArrayList<>();
		}

		for (int i = 0; i < nP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			Person cPer = new Person(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()), i);
			if (cPer.group == 1) {
				vChanged[100_000 + cPer.pos - cPer.sTime].add(cPer);
			} else {
				hChanged[100_000 + cPer.pos - cPer.sTime].add(cPer);
			}
		}

		int[] xAns = new int[nP];
		int[] yAns = new int[nP];

		for (int i = 0; i <= 200_000; i++) {
			if (!vChanged[i].isEmpty() || !hChanged[i].isEmpty()) {
				ArrayList<Person> cVChanged = vChanged[i];
				ArrayList<Person> cHChanged = hChanged[i];
				Collections.sort(cVChanged);
				Collections.sort(cHChanged);
				
				
				ArrayDeque<Person> exQueue = new ArrayDeque<>();
				for (Person cPerson : cHChanged) {
					exQueue.addLast(cPerson);
				}

				for (int j = cVChanged.size() - 1; j >= 0; j--) {
					exQueue.addLast(cVChanged.get(j));
				}

				ArrayDeque<Person> enQueue = new ArrayDeque<>();
				for (int j = cVChanged.size() - 1; j >= 0; j--) {
					enQueue.addLast(cVChanged.get(j));
				}

				for (Person cPerson : cHChanged) {
					enQueue.addLast(cPerson);
				}

				while (!exQueue.isEmpty()) {
					Person nEn = enQueue.removeFirst();
					Person nEx = exQueue.removeFirst();
					if (nEx.group == 1) {
						xAns[nEn.ind] = nEx.pos;
						yAns[nEn.ind] = h;
					} else {
						xAns[nEn.ind] = w;
						yAns[nEn.ind] = nEx.pos;
					}
				}
			}
		}

		for (int i = 0; i < nP; i++) {
			printer.println(xAns[i] + " " + yAns[i]);
		}
		printer.close();
	}

	class Person implements Comparable<Person> {
		int group;
		int pos;
		int sTime;
		int ind;

		Person(int group, int pos, int sTime, int ind) {
			this.group = group;
			this.pos = pos;
			this.sTime = sTime;
			this.ind = ind;
		}

		public int compareTo(Person o) {
			return Integer.compare(pos, o.pos);
		}
	}

}
