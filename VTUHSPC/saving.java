import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class saving {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int space = line.indexOf(' ');
		int n = Integer.parseInt(line.substring(0, space));
		int y = Integer.parseInt(line.substring(space + 1));
		boolean[] found = new boolean[n];
		for(int i = 0; i < y; i++) {
			int curr = Integer.parseInt(br.readLine());
			found[curr] = true;
		}
		int total = 0;
		for(int i = 0; i < n; i++) {
			if(!found[i]) {
				System.out.println(i);
			} else {
				total++;
			}
		}
		System.out.println("Mario got " + total + " of the dangerous obstacles.");
	}

}
