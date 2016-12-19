/*
ID: eric.ca1
LANG: JAVA
TASK: skidesign
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class skidesign {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("skidesign.in"));
	    PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
	    
	    ArrayList<Integer> heights = new ArrayList<Integer>();
	    
	    reader.readLine();
	    String inputLine = reader.readLine();
	    while(inputLine != null){
	    	heights.add(Integer.parseInt(inputLine));
	    	inputLine = reader.readLine();
	    }
	    reader.close();
	    
	    Collections.sort(heights);
	    
	    int currentCost = 0;
	    int bestCost = Integer.MAX_VALUE;
	    
	    int endAmount = heights.get(heights.size() -1) - 16;
	    
	    for(int i = heights.get(0); i < endAmount; i++){
	    	currentCost = 0;
	    	for(int j : heights){
	    		int deviation = 0;
	    		if(j < i){
	    			deviation = i-j;
	    			currentCost = currentCost + (deviation)*(deviation);
	    		}
	    		else if(j > i + 17){
	    			deviation = j - i - 17;
	    			currentCost = currentCost + (deviation)*(deviation);
	    		}
	    	}
	    	if(currentCost < bestCost){
	    		bestCost = currentCost;
	    	}
	    }
	    printer.println(bestCost);
	    printer.close();
	    
	}

}
