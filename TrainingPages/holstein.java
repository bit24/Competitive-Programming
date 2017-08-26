/*
ID: eric.ca1
LANG: JAVA
TASK: holstein
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class holstein {

	public static holstein helper = new holstein();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(
				new FileReader("holstein.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("holstein.out")));

		// number of types of vitamins
		int numVitamins = Integer.parseInt(reader.readLine());

		// minimum vitaminRequirements
		String[] stringInput = reader.readLine().split(" ");
		int[] vitaminAmounts = new int[numVitamins];
		for (int i = 0; i < numVitamins; i++) {
			vitaminAmounts[i] = Integer.parseInt(stringInput[i]);
		}

		// number of available feeds
		int numFeeds = Integer.parseInt(reader.readLine());

		// feedContents
		int[][] feedsContent = new int[numFeeds][];
		for (int feedNumber = 0; feedNumber < numFeeds; feedNumber++) {
			stringInput = reader.readLine().split(" ");

			int[] vitaminContent = new int[numVitamins];

			int currentIndex = 0;
			for (String currentString : stringInput) {
				if (currentString.equals("")) {
					continue;
				}
				vitaminContent[currentIndex++] = Integer
						.parseInt(currentString);
			}

			feedsContent[feedNumber] = vitaminContent;
		}
		reader.close();

		informationPasser finalInformation = recursiveCalculator(
				vitaminAmounts, 0, numFeeds - 1, feedsContent, numVitamins);

		String answerText = "" + finalInformation.feeds;
		for (int i : finalInformation.elements) {
			answerText = answerText + " " + (i+1);
		}
		printer.println(answerText);
		printer.close();
	}

	public static informationPasser recursiveCalculator(
			int[] vitaminContentRequired, int currentElement, int endElement,
			int[][] feedsContent, int numVitamins) {

		if (isAllZero(vitaminContentRequired)) {
			informationPasser newInformationPasser = helper.new informationPasser();
			newInformationPasser.elements = new int[0];
			newInformationPasser.feeds = 0;
			newInformationPasser.success = true;
			return newInformationPasser;
		}

		if (currentElement == endElement) {
			int[] secondaryVitaminContentRequired = new int[vitaminContentRequired.length];

			for (int vitaminNumber = 0; vitaminNumber < numVitamins; vitaminNumber++) {
				secondaryVitaminContentRequired[vitaminNumber] = vitaminContentRequired[vitaminNumber]
						- feedsContent[currentElement][vitaminNumber];

				if (secondaryVitaminContentRequired[vitaminNumber] > 0) {
					informationPasser failure = helper.new informationPasser();
					failure.success = false;
					return failure;
				}
			}
			informationPasser newInformationPasser = helper.new informationPasser();
			newInformationPasser.elements = new int[] { currentElement };
			newInformationPasser.feeds = 1;
			newInformationPasser.success = true;
			return newInformationPasser;
		}

		// possibility1
		informationPasser possibility1 = null;

		int[] secondaryVitaminContentRequired = new int[vitaminContentRequired.length];

		for (int vitaminNumber = 0; vitaminNumber < numVitamins; vitaminNumber++) {
			secondaryVitaminContentRequired[vitaminNumber] = vitaminContentRequired[vitaminNumber]
					- feedsContent[currentElement][vitaminNumber];

			if (secondaryVitaminContentRequired[vitaminNumber] <= 0) {
				secondaryVitaminContentRequired[vitaminNumber] = 0;
			}
		}

		possibility1 = recursiveCalculator(secondaryVitaminContentRequired,
				currentElement + 1, endElement, feedsContent, numVitamins);

		// possibility2
		informationPasser possibility2 = recursiveCalculator(
				vitaminContentRequired, currentElement + 1, endElement,
				feedsContent, numVitamins);

		// evaluation statements

		if (possibility2.success == false && possibility1.success == false) {
			informationPasser failure = helper.new informationPasser();
			failure.success = false;
			return failure;
		}

		if (possibility2.success == false && possibility1.success == true) {
			// possibility2 is no, but possibility1 is yes
			informationPasser newInformationPasser = helper.new informationPasser();
			int[] elements = new int[possibility1.elements.length + 1];
			elements[0] = currentElement;
			for (int i = 1; i < elements.length; i++) {
				elements[i] = possibility1.elements[i - 1];
			}
			newInformationPasser.elements = elements;
			newInformationPasser.feeds = possibility1.feeds + 1;
			return newInformationPasser;
		}

		if (possibility2.success == true && possibility1.success == false) {
			// possibility2 is yes, but possibility1 is no
			informationPasser newInformationPasser = helper.new informationPasser();

			newInformationPasser.elements = possibility2.elements;
			newInformationPasser.feeds = possibility2.feeds;
			return newInformationPasser;
		}

		// both are OK
		if (possibility2.feeds < possibility1.feeds+1) {
			informationPasser newInformationPasser = helper.new informationPasser();

			newInformationPasser.elements = possibility2.elements;
			newInformationPasser.feeds = possibility2.feeds;
			return newInformationPasser;
		} else {
			informationPasser newInformationPasser = helper.new informationPasser();
			int[] elements = new int[possibility1.elements.length + 1];
			elements[0] = currentElement;
			for (int i = 1; i < elements.length; i++) {
				elements[i] = possibility1.elements[i - 1];
			}

			newInformationPasser.elements = elements;
			newInformationPasser.feeds = possibility1.feeds + 1;
			return newInformationPasser;
		}
	}

	public static boolean isAllZero(int[] array) {
		for (int i : array) {
			if (i != 0) {
				return false;
			}
		}

		return true;
	}

	class informationPasser {
		int[] elements;
		int feeds;
		boolean success = true;
	}

}
