import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_416A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long a = Integer.parseInt(inputData.nextToken());
		long b = Integer.parseInt(inputData.nextToken());

		long cnt1 = (long) Math.sqrt(a);
		long cnt2 = ((long) Math.sqrt(1 + 4 * b) - 1) / 2;
		
		if(cnt1 <= cnt2){
			System.out.println("Vladik");
		}
		else{
			System.out.println("Valera");
		}
	}

}
