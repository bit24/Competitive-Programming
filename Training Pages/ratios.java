import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/*
ID: eric.ca1
LANG: JAVA
TASK: ratios
*/
public class ratios {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("ratios.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("ratios.out")));
		String[] dataArray = reader.readLine().split(" ");
		ArrayList<String> processedData = new ArrayList<String>();
		for(int i = 0; i < dataArray.length; i++){
			if(!dataArray[i].equals("")){
				processedData.add(dataArray[i]);
			}
		}
		int[] goalMixture = new int[3];
		goalMixture[0] = Integer.parseInt(processedData.get(0));
		goalMixture[1] = Integer.parseInt(processedData.get(1));
		goalMixture[2] = Integer.parseInt(processedData.get(2));
		
		int[][] feeds = new int[3][3];
		for(int currentMixture= 0; currentMixture < 3; currentMixture++){
			dataArray = reader.readLine().split(" ");
			processedData.clear();
			
			for(int i = 0; i < dataArray.length; i++){
				if(!dataArray[i].equals("")){
					processedData.add(dataArray[i]);
				}
			}
			
			feeds[currentMixture][0] = Integer.parseInt(processedData.get(0));
			feeds[currentMixture][1] = Integer.parseInt(processedData.get(1));
			feeds[currentMixture][2] = Integer.parseInt(processedData.get(2));
		}
		reader.close();
		
		int[] ratios = generateMixture(feeds, goalMixture);
		if(ratios[0] == -1){
			printer.println("NONE");
		}
		else{
			for(int i = 0; i < 3; i++){
				printer.print(ratios[i] + " ");
			}
			
			printer.println(mixture(feeds, ratios)[0] / goalMixture[0]);
		}
		printer.close();
	}
	
	public static int[] generateMixture(int[][] feeds, int[] goalMixture){
		int[] currentPossibilty = new int[]{0, 0, 1};
		
		int currentBest = Integer.MAX_VALUE;
		int[] bestRatio = new int[3];
		Arrays.fill(bestRatio, -1);
		
		bigLoop:
		while(true){
			if(isMultipleOf(mixture(feeds, currentPossibilty), goalMixture)){
				if(sum(currentPossibilty) < currentBest){
					currentBest = sum(currentPossibilty);
					bestRatio = Arrays.copyOf(currentPossibilty, currentPossibilty.length);
				}
			}
			
			currentPossibilty[0]++;
			for(int currentIndex = 0; true; currentIndex++){
				if(currentPossibilty[currentIndex] >= 100){
					if(currentIndex == 2){
						break bigLoop;
					}
					currentPossibilty[currentIndex] -= 100;
					currentPossibilty[currentIndex+1] += 1;
				}
				else{
					break;
				}
			}
		}
		
		return bestRatio;
		
	}
	
	public static boolean isMultipleOf(int[] superArray, int[] subArray){
		int multiple = 0;
		if(superArray[0] % subArray[0] == 0){
			multiple = superArray[0] / subArray[0];
		}
		else{
			return false;
		}
		
		for(int i = 1; i < 3; i++){
			if(subArray[i] * multiple != superArray[i]){
				return false;
			}
		}
		return true;
		
	}
	
	public static int[] mixture(int[][] feeds, int[] values){
		int[] mixture = new int[3];
		
		for(int currentFeed = 0; currentFeed < 3; currentFeed++){
			for(int currentContent = 0; currentContent < 3; currentContent++){
				mixture[currentContent] += feeds[currentFeed][currentContent] * values[currentFeed];
			}
		}
		
		return mixture;
	}
	
	public static int sum(int[] elements){
		int sum = 0;
		for(int i = 0; i < elements.length; i++){
			sum += elements[i];
		}
		return sum;
	}

}
