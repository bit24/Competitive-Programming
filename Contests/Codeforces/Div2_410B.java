import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_410B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numS = Integer.parseInt(reader.readLine());
		String[] strs = new String[numS];

		for (int i = 0; i < numS; i++) {
			strs[i] = reader.readLine();
		}

		int mCost = Integer.MAX_VALUE;
		for (int b = 0; b < numS; b++) {
			int cost = 0;
			othLoop:
			for (int oth = 0; oth < numS; oth++) {
				if (oth == b) {
					continue;
				}
				for (int i = 0; i < strs[oth].length(); i++) {
					if (equals(strs[b], strs[oth], i)) {
						cost += i;
						continue othLoop;
					}
				}
				System.out.println(-1);
				return;
			}
			if(cost < mCost){
				mCost = cost;
			}
		}
		System.out.println(mCost);
	}

	static boolean equals(String a, String b, int off) {
		for (int aI = 0; aI < a.length(); aI++) {
			if (a.charAt(aI) != b.charAt((aI + off) % b.length())) {
				return false;
			}
		}
		return true;
	}

}
