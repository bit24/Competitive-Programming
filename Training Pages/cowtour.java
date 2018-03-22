import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/*ID: eric.ca1
 LANG: JAVA
 TASK: cowtour
 */
public class cowtour {

	static cowtour helper = new cowtour();

	public static final double INFINITY = Double.POSITIVE_INFINITY;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowtour.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("cowtour.out")));
		int numPastures = Integer.parseInt(reader.readLine());

		coordinate[] coordinateData = new coordinate[numPastures + 1];

		for (int i = 1; i <= numPastures; i++) {
			String[] inputData = reader.readLine().split(" ");
			coordinate newCoordinate = new coordinate();
			newCoordinate.xCoor = Integer.parseInt(inputData[0]);
			newCoordinate.yCoor = Integer.parseInt(inputData[1]);
			coordinateData[i] = newCoordinate;
		}

		double[][] edges = new double[numPastures + 1][];
		for (int i = 0; i <= numPastures; i++) {
			edges[i] = new double[numPastures + 1];
		}

		for (int currentRow = 1; currentRow <= numPastures; currentRow++) {
			String inputData = reader.readLine();
			for (int currentColumn = 1; currentColumn <= numPastures; currentColumn++) {
				if (inputData.charAt(currentColumn - 1) == '1') {
					edges[currentRow][currentColumn] = pythagorean(
							Math.abs(coordinateData[currentRow].xCoor
									- coordinateData[currentColumn].xCoor),
							Math.abs(coordinateData[currentRow].yCoor
									- coordinateData[currentColumn].yCoor));
				} else {
					edges[currentRow][currentColumn] = INFINITY;
				}
			}
		}
		reader.close();

		double[][] data = new double[numPastures + 1][];

		for (int i = 0; i < data.length; i++) {
			data[i] = new double[numPastures + 1];
		}

		for (int i = 1; i <= numPastures; i++) {
			for (int j = 1; j <= numPastures; j++) {
				if (i == j) {
					data[i][j] = 0;
					continue;
				}

				if (edges[i][j] != INFINITY) {
					data[i][j] = edges[i][j];
				} else {
					data[i][j] = INFINITY;
				}
			}
		}
		double biggestResult = 0;
		for (int intermediateNodes = 1; intermediateNodes <= numPastures; intermediateNodes++) {
			for (int startNode = 1; startNode <= numPastures; startNode++) {
				for (int endNode = startNode; endNode <= numPastures; endNode++) {
					double result = Math.min(data[startNode][intermediateNodes]
							+ data[intermediateNodes][endNode],
							data[startNode][endNode]);
					data[startNode][endNode] = result;
					data[endNode][startNode] = result;
				}

			}
		}
		
		System.out.println(biggestResult);
		
		double[] farthestDistance = new double[numPastures + 1];
		for (int i = 0; i < farthestDistance.length; i++) {
			farthestDistance[i] = 0;
		}

		for (int i = 1; i <= numPastures; i++) {
			for (int j = 1; j <= numPastures; j++) {

				if (farthestDistance[i] < data[i][j] && data[i][j] != INFINITY) {
					farthestDistance[i] = data[i][j];
				}
			}
		}
		
		double farthestDistNoBridge = 0;
		for(int i = 0; i < farthestDistance.length; i++){
			farthestDistNoBridge = Math.max(farthestDistNoBridge, farthestDistance[i]);
		}

		double minValue = INFINITY;

		for (int i = 1; i <= numPastures; i++) {
			for (int j = 1; j <= numPastures; j++) {
				if (data[i][j] == INFINITY) {

					double newPosValue = pythagorean(
							Math.abs(coordinateData[i].xCoor
									- coordinateData[j].xCoor),
							Math.abs(coordinateData[i].yCoor
									- coordinateData[j].yCoor));
					newPosValue += farthestDistance[i] + farthestDistance[j];
					

					if (newPosValue < minValue) {
						minValue = newPosValue;
					}

				}
			}
		}

		DecimalFormat formatter = new DecimalFormat("#0.000000");
		printer.println(formatter.format(Math.max(minValue, farthestDistNoBridge)));
		printer.close();
	}

	static class coordinate {
		int xCoor = 0;
		int yCoor = 0;
	}

	public static double pythagorean(int A, int B) {
		return Math.sqrt(A * A + B * B);
	}

}
