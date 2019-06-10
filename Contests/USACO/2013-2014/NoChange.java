import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NoChange {

    static int numCoins;
    static int numElements;

    static int[] coinValues;
    static int[] elements;
    static int[] prefixSums;

    public static void main(String[] args) throws IOException {
       BufferedReader reader = new BufferedReader(new FileReader("nochange.in"));
        PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("nochange.out")));
        StringTokenizer inputData = new StringTokenizer(reader.readLine());
        numCoins = Integer.parseInt(inputData.nextToken());
        numElements = Integer.parseInt(inputData.nextToken());

        coinValues = new int[numCoins];
        elements = new int[numElements];
        prefixSums = new int[numElements];

        for (int i = 0; i < numCoins; i++) {
            coinValues[i] = Integer.parseInt(reader.readLine());
        }
        for (int i = 0; i < numElements; i++) {
            elements[i] = Integer.parseInt(reader.readLine());
            prefixSums[i] = (i != 0 ? prefixSums[i - 1] + elements[i] : elements[i]);
        }

        int[] maxValue = new int[1 << numCoins];

        for (int bitSet = 0; bitSet < (1 << numCoins); bitSet++) {

            for (int currentCoin = 0; currentCoin < numCoins; currentCoin++) {
                if ((bitSet & (1 << currentCoin)) == 0) {

                    int searchValue = maxValue[bitSet] + coinValues[currentCoin];

                    int index = Arrays.binarySearch(prefixSums, searchValue);

                    if (index < 0) {
                        index = index * -1 - 2;
                    }
                    
                    if(index < 0){
                        continue;
                    }

                    maxValue[bitSet ^ (1 << currentCoin)] = Math.max(maxValue[bitSet ^ (1 << currentCoin)],
                            prefixSums[index]);
                }
            }
        }

        int answer = -1;
        for (int bitSet = 0; bitSet < (1 << numCoins); bitSet++) {
            if (maxValue[bitSet] == prefixSums[numElements - 1]) {
                int value = 0;

                for (int currentCoin = 0; currentCoin < numCoins; currentCoin++) {
                    if ((bitSet & (1 << currentCoin)) == 0) {
                        value += coinValues[currentCoin];
                    }
                }
                answer = Math.max(answer, value);
            }
        }

        printer.println(answer);
        printer.close();
    }
}
