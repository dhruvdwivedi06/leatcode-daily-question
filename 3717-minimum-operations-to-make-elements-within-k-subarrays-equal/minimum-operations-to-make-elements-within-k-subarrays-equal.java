import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;
        long[] cost = new long[m];

        
        Comparator<int[]> cmp = (a, b) -> a[0] != b[0]
                ? Integer.compare(a[0], b[0])
                : Integer.compare(a[1], b[1]);
        TreeSet<int[]> lo = new TreeSet<>(cmp);   
        TreeSet<int[]> hi = new TreeSet<>(cmp);   
        long loSum = 0, hiSum = 0;

        for (int i = 0; i < n; i++) {
            int[] e = new int[]{nums[i], i};
            if (lo.isEmpty() || cmp.compare(e, lo.last()) <= 0) {
                lo.add(e);
                loSum += nums[i];
            } else {
                hi.add(e);
                hiSum += nums[i];
            }

            if (i >= x) {
                int[] r = new int[]{nums[i - x], i - x};
                if (lo.remove(r)) {
                    loSum -= r[0];
                } else {
                    hi.remove(r);
                    hiSum -= r[0];
                }
            }

            while (lo.size() > hi.size() + 1) {
                int[] t = lo.pollLast();
                loSum -= t[0];
                hi.add(t);
                hiSum += t[0];
            }
            while (lo.size() < hi.size()) {
                int[] t = hi.pollFirst();
                hiSum -= t[0];
                lo.add(t);
                loSum += t[0];
            }

            if (i >= x - 1) {
                long med = lo.last()[0];
                cost[i - x + 1] = (hiSum - med * hi.size()) + (med * lo.size() - loSum);
            }
        }

        
        long INF = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1];
        for (int j = 1; j <= k; j++) {
            long[] cur = new long[n + 1];
            Arrays.fill(cur, INF);
            for (int i = j * x; i <= n; i++) {
                cur[i] = Math.min(cur[i - 1], prev[i - x] + cost[i - x]);
            }
            prev = cur;
        }
        return prev[n];
    }
}