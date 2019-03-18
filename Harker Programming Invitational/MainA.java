import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.TreeSet;

public class MainA {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int N = Integer.parseInt(br.readLine());
		String s = br.readLine();
		int f = 0;

		TreeSet<Character> cur = new TreeSet<Character>();
		for (int i = 0; i < N; i++) {
			char x = s.charAt(i);
			TreeSet<Character> cur2 = new TreeSet<Character>();
			int ans = 0;
			if (x == 'r') {
				if (!cur.contains('r')) {
					ans = 1;
				}
				cur2.add('r');
			}
			if (x == 'y') {
				if (!cur.contains('y')) {
					ans = 1;
				}
				cur2.add('y');
			}
			if (x == 'b') {
				if (!cur.contains('b')) {
					ans = 1;
				}
				cur2.add('b');
			}

			if (x == 'o') {
				if (cur.contains('o')) {
					ans = 0;
					cur2.add('o');
				} else if (!cur.contains('r') && !cur.contains('y')) {
					ans = 1;
					cur2.add('o');
				} else if (!cur.contains('r') || !cur.contains('y')) {
					ans = 1;
					cur2.add('r');
					cur2.add('y');
				} else {
					ans = 0;
					cur2.add('r');
					cur2.add('y');
				}
			}

			if (x == 'g') {
				if (cur.contains('g')) {
					ans = 0;
					cur2.add('g');
				} else if (!cur.contains('b') && !cur.contains('y')) {
					ans = 1;
					cur2.add('g');
				} else if (!cur.contains('b') || !cur.contains('y')) {
					ans = 1;
					cur2.add('b');
					cur2.add('y');
				} else {
					ans = 0;
					cur2.add('b');
					cur2.add('y');
				}
			}

			if (x == 'p') {
				if (cur.contains('p')) {
					ans = 0;
					cur2.add('p');
				} else if (!cur.contains('r') && !cur.contains('b')) {
					ans = 1;
					cur2.add('p');
				} else if (!cur.contains('r') || !cur.contains('b')) {
					ans = 1;
					cur2.add('r');
					cur2.add('b');
				} else {
					ans = 0;
					cur2.add('r');
					cur2.add('b');
				}
			}

			cur = cur2;

			f += ans;
		}

		printer.println(f);
		printer.close();
	}
}
