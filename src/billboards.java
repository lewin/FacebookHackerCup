import java.io.*;
import java.util.*;

public class billboards {
	private static BufferedReader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new FileReader (new File ("billboards.txt")));
		out = new PrintWriter (new FileWriter ("billboards.out"));
		
		int T  = Integer.parseInt (in.readLine ());
		int test = 0;
		while (T-- > 0) {
			String [] st = in.readLine ().split ("[\\s]");
			int W = Integer.parseInt (st [0]), H = Integer.parseInt (st [1]);
			int [] lens = new int [st.length - 2];
			for (int i = 2; i < st.length; i++)
				lens [i - 2] = st [i].length();
			
			int lo = 0, hi = Math.max (W, H);
			while (lo < hi) {
				int mid = (lo + hi + 1) >> 1;
				if (can (mid, H, W, lens)) lo = mid;
				else hi = mid - 1;
			}
			
			out.printf ("Case #%d: %d\n", ++test, lo);
		}
		out.close();
		System.exit(0);
	}
	
	private static boolean can (int mid, int H, int W, int [] len) {
		int numrows = H / mid, currow = W;
		boolean first = true;
		int idx = 0;
		while (idx < len.length && numrows > 0) {
			if (currow - len [idx] * mid - (first ? 0 : mid) < 0) {
				currow = W;
				numrows--;
				first = true;
				continue;
			}
			if (!first) currow -= mid;
			currow -= len [idx] * mid;
			first = false;
			idx++;
		}
		return idx == len.length;
	}
}
