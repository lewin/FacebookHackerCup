import java.io.*;
import java.util.*;

public class squished {
	private static Scanner in;
	private static PrintWriter out;
	private static long mod = 4207849484l;
	
	public static void main (String [] args) throws IOException {
		in = new Scanner (new File ("squished_status.txt"));
		out = new PrintWriter (new FileWriter ("squished.out"));
		
		int T = in.nextInt();
		int test = 0;
		while (T-- > 0) {
			int M = in.nextInt();
			String s = in.next ();
			System.out.println (M + " " + s);
			
			if (M < 10) {
				boolean ok = true;
				for (char c : s.toCharArray())
					if (c - '0' > M || c - '0' == 0) {
						ok = false;
						break;
					}
				
				out.printf ("Case #%d: %d\n", ++test, ok ? 1 : 0);
				continue;
			}
			
			long [] dp = new long [3];
			Arrays.fill (dp, 1);
			
			for (int i = 0; i < s.length (); i++) {
				long cur = 0;
				int help, help2;
				if ((help = help2 = s.charAt (i) - '0') <= M && help2 != 0)
					cur = (cur + dp [2]) % mod;
				if (i - 1 >= 0 && (help += (help2 = s.charAt (i - 1) - '0') * 10) <= M && help2 != 0)
					cur = (cur + dp [1]) % mod;
				if (i - 2 >= 0 && (help += (help2 = s.charAt (i - 2) - '0') * 100) <= M && help2 != 0)
					cur = (cur + dp [0]) % mod;
				
				dp [0] = dp [1];
				dp [1] = dp [2];
				dp [2] = cur;
			}
			out.printf ("Case #%d: %d\n", ++test, dp [2]);
		}
		out.close();
		System.exit(0);
	}
}
