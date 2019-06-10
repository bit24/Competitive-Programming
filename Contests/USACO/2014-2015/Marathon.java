import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Marathon {

	public static void main(String[] args) throws IOException {
		new Marathon().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("marathon.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numElements = Integer.parseInt(inputData.nextToken());
		int numOperations = Integer.parseInt(inputData.nextToken());

		int[] x = new int[numElements];
		int[] y = new int[numElements];

		for (int i = 0; i < numElements; i++) {
			inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] distances = new int[numElements - 1];
		for (int i = 0; i + 1 < numElements; i++) {
			distances[i] = distance(x[i], y[i], x[i + 1], y[i + 1]);
		}

		int[] difference = new int[numElements - 2];
		for (int i = 0; i + 2 < numElements; i++) {
			difference[i] = distances[i] + distances[i + 1] - distance(x[i], y[i], x[i + 2], y[i + 2]);
		}

		SegmentTree distanceSumTree = new SegmentTree(new Sum());
		distanceSumTree.build(distances);

		SegmentTree differenceMaxTree = new SegmentTree(new Max());
		differenceMaxTree.build(difference);

		for (int i = 0; i < numOperations; i++) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("U")) {
				int updateIndex = Integer.parseInt(inputData.nextToken()) - 1;
				int newX = Integer.parseInt(inputData.nextToken());
				int newY = Integer.parseInt(inputData.nextToken());

				x[updateIndex] = newX;
				y[updateIndex] = newY;

				if (updateIndex - 1 >= 0) {
					distanceSumTree.modify(updateIndex - 1,
							distance(x[updateIndex - 1], y[updateIndex - 1], newX, newY));
				}
				if (updateIndex + 1 < numOperations) {
					distanceSumTree.modify(updateIndex, distance(newX, newY, x[updateIndex + 1], y[updateIndex + 1]));
				}

				if (updateIndex - 2 >= 0) {
					differenceMaxTree.modify(updateIndex - 2,
							distanceSumTree.get(updateIndex - 2) + distanceSumTree.get(updateIndex - 1)
									- distance(x[updateIndex - 2], y[updateIndex - 2], newX, newY));
				}

				if (updateIndex - 1 >= 0 && updateIndex + 1 < numOperations) {
					differenceMaxTree.modify(updateIndex - 1, distanceSumTree.get(updateIndex - 1)
							+ distanceSumTree.get(updateIndex)
							- distance(x[updateIndex - 1], y[updateIndex - 1], x[updateIndex + 1], y[updateIndex + 1]));
				}

				if (updateIndex + 2 < numOperations) {
					differenceMaxTree.modify(updateIndex,
							distanceSumTree.get(updateIndex) + distanceSumTree.get(updateIndex + 1)
									- distance(newX, newY, x[updateIndex + 2], y[updateIndex + 2]));
				}
			} else {
				int left = Integer.parseInt(inputData.nextToken()) - 1;
				int right = Integer.parseInt(inputData.nextToken());
				printer.println(distanceSumTree.query(left, right - 1) - differenceMaxTree.query(left, right - 2));
			}
		}
		reader.close();
		printer.close();
	}

	public class Max implements FunctionHolder {
		public int function(int a, int b) {
			return a > b ? a : b;
		}
	}

	public class Sum implements FunctionHolder {
		public int function(int a, int b) {
			return a + b;
		}
	}

	public int distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	public class SegmentTree {

		int size;
		int[] data;
		FunctionHolder operator;

		public int get(int index) {
			return data[index + size];
		}

		public void modify(int index, int newValue) {
			index += size;
			data[index] = newValue;
			for (index >>= 1; index > 0; index >>= 1) {
				data[index] = operator.function(data[index << 1], data[index << 1 | 1]);
			}
		}

		public void build(int[] input) {
			int maxPower = 0;
			int inputLength = input.length;
			while (inputLength != 0) {
				inputLength >>= 1;
				maxPower++;
			}
			size = 1 << maxPower;
			data = new int[size * 2];

			for (int i = 0; i < input.length; i++) {
				data[i + size] = input[i];
			}
			for (int i = size - 1; i > 0; i--) {
				data[i] = operator.function(data[i << 1], data[i << 1 | 1]);
			}
		}

		// left inclusive, right exclusive
		public int query(int left, int right) {
			int ans = 0;
			for (left += size, right += size; left < right; left >>= 1, right >>= 1) {
				if ((left & 1) != 0) {
					ans = operator.function(ans, data[left++]);
				}
				// because right exclusive
				if ((right & 1) != 0) {
					ans = operator.function(ans, data[--right]);
				}
			}
			return ans;
		}

		SegmentTree(FunctionHolder operator) {
			this.operator = operator;
		}
	}

	interface FunctionHolder {
		public int function(int a, int b);
	}
}