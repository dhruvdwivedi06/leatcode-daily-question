
from typing import List

class Solution:
    def maximumSaleItems(self, items: List[List[int]], budget: int) -> int:
        n = len(items)
        dp = [0] * (budget + 1)

        for i in range(n):
            factor, price = items[i]

            free = 0
            for j in range(n):
                if i != j and items[j][0] % factor == 0:
                    free += 1

            old = dp[:]

            for r in range(price):
                best = float('-inf')

                for b in range(r, budget + 1, price):
                    if b >= price:
                        x = b - price
                        best = max(best, old[x] - (x - r) // price)

                    if best != float('-inf'):
                        dp[b] = max(
                            old[b],
                            best + (b - r) // price + free
                        )

        return dp[budget]
