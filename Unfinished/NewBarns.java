import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

static int[] ePt = new int[100_000];
static int[] nxt = new int[100_000];
static int[] lSt = new int[50_000];
static int nE = 0;

static boolean[] removed = new boolean[50_000];

static int[] cLevel = new int[50_000];
static int[] cPar = new int[50_000];
static int[] cBranch = new int[50_000];

static int[] dist = new int[50_000][17];

static int cCnt;
static int[] cnt = new int[50_000];

static int[] furthest = new int[50_000][2];
static int[] fBranch = new int[50_000][2];

static boolean[] active = new boolean[50_000];

class NewBarns {
    public static void main(String[] args) throws IOException {
        Arrays.fill(lSt, -1);
        Arrays.fill(cPar, -1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int nQ = Integer.parseInt(reader.readLine());
        boolean[] query = new boolean[nQ];
        int[] action = new int[nQ];

        for (int i = 0; i < nQ; i++) {
            StringTokenizer inputData = new StringTokenizer(reader.readLine());
            if (inputData.nextToken().equals("Q")) {
                query[i] = true;
            }
            action[i] = Integer.parseInt(inputData.nextToken()) - 1;
            if (!query[i]) {
                ePt[nE] = i;
                nxt[nE] = lSt[action[i]];
                lSt[action[i]] = nE++;

                ePt[nE] = action[i];
                nxt[nE] = lSt[i];
                lSt[i] = nE++;
            }
        }

        prep(0, 0, -1, -1);

        for (int i = 0; i < nQ; i++) {
            if (query[i]) {
                printer.println(query(action[i]);
                }
                else {
                    activate(action[i]);
                }
            }
            printer.close();
        }

        static int query(int cV) {
            int cCPar = cPar[cV];
            int sC = cV;
            int max = 0;

            while (cCPar != -1) {
                if (active[cCPar]) {
                    if (fBranch[cCPar][0] != cBranch[sC]) {
                        max = Math.max(max, dist[cV][cCentL[cCPar]] + furthest[cCPar][0])
                    } else {
                        max = Math.max(max, dist[cV][cCentL[cCPar]] + furthest[cCPar][1])
                    }
                }  
		sC = cCPar;
            	cCPar = cPar[cCPar];
            }
        }
    }

    static void activate(int cV) {
        active[cV] = true;
        int cCPar = cPar[cV];
        int sC = cV;
        while (cCPar != -1) {
            if (dist[cV][cCentL[cCPar]] > furthest[cCPar][0]) {
                furthest[cCPar][0] = dist[cV][cCentL[cCPar]];
                fBranc[cCPar][0] = cBranch[sC];
            } else if (dist[cV][cCentL[cV]] > dist[cV][1] && fBranch[cCPar][0] != cBranch[sC]) {
                furthest[cCPar][1] = dist[cV][cCentL[cCPar]];
                fBranc[cCPar][1] = cBranch[sC];
            }
            sC = cCPar;
            cCPar = cPar[cCPar];
        }
    }

    static void prep(int mV, int cCentL, int cCentP, int cCentB) {
        cCnt = cnt(mV, -1);
        int cent = fCent(mV, -1);
        cLevel[cent] = cCentL;
        cPar[cent] = cCentP;
        cBranch[cent] = cCentB;

        cDist(cent);

        removed[cent] = true;

        int cCBranch = 0;
        int eI = lSt[cent];
        while (eI != -1) {
            int aV = ePt[eI];
            if (!removed[aV]) {
                prep(aV, cCentL + 1, cV, cCBranch);
            }
            eI = nxt[eI];
            cCBranch++;
        }
    }

    static void cDist(int cV, int pV, int cCentL) {
        int eI = lSt[cV];
        while (eI != -1) {
            int aV = ePt[eI];
            if (!removed[aV] && aV != pV) {
                dist[aV][cCentL] = dist[cV][cCentL] + 1;
            }
            eI = nxt[eI];
        }
    }

    static int fCent(int cV, int pV) {
        int eI = lSt[cV];
        while (eI != -1) {
            int aV = ePt[eI];
            if (!removed[aV] && aV != pV && cnt[aV] > cCnt / 2) {
                return fCent(aV, cV);
            }
            eI = nxt[eI];
        }
        return cV;
    }

    static int cnt(int cV, int pV) {
        int eI = lSt[cV];
        int cCnt = 0;
        while (eI != -1) {
            int aV = ePt[eI];
            if (!removed[aV] && aV != pV) {
                cCnt += cnt(aV, cV);
            }
            eI = nxt[eI];
        }
        cnt[cV] = cCnt;
        return cCnt;
    }
}
