import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ConnectSolver {

	static final int MAXN = 12;
	static final int MAXM = 39;
	static final int inf = 9999;

	static int n;
	static int m;
	static char[][] terrain = new char[2 * MAXN + 1][2 * MAXM + 2];
	static int[][][][] memoTable = new int[MAXN][MAXM][1 << MAXN][2];

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		n = Integer.parseInt(inputData.nextToken());
		m = Integer.parseInt(inputData.nextToken());
		
		for(int i = 0; i < n; i++){
			terrain[i] = reader.readLine().toCharArray();
		}
		n /= 2;
		m /= 2;
		for(int i = 0; i < memoTable.length; i++){
			for(int j = 0; j < memoTable[i].length; j++){
				for(int k = 0; k < memoTable[i][j].length; k++){
					Arrays.fill(memoTable[i][j][k], -1);
				}
			}
		}
		
		System.out.println(recurse(0, 0, 0, 0));
		
	}

	public static int recurse(int r, int c, int mask, int U) {
		if (r == n) {
			return recurse(0, c + 1, mask, 0);
		}
		if (c == m) {
			return 0;
		}

		int minCost = memoTable[r][c][mask][U];
		if (minCost >= 0) {
			return minCost;
		}

		minCost = inf;

		boolean left = ((mask >> r) & 1) != 0;
		boolean right = terrain[2 * r + 1][2 * c + 2] == ' ';
		boolean bottom = terrain[2 * r + 2][2 * c + 1] == ' ';
		boolean top = U == 1;
		boolean figurineStart = terrain[2 * r + 1][2 * c + 1] == 'X';
		
		int newMask;
		int newUp;
		
		// has figurine
		if (figurineStart) {
			// left response
			if (left && !top) {
				newMask = mask & ~(1<<r);
				newUp = 0;
				int cost = 1 + recurse(r + 1, c, newMask, newUp);
				minCost = Math.min(minCost, cost);
			}
			// top response
			if (!left && top) {
				newMask = mask & ~(1<<r);
				newUp = 0;
				int cost = 1 + recurse(r + 1, c, newMask, newUp);
				minCost = Math.min(minCost, cost);
			}
			// can be to the right
			if (!left && !top && right) {
				newMask = mask | (1<<r);
				newUp = 0;
				int cost = 1 + recurse(r + 1, c, newMask, newUp);
				minCost = Math.min(minCost, cost);
			}
			// can be downwards
			if (!left && !top && bottom) {
				newMask = mask & ~(1<<r);
				newUp = 1;
				int cost = 1 + recurse(r + 1, c, newMask, newUp);
				minCost = Math.min(minCost, cost);
			}
		}
		// no figurine
		else {
			// left to top connection
			if (left && top) {
				newMask = mask & ~(1<<r);
				newUp = 0;
				int cost = 2 + recurse(r + 1, c, newMask, newUp);
				minCost = Math.min(minCost, cost);
			}

			else if (left && !top) {
				// can be left to right connection
				if (right) {
					newMask = mask | (1<<r);
					newUp = 0;
					int cost = 2 + recurse(r + 1, c, newMask, newUp);
					minCost = Math.min(minCost, cost);
				}
				// can be left to bottom connection
				if (bottom) {	
					newMask = mask & ~(1<<r);
					newUp = 1;
					int cost = 2 + recurse(r + 1, c, newMask, newUp);
					minCost = Math.min(minCost, cost);
				}
			}

			else if (!left && top) {
				// can be top to right connection
				if (right) {
					newMask = mask | (1<<r);
					newUp = 0;
					int cost = 2 + recurse(r + 1, c, newMask, newUp);
					minCost = Math.min(minCost, cost);
				}
				// can be top to bottom connection
				if (bottom) {
					newMask = mask & ~(1<<r); 
					newUp = 1;
					int cost = 2 + recurse(r + 1, c, newMask, newUp);
					minCost = Math.min(minCost, cost);
				}
			}
			// can be empty
			else if (!left && !top) { 
				newMask = mask & ~(1<<r);
				newUp = 0;
				int cost = recurse(r + 1, c, newMask, newUp);
				minCost = Math.min(minCost, cost);

				// can be right to bottom
				if (right & bottom) {
					newMask = mask | (1<<r);
					newUp = 1;
					cost = 2 + recurse(r + 1, c, newMask, newUp);
					minCost = Math.min(minCost, cost);
				}
			}
		}
		return memoTable[r][c][mask][U] = minCost;
	}
}
