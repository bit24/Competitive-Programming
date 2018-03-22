import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ErdosRenyi {

	static int nV = 10000; // number of vertices
	static int eE = 1_000_000; // expected number of edges

	public static void main(String[] args) throws IOException {
		Random rng = new Random();
		double p = (double) eE / nV / (nV - 1) * 2;

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("ER.out")));
		int nE = 0;
		for (int i = 0; i < nV; i++) {
			for (int j = i + 1; j < nV; j++) {
				if (rng.nextDouble() <= p) {
					printer.println(i + " " + j);
					nE++;
				}
			}
		}
		printer.close();
		System.out.println("Edges Generated: " + nE);
	}
}
