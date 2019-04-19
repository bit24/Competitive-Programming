import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_410C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numE = Integer.parseInt(reader.readLine());
		int[] elements = new int[numE];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		int gcd = elements[0];
		for (int i = 1; i < numE; i++) {
			gcd = gcd(gcd, elements[i]);
		}

		if (gcd > 1) {
			printer.println("YES");
			printer.println(0);
			printer.close();
			return;
		}

		int cnt = 0;
		for (int i = 0; i < numE; i++) {
			if ((elements[i] & 1) == 1) {
				if (i + 1 < numE && (elements[i + 1] & 1) == 1) {
					cnt++;
					i++;
				} else {
					cnt += 2;
				}
			}
		}
		printer.println("YES");
		printer.println(cnt);
		printer.close();
	}

	static int gcd(int a, int b) {
		if (a > b) {
			return gcd(b, a);
		}
		if(a == 0){
			return b;
		}
		return gcd(a, b % a);
	}

}
