import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) sorted[m++] = sorted[i];
        }

        int[] rnk = new int[n], tmp = new int[n];
        int[] sa = new int[n], sa2 = new int[n];
        int[] cnt = new int[Math.max(m, n) + 1];
        for (int i = 0; i < n; i++) {
            rnk[i] = Arrays.binarySearch(sorted, 0, m, nums[i]);
            cnt[rnk[i]]++;
        }
        for (int i = 1; i < m; i++) cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--) sa[--cnt[rnk[i]]] = i;

        for (int k = 1; m < n; k <<= 1) {
            int p = 0;
            for (int i = Math.max(0, n - k); i < n; i++) sa2[p++] = i;
            for (int j = 0; j < n; j++) if (sa[j] >= k) sa2[p++] = sa[j] - k;

            Arrays.fill(cnt, 0, m, 0);
            for (int i = 0; i < n; i++) cnt[rnk[i]]++;
            for (int i = 1; i < m; i++) cnt[i] += cnt[i - 1];
            for (int j = n - 1; j >= 0; j--) sa[--cnt[rnk[sa2[j]]]] = sa2[j];

            tmp[sa[0]] = 0;
            int classes = 1;
            for (int j = 1; j < n; j++) {
                int a = sa[j - 1], b = sa[j];
                int ra = a + k < n ? rnk[a + k] : -1;
                int rb = b + k < n ? rnk[b + k] : -1;
                boolean same = rnk[a] == rnk[b] && ra == rb;
                tmp[b] = same ? classes - 1 : classes++;
            }
            int[] t = rnk; rnk = tmp; tmp = t;
            m = classes;
        }

        // Kasai: lcp[j] = LCP of suffixes sa[j-1] and sa[j]
        for (int j = 0; j < n; j++) rnk[sa[j]] = j;
        int[] lcp = new int[n + 1];
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (rnk[i] > 0) {
                int j = sa[rnk[i] - 1];
                while (i + h < n && j + h < n && nums[i + h] == nums[j + h]) h++;
                lcp[rnk[i]] = h;
                if (h > 0) h--;
            } else {
                h = 0;
            }
        }

        int ans = n;
        for (int j = 0; j < n; j++) {
            int need = Math.max(lcp[j], lcp[j + 1]) + 1;
            if (need <= n - sa[j]) ans = Math.min(ans, need);
        }
        return ans;
    }
}