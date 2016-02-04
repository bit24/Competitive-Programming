import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class CountyFair {
	
	static int numVertices;
	
	static int[][] wTime;
	
	static HashMap<Integer, Integer> sTNS = new HashMap<Integer, Integer>();
	
	static CountyFair b = new CountyFair();
	
	static Pair[] dataPairs;
	static int[] oMAP;

	static int startVertex;

	static int[] prizeTime;
	static int[] dp;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		numVertices = Integer.parseInt(reader.readLine());
		
		dataPairs = new Pair[numVertices];
		dp = new int[numVertices];
		prizeTime = new int[numVertices];
		
		for(int i = 0; i < numVertices; i++){
			Pair p = b.new Pair();
			
			p.time = Integer.parseInt(reader.readLine());
			p.number = i;
			dataPairs[i] = p;
			dp[i] = Integer.MIN_VALUE;
		}
		
		Arrays.sort(dataPairs);
		
		oMAP = new int[numVertices];
		
		for(int i = 0 ; i < numVertices; i++){
			 oMAP[dataPairs[i].number] = i;
		}
		
		for(int i = 0; i < numVertices; i++){
			prizeTime[i] = dataPairs[i].time;
		}
		
		
		wTime = new int[numVertices][numVertices];
		
		for(int i = 0; i < numVertices; i++){
			for(int j = 0; j < numVertices; j++){
				wTime[oMAP[i]][oMAP[j]] = Integer.parseInt(reader.readLine());
			}
		}
		
		int startVertex = oMAP[0];
		
		dp[startVertex] = 1;
		
		for(int i = 0; i < numVertices; i++){
			if(wTime[startVertex][i] <= prizeTime[i]){
				dp[i] = 1;
			}
		}
		
		for(int cV = 0; cV < numVertices; cV++){
			for(int pV = 0; pV < cV; pV++){
				if(dataPairs[pV].time + wTime[pV][cV] <= prizeTime[cV]){
					dp[cV] = Math.max(dp[cV], dp[pV] + 1);
				}
			}
		}
		
		int max = 0;
		
		for(int i : dp){
			max = Math.max(max, i);
		}
		System.out.println(max);
		
		
	}
	
	class Pair implements Comparable<Pair>{
		int time;
		int number;
		
		public int compareTo(Pair o) {
			return Integer.compare(time, o.time);
		}
		
	}

}
