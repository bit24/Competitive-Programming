import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class dominion {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		StringTokenizer st = new StringTokenizer(line);
		int money = Integer.parseInt(st.nextToken()) * 3 + Integer.parseInt(st.nextToken()) * 2 + Integer.parseInt(st.nextToken());
		if(money < 2) {
			System.out.println("Copper");
		} else {
			System.out.println(victory(money) + " or " + treasure(money));
		}
	}
	
	public static String treasure(int money) {
		if(money >= 6)
			return "Gold";
		else if(money >= 3)
			return "Silver";
		else
			return "Copper";
	}
	
	public static String victory(int money) {
		if(money >= 8)
			return "Province";
		else if(money >= 5)
			return "Duchy";
		else
			return "Estate";
	}

}
