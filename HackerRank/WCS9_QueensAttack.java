import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class WCS9_QueensAttack {

	public static void main(String[] args) throws IOException {
		new WCS9_QueensAttack().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int size = Integer.parseInt(inputData.nextToken());
		int numB = Integer.parseInt(inputData.nextToken());
		inputData = new StringTokenizer(reader.readLine());
		int sX = Integer.parseInt(inputData.nextToken());
		int sY = Integer.parseInt(inputData.nextToken());

		TreeSet<Pair> blocking = new TreeSet<Pair>();
		for (int i = 0; i < numB; i++) {
			inputData = new StringTokenizer(reader.readLine());
			blocking.add(new Pair(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken())));
		}
		reader.close();

		int ans = 0;
		for (int cD = 0; cD < 8; cD++) {
			int cX = sX;
			int cY = sY;

			while (true) {
				cX = cX + xMod[cD];
				cY = cY + yMod[cD];
				if (1 <= cX && cX <= size && 1 <= cY && cY <= size) {
					if (blocking.contains(new Pair(cX, cY))) {
						break;
					}
					ans++;
				} else {
					break;
				}
			}
		}
		printer.println(ans);
		printer.close();
	}

	int[] xMod = new int[] { 0, 1, 1, 1, 0, -1, -1, -1 };
	int[] yMod = new int[] { 1, 1, 0, -1, -1, -1, 0, 1 };

	class Pair implements Comparable<Pair> {
		int x;
		int y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Pair o) {
			return x < o.x ? -1 : (x == o.x ? (y < o.y ? -1 : (y == o.y ? 0 : 1)) : 1);
		}
	}

}
