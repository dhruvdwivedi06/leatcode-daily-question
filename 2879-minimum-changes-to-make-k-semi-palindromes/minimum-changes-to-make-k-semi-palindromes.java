
class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int best = Integer.MAX_VALUE;

                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;

                    int changes = 0;
                    int groups = len / d;

                    for (int r = 0; r < d; r++) {
                        for (int t = 0; t < groups / 2; t++) {
                            int x = i + r + t * d;
                            int y = i + r + (groups - 1 - t) * d;

                            if (s.charAt(x) != s.charAt(y)) {
                                changes++;
                            }
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[i][i + len - 1] = best;
            }
        }

        int INF = 1000000;
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int p = 1; p <= k; p++) {
                for (int j = 0; j <= i - 2; j++) {
                    if (dp[j][p - 1] == INF) continue;

                    dp[i][p] = Math.min(
                        dp[i][p],
                        dp[j][p - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
