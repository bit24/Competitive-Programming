
class MinYQuery {

	Line[] hull;
	int hSize;

	double xInsect(Line a, Line b) {
		return (double) (b.yIncpt - a.yIncpt) / (a.slope - b.slope);
	}

	boolean relevant() {
		if (hSize <= 2) {
			return true;
		}
		return xInsect(hull[hSize - 3], hull[hSize - 2]) < xInsect(hull[hSize - 3], hull[hSize - 1]);
	}

	void add(Line newLine) {
		assert (hSize == 0 || newLine.slope < hull[hSize - 1].slope);
		hull[hSize++] = newLine;

		while (!relevant()) {
			hull[hSize - 2] = hull[hSize - 1];
			hSize--;
		}
	}

	long query(long xVal) {
		int low = 0;
		int high = hSize - 1;
		while (low != high) {
			int mid = (low + high) / 2;

			if (mid != 0 && xVal < xInsect(hull[mid - 1], hull[mid])) {
				high = mid - 1;
			} else if (mid == hSize - 1 || xVal <= xInsect(hull[mid], hull[mid + 1])) {
				Line mLine = hull[mid];
				return mLine.slope * xVal + mLine.yIncpt;
			} else {
				low = mid + 1;
			}
		}
		Line mLine = hull[low];
		return mLine.slope * xVal + mLine.yIncpt;
	}

	class Line {
		long slope;
		long yIncpt;

		Line(long slope, long yIncpt) {
			this.slope = slope;
			this.yIncpt = yIncpt;
		}
	}
}
