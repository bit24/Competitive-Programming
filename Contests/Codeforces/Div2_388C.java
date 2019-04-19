import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Div2_388C {

	public static void main(String[] args) throws IOException {
		new Div2_388C().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());
		String teamData = reader.readLine();
		reader.close();

		ArrayDeque<Integer> aQueue = new ArrayDeque<Integer>();
		ArrayDeque<Integer> bQueue = new ArrayDeque<Integer>();

		int time;
		for (time = 0; time < numE; time++) {
			if (teamData.charAt(time) == 'D') {
				aQueue.addLast(time);
			} else {
				bQueue.addLast(time);
			}
		}

		if (bQueue.isEmpty()) {
			System.out.println('D');
			return;
		}
		if (aQueue.isEmpty()) {
			System.out.println('R');
			return;
		}

		for (;; time++) {
			int a = aQueue.removeFirst();
			int b = bQueue.removeFirst();

			if (bQueue.isEmpty()) {
				System.out.println('D');
				return;
			}
			if (aQueue.isEmpty()) {
				System.out.println('R');
				return;
			}

			if (a < b) {
				aQueue.addLast(time);
			} else {
				bQueue.addLast(time);
			}
		}

	}

}
