import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
ID: eric.ca1
LANG: JAVA
TASK: spin
*/
public class spin {
	
	public static spin helper = new spin();
	
	public static void main(String[] args) throws IOException{
		long startTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("spin.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("spin.out")));
		
		boolean[][] wedgeDegrees = new boolean[5][360];
		int[] wheelSpeeds = new int[5];
		
		for(int currentWheel = 0; currentWheel < 5; currentWheel++){
			String[] inputData = reader.readLine().split(" ");
			ArrayList<String> whiteSpaceRemoved = new ArrayList<String>();
			for(int i = 0; i < inputData.length; i++){
				if(!inputData[i].equals("")){
					whiteSpaceRemoved.add(inputData[i]);
				}
			}
			
			wheelSpeeds[currentWheel] = Integer.parseInt(whiteSpaceRemoved.get(0));
			
			for(int i = 2; i < whiteSpaceRemoved.size(); i+=2){
				int startIndex = Integer.parseInt(whiteSpaceRemoved.get(i));
				int endIndex = Integer.parseInt(whiteSpaceRemoved.get(i+1)) + startIndex;
				if(endIndex > 359){
					endIndex -= 360;
				}
				for(int currentDegree = startIndex; currentDegree != endIndex; currentDegree++){
					if(currentDegree == 360){
						currentDegree = 0;
						if(endIndex == 0){
							break;
						}
					}
					wedgeDegrees[currentWheel][currentDegree] = true;
				}
				wedgeDegrees[currentWheel][endIndex] = true;
			}
		}
		reader.close();
		
		int[] wheelDegrees = new int[]{0, 0, 0, 0, 0};
		int secondsPassed = 0;
		
		HashSet<SpecSet> visited = new HashSet<SpecSet>();
		
		while(true){
			SpecSet currentSet = helper. new SpecSet(Arrays.copyOf(wheelDegrees, 5));
			if(visited.contains(currentSet)){
				printer.println("none");
				printer.close();
				System.out.println(System.currentTimeMillis()-startTime);
				System.exit(0);
			}
			visited.add(currentSet);
			boolean isAligned = false;
			
			outSideLoop:
			for(int degreeToCheck = 0; degreeToCheck < 360; degreeToCheck++){
				for(int currentWheel = 0; currentWheel < 5; currentWheel++){
					int currentWheelDegreeToCheck = degreeToCheck;
					currentWheelDegreeToCheck -= wheelDegrees[currentWheel];
					while(currentWheelDegreeToCheck < 0){
						currentWheelDegreeToCheck += 360;
					}
					if(!wedgeDegrees[currentWheel][currentWheelDegreeToCheck]){
						continue outSideLoop;
					}
				}
				isAligned = true;
			}
			
			
			
			if(isAligned){
				printer.println(secondsPassed);
				printer.close();
				System.out.println(System.currentTimeMillis()-startTime);
				System.exit(0);
			}
			
			for(int currentWheel = 0; currentWheel < 5; currentWheel++){
				wheelDegrees[currentWheel] += wheelSpeeds[currentWheel];
				if(wheelDegrees[currentWheel] > 359){
					wheelDegrees[currentWheel] -= 360;
				}
			}
			secondsPassed++;
		}
	}
	
	class SpecSet{
		public int[] items = new int[5];
		
		public int hashCode(){
			return Arrays.hashCode(items);
		}
		
		public boolean equals(Object obj){
			return Arrays.equals(items, ((SpecSet) obj).items);
		}
		
		public SpecSet(int[] elements){
			items = elements;
		}
		
	}
	
}
