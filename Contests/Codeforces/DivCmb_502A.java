import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_502A {

	public static void main(String[] args) throws IOException {
		new DivCmb_502A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int nS = Integer.parseInt(reader.readLine());
		Student[] students = new Student[nS];

		for (int i = 0; i < nS; i++) {
			students[i] = new Student();
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			students[i].i = i;
			students[i].s = Integer.parseInt(inputData.nextToken()) + Integer.parseInt(inputData.nextToken())
					+ Integer.parseInt(inputData.nextToken()) + Integer.parseInt(inputData.nextToken());
		}
		Arrays.sort(students);

		for (int i = 0; i < nS; i++) {
			if (students[i].i == 0) {
				printer.println(i + 1);
				printer.close();
				return;
			}
		}
	}

	class Student implements Comparable<Student> {
		int s;
		int i;

		public int compareTo(Student o) {
			if (s != o.s) {
				return -Integer.compare(s, o.s);
			}
			return Integer.compare(i, o.i);
		}
	}
}