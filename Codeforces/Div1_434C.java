import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_434C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int fCnt = Integer.parseInt(reader.readLine());
		String[] name = new String[fCnt + 1];
		boolean[] example = new boolean[fCnt + 1];

		int eCnt = 0;
		for (int i = 1; i <= fCnt; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			name[i] = inputData.nextToken();
			example[i] = inputData.nextToken().equals("1");
			if (example[i]) {
				eCnt++;
			}
		}
		reader.close();

		ArrayDeque<String> aE = new ArrayDeque<String>();
		ArrayDeque<String> aR = new ArrayDeque<String>();

		ArrayDeque<Integer> uaE = new ArrayDeque<Integer>();
		ArrayDeque<Integer> uaR = new ArrayDeque<Integer>();

		ArrayDeque<Integer> nE = new ArrayDeque<Integer>();
		ArrayDeque<Integer> nR = new ArrayDeque<Integer>();

		boolean[] used = new boolean[1000_000];

		boolean[] taken = new boolean[fCnt + 1];
		for (int i = 1; i <= fCnt; i++) {
			boolean iName = true;
			for (int j = 0; j < name[i].length(); j++) {
				if (!Character.isDigit(name[i].charAt(j))) {
					iName = false;
					break;
				}
			}
			if (name[i].charAt(0) == '0') {
				iName = false;
			}
			if (!iName) {
				if (example[i]) {
					nE.add(i);
				} else {
					nR.add(i);
				}
				continue;
			}
			int cName = Integer.parseInt(name[i]);
			used[cName] = true;
			if (cName <= fCnt) {
				taken[cName] = true;
				if (cName <= eCnt) {
					if (!example[i]) {
						uaE.add(i);
					}
				} else {
					if (example[i]) {
						uaR.add(i);
					}
				}
			} else {
				if (example[i]) {
					nE.add(i);
				} else {
					nR.add(i);
				}
			}
		}

		for (int i = 1; i <= eCnt; i++) {
			if (!taken[i]) {
				aE.add(Integer.toString(i));
			}
		}

		for (int i = eCnt + 1; i <= fCnt; i++) {
			if (!taken[i]) {
				aR.add(Integer.toString(i));
			}
		}

		int nFile = 0;
		while (used[nFile]) {
			nFile++;
		}

		ArrayList<String> ans = new ArrayList<String>();
		while (true) {
			if (!uaE.isEmpty() && !aR.isEmpty()) {
				ans.add("move " + name[uaE.peek()] + " " + aR.remove());
				aE.add(name[uaE.remove()]);
			} else if (!uaR.isEmpty() && !aE.isEmpty()) {
				ans.add("move " + name[uaR.peek()] + " " + aE.remove());
				aR.add(name[uaR.remove()]);
			} else if (!aE.isEmpty() && !nE.isEmpty()) {
				ans.add("move " + name[nE.remove()] + " " + aE.remove());
			} else if (!aR.isEmpty() && !nR.isEmpty()) {
				ans.add("move " + name[nR.remove()] + " " + aR.remove());
			} else if (!uaE.isEmpty()) {
				ans.add("move " + name[uaE.peek()] + " " + nFile);
				aE.add(name[uaE.peek()]);
				nR.add(uaE.peek());
				name[uaE.remove()] = Integer.toString(nFile++);
				while (used[nFile]) {
					nFile++;
				}
			} else if (!uaR.isEmpty()) {
				ans.add("move " + name[uaR.peek()] + " " + nFile);
				aR.add(name[uaR.peek()]);
				nE.add(uaR.peek());
				name[uaR.remove()] = Integer.toString(nFile++);
				while (used[nFile]) {
					nFile++;
				}
			} else {
				break;
			}
		}

		printer.println(ans.size());
		for (String cur : ans) {
			printer.println(cur);
		}
		printer.close();
	}

}
