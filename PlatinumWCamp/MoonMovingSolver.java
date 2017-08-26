import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class MoonMovingSolver {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long start = Integer.parseInt(inputData.nextToken());
		int query = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		int n1 = Integer.parseInt(inputData.nextToken());
		int a1 = Integer.parseInt(inputData.nextToken());
		int d1 = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		int n2 = Integer.parseInt(inputData.nextToken());
		int a2 = Integer.parseInt(inputData.nextToken());
		int d2 = Integer.parseInt(inputData.nextToken());
		reader.close();

		PriorityQueue<Long> set = new PriorityQueue<Long>();
		set.add(start);

		for (int count = 1; count < query; count++) {
			long next = set.peek();
			//System.out.println(next + " -> " + (next * n1 / d1 + a1));
			//System.out.println(next + " -> " + (next * n2 / d2 + a2));
			set.add(next * n1 / d1 + a1);
			set.add(next * n2 / d2 + a2);

			while (!set.isEmpty() && set.peek() == next) {
				//System.out.println(set.remove());
				set.remove();
			}
		}
		System.out.println(set.remove());
	}

}
