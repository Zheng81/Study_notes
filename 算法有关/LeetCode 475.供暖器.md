# LeetCode 475.供暖器

冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。

现在，给出位于一条水平线上的房屋和供暖器的位置，找到可以覆盖所有房屋的最小加热半径。

所以，你的输入将会是房屋和供暖器的位置。你将输出供暖器的最小加热半径。

说明:

给出的房屋和供暖器的数目是非负数且不会超过 25000。
给出的房屋和供暖器的位置均是非负数且不会超过10^9。
只要房屋位于供暖器的半径内(包括在边缘上)，它就可以得到供暖。
所有供暖器都遵循你的半径标准，加热的半径也一样。
示例 1:

> 输入: [1,2,3],[2]
> 输出: 1
> 解释: 仅在位置2上有一个供暖器。如果我们将加热半径设为1，那么所有房屋就都能得到供暖。
> 示例 2:

> 输入: [1,2,3,4],[1,4]
> 输出: 1
> 解释: 在位置1, 4上有两个供暖器。我们需要将加热半径设为1，这样所有房屋就都能得到供暖。

------

题解：看题目可能一眼下去很晕，看不太懂，但仔细看下去还是能看懂的，题目的意思是给出一系列房子的位置(房子位置不一定是按顺序来的)和供暖器的位置，求最小的供暖器供暖半径，这个半径要能把房子都容纳进去(即供暖范围覆盖所有房子)

暴力法:两层遍历，分别找出每个房子与他最近的供暖器的距离，然后求这些距离中的最大值，这样子			就能把全部的房子给包围进去了

```java
class Solution {
    public static int findRadius(int[] houses, int[] heaters) {
        int ans = 0;
        int i = 0;
        Arrays.sort(houses);
        Arrays.sort(heaters);
        for (int house : houses) {
            while (i < heaters.length - 1 && Math.abs(heaters[i] - house) >= Math.abs(heaters[i + 1] - house))
                i++;
            ans = Math.max(Math.abs(heaters[i] - house), ans);
        }
        return ans;
    }
}
```

也可以用二分法来简化找最近供暖器

```java
class Solution {
    public static int findRadius(int[] houses, int[] heaters) {
        int ans = 0;
        int i = 0;
        Arrays.sort(houses);
        Arrays.sort(heaters);
        for (int house : houses) {
            int left = 0, right = heaters.length;
            while (left < right)
            {
                int mid = left + (right - left)/2;
                if (house > heaters[mid]) left = mid + 1;
                else right = mid;
            }
            int dist1 = (right == 0) ? Integer.MAX_VALUE : Math.abs(house - heaters[right - 1]);
            int dist2 = (right == heaters.length) ? Integer.MAX_VALUE : Math.abs(house - heaters[right]);
            ans =   Math.max(ans, Math.min(dist1, dist2));
        }
        return ans;
    }
}
```

