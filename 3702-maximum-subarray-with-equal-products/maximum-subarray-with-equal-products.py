from math import gcd
from typing import List

class Solution:
    def maxLength(self, nums: List[int]) -> int:
        n = len(nums)
        best = 0
        for i in range(n):
            p, g, l = 1, 0, 1
            for j in range(i, n):
                x = nums[j]
                p *= x
                g = gcd(g, x)
                l = l * x // gcd(l, x)
                if p == l * g:
                    best = max(best, j - i + 1)
              
                if p > 25200:
                    break
        return best