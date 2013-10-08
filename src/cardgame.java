import java.io.*;
import java.util.*;

public class cardgame {
    private static BufferedReader in;
    private static PrintWriter out;
    
    private static long [] fact, invfact;
    private static int mod = 1000000007;
    
    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new FileReader (new File ("card_game.txt")));
        out = new PrintWriter (new FileWriter ("cardgame.out"));
        
        invfact = new long [10001];
        fact = new long [10001];
        fact [0] = 1;
        invfact [0] = 1;
        for (int i = 1; i <= 10000; i++) {
            fact [i] = (fact [i - 1] * i) % mod;
            invfact [i] = (invfact [i - 1] * mod_exp (i, mod - 2)) % mod;
        }
        
        int T  = Integer.parseInt (in.readLine ());
        int test = 0;
        StringTokenizer st;
        while (T-- > 0) {
            st = new StringTokenizer (in.readLine());
            int N = Integer.parseInt (st.nextToken());
            int K = Integer.parseInt (st.nextToken());
            
            long [] arr = new long [N];
            st = new StringTokenizer (in.readLine());
            for (int i = 0; i < N; i++)
                arr [i] = Integer.parseInt (st.nextToken());
            Arrays.sort (arr);
            
            long res = 0;
            for (int i = K - 1; i < N; i++) {
                res = (res + comb (i, K - 1) * arr [i]) % mod;
            }
            
            out.printf ("Case #%d: %d\n", ++test, res);
        }
        out.close();
        System.exit(0);
    }
    
    private static long mod_exp (long b, long e) {
        long res = 1;
        while (e > 0) {
            if ((e & 1) == 1)
                res = (res * b) % mod;
            b = (b * b) % mod;
            e >>= 1;
        }
        return res;
    }
    
    private static long comb (int n, int k) {
        return (((fact [n] * invfact [k]) % mod) * invfact [n - k]) % mod;
    }
}
