import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/*
 ID: eric.ca1
 LANG: JAVA
 TASK: prefix
 */
public class prefix {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("prefix.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("prefix.out")));
		HashSet<String> primitives = new HashSet<String>();
		
		String[] data;
		String line = reader.readLine();
		while(!line.equals(".")){
			data = line.split(" ");
			for(String currentString : data){
				primitives.add(currentString);
			}
			line = reader.readLine();
		}
		
		char[] sequence = new char[200000];
		int sequenceIterator = 0;
		
		line = reader.readLine();
		while(line != null){
			for(int i = 0; i < line.length(); i++){
				sequence[sequenceIterator++] = line.charAt(i);
			}
			line = reader.readLine();
		}
		
		boolean[] hasLength = new boolean[sequenceIterator + 1];
		
		
		int endIndex = 0;
		hasLength[0] = true;
		
		
 mLoop: for(int currentIndex = 0; currentIndex < sequenceIterator+1; currentIndex++){
			for(int i = 1; i <= 10; i++){
				int backIndex = currentIndex - i;
				if(backIndex < 0){
					break;
				}
				
				if(hasLength[backIndex]){
					endIndex = 0;
					
					String primToFind = "";
					for(int j = backIndex; j < currentIndex; j++){
						primToFind += sequence[j];
					}
					
					if(primitives.contains(primToFind)){

						hasLength[currentIndex] = true;
						continue mLoop;
					}
					
				}else{
					endIndex++;
					if(endIndex > 10){
			 			break mLoop;
			 		}
				}
			}
		}
		
		for(int i = sequenceIterator; i>=0; i--){
			if(hasLength[i]){
				printer.println(i);
				printer.close();
				break;
			}
		}
		
	}
	


}
