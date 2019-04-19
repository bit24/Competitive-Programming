import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DragonCorrect {

	public static void main(String[] args) throws IOException {
		new DragonCorrect().execute();
	}

	int dH;
	int dA;
	int kH;
	int kA;
	int bufV;
	int debV;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("dragon.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("dragon.out")));

		int numT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			dH = Integer.parseInt(inputData.nextToken());
			dA = Integer.parseInt(inputData.nextToken());
			kH = Integer.parseInt(inputData.nextToken());
			kA = Integer.parseInt(inputData.nextToken());
			bufV = Integer.parseInt(inputData.nextToken());
			debV = Integer.parseInt(inputData.nextToken());

			int min = Integer.MAX_VALUE;

			if (debV != 0) {
				for (int debC = 0; debC * debV <= kA + debV; debC++) {
					if (bufV != 0) {
						for (int bufC = 0; bufC * bufV <= kH + bufV; bufC++) {
							int cost = simulate(debC, bufC);
							if (cost < min) {
								min = cost;
							}
						}
					} else {
						int cost = simulate(debC, 0);
						if (cost < min) {
							min = cost;
						}
					}
				}
			} else {
				if (bufV != 0) {
					for (int bufC = 0; bufC * bufV <= kH + bufV; bufC++) {
						int cost = simulate(0, bufC);
						if (cost < min) {
							min = cost;
						}
					}
				} else {
					int cost = simulate(0, 0);
					if (cost < min) {
						min = cost;
					}
				}
			}
			if (min == Integer.MAX_VALUE) {
				printer.println("Case #" + cT + ": IMPOSSIBLE");
			}
			else{
				printer.println("Case #" + cT + ": " + min);
			}
		}
		reader.close();
		printer.close();
	}

	int simulate(int debC, int bufC) {
		boolean jCured = false;

		int cDH = dH;
		int cDA = dA;
		int cKH = kH;
		int cKA = kA;

		for (int cnt = 1;; cnt++) {
			if (debC > 0) {
				if (cDH - (cKA - debV) <= 0) {
					if (jCured) {
						return Integer.MAX_VALUE;
					}
					jCured = true;
					cDH = dH;
				} else {
					cKA -= debV;
					if (cKA < 0) {
						cKA = 0;
					}
					debC--;
					jCured = false;
				}
			} else if (bufC > 0) {
				if (cDH - cKA <= 0) {
					if (jCured) {
						return Integer.MAX_VALUE;
					}
					jCured = true;
					cDH = dH;
				} else {
					cDA += bufV;
					bufC--;
					jCured = false;
				}
			} else {
				if (cKH - cDA > 0 && cDH - cKA <= 0) {
					if (jCured) {
						return Integer.MAX_VALUE;
					}
					jCured = true;
					cDH = dH;
				} else {
					cKH -= cDA;
					if (cKH <= 0) {
						return cnt;
					}
					jCured = false;
				}
			}
			cDH -= cKA;
		}
	}

}
