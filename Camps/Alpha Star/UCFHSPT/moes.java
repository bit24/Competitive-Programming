import java.io.IOException;
import java.util.*;
public class moes {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		HashSet<String> h = new HashSet<String>();
		for(int i = 1; i <= N; i++)
		{
			String s = sc.next();
			if(!h.contains(s))
			{
				h.add(s);
				System.out.println("Customer #" + i + ": Welcome to Moe's!!!");
			}
			else
			{
				System.out.println("Customer #" + i + ": **continue working**");
			}
		}
	}
}
