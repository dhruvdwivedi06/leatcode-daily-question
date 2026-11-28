import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] cnt = new int[n + 1];
        int m = Integer.MAX_VALUE;
        for (int[] it : items) {
            cnt[it[0]]++;
            m = Math.min(m, it[1]);
        }

        // g[f] = number of items whose factor is a multiple of f
        int[] g = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            for (int x = f; x <= n; x += f) {
                g[f] += cnt[x];
            }
        }

        // encode (price, gift count) so we can sort by price
        long[] keys = new long[n];
        for (int i = 0; i < n; i++) {
            int gi = g[items[i][0]] - 1; // exclude itself
            keys[i] = ((long) items[i][1] << 17) | gi;
        }
        Arrays.sort(keys);

        long rem = budget;
        long ans = 0;
        for (long key : keys) {
            long p = key >> 17;
            long gi = key & ((1 << 17) - 1);
            if (p >= 2L * m) break;
            long k = Math.min(gi, rem / p);
            rem -= k * p;
            ans += 2 * k;
        }
        ans += rem / m;
        return (int) ans;
    }
}