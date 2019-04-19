import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_360B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder number = new StringBuilder(reader.readLine());
		StringBuilder numberR = new StringBuilder(number);
		numberR.reverse();
		
		number.append(numberR);
		System.out.println(number.toString());
	}
}
