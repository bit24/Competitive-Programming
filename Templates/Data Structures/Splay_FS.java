import java.util.Arrays;

public class Splay_FS {

	Splay_FS() {
		Arrays.fill(key, -1);
		for (int[] a : ch) {
			Arrays.fill(a, -1);
		}
		Arrays.fill(par, -1);
	}

	// virtual nodes
	int[] key = new int[1_000_000];
	int[][] ch = new int[1_000_000][2];
	int[] par = new int[1_000_000];
	int nxt = 0;

	int root = -1;

	void ro(int x) {
		int p = par[x], d = ch[p][0] == x ? 0 : 1, g = par[p];
		ch[p][d] = ch[x][d ^ 1];
		ch[x][d ^ 1] = p;

		if (g != -1) {
			ch[g][ch[g][0] == p ? 0 : 1] = x;
		}
		if(ch[p][d] != -1) {
			par[ch[p][d]] = p;
		}
		par[x] = g;
		par[p] = x;
		upd(p);
		upd(x);
	}

	// splay so that g becomes the parent of x
	void splay(int x, int g) {
		int p;

		while ((p = par[x]) != g) {
			if (par[p] != g) {
				if ((ch[p][0] == x) != (ch[par[p]][0] == p)) {
					ro(x);
				} else {
					ro(p);
				}
			}
			ro(x);
		}
		upd(x);
		if (g == -1) {
			root = x;
		}
	}

	// maintains augmented data
	void upd(int c) {

	}

	int search(int k) {
		if (root == -1) {
			return -1;
		}
		int x = root;
		while (true) {
			if (k == key[x]) {
				splay(x, -1);
				return x;
			} else {
				int d = k < key[x] ? 0 : 1;
				if (ch[x][d] == -1) {
					splay(x, -1);
					return -1;
				}
				x = ch[x][d];
			}
		}
	}

	void insert(int k) {
		if (root == -1) {
			key[nxt] = k;
			root = nxt++;
			return;
		}
		int x = search(k);
		if (x == -1) {
			key[nxt] = k;

			int d = key[root] < k ? 0 : 1; // reverse of normal because new root creation

			ch[nxt][d] = root;
			ch[nxt][d ^ 1] = ch[root][d ^ 1];
			ch[root][d ^ 1] = -1;

			par[root] = nxt;
			if (ch[nxt][d ^ 1] != -1) {
				par[ch[nxt][d ^ 1]] = nxt;
			}

			upd(root);
			upd(nxt);
			root = nxt;
			nxt++;
		}
	}

	void delete(int k) {
		int x = search(k);
		if (x != -1) {
			if (ch[x][0] == -1) {
				root = ch[x][1];
				if (ch[x][1] != -1) {
					par[ch[x][1]] = -1;
				}
				return;
			}
			x = ch[x][0];
			while (ch[x][1] != -1) {
				x = ch[x][1];
			}
			int p = par[x];
			ch[p][ch[p][0] == x ? 0 : 1] = ch[x][0];
			if(ch[x][0] != -1) {
				par[ch[x][0]] = p;
			}

			upd(p);

			ch[x][0] = ch[root][0];
			ch[x][1] = ch[root][1];
			if (ch[root][0] != -1) {
				par[ch[root][0]] = x;
			}
			if (ch[root][1] != -1) {
				par[ch[root][1]] = x;
			}
			par[x] = -1;

			upd(x);
			root = x;
		}
	}
}
