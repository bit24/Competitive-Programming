import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_409B {

	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		String inp = reader.readLine();
		String out = reader.readLine();
		
		StringBuilder str = new StringBuilder();
		
		for(int i = 0; i < inp.length(); i++){
			if(out.charAt(i) > inp.charAt(i)){
				System.out.println(-1);
				return;
			}
			
			str.append(out.charAt(i));
		}
		printer.println(str.toString());
		printer.close();
	}

}
