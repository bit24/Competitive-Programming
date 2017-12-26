import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.LinkedList;
import java.util.Scanner;

class Main {

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		while(reader.hasNext()){
			int M = reader.nextInt();
			int N = reader.nextInt();
			
			if(M == 0 || N == 0){
				continue;
			}
			
			int[][] elements = new int[M][N];
			
			for(int m = 0; m < M; m++){
				for(int n = 0; n < N; n++){
					elements[m][n] = reader.nextInt();
				}
			}
			
			int best = 0;
			int[] height = elements[0];
			
			for(int m = 0; m < M; m++){
				for(int n = 0; n < N; n++){
					if(elements[m][n] == 0){
						height[n] += 1;
					}
					else{
						height[n] = 0;
					}
				}
				best = Math.max(best, findMaxArea(height));
			}
			printer.println(best);
		}
		reader.close();
		printer.close();

	}
	
	static int findMaxArea(int[] height){
		LinkedList<Integer> stack = new LinkedList<Integer>();
		int rightIndex = 0;
		int best = 0;
		
		while(rightIndex < height.length){	
			if(stack.isEmpty() || height[rightIndex] > height[stack.peek()]){
				stack.push(rightIndex++);
			}
			else{
				int h = height[stack.pop()];
				int w = stack.isEmpty() ? rightIndex : rightIndex - stack.peek() - 1;
				best = Math.max(best, w*h);
			}
		}
		
		while(!stack.isEmpty()){
			int h = height[stack.pop()];
			int w = stack.isEmpty() ? rightIndex : rightIndex - stack.peek() - 1;
			best = Math.max(best, w*h);
		}
		return best;
	}

}
