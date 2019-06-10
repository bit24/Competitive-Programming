import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class First {

	public static void main(String[] args) throws IOException {
		new First().execute();
	}

	ArrayList<Integer>[] aList;
	boolean[] visited;
	boolean[] cStack;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("first.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("first.out")));
		int numS = Integer.parseInt(reader.readLine());
		Node trie = new Node();

		String[] strs = new String[numS];

		for (int i = 0; i < numS; i++) {
			String cStr = strs[i] = reader.readLine();
			Node cur = trie;
			for (int j = 0; j < cStr.length(); j++) {
				if (cur.nxt[cStr.charAt(j) - 'a'] == null) {
					cur.nxt[cStr.charAt(j) - 'a'] = new Node();
				}
				cur = cur.nxt[cStr.charAt(j) - 'a'];
			}
			cur.end = true;
		}
		reader.close();

		aList = new ArrayList[26];
		for (int i = 0; i < 26; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		ArrayList<String> ans = new ArrayList<String>();
		strLoop:
		for (String cStr : strs) {
			for (int i = 0; i < 26; i++) {
				aList[i].clear();
			}

			Node cur = trie;
			for (int i = 0; i < cStr.length(); i++) {
				if (cur.end) {
					continue strLoop;
				}
				for (int j = 0; j < 26; j++) {
					if (cStr.charAt(i) - 'a' != j && cur.nxt[j] != null) {
						aList[cStr.charAt(i) - 'a'].add(j);
					}
				}
				cur = cur.nxt[cStr.charAt(i) - 'a'];
			}

			visited = new boolean[26];
			for (int i = 0; i < 26; i++) {
				if (!visited[i]) {
					cStack = new boolean[26];
					if (dCycle(i)) {
						continue strLoop;
					}
				}
			}
			ans.add(cStr);
		}

		printer.println(ans.size());
		for (String cStr : ans) {
			printer.println(cStr);
		}
		printer.close();
	}

	boolean dCycle(int cI) {
		visited[cI] = true;
		cStack[cI] = true;
		for (int aI : aList[cI]) {
			if (cStack[aI]) {
				return true;
			}
			if (!visited[aI] && dCycle(aI)) {
				return true;
			}
		}
		cStack[cI] = false;
		return false;
	}

	class Node {
		Node[] nxt = new Node[26];
		boolean end;
	}

}
