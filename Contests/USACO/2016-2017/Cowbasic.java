import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Cowbasic {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		new Cowbasic().execute();
		System.out.println(System.currentTimeMillis() - startTime);
	}

	ArrayList<String> prgm;
	HashMap<String, Integer> varNames;

	int size;
	final long MOD = 1_000_000_007L;

	// identity matrix
	long[][] idMat;

	int constT;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowbasic.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cowbasic.out")));
		prgm = new ArrayList<String>();

		String nLine = reader.readLine();
		while (nLine != null) {
			prgm.add(nLine.trim());
			nLine = reader.readLine();
		}
		reader.close();

		// sentinel for ease of parsing
		prgm.add("}");

		varNames = new HashMap<String, Integer>();

		// maps each variable name to an int ID
		for (String str : prgm) {
			if (Character.isLowerCase(str.charAt(0))) {
				String name = str.substring(0, str.indexOf(' '));
				if (!varNames.containsKey(name)) {
					varNames.put(name, varNames.size());
				}
			}
		}

		constT = varNames.size();

		// number of variables plus constT
		size = varNames.size() + 1;

		// initialize idMat
		idMat = new long[size][size];
		for (int i = 0; i < size; i++) {
			idMat[i][i] = 1;
		}

		long[][] prgmMat = parse();

		// account for sentinel
		String retLn = prgm.get(prgm.size() - 2);
		int retVar = varNames.get(retLn.substring(retLn.indexOf(' ') + 1));

		printer.println(prgmMat[retVar][constT]);
		printer.close();
	}

	int gLine = 0;

	// simple recursive parser
	long[][] parse() {
		long[][] res = idMat;

		String nLine = prgm.get(gLine++);

		while (nLine.charAt(0) != '}') {
			if (Character.isLowerCase(nLine.charAt(0))) {
				res = mult(toMat(nLine), res);
			} else if (Character.isDigit(nLine.charAt(0))) {
				long[][] subroutine = parse();

				long[][] compressed = exponentiate(subroutine,
						Integer.parseInt(nLine.substring(0, nLine.indexOf(' '))));
				res = mult(compressed, res);
			}
			nLine = prgm.get(gLine++);
		}
		return res;
	}

	long[][] toMat(String str) {
		long[][] res = new long[size][size];
		for (int i = 0; i < size; i++) {
			res[i][i] = 1;
		}
		// start off res as identity matrix

		StringTokenizer tokens = new StringTokenizer(str);

		int var = varNames.get(tokens.nextToken());
		long[] modRow = res[var];
		modRow[var] = 0;

		while (tokens.hasMoreTokens()) {
			String nT = tokens.nextToken();

			// no modulo necessary in this unit due to lines only being 350 characters long and int < 100_000
			if (Character.isDigit(nT.charAt(0))) {
				modRow[constT] += Integer.parseInt(nT);
			} else if (Character.isLowerCase(nT.charAt(0))) {
				modRow[varNames.get(nT)]++;
			}
		}
		return res;
	}

	long[][] mult(long[][] a, long[][] b) {
		long[][] res = new long[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				long sum = 0;
				for (int k = 0; k < size; k++) {
					sum = (sum + a[i][k] * b[k][j]) % MOD;
				}
				res[i][j] = sum;
			}
		}
		return res;
	}

	long[][] exponentiate(long[][] base, int exp) {
		long[][] cSqr = new long[size][];
		for (int i = 0; i < size; i++) {
			cSqr[i] = Arrays.copyOf(base[i], size);
		}

		long[][] res = idMat;
		while (exp != 0) {
			if ((exp & 1) == 1) {
				res = mult(res, cSqr);
			}

			cSqr = mult(cSqr, cSqr);
			exp >>= 1;
		}
		return res;
	}
}
