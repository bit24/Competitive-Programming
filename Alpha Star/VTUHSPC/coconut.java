import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.ListIterator;

public class coconut {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int space = line.indexOf(' ');
		int s = Integer.parseInt(line.substring(0, space));
		int n = Integer.parseInt(line.substring(space + 1));
		LinkedList<Point> l = new LinkedList<Point>();
		for(int i = 0; i < n; i++) {
			l.add(new Point(i, 2));
		}
		ListIterator<Point> iter = l.listIterator();
		Point current = null;
		while(l.size() > 1) {
			for(int i = 0; i < s; i++) {
				if(!iter.hasNext()) {
					iter = l.listIterator();
				}
				current = iter.next();
			}
			if(current.y == 2) {
				int index = iter.previousIndex();
				current.y = 1;
				iter.add(new Point(current.x, 1));
				iter = l.listIterator(index);
			} else if(current.y == 1) {
				current.y = 0;
			} else if(current.y == 0) {
				iter.remove();
			}
		}
		System.out.println(((int) l.get(0).x + 1));
	}

}
