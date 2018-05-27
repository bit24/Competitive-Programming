public class SplayTreeSimple {

	// no actual nodes

	int[][] ch;
	int[] par;

	void ro(int x) {
		int p = par[x], d = ch[p][0] == x ? 0 : 1, g = par[p];
		ch[p][d] = ch[x][d ^ 1];
		ch[x][d ^ 1] = p;

		if (g != -1) {
			ch[g][ch[g][0] == p ? 0 : 1] = x;
		}
		par[ch[p][d]] = x;
		par[x] = g;
		par[p] = x;
		upd(p);
		upd(x);
	}

	void splay(int x, int g) {
		int y;

		while ((y = par[x]) != g) {
			if (par[y] != g) {
				if ((ch[y][0] == x) != (ch[par[y]][0] == y)) {
					ro(x);
				}
				ro(y);
			}
		}
		upd(x);
		if(g == 0){
			
		}

	}

	// maintains augmented data
	void upd(int c) {

	}

}
