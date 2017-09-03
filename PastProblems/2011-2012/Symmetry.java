import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Symmetry {

	int numPoints;

	int[] x;
	int[] y;

	HashSet<Point> pointSet = new HashSet<Point>();

	ArrayList<Line> reflectionLines = new ArrayList<Line>();

	public static void main(String[] args) throws IOException {
		new Symmetry().run();
	}

	public void init() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("symmetry.in"));
		numPoints = Integer.parseInt(reader.readLine());
		x = new int[numPoints];
		y = new int[numPoints];

		for (int i = 0; i < numPoints; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
			pointSet.add(new Point(x[i], y[i]));
		}
		reader.close();
	}

	public void run() throws IOException {
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("symmetry.out")));
		init();

		findReflectionLines(0);
		findReflectionLines(1);
		findReflectionLines(2);

		int pivot = 0;
		int pivotReflection = 1;

		if (x[pivot] == x[pivotReflection]) {

			if (checkUndefinedLine(new Fraction(x[pivot]))) {
				Line reflectionLine = new Line(null, null);
				if (!reflectionLines.contains(reflectionLine)) {
					reflectionLines.add(reflectionLine);
				}
			}
		} else {
			Fraction reflectionSlope = new Fraction(y[pivot] - y[pivotReflection], x[pivot] - x[pivotReflection]);

			Fraction midpointX = new Fraction(x[pivot] + x[pivotReflection], 2);
			Fraction midpointY = new Fraction(y[pivot] + y[pivotReflection], 2);

			Fraction yIntercept = midpointY.subtract(reflectionSlope.multiply(midpointX));

			if (checkDefinedLine(reflectionSlope, yIntercept)) {
				Line reflectionLine = new Line(reflectionSlope, yIntercept);
				if (!reflectionLines.contains(reflectionLine)) {
					reflectionLines.add(reflectionLine);
				}
			}
		}
		printer.println(reflectionLines.size());
		printer.close();
	}

	public void findReflectionLines(int pivot) {
		for (int pivotReflection = 0; pivotReflection < numPoints; pivotReflection++) {

			if (x[pivot] == x[pivotReflection]) {
				Fraction reflectionSlope = new Fraction(0);
				Fraction yIntercept = new Fraction(y[pivot] + y[pivotReflection], 2);

				if (checkDefinedLine(reflectionSlope, yIntercept)) {
					Line reflectionLine = new Line(reflectionSlope, yIntercept);
					if (!reflectionLines.contains(reflectionLine)) {
						reflectionLines.add(reflectionLine);
					}
				}
			} else if (y[pivot] == y[pivotReflection]) {
				if (checkUndefinedLine(new Fraction(x[pivot] + x[pivotReflection], 2))) {

					Line reflectionLine = new Line(null, null);
					if (!reflectionLines.contains(reflectionLine)) {
						reflectionLines.add(reflectionLine);
					}
				}
			} else {
				Fraction reflectionSlope = new Fraction(y[pivot] - y[pivotReflection], x[pivot] - x[pivotReflection]);
				reflectionSlope = new Fraction(-1).divide(reflectionSlope);

				Fraction midpointX = new Fraction(x[pivot] + x[pivotReflection], 2);
				Fraction midpointY = new Fraction(y[pivot] + y[pivotReflection], 2);

				Fraction yIntercept = midpointY.subtract(reflectionSlope.multiply(midpointX));

				if (checkDefinedLine(reflectionSlope, yIntercept)) {
					Line reflectionLine = new Line(reflectionSlope, yIntercept);
					if (!reflectionLines.contains(reflectionLine)) {
						reflectionLines.add(reflectionLine);
					}
				}
			}
		}
	}

	public boolean checkUndefinedLine(Fraction xCoor) {
		for (int i = 0; i < numPoints; i++) {
			Fraction reflectedX = xCoor.add(xCoor.subtract(x[i]));

			int reflectedY = y[i];

			if (!pointSet.contains(new Point(reflectedX.toInt(), reflectedY))) {
				return false;
			}
		}
		return true;
	}

	public boolean checkDefinedLine(Fraction slope, Fraction yIntercept) {
		for (int i = 0; i < numPoints; i++) {
			Fraction intermediate = (new Fraction(x[i]).add((new Fraction(y[i]).subtract(yIntercept)).multiply(slope)))
					.divide((slope.multiply(slope).add(1)));

			Fraction reflectedX = intermediate.multiply(2).subtract(x[i]);

			Fraction reflectedY = intermediate.multiply(2).multiply(slope).subtract(new Fraction(y[i]))
					.add(yIntercept.multiply(2));

			if (!pointSet.contains(new Point(reflectedX.toInt(), reflectedY.toInt()))) {
				return false;
			}
		}
		return true;
	}

	class Line {
		Fraction slope;
		Fraction yIntercept;

		Line(Fraction a, Fraction b) {
			slope = a;
			yIntercept = b;
		}

		public boolean equals(Object o) {
			Line other = (Line) o;

			if (slope == null) {
				assert (yIntercept == null);
				if (other.slope == null) {
					assert (other.yIntercept == null);
					return true;
				}
				assert (other.yIntercept != null);
				return false;
			}
			if (other.slope == null) {
				assert (other.yIntercept == null);
				return false;
			}

			return slope.equals(other.slope) && yIntercept.equals(other.yIntercept);
		}
	}

	class Point {
		double x;
		double y;

		Point() {}

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public int hashCode() {
			return Double.valueOf(x).hashCode() ^ Double.valueOf(x).hashCode();
		}

		public boolean equals(Object object) {
			Point other = (Point) object;
			return x == other.x && y == other.y;
		}

	}

	class Fraction {

		long numerator;
		long denominator;

		public Fraction(long value) {
			numerator = value;
			denominator = 1;
		};

		public boolean equals(Object object) {
			simplify();
			Fraction other = (Fraction) object;

			if (other == null) {
				return false;
			}

			return numerator == other.numerator && denominator == other.denominator;
		}

		public Fraction(long numerator, long denominator) {
			this.numerator = numerator;
			this.denominator = denominator;
			simplify();
		}

		public Fraction multiply(Fraction other) {
			return new Fraction(numerator * other.numerator, denominator * other.denominator);
		}

		public Fraction multiply(int other) {
			return multiply(new Fraction(other));
		}

		public Fraction divide(Fraction other) {
			return new Fraction(numerator * other.denominator, denominator * other.numerator);
		}

		public Fraction divide(int other) {
			return divide(new Fraction(other));
		}

		public Fraction add(Fraction other) {
			long newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
			long newDenominator = this.denominator * other.denominator;
			return new Fraction(newNumerator, newDenominator);
		}

		public Fraction add(int other) {
			return add(new Fraction(other));
		}

		public Fraction subtract(Fraction other) {
			long newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
			long newDenominator = this.denominator * other.denominator;
			return new Fraction(newNumerator, newDenominator);
		}

		public Fraction subtract(int other) {
			return subtract(new Fraction(other));
		}

		public void simplify() {
			assert (denominator != 0);
			long gcd = gcd(numerator, denominator);
			if (gcd == 0) {
				return;
			}
			numerator /= gcd;
			denominator /= gcd;

			if (denominator < 0) {
				denominator *= -1;
				numerator *= -1;
			}
		}

		public double toInt() {
			return (numerator / denominator);
		}

		public long gcd(long a, long b) {
			if (b == 0) {
				return a;
			}
			return gcd(b, a % b);
		}
	}
}