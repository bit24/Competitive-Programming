class Fraction {

	long num;
	long den;

	public Fraction(long value) {
		num = value;
		den = 1;
	};

	public boolean equals(Object object) {
		simplify();
		Fraction other = (Fraction) object;

		if (other == null) {
			return false;
		}

		return num == other.num && den == other.den;
	}

	public Fraction(long numerator, long denominator) {
		this.num = numerator;
		this.den = denominator;
		simplify();
	}

	public Fraction multiply(Fraction other) {
		return new Fraction(num * other.num, den * other.den);
	}

	public Fraction multiply(int other) {
		return multiply(new Fraction(other));
	}
	////

	public Fraction divide(Fraction other) {
		return new Fraction(num * other.den, den * other.num);
	}

	public Fraction divide(int other) {
		return divide(new Fraction(other));
	}
	////

	public Fraction add(Fraction other) {
		long newNumerator = this.num * other.den + other.num * this.den;
		long newDenominator = this.den * other.den;
		return new Fraction(newNumerator, newDenominator);
	}

	public Fraction add(int other) {
		return add(new Fraction(other));
	}
	////

	public Fraction subtract(Fraction other) {
		long newNumerator = this.num * other.den - other.num * this.den;
		long newDenominator = this.den * other.den;
		return new Fraction(newNumerator, newDenominator);
	}

	public Fraction subtract(int other) {
		return subtract(new Fraction(other));
	}
	////

	public void simplify() {
		assert (den != 0);
		long gcd = gcd(num, den);
		if (gcd == 0) {
			return;
		}
		num /= gcd;
		den /= gcd;

		if (den < 0) {
			den *= -1;
			num *= -1;
		}
	}

	public double toInt() {
		return (num / den);
	}

	public long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}
}