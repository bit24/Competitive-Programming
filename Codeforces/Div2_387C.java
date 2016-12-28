import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_387C {

	public static void main(String[] args) throws IOException {
		new Div2_387C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numServers = Integer.parseInt(inputData.nextToken());
		int numTasks = Integer.parseInt(inputData.nextToken());

		PriorityQueue<Integer> serverPool = new PriorityQueue<Integer>();
		for (int i = 1; i <= numServers; i++) {
			serverPool.add(i);
		}

		PriorityQueue<Event> returnQueue = new PriorityQueue<Event>();

		for (int cTask = 0; cTask < numTasks; cTask++) {
			inputData = new StringTokenizer(reader.readLine());
			int time = Integer.parseInt(inputData.nextToken());
			int serverNeed = Integer.parseInt(inputData.nextToken());
			int timeNeed = Integer.parseInt(inputData.nextToken());

			while (!returnQueue.isEmpty() && returnQueue.peek().key < time) {
				serverPool.add(returnQueue.remove().value);
			}

			if (serverPool.size() >= serverNeed) {
				int finishingTime = time + timeNeed - 1;

				int sum = 0;
				for (int i = 0; i < serverNeed; i++) {
					int server = serverPool.remove();
					returnQueue.add(new Event(finishingTime, server));
					sum += server;
				}
				printer.println(sum);
			} else {
				printer.println(-1);
			}
		}
		reader.close();
		printer.close();
	}

	class Event implements Comparable<Event> {
		int key;
		int value;

		Event(int key, int value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(Event o) {
			return Integer.compare(key, o.key);
		}
	}

}
