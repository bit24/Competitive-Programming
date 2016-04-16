import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class AmazingRobotsSolver {

	// botNum, time, i, j
	int[][][][] guardPosition = new int[2][][][];
	char[][][] terrain = new char[2][][];
	int[] startI = new int[2];
	int[] startJ = new int[2];
	int[] numGuards = new int[2];
	int[] maxI = new int[2];
	int[] maxJ = new int[2];

	static final char OPEN = '.';
	static final char BLOCKED = '#';
	static final char START = 'X';
	static int[] iMod = new int[] { -1, 0, 0, 1 };
	static int[] jMod = new int[] { 0, -1, 1, 0 };

	public static void main(String[] args) throws IOException {
		new AmazingRobotsSolver().solve();
	}

	static int[][][][][] stateCost;
	
	int ans = Integer.MAX_VALUE;

	public void processState(State currentState) {
		int time = currentState.time;
		int[] i = currentState.i;
		int[] j = currentState.j;
		
		if(outside(0, i[0], j[0]) && outside(1, i[1], j[1])){
			ans = stateCost[time][i[0]][j[0]][i[1]][j[1]];
		}
		
		directionLoop: for (int direction = 0; direction < 4; direction++) {
			int[] newI = new int[2];
			int[] newJ = new int[2];

			for (int currentBot = 0; currentBot < 2; currentBot++) {
				// is already outside
				if (outside(currentBot, i[currentBot], j[currentBot])) {
					newI[currentBot] = maxI[currentBot];
					newJ[currentBot] = maxJ[currentBot];
					continue;
				}
				// moves outside
				if (outside(currentBot, i[currentBot] + iMod[direction], j[currentBot] + jMod[direction])) {
					newI[currentBot] = maxI[currentBot];
					newJ[currentBot] = maxJ[currentBot];
					continue;
				}

				if (terrain[currentBot][i[currentBot] + iMod[direction]][j[currentBot] + jMod[direction]] != BLOCKED) {
					newI[currentBot] = i[currentBot] + iMod[direction];
					newJ[currentBot] = j[currentBot] + jMod[direction];
					if (guardPosition[currentBot][time][newI[currentBot]][newJ[currentBot]] == getOpposite(direction)) {
						continue directionLoop;
					}
				} else {
					newI[currentBot] = i[currentBot];
					newJ[currentBot] = j[currentBot];
				}
				// There exists a guard...
				if (guardPosition[currentBot][(time + 1) % 12][newI[currentBot]][newJ[currentBot]] != -1) {
					continue directionLoop;
				}
			}
			int previousCost = stateCost[time][i[0]][j[0]][i[1]][j[1]];
			if(stateCost[(time+1) % 12][newI[0]][newJ[0]][newI[1]][newJ[1]] > previousCost + 1){
				stateCost[(time+1) % 12][newI[0]][newJ[0]][newI[1]][newJ[1]] = previousCost + 1;
				searchQueue.add(new State((time + 1) % 12, newI, newJ));
			}
		}
	}

	public boolean outside(int currentBot, int i, int j) {
		return i < 0 || i >= maxI[currentBot] || j < 0 || j >= maxJ[currentBot];
	}

	public void solve() throws IOException {
		input();
		stateCost = new int[12][maxI[0]+1][maxJ[0]+1][maxI[1]+1][maxJ[1]+1];
		for (int i = 0; i < stateCost.length; i++) {
			for (int j = 0; j < stateCost[i].length; j++) {
				for (int k = 0; k < stateCost[i][j].length; k++) {
					for (int l = 0; l < stateCost[i][j][k].length; l++) {
						Arrays.fill(stateCost[i][j][k][l], Integer.MAX_VALUE);
					}
				}
			}
		}
		
		int result = initializeBFS();

		if (result > 1000) {
			System.out.println(-1);
		} else {
			System.out.println(result);
		}
	}

	BufferedReader reader;

	public void input(int current) throws IOException {
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		maxI[current] = Integer.parseInt(inputData.nextToken());
		maxJ[current] = Integer.parseInt(inputData.nextToken());
		terrain[current] = new char[maxI[current]][maxJ[current]];

		for (int i = 0; i < maxI[current]; i++) {
			terrain[current][i] = reader.readLine().toCharArray();
			for (int j = 0; j < maxJ[current]; j++) {
				if (terrain[current][i][j] == START) {
					startI[current] = i;
					startJ[current] = j;
				}
			}
		}

		guardPosition[current] = new int[12][maxI[current]][maxJ[current]];

		for (int i = 0; i < guardPosition[current].length; i++) {
			for (int j = 0; j < guardPosition[current][i].length; j++) {
				Arrays.fill(guardPosition[current][i][j], -1);
			}
		}

		numGuards[current] = Integer.parseInt(reader.readLine());
		for (int i = 0; i < numGuards[current]; i++) {
			inputData = new StringTokenizer(reader.readLine());
			
			int currentI = Integer.parseInt(inputData.nextToken()) - 1;

			int currentJ = Integer.parseInt(inputData.nextToken()) - 1;

			int patrolLength = Integer.parseInt(inputData.nextToken());
			int direction = getIntDirection(inputData.nextToken().charAt(0));
			int counter = patrolLength - 1;
			boolean forward = true;
			for (int time = 0; time < 12; time++) {
				if (counter == 0) {
					counter = patrolLength - 1;
					forward = !forward;
				}

				guardPosition[current][time][currentI][currentJ] = forward ? direction : getOpposite(direction);

				if (forward) {
					currentI += iMod[direction];
					currentJ += jMod[direction];
				} else {
					currentI -= iMod[direction];
					currentJ -= jMod[direction];
				}
				counter--;
			}
		}
	}
	
	LinkedList<State> searchQueue = new LinkedList<State>();
	
	public int initializeBFS(){
		State startState = new State(0, startI, startJ);
		searchQueue.add(startState);
		stateCost[0][startI[0]][startJ[0]][startI[1]][startJ[1]] = 0;
		while(!searchQueue.isEmpty() && ans == Integer.MAX_VALUE){
			State searchState = searchQueue.remove();
			if(stateCost[searchState.time][searchState.i[0]][searchState.j[0]][searchState.i[1]][searchState.j[1]] == -1){
				continue;
			}
			else{
				processState(searchState);
			}
		}
		return ans;
	}
	
	class State{
		int time;
		int[] i;
		int[] j;
		
		State(int time, int[] i, int[] j){
			this.time = time;
			this.i = i;
			this.j = j;
		}
	}

	public void input() throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		input(0);
		input(1);
	}

	public int getOpposite(int direction) {
		if (direction == 0) {
			return 3;
		}
		if (direction == 1) {
			return 2;
		}
		if (direction == 2) {
			return 1;
		}
		return 0;
	}

	public int getIntDirection(char charDirection) {
		if (charDirection == 'N') {
			return 0;
		}
		if (charDirection == 'W') {
			return 1;
		}
		if (charDirection == 'E') {
			return 2;
		}
		return 3;
	}

}
