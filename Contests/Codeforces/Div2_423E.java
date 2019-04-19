import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_423E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String inp = reader.readLine();
		int len = inp.length();
		int[] str = new int[len + 1];

		for (int i = 1; i <= len; i++) {
			str[i] = toInt(inp.charAt(i - 1));
		}

		// letter, rL, off, bit
		int[][][][] count = new int[4][11][][];

		for (int cLet = 0; cLet < 4; cLet++) {
			for (int rL = 1; rL <= 10; rL++) {
				count[cLet][rL] = new int[rL + 1][(len + rL - 1) / rL + 1];
			}
		}

		for (int rL = 1; rL <= 10; rL++) {
			for (int cI = 1; cI <= len; cI++) {
				int cR = (cI + rL - 1) / rL;
				int cOff = cI % rL;
				if (cOff == 0) {
					cOff = rL;
				}
				update(count[str[cI]][rL][cOff], cR, 1);
			}
		}

		int numQ = Integer.parseInt(reader.readLine());
		while (numQ-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("1")) {
				int uI = Integer.parseInt(inputData.nextToken());
				int nLet = toInt(inputData.nextToken().charAt(0));

				for (int rL = 1; rL <= 10; rL++) {
					int cR = (uI + rL - 1) / rL;
					int cOff = uI % rL;
					if (cOff == 0) {
						cOff = rL;
					}
					update(count[str[uI]][rL][cOff], cR, -1);
					update(count[nLet][rL][cOff], cR, 1);
				}
				str[uI] = nLet;
			} else {
				int qL = Integer.parseInt(inputData.nextToken());
				int qR = Integer.parseInt(inputData.nextToken());
				String qStr = inputData.nextToken();
				int rL = qStr.length();

				int sum = 0;
				for (int qSI = 1; qSI <= qStr.length() && qL + qSI - 1 <= qR; qSI++) {
					int qLet = toInt(qStr.charAt(qSI - 1));
					int fR = (qL + qSI + rL - 2) / rL;
					// qL + qSI-1 + (x-1)*rL <= qR
					// x <= (qR - qL -qSI+1)/rL + 1
					int nR = (qR - qL - qSI + 1) / rL + 1;
					int cOff = (qL + qSI - 1) % rL;
					if (cOff == 0) {
						cOff = rL;
					}
					sum += query(count[qLet][rL][cOff], fR, fR + nR - 1);
				}
				printer.println(sum);
			}
		}
		printer.close();
	}

	static int toInt(char inp) {
		switch (inp) {
			case 'A':
				return 0;
			case 'T':
				return 1;
			case 'C':
				return 2;
			case 'G':
				return 3;
		}
		return -1;
	}

	static void update(int[] bit, int ind, int dlt) {
		while (ind < bit.length) {
			bit[ind] += dlt;
			ind += (ind & -ind);
		}
	}

	static int query(int[] bit, int ind) {
		int sum = 0;
		while (ind > 0) {
			sum += bit[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}

	static int query(int[] bit, int l, int r) {
		if (l == 1) {
			return query(bit, r);
		} else {
			return query(bit, r) - query(bit, l - 1);
		}
	}

}
