import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 ID: eric.ca1
 LANG: JAVA
 TASK: hamming
 */

public class hamming {
	public static short[] masks = new short[] { 1, 2, 4, 8, 16, 32, 64, 128,
			256, 512, 1024, 2048 };

	public static hamming helper = new hamming();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("hamming.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("hamming.out")));

		String[] inputData = reader.readLine().split(" ");

		reader.close();

		int numCodeWords = Integer.parseInt(inputData[0]);
		int numBits = Integer.parseInt(inputData[1]);
		int minimumHammingDistance = Integer.parseInt(inputData[2]);
		int maximum = (int) Math.pow(2, numBits);

		informationHolder finalInformationHolder = hasHammingDistance(
				minimumHammingDistance, numBits, new int[] {}, numCodeWords, 0,
				maximum);
		int i = 0;
		for (i = 0; i < finalInformationHolder.contents.length; i++) {
			if (i % 10 == 0) {
				printer.print(finalInformationHolder.contents[i]);
				continue;
			}

			printer.print(" " + finalInformationHolder.contents[i]);
			
			if(i % 10 == 9){
				printer.println();
			}
		}
		if((i-1) % 10 != 9){
			printer.println();
		}
		
		printer.close();

	}

	public static int hammingDistance(int numBits, int number1, int number2) {
		int result = (int) (number1 ^ number2);
		int hammingDistance = 0;
		for (int i = 0; i < numBits; i++) {
			if ((result & masks[i]) != 0) {
				hammingDistance++;
			}
		}
		return hammingDistance;
	}

	public static long stringDistance(long number1, long number2) {
		long result = number1 ^ number2;
		long hammingDistance = 0;
		for (int i = 0; i < 32; i++) {
			if ((result & 1) != 0) {
				hammingDistance++;
			}
			result = result >> 1;
		}
		return hammingDistance;

	}

	public static informationHolder hasHammingDistance(int hammingDistance,
			int numBits, int[] numbers, int numbersLeft, int number, int maxNum) {
		if (numbersLeft == 0) {
			informationHolder newInformationHolder = helper.new informationHolder();
			newInformationHolder.contents = numbers;
			newInformationHolder.isTrue = true;
			return newInformationHolder;
		}

		iloop: for (int i = number; i < maxNum; i++) {
			for (int currentNumber : numbers) {

				if ((hammingDistance(numBits, currentNumber, i)) < hammingDistance) {
					continue iloop;
				}
			}

			int[] newArray = new int[numbers.length + 1];
			for (int j = 0; j < numbers.length; j++) {
				newArray[j] = numbers[j];
			}
			newArray[newArray.length - 1] = i;

			informationHolder newInformationHolder = hasHammingDistance(
					hammingDistance, numBits, newArray, numbersLeft - 1, i,
					maxNum);
			if (newInformationHolder.isTrue == true) {
				return newInformationHolder;
			}
		}

		return helper.new informationHolder();
	}

	class informationHolder {
		boolean isTrue = false;
		int[] contents;
	}

}
