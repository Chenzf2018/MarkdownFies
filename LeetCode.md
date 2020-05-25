# 字符串

## 3. 无重复字符的最长子串（**）

**题目**：

给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。

**提示**：

```
输入: "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

输入: "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。

输入: "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。

请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
```

### 滑动窗口

**思路和算法**

不妨以示例一中的字符串$\texttt{abcabcbb}$为例，找出**从每一个字符开始的，不包含重复字符的最长子串**，那么其中最长的那个字符串即为答案。对于示例一中的字符串，我们列举出这些结果，其中括号中表示选中的字符以及最长的字符串：

- 以$\texttt{(a)bcabcbb}$开始的最长字符串为$\texttt{(abc)abcbb}$；
- 以$\texttt{a(b)cabcbb}$开始的最长字符串为$\texttt{a(bca)bcbb}$；
- 以$\texttt{ab(c)abcbb}$开始的最长字符串为$\texttt{ab(cab)cbb}$；
- 以$\texttt{abc(a)bcbb}$开始的最长字符串为$\texttt{abc(abc)bb}$；
- 以$\texttt{abca(b)cbb}$开始的最长字符串为$\texttt{abca(bc)bb}$；
- 以$\texttt{abcab(c)bb}$开始的最长字符串为$\texttt{abcab(cb)b}$；
- 以$\texttt{abcabc(b)b}$开始的最长字符串为$\texttt{abcabc(b)b}$；
- 以$\texttt{abcabcb(b)}$开始的最长字符串为$\texttt{abcabcb(b)}$。



如果我们依次递增地枚举**子串的起始位置**，那么子串的结束位置也是递增的！

这里的原因在于，假设我们选择字符串中的第$k$个字符作为起始位置，并且得到了**不包含重复字符的最长子串的结束位置为$r_k$**。那么**当我们选择第$k+1$个字符作为起始位置时（移除第$k$个字符`hashSet.remove(s.charAt(i - 1));`）**，首先从$k+1$到$r_k$的字符显然是不重复的，并且由于少了原本的第$k$个字符，我们可以尝试继续增大$r_k$，直到右侧出现了重复字符为止。

这样以来，我们就可以使用**滑动窗口**来解决这个问题了：

- 我们使用**两个指针**表示字符串中的某个子串（的**左右边界**）。其中**左指针**代表着上文中**枚举子串的起始位置**，而**右指针**即为上文中的$r_k$；

- 在每一步的操作中
    - 左指针向右移动一格，表示**开始枚举下一个字符作为起始位置**
    - 不断地向右移动右指针，但需要**保证这两个指针对应的子串中没有重复的字符**。
    - 在移动结束后，这个子串就对应着**以左指针开始的，不包含重复字符的最长子串**。我们记录下这个子串的长度；

- 在枚举结束后，我们找到的最长的子串的长度即为答案。

**判断重复字符**：

还需要使用一种数据结构来判断**是否有重复的字符**，常用的数据结构为**哈希集合**(`HashSet`)。在左指针向右移动的时候，我们从哈希集合中移除一个字符，在右指针向右移动的时候，我们往哈希集合中添加一个字符。

### 代码

```java
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {
    private static int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> hashSet = new HashSet<>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;

        // i窗口的起始位置
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                hashSet.remove(s.charAt(i - 1));
            }

            while (rk + 1 < n && ! hashSet.contains(s.charAt(rk + 1))) {
                // 没有重复的，就不断地移动右指针
                hashSet.add(s.charAt(rk + 1));
                ++rk;
            }

            // 第 i 到 rk 个字符是一个极长的无重复字符子串，长度为rk - i + 1
            // i是当前左指针所在位置
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入待检测的字符串：");
        System.out.println("无重复字符的最长子串：" + lengthOfLongestSubstring(input.nextLine()));
    }
}
```

### 复杂度分析

- 时间复杂度：$O(N)$，其中$N$是字符串的长度。左指针和右指针分别会遍历整个字符串一次。

- 空间复杂度：$O(|\Sigma|)$，其中$\Sigma$表示字符集（即字符串中可以出现的字符），$|\Sigma|$表示字符集的大小。在本题中没有明确说明字符集，因此可以默认为所有`ASCII`码在`[0, 128)`内的字符，即$|\Sigma| = 128$。我们需要**用到哈希集合来存储出现过的字符**，而字符最多有$|\Sigma|$个，因此空间复杂度为$O(|\Sigma|)$。


**使用map**
<div align=center><img src=LeetCode\3滑动窗口.png></div>


# 动态规划

## 5. 最长回文子串（**）

给定一个字符串`s`，找到`s`中最长的回文子串。你可以假设`s`的最大长度为1000。

**示例**：

```
输入: "babad"
输出: "bab"
注意: "aba" 也是一个有效答案。

输入: "cbbd"
输出: "bb"
```

**思路与算法**：

对于一个子串而言，如果它是回文串，并且长度大于2，那么将它首尾的两个字母去除之后，它仍然是个回文串。

例如对于字符串$\textrm{``ababa''}$，如果我们已经知道$\textrm{``bab''}$是回文串，那么$\textrm{``ababa''}$一定是回文串，这是因为它的首尾两个字母都是$\textrm{``a''}$。

根据这样的思路，我们就可以用动态规划的方法解决本题。我们用$P(i,j)$表示字符串$s$的第$i$到$j$个字母组成的串（下文表示成$s[i:j]$）是否为回文串：

$P(i,j)=\left\{\begin{matrix}
true, 如果字串S_{i}...S_{j}是回文串 &\\
false, 其他情况&
\end{matrix}\right.$

这里的**其它情况**包含两种可能性：

- $s[i,j]$本身不是一个回文串；
- $i > j$，此时$s[i, j]$本身不合法。

那么就可以写出动态规划的<font color=red>状态转移方程</font>：
$P(i, j) = P(i+1, j-1) \wedge (S_i == S_j)$

也就是说，**只有$s[i+1:j-1]$是回文串，并且$s$的第$i$和$j$个字母相同时，$s[i:j]$才会是回文串**。

上文的所有讨论是建立在子串长度大于2的前提之上的，我们还需要考虑动态规划中的**边界条件**，即**子串的长度为1或2**。对于长度为1的子串，它显然是个回文串；对于长度为2的子串，只要它的两个字母相同，它就是一个回文串。因此我们就可以写出动态规划的<font color=red>边界条件</font>：

$\left\{\begin{matrix}
P(i,i)=true &\\
P(i,i+1)=(S_i == S_{i+1})&
\end{matrix}\right.$

根据这个思路，我们就可以完成动态规划了，**最终的答案即为所有$P(i, j) = \text{true}$中$j-i+1$（即子串长度）的最大值**。

注意：在状态转移方程中，我们是**从长度较短的字符串**向**长度较长的字符串**进行转移的，因此一定要注意动态规划的循环顺序。

### 状态转移方程

- 状态：$dp[i][j]$表示子串$s[i...j]$是否是回文；
- 状态转移方程：$dp[i][j] = ((s[i] == s[j])$ `and` $dp[i+1][j-1])$，在$s[i] == s[j]$前提下，子串是否回文决定了整体是否是回文。
- 边界条件：$(j - 1) - (i + 1) + 1< 2$，即$j - i < 3$或$s[i...j]$长度小于3时，不用检查子串是否回文，不需要状态转移。
- <font color=red>结合边界条件，可将状态转移方程写成</font>$dp[i][j] = ((s[i] == s[j])$ `and` $(dp[i+1][j-1]$ `or` $j - i < 3))$

### 用填表来理解状态转移
<div align=center><img src=LeetCode\dp填表法.png width=60%></div>

**初始化dp表**：（由于$i\leqslant j$，表左下角全为`FALSE`）
<div align=center><img src=LeetCode\初始化dp表.png></div>

**注意右上角填表顺序**：
<div align=center><img src=LeetCode\dp填表.png></div>

### 代码
```java
import java.util.Scanner;

/**
 * 5. 最长回文子串（**）
 */

public class LongestPalindromicSubstring {
    private static String longestPalindrome(String s){
        int len = s.length();
        if (len < 2)
            return s;

        int maxLen = 1;  // 注意这里初始化为1
        int begin = 0;

        // dp[i][j]表示s[i...j]是否是回文串
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++)
            dp[i][i] = true;

        char[] charArray = s.toCharArray();  // 注意这里直接转换成数组
        // 表格的左下角先填
        for (int j = 1; j < len; j++){
            for (int i = 0; i < j; i++){
                if (charArray[i] != charArray[j])
                    dp[i][j] = false;
                else{
                    if (j - i < 3)
                        dp[i][j] = true;
                    else
                        dp[i][j] = dp[i + 1][j - 1];
                }

                // 只要dp[i][j] == true成立，就表示子串s[i...j]是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen){
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }

        return s.substring(begin, begin + maxLen);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入待检测的字符串：");
        System.out.println("最长回文子串：" + longestPalindrome(input.nextLine()));
    }
}
```

### 复杂度分析

- 时间复杂度： $O(n^2)$ 两个for循环
- 空间复杂度： $O(n^2)$ dp数组的大小

## 53. 最大子序和（*）

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
### 代码

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

### 复杂度分析

- 时间复杂度：$O(n)$，其中$n$为`nums`数组的长度。我们只需要遍历一遍数组即可求得答案。
- 空间复杂度：$O(1)$。我们只需要常数空间存放若干变量。



# 回溯算法

## 10. 正则表达式匹配(***)

给你一个字符串`s`和一个字符规律`p`，请你来实现一个支持`'.'`和`'*'`的正则表达式匹配。

```
'.' 匹配任意单个字符
'*' 匹配零个或多个前面的那一个元素
```

所谓匹配，是要涵盖整个字符串`s`的，而不是部分字符串。

说明:
```
s 可能为空，且只包含从 a-z 的小写字母。
p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
```

**示例**：
```
输入:
s = "aa"
p = "a"
输出: false
解释: "a" 无法匹配 "aa" 整个字符串。

输入:
s = "aa"
p = "a*"
输出: true
解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。

输入:
s = "ab"
p = ".*"
输出: true
解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。

输入:
s = "aab"
p = "c*a*b"
输出: true
解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。

输入:
s = "mississippi"
p = "mis*is*p*."
输出: false
```

### 思路与算法

- 如果没有星号（正则表达式中的`*`），只需要从左到右检查匹配串 `s`是否能匹配模式串`p`的每一个字符。

- 如果模式串中有星号，它会出现在**第二个位置**，即$\text{pattern[1]}$
    - 可以直接忽略**模式串**中这一部分；
    - 或者**删除匹配串**的第一个字符，**前提**是它能够匹配模式串当前位置字符，即$\text{pattern[0]}$

### 代码

```java
import java.util.Scanner;

public class RegularExpressionMatching {
    private static boolean isMatch(String text, String pattern) {
        if (pattern.isEmpty())
            return text.isEmpty();

        boolean first_match = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*'){
            return (isMatch(text, pattern.substring(2)) ||
                    (first_match && isMatch(text.substring(1), pattern)));
        } else {
            // 在text和pattern第一个元素匹配的前提下，比较两者第二个元素
            return first_match && isMatch(text.substring(1), pattern.substring(1));
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入待匹配字符串：");
        String str1 = input.nextLine();
        System.out.println("请输入匹配模式：");
        String str2 = input.nextLine();
        System.out.println(str1 + " 与 " + str2 + " 是否匹配：" + isMatch(str1, str2));
    }
}
```

### 复杂度分析

用$T$和$P$分别表示匹配串和模式串的长度，时间复杂度和空间复杂度均为：$O\big((T+P)2^{T + \frac{P}{2}}\big)$

# 栈

## 84. 柱状图中最大的矩形(***)

给定`n`个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为1。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

<div align=center><img src=LeetCode\84histogram.png></div>

以上是柱状图的示例，其中每个柱子的宽度为1，给定的高度为`[2,1,5,6,2,3]`。

<div align=center><img src=LeetCode\84histogram_area.png></div>

图中阴影部分为所能勾勒出的最大矩形面积，其面积为10个单位。




## 85. 最大矩形

给定一个仅包含`0`和`1`的二维二进制矩阵，找出只包含`1`的最大矩形，并返回其面积。

**示例**：

```
输入:
[
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
输出: 6
```

# 哈希表

## 1. 两数之和（*）

给定一个整数数组`nums`和一个目标值`target`，请你在该数组中找出和为目标值的那**两个整数**，并返回他们的数组**下标**。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

**示例**：
```
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```

### 思路与算法

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

**两遍哈希表**：

为了对运行时间复杂度进行优化，我们需要一种更有效的方法来检查数组中是否存在目标元素。如果存在，我们需要找出它的索引。保持数组中的每个**元素**与其**索引**相互对应的最好方法是什么？哈希表。

通过**以空间换取速度**的方式，我们可以将查找时间从$O(n)$降低到$O(1)$。哈希表正是为此目的而构建的，它支持**以近似恒定的时间进行快速查找**。用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到$O(n)$。但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为$O(1)$。

一个简单的实现使用了**两次迭代**。在第一次迭代中，我们将每个元素的值和它的索引**添加**到表中。然后，在第二次迭代中，我们将**检查**每个元素所对应的目标元素$(target - nums[i])$是否存在于表中。注意，**该目标元素不能是$nums[i]$本身**！


### 代码
```java
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    private static int[] twoSum(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
            map.put(nums[i], i);

        for (int i = 0; i < nums.length; i++){
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i)
                return new int[] { i, map.get(complement) };
        }

        throw new IllegalArgumentException("No two sum solution.");
    }

    public static void main(String[] args) {
        int[] results = twoSum(new int[]{2, 7, 11, 15}, 9);
        for (int result : results)
            System.out.print(result + " ");
    }
}
```

# 链表

## 2. 两数相加（**）

给出两个**非空**的链表用来表示两个**非负的整数**。其中，它们各自的位数是按照**逆序**的方式存储的，并且它们的每个节点只能存储**一位**数字。

如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

您可以假设除了数字0之外，这两个数都不会以0开头。

**示例**：

```
输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807
```

### 思路与算法

<div align=center><img src=LeetCode\2预先指针.png></div>

测试用例 | 说明 | 
:- | :-: | 
$l1=[0,1]，l2=[0,1,2]$ | 当一个列表比另一个列表长时 | 
$l1=[]，l2=[0,1]$ | 当一个列表为空时，即出现空列表 | 
$l1=[9,9]，l2=[1]$ | 求和运算最后可能出现额外的进位，这一点很容易被遗忘 | 

### 代码

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

public class AddTwoNumbers {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode addTwoNumbers(ListNode listNode1, ListNode listNode2)
    {
        ListNode pre = new ListNode(0);  // 定义一个哑结点dummyHead/预先指针pre
        ListNode p = listNode1, q = listNode2, curr = pre;
        int carry = 0;  // 进位

        while (p != null || q != null)
        {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;

            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if (p != null)
                p = p.next;
            if (q != null)
                q = q.next;
        }

        // 防止求和最后出现进位 99 + 1 -> 001
        if (carry == 1)
            curr.next = new ListNode(carry);

        return pre.next;
    }
}
```

# 二分查找

## 4. 寻找两个正序数组的中位数（***）

给定两个大小为`m`和`n`的正序（从小到大）数组`nums1`和`nums2`。请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为$O(log(m + n))$。

你可以假设`nums1`和`nums2`不会同时为空。

**示例**：
```
nums1 = [1, 3]
nums2 = [2]

则中位数是 2.0

nums1 = [1, 2]
nums2 = [3, 4]

则中位数是 (2 + 3)/2 = 2.5
```

### 思路与算法

给定两个有序数组，要求找到两个有序数组的中位数，最直观的思路有以下两种：

- 使用归并的方式，合并两个有序数组，得到一个大的**有序**数组。大的有序数组的中间位置的元素，即为中位数。

- 不需要合并两个有序数组，只要找到中位数的位置即可。由于两个数组的长度已知，因此**中位数对应的两个数组的下标之和**也是已知的。维护两个指针，初始时分别指向两个数组的下标$0$的位置，每次将指向较小值的指针后移一位（如果一个指针已经到达数组末尾，则只需要移动另一个数组的指针），直到到达中位数的位置。

假设两个有序数组的长度分别为$m$和$n$，上述两种思路的复杂度如何？

第一种思路的时间复杂度是$O(m+n)$，空间复杂度是$O(m+n)$。第二种思路虽然可以将空间复杂度降到$O(1)$，但是时间复杂度仍是$O(m+n)$。题目要求时间复杂度是$O(\log(m+n))$，因此上述两种思路都不满足题目要求的时间复杂度。

如何把时间复杂度降低到$O(\log(m+n))$呢？如果对时间复杂度的要求有$\log$，通常都需要用到**二分查找**，这道题也可以通过二分查找实现。

根据中位数的定义，当$m+n$是奇数时，中位数是两个有序数组中的第 $(m+n)/2$个元素($(m+n+1)/2$**说法似乎有问题，应是向上取整**)，当$m+n$是偶数时，中位数是两个有序数组中的第 $(m+n)/2$个元素和第$(m+n)/2+1$个元素的平均值。因此，这道题可以转化成寻找两个有序数组中的第$k$小的数，其中$k$为$(m+n)/2$或$(m+n)/2+1$。

假设两个有序数组分别是$\text{A}$和$\text{B}$。要找到第$k$个元素，我们可以比较$\text{A}[k/2-1]$和$\text{B}[k/2-1]$，其中`/`表示**整数除法**。由于$\text{A}[k/2-1]$和$\text{B}[k/2-1]$的前面分别有$\text{A}[0\,..\,k/2-2]$和$\text{B}[0\,..\,k/2-2]$，即$k/2-1$个元素，对于$\text{A}[k/2-1]$和$\text{B}[k/2-1]$中的较小值，最多只会有$(k/2-1)+(k/2-1) \leq k/2-2$个元素比它小，那么它就不能是第$k$小的数了。

因此我们可以归纳出三种情况：

- 如果$\text{A}[k/2-1] < \text{B}[k/2-1]$，则比$\text{A}[k/2-1]$小的数最多只有$\text{A}$的前$k/2-1$个数和$\text{B}$的前$k/2-1$个数，即比$\text{A}[k/2-1]$小的数最多只有$k-2$个，因此 $\text{A}[k/2-1]$不可能是第$k$个数，$\text{A}[0]$到$\text{A}[k/2-1]$也都不可能是第$k$个数，可以全部排除。

- 如果$\text{A}[k/2-1] > \text{B}[k/2-1]$，则可以排除 $\text{B}[0]$到$\text{B}[k/2-1]$。

- 如果$\text{A}[k/2-1] = \text{B}[k/2-1]$，则可以归入第一种情况处理。

<div align=center><img src=LeetCode\4_fig1.png></div>

可以看到，比较$\text{A}[k/2-1]$和$\text{B}[k/2-1]$之后，可以排除$k/2$个不可能是第$k$小的数，查找范围缩小了一半。同时，我们将在排除后的新数组上继续进行二分查找，并且根据我们排除数的个数，减少$k$的值，这是因为我们排除的数都不大于第$k$小的数。

有以下三种情况需要特殊处理：

- 如果$\text{A}[k/2-1]$或者$\text{B}[k/2-1]$越界，那么我们可以选取对应数组中的最后一个元素。在这种情况下，我们必须根据排除数的个数减少$k$的值，而不能直接将$k$减去$k/2$。

- 如果一个数组为空，说明该数组中的所有元素都被排除，我们可以直接返回另一个数组中第$k$小的元素。

- 如果$k=1$，我们只要返回两个数组首元素的最小值即可。


# 数组

## 4. 寻找两个正序数组的中位数（***）

给定两个大小为`m`和`n`的正序（从小到大）数组`nums1`和`nums2`。请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为$O(log(m + n))$。

你可以假设`nums1`和`nums2`不会同时为空。

**示例**：
```
nums1 = [1, 3]
nums2 = [2]

则中位数是 2.0

nums1 = [1, 2]
nums2 = [3, 4]

则中位数是 (2 + 3)/2 = 2.5
```

### 双指针

合并两个数组！

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length, n = n1 + n2;
        int i = 0, j = 0, k = 0, a[] = new int[n];

        while (i < n1 && j < n2) {
            if (nums1[i] < nums2[j]) 
                a[k++] = nums1[i++];
            else             
                a[k++] = nums2[j++];
        }

        while (i < n1)  
            a[k++] = nums1[i++];

        while (j < n2)  
            a[k++] = nums2[j++];

        return n % 2 == 1 ? a[(n - 1) / 2] * 1.0 : (a[n / 2] + a[n / 2 - 1]) / 2.0;
    }
}
```
