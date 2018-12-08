import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BabyNames {

	final long MOD = 1_000_000_123;
	final long[] pBases = { 900001, 900007, 900019, 900037, 900061, 900089, 900091, 900103, 900121, 900139, 900143,
			900149, 900157, 900161, 900169, 900187, 900217, 900233, 900241, 900253, 900259, 900283, 900287, 900293,
			900307, 900329, 900331, 900349, 900397, 900409, 900443, 900461, 900481, 900491, 900511, 900539, 900551,
			900553, 900563, 900569, 900577, 900583, 900587, 900589, 900593, 900607, 900623, 900649, 900659, 900671,
			900673, 900689, 900701, 900719, 900737, 900743, 900751, 900761, 900763, 900773, 900797, 900803, 900817,
			900821, 900863, 900869, 900917, 900929, 900931, 900937, 900959, 900971, 900973, 900997, 901007, 901009,
			901013, 901063, 901067, 901079, 901093, 901097, 901111, 901133, 901141, 901169, 901171, 901177, 901183,
			901193, 901207, 901211, 901213, 901247, 901249, 901253, 901273, 901279, 901309, 901333, 901339, 901367,
			901399 };

	BufferedReader reader;
	PrintWriter printer;

	String ans = null;

	public static void main(String[] args) throws IOException {
		new BabyNames().main();
	}

	public void main() throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int id = Integer.parseInt(inputData.nextToken());
		int N = Integer.parseInt(inputData.nextToken());

		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			list.add(reader.readLine());
		}

		process(0, list);
		printer.println("RETURN " + ans);
		printer.close();
	}

	long hash(String str, int level) {
		long cur = 0;
		for (int i = 0; i < str.length(); i++) {
			cur = (cur * pBases[level] + str.charAt(i)) % MOD;
		}
		return cur;
	}

	void process(int cLevel, ArrayList<String> strs) throws IOException {
		if (ans != null) {
			return;
		}

		printer.println("MOO " + Integer.toBinaryString(strs.size()));
		printer.flush();

		int oN = Integer.parseInt(reader.readLine(), 2);
		int mN = Math.max(strs.size(), oN);
		// printer.println("DEBUG: " + mN);
		// printer.flush();

		if (mN == 1) {
			ans = strs.get(0);
			return;
		}

		ArrayList<String>[] buckets = new ArrayList[mN];

		for (String cStr : strs) {
			int i = (int) (hash(cStr, cLevel) % mN);
			if (buckets[i] == null) {
				buckets[i] = new ArrayList<>();
			}
			buckets[i].add(cStr);
		}
		StringBuilder oStr = new StringBuilder();
		oStr.append("MOO ");

		for (int i = 0; i < mN; i++) {
			oStr.append(buckets[i] == null ? '0' : '1');
		}
		printer.println(oStr.toString());
		printer.flush();

		String resp = reader.readLine();

		for (int i = 0; i < mN; i++) {
			if (buckets[i] != null && resp.charAt(i) == '1') {
				process(cLevel + 1, buckets[i]);
			}
		}
	}

	String padded(int c) {
		String str = Integer.toBinaryString(c);
		while (str.length() < 14) {
			str = "0" + str;
		}
		return str;
	}
}