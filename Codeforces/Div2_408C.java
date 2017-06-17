import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_408C {
	static ArrayList<Integer>[] aList;
	static int[] str;
	static int ans = 0;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		str = new int[numV];
		aList = new ArrayList[numV];

		int mStr = Integer.MIN_VALUE;
		int mCnt = 0;

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numV; i++) {
			str[i] = Integer.parseInt(inputData.nextToken());
			if (str[i] > mStr) {
				mStr = str[i];
				mCnt = 1;
			} else if (str[i] == mStr) {
				mCnt++;
			}
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}
		reader.close();

		if (mCnt >= 2) {
			for (int i = 0; i < numV; i++) {
				int reachC = str[i] == mStr ? 1 : 0;
				for (int adj : aList[i]) {
					if (str[adj] == mStr) {
						reachC++;
					}
				}
				if (reachC == mCnt) {
					System.out.println(mStr + 1);
					return;
				}
			}
			System.out.println(mStr + 2);
			return;
		}
		if (mCnt == 1) {
			int mMin1Cnt = 0;
			for (int i = 0; i < numV; i++) {
				if (str[i] == mStr - 1) {
					mMin1Cnt++;
				}
			}
			for (int i = 0; i < numV; i++) {
				if (str[i] == mStr) {
					int reachC = 0;
					for (int adj : aList[i]) {
						if (str[adj] == mStr - 1) {
							reachC++;
						}
					}
					if (reachC == mMin1Cnt) {
						System.out.println(mStr);
						return;
					}
					break;
				}
			}
			System.out.println(mStr + 1);
		}
	}
}
