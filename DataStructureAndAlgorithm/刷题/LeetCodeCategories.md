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

# 栈


## 20. *有效的括号(简单)

给定一个只包括`'('`，`')'`，`'{'`，`'}'`，`'['`，`']'`的字符串，判断字符串是否有效。

有效字符串需满足：
- 左括号必须用相同类型的右括号闭合。
- 左括号必须以正确的顺序闭合。

注意空字符串可被认为是有效字符串。

**示例**：

```
输入: "()"
输出: true

输入: "()[]{}"
输出: true

输入: "(]"
输出: false

输入: "([)]"
输出: false

输入: "{[]}"
输出: true
```

**思路与算法：**

- 初始化栈`S`。
- 一次处理表达式的每个括号。
- 如果遇到**开括号**，我们只需将其**推到栈上**即可。这意味着我们将稍后处理它，让我们简单地转到前面的子表达式。
- 如果我们遇到一个**闭括号**，那么我们**检查栈顶**的元素。如果**此时栈顶**的元素是一个相同类型的左括号，那么我们将它从栈中弹出并继续处理。否则，这意味着表达式无效。
- 如果到最后我们剩下的栈中仍然有元素，那么这意味着表达式无效。

<div align=center><img src=LeetCode\20.png></div>


**代码：**
```java
import java.util.HashMap;
import java.util.Stack;

/**
 * 20. 有效的括号(*)
 */

public class ValidParentheses {
    // Hash table that takes care of the mappings.
    private HashMap<Character, Character> mappings;

    // Initialize hash map with mappings. This simply makes the code easier to read.
    public ValidParentheses(){
        this.mappings = new HashMap<Character, Character>();
        this.mappings.put(')', '(');
        this.mappings.put('}', '{');
        this.mappings.put(']', '[');
    }

    public boolean isValid(String s){
        // Initialize a stack to be used in the algorithm.
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            // If the current character is a closing bracket.
            if (this.mappings.containsKey(c)){
                // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
                char topElement = stack.empty() ? '#' : stack.pop();

                // If the mapping for this bracket doesn't match the stack's top element, return false.
                if (topElement != this.mappings.get(c))
                    return false;
            }
            else{
                // If it was an opening bracket, push to the stack.
                stack.push(c);
            }
        }

        // If the stack still contains elements, then it is an invalid expression.
        return stack.isEmpty();
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(n)$，因为我们一次只遍历给定的字符串中的一个字符并在栈上进行$O(1)$的推入和弹出操作。
- 空间复杂度：$O(n)$，当我们将所有的开括号都推到栈上时以及在最糟糕的情况下，我们最终要把所有括号推到栈上。例如`((((((((((`。




## 144. 二叉树的前序遍历(中等)

给定一个二叉树，返回它的前序遍历

```
输入: [1,null,2,3]  
   1
    \
     2
    /
   3 

输出: [1,2,3]
```

<div align=center><img src=LeetCode\二叉树遍历.png width=30%></div>

**从子树的角度来观察：**

- 如果按照`根节点 -> 左孩子 -> 右孩子`的方式遍历，即**先序遍历**：`1 2 4 5 3 6 7`；
- 如果按照`左孩子 -> 根节点 -> 右孩子`的方式遍历，即**中序遍历**：`4 2 5 1 6 3 7`；
- 如果按照`左孩子 -> 右孩子 -> 根节点`的方式遍历，即**后序遍历**：`4 5 2 6 7 3 1`；
- **层次遍历**就是按照每一层从左向右的方式进行遍历：`1 2 3 4 5 6 7`。

### 递归

```java
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_144_二叉树的前序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0 递归法
 */

public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    public void preorderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.val);
            // 把node.left看成以其为根的子树
            if (node.left != null) {
                preorderTraversal(node.left, result);
            }
            // 把node.right看成以其为根的子树
            if (node.right != null) {
                preorderTraversal(node.right, result);
            }
        }
    }
}
```

### 迭代-栈

使用**栈**来进行迭代，过程如下：
- 初始化栈，并将根节点入栈；
- 当栈不为空时：
  - 弹出栈顶元素node，并将值添加到结果中；
  - 如果node的右子树非空，将右子树入栈；
  - 如果node的左子树非空，将左子树入栈；

由于栈是“先进后出”的顺序，所以入栈时**先将右子树入栈**，这样使得前序遍历结果为 “根->左->右”的顺序。

<div align=center><img src=LeetCode\144.gif></div>

```java
package solution;

import java.util.LinkedList;
import java.util.List;

/**
 * leetcode_144_二叉树的前序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 2.0 迭代法
 */

public class PreorderTraversal_v2 {
    public List<Integer> preorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();

        if (root == null) {
            return output;
        }
        
        // 从根节点开始，每次迭代弹出当前栈顶元素，并将其孩子节点压入栈中
        stack.add(root);
        while (! stack.isEmpty()) {
            // 每次迭代弹出当前栈顶元素
            TreeNode node = stack.pollLast();
            output.add(node.val);
            
            // 先压右孩子
            if (node.right != null) {
                stack.add(node.right);
            }
            
            // 再压左孩子
            if (node.left != null) {
                stack.add(node.left);
            }
        }
        
        return output;
    }
}
```

**算法复杂度**

- 时间复杂度：**访问每个节点恰好一次**，时间复杂度为$O(N)$，其中$N$是节点的个数，也就是树的大小。
- 空间复杂度：取决于树的结构，最坏情况存储整棵树，因此空间复杂度是$O(N)$。



## 155. **最小栈(简单)

**题目：**
设计一个支持`push`，`pop`，`top`操作，并能在**常数时间内检索到最小元素**的栈。

`push(x)` —— 将元素`x`推入栈中。
`pop()` —— 删除栈顶的元素。
`top()` —— 获取栈顶元素。
`getMin()` —— 检索栈中的最小元素。

**示例：**
```
输入：
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

输出：
[null,null,null,null,-3,null,0,-2]

解释：
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.getMin();   --> 返回 -2.
```

**思路与算法：辅助栈**

要做出这道题目，首先要理解栈结构**先进后出**的性质。

对于栈来说，如果一个元素`a`在入栈时，栈里有其它的元素`b, c, d`，那么无论这个栈在之后经历了什么操作，只要`a`在栈中，`b, c, d`就一定在栈中，因为在`a`被弹出之前，`b, c, d`不会被弹出。

因此，在操作过程中的任意一个时刻，只要栈顶的元素是`a`，那么我们就可以确定栈里面现在的元素一定是`a, b, c, d`。

那么，我们可以<font color=red>在每个元素`a`入栈时把当前栈的最小值`m`存储起来</font>。在这之后无论何时，如果栈顶元素是`a`，我们就可以直接返回存储的最小值`m`。

<div align=center><img src=LeetCode\155_MinStack.gif></div>

- `push()`方法： 每当`push()`新值进来时，如果**小于等于`min_stack`栈顶值**，则一起`push()`，即更新了栈顶最小值；
- `pop()`方法： 判断将`pop()`出去的元素值**是否是`min_stack`栈顶元素值（即最小值）**，如果是则将`min_stack`栈顶元素一起`pop()`，这样可以保证`min_stack`栈顶元素始终是`stack`中的最小值。
- getMin()方法： 返回`min_stack`栈顶即可。


**代码：**
```java
class MinStack {

    /** initialize your data structure here. */
    private Stack<Integer> dataStack;
    private Stack<Integer> minStack;

    public MinStack() {
        dataStack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int x) {
        dataStack.push(x);
        if(minStack.isEmpty() || x <= minStack.peek())
            minStack.push(x);
    }
    
    public void pop() {
        int x = dataStack.pop();
        if(x == minStack.peek())
            minStack.pop();
    }
    
    public int top() {
        return dataStack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
```




## 225. 用队列实现栈(简单)

**题目：**
使用队列实现栈的下列操作：
- `push(x)` -- 元素`x`入栈
- `pop()` -- 移除栈顶元素
- `top()` -- 获取栈顶元素
- `empty()` -- 返回栈是否为空

注意:

- 你只能使用队列的基本操作-- 也就是`push to back`, `peek/pop from front`, `size`, 和 `is empty`这些操作是合法的。
- 你所使用的语言也许不支持队列。 你可以使用`list`或者`deque`（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
- 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用`pop`或者`top`操作）。


**思路与算法：**
- 栈是一种**后进先出**（last in - first out， LIFO）的数据结构，栈内元素从顶端压入（push），从顶端弹出（pop）。一般我们用**数组**或者**链表**来实现栈。
- 队列是一种与栈相反的**先进先出**（first in - first out， FIFO）的数据结构，队列中元素只能从**后端**（`rear`）入队（push），然后从**前端**（`front`）端出队（pop）。

为了满足栈的特性，我们需要维护**两个队列**`q1`和`q2`。同时，我们用一个额外的变量来保存栈顶元素。

**方法一（两个队列，压入$O(1)$， 弹出$O(n)$）**

**压入**（push）：
新元素永远从`q1`的**后端入队**，同时`q1`的后端也是栈的 栈顶（top）元素：
<div align=center><img src=LeetCode\225_1.png></div>

```java
private Queue<Integer> q1 = new LinkedList<>();
private int top;

// Push element x onto stack.
public void push(int x) {
    q1.add(x);
    top = x;
}
```

- 时间复杂度：$O(1)$
**队列是通过链表来实现**的，入队（add）操作的时间复杂度为$O(1)$。

- 空间复杂度：$O(1)$

**弹出**（pop）：
我们需要把栈顶元素弹出，就是`q1`中最后入队的元素。

考虑到队列是一种`FIFO`的数据结构，最后入队的元素应该在最后被出队。因此我们需要维护另外一个队列`q2`，这个队列用作**临时存储`q1`中出队的元素**。`q2`中最后入队的元素将作为新的栈顶元素。接着将`q1`中最后剩下的元素出队。我们通过**把`q1`和`q2`互相交换**（STEP4）的方式来避免把`q2`中的元素往`q1`中拷贝。

<div align=center><img src=LeetCode\225_2.png></div>

```java
private Queue<Integer> q1 = new LinkedList<>();
private Queue<Integer> q2 = new LinkedList<>();
// Removes the element on top of the stack.
public void pop() {
    while (q1.size() > 1) {
        top = q1.remove();
        q2.add(top);
    }
    q1.remove();
    Queue<Integer> temp = q1;
    q1 = q2;
    q2 = temp;
}
```

时间复杂度：$O(n)$
算法让`q1`中的$n$个元素出队，让$n - 1$个元素从`q2`入队，在这里$n$是栈的大小。这个过程总共产生了$2n - 1$次操作，时间复杂度为$O(n)$。


**方法二（两个队列， 压入$O(n)$， 弹出$O(1)$）**

**压入**（push)：

接下来介绍的算法让每一个新元素从`q2`入队，同时把这个元素作为栈顶元素保存。当`q1`非空（也就是栈非空），我们让`q1`中所有的元素全部出队，再将出队的元素从`q2`入队。通过这样的方式，新元素（栈中的栈顶元素）将会在`q2`的前端。我们通过将`q1，q2`互相交换的方式来避免把`q2`中的元素往`q1`中拷贝。

<div align=center><img src=LeetCode\225_3.png></div>

```java
public void push(int x) {
    q2.add(x);
    top = x;
    while (!q1.isEmpty()) {                
        q2.add(q1.remove());
    }
    Queue<Integer> temp = q1;
    q1 = q2;
    q2 = temp;
}
```

时间复杂度：$O(n)$
算法会让`q1`出队$n$个元素，同时入队$n + 1$个元素到`q2`。这个过程会产生$2n + 1$步操作，同时`链表`中`插入`操作和`移除`操作的时间复杂度为$O(1)$，因此时间复杂度为$O(n)$。

空间复杂度：$O(1)$

**弹出**（pop）:

直接让`q1`中元素出队，同时将出队后的`q1`中的队首元素作为栈顶元素保存。

<div align=center><img src=LeetCode\225_4.png></div>

```java
// Removes the element on top of the stack.
public int pop() {
    q1.remove();
    int res = top;
    if (!q1.isEmpty()) {
        top = q1.peek();
    }
    return res;
}
```

时间复杂度：$O(1)$；空间复杂度：$O(1)$


**方法三（一个队列， 压入$O(n)$， 弹出$O(1)$）**

上面介绍的两个方法都有一个缺点，它们都用到了两个队列。下面介绍的方法只需要**使用一个队列**。

**压入**（push）：

当我们将一个元素从队列入队的时候，根据队列的性质这个元素会存在队列的后端。但当我们实现一个栈的时候，最后入队的元素应该在前端，而不是在后端。为了实现这个目的，**每当入队一个新元素的时候，我们可以把队列的顺序反转过来**。

<div align=center><img src=LeetCode\225_5.png></div>

```java
private LinkedList<Integer> q1 = new LinkedList<>();

// Push element x onto stack.
public void push(int x) {
    q1.add(x);
    int sz = q1.size();
    while (sz > 1) {
        q1.add(q1.remove());
        sz--;
    }
}
```

时间复杂度：$O(n)$
这个算法需要从`q1`中出队$n$个元素，同时还需要入队$n$个元素到`q1`，其中$n$是栈的大小。这个过程总共产生了$2n + 1$步操作。链表中`插入`操作和`移除`操作的时间复杂度为$O(1)$，因此时间复杂度为$O(n)$。

空间复杂度：$O(1)$

**代码：**

```java
class ImplementStackUsingQueues   {
    Queue<Integer> queue;

    /** Initialize your data structure here. */
    public MyStack() {
        queue = new ArrayDeque<>();
    }
    
    /** Push element x onto stack. */
    // 入栈时，将新元素x进入队列后，将新元素x之前的所有元素重新入队，此时元素x处于队头
    public void push(int x) {
        queue.offer(x);
        int size = queue.size();
        for(int i = 0; i < size - 1; i++)
            queue.offer(queue.poll());
    }
    
    /** Removes the element on top of the stack and returns that element. */
    // 出栈pop操作和检查栈顶元素的top操作在调用队列相应方法前，需要检查队列是否为空，否则可能产生空指针异常
    public int pop() {
        if(queue.isEmpty())
            throw new RuntimeException("Empty Stack!");
        return queue.poll();
    }
    
    /** Get the top element. */
    public int top() {
        if(queue.isEmpty())
            throw new RuntimeException("Empty Stack!");
        return queue.peek();
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
```


## 496. 下一个更大元素I(简单)

https://leetcode-cn.com/problems/next-greater-element-i/solution/xia-yi-ge-geng-da-yuan-su-i-by-leetcode/

**题目：**

给定两个没有重复元素的数组`nums1`和`nums2`，其中`nums1`是`nums2`的子集。找到`nums1`中每个元素在`nums2`中的下一个比其大的值。

`nums1`中数字`x`的下一个更大元素是指`x`在`nums2`中对应位置的右边的第一个比`x`大的元素。如果不存在，对应位置输出`-1`。

**示例：**

```
输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
输出: [-1,3,-1]
解释:
    对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
    对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
    对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。

输入: nums1 = [2,4], nums2 = [1,2,3,4].
输出: [3,-1]
解释:
    对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
    对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。

提示：

nums1和nums2中所有元素是唯一的。
nums1和nums2的数组大小都不超过1000。
```

### 单调栈

我们可以忽略数组`nums1`，**<font color=red>先将`nums2`中的每一个元素，求出其下一个更大的元素。随后将这些答案放入哈希映射（HashMap）中，再遍历数组`nums1`，并直接找出答案</font>**。对于`nums2`，我们可以使用**单调栈**来解决这个问题。

- 我们首先把第一个元素`nums2[1]`放入栈，随后对于第二个元素`nums2[2]`
  - 如果`nums2[2] > nums2[1]`，那么我们就**找到了`nums2[1]`的下一个更大元素`nums2[2]`**，此时就可以**把`nums2[1]`出栈并把`nums2[2]`入栈**，**将`nums2[1]`:`nums2[2]`放入HashMap中**；
  - **如果`nums2[2] <= nums2[1]`，我们就仅把`nums2[2]`入栈**。
- 对于第三个元素`nums2[3]`，此时栈中有若干个元素，那么所有比`nums2[3]`小的元素都找到了下一个更大元素（即`nums2[3]`），因此都可以出栈，在这之后，我们将`nums2[3]`入栈，以此类推。

可以发现，我们维护了一个单调栈，栈中的元素从栈顶到栈底是单调不降的。当我们遇到一个新的元素`nums2[i]`时，我们判断栈顶元素是否小于`nums2[i]`，如果是，那么栈顶元素的下一个更大元素即为`nums2[i]`，我们将栈顶元素出栈。重复这一操作，直到栈为空或者栈顶元素大于`nums2[i]`。此时我们将`nums2[i]`入栈，保持栈的单调性，并对接下来的`nums2[i + 1], nums2[i + 2] ...`执行同样的操作。

<div align=center><img src=LeetCode\496_3.gif></div>

<div align=center><img src=LeetCode\496_1.jpg></div>

<div align=center><img src=LeetCode\496_2.jpg></div>


**代码：**
```java
class NextGreaterElementI {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] result = new int[nums1.length];

        for(int i = 0; i < nums2.length; i++){
            while(!stack.empty() && nums2[i] > stack.peek())  // 此时栈顶是nums2[i]之前的数
                map.put(stack.pop(), nums2[i]);
            stack.push(nums2[i]);
        }

        while(!stack.empty())
            map.put(stack.pop(), -1);

        for(int i = 0; i < nums1.length; i++)
            result[i] = map.get(nums1[i]);
        
        return result;
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(M+N)$，其中$M$和$N$分别是数组`nums1`和`nums2`的长度。

- 空间复杂度：$O(N)$。我们在遍历`nums2`时，需要使用栈，以及哈希映射用来临时存储答案。




## 682. 棒球比赛(简单)

**题目：**

你现在是棒球比赛记录员。
给定一个字符串列表，每个字符串可以是以下四种类型之一：
- 整数（一轮的得分）：直接表示您在本轮中获得的积分数。
- "+"（一轮的得分）：表示**本轮**获得的得分是**前两轮**有效回合得分的**总和**。
- "D"（一轮的得分）：表示**本轮**获得的得分是**前一轮**有效回合得分的**两倍**。
- "C"（一个操作，这不是一个回合的分数）：表示您获得的**最后一个**有效回合的分数是**无效**的，应该**被移除**。

每一轮的操作都是永久性的，可能会对前一轮和后一轮产生影响。你需要返回你在所有回合中得分的总和。


**示例：**
```
输入: ["5","2","C","D","+"]
输出: 30
解释: 
第1轮：你可以得到5分。总和是：5。
第2轮：你可以得到2分。总和是：7。
操作1：第2轮的数据无效。总和是：5。
第3轮：你可以得到10分（第2轮的数据已被删除）。总数是：15。
第4轮：你可以得到5 + 10 = 15分。总数是：30。

输入: ["5","-2","4","C","D","9","+","+"]
输出: 27
解释: 
第1轮：你可以得到5分。总和是：5。
第2轮：你可以得到-2分。总数是：3。
第3轮：你可以得到4分。总和是：7。
操作1：第3轮的数据无效。总数是：3。
第4轮：你可以得到-4（-2*2）分（第三轮的数据已被删除）。总和是：-1。
第5轮：你可以得到9分。总数是：8。
第6轮：你可以得到-4 + 9 = 5分。总数是13。
第7轮：你可以得到9 + 5 = 14分。总数是27。
```

**代码：**
```java
class BaseballGame {
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();

        for(String op : ops){
            if(op.equals("+")){
                // 本轮获得的得分是前两轮有效回合得分的总和
                int top = stack.pop();
                int newTop = top + stack.peek();
                stack.push(top);
                stack.push(newTop);
            }
            else if(op.equals("C"))
                stack.pop();
            else if(op.equals("D"))
                stack.push(2 * stack.peek());
            else
                stack.push(Integer.valueOf(op));
        }

        int ans = 0;
        for(int score : stack)
            ans += score;
        return ans;
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(N)$，其中$N$是`ops`的长度。我们解析给定数组中的每个元素，然后每个元素执行$O(1)$的工作。

- 空间复杂度：$O(N)$，用于存储`stack`的空间。



## 1021. 删除最外层的括号(简单)

**题目：**


有效括号字符串为空`("")`、`"(" + A + ")"`或`A + B`，其中`A`和`B`都是有效的括号字符串，`+`代表字符串的连接。例如，`""`，`"()"`，`"(())()"`和`"(()(()))"`都是有效的括号字符串。

如果有效字符串`S`非空，且不存在将其拆分为`S = A+B`的方法，我们称其为原语（primitive），其中`A`和`B`都是非空有效括号字符串。

给出一个非空有效字符串`S`，考虑将其进行原语化分解，使得：`S = P_1 + P_2 + ... + P_k`，其中`P_i`是有效括号字符串原语。

对`S`进行原语化分解，删除分解中每个原语字符串的最外层括号，返回`S`。

**示例：**

```
输入："(()())(())"
输出："()()()"
解释：
输入字符串为 "(()())(())"，原语化分解得到 "(()())" + "(())"，
删除每个部分中的最外层括号后得到 "()()" + "()" = "()()()"。

输入："(()())(())(()(()))"
输出："()()()()(())"
解释：
输入字符串为 "(()())(())(()(()))"，原语化分解得到 "(()())" + "(())" + "(()(()))"，
删除每个部分中的最外层括号后得到 "()()" + "()" + "()(())" = "()()()()(())"。

输入："()()"
输出：""
解释：
输入字符串为 "()()"，原语化分解得到 "()" + "()"，
删除每个部分中的最外层括号后得到 "" + "" = ""。
```

**思路与算法：**
遍历字符串，**遇到左括号就入栈，遇到右括号就出栈**，每次**栈空**的时候，都说明找到了一个原语，记录下每个原语的起始位置和结束位置，取原字符串在原语的**起始位置+1**到原语的结束位置的子串便得到原语删除了最外层括号的字符串，拼接，即可解出答案。

https://leetcode-cn.com/problems/remove-outermost-parentheses/solution/chao-xiang-xi-ti-jie-si-lu-jie-zhu-zhan-yuan-yu-hu/

**代码：**
```java
class RemoveOutermostParentheses {
    public String removeOuterParentheses(String string) {
        StringBuilder ans = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        int start = 0;  // 初始化原语的起始位置
        int end = 0;  // 初始化原语的结束位置
        boolean flag = false;  // 标志每个原语

        for(int i = 0; i <string.length(); i++){
            char ch = string.charAt(i);
            // 遇到左括号，入栈
            if(ch == '('){
                stack.push(ch);
                // 遇到的第一个左括号，是原语的开始位置，记录下原语开始位置
                if(!flag){
                    start = i;
                    flag = true;
                }
            }

            // 遇到右括号，出栈
            if(ch == ')'){
                stack.pop();
                // 当栈空的时候，找到了一个完整的原语
                if(stack.empty()){
                    // 记录下结束位置
                    end = i;
                    // 去掉原语的最外层括号，并添加到答案中
                    ans.append(string.substring(start + 1, end));

                    // 置标志为false，往后接着找下一个原语
                    flag = false;
                    start = end;
                }
            }
        }

        return ans.toString();
    }
}
```


## 1047. 删除字符串中的所有相邻重复项(简单)

**题目：**
给出由小写字母组成的字符串`S`，重复项删除操作会选择**两个相邻且相同的字母**，并删除它们。

在`S`上反复执行重复项删除操作，直到无法继续删除。

在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。

**示例：**

```
输入："abbaca"
输出："ca"
解释：
例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
```

**思路与算法：**
可以用栈来维护没有重复项的字母序列：
- 若当前的字母和**栈顶**的字母相同，则弹出栈顶的字母；
- 若当前的字母和**栈顶**的字母不同，则放入当前的字母。

<div align=left><img src=LeetCode\1047.jpg></div>

**代码：**

https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/solution/hen-jian-dan-de-ti-mu-shi-yong-zhan-jiu-neng-shi-x/

```java
class RemoveAllAdjacentDuplicatesInString {
    public String removeDuplicates(String S) {
        char[] s = S.toCharArray();
        int len = s.length;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            /*
            if(s[i] == stack.peek())
                stack.pop();
            else
                stack.push(s[i]);*/
            if(stack.empty() || s[i] != stack.peek())
                stack.push(s[i]);
            else
                stack.pop();
        }
        /* 数据的展示可以继续优化 */
        StringBuilder str = new StringBuilder();
        for (Character c : stack) {
            str.append(c);
        }
        return str.toString();
    }
}
```

```java
class Solution {
    public String removeDuplicates(String S) {
        StringBuilder sb = new StringBuilder();
        int sbLength = 0;
        for (char character : S.toCharArray()) {
            // 若当前的字母和栈顶的字母相同，则弹出栈顶的字母
            if (sbLength != 0 && character == sb.charAt(sbLength - 1))
                sb.deleteCharAt(sbLength-- - 1);
            else {
                sb.append(character);
                sbLength++;
            }
        }
        return sb.toString();
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是字符串的长度。

- 空间复杂度：$O(N)$。





# 双指针


## 剑指Offer 22. 链表中倒数第k个节点(简单)

输入一个链表，输出该链表中倒数第k个节点。

为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。

 
```
示例：

给定一个链表: 1->2->3->4->5, 和 k = 2.

返回链表 4->5.
```

**解题思路：**

解法一：
- 先遍历统计链表长度，记为$n$；
- 设置一个指针走$(n−k)$步，即可找到链表倒数第$k$个节点。

解法二：使用双指针则可以不用统计链表长度。

- 初始化：前指针`former`、后指针`latter`，双指针都指向头节点`head`​。
- 构建双指针距离：前指针`former`先向前走$k$步（结束后，双指针`former`和`latter`间相距 $k$步）。
- 双指针共同移动：循环中，双指针`former`和`latter`每轮都向前走一步，直至`former`走过链表**尾节点**时跳出（跳出后，`latter`与尾节点距离为$k−1$，即`latter`指向倒数第$k$个节点）。
返回值： 返回`latter`即可。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode former = head, latter = head;
        for(int i = 0; i < k; i++)
            former = former.next;
        while(former != null) {
            former = former.next;
            latter = latter.next;
        }
        return latter;
    }
}
```

**复杂度分析：**

- 时间复杂度$O(N)$：$N$为链表长度；总体看，`former`走了$N$步，`latter`走了$(N-k)$步。
- 空间复杂度$O(1)$： 双指针`former`、`latter`使用常数大小的额外空间。




作者：jyd
链接：https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/solution/mian-shi-ti-22-lian-biao-zhong-dao-shu-di-kge-j-11/



## 21. 合并两个有序链表(简单)

**题目：**

将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

**示例：**

```
输入：1->2->4  1->3->4
输出：1->1->2->3->4->4
```


- 首先，我们设定一个哨兵节点`prehead`，这可以在最后让我们比较容易地返回合并后的链表。
- 我们维护一个`prev`指针，我们需要做的是调整它的`next`指针。
- 然后，我们重复以下过程，直到`l1`或者`l2`指向了`null`：
  - 如果`l1`当前节点的值小于等于`l2`，我们就把`l1`当前的节点接在`prev`节点的后面，同时将`l1`指针往后移一位。
  - 否则，我们对`l2`做同样的操作。
  - **不管我们将哪一个元素接在了后面，我们都需要把`prev`向后移一位**。

在循环终止的时候，`l1`和`l2`至多有一个是非空的。由于输入的两个链表都是有序的，所以不管哪个链表是非空的，它包含的所有元素都比前面已经合并链表中的所有元素都要大。这意味着我们只需要简单地将非空链表接在合并链表的后面，并返回合并链表即可。

<div align=center><img src=LeetCode\21.gif></div>

```java
package solution;

/**
 * @author Chenzf
 * @date 2020/7/25
 * @version 2.0 迭代
 */

public class MergeTwoSortedList_v2 {
    public ListNode mergeTwoLists(ListNode listNode1, ListNode listNode2) {
        ListNode preHead = new ListNode(-1);
        ListNode prev = preHead;

        while (listNode1 != null && listNode2 != null) {
            if (listNode1.val <= listNode2.val) {
                prev.next = listNode1;
                listNode1 = listNode1.next;
            } else {
                prev.next = listNode2;
                listNode2 = listNode2.next;
            }

            prev = prev.next;
        }

        // 此时l1和l2只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = (listNode1 == null) ? listNode2 : listNode1;
        
        return preHead.next;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n + m)$，其中$n$和$m$分别为两个链表的长度。因为每次循环迭代中，`listNode1`和`listNode2`只有一个元素会被放进合并链表中， 因此`while`循环的次数不会超过两个链表的长度之和。所有其他操作的时间复杂度都是常数级别的，因此总的时间复杂度为$O(n+m)$。

- 空间复杂度：$O(1)$。我们只需要常数的空间存放若干变量。



## 26. *删除排序数组中的重复项(简单)

给定**一个<font color=red>排序数组</font>**，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

**不要使用额外的数组空间**，你必须在原地修改输入数组并在使用$O(1)$额外空间的条件下完成。


```
示例 1:

给定数组 nums = [1,1,2], 

函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 

你不需要考虑数组中超出新长度后面的元素。

示例 2:

给定 nums = [0,0,1,1,1,2,2,3,3,4],

函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。

你不需要考虑数组中超出新长度后面的元素。
```

说明：

为什么返回数值是整数，但输出的答案是数组呢?

请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。

你可以想象内部操作如下:
```
// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
int len = removeDuplicates(nums);

// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
```

**思路与算法：**

放置两个指针$i$和$j$，其中$i$是慢指针，而$j$是快指针
- 只要$nums[i] = nums[j]$，我们就增加$j$以**跳过重复项**。

- 当我们遇到$nums[j] \neq nums[i]$时，跳过重复项的运行已经结束，因此我们必须把它（$nums[j]$）的值复制到$nums[i + 1]$。然后递增$i$，接着我们将再次重复相同的过程，直到$j$到达数组的末尾为止。


```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int i = 0;
        // 相同的元素跳过，找到不同的元素并替换相同的元素
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，假设数组的长度是$n$，那么$i$和$j$分别最多遍历$n$步。

- 空间复杂度：$O(1)$。




## 27. *移除元素(简单)

给你一个数组`nums`和一个值`val`，你需要**原地移除所有数值等于val的元素**，并返回移除后数组的新长度。

不要使用额外的数组空间，你必须仅使用$O(1)$额外空间并原地修改输入数组。

元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。

 
```
示例 1:

给定 nums = [3,2,2,3], val = 3,

函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。

你不需要考虑数组中超出新长度后面的元素。

示例 2:

给定 nums = [0,1,2,2,3,0,4,2], val = 2,

函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。

注意这五个元素可为任意顺序。

你不需要考虑数组中超出新长度后面的元素。
```

**思路与算法：**

当`nums[j]`与给定的值相等时，递增`j`以跳过该元素。只要$nums[j] \neq val$，我们就复制`nums[j]` 到`nums[i]`并同时递增两个索引。重复这一过程，直到`j`到达数组的末尾，该数组的新长度为`i`。

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            // 把!= val的值往前移
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，
  假设数组总共有$n$个元素，$i$和$j$至少遍历$2n$步。

- 空间复杂度：$O(1)$。


## 88. 合并两个有序数组(简单)

给你两个有序整数数组 `nums1` 和 `nums2`，请你**将 `nums2` 合并到 `nums1` 中**，使 nums1 成为一个有序数组。

 
说明:

初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 
```
示例:

输入:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

输出: [1,2,2,3,5,6]
```

**方法一 : 合并后排序**

最朴素的解法就是**将两个数组合并之后再排序**。该算法时间复杂度较差，为$O((n + m)\log(n + m))$。这是由于这种方法**没有利用两个数组本身已经有序这一点**。

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
    }
}
```

复杂度分析

- 时间复杂度 : $O((n + m)\log(n + m))$。
- 空间复杂度 : $O(1)$。


**方法二 : 双指针 / 从前往后**

一般而言，对于有序数组可以通过 双指针法 达到$O(n + m)$的时间复杂度。

最直接的算法实现是将指针`p1` 置为 `nums1`的开头， `p2`为 `nums2`的开头，在每一步将最小值放入输出数组中。

由于 `nums1` 是用于输出的数组，需要将`nums1`中的前`m`个元素放在其他地方，也就需要$O(m)$的空间复杂度。

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Make a copy of nums1.
        int [] nums1_copy = new int[m];
        System.arraycopy(nums1, 0, nums1_copy, 0, m);

        // Two get pointers for nums1_copy and nums2.
        int p1 = 0;
        int p2 = 0;

        // Set pointer for nums1
        int p = 0;

        // Compare elements from nums1_copy and nums2 and add the smallest one into nums1.
        while ((p1 < m) && (p2 < n)) {
            nums1[p++] = (nums1_copy[p1] < nums2[p2]) ? nums1_copy[p1++] : nums2[p2++];
        }

        // if there are still elements to add
        if (p1 < m) {
            System.arraycopy(nums1_copy, p1, nums1, p1 + p2, m + n - p1 - p2);
        }
        if (p2 < n) {
            System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
        }
    }
}
```

**复杂度分析**

- 时间复杂度 : $O(n + m)$。
- 空间复杂度 : $O(m)$。


**方法三 : 双指针 / 从后往前**

方法二已经取得了最优的时间复杂度$O(n + m)$，但需要使用额外空间。这是由于在从头改变`nums1`的值时，需要把`nums1`中的元素存放在其他位置。

如果我们从结尾开始改写 `nums1` 的值又会如何呢？这里没有信息，因此不需要额外空间。

这里的指针 `p` 用于追踪添加元素的位置。


<div align=center><img src=LeetCode\88.jpg></div>


```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // two get pointers for nums1 and nums2
        int p1 = m - 1;
        int p2 = n - 1;
        // set pointer for nums1
        int p = m + n - 1;

        // while there are still elements to compare
        while ((p1 >= 0) && (p2 >= 0)) {
            // compare two elements from nums1 and nums2 and add the largest one in nums1 
            nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];
        }

        // add missing elements from nums2
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n + m)$。
- 空间复杂度：$O(1)$。


## 125. 验证回文串(简单)

给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。

说明：本题中，我们将空字符串定义为有效的回文串。

```
示例 1:

输入: "A man, a plan, a canal: Panama"
输出: true

示例 2:

输入: "race a car"
输出: false
```

**思路与算法：**

直接在原字符串s上使用双指针。在移动任意一个指针时，需要不断地向另一指针的方向移动，直到遇到一个字母或数字字符，或者两指针重合为止。也就是说，我们每次将指针移到下一个字母字符或数字字符，再判断这两个指针指向的字符是否相同。

```java
class Solution {
    public boolean isPalindrome(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(|s|)$，其中$|s|$是字符串s的长度。

- 空间复杂度：$O(1)$。





## 141. *环形链表(简单)

给定一个链表，判断链表中是否有环。

为了表示给定链表中的环，我们使用整数pos来表示**链表尾连接到链表中的位置**（索引从0开始）。 如果pos是-1，则在该链表中没有环。

 
```
示例 1：

输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。
```

<div align=center><img src=LeetCode\141_1.png width=50%></div>


**思路与算法：**

通过使用具有不同速度的**快、慢两个指针**遍历链表，空间复杂度可以被降低至$O(1)$。**慢指针每次移动一步，而快指针每次移动两步**。

- 如果列表中不存在环，最终快指针将会最先到达尾部，此时我们可以返回false。

- 现在考虑一个环形链表，把慢指针和快指针想象成两个在环形赛道上跑步的运动员（分别称之为慢跑者与快跑者）。而**快跑者最终一定会追上慢跑者**。这是为什么呢？考虑下面这种情况（记作情况 A）- 假如快跑者只落后慢跑者一步，在下一次迭代中，它们就会分别跑了一步或两步并相遇。

<div align=center><img src=LeetCode\141.gif></div>

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        // 让快指针先走一步，可以进入while循环
        ListNode fast = head.next;
        
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            // 慢指针每次移动一步，而快指针每次移动两步
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，让我们将$n$设为链表中结点的总数。为了分析时间复杂度，我们分别考虑下面两种情况。

    - 链表中不存在环：快指针将会首先到达尾部，其时间取决于列表的长度，也就是$O(n)$。

    - 链表中存在环：我们将慢指针的移动过程划分为两个阶段：非环部分与环形部分：

        - 慢指针在走完非环部分阶段后将进入环形部分：此时，快指针已经进入环中：$\text{迭代次数} = \text{非环部分长度} = N$

        - 两个指针都在环形区域中：考虑两个在环形赛道上的运动员 - 快跑者每次移动两步而慢跑者每次只移动一步。其**速度的差值为1**，因此需要经过$\dfrac{\text{二者之间距离}}{\text{速度差值}}$次循环后，快跑者可以追上慢跑者。这个距离几乎就是$\text{环形部分长度 K}$，且速度差值为1，我们得出这样的结论$\text{迭代次数}=近似于\text{环形部分长度 K}$。

    - 因此，在最糟糕的情形下，时间复杂度为$O(N+K)$，也就是$O(n)$。

- 空间复杂度：$O(1)$，我们只使用了慢指针和快指针两个结点，所以空间复杂度为$O(1)$。



## 160. *相交链表(简单)

**题目描述：**

编写一个程序，找到两个单链表相交的起始节点。

如下面的两个链表：

<div align=center><img src=LeetCode\160.png width=60%></div>

在节点c1开始相交。

示例1：
<div align=center><img src=LeetCode\160_1.png width=60%></div>
```
输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5]
输出：Reference of the node with value = 8
解释：相交节点的值为8（注意，如果两个链表相交则不能为0）。从各自的表头开始算起，在A中，相交节点前有2个节点；在B中，相交节点前有3个节点。
```

示例2：
<div align=center><img src=LeetCode\160_2.png width=50%></div>
```
输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4]
输出：Reference of the node with value = 2
解释：相交节点的值为2（注意，如果两个链表相交则不能为 0）。从各自的表头开始算起，在A中，相交节点前有3个节点；在B中，相交节点前有1个节点。
```

示例3：
<div align=center><img src=LeetCode\160_3.png width=30%></div>
```
输入：intersectVal = 0, listA = [2,6,4], listB = [1,5]
输出：null
解释：从各自的表头开始算起，由于这两个链表不相交，所以intersectVal为0
```

**思路与算法：**

- 设定两个指针分别指向两个链表头部，一起向前走直到其中一个到达末端，另一个与末端距离则是两链表的**长度差**。再通过**长链表指针先走**的方式**消除长度差**，**最终两链表即可同时走到相交点**。

- 换个方式**消除长度差**：**类似拼接两链表**
  - 设长-短链表为C，短-长链表为D（分别代表长链表在前和短链表在前的拼接链表），则当C走到长短链表交接处时(C与D速度相同)，D在长链表中，此时**D与长链表头距离为长度差**
  - **短链表走完后，从长链表头开始接着走；当长链表走完后，从短链表头开始走时，此时两者剩余路程相同**！

<div align=center><img src=LeetCode\160_4.png width=60%></div>

<div align=center><img src=LeetCode\160_5.png></div>

```java
package solution;

/**
 * leetcode_160_相交链表
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0
 */

public class GetIntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pointA = headA, pointB = headB;
        // 速度相同，走过相同的路，且有交点，则注定会相遇
        while (pointA != pointB) {
            pointA = (pointA == null) ? headB : pointA.next;
            pointB = (pointB == null) ? headA : pointB.next;
        }

        return pointA;
    }
}
```




## 167. 两数之和 II - 输入有序数组(简单)

给定一个已**按照升序排列**的有序数组，找到两个数使得它们相加之和等于目标数。

函数应该返回这两个下标值`index1`和`index2`，其中`index1`必须小于`index2`。

说明:

- 返回的下标值（`index1`和`index2`）不是从零开始的。
- 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。

```
示例:

输入: numbers = [2, 7, 11, 15], target = 9
输出: [1,2]
解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
```

**思路与算法：**

- 初始时两个指针分别指向**第一个元素位置**和**最后一个元素的位置**。
- 每次计算两个指针指向的两个元素之和，并和目标值比较。
  - 如果两个元素之和**等于**目标值，则发现了唯一解。
  - 如果两个元素之和**小于**目标值，则**将左侧指针右移一位**。
  - 如果两个元素之和**大于**目标值，则**将右侧指针左移一位**。
- 移动指针之后，重复上述操作，直到找到答案。


```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int low = 0, high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low + 1, high + 1};
            } else if (sum < target) {
                low++;
            } else {
                high--;
            }
        }
        return new int[]{-1, -1};
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$是数组的长度。两个指针移动的总次数最多为$n$次。

- 空间复杂度：$O(1)$。



## 203. 移除链表元素(简单)


删除链表中等于给定值val的所有节点。

```
示例:

输入: 1->2->6->3->4->5->6, val = 6
输出: 1->2->3->4->5
```


### 双指针

如果删除的节点是中间的节点，则问题似乎非常简单：

- 选择要删除节点的前一个结点`prev`。
- 将`prev`的`next`设置为要删除结点的`next`。

<div align=center><img src=LeetCode\203_1.jpg></div>

当要删除的一个或多个节点位于链表的头部时，事情会变得复杂：

<div align=center><img src=LeetCode\203_2.jpg></div>

可以通过**哨兵节点**去解决它，哨兵节点广泛应用于树和链表中，如伪头、伪尾、标记等，它们是纯功能的，通常不保存任何数据，其主要目的是使链表标准化，如使链表永不为空、永不无头、简化插入和删除。

<div align=center><img src=LeetCode\203_3.jpg></div>

在这里哨兵节点将被用于**伪头**。

算法：

- 初始化哨兵节点为`ListNode(0)`且设置`sentinel.next = head`。
- 初始化两个指针`curr`和`prev`指向当前节点和前继节点。
- 当`curr != null`：
  - 比较当前节点和要删除的节点：
    - 若当前节点就是要删除的节点：则`prev.next = curr.next`。
    - 否则设`prve = curr`。
  - 遍历下一个元素：`curr = curr.next`。
- 返回`sentinel.next`。


```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
  public ListNode removeElements(ListNode head, int val) {
    ListNode sentinel = new ListNode(0);
    sentinel.next = head;

    ListNode prev = sentinel, curr = head;
    while (curr != null) {
      if (curr.val == val) {
          prev.next = curr.next;
      } else {
          prev = curr;
      }
      curr = curr.next;
    }
    return sentinel.next;
  }
}
```


**复杂度分析**

- 时间复杂度：$\mathcal{O}(N)$，只遍历了一次。
- 空间复杂度：$\mathcal{O}(1)$。



## 206. 反转链表(简单)

**题目：**

反转一个单链表。

```
输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
```

**思路与算法：**

### 双指针迭代

<div align=center><img src=LeetCode\206.gif></div>

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

package solution;

/**
 * leetcode_206_反转链表
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0 迭代
 */

public class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        return prev;
    }
}

```

时间复杂度：$O(n)$，假设$n$是列表的长度，时间复杂度是$O(n)$。
空间复杂度：$O(1)$。


## 234. 回文链表(简单)

请判断一个链表是否为回文链表。

```
示例 1:
输入: 1->2
输出: false

示例 2:
输入: 1->2->2->1
输出: true
```

### 将值复制到数组中后用双指针法

有两种常用的列表实现，一种是**数组列表**和**链表**。如果我们想在列表中存储值，那么它们是如何保存的呢？

数组列表底层是使用数组存储值，我们可以通过索引在$O(1)$的时间访问列表任何位置的值，这是由于内存寻址的方式。

链表存储的是称为节点的对象，每个节点保存一个值和指向下一个节点的指针。访问某个特定索引的节点需要$O(n)$的时间，因为要通过指针获取到下一个位置的节点。

确定数组列表是否为回文很简单，我们可以**使用双指针法来比较两端的元素，并向中间移动**。一个指针从起点向中间移动，另一个指针从终点向中间移动。这需要$O(n)$的时间，因为访问每个元素的时间是$O(1)$，而有$n$个元素要访问。

然而，直接在链表上操作并不简单，因为不论是正向访问还是反向访问都不是$O(1)$。而**将链表的值复制到数组列表中是$O(n)$**，因此最简单的方法就是将链表的值复制到数组列表中，再使用双指针法判断。

```java {.line-numbers highlight=27-29}
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_234_回文链表
 * @author Chenzf
 * @date 2020/7/30
 * @version 1.0 将值复制到数组中后用双指针法
 */

public class PalindromeLinkedList {
    public boolean isPalindrome(ListNode head) {
        List<Integer> arrayList = new ArrayList<>();

        // Convert LinkedList into ArrayList.
        ListNode currentNode = head;
        while (currentNode != null) {
            arrayList.add(currentNode.val);
            currentNode = currentNode.next;
        }

        // Use two-pointer technique to check for palindrome.
        int left = 0, right = arrayList.size() - 1;
        while (left < right) {
            // Note that we must use ! .equals instead of !=
            // because we are comparing Integer, not int.
            if (! arrayList.get(left).equals(arrayList.get(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$指的是链表的元素个数。
  - 第一步： 遍历链表并将值复制到数组中，$O(n)$。
  - 第二步：双指针判断是否为回文，执行了$O(n/2)$次的判断，即$O(n)$。
  - 总的时间复杂度：$O(2n) = O(n)$。
- 空间复杂度：$O(n)$，其中$n$指的是链表的元素个数，我们使用了一个数组列表存放链表的元素值。


### 将链表的后半部分反转

可以将链表的后半部分反转（修改链表结构），然后将前半部分和后半部分进行比较。比较完成后我们应该将链表恢复原样。虽然不需要恢复也能通过测试用例，因为使用该函数的人不希望链表结构被更改。

可以分为以下几个步骤：

- 找到前半部分链表的尾节点。
- 反转后半部分链表。
- 判断是否为回文。
- 恢复链表。
- 返回结果。


步骤一，我们可以**计算链表节点的数量**，然后遍历链表找到前半部分的尾节点。或者可以**使用快慢指针**在一次遍历中找到：**慢指针一次走一步，快指针一次走两步**，快慢指针同时出发。当快指针移动到链表的末尾时，慢指针到链表的中间。通过慢指针将链表分为两部分。若链表有奇数个节点，则中间的节点应该看作是前半部分。

步骤二，可以使用在反向链表问题中找到解决方法来反转链表的后半部分。

步骤三，比较两个部分的值，当后半部分到达末尾则比较完成，可以忽略计数情况中的中间节点。

步骤四与步骤二使用的函数相同，再反转一次恢复链表本身。

```java
package solution;

/**
 * leetcode_234_回文链表
 * @author Chenzf
 * @date 2020/7/30
 * @version 2.0 将链表的后半部分反转
 */

public class PalindromeLinkedList_v2 {
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        // Find the end of first half
        ListNode firstHalfEnd = findEndOfFirstHalf(head);
        // Reverse second half
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // Check whether or not there is a palindrome.
        ListNode point1 = head;
        ListNode point2 = secondHalfStart;
        boolean result = true;

        while (result && point2 != null) {
            if (point1.val != point2.val) {
                result = false;
            }

            point1 = point1.next;
            point2 = point2.next;
        }

        // Restore the list and return the result.
        firstHalfEnd.next = reverseList(secondHalfStart);
        
        return result;
    }

    /**
     * 使用快慢指针在一次遍历中找到：慢指针一次走一步，快指针一次走两步，快慢指针同时出发。
     * 当快指针移动到链表的末尾时，慢指针到链表的中间。通过慢指针将链表分为两部分。
     */
    private ListNode findEndOfFirstHalf(ListNode head) {
        ListNode fastPoint = head;
        ListNode slowPoint = head;
        while (fastPoint.next != null && fastPoint.next.next != null) {
            fastPoint = fastPoint.next.next;
            slowPoint = slowPoint.next;
        }

        return slowPoint;
    }

    /**
     * 反转链表
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode temp = null;
        while (curr != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        return prev;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$指的是链表的大小。
- 空间复杂度：$O(1)$，我们是一个接着一个的改变指针，在堆栈上的堆栈帧不超过$O(1)$。

该方法的缺点是，在并发环境下，函数运行时需要锁定其他线程或进程对链表的访问，因为在函数执执行过程中链表暂时断开。



## 283. *移动零(简单)

给定一个数组nums，编写一个函数将所有0移动到数组的末尾，同时保持非零元素的相对顺序。

```
示例:

输入: [0,1,0,3,12]
输出: [1,3,12,0,0]
```

说明:

- 必须在原数组上操作，不能拷贝额外的数组。
- 尽量减少操作次数。

```java
class Solution {
	public void moveZeroes(int[] nums) {
		if(nums == null) {
			return;
		}
		//第一次遍历的时候，j指针记录非0的个数，只要是非0的统统都赋给nums[j]
		int j = 0;
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] != 0) {
				nums[j++] = nums[i];
			}
		}
		//非0元素统计完了，剩下的都是0了。所以第二次遍历把末尾的元素都赋为0即可
		for(int i = j; i < nums.length; i++) {
			nums[i] = 0;
		}
	}
}
```



## 344. 反转字符串(简单)

**题目描述：**

编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组char[]的形式给出。

不要给另外的数组分配额外的空间，你必须**原地修改输入数组**、使用$O(1)$的额外空间解决这一问题。

你可以假设数组中的所有字符都是`ASCII`码表中的可打印字符。

```
示例1：
输入：["h","e","l","l","o"]
输出：["o","l","l","e","h"]

示例2：
输入：["H","a","n","n","a","h"]
输出：["h","a","n","n","a","H"]
```

**思路与算法：**

双指针法是使用两个指针，一个左指针left，右指针right，**开始工作时left指向首元素，right指向尾元素。交换两个指针指向的元素，并向中间移动，直到两个指针相遇**。


<div align=center><img src=LeetCode\344.jpg width=70%></div>

```java
package solution;

/**
 * leetcode_344_反转字符串
 * @author Chenzf 
 * @date 2020/7/26
 * @version 1.0
 */

public class ReverseString {
    public void reverseString(char[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }
        
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$\mathcal{O}(N)$。执行了$N/2$次的交换。
- 空间复杂度：$\mathcal{O}(1)$，只使用了常数级空间。



## 345. 反转字符串中的元音字母(简单)

编写一个函数，以字符串作为输入，反转该字符串中的元音字母。

```
示例 1:

输入: "hello"
输出: "holle"

示例 2:

输入: "leetcode"
输出: "leotcede"
说明:
元音字母不包含字母"y"。
```

```java
class Solution {
    public String reverseVowels(String s) {
        // 先将字符串转成字符数组（方便操作）
        // 以上是只针对 Java 语言来说的 因为 chatAt(i) 每次都要检查是否越界 有性能消耗
        char[] arr = s.toCharArray();
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        while (left < right) {
            // 从左判断如果当前元素不是元音
            while (left < n && !isVowel(arr[left]) ) {
                left++;
            }
            // 从右判断如果当前元素不是元音
            while (right >= 0 && !isVowel(arr[right]) ) {
                right--;
            }
            // 如果没有元音
            if (left >= right) {
                break;
            }
            // 交换前后的元音
            swap(arr, left, right);
            left++;
            right--;
        }
        // 最后返回的时候要转换成字符串输出
        return new String(arr);
    }

    private void swap(char[] arr, int a, int b) {
        char temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // 判断是不是元音
    private boolean isVowel(char ch) {
        // 这里要直接用 return 语句返回，不要返回 true 或者 false
         return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
                ||ch=='A'|| ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U';
    }
}
```



## 350. 两个数组的交集 II(简单)


给定两个数组，编写一个函数来计算它们的交集。

```
示例 1：

输入：nums1 = [1,2,2,1], nums2 = [2,2]
输出：[2,2]

示例 2:

输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出：[4,9]
```

说明：

- 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
- 可以不考虑输出结果的顺序。

进阶：

- 如果给定的数组已经排好序呢？你将如何优化你的算法？
- 如果nums1的大小比nums2小很多，哪种方法更优？
- 如果nums2的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？


**思路与算法：**

- **首先对两个数组进行排序**，然后使用两个指针遍历两个数组。

- 初始时，两个指针分别指向两个数组的头部。每次比较两个指针指向的两个数组中的数字
  - 如果两个数字**不相等**，则**将指向较小数字的指针右移一位**
  - 如果两个数字**相等**，将该数字添加到答案，并**将两个指针都右移一位**。
- 当至少有一个指针超出数组范围时，遍历结束。

```java
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        int length1 = nums1.length, length2 = nums2.length;
        int[] intersection = new int[Math.min(length1, length2)];
        int index1 = 0, index2 = 0, index = 0;
        while (index1 < length1 && index2 < length2) {
            if (nums1[index1] < nums2[index2]) {
                index1++;
            } else if (nums1[index1] > nums2[index2]) {
                index2++;
            } else {
                intersection[index] = nums1[index1];
                index1++;
                index2++;
                index++;
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }
}
```

**复杂度分析**

- 时间复杂度：$O(m \log m+n \log n)$，其中$m$和$n$分别是两个数组的长度。对两个数组进行排序的时间复杂度是$O(m \log m+n \log n)$，遍历两个数组的时间复杂度是$O(m+n)$，因此总时间复杂度是$O(m \log m+n \log n)$。

- 空间复杂度：$O(\min(m,n))$，其中$m$和$n$分别是两个数组的长度。为返回值创建一个数组 `intersection`，其长度为较短的数组的长度。













## 557. 反转字符串中的单词 III(简单)


给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。

```
示例 1:

输入: "Let's take LeetCode contest"
输出: "s'teL ekat edoCteeL tsetnoc" 
```

**思路与算法：**

字符串转数组，遍历数组，每碰到1次空格反转空格前的单词，因为最后一个单词后面没有空格，遍历结束后再反转1次最后一个单词。

```java
class Solution {
    public String reverseWords(String s) {
        char[] charArray = s.toCharArray();
        int n = charArray.length;
        int left = 0, right = 0;
        char temp;

        for (int i = 0; i < n; i++) {
            if (charArray[i] == ' ') {
                right = i - 1;
                while (left < right) {
                    temp = charArray[left];
                    charArray[left++] = charArray[right];
                    charArray[right--] = temp;  
                }
                left = i + 1;
            }
        }

        // 最后一个单词后面没有空格
        right = n - 1;
        while (left < right) {
            temp= charArray[left];
            charArray[left++] = charArray[right];
            charArray[right--] = temp;
        }

        return new String(charArray);
    }
}
```



## 876. 链表的中间结点(简单)

给定一个带有头结点head的非空单链表，返回链表的中间结点。如果有两个中间结点，则返回第二个中间结点。

 
```
示例 1：

输入：[1,2,3,4,5]
输出：此列表中的结点3(序列化形式：[3,4,5])
解释：返回的结点值为3。(测评系统对该结点序列化表述是[3,4,5])。
注意，我们返回了一个ListNode类型的对象ans，这样：`ans.val = 3, ans.next.val = 4, ans.next.next.val = 5`以及`ans.next.next.next = NULL`。

示例 2：

输入：[1,2,3,4,5,6]
输出：此列表中的结点 4 (序列化形式：[4,5,6])
由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
```

提示：给定链表的结点数介于1和100之间。


**思路与算法：快慢指针法**

用两个指针`slow`与`fast`一起遍历链表。`slow`**一次走一步**，`fast`**一次走两步**。那么当`fast`到达链表的末尾时，`slow`必然位于中间。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            // 保证了如果有两个中间结点，则返回第二个中间结点
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是给定链表的结点数目。

- 空间复杂度：$O(1)$，只需要常数空间存放`slow`和`fast`两个指针。


## 977. 有序数组的平方(简单)

给定一个按**非递减**顺序排序的整数数组A，返回每个数字的平方组成的新数组，要求也**按非递减**顺序排序。

```
示例 1：

输入：[-4,-1,0,3,10]
输出：[0,1,9,16,100]

示例 2：

输入：[-7,-3,2,3,11]
输出：[4,9,9,49,121]
```

提示：

- $1 <= A.length <= 10000$
- $-10000 <= A[i] <= 10000$
- A已按非递减顺序排序。


**思路与算法：**

因为**数组A已经排好序了**，所以可以说**数组中的负数已经按照平方值降序排好了，数组中的非负数已经按照平方值升序排好了**。

举一个例子，若给定数组为`[-3, -2, -1, 4, 5, 6]`，数组中负数部分`[-3, -2, -1]`的平方为`[9, 4, 1]`，数组中非负部分`[4, 5, 6]`的平方为`[16, 25, 36]`。我们的策略就是**从前向后遍历数组中的非负数部分**，并且**反向遍历数组中的负数部分**。


```java
class Solution {
    public int[] sortedSquares(int[] A) {
        int N = A.length;
        // 指针j正向读取非负数部分
        int j = 0;
        while (j < N && A[j] < 0) {
            j++;
        }

        // 指针i反向读取负数部分
        int i = j - 1;
        int[] ans = new int[N];
        int temp = 0;

        while (i >= 0 && j < N) {
            if (A[i] * A[i] < A[j] * A[j]) {
                ans[temp++] = A[i] * A[i];
                i--;
            } else {
                ans[temp++] = A[j] * A[j];
                j++;
            }
        }

        while (i >= 0) {
            ans[temp++] = A[i] * A[i];
            i--;
        }
        while (j < N) {
            ans[temp++] = A[j] * A[j];
            j++;
        }

        return ans;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是数组A的长度。
- 空间复杂度：$O(N)$。


## 1470. 重新排列数组(简单)

给你一个数组nums，数组中有$2n$个元素，按$[x_1, x_2,..., x_n, y_1, y_2,..., y_n]$的格式排列。

请你将数组按$[x_1, y_1, x_2, y_2,..., x_n, y_n]$格式重新排列，返回重排后的数组。

 
```
示例 1：

输入：nums = [2,5,1,3,4,7], n = 3
输出：[2,3,5,4,1,7] 
解释：由于 x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 ，所以答案为 [2,3,5,4,1,7]

示例 2：

输入：nums = [1,2,3,4,4,3,2,1], n = 4
输出：[1,4,2,3,3,2,4,1]

示例 3：

输入：nums = [1,1,2,2], n = 2
输出：[1,2,1,2]
```

提示：
```
1 <= n <= 500
nums.length == 2n
1 <= nums[i] <= 10^3
```


```java
class Solution {
    public int[] shuffle(int[] nums, int n) {
        int temp[] = new int[nums.length];
        int index = 0;
        for(int i = 0;i < n;i++){
            temp[index++] = nums[i];
            temp[index++] = nums[i + n];
        }
        return temp;
    }
}
```




# 滑动窗口


## 28. 实现 strStr()(简单)

实现 strStr() 函数。

给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。

```
示例 1:

输入: haystack = "hello", needle = "ll"
输出: 2
示例 2:

输入: haystack = "aaaaa", needle = "bba"
输出: -1
```


说明:

当 needle 是空字符串时，我们应当返回什么值呢？

对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。


**方法一：子串逐一比较 - 线性时间复杂度**

最直接的方法 - 沿着字符换逐步移动滑动窗口，将窗口内的子串与 needle 字符串比较。

<div align=center><img src=LeetCode\28.png></div>

```java {.line-numbers highlight=6}
class Solution {
  public int strStr(String haystack, String needle) {
    int L = needle.length(), n = haystack.length();

    for (int start = 0; start < n - L + 1; start++) {
      if (haystack.substring(start, start + L).equals(needle)) {
        return start;
      }
    }
    return -1;
  }
}
```

**复杂度分析**

- 时间复杂度：$O((N - L)L)$，其中 N 为 haystack 字符串的长度，L 为 needle 字符串的长度。内循环中比较字符串的复杂度为 L，总共需要比较 (N - L) 次。

- 空间复杂度：$O(1)$。



**方法二：双指针 - 线性时间复杂度**

上一个方法的缺陷是会将 haystack 所有长度为 L 的子串都与 needle 字符串比较，实际上是不需要这么做的。

- 首先，**只有haystack子串的第一个字符跟 needle 字符串第一个字符相同的时候才需要比较**。

<div align=center><img src=LeetCode\28_2.png></div>

- 其次，可以一个字符一个字符比较，一旦不匹配了就立刻终止。

<div align=center><img src=LeetCode\28_3.png></div>

如下图所示，比较到最后一位时发现不匹配，这时候开始回溯。需要注意的是，**pn 指针是移动到 `pn = pn - curr_len + 1` 的位置，而 不是 `pn = pn - curr_len` 的位置**。

<div align=center><img src=LeetCode\28_4.png></div>

这时候再比较一次，就找到了完整匹配的子串，直接**返回子串的开始位置 pn - L**。

<div align=center><img src=LeetCode\28_5.png></div>

```java
class Solution {
    public int strStr(String haystack, String needle) {
        int L = needle.length(), n = haystack.length();
        if (L == 0) {
            return 0;
        }

        int pn = 0;
        while (pn < n - L + 1) {
            // find the position of the first needle character in the haystack string
            while (pn < n - L + 1 && haystack.charAt(pn) != needle.charAt(0)) {
                ++pn;
            }

            // compute the max match string
            int currLen = 0, pL = 0;
            while (pL < L && pn < n && haystack.charAt(pn) == needle.charAt(pL)) {
                ++pn;
                ++pL;
                ++currLen;
            }

            // if the whole needle string is found, return its start position
            if (currLen == L) {
                return pn - L;
            }

            // otherwise, backtrack
            pn = pn - currLen + 1;
        }
        
        return -1;
    }
}
```

**复杂度分析**

- 时间复杂度：最坏时间复杂度为$O((N - L)L)$，最优时间复杂度为$O(N)$。

- 空间复杂度：$O(1)$。


## 121. *买卖股票的最佳时机(简单)

给定一个数组，它的第i个元素是一支给定股票第i天的价格。

如果你**最多只允许完成一笔交易**（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。

注意：你不能在买入股票前卖出股票。


```
示例 1:

输入: [7,1,5,3,6,4]
输出: 5

解释: 在第2天（股票价格 = 1）的时候买入，在第5天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5。
注意：利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。


示例 2:

输入: [7,6,4,3,1]
输出: 0
解释: 在这种情况下, 没有交易完成, 所以最大利润为0。
```


- 我们需要找出给定数组中两个数字之间的最大差值（即，最大利润）。此外，第二个数字（卖出价格）必须大于第一个数字（买入价格）。
- 用一个变量记录一个历史最低价格`minPrice`，那么在第i天卖出股票能得到的利润就是`prices[i] - minprice`。

```java
package solution;

/**
 * leetcode_121_买卖股票的最佳时机
 * @author Chenzf 
 * @date 2020/7/29
 * @version 1.0
 */

public class BestTimeBuySellStock {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxPrice = 0;
        
        for (int i = 0; i < prices.length; i++) {
            // 确保了买进在卖出之前
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxPrice) {
                maxPrice = prices[i] - minPrice;
            }
        }
        
        return maxPrice;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，**只需要遍历一次**。
- 空间复杂度：$O(1)$，**只使用了常数个变量**。







# 链表


## 21. 合并两个有序链表(简单)

**题目：**

将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

**示例：**

```
输入：1->2->4  1->3->4
输出：1->1->2->3->4->4
```

### 递归

```java
package solution;

/**
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0 递归
 */

public class MergeTwoSortedList {
    public ListNode mergeTwoLists(ListNode listNode1, ListNode listNode2) {
        if (listNode1 == null) {
            return listNode2;
        } else if (listNode2 == null) {
            return listNode1;
        } else if (listNode1.val < listNode2.val) {
            listNode1.next = mergeTwoLists(listNode1.next, listNode2);
            return listNode1;
        } else {
            listNode2.next = mergeTwoLists(listNode1, listNode2.next);
            return listNode2;
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n + m)$，其中$n$和$m$分别为两个链表的长度。因为每次调用递归都会去掉`listNode1`或者`listNode2`的头节点（直到至少有一个链表为空），函数`mergeTwoList`至多只会递归调用每个节点一次。因此，**时间复杂度取决于合并后的链表长度**，即$O(n+m)$。

- 空间复杂度：$O(n + m)$，其中$n$和$m$分别为两个链表的长度。**递归调用`mergeTwoList`函数时需要消耗栈空间，栈空间的大小取决于递归调用的深度**。结束递归调用时`mergeTwoList`函数最多调用$n+m$次，因此空间复杂度为$O(n+m)$。


### 迭代

- 首先，我们设定一个哨兵节点`prehead`，这可以在最后让我们比较容易地返回合并后的链表。
- 我们维护一个`prev`指针，我们需要做的是调整它的`next`指针。
- 然后，我们重复以下过程，直到`l1`或者`l2`指向了`null`：
  - 如果`l1`当前节点的值小于等于`l2`，我们就把`l1`当前的节点接在`prev`节点的后面，同时将`l1`指针往后移一位。
  - 否则，我们对`l2`做同样的操作。
  - **不管我们将哪一个元素接在了后面，我们都需要把`prev`向后移一位**。

在循环终止的时候，`l1`和`l2`至多有一个是非空的。由于输入的两个链表都是有序的，所以不管哪个链表是非空的，它包含的所有元素都比前面已经合并链表中的所有元素都要大。这意味着我们只需要简单地将非空链表接在合并链表的后面，并返回合并链表即可。

<div align=center><img src=LeetCode\21.gif></div>

```java
package solution;

/**
 * @author Chenzf
 * @date 2020/7/25
 * @version 2.0 迭代
 */

public class MergeTwoSortedList_v2 {
    public ListNode mergeTwoLists(ListNode listNode1, ListNode listNode2) {
        ListNode preHead = new ListNode(-1);
        ListNode prev = preHead;

        while (listNode1 != null && listNode2 != null) {
            if (listNode1.val <= listNode2.val) {
                prev.next = listNode1;
                listNode1 = listNode1.next;
            } else {
                prev.next = listNode2;
                listNode2 = listNode2.next;
            }

            prev = prev.next;
        }

        // 此时l1和l2只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = (listNode1 == null) ? listNode2 : listNode1;
        
        return preHead.next;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n + m)$，其中$n$和$m$分别为两个链表的长度。因为每次循环迭代中，`listNode1`和`listNode2`只有一个元素会被放进合并链表中， 因此`while`循环的次数不会超过两个链表的长度之和。所有其他操作的时间复杂度都是常数级别的，因此总的时间复杂度为$O(n+m)$。

- 空间复杂度：$O(1)$。我们只需要常数的空间存放若干变量。



## 83. 删除排序链表中的重复元素(简单)

给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。

```
示例 1:

输入: 1->1->2
输出: 1->2
示例 2:

输入: 1->1->2->3->3
输出: 1->2->3
```

由于输入的列表**已排序**，因此我们可以通过将**结点的值**与**它之后的结点**进行比较来确定它是否为重复结点。如果它是重复的，我们更改当前结点的next指针，以便它跳过下一个结点并直接指向下一个结点之后的结点。


```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.next.val == current.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，因为列表中的每个结点都检查一次以确定它是否重复，所以总运行时间为$O(n)$，其中$n$是列表中的结点数。

- 空间复杂度：$O(1)$，没有使用额外的空间。




## 141. *环形链表(简单)

给定一个链表，判断链表中是否有环。

为了表示给定链表中的环，我们使用整数pos来表示**链表尾连接到链表中的位置**（索引从0开始）。 如果pos是-1，则在该链表中没有环。

 
```
示例 1：

输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。
```

<div align=center><img src=LeetCode\141_1.png width=50%></div>


**思路与算法：**

通过使用具有不同速度的**快、慢两个指针**遍历链表，空间复杂度可以被降低至$O(1)$。**慢指针每次移动一步，而快指针每次移动两步**。

- 如果列表中不存在环，最终快指针将会最先到达尾部，此时我们可以返回false。

- 现在考虑一个环形链表，把慢指针和快指针想象成两个在环形赛道上跑步的运动员（分别称之为慢跑者与快跑者）。而**快跑者最终一定会追上慢跑者**。这是为什么呢？考虑下面这种情况（记作情况 A）- 假如快跑者只落后慢跑者一步，在下一次迭代中，它们就会分别跑了一步或两步并相遇。

<div align=center><img src=LeetCode\141.gif></div>

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        // 让快指针先走一步，可以进入while循环
        ListNode fast = head.next;
        
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            // 慢指针每次移动一步，而快指针每次移动两步
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，让我们将$n$设为链表中结点的总数。为了分析时间复杂度，我们分别考虑下面两种情况。

    - 链表中不存在环：快指针将会首先到达尾部，其时间取决于列表的长度，也就是$O(n)$。

    - 链表中存在环：我们将慢指针的移动过程划分为两个阶段：非环部分与环形部分：

        - 慢指针在走完非环部分阶段后将进入环形部分：此时，快指针已经进入环中：$\text{迭代次数} = \text{非环部分长度} = N$

        - 两个指针都在环形区域中：考虑两个在环形赛道上的运动员 - 快跑者每次移动两步而慢跑者每次只移动一步。其**速度的差值为1**，因此需要经过$\dfrac{\text{二者之间距离}}{\text{速度差值}}$次循环后，快跑者可以追上慢跑者。这个距离几乎就是$\text{环形部分长度 K}$，且速度差值为1，我们得出这样的结论$\text{迭代次数}=近似于\text{环形部分长度 K}$。

    - 因此，在最糟糕的情形下，时间复杂度为$O(N+K)$，也就是$O(n)$。

- 空间复杂度：$O(1)$，我们只使用了慢指针和快指针两个结点，所以空间复杂度为$O(1)$。




## 160. *相交链表(简单)

**题目描述：**

编写一个程序，找到两个单链表相交的起始节点。

如下面的两个链表：

<div align=center><img src=LeetCode\160.png width=60%></div>

在节点c1开始相交。

示例1：
<div align=center><img src=LeetCode\160_1.png width=60%></div>
```
输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5]
输出：Reference of the node with value = 8
解释：相交节点的值为8（注意，如果两个链表相交则不能为0）。从各自的表头开始算起，在A中，相交节点前有2个节点；在B中，相交节点前有3个节点。
```

示例2：
<div align=center><img src=LeetCode\160_2.png width=50%></div>
```
输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4]
输出：Reference of the node with value = 2
解释：相交节点的值为2（注意，如果两个链表相交则不能为 0）。从各自的表头开始算起，在A中，相交节点前有3个节点；在B中，相交节点前有1个节点。
```

示例3：
<div align=center><img src=LeetCode\160_3.png width=30%></div>
```
输入：intersectVal = 0, listA = [2,6,4], listB = [1,5]
输出：null
解释：从各自的表头开始算起，由于这两个链表不相交，所以intersectVal为0
```

**思路与算法：**

- 设定两个指针分别指向两个链表头部，一起向前走直到其中一个到达末端，另一个与末端距离则是两链表的**长度差**。再通过**长链表指针先走**的方式**消除长度差**，**最终两链表即可同时走到相交点**。

- 换个方式**消除长度差**：**类似拼接两链表**
  - 设长-短链表为C，短-长链表为D（分别代表长链表在前和短链表在前的拼接链表），则当C走到长短链表交接处时(C与D速度相同)，D在长链表中，此时**D与长链表头距离为长度差**
  - **<font color=red>短链表走完后，从长链表头开始接着走；当长链表走完后，从短链表头开始走时，此时两者剩余路程相同</font>**！

<div align=center><img src=LeetCode\160_4.png width=60%></div>

<div align=center><img src=LeetCode\160_5.png></div>

```java
package solution;

/**
 * leetcode_160_相交链表
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0
 */

public class GetIntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pointA = headA, pointB = headB;
        // 速度相同，走过相同的路，且有交点，则注定会相遇
        while (pointA != pointB) {
            pointA = (pointA == null) ? headB : pointA.next;
            pointB = (pointB == null) ? headA : pointB.next;
        }

        return pointA;
    }
}
```


## 203. 移除链表元素(简单)


删除链表中等于给定值val的所有节点。

```
示例:

输入: 1->2->6->3->4->5->6, val = 6
输出: 1->2->3->4->5
```


### 双指针

如果删除的节点是中间的节点，则问题似乎非常简单：

- 选择要删除节点的前一个结点`prev`。
- 将`prev`的`next`设置为要删除结点的`next`。

<div align=center><img src=LeetCode\203_1.jpg></div>

当要删除的一个或多个节点位于链表的头部时，事情会变得复杂：

<div align=center><img src=LeetCode\203_2.jpg></div>

可以通过**哨兵节点**去解决它，哨兵节点广泛应用于树和链表中，如伪头、伪尾、标记等，它们是纯功能的，通常不保存任何数据，其主要目的是使链表标准化，如使链表永不为空、永不无头、简化插入和删除。

<div align=center><img src=LeetCode\203_3.jpg></div>

在这里哨兵节点将被用于**伪头**。

算法：

- 初始化哨兵节点为`ListNode(0)`且设置`sentinel.next = head`。
- 初始化两个指针`curr`和`prev`指向当前节点和前继节点。
- 当`curr != null`：
  - 比较当前节点和要删除的节点：
    - 若当前节点就是要删除的节点：则`prev.next = curr.next`。
    - 否则设`prve = curr`。
  - 遍历下一个元素：`curr = curr.next`。
- 返回`sentinel.next`。


```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
  public ListNode removeElements(ListNode head, int val) {
    ListNode sentinel = new ListNode(0);
    sentinel.next = head;

    ListNode prev = sentinel, curr = head;
    while (curr != null) {
      if (curr.val == val) {
          prev.next = curr.next;
      } else {
          prev = curr;
      }
      curr = curr.next;
    }
    return sentinel.next;
  }
}
```


**复杂度分析**

- 时间复杂度：$\mathcal{O}(N)$，只遍历了一次。
- 空间复杂度：$\mathcal{O}(1)$。




## 206. 反转链表(简单)

**题目：**

反转一个单链表。

```
输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
```

**思路与算法：**

### 双指针迭代

<div align=center><img src=LeetCode\206.gif></div>

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

package solution;

/**
 * leetcode_206_反转链表
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0 迭代
 */

public class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        return prev;
    }
}

```

时间复杂度：$O(n)$，假设$n$是列表的长度，时间复杂度是$O(n)$。
空间复杂度：$O(1)$。


### 递归

递归版本稍微复杂一些，其关键在于反向工作。

**假设列表的其余部分已经被反转，现在我该如何反转它前面的部分**？

假设列表为：$n_{1}\rightarrow ... \rightarrow n_{k-1} \rightarrow n_{k} \rightarrow n_{k+1} \rightarrow ... \rightarrow n_{m} \rightarrow \varnothing$

若从节点$n_{k+1}$到$n_{m}$已经被反转，而我们正处于$n_{k}$：$n_{1}\rightarrow ... \rightarrow n_{k-1} \rightarrow n_{k} \rightarrow n_{k+1} \leftarrow ... \leftarrow n_{m}$

我们希望$n_{k+1}$的下一个节点指向$n_{k}$，所以，$n_{k}.next.next = n_{k}$($n_{k}.next => n_{k+1}$)。

要小心的是$n_{1}$的下一个必须指向$Ø$。如果你忽略了这一点，你的链表中可能会产生循环。如果使用大小为2的链表测试代码，则可能会捕获此错误。

<div align=center><img src=LeetCode\206_1.gif></div>

```java
class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        
        ListNode cur =reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return cur;
    }
}
```

时间复杂度：$O(n)$，假设$n$是列表的长度，那么时间复杂度为$O(n)$。
空间复杂度：$O(n)$，由于使用递归，将会**使用隐式栈空间**。递归深度可能会达到$n$层。



## 234. 回文链表(简单)

请判断一个链表是否为回文链表。

```
示例 1:
输入: 1->2
输出: false

示例 2:
输入: 1->2->2->1
输出: true
```

### 将值复制到数组中后用双指针法

有两种常用的列表实现，一种是**数组列表**和**链表**。如果我们想在列表中存储值，那么它们是如何保存的呢？

数组列表底层是使用数组存储值，我们可以**通过索引**在$O(1)$的时间访问列表任何位置的值，这是由于**内存寻址**的方式。

链表存储的是称为节点的对象，每个节点保存一个值和指向下一个节点的指针。访问某个特定索引的节点需要$O(n)$的时间，因为要通过指针获取到下一个位置的节点。

确定数组列表是否为回文很简单，我们可以**使用双指针法来比较两端的元素，并向中间移动**。一个指针从起点向中间移动，另一个指针从终点向中间移动。这需要$O(n)$的时间，因为访问每个元素的时间是$O(1)$，而有$n$个元素要访问。

然而，直接在链表上操作并不简单，因为不论是正向访问还是反向访问都不是$O(1)$。而**将链表的值复制到数组列表中是$O(n)$**，因此最简单的方法就是将链表的值复制到数组列表中，再使用双指针法判断。

```java {.line-numbers highlight=27-29}
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_234_回文链表
 * @author Chenzf
 * @date 2020/7/30
 * @version 1.0 将值复制到数组中后用双指针法
 */

public class PalindromeLinkedList {
    public boolean isPalindrome(ListNode head) {
        List<Integer> arrayList = new ArrayList<>();

        // Convert LinkedList into ArrayList.
        ListNode currentNode = head;
        while (currentNode != null) {
            arrayList.add(currentNode.val);
            currentNode = currentNode.next;
        }

        // Use two-pointer technique to check for palindrome.
        int left = 0, right = arrayList.size() - 1;
        while (left < right) {
            // Note that we must use ! .equals instead of !=
            // because we are comparing Integer, not int.
            if (! arrayList.get(left).equals(arrayList.get(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$指的是链表的元素个数。
  - 第一步： 遍历链表并将值复制到数组中，$O(n)$。
  - 第二步：双指针判断是否为回文，执行了$O(n/2)$次的判断，即$O(n)$。
  - 总的时间复杂度：$O(2n) = O(n)$。
- 空间复杂度：$O(n)$，其中$n$指的是链表的元素个数，我们使用了一个数组列表存放链表的元素值。


### 将链表的后半部分反转

可以将链表的后半部分反转（修改链表结构），然后将前半部分和后半部分进行比较。比较完成后我们应该将链表恢复原样。虽然不需要恢复也能通过测试用例，因为使用该函数的人不希望链表结构被更改。

可以分为以下几个步骤：

- 找到前半部分链表的尾节点。
- 反转后半部分链表。
- 判断是否为回文。
- 恢复链表。
- 返回结果。


步骤一，我们可以**计算链表节点的数量**，然后遍历链表找到前半部分的尾节点。或者可以**使用快慢指针**在一次遍历中找到：**慢指针一次走一步，快指针一次走两步**，快慢指针同时出发。当快指针移动到链表的末尾时，慢指针到链表的中间。通过慢指针将链表分为两部分。若链表有奇数个节点，则中间的节点应该看作是前半部分。

步骤二，可以使用在反向链表问题中找到解决方法来反转链表的后半部分。

步骤三，比较两个部分的值，当后半部分到达末尾则比较完成，可以忽略计数情况中的中间节点。

步骤四与步骤二使用的函数相同，再反转一次恢复链表本身。

```java
package solution;

/**
 * leetcode_234_回文链表
 * @author Chenzf
 * @date 2020/7/30
 * @version 2.0 将链表的后半部分反转
 */

public class PalindromeLinkedList_v2 {
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        // Find the end of first half
        ListNode firstHalfEnd = findEndOfFirstHalf(head);
        // Reverse second half
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // Check whether or not there is a palindrome.
        ListNode point1 = head;
        ListNode point2 = secondHalfStart;
        boolean result = true;

        while (result && point2 != null) {
            if (point1.val != point2.val) {
                result = false;
            }

            point1 = point1.next;
            point2 = point2.next;
        }

        // Restore the list and return the result.
        firstHalfEnd.next = reverseList(secondHalfStart);
        
        return result;
    }

    /**
     * 使用快慢指针在一次遍历中找到：慢指针一次走一步，快指针一次走两步，快慢指针同时出发。
     * 当快指针移动到链表的末尾时，慢指针到链表的中间。通过慢指针将链表分为两部分。
     */
    private ListNode findEndOfFirstHalf(ListNode head) {
        ListNode fastPoint = head;
        ListNode slowPoint = head;
        while (fastPoint.next != null && fastPoint.next.next != null) {
            fastPoint = fastPoint.next.next;
            slowPoint = slowPoint.next;
        }

        return slowPoint;
    }

    /**
     * 反转链表
     */
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode temp = null;
        while (curr != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        return prev;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$指的是链表的大小。
- 空间复杂度：$O(1)$，我们是一个接着一个的改变指针，在堆栈上的堆栈帧不超过$O(1)$。

该方法的缺点是，在并发环境下，函数运行时需要锁定其他线程或进程对链表的访问，因为在函数执执行过程中链表暂时断开。



## 237. 删除链表中的节点(简单)

编写一个函数，使其可以删除某个链表中给定的（非末尾）节点。传入函数的唯一参数为要被删除的节点。

现有一个链表：head = [4,5,1,9]，它可以表示为:

<div align=center><img src=LeetCode\237.png width=50%></div>

```
示例 1：

输入：head = [4,5,1,9], node = 5
输出：[4,1,9]
解释：给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9。

示例 2：

输入：head = [4,5,1,9], node = 1
输出：[4,5,9]
解释：给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
```

提示：

- 链表至少包含两个节点。
- 链表中所有节点的值都是唯一的。
- 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
- 不要从你的函数中返回任何结果。



**思路与算法：**

从链表里删除一个节点 node 的最常见方法是**修改之前节点的 next 指针，使其指向之后的节点**。
<div align=center><img src=LeetCode\237_1.png width=50%></div>

因为，我们**无法访问我们想要删除的节点 之前 的节点**，我们始终不能修改该节点的 next 指针。

**我们可以将想要删除的节点的值替换为它后面节点中的值，然后删除它之后的节点**。


<div align=center><img src=LeetCode\237_3.jpg width=50%></div>


```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
```

时间和空间复杂度都是：$O(1)$。



## 876. 链表的中间结点(简单)

给定一个带有头结点head的非空单链表，返回链表的中间结点。如果有两个中间结点，则返回第二个中间结点。

 
```
示例 1：

输入：[1,2,3,4,5]
输出：此列表中的结点3(序列化形式：[3,4,5])
解释：返回的结点值为3。(测评系统对该结点序列化表述是[3,4,5])。
注意，我们返回了一个ListNode类型的对象ans，这样：`ans.val = 3, ans.next.val = 4, ans.next.next.val = 5`以及`ans.next.next.next = NULL`。

示例 2：

输入：[1,2,3,4,5,6]
输出：此列表中的结点 4 (序列化形式：[4,5,6])
由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
```

提示：给定链表的结点数介于1和100之间。


**思路与算法：快慢指针法**

用两个指针`slow`与`fast`一起遍历链表。`slow`**一次走一步**，`fast`**一次走两步**。那么当`fast`到达链表的末尾时，`slow`必然位于中间。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            // 保证了如果有两个中间结点，则返回第二个中间结点
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是给定链表的结点数目。

- 空间复杂度：$O(1)$，只需要常数空间存放`slow`和`fast`两个指针。





## 1290. 二进制链表转整数(简单)

给你一个单链表的引用结点head。链表中每个结点的值不是0就是1。已知此链表是一个整数数字的二进制表示形式。请你返回该链表所表示数字的十进制值 。

```
示例 1：
输入：head = [1,0,1]
输出：5
解释：二进制数 (101) 转化为十进制数 (5)
```

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int getDecimalValue(ListNode head) {
        ListNode current = head;
        int sum = 0;

        while(current != null){
            sum = sum * 2 + current.val;
            current = current.next;
        }
        return sum;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是链表中的节点个数。

- 空间复杂度：$O(1)$。


## 1474. 删除链表M个节点后的N个节点(简单)

Given the head of a linked list and two integers m and n. Traverse the linked list and remove some nodes in the following way:

- Start with the head as the current node.
- Keep the first $m$ nodes starting with the current node.
- Remove the next $n$ nodes
- Keep repeating steps 2 and 3 until you reach the end of the list.
- Return the head of the modified list after removing the mentioned nodes.

<div align=center><img src=LeetCode\1474_1.png></div>
```
Example 1:
Input: head = [1,2,3,4,5,6,7,8,9,10,11,12,13], m = 2, n = 3
Output: [1,2,6,7,11,12]
Explanation: Keep the first (m = 2) nodes starting from the head of the linked List  (1 ->2) show in black nodes.
Delete the next (n = 3) nodes (3 -> 4 -> 5) show in read nodes.
Continue with the same procedure until reaching the tail of the Linked List.
Head of linked list after removing nodes is returned.
```

<div align=center><img src=LeetCode\1474_2.png></div>
```
Example 2:
Input: head = [1,2,3,4,5,6,7,8,9,10,11], m = 1, n = 3
Output: [1,5,9]
Explanation: Head of linked list after removing nodes is returned.
```

```java
class Solution {
    int mm, nn;
    public ListNode deleteNodes(ListNode head, int m, int n) {
        mm = m; // 将m和n保存为全局变量
        nn = n;
        return helper(head, m, n); // 递归求解
    }
    
    ListNode helper(ListNode head, int m, int n) {
        if (head == null) return null; // 当前节点为空，返回空
        if (n == 0) { // 如果已删除完n个节点，复原m和n值继续
            m = mm;
            n = nn;
        }
        if (m > 0) { // 如果m大于0，当前元素需要保留
            // 当前节点的下一节点为递归返回值
            head.next = helper(head.next, m-1, n);
            // 返回当前节点
            return head;
        } else { // 如果m等于0，当前元素需要删除
            // 递归到下一节点，递归返回之为当前返回值。
            return helper(head.next, m, n-1);
        }
    }
}
```




# 哈希表

## 常用方法

`map.containsKey(string.charAt(i))`
`map.put(string.charAt(i), map.get(string.charAt(i)) + 1)`
`count.put(c, count.getOrDefault(c, 0) + 1);`

```java
Map<Integer, Integer> counts = countNums(nums);
Map.Entry<Integer, Integer> majorityEntry = null;

for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
    if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
        majorityEntry = entry;
    }
}
```

```java
public int[] set_intersection(HashSet<Integer> set1, HashSet<Integer> set2) {
    int [] output = new int[set1.size()];
    int index = 0;
    for (Integer s : set1) {
        if (set2.contains(s)) {
            output[index++] = s;
        }
    }
    return Arrays.copyOf(output, index);
}
```

```java
for (int i = 0; i < n; i++) {
    char c = s.charAt(i);
    count.put(c, count.getOrDefault(c, 0) + 1);
}
```


## 遍历添加元素

`242：有效的字母异位词(简单)`

```java {.line-numbers highlight=11-14}
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }

        HashMap<Character,Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            // 如果map中不包含s当前的字符，则添加
            if(!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), 1);
            } else {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
        }

        for(int i = 0; i < t.length(); i++) {
            // 如果map中不包含t当前的字符，则添加
            if(!map.containsKey(t.charAt(i))){
                map.put(t.charAt(i), 1);
            }
            map.put(t.charAt(i), map.get(t.charAt(i)) - 1);
        }

        ArrayList<Integer> list = new ArrayList<>(map.values());

        for(int a : list) {
            if(a != 0) {
                return false;
            }
        }

        return true;
    }
}
```



## 1. 两数之和(简单)

给定一个整数数组`nums`和一个目标值`target`，请你在该数组中找出**和为目标值**的那**两个整数**，并返回他们的数组**下标**。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

**示例**：
```
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```

**思路与算法：**

**暴力法**很简单，遍历每个元素$x$，并查找是否存在一个值与$target - x$相等的目标元素。


```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
```

时间复杂度：$O(n^2)$，空间复杂度：$O(1)$。

### HashMap

为了对运行时间复杂度进行优化，我们需要一种更有效的方法来**检查数组中是否存在目标元素**。如果存在，我们需要找出它的索引。保持数组中的每个**元素**与其**索引**相互对应的最好方法是什么？哈希表。

通过**以空间换取速度**的方式，我们可以将查找时间从$O(n)$降低到$O(1)$。哈希表正是为此目的而构建的，它支持**以近似恒定的时间进行快速查找**。用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到$O(n)$。但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为$O(1)$。

一个简单的实现使用了**两次迭代**。在**第一次迭代**中，我们**将每个元素的值和它的索引添加到表中**。然后，在**第二次迭代**中，我们将**检查**每个元素所对应的目标元素$(target - nums[i])$是否存在于表中。注意，**该目标元素不能是$nums[i]$本身**！

```java
hashMap.put(nums[i], i);
hashMap.containsKey(complement)
hashMap.get(complement) != i
throw new IllegalArgumentException("No two sum solution");
```

**代码：**
```java {.line-numbers highlight=30}
import java.util.Map;
import java.util.HashMap;

/**
 * leetcode_1_两数之和
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 */

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        // 创建一个HashMap来存储数据，便于查找
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // V put(K key, V value)
            hashMap.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // 如果hashMap中存在complement且不是nums[i]本身
            // hashMap.get(Object Key)
            if (hashMap.containsKey(complement) && hashMap.get(complement) != i) {
                return new int[] {i, hashMap.get(complement)};
            }
        }

        // Missing return statement
        throw new IllegalArgumentException("No two sum solution");
    }
}
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Chenzf
 * @date 2020/7/11
 */

public class TestTwoSum {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待测数据：");
            String[] strings = reader.readLine().split(" ");
            int[] nums = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                nums[i] = Integer.parseInt(strings[i]);
            }
            System.out.println("请输入目标值：");
            int target = Integer.parseInt(reader.readLine());

            // System.out.println(TwoSum.twoSum(nums, target)); // [I@16d3586

            int[] results = TwoSum.twoSum(nums, target);
            for (int result : results) {
                System.out.print(result + " ");
            }

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
```

**复杂度分析：**

时间复杂度：$O(n)$
我们把包含有$n$个元素的列表遍历两次。由于**哈希表将查找时间缩短到**$O(1)$，所以时间复杂度为$O(n)$。

空间复杂度：$O(n)$
所需的额外空间**取决于哈希表中存储的元素数量**，该表中存储了$n$个元素。



## 169. 多数元素(简单)

给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。

你可以假设数组是非空的，并且给定的数组总是存在多数元素。

```
示例 1:

输入: [3,2,3]
输出: 3

示例 2:

输入: [2,2,1,1,1,2,2]
输出: 2
```

使用哈希映射（HashMap）来存储每个元素以及出现的次数。对于哈希映射中的每个键值对，**键表示一个元素，值表示该元素出现的次数**。

我们用一个循环遍历数组 nums 并将数组中的每个元素加入哈希映射中。在这之后，我们遍历哈希映射中的所有键值对，返回值最大的键。我们同样也可以**在遍历数组 nums 时候使用打擂台的方法，维护最大的值**，这样省去了最后对哈希映射的遍历。


```java {.line-numbers highlight=6}
class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> counts = countNums(nums);
        Map.Entry<Integer, Integer> majorityEntry = null;
        
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }

        return majorityEntry.getKey();
    }

    private Map<Integer, Integer> countNums(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (!counts.containsKey(num)) {
                counts.put(num, 1);
            }
            else {
                counts.put(num, counts.get(num) + 1);
            }
        }
        return counts;
    }
}
```


**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$是数组 nums 的长度。
- 空间复杂度：$O(n)$。


如果将数组 nums 中的所有元素**按照单调递增或单调递减的顺序排序**，那么下标为$\lfloor \dfrac{n}{2} \rfloor$的元素（下标从 0 开始）一定是众数。

```java
class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.(length/2)];
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n\log n)$。将数组排序的时间复杂度为$O(n\log n)$。

- 空间复杂度：$O(\log n)$。如果**使用语言自带的排序算法，需要使用$O(\log n)$的栈空间**。如果自己编写堆排序，则只需要使用$O(1)$的额外空间。




## *202. 快乐数(简单)

编写一个算法来判断一个数 n 是不是快乐数。

「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。

如果 n 是快乐数就返回 True ；不是，则返回 False 。

 
```
示例：

输入：19
输出：true
解释：
12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1
```

**思路与算法：**

从$7$开始。则下一个数字是$49$（因为$7^2=49$），然后下一个数字是$97$（因为$4^2+9^2=97$）。我们可以不断重复该的过程，直到我们得到$1$。因为我们得到了$1$，我们知道 $7$是一个快乐数，函数应该返回 `true`。

<div align=center><img src=Leetcode\202.png></div>


我们从$116$开始。通过反复通过平方和计算下一个数字，我们最终得到$58$，再继续计算之后，我们又回到$58$。由于我们回到了一个已经计算过的数字，可以知道有一个循环，因此不可能达到$1$。所以对于$116$，函数应该返回 `false`。

<div align=center><img src=Leetcode\202_2.png width=70%></div>


根据我们的探索，我们猜测会有以下三种可能：

- 最终会得到1。
- 最终会进入循环。
- 值会越来越大，最后接近无穷大。

第三个情况比较难以检测和处理。我们怎么知道它会继续变大，而不是最终得到1呢？

我们可以仔细想一想，每一位数的最大数字的下一位数是多少。

| Digits |    Largest    | Next |
|:------:|:-------------:|:----:|
|    1   |       9       |  81  |
|    2   |       99      |  162 |
|    3   |      999      |  243 |
|    4   |      9999     |  324 |
|   13   | 9999999999999 | 1053 |


对于3位数的数字，它不可能大于243。这意味着它要么被困在243以下的循环内，要么跌到 1。4位或4位以上的数字在每一步都会丢失一位，直到降到3位为止。所以我们知道，**最坏的情况下，算法可能会在243以下的所有数字上循环，然后回到它已经到过的一个循环或者回到1**。但它不会无限期地进行下去，所以我们排除第三种选择。


算法分为两部分：

- 给一个数字n，它的下一个数字是什么？
- 按照一系列的数字来判断我们是否进入了一个循环。

第1部分我们按照题目的要求做**数位分离**，求平方和。

第2部分可以使用HashSe 完成。每次生成链中的下一个数字时，我们都会检查它是否已经在HashSet中。

- 如果它不在 HashSet 中，我们应该添加它。
- 如果它在 HashSet 中，这意味着我们处于一个循环中，因此应该返回 false。

我们使用 HashSet 而不是向量、列表或数组的原因是因为我们反复检查其中是否存在某数字。**检查数字是否在哈希集中需要$O(1)$的时间**，而对于其他数据结构，则需要$O(n)$的时间。选择正确的数据结构是解决这些问题的关键部分。

```java
class Solution {
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while (n != 1 && !set.contains(n)) {
            set.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    private int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(\log n)$
- 空间复杂度：$O(\log n)$




## 217. 存在重复元素(简单)

给定一个整数数组，判断是否存在重复元素。

如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。

 
```
示例 1:

输入: [1,2,3,1]
输出: true

示例 2:

输入: [1,2,3,4]
输出: false

示例 3:

输入: [1,1,1,3,3,4,3,2,4,2]
输出: true
```

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int x: nums) {
            if (set.contains(x)) return true;
            set.add(x);
        }
        return false;
    }
}
```


**复杂度分析**

- 时间复杂度 : $O(n)$。
search() 和 insert() 各自使用$n$次，每个操作耗费常数时间。

- 空间复杂度 :$O(n)$。哈希表占用的空间与元素数量是线性关系。

注意

对于一些特定的 nn 不太大的测试样例，本方法的运行速度可能会比方法二更慢。这是因为哈希表在维护其属性时有一些开销。要注意，程序的实际运行表现和 Big-O 符号表示可能有所不同。Big-O 只是告诉我们在 充分 大的输入下，算法的相对快慢。因此，在 nn 不够大的情况下， O(n)O(n) 的算法也可以比 O(n \log n)O(nlogn)的更慢。








## 236. 二叉树的最近公共祖先(中等)

**题目描述：**

给定一个二叉树，找到该树中两个指定节点的最近公共祖先。

公共祖先的定义为：“对于有根树T的两个结点p、q，最近公共祖先表示为一个结点x，满足x是 p、q的祖先且**x的深度尽可能大**（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]

<div align=center><img src=LeetCode\236.png></div>

```
示例 1:
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出: 3
解释: 节点5和节点1的最近公共祖先是节点3。

示例 2:
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
输出: 5
解释: 节点5和节点4的最近公共祖先是节点5。因为根据定义最近公共祖先节点可以为节点本身。
```

说明:
- 所有节点的值都是唯一的。
- p、q为不同节点且均存在于给定的二叉树中。


**思路与算法：存储父节点**


可以**用哈希表存储所有节点的父节点**，然后我们就可以**利用节点的父节点信息从$p$结点开始不断往上跳，并记录已经访问过的节点；再从$q$节点开始不断往上跳，如果碰到已经访问过的节点，那么这个节点就是我们要找的最近公共祖先**。

**算法**
- 从根节点开始遍历整棵二叉树，**用哈希表记录每个节点的父节点指针**。
- 从$p$节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。
- 同样，我们再从$q$节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是$p$和$q$的深度最深的公共祖先，即`LCA节点`。


```java
package solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * leetcode_236_二叉树的最近公共祖先
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0 用哈希表存储所有节点的父节点
 */

public class LowestCommonAncestor {
    Map<Integer, TreeNode> parent = new HashMap<>();
    Set<Integer> visited = new HashSet<>();

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针
        deepFirstSearch(root);

        // 从p节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点
        while (p != null) {
            visited.add(p.val);
            // 得到p节点所对应的父节点
            p = parent.get(p.val);
        }

        // 再从q节点开始不断往它的祖先移动
        // 如果有祖先已经被访问过，即意味着这是p和q的深度最深的公共祖先
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }

        return null;
    }

    /**
     * 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针
     */
    public void deepFirstSearch(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            deepFirstSearch(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            deepFirstSearch(root.right);
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是二叉树的节点数。二叉树的所有节点有且只会被访问一次，从$p$和$q$节点往上跳经过的祖先节点个数不会超过$N$，因此总的时间复杂度为$O(N)$。

- 空间复杂度：$O(N)$，其中$N$是二叉树的节点数。
  - **递归调用的栈深度取决于二叉树的高度**，二叉树最坏情况下为一条链，此时高度为$N$，因此空间复杂度为$O(N)$。
  - 哈希表存储每个节点的父节点也需要$O(N)$的空间复杂度
  - 因此最后总的空间复杂度为$O(N)$。









## 268. 缺失数字(简单)

给定一个包含 `0, 1, 2, ..., n` 中 **n 个数**的序列，找出 `0 .. n` 中没有出现在序列中的那个数。

```
示例 1:

输入: [3,0,1]
输出: 2

示例 2:

输入: [9,6,4,2,3,5,7,0,1]
输出: 8
```

```java
class Solution {
    public int missingNumber(int[] nums) {
        Set<Integer> numSet = new HashSet<Integer>();
        for (int num : nums) {
            numSet.add(num);
        }

        int expectedNumCount = nums.length + 1;
        for (int number = 0; number < expectedNumCount; number++) {
            if (!numSet.contains(number)) {
                return number;
            }
        }
        return -1;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$。集合的插入操作的时间复杂度都是$O(1)$，一共插入了$n$个数，时间复杂度为$O(n)$。集合的查询操作的时间复杂度同样是$O(1)$，最多查询$n+1$次，时间复杂度为 $O(n)$。因此总的时间复杂度为$O(n)$。
- 空间复杂度：$O(n)$。集合中会存储$n$个数，因此空间复杂度为$O(n)$。



## 349. 两个数组的交集(简单)

给定两个数组，编写一个函数来计算它们的交集。

```
示例 1：

输入：nums1 = [1,2,2,1], nums2 = [2,2]
输出：[2]

示例 2：

输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出：[9,4]
```

说明：
- 输出结果中的**每个元素一定是唯一的**。
- 我们**可以不考虑输出结果的顺序**。


最直观的方法是迭代并检查第一个数组`nums1`中的每个值是否也存在于`nums2`中。如果存在，则将值添加到输出。这种方法的时间复杂度为$O(n \times m)$，其中$n$和$m$分别为数组`nums1`和`nums2`的长度。

为了在线性时间内解决这个问题，我们使用集合set这一数据结构，该结构可以提供平均时间复杂度为$O(1)$的`in/contains`操作（用于测试某一元素是否为该集合的成员）。

本解法先将两个数组都转换为集合，然后**迭代较小的集合，检查其中的每个元素是否同样存在于较大的集合中**。平均情况下，这种方法的时间复杂度为$O(n+m)$。




```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // 将两个数组都转换为集合
        HashSet<Integer> set1 = new HashSet<Integer>();
        for (Integer n : nums1) {
            set1.add(n);
        }
        HashSet<Integer> set2 = new HashSet<Integer>();
        for (Integer n : nums2) {
            set2.add(n);
        }
        
        // 迭代较小的集合，检查其中的每个元素是否同样存在于较大的集合中
        if (set1.size() < set2.size()) {
            return set_intersection(set1, set2);
        }
        else {
            return set_intersection(set2, set1);
        }
    }
    
    public int[] set_intersection(HashSet<Integer> set1, HashSet<Integer> set2) {
        int [] output = new int[set1.size()];
        int index = 0;
        for (Integer s : set1) {
            if (set2.contains(s)) {
                output[index++] = s;
            }
        }
        return Arrays.copyOf(output, index);
        // return output;
        /*
        输入：[4,9,5] [9,4,9,8,4]
        输出：[4,9,0]
        预期：[9,4]
        */
    }
}
```

**复杂度分析**

- 时间复杂度：$O(m+n)$，其中$n$和$m$是数组的长度。将`nums1`转换为集合需要$O(n)$的时间，类似地，将`nums2`转换为集合需要$O(m)$的时间。而在平均情况下，集合的`in/contains`操作只需要$O(1)$的时间。
- 空间复杂度：$O(m+n)$，最坏的情况是数组中的所有元素都不同。




## 387. 字符串中的第一个唯一字符(简单)

给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

```
示例：

s = "leetcode"
返回 0

s = "loveleetcode"
返回 2
```

算法的思路就是遍历一遍字符串，然后把字符串中每个字符出现的次数保存在一个散列表中。这个过程的时间复杂度为$O(N)$，其中$N$为字符串的长度。

接下来需要再遍历一次字符串，这一次利用散列表来检查遍历的每个字符是不是唯一的。如果当前字符唯一，直接返回当前下标就可以了。第二次遍历的时间复杂度也是$O(N)$。

```java
class Solution {
    public int firstUniqChar(String s) {
        HashMap<Character, Integer> count = new HashMap<Character, Integer>();
        int n = s.length();
        // build hash map : character and how often it appears
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        
        // find the index
        for (int i = 0; i < n; i++) {
            if (count.get(s.charAt(i)) == 1) 
                return i;
        }
        return -1;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$
    只遍历了两遍字符串，同时散列表中查找操作是常数时间复杂度的。

- 空间复杂度：$O(N)$
    用到了散列表来存储字符串中每个元素出现的次数。




## 771. 宝石与石头(简单)

给定字符串`J`代表石头中**宝石的类型**，和字符串`S`代表你拥有的**石头**。 `S`中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。

**`J`中的字母不重复**，`J`和`S`中的所有字符都是字母。**字母区分大小写**，因此"a"和"A"是不同类型的石头。

```
示例 1:

输入: J = "aA", S = "aAAbbbb"
输出: 3

示例 2:

输入: J = "z", S = "ZZ"
输出: 0
```
注意:

- `S`和`J`最多含有50个字母。
- `J`中的字符不重复。


```java
class Solution {
    public int numJewelsInStones(String J, String S) {
        // J宝石类型
        Set<Character> Jset = new HashSet();
        for (char j: J.toCharArray())
            Jset.add(j);

        int ans = 0;
        // S各种石头
        for (char s: S.toCharArray())
            if (Jset.contains(s))
                ans++;
        return ans;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(J.length + S.length))$。$O(J.length)$这部分来自于创建$J$，$O(S.length)$这部分来自于$S$。

- 空间复杂度：$O(J.length)$。


## 804. 唯一摩尔斯密码词(简单)

国际摩尔斯密码定义一种标准编码方式，将每个字母对应于一个由一系列点和短线组成的字符串， 比如: `"a"` 对应 `".-"`，`"b"` 对应 `"-..."`，`"c"` 对应 `"-.-."`等等。

为了方便，所有26个英文字母对应摩尔斯密码表如下：

[".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]

给定一个单词列表，每个单词可以写成每个字母对应摩尔斯密码的组合。例如，"cab" 可以写成 "-.-..--..."，(即 `"-.-." + ".-" + "-..."` 字符串的结合)。我们将这样一个连接过程称作单词翻译。

返回我们可以获得所有词不同单词翻译的数量。

```
例如:
输入: words = ["gin", "zen", "gig", "msg"]
输出: 2
解释: 
各单词翻译如下:
"gin" -> "--...-."
"zen" -> "--...-."
"gig" -> "--...--."
"msg" -> "--...--."

共有 2 种不同翻译, "--...-." 和 "--...--.".
```

注意:
```
单词列表words 的长度不会超过 100。
每个单词 words[i]的长度范围为 [1, 12]。
每个单词 words[i]只包含小写字母。
```

```java
class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        String[] MORSE = new String[] {".-","-...","-.-.","-..",".","..-.","--.", 
                        "....","..",".---","-.-",".-..","--","-.",
                        "---",".--.","--.-",".-.","...","-","..-",
                        "...-",".--","-..-","-.--","--.."};

        Set<String> result = new HashSet();

        for (String word: words) {
            StringBuilder code = new StringBuilder();
            for (char c: word.toCharArray()) {
                code.append(MORSE[c - 'a']);
            }
            result.add(code.toString());
        }

        return result.size();
    }
}
```



## 1436. *旅行终点站(简单)

给你一份旅游线路图，该线路图中的旅行线路用数组`paths`表示，其中`paths[i] = [cityAi, cityBi]`表示该线路将会从`cityAi`直接前往`cityBi`。请你**找出这次旅行的终点站**，即没有任何可以通往其他城市的线路的城市。

**题目数据保证线路图会形成一条不存在循环的线路**，因此只会有一个旅行终点站。

 
```
示例 1：

输入：paths = [["London","New York"], ["New York","Lima"], ["Lima","Sao Paulo"]]
输出："Sao Paulo" 
解释：从 "London" 出发，最后抵达终点站 "Sao Paulo" 。本次旅行的路线是 "London" -> "New York" -> "Lima" -> "Sao Paulo" 。

示例 2：

输入：paths = [["B","C"], ["D","B"], ["C","A"]]
输出："A"
解释：所有可能的线路是：
"D" -> "B" -> "C" -> "A". 
"B" -> "C" -> "A". 
"C" -> "A". 
"A". 
显然，旅行终点站是"A"。

示例 3：

输入：paths = [["A","Z"]]
输出："Z"
```

提示：
```
1 <= paths.length <= 100
paths[i].length == 2
1 <= cityAi.length, cityBi.length <= 10
cityAi != cityBi
所有字符串均由大小写英文字母和空格字符组成。
```

```java
// HashMap.put()
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
// HashMap.get()
public V get(Object key) {
    Node<K, V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}
```

```java
public class Solution {
    public String destCity(List<List<String>> paths) {
        Map<String, String> map = prepare(paths);
        // 出发城市
        String from = paths.get(0).get(0);
        while(true){
            if(!map.containsKey(from))
                return from;
            // get(Object key)
            from = map.get(from);
        }
    }

    private Map<String, String> prepare(List<List<String>> paths){
        Map<String, String> map = new HashMap<>();
        for(List<String> path : paths)
            // key为出发城市；value为到达城市
            map.put(path.get(0), path.get(1));
        return map;
    }
}
```





## 1512. *好数对的数目(简单)

给你一个整数数组`nums`，如果一组数字$(i,j)$满足$nums[i] == nums[j]$且$i < j$，就可以认为这是一组**好数对**。返回好数对的数目。

 
```
示例 1：

输入：nums = [1,2,3,1,1,3]
输出：4
解释：有 4 组好数对，分别是 (0,3), (0,4), (3,4), (2,5) ，下标从 0 开始

示例 2：

输入：nums = [1,1,1,1]
输出：6
解释：数组中的每组数字都是好数对

示例 3：

输入：nums = [1,2,3]
输出：0
```

提示：

- $1 <= nums.length <= 100$
- $1 <= nums[i] <= 100$


```java
default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
            ? v
            : defaultValue;
    }
```

如果`map`中含有指定的`key`，就**返回该`key`对应的`value`**，否则使用该方法的第二个参数作为默认值返回。

```java
@Test
    public void testSix() {
        Map<String, Integer> map = new HashMap<>(3);
        map.put("john", null);
        map.put("jack", 180);
        map.put("jane", 168);

        // 不会使用提供的默认值
        Integer wanna1 = map.getOrDefault("jane", 166);
        // 会使用提供的默认值
        Integer wanna2 = map.getOrDefault("tom", 166);
        // 不会使用提供的默认值
        Integer wanna3 = map.getOrDefault("john", 185);
        logger.info("Map.getOrDefault : {}, {}, {}", wanna1, wanna2, wanna3);
    }

dev 17:17:57.414 [main] INFO  com.info.maka.waimai.CommonTest - Map.getOrDefault : 168, 166, null
```

```java
class Solution {
    public int numIdenticalPairs(int[] nums) {
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (int num : nums) {
            // 如果hashMap中含有指定的key，就返回该key对应的value，否则使用该方法的第二个参数作为默认值返回
            // V getOrDefault(Object key, V defaultValue)
            hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        }

        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            // 知道相同值的数量v，一共有(v-1)+(v-2)+...+1中组合
            int v = entry.getValue();
            ans += v * (v - 1) / 2;
        }

        return ans;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$。
- 空间复杂度：$O(n)$，即哈希表使用到的辅助空间的空间代价。



# 数组

## 数组复制与排序

```java {.line-numbers highlight=3-4}
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
    }
}
```


## 1. 两数之和(简单)

给定一个整数数组`nums`和一个目标值`target`，请你在该数组中找出**和为目标值**的那**两个整数**，并返回他们的数组**下标**。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

**示例**：
```
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```

**思路与算法：**

**暴力法**很简单，遍历每个元素$x$，并查找是否存在一个值与$target - x$相等的目标元素。


```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
```

时间复杂度：$O(n^2)$，空间复杂度：$O(1)$。

### HashMap

为了对运行时间复杂度进行优化，我们需要一种更有效的方法来**检查数组中是否存在目标元素**。如果存在，我们需要找出它的索引。保持数组中的每个**元素**与其**索引**相互对应的最好方法是什么？哈希表。

通过**以空间换取速度**的方式，我们可以将查找时间从$O(n)$降低到$O(1)$。哈希表正是为此目的而构建的，它支持**以近似恒定的时间进行快速查找**。用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到$O(n)$。但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为$O(1)$。

一个简单的实现使用了**两次迭代**。在**第一次迭代**中，我们**将每个元素的值和它的索引添加到表中**。然后，在**第二次迭代**中，我们将**检查**每个元素所对应的目标元素$(target - nums[i])$是否存在于表中。注意，**该目标元素不能是$nums[i]$本身**！

```java
hashMap.put(nums[i], i);
hashMap.containsKey(complement)
hashMap.get(complement) != i
throw new IllegalArgumentException("No two sum solution");
```

**代码：**
```java {.line-numbers highlight=30}
import java.util.Map;
import java.util.HashMap;

/**
 * leetcode_1_两数之和
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 */

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        // 创建一个HashMap来存储数据，便于查找
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // V put(K key, V value)
            hashMap.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // 如果hashMap中存在complement且不是nums[i]本身
            // hashMap.get(Object Key)
            if (hashMap.containsKey(complement) && hashMap.get(complement) != i) {
                return new int[] {i, hashMap.get(complement)};
            }
        }

        // Missing return statement
        throw new IllegalArgumentException("No two sum solution");
    }
}
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Chenzf
 * @date 2020/7/11
 */

public class TestTwoSum {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待测数据：");
            String[] strings = reader.readLine().split(" ");
            int[] nums = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                nums[i] = Integer.parseInt(strings[i]);
            }
            System.out.println("请输入目标值：");
            int target = Integer.parseInt(reader.readLine());

            // System.out.println(TwoSum.twoSum(nums, target)); // [I@16d3586

            int[] results = TwoSum.twoSum(nums, target);
            for (int result : results) {
                System.out.print(result + " ");
            }

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
```

**复杂度分析：**

时间复杂度：$O(n)$
我们把包含有$n$个元素的列表遍历两次。由于**哈希表将查找时间缩短到**$O(1)$，所以时间复杂度为$O(n)$。

空间复杂度：$O(n)$
所需的额外空间**取决于哈希表中存储的元素数量**，该表中存储了$n$个元素。


## 42. 接雨水(困难)

给定$n$个非负整数表示每个宽度为1的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

<div align=center><img src=LeetCode\42.png></div>

```
示例:

输入: [0,1,0,2,1,0,1,3,2,1,2,1]
输出: 6
```

### 暴力解法

对于数组中的每个元素，我们**找出<font color=red>下雨后水能达到的最高位置，等于两边最大高度的较小值减去当前高度的值</font>**。

- 初始化$ans=0$
- 从左向右扫描数组：
    - 初始化$\text{max\_left}=0$和$\text{max\_right}=0$
    - **从当前元素向左扫描**并更新：$\text{max\_left}=\max(\text{max\_left},\text{height}[j])$
    - **从当前元素向右扫描**并更新：$\text{max\_right}=\max(\text{max\_right},\text{height}[j])$
    - 将$\min(\text{max\_left},\text{max\_right}) - \text{height}[i]$累加到 $\text{ans}$


```java
class Solution {
    public int trap(int[] height) {
        int sum = 0;
        //最两端的列不用考虑，因为一定不会有水。所以下标从1到length - 2
        for (int i = 1; i < height.length - 1; i++) {
            int max_left = 0;
            //找出左边最高
            for (int j = i - 1; j >= 0; j--) {
                if (height[j] > max_left) {
                    max_left = height[j];
                }
            }

            int max_right = 0;
            //找出右边最高
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] > max_right) {
                    max_right = height[j];
                }
            }

            //找出两端较小的
            int min = Math.min(max_left, max_right);
            //只有较小的一段大于当前列的高度才会有水，其他情况不会有水
            if (min > height[i]) {
                sum = sum + (min - height[i]);
            }
        }
        return sum;
    }
}
```

**复杂性分析**

- 时间复杂度：$O(n^2)$。数组中的每个元素都需要向左向右扫描。

- 空间复杂度：$O(1)$的额外空间。



### 动态规划

在暴力方法中，我们仅仅为了找到最大值每次都要向左和向右扫描一次。但是我们可以提前存储这个值。

提前存储每个位置上所有左边柱子高度的最大值和所有右边柱子高度的最大值

- 找到数组中**从下标 i 到最左端**最高的条形块高度$\text{left\_max}$。
- 找到数组中从下标 i 到最右端最高的条形块高度$\text{right\_max}$。


<div align=center><img src=LeetCode\42_1.jpg></div>

- 扫描数组 $\text{height}$ 并更新答案：
    - 累加 $\min(\text{max\_left}[i],\text{max\_right}[i]) - \text{height}[i]$到 $ans$ 上

```java
class Solution {
    public int trap(int[] height) {
        int ans = 0;
        int len = height.length;
        if (len < 3) {
            return 0;
        }

        int[] leftMaxArr = new int[len];
        int[] rightMaxArr = new int[len];
        leftMaxArr[0] = height[0];
        rightMaxArr[len - 1] = height[len - 1];

        for (int i = 1; i < len; i++) {
            leftMaxArr[i] = Math.max(leftMaxArr[i - 1], height[i]);
        }

        for (int i = len - 2; i >= 0; i--) {
            rightMaxArr[i] = Math.max(rightMaxArr[i + 1], height[i]);
        }

        for (int i = 0; i < len; i++) {
            ans += Math.min(leftMaxArr[i], rightMaxArr[i]) - height[i];
        }

        return ans;
    }
}
```


**复杂性分析**

- 时间复杂度：$O(n)$。

    - 存储最大高度数组，需要两次遍历，每次$O(n)$。
    - 最终使用存储的数据更新$\text{ans}$，$O(n)$。

- 空间复杂度：$O(n)$额外空间。

    - 和方法 1 相比使用了额外的$O(n)$空间用来放置$\text{left\_max}$和$\text{right\_max}$数组。






## 59. *螺旋矩阵 II (中等)

给定一个正整数$n$，生成一个包含1到$n^2$所有元素，且**元素按顺时针顺序螺旋排列**的正方形矩阵。

```
示例:

输入: 3
输出:
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]
```

**思路与算法：**

链接：https://leetcode-cn.com/problems/spiral-matrix-ii/solution/spiral-matrix-ii-mo-ni-fa-she-ding-bian-jie-qing-x/

- 生成一个$n×n$空矩阵`mat`，随后模拟整个**向内环绕填入**的过程：
    - 定义当前左右上下边界`left, right, top, bottom`，初始值`num = 1`，迭代终止值 `tar = n * n`；
    - 当`num <= tar`时，始终按照`从左到右、从上到下、从右到左、从下到上`填入顺序循环，每次填入后：
        - 执行`num += 1`：得到下一个需要填入的数字；
        - 更新边界：例如**从左到右填完后**，上边界`top += 1`，相当于**上边界向内缩1**。
    - 使用`num <= tar`而不是`left < right || top < bottom`作为迭代条件，是为了解决当n为奇数时，矩阵中心数字无法在迭代过程中被填充的问题。
- 最终返回`mat`即可。

<div align=center><img src=LeetCode\59.png></div>


```java
class Solution {
    public int[][] generateMatrix(int n) {
        int left = 0, right = n - 1, top = 0, bottom = n - 1;
        int[][] matrix = new int[n][n];
        int num = 1, tail = n * n;

        while(num <= tail){
            // left to right.
            for(int i = left; i <= right; i++) matrix[top][i] = num++;
            // 进入下一行
            top++;

            // top to bottom.
            for(int i = top; i <= bottom; i++) matrix[i][right] = num++;
            // 进入左边一列
            right--;

            // right to left.
            for(int i = right; i >= left; i--) matrix[bottom][i] = num++;
            // 进入上面一行
            bottom--;

            // bottom to top.
            for(int i = bottom; i >= top; i--) matrix[i][left] = num++;
            // 进入右边一列
            left++;
        }

        return matrix;
    }
}
```



## 66. 加一(简单)


给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。

最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。

你可以假设除了整数 0 之外，这个整数不会以零开头。

```
示例 1:

输入: [1,2,3]
输出: [1,2,4]
解释: 输入数组表示数字 123。

示例 2:

输入: [4,3,2,1]
输出: [4,3,2,2]
解释: 输入数组表示数字 4321。
```


**思路与算法：**

链接：https://leetcode-cn.com/problems/plus-one/solution/hua-jie-suan-fa-66-jia-yi-by-guanpengchn/

- 末位无进位，则末位加一即可，因为末位无进位，前面也不可能产生进位，比如 45 => 46

- 末位有进位，**在中间位置进位停止**，则需要找到进位的典型标志，即为当前位%10后为 0，则前一位加 1，直到不为 0 为止，比如 499 => 500

- 末位有进位，并且**一直进位到最前方导致结果多出一位**，对于这种情况，需要在第 2 种情况遍历结束的基础上，进行单独处理，比如 999 => 1000


```java
class Solution {
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for(int i = len - 1; i >= 0; i--) {
            digits[i]++;
            digits[i] %= 10;

            if(digits[i] != 0)
                return digits;
        }

        // 执行到这里表明最高位为1，其余为0
        digits = new int[len + 1];
        digits[0] = 1;

        return digits;
    }
}
```





## 121. *买卖股票的最佳时机(简单)

给定一个数组，它的第i个元素是一支给定股票第i天的价格。

如果你**最多只允许完成一笔交易**（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。

注意：你不能在买入股票前卖出股票。


```
示例 1:

输入: [7,1,5,3,6,4]
输出: 5

解释: 在第2天（股票价格 = 1）的时候买入，在第5天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5。
注意：利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。


示例 2:

输入: [7,6,4,3,1]
输出: 0
解释: 在这种情况下, 没有交易完成, 所以最大利润为0。
```

### 滑动窗口

- 我们需要找出给定数组中两个数字之间的最大差值（即，最大利润）。此外，第二个数字（卖出价格）必须大于第一个数字（买入价格）。
- 用一个变量记录一个历史最低价格`minPrice`，那么在第i天卖出股票能得到的利润就是`prices[i] - minprice`。

```java
package solution;

/**
 * leetcode_121_买卖股票的最佳时机
 * @author Chenzf 
 * @date 2020/7/29
 * @version 1.0
 */

public class BestTimeBuySellStock {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxPrice = 0;
        
        for (int i = 0; i < prices.length; i++) {
            // 确保了买进在卖出之前
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxPrice) {
                maxPrice = prices[i] - minPrice;
            }
        }
        
        return maxPrice;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，**只需要遍历一次**。
- 空间复杂度：$O(1)$，**只使用了常数个变量**。







## 122. 买卖股票的最佳时机II(简单)

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
  
贪心算法的决策是：**只加正数**。

- **股票买卖策略：**

  - 单独交易日：设今天价格$p_1$、明天价格$p_2$，则**今天买入、明天卖出**可赚取金额$p_2 - p_1$（负值代表亏损）。
  - 连续上涨交易日：设此上涨交易日股票价格分别为$p_1, p_2, ... , p_n$，则**第一天买最后一天卖收益最大**，即$p_n - p_1$；**等价于每天都买卖**，即$p_n - p_1=(p_2 - p_1)+(p_3 - p_2)+...+(p_n - p_{n-1})$。
  - 连续下降交易日：则**不买卖收益最大**，即不会亏钱。

**代码：**
```java
class BestTimeToBuyAndSellStockII {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for(int i = 1; i< prices.length; i++){
            if(prices[i] > prices[i - 1])
                maxProfit += prices[i] - prices[i - 1];
        }

        return maxProfit;
    }
}
```

**复杂度分析：**
时间复杂度：$O(n)$，遍历一次。
空间复杂度：$O(1)$，需要常量的空间。





## 189. 旋转数组—移位(简单)

给定一个数组，将数组中的元素向右移动k个位置，其中k是非负数。

```
示例 1:
输入: [1,2,3,4,5,6,7] 和 k = 3
输出: [5,6,7,1,2,3,4]

解释:
向右旋转 1 步: [7,1,2,3,4,5,6]
向右旋转 2 步: [6,7,1,2,3,4,5]
向右旋转 3 步: [5,6,7,1,2,3,4]

示例 2:
输入: [-1,-100,3,99] 和 k = 2
输出: [3,99,-1,-100]

解释: 
向右旋转 1 步: [99,-1,-100,3]
向右旋转 2 步: [3,99,-1,-100]
```

**思路与算法：**

如果我们直接**把每一个数字放到它最后的位置**，但这样的后果是遗失原来的元素。因此，我们需要把被替换的数字保存在变量`temp`里面。然后，我们将被替换数字(`temp`)放到它正确的位置，并继续这个过程$n$次，$n$是数组的长度。这是因为我们需要将数组里所有的元素都移动。

但是，这种方法可能会有个问题，如果$n\%k==0$，其中$k=k\%n$（因为如果$k$大于$n$，移动$k$次实际上相当于移动$k\%n$次）。这种情况下，我们会发现在没有遍历所有数字的情况下回到出发数字。此时，我们应该从下一个数字开始再重复相同的过程。

<div align=center><img src=LeetCode\189.png></div>

```java
package solution;

/**
 * leetcode_189_旋转数组
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0
 */

public class RotateArray {
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}
```

**使用额外的数组：**

可以用一个额外的数组来将每个元素放到正确的位置上。
即，原本数组里下标为$i$的，我们把它放到$(i+k)\%数组长度$的位置。然后把新的数组拷贝到原数组中。

```java
public class Solution {
    public void rotate(int[] nums, int k) {
        int[] a = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            a[(i + k) % nums.length] = nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = a[i];
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$。将数字放到新的数组中需要一遍遍历，另一边来把新数组的元素拷贝回原数组。
- 空间复杂度：$O(n)$。另一个数组需要原数组长度的空间。


## 面试题 01.07. 旋转矩阵—90度(中等)

给你一幅由$N × N$矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。

不占用额外内存空间能否做到？

```
示例 1:

给定 matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

原地旋转输入矩阵，使其变为:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
示例 2:

给定 matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

原地旋转输入矩阵，使其变为:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]
```

**思路与算法：**

<font color=red>先由对角线` [1, 5, 9]` 为轴进行翻转</font>，于是数组

```
[1,2,3]
[4,5,6]
[7,8,9]
```

变成了:
```
[1,4,7]
[2,5,8]
[3,6,9]
```

<font color=red>再对每一行以中点进行翻转</font>，就得到了：

```
[7,4,1]
[8,5,2]
[9,6,3]
```


```java
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // 先以对角线（左上-右下）为轴进行翻转
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        // 再对每一行以中点进行翻转
        int mid = n >> 1;  // 相当于n除以2
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < mid; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = tmp;
            }
        }
    }
}
```



## 283. 移动零(简单)

给定一个数组nums，编写一个函数将所有0移动到数组的末尾，同时保持非零元素的相对顺序。

```
示例:

输入: [0,1,0,3,12]
输出: [1,3,12,0,0]
```

说明:

- 必须在原数组上操作，不能拷贝额外的数组。
- 尽量减少操作次数。

```java {.line-numbers highlight=7-14}
class Solution {
	public void moveZeroes(int[] nums) {
        if (nums == null) {
            return;
        }
        
        //第一次遍历的时候，j指针记录非0的个数，只要是非0的统统都赋给nums[j]
        int j = 0;

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                // 不用再额外的创建新数组
                nums[j++] = nums[i];
            }
        }
        
        //非0元素统计完了，剩下的都是0了。所以第二次遍历把末尾的元素都赋为0即可
        for (int i = j; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
```

- 时间复杂度：$O(n)$
- 空间复杂度：$O(1)$




## 1313. 解压缩编码列表(简单)

给你一个以行程长度编码压缩的整数列表 nums 。

考虑每对相邻的两个元素$[freq, val] = [nums[2*i], nums[2*i+1]]$（其中$i >= 0$），每一对都表示解压后子列表中**有`freq`个值为`val`的元素**，你需要从左到右连接所有子列表以生成解压后的列表。

请你返回解压后的列表。

 
```
示例1：

输入：nums = [1,2,3,4]
输出：[2,4,4,4]
解释：第一对 [1,2] 代表着 2 的出现频次为 1，所以生成数组 [2]。
第二对 [3,4] 代表着 4 的出现频次为 3，所以生成数组 [4,4,4]。
最后将它们串联到一起 [2] + [4,4,4] = [2,4,4,4]。

示例 2：

输入：nums = [1,1,2,3]
输出：[1,3,3]
```

提示：
```
2 <= nums.length <= 100
nums.length % 2 == 0
1 <= nums[i] <= 100
```

以步长（step）为2遍历数组nums，对于当前遍历到的元素a和b，我们将a个b添加进答案中即可。

```java
public class Solution {

    public int[] decompressRLElist(int[] nums) {
        if (nums.length == 1) {
            return new int[]{nums[0]};
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i += 2) {
            for (int j = 0; j < nums[i]; j++) {
                ans.add(nums[i + 1]);
            }
        }
        // return ans.stream().mapToInt(i->i).toArray();//这样转耗时较大，不如下面这种
        int[] res = new int[ans.size()];
        int index = 0;
        for (Integer num : ans) {
            res[index++] = num;
        }
        return res;
    }
}
```


## 1330. 翻转子数组得到最大的数组值(困难)


给你一个整数数组 nums 。「数组值」定义为所有满足 `0 <= i < nums.length-1` 的 `|nums[i]-nums[i+1]|` 的和。

你可以选择给定数组的任意子数组，并将该子数组翻转。但你只能执行这个操作 一次 。

请你找到可行的最大 数组值 。

 
```
示例 1：

输入：nums = [2,3,1,5,4]
输出：10
解释：通过翻转子数组 [3,1,5] ，数组变成 [2,5,1,3,4] ，数组值为 10 。
示例 2：

输入：nums = [2,4,9,24,2,1,10]
输出：68
```

提示：
```
1 <= nums.length <= 3*10^4
-10^5 <= nums[i] <= 10^5
```







## 1365. 有多少小于当前数字的数字(简单)

给你一个数组`nums`，对于其中每个元素`nums[i]`，请你统计数组中比它小的所有数字的数目。

换而言之，对于每个`nums[i]`你必须计算出有效的`j`的数量，其中`j`满足`j != i`且`nums[j] < nums[i]`。

以数组形式返回答案。

```
示例 1：

输入：nums = [8,1,2,2,3]
输出：[4,0,1,1,3]
解释： 
对于 nums[0]=8 存在四个比它小的数字：（1，2，2 和 3）。 
对于 nums[1]=1 不存在比它小的数字。
对于 nums[2]=2 存在一个比它小的数字：（1）。 
对于 nums[3]=2 存在一个比它小的数字：（1）。 
对于 nums[4]=3 存在三个比它小的数字：（1，2 和 2）。

示例 2：

输入：nums = [6,5,4,8]
输出：[2,1,0,3]

示例 3：

输入：nums = [7,7,7,7]
输出：[0,0,0,0]
```

提示：

- $2 <= nums.length <= 500$
- $0 <= nums[i] <= 100$


```java
class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        //new一个新数组
        int[] newArr = new int[nums.length];
        int index = 0;
        //每次取出一个进行遍历
        for (int num : nums) {
            //count重置
            int temp = num, count = 0;
            for (int i = 0; i < nums.length; i++) {
                //数组中元素小于当前数，count++
                if (nums[i] < temp) {
                    count++;
                }
            }
            //有效j（小于的个数）放入数组中
            newArr[index] = count;
            index++;
        }
        return newArr;
    }
}
```


## 1389. 按既定顺序创建目标数组(简单)

给你两个整数数组nums和index。你需要按照以下规则创建目标数组：

- 目标数组target最初为空。
- 按从左到右的顺序依次读取 nums[i] 和 index[i]，在 target 数组中的下标 index[i] 处插入值 nums[i] 。
- 重复上一步，直到在 nums 和 index 中都没有要读取的元素。

请你返回目标数组。

题目保证数字插入位置总是存在。

 
```
示例 1：

输入：nums = [0,1,2,3,4], index = [0,1,2,2,1]
输出：[0,4,1,3,2]
解释：
nums       index     target
0            0        [0]
1            1        [0,1]
2            2        [0,1,2]
3            2        [0,1,3,2]
4            1        [0,4,1,3,2]

示例 2：

输入：nums = [1,2,3,4,0], index = [0,1,2,3,0]
输出：[0,1,2,3,4]
解释：
nums       index     target
1            0        [1]
2            1        [1,2]
3            2        [1,2,3]
4            3        [1,2,3,4]
0            0        [0,1,2,3,4]

示例 3：

输入：nums = [1], index = [0]
输出：[1]
```

提示：
```
1 <= nums.length, index.length <= 100
nums.length == index.length
0 <= nums[i] <= 100
0 <= index[i] <= i
```


不断在数组的指定的索引`index[i]`处插入元素。这里注意题目提示中有一个限制条件`index[i] <= i`，也就是说**在插入第`i`个元素时，索引最多为最后一个元素**。

有了上述条件，直接使用java中`ArrayList`类中的`add(int index, Object o)`函数即可**将指定元素插入到指定位置**。最后再将List中元素依次放到数组中即可。

```java {.line-numbers highlight=11}
class Solution {
    public int[] createTargetArray(int[] nums, int[] index) {
        int n = nums.length;
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < n; i++){
            result.add(index[i], nums[i]);
        }
        
        int[] target = new int[n];
        for(int i = 0; i < n; i++){
            target[i] = result.get(i);
        }
        return target;
    }
}
```






## 1431. 拥有最多糖果的孩子(简单)

给你一个数组`candies`和一个整数`extraCandies`，其中`candies[i]`代表第`i`个孩子拥有的糖果数目。

对每一个孩子，检查是否存在一种方案，将额外的`extraCandies`个糖果分配给孩子们之后，此孩子有最多的糖果。注意，允许有多个孩子同时拥有最多的糖果数目。

 
```
示例 1：

输入：candies = [2,3,5,1,3], extraCandies = 3
输出：[true,true,true,false,true] 
解释：
孩子 1 有 2 个糖果，如果他得到所有额外的糖果（3个），那么他总共有 5 个糖果，他将成为拥有最多糖果的孩子。
孩子 2 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
孩子 3 有 5 个糖果，他已经是拥有最多糖果的孩子。
孩子 4 有 1 个糖果，即使他得到所有额外的糖果，他也只有 4 个糖果，无法成为拥有糖果最多的孩子。
孩子 5 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。

示例 2：

输入：candies = [4,2,1,1,2], extraCandies = 1
输出：[true,false,false,false,false] 
解释：只有 1 个额外糖果，所以不管额外糖果给谁，只有孩子 1 可以成为拥有糖果最多的孩子。
示例 3：

输入：candies = [12,1,12], extraCandies = 10
输出：[true,false,true]
```


提示：
$2 <= candies.length <= 100$
$1 <= candies[i] <= 100$
$1 <= extraCandies <= 50$


**思路与算法：**

对于每一个小朋友，只要这个小朋友「拥有的糖果数目」加上「额外的糖果数目」大于等于**所有小朋友拥有的糖果数目最大值**，那么这个小朋友就可以拥有最多的糖果。

```java {.line-numbers highlight=11}
class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int n = candies.length;
        int maxCandies = 0;
        for (int i = 0; i < n; ++i) {
            maxCandies = Math.max(maxCandies, candies[i]);
        }

        List<Boolean> result = new ArrayList<Boolean>();
        for (int i = 0; i < n; ++i) {
            result.add(candies[i] + extraCandies >= maxCandies);
        }
        return result;
    }
}
```



## 1470. 重新排列数组(简单)

给你一个数组nums，数组中有$2n$个元素，按$[x_1, x_2,..., x_n, y_1, y_2,..., y_n]$的格式排列。

请你将数组按$[x_1, y_1, x_2, y_2,..., x_n, y_n]$格式重新排列，返回重排后的数组。

 
```
示例 1：

输入：nums = [2,5,1,3,4,7], n = 3
输出：[2,3,5,4,1,7] 
解释：由于 x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 ，所以答案为 [2,3,5,4,1,7]

示例 2：

输入：nums = [1,2,3,4,4,3,2,1], n = 4
输出：[1,4,2,3,3,2,4,1]

示例 3：

输入：nums = [1,1,2,2], n = 2
输出：[1,2,1,2]
```

提示：
```
1 <= n <= 500
nums.length == 2n
1 <= nums[i] <= 10^3
```


```java
class Solution {
    public int[] shuffle(int[] nums, int n) {
        int temp[] = new int[nums.length];
        int index = 0;
        for(int i = 0;i < n;i++){
            temp[index++] = nums[i];
            temp[index++] = nums[i + n];
        }
        return temp;
    }
}
```


## 1480. 一维数组的动态和(简单)

给你一个数组`nums`。数组「动态和」的计算公式为：`runningSum[i] = sum(nums[0]…nums[i])`。

请返回`nums`的动态和。

 
```
示例 1：

输入：nums = [1,2,3,4]
输出：[1,3,6,10]
解释：动态和计算过程为[1, 1+2, 1+2+3, 1+2+3+4]。

示例 2：

输入：nums = [1,1,1,1,1]
输出：[1,2,3,4,5]
解释：动态和计算过程为[1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1]。

示例 3：

输入：nums = [3,1,2,10,1]
输出：[3,4,6,16,17]
```

提示：
- $1 <= nums.length <= 1000$
- $-10^6 <= nums[i] <= 10^6$


```java
class Solution {
    public int[] runningSum(int[] nums) {
        int temp[] = new int[nums.length];
        temp[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            temp[i] = temp[i-1] + nums[i];
        }
        return temp;
    }
}
```


## 1486. 数组异或操作(简单)

给你两个整数，n和start。

数组nums定义为：$nums[i] = start + 2*i$（下标从0开始）且$n == nums.length$。

请返回nums中所有元素按位异或（XOR）后得到的结果。

 
```
示例 1：

输入：n = 5, start = 0
输出：8
解释：数组 nums 为 [0, 2, 4, 6, 8]，其中 (0 ^ 2 ^ 4 ^ 6 ^ 8) = 8 。
     "^" 为按位异或 XOR 运算符。

示例 2：

输入：n = 4, start = 3
输出：8
解释：数组 nums 为 [3, 5, 7, 9]，其中 (3 ^ 5 ^ 7 ^ 9) = 8.

示例 3：

输入：n = 1, start = 7
输出：7

示例 4：

输入：n = 10, start = 5
输出：2
```

提示：
```
1 <= n <= 1000
0 <= start <= 1000
n == nums.length
```

```java
class Solution {
    public int xorOperation(int n, int start) {
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans ^= (start + i * 2);
        }
        return ans;
    }
}
```



# 字符串


## 字符串与字符方法

`Character.isLetterOrDigit(s.charAt(left))`
`Character.toLowerCase(s.charAt(left))`

```java
class Solution {
    public boolean isPalindrome(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }
}
```



## 将字符串转换为字符数组

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }

    // 将字符串转为字符数组
    char[] str1 = s.toCharArray();
    char[] str2 = t.toCharArray();
    Arrays.sort(str1);
    Arrays.sort(str2);

    return Arrays.equals(str1, str2);
}
```




## 13. 罗马数字转整数(简单)

罗马数字包含以下七种字符:` I， V， X， L，C，D` 和 `M`。

```
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```

例如， 罗马数字 2 写做 `II` ，即为两个并列的 `I`。12 写做 `XII` ，即为 `X + II` 。 27 写做  `XXVII`， 即为 `XX + V + II` 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是`IV`。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。

给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。

```
示例 1:

输入: "III"
输出: 3

示例 2:

输入: "IV"
输出: 4

示例 3:

输入: "IX"
输出: 9

示例 4:

输入: "LVIII"
输出: 58
解释: L = 50, V= 5, III = 3.

示例 5:

输入: "MCMXCIV"
输出: 1994
解释: M = 1000, CM = 900, XC = 90, IV = 4.
```


**思路与算法：**

按照题目的描述，可以总结如下规则：

- 罗马数字由 `I,V,X,L,C,D,M` 构成；
- **当小值在大值的左边，则减小值**，如 IV=5-1=4；
- **当小值在大值的右边，则加小值**，如 VI=5+1=6；
- 由上可知，右值永远为正，因此最后一位必然为正。

一言蔽之，**把一个小值放在大值的左边，就是做减法，否则为加法**。


在代码实现上，可以**往后看多一位，对比当前位与后一位的大小关系**，从而确定当前位是加还是减法。当没有下一位时，做加法即可。

也可**保留当前位的值，当遍历到下一位的时，对比保留值与遍历位的大小关系**，再确定保留值为加还是减。最后一位做加法即可。


```java
import java.util.*;

class Solution {
    public int romanToInt(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for(int i = 1; i < s.length(); i ++) {
            int num = getValue(s.charAt(i));
            if(preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }
    
    private int getValue(char ch) {
        switch(ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}
```



## 14. 最长公共前缀(简单)longest-common-prefix

编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 ""。

```
示例 1:

输入: ["flower","flow","flight"]
输出: "fl"

示例 2:

输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
```

说明：所有输入只包含小写字母 a-z 。


**横向扫描**

用$\textit{LCP}(S_1 \ldots S_n)$表示字符串$S_1 \ldots S_n$的最长公共前缀。

可以得到以下结论：

$\textit{LCP}(S_1 \ldots S_n) = \textit{LCP}(\textit{LCP}(\textit{LCP}(S_1, S_2),S_3),\ldots S_n)$

基于该结论，可以得到一种查找字符串数组中的最长公共前缀的简单方法。**依次遍历字符串数组中的每个字符串，对于每个遍历到的字符串，更新最长公共前缀**，当遍历完所有的字符串以后，即可得到字符串数组中的最长公共前缀。

<div align=center><img src=LeetCode\14.png width=60%></div>

如果在尚未遍历完所有的字符串时，最长公共前缀已经是空串，则最长公共前缀一定是空串，因此不需要继续遍历剩下的字符串，直接返回空串即可。

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }

    public String longestCommonPrefix(String str1, String str2) {
        int length = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < length && str1.charAt(index) == str2.charAt(index)) {
            index++;
        }
        return str1.substring(0, index);
    }
}
```

**复杂度分析**

- 时间复杂度：$O(mn)$，其中$m$是字符串数组中的字符串的平均长度，$n$是字符串的数量。最坏情况下，**字符串数组中的每个字符串的每个字符都会被比较一次**。

- 空间复杂度：$O(1)$。使用的额外空间复杂度为常数。






## 38. *外观数列(简单)

给定一个正整数 n（1 ≤ n ≤ 30），输出外观数列的第 n 项。

注意：整数序列中的每一项将表示为一个字符串。

「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。前五项如下：

```
1.     1
2.     11
3.     21
4.     1211
5.     111221
```

第一项是数字 1

描述前一项，这个数是 1 即 “一个 1 ”，记作 11

描述前一项，这个数是 11 即 “两个 1 ” ，记作 21

描述前一项，这个数是 21 即 “一个 2 一个 1 ” ，记作 1211

描述前一项，这个数是 1211 即 “一个 1 一个 2 两个 1 ” ，记作 111221

 
```
示例 1:

输入: 1
输出: "1"
解释：这是一个基本样例。

示例 2:

输入: 4
输出: "1211"
解释：当 n = 3 时，序列是 "21"，其中我们有 "2" 和 "1" 两组，"2" 可以读作 "12"，也就是出现频次 = 1 而 值 = 2；类似 "1" 可以读作 "11"。所以答案是 "12" 和 "11" 组合在一起，也就是 "1211"。
```

```java {.line-numbers highlight=23}
class Solution {
    public String countAndSay(int n) {
        if (n <= 0) {
            return "";
        }
        String[] seq = new String[n];
        seq[0] = "1";
        for (int i = 1; i < n; i++) {
            seq[i] = convert(seq[i - 1]);
        }
        return seq[n - 1];
    }

    public String convert(String str) {
        StringBuilder ans = new StringBuilder();
        int count = 1;
        for (int i = 0; i < str.length(); i++) {
            // 这一部分是"count", 记录连续出现x个y
            if (i < str.length() - 1 && str.charAt(i) == str.charAt(i + 1)) {
                count++;
            } else {
            // 这一部分是"say"，将上面"count"的结果"xy"作为下一个数的一部分（若上面记录到2个1，就拼接"21"）
                ans.append(count).append(str.charAt(i));
                count = 1;
            }
        }
        return new String(ans);
    }
}
```


## 125. 验证回文串(简单)

给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。

说明：本题中，我们将空字符串定义为有效的回文串。

```
示例 1:

输入: "A man, a plan, a canal: Panama"
输出: true

示例 2:

输入: "race a car"
输出: false
```

**思路与算法：**

直接在原字符串s上使用双指针。在移动任意一个指针时，需要不断地向另一指针的方向移动，直到遇到一个字母或数字字符，或者两指针重合为止。也就是说，我们每次将指针移到下一个字母字符或数字字符，再判断这两个指针指向的字符是否相同。

```java
class Solution {
    public boolean isPalindrome(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(|s|)$，其中$|s|$是字符串s的长度。

- 空间复杂度：$O(1)$。





## 171. Excel表列序号(简单)

给定一个Excel表格中的列名称，返回其相应的列序号。

```
例如，

A -> 1
B -> 2
C -> 3
...
Z -> 26
    
AA -> 27
AB -> 28 
...
```


```java
class Solution {
    public int titleToNumber(String s) {
        int ans = 0;
        for(int i = 0; i < s.length(); i++) {
            int num = s.charAt(i) - 'A' + 1;
            ans = ans * 26 + num;
        }
        return ans;
    }
}
```



## 242. 有效的字母异位词(简单)

给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

```
示例 1:

输入: s = "anagram", t = "nagaram"
输出: true

示例 2:

输入: s = "rat", t = "car"
输出: false
```

说明：可以假设字符串只包含小写字母。


### 排序

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        // 将字符串转为字符数组
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);

        return Arrays.equals(str1, str2);
    }
}
```


**复杂度分析**

- 时间复杂度：$O(n \log n)$，假设$n$是$s$的长度，排序成本$O(n\log n)$和比较两个字符串的成本$O(n)$。排序时间占主导地位，总体时间复杂度为$O(n \log n)$。
- 空间复杂度：$O(1)$，空间取决于排序实现，如果使用 `heapsort`，通常需要$O(1)$辅助空间。注意，在 Java 中，`toCharArray()` 制作了一个字符串的拷贝，所以它花费$O(n)$额外的空间，但是我们忽略了这一复杂性分析，因为：这依赖于语言的细节。这取决于函数的设计方式。例如，可以将函数参数类型更改为 char[]。


### HashMap

利用hash保存每一位s的出现次数，然后减去t出现的次数，最后值全为0说明符合条件。

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }

        HashMap<Character,Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            // 如果map中不包含s当前的字符，则添加
            if(!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), 1);
            } else {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
        }

        for(int i = 0; i < t.length(); i++) {
            // 如果map中不包含t当前的字符，则添加
            if(!map.containsKey(t.charAt(i))){
                map.put(t.charAt(i), 1);
            }
            map.put(t.charAt(i), map.get(t.charAt(i)) - 1);
        }

        ArrayList<Integer> list = new ArrayList<>(map.values());

        for(int a : list) {
            if(a != 0) {
                return false;
            }
        }

        return true;
    }
}
```




## 412. Fizz Buzz(简单)

写一个程序，输出从 1 到 n 数字的字符串表示。

1. 如果 n 是3的倍数，输出“Fizz”；

2. 如果 n 是5的倍数，输出“Buzz”；

3. 如果 n 同时是3和5的倍数，输出 “FizzBuzz”。

```
示例：

n = 15,

返回:
[
    "1",
    "2",
    "Fizz",
    "4",
    "Buzz",
    "Fizz",
    "7",
    "8",
    "Fizz",
    "Buzz",
    "11",
    "Fizz",
    "13",
    "14",
    "FizzBuzz"
]
```

```java {.line-numbers highlight=21}
class Solution {
    public List<String> fizzBuzz(int n) {

        List<String> ans = new ArrayList<String>();

        for (int num = 1; num <= n; num++) {
            boolean divisibleBy3 = (num % 3 == 0);
            boolean divisibleBy5 = (num % 5 == 0);
            
            if (divisibleBy3 && divisibleBy5) {
                // Divides by both 3 and 5, add FizzBuzz
                ans.add("FizzBuzz");
            } else if (divisibleBy3) {
                // Divides by 3, add Fizz
                ans.add("Fizz");
            } else if (divisibleBy5) {
                // Divides by 5, add Buzz
                ans.add("Buzz");
            } else {
                // Not divisible by 3 or 5, add the number
                ans.add(Integer.toString(num));
            }
        }

        return ans;
    }
}
```



## 657. 机器人能否返回原点(简单)

在二维平面上，有一个机器人从原点 (0, 0) 开始。给出它的移动顺序，判断这个机器人在完成移动后是否在 (0, 0) 处结束。

移动顺序由字符串表示。字符 `move[i]` 表示其第 i 次移动。机器人的有效动作有 R（右），L（左），U（上）和 D（下）。如果机器人在完成所有动作后返回原点，则返回 true。否则，返回 false。

注意：机器人“面朝”的方向无关紧要。 “R” 将始终使机器人向右移动一次，“L” 将始终向左移动等。此外，假设每次移动机器人的移动幅度相同。

 
```
示例 1:

输入: "UD"
输出: true
解释：机器人向上移动一次，然后向下移动一次。所有动作都具有相同的幅度，因此它最终回到它开始的原点。因此，我们返回 true。

示例 2:

输入: "LL"
输出: false
解释：机器人向左移动两次。它最终位于原点的左侧，距原点有两次 “移动” 的距离。我们返回 false，因为它在移动结束时没有返回原点。
```

```java {.line-numbers highlight=4}
class Solution {
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (char move: moves.toCharArray()) {
            if (move == 'U') y++;
            else if (move == 'D') y--;
            else if (move == 'L') x--;
            else if (move == 'R') x++;
        }
        return x == 0 && y == 0;
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(N)$，其中$N$是moves 指令的长度。我们需要遍历字符串一遍。

- 空间复杂度：$O(1)$。在 Java 中，我们字符串数组的长度是$O(N)$。




## 709. 转换成小写字母(简单)

实现函数`ToLowerCase()`，该函数接收一个字符串参数`str`，并将该字符串中的大写字母转换成小写字母，之后返回新的字符串。

 
```
示例 1：

输入: "Hello"
输出: "hello"

示例 2：

输入: "here"
输出: "here"

示例 3：

输入: "LOVELY"
输出: "lovely"
```

```java
class Solution {
    public String toLowerCase(String str) {
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 'A' && arr[i] <= 'Z') {
                // 'a' 97; 'A' 65
                arr[i] += 'a' - 'A';
            }
        }
        return String.valueOf(arr);
    }
}
```


## 893. *特殊等价字符串组(简单)

你将得到一个字符串数组 A，返回 A 中特殊等价字符串组的数量。

每次移动都可以交换 S 的**任意两个偶数下标的字符**或**任意两个奇数下标的字符**。如果经过任意次数的移动，S == T，那么两个字符串 S 和 T 是 特殊等价 的。

例如，S = "zzxy" 和 T = "xyzz" 是一对特殊等价字符串，因为可以先交换 S[0] 和 S[2]，然后交换 S[1] 和 S[3]，使得 "zzxy" -> "xzzy" -> "xyzz" 。

现在规定，A 的 一组特殊等价字符串 就是 A 的一个同时满足下述条件的非空子集：

- 该组中的每一对字符串都是 特殊等价 的
- 该组字符串已经涵盖了该类别中的所有特殊等价字符串，容量达到理论上的最大值（也就是说，如果一个字符串不在该组中，那么这个字符串就 不会 与该组内任何字符串特殊等价）



 
```
示例 1：

输入：["abcd","cdab","cbad","xyzz","zzxy","zzyx"]
输出：3
解释：
其中一组为 ["abcd", "cdab", "cbad"]，因为它们是成对的特殊等价字符串，且没有其他字符串与这些字符串特殊等价。
另外两组分别是 ["xyzz", "zzxy"] 和 ["zzyx"]。特别需要注意的是，"zzxy" 不与 "zzyx" 特殊等价。

示例 2：

输入：["abc","acb","bac","bca","cab","cba"]
输出：3
解释：3 组 ["abc","cba"]，["acb","bca"]，["bac","cab"]
```

提示：
```
1 <= A.length <= 1000
1 <= A[i].length <= 20
所有 A[i] 都具有相同的长度。
所有 A[i] 都只由小写字母组成。
```

**思路与算法：**

先提取出每个子数组奇数和偶数的数；然后对其进行排序组合成一个数组；存入Set中，最后返回set的长度即可。

```java
class Solution {
    public int numSpecialEquivGroups(String[] A) {
        Set<String> set = new HashSet<>();

		for(String str: A) {
			//这个下标0代表偶数位，先把偶数位的放进sb0中，然后转换成char[]类型进行排序
			StringBuilder sb0 = new StringBuilder();
			for(int i = 0; i < str.length(); i += 2) 
			    sb0.append(str.charAt(i));
			char[] c0 = sb0.toString().toCharArray();
			Arrays.sort(c0);

			//这个下标1代表奇数位，先把奇数位的放进sb1中，然后转换成char[]类型进行排序
			StringBuilder sb1 = new StringBuilder();
			for(int i = 1; i < str.length(); i += 2)
				sb1.append(str.charAt(i));
			char[] c1 = sb1.toString().toCharArray();
			Arrays.sort(c1);

			//最后把两个char[] c0 和 c1 转换成String 相加添加到set 里面去
			set.add(String.valueOf(c0) + String.valueOf(c1));
		}
        
		//最后返回set的大小即可
		return set.size();
    }
}
```


## 1108. IP 地址无效化(简单)

给你一个有效的IPv4地址address，返回这个IP地址的无效化版本。所谓无效化IP地址，其实就是用"[.]"代替了每个"."。

```
示例 1：

输入：address = "1.1.1.1"
输出："1[.]1[.]1[.]1"

示例 2：

输入：address = "255.100.50.0"
输出："255[.]100[.]50[.]0"
```

提示：给出的address是一个有效的IPv4地址


### 使用replace方法

```java
class Solution {
    public String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }
}
/*
执行用时：0 ms
内存消耗：37.8 MB
*/
```

### 创建StringBuilder

```java
class Solution {
    public String defangIPaddr(String address) {
        StringBuilder s = new StringBuilder(address);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                s.insert(i + 1, ']');// 先插入后面，此时 i 下标仍是'.'
                s.insert(i, '[');// 插入 '.' 前面，此时 i 下标是'[' ,i+2 下标为']'
                i += 3;// 故 i 直接加 3，为下一个字符，注意此时已经是原来 i+1 下标的字符了；
                //此次循环结束进入下次循环还会进行加 1，不过又因为 ip 地址格式的原因，不会有连续的两个 '.' 连着；
                //所以这个位置绝不可能是 '.'，所以再加 1，也没问题。
            }
        }
        return s.toString();
    }
}
/*
执行用时：1 ms
内存消耗：37.7 MB
*/
```

```java
class Solution {
    public String defangIPaddr(String address) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == '.') {
                s.append("[.]");
            } else {
                s.append(address.charAt(i));
            }
        }
        return s.toString();
    }
}
/*
执行用时：0 ms
内存消耗：37.6 MB
*/
```


## 1309. 解码字母到整数映射(简单)

给你一个字符串 s，它由数字（'0' - '9'）和 '#' 组成。我们希望按下述规则将 s 映射为一些小写英文字符：

- 字符（'a' - 'i'）分别用（'1' - '9'）表示。
- 字符（'j' - 'z'）分别用（'10#' - '26#'）表示。 

返回映射之后形成的新字符串。

题目数据保证映射始终唯一。

 
```
示例 1：

输入：s = "10#11#12"
输出："jkab"
解释："j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".

示例 2：

输入：s = "1326#"
输出："acz"

示例 3：

输入：s = "25#"
输出："y"

示例 4：

输入：s = "12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#"
输出："abcdefghijklmnopqrstuvwxyz"
```

提示：
```
1 <= s.length <= 1000
s[i] 只包含数字（'0'-'9'）和 '#' 字符。
s 是映射始终存在的有效字符串。
```

```java
class Solution {
    public String freqAlphabets(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            if (i < s.length() - 2 && s.charAt(i + 2) == '#') {
                sb.append((char) ('a' + Integer.parseInt(s.substring(i, i + 2)) - 1));
                i += 3;
            } else {
                sb.append((char) ('a' + Integer.parseInt(s.substring(i, i + 1)) - 1));
                i++;
            }
        }
        return sb.toString(); 
    }
}
```


## 1370. 上升下降字符串(简单)

给你一个字符串s，请你根据下面的算法重新构造字符串：

1. 从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。
2. 从 s 剩余字符中选出 最小 的字符，且该字符比上一个添加的字符大，将它 接在 结果字符串后面。
3. 重复步骤 2 ，直到你没法从 s 中选择字符。
4. 从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。
5. 从 s 剩余字符中选出 最大 的字符，且该字符比上一个添加的字符小，将它 接在 结果字符串后面。
6. 重复步骤 5 ，直到你没法从 s 中选择字符。
7. 重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。

在任何一步中，如果最小或者最大字符不止一个 ，你可以选择其中任意一个，并将其添加到结果字符串。

请你返回将 s 中字符重新排序后的 结果字符串 。

 
```
示例 1：

输入：s = "aaaabbbbcccc"
输出："abccbaabccba"
解释：第一轮的步骤 1，2，3 后，结果字符串为 result = "abc"
第一轮的步骤 4，5，6 后，结果字符串为 result = "abccba"
第一轮结束，现在 s = "aabbcc" ，我们再次回到步骤 1
第二轮的步骤 1，2，3 后，结果字符串为 result = "abccbaabc"
第二轮的步骤 4，5，6 后，结果字符串为 result = "abccbaabccba"

示例 2：

输入：s = "rat"
输出："art"
解释：单词 "rat" 在上述算法重排序以后变成 "art"

示例 3：

输入：s = "leetcode"
输出："cdelotee"

示例 4：

输入：s = "ggggggg"
输出："ggggggg"

示例 5：

输入：s = "spo"
输出："ops"
```

提示：
```
1 <= s.length <= 500
s 只包含小写英文字母。
```

```java
class Solution {
    public String sortString(String s) {
        int[] count = new int[26];
        // 统计每个字母出现的次数
        for(char ch : s.toCharArray()) {
            count[ch - 'a'] += 1;
        }

        StringBuilder result = new StringBuilder();

        // 每次发现一个桶当中计数值不为 0 的时候，就把这个桶对应的字母添加到结果字符串的最后方，
        // 然后对计数值减一
        while(result.length() != s.length()){
            // 从小到大
            for(int i = 0; i < 26; i++){
                if(count[i] == 0)
                    continue;
                result.append((char)(i + 'a'));
                count[i] -= 1;
            }
            // 从大到小
            for(int i = 25; i >= 0; i--){
                if(count[i] == 0)
                    continue;
                result.append((char)(i + 'a'));
                count[i] -= 1;
            }
        }
        return result.toString();
    }
}
```


## 1374. 生成每种字符都是奇数个的字符串(简单)

给你一个整数 n，请你返回一个含 n 个字符的字符串，其中每种字符在该字符串中都恰好出现 奇数次 。

返回的字符串必须只含小写英文字母。如果存在多个满足题目要求的字符串，则返回其中任意一个即可。

 
```
示例 1：

输入：n = 4
输出："pppz"
解释："pppz" 是一个满足题目要求的字符串，因为 'p' 出现 3 次，且 'z' 出现 1 次。当然，还有很多其他字符串也满足题目要求，比如："ohhh" 和 "love"。

示例 2：

输入：n = 2
输出："xy"
解释："xy" 是一个满足题目要求的字符串，因为 'x' 和 'y' 各出现 1 次。当然，还有很多其他字符串也满足题目要求，比如："ag" 和 "ur"。

示例 3：

输入：n = 7
输出："holasss"
```

提示：`1 <= n <= 500`

```java
class Solution {
    public String generateTheString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if (n == 0) {
            return null;
        }
        if (n % 2 == 0) {  //偶数
            for (int i = 0; i < n - 1; i++) {
                stringBuilder.append('a');
            }
            stringBuilder.append('b');
        } else {
            for (int i = 0; i < n; i++) {
                stringBuilder.append('a');
            }
        }
        return stringBuilder.toString();
    }
}
```

# 动态规划


## 53. 最大子序和(简单)


给定一个整数数组`nums`，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

**示例**：
```
输入: [-2,1,-3,4,-1,2,1,-5,4],
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```

**思路和算法**：
- 步骤一、定义状态：定义数组元素的含义
    - 定义$dp[i]$为以$i$结尾子串的最大值
- 步骤二、状态转移方程：找出数组元素间的关系式
    $dp[i]=\begin{cases}
    & dp[i-1] + nums[i], \text{ } dp[i-1]\geqslant 0 \\ 
    & nums[i], \text{ }dp[i-1]< 0 
    \end{cases}$
- 步骤三、初始化：找出初始条件
    `dp[0] = nums[0];`
- 步骤四、状态压缩：优化数组空间
    每次状态的更新只依赖于前一个状态，就是说$dp[i]$的更新只取决于$dp[i-1]$, 我们只用一个存储空间保存上一次的状态即可。
- 步骤五、选出结果


**总结**：
- 动态规划的是首先对数组进行遍历，**当前最大连续子序列和**为`sum`，结果为`ans`。
- 如果`sum > 0`，则说明`sum`对结果有增益效果，则`sum`保留并**加上**当前遍历数字。
- 如果`sum <= 0`，则说明`sum`对结果无增益效果，需要舍弃，则`sum`直接**更新**为当前遍历数字。
- 每次比较`sum`和`ans`的大小，将**最大值**置为`ans`，遍历结束返回结果。

<div align=center><img src=LeetCode\正数增益.png></div>

​
**代码：**

```java
import java.util.Scanner;

/**
 * 53. 最大子序和（*）
 */

public class MaximumSubArray {
    public static int maxSubArray(int[] nums){
        if (nums == null)
            return 0;

        int[] dp = new int[nums.length];
        int ans = nums[0];
        // 初始化
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++){
            if (dp[i - 1] >= 0)
                dp[i] = dp[i - 1] + nums[i];
            else
                dp[i] = nums[i];
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入待检测的数组大小：");
        int len = input.nextInt();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++)
            nums[i] = input.nextInt();
        System.out.println("最大子序和：" + maxSubArray(nums));
    }
}
```

```java
package solution;

/**
 * leetcode_53_最大子序和
 * @author Chenzf
 * @date 2020/7/23
 * @version 1.0
 */

public class MaximumSubarray {
    public static int maxSubArray(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int[] dp = new int[nums.length];
        int result = nums[0];
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }

            // 摒弃前面出现的衰减因素
            result = Math.max(result, dp[i]);
        }

        return result;
    }
}
```

```java
package test;

import solution.MaximumSubarray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * leetcode_53_最大子序和
 * @author Chenzf
 */

public class TestMaxSubArray {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待测数组：");
            String[] strings = reader.readLine().split(" ");
            int[] nums = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                nums[i] = Integer.parseInt(strings[i]);
            }

            int result = MaximumSubarray.maxSubArray(nums);

            System.out.println("最大子序和为：" + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(n)$，其中$n$为`nums`数组的长度。我们只需要遍历一遍数组即可求得答案。
- 空间复杂度：$O(1)$。我们只需要常数空间存放若干变量。




## 70. 爬楼梯(简单)

假设你正在爬楼梯。需要 n 阶你才能到达楼顶。

每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

注意：给定 n 是一个正整数。

```
示例 1：

输入： 2
输出： 2
解释： 有两种方法可以爬到楼顶。
1.  1 阶 + 1 阶
2.  2 阶

示例 2：

输入： 3
输出： 3
解释： 有三种方法可以爬到楼顶。
1.  1 阶 + 1 阶 + 1 阶
2.  1 阶 + 2 阶
3.  2 阶 + 1 阶
```

### 滚动数组

我们用$f(x)$表示爬到第$x$级台阶的方案数，考虑最后一步可能跨了一级台阶，也可能跨了两级台阶，所以我们可以列出如下式子：$f(x) = f(x - 1) + f(x - 2)$

它意味着爬到**第$x$级**台阶的方案数是爬到**第$x - 1$级**台阶的方案数和爬到**第$x - 2$级**台阶的方案数的和。很好理解，因为每次只能爬1级或2级，所以$f(x)$只能从$f(x - 1)$和$f(x - 2)$转移过来，而这里要统计方案总数，我们就需要对这两项的贡献求和。

以上是动态规划的**转移方程**，下面我们来讨论**边界条件**。

我们是从第0级开始爬的，所以从第0级爬到第0级我们可以看作只有一种方案，即$f(0) = 1$；从第0级到第1级也只有一种方案，即爬一级，$f(1) = 1$。这两个作为边界条件就可以继续向后推导出第$n$级的正确结果。

我们不妨写几项来验证一下，根据转移方程得到$f(2) = 2$，$f(3) = 3$，$f(4) = 5$......我们把这些情况都枚举出来，发现计算的结果是正确的。

我们不难通过转移方程和边界条件给出一个时间复杂度和空间复杂度都是$O(n)$的实现，但是由于这里的$f(x)$只和$f(x - 1)$与$f(x - 2)$有关，所以我们可以用「滚动数组思想」把空间复杂度优化成$O(1)$。下面的代码中给出的就是这种实现。

<div align=center><img src=LeetCode\70.gif width=70%></div>

```java
class Solution {
    public int climbStairs(int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; i++) {
            p = q; 
            q = r; 
            r = p + q;
        }
        return r;
    }
}
```

**复杂度分析**

- 时间复杂度：循环执行$n$次，每次花费常数的时间代价，故渐进时间复杂度为$O(n)$。
- 空间复杂度：这里只用了常数个变量作为辅助空间，故渐进空间复杂度为$O(1)$。





## 118. 杨辉三角(简单)

<div align=center><img src=LeetCode\118.gif></div>

在杨辉三角中，每个数是它左上方和右上方的数的和。

```
示例:

输入: 5
输出:
[
 [1],
 [1,1],
 [1,2,1],
 [1,3,3,1],
 [1,4,6,4,1]
]
```

```java
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();

        // First base case; if user requests zero rows, they get zero rows.
        if (numRows == 0) {
            return triangle;
        }

        // Second base case; first row is always [1].
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);

        for (int rowNum = 1; rowNum < numRows; rowNum++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(rowNum-1);

            // The first row element is always 1.
            row.add(1);

            // Each triangle element (other than the first and last of each row)
            // is equal to the sum of the elements above-and-to-the-left and above-and-to-the-right.
            for (int j = 1; j < rowNum; j++) {
                row.add(prevRow.get(j-1) + prevRow.get(j));
            }

            // The last row element is always 1.
            row.add(1);

            triangle.add(row);
        }

        return triangle;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(numRows^2)$
- 空间复杂度：$O(numRows^2)$，需要存储在triangle中更新的每个数字。



## 198. 打家劫舍(简单)

你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是**相邻的房屋装有相互连通的防盗系统**，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。

 
```
示例 1：

输入：[1,2,3,1]
输出：4
解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     偷窃到的最高金额 = 1 + 3 = 4 。

示例 2：

输入：[2,7,9,3,1]
输出：12
解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     偷窃到的最高金额 = 2 + 9 + 1 = 12 。
```

提示：
```
0 <= nums.length <= 100
0 <= nums[i] <= 400
```


**思路与算法：**

首先考虑最简单的情况。如果**只有一间房屋**，则偷窃该房屋，可以偷窃到最高总金额。如果**只有两间房屋**，则由于两间房屋相邻，不能同时偷窃，只能偷窃其中的一间房屋，因此选择其中金额较高的房屋进行偷窃，可以偷窃到最高总金额。

如果房屋数量大于两间，应该如何计算能够偷窃到的最高总金额呢？

对于第$k~(k>2)$间房屋，有两个选项：

- **偷窃第$k$间房屋，那么就不能偷窃第$k−1$间房屋，偷窃总金额为前$k−2$间房屋的最高总金额与第$k$间房屋的金额之和**。

- **不偷窃第$k$间房屋，偷窃总金额为前$k−1$间房屋的最高总金额**。

**在两个选项中选择偷窃总金额较大的选项**，该选项对应的偷窃总金额即为前$k$间房屋能偷窃到的最高总金额。

用$dp[i]$表示前$i$间房屋能偷窃到的最高总金额，那么就有如下的**状态转移方程**：

$\textit{dp}[i] = \max(\textit{dp}[i-2]+\textit{nums}[i], \textit{dp}[i-1])$


**边界条件**为：

$\begin{cases} \textit{dp}[0] = \textit{nums}[0] & 只有一间房屋，则偷窃该房屋 \\ \textit{dp}[1] = \max(\textit{nums}[0], \textit{nums}[1]) & 只有两间房屋，选择其中金额较高的房屋进行偷窃 \end{cases}$

​
最终的答案即为$\textit{dp}[n-1]$，其中$n$是数组的长度。


```java
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int[] dp = new int[length];
        // 边界条件
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        // 状态方程
        for (int i = 2; i < length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        
        return dp[length - 1];
    }
}
```

### 滚动数组

上述方法使用了数组存储结果。考虑到**每间房屋的最高总金额只和该房屋的前两间房屋的最高总金额相关**，因此可以使用滚动数组，在**每个时刻只需要存储前两间房屋的最高总金额**。

```java
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int first = nums[0], second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        
        return second;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$是数组长度。只需要对数组遍历一次。

- 空间复杂度：$O(1)$。使用滚动数组，可以只存储前两间房屋的最高总金额，而不需要存储整个数组的结果，因此空间复杂度是$O(1)$。






## 1035. 不相交的线(简单)



我们在两条独立的水平线上按给定的顺序写下 A 和 B 中的整数。

现在，我们可以绘制一些连接两个数字 A[i] 和 B[j] 的直线，只要 A[i] == B[j]，且我们**绘制的直线不与任何其他连线（非水平线）相交**。

以这种方法绘制线条，并返回我们可以绘制的最大连线数。

<div align=center><img src=LeetCode\1035.png width=30%></div>
 
```
示例 1：

输入：A = [1,4,2], B = [1,2,4]
输出：2
解释：
我们可以画出两条不交叉的线，如上图所示。
我们无法画出第三条不相交的直线，因为从 A[1]=4 到 B[2]=4 的直线将与从 A[2]=2 到 B[1]=2 的直线相交。
示例 2：

输入：A = [2,5,1,2,5], B = [10,5,2,1,5,2]
输出：3
示例 3：

输入：A = [1,3,7,1,7,5], B = [1,9,2,5,1]
输出：2
```

提示：
```
1 <= A.length <= 500
1 <= B.length <= 500
1 <= A[i], B[i] <= 2000
```

**思路与算法：最长公共子序列**

链接：https://leetcode-cn.com/problems/uncrossed-lines/solution/you-yi-ge-dong-tai-gui-hua-by-johnwii/

举例，

数组 var A = int[] {1, 3, 5, 7, 9, 11, 13, 15, 17}
数组 var B = int[] {7, 9, 5, 4, 3, 2, 1, 17, 15}

两个数组可以构建出3条不相交的线：

<div align=center><img src=LeetCode\1035_1.png></div>



<div align=center><img src=LeetCode\1035_2.png></div>

`dp[i][j]`表示A截止到i，B截止到j点，此时的最大连线数。

转移方程为：

- 当`A[i] == B[j]`时: `dp[i][j] = dp[i - 1][j - 1] + 1`
- 当`A[i] != B[j]`时: `dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])`

```java
class Solution {
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i ++) {
            for (int j = 1; j <= B.length; j ++) {
                dp[i][j] = A[i - 1] == B[j - 1] ? dp[i - 1][j - 1] + 1 :
                                                  Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[A.length][B.length];
    }
}
```

## 1143. 最长公共子序列(中等)

给定两个字符串`text1`和`text2`，返回这两个字符串的最长公共子序列的长度。

一个字符串的**子序列**是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。

例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。

若这两个字符串没有公共子序列，则返回 0。

 
```
示例 1:

输入：text1 = "abcde", text2 = "ace" 
输出：3  
解释：最长公共子序列是 "ace"，它的长度为 3。

示例 2:

输入：text1 = "abc", text2 = "abc"
输出：3
解释：最长公共子序列是 "abc"，它的长度为 3。

示例 3:

输入：text1 = "abc", text2 = "def"
输出：0
解释：两个字符串没有公共子序列，返回 0。
```

提示:
```
1 <= text1.length <= 1000
1 <= text2.length <= 1000
输入的字符串只含有小写英文字符。
```

**思路与算法：**
链接：https://leetcode-cn.com/problems/longest-common-subsequence/solution/dong-tai-gui-hua-tu-wen-jie-xi-by-yijiaoqian/

最长公共子序列（Longest Common Subsequence，简称 LCS）是一道非常经典的面试题目，因为它的解法是典型的**二维动态规划**，大部分比较困难的字符串问题都和这个问题一个套路，比如说**编辑距离**。而且，这个算法稍加改造就可以用于解决其他问题，所以说LCS算法是值得掌握的。

所谓子序列，就是要保留原始顺序，但可以是不连续的。审题之后你可能会有疑问，这个问题为啥就是动态规划来解决呢？

因为子序列类型的问题，穷举出所有可能的结果都不容易，而动态规划算法做的就是**穷举 + 剪枝**，它俩天生一对儿。所以可以说只要涉及子序列问题，十有八九都需要动态规划来解决。


**第一步，一定要明确 dp 数组的含义。**

对于两个字符串的动态规划问题，套路是通用的。

比如说对于字符串 s1 和 s2，它们的长度分别是 m、n，一般来说都要构造一个这样的 DP table：`int[][] dp = new int[m+1][n+1]`。

这里为什么要加1，原因是你可以不加1，但是不加1你就会用其它限制条件来确保这个index是有效的，而当你加1之后你就不需要去判断只是**让索引为0的行和列表示空串**。


**第二步，定义 base case**
我们专门**让索引为0的行和列表示空串**，`dp[0][...]` 和 `dp[...][0]` 都**应该初始化为0**，这就是base case。

**第三部，找状态转移方程**

这是动态规划最难的一步，我们来通过案例推导出来。

对于 `text1：abcde` 和 `text2：ace` 两个字符串，我们定义两个指针进行遍历 `i` 和 `j`。

遍历 `text1` 长度为 `m`，定义指针 `i`，从 $0～m$。**固定 `i` 指针（i == 1）位置**，接下来开始遍历 `text2` 长度为 `n`，定义指针 `j`，从 $0～n$。



<div align=center><img src=LeetCode\1143.jpeg></div>

- 第一次遍历 `i = 1, j = 1`，两个a相同所以 `dp[1][1] = 1`

- 第二次遍历 `i = 1, j = 2`，a与c不等，也不能是0，这里**需转换成 a 与 ac 最长子序列**，这里需要把之前的关系传递过来，所以`dp[1][2] = 1`

- 第三次遍历 `i = 1, j = 3`，a与e不相同，把之前的关系传递过来，所以`dp[1][3] = 1`
`text2：ace` 已经走完来第一轮，接下来`text1：abcde` 走到来b字符。

- 第四次遍历 `i = 2, j = 1`，就是需要比较ab与a的最长子串，**把之前的关系传递过来**，所以`dp[2][1] = 1`依次类推...

我们会发现遍历两个串字符，**当不同时，需要考虑两层遍历前面的值（关系传递），也就是<font color=red>左边和上边的其中较大的值</font>，当相同时，需要考虑各自不包含当前字符串的子序列长度，再加上1**。

因此可以得出：

- 现在对比的这**两个字符不相同**的，那么我们要取它的「**要么是text1往前退一格，要么是text2往前退一格，两个的最大值**」：`dp[i + 1][j + 1] = Math.max(dp[i+1][j], dp[i][j+1]);`

- 对比的**两个字符相同**，去找它们前面各退一格的值加1即可：`dp[i+1][j+1] = dp[i][j] + 1;`


```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 获取两个串字符
                char c1 = text1.charAt(i), c2 = text2.charAt(j);
                if (c1 == c2) {
                    // 去找它们前面各退一格的值加1即可
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    //要么是text1往前退一格，要么是text2往前退一格，两个的最大值
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[m][n];
    }
}
```


# 树

## 面试题 04.02. 最小高度树(简单)

给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵**高度最小的二叉搜索树**。

```
示例:
给定有序数组: [-10,-3,0,5,9],

一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：

          0 
         / \ 
       -3   9 
       /   / 
     -10  5 
```

二叉查找树（Binary Search Tree），（又：二叉搜索树，二叉排序树）它或者是一棵空树，或者是具有下列性质的二叉树： 

- 若它的左子树不空，则**左子树上所有结点的值均小于它的根结点的值**； 
- 若它的右子树不空，则**右子树上所有结点的值均大于它的根结点的值**； 
- 它的左、右子树也分别为二叉排序树。

二叉搜索树作为一种经典的数据结构，它既有链表的快速插入与删除操作的特点，又有数组快速查找的优势；所以应用十分广泛，例如在文件系统和数据库系统一般会采用这种数据结构进行高效率的排序与检索操作。


**思路与算法：**

链接：https://leetcode-cn.com/problems/minimum-height-tree-lcci/solution/di-gui-gou-jian-by-zui-weng-jiu-xian/

要求高度最小，保持高度平衡，也就是说**左右子树的节点个数应该尽可能接近**，那么可以

1. **用nums数组的中间值mid作为根节点**，根据mid划分左子数组和右子数组。左子数组构建左子树，右子数组构建右子树。

2. 那么现在的问题就转化为**怎么用左子数组构建左子树/右子数组构建右子树**

3. 以左子数组构建左子树为例；为了保持高度平衡，继续采用1中的划分方法，划分出新的左子数组和右子数组；

4. 当左子数组/右子数组为空时，返回null。

右子数组构建右子树的过程与上述相同。

<div align=center><img src=LeetCode\4.2.png width=60%></div>

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int low, int high) {
        // low > high表示子数组为空
        if (low > high) {
            return null;
        }

        // 以mid作为根节点；
        // 计算时要加low，防止最后出现mid=(4-4)/0=0，实际位置应为(4-4)/0+4
        int mid = (high - low) / 2 + low;
        TreeNode root = new TreeNode(nums[mid]);

        // 左子数组[low, mid -1]构建左子树
        root.left = helper(nums, low, mid - 1);

        // 右子数组[mid + 1, high]构建右子树
        root.right = helper(nums, mid + 1, high);

        return root;
    }
}
```

复杂度分析：

- 数组中的元素都使用1次，时间复杂度为$O(n)$。

- 递归使用栈辅助空间，空间复杂度$O(n)$。


## 遍历树

<div align=center><img src=LeetCode\遍历树.jpg></div>








## 94. 二叉树的中序遍历(中等)



给定一个二叉树，返回它的中序遍历。

```
输入: [1,null,2,3]
   1
    \
     2
    /
   3

输出: [1,3,2]
```

### 递归

```java
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_94_二叉树的中序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0 递归法
 */

public class InorderTraversal_v1 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    public void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            if (node.left != null) {
                inorderTraversal(node.left, result);
            }
            result.add(node.val);
            if (node.right != null) {
                inorderTraversal(node.right, result);
            }
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$。递归函数$T(n) = 2 \cdot T(n/2)+1$。
- 空间复杂度：最坏情况下需要空间$O(n)$，平均情况为$O(\log n)$。




### 迭代

使用栈：
<div align=center><img src=LeetCode\94.gif></div>
<div align=center><img src=LeetCode\94.jpg></div>

```java
package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * leetcode_94_二叉树的中序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 2.0 迭代法
 */

public class InorderTraversal_v2 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || ! stack.isEmpty()) {
            // 先将根节点cur和所有的左孩子入栈并加入结果中，直至cur为空
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            // 每弹出一个栈顶元素，就到达它的右孩子
            // 再将这个节点当作 cur 重新按上面的步骤来一遍，直至栈为空
            curr = stack.pop();
            result.add(curr.val);
            curr = curr.right;
        }

        return result;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$。
- 空间复杂度：$O(n)$。






## 102. 二叉树的层序遍历(中等)

**题目描述：**

给一个二叉树，请返回其按层序遍历得到的节点值。即逐层地，从左到右访问所有节点。

**示例：**

```
二叉树：[3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7

返回其层次遍历结果：

[[3],[9,20],[15,7]]
```

<div align=center><img src=LeetCode\102.png width=80%></div>


### 队列

链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal/solution/die-dai-di-gui-duo-tu-yan-shi-102er-cha-shu-de-cen/

BFS使用**队列**，把每个还没有搜索到的点依次放入队列，然后再弹出队列的头部元素当做当前遍历点。


广度优先需要用队列作为辅助结构，我们先将根节点放到队列中，然后不断遍历队列。

<div align=center><img src=LeetCode\102.jpg width=60%></div>

**首先拿出根节点，如果左子树/右子树不为空，就将他们放入队列中**。第一遍处理完后，根节点已经从队列中拿走了，而根节点的两个孩子已放入队列中了，现在队列中就有两个节点 2 和 5。

<div align=center><img src=LeetCode\102_1.jpg width=60%></div>

第二次处理，会将 2 和 5 这两个节点从队列中拿走，然后再将 2 和 5 的子节点放入队列中，现在队列中就有三个节点 3，4，6。

<div align=center><img src=LeetCode\102_2.jpg width=60%></div>

```java
import java.util.*;	
class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if(root == null) {
            return new ArrayList<List<Integer>>();
        }
		
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        // 将根节点放入队列中，然后不断遍历队列
        queue.add(root);
        while(queue.size() > 0) {
            // 获取当前队列的长度，这个长度相当于 当前这一层的节点个数
            int size = queue.size();
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            // 将队列中的元素都拿出来(也就是获取这一层的节点)，放到临时list中
            // 如果节点的左/右子树不为空，也放入队列中
            for(int i = 0; i < size; i++) {
                TreeNode t = queue.remove();
                tmp.add(t.val);
                if(t.left != null) {
                    queue.add(t.left);
                }
                if(t.right != null) {
                    queue.add(t.right);
                }
            }
            // 将临时list加入最终返回结果中
            res.add(0, tmp);
        }
        return res;
    }
}
```

- 时间复杂度：$O(n)$
- 空间复杂度：$O(n)$


BFS总共有两个模板：

1. 如果**不需要确定当前遍历到了哪一层**，BFS模板如下
```
while queue 不空：
    cur = queue.pop()
    for 节点 in cur的所有相邻节点：
        if 该节点有效且未访问过：
            queue.push(该节点)
```

2. 如果要确定当前遍历到了哪一层，BFS模板如下
这里增加了level表示当前遍历到二叉树中的哪一层了，也可以理解为在一个图中，现在已经走了多少步了。size表示在当前遍历层有多少个元素，也就是队列中的元素数，我们把这些元素一次性遍历完，即把当前层的所有元素都向外走了一步。

```
level = 0
while queue 不空：
    size = queue.size()
    while (size --) {
        cur = queue.pop()
        for 节点 in cur的所有相邻节点：
            if 该节点有效且未被访问过：
                queue.push(该节点)
    }
    level ++;
```

本题要求二叉树的层次遍历，所以同一层的节点应该放在一起，故使用模板二。

使用队列保存每层的所有节点，每次把队列里的原先所有节点进行出队列操作，再把每个元素的非空左右子节点进入队列。因此即可得到每层的遍历。


**代码实现：**

```java {.line-numbers highlight=37-40}
package solution;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * leetcode_102_二叉树的层序遍历
 */

public class BinaryTreeLevelOrder {
    public List<List<Integer>> levelOrder(TreeNode node) {
        // 最后输出结果
        List<List<Integer>> result = new ArrayList<>();
        // 存放每层的结点
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(node);

        while (! queue.isEmpty()) {
            int size = queue.size();

            // 每一层的遍历结果
            List<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                // queue.peek()返回队列的头元素，不删除
                TreeNode currentNode = queue.peek();
                /*
                Queue中remove()和poll()都是用来从队列头部删除一个元素。
                在队列元素为空的情况下，remove() 方法会抛出NoSuchElementException异常，
                poll() 方法只会返回null
                 */
                queue.poll();

                // 防止当结点的左结点为null，右结点不为null时，出现queue=[null, XX]
                if (currentNode == null) {
                    continue;
                }

                level.add(currentNode.val);

                // 利用完当前结点后，将其左、右结点加入队列中
                queue.offer(currentNode.left);
                queue.offer(currentNode.right);
            }

            if (! level.isEmpty()) {
                // 将每一层的结果加入最终结果列表中
                result.add(level);
            }
        }

        return result;
    }
}
```








## 104. 二叉树的最大深度(简单)

**题目：**

给定一个二叉树，找出其最大深度。二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

**示例：**
```
给定二叉树 [3,9,20,null,null,15,7]，

    3
   / \
  9  20
    /  \
   15   7
返回它的最大深度 3 。
```

### 递归，DFS(深度优先搜索)

$H(1) = 1 + max(H(2), H(7))$
即$H(1) = 1 + max(H(1).left, H(1).right)$
<div align=center><img src=LeetCode\104.jpg></div>

**代码：**
```java
package solution;

/**
 * leetcode_104_二叉树最大深度
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 *
 * Definition for a binary tree node.
 *
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *
 *     TreeNode(int val) {
 *         this.val = val;
 *     }
 *
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

public class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = maxDepth(node.left);
            int rightHeight = maxDepth(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}
```

**复杂度分析：**

时间复杂度：我们每个结点只访问一次，因此时间复杂度为$O(N)$，其中$N$是结点的数量。

空间复杂度：在最糟糕的情况下，树是完全不平衡的，例如每个结点只剩下左子结点，递归将会被调用$N$次（树的高度），因此保持调用栈的存储将是$O(N)$。但在最好的情况下（树是完全平衡的），树的高度将是$\log(N)$。因此，在这种情况下的空间复杂度将是$O(\log(N))$。


在递归中，如果层级过深，很可能保存过多的临时变量，导致栈溢出。

事实上，**函数调用的参数是通过栈空间来传递的**，在调用过程中会占用线程的栈资源。而递归调用，只有走到最后的结束点后函数才能依次退出，而未到达最后的结束点之前，占用的栈空间一直没有释放，如果递归调用次数过多，就可能导致占用的栈资源超过线程的最大值，从而导致栈溢出，导致程序的异常退出。

99%的递归转非递归，都可以通过栈来进行实现。 




## 107. 二叉树的层次遍历 II 从下到上(简单)

给定一个二叉树，返回其节点值**自底向上的层次遍历**。（即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）

例如：
给定二叉树 `[3,9,20,null,null,15,7]`

```
    3
   / \
  9  20
    /  \
   15   7
```

返回其自底向上的层次遍历为：
```
[
  [15,7],
  [9,20],
  [3]
]
```




## 144. 二叉树的前序遍历(中等)

给定一个二叉树，返回它的前序遍历

```
输入: [1,null,2,3]  
   1
    \
     2
    /
   3 

输出: [1,2,3]
```

<div align=center><img src=LeetCode\二叉树遍历.png width=30%></div>

**从子树的角度来观察：**

- 如果按照`根节点 -> 左孩子 -> 右孩子`的方式遍历，即**先序遍历**：`1 2 4 5 3 6 7`；
- 如果按照`左孩子 -> 根节点 -> 右孩子`的方式遍历，即**中序遍历**：`4 2 5 1 6 3 7`；
- 如果按照`左孩子 -> 右孩子 -> 根节点`的方式遍历，即**后序遍历**：`4 5 2 6 7 3 1`；
- **层次遍历**就是按照每一层从左向右的方式进行遍历：`1 2 3 4 5 6 7`。

### 递归

```java
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_144_二叉树的前序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0 递归法
 */

public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    public void preorderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.val);
            // 把node.left看成以其为根的子树
            if (node.left != null) {
                preorderTraversal(node.left, result);
            }
            // 把node.right看成以其为根的子树
            if (node.right != null) {
                preorderTraversal(node.right, result);
            }
        }
    }
}
```

### 迭代

使用**栈**来进行迭代，过程如下：
- 初始化栈，并将根节点入栈；
- 当栈不为空时：
  - 弹出栈顶元素node，并将值添加到结果中；
  - 如果node的右子树非空，将右子树入栈；
  - 如果node的左子树非空，将左子树入栈；

由于栈是“先进后出”的顺序，所以入栈时**先将右子树入栈**，这样使得前序遍历结果为 “根->左->右”的顺序。

<div align=center><img src=LeetCode\144.gif></div>

```java
package solution;

import java.util.LinkedList;
import java.util.List;

/**
 * leetcode_144_二叉树的前序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 2.0 迭代法
 */

public class PreorderTraversal_v2 {
    public List<Integer> preorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();

        if (root == null) {
            return output;
        }
        
        // 从根节点开始，每次迭代弹出当前栈顶元素，并将其孩子节点压入栈中
        stack.add(root);
        while (! stack.isEmpty()) {
            // 每次迭代弹出当前栈顶元素
            TreeNode node = stack.pollLast();
            output.add(node.val);
            
            // 先压右孩子
            if (node.right != null) {
                stack.add(node.right);
            }
            
            // 再压左孩子
            if (node.left != null) {
                stack.add(node.left);
            }
        }
        
        return output;
    }
}
```

**算法复杂度**

- 时间复杂度：**访问每个节点恰好一次**，时间复杂度为$O(N)$，其中$N$是节点的个数，也就是树的大小。
- 空间复杂度：取决于树的结构，最坏情况存储整棵树，因此空间复杂度是$O(N)$。









## 145. 二叉树的后序遍历(困难)

给定一个二叉树，返回它的后序遍历。
```
输入: [1,null,2,3]  
   1
    \
     2
    /
   3 

输出: [3,2,1]
```

### 递归

```java
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_145_二叉树的后序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0 递归
 */

public class PostorderTraversal {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result;
    }
    
    public void postorderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            if (node.left != null) {
                postorderTraversal(node.left, result);
            }
            if (node.right != null) {
                postorderTraversal(node.right,result);
            }
            result.add(node.val);
        }
    }
}
```

### 迭代

https://leetcode-cn.com/problems/binary-tree-postorder-traversal/solution/di-gui-die-dai-qu-qiao-san-chong-fang-fa-quan-jie-/

从根节点开始依次迭代，弹出栈顶元素输出到输出列表中，然后依次压入它的所有孩子节点，**按照从上到下、从左至右的顺序依次压入栈中**。因为深度优先搜索后序遍历的顺序是从下到上、从左至右，所以需要**将输出列表逆序输出**。

我们已知**后序遍历**的节点访问顺序为：`左 → 右 → 中`；我们将这个次序颠倒过来：`中 → 右 → 左`；有没有想到前序遍历的节点访问顺序呢——`中 → 左 → 右`；因此，我们可以**将前序遍历代码中的压栈顺序进行调整，并将结果逆序输出就可以啦**！


```java
package solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * leetcode_145_二叉树的后序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 2.0 迭代
 */

public class PostorderTraversal_v2 {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        
        if (root == null) {
            return output;
        }
        
        stack.add(root);
        while (! stack.isEmpty()) {
            TreeNode node = stack.pollLast();
            // 每次在链表的头部插入元素
            output.addFirst(node.val);
            
            if (node.left != null) {
                stack.add(node.left);
            }
            
            if (node.right != null) {
                stack.add(node.right);
            }
        }
        
        return output;
    }
}
```

**复杂度分析**

- 时间复杂度：**访问每个节点恰好一次**，因此时间复杂度为$O(N)$，其中$N$是节点的个数，也就是树的大小。
- 空间复杂度：取决于树的结构，最坏情况需要保存整棵树，因此空间复杂度为$O(N)$。



## 101. 对称二叉树(简单)

**题目：**
给定一个二叉树，检查它是否是镜像对称的。

**示例：**
```
例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

    1
   / \
  2   2
 / \ / \
3  4 4  3
 

但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

    1
   / \
  2   2
   \   \
   3    3
```

**思路与算法：递归**

递归结束条件：

- 都为空指针则返回`true`
- 只有一个为空则返回`false`

递归过程：

- 判断两个指针当前节点值是否相等
- 判断`A`的右子树与`B`的左子树是否对称
- 判断`A`的左子树与`B`的右子树是否对称

短路：

在递归判断过程中存在短路现象，也就是做`与`操作时，如果前面的值返回`false`则后面的不再进行计算。

判断二叉树是否对称，就像照镜子一样（复制了一个相同的树）！
<div align=center><img src=LeetCode\101.jpg></div>

**代码：**
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    public boolean isMirror(TreeNode t1, TreeNode t2){
        if(t1 == null && t2 == null)
            return true;
        if(t1 == null || t2 == null)
            return false;
        
        return (t1.val == t2.val) && isMirror(t1.right, t2.left) && isMirror(t1.left, t2.right);
    }
}
```






## 108. **将有序数组转换为二叉搜索树(简单)

将一个按照**升序**排列的有序数组，转换为一棵高度平衡二叉搜索树。

**高度平衡二叉树定义：**

一个高度平衡二叉树是指一个二叉树**每个节点的左右两个子树的高度差的绝对值不超过1**。

**二叉搜索树定义：**

二叉查找树（Binary Search Tree），（又：二叉搜索树，二叉排序树）它或者是一棵空树，或者是具有下列性质的二叉树：
- 若它的左子树不空，则**左子树上所有结点的值均小于它的根结点的值**； 
- 若它的右子树不空，则**右子树上所有结点的值均大于它的根结点的值**； 
- 它的左、右子树也分别为二叉排序树。

**示例：**
```
给定有序数组: [-10,-3,0,5,9],

一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：

      0
     / \
   -3   9
   /   /
 -10  5
```

**思路与算法：**

遍历树的两种通用策略：

- 深度优先遍历（DFS）

    这种方法以深度depth优先为策略，从根节点开始一直遍历到某个叶子节点，然后回到根节点，再遍历另外一个分支。根据根节点，左孩子节点和右孩子节点的访问顺序又可以将`DFS`细分为**先序遍历**`preorder`，**中序遍历**`inorder`和**后序遍历**`postorder`。

- 广度优先遍历（BFS）

    按照高度顺序，从上往下逐层遍历节点。先遍历上层节点再遍历下层节点。

下图中按照不同的方法遍历对应子树，得到的遍历顺序都是`1-2-3-4-5`：
<div align=center><img src=LeetCode\bfs_dfs.png></div>

**将有序数组转换为二叉搜索树的结果为什么*不唯一*？**

**<font color=red>二叉搜索树的中序遍历是一个升序序列</font>**。将有序数组作为输入，可以把该问题看做**根据中序遍历序列创建二叉搜索树**。

这个问题的答案唯一吗？例如：是否可以根据中序遍历序列和二叉搜索树之间是否一一对应，答案是否定的。

下面是一些关于`BST`（二叉查找树）的知识：
- 中序遍历不能唯一确定一棵二叉搜索树。
- 先序和后序遍历不能唯一确定一棵二叉搜索树。
- 先序/后序遍历和中序遍历的关系：`inorder = sorted(postorder) = sorted(preorder)`
- **<font color=red>中序+后序、中序+先序可以唯一确定一棵二叉树</font>**。

因此，**“有序数组 -> BST”有多种答案**。

<div align=center><img src=LeetCode\bfs_dfs1.png></div>

如果添加一个附件条件：树的高度应该是平衡的。例如：**每个节点的两棵子树高度差不超过1**。这种情况下答案唯一吗？

仍然不是唯一的：
<div align=center><img src=LeetCode\bfs_dfs2.png></div>

**高度平衡意味着每次必须选择中间数字作为根节点**。这对于**奇数个数**的数组是有用的，但对**偶数个数**的数组没有预定义的选择方案。

<div align=center><img src=LeetCode\bfs_dfs3.png></div>

对于偶数个数的数组，要么选择中间位置左边的元素作为根节点，要么选择中间位置右边的元素作为根节点，不同的选择方案会创建不同的平衡二叉搜索树。

**方法：**
中序遍历：始终选择**中间位置左边元素**作为根节点
<div align=center><img src=LeetCode\bfs_dfs4.png></div>

- 方法`helper(left, right)`使用数组`numsnums`中索引从`left`到`right`的元素创建`BST`：
    - 如果`left > right`，子树中不存在元素，返回空。
    - 找出中间元素：`p = (left + right) // 2`。
    - 创建根节点：`root = TreeNode(nums[p])`。
    - 递归创建左子树`root.left = helper(left, p - 1)`和右子树`root.right = helper(p + 1, right)`。

- 返回`helper(0, len(nums) - 1)`。

**代码：**
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right) / 2;

        TreeNode root = new TreeNode(nums[mid]);
        root.left = helper(nums, left, mid - 1);
        root.right = helper(nums, mid + 1, right);
        return root;
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(n)$，其中$n$是数组的长度。每个数字只访问一次。

- 空间复杂度：$O(\log n)$，其$n$是数组的长度。空间复杂度不考虑返回值，因此空间复杂度主要取决于递归栈的深度，递归栈的深度是$O(\log n)$。




## 226. 翻转二叉树(简单)

翻转一棵二叉树。

```
示例：

输入：

     4
   /   \
  2     7
 / \   / \
1   3 6   9

输出：

     4
   /   \
  7     2
 / \   / \
9   6 3   1
```

反转一棵空树结果还是一颗空树。

对于一棵根为$r$，左子树为$left$， 右子树为$right$的树来说，它的反转树是一颗根为$r$，左子树为$left$的反转树，右子树为$right$的反转树的树。

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);
        root.left = right;
        root.right = left;
    
        return root;
    }
}
```

复杂度分析：

- 既然树中的**每个节点都只被访问一次**，那么**时间复杂度就是$O(n)$**，其中$n$是树中节点的个数。在反转之前，不论怎样我们至少都得访问每个节点至少一次，因此这个问题无法做地比$O(n)$更好了。

- 本方法使用了递归，在最坏情况下**栈内需要存放$O(h)$个方法调用**，其中$h$是树的高度。由于$h\in O(n)$，可得出**空间复杂度为$O(n)$**。












## 230. 二叉搜索树中第K小的元素(中等)


给定一个二叉搜索树，编写一个函数 kthSmallest 来查找其中第 k 个最小的元素。

说明：
你可以假设 k 总是有效的，1 ≤ k ≤ 二叉搜索树元素个数。

```
示例 1:

输入: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
输出: 1

示例 2:

输入: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
输出: 3
```

**思路与算法：**

**<font color=red>BST的中序遍历是升序序列！</font>**

<div align=center><img src=LeetCode\230.jpg></div>

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        ArrayList<Integer> nums = inorder(root, new ArrayList<Integer>());
        return nums.get(k - 1);
    }

    public ArrayList<Integer> inorder(TreeNode root, ArrayList<Integer> arr) {
        if (root == null) return arr;

        inorder(root.left, arr);
        arr.add(root.val);
        inorder(root.right, arr);

        return arr;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，遍历了整个树。
- 空间复杂度：$O(N)$，用了一个数组存储中序序列。


## 235. 二叉搜索树的最近公共祖先(简单)

给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。

公共祖先的定义为：“对于有根树T的两个结点p、q，最近公共祖先表示为一个结点x，满足x是 p、q的祖先且**x的深度尽可能大**（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉搜索树:  `root = [6,2,8,0,4,7,9,null,null,3,5]`

<div align=center><img src=LeetCode\235.png></div>

```
示例 1:

输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
输出: 6 
解释: 节点 2 和节点 8 的最近公共祖先是 6。

示例 2:

输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
输出: 2
解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
```

说明:
```
所有节点的值都是唯一的。
p、q 为不同节点且均存在于给定的二叉搜索树中。
```

**存储父节点**

可以**用哈希表存储所有节点的父节点**，然后我们就可以**利用节点的父节点信息从$p$结点开始不断往上跳，并记录已经访问过的节点；再从$q$节点开始不断往上跳，如果碰到已经访问过的节点，那么这个节点就是我们要找的最近公共祖先**。

**算法**
- 从根节点开始遍历整棵二叉树，**用哈希表记录每个节点的父节点指针**。
- 从$p$节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。
- 同样，我们再从$q$节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是$p$和$q$的深度最深的公共祖先，即`LCA节点`。


```java
package solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * leetcode_236_二叉树的最近公共祖先
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0 用哈希表存储所有节点的父节点
 */

public class LowestCommonAncestor {
    Map<Integer, TreeNode> parent = new HashMap<>();
    Set<Integer> visited = new HashSet<>();

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针
        deepFirstSearch(root);

        // 从p节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点
        while (p != null) {
            visited.add(p.val);
            // 得到p节点所对应的父节点
            p = parent.get(p.val);
        }

        // 再从q节点开始不断往它的祖先移动
        // 如果有祖先已经被访问过，即意味着这是p和q的深度最深的公共祖先
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }

        return null;
    }

    /**
     * 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针
     */
    public void deepFirstSearch(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            deepFirstSearch(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            deepFirstSearch(root.right);
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是二叉树的节点数。二叉树的所有节点有且只会被访问一次，从$p$和$q$节点往上跳经过的祖先节点个数不会超过$N$，因此总的时间复杂度为$O(N)$。

- 空间复杂度：$O(N)$，其中$N$是二叉树的节点数。
  - **递归调用的栈深度取决于二叉树的高度**，二叉树最坏情况下为一条链，此时高度为$N$，因此空间复杂度为$O(N)$。
  - 哈希表存储每个节点的父节点也需要$O(N)$的空间复杂度
  - 因此最后总的空间复杂度为$O(N)$。


## 236. 二叉树的最近公共祖先(中等)

**题目描述：**

给定一个二叉树，找到该树中两个指定节点的最近公共祖先。

公共祖先的定义为：“对于有根树T的两个结点p、q，最近公共祖先表示为一个结点x，满足x是 p、q的祖先且**x的深度尽可能大**（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉树:  `root = [3,5,1,6,2,0,8,null,null,7,4]`

<div align=center><img src=LeetCode\236.png></div>

```
示例 1:
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出: 3
解释: 节点5和节点1的最近公共祖先是节点3。

示例 2:
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
输出: 5
解释: 节点5和节点4的最近公共祖先是节点5。因为根据定义最近公共祖先节点可以为节点本身。
```

说明:
- 所有节点的值都是唯一的。
- p、q为不同节点且均存在于给定的二叉树中。


**存储父节点**

可以**用哈希表存储所有节点的父节点**，然后我们就可以**利用节点的父节点信息从$p$结点开始不断往上跳，并记录已经访问过的节点；再从$q$节点开始不断往上跳，如果碰到已经访问过的节点，那么这个节点就是我们要找的最近公共祖先**。

**算法**
- 从根节点开始遍历整棵二叉树，**用哈希表记录每个节点的父节点指针**。
- 从$p$节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点。
- 同样，我们再从$q$节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是$p$和$q$的深度最深的公共祖先，即`LCA节点`。


```java
package solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * leetcode_236_二叉树的最近公共祖先
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0 用哈希表存储所有节点的父节点
 */

public class LowestCommonAncestor {
    Map<Integer, TreeNode> parent = new HashMap<>();
    Set<Integer> visited = new HashSet<>();

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针
        deepFirstSearch(root);

        // 从p节点开始不断往它的祖先移动，并用数据结构记录已经访问过的祖先节点
        while (p != null) {
            visited.add(p.val);
            // 得到p节点所对应的父节点
            p = parent.get(p.val);
        }

        // 再从q节点开始不断往它的祖先移动
        // 如果有祖先已经被访问过，即意味着这是p和q的深度最深的公共祖先
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }

        return null;
    }

    /**
     * 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针
     */
    public void deepFirstSearch(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            deepFirstSearch(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            deepFirstSearch(root.right);
        }
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是二叉树的节点数。二叉树的所有节点有且只会被访问一次，从$p$和$q$节点往上跳经过的祖先节点个数不会超过$N$，因此总的时间复杂度为$O(N)$。

- 空间复杂度：$O(N)$，其中$N$是二叉树的节点数。
  - **递归调用的栈深度取决于二叉树的高度**，二叉树最坏情况下为一条链，此时高度为$N$，因此空间复杂度为$O(N)$。
  - 哈希表存储每个节点的父节点也需要$O(N)$的空间复杂度
  - 因此最后总的空间复杂度为$O(N)$。




## 257. 二叉树的所有路径(简单)

给定一个二叉树，返回所有从根节点到叶子节点的路径。

说明: 叶子节点是指没有子节点的节点。

```
示例:

输入:

   1
 /   \
2     3
 \
  5

输出: ["1->2->5", "1->3"]
```

解释: 所有根节点到叶子节点的路径为: `1->2->5, 1->3`


**思路与算法：**

在递归遍历二叉树时，需要考虑当前的节点和它的孩子节点。

- 如果**当前的节点不是叶子节点**，则在当前的路径末尾添加该节点，并递归遍历该节点的每一个孩子节点。
- 如果**当前的节点是叶子节点**，则在当前的路径末尾添加该节点后，就得到了一条从根节点到叶子节点的路径，可以把该路径加入到答案中。


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        LinkedList<String> paths = new LinkedList();
        construct_paths(root, "", paths);
        return paths;
    }

    public void construct_paths(TreeNode root, String path, LinkedList<String> paths) {
        if (root != null) {
            path += Integer.toString(root.val);
            // 当前节点是叶子节点
            if ((root.left == null) && (root.right == null))
                // 把路径加入到答案中
                paths.add(path);  
            else {
                // 当前节点不是叶子节点，继续递归遍历
                path += "->";
                construct_paths(root.left, path, paths);
                construct_paths(root.right, path, paths);
            }
        }
    }
}
```


**复杂度分析**

- 时间复杂度：每个节点只会被访问一次，因此时间复杂度为$O(N)$，其中$N$表示节点数目。
- 空间复杂度：$O(N)$。这里不考虑存储答案 paths 使用的空间，仅考虑额外的空间复杂度。
  额外的空间复杂度为**递归时使用的栈空间**，在最坏情况下，当二叉树中每个节点只有一个孩子节点时，递归的层数为$N$，此时空间复杂度为$O(N)$。**在最好情况下，当二叉树为平衡二叉树时，它的高度为$\log(N)$，此时空间复杂度为$O(\log(N))$**。






## 429. N叉树的层序遍历(简单)

给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。

例如，给定一个 3叉树 :

<div align=center><img src=LeetCode\590.png width=50%></div>

返回其层序遍历:
```
[
    [1],
    [3,2,4],
    [5,6]
]
```

说明:

- 树的深度不会超过 1000。
- 树的节点总数不会超过 5000。


```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<List<Integer>> levelOrder(Node root) {      
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                level.add(node.val);
                queue.addAll(node.children);
            }
            result.add(level);
        }
        return result;
    }
}
```


## 559. N叉树的最大深度(简单)

给定一个 N 叉树，找到其最大深度。

最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。

例如，给定一个 3叉树 :

<div align=center><img src=LeetCode\590.png width=50%></div>

我们应返回其最大深度：3。

说明:

- 树的深度不会超过 1000。
- 树的节点总不会超过 5000。


```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        } else if (root.children.isEmpty()) {
            return 1;  
        } else {
            List<Integer> heights = new LinkedList<>();
            for (Node item : root.children) {
                heights.add(maxDepth(item)); 
            }
            return Collections.max(heights) + 1;
        }
    }
}
```


**复杂度分析**

- 时间复杂度：**每个节点遍历一次**，所以时间复杂度是$O(N)$，其中$N$为节点数。

- 空间复杂度：最坏情况下，树完全非平衡，例如每个节点有且仅有一个孩子节点，递归调用会发生$N$次（等于树的深度），所以存储调用栈需要$O(N)$。但是在最好情况下（树完全平衡），树的高度为$\log(N)$。所以在此情况下空间复杂度为$O(\log(N))$。




## 589. N叉树的前序遍历(简单)

给定一个N叉树，返回其节点值的前序遍历。

例如，给定一个3叉树：

<div align=center><img src=LeetCode\590.png width=50%></div>

返回其前序遍历: [1,3,5,6,2,4]。

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<Integer> list = new ArrayList<>();

    public List<Integer> preorder(Node root) {
        // 如果给出的树节点为空，直接返回，这里是递归的出口
        if(root == null) {
            return list;
        }

        // 如果该节点没有孩子节点了，就把该节点的value值加入list
        if(root.children.size() == 0) {
            list.add(root.val);
        } else {
            //关键在这，别忘了把这个父节点加入list
            list.add(root.val);
            // 遍历该节点的所有孩子节点
            for(Node node : root.children) {
                // 把孩子节点继续递归遍历
                preorder(node);
            }
        }
        return list;
    }
}
```

## 590. N叉树的后序遍历(简单)

给定一个N叉树，返回其节点值的后序遍历。

例如，给定一个3叉树：

<div align=center><img src=LeetCode\590.png width=50%></div>

返回其后序遍历: [5,6,3,2,4,1]

```java {.line-numbers highlight=38}
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Solution {
    public List<Integer> list = new ArrayList<>();

    public List<Integer> postorder(Node root) {
        // 如果给出的树节点为空，直接返回，这里是递归的出口
        if(root == null) {
            return list;
        }

        // 如果该节点没有孩子节点了，就把该节点的value值加入list
        if(root.children.size() == 0) {
            list.add(root.val);
        } else {
            // 遍历该节点的所有孩子节点
            for(Node node : root.children) {
                // 把孩子节点继续递归遍历
                postorder(node);
            }
            //关键在这，别忘了把这个父节点加入list
            list.add(root.val);
        }
        return list;
    }
}
```

## 617. 合并二叉树(简单)

给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。

你需要将他们合并为一个新的二叉树。合并的规则是**如果两个节点重叠，那么将他们的值相加作为节点合并后的新值**，否则不为NULL的节点将直接作为新二叉树的节点。

```
示例 1:

输入: 
	Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7                  
输出: 
合并后的树:
	     3
	    / \
	   4   5
	  / \   \ 
	 5   4   7
```

注意: 合并必须从两个树的根节点开始。


**思路与算法：**

链接：https://leetcode-cn.com/problems/merge-two-binary-trees/solution/dong-hua-yan-shi-di-gui-die-dai-617he-bing-er-cha-/

遍历二叉树很简单，用前序遍历就可以了，再依次把访问到的节点值相加，因为题目没有说不能改变树的值和结构，我们**不用再创建新的节点了，直接将树2合并到树1上再返回**就可以了。

递归的条件：

- 终止条件：树1的节点为null，或者树2的节点为null
- 递归函数内：将两个树的节点相加后，再赋给树1的节点。再递归的执行两个树的左节点，递归执行两个树的右节点


<div align=center><img src=LeetCode\617.gif width=80%></div>

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;

        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        
        return t1;
    }
}
```


复杂度分析

- 时间复杂度：$O(N)$，其中$N$是两棵树中节点个数的较小值。

- 空间复杂度：$O(N)$，在最坏情况下，会**递归$N$层**，需要$O(N)$的**栈空间**。



## 637. 二叉树的层平均值(简单)

给定一个非空二叉树，返回一个由每层节点平均值组成的数组。

```
输入：
    3
   / \
  9  20
    /  \
   15   7
输出：[3, 14.5, 11]
解释：
第 0 层的平均值是 3 ,  第1层是 14.5 , 第2层是 11 。因此返回 [3, 14.5, 11] 。
```

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public List <Double> averageOfLevels(TreeNode root) {
        List <Double> res = new ArrayList < > ();
        Queue <TreeNode> queue = new LinkedList < > ();

        queue.add(root);
        while (!queue.isEmpty()) {
            long sum = 0, count = 0;
            Queue <TreeNode> temp = new LinkedList<>();
            while (!queue.isEmpty()) {
                TreeNode n = queue.remove();
                sum += n.val;
                count++;
                if (n.left != null)
                    temp.add(n.left);
                if (n.right != null)
                    temp.add(n.right);
            }
            queue = temp;
            res.add(sum * 1.0 / count);
        }
        return res;
    }
}
```


## 669. 修剪二叉搜索树(简单)

给定一个二叉搜索树，同时给定最小边界L 和最大边界 R。通过修剪二叉搜索树，使得所有节点的值在[L, R]中 (R>=L) 。你可能需要改变树的根节点，所以结果应当返回修剪好的二叉搜索树的新的根节点。

```
示例 1:

输入: 
    1
   / \
  0   2

  L = 1
  R = 2

输出: 
    1
      \
       2

示例 2:

输入: 
    3
   / \
  0   4
   \
    2
   /
  1

  L = 1
  R = 3

输出: 
      3
     / 
   2   
  /
 1
```


**思路与算法：**

- 当$\text{node.val > R}$，那么修剪后的二叉树必定出现在节点的左边。
- 当$\text{node.val < L}$，那么修剪后的二叉树出现在节点的右边。
- 否则，我们将会修剪树的两边。


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) return root;
        if (root.val > R) return trimBST(root.left, L, R);
        if (root.val < L) return trimBST(root.right, L, R);

        root.left = trimBST(root.left, L, R);
        root.right = trimBST(root.right, L, R);
        return root;
    }
}
```


**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是给定的树的全部节点。我们**最多访问每个节点一次**。

- 空间复杂度：$O(N)$，即使我们没有明确使用任何额外的内存，在最糟糕的情况下，我们**递归调用的栈可能与节点数一样大**。





## 700. 二叉搜索树中的搜索(简单)

**题目描述：**

给定二叉搜索树(BST)的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。返回以该节点为根的子树。如果节点不存在，则返回NULL。

**示例：**

```
给定二叉搜索树:

        4
       / \
      2   7
     / \
    1   3

和值: 2

你应该返回如下子树:

      2     
     / \   
    1   3

在上述示例中，如果要找的值是 5，但因为没有节点值为 5，我们应该返回 NULL。
```

<div align=center><img src=LeetCode\700_1.png width=70%></div>


**思路与算法：**

**递归**实现非常简单：

- 如果根节点为空`root == null`或者根节点的值等于搜索值`val == root.val`，返回根节点。
- 如果`val < root.val`，进入根节点的左子树查找`searchBST(root.left, val)`。
- 如果`val > root.val`，进入根节点的右子树查找`searchBST(root.right, val)`。
- 返回根节点。

<div align=center><img src=LeetCode\700_2.png width=70%></div>

```java {.line-numbers highlight=16}
package solution;

/**
 * leetcode_700_二叉搜索树中的搜索
 * @author Chenzf 
 * @date 2020/7/12
 * @version 1.0 递归
 */

public class SearchInBinarySearchTree {
    public TreeNode searchBST(TreeNode node, int value) {
        if (node == null || value == node.val) {
            return node;
        }
        
        return value < node.val ? searchBST(node.left, value) : searchBST(node.right, value);
    }
}
```

**递归复杂度分析：**

- 时间复杂度：$\mathcal{O}(H)$，其中$H$是树高。平均时间复杂度为$\mathcal{O}(\log N)$，最坏时间复杂度为$\mathcal{O}(N)$。

- 空间复杂度：$\mathcal{O}(H)$，**递归栈**的深度为$H$。平均情况下深度为$\mathcal{O}(\log N)$，最坏情况下深度为$\mathcal{O}(N)$。


为了降低空间复杂度，将递归转换为**迭代**：

- 如果根节点不空`root != null`且根节点不是目的节点`val != root.val`：
  - 如果`val < root.val`，进入根节点的左子树查找`root = root.left`。
  - 如果`val > root.val`，进入根节点的右子树查找`root = root.right`。
- 返回root。

<div align=center><img src=LeetCode\700_3.png width=70%></div>

```java {.line-numbers highlight=12-13}
package solution;

/**
 * leetcode_700_二叉搜索树中的搜索
 * @author Chenzf
 * @date 2020/7/12
 * @version 2.0 迭代
 */

public class SearchInBinarySearchTree {
    public TreeNode searchBST(TreeNode node, int value) {
        while (node != null && value != node.val) {
            node = value < node.val ? node.left : node.right;
        }
        
        return node;
    }
}
```

**递归复杂度分析：**

- 时间复杂度：$\mathcal{O}(H)$，其中$H$是树高。平均时间复杂度为$\mathcal{O}(\log N)$，最坏时间复杂度为$\mathcal{O}(N)$。
- 空间复杂度：$\mathcal{O}(1)$，恒定的额外空间。


**递归与迭代的区别：**

- 递归：**重复调用函数自身**实现循环称为递归；
- 迭代：利用变量的**原值推出新值**称为迭代，或者说迭代是**函数内某段代码实现循环**；


## 897. 递增顺序查找树(简单)

给你一个树，请你 按中序遍历 重新排列树，使树中最左边的结点现在是树的根，并且每个结点没有左子结点，只有一个右子结点。

 
```
示例 ：

输入：[5,3,6,2,4,null,8,1,null,null,null,7,9]

       5
      / \
    3    6
   / \    \
  2   4    8
 /        / \ 
1        7   9

输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]

 1
  \
   2
    \
     3
      \
       4
        \
         5
          \
           6
            \
             7
              \
               8
                \
                 9  
```

提示：
```
给定树中的结点数介于 1 和 100 之间。
每个结点都有一个从 0 到 1000 范围内的唯一整数值。
```


**思路与算法：**

我们在树上进行**中序遍历**，就**可以<font color=red>从小到大</font>得到树上的节点**。我们把这些节点的对应的值存放在数组中，它们已经有序。接着我们直接根据数组构件题目要求的树即可。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {    
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> vals = new ArrayList();
        inorder(root, vals);
        TreeNode ans = new TreeNode(0), cur = ans;
        // 创建树
        for (int v: vals) {
            cur.right = new TreeNode(v);
            cur = cur.right;
        }
        return ans.right;
    }

    public void inorder(TreeNode node, List<Integer> vals) {
        if (node == null) return;
        inorder(node.left, vals);
        vals.add(node.val);
        inorder(node.right, vals);
    }
}
```

复杂度分析

- 时间复杂度：$O(N)$，其中$N$是树上的节点个数。

- 空间复杂度：$O(N)$。


## 938. *二叉搜索树的范围和(简单)

给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。

二叉搜索树保证具有唯一的值。

 
```
示例 1：

输入：root = [10,5,15,3,7,null,18], L = 7, R = 15
输出：32

示例 2：

输入：root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
输出：23
```

提示：
```
树中的结点数量最多为 10000 个。
最终的答案保证小于 2^31。
```

**思路与算法：**

链接：https://leetcode-cn.com/problems/range-sum-of-bst/solution/hua-jie-suan-fa-938-er-cha-sou-suo-shu-de-fan-wei-/

递归终止条件：
- 当前节点为 null 时返回 0
- 当前节点 X < L 时则返回右子树之和
- 当前节点 X > R 时则返回左子树之和
- 当前节点 X >= L 且 X <= R 时则返回：当前节点值 + 左子树之和 + 右子树之和

<div align=center><img src=LeetCode\938.jpg></div>


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }
}


class Solution {
    int ans;
    public int rangeSumBST(TreeNode root, int L, int R) {
        ans = 0;
        dfs(root, L, R);
        return ans;
    }

    public void dfs(TreeNode node, int L, int R) {
        if (node != null) {
            if (L <= node.val && node.val <= R)
                ans += node.val;
            if (L < node.val)
                dfs(node.left, L, R);
            if (node.val < R)
                dfs(node.right, L, R);
        }
    }
}
```


**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是树中的节点数目。

- 空间复杂度：$O(H)$，其中$H$是树的高度。



## 965. 单值二叉树(简单)

如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。

只有给定的树是单值二叉树时，才返回 true；否则返回 false。

```
输入：[1,1,1,1,1,null,1]
输出：true

输入：[2,2,2,5,2]
输出：false
```

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    List<Integer> vals;
    public boolean isUnivalTree(TreeNode root) {
        vals = new ArrayList();
        dfs(root);
        for (int v: vals)
            if (v != vals.get(0))
                return false;
        return true;
    }

    public void dfs(TreeNode node) {
        if (node != null) {
            vals.add(node.val);
            dfs(node.left);
            dfs(node.right);
        }
    }
}
```



## 1022. *从根到叶的二进制数之和(简单)

给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。例如，如果路径为`0 -> 1 -> 1 -> 0 -> 1`，那么它表示二进制数·，也就是13。

对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。

以 10^9 + 7 为模，返回这些数字之和。

<div align=center><img src=LeetCode\1022.png width=40%></div>

```
输入：[1,0,1,0,1,0,1]
输出：22
解释：(100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
```

提示：
```
树中的结点数介于 1 和 1000 之间。
node.val 为 0 或 1 。
```

**思路与算法：**

链接：https://leetcode-cn.com/problems/sum-of-root-to-leaf-binary-numbers/solution/jie-jin-shuang-bai-cong-gen-dao-xie-de-er-jin-zhi-/

- 对于一个二进制数110，我们知道$(110)_2=1×2^2 + 1×2^1+0×2^0 = 6$
- 但是由于我们由顶到底，而顶是最高位，我们不知道有多少层，也就不知道要乘2的几次方。
- 解决方法：每次往下一层，对上一层的结果乘2。如递归构建101这条路径时：
    - 访问1时：$0×2 + 1$
    - 访问0时：$(0×2 + 1)×2 + 0$
    - 访问1时：$((0×2 + 1)×2 + 0)×2 + 1$
    - 把最终结果展开得到$0×2^3 + 1×2^2+0×2^1+ 1 = 5$


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    int ans = 0;    // 存放结果
    int mod = 1000000000 + 7;   // 用作取模

    public int sumRootToLeaf(TreeNode root) {
        helper(root, 0);
        return ans % mod;
    }

    public void helper(TreeNode root, int sum) {
        if (root != null) {      
            sum = sum * 2 + root.val;   
            if (root.left == null && root.right == null) {
                // 到达叶子节点，得到一个和，加到结果上
                ans += sum;     
            } else {    // 没有到达叶子节点，继续递归
                helper(root.left, sum);
                helper(root.right, sum);
            }
        }
    }
}
```



# 递归

## 面试题 04.02. 最小高度树(简单)

给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵**高度最小的二叉搜索树**。

```
示例:
给定有序数组: [-10,-3,0,5,9],

一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：

          0 
         / \ 
       -3   9 
       /   / 
     -10  5 
```

二叉查找树（Binary Search Tree），（又：二叉搜索树，二叉排序树）它或者是一棵空树，或者是具有下列性质的二叉树： 

- 若它的左子树不空，则**左子树上所有结点的值均小于它的根结点的值**； 
- 若它的右子树不空，则**右子树上所有结点的值均大于它的根结点的值**； 
- 它的左、右子树也分别为二叉排序树。

二叉搜索树作为一种经典的数据结构，它既有链表的快速插入与删除操作的特点，又有数组快速查找的优势；所以应用十分广泛，例如在文件系统和数据库系统一般会采用这种数据结构进行高效率的排序与检索操作。


**思路与算法：**

链接：https://leetcode-cn.com/problems/minimum-height-tree-lcci/solution/di-gui-gou-jian-by-zui-weng-jiu-xian/

要求高度最小，保持高度平衡，也就是说**左右子树的节点个数应该尽可能接近**，那么可以

1. **用nums数组的中间值mid作为根节点**，根据mid划分左子数组和右子数组。左子数组构建左子树，右子数组构建右子树。

2. 那么现在的问题就转化为**怎么用左子数组构建左子树/右子数组构建右子树**

3. 以左子数组构建左子树为例；为了保持高度平衡，继续采用1中的划分方法，划分出新的左子数组和右子数组；

4. 当左子数组/右子数组为空时，返回null。

右子数组构建右子树的过程与上述相同。

<div align=center><img src=LeetCode\4.2.png width=60%></div>

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int low, int high) {
        // low > high表示子数组为空
        if (low > high) {
            return null;
        }

        // 以mid作为根节点；
        // 计算时要加low，防止最后出现mid=(4-4)/0=0，实际位置应为(4-4)/0+4
        int mid = (high - low) / 2 + low;
        TreeNode root = new TreeNode(nums[mid]);

        // 左子数组[low, mid -1]构建左子树
        root.left = helper(nums, low, mid - 1);

        // 右子数组[mid + 1, high]构建右子树
        root.right = helper(nums, mid + 1, high);

        return root;
    }
}
```

复杂度分析：

- 数组中的元素都使用1次，时间复杂度为$O(n)$。

- 递归使用栈辅助空间，空间复杂度$O(n)$。



## 104. 二叉树的最大深度(简单)

**题目：**

给定一个二叉树，找出其最大深度。二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

**示例：**
```
给定二叉树 [3,9,20,null,null,15,7]，

    3
   / \
  9  20
    /  \
   15   7
返回它的最大深度 3 。
```

### 递归，DFS(深度优先搜索)

$H(1) = 1 + max(H(2), H(7))$
即$H(1) = 1 + max(H(1).left, H(1).right)$
<div align=center><img src=LeetCode\104.jpg></div>

**代码：**
```java
package solution;

/**
 * leetcode_104_二叉树最大深度
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 *
 * Definition for a binary tree node.
 *
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *
 *     TreeNode(int val) {
 *         this.val = val;
 *     }
 *
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

public class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = maxDepth(node.left);
            int rightHeight = maxDepth(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}
```

**复杂度分析：**

- 时间复杂度：我们每个结点只访问一次，因此时间复杂度为$O(N)$，其中$N$是结点的数量。

- 空间复杂度：在最糟糕的情况下，树是完全不平衡的，例如每个结点只剩下左子结点，递归将会被调用$N$次（树的高度），因此保持调用栈的存储将是$O(N)$。但在最好的情况下（树是完全平衡的），树的高度将是$\log(N)$。因此，在这种情况下的空间复杂度将是$O(\log(N))$。


在递归中，如果层级过深，很可能保存过多的临时变量，导致栈溢出。

事实上，**函数调用的参数是通过栈空间来传递的**，在调用过程中会占用线程的栈资源。而递归调用，只有走到最后的结束点后函数才能依次退出，而未到达最后的结束点之前，占用的栈空间一直没有释放，如果递归调用次数过多，就可能导致占用的栈资源超过线程的最大值，从而导致栈溢出，导致程序的异常退出。

99%的递归转非递归，都可以通过栈来进行实现。 




## 226. 翻转二叉树(简单)

翻转一棵二叉树。

```
示例：

输入：

     4
   /   \
  2     7
 / \   / \
1   3 6   9

输出：

     4
   /   \
  7     2
 / \   / \
9   6 3   1
```

反转一棵空树结果还是一颗空树。

对于一棵根为$r$，左子树为$left$， 右子树为$right$的树来说，它的反转树是一颗根为$r$，左子树为$left$的反转树，右子树为$right$的反转树的树。

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);
        root.left = right;
        root.right = left;
    
        return root;
    }
}
```

复杂度分析：

- 既然树中的**每个节点都只被访问一次**，那么**时间复杂度就是$O(n)$**，其中$n$是树中节点的个数。在反转之前，不论怎样我们至少都得访问每个节点至少一次，因此这个问题无法做地比$O(n)$更好了。

- 本方法使用了递归，在最坏情况下**栈内需要存放$O(h)$个方法调用**，其中$h$是树的高度。由于$h\in O(n)$，可得出**空间复杂度为$O(n)$**。


## 617. 合并二叉树(简单)

给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。

你需要将他们合并为一个新的二叉树。合并的规则是**如果两个节点重叠，那么将他们的值相加作为节点合并后的新值**，否则不为NULL的节点将直接作为新二叉树的节点。

```
示例 1:

输入: 
	Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7                  
输出: 
合并后的树:
	     3
	    / \
	   4   5
	  / \   \ 
	 5   4   7
```

注意: 合并必须从两个树的根节点开始。


**思路与算法：**

链接：https://leetcode-cn.com/problems/merge-two-binary-trees/solution/dong-hua-yan-shi-di-gui-die-dai-617he-bing-er-cha-/

遍历二叉树很简单，用前序遍历就可以了，再依次把访问到的节点值相加，因为题目没有说不能改变树的值和结构，我们**不用再创建新的节点了，直接将树2合并到树1上再返回**就可以了。

递归的条件：

- 终止条件：树1的节点为null，或者树2的节点为null
- 递归函数内：将两个树的节点相加后，再赋给树1的节点。再递归的执行两个树的左节点，递归执行两个树的右节点


<div align=center><img src=LeetCode\617.gif width=80%></div>

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;

        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        
        return t1;
    }
}
```


复杂度分析

- 时间复杂度：$O(N)$，其中$N$是两棵树中节点个数的较小值。

- 空间复杂度：$O(N)$，在最坏情况下，会**递归$N$层**，需要$O(N)$的**栈空间**。


## 700. 二叉搜索树中的搜索(简单)

**题目描述：**

给定二叉搜索树(BST)的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。返回以该节点为根的子树。如果节点不存在，则返回NULL。

**示例：**

```
给定二叉搜索树:

        4
       / \
      2   7
     / \
    1   3

和值: 2

你应该返回如下子树:

      2     
     / \   
    1   3

在上述示例中，如果要找的值是 5，但因为没有节点值为 5，我们应该返回 NULL。
```

<div align=center><img src=LeetCode\700_1.png width=70%></div>


**思路与算法：**

**递归**实现非常简单：

- 如果根节点为空`root == null`或者根节点的值等于搜索值`val == root.val`，返回根节点。
- 如果`val < root.val`，进入根节点的左子树查找`searchBST(root.left, val)`。
- 如果`val > root.val`，进入根节点的右子树查找`searchBST(root.right, val)`。
- 返回根节点。

<div align=center><img src=LeetCode\700_2.png width=70%></div>

```java {.line-numbers highlight=16}
package solution;

/**
 * leetcode_700_二叉搜索树中的搜索
 * @author Chenzf 
 * @date 2020/7/12
 * @version 1.0 递归
 */

public class SearchInBinarySearchTree {
    public TreeNode searchBST(TreeNode node, int value) {
        if (node == null || value == node.val) {
            return node;
        }
        
        return value < node.val ? searchBST(node.left, value) : searchBST(node.right, value);
    }
}
```

**递归复杂度分析：**

- 时间复杂度：$\mathcal{O}(H)$，其中$H$是树高。平均时间复杂度为$\mathcal{O}(\log N)$，最坏时间复杂度为$\mathcal{O}(N)$。

- 空间复杂度：$\mathcal{O}(H)$，**递归栈**的深度为$H$。平均情况下深度为$\mathcal{O}(\log N)$，最坏情况下深度为$\mathcal{O}(N)$。


为了降低空间复杂度，将递归转换为**迭代**：

- 如果根节点不空`root != null`且根节点不是目的节点`val != root.val`：
  - 如果`val < root.val`，进入根节点的左子树查找`root = root.left`。
  - 如果`val > root.val`，进入根节点的右子树查找`root = root.right`。
- 返回root。

<div align=center><img src=LeetCode\700_3.png width=70%></div>

```java {.line-numbers highlight=12-13}
package solution;

/**
 * leetcode_700_二叉搜索树中的搜索
 * @author Chenzf
 * @date 2020/7/12
 * @version 2.0 迭代
 */

public class SearchInBinarySearchTree {
    public TreeNode searchBST(TreeNode node, int value) {
        while (node != null && value != node.val) {
            node = value < node.val ? node.left : node.right;
        }
        
        return node;
    }
}
```

**递归复杂度分析：**

- 时间复杂度：$\mathcal{O}(H)$，其中$H$是树高。平均时间复杂度为$\mathcal{O}(\log N)$，最坏时间复杂度为$\mathcal{O}(N)$。
- 空间复杂度：$\mathcal{O}(1)$，恒定的额外空间。


**递归与迭代的区别：**

- 递归：**重复调用函数自身**实现循环称为递归；
- 迭代：利用变量的**原值推出新值**称为迭代，或者说迭代是**函数内某段代码实现循环**；



## 938. *二叉搜索树的范围和(简单)

给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。

二叉搜索树保证具有唯一的值。

 
```
示例 1：

输入：root = [10,5,15,3,7,null,18], L = 7, R = 15
输出：32

示例 2：

输入：root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
输出：23
```

提示：
```
树中的结点数量最多为 10000 个。
最终的答案保证小于 2^31。
```

**思路与算法：**

链接：https://leetcode-cn.com/problems/range-sum-of-bst/solution/hua-jie-suan-fa-938-er-cha-sou-suo-shu-de-fan-wei-/

递归终止条件：
- 当前节点为 null 时返回 0
- 当前节点 X < L 时则返回右子树之和
- 当前节点 X > R 时则返回左子树之和
- 当前节点 X >= L 且 X <= R 时则返回：当前节点值 + 左子树之和 + 右子树之和

<div align=center><img src=LeetCode\938.jpg></div>


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */


class Solution {
    int ans;
    public int rangeSumBST(TreeNode root, int L, int R) {
        ans = 0;
        dfs(root, L, R);
        return ans;
    }

    public void dfs(TreeNode node, int L, int R) {
        if (node != null) {
            if (L <= node.val && node.val <= R)
                ans += node.val;
            if (L < node.val)
                dfs(node.left, L, R);
            if (node.val < R)
                dfs(node.right, L, R);
        }
    }
}


class Solution {
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }
}
```


**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是树中的节点数目。

- 空间复杂度：$O(H)$，其中$H$是树的高度。



# 深度优先遍历


## 144. 二叉树的前序遍历(中等)

给定一个二叉树，返回它的前序遍历

```
输入: [1,null,2,3]  
   1
    \
     2
    /
   3 

输出: [1,2,3]
```

<div align=center><img src=LeetCode\二叉树遍历.png width=30%></div>

**从子树的角度来观察：**

- 如果按照`根节点 -> 左孩子 -> 右孩子`的方式遍历，即**先序遍历**：`1 2 4 5 3 6 7`；
- 如果按照`左孩子 -> 根节点 -> 右孩子`的方式遍历，即**中序遍历**：`4 2 5 1 6 3 7`；
- 如果按照`左孩子 -> 右孩子 -> 根节点`的方式遍历，即**后序遍历**：`4 5 2 6 7 3 1`；
- **层次遍历**就是按照每一层从左向右的方式进行遍历：`1 2 3 4 5 6 7`。

### 递归

```java
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_144_二叉树的前序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0 递归法
 */

public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    public void preorderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.val);
            // 把node.left看成以其为根的子树
            if (node.left != null) {
                preorderTraversal(node.left, result);
            }
            // 把node.right看成以其为根的子树
            if (node.right != null) {
                preorderTraversal(node.right, result);
            }
        }
    }
}
```

### 迭代——栈

使用**栈**来进行迭代，过程如下：
- 初始化栈，并将根节点入栈；
- 当栈不为空时：
  - 弹出栈顶元素node，并将值添加到结果中；
  - 如果node的右子树非空，将右子树入栈；
  - 如果node的左子树非空，将左子树入栈；

由于栈是“先进后出”的顺序，所以入栈时**先将右子树入栈**，这样使得前序遍历结果为 “根->左->右”的顺序。

<div align=center><img src=LeetCode\144.gif></div>

```java
package solution;

import java.util.LinkedList;
import java.util.List;

/**
 * leetcode_144_二叉树的前序遍历
 * @author Chenzf
 * @date 2020/7/26
 * @version 2.0 迭代法
 */

public class PreorderTraversal_v2 {
    public List<Integer> preorderTraversal(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();

        if (root == null) {
            return output;
        }
        
        // 从根节点开始，每次迭代弹出当前栈顶元素，并将其孩子节点压入栈中
        stack.add(root);
        while (! stack.isEmpty()) {
            // 每次迭代弹出当前栈顶元素
            TreeNode node = stack.pollLast();
            output.add(node.val);
            
            // 先压右孩子
            if (node.right != null) {
                stack.add(node.right);
            }
            
            // 再压左孩子
            if (node.left != null) {
                stack.add(node.left);
            }
        }
        
        return output;
    }
}
```

**算法复杂度**

- 时间复杂度：**访问每个节点恰好一次**，时间复杂度为$O(N)$，其中$N$是节点的个数，也就是树的大小。
- 空间复杂度：取决于树的结构，最坏情况存储整棵树，因此空间复杂度是$O(N)$。


## 617. 合并二叉树(简单)

给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。

你需要将他们合并为一个新的二叉树。合并的规则是**如果两个节点重叠，那么将他们的值相加作为节点合并后的新值**，否则不为NULL的节点将直接作为新二叉树的节点。

```
示例 1:

输入: 
	Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7                  
输出: 
合并后的树:
	     3
	    / \
	   4   5
	  / \   \ 
	 5   4   7
```

注意: 合并必须从两个树的根节点开始。


**思路与算法：**

链接：https://leetcode-cn.com/problems/merge-two-binary-trees/solution/dong-hua-yan-shi-di-gui-die-dai-617he-bing-er-cha-/

遍历二叉树很简单，用前序遍历就可以了，再依次把访问到的节点值相加，因为题目没有说不能改变树的值和结构，我们**不用再创建新的节点了，直接将树2合并到树1上再返回**就可以了。

递归的条件：

- 终止条件：树1的节点为null，或者树2的节点为null
- 递归函数内：将两个树的节点相加后，再赋给树1的节点。再递归的执行两个树的左节点，递归执行两个树的右节点


<div align=center><img src=LeetCode\617.gif width=80%></div>

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;

        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        
        return t1;
    }
}
```


复杂度分析

- 时间复杂度：$O(N)$，其中$N$是两棵树中节点个数的较小值。

- 空间复杂度：$O(N)$，在最坏情况下，会**递归$N$层**，需要$O(N)$的**栈空间**。



## 700. 二叉搜索树中的搜索(简单)

**题目描述：**

给定二叉搜索树(BST)的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。返回以该节点为根的子树。如果节点不存在，则返回NULL。

**示例：**

```
给定二叉搜索树:

        4
       / \
      2   7
     / \
    1   3

和值: 2

你应该返回如下子树:

      2     
     / \   
    1   3

在上述示例中，如果要找的值是 5，但因为没有节点值为 5，我们应该返回 NULL。
```

<div align=center><img src=LeetCode\700_1.png width=70%></div>


**思路与算法：**

**递归**实现非常简单：

- 如果根节点为空`root == null`或者根节点的值等于搜索值`val == root.val`，返回根节点。
- 如果`val < root.val`，进入根节点的左子树查找`searchBST(root.left, val)`。
- 如果`val > root.val`，进入根节点的右子树查找`searchBST(root.right, val)`。
- 返回根节点。

<div align=center><img src=LeetCode\700_2.png width=70%></div>

```java {.line-numbers highlight=16}
package solution;

/**
 * leetcode_700_二叉搜索树中的搜索
 * @author Chenzf 
 * @date 2020/7/12
 * @version 1.0 递归
 */

public class SearchInBinarySearchTree {
    public TreeNode searchBST(TreeNode node, int value) {
        if (node == null || value == node.val) {
            return node;
        }
        
        return value < node.val ? searchBST(node.left, value) : searchBST(node.right, value);
    }
}
```

**递归复杂度分析：**

- 时间复杂度：$\mathcal{O}(H)$，其中$H$是树高。平均时间复杂度为$\mathcal{O}(\log N)$，最坏时间复杂度为$\mathcal{O}(N)$。

- 空间复杂度：$\mathcal{O}(H)$，**递归栈**的深度为$H$。平均情况下深度为$\mathcal{O}(\log N)$，最坏情况下深度为$\mathcal{O}(N)$。


为了降低空间复杂度，将递归转换为**迭代**：

- 如果根节点不空`root != null`且根节点不是目的节点`val != root.val`：
  - 如果`val < root.val`，进入根节点的左子树查找`root = root.left`。
  - 如果`val > root.val`，进入根节点的右子树查找`root = root.right`。
- 返回root。

<div align=center><img src=LeetCode\700_3.png width=70%></div>

```java {.line-numbers highlight=12-13}
package solution;

/**
 * leetcode_700_二叉搜索树中的搜索
 * @author Chenzf
 * @date 2020/7/12
 * @version 2.0 迭代
 */

public class SearchInBinarySearchTree {
    public TreeNode searchBST(TreeNode node, int value) {
        while (node != null && value != node.val) {
            node = value < node.val ? node.left : node.right;
        }
        
        return node;
    }
}
```

**递归复杂度分析：**

- 时间复杂度：$\mathcal{O}(H)$，其中$H$是树高。平均时间复杂度为$\mathcal{O}(\log N)$，最坏时间复杂度为$\mathcal{O}(N)$。
- 空间复杂度：$\mathcal{O}(1)$，恒定的额外空间。


**递归与迭代的区别：**

- 递归：**重复调用函数自身**实现循环称为递归；
- 迭代：利用变量的**原值推出新值**称为迭代，或者说迭代是**函数内某段代码实现循环**；




## 938. *二叉搜索树的范围和(简单)

给定二叉搜索树的根结点 root，返回 L 和 R（含）之间的所有结点的值的和。

二叉搜索树保证具有唯一的值。

 
```
示例 1：

输入：root = [10,5,15,3,7,null,18], L = 7, R = 15
输出：32

示例 2：

输入：root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
输出：23
```

提示：
```
树中的结点数量最多为 10000 个。
最终的答案保证小于 2^31。
```

**思路与算法：**

链接：https://leetcode-cn.com/problems/range-sum-of-bst/solution/hua-jie-suan-fa-938-er-cha-sou-suo-shu-de-fan-wei-/

递归终止条件：
- 当前节点为 null 时返回 0
- 当前节点 X < L 时则返回右子树之和
- 当前节点 X > R 时则返回左子树之和
- 当前节点 X >= L 且 X <= R 时则返回：当前节点值 + 左子树之和 + 右子树之和

<div align=center><img src=LeetCode\938.jpg></div>


```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */


class Solution {
    int ans;
    public int rangeSumBST(TreeNode root, int L, int R) {
        ans = 0;
        dfs(root, L, R);
        return ans;
    }

    public void dfs(TreeNode node, int L, int R) {
        if (node != null) {
            if (L <= node.val && node.val <= R)
                ans += node.val;
            if (L < node.val)
                dfs(node.left, L, R);
            if (node.val < R)
                dfs(node.right, L, R);
        }
    }
}


class Solution {
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是树中的节点数目。

- 空间复杂度：$O(H)$，其中$H$是树的高度。


# 广度优先搜索

## 102. 二叉树的层序遍历(中等)

**题目描述：**

给一个二叉树，请返回其按层序遍历得到的节点值。即逐层地，从左到右访问所有节点。

**示例：**

```
二叉树：[3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7

返回其层次遍历结果：

[[3],[9,20],[15,7]]
```

<div align=center><img src=LeetCode\102.png width=80%></div>


### 队列

链接：https://leetcode-cn.com/problems/binary-tree-level-order-traversal/solution/die-dai-di-gui-duo-tu-yan-shi-102er-cha-shu-de-cen/

BFS使用**队列**，把每个还没有搜索到的点依次放入队列，然后再弹出队列的头部元素当做当前遍历点。


广度优先需要用队列作为辅助结构，我们先将根节点放到队列中，然后不断遍历队列。

<div align=center><img src=LeetCode\102.jpg width=60%></div>

**首先拿出根节点，如果左子树/右子树不为空，就将他们放入队列中**。第一遍处理完后，根节点已经从队列中拿走了，而根节点的两个孩子已放入队列中了，现在队列中就有两个节点 2 和 5。

<div align=center><img src=LeetCode\102_1.jpg width=60%></div>

第二次处理，会将 2 和 5 这两个节点从队列中拿走，然后再将 2 和 5 的子节点放入队列中，现在队列中就有三个节点 3，4，6。

<div align=center><img src=LeetCode\102_2.jpg width=60%></div>

```java
import java.util.*;	
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) {
            return new ArrayList<List<Integer>>();
        }
		
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        // 将根节点放入队列中，然后不断遍历队列
        queue.add(root);
        while(queue.size() > 0) {
            // 获取当前队列的长度，这个长度相当于 当前这一层的节点个数
            int size = queue.size();
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            // 将队列中的元素都拿出来(也就是获取这一层的节点)，放到临时list中
            // 如果节点的左/右子树不为空，也放入队列中
            for(int i = 0; i < size; i++) {
                TreeNode t = queue.remove();
                tmp.add(t.val);
                if(t.left != null) {
                    queue.add(t.left);
                }
                if(t.right != null) {
                    queue.add(t.right);
                }
            }
            // 将临时list加入最终返回结果中
            res.add(tmp);
        }
        return res;
    }
}
```

- 时间复杂度：$O(n)$
- 空间复杂度：$O(n)$


BFS总共有两个模板：

1. 如果**不需要确定当前遍历到了哪一层**，BFS模板如下
```
while queue 不空：
    cur = queue.pop()
    for 节点 in cur的所有相邻节点：
        if 该节点有效且未访问过：
            queue.push(该节点)
```

2. 如果要确定当前遍历到了哪一层，BFS模板如下
这里增加了level表示当前遍历到二叉树中的哪一层了，也可以理解为在一个图中，现在已经走了多少步了。size表示在当前遍历层有多少个元素，也就是队列中的元素数，我们把这些元素一次性遍历完，即把当前层的所有元素都向外走了一步。

```
level = 0
while queue 不空：
    size = queue.size()
    while (size --) {
        cur = queue.pop()
        for 节点 in cur的所有相邻节点：
            if 该节点有效且未被访问过：
                queue.push(该节点)
    }
    level ++;
```

本题要求二叉树的层次遍历，所以同一层的节点应该放在一起，故使用模板二。

使用队列保存每层的所有节点，每次把队列里的原先所有节点进行出队列操作，再把每个元素的非空左右子节点进入队列。因此即可得到每层的遍历。


**代码实现：**

```java {.line-numbers highlight=37-40}
package solution;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * leetcode_102_二叉树的层序遍历
 */

public class BinaryTreeLevelOrder {
    public List<List<Integer>> levelOrder(TreeNode node) {
        // 最后输出结果
        List<List<Integer>> result = new ArrayList<>();
        // 存放每层的结点
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(node);

        while (! queue.isEmpty()) {
            int size = queue.size();

            // 每一层的遍历结果
            List<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                // queue.peek()返回队列的头元素，不删除
                TreeNode currentNode = queue.peek();
                /*
                Queue中remove()和poll()都是用来从队列头部删除一个元素。
                在队列元素为空的情况下，remove() 方法会抛出NoSuchElementException异常，
                poll() 方法只会返回null
                 */
                queue.poll();

                // 防止当结点的左结点为null，右结点不为null时，出现queue=[null, XX]
                if (currentNode == null) {
                    continue;
                }

                level.add(currentNode.val);

                // 利用完当前结点后，将其左、右结点加入队列中
                queue.offer(currentNode.left);
                queue.offer(currentNode.right);
            }

            if (! level.isEmpty()) {
                // 将每一层的结果加入最终结果列表中
                result.add(level);
            }
        }

        return result;
    }
}
```


# 位运算


## 136. 只出现一次的数字(简单)

给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

说明：

你的算法应该**具有线性时间复杂度**。 你可以**不使用额外空间**来实现吗？

```
示例 1:

输入: [2,2,1]
输出: 1

示例 2:

输入: [4,1,2,1,2]
输出: 4
```

**思路与算法：**

如果没有时间复杂度和空间复杂度的限制，这道题有很多种解法，可能的解法有如下几种。

- 使用**集合**存储数字。遍历数组中的每个数字，如果集合中没有该数字，则将该数字加入集合，**如果集合中已经有该数字，则将该数字从集合中删除**，最后剩下的数字就是只出现一次的数字。

- 使用**哈希表**存储**每个数字和该数字出现的次数**。遍历数组即可得到每个数字出现的次数，并更新哈希表，最后遍历哈希表，得到只出现一次的数字。



上述解法都**需要额外使用$O(n)$的空间**，其中$n$是数组长度。如果要求使用线性时间复杂度和常数空间复杂度，上述三种解法显然都不满足要求。那么，如何才能做到线性时间复杂度和常数空间复杂度呢？

答案是使用**位运算**。对于这道题，可使用**异或**运算$\oplus$。

异或运算有以下三个性质。

- **任何数和0做异或运算，结果仍然是原来的数**，即$a \oplus 0=a$。
- 任何数和其自身做异或运算，结果是0，即 $a \oplus a=0$。
- 异或运算满足交换律和结合律，即$a \oplus b \oplus a=b \oplus a \oplus a=b \oplus (a \oplus a)=b \oplus0=b$。


假设数组中有$2m+1$个数，其中有$m$个数各出现两次，一个数出现一次。令$a_{1}、a_{2}、\ldots…、a_{m}$为出现两次的$m$个数，$a_{m+1}$为出现一次的数。根据性质 3，数组中的全部元素的异或运算结果总是可以写成如下形式：

$(a_{1} \oplus a_{1}) \oplus (a_{2} \oplus a_{2}) \oplus \cdots \oplus (a_{m} \oplus a_{m}) \oplus a_{m+1}$
 

根据性质 2 和性质 1，上式可化简和计算得到如下结果：

$0 \oplus 0 \oplus \cdots \oplus 0 \oplus a_{m+1}=a_{m+1}$

因此，**数组中的全部元素的异或运算结果即为数组中只出现一次的数字**。


```java
class Solution {
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$是数组长度。只需要对数组遍历一次。

- 空间复杂度：$O(1)$。



## 190. 颠倒二进制位(简单)

颠倒给定的 32 位无符号整数的二进制位。

```
示例 1：

输入: 00000010100101000001111010011100
输出: 00111001011110000010100101000000
解释: 输入的二进制串 00000010100101000001111010011100 表示无符号整数 43261596，
     因此返回 964176192，其二进制表示形式为 00111001011110000010100101000000。

示例 2：

输入：11111111111111111111111111111101
输出：10111111111111111111111111111111
解释：输入的二进制串 11111111111111111111111111111101 表示无符号整数 4294967293，
     因此返回 3221225471 其二进制表示形式为 10111111111111111111111111111111 。
```

提示：

- 在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
- 在Java中，编译器使用二进制补码记法来表示有符号整数。因此，在上面的 示例 2 中，输入表示有符号整数 -3，输出表示有符号整数 -1073741825。


**思路与算法：取模求和**

链接：https://leetcode-cn.com/problems/reverse-bits/solution/zhi-qi-ran-zhi-qi-suo-yi-ran-wei-yun-suan-jie-fa-x/

与反转十进制整数使用取模除十累加的方法类似，

- 十进制：ans = ans * 10 + n % 10; n = n / 10;
- 二进制：ans = ans * 2 + n % 2; n = n / 2;

但是，仅仅使用这种写法，会有一些问题，比如都要考虑**是否整型溢出**，**Java的整数溢出后的二进制数会表示成负数（补码形式）**，**Java中负数除以2会向零取整: -3 / 2 = -1 而 -3 >> 1 = -2**

然后还要考虑**前导零**，因为十进制是不考虑前面是否还有零的，比如100反转后就是1，不用写成001，而二进制要考虑前导零的问题。

所以综上所述，要**使用位运算来避免溢出问题**，同时**循环32次**。

因为一共只有32位，所以**时间复杂度和空间复杂度都是O(1)**。


```java
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res = (res << 1) + (n & 1);
            n >>= 1;
        }
        return res;
    }
}
```





## 191. 位1的个数(简单)

编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。

 
```
示例 1：

输入：00000000000000000000000000001011
输出：3
解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。

示例 2：

输入：00000000000000000000000010000000
输出：1
解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。

示例 3：

输入：11111111111111111111111111111101
输出：31
解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
```


**思路与算法：**

遍历数字的 32 位。如果某一位是 1 ，将计数器加一。

我们使用 **位掩码** 来检查数字的第$i^{th}$ 位。

一开始，掩码$m=1$因为1的二进制表示是`0000 0000 0000 0000 0000 0000 0000 0001`，显然，任何数字跟掩码 1 进行**逻辑与运算**，都可以让我们**获得这个数字的最低位**。

**检查下一位时，我们将掩码左移一位**：`0000 0000 0000 0000 0000 0000 0000 0010`，并重复此过程。


```java
class Solution {
    public int hammingWeight(int n) {
        int bits = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                bits++;
            }
            mask <<= 1;
        }
        return bits;
    }
}
```


## 268. 缺失数字(简单)

给定一个包含 `0, 1, 2, ..., n` 中 **n 个数**的序列，找出 `0 .. n` 中没有出现在序列中的那个数。

```
示例 1:

输入: [3,0,1]
输出: 2

示例 2:

输入: [9,6,4,2,3,5,7,0,1]
输出: 8
```


**HashMap：**

```java
class Solution {
    public int missingNumber(int[] nums) {
        Set<Integer> numSet = new HashSet<Integer>();
        for (int num : nums) {
            numSet.add(num);
        }

        int expectedNumCount = nums.length + 1;
        for (int number = 0; number < expectedNumCount; number++) {
            if (!numSet.contains(number)) {
                return number;
            }
        }
        return -1;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$。集合的插入操作的时间复杂度都是$O(1)$，一共插入了$n$个数，时间复杂度为$O(n)$。集合的查询操作的时间复杂度同样是$O(1)$，最多查询$n+1$次，时间复杂度为 $O(n)$。因此总的时间复杂度为$O(n)$。
- 空间复杂度：$O(n)$。集合中会存储$n$个数，因此空间复杂度为$O(n)$。


**位运算：**

| 下标 | 0 | 1 | 2 | 3 |
|:----:|:-:|:-:|:-:|:-:|
| 数字 | 0 | 1 | 3 | 4 |

将结果的初始值设为$n$，再对数组中的每一个数以及它的下标进行一个异或运算，即：

$\begin{aligned} \mathrm{missing} &= 4 \wedge (0 \wedge 0) \wedge (1 \wedge 1) \wedge (2 \wedge 3) \wedge (3 \wedge 4) \\ &= (4 \wedge 4) \wedge (0 \wedge 0) \wedge (1 \wedge 1) \wedge (3 \wedge 3) \wedge 2 \\ &= 0 \wedge 0 \wedge 0 \wedge 0 \wedge 2 \\ &= 2 \end{aligned}$

```java
class Solution {
    public int missingNumber(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i ^ nums[i];
        }
        return missing;
    }
}
```

复杂度分析

- 时间复杂度：$O(n)$。这里假设异或运算的时间复杂度是常数的，总共会进行$O(n)$次异或运算，因此总的时间复杂度为$O(n)$。
- 空间复杂度：$O(1)$。算法中只用到了$O(1)$的额外空间，用来存储答案。



## 371. 两整数之和(简单)
 
不使用运算符 `+` 和 `-` ​​​​​​​，计算两整数 ​​​​​​​a 、b ​​​​​​​之和。

```
示例 1:

输入: a = 1, b = 2
输出: 3

示例 2:

输入: a = -2, b = 3
输出: 1
```


**思路与算法：**

链接：https://leetcode-cn.com/problems/sum-of-two-integers/solution/0msfu-xian-ji-suan-ji-zui-ji-ben-de-jia-fa-cao-zuo/




十进制的加法，比如15+7，最低位5+7得到12，**对10取模得到2，进位为1，再高位相加1+0再加上进位1就得到高位结果2，组合起来就是22**。

这里面涉及到了两个数字，一个是相加得到的低位，也就是5+7得到的结果2，第二个是进位1。在二进制的计算中就是要通过位操作来得到结果的低位和进位，对于不同的情况，用表格来表示一下，两个数字分别为a和b

| a | b | 低位 | 进位 |
|:-:|:-:|:----:|:----:|
| 0 | 0 |   0  |   0  |
| 1 | 0 |   1  |   0  |
| 0 | 1 |   1  |   0  |
| 1 | 1 |   0  |   1  |

从上面的表格就可以发现，`低位 = a ^ b`，`进位 = a & b`。这样的计算可能要持续多次，回想一下在十进制的计算中，如果进位一直大于0，就得往后面进行计算，在这里也是一样，只要进位不是0，我们就得一直重复计算低位和进位的操作（需要在下一次计算之前要把进位向左移动一位，这样进位才能和更高位进行运算）。这个时候的a和b就是刚才计算的低位和进位。

链接：https://leetcode-cn.com/problems/sum-of-two-integers/solution/li-yong-wei-cao-zuo-shi-xian-liang-shu-qiu-he-by-p/


首先看十进制是如何做的： 5+7=12，三步走

- 第一步：**相加各位的值，不算进位**，得到2。
- 第二步：**计算进位值**，得到10。如果这一步的进位值为0，那么第一步得到的值就是最终结果。
- 第三步：重复上述两步，只是相加的值变成上述两步的得到的结果2和10，得到12。





用三步走的方式计算二进制值相加： `5---101`，`7---111`

- 第一步：**相加各位的值，不算进位**，得到010，**二进制每位相加就相当于各位做异或操作**，`101^111`。
- 第二步：**计算进位值**，得到1010，**相当于各位进行与操作**得到101，再向左移一位得到1010，`(101&111)<<1`。
- 第三步重复上述两步，**各位相加** `010^1010=1000`，进位值为`100=(010 & 1010)<<1`。
- 继续重复上述两步：`1000^100 = 1100`，进位值为0，跳出循环，1100为最终结果。
- 结束条件：进位为0，即a为最终的求和结果。


```java
class Solution {
    public int getSum(int a, int b) {
        while(b != 0){
            // 相加各位的值，不算进位
            // 二进制每位相加就相当于各位做异或
            int temp = a ^ b;
            // 计算进位值
            b = (a & b) << 1;

            a = temp;
        }
        return a;
    }
}
```







# 数学


## 7. *整数反转

**题目：**
给出一个32位的有符号整数，你需要将这个整数中每位上的数字进行反转。

```
输入: 123
输出: 321

输入: -123
输出: -321

输入: 120
输出: 21
```
注意:
假设我们的环境只能存储得下32位的有符号整数，则其数值范围为$[−2^{31},  2^{31} − 1]$（$-2,147,483,648 - 2,147,483,647$）。请根据这个假设，**如果反转后整数溢出那么就返回0**。

**思路与算法：**

要**在没有辅助堆栈/数组的帮助下“弹出”和“推入”数字**，我们可以使用数学方法。
```
//pop operation:
pop = x % 10;
x /= 10;

//push operation:
temp = rev * 10 + pop;
rev = temp;
```

本题如果不考虑**溢出问题**，是非常简单的。解决溢出问题有两个思路：
- 第一个思路是通过**字符串转换**加`try catch`的方式来解决；
- 第二个思路就是通过数学计算来解决。
  
由于字符串转换的效率较低且使用较多库函数，所以解题方案不考虑该方法，而是通过数学计算来解决。

通过循环将数字`x`的每一位拆开，在计算新值时每一步都判断是否溢出。

溢出条件有两个，一个是大于整数最大值`MAX_VALUE`，另一个是小于整数最小值`MIN_VALUE`，设当前计算结果为`ans`，下一位为`pop`。

从`ans * 10 + pop > MAX_VALUE`这个溢出条件来看：
- 当出现`ans > MAX_VALUE / 10`且**还有`pop`需要添加**时，则一定溢出；
- 当出现`ans == MAX_VALUE / 10`且`pop > 7`时，则一定溢出，**7是$2^{31} - 1$的个位数**。
  
从`ans * 10 + pop < MIN_VALUE`这个溢出条件来看：
- 当出现`ans < MIN_VALUE / 10`且**还有pop需要添加**时，则一定溢出；
- 当出现`ans == MIN_VALUE / 10`且`pop < -8`时，则一定溢出，**8是$-2^{31}$的个位数**。

**代码：**

```java
class ReverseInteger {
    public int reverse(int x) {
        int ans = 0;
        while(x != 0){
            int pop = x % 10;
            if(ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7))
                return 0;
            if(ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8))
                return 0;
            ans = ans * 10 + pop;
            x /= 10;
        }

        return ans;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(\log(x))$，$x$中大约有$\log_{10}(x)$位数字。

- 空间复杂度：$O(1)$。




## 172. 阶乘后的零(简单)

给定一个整数 n，返回 n! 结果尾数中零的数量。

```
示例 1:

输入: 3
输出: 0
解释: 3! = 6, 尾数中没有零。

示例 2:

输入: 5
输出: 1
解释: 5! = 120, 尾数中有 1 个零.
```

**计算阶乘**

解决这个问题的最简单的办法就是计算$n!$，然后计算它的末尾数 0 个数。阶乘是通过将所有在1和n之间的数字相乘计算的。

如果一个数字末尾有零，那么它可以被10整除。**除以10将删除该零，并将所有其他数字右移一位**。因此，我们可以**通过反复检查数字是否可以被10整除来计算末尾0的个数**。

在Java中，我们需要**使用BigInteger，防止在计算阶乘的过程中溢出**。

```java
import java.math.BigInteger;

class Solution {
    public int trailingZeroes(int n) {
        // Calculate n!
        BigInteger nFactorial = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            nFactorial = nFactorial.multiply(BigInteger.valueOf(i));
        }
                        
        // Count how many 0's are on the end.
        int zeroCount = 0;
        
        while (nFactorial.mod(BigInteger.TEN).equals(BigInteger.ZERO)) {
            nFactorial = nFactorial.divide(BigInteger.TEN);
            zeroCount++;
        }
        
        return zeroCount;
    }
}
```

**复杂度分析**

- 时间复杂度：低于$O(n ^ 2)$
- 空间复杂度：$O(\log n!) = O(n \log n)$，为了存储$n!$，我们需要$O(\log n!)$位，而它等于$O(n \log n)$。


**计算因子5**

链接：https://leetcode-cn.com/problems/factorial-trailing-zeroes/solution/xiang-xi-tong-su-de-si-lu-fen-xi-by-windliang-3/


肯定不能依赖于把阶乘算出来再去判断有多少个零了，因为**阶乘很容易就溢出**了。

首先末尾有多少个 0 ，只需要给当前数乘以一个 10 就可以加一个 0。

再具体对于$5!$，也就是$5 * 4 * 3 * 2 * 1 = 120$，我们发现结果会有一个 0，原因就是 **2 和 5 相乘构成了一个 10**。而对于 10 的话，其实也只有 2 * 5 可以构成，所以我们**只需要找有多少对 2/5**。

我们把每个乘数再稍微分解下，看一个例子。

$11! = 11 * 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1 = 11 * (2 * 5) * 9 * (4 * 2) * 7 * (3 * 2) * (1 * 5) * (2 * 2) * 3 * (1 * 2) * 1$

- 对于含有 2 的因子的话是 1 * 2, 2 * 2, 3 * 2, 4 * 2 ...

- 对于含有 5 的因子的话是 1 * 5, 2 * 5...

**含有 2 的因子每两个出现一次，含有 5 的因子每 5 个出现一次，所有 2 出现的个数远远多于 5，换言之找到一个 5，一定能找到一个 2 与之配对。所以我们只需要找有多少个 5**。

直接的，我们只需要判断每个累乘的数有多少个 5 的因子即可。




对于一个数的阶乘，**5的因子一定是每隔5个数出现一次**，也就是下边的样子。

$n! = 1 * 2 * 3 * 4 * (1 * 5) * ... * (2 * 5) * ... * (3 * 5) *... * n$

**因为每隔5个数出现一个5，所以计算出现了多少个5，我们只需要用$n/5$就可以算出来**。

但还没有结束，继续分析。

$... * (1 * 5) * ... * (1 * 5 * 5) * ... * (2 * 5 * 5) * ... * (3 * 5 * 5) * ... * n$

**每隔 25 个数字，出现的是两个 5**，所以除了每隔 5 个数算作一个 5，每隔 25 个数，还需要多算一个 5。

也就是我们需要**再加上$n / 25$个 5**。

同理我们还会发现**每隔 5 * 5 * 5 = 125 个数字，会出现 3 个 5**，所以我们还需要**再加上$n / 125$**。

综上，规律就是**每隔 5 个数，出现一个 5，每隔 25 个数，出现 2 个 5，每隔 125 个数，出现 3 个 5... 以此类推**。

**最终 5 的个数就是**$n / 5 + n / 25 + n / 125 ...$

**写程序的话，如果直接按照上边的式子计算，分母可能会造成溢出**。所以**算$n / 25$的时候，我们先把 n 更新**，$n = n / 5$，然后再计算 $n / 5$ 即可。后边的同理。

```java
class Solution {
    public int trailingZeroes(int n) {
        int zeroCount = 0;
        long currentMultiple = 5;
        while (n > 0) {
            n /= 5;
            zeroCount += n;
        }
        return zeroCount;
    }
}
```


## 204. 计数质数(简单)

统计所有小于非负整数 n 的质数的数量。

```
示例:

输入: 10
输出: 4
解释: 小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
```

**思路与算法：**

链接：https://leetcode-cn.com/problems/count-primes/solution/ru-he-gao-xiao-pan-ding-shai-xuan-su-shu-by-labula/

素数的定义很简单，**如果一个数如果只能被 1 和它本身整除，那么这个数就是素数**。

```java
int countPrimes(int n) {
    int count = 0;
    for (int i = 2; i < n; i++)
        if (isPrim(i)) count++;
    return count;
}

// 判断整数 n 是否是素数
boolean isPrime(int n) {
    for (int i = 2; i < n; i++)
        if (n % i == 0)
            // 有其他整除因子
            return false;
    return true;
}
```

这样写的话时间复杂度$O(n^2)$。

i 不需要遍历到 n，而只需要到 `sqrt(n)` 即可：

```
12 = 2 × 6
12 = 3 × 4
12 = sqrt(12) × sqrt(12)
12 = 4 × 3
12 = 6 × 2
```

可以看到，**后两个乘积就是前面两个反过来，反转临界点就在 sqrt(n)**。

换句话说，如果在`[2, sqrt(n)]`这个区间之内没有发现可整除因子，就可以直接断定 n 是素数了，因为在区间 `[sqrt(n),n]` 也一定不会发现可整除因子。

首先从 2 开始，我们知道 2 是一个素数，那么 $2 × 2 = 4, 3 × 2 = 6, 4 × 2 = 8...$ 都不可能是素数了。

然后我们发现 3 也是素数，那么 $3 × 2 = 6, 3 × 3 = 9, 3 × 4 = 12...$ 也都不可能是素数了。

```java
int countPrimes(int n) {
    boolean[] isPrim = new boolean[n];
    // 将数组都初始化为 true
    Arrays.fill(isPrim, true);

    for (int i = 2; i < n; i++) 
        if (isPrim[i]) 
            // i 的倍数不可能是素数了
            for (int j = 2 * i; j < n; j += i) 
                    isPrim[j] = false;
    
    int count = 0;
    for (int i = 2; i < n; i++)
        if (isPrim[i]) {
            count++;
        }
    
    return count;
}
```

<div align=center><img src=LeetCode\204_1.gif></div>


回想刚才判断一个数是否是素数的 isPrime 函数，**由于因子的对称性，其中的 for 循环只需要遍历 `[2,sqrt(n)]` 就够了**。这里也是类似的，我们外层的 for 循环也只需要遍历到 `sqrt(n)`：

```java
for (int i = 2; i * i < n; i++) 
    if (isPrim[i]) 
        ...
```

内层的 for 循环也可以优化，比如 n = 25，i = 4 时算法会标记 4 × 2 = 8，4 × 3 = 12 等等数字，但是这两个数字已经被 i = 2 和 i = 3 的 2 × 4 和 3 × 4 标记了。

我们可以稍微优化一下，让 j 从 i 的平方开始遍历，而不是从 2 * i 开始：

```java
for (int j = i * i; j < n; j += i) 
    isPrim[j] = false;
```

```java
class Solution {
    int countPrimes(int n) {
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        
        for (int i = 2; i * i < n; i++) 
            if (isPrim[i]) 
                for (int j = i * i; j < n; j += i) 
                    isPrim[j] = false;
        
        int count = 0;
        for (int i = 2; i < n; i++)
            if (isPrim[i]) count++;
        
        return count;
    }
}
```





# 二分查找

## 69. x 的平方根(简单)

实现 int sqrt(int x) 函数。

计算并返回 x 的平方根，其中 x 是非负整数。

由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。

```
示例 1:

输入: 4
输出: 2

示例 2:

输入: 8
输出: 2
说明: 8 的平方根是 2.82842..., 
     由于返回类型是整数，小数部分将被舍去。
```

**思路与算法：**

由于$x$平方根的整数部分$\textit{ans}$是满足$k^2 \leq x$的最大$k$值，因此我们可以对$k$进行二分查找，从而得到答案。

二分查找的下界为$0$，上界可以粗略地设定为$x$。在二分查找的每一步中，我们只需要比较中间元素$\textit{mid}$的平方与$x$的大小关系，并通过比较的结果调整上下界的范围。由于我们所有的运算都是整数运算，不会存在误差，因此在得到最终的答案$\textit{ans}$后，也就不需要再去尝试$\textit{ans} + 1$了。


```java
class Solution {
    public int mySqrt(int x) {
        int left = 0, right = x, ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long)mid * mid <= x) {
                ans = mid;
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return ans;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(\log x)$，即为二分查找需要的次数。

- 空间复杂度：$O(1)$。





# 回溯

回溯法(back tracking)是一种选优搜索法，又称为试探法，按选优条件向前搜索，以达到目标。但**当探索到某一步时，发现原先选择并不优或达不到目标，就退回一步重新选择**。这种**走不通就退回再走**的技术为回溯法，而满足回溯条件的某个状态的点称为“回溯点”。


## 78. 子集(简单)

给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。

说明：解集不能包含重复的子集。

```
示例:

输入: nums = [1,2,3]
输出:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
```

**思路与算法：**

观察**全排列/组合/子集问题**，它们比较相似，且可以使用一些通用策略解决。

首先，它们的解空间非常大：

- 全排列：$N!$。

- 组合：$N!$。

- 子集：$2^N$，每个元素都可能存在或不存在。

在它们的指数级解法中，要确保生成的结果 完整 且 无冗余，有三种常用的方法：

- 递归

- 回溯

- 基于二进制位掩码和对应位掩码之间的映射字典生成排列/组合/子集

相比前两种方法，第三种方法将每种情况都简化为二进制数，易于实现和验证。此外，第三种方法具有最优的时间复杂度，可以生成按照字典顺序的输出结果。


**方法一：递归**

逐个枚举，空集的幂集只有空集，**每增加一个元素，让之前幂集中的每个集合，追加这个元素**，就是新增的子集。

<div align=center><img src=LeetCode\78_1.png></div>

