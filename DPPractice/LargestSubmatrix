import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		int numProblems = Integer.parseInt(reader.readLine());
		
		for(int i = 0; i < numProblems; i++){
			reader.readLine();
			char[] inputData = reader.readLine().toCharArray();
			int size = inputData.length;
			
			char[][] elements = new char[size][];
			
			elements[0] = inputData;
			for(int j = 1; j < size; j++){
				elements[j] = reader.readLine().toCharArray();
			}
			
			int max = 0;
			
			int[] height = new int[size];
			for(int cRow = 0; cRow < size; cRow++){
				for(int j = 0; j < size; j++){
					if(elements[cRow][j] == '1'){
						height[j] = height[j] + 1;
					}
					else{
						height[j] = 0;
					}
				}
				
				max = Integer.max(max, getMax(height));
			}
			out.add(max);
		}
		
		System.out.println(out.get(0));
		
		for(int i = 1; i < out.size(); i++){
			System.out.println();
			System.out.println(out.get(i));
		}
	}
	
	static int getMax(int[] elements){
		LinkedList<Integer> stack = new LinkedList<Integer>();
		int max = 0;
		int rightIndex = 0;
		while(rightIndex < elements.length){
			if(stack.isEmpty() || elements[stack.peek()] < elements[rightIndex]){
				stack.push(rightIndex++);
			}
			else{
				int height = elements[stack.pop()];
				int width = 0;
				//indices stack.peek() and rightIndex are exclusive
				if(!stack.isEmpty()){
					width = rightIndex - stack.peek() - 1;
				}
				else{
					width = rightIndex;
				}
				int area = width*height;
				max = Integer.max(max, area);
			}
		}
		
		while(!stack.isEmpty()){
			int height = elements[stack.pop()];
			int width = 0;
			//indices stack.peek() and rightIndex are exclusive
			if(!stack.isEmpty()){
				width = rightIndex - stack.peek() - 1;
			}
			else{
				width = rightIndex;
			}
			int area = width*height;
			max = Integer.max(max, area);
		}
		return max;
	}

}
