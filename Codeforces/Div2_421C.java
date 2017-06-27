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

		StringBuilder cString = new StringBuilder();
		for (int i = 0; i < a; i++) {
			cString.append((char) ('a' + i));
		}
		char last = cString.charAt(cString.length() - 1);
		for (int i = 0; i < b; i++) {
			cString.append(last);
		}
		TreeSet<Integer> used = new TreeSet<Integer>();
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

		last = cString.charAt(cString.length() - 1);
		for (int i = 0; i < b; i++) {
			cString.append(last);
		}

		int len = cString.length();

		used.clear();
		if (r - l + 1 >= len) {
			for (char c : cString.toString().toCharArray()) {
				used.add(c - 'a');
			}
		} else {
			String pString = " " + cString.toString();

			int start = (int) ((l % len) == 0 ? len : (l % len));
			int end = (int) ((r + 1) % len == 0 ? len : (r + 1) % len);

			for (int i = start; i != end; i = (int) ((i + 1) % len == 0 ? len : (i + 1) % len)) {
				used.add(pString.charAt(i) - 'a');
			}
		}
		System.out.println(used.size());
	}

}
