import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GB_2015B {

	public static void main(String[] args) throws IOException {
		new GB_2015B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		long start = Long.parseLong(inputData.nextToken());
		long end = Long.parseLong(inputData.nextToken());

		int count = 0;
		for (int mask = 1; mask < 64; mask++) {
			for (int i = 0; i < mask - 1; i++) {
				long value = ((1L << mask) - 1L) ^ (1L << i);
				if (start <= value && value <= end) {
					//System.out.println(Long.toBinaryString((1L << mask) - 1L));
					//System.out.println(Long.toBinaryString((1L << i)));

					count++;
				}
			}
		}
		System.out.println(count);
	}

}
