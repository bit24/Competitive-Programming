import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_474D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nQ = Integer.parseInt(reader.readLine());

		long[] delta = new long[75];

		while (nQ-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int type = Integer.parseInt(inputData.nextToken());
			long target = Long.parseLong(inputData.nextToken());
			int tLevel = fLevel(target);
			long tLSize = fSize(tLevel);

			if (type != 3) {
				long shift = Long.parseLong(inputData.nextToken());
				delta[tLevel] = (delta[tLevel] - shift % tLSize + tLSize) % tLSize;

				if (type == 1) {
					int lLevel = tLevel + 1;
					long lLSize = tLSize * 2;
					delta[lLevel] = (delta[lLevel] + 2 * shift) % lLSize;
				}
			} else {
				long cNode = target;

				while (cNode != 0) {
					printer.print(cNode + " ");
					int cLevel = fLevel(cNode);
					long cLSize = fSize(cLevel);
					cNode -= delta[cLevel];
					if (cNode >= 2 * cLSize) {
						cNode -= cLSize;
					}
					if (cNode < cLSize) {
						cNode += cLSize;
					}
					
					cNode /= 2;
				}

				printer.println();
			}
		}
		printer.close();
	}

	// note node 1 is at level 1
	static int fLevel(long target) {
		return 64 - Long.numberOfLeadingZeros(target);
	}

	static long fSize(long level) {
		return 1L << (level - 1);
	}
	
	static void ass(boolean inp) {
		if(!inp) {
			throw new RuntimeException();
		}
	}
}
