import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_421C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int a = Integer.parseInt(inputData.nextToken());
		int b = Integer.parseInt(inputData.nextToken());
		long l = Long.parseLong(inputData.nextToken());
		long r = Long.parseLong(inputData.nextToken());

		if (r - l + 1 >= 2 * (a + b)) {
			if (a <= b) {
				System.out.println(a + 1);
			} else {
				System.out.println(2 * a - b);
			}
			return;
		}

		StringBuilder cString = new StringBuilder();
		for (int i = 0; i < a; i++) {
			cString.append((char) ('a' + i));
		}

		l--;
		r--;

		TreeSet<Integer> used = new TreeSet<Integer>();

		
		int ans = 26;
		for (int first = 0; first < 26; first++) {
			for (int second = a; second < 26; second++) {
				for (int i = 0; i < b; i++) {
					cString.append((char)('a' + first));
				}
				used.clear();
				for (char c : cString.substring(cString.length() - a, cString.length()).toCharArray()) {
					used.add(c - 'a');
				}
				int nxt = 0;
				for (int i = 0; i < a; i++) {
					while (used.contains(nxt)) {
						nxt++;
					}
					cString.append((char) ('a' + nxt));
					nxt++;
				}
				for (int i = 0; i < b; i++) {
					cString.append((char)('a' + second));
				}

				int len = cString.length();

				used.clear();
				for (int i = (int) (l % len); i != (r + 1) % len; i = (i + 1) % len) {
					used.add(cString.charAt(i) - 'a');
				}
				if(used.size() < ans){
					ans = used.size();
				}
				cString.delete(a, cString.length());
			}
		}

		System.out.println(ans);
	}

}