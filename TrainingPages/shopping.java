
/*ID: eric.ca1
 LANG: JAVA
 TASK: shopping
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class shopping {

	public static shopping helper = new shopping();

	public static Map reference;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("shopping.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		int numSpecialOffers = Integer.parseInt(reader.readLine());
		UnprocessedOffer[] unprocessedOffers = new UnprocessedOffer[numSpecialOffers];

		for (int currentUnprocessedOffer = 0; currentUnprocessedOffer < numSpecialOffers; currentUnprocessedOffer++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numProducts = Integer.parseInt(inputData.nextToken());
			UnprocessedOffer newOffer = helper.new UnprocessedOffer(numProducts);

			for (int currentProduct = 0; currentProduct < numProducts; currentProduct++) {
				newOffer.codes[currentProduct] = Integer.parseInt(inputData.nextToken());
				newOffer.arguments[currentProduct] = Integer.parseInt(inputData.nextToken());
			}
			newOffer.price = Integer.parseInt(inputData.nextToken());
			unprocessedOffers[currentUnprocessedOffer] = newOffer;
		}

		int numProductsToPurchase = Integer.parseInt(reader.readLine());
		int[] purchaseCodes = new int[numProductsToPurchase];
		int[] purchaseAmounts = new int[numProductsToPurchase];
		int[] regularPrice = new int[numProductsToPurchase];

		for (int currentProduct = 0; currentProduct < numProductsToPurchase; currentProduct++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			purchaseCodes[currentProduct] = Integer.parseInt(inputData.nextToken());
			purchaseAmounts[currentProduct] = Integer.parseInt(inputData.nextToken());
			regularPrice[currentProduct] = Integer.parseInt(inputData.nextToken());
		}

		HashMap<Integer, Integer> codeToIndex = new HashMap<Integer, Integer>();
		for (int i = 0; i < numProductsToPurchase; i++) {
			codeToIndex.put(purchaseCodes[i], i);
		}
		reader.close();
		
		
		ArrayList<ProcessedOffer> processedOffers = new ArrayList<ProcessedOffer>();
		unprocessedOfferLoop: for (UnprocessedOffer currentOffer : unprocessedOffers) {
			ProcessedOffer newOffer = helper.new ProcessedOffer(5);
			for (int currentItem = 0; currentItem < currentOffer.codes.length; currentItem++) {
				int currentCode = currentOffer.codes[currentItem];
				int currentAmount = currentOffer.arguments[currentItem];

				if (!codeToIndex.containsKey(currentCode)) {
					continue unprocessedOfferLoop;
				} else {
					newOffer.amount[codeToIndex.get(currentCode)] += currentAmount;
				}

			}
			newOffer.price = currentOffer.price;
			processedOffers.add(newOffer);
		}

		for (int i = 0; i < numProductsToPurchase; i++) {
			ProcessedOffer newOffer = helper.new ProcessedOffer(5);
			newOffer.amount[i] = 1;
			newOffer.price = regularPrice[i];
			processedOffers.add(newOffer);
		}

		reference = helper.new Map(6);
		int[] loopRestraints = new int[5];
		for (int i = 0; i < 5; i++) {
			if (i < purchaseAmounts.length) {
				loopRestraints[i] = purchaseAmounts[i] + 1;
			} else {
				loopRestraints[i] = 1;
			}
		}
		
		reference.set(0,0,0,0,0, 0);

		for (int a = 0; a < loopRestraints[0]; a++) {
			for (int b = 0; b < loopRestraints[1]; b++) {
				for (int c = 0; c < loopRestraints[2]; c++) {
					for (int d = 0; d < loopRestraints[3]; d++) {
						for (int e = 0; e < loopRestraints[4]; e++) {
							for (ProcessedOffer currentOffer : processedOffers) {
								int[] possibleOffer = new int[] { a - currentOffer.amount[0],
										b - currentOffer.amount[1], c - currentOffer.amount[2],
										d - currentOffer.amount[3], e - currentOffer.amount[4] };
								if(isLegal(possibleOffer, loopRestraints)){
									int possibleValue = reference.get(possibleOffer);
									if(possibleValue == Integer.MAX_VALUE){
										continue;
									}
									possibleValue += currentOffer.price;
									if(possibleValue < reference.get(a, b, c, d, e)){
										reference.set(a, b, c, d, e, possibleValue);
									}
								}
							}

						}
					}
				}
			}
		}
		
		printer.println(reference.get(loopRestraints[0] - 1, loopRestraints[1] - 1, loopRestraints[2] - 1, loopRestraints[3] - 1, loopRestraints[4] - 1));
		printer.close();
		
	}

	public static boolean isLegal(int[] values, int[] upperBounds) {
		for (int i = 0; i < values.length; i++) {
			if ((values[i] < 0) || (values[i] >= upperBounds[i])) {
				return false;
			}
		}
		return true;
	}

	class Map {
		int[][][][][] data;

		public Map(int maxValue) {
			data = new int[maxValue][maxValue][maxValue][maxValue][maxValue];
			for(int[][][][] array4 : data){
				for(int[][][] array3 : array4){
					for(int[][] array2 : array3){
						for(int[] array1 : array2){
							for(int i = 0; i < array1.length; i++){
								array1[i] = Integer.MAX_VALUE;
							}
						}
					}
				}
			}
			
		}

		public int get(int a, int b, int c, int d, int e) {
			return data[a][b][c][d][e];
		}
		
		public int get(int[] data){
			return get(data[0], data[1], data[2], data[3], data[4]);
		}
		
		public void set(int a, int b, int c, int d, int e, int value) {
			data[a][b][c][d][e] = value;
		}
		
		public void set(int [] data, int value){
			set(data[0], data[1], data[2], data[3], data[4], value);
		}
		
	}

	public static void process(int currentItem) {

	}

	class ProcessedOffer {
		public int[] amount;
		int price = 0;

		public ProcessedOffer(int size) {
			amount = new int[size];
		}
	}

	class UnprocessedOffer {
		public int[] codes;
		public int[] arguments;
		public int price = 0;

		public UnprocessedOffer(int size) {
			codes = new int[size];
			arguments = new int[size];
		}
	}

}