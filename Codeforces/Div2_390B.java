import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_390B {

	public static void main(String[] args) throws IOException {
		new Div2_390B().execute();
		;
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String[] grid = new String[4];
		for (int i = 0; i < 4; i++) {
			grid[i] = reader.readLine();
		}

		for (int i = 0; i < 4; i++) {
			if (grid[i].contains("xx.") || grid[i].contains(".xx") || grid[i].contains("x.x")) {
				System.out.println("YES");
				return;
			}
		}

		String[] nGrid = new String[] { "", "", "", "" };

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				nGrid[j] += grid[i].charAt(j);
			}
		}

		for (int i = 0; i < 4; i++) {
			if (nGrid[i].contains("xx.") || nGrid[i].contains(".xx") || nGrid[i].contains("x.x")) {
				System.out.println("YES");
				return;
			}
		}

		String[] dGrid = new String[6];
		dGrid[0] = "" + grid[2].charAt(0) + grid[1].charAt(1) + grid[0].charAt(2);
		dGrid[1] = "" + grid[3].charAt(0) + grid[2].charAt(1) + grid[1].charAt(2) + grid[0].charAt(3);
		dGrid[2] = "" + grid[3].charAt(1) + grid[2].charAt(2) + grid[1].charAt(3);
		dGrid[3] = "" + grid[0].charAt(1) + grid[1].charAt(2) + grid[2].charAt(3);
		dGrid[4] = "" + grid[0].charAt(0) + grid[1].charAt(1) + grid[2].charAt(2) + grid[3].charAt(3);
		dGrid[5] = "" + grid[1].charAt(0) + grid[2].charAt(1) + grid[3].charAt(2);

		for (int i = 0; i < 6; i++) {
			if (dGrid[i].contains("xx.") || dGrid[i].contains(".xx") || dGrid[i].contains("x.x")) {
				System.out.println("YES");
				return;
			}
		}
		System.out.println("NO");

	}

}
