import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: shuttle
*/

public class shuttle {
	static int subSize;
	static int totalSize;
	static int finishState;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("shuttle.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("shuttle.out")));

		subSize = Integer.parseInt(reader.readLine());
		reader.close();
		totalSize = 2 * subSize + 1;

		int base = (1 << (subSize)) - 1;

		finishState = base << (subSize + 1);

		String ans = dfs("", base, subSize);

		int count = 0;
		StringTokenizer tokens = new StringTokenizer(ans);
		int numTokens = tokens.countTokens();
		for(int i = 0; i < numTokens-1; i++){
		
			int value = totalSize - Integer.parseInt(tokens.nextToken());
			printer.print(value);
			count++;
			if (count == 20) {
				count = 0;
				printer.println();
			} else {
				printer.print(" ");
			}
		}
		printer.println(totalSize - Integer.parseInt(tokens.nextToken()));
		printer.close();
	}

	// hopefully search will be sparse
	public static String dfs(String searchQueue, int mask, int blank) {
		mask &= ~(1 << blank);
		if (mask == finishState && blank == subSize) {
			return searchQueue;
		}

		for (int currentIndex = totalSize - 1; currentIndex >= 0; currentIndex--) {
			if (currentIndex == blank) {
				continue;
			}
			if ((mask & (1 << currentIndex)) == 0) {

				if (currentIndex - 1 == blank) {
					String s = dfs(searchQueue + currentIndex + " ", mask & ~(1 << blank), currentIndex);
					if (s != null) {
						return s;
					}
				}

				if (currentIndex - 2 == blank && (mask & (1 << (currentIndex - 1))) != 0) {
					String s = dfs(searchQueue + currentIndex + " ", mask & ~(1 << blank), currentIndex);
					if (s != null) {
						return s;
					}
				}

			} else {
				if (currentIndex + 1 == blank) {
					String s = dfs(searchQueue + currentIndex + " ", mask | (1 << blank), currentIndex);
					if (s != null) {
						return s;
					}
				}
				if (currentIndex + 2 == blank && (mask & (1 << (currentIndex + 1))) == 0) {
					String s = dfs(searchQueue + currentIndex + " ", mask | (1 << blank), currentIndex);
					if (s != null) {
						return s;
					}
				}
			}
		}
		return null;
	}

}
