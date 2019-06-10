import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GoogolSolver {

	public static void main(String[] args) throws IOException {
		new GoogolSolver().execute();
	}

	ArrayList<String> backbone = new ArrayList<String>();
	ArrayList<String> backSupp = new ArrayList<String>();

	BufferedReader reader;
	PrintWriter printer;

	void execute() throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		backbone.add("1");
		String lV = "1";

		while (true) {
			printer.println(lV);
			printer.flush();
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			String left = inputData.nextToken();
			String right = inputData.nextToken();
			if (left.equals("0")) {
				break;
			}
			backbone.add(left);
			backSupp.add(right);
			lV = left;
		}
		
		backSupp.add("0");

		BigInteger lastSize = BigInteger.ONE;
		for (int i = backbone.size() - 2; i >= 0; i--) {
			if (!backSupp.get(i).equals("0")) {
				lastSize = lastSize.add(fwl(backSupp.get(i), lastSize)).add(ONE);
			} else {
				lastSize = lastSize.add(ONE);
			}
		}
		printer.println("Answer " + lastSize.toString());
		printer.close();
	}

	String getData(String output) throws IOException {
		printer.println(output);
		printer.flush();
		return reader.readLine();
	}

	final BigInteger ONE = BigInteger.ONE;
	final BigInteger TWO = new BigInteger("2");

	BigInteger fwl(String cV, BigInteger lsV) throws IOException {
		StringTokenizer readInput = new StringTokenizer(getData(cV));
		String lChild = readInput.nextToken();
		String rChild = readInput.nextToken();

		if (lChild.equals("0")) {
			return ONE;
		}
		if (rChild.equals("0")) {
			return TWO;
		}

		// is odd test
		if (lsV.testBit(0)) {
			BigInteger lST = lsV.subtract(ONE).shiftRight(1);
			BigInteger rST = fwl(rChild, lST);
			return lST.add(rST).add(ONE);
		} else {
			BigInteger rST = lsV.subtract(TWO).shiftRight(1);
			BigInteger lST = fwr(lChild, rST);
			return lST.add(rST).add(ONE);
		}
	}

	BigInteger fwr(String cV, BigInteger rsV) throws IOException {
		StringTokenizer readInput = new StringTokenizer(getData(cV));
		String lChild = readInput.nextToken();
		String rChild = readInput.nextToken();

		if (lChild.equals("0")) {
			return ONE;
		}
		if (rChild.equals("0")) {
			return TWO;
		}

		// is odd test
		if (rsV.testBit(0)) {
			BigInteger rST = rsV.subtract(ONE).shiftRight(1);
			BigInteger lST = fwl(lChild, rST);
			return lST.add(rST).add(ONE);
		} else {
			BigInteger lST = rsV.shiftRight(1);
			BigInteger rST = fwl(rChild, lST);
			return lST.add(rST).add(ONE);
		}
	}

}
