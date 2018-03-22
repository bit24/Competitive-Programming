import java.io.IOException;
import java.util.*;
public class guessing {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		main:
		for(int i = 1; i <= N; i++)
		{
			int a = sc.nextInt();
			int b = sc.nextInt();
			int x = sc.nextInt();
			
			int low = a;
			int high = b;
			int numGuesses = 1;
			while(low <= high)
			{
				int mid = (int)Math.ceil((low + high)/2.0);
				if(x == mid)
				{
					if(numGuesses == 1)
					{
						System.out.println("Game #" + i + ": 1 guess");
						continue main;
					}
					System.out.println("Game #" + i + ": " + numGuesses + " guesses");
					continue main;
				}
				numGuesses++;
				if(x < mid)
				{
					high = mid-1;
				}
				else
				{
					low = mid+1;
				}
			}
		}
	}
}
