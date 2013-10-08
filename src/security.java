import java.io.*;
import java.util.*;

public class security {
    private static BufferedReader in;
    private static PrintWriter out;
    
    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new FileReader (new File ("security.txt")));
        out = new PrintWriter (new FileWriter ("security.out"));
        
        
        int T  = Integer.parseInt (in.readLine ());
        int test = 0;
        StringTokenizer st;
        while (T-- > 0) {
            int M = Integer.parseInt (in.readLine());
            String k1 = in.readLine(), k2 = in.readLine();
            int L = k1.length(), D = L / M;
            N = 2 * (M + 1);
            int E = 2 * M * M + 4 * M;
            eadj = new int [E];
            now = new int [E];
            elast = new int [N];
            eprev = new int [E];
            flow = new int [E];
            capa = new int [E];
            level = new int [N];
            eidx = 0;
            Arrays.fill (elast, -1);
            PriorityQueue<Pair> pq;
            for (int i = 0; i < M; i++) {
                pq = new PriorityQueue <Pair>();
                for (int j = 0; j < M; j++) {
                    String p1 = k1.substring (i * D, (i + 1) * D), p2 = k2.substring (j * D, (j + 1) * D);
                    if (match (p1, p2)) 
                        pq.add (new Pair (create (p1, p2), j));
                }
                
                while (pq.size() > 0) {
                    Pair p = pq.poll();
                    add_edge (i + 1, p.idx + M + 1);
                }
            }
            
             for (int i = 0; i < M; i++) {
                add_edge (0, i + 1);
                add_edge (i + 1 + M, N - 1);
             }
             
             int ans = dinic (0, N - 1);
             out.printf ("Case #%d: ", ++test);
             if (ans != M) {
                 out.printf ("IMPOSSIBLE\n");
             } else {
                 String res = "";
                 for (int i = 0; i < M; i++) {
                     for (int e = elast [i + 1]; e != -1; e = eprev [e]) {
                         if (flow [e] == capa [e]) {
                             int j = eadj [e] - M - 1;
                             res += create(k1.substring (i * D, (i + 1) * D), k2.substring (j * D, (j + 1) * D)); 
                             break;
                         }
                     }
                 }
                 out.printf ("%s\n", res);
             }
        }
        out.close();
        System.exit(0);
    }
    
    static class Pair implements Comparable <Pair> {
        public String s;
        public int idx;
        
        public Pair (String s, int idx) {
            this.s = s;
            this.idx = idx;
        }
        
        public int compareTo (Pair other) {
            return s.compareTo (other.s);
        }
    }
    
    private static boolean match (String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if (c1 != '?' && c2 != '?' && c1 != c2)
                return false;
        }
        return true;
    }
    
    private static String create (String s1, String s2) {
        String res = "";
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if (c1 == '?' && c2 == '?') res += 'a';
            else if (c1 == '?') res += c2;
            else res += c1;
        }
        return res;
    }
    
    private static int []   flow, capa, now;
    private static int [] eadj, elast, eprev;
    private static int eidx;
    private static int INF = 1 << 29;
    private static int N;
    
    private static void add_edge (int a, int b) {
        eadj[eidx] = b;
        flow[eidx] = 0;
        capa[eidx] = 1;
        eprev[eidx] = elast[a];
        elast[a] = eidx++;
        eadj[eidx] = a;
        flow[eidx] = 1;
        capa[eidx] = 1;
        eprev[eidx] = elast[b];
        elast[b] = eidx++;
    }
    
    private static int dinic (int source, int sink) {
        int res, flow = 0;
        while (bfs (source, sink)) {
            System.arraycopy (elast, 0, now, 0, N);
            while ((res = dfs (source, INF, sink)) > 0)
                flow += res;
        }
        return flow;
    }
    
    private static int []   level;
    
    private static boolean bfs (int source, int sink) {
        Arrays.fill (level, -1);
        int front = 0, back = 0;
        int [] queue = new int [N];
        
        level[source] = 0;
        queue[back++] = source;
        
        while (front < back && level[sink] == -1) {
            int node = queue[front++];
            for (int e = elast[node]; e != -1; e = eprev[e]) {
                int to = eadj[e];
                if (level[to] == -1 && flow[e] < capa[e]) {
                    level[to] = level[node] + 1;
                    queue[back++] = to;
                }
            }
        }
        
        return level[sink] != -1;
    }
    
    private static int dfs (int cur, int curflow, int goal) {
        if (cur == goal)
            return curflow;
        
        for (int e = now[cur]; e != -1; now[cur] = e = eprev[e]) {
            if (level[eadj[e]] > level[cur] && flow[e] < capa[e]) {
                int res = dfs (eadj[e], Math.min (curflow, capa[e] - flow[e]), goal);
                if (res > 0) {
                    flow[e] += res;
                    flow[e ^ 1] -= res;
                    return res;
                }
            }
        }
        return 0;
    }
}
