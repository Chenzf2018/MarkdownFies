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

这里的原因在于，假设我们选择字符串中的第$k$个字符作为起始位置，并且得到了**不包含重复字符的最长子串的结束位置为$r_k$**。那么**当我们选择第$k+1$个字符作为起始位置时（移除第$k$个字符`hashSet.remove(s.charAt(i - 1));`），首先从$k+1$到$r_k$的字符显然是不重复的，并且由于少了原本的第$k$个字符，我们可以尝试继续增大$r_k$，直到右侧出现了重复字符为止**。

这样以来，我们就可以使用**滑动窗口**来解决这个问题了：

- 我们使用**两个指针**表示字符串中的某个子串（的**左右边界**）。其中**左指针**代表着上文中**枚举子串的起始位置**，而**右指针**即为上文中的$r_k$；

- 在每一步的操作中
    - 左指针向右移动一格，表示**开始枚举下一个字符作为起始位置**
    - 不断地向右移动右指针，但需要**保证这两个指针对应的子串中没有重复的字符**。
    - 在移动结束后，这个子串就对应着**以左指针开始的，不包含重复字符的最长子串**。我们记录下这个子串的长度；

- 在枚举结束后，我们找到的最长的子串的长度即为答案。

**判断重复字符**：

还需要使用一种数据结构来判断**是否有重复的字符**，常用的数据结构为**哈希集合**(`HashSet`)。在左指针向右移动的时候，我们从哈希集合中移除一个字符，在右指针向右移动的时候，我们往哈希集合中添加一个字符。

**代码：**

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

**复杂度分析：**

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

**状态转移方程：**

- 状态：$dp[i][j]$表示子串$s[i...j]$是否是回文；
- 状态转移方程：$dp[i][j] = ((s[i] == s[j])$ `and` $dp[i+1][j-1])$，在$s[i] == s[j]$前提下，子串是否回文决定了整体是否是回文。
- 边界条件：$(j - 1) - (i + 1) + 1< 2$，即$j - i < 3$或$s[i...j]$长度小于3时，不用检查子串是否回文，不需要状态转移。
- <font color=red>结合边界条件，可将状态转移方程写成</font>$dp[i][j] = ((s[i] == s[j])$ `and` $(dp[i+1][j-1]$ `or` $j - i < 3))$

**用填表来理解状态转移：**
<div align=center><img src=LeetCode\dp填表法.png width=60%></div>

**初始化dp表**：（由于$i\leqslant j$，表左下角全为`FALSE`）
<div align=center><img src=LeetCode\初始化dp表.png></div>

**注意右上角填表顺序**：
<div align=center><img src=LeetCode\dp填表.png></div>

**代码**：
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

**复杂度分析**：

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

**复杂度分析：**

- 时间复杂度：$O(n)$，其中$n$为`nums`数组的长度。我们只需要遍历一遍数组即可求得答案。
- 空间复杂度：$O(1)$。我们只需要常数空间存放若干变量。


## 818. 赛车（***）

你的赛车起始停留在位置0，速度为+1，正行驶在一个无限长的数轴上。（车也可以向负数方向行驶。）

你的车会根据一系列由$A$（加速）和$R$（倒车）组成的指令进行自动驾驶。

当车得到指令`"A"`时, 将会做出以下操作：`position += speed, speed *= 2`。

当车得到指令`"R"`时, 将会做出以下操作：如果当前速度是正数，则将车速调整为`speed = -1`；否则将车速调整为`speed = 1`。  (当前所处位置不变。)

例如，当得到一系列指令`"AAR"`后, 你的车将会走过位置 `0->1->3->3`，并且速度变化为`1->2->4->-1`。

现在给定一个目标位置，请给出能够到达目标位置的最短指令列表的长度。

**示例：**
```
示例 1:
输入: 
target = 3
输出: 2
解释: 
最短指令列表为 "AA"
位置变化为 0->1->3

示例 2:
输入: 
target = 6
输出: 5
解释: 
最短指令列表为 "AAARA"
位置变化为 0->1->3->7->7->6
```

**思路与算法**

我们用$A^k$表示连续使用`k`次$A$指令，这样就可以用$A^{k_1} R A^{k_2} R \cdots A^{k_n}, k_i \geq 0$表示任意一种指令列表。注意到**最优的指令列表不可能以$R$结束，因为在到了终点后转向是无意义的；同样最优的指令列表也不必以$R$开始**，假设$R A^{k_1} R A^{k_2} \cdots R A^{k_n}$是一种最优的指令列表，那么我们可以将$R A^{k_1} R$根据$n$的奇偶性将其变为$R A^{k_1}$或$RR A^{k_1}$放在指令列表的末尾。

对于指令列表$A^{k_1} R A^{k_2} R \cdots A^{k_n}$，它可以使得赛车到达的位置为$(2^{k_1} - 1) - (2^{k_2} - 1) + (2^{k_3} - 1) - \cdots$，因此不失一般性，可以交换$k_1, k_3, \cdots$这些奇数位置的$k_i$使得这个数列单调不增，同样可以交换$k_2, k_4, \cdots$这些偶数位置的$k_i$使得这个数列单调不增。同时所有的$k_i$都有一个上界$a + 1$，其中$a$为最小满足$2^a \geq \text{target}$的整数，即如果在某一时刻赛车经过了终点，那么折返比继续行驶更优。

可以使用动态规划来找到最短的指令长度。

假设我们需要到达位置`x`，且$2^{k-1} \leq x < 2^k$，我们用 `dp[x]`表示到达位置`x`的最短指令长度。如果$t = 2^k - 1$（$t$表示当前位置），那么我们只需要用$A^k$即可。否则我们需要考虑两种情况：

- 我们首先用$A^{k-1}$到达位置$2^{k-1} - 1$，此时我们已经**逼近了终点**，随后折返并使用$A^j$，这样我们到达了位置$2^{k-1} - 2^j$，使用的指令为$A^{k-1} R A^j R$（方向需要对着`target`），长度为$k - 1 + j + 2$，剩余的距离为$x - (2^{k-1} - 2^j) < x$；

- 我们首先用$A^k$到达位置$2^k - 1$，随后仅使用折返指令，此时我们已经**超过了终点**并且速度方向朝向终点，使用的指令为$A^k R$，长度为$k + 1$，剩余的距离为$x - (2^k - 1) < x$。

**另一种思路（推荐）**：

`dp[i]`：车子从0行驶到`i`的最短指令长度，起始速度是1，和target方向相同。
`dp[0] = 0`，我们要求的是`dp[target]`。

因为每次转向，车速会变为1，所以我们可以利用这个特性来复用之前在`dp`里存的值，即可以找到重复子问题。每次车子转向了，车速变为1，如果这时的速度方向也是朝向`target`，那就和起初状态相同了，可以用`dp`了！所以，我们需要找到这种情况，就可以分解成子问题了。

我们能用的是$A$和$R$，但我们不知道中途会用多少次$A$和$R$。假设我们先$A$了$k$次，在$k$次加速后的位置是：`pos = 2^k - 1`

- 如果`pos < target`：要先$A$若干次再$R$（这时行驶方向和`target`相反了）再若干次$A$再$R$。即$AA…ARA…AR$。在这两次$R$之间，假设$A$了$q$次，这时的位置在`pos - (2^q - 1)`。然后这时候是第二次的$R$，`speed = 1`，方向和`target`相同。这个就等于从0走到`target - (pos - (2^q - 1))`，这个步骤的最短指令集长度就是`dp[target - (pos - (2^q - 1))]`。
所以，这个情况的全部指令集长度是`k（先A了k次）+ 1（R）+ q（再A了q次）+ 1（R）+ dp[target - (pos - (2^q - 1))]`。即`dp[i] = min(dp[i], k + 1 + q + 1 + dp[target - (pos - 2^q - 1)])`

- 如果`pos == target`：可以直接一直$A$到`target`。即$AA…A$
这时最短指令集长度就是`k`，不用任何转向。即`dp[i] = k`。

- 如果`pos > target`：需要一次$R$，使车子现在朝向`target`。$AA…ARAA…A$。$R$过之后，现在需要达到`target`的距离是`pos - target`，速度为1，等同于原来从正方向驶向`target`的指令集长度。再加上之前的$k$次$A$和一次$R$，所以总的指令集长度为`k + 1 + dp[pos - target]`。即`dp[i] = min{dp[i], k + 1 + dp[pos - target]`。


**另一种思路（推荐）：**

- `dp[i]`：到距离为i的点的最少操作次数（前提条件：运动方向要指向i点，初始速度大小要为1）
- 经过`m`次连续$A$操作之后的走过的距离：`len = 2^m - 1`
- 由于dp的条件为：速度为1，面朝目标点，所以只有进行R操作后，且运动方向朝向目标点才可以使用dp[]
- 因此计算`dp[i]`的思路就是：通过$k$次操作到达**最接近**`i`点的$K$点，且此时的运动方向朝向`i`，遍历每一种$k$然后计算`min(k + dp[abs(i - K)])`。
- 这个$K$点有三种可能；
    - `K < i`: 首先通过$m$次$A$操作到达`j = (1 << m) - 1`（$2^m - 1$）（再多走，就超过了），然后执行一次$R$操作，此时方向与i点相反，于是进行$q$次$A$操作，移动了`p = (1 << q) - 1`步，到达`j - p`点，此时只需要先翻转一次，然后执行`dp[i - (j - p)]`次操作即可。 这种情况下的条件为：`j < i`，`p < j`。
    - `K = i`：通过$m$次$A$操作到达`j = (1 << m) - 1`，此时`j = i`，因此`dp[i] = m`。
    - `K > i`：首先通过$m$次$A$操作到达`j = (1 << m) - 1`，然后执行一次$R$操作，此时就可以通过`dp[j - i]`获得`j - i`步的最少操作数，因为翻转一次之后速度为1，且方向也指向i的方向。
- 最终`dp[i]`就是在以上三种情况下找到一个最小值。


**代码：**
```java
import java.util.Arrays;

/**
 * 818. 赛车（***）
 */

public class RaceCar {
    private static int raceCar(int target){
        int[] dp = new int[target + 3];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; dp[1] = 1; dp[2] = 4; // AARA

        for (int t = 3; t <= target; t++){
            // 指定一个int，返回这个数的二进制串中从最左边算起连续的“0”的总数量
            /*
            >>> Integer.toBinaryString(4)
            u'100'
            >>> 32-Integer.numberOfLeadingZeros(4)
            3

            >>> Integer.toBinaryString(8)
            u'1000'
            >>> 32-Integer.numberOfLeadingZeros(8)
            4 // k = 4, 2^k < t
             */
            int k = 32 - Integer.numberOfLeadingZeros(t);

            // t表示当前位置
            if (t == (1<<k) - 1){
                dp[t] = k;
                continue;
            }

            for (int j = 0; j < k - 1; j++)
                dp[t] = Math.min(dp[t], dp[t - (1<<(k - 1)) + (1<<j)] + k -1 + j + 2);

            if ((1<<k) - 1 - t < t)
                dp[t] = Math.min(dp[t], dp[(1<<k) - 1 - t] + k + 1);
        }

        return dp[target];
    }

    public static void main(String[] args) {
        System.out.println(raceCar(6));
    }
}
```


**复杂度分析：**

- 时间复杂度：$O(T \log T)$。对于每一个位置`x`，需要循环$O(\log x)$次。

- 空间复杂度：$O(T)$。



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

**思路与算法：**

- 如果没有星号（正则表达式中的`*`），只需要从左到右检查匹配串 `s`是否能匹配模式串`p`的每一个字符。

- 如果模式串中有星号，它会出现在**第二个位置**，即$\text{pattern[1]}$
    - 可以直接忽略**模式串**中这一部分；
    - 或者**删除匹配串**的第一个字符，**前提**是它能够匹配模式串当前位置字符，即$\text{pattern[0]}$

**代码：**

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

**复杂度分析：**

用$T$和$P$分别表示匹配串和模式串的长度，时间复杂度和空间复杂度均为：$O\big((T+P)2^{T + \frac{P}{2}}\big)$


## 17. 电话号码的字母组合(**)

给定一个仅包含数字`2-9`的字符串，返回所有它能表示的字母组合。

给出数字到字母的映射如下（与电话按键相同）。注意`1`不对应任何字母。

<div align=center><img src=LeetCode\17_telephone_keypad.png width=50%></div>

**示例**：
```
输入："23"
输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
```

**思路与算法：**

回溯是一种通过**穷举**所有可能情况来找到所有解的算法。如果一个候选解**最后被发现并不是可行解，回溯算法会舍弃它，并在前面的一些步骤做出一些修改，并重新尝试找到可行解**。

给出如下回溯函数`backtrack(combination, next_digits)`，它将一个目前已经产生的组合`combination`和接下来准备要输入的数字`next_digits`作为参数。

如果没有更多的数字需要被输入，那意味着当前的组合已经产生好了。
如果还有数字需要被输入：
- 遍历下一个数字所对应的所有映射的字母。
- 将当前的字母添加到组合最后，也就是`combination = combination + letter`。
- 重复这个过程，输入剩下的数字：`backtrack(combination + letter, next_digits[1:])`。

这是个排列组合问题！这个排列组合可以用树的形式表示出来；当给定了输入字符串，比如："23"，那么整棵树就构建完成了，如下：

<div align=center><img src=LeetCode\17_telephone_keypad1.png width=80%></div>

问题转化成了**从根节点到空节点一共有多少条路径**！

**代码：**
```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 17. 电话号码的字母组合(**)
 */

public class LetterCombinationsofPhoneNumber {
    static Map<String, String> phone = new HashMap<>(){{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};

     static List<String> output = new ArrayList<>();

    private static void backtrack(String combination, String next_digits){
        // if there is no more digits to check
        if (next_digits.length() == 0){
            // the combination is done
            output.add(combination);
        }
        else {  // if there are still digits to check
            // iterate over all letters which map the next available digit
            String digit = next_digits.substring(0, 1);
            String letters = phone.get(digit);

            for (int i = 0; i < letters.length(); i++){
                String letter = phone.get(digit).substring(i, i + 1);
                // append the current letter to the combination and proceed to the next digits
                backtrack(combination + letter, next_digits.substring(1));
            }
        }
    }

    private static List<String> letterCombinations(String digits){
        if (digits.length() != 0)
            backtrack("", digits);
        return output;
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
    }
}
```


# 栈

## 20. 有效的括号(*)

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


## 155. 最小栈（*）
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

要做出这道题目，首先要理解栈结构先进后出的性质。

对于栈来说，如果一个元素`a`在入栈时，栈里有其它的元素`b, c, d`，那么无论这个栈在之后经历了什么操作，只要`a`在栈中，`b, c, d`就一定在栈中，因为在`a`被弹出之前，`b, c, d`不会被弹出。

因此，在操作过程中的任意一个时刻，只要栈顶的元素是`a`，那么我们就可以确定栈里面现在的元素一定是`a, b, c, d`。

那么，我们可以在每个元素`a`入栈时把当前栈的最小值`m`存储起来。在这之后无论何时，如果栈顶元素是`a`，我们就可以直接返回存储的最小值`m`。

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

## 225. 用队列实现栈

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

**压入**（push）：
新元素永远从`q1`的**后端入队**，同时`q1`的后端也是栈的 栈顶（top）元素：
<div align=center><img src=LeetCode\225_1.png></div>

```java
private Queue<Integer> q1 = new LinkedList<>();
private Queue<Integer> q2 = new LinkedList<>();
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

## 232. 用栈实现队列(*)

**题目：**

使用栈实现队列的下列操作：

- `push(x)` -- 将一个元素放入队列的尾部。
- `pop()` -- 从队列首部移除元素。
- `peek()` -- 返回队列首部的元素。
- `empty()` -- 返回队列是否为空。

**示例：**
```
MyQueue queue = new MyQueue();

queue.push(1);
queue.push(2);  
queue.peek();  // 返回 1
queue.pop();   // 返回 1
queue.empty(); // 返回 false
```

说明:

- 你只能使用标准的栈操作 -- 也就是只有`push to top`, `peek/pop from top`, `size`, 和`is empty`操作是合法的。
- 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
- 假设所有操作都是有效的 （例如，一个空的队列不会调用`pop`或者`peek`操作）。

**思路与算法：**

- 队列是一种**先进先出**（first in - first out， FIFO）的数据结构，队列中的元素都从后端（rear）入队（push），从前端（front）出队（pop）。实现队列最直观的方法是用**链表**。
- 栈是一种**后进先出**（last in - first out， LIFO）的数据结构，栈中元素从栈顶（top）压入（push)，也从栈顶弹出（pop）。
- 
为了满足队列的`FIFO`的特性，我们需要用到**两个栈**，用它们其中一个来反转元素的入队顺序，用另一个来存储元素的最终顺序。


**入队**（push）：

一个队列是`FIFO`的，但一个栈是`LIFO`的。这就意味着最新压入的元素必须得放在栈底。为了实现这个目的，我们首先需要把`s1`中所有的元素移到`s2`中，接着把新元素压入`s2`。最后把`s2`中所有的元素弹出，再把弹出的元素压入`s1`。

<div align=center><img src=LeetCode\232_1.png></div>

```java
private int front;

public void push(int x) {
    if (s1.empty())
        front = x;
    while (!s1.isEmpty())
        s2.push(s1.pop());
    s2.push(x);
    while (!s2.isEmpty())
        s1.push(s2.pop());
}
```

时间复杂度：$O(n)$
对于除了新元素之外的所有元素，它们都会被压入两次，弹出两次。新元素只被压入一次，弹出一次。这个过程产生了$4n + 2$次操作，其中$n$是队列的大小。由于**压入**操作和**弹出**操作的时间复杂度为$O(1)$，所以时间复杂度为$O(n)$。

空间复杂度：$O(n)$
需要额外的内存来存储队列中的元素。


**出队**（pop）：

根据栈`LIFO`的特性，`s1`中第一个压入的元素在栈底。为了弹出`s1`的栈底元素，我们得把`s1`中所有的元素全部弹出，再把它们压入到另一个栈`s2`中，这个操作会让元素的入栈顺序反转过来。通过这样的方式，`s1`中栈底元素就变成了`s2`的栈顶元素，这样就可以直接从`s2`将它弹出了。一旦`s2`变空了，我们只需把`s1`中的元素再一次转移到`s2`就可以了。

<div align=center><img src=LeetCode\232_2.png></div>

```java
// Removes the element from in front of queue.
public int pop() {
    if (s2.isEmpty()) {
        while (!s1.isEmpty())
            s2.push(s1.pop());
    }
    return s2.pop();    
}
```

**代码：**

```java
class ImplementQueueUsingStacks {
    Stack<Integer> stackPush;
    Stack<Integer> stackPop;

    /** Initialize your data structure here. */
    public MyQueue() {
        stackPush = new Stack<>();
        stackPop = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        stackPush.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if(stackPush.empty() && stackPop.empty())
            throw new RuntimeException("Stack is empty!");
        else if(stackPop.empty()){
            while(!stackPush.empty())
                stackPop.push(stackPush.pop());
        }

        return stackPop.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        if(stackPush.empty() && stackPop.empty())
            throw new RuntimeException("Stack is empty!");
        else if(stackPop.empty()){
            while(!stackPush.empty())
                stackPop.push(stackPush.pop());
        }
        return stackPop.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        if(stackPop.empty() && stackPush.empty())
            return true;
        return false;
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
```

## 496. 下一个更大元素 I(*)

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

**思路与算法：单调栈**

我们可以忽略数组`nums1`，先将`nums2`中的每一个元素，求出其下一个更大的元素。随后将这些答案放入哈希映射（HashMap）中，再遍历数组`nums1`，并直接找出答案。对于`nums2`，我们可以使用**单调栈**来解决这个问题。

我们首先把第一个元素`nums2[1]`放入栈，随后对于第二个元素`nums2[2]`，如果`nums2[2] > nums2[1]`，那么我们就找到了`nums2[1]`的下一个更大元素`nums2[2]`，此时就可以**把`nums2[1]`出栈并把`nums2[2]`入栈**；**如果`nums2[2] <= nums2[1]`，我们就仅把`nums2[2]`入栈**。对于第三个元素`nums2[3]`，此时栈中有若干个元素，那么所有比`nums2[3]`小的元素都找到了下一个更大元素（即`nums2[3]`），因此可以出栈，在这之后，我们将`nums2[3]`入栈，以此类推。

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
            map.put(stack.pop(),-1);

        for(int i = 0; i < nums1.length; i++)
            result[i] = map.get(nums1[i]);
        
        return result;
    }
}
```

**复杂度分析：**

时间复杂度：$O(M+N)$，其中$M$和$N$分别是数组`nums1`和`nums2`的长度。

空间复杂度：$O(N)$。我们在遍历`nums2`时，需要使用栈，以及哈希映射用来临时存储答案。


## 682. 棒球比赛（*）

**题目：**

你现在是棒球比赛记录员。
给定一个字符串列表，每个字符串可以是以下四种类型之一：
- 整数（一轮的得分）：直接表示您在本轮中获得的积分数。
- "+"（一轮的得分）：表示本轮获得的得分是前两轮有效回合得分的总和。
- "D"（一轮的得分）：表示本轮获得的得分是前一轮有效回合得分的两倍。
- "C"（一个操作，这不是一个回合的分数）：表示您获得的最后一个有效回合的分数是无效的，应该被移除。

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
复杂度分析：$O(N)$，其中$N$是`ops`的长度。我们解析给定数组中的每个元素，然后每个元素执行$O(1)$的工作。

空间复杂度：$O(N)$，用于存储`stack`的空间。

## 844. 比较含退格的字符串（*）

**题目：**
给定`S`和`T`两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。`#`代表退格字符。

注意：如果对空文本输入退格字符，文本继续为空。

**示例：**
```
输入：S = "ab#c", T = "ad#c"
输出：true
解释：S 和 T 都会变成 “ac”。

输入：S = "ab##", T = "c#d#"
输出：true
解释：S 和 T 都会变成 “”。

输入：S = "a##c", T = "#a#c"
输出：true
解释：S 和 T 都会变成 “c”。

输入：S = "a#c", T = "b"
输出：false
解释：S 会变成 “c”，但 T 仍然是 “b”。
```

提示：$1 <= S.length <= 200$；$1 <= T.length <= 200$；`S`和`T`只含有小写字母以及字符 `#`。

**思路与算法：**

使用`build(S)`和`build(T)`，借助栈构造去除了退格符和被删除字符后的字符串，然后比较它们是否相等。

**代码：**

```java
class BackspaceStringCompare {
    public boolean backspaceCompare(String S, String T) {
        return build(S).equals(build(T));
    }

    public String build(String S){
        Stack<Character> ans = new Stack<>();

        for(char c : S.toCharArray()){
            if(c != '#')
                ans.push(c);
            else if(! ans.empty())
                ans.pop();
        }

        return String.valueOf(ans);
    } 
}
```

**复杂度分析：**
时间复杂度：$O(M + N)$，其中$M, N$是字符串$S$和$T$的长度。

空间复杂度：$O(M + N)$。

进阶：你可以用$O(N)$的时间复杂度和$O(1)$的空间复杂度解决该问题吗？

## 1021. 删除最外层的括号(*)

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


## 1047. 删除字符串中的所有相邻重复项(*)

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


**代码：**
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

## 1441. 用栈操作构建数组(*)

**题目：**

给你一个目标数组`target`和一个整数`n`。每次迭代，需要从`list = {1,2,3..., n}`中依序读取一个数字。

请使用下述操作来构建目标数组`target`：
- `Push`：从`list`中读取一个新元素，并将其推入数组中。
- `Pop`：删除数组中的最后一个元素。

如果目标数组构建完成，就停止读取更多元素。

题目数据保证目标数组严格递增，并且只包含`1`到`n`之间的数字。请返回构建目标数组所用的操作序列。题目数据保证答案是唯一的。

**示例：**

```
输入：target = [1,3], n = 3
输出：["Push","Push","Pop","Push"]
解释： 
读取 1 并自动推入数组 -> [1]
读取 2 并自动推入数组，然后删除它 -> [1]
读取 3 并自动推入数组 -> [1,3]

输入：target = [1,2,3], n = 3
输出：["Push","Push","Push"]

输入：target = [1,2], n = 4
输出：["Push","Push"]
解释：只需要读取前 2 个数字就可以停止。

输入：target = [2,3,4], n = 4
输出：["Push","Pop","Push","Push","Push"]
```

**代码：**

```java
class Solution {
    public List<String> buildArray(int[] target, int n) {
        List<String> result = new ArrayList<>();
        int index = 0;
        for (int i = 1; i <= n; i ++) {
            // 存在的数字只需要"Push"
            if (target[index] == i) {
                result.add("Push");
                index ++;
                // 保证索引有效
                if (index >= target.length) {
                    break;
                }
            }
            else {
                result.add("Push");
                result.add("Pop");
            }
        }
        return result;
    }
}
```

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

**两遍哈希表**：

为了对运行时间复杂度进行优化，我们需要一种更有效的方法来检查数组中是否存在目标元素。如果存在，我们需要找出它的索引。保持数组中的每个**元素**与其**索引**相互对应的最好方法是什么？哈希表。

通过**以空间换取速度**的方式，我们可以将查找时间从$O(n)$降低到$O(1)$。哈希表正是为此目的而构建的，它支持**以近似恒定的时间进行快速查找**。用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到$O(n)$。但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为$O(1)$。

一个简单的实现使用了**两次迭代**。在第一次迭代中，我们**将每个元素的值和它的索引添加到表中**。然后，在第二次迭代中，我们将**检查**每个元素所对应的目标元素$(target - nums[i])$是否存在于表中。注意，**该目标元素不能是$nums[i]$本身**！


**代码：**
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
                return new int[] { i, map.get(complement) };  // 找到一组就结束
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

**思路与算法：**

<div align=center><img src=LeetCode\2预先指针.png></div>

测试用例 | 说明 | 
:- | :-: | 
$l1=[0,1]，l2=[0,1,2]$ | 当一个列表比另一个列表长时 | 
$l1=[]，l2=[0,1]$ | 当一个列表为空时，即出现空列表 | 
$l1=[9,9]，l2=[1]$ | 求和运算最后可能出现额外的进位，这一点很容易被遗忘 | 

**代码：**

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

## 19. 删除链表的倒数第N个节点(**)

给定一个链表，删除链表的倒数第n个节点，并且返回链表的头结点。

**示例**：
```
给定一个链表: 1->2->3->4->5, 和 n = 2.

当删除了倒数第二个节点后，链表变为 1->2->3->5.
```

**思路与算法：**

这个问题可以容易地简化成另一个问题：删除从列表开头数起的第$(L - n + 1)$个结点，其中$L$是列表的长度。只要我们找到列表的长度 $L$，这个问题就很容易解决。

**两次遍历**：
首先我们将添加一个**哑结点**作为辅助，该结点位于列表头部。哑结点用来简化某些极端情况，例如列表中只含有一个结点，或需要删除列表的头部。

在**第一次遍历**中，我们**找出列表的长度**$L$。**然后设置一个指向哑结点的指针，并移动它遍历列表**，直至它到达第$(L - n)$个结点那里。我们把第$(L - n)$个结点的`next`指针重新链接至第$(L - n + 2)$个结点，完成这个算法。

**一次遍历**：

可以使用**两个指针**而不是一个指针。**第一个指针从列表的开头向前移动$n+1$步**，而**第二个指针将从列表的开头出发**。现在，这**两个指针被$n$个结点分开**。我们通过**同时移动两个指针向前来保持这个恒定的间隔，直到第一个指针到达最后一个结点**。此时第二个指针将指向从最后一个结点数起的第$n$个结点。我们重新链接第二个指针所引用的结点的`next`指针指向该结点的下下个结点。
<div align=center><img src=LeetCode\19.png width = 80%></div>

**代码：**
```java
// ListNode.java
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

// RemoveNthNodeFromEndofList.java

public class RemoveNthNodeFromEndofList {
    private ListNode removeNthFromEnd(ListNode head, int n){
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;

        // Advances first pointer so that the gap between first and second is n nodes apart
        for (int i = 1; i <= n + 1; i++)
            first = first.next;

        // Move first to the end and Move second, maintaining the gap
        while (first != null){
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next;
        return dummy.next;
    }
}
```

**复杂度分析：**

时间复杂度：$O(L)$，该算法对含有$L$个结点的列表进行了一次遍历。因此时间复杂度为$O(L)$。

空间复杂度：$O(1)$，只用了常量级的额外空间。


## 206. 反转链表

**题目：**

反转一个单链表。

```
输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
```

**思路与算法：**

- **双指针迭代**
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
class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode temp = null;

        while(cur != null){
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }

        return pre;
    }
}
```

时间复杂度：$O(n)$，假设$n$是列表的长度，时间复杂度是$O(n)$。
空间复杂度：$O(1)$。


- **递归**

递归版本稍微复杂一些，其关键在于反向工作。**假设列表的其余部分已经被反转，现在我该如何反转它前面的部分**？

假设列表为：$n_{1}\rightarrow ... \rightarrow n_{k-1} \rightarrow n_{k} \rightarrow n_{k+1} \rightarrow ... \rightarrow n_{m} \rightarrow \varnothing$

若从节点$n_{k+1}$到$n_{m}$已经被反转，而我们正处于$n_{k}$：$n_{1}\rightarrow ... \rightarrow n_{k-1} \rightarrow n_{k} \rightarrow n_{k+1} \leftarrow ... \leftarrow n_{m}$

我们希望$n_{k+1}$的下一个节点指向$n_{k}$。所以，$n_{k}.next.next = n_{k}$。

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
空间复杂度：$O(n)$，由于使用递归，将会使用隐式栈空间。递归深度可能会达到$n$层。


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

**思路与算法：**

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

# 双指针

## 11. 盛最多水的容器（**）

给你n个非负整数$a1，a2，...，an$，每个数代表坐标中的一个点$(i, ai)$。在坐标内画n条垂直线，垂直线i的两个端点分别为$(i, ai)$和$(i, 0)$。找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。

说明：你不能倾斜容器，且n的值至少为2。


**示例**：
```
输入：[1,8,6,2,5,4,8,3,7]
输出：49
```

**思路与算法：**

题目中的示例为：
```
[1, 8, 6, 2, 5, 4, 8, 3, 7]
 ^                       ^
```
在初始时，**左右指针分别指向数组的左右两端**，它们可以容纳的水量为$\min(1, 7) * 8 = 8$。

此时我们需要移动一个指针。移动哪一个呢？直觉告诉我们，**应该移动对应数字较小的那个指针**（即此时的左指针）。这是因为，由于容纳的水量是由

$两个指针指向的数字中较小值 * 指针之间的距离$


决定的。

**总是移动数字较小的那个指针**的思路是正确的！

所以，我们将左指针向右移动：
```
[1, 8, 6, 2, 5, 4, 8, 3, 7]
    ^                    ^
```
此时可以容纳的水量为$\min(8, 7) * 7 = 49$。由于右指针对应的数字较小，我们移动右指针：
```
[1, 8, 6, 2, 5, 4, 8, 3, 7]
    ^                 ^
```
此时可以容纳的水量为$\min(8, 3) * 6 = 18$。由于右指针对应的数字较小，我们移动右指针：
```
[1, 8, 6, 2, 5, 4, 8, 3, 7]
    ^              ^
```
此时可以容纳的水量为$\min(8, 8) * 5 = 40$。两指针对应的数字相同，我们可以任意移动一个，例如左指针：
```
[1, 8, 6, 2, 5, 4, 8, 3, 7]
       ^           ^
```
此时可以容纳的水量为$\min(6, 8) * 4 = 24$。由于左指针对应的数字较小，我们移动左指针，并且可以发现，在这之后左指针对应的数字总是较小，因此我们会一直移动左指针，直到两个指针重合。在这期间，对应的可以容纳的水量为：$\min(2, 8) * 3 = 6，\min(5, 8) * 2 = 10，\min(4, 8) * 1 = 4$。

在我们移动指针的过程中，计算到的最多可以容纳的数量为$49$，即为最终的答案。

**代码：**

```java
public class ContainerWithMostWater {
    private static int maxArea(int[] height){
        int left = 0, right = height.length - 1;
        int ans = 0;

        while (left < right){
            int area = Math.min(height[left], height[right]) * (right - left);
            ans = Math.max(ans, area);

            if (height[left] < height[right])
                left++;
            else
                right--;
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }
}
```

**复杂度分析：**
时间复杂度：$O(N)$，双指针总计最多遍历整个数组一次。
空间复杂度：$O(1)$，只需要额外的常数级别的空间。

## 15. 三数之和

给你一个包含n个整数的数组nums，判断nums中是否存在三个元素$a，b，c$，使得$a + b + c = 0$？请你找出所有满足条件且不重复的三元组。

**示例**：
```
给定数组 nums = [-1, 0, 1, 2, -1, -4]，

满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```

**思路与算法：**

- 首先对数组进行排序，排序后固定一个数$nums[i]$，再使用左右指针指向$nums[i]$后面的两端，数字分别为$nums[L]$和$nums[R]$，计算三个数的和`sum`判断是否满足为`0`，满足则添加进结果集；
- 如果$nums[i]$大于$0$，则三数之和必然无法等于$0$，结束循环；
- 如果$nums[i] == nums[i-1]$，则说明该数字重复，会导致结果重复，所以应该跳过；
    - 当$sum == 0$时，$nums[L] == nums[L+1]$则会导致结果重复，应该跳过，$L++$；$nums[R] == nums[R-1]$则会导致结果重复，应该跳过，$R--$。
    - 当$sum < 0$时，$L++$；
    - 当$sum > 0$时，$R--$

**代码：**
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    private static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;

        if (nums == null || len < 3)
            return ans;

        Arrays.sort(nums);  // 排序

        for (int i = 0; i < len; i++){
            if (nums[i] > 0)
                break;

            if (i > 0 && nums[i] == nums[i - 1])
                continue;  // 提前进入下一个循环，去重

            int left = i + 1, right = len - 1;
            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0){
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1])
                        left++;  // 去重
                    while (left < right && nums[right] == nums[right - 1])
                        right--;  // 去重

                    left++;
                    right--;
                }
                else if (sum < 0)
                    left++;
                else
                    right--;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[] {-1, 0, 1, 2, -1, -4}));
    }
}
```

**复杂度分析：**

- 时间复杂度$O(N^2)$
- 空间复杂度$O(1)$：指针使用常数大小的额外空间。


# 堆

## 23. 合并K个排序链表（***）

合并k个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。

**示例**：
```
输入:
[
  1->4->5,
  1->3->4,
  2->6
]
输出: 1->1->2->3->4->4->5->6
```

**思路与算法**：

思路一：K指针：K个指针分别指向K条链表。

思路二：
把链表**放入`堆`中**，然后由堆根据节点的`val`自动排好序：
<div align=center><img src=LeetCode\23.jpg width=80%></div>

这是一个`小根堆`，我们只需要每次**输出`堆顶`的元素**，直到整个堆为空即可。

执行过程如下：
<div align=center><img src=LeetCode\23_1.jpg></div>

**代码**：
```java
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 23. 合并K个排序链表（***）
 */

public class MergekSortedLists {
    private ListNode mergeKLists(ListNode[] listNodes){
        if (listNodes == null || listNodes.length == 0)
            return null;

        //创建一个堆，并设置元素的排序方式
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return (o1.val - o2.val);
            }
        });

        //遍历链表数组，然后将每个链表的每个节点都放入堆中
        for (int i = 0; i < listNodes.length; i++){
            while (listNodes[i] != null){
                queue.add(listNodes[i]);
                // 若采用题中示例，此处listNodes.length=3，有三个链表
                listNodes[i] = listNodes[i].next;
            }
        }

        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;

        //从堆中不断取出元素，并将取出的元素串联起来
        while (! queue.isEmpty()){
            dummy.next = queue.poll();
            dummy = dummy.next;
        }
        
        dummy.next = null;
        
        return head.next;
    }
}
```

# 深度优先搜索

## 100. 相同的树（*）

**问题：**
给定两个二叉树，编写一个函数来检验它们是否相同。如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。

**示例：**
```
输入:       1         1
          / \       / \
         2   3     2   3

        [1,2,3],   [1,2,3]

输出: true

输入:      1          1
          /           \
         2             2

        [1,2],     [1,null,2]

输出: false
```

**思路与算法：递归**

终止条件与返回值：

- 当两棵树的当前节点都为`null`时返回`true`
- 当其中一个为`null`另一个不为`null`时返回`false`
- 当两个都不为空但是值不相等时，返回`false`

执行过程：当满足终止条件时进行返回，不满足时分别判断左子树和右子树是否相同，其中要注意代码中的短路效应（当左子树不相等时，右子树不需要再进行判断！）


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

class SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null)
            return true;
        if(p == null || q == null)
            return false;
        if(p.val != q.val)
            return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);  // 短路效应
    }
}
```

**复杂度分析：**
时间复杂度：$O(n)$，$n$为树的节点个数
空间复杂度 : 最优情况（完全平衡二叉树）时为$O(\log(N))$，最坏情况下（完全不平衡二叉树）时为${O}(N)$，用于维护递归栈。

## 101. 对称二叉树(*)

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

## 104. 二叉树的最大深度(*)

**题目：**

给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

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

**思路与算法：递归，DFS(深度优先搜索)**
$H(1) = 1 + max(H(2), H(7))$
即$H(1) = 1 + max(H(1).left, H(1).right)$
<div align=center><img src=LeetCode\104.jpg></div>

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
class MaximumDepthofBinaryTree {
    public int maxDepth(TreeNode root) {
        if(root == null)
            return 0;
        else{
            int left_height = maxDepth(root.left);
            int right_height = maxDepth(root.right);
            return java.lang.Math.max(left_height, right_height) + 1;
        }
    }
}
```

**复杂度分析：**

时间复杂度：我们每个结点只访问一次，因此时间复杂度为$O(N)$，其中$N$是结点的数量。

空间复杂度：在最糟糕的情况下，树是完全不平衡的，例如每个结点只剩下左子结点，递归将会被调用$N$次（树的高度），因此保持调用栈的存储将是$O(N)$。但在最好的情况下（树是完全平衡的），树的高度将是$\log(N)$。因此，在这种情况下的空间复杂度将是$O(\log(N))$。


## 108. 将有序数组转换为二叉搜索树(*)

将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。

本题中，一个高度平衡二叉树是指一个二叉树**每个节点的左右两个子树的高度差的绝对值不超过1**。

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

**二叉搜索树的中序遍历是一个升序序列**。将有序数组作为输入，可以把该问题看做**根据中序遍历序列创建二叉搜索树**。

这个问题的答案唯一吗？例如：是否可以根据中序遍历序列和二叉搜索树之间是否一一对应，答案是否定的。

下面是一些关于`BST`（二叉查找树）的知识：
- 中序遍历不能唯一确定一棵二叉搜索树。
- 先序和后序遍历不能唯一确定一棵二叉搜索树。
- 先序/后序遍历和中序遍历的关系：`inorder = sorted(postorder) = sorted(preorder)`
- `中序+后序`、`中序+先序`可以唯一确定一棵二叉树。

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
        this.nums = nums;
        return helper(0, nums.length - 1);
    }

    int[] nums;

    // 方法helper(left, right)使用数组numsnums中索引从left到right的元素创建BST
    public TreeNode helper(int left, int right){
        if(left > right)
            return null;
        
        // always choose left middle node as a root
        int p = (left + right) / 2;

        /*
        // always choose right middle node as a root
        int p = (left + right) / 2;
        if ((left + right) % 2 == 1) ++p;
        */

        // inorder traversal: left -> node -> right
        TreeNode root = new TreeNode(nums[p]);
        root.left = helper(left, p - 1);
        root.right = helper(p + 1, right);
        return root;
    }
}
```

**复杂度分析：**

时间复杂度：$\mathcal{O}(N)$，每个元素只访问一次。

空间复杂度：$\mathcal{O}(N)$，二叉搜索树空间$\mathcal{O}(N)$，递归栈深度$\mathcal{O}(\log N)$。


# 贪心算法

求解**最优化问题**的算法通常需要经过一系列的步骤，在每个步骤都面临多种选择，对于许多最优问题，使用动态规划求最优解有些杀鸡用牛刀。可以使用更简单、更高效的算法。贪心算法（greedy algorithm）**在每一步都做出当时看起来最佳的选择**。即，其是局部最优的选择，寄希望这样的选择能导致全局最优解。

- “贪心算法” 和 “动态规划”、“回溯搜索” 算法一样，完成一件事情，是分步决策的；
- “贪心算法” 在每一步总是做出在当前看来最好的选择。
- 贪心算法和动态规划相比，**它既不看前面（也就是说它不需要从前面的状态转移过来），也不看后面（无后效性，后面的选择不会对前面的选择有影响）**，因此贪心算法时间复杂度一般是线性的，空间复杂度是常数级别的。


## 122. 买卖股票的最佳时机 II（*）

**题目：**

给定一个数组，它的第`i`个元素是一支给定股票第`i`天的价格。

设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。

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


## 392. 判断子序列（*）

**题目：**

给定字符串`s`和`t`，判断`s`是否为`t`的子序列。

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
把`s`的字符依次和`t`的字符匹配，**匹配到`s`就下移，无论是否匹配，`t`的下标都下移**，匹配完`s`，说明`s`是`t`的子序列，否则不是。

**代码：**
```java
class IsSubsequence {
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        // for循环与i无关
        for(char ch : s.toCharArray()){
            while(i < t.length() && t.charAt(i) != ch)
                i++;
            i++;
        }

        return i <= t.length() ? true : false;
    }
}
```

**后续挑战:**

如果有大量输入的`S`，称作`S1, S2, ... , Sk`其中`k >= 10亿`，你需要依次检查它们是否为 `T`的子序列。在这种情况下，你会怎样改变代码？

参考：https://leetcode-cn.com/problems/is-subsequence/solution/javati-jie-he-hou-xu-tiao-zhan-by-lil-q/


## 455. 分发饼干（*）

**题目：**
假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。对每个孩子`i`，都有一个胃口值`gi`，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干`j`，都有一个尺寸`sj`。如果`sj >= gi`，我们可以将这个饼干`j`分配给孩子`i`，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。

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

- 给一个孩子的饼干应当尽量小并且又能满足该孩子，这样大饼干才能拿来给满足度比较大的孩子。
- 因为满足度最小的孩子最容易得到满足，所以先满足满足度最小的孩子。


在以上的解法中，我们只在每次分配时饼干时选择一种看起来是当前最优的分配方法，但无法保证这种局部最优的分配方法最后能得到全局最优解。我们假设能得到全局最优解，并使用反证法进行证明，即假设存在一种比我们使用的贪心策略更优的最优策略。如果不存在这种最优策略，表示贪心策略就是最优策略，得到的解也就是全局最优解。

证明：假设在某次选择中，贪心策略选择给当前满足度最小的孩子分配第`m`个饼干，第`m`个饼干为可以满足该孩子的最小饼干。假设存在一种最优策略，可以给该孩子分配第`n`个饼干，并且`m < n`。我们可以发现，经过这一轮分配，贪心策略分配后剩下的饼干一定有一个比最优策略来得大。因此在后续的分配中，贪心策略一定能满足更多的孩子。也就是说不存在比贪心策略更优的策略，即贪心策略就是最优策略。

<div align=center><img src=LeetCode\455.gif></div>


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

# 数学

## 7. 整数反转

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
假设我们的环境只能存储得下32位的有符号整数，则其数值范围为$[−2^{31},  2^{31} − 1]$（$-2,147,483,648 - 2,147,483,647$）。请根据这个假设，如果反转后整数溢出那么就返回0。

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
- 当出现`ans == MAX_VALUE / 10`且`pop > 7`时，则一定溢出，7是$2^{31} - 1$的个位数。
  
从`ans * 10 + pop < MIN_VALUE`这个溢出条件来看：
- 当出现`ans < MIN_VALUE / 10`且**还有pop需要添加**时，则一定溢出；
- 当出现`ans == MIN_VALUE / 10`且`pop < -8`时，则一定溢出，8是$-2^{31}$的个位数。

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

# 设计

## 146.LRU缓存机制

`LRU`的全称是`Least Recently Used`，也就是说我们认为**最近使用过的数据应该是是有用的**，很久都没用过的数据应该是无用的，内存满了就优先删那些很久没用过的数据。

运用你所掌握的数据结构，设计和实现一个`LRU`(**最近最少使用**) 缓存机制。它应该支持以下操作：获取数据`get`和写入数据`put`。

获取数据`get(key)` 
- 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。

写入数据`put(key, value)`
- 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组`关键字/值`。**当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间**。

```
LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // 返回  1
cache.put(3, 3);    // 该操作会使得关键字 2 作废
cache.get(2);       // 返回 -1 (未找到)
cache.put(4, 4);    // 该操作会使得关键字 1 作废
cache.get(1);       // 返回 -1 (未找到)
cache.get(3);       // 返回  3
cache.get(4);       // 返回  4
```

**思路与算法：**

实现本题的两种操作，需要用到一个哈希表和一个双向链表。

在面试中，面试官一般会期望读者能够自己实现一个简单的双向链表，而不是使用语言自带的、封装好的数据结构。在`Python`语言中，有一种结合了哈希表与双向链表的数据结构`OrderedDict`，只需要短短的几行代码就可以完成本题。在`Java`语言中，同样有类似的数据结构`LinkedHashMap`。这些做法都不会符合面试官的要求，因此下面只给出使用封装好的数据结构实现的代码，而不多做任何阐述：

```java
class LRUCache extends LinkedHashMap<Integer, Integer>{
    private int capacity;
    
    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity; 
    }
}
```

LRU 缓存机制可以通过哈希表辅以双向链表实现，我们用一个哈希表和一个双向链表维护所有在缓存中的键值对。

**双向链表**按照**被使用的顺序**存储了这些**键值对**，**靠近头部**的键值对是**最近使用**的，而靠近尾部的键值对是最久未使用的。

**哈希表**即为普通的哈希映射（`HashMap`），通过缓存数据的**键**映射到其在双向链表中的位置。

这样一来，我们**首先使用哈希表进行定位**，找出缓存项在双向链表中的位置，随后**将其移动到双向链表的头部**，即可在$O(1)$的时间内完成`get`或者`put`操作。具体的方法如下：

- 对于`get`操作，首先判断`key`是否存在：
  - 如果`key`不存在，则返回`-1`；
  - 如果`key`存在，则`key`对应的节点是最近被使用的节点。通过哈希表定位到该节点在双向链表中的位置，并**将其移动到双向链表的头部**，最后返回该节点的值。

- 对于`put`操作，首先判断`key`是否存在：
  - 如果`key`不存在，**使用`key`和`value`创建一个新的节点，在双向链表的头部添加该节点，并将`key`和该节点添加进哈希表中**。然后判断双向链表的节点数是否超出容量，**如果超出容量，则删除双向链表的尾部节点，并删除哈希表中对应的项**；
  - 如果`key`存在，则与`get`操作类似，先通过哈希表定位，再将对应的节点的值**更新**为`value`，并**将该节点移到双向链表的头部**。

上述各项操作中，访问哈希表的时间复杂度为$O(1)$，在双向链表的头部添加节点、在双向链表的尾部删除节点的复杂度也为$O(1)$。而将一个节点移到双向链表的头部，可以分成「删除该节点」和「在双向链表的头部添加节点」两步操作，都可以在$O(1)$时间内完成。

小贴士：在双向链表的实现中，使用一个伪头部（`dummy head`）和伪尾部（`dummy tail`）标记界限，这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在。

<div align=center><img src=LeetCode\146.gif></div>

进阶:

你是否可以在 O(1) 时间复杂度内完成这两种操作？
