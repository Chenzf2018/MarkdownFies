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
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                // 此时i=j
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

- 首先对两个数组进行排序，然后使用两个指针遍历两个数组。

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










## 1512. 好数对的数目(简单)

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