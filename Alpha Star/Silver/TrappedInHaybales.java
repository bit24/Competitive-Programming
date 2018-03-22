import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TrappedInHaybales {
	
	static int numBales;
	static int initialPosition;
	
	static TrappedInHaybales current = new TrappedInHaybales();
	
	static Tuple[] tuples;
	
	static int[] position;
	static int[] size;
	
	static int minDifference = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("trapped.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("trapped.out")));
		
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numBales = Integer.parseInt(inputData.nextToken());
		initialPosition = Integer.parseInt(inputData.nextToken());
		
		tuples = new Tuple[numBales];
		for(int i = 0; i < numBales; i++){
			inputData = new StringTokenizer(reader.readLine());
			
			int size = Integer.parseInt(inputData.nextToken());
			int position = Integer.parseInt(inputData.nextToken());
			
			Tuple newTuple = current . new Tuple(position, size);
			tuples[i] = newTuple;
		}
		reader.close();
		Arrays.sort(tuples);
		
		position = new int[numBales];
		size = new int[numBales];
		for(int i = 0; i < numBales; i++){
			position[i] = tuples[i].a;
			size[i] = tuples[i].b;
		}
		
		int left = Arrays.binarySearch(position, initialPosition) * - 1 - 2;
		int right = left+1;
		
		if(right == numBales || left == 0){
			printer.println(-1);
			printer.close();
			return;
		}
		
		big:
		for(; right < numBales; right++){
			while(size[left] < position[right] - position[left]){
				left--;
				if(left < 0){
					break big;
				}
			}
			minDifference = Math.min(minDifference, (position[right] - position[left])-size[right]);
		}
		
		left = Arrays.binarySearch(position, initialPosition) * - 1 - 2;
		right = left+1;
		
		big:
		for(; left >=0; left--){
			while(size[right] < position[right] - position[left]){
				right++;
				if(right >= numBales){
					break big;
				}
			}
			minDifference = Math.min(minDifference, (position[right] - position[left])-size[left]);
		}
		if(minDifference == Integer.MAX_VALUE){
			printer.println(-1);
		}
		else if(minDifference <= 0){
			printer.println(0);
		}
		else{
			printer.println(minDifference);
		}
		printer.close();
	}
	
	class Tuple implements Comparable<Tuple> {
		int a;
		int b;

		Tuple() {
		}

		Tuple(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public int compareTo(Tuple o) {
			int value = Integer.compare(a, o.a);
			if (value == 0) {
				return Integer.compare(b, o.b);
			} else {
				return value;
			}
		}

		public boolean equals(Object other) {
			if (Tuple.class != other.getClass()) {
				return false;
			}
			Tuple oTuple = (Tuple) other;

			if (a == oTuple.a && b == oTuple.b) {
				return true;
			}
			return false;
		}

	}
}
