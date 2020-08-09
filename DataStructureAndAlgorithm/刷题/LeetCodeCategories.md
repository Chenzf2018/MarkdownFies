# 贪心算法

求解**最优化问题**的算法通常需要经过一系列的步骤，在每个步骤都面临多种选择，对于许多最优问题，使用动态规划求最优解有些杀鸡用牛刀。可以使用更简单、更高效的算法。

贪心算法（greedy algorithm）**在每一步都做出当时看起来最佳的选择**。即，其是**局部最优的选择**，寄希望这样的选择能导致全局最优解。

- “贪心算法” 和 “动态规划”、“回溯搜索” 算法一样，完成一件事情，是分步决策的；
- “贪心算法” 在每一步总是做出在当前看来最好的选择。
- 贪心算法和动态规划相比，**它既不看前面（也就是说它不需要从前面的状态转移过来），也不看后面（无后效性，后面的选择不会对前面的选择有影响）**，因此贪心算法**时间复杂度**一般是**线性**的，**空间复杂度**是**常数**级别的。


## 122. **买卖股票的最佳时机II(简单)

**题目：**

给定一个数组，它的第`i`个元素是一支给定股票第`i`天的价格。

设计一个算法来计算你所能获取的最大利润。你可以**尽可能地完成更多的交易**（多次买卖一支股票）。

注意：你不能同时参与多笔交易（你**必须在再次购买前出售掉之前的股票**）。

**示例：**
```
输入: [7,1,5,3,6,4]
输出: 7
解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。

输入: [1,2,3,4,5]
输出: 4
解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     注意你不能在第1天和第2天接连购买股票，之后再将它们卖出。
     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。

输入: [7,6,4,3,1]
输出: 0
解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
```

**思路与算法：**

这道题 “贪心” 的地方在于，对于`今天的股价 - 昨天的股价`，得到的结果有3种可能：
- 正数
- 0
- 负数。
  
**<font color=red>贪心算法的决策是：只加正数</font>**。

- **股票买卖策略：**

  - **单独交易日**：设今天价格$p_1$、明天价格$p_2$，则**今天买入、明天卖出**可赚取金额$p_2 - p_1$（负值代表亏损）。
  - **<font color=red>连续上涨交易日</font>**：设此上涨交易日股票价格分别为$p_1, p_2, ... , p_n$，则**第一天买最后一天卖收益最大**，即$p_n - p_1$；**<font color=red>等价于每天都买卖</font>**，即$p_n - p_1=(p_2 - p_1)+(p_3 - p_2)+...+(p_n - p_{n-1})$。
  - **连续下降交易日**：则**不买卖收益最大**，即不会亏钱。

**代码：**
```java
class BestTimeToBuyAndSellStockII {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for(int i = 1; i< prices.length; i++){
            // 连续上涨日交易
            if(prices[i] > prices[i - 1])
                maxProfit += prices[i] - prices[i - 1];
        }

        return maxProfit;
    }
}
```

**复杂度分析：**
- 时间复杂度：$O(n)$，遍历一次。
- 空间复杂度：$O(1)$，需要常量的空间。



## 392. *判断子序列(简单)

**题目：**

给定字符串`s`和`t`，判断`s`是否为`t`的**子序列**。

你可以认为`s`和`t`中仅包含英文小写字母。字符串`t`可能会很长（长度 ~= 500,000），而`s`是个短字符串（长度 <=100）。

字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而**不改变剩余字符相对位置**形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。

**示例：**

```
s = "abc", t = "ahbgdc"
返回 true.

s = "axc", t = "ahbgdc"
返回 false.
```

**思路与算法：**
把`s`的字符依次和`t`的字符匹配，**匹配到，`s`就后移，无论是否匹配，`t`的下标都后移**，匹配完`s`，说明`s`是`t`的子序列，否则不是。

**代码：**
```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }
}

```

**后续挑战:**

如果有大量输入的`S`，称作`S1, S2, ... , Sk`其中`k >= 10亿`，你需要依次检查它们是否为 `T`的子序列。在这种情况下，你会怎样改变代码？

参考：https://leetcode-cn.com/problems/is-subsequence/solution/javati-jie-he-hou-xu-tiao-zhan-by-lil-q/


## 455. **分发饼干(简单)

**题目：**
假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
- 对**每个孩子**`i`，都有一个**胃口值**`gi`，这是能让孩子们满足胃口的饼干的最小尺寸；
- 并且**每块饼干**`j`，都有一个**尺寸**`sj`。如果`sj >= gi`，我们可以将这个饼干`j`分配给孩子`i`，这个孩子会得到满足。
  
你的目标是**尽可能满足越多数量的孩子**，并输出这个最大数值。

注意：可以假设胃口值为正；一个小朋友最多只能拥有一块饼干。


**示例：**

```
输入: [1,2,3], [1,1]
输出: 1

解释: 
你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
所以你应该输出1。


输入: [1,2], [1,2,3]
输出: 2

解释: 
你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。
你拥有的饼干数量和尺寸都足以让所有孩子满足。
所以你应该输出2.
```

**思路与算法：**

- **给一个孩子的饼干应当尽量小并且又能满足该孩子**，这样大饼干才能拿来给满足度比较大的孩子。
- 因为满足度最小的孩子最容易得到满足，所以**先满足满足度最小的孩子**。


在以上的解法中，我们只**在每次分配时饼干时选择一种看起来是当前最优的分配方法**，但无法保证这种局部最优的分配方法最后能得到全局最优解。我们假设能得到全局最优解，并使用反证法进行证明，即假设存在一种比我们使用的贪心策略更优的最优策略。如果不存在这种最优策略，表示贪心策略就是最优策略，得到的解也就是全局最优解。

证明：假设在某次选择中，贪心策略选择给当前满足度最小的孩子分配第`m`个饼干，第`m`个饼干为可以满足该孩子的最小饼干。假设存在一种最优策略，可以给该孩子分配第`n`个饼干，并且`m < n`。我们可以发现，经过这一轮分配，贪心策略分配后剩下的饼干一定有一个比最优策略来得大。因此在后续的分配中，贪心策略一定能满足更多的孩子。也就是说不存在比贪心策略更优的策略，即贪心策略就是最优策略。

<div align=center><img src=LeetCode\455.gif></div>
<div align=center><img src=LeetCode\455.jpg></div>

**代码：**
```java
class AssignCookies {
    public int findContentChildren(int[] children, int[] biscuits) {
        if(children == null || biscuits == null)
            return 0;
        
        Arrays.sort(children); Arrays.sort(biscuits);

        int child_i = 0, biscuit_j = 0;
        while(child_i < children.length && biscuit_j < biscuits.length){
            if(children[child_i] <= biscuits[biscuit_j])
                child_i++;
            biscuit_j++;
        }

        return child_i;
    }
}
```


## 860. 柠檬水找零(简单)

在柠檬水摊上，每一杯柠檬水的售价为 5 美元。

顾客排队购买你的产品，（**按账单 bills 支付的顺序**）一次购买一杯。

每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。

注意，一开始你手头没有任何零钱。

如果你能**给每位顾客正确找零**，返回 true ，否则返回 false 。

```
示例 1：

输入：[5,5,5,10,20]
输出：true
解释：
前 3 位顾客那里，我们按顺序收取 3 张 5 美元的钞票。
第 4 位顾客那里，我们收取一张 10 美元的钞票，并返还 5 美元。
第 5 位顾客那里，我们找还一张 10 美元的钞票和一张 5 美元的钞票。
由于所有客户都得到了正确的找零，所以我们输出 true。

示例 2：

输入：[5,5,10]
输出：true

示例 3：

输入：[10,10]
输出：false

示例 4：

输入：[5,5,10,10,20]
输出：false
解释：
前 2 位顾客那里，我们按顺序收取 2 张 5 美元的钞票。
对于接下来的 2 位顾客，我们收取一张 10 美元的钞票，然后返还 5 美元。
对于最后一位顾客，我们无法退回 15 美元，因为我们现在只有两张 10 美元的钞票。
由于不是每位顾客都得到了正确的找零，所以答案是 false。
```

提示：

- $0 <= bills.length <= 10000$
- `bills[i]`不是 5 就是 10 或是 20 


**思路与算法：**

最初，我们既没有 5 美元钞票也没有 10 美元钞票。

- 如果顾客支付了 5 美元钞票，那么我们就得到 5 美元的钞票。

- 如果顾客支付了 10 美元钞票，我们必须找回一张 5 美元钞票。如果我们没有 5 美元的钞票，答案就是 False ，因为我们无法正确找零。

- 如果顾客支付了 20 美元钞票，我们必须找回 15 美元。

    - 如果我们有一张 10 美元和一张 5 美元，那么我们总会更愿意这样找零，这比用三张 5 美元进行找零更有利。
    - 如果我们只有三张 5 美元的钞票，那么我们将这样找零。
    - 否则，我们将无法给出总面值为 15 美元的零钱，答案是 False 。

```java
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int bill: bills) {
            if (bill == 5)
                five++;
            else if (bill == 10) {
                if (five == 0) {
                    return false;
                }
                five--;
                ten++;
            } else {
                // 支付了20，需要找零15
                // 优先使用10元而不是5元
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}
```

**复杂度分析**

时间复杂度：$O(N)$，其中$N$是`bills`的长度。

空间复杂度：$O(1)$。



## 874. 模拟行走机器人(简单)

机器人在一个无限大小的网格上行走，从点 (0, 0) 处开始出发，**面向北方**。该机器人可以接收以下三种类型的命令：

- -2：向左转 90 度
- -1：向右转 90 度
- $1 <= x <= 9$：向前移动 x 个单位长度

在网格上有一些格子被视为障碍物。

第 `i` 个障碍物位于网格点`(obstacles[i][0], obstacles[i][1])`

机器人无法走到障碍物上，它将会停留在障碍物的前一个网格方块上，但仍然可以继续该路线的其余部分。

返回从原点到机器人所有经过的路径点（坐标为整数）的最大欧式距离的平方。

 
```
示例 1：

输入: commands = [4,-1,3], obstacles = []
输出: 25
解释: 机器人将会到达 (3, 4)

示例 2：

输入: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
输出: 65
解释: 机器人在左转走到 (1, 8) 之前将被困在 (1, 4) 处
```

提示：
```
0 <= commands.length <= 10000
0 <= obstacles.length <= 10000
-30000 <= obstacle[i][0] <= 30000
-30000 <= obstacle[i][1] <= 30000
答案保证小于 2 ^ 31
```




## 944. 删列造序(简单)

给定由`N`个小写字母字符串组成的数组`A`，其中每个字符串长度相等。

你需要选出一组要删掉的列`D`，对`A`执行删除操作，使`A`中剩余的每一列都是**非降序**排列的，然后请你返回`D.length`的最小可能值。

删除操作的定义是：选出一组要删掉的列，删去`A`中对应列中的所有字符，形式上，第`n`列为$A[0][n], A[1][n], ..., A[A.length-1][n]$。

 
```
示例 1：

输入：["cba", "daf", "ghi"]
输出：1
解释：
当选择 D = {1}，删除后 A 的列为：["c","d","g"] 和 ["a","f","i"]，均为非降序排列。
若选择 D = {}，那么 A 的列 ["b","a","h"] 就不是非降序排列了。

示例 2：

输入：["a", "b"]
输出：0
解释：D = {}

示例 3：

输入：["zyx", "wvu", "tsr"]
输出：3
解释：D = {0, 1, 2}
```

提示：
- $1 <= A.length <= 100$
- $1 <= A[i].length <= 1000$
 

**删除操作范例**：

比如，有`A = ["abcdef", "uvwxyz"]`，
<div align=left><img src=LeetCode\944_1.png width=50%></div>

要删掉的列为`{0, 2, 3}`，删除后A为`["bef", "vyz"]`， A的列分别为`["b","v"], ["e","y"], ["f","z"]`。
<div align=left><img src=LeetCode\944_2.png width=50%></div>


**思路与算法：**
对于每一列，我们检查它是否是有序的。如果它有序，则将答案增加1，否则它必须被删除。

```java
class Solution {
    public int minDeletionSize(String[] A) {
        int ans = 0;
        // 共A[0].length()列
        for (int i = 0; i < A[0].length(); i++)
            // 共A.length - 1行
            for (int j = 0; j < A.length - 1; j++)
                // 直接比字符之间的大小
                if (A[j].charAt(i) > A[j + 1].charAt(i)) {
                    ans++;
                    break;
                }

        return ans;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$**是数组A中的元素个数**。
- 空间复杂度：$O(1)$。



## 1005. *K次取反后最大化的数组和(简单)

给定一个整数数组`A`，我们只能用以下方法修改该数组：我们选择某个索引`i`并将`A[i]`替换为`-A[i]`，然后总共重复这个过程`K`次。（我们**可以多次选择同一个索引`i`**。）

以这种方式修改数组后，返回数组可能的最大和。

```
示例 1：

输入：A = [4,2,3], K = 1
输出：5
解释：选择索引 (1,) ，然后 A 变为 [4,-2,3]。
示例 2：

输入：A = [3,-1,0,2], K = 3
输出：6
解释：选择索引 (1, 2, 2) ，然后 A 变为 [3,1,0,2]。
示例 3：

输入：A = [2,-3,-1,5,-4], K = 2
输出：13
解释：选择索引 (1, 4) ，然后 A 变为 [2,3,-1,5,4]。
```

提示：

- $1 <= A.length <= 10000$
- $1 <= K <= 10000$
- $-100 <= A[i] <= 100$

**解题思路**


贪心策略：要想使返回的数组值最大，那么尽可能**把小的负数变成正数(原来是最小的负数，变成正的时，就变成最大的了)**，数组排序后分为以下三种情况：
- **负数的个数>K**，那么把**最小的K个负数变为正数**即可
- **负数的个数<K**，那么把所有的负数变为正数后，**剩下的次数为偶数次**，那么直接返回就可以了
- **负数的个数<K**，那么把所有的负数变为正数后，**剩下的次数为奇数次**，那么**把最小的非负数变成负数**即可

作者：tian-dao-yao-xing
链接：https://leetcode-cn.com/problems/maximize-sum-of-array-after-k-negations/solution/yi-ci-pai-xu-ju-ti-kan-jie-ti-si-lu-ba-by-tian-dao/



排序后，遍历数组A，如果遇到$A[i] < 0$，且**此时K大于0**，那么就**将$A[i]$反转为正数，K减1**。如果$A[i] > 0$，那么就先暂时不用反转。同时在遍历的过程中维护一个最小值。

作者：keithy
链接：https://leetcode-cn.com/problems/maximize-sum-of-array-after-k-negations/solution/java-pai-xu-hou-yi-ci-bian-li-jie-jue-by-keithy/



```java
class Solution {
    public int largestSumAfterKNegations(int[] A, int K) {
        // 从小到大排序
        Arrays.sort(A);
        int sum = 0, min = Integer.MAX_VALUE;
        for (int i = 0; i < A.length; i++) {
            // K-- > 0 先运算K>0，再运算K--
            // 把负数变为正数
            if (A[i] < 0 && K-- > 0) {
                A[i] = -1 * A[i];
            }
            sum += A[i];
            min = min < A[i] ? min : A[i];
        }
        // 把所有的负数变为正数后，剩下的次数为奇数次，那么把最小的非负数变成负数
        // 把所有的负数变为正数后做了累加sum += A[i];多加了一次
        if (K > 0 && K % 2 == 1)
            sum = sum - 2 * min;
        return sum;
    }
}
```





## 1029. **两地调度(简单)

公司计划面试`2N`人。第`i`人飞往`A`市的费用为`costs[i][0]`，飞往`B`市的费用为`costs[i][1]`。

返回**将每个人都飞到某座城市的最低费用，要求每个城市都有`N`人抵达**。


示例：
```
输入：[[10,20],[30,200],[400,50],[30,20]]
输出：110
解释：
第一个人去 A 市，费用为 10。
第二个人去 A 市，费用为 30。
第三个人去 B 市，费用为 50。
第四个人去 B 市，费用为 20。

最低总费用为 10 + 30 + 50 + 20 = 110，每个城市都有一半的人在面试。
```

提示：
- $1 <= costs.length <= 100$
- $costs.length$为偶数
- $1 <= costs[i][0], costs[i][1] <= 1000$


**思路与算法：**

我们这样来看这个问题，公司首先将这$2N$个人全都安排飞往`B`市，再选出$N$个人改变它们的行程，让他们飞往`A`市。如果选择改变一个人的行程，那么公司将会额外付出`price_A - price_B`的费用，这个费用可正可负。

<div align=center><img src=LeetCode\1029.png></div>

因此最优的方案是，**选出`price_A - price_B`最小的$N$个人(说明飞往A市更合适)**，让他们飞往`A`市，其余人飞往`B`市。

### 用Arrays.sort对二维数组进行排序

```java
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        // 用Arrays.sort对二维数组进行排序
        Arrays.sort(costs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // o1[0] - o1[1]为第一个人的price_A - price_B
                // o2[0] - o2[1]为第二个人的price_A - price_B
                return o1[0] - o1[1] - (o2[0] - o2[1]);
            }
        });
        
        int total = 0;
        int n = costs.length / 2;
        for (int i = 0; i < n; i++) {
            // price_A - price_B最小的n人飞往A市，其余人飞往B市
            total += costs[i][0] + costs[i + n][1];
        }
        return total;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N \log N)$，需要对`price_A - price_B`进行排序。

- 空间复杂度：$O(1)$。


## 1046. 最后一块石头的重量(简单)

有一堆石头，每块石头的重量都是正整数。

每一回合，从中**选出两块最重的石头**，然后将它们一起粉碎。假设石头的重量分别为$x$和$y$，且$x <= y$。那么粉碎的可能结果如下：

- 如果$x == y$，那么两块石头都会被完全粉碎；
- 如果$x != y$，那么重量为$x$的石头将会完全粉碎，而重量为$y$的石头新重量为$y-x$。

**最后，最多只会剩下一块石头**。返回此石头的重量。如果没有石头剩下，就返回0。


示例：
```
输入：[2,7,4,1,8,1]
输出：1
解释：
先选出 7 和 8，得到 1，所以数组转换为 [2,4,1,1,1]，
再选出 2 和 4，得到 2，所以数组转换为 [2,1,1,1]，
接着是 2 和 1，得到 1，所以数组转换为 [1,1,1]，
最后选出 1 和 1，得到 0，最终数组转换为 [1]，这就是最后剩下那块石头的重量。
```

提示：

- $1 <= stones.length <= 30$
- $1 <= stones[i] <= 1000$

```java
class Solution {
    public int lastStoneWeight(int[] stones) {
        int index = stones.length - 1;
        for(int i = 0; i < stones.length - 1; i++) {
            Arrays.sort(stones);
            stones[index] -= stones[index - 1];
            stones[index - 1] = 0;
        }
        return stones[stones.length - 1];
    }
}
```



## 1196. 最多可以买到的苹果数量(简单)

楼下水果店正在促销，你打算买些苹果，`arr[i]`表示第`i`个苹果的单位重量。

你有一个购物袋，最多可以装`5000`单位重量的东西，算一算，**最多**可以往购物袋里装入多少苹果。

```
示例 1：
输入：arr = [100, 200, 150, 1000]
输出：4
解释：所有4个苹果都可以装进去，因为它们的重量之和为1450。

示例 2：
输入：arr = [900, 950, 800, 1000, 700, 800]
输出：5
解释：6个苹果的总重量超过了5000，所以我们只能从中任选5个。
```

提示：
- $1 <= arr.length <= 10^3$
- $1 <= arr[i] <= 10^3$


**解题思路：**

先将数组排序，然后由轻到重向篮子里放，直到总重量超过5000为止，所装下的苹果个数即是答案。

证明该贪心算法很容易，因为数组已经排序，**没装进篮子的苹果重量一定大于等于当前篮子中任意一个**，因此用剩下的去替换篮子中的苹果会得不偿失。

```java
public int maxNumberOfApples(int[] arr) {
    Arrays.sort(arr);
    int basket = 5000;
    for(int i = 0; i < arr.length; i++) {
        basket -= arr[i];
        if(basket < 0) {
            return i;
        }
    }
    return arr.length;
}
```

## 1217. 移动筹码(简单)

数轴上放置了一些筹码，每个筹码的位置存在数组`chips`当中。

你可以对任何筹码执行下面两种操作之一（不限操作次数，0次也可以）：

- 将第`i`个筹码向左或者右移动2个单位，代价为0。
- 将第`i`个筹码向左或者右移动1个单位，代价为1。

最开始的时候，同一位置上也可能放着两个或者更多的筹码。

返回**将所有筹码移动到同一位置（任意位置）上所需要的最小代价**。

 

示例 1：

输入：chips = [1,2,3]
输出：1
解释：第二个筹码移动到位置三的代价是 1，第一个筹码移动到位置三的代价是 0，总代价为 1。
示例 2：

输入：chips = [2,2,2,3,3]
输出：2
解释：第四和第五个筹码移动到位置二的代价都是 1，所以最小总代价为 2。
 

提示：
- $1 <= chips.length <= 100$
- $1 <= chips[i] <= 10^9$


**思路与算法：**

- 把在偶数位的都挪到一起，把在奇数位的都挪到一起。这样，只要比较哪个多，把剩下的都挪过去即可。
- 所以这道题目实际上就是让你**计算一个数列中奇数和偶数的数量的最小值**。

作者：qsctech-sange
链接：https://leetcode-cn.com/problems/play-with-chips/solution/yi-xing-dai-ni-qing-song-guo-guan-by-qsctech-sange/


```java
class Solution {
    public int minCostToMoveChips(int[] chips) {
        int odd = 0, even = 0;
        for (int chip : chips) {
            if (chip % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }
        // return Math.min(even, odd);
        return even > odd ? odd : even;
    }
}
```


## 1221. *分割平衡字符串(简单)

在一个「平衡字符串」中，**`L`和`R`字符的数量是相同的**。

给出一个平衡字符串`s`，请你将它分割成尽可能多的平衡字符串。

返回可以通过分割得到的平衡字符串的最大数量。

 
```
示例 1：

输入：s = "RLRRLLRLRL"
输出：4
解释：s 可以分割为 "RL", "RRLL", "RL", "RL", 每个子字符串中都包含相同数量的 'L' 和 'R'。

示例 2：

输入：s = "RLLLLRRRLR"
输出：3
解释：s 可以分割为 "RL", "LLLRRR", "LR", 每个子字符串中都包含相同数量的 'L' 和 'R'。

示例 3：

输入：s = "LLLLRRRR"
输出：1
解释：s 只能保持原样 "LLLLRRRR".
```

提示：

- $1 <= s.length <= 1000$
- `s[i] = L`或`R`
- 分割得到的每个字符串都必须是平衡字符串。


**思路与算法：**

在适当的位置截断源串得到平衡子串，截断后，**前后子串的计数不互相影响（无后效性）**，且所有局部最优相加即为整体的最优解。


- 设置**一个`L`与`R`的差值计数器**`diffCount`，设置一个**平衡子串计数器**`count`；
- 顺序遍历源串字符，遇`L`则`diffCount + 1`，遇到`R`则`diffCount - 1`；
- **每遍历一个字符检查一次`diffCount`是否为`0`**，若为`0`则`count + 1`

作者：0-vector
链接：https://leetcode-cn.com/problems/split-a-string-in-balanced-strings/solution/java-tan-xin-suan-fa-fen-ge-ping-heng-zi-fu-chuan-/

```java
class Solution {
    public int balancedStringSplit(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        // L与R的差值计数器diffCount；平衡子串计数器count
        int count = 0, diffCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'L') {
                diffCount++;
            } else {
                diffCount--;
            }
            if (diffCount == 0) {
                count ++;
            }
        }
        return count;
    }
}
```

## 1403. 非递增顺序的最小子序列(简单)

给你一个数组`nums`，请你从中抽取一个子序列，满足<u>该子序列的元素之和</u>**严格大于**<u>未包含在该子序列中的各元素之和</u>。

- 如果存在多个解决方案，只需返回**长度最小的子序列**。
- 如果仍然有多个解决方案，则返回**元素之和最大**的子序列。

与子数组不同的地方在于，「数组的子序列」不强调元素在原数组中的连续性，也就是说，它可以通过从数组中分离一些（也可能不分离）元素得到。

注意，题目数据保证满足所有约束条件的解决方案是唯一的。同时，<u>返回的答案应当按**非递增顺序**排列</u>。

 
```
示例 1：

输入：nums = [4,3,10,9,8]
输出：[10,9] 
解释：子序列 [10,9] 和 [10,8] 是最小的、满足元素之和大于其他各元素之和的子序列。但是 [10,9] 的元素之和最大。
 
示例 2：

输入：nums = [4,4,7,6,7]
输出：[7,7,6] 
解释：子序列 [7,7] 的和为 14 ，不严格大于剩下的其他元素之和（14 = 4 + 4 + 6）。因此，[7,6,7] 是满足题意的最小子序列。注意，元素按非递增顺序返回。  

示例 3：

输入：nums = [6]
输出：[6]
```

提示：

- $1 <= nums.length <= 500$
- $1 <= nums[i] <= 100$


**思路与算法：**

- 先将数组进行排序。
- 需要知道子序列的和，以及**除去子序列后剩余元素的和**。我们可以先遍历一遍原数组，求出数组所有元素之和left。
- 然后从数组最后一个元素开始第二次遍历，用sum记录**子序列的和**。对于每一个元素`nums[i]`
    - 更新**子序列的和**`sum += nums[i]`
    - 更新**除去子序列后剩余元素的和**`left -= nums[i]`
    - 将元素添加到**子序列**中`result.add(nums[i])`
    - 判断该子序列的元素之和是否严格大于未包含在该子序列中的各元素之和，如果是中止循环。

作者：ustcyyw
链接：https://leetcode-cn.com/problems/minimum-subsequence-in-non-increasing-order/solution/5376java-xian-pai-xu-zai-xuan-yuan-su-by-ustcyyw/

```java
class Solution {
    public List<Integer> minSubsequence(int[] nums) {
        List<Integer> result = new ArrayList<>();
        // 升序排序
        Arrays.sort(nums);
        // 除去子序列后剩余元素的和left；子序列的和sum
        int left = 0, sum = 0;
        for(int i : nums)
            left += i;
        for(int i = nums.length - 1; i >= 0; i--){
            sum += nums[i];
            left -= nums[i];
            result.add(nums[i]);
            if(sum > left)
                break;
        }
        return result;
    }
}
```

## 1518. *换酒问题(简单)

小区便利店正在促销，用`numExchange`个空酒瓶可以兑换一瓶新酒。你购入了`numBottles`瓶酒。

如果喝掉了酒瓶中的酒，那么酒瓶就会变成空的。

请你计算**最多**能喝到多少瓶酒。

<div align=left><img src=LeetCode\1518_1.png width=70%></div>

```
输入：numBottles = 9, numExchange = 3
输出：13
解释：你可以用 3 个空酒瓶兑换 1 瓶酒。
所以最多能喝到 9 + 3 + 1 = 13 瓶酒。
```

<div align=left><img src=LeetCode\1518_2.png width=70%></div>

```
输入：numBottles = 15, numExchange = 4
输出：19
解释：你可以用 4 个空酒瓶兑换 1 瓶酒。
所以最多能喝到 15 + 3 + 1 = 19 瓶酒。
```

```java
class Solution {
    public int numWaterBottles(int numBottles, int numExchange) {
        // 最多可以喝的酒的数量
        int result = numBottles;
        while (numBottles >= numExchange) {
            // 换的酒数量
            result += numBottles / numExchange;
            // 现存酒瓶数量
            numBottles = numBottles / numExchange + numBottles % numExchange;
        }
        return result;
    }
}
```