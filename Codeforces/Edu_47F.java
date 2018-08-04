import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Edu_47F implements Runnable {

	public static void main(String[] args) throws IOException {
		new Thread(null, new Edu_47F(), "Main", 1 << 28).start();
	}

	ArrayList<Integer>[] aList;

	int[] mCnt;
	int[] mRDep;

	ArrayList<Integer>[] cnts;

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
			int nV = Integer.parseInt(reader.readLine());
			aList = new ArrayList[nV];

			for (int i = 0; i < nV; i++) {
				aList[i] = new ArrayList<Integer>();
			}

			for (int i = 0; i < nV - 1; i++) {
				StringTokenizer inputData = new StringTokenizer(reader.readLine());
				int a = Integer.parseInt(inputData.nextToken()) - 1;
				int b = Integer.parseInt(inputData.nextToken()) - 1;
				aList[a].add(b);
				aList[b].add(a);
			}

			mCnt = new int[nV];
			mRDep = new int[nV];
			cnts = new ArrayList[nV];

			dfs(0, -1);

			for (int i = 0; i < nV; i++) {
				printer.println(mRDep[i]);
			}
			printer.close();
		} catch (Exception e) {

		}
	}

	void dfs(int cV, int pV) {
		int mCDepth = 0;
		int mC = -1;

		for (int aV : aList[cV]) {
			if (aV != pV) {
				dfs(aV, cV);
				if (cnts[aV].size() > mCDepth) {
					mCDepth = cnts[aV].size();
					mC = aV;
				}
			}
		}

		if (mC == -1) {
			cnts[cV] = new ArrayList<>();
			cnts[cV].add(1);
			mCnt[cV] = 1;
			mRDep[cV] = 0;
			return;
		}

		ArrayList<Integer> pCnts = cnts[mC];
		int pMCnt = mCnt[mC];
		int pMRDep = mRDep[mC];

		for (int aV : aList[cV]) {
			if (aV != pV && aV != mC) {
				ArrayList<Integer> tMerge = cnts[aV];

				for (int i = 0; i < tMerge.size(); i++) {
					int nVal = pCnts.get(pCnts.size() - 1 - i) + tMerge.get(tMerge.size() - 1 - i);
					pCnts.set(pCnts.size() - 1 - i, nVal);
					if (nVal > pMCnt || (nVal == pMCnt && i < pMRDep)) {
						pMCnt = nVal;
						pMRDep = i;
					}
				}
			}
		}

		pCnts.add(1);
		pMRDep++;

		if (1 >= pMCnt) {
			pMCnt = 1;
			pMRDep = 0;
		}

		cnts[cV] = pCnts;
		mCnt[cV] = pMCnt;
		mRDep[cV] = pMRDep;
	}

}
