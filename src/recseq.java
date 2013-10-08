import java.io.*;
import java.util.*;

public class recseq {
	private static BufferedReader in;
	private static PrintWriter out;
	
	private static int [] pos;
	private static char [] seq;
	private static int idx;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new FileReader (new File ("recover_the_sequence.txt")));
		out = new PrintWriter (new FileWriter ("recseq.out"));
		
		int T  = Integer.parseInt (in.readLine ());
		int test = 0;
		while (T-- > 0) {
			int N = Integer.parseInt (in.readLine ());
			seq = in.readLine().toCharArray ();
			pos = new int [N];
			for (int i = 0; i < N; i++) pos [i] = i + 1;
			idx = 0;
			merge_sort (0, N - 1);
			
			int [] arr = new int [N];
			for (int i = 0; i < N; i++)
				arr [pos [i] - 1] = i + 1;
			
			int ans = 1;
			for (int i = 0; i < N; i++) {
				ans = (31 * ans + arr [i]) % 1000003;
			}
			
			out.printf ("Case #%d: %d\n", ++test, ans);
		}
		out.close();
		System.exit(0);
	}
	
	private static void merge_sort (int begin, int end) {
		if (begin >= end) return;
		if (end - begin == 1) {
			if (seq [idx++] == '2') {
				int t = pos [begin]; 
				pos [begin] = pos [end]; 
				pos [end] = t;
			}
			return;
		}
		
		int mid = (begin + end + 1) >> 1;
			
		merge_sort (begin, mid - 1);
		merge_sort (mid, end);
		merge (begin, end);
	}
	
	private static void merge (int begin, int end) {
		int mid = (begin + end + 1) >> 1;
		
		int [] npos = new int [end - begin + 1];
		int nidx = 0;
		int idx1 = begin, idx2 = mid;
		while (idx1 < mid && idx2 <= end) {
			if (seq [idx++] == '1')
				npos [nidx++] = pos [idx1++];
			else
				npos [nidx++] = pos [idx2++];
		}
		
		while (idx1 < mid)
			npos [nidx++] = pos [idx1++];
		while (idx2 <= end)
			npos [nidx++] = pos [idx2++];
		
		for (int i = 0; i < end - begin + 1; i++)
			pos [begin + i] = npos [i];
	}
}
