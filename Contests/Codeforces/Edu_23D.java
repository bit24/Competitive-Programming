import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Edu_23D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numE = Integer.parseInt(reader.readLine());
		int[] elements = new int[numE + 2];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		for (int i = 1; i <= numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		long ans = 0;

		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		elements[0] = Integer.MAX_VALUE;
		stack.push(0);

		int[] left = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			while (elements[stack.peek()] < elements[i]) {
				stack.pop();
			}
			left[i] = stack.peek() + 1;
			stack.push(i);
		}

		stack.clear();
		elements[numE + 1] = Integer.MAX_VALUE;
		stack.push(numE + 1);

		for (int i = numE; i >= 1; i--) {
			while (elements[i] >= elements[stack.peek()]) {
				stack.pop();
			}
			int cRight = stack.peek() - 1;
			ans += (i - left[i] + 1L) * (cRight - i + 1L) * elements[i];
			stack.push(i);
		}

		stack.clear();
		elements[0] = 0;
		stack.push(0);

		for (int i = 1; i <= numE; i++) {
			while (elements[stack.peek()] > elements[i]) {
				stack.pop();
			}
			left[i] = stack.peek() + 1;
			stack.push(i);
		}

		stack.clear();
		elements[numE + 1] = 0;
		stack.push(numE + 1);

		for (int i = numE; i >= 1; i--) {
			while (elements[i] <= elements[stack.peek()]) {
				stack.pop();
			}
			int cRight = stack.peek() - 1;
			ans -= (i - left[i] + 1L) * (cRight - i + 1L) * elements[i];
			stack.push(i);
		}
		System.out.println(ans);
	}

}
