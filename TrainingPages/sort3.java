/*
ID: eric.ca1
LANG: JAVA
TASK: sort3
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class sort3 {
	
	public static sort3 helper = new sort3();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("sort3.in"));
	    PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
	    int numNumbers = Integer.parseInt(reader.readLine());
	    int[] numberSet = new int[numNumbers];
	    Partition[] partitions = new Partition[4];
	    partitions[1] = helper.new Partition(1);
	    partitions[2] = helper.new Partition(2);
	    partitions[3] = helper.new Partition(3);

	    
	    for(int i = 0; i < numNumbers; i++){
	    	int currentNumber = Integer.parseInt(reader.readLine());
	    	numberSet[i] = currentNumber;
	    	if(currentNumber == 1){
	    		partitions[1].endIndex++;
	    		partitions[2].startIndex++;
	    		partitions[2].endIndex++;
	    		partitions[3].startIndex++;
	    		partitions[3].endIndex++;
	    	}
	    	else if(currentNumber == 2){
	    		partitions[2].endIndex++;
	    		partitions[3].startIndex++;
	    		partitions[3].endIndex++;
	    	}
	    	else if(currentNumber == 3){
	    		partitions[3].endIndex++;
	    	}
	    	
	    }
	    
	    reader.close();
	    
	    int numSwaps = 0;
	    
	    for(int curNumIndex = 0; curNumIndex < numNumbers; curNumIndex++){
	    	int currentNumber = numberSet[curNumIndex];
	    	int currentPartition = findCurrentPartition(curNumIndex, partitions);
	    	if(currentPartition != currentNumber){
	    		numSwaps += swapNumber(numberSet, partitions, curNumIndex, currentNumber, currentPartition);
	    	}
	    }
	    printer.println(numSwaps);
	    printer.close();
	}
	
	public static int swapNumber(int[] numberSet, Partition[] partitions, int currentNumberIndex, int currentNumber, int currentPartition){
	    int indexToSwap = findIndexToSwap(currentNumber, currentPartition, partitions, numberSet);
	   	int tempStorage = numberSet[indexToSwap];
	   	numberSet[indexToSwap] = currentNumber;
	   	numberSet[currentNumberIndex] = tempStorage;
    	
	   	currentNumber = numberSet[currentNumberIndex];
	   	currentPartition = findCurrentPartition(currentNumberIndex, partitions);
	   	if(currentPartition != currentNumber){
	   		return swapNumber(numberSet, partitions, currentNumberIndex, currentNumber, currentPartition) + 1;
	   	}
	   	return 1;
	}
	
	public static int findIndexToSwap(int number, int prefferedSwapNumber, Partition[] partitions, int[] numSet){
		int startIndex = partitions[number].startIndex;
		int endIndex = partitions[number].endIndex;
		int index = 0;
		for(int i = startIndex; i <= endIndex; i++){
			if(numSet[i] == prefferedSwapNumber){
				return i;
			}
			else if(numSet[i] != number){
				index = i;
			}
		}
		return index;
	}
	
	public static int findCurrentPartition(int index, Partition[] partitions){
		for(int i = 1; i < 4; i++){
			if(partitions[i].startIndex <= index && partitions[i].endIndex >= index){
				return i;
			}
		}
		return 2;
	}
	
	public class Partition{
		int value;
		public int startIndex = 0;
		public int endIndex = -1;
		
		public Partition(int value){
			this.value = value;
		}
	}

}
