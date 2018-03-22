import java.util.ArrayList;

class CentroidDecomp {

	ArrayList<Integer>[] aList;

	// in centroid trees, it's important to have par pointers
	int[] parent;

	int[] vCount;

	int gSize = 0;

	boolean[] removed;

	// whenever something has no par use -1

	// prerequisite: initialize vCount and par
	// the root can be any arbitrary vertex within that "group"
	void decompose(int root, int ctParent) {
		fCount(root, -1);
		gSize = vCount[root];
		int centroid = fCentroid(root, -1);

		parent[centroid] = ctParent;

		removed[centroid] = true;

		for (int aV : aList[centroid]) {
			if (!removed[aV]) {
				decompose(aV, centroid);
			}
		}
	}

	// prerequisite: fDegree has to be run on the "group" and gSize has to be set to vCount of root
	// returns centroid
	int fCentroid(int cV, int pV) {
		dLoop:
		while (true) {
			for (int aV : aList[cV]) {
				if (!removed[aV] && aV != pV && vCount[aV] > gSize / 2) {
					pV = cV;
					cV = aV;
					continue dLoop;
				}
			}
			return cV;
		}
	}

	void fCount(int cV, int pV) {
		vCount[cV] = 1;
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fCount(aV, cV);
				vCount[cV] += vCount[aV];
			}
		}
	}
}