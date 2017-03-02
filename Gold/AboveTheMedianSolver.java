import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class AboveTheMedianSolver {

	public static void main(String[] args) throws IOException {
		new AboveTheMedianSolver().execute();
	}

	int numE;
	int numR;

	boolean[] ac;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("median.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("median.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		numR = Integer.parseInt(inputData.nextToken());

		ac = new boolean[numE];
		for (int i = 0; i < numE; i++) {
			ac[i] = Integer.parseInt(reader.readLine()) >= numR;
		}
		reader.close();

		wBIT = new int[numE + 2];
		nBIT = new int[numE + 1];

		int sum = 0;
		update(0);

		long ans = 0;
		for (int i = 0; i < numE; i++) {
			if (ac[i]) {
				sum++;
			} else {
				sum--;
			}
			ans += query(-sum, numE);
			update(sum);
		}
		printer.println(ans);
		printer.close();
	}

	int[] wBIT;
	int[] nBIT;

	int query(int left, int right) {
		int sum = 0;
		if (left >= 0) {
			sum -= query(wBIT, Math.min(left + 1, numE + 1));
		} else {
			sum += query(nBIT, Math.min(-left, numE));
		}
		if (right >= 0) {
			sum += query(wBIT, Math.min(right + 1, numE + 1));
		} else {
			sum -= query(nBIT, Math.min(-right, numE));
		}
		return sum;
	}

	void update(int ind) {
		if (ind >= 0) {
			update(wBIT, ind + 1);
		} else {
			update(nBIT, -ind);
		}
	}

	void update(int[] operand, int ind) {
		while (ind <= numE) {
			operand[ind] += 1;
			ind += (ind & -ind);
		}
	}

	int query(int[] operand, int ind) {
		int sum = 0;
		while (ind > 0) {
			sum += operand[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}

}
