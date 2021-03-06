import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CowLineupSolver {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numElements = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken()) + 1;

		int[] elements = new int[numElements];

		for (int i = 0; i < numElements; i++) {
			elements[i] = Integer.parseInt(reader.readLine());
		}

		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();

		int tCount = 0;

		int lI = 0;

		int ans = 0;

		for (int cI = 0; cI < numElements; cI++) {
			if (!count.containsKey(elements[cI])) {
				count.put(elements[cI], 1);
				tCount++;
			} else {
				count.put(elements[cI], count.get(elements[cI]) + 1);
			}
			while (tCount > k) {
				int currentCount = count.get(elements[lI]);
				if (currentCount > ans) {
					ans = currentCount;
				}
				
				count.put(elements[lI], count.get(elements[lI]) - 1);
				if (count.get(elements[lI]) == 0) {
					count.remove(elements[lI]);
					tCount--;
				}
				lI++;
			}
			assert (tCount <= k);
			int currentCount = count.get(elements[lI]);
			if (currentCount > ans) {
				ans = currentCount;
			}
		}
		
		while(lI < numElements){
			int currentCount = count.get(elements[lI]);
			if (currentCount > ans) {
				ans = currentCount;
			}
			
			count.put(elements[lI], count.get(elements[lI]) - 1);
			if (count.get(elements[lI]) == 0) {
				count.remove(elements[lI]);
				tCount--;
			}
			lI++;
		}

		System.out.println(ans);
	}
}
