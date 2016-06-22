import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: fence9
*/
public class fence9 {
	
	static double x1;
	static double y1;
	static double x2;
	
	static double rSlope1;
	static double rSlope2;
	
	static int count;
		
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("fence9.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fence9.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		x1 = Double.parseDouble(inputData.nextToken());
		y1 = Double.parseDouble(inputData.nextToken());
		x2 = Double.parseDouble(inputData.nextToken());
		rSlope1 = x1/y1;
		rSlope2 = (x1 - x2)/ y1;
		
		for(int y = 1; y < y1; y++){
			double leftSide = y*rSlope1;
			int l = ((int) (leftSide)) + 1;
			int r;
			
			double rightSide = y*rSlope2 + x2;
			r = (int) (Math.ceil(rightSide) - 1);
			
			count += r-l+1;
		}
		reader.close();
		printer.println(count);
		printer.close();
	}
}
