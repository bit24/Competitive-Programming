import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: window
*/

public class window {
	PrintWriter printer;

	public static void main(String[] args) throws IOException {
		new window().executeInstructions();
	}

	public void executeInstructions() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("window.in"));
		printer = new PrintWriter(new BufferedWriter(new FileWriter("window.out")));

		String nextLine = reader.readLine();

		while (nextLine != null) {
			parsNExecute(nextLine);
			nextLine = reader.readLine();
		}
		reader.close();
		printer.close();
	}

	public void parsNExecute(String inputString) {
		char instructID = inputString.charAt(0);

		StringTokenizer parameters = new StringTokenizer(inputString.substring(2, inputString.length() - 1), ",");
		char ID = parameters.nextToken().charAt(0);
		if (instructID == 'w') {
			create(ID, Integer.parseInt(parameters.nextToken()), Integer.parseInt(parameters.nextToken()),
					Integer.parseInt(parameters.nextToken()), Integer.parseInt(parameters.nextToken()));
		}
		if (instructID == 't') {
			toBack(ID);
		}
		if (instructID == 'b') {
			toFront(ID);
		}
		if (instructID == 'd') {
			delete(ID);
		}
		if (instructID == 's') {
			printVis(ID);
		}
	}

	Rectangle[] windows = new Rectangle[100];
	int numWindows = 0;

	public void create(char ID, int x1, int y1, int x2, int y2) {
		int left = Math.min(x1, x2);
		int right = Math.max(x1, x2);
		int top = Math.max(y1, y2);
		int bottom = Math.min(y1, y2);

		windows[numWindows++] = new Rectangle(ID, left, right, top, bottom);
	}

	public void toFront(char ID) {
		if (windows[0].ID == ID) {
			return;
		}

		Rectangle slidingTemp = windows[0];
		Rectangle swapTemp;

		int i = 0;
		for (i = 1; i < numWindows && windows[i].ID != ID; i++) {
			swapTemp = slidingTemp;
			slidingTemp = windows[i];
			windows[i] = swapTemp;
		}
		windows[0] = windows[i];
		windows[i] = slidingTemp;
	}

	public void toBack(char ID) {
		if (windows[numWindows - 1].ID == ID) {
			return;
		}

		Rectangle slidingTemp = windows[numWindows - 1];
		Rectangle swapTemp;

		int i = 0;
		for (i = numWindows - 2; i >= 0 && windows[i].ID != ID; i--) {
			swapTemp = slidingTemp;
			slidingTemp = windows[i];
			windows[i] = swapTemp;
		}
		windows[numWindows - 1] = windows[i];
		windows[i] = slidingTemp;
	}

	public void delete(char ID) {
		if (windows[numWindows - 1].ID == ID) {
			numWindows--;
			windows[numWindows] = null;
			return;
		}
		Rectangle slidingTemp = windows[numWindows - 1];
		Rectangle swapTemp;

		int i = 0;
		for (i = numWindows - 2; i >= 0 && windows[i].ID != ID; i--) {
			swapTemp = slidingTemp;
			slidingTemp = windows[i];
			windows[i] = swapTemp;
		}
		windows[i] = slidingTemp;

		numWindows--;
		windows[numWindows] = null;
	}

	public void printVis(char ID) {
		int i;

		for (i = 0; i < numWindows && windows[i].ID != ID; i++)
			;

		Rectangle cWindow = windows[i];
		double visArea = findVisArea(cWindow, i + 1);
		double totalArea = (cWindow.right - cWindow.left) * (cWindow.top - cWindow.bottom);
		DecimalFormat formatter = new DecimalFormat("0.000");
		printer.println(formatter.format(visArea / totalArea * 100));
	}

	public int findVisArea(Rectangle window, int index) {
		if (index >= numWindows) {
			return (window.right - window.left) * (window.top - window.bottom);
		}
		Rectangle obstruction = windows[index];

		if (!intersect(window, obstruction)) {
			return findVisArea(window, index + 1);
		}

		int visibleArea = 0;
		Rectangle strictlyLeft = new Rectangle(window.left, Math.min(window.right, obstruction.left),
				Math.min(window.top, obstruction.top), Math.max(window.bottom, obstruction.bottom));

		if (strictlyLeft.left < strictlyLeft.right && strictlyLeft.bottom < strictlyLeft.top) {
			visibleArea += findVisArea(strictlyLeft, index + 1);
		}

		Rectangle strictlyRight = new Rectangle(Math.max(window.left, obstruction.right), window.right,
				Math.min(window.top, obstruction.top), Math.max(window.bottom, obstruction.bottom));

		if (strictlyRight.left < strictlyRight.right && strictlyRight.bottom < strictlyRight.top) {
			visibleArea += findVisArea(strictlyRight, index + 1);
		}

		Rectangle top = new Rectangle(window.left, window.right, window.top, Math.max(window.bottom, obstruction.top));
		if (top.left < top.right && top.bottom < top.top) {
			visibleArea += findVisArea(top, index + 1);
		}

		Rectangle bottom = new Rectangle(window.left, window.right, Math.min(window.top, obstruction.bottom),
				window.bottom);

		if (bottom.left < bottom.right && bottom.bottom < bottom.top) {
			visibleArea += findVisArea(bottom, index + 1);
		}
		return visibleArea;
	}

	public boolean intersect(Rectangle rect1, Rectangle rect2) {
		if (rect1.top < rect2.bottom || rect2.top < rect1.bottom) {
			return false;
		}

		if (rect1.right < rect2.left || rect2.right < rect1.left) {
			return false;
		}
		return true;
	}

	public boolean valid(Rectangle window) {
		return window.left < window.right && window.bottom < window.top;
	}

	class Rectangle {
		char ID;
		int left;
		int right;
		int top;
		int bottom;

		Rectangle(int b, int c, int d, int e) {
			left = b;
			right = c;
			top = d;
			bottom = e;
		}

		Rectangle(char a, int b, int c, int d, int e) {
			ID = a;
			left = b;
			right = c;
			top = d;
			bottom = e;
		}
	}

}
