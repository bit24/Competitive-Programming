import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Div2_402E {

	public static void main(String[] args) throws IOException {
		new Div2_402E().execute();
	}

	int numV;
	int numB;
	byte[][] variables;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numV = Integer.parseInt(inputData.nextToken());
		numB = Integer.parseInt(inputData.nextToken());

		variables = new byte[numV + 1][];
		variables[0] = new byte[numB];

		Arrays.fill(variables[0], VAR);

		int numE = 1;
		HashMap<String, Integer> index = new HashMap<String, Integer>();
		index.put("?", 0);

		for (int i = 0; i < numV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			String text = inputData.nextToken();
			index.put(text, numE);
			inputData.nextToken();

			if (inputData.countTokens() == 1) {
				variables[numE++] = toArray(inputData.nextToken());
				continue;
			}

			byte[] a = variables[index.get(inputData.nextToken())];
			String op = inputData.nextToken();
			byte[] b = variables[index.get(inputData.nextToken())];

			if (op.equals("AND")) {
				variables[numE++] = AND(a, b);
			} else if (op.equals("XOR")) {
				variables[numE++] = XOR(a, b);
			} else {
				variables[numE++] = OR(a, b);
			}
		}

		int[] varC = new int[numB];
		int[] nvarC = new int[numB];
		for (int i = 1; i <= numV; i++) {
			for (int j = 0; j < numB; j++) {
				if (variables[i][j] == VAR) {
					varC[j]++;
				}
				if (variables[i][j] == NVAR) {
					nvarC[j]++;
				}
			}
		}

		int[] minD = new int[numB];
		int[] maxD = new int[numB];
		for (int i = 0; i < numB; i++) {
			int pos1 = varC[i];
			int pos2 = nvarC[i];
			if (pos1 > pos2) {
				maxD[i] = 1;
				minD[i] = 0;
			} else if (pos1 == pos2) {
				maxD[i] = 0;
				minD[i] = 0;
			} else {
				maxD[i] = 0;
				minD[i] = 1;
			}
		}
		for (int i = 0; i < numB; i++) {
			printer.print(minD[i]);
		}
		printer.println();
		for (int i = 0; i < numB; i++) {
			printer.print(maxD[i]);
		}
		printer.println();
		printer.close();
	}

	byte[] toArray(String input) {
		byte[] ans = new byte[numB];
		for (int i = 0; i < numB; i++) {
			ans[i] = (byte) Character.getNumericValue(input.charAt(i));
		}
		return ans;
	}

	byte VAR = 2;
	byte NVAR = 3;

	byte[][] index = new byte[][] { { 0, VAR }, { 0, NVAR }, { 1, VAR }, { 1, NVAR }, { VAR, VAR }, { VAR, NVAR },
			{ NVAR, NVAR } };

	byte[] ANDDEF = new byte[] { 0, 0, VAR, NVAR, VAR, 0, NVAR };
	byte[] XORDEF = new byte[] { VAR, NVAR, NVAR, VAR, 0, 1, 0 };
	byte[] ORDEF = new byte[] { VAR, NVAR, 1, 1, VAR, 1, NVAR };

	byte[] AND(byte[] a, byte[] b) {
		byte[] c = new byte[numB];
		for (int i = 0; i < numB; i++) {
			byte cA = a[i];
			byte cB = b[i];
			if (cA < 2 && cB < 2) {
				c[i] = (byte) (cA & cB);
				continue;
			}
			if (cB < cA) {
				byte temp = cA;
				cA = cB;
				cB = temp;
			}
			for (int j = 0; j < 7; j++) {
				if (cA == index[j][0] && cB == index[j][1]) {
					c[i] = ANDDEF[j];
					break;
				}
			}
		}
		return c;
	}

	byte[] XOR(byte[] a, byte[] b) {
		byte[] c = new byte[numB];
		for (int i = 0; i < numB; i++) {
			byte cA = a[i];
			byte cB = b[i];
			if (cA < 2 && cB < 2) {
				c[i] = (byte) (cA ^ cB);
				continue;
			}
			if (cB < cA) {
				byte temp = cA;
				cA = cB;
				cB = temp;
			}
			for (int j = 0; j < 7; j++) {
				if (cA == index[j][0] && cB == index[j][1]) {
					c[i] = XORDEF[j];
					break;
				}
			}
		}
		return c;
	}

	byte[] OR(byte[] a, byte[] b) {
		byte[] c = new byte[numB];
		for (int i = 0; i < numB; i++) {
			byte cA = a[i];
			byte cB = b[i];
			if (cA < 2 && cB < 2) {
				c[i] = (byte) (cA | cB);
				continue;
			}
			if (cB < cA) {
				byte temp = cA;
				cA = cB;
				cB = temp;
			}
			for (int j = 0; j < 7; j++) {
				if (cA == index[j][0] && cB == index[j][1]) {
					c[i] = ORDEF[j];
					break;
				}
			}
		}
		return c;
	}

}
