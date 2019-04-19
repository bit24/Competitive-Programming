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

public class Div1_433B {

	public static void main(String[] args) throws IOException {
		new Div1_433B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nJ = Integer.parseInt(inputData.nextToken());
		int nF = Integer.parseInt(inputData.nextToken());
		int lR = Integer.parseInt(inputData.nextToken());

		ArrayList<Flight>[] aFlight = new ArrayList[nJ + 1];
		ArrayList<Flight>[] dFlight = new ArrayList[nJ + 1];

		for (int i = 1; i <= nJ; i++) {
			aFlight[i] = new ArrayList<>();
			dFlight[i] = new ArrayList<>();
		}

		for (int i = 0; i < nF; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int cT = Integer.parseInt(inputData.nextToken());
			int dep = Integer.parseInt(inputData.nextToken());
			int arr = Integer.parseInt(inputData.nextToken());
			int cC = Integer.parseInt(inputData.nextToken());

			if (arr == 0) {
				aFlight[dep].add(new Flight(cT + 1, cC, 0, dep));
			} else {
				dFlight[arr].add(new Flight(cT - lR + 1, cC, 1, arr));
			}
		}

		ArrayDeque<Flight>[] aQueue = new ArrayDeque[nJ + 1];
		ArrayDeque<Flight>[] dQueue = new ArrayDeque[nJ + 1];

		ArrayList<Flight> sFlights = new ArrayList<>();
		for (int i = 1; i <= nJ; i++) {
			ArrayList<Flight> cList = aFlight[i];
			Collections.sort(cList);
			ArrayDeque<Flight> nList = new ArrayDeque<>();

			for (int j = 0; j < cList.size(); j++) {
				if (nList.isEmpty() || nList.peekLast().cost > cList.get(j).cost) {
					nList.addLast(cList.get(j));
				}
			}
			aQueue[i] = nList;
			sFlights.addAll(nList);

			cList = dFlight[i];
			Collections.sort(cList);
			nList = new ArrayDeque<>();

			for (int j = cList.size() - 1; j >= 0; j--) {
				if (nList.isEmpty() || cList.get(j).cost < nList.peekFirst().cost) {
					nList.addFirst(cList.get(j));
				}
			}
			dQueue[i] = nList;
			sFlights.addAll(nList);
		}

		Collections.sort(sFlights);
		ArrayDeque<Flight> sQueue = new ArrayDeque<>(sFlights);

		int sTime = 0;
		for (int i = 1; i <= nJ; i++) {
			if (aQueue[i].isEmpty()) {
				printer.println(-1);
				printer.close();
				return;
			}
			sTime = Math.max(sTime, aQueue[i].peekFirst().time);
		}

		int[] lastA = new int[nJ + 1];

		long cCost = 0;
		for (int i = 1; i <= nJ; i++) {
			Flight first = aQueue[i].peekFirst();
			if (first == null) {
				printer.println(-1);
				printer.close();
				return;
			}
			while (!aQueue[i].isEmpty() && aQueue[i].peekFirst().time <= sTime) {
				first = aQueue[i].removeFirst();
			}
			aQueue[i].addFirst(first);
			lastA[i] = first.cost;
			cCost += first.cost;

			while (!dQueue[i].isEmpty() && dQueue[i].peekFirst().time <= sTime) {
				dQueue[i].removeFirst();
			}
			if (dQueue[i].isEmpty()) {
				printer.println(-1);
				printer.close();
				return;
			}
			cCost += dQueue[i].peekFirst().cost;
		}

		long mAns = cCost;

		while (sQueue.peekFirst().time <= sTime) {
			sQueue.removeFirst();
		}

		while (!sFlights.isEmpty()) {
			long cTime = sQueue.peekFirst().time;
			while (sQueue.peekFirst().time == cTime) {
				Flight nxt = sQueue.removeFirst();
				if (nxt.type == 0) {
					cCost -= lastA[nxt.city];
					cCost += nxt.cost;
					lastA[nxt.city] = nxt.cost;
				} else {
					cCost -= nxt.cost;
					dQueue[nxt.city].removeFirst();
					if (dQueue[nxt.city].isEmpty()) {
						printer.println(mAns);
						printer.close();
						return;
					}
					cCost += dQueue[nxt.city].peekFirst().cost;
				}
			}
			mAns = Math.min(mAns, cCost);
		}
		printer.println(mAns);
		printer.close();
	}

	class Flight implements Comparable<Flight> {
		int time;
		int cost;
		int type;
		int city;

		Flight(int time, int cost, int type, int city) {
			this.time = time;
			this.cost = cost;
			this.type = type;
			this.city = city;
		}

		public int compareTo(Flight o) {
			return Integer.compare(time, o.time);
		}
	}

}
