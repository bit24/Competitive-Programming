import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_386C {

	public static void main(String[] args) throws IOException {
		new Div2_386C().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long end = Long.parseLong(inputData.nextToken());
		long iStart = Long.parseLong(inputData.nextToken());
		long iEnd = Long.parseLong(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		long tCost = Long.parseLong(inputData.nextToken());
		long iCost = Long.parseLong(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		long tPosition = Long.parseLong(inputData.nextToken());
		boolean tDirection = Long.parseLong(inputData.nextToken()) == 1;

		long v1 = iCost * Math.abs((iEnd - iStart));
		long v2;

		boolean iDirection = iStart < iEnd;
		if (tDirection != iDirection) {
			v2 = tCost * (tDirection ? 2 * end - tPosition - iEnd : tPosition + iEnd);
		} else {
			if (tDirection) {
				v2 = tCost * (tPosition <= iStart ? iEnd - tPosition : 2 * end - tPosition + iEnd);
			} else {
				v2 = tCost * (iStart <= tPosition ? tPosition - iEnd : tPosition + 2 * end - iEnd);
			}
		}

		System.out.println(Math.min(v1, v2));
	}

}
