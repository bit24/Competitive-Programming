import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class maze {

	public static int[][] directions = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int r = Integer.parseInt(st.nextToken()), c = Integer.parseInt(st.nextToken());
			String[] maze = new String[r];
			int startX = -1, startY = -1;
			boolean found = false;
			for (int j = 0; j < r; j++) {
				maze[j] = reader.readLine();
				if (!found) {
					int pos = maze[j].indexOf('S');
					if (pos >= 0) {
						startX = j;
						startY = pos;
						found = true;
					}
				}
			}
			int dir = 0;
			int x = startX, y = startY;
			int num = 0;
			while (true) {
				if (maze[x].charAt(y) == 'E') {
					printer.println("Maze #" + (i + 1) + ": " + num * 5 + " seconds");
					break;
				} else if (num > 0 && maze[x].charAt(y) == 'S') {
					printer.println("Maze #" + (i + 1) + ": Impossible");
					break;
				} else if (x + directions[(dir + 1) % 4][0] >= 0 && x + directions[(dir + 1) % 4][0] < r
						&& y + directions[(dir + 1) % 4][1] >= 0 && y + directions[(dir + 1) % 4][1] < c
						&& maze[x + directions[(dir + 1) % 4][0]].charAt(y + directions[(dir + 1) % 4][1]) != '#') {
					dir = (dir + 1) % 4;
					x += directions[dir][0];
					y += directions[dir][1];
				} else if (x + directions[dir][0] >= 0 && x + directions[dir][0] < r && y + directions[dir][1] >= 0
						&& y + directions[dir][1] < c
						&& maze[x + directions[dir][0]].charAt(y + directions[dir][1]) != '#') {
					x += directions[dir][0];
					y += directions[dir][1];
				} else if (x + directions[(dir + 3) % 4][0] >= 0 && x + directions[(dir + 3) % 4][0] < r
						&& y + directions[(dir + 3) % 4][1] >= 0 && y + directions[(dir + 3) % 4][1] < c
						&& maze[x + directions[(dir + 3) % 4][0]].charAt(y + directions[(dir + 3) % 4][1]) != '#') {
					dir = (dir + 3) % 4;
					x += directions[dir][0];
					y += directions[dir][1];
				} else if (x + directions[(dir + 2) % 4][0] >= 0 && x + directions[(dir + 2) % 4][0] < r
						&& y + directions[(dir + 2) % 4][1] >= 0 && y + directions[(dir + 2) % 4][1] < c
						&& maze[x + directions[(dir + 2) % 4][0]].charAt(y + directions[(dir + 2) % 4][1]) != '#') {
					dir = (dir + 2) % 4;
					x += directions[dir][0];
					y += directions[dir][1];
				} else {
					printer.println("Maze #" + (i + 1) + ": Impossible");
					break;
				}
				num++;
			}
		}
		printer.close();
	}
}
