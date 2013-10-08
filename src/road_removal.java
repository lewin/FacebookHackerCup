import java.io.*;
import java.util.*;

public class road_removal {
	private static Reader in;
	private static PrintWriter out;
	private static int [] size, par;
	private static boolean [] important;
	private static int N, M, K;
	
	static class Edge implements Comparable <Edge> {
		public int from, to;
		public boolean has;
		
		public Edge (int from, int to) {
			this.from = from;
			this.to = to;
			has = from < K || to < K;
		}
		
		public int compareTo (Edge other) {
			if (this.has && !other.has) return 1;
			if (other.has && !this.has) return -1;
			return 0;
		}
	}
	
	private static int root (int node) {
		if (par [node] == node) return node;
		else return par [node] = root (par [node]);
	}
	
	private static boolean join (int a, int b) {
		int x = root (a), y = root (b);
		if (x == y)
			return !important [x] && !important [y];
		
		if (size [x] < size [y]) {
			int t = x; x = y; y = t;
		}
		par [y] = x;
		size [x] += size [y];
		if (important [x] || important [y])
			important [x] = important [y] = true;
		return true;
	}
	
	public static void main (String [] args) throws IOException {
		in = new Reader ("road_removal.txt");
		out = new PrintWriter (new FileWriter ("road_removal.out"));
		
		int T = in.nextInt();
		int test = 0;
		while (T-- > 0) {
			N = in.nextInt(); M = in.nextInt(); K = in.nextInt();
			
			size = new int [N];
			par = new int [N];
			important = new boolean [N];
			for (int i = 0; i < N; i++) {
				size [i] = 1;
				par [i] = i;
				important [i] = i < K;
			}
			
			Edge [] edges = new Edge [M];
			for (int i = 0; i < M; i++) {
				edges [i] = new Edge (in.nextInt(), in.nextInt());
			}
			
			Arrays.sort (edges);
			int count = 0;
			for (int i = 0; i < M; i++) {
				int a = edges [i].from, b = edges [i].to;
				if (join (a, b))
					count++;
			}
			
			out.printf ("Case #%d: %d\n", ++test, M - count);
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
