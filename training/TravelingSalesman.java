import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TravelingSalesman {

	static int numVertices;
	static int numEdges;

	static int[][] cost;

	static int[][] cache;
	static int[][] previous;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());

		cost = new int[numVertices][numVertices];
		for(int i = 0; i < numVertices; i++){
			for(int j = 0; j < numVertices; j++){
				cost[i][j] = Integer.MAX_VALUE;
			}
		}
		
		// create two-way adjacency list and look-up matrix
		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			cost[a][b] = c;
			cost[b][a] = c;
		}
		//long startTime = System.currentTimeMillis();

		// initializing cache 1 << numVertices+1 is max size
		cache = new int[numVertices][1 << numVertices];

		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < (1 << numVertices); j++) {
				cache[i][j] = UNDECIDED;
			}
		}

		int totalMin = Integer.MAX_VALUE;

		int wholeSet = 0;
		for (int i = 0; i < numVertices; i++) {
			wholeSet <<= 1;
			wholeSet |= 1;
		}

		for (int i = 0; i < numVertices; i++) {
			totalMin = Math.min(totalMin, tsp(i, wholeSet));
		}
		if (totalMin == Integer.MAX_VALUE) {
			System.out.println("IMPOSSIBLE");
		} else {
			System.out.println(totalMin);
		}
		//System.out.println(System.currentTimeMillis()-startTime);
	}

	static final int UNDECIDED = -1;

	public static int tsp(int endVertex, int bitSet) {
		if (cache[endVertex][bitSet] != UNDECIDED) {
			return cache[endVertex][bitSet];
		}

		int newBitSet = bitSet & (~(1 << endVertex));

		int min = Integer.MAX_VALUE;
		
		int processingBitSet = newBitSet;

		if (newBitSet == 0) {
			min = 0;
		} else {
			
			for(int previous = 0; previous < numVertices; previous++){
				if((processingBitSet & 1) != 0 && cost[previous][endVertex] != Integer.MAX_VALUE){
					int subsetCost = cache[endVertex][bitSet] != UNDECIDED ? cache[endVertex][bitSet]
							: tsp(previous, newBitSet);

					if (subsetCost != Integer.MAX_VALUE) {
						min = Math.min(min, subsetCost + cost[previous][endVertex]);
					}
				}
				processingBitSet >>= 1;
				if(processingBitSet == 0){
					continue;
				}
			}
			
			/*
			for (int previous : aList.get(endVertex)) {

				// contains element
				if ((newBitSet & (1 << previous)) != 0) {

					int subsetCost = cache[endVertex][bitSet] != UNDECIDED ? cache[endVertex][bitSet]
							: tsp(previous, newBitSet);

					if (subsetCost != Integer.MAX_VALUE) {
						min = Math.min(min, subsetCost + cost[previous][endVertex]);
					}
				}
			}*/
		}
		cache[endVertex][bitSet] = min;
		return min;
	}

}
