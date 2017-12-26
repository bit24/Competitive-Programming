import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
	
	static ArrayList<String> outputData = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		int value = reader.nextInt();
		
		while(value != 0){
			int length = value;
			
			int best = Integer.MIN_VALUE;
			int currentBest = reader.nextInt();
			
			for(int i = 1; i < length; i++){
				if(currentBest < 0){
					currentBest = reader.nextInt();
				}
				else{
					currentBest += reader.nextInt();
				}
				best = Math.max(currentBest, best);
			}
			out.add(best);
			value = reader.nextInt();
		}
		
		reader.close();
		for(int i : out){
			if(i > 0){
				System.out.println("The maximum winning streak is " + i + ".");
			}
			else{
				System.out.println("Losing streak.");
			}
		}
	}
	

}
