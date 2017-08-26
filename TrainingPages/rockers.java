import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: rockers
*/
public class rockers {

	static int numSongs;
	static int numDiscs;
	static int diskCap;

	static int[][][] dp;

	static int[] songLength;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("rockers.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("rockers.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numSongs = Integer.parseInt(inputData.nextToken());
		diskCap = Integer.parseInt(inputData.nextToken());
		numDiscs = Integer.parseInt(inputData.nextToken());
		songLength = new int[numSongs + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numSongs; i++) {
			songLength[i] = Integer.parseInt(inputData.nextToken());
		}

		dp = new int[numSongs + 1][numDiscs + 1][diskCap + 1];

		for (int currentSong = 1; currentSong <= numSongs; currentSong++) {
			for (int currentDisk = 1; currentDisk <= numDiscs; currentDisk++) {
				dp[currentSong][currentDisk][0] = dp[currentSong][currentDisk - 1][diskCap];
				for (int currentTime = 1; currentTime <= diskCap; currentTime++) {
					if (currentTime >= songLength[currentSong]) {
						dp[currentSong][currentDisk][currentTime] = Math.max(
								dp[currentSong - 1][currentDisk][currentTime - songLength[currentSong]] + 1,
								dp[currentSong - 1][currentDisk][currentTime]);
					} else {
						dp[currentSong][currentDisk][currentTime] = Math.max(dp[currentSong][currentDisk - 1][diskCap],
								dp[currentSong - 1][currentDisk][currentTime]);
					}
				}
			}
		}

		reader.close();
		printer.println(dp[numSongs][numDiscs][diskCap]);
		printer.close();
	}

}
