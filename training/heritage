import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*ID: eric.ca1
LANG: JAVA
TASK: heritage
*/

public class heritage {
	
	static String inOrder;
	static String preOrder;
	
	static ArrayList<Character> stuff = new ArrayList<Character>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("heritage.in"));
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("heritage.out")));
		inOrder = reader.readLine();
		preOrder = reader.readLine();
		printTree(inOrder, preOrder);
		for(Character c : stuff){
			printer.print(c);
		}
		printer.println();
		reader.close();
		printer.close();
		
	}
	
	static void printTree(String inOrder, String preOrder){
		if(inOrder.length() < 1){
			return;
		}
		if(inOrder.length() == 1){
			stuff.add(inOrder.charAt(0));
			return;
		}
		
		char root = preOrder.charAt(0);
		int rootPosition = inOrder.indexOf(root);
		
		String lInOrder = inOrder.substring(0, rootPosition);
		
		int RFEPosition = rootPosition+1;
		String LPreOrder = preOrder.substring(1, RFEPosition);
		
		printTree(lInOrder, LPreOrder);
		
		String RInOrder = inOrder.substring(RFEPosition, inOrder.length());
		String RPreOrder = preOrder.substring(RFEPosition, preOrder.length());
		
		printTree(RInOrder, RPreOrder);
		
		stuff.add(root);
		
	}

}
