import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Edu_23A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int x1 = Integer.parseInt(inputData.nextToken());
		int y1 = Integer.parseInt(inputData.nextToken());
		int x2 = Integer.parseInt(inputData.nextToken());
		int y2 = Integer.parseInt(inputData.nextToken());
		inputData = new StringTokenizer(reader.readLine());
		int xD = Integer.parseInt(inputData.nextToken());
		int yD = Integer.parseInt(inputData.nextToken());

		if (Math.abs(x1 - x2) % xD != 0) {
			System.out.println("NO");
			return;
		}
		if (Math.abs(y1 - y2) % yD != 0) {
			System.out.println("NO");
			return;
		}

		if (((Math.abs(x1 - x2) / xD + Math.abs(y1 - y2) / yD) & 1) == 0) {
			System.out.println("YES");
		}
		else{
			System.out.println("NO");
		}
	}

}
