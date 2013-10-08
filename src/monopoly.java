import java.io.*;
import java.util.*;

public class monopoly {
	private static Reader in;
	private static PrintWriter out;
	private static int [] par, depth, taken;
	private static int eidx, N, D; 
	
	private static int [][] a;
	
	private static int root (int node) {
		if (par [node] == node) return node;
		return par [node] = root (par [node]);
	}
	
	private static void join (int a, int b) {
		int x = root (a), y = root (b);
		
		if (taken [x] == D && taken [y] == D)
			throw new RuntimeException();
		
		if (taken [x] == D) {
			par [x] = y;
			taken [y]++;
			if (depth [x] + 1 > depth [y])
				depth [y] = depth [x] + 1;
		} else if (taken [y] == D) {
			par [y] = x;
			taken [x]++;
			if (depth [y] + 1 > depth [x])
				depth [x] = depth [y] + 1;
		} else if (depth [x] > depth [y]) {
			par [y] = x;
			taken [x]++;
			if (depth [y] + 1 > depth [x])
				depth [x] = depth [y] + 1;
		} else {
			par [x] = y;
			taken [y]++;
			if (depth [x] + 1 > depth [y])
				depth [y] = depth [x] + 1;
		}
	}
	
	public static void main (String [] args) throws IOException {
		in = new Reader ("monopoly.txt");
		out = new PrintWriter (new FileWriter ("monopoly.out"), true);
		
		int T = in.nextInt();
		int test = 0;
		while (T-- > 0) {
			N = in.nextInt(); D = in.nextInt();

			par = new int [N];
			taken = new int [N];
			depth = new int [N];

			for (int i = 0; i < N; i++) {
				par [i] = i;
				taken [i] = 0;
				depth [i] = 1;
			}
			
			for (int i = 0; i < N - 1; i++) {
				int a = in.nextInt(), b = in.nextInt();
				join (a - 1, b - 1);
			}
			
			out.printf ("Case #%d: %d\n", ++test, depth [root (0)]);
		}
		out.close();
		System.exit(0);
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
