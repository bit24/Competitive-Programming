import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CS_2014E {

	public static void main(String[] args) throws IOException {
		new CS_2014E().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nC = Integer.parseInt(inputData.nextToken());
		int D = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < nC; i++) {
			inputData = new StringTokenizer(reader.readLine());
			long cX = Integer.parseInt(inputData.nextToken());
			long cY = Integer.parseInt(inputData.nextToken());
			long r = Integer.parseInt(inputData.nextToken());
			double dist = Math.hypot(cX, cY);
			long lR = D * (long) Math.ceil((dist - r) / D);
			long hR = D * (long) Math.floor((dist + r) / D);

			for (long cR = lR; cR <= hR; cR += D) {
				pIntersect(cR, r, new Point(cX, cY));
			}
		}

		if (sAngles.isEmpty()) {
			printer.println(0);
			printer.close();
			return;
		}

		int tCnt = 0;
		int cCnt = 0;

		int max = 0;
		while (tCnt < nC * 32) {
			double cAngle = sAngles.peek();
			while (sAngles.peek() <= cAngle + 0.0000001) {
				sAngles.add(sAngles.remove() + 2 * Math.PI);
				cCnt++;
				tCnt++;
			}
			while (eAngles.peek() < cAngle - 0.0000001) {
				eAngles.add(eAngles.remove() + 2 * Math.PI);
				cCnt--;
			}

			if (cCnt > max) {
				max = cCnt;
			}
		}

		printer.println(max);
		printer.close();
	}

	PriorityQueue<Double> sAngles = new PriorityQueue<>();
	PriorityQueue<Double> eAngles = new PriorityQueue<>();

	class Point {
		double x;
		double y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	void pIntersect(long r1, long r2, Point cent2) {
		double dist = Math.hypot(cent2.x, cent2.y);

		double a = cent2.x / dist;
		double b = cent2.y / dist;
		// vector <-b,a> is orthogonal

		//if (Math.abs(dist - (r1 + r2)) > 0.0000001) {
			double uMult = (r1 * r1 - r2 * r2 + dist * dist) / (2 * dist);
			double vMult = Math.sqrt(r1 * r1 - uMult * uMult);
			double sAngle = Math.atan2(uMult * b - vMult * a, uMult * a + vMult * b);
			double eAngle = Math.atan2(uMult * b + vMult * a, uMult * a - vMult * b);
			if (sAngle < 0) {
				sAngle += 2 * Math.PI;
			}
			if (eAngle < 0) {
				eAngle += 2 * Math.PI;
			}
			if (eAngle < sAngle) {
				eAngle += 2 * Math.PI;
			}

			sAngles.add(sAngle);
			eAngles.add(eAngle);

			// (uMult * a + vMult * b, uMult * b - vMult * a)
			// (uMult * a - vMult * b, uMult * b + vMult * a)
		/*} else {
			double angle = Math.atan2(b * r1, a * r1);
			if (angle < 0) {
				angle += 2 * Math.PI;
			}
			sAngles.add(angle);
			eAngles.add(angle);
			// (a * r1, b * r1);
		}*/
	}
}
