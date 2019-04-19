import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_417A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		boolean[] cross = new boolean[4];
		boolean[] ped = new boolean[4];
		for (int i = 0; i < 4; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("1")) {
				cross[(i + 3) % 4] = true;
				cross[i] = true;
			}
			if (inputData.nextToken().equals("1")) {
				cross[(i + 2) % 4] = true;
				cross[i] = true;
			}
			if (inputData.nextToken().equals("1")) {
				cross[(i + 1) % 4] = true;
				cross[i] = true;
			}
			if (inputData.nextToken().equals("1")) {
				ped[i] = true;
			}
		}
		
		for(int i = 0; i < 4; i++){
			if(cross[i] && ped[i]){
				System.out.println("YES");
				return;
			}
		}
		System.out.println("NO");
	}

}
