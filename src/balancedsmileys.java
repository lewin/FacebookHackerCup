import java.io.*;
import java.util.*;

public class balancedsmileys {
    private static BufferedReader in;
    private static PrintWriter out;
    
    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new FileReader (new File ("balanced_smileystxt.txt")));
        out = new PrintWriter (new FileWriter ("balancedsmileys.out"));
        int T = Integer.parseInt (in.readLine());
        int t = 0;
        outer : while (T-- > 0) {
            String s = in.readLine();
            boolean [] cur = new boolean [51];
            cur [0] = true;
            if (s.charAt (0) == ')') {
                out.printf ("Case #%d: %s\n", ++t, "NO");
                continue outer;
            } else if (s.charAt (0) == '(') {
                cur [1] = true;
                cur [0] = false;
            }
            boolean [] next;
            for (int i = 1; i < s.length(); i++) {
                next = new boolean [51];
                if (s.charAt (i) == '(') {
                    for (int j = 1; j <= 50; j++)
                        next [j] = cur [j - 1];
                    if (s.charAt (i - 1) == ':') {
                        for (int j = 0; j <= 50; j++)
                            next [j] |= cur [j];
                    }
                    cur = next;
                }
                if (s.charAt (i) == ')') {
                    for (int j = 0; j < 50; j++)
                        next [j] = cur [j + 1];
                    if (s.charAt (i - 1) == ':') {
                        for (int j = 0; j <= 50; j++)
                            next [j] |= cur [j];
                    }
                    cur = next;
                }
            }
            out.printf ("Case #%d: %s\n", ++t, cur [0] ? "YES" : "NO");
        }
        out.close();
        System.exit(0);
    }
}
