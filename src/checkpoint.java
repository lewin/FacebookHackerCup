import java.io.*;
import java.util.*;

public class checkpoint {
	private static BufferedReader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new FileReader (new File ("checkpoint.txt")));
		out = new PrintWriter (new FileWriter ("checkpoint.out"));
		
		int lim = 10000000;
		int [] sum = new int [lim + 1];
		Arrays.fill (sum, 1 << 29);
		for (int i = lim; i >= 1; i--) {
			long cur = i;
			for (int j = 1; j <= i / 2; j++) {
				cur *= (i - j + 1); cur /= j;
				if (cur > lim) break;
				sum [(int)cur] = i;
			}
		}
		
		int T  = Integer.parseInt (in.readLine ());
		int test = 0;
		while (T-- > 0) {
			int S = Integer.parseInt (in.readLine ());
			int min = Integer.MAX_VALUE;
			for (int i = 1; i * i <= S; i++) {
				if (S % i != 0) continue;
				if (sum [i] + sum [S / i] < min)
					min = sum [i] + sum [S / i];
			}
			out.printf ("Case #%d: %d\n", ++test, min);
		}
		out.close();
		System.exit(0);
	}
}
