import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_17B {

	public static void main(String[] args) throws IOException {
		new Edu_17B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int[] numL = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
				Integer.parseInt(inputData.nextToken()) };

		int numM = Integer.parseInt(reader.readLine());

		Item[] items = new Item[numM];

		for (int i = 0; i < numM; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int cost = Integer.parseInt(inputData.nextToken());
			String type = inputData.nextToken();

			if (type.charAt(0) == 'U') {
				items[i] = new Item(cost, 0);
			} else {
				items[i] = new Item(cost, 1);
			}
		}
		reader.close();

		Arrays.sort(items);

		int count = 0;

		long tCost = 0;

		for (Item current : items) {
			if (numL[current.group] > 0) {
				numL[current.group]--;
				count++;
				tCost += current.cost;
			} else if (numL[2] > 0) {
				numL[2]--;
				count++;
				tCost += current.cost;
			}
		}

		System.out.println(count + " " + tCost);
	}

	class Item implements Comparable<Item> {
		int cost;
		int group;

		public int compareTo(Item o) {
			return Integer.compare(cost, o.cost);
		}

		Item(int c, int g) {
			cost = c;
			group = g;
		}
	}

}
