import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class nick {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());
		for (int t = 1; t <= nT; t++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int[] len = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()) };
			
		}
	}
}
