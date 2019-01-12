import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CowCliquer extends Grader {
	// Copy these exactly:
	public static void main(String args[]) throws IOException {
		new CowCliquer().run();
	}

	@Override
	public Grader newInstance() {
		return new CowCliquer();
	}

	// Implement:

	HashMap<String, Integer> nInd = new HashMap<>();

	ArrayList<Integer>[] uT = new ArrayList[200_001];

	ArrayList<Integer>[] rank = new ArrayList[200_001];

	ArrayList<Integer>[] inf = new ArrayList[200_001];

	int searchL(ArrayList<Integer> uT, int cT) {
		int low = 0;
		int high = uT.size() - 1;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (uT.get(mid) <= cT) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	int fParL(int i) {
		if (inf[i].get(inf[i].size() - 1) >= 0) {
			return fParL(inf[i].get(inf[i].size() - 1));
		} else {
			return i;
		}
	}

	int fPar(int i, int cT) {
		int lInd = searchL(uT[i], cT);
		if (inf[i].get(lInd) >= 0) {
			return fPar(inf[i].get(lInd), cT);
		} else {
			return i;
		}
	}

	@Override
	public void addFriend(String cow1, String cow2, int cT) {
		Integer i1 = nInd.get(cow1);
		Integer i2 = nInd.get(cow2);

		if (i1 == null) {
			uT[nInd.size()] = new ArrayList<>();
			uT[nInd.size()].add(-1);
			inf[nInd.size()] = new ArrayList<>();
			inf[nInd.size()].add(-1);
			i1 = nInd.size();
			nInd.put(cow1, nInd.size());
		}
		if (i2 == null) {
			uT[nInd.size()] = new ArrayList<>();
			uT[nInd.size()].add(-1);
			inf[nInd.size()] = new ArrayList<>();
			inf[nInd.size()].add(-1);
			i2 = nInd.size();
			nInd.put(cow2, nInd.size());
		}

		int p1 = fParL(i1);
		int p2 = fParL(i2);
		if(p1 == p2) {
			return;
		}
		
		int s1 = -inf[p1].get(inf[p1].size() - 1);
		int s2 = -inf[p2].get(inf[p2].size() - 1);

		uT[p1].add(cT);
		uT[p2].add(cT);
		if (s1 < s2) {
			inf[p1].add(p2);
			inf[p2].add(-(s1 + s2));
		} else {
			inf[p2].add(p1);
			inf[p1].add(-(s1 + s2));
		}
	}

	@Override
	public boolean checkFriend(String cow1, String cow2, int cT) {
		Integer i1 = nInd.get(cow1);
		Integer i2 = nInd.get(cow2);
		if (i1 == null || i2 == null) {
			return false;
		}
		return fPar(i1, cT) == fPar(i2, cT);
	}

	@Override
	public int getNumberOfFriends(String cow, int cT) {
		Integer i = nInd.get(cow);
		int p = fPar(i, cT);
		int lInd = searchL(uT[p], cT);
		return -inf[p].get(lInd);
	}
}