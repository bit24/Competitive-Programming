import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class DivCmb_474F {

	static int nV;
	static int nE;

	static TreeMap<Integer, Integer>[] endM;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());

		endM = new TreeMap[nV];
		for (int i = 0; i < nV; i++) {
			endM[i] = new TreeMap<>();
			endM[i].put(-1, 0);
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int w = Integer.parseInt(inputData.nextToken());
			add(endM[b], w, endM[a].lowerEntry(w).getValue() + 1);
		}
		
		int ans = 0;
		for(int i = 0; i < nV; i++) {
			ans = Math.max(ans, endM[i].lastEntry().getValue());
		}
		printer.println(ans);
		printer.close();
	}

	static void add(TreeMap<Integer, Integer> cMap, int nKey, int nCnt) {
		if (cMap.floorEntry(nKey).getValue() < nCnt) {
			cMap.put(nKey, nCnt);

			Entry<Integer, Integer> grtr = cMap.higherEntry(nKey);
			while (grtr != null && grtr.getValue() <= nCnt) {
				cMap.remove(grtr.getKey());
				grtr = cMap.higherEntry(nKey);
			}
		}
	}

}
