import java.io.*;
import java.util.*;

public class auction {
	private static BufferedReader in;
	private static PrintWriter out;
	
	static class Pair implements Comparable <Pair> {
		public int a, b;
		
		public Pair (int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		public int compareTo (Pair other) {
			return a == other.a ? b - other.b : a - other.a;
		}
	}
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new InputStreamReader (System.in));//FileReader (new File ("auction.txt")));
		out = new PrintWriter (System.out);//new FileWriter ("auction.out"));
		
		System.gc();
		int T  = Integer.parseInt (in.readLine ());
		int test = 0;
		while (T-- > 0) {
			StringTokenizer st = new StringTokenizer (in.readLine ());
			long N = Long.parseLong (st.nextToken ());
			int P1 = Integer.parseInt (st.nextToken ());
			int W1 = Integer.parseInt (st.nextToken ());
			int M = Integer.parseInt (st.nextToken ());
			int K = Integer.parseInt (st.nextToken ());
			long A = Long.parseLong (st.nextToken ());
			long B = Long.parseLong (st.nextToken ());
			long C = Long.parseLong (st.nextToken ());
			long D = Long.parseLong (st.nextToken ());
			
			if (N <= 1000000) {
				Pair [] arr = new Pair [(int)N];
				arr [0] = new Pair (P1, W1);
				for (int i = 1; i < N; i++) {
					P1 = (int)((A * P1 + B) % M) + 1;
					W1 = (int)((C * W1 + D) % K) + 1;
					arr [i] = new Pair (P1, W1);
				}
				
				Arrays.sort (arr);
				int lasta = -1, lastb = -1, min = Integer.MAX_VALUE, max = 0;
				int bargain = 0, terrible = 0;
				for (int i = 0; i < N; i++) {
					if (arr [i].b < min) {
						bargain++;
						min = arr [i].b;
						lasta = arr [i].a;
					} else if (arr [i].b == min && lasta == arr [i].a) {
						bargain++;
					}
				}
				for (int i = (int)N - 1; i >= 0; i--) {
					if (arr [i].b > max) {
						terrible++;
						max = arr [i].b;
						lastb = arr [i].a;
					} else if (arr [i].b == max && lastb == arr [i].a) {
						terrible++;
					}
				}
				
				out.printf ("Case #%d: %d %d\n", ++test, terrible, bargain);
			} else {
				int [] seq1 = new int [M];
				seq1 [0] = P1;
				int oP1 = P1, idx1 = 1;
				for (int i = 1; i < Math.min (N, M); i++) {
					P1 = (int)((A * P1 + B) % M) + 1;
					if (P1 == oP1) break;
					seq1 [idx1++] = P1;
				}
				
				int [] seq2 = new int [K];
				seq1 [0] = W1;
				int oW1 = W1, idx2 = 1;
				for (int i = 1; i < Math.min (N, K); i++) {
					W1 = (int)((C * W1 + D) % M) + 1;
					if (W1 == oW1) break;
					seq1 [idx2++] = P1;
				}
				
				Pair [] pos = new Pair [idx1];
				for (int i = 0; i < idx1; i++) {
					pos [i] = new Pair (seq1 [i], i);
				}
				Arrays.sort (pos);
			}
		}
		out.close();
		System.exit(0);
	}
}
