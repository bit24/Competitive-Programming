import java.util.ArrayList;

class KMP {

	int tS;
	String text;

	int pS;
	private String pattern;

	int[] pTable;

	KMP(String text, String pattern) {
		this.text = " " + text;
		tS = text.length();
		this.pattern = " " + pattern;
		pS = pattern.length();
	}

	public ArrayList<Integer> search() {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		computePTable();
		int pPre = 0;

		for (int cI = 1; cI <= tS; cI++) {
			while (pPre >= 0 && pattern.charAt(pPre + 1) != text.charAt(cI)) {
				pPre = pTable[pPre];
			}
			pPre++;
			if (pPre == pS) {
				indices.add(cI - pS + 1);
				pPre = pTable[pPre];
			}
		}
		return indices;
	}

	// pTable contains the length of the longest proper prefix that is also a proper suffix
	// proper means that it cannot just be the whole string
	public void computePTable() {
		pTable = new int[pS + 1];
		pTable[0] = -1;
		int pPre = -1;

		for (int cI = 1; cI <= pS; cI++) {
			while (pPre >= 0 && pattern.charAt(pPre + 1) != pattern.charAt(cI)) {
				pPre = pTable[pPre];
			}
			pTable[cI] = ++pPre;
		}
	}
}
