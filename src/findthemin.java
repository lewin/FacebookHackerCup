import java.io.*;
import java.util.*;

public class findthemin {
    private static BufferedReader in;
    private static PrintWriter out;
    
    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new FileReader (new File ("find_the_mintxt.txt")));
        out = new PrintWriter (new FileWriter ("findthemin.out"));
        int T = Integer.parseInt (in.readLine());
        int t = 0;
        StringTokenizer st;
        while (T-- > 0) {
            st = new StringTokenizer (in.readLine());
            int N = Integer.parseInt (st.nextToken());
            int K = Integer.parseInt (st.nextToken());
            st = new StringTokenizer (in.readLine());
            int A = Integer.parseInt (st.nextToken());
            int B = Integer.parseInt (st.nextToken());
            int C = Integer.parseInt (st.nextToken());
            int R = Integer.parseInt (st.nextToken());
            
            int [] M = new int [K + 1];
            int [] vis = new int [K + 2];
            M [0] = A;
            if (A <= K)
                vis [A]++;
            for (int i = 1; i <= K; i++) {
                M [i] = (int)(((long)(((long)B * M [i - 1]) % R) + C) % R);
                if (M [i] <= K)
                    vis [M [i]]++;
            }
            
            PriorityQueue <Integer> pq = new PriorityQueue <Integer> ();
            for (int i = 0; i <= K; i++) {
                if (vis [i] == 0) {
                    pq.add (i);
                }
            }
            int i = 0;
            while (pq.size() > 0) {
                int c = pq.poll();
                vis [c]++;
                if (M [i] <= K) {
                    if (--vis [M [i]] == 0)
                        pq.add (M [i]);
                }
                M [i] = c;
                i++;
            }
            out.printf("Case #%d: %d\n", ++t, M [N % (K + 1)]);
        }
        out.close();
        System.exit(0);
    }
}
