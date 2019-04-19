import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class Div2_389D {

	public static void main(String[] args) throws IOException {
		new Div2_389D().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numStr = Integer.parseInt(inputData.nextToken());

		String[] str = new String[numStr];
		String[] strR = new String[numStr];
		int[] value = new int[numStr];
		for (int i = 0; i < numStr; i++) {
			inputData = new StringTokenizer(reader.readLine());
			str[i] = inputData.nextToken();
			strR[i] = reverse(str[i]);
			value[i] = Integer.parseInt(inputData.nextToken());
		}

		LinkedHashMap<String, ArrayList<Integer>> values = new LinkedHashMap<String, ArrayList<Integer>>();

		for (int i = 0; i < numStr; i++) {
			if (!values.containsKey(str[i])) {
				values.put(str[i], new ArrayList<Integer>());
			}
			values.get(str[i]).add(value[i]);
		}

		for (ArrayList<Integer> list : values.values()) {
			Collections.sort(list);
		}

		int maxBase = 0;

		int addValue = 0;

		HashSet<String> used = new HashSet<String>();
		for (int i = 0; i < numStr; i++) {
			if (used.contains(str[i])) {
				continue;
			}
			used.add(str[i]);
			used.add(strR[i]);
			if (!str[i].equals(strR[i])) {
				ArrayList<Integer> stringV = values.get(str[i]);
				ArrayList<Integer> mirrorV = values.get(strR[i]);
				if (stringV == null || mirrorV == null) {
					continue;
				}

				while ((!stringV.isEmpty()) && (!mirrorV.isEmpty())) {
					int newValue = stringV.get(stringV.size() - 1) + mirrorV.get(mirrorV.size() - 1);
					if (newValue > 0) {
						addValue += newValue;
						stringV.remove(stringV.size() - 1);
						mirrorV.remove(mirrorV.size() - 1);
					} else {
						break;
					}
				}
			} else {
				ArrayList<Integer> stringV = values.get(str[i]);
				while (stringV.size() >= 2) {
					int newValue = stringV.get(stringV.size() - 1) + stringV.get(stringV.size() - 2);
					if (newValue > 0) {
						if (stringV.get(stringV.size() - 2) < 0) {
							if (maxBase < (-1 * stringV.get(stringV.size() - 2))) {
								maxBase = (-1 * stringV.get(stringV.size() - 2));
							}
						}
						addValue += newValue;
						stringV.remove(stringV.size() - 1);
						stringV.remove(stringV.size() - 1);
					} else {
						break;
					}
				}
				if (!stringV.isEmpty()) {
					if (maxBase < stringV.get(stringV.size() - 1)) {
						maxBase = stringV.get(stringV.size() - 1);
					}
				}
			}
		}
		System.out.println(maxBase + addValue);

	}

	String reverse(String input) {
		StringBuilder str = new StringBuilder();
		for (int i = input.length() - 1; i >= 0; i--) {
			str.append(input.charAt(i));
		}
		return str.toString();
	}

}
