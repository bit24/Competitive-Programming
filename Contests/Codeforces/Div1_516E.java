import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_516E {

	static int N;
	static char[][] grid;
	static int[] tar;
	static int[] st;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());
		tar = new int[N];
		st = new int[N];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			tar[i] = Integer.parseInt(inputData.nextToken()) - 1;
			st[tar[i]] = i;
		}
		boolean perf = true;
		for (int i = 0; i < N; i++) {
			if (tar[i] != i) {
				perf = false;
				break;
			}
		}

		if (perf) {
			printer.println(N);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					printer.print('.');
				}
				printer.println();
			}
			printer.close();
			return;
		}

		grid = new char[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(grid[i], '.');
		}

		visited = new boolean[N];

		cycle = new ArrayList<>();
		fCycle(st[N - 1]);
		ArrayList<Integer> pLast = cycle;

		int cH = 0;
		for (int i = 0; i < N; i++) {
			if (!visited[i]) {
				cycle = new ArrayList<>();
				fCycle(i);
				if (cycle.size() == 1) {
					continue;
				}

				if (cycle.get(0) != N - 1) {
					grid[cH][N - 1] = '/';
				}
				for (int j = 0; j < cycle.size() - 1; j++) {
					int cSt = cycle.get(j);
					if (cSt == N - 1) {
						continue;
					}
					int cTar = tar[cSt];
					if (cTar < cSt) {
						grid[cH][cTar] = '\\';
						grid[cH][cSt] = '\\';
					} else {
						grid[cH][cSt] = '/';
						grid[cH][cTar] = '/';
					}
					cH++;
				}
				if (cycle.get(cycle.size() - 1) != N - 1 && cycle.get(cycle.size() - 2) != N - 1) {
					grid[cH][N - 1] = '\\';
					grid[cH][cycle.get(cycle.size() - 2)] = '\\';
					cH++;
				}
			}
		}
		if (pLast != null) {
			cycle = pLast;
			if (cycle.get(0) != N - 1) {
				grid[cH][N - 1] = '/';
			}
			for (int j = 0; j < cycle.size() - 1; j++) {
				int cSt = cycle.get(j);
				if (cSt == N - 1) {
					continue;
				}
				int cTar = tar[cSt];
				if (cTar < cSt) {
					grid[cH][cTar] = '\\';
					grid[cH][cSt] = '\\';
				} else {
					grid[cH][cSt] = '/';
					grid[cH][cTar] = '/';
				}
				cH++;
			}
			if (cycle.get(cycle.size() - 1) != N - 1 && cycle.get(cycle.size() - 2) != N - 1) {
				grid[cH][N - 1] = '\\';
				grid[cH][cycle.get(cycle.size() - 2)] = '\\';
				cH++;
			}
		}

		printer.println(N - 1);
		for (int i = N - 1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				printer.print(grid[i][j]);
			}
			printer.println();
		}
		printer.close();
	}

	static boolean[] visited;

	static ArrayList<Integer> cycle;

	static void fCycle(int i) {
		visited[i] = true;
		cycle.add(i);

		int nxt = st[i];
		if (!visited[nxt]) {
			fCycle(nxt);
		}
	}

}
