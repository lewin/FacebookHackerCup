import java.io.*;
import java.util.*;

public class sequence_slicing {
	private static Reader in;
	private static PrintWriter out;
	private static int N, K, R;
	
	static class Item implements Comparable <Item> {
		public int mod, val, pos;
		public Item (int number, int pos) {
			this.mod = number % N;
			this.val = number / N;
			this.pos = pos;
		}
		
		public int compareTo (Item other) {
			return mod == other.mod ? val - other.val : mod - other.mod;
		}
	}
	private static Item [] list;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();//"sequence_slicing.txt");
		out = new PrintWriter (System.out, true);//new FileWriter ("sequence_slicing.out"));
		
		int T = in.nextInt();
		int test = 0;
		while (T-- > 0) {
			N = in.nextInt(); K = in.nextInt(); R = in.nextInt();
			
			int [] seq = new int [N];
			list = new Item [N];
			for (int i = 0; i < N; i++) {
				seq [i] = in.nextInt();
				list [i] = new Item (seq [i], i);
			}
			Arrays.sort (list);
			
			if (K == 1) {
				out.printf ("Case #%d: %d/%d\n", ++test, 1, 1);
				continue;
			}
			
			long total = 0;
			for (int i = 0; i < N; i++) {
				int lo = i + K - 1, hi = R;
				while (lo < hi) {
					int mid = (lo + hi) >> 1;
					if (count (i, mid) < K) lo = mid + 1;
					else hi = mid;
				}
				if (count (i, lo) < K) break;
				
				long times = (R - lo) / N;
				total += (R - lo + 1) * (times + 1);
				total -= times * (times + 1) / 2 * N;
			}
			total *= 2;
			
			long all = (long)(R + 1) * (R + 1);
			long gcd = gcd (total, all);
			total /= gcd; all /= gcd;
			if (total == 0) all = 1;
			
			out.printf ("Case #%d: %d/%d\n", ++test, total, all);
		}
		out.close();
		System.exit(0);
	}
	
	private static int count (int from, int to) {
		int count = 0;
		int prevmod = -1, prev = 0;
		for (int i = 0; i < N; i++) {
			prev = list [i].mod == prevmod ? prev : 0;
			prevmod = list [i].mod;
			
			int start = (list [i].pos >= from ? 0 : 1);
			int end = to / N + (list [i].pos <= to % N ? 1 : 0);
			start += list [i].val; end += list [i].val;
			if (end < start) continue;
			if (start < prev) start = prev;
			count += (end - start);
			prev = end;
		}
		return count;
	}
	
	private static long gcd (long x, long y) {
      for (; x != 0; x ^= y, y ^= x, x ^= y, x %= y);
      return y;
  }    
	
	static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte [] buffer;
		private int bufferPointer, bytesRead;
		
		public Reader () {
			din = new DataInputStream (System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
		
		public Reader (String file_name) throws IOException {
			din = new DataInputStream (new FileInputStream (file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
		
		public String readLine () throws IOException {
			byte [] buf = new byte[1024];
			int cnt = 0, c;
			while ((c = read ()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String (buf, 0, cnt);
		}
		
		public int nextInt () throws IOException {
			int ret = 0;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg)
				c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}
		
		public long nextLong () throws IOException {
			long ret = 0;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg)
				c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}
		
		public double nextDouble () throws IOException {
			double ret = 0, div = 1;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg)
				c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read ()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
			if (neg)
				return -ret;
			return ret;
		}
		
		private void fillBuffer () throws IOException {
			bytesRead = din.read (buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}
		
		private byte read () throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer ();
			return buffer[bufferPointer++];
		}
		
		public void close () throws IOException {
			if (din == null)
				return;
			din.close ();
		}
	}
}
