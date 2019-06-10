import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class RouteDesign {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("route.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("route.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nA = Integer.parseInt(inputData.nextToken());
		int nB = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		long[] aVal = new long[nA];
		long[] bVal = new long[nB];

		ArrayList<Integer>[] aAList = new ArrayList[nA];
		ArrayList<Integer>[] bAList = new ArrayList[nB];

		long[] aCVal = new long[nA];
		long[] bCVal = new long[nB];

		for (int i = 0; i < nA; i++) {
			aCVal[i] = aVal[i] = Integer.parseInt(reader.readLine());
			aAList[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < nB; i++) {
			bCVal[i] = bVal[i] = Integer.parseInt(reader.readLine());
			bAList[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aAList[a].add(b);
			bAList[b].add(a);
		}
		reader.close();
		for (ArrayList<Integer> cList : aAList) {
			Collections.sort(cList);
		}
		for (ArrayList<Integer> cList : bAList) {
			Collections.sort(cList);
		}

		int[] aCnt = new int[nA];
		int[] bCnt = new int[nB];

		for (int i = 0; i < Math.max(nA, nB); i++) {
			if (i < nA) {
				for (int j = aCnt[i]; j < aAList[i].size(); j++) {
					int adj = aAList[i].get(j);
					long temp = bCVal[adj] + aVal[i];
					bCVal[adj] = Math.max(bCVal[adj], aCVal[i] + bVal[adj]);
					aCVal[i] = Math.max(aCVal[i], temp);
					bCnt[adj]++;
				}
			}
			if (i < nB) {
				for (int j = bCnt[i]; j < bAList[i].size(); j++) {
					int adj = bAList[i].get(j);
					long temp = aCVal[adj] + bVal[i];
					aCVal[adj] = Math.max(aCVal[adj], bCVal[i] + aVal[adj]);
					bCVal[i] = Math.max(bCVal[i], temp);
					aCnt[adj]++;
				}
			}
		}
		
		long ans = 0;
		for(long val : aCVal) {
			ans = Math.max(ans, val);
		}
		for(long val : bCVal) {
			ans = Math.max(ans, val);
		}
		printer.println(ans);
		printer.close();
	}

}
