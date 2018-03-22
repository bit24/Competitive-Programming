import java.util.Random;

public class SkipList {

	static Random rng = new Random();

	static int mH = 0;

	static int mask = ~0 << mH;

	static int getRH() {
		return Integer.numberOfTrailingZeros(rng.nextInt() | mask);
	}

}
