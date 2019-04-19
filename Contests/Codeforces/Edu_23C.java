import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Edu_23C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long bound = Long.parseLong(inputData.nextToken());
		long s = Long.parseLong(inputData.nextToken());
		if(bound < s){
			System.out.println(0);
			return;
		}

		long cur = s;
		while (cur <= bound && s + digitSum(cur) > cur) {
			cur++;
		}
		System.out.println(bound - cur + 1);
	}

	static int digitSum(long inp) {
		int sum = 0;
		while (inp != 0) {
			sum += inp % 10;
			inp /= 10;
		}
		return sum;
	}

}
