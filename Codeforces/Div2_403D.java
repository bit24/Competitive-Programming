import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div2_403D {

	public static void main(String[] args) throws IOException {
		new Div2_403D().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numE = Integer.parseInt(reader.readLine());

		String[] strings = new String[numE];

		for (int i = 0; i < numE; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			strings[i] = inputData.nextToken().substring(0, 3) + inputData.nextToken().charAt(0) + i;
		}
		Arrays.sort(strings, lexico);

		for (int i = 0; i < numE; i++) {
			int j;
			for (j = i; j + 1 < numE && lexico.compare(strings[i], strings[j + 1]) == 0; j++) {
			}

			if (i < j) {
				for (int k = i; k <= j; k++) {
					strings[k] = strings[k].substring(0, 2) + strings[k].substring(3);
				}
			}
			i = j;
		}
		
		boolean updated = true;
		
		while(updated){
			updated = false;
			
			Arrays.sort(strings, lexico);
			for (int i = 0; i + 1 < numE; i++) {
				if (lexico.compare(strings[i], strings[i + 1]) == 0) {
					if (Character.isAlphabetic(strings[i].charAt(3))) {
						strings[i] = strings[i].substring(0, 2) + strings[i].substring(3);
						updated = true;
					} else if (Character.isAlphabetic(strings[i + 1].charAt(3))) {
						strings[i + 1] = strings[i + 1].substring(0, 2) + strings[i + 1].substring(3);
						updated = true;
					} else {
						System.out.println("NO");
						return;
					}
				}
			}
		}

		printer.println("YES");

		String[] toPrint = new String[numE];
		for (String str : strings) {
			if (!Character.isAlphabetic(str.charAt(3))) {
				toPrint[Integer.parseInt(str.substring(3))] = str;
			} else {
				toPrint[Integer.parseInt(str.substring(4))] = str;
			}
		}

		for (String str : toPrint) {
			printer.println(str.substring(0, 3));
		}
		printer.close();
	}

	Comparator<String> lexico = new Comparator<String>() {
		public int compare(String o1, String o2) {
			for (int i = 0; i < 3; i++) {
				if (o1.charAt(i) < o2.charAt(i)) {
					return -1;
				}
				if (o1.charAt(i) > o2.charAt(i)) {
					return 1;
				}
			}
			return 0;
		}
	};

}