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
		int numProblems = reader.nextInt();
		
		
		while(numProblems-- > 0){
			int size = reader.nextInt();
			boolean[][] elements = new boolean[size][size];
			int numBlocks = reader.nextInt();
			for(int i = 0; i < numBlocks; i++){
				int a = reader.nextInt() - 1;
				int b = reader.nextInt() - 1;
				int c = reader.nextInt() - 1;
				int d = reader.nextInt() - 1;
				for(int x = a; x <= c; x++){
					for(int y = b; y <= d; y++){
						elements[x][y] = true;
					}
				}
			}
			
			int best = 0;
			int[] height = new int[size];
			for(int cRow = 0; cRow < size; cRow++){
				for(int cColumn = 0; cColumn < size; cColumn++){
					if(elements[cRow][cColumn]){
						height[cColumn] = 0;
					}
					else{
						height[cColumn] += 1;
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
