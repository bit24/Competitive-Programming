import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_404A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numI = Integer.parseInt(reader.readLine());
		int cnt = 0;
		while (numI-- > 0) {
			switch (reader.readLine().charAt(0)) {
				case 'T':
					cnt += 4;
					break;
				case 'C':
					cnt += 6;
					break;
				case 'O':
					cnt += 8;
					break;
				case 'D':
					cnt += 12;
					break;
				case 'I':
					cnt += 20;
					break;
			}
		}
		System.out.println(cnt);
	}

}
