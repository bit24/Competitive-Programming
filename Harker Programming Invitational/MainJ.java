import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class MainJ {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		
		int N = Integer.parseInt(br.readLine());
		
		int[] d = new int[N*2];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < N*2 ; i++) {
			d[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] x = new int[N*2];
		int ans = 0;
		
		for(int i = 0; i < N*2; i++) {
			int cur = d[i];
			if(x[i] == 1) {
				continue;
			}
			x[i] = 1;
			for(int j = i+1; j < N*2; j++) {
				if(d[j] == cur) {
					x[j] = 1;
					break;
				}else if(x[j] == 0) {
					ans++;
				}
			}
		}
		
		printer.println(ans);
		printer.close();
	}
}
