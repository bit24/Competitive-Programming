import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div2_405B {

	public static void main(String[] args) throws IOException {
		new Div2_405B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numE = Integer.parseInt(inputData.nextToken());

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] aList = new ArrayList[numV + 1];

		for (int i = 1; i <= numV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
		}

		for (int i = 1; i <= numV; i++) {
			Collections.sort(aList[i]);
		}

		int[] gSize = new int[numV + 1];
		int nGroup = 0;

		int[] group = new int[numV + 1];

		for (int i = 1; i <= numV; i++) {
			int count = 0;
			int groupNum = -1;

			for (int adj : aList[i]) {
				if (adj > i) {
					break;
				}
				count++;
				if (groupNum == -1) {
					groupNum = group[adj];
				} else if (groupNum != group[adj]) {
					printer.println("NO");
					printer.close();
					return;
				}
			}

			if (groupNum == -1) {
				group[i] = nGroup;
				gSize[nGroup++] = 1;
				continue;
			}

			if (gSize[groupNum] != count) {
				printer.println("NO");
				printer.close();
				return;
			}

			group[i] = groupNum;
			gSize[groupNum]++;
		}
		printer.println("YES");
		printer.close();
	}

}
