import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_410A {
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = reader.readLine();
		int dif = 0;
		for (int i = 0; i < str.length() / 2; i++) {
			if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
				dif++;
			}
		}
		
		if(dif == 1 || ((str.length() & 1) == 1 && dif == 0)){
			System.out.println("YES");
		}
		else{
			System.out.println("NO");
		}
	}

}
