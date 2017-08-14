import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_427B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int dif = Integer.parseInt(reader.readLine());
		String text = reader.readLine();

		int[] cnt = new int[10];

		for (int i = 0; i < text.length(); i++) {
			dif -= Character.getNumericValue(text.charAt(i));
			cnt[Character.getNumericValue(text.charAt(i))]++;
		}

		int ans = 0;
		for (int i = 0; i < 9; i++) {
			int del = Math.max(0, Math.min(cnt[i], (dif + (9 - i) - 1) / (9 - i)));
			ans += del;
			dif -= del * (9 - i);
		}
		System.out.println(ans);
	}

}
