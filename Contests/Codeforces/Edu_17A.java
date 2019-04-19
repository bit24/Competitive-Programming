import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Edu_17A {

	public static void main(String[] args) throws IOException {
		new Edu_17A().execute();
	}

	ArrayList<Long> primes = new ArrayList<Long>();
	ArrayList<Long> count = new ArrayList<Long>();

	ArrayList<Long> divisors = new ArrayList<Long>();

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long n = Long.parseLong(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken()) - 1;
		reader.close();

		long cV = n;
		for (long i = 2; i <= 1 + Math.sqrt(n); i++) {
			if (cV % i == 0L) {
				primes.add(i);
				long numP = 0;
				while (cV % i == 0) {
					cV /= i;
					numP++;
				}
				count.add(numP);
			}
		}

		if (cV > 1) {
			primes.add(cV);
			count.add(1L);
		}
		addAll(0, 1);
		Collections.sort(divisors);

		if (k >= divisors.size()) {
			System.out.println(-1);
		} else {
			System.out.println(divisors.get(k));
		}
	}

	void addAll(int index, long cProduct) {
		if (index >= primes.size()) {
			divisors.add(cProduct);
			return;
		}
		for (int i = 0; i <= count.get(index); i++) {
			addAll(index + 1, cProduct);
			cProduct *= primes.get(index);
		}
	}

}
