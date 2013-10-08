import java.io.*;
import java.util.*;

public class alphabetsoup {
	private static BufferedReader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new FileReader (new File ("alphabet_soup.txt")));
		out = new PrintWriter (new FileWriter ("alphabetsoup.out"));
		
		int T  = Integer.parseInt (in.readLine ());
		int test = 0;
		while (T-- > 0) {
			String [] st = in.readLine().split ("[\\s]");
			int [] counts = new int [26];
			for (String s : st)
				for (char c : s.toCharArray())
					counts [c - 'A']++;
			
			counts ['C' - 'A'] /= 2;
			String letters = "HACKERUP";
			int ans = Integer.MAX_VALUE;
			for (char c : letters.toCharArray())
				if (counts [c - 'A'] < ans)
					ans = counts [c - 'A'];
			
			
			out.printf ("Case #%d: %d\n", ++test, ans);
		}
		out.close();
		System.exit(0);
	}
}
