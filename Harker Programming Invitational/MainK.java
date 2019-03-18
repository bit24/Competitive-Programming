import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class MainK {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N= Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		int[] d = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			d[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B= Integer.parseInt(st.nextToken());
			d[A] = Math.max(d[A], B);
		}
		
		int[] p = new int[N+1];
		
		for(int i = 1; i <= N; i++) {
			p[i] = p[i-1] + d[i];
		}
		
		int ans = 0;
		for(int i = M; i <= N; i++) {
			ans = Math.max(ans, p[i] - p[i-M]);
		}
		
		printer.println(ans);
		printer.close();
		
	}
}
