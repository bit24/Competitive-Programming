import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_408B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		inputData.nextToken();
		int numH = Integer.parseInt(inputData.nextToken());
		int numO = Integer.parseInt(inputData.nextToken());

		boolean[] holed = new boolean[1_000_001];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numH; i++) {
			holed[Integer.parseInt(inputData.nextToken())] = true;
		}
		
		int cLoc = 1;
		if(holed[cLoc]){
			System.out.println(cLoc);
			return;
		}
		
		for(int i = 0; i < numO; i++){
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			if(cLoc == a){
				cLoc = b;
			}
			else if(cLoc == b){
				cLoc = a;
			}
			if(holed[cLoc]){
				System.out.println(cLoc);
				return;
			}
		}
		System.out.println(cLoc);
	}

}
