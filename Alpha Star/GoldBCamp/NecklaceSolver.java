import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class NecklaceSolver {

	public static void main(String[] args) throws IOException {
		new NecklaceSolver().solve();
	}
	
	int[] text;
	int[] pattern;
	int[][] progressionOf;
	
	void solve() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String baseString = reader.readLine();
		String patternString = reader.readLine();
		reader.close();
		text = new int[baseString.length()];
		for(int i = 0; i < baseString.length(); i++){
			text[i] = baseString.charAt(i) - 'a';
		}
		pattern = new int[patternString.length()];
		for(int i = 0; i < patternString.length(); i++){
			pattern[i] = patternString.charAt(i) - 'a';
		}
		progressionOf = new int[pattern.length+1][26];
		for(int i = 1; i < pattern.length; i++){
			int previousNewLength = progressionOf[i-1][pattern[i-1]];
			progressionOf[i-1][pattern[i-1]] = i;
			for(int j = 0; j < 26; j++){
				progressionOf[i][j] = progressionOf[previousNewLength][j];
			}
		}
		progressionOf[pattern.length-1][pattern[pattern.length-1]] = pattern.length;
		
		int[] minCost = new int[text.length];
		Arrays.fill(minCost, Integer.MAX_VALUE/4);
		minCost[0] = 0;
		for(int currentLength = 0; currentLength < text.length; currentLength++){
			int[] newCost = new int[minCost.length];
			for(int i = 0; i < newCost.length; i++){
				newCost[i] = minCost[i]+1;
			}
			for(int patternProgression = 0; patternProgression < pattern.length; patternProgression++){
				int newProgression = progressionOf[patternProgression][text[currentLength]];
				if(newProgression < pattern.length){
					newCost[newProgression] = Math.min(newCost[newProgression], minCost[patternProgression]);
				}
			}
			minCost = newCost;
		}
		
		int ans = Integer.MAX_VALUE/4;
		for(int i : minCost){
			ans = Math.min(ans, i);
		}
		System.out.println(ans);
		
	}

}
