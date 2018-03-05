import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class plinko {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int b = Integer.parseInt(reader.readLine());
		for(int i = 0; i < b; i++) {
			StringTokenizer wh = new StringTokenizer(reader.readLine());
			int w = Integer.parseInt(wh.nextToken()), h = Integer.parseInt(wh.nextToken());
			String[] board = new String[h];
			for(int j = 0; j < h; j++) {
				board[j] = reader.readLine();
			}
			int max = -1;
			int maxIndex = -1;
			for(int j = 0; j < w; j++) {
				int x = j, y = 0;
				while(true) {
					if(y == h - 1) {
						int score = Integer.parseInt("" + board[y].charAt(x));
						if(score > max) {
							max = score;
							maxIndex = j;
						}
						break;
					} else if(board[y].charAt(x) == '.') {
						y++;
					} else if(board[y].charAt(x) == '|' || board[y].charAt(x) == '_') {
						break;
					} else if(board[y].charAt(x) == '\\') {
						while(board[y].charAt(++x) == '_') {}
						if(board[y].charAt(x) != '.')
							break;
					} else if(board[y].charAt(x) == '/') {
						while(board[y].charAt(--x) == '_') {}
						if(board[y].charAt(x) != '.')
							break;
					}
				}
			}
			if(max <= 0) {
				printer.println("Board #" + (i + 1) + ": Don't even bother dropping a coin. You can't win!");
			} else {
				printer.println("Board #" + (i + 1) + ": Drop at column " + maxIndex + " for a score of " + max + " points.");
			}
			printer.println();
		}
		printer.close();
	}
}
