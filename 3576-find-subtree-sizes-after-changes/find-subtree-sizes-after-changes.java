import java.util.*;

class Solution {
    List<Integer>[] tree;
    List<Integer>[] path;
    int[] ans;
    String s;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.s = s;

        tree = new ArrayList[n];
        path = new ArrayList[26];
        ans = new int[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < 26; i++) {
            path[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        dfs(0, -1);

        return ans;
    }

    void dfs(int node, int par) {
        ans[node] = 1;

        int idx = s.charAt(node) - 'a';

        path[idx].add(node);

        for (int child : tree[node]) {
            dfs(child, node);
        }

        int newParent = par;

        if (path[idx].size() > 1) {
            newParent = path[idx].get(path[idx].size() - 2);
        }

        if (newParent != -1) {
            ans[newParent] += ans[node];
        }

        path[idx].remove(path[idx].size() - 1);
    }
}