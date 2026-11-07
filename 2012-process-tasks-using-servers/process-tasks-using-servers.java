
class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] 
                ? a[0] - b[0] 
                : a[1] - b[1]
        );

        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] != b[0]
                ? Long.compare(a[0], b[0])
                : Long.compare(a[1], b[1])
        );

        for (int i = 0; i < servers.length; i++) {
            available.offer(new int[]{servers[i], i});
        }

        int[] ans = new int[tasks.length];
        long time = 0;

        for (int i = 0; i < tasks.length; i++) {

            time = Math.max(time, i);

            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                long[] server = busy.poll();
                available.offer(new int[]{(int)server[1], (int)server[2]});
            }

            if (available.isEmpty()) {
                time = busy.peek()[0];

                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    long[] server = busy.poll();
                    available.offer(new int[]{(int)server[1], (int)server[2]});
                }
            }

            int[] server = available.poll();
            ans[i] = server[1];

            busy.offer(new long[]{
                time + tasks[i], server[0], server[1]
            });
        }

        return ans;
    }
}