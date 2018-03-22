import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class WasteRecycling {

	static int ans;

	static int[][] able;
	static int[] nAble;

	static int nW;
	static int[] wags;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nW = Integer.parseInt(inputData.nextToken());
		int nT = Integer.parseInt(inputData.nextToken());
		int nS = Integer.parseInt(inputData.nextToken());

		able = new int[nT][10];
		nAble = new int[nT];

		for (int i = 0; i < nS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int nxt = Integer.parseInt(inputData.nextToken()) - 1;
			while (nxt != -1) {
				able[nxt][nAble[nxt]++] = i;
				nxt = Integer.parseInt(inputData.nextToken()) - 1;
			}
		}

		inputData = new StringTokenizer(reader.readLine());
		wags = new int[nW];
		for (int i = 0; i < nW; i++) {
			wags[i] = Integer.parseInt(inputData.nextToken()) - 1;
		}

		process(0, -1, -1, -1);

		printer.println(ans + 1);
		printer.close();
	}

	static void process(int cI, int first, int second, int third) {
		ans = Math.max(ans, cI - 1);
		if (cI == nW) {
			return;
		}
		boolean cF = false;
		boolean cS = false;
		boolean cT = false;

		for (int i = 0; i < nAble[wags[cI]]; i++) {
			if (able[wags[cI]][i] == first) {
				cF = true;
			}
			if (able[wags[cI]][i] == second) {
				cS = true;
			}
			if (able[wags[cI]][i] == third) {
				cT = true;
			}
		}

		if (second == -1) {
			if (cF || cT) {
				process(cI + 1, first, second, third);
			} else {
				if (first == -1) {
					for (int i = 0; i < nAble[wags[cI]]; i++) {
						process(cI + 1, able[wags[cI]][i], second, third);
					}
				}
				for (int i = 0; i < nAble[wags[cI]]; i++) {
					process(cI + 1, first, able[wags[cI]][i], third);
				}
				if (third == -1) {
					for (int i = 0; i < nAble[wags[cI]]; i++) {
						process(cI + 1, first, second, able[wags[cI]][i]);
					}
				}
			}
		} else {
			if (cF || cS) {
				process(cI + 1, first, second, third);
			} else {
				if (first == -1) {
					for (int i = 0; i < nAble[wags[cI]]; i++) {
						process(cI + 1, able[wags[cI]][i], second, third);
					}
				}

				if (third == -1) {
					for (int i = 0; i < nAble[wags[cI]]; i++) {
						finish(cI, second, able[wags[cI]][i]);
					}
				} else {
					finish(cI, second, third);
				}
			}
		}
	}

	static void finish(int cI, int fA, int fB) {
		ans = Math.max(ans, cI - 1);
		if (cI == nW) {
			return;
		}

		boolean cF = false;
		for (int i = 0; i < nAble[wags[cI]]; i++) {
			if (able[wags[cI]][i] == fA || able[wags[cI]][i] == fB) {
				cF = true;
				break;
			}
		}
		if (cF) {
			finish(cI + 1, fA, fB);
		}
	}

}
