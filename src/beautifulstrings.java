import java.io.*;
import java.util.*;

public class beautifulstrings {
    private static BufferedReader in;
    private static PrintWriter out;
    
    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new FileReader (new File ("beautiful_stringstxt.txt")));
        out = new PrintWriter (new FileWriter ("beautifulstrings.out"));
        int T = Integer.parseInt (in.readLine());
        int t = 0;
        while (T-- > 0) {
            String s = in.readLine();
            int [] freq = new int [26];
            for (char c : s.toCharArray()) {
                if (c >= 'A' && c <= 'Z')
                    freq [c - 'A']++;
                if (c >= 'a' && c <= 'z')
                    freq [c - 'a']++;
            }
            Arrays.sort (freq);
            
            int ans = 0;
            for (int i = 0; i < 26; i++)
                ans += (i + 1) * freq [i];
            
            out.printf ("Case #%d: %d\n", ++t, ans);
        }
        out.close();
        System.exit(0);
    }
}
