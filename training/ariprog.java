/*
ID: eric.ca1
LANG: JAVA
TASK: ariprog
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
public class ariprog {

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("ariprog.in"));
	    PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
	    
	    int progressionLength = Integer.parseInt(reader.readLine());
	    int max = Integer.parseInt(reader.readLine());
	    reader.close();
	    
	    HashSet<Integer> dualSquareSet = generateDualSquares(max);
	    
	   // long startTime = System.currentTimeMillis();
	    ArrayList<Integer> dualSquares = new ArrayList<Integer>(dualSquareSet);
	    Collections.sort(dualSquares);
	   // System.out.println(System.currentTimeMillis()-startTime);
	    
		int maxValue = max*max*2;
		
		ariprog thingForBuildingStuff = new ariprog();
		
		ArrayList<Sequence> combos = new ArrayList<Sequence>();
		
		int currentInteger;
		
		int maxLength = progressionLength-1;
		
		//long startTime2 = System.currentTimeMillis();
		//System.out.println(startTime2-startTime);
		
		for(int i = 0; i < dualSquares.size()-1; i++){
			int a = dualSquares.get(i);
			int maxBValue = (maxValue-a)/(maxLength);
			
			bLoop: for(int k = i+1, b = dualSquares.get(k) - a; b <= maxBValue; b = dualSquares.get(++k) -a){
				currentInteger = a;
				for(int j = 0; j < progressionLength; j++){
					if(!dualSquareSet.contains(currentInteger)){
						continue bLoop;
					}
					currentInteger = currentInteger + b; 
				}
				combos.add(thingForBuildingStuff.new Sequence(a, b));
			}
			
			
		}
		
		if(combos.size() == 0){
			printer.println("NONE");
		}
		else{
			Collections.sort(combos);
			for(Sequence currentSequence : combos){
				printer.println(currentSequence.start + " " + currentSequence.difference);
			}
		}
		
		printer.close();
		
		
	}
	
	public static HashSet<Integer> generateDualSquares(int max){
		HashSet<Integer> veryBigSet = new HashSet<Integer>();
		for(int i = 0; i <= max; i++){
			for(int j = i; j <= max; j++){
				veryBigSet.add(i*i + j*j);
			}
		}
		return veryBigSet;
	}
	
	class Sequence implements Comparable<Sequence>{
		int start;
		int difference;
		public Sequence(int start, int difference){
			this.start = start;
			this.difference = difference;
		}
		public int compareTo(Sequence otherSequence){
			if(difference != otherSequence.difference){
				if(difference > otherSequence.difference){
					return 1;
				}
				else{
					return -1;
				}
			}else{
				if(start > otherSequence.start){
					return 1;
				}
				else{
					return -1;
				}
			}
		}
	}

}
