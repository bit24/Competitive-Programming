import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_391A {

	static String pattern = "Bulbasr";

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = reader.readLine();
		reader.close();

		int[] count = new int[7];
		for (int i = 0; i < line.length(); i++) {
			for (int j = 0; j < 7; j++) {
				if (line.charAt(i) == pattern.charAt(j)) {
					count[j]++;
					break;
				}
			}
		}
		count[1] /= 2;
		count[4] /= 2;
		
		int min = count[0];
		for(int i = 1; i < 7; i++){
			if(count[i] < min){
				min = count[i];
			}
		}
		System.out.println(min);
	}

}
