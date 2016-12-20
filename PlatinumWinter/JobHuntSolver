import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class JobHuntSolver {

	public static void main(String[] args) throws IOException {
		new JobHuntSolver().execute();
	}

	int gain;
	int numP;
	int numN;
	int numV;
	int start;

	int[] eStart;
	int[] eEnd;
	int[] value;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		gain = Integer.parseInt(inputData.nextToken());
		numP = Integer.parseInt(inputData.nextToken());
		numV = Integer.parseInt(inputData.nextToken());
		numN = Integer.parseInt(inputData.nextToken());
		start = Integer.parseInt(inputData.nextToken()) - 1;

		eStart = new int[numP + numN];
		eEnd = new int[numP + numN];
		value = new int[numP + numN];

		for (int i = 0; i < numP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			eStart[i] = Integer.parseInt(inputData.nextToken()) - 1;
			eEnd[i] = Integer.parseInt(inputData.nextToken()) - 1;
			value[i] = gain;
		}

		for (int i = 0; i < numN; i++) {
			inputData = new StringTokenizer(reader.readLine());
			eStart[i + numP] = Integer.parseInt(inputData.nextToken()) - 1;
			eEnd[i + numP] = Integer.parseInt(inputData.nextToken()) - 1;
			value[i + numP] = gain - Integer.parseInt(inputData.nextToken());
		}

		int[] maxValue = new int[numV];
		Arrays.fill(maxValue, Integer.MIN_VALUE / 2);
		maxValue[start] = gain;

		for (int i = 0; i < numV; i++) {
			for (int j = 0; j < numP + numN; j++) {
				int newVal = maxValue[eStart[j]] + value[j];
				if (newVal > maxValue[eEnd[j]]) {
					maxValue[eEnd[j]] = newVal;
				}
			}
		}

		for (int i = 0; i < numP + numN; i++) {
			int newVal = maxValue[eStart[i]] + value[i];
			if (newVal > maxValue[eEnd[i]]) {
				System.out.println(-1);
				return;
			}
		}

		int ans = 0;
		for (int i = 0; i < numV; i++) {
			if (maxValue[i] > ans) {
				ans = maxValue[i];
			}
		}

		System.out.println(ans);

	}

}
