import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Taxi {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("taxi.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("taxi.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numR = Integer.parseInt(inputData.nextToken());
		int fEPt = Integer.parseInt(inputData.nextToken());

		int[] start = new int[numR];
		int[] end = new int[numR];

		TreeSet<Integer> sigPts = new TreeSet<Integer>();
		sigPts.add(0);
		sigPts.add(fEPt);

		for (int i = 0; i < numR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			start[i] = Integer.parseInt(inputData.nextToken());
			end[i] = Integer.parseInt(inputData.nextToken());
			sigPts.add(start[i]);
			sigPts.add(end[i]);
		}
		reader.close();
		ArrayList<Integer> sigList = new ArrayList<Integer>(sigPts);

		int nSigPts = sigList.size();
		int[] dist = new int[nSigPts];
		TreeMap<Integer, Integer> mappings = new TreeMap<Integer, Integer>();

		for (int i = 0; i + 1 < nSigPts; i++) {
			dist[i] = sigList.get(i + 1) - sigList.get(i);
			mappings.put(sigList.get(i), i);
		}
		mappings.put(sigList.get(nSigPts - 1), nSigPts - 1);

		for (int i = 0; i < numR; i++) {
			start[i] = mappings.get(start[i]);
			end[i] = mappings.get(end[i]);
		}

		int[] rDelta = new int[nSigPts + 1];
		int[] lDelta = new int[nSigPts + 1];

		for (int i = 0; i < numR; i++) {
			if (start[i] < end[i]) {
				rDelta[start[i]]++;
				rDelta[end[i]]--;
			} else {
				lDelta[end[i]]++;
				lDelta[start[i]]--;
			}
		}

		int rSum = 0;
		int lSum = 0;

		long tCost = 0;
		for (int i = 0; i + 1 < nSigPts; i++) {
			rSum += rDelta[i];
			lSum += lDelta[i];

			tCost += (long) Math.max(rSum, lSum + 1) * dist[i];
			tCost += (long) Math.max(lSum, rSum - 1) * dist[i];
		}
		printer.println(tCost);
		printer.close();
	}

}
