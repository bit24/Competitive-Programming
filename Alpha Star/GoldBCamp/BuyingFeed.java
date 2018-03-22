import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BuyingFeed {

	static int amountNeeded;
	static long endPoint;
	static int numStores;

	static BuyingFeed solver = new BuyingFeed();

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		amountNeeded = Integer.parseInt(inputData.nextToken());
		endPoint = Integer.parseInt(inputData.nextToken());
		numStores = Integer.parseInt(inputData.nextToken());

		Tuple3[] stores = new Tuple3[numStores];
		for (int i = 0; i < numStores; i++) {
			inputData = new StringTokenizer(reader.readLine());
			stores[i] = solver.new Tuple3(Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}
		Arrays.sort(stores);

		long[] costOf = new long[amountNeeded + 1];
		long[] previousCostOf = new long[amountNeeded + 1];
		
		Arrays.fill(previousCostOf, -1);
		previousCostOf[0] = 0;

		for (int currentStore = 0; currentStore < numStores; currentStore++) {
			long distanceToPrevious = currentStore == 0 ? 0 : stores[currentStore].x - stores[currentStore - 1].x;
			Arrays.fill(costOf, -1);

			LinkedList<Tuple2> queueDump = new LinkedList<Tuple2>();

			for (int currentAmount = 0; currentAmount <= amountNeeded; currentAmount++) {
				if (previousCostOf[currentAmount] != -1) {
					long toInsertIntoQueue = (long)(previousCostOf[currentAmount] + distanceToPrevious * currentAmount * currentAmount)
							- currentAmount * stores[currentStore].cost;
					
					while (!queueDump.isEmpty() && queueDump.peekLast().value > toInsertIntoQueue) {
						queueDump.removeLast();
					}
					queueDump.addLast(solver.new Tuple2(toInsertIntoQueue, currentAmount + stores[currentStore].amount));
				}
				
				while(!queueDump.isEmpty() && queueDump.peekFirst().removalDate < currentAmount){
					queueDump.removeFirst();
				}
				if(!queueDump.isEmpty()){
					costOf[currentAmount] = (long) queueDump.peekFirst().value + currentAmount*stores[currentStore].cost;
				}
			}
			
			previousCostOf = costOf;
			costOf = new long[amountNeeded+1];
		}
		
		long distanceToEnd = (long) amountNeeded*amountNeeded*(endPoint- stores[numStores-1].x);
		
		System.out.print(distanceToEnd + previousCostOf[amountNeeded]);

	}

	class Tuple2{
		long value;
		int removalDate;

		Tuple2() {
		}

		Tuple2(long a, int b) {
			this.value = a;
			this.removalDate = b;
		}
	}

	class Tuple3 implements Comparable<Tuple3>{
		int x;
		int amount;
		long cost;

		Tuple3() {
		}

		Tuple3(int a, int b, int c) {
			this.x = a;
			this.amount = b;
			this.cost = c;
		}

		public int compareTo(Tuple3 o) {
			return Integer.compare(x, o.x);
		}
	}

}
