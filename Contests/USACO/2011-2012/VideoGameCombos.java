import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class VideoGameCombos {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("combos.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("combos.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nP = Integer.parseInt(inputData.nextToken());
		int len = Integer.parseInt(inputData.nextToken());
		String[] combo = new String[nP];

		TreeSet<String> pPref = new TreeSet<String>();
		TreeSet<String> cPat = new TreeSet<String>();
		for (int i = 0; i < nP; i++) {
			String cStr = reader.readLine();
			combo[i] = cStr;
			cPat.add(cStr);

			for (int j = 1; j <= cStr.length(); j++) {
				pPref.add(cStr.substring(0, j));
			}
		}
		reader.close();
		pPref.add("");

		TreeMap<String, Integer> cPosSuf = new TreeMap<String, Integer>();
		TreeMap<String, Integer> nPosSuf = new TreeMap<String, Integer>();

		cPosSuf.put("", 0);

		for (int i = 0; i < len; i++) {
			for (Entry<String, Integer> cE : cPosSuf.entrySet()) {

				for (char nC = 'A'; nC <= 'C'; nC++) {
					int mC = cE.getValue();
					String nStr = cE.getKey() + nC;
					for (int j = 1; j <= Math.min(nStr.length(), 15); j++) {
						if (cPat.contains(nStr.substring(nStr.length() - j, nStr.length()))) {
							mC++;
						}
					}

					for (int j = 0; j <= Math.min(nStr.length(), 15); j++) {
						String sSuf = nStr.substring(nStr.length() - j, nStr.length());
						if (pPref.contains(sSuf)) {
							Integer bM = nPosSuf.get(sSuf);
							if (bM == null || bM < mC) {
								nPosSuf.put(sSuf, mC);
							}
						}
					}
				}
			}
			cPosSuf = nPosSuf;
			nPosSuf = new TreeMap<String, Integer>();
		}
		int bFA = 0;
		for(Entry<String, Integer> cE : cPosSuf.entrySet()) {
			if(bFA < cE.getValue()) {
				bFA = cE.getValue();
			}
		}
		printer.println(bFA);
		printer.close();
	}

}
