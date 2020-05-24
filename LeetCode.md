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

        int maxLen = 1;
        int begin = 0;

        // dp[i][j]表示s[i...j]是否是回文串
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++)
            dp[i][i] = true;

        char[] charArray = s.toCharArray();
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