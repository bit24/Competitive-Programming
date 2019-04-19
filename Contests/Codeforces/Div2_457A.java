import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_457A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int x = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int h = Integer.parseInt(inputData.nextToken());
		int m = Integer.parseInt(inputData.nextToken());

		int cnt = 0;
		while (m % 10 != 7 && h % 10 != 7) {
			m -= x;
			if (m < 0) {
				m += 60;
				h--;
				if (h < 0) {
					h += 24;
				}
			}
			cnt++;
		}
		printer.println(cnt);
		printer.close();
	}

}
