import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class TheBessieShuffle {

	static int[] next;
	static ArrayList<ArrayList<Integer>> cycles;
	static int[] cycleN;
	static int[] cycleP;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("shufflegold.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("shufflegold.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nE = Integer.parseInt(inputData.nextToken());
		int permL = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		next = new int[permL];
		for (int i = 0; i < permL; i++) {
			next[i] = Integer.parseInt(reader.readLine()) - 2;
		}

		cycleN = new int[permL];
		cycleP = new int[permL];
		Arrays.fill(cycleN, -1);
		ArrayList<Integer> pipe = new ArrayList<Integer>();
		for (int cI = permL - 1; cI >= 0;) {
			pipe.add(cI);
			cycleN[cI] = -2;
			cI = next[cI];
		}
		Collections.reverse(pipe);
		int pipeL = pipe.size();

		int[] pipePos = new int[permL];
		for (int i = 0; i < pipeL; i++) {
			pipePos[pipe.get(i)] = i;
		}

		cycles = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < permL; i++) {
			if (cycleN[i] == -1) {
				ArrayList<Integer> cCycle = new ArrayList<Integer>();
				dfs(i, cCycle, cycles.size());
				cycles.add(cCycle);
			}
		}

		while (nQ-- > 0) {
			int cQ = nE - Integer.parseInt(reader.readLine());
			if (cQ >= nE - permL + 1) {
				int oI = cQ - (nE - permL + 1);
				if (cycleN[oI] == -2) {
					int pipeP = nE - permL + 1 + pipePos[oI];
					if (pipeP < pipeL) {
						printer.println(1 + pipe.get(pipeP));
					} else {
						printer.println(1 + pipeP - pipeL + permL);
					}
				} else {
					ArrayList<Integer> cCycle = cycles.get(cycleN[oI]);
					printer.println(1 + cCycle.get(mod(cycleP[oI] - (nE - permL + 1), cCycle.size())));
				}
			} else if (cQ < pipeL) {
				printer.println(1 + pipe.get(cQ));
			} else {
				printer.println(1 + cQ - pipeL + permL);
			}
		}
		reader.close();
		printer.close();
	}

	static int mod(int a, int b) {
		a %= b;
		return a < 0 ? a + b : a;
	}

	static void dfs(int i, ArrayList<Integer> cCycle, int cCycleN) {
		cycleP[i] = cCycle.size();
		cCycle.add(i);
		cycleN[i] = cCycleN;
		if (cycleN[next[i]] == -1) {
			dfs(next[i], cCycle, cCycleN);
		}
	}

}
