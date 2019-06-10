import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Censoring {

	static final long mod = 1_000_000_007;
	static final long base = 100_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("censor.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

		String text = reader.readLine();

		int nP = Integer.parseInt(reader.readLine());
		TreeMap<Integer, TreeMap<Long, ArrayList<String>>> byLen = new TreeMap<>();
		for (int i = 0; i < nP; i++) {
			String cStr = reader.readLine();
			TreeMap<Long, ArrayList<String>> cMap = byLen.get(cStr.length());
			if (cMap == null) {
				cMap = new TreeMap<Long, ArrayList<String>>();
				byLen.put(cStr.length(), cMap);
			}
			long cHash = 0;
			for (int j = 0; j < cStr.length(); j++) {
				cHash = (cHash * base + cStr.charAt(j)) % mod;
			}
			ArrayList<String> cList = cMap.get(cHash);
			if (cList == null) {
				cList = new ArrayList<String>();
				cMap.put(cHash, cList);
			}
			cList.add(cStr);
		}
		reader.close();

		long[] pwr = new long[text.length() + 1];
		pwr[0] = 1;
		for (int i = 1; i <= text.length(); i++) {
			pwr[i] = pwr[i - 1] * base % mod;
		}

		StringBuilder pStr = new StringBuilder();
		long[] pHash = new long[text.length()];

		nChar:
		for (int i = 0; i < text.length(); i++) {
			pHash[pStr.length()] = pStr.length() == 0 ? text.charAt(i)
					: (pHash[pStr.length() - 1] * base + text.charAt(i)) % mod;
			pStr.append(text.charAt(i));

			for (Entry<Integer, TreeMap<Long, ArrayList<String>>> cSG : byLen.entrySet()) {
				int cLen = cSG.getKey();
				if (cLen > pStr.length()) {
					break;
				}
				long cHash = (pHash[pStr.length() - 1]
						- (pStr.length() != cLen ? pHash[pStr.length() - 1 - cLen] * pwr[cLen] : 0) % mod + mod) % mod;
				ArrayList<String> pMat = cSG.getValue().get(cHash);
				if (pMat != null) {
					for (String cStr : pMat) {
						if (cStr.equals(pStr.substring(pStr.length() - cLen, pStr.length()))) {
							pStr.delete(pStr.length() - cLen, pStr.length());
							continue nChar;
						}
					}
				}
			}
		}
		printer.println(pStr.toString());
		printer.close();
	}

}