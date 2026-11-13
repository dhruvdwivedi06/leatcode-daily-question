import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        long ans = 0;
        long cost = 0;

        Deque<int[]> dq = new ArrayDeque<>();

        for (int i = nums.length - 1, j = nums.length - 1; i >= 0; i--) {
            int num = nums[i];
            int count = 1;

            while (!dq.isEmpty() && dq.peekLast()[0] < num) {
                int[] p = dq.pollLast();

                int nextNum = p[0];
                int nextCount = p[1];

                count += nextCount;
                cost += (long) (num - nextNum) * nextCount;
            }

            dq.offerLast(new int[]{num, count});

            while (cost > k) {
                int[] p = dq.pollFirst();

                int rightmostNum = p[0];
                int rightmostCount = p[1];

                cost -= rightmostNum - nums[j];

                if (rightmostCount > 1) {
                    dq.offerFirst(new int[]{
                        rightmostNum, rightmostCount - 1
                    });
                }

                j--;
            }

            ans += j - i + 1;
        }

        return ans;
    }
}