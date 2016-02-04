import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NearbyCows {
	
	static int N;
	static int K;
	
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static int[][] dpS;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());
		for(int i = 0; i < N; i++){
			aList.add(new ArrayList<Integer>());
		}
		
		for(int i = 2; i <= N; i++){
			inputData = new StringTokenizer(reader.readLine());
			int a_i = Integer.parseInt(inputData.nextToken()) - 1;
			int b_i = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a_i).add(b_i);
			aList.get(b_i).add(a_i);
		}
		
		dpS = new int[N][K+1];
		
		for(int i = 0; i < N; i++){
			dpS[i][0] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		for(int numSteps = 1; numSteps <= K; numSteps++){
			for(int currentNode = 0; currentNode < N; currentNode++){
				int preSum = 0;
				for(int neighbor : aList.get(currentNode)){
					preSum += dpS[neighbor][numSteps-1];
				}
				int subtractSum = 0;
				if(numSteps >= 2){
					subtractSum = (aList.get(currentNode).size()-1)*dpS[currentNode][numSteps-2];
				}
				else{
					preSum += dpS[currentNode][0];
				}
				dpS[currentNode][numSteps] = preSum - subtractSum;
			}
		}
		
		for(int i = 0; i < N; i++){
			System.out.println(dpS[i][K]);
		}
	}

}
