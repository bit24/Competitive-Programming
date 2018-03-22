class Fraction {

	long num;
	long den;

	Fraction(long value) {
		num = value;
		den = 1;
	}

	public boolean equals(Object object) {
		simplify();
		Fraction other = (Fraction) object;
		other.simplify();

		return num == other.num && den == other.den;
	}

	Fraction(long num, long den) {
		this.num = num;
		this.den = den;
		simplify();
	}

	Fraction multiply(Fraction other) {
		return new Fraction(num * other.num, den * other.den);
	}

	Fraction divide(Fraction other) {
		return new Fraction(num * other.den, den * other.num);
	}

	Fraction add(Fraction other) {
		return new Fraction(num * other.den + other.num * den, den * other.den);
	}

	Fraction subtract(Fraction other) {
		return new Fraction(num * other.den - other.num * den, den * other.den);
	}

	void simplify() {
		assert (den != 0);
		long gcd = gcd(num, den);
		if (gcd == 0) {
			throw new RuntimeException();
		}
		num /= gcd;
		den /= gcd;

		if (den < 0) {
			den = -den;
			num = -num;
		}
	}

	double toInt() {
		return (num / den);
	}

	long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}
}
