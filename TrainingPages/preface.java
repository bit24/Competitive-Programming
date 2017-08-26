/*
 ID: eric.ca1
 LANG: JAVA
 TASK: preface
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class preface {
	
	public static final char[] reference = new char[]{'I', 'V', 'X', 'L', 'C', 'D', 'M'};

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("preface.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("preface.out")));
		
		int[] arrayOfInformation = new int[7];
		
		int stopCondition = Integer.parseInt(reader.readLine());
		reader.close();
		
		String s = "";
		for(int i = 1; i <= stopCondition; i++){
			
			s = add1(s, arrayOfInformation);
			
		}
		
		
		for(int i = 0; i < 7; i++){
			if(arrayOfInformation[i] == 0){
				break;
			}
			printer.println(reference[i] + " " + arrayOfInformation[i]);
		}
		
		printer.close();
	}

	public static String add1(String s, int[] timesUsed){
		s += 'I';
		s = simplify(s);
		for(int i = s.length() - 1; i >= 0; i--){
			addNumeral(s.charAt(i), timesUsed);
		}
		return s;
	}
	
	public static void addNumeral(char numeral, int[] guide){
		switch (numeral){
			case 'I': guide[0]++; return;
			case 'V': guide[1]++; return;
			case 'X': guide[2]++; return;
			case 'L': guide[3]++; return;
			case 'C': guide[4]++; return;
			case 'D': guide[5]++; return;
			case 'M': guide[6]++; return;
		}
	}

	public static String simplify(String s){
		if(s.length() == 0){
			return s;
		}
		
		char lastCharacter = s.charAt(s.length()-1);
		//if the four last characters are the same (we can assume they are a power of 10), we merge
		if(s.length() >= 4 && lastCharacter ==  s.charAt(s.length()-2) && lastCharacter ==  s.charAt(s.length()-3) && lastCharacter ==  s.charAt(s.length()-4)){
			
			boolean isPowerOf10 = true;
			if(s.length() > 4 && s.charAt(s.length()-5) == getSizeUp(lastCharacter)){
				isPowerOf10 = false;
			}
			
			
			if(isPowerOf10){
				s = s.substring(0, s.length()-4);
				s += lastCharacter;
				s+= getSizeUp(lastCharacter);
			}
			
			else{
				s = s.substring(0, s.length()-5);
				s += lastCharacter;
				s += getSizeUp(getSizeUp(lastCharacter));
			}
		}
		
		if(s.length() >= 3 && s.charAt(s.length()-3) == lastCharacter){
			if(s.charAt(s.length()-2) == getSizeUp(lastCharacter)){
				char savedChar = s.charAt(s.length()-2);
				s = s.substring(0, s.length()-3);
				return simplify(s += savedChar);
			}
			if(s.charAt(s.length()-2) == getSizeUp(getSizeUp(lastCharacter)) && isPowerOf10(lastCharacter)){
				char savedChar = s.charAt(s.length()-2);
				s = s.substring(0, s.length()-3);
				return simplify(s += savedChar);
			}
			
		}
		return s;
	}
	
	public static boolean isPowerOf10(char input){
		if(input == 'I' || input == 'X' || input == 'C' || input == 'M'){
			return true;
		}
		return false;
	}
	
	public static char getSizeUp(char input){
		switch(input){
			case 'I': return 'V';
			case 'V': return 'X';
			case 'X': return 'L';
			case 'L': return 'C';
			case 'C': return 'D';
			case 'D': return 'M';
			default : return 'F';
		}
	}
	
}
