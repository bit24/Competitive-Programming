import java.util.TreeMap;
import java.util.TreeSet;

public class CoordinateCompression {

	static TreeMap<Integer, Integer> cMap = new TreeMap<>();
	static TreeMap<Integer, Integer> rMap = new TreeMap<>();

	static void compress(int[] inputs) {
		TreeSet<Integer> keys = new TreeSet<>();
		for (int i = 0; i < inputs.length; i++) {
			keys.add(inputs[i]);
		}
		int nInd = 0;
		for (int cKey : keys) {
			cMap.put(cKey, nInd);
			rMap.put(nInd++, cKey);
		}

		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = cMap.get(inputs[i]);
		}
	}
	
	static void decompress(int[] inputs) {
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = rMap.get(inputs[i]);
		}
	}
}
