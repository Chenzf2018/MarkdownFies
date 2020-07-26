# 字符串

## 3. 无重复字符的最长子串(中等)

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

**思路：**

1. 遍历字符；
2. 以当前字符为起点，向其右侧遍历字符，如果在`HashSet`中不存在遍历到的字符，则存入`HashSet`中(避免重复)，直到遇到`HashSet`中存在的字符，记录截至位置`rk`；
3. 向右侧移动起点，重复步骤二。

**代码：**

注意：字符串的长度函数为`length()`：`(String s) s.length();`，`(String[] s) s.length;`

```java {.line-numbers highlight=24}
import java.util.Set;
import java.util.HashSet;

/**
 * leetcode_3_无重复字符的最长子串
 * @author Chenzf
 * @date 2020/7/10
 * @version 1.0
 */

public class LongestSubstringWithoutRepeatingCharacters {
    public static int longestSubstringWithoutRepeatCharacters(String s) {
        // 创建HashSet存入字符
        Set<Character> hashSet = new HashSet<>();

        int length = s.length();
        // 最长子串的截止位置
        int rk = 0;
        int result = 0;

        for (int i = 0; i < length; i++) {
            if (i != 0) {
                // 左指针右移一位，并移除当前字符的前一个字符
                // 移动窗口，并删除前一个字符
                hashSet.remove(s.charAt(i - 1));
            }

            while (rk < length && ! hashSet.contains(s.charAt(rk))) {
                hashSet.add(s.charAt(rk));
                rk++;
            }

            result = Math.max(result, ((rk - 1) - i) + 1);
        }

        return result;
    }

}
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Chenzf
 * @date 2020/7/10
 */

public class TestLongestSubstringWithoutRepeatingCharacter {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待检测字符串：");
            String string = reader.readLine();

            int result =
                    LongestSubstringWithoutRepeatingCharacters.longestSubstringWithoutRepeatCharacters(string);

            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(N)$，其中$N$是字符串的长度。左指针和右指针分别会遍历整个字符串一次。

- 空间复杂度：$O(|\Sigma|)$，其中$\Sigma$表示字符集（即字符串中可以出现的字符），$|\Sigma|$表示字符集的大小。在本题中没有明确说明字符集，因此可以默认为所有`ASCII`码在`[0, 128)`内的字符，即$|\Sigma| = 128$。我们需要**用到哈希集合来存储出现过的字符**，而字符最多有$|\Sigma|$个，因此空间复杂度为$O(|\Sigma|)$。


**使用map**
<div align=center><img src=LeetCode\3滑动窗口.png></div>


## 67. 二进制求和(简单)

**题目描述：**

给你两个二进制字符串，返回它们的和（用二进制表示）。输入为非空字符串且只包含数字1和0。

**示例：**

```
示例 1:

输入: a = "11", b = "1"
输出: "100"

示例 2:

输入: a = "1010", b = "1011"
输出: "10101"
 

提示：

1. 每个字符串仅由字符'0'或'1'组成。
2. 1 <= a.length, b.length <= 10^4
3. 字符串如果不是 "0" ，就都不含前导零。
```

**思路与算法：**

将两个字符串从右向左，连同进位，对应相加。会得到一个反向的字符，需要最后进行反转！
<div align=center><img src=LeetCode\67.jpg></div>

**代码实现：**

```java
/**
 * leetcode_67_二进制求和
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 */

public class AddBinary {
    public static String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        // 进位标志
        int carry = 0;
        // int sum = 0;

        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            // ans当前位的和
            int sum = carry;
            sum += (i >= 0 ? a.charAt(i) - '0' : 0);
            sum += (j >=0 ? b.charAt(j) - '0' : 0);
            ans.append(sum % 2);
            // carry /= 2;
            carry = sum / 2;
        }

        // 会多产生一个进位
        ans.append(carry == 1 ? carry : "");

        return ans.reverse().toString();
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

public class TestAddBinary {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入两个字符串：");
            String a = reader.readLine();
            String b = reader.readLine();

            System.out.println(AddBinary.addBinary(a, b));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 165. 比较版本号(中等)

**题目描述：**

比较两个版本号version1和version2。

如果`version1 > version2`返回1，如果`version1 < version2`返回-1， 除此之外返回0。

你可以假设版本字符串非空，并且只包含数字和`.`字符。`.`字符不代表小数点，而是用于分隔数字序列。

例如，2.5 不是“两个半”，也不是“差一半到三”，而是第二版中的第五个小版本。

你可以假设版本号的每一级的默认修订版号为0。例如，版本号3.4的第一级（大版本）和第二级（小版本）修订号分别为3和4。其第三级和第四级修订号均为0。

**示例：**

```
示例 1:
输入: version1 = "0.1", version2 = "1.1"
输出: -1

示例 2:
输入: version1 = "1.0.1", version2 = "1"
输出: 1

示例 3:
输入: version1 = "7.5.2.4", version2 = "7.5.3"
输出: -1

示例 4：
输入：version1 = "1.01", version2 = "1.001"
输出：0
解释：忽略前导零，“01” 和 “001” 表示相同的数字 “1”。

示例 5：
输入：version1 = "1.0", version2 = "1.0.0"
输出：0
解释：version1 没有第三级修订号，这意味着它的第三级修订号默认为 “0”。
```
提示：
- 版本字符串由以点`.`分隔的数字字符串组成。这个数字字符串可能有前导零。
- 版本字符串不以点开始或结束，并且其中不会有两个连续的点。

**思路与算法：**

**方法一：分割+解析，两次遍历，线性空间**

将两个字符串按点字符分割成块，然后逐个比较这些块：

<div align=center><img src=LeetCode\165.jpg></div>

如果两个版本号的块数相同，则可以有效工作。如果不同，则需要**在较短字符串末尾补充相应的`.0`块数使得块数相同**：

<div align=center><img src=LeetCode\165_1.jpg></div>

- 根据点分割两个字符串将分割的结果存储到数组中。
- 遍历较长数组并逐个比较块。如果其中一个数组结束了，实际上可以根据需要添加尽可能多的零，以继续与较长的数组进行比较。
    - 如果两个版本号不同，则返回1或-1。
- 版本号相同，返回0。

```java
package solution;

/**
 * leetcode_165_比较版本号
 * @author Chenzf
 * @date 2020/7/21
 * @version 1.0
 */

public class CompareVersionNumbers {
    public int compareVersion(String version1, String version2) {
        String[] nums1 = version1.split("\\.");
        String[] nums2 = version2.split("\\.");
        int length1 = nums1.length, length2 = nums2.length;

        int i1, i2;
        for (int i = 0; i < Math.max(length1, length2); i++) {
            i1 = (i < length1) ? Integer.parseInt(nums1[i]) : 0;
            i2 = (i < length2) ? Integer.parseInt(nums2[i]) : 0;

            if (i1 != i2) {
                return i1 > i2 ? 1 : -1;
            }
        }

        // 相等
        return 0;
    }
}
```

**复杂度分析**

- 时间复杂度：$\mathcal{O}(N + M + \max(N, M))$。其中$N$和$M$指的是输入字符串的长度。
- 空间复杂度：$\mathcal{O}(N + M)$，使用了两个数组nums1和nums2存储两个字符串的块。


**方法二：双指针，一次遍历，常数空间**

方法一有两个缺点：
- 是两次遍历的解决方法。
- 消耗线性空间。
- 
我们能否实现一个只有一次遍历和消耗常数空间的解决方法呢？

其思想是**在每个字符串上使用两个指针，跟踪每个数组的开始和结束**。

这样，可以并行地沿着两个字符串移动，检索并比较相应的块。一旦两个字符串都被解析，比较也就完成了。

**算法：**

首先，我们定义了一个名为`get_next_chunk(version, n, p)`的函数，用于检索字符串中的下一个块。这个函数有三个参数：输入字符串version，它的长度n，以及指针p为要检索块的第一个字符。它在指针p和下一个点之间返回一个整数块。为了帮助迭代，返回的是下一个块的第一个字符的指针。

下面是如何使用此函数解决问题的方法：

- 指针p1和p2分别指向version1和version2的起始位置：p1=p2=0。
- 并行遍历两个字符串。当`p1 < n1 or p2 < n2`：
    - 使用`get_next_chunk`函数获取version1和version2的下一个块i1和i2。
    - 比较i1和i2。如果不相同，则返回1或-1。
- 如果到了这里，说明版本号相同，则返回0。
  

下面实现`get_next_chunk(version, n, p)`函数：

- 块的开头由指针p标记。如果p设置为字符串的结尾，则字符串解析完成。若要继续比较，则在添加`.0`返回。
- 如果p不在字符串的末尾，则沿字符串移动指针p_end以查找**块的结尾**。
- 返回块`version.substring(p, p_end)`。

<div align=center><img src=LeetCode\165_2.jpg></div>

```java
package solution;

import javafx.util.Pair;

/**
 * leetcode_165_比较版本号
 * @author Chenzf
 * @date 2020/7/21
 * @version 2.0
 */

public class CompareVersionNumbers_v2 {
    public int compareVersion(String version1, String version2) {
        int point1 = 0, point2 = 0;
        int length1 = version1.length(), length2 = version2.length();

        int i1, i2;
        Pair<Integer, Integer> pair;
        while (point1 < length1 || point2 < length2) {
            pair = getNextChunk(version1, length1, point1);
            i1 = pair.getKey();
            point1 = pair.getValue();

            pair = getNextChunk(version2, length2, point2);
            i2 = pair.getKey();
            point2 = pair.getValue();

            if (i1 != i2) {
                return i1 > i2 ? 1 : -1;
            }
        }

        // 版本相等
        return 0;
    }

    /**
     * Map对象是key, value 可以多对
     *
     * Pair对象是 object, object 只能一对
     */
    public Pair<Integer, Integer> getNextChunk(String version, int StringLength, int point) {
        // if pointer is set to the end of string, return 0
        // i1 = pair.getKey(); point1 = pair.getValue();
        if (point > StringLength - 1) {
            return new Pair<>(0, point);
        }

        // find the end of chunk
        int i, pointEnd = point;
        while (pointEnd < StringLength && version.charAt(pointEnd) != '.') {
            pointEnd++;
        }

        //retrieve the chunk
        if (pointEnd != StringLength - 1) {
            i = Integer.parseInt(version.substring(point, pointEnd));
        } else {
            i = Integer.parseInt(version.substring(point, StringLength));
        }

        // find the beginning of next chunk
        point = pointEnd + 1;

        return new Pair(i, point);
    }
}
```

**复杂度分析：**

时间复杂度：$\mathcal{O}(\max(N, M))$。其中$N$和$M$指的是输入字符串的长度。
空间复杂度：$\mathcal{O}(1)$，**没有使用额外的数据结构**。





# 动态规划

## 5. 最长回文子串(中等)

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

**对于一个子串而言，如果它是回文串，并且长度大于2，那么将它首尾的两个字母去除之后，它仍然是个回文串**。

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

## 53. 最大子序和（简单）

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

**两遍哈希表**：

为了对运行时间复杂度进行优化，我们需要一种更有效的方法来**检查数组中是否存在目标元素**。如果存在，我们需要找出它的索引。保持数组中的每个**元素**与其**索引**相互对应的最好方法是什么？哈希表。

通过**以空间换取速度**的方式，我们可以将查找时间从$O(n)$降低到$O(1)$。哈希表正是为此目的而构建的，它支持**以近似恒定的时间进行快速查找**。用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到$O(n)$。但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为$O(1)$。

一个简单的实现使用了**两次迭代**。在**第一次迭代**中，我们**将每个元素的值和它的索引添加到表中**。然后，在**第二次迭代**中，我们将**检查**每个元素所对应的目标元素$(target - nums[i])$是否存在于表中。注意，**该目标元素不能是$nums[i]$本身**！


**代码：**
```java
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


## 128. 最长连续序列(困难)

**题目：**

给定一个未排序的整数数组，找出最长连续序列的长度。要求算法的时间复杂度为$O(n)$。

示例:
```
输入: [100, 4, 200, 1, 3, 2]
输出: 4
解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为4。
```

**思路与算法：**

我们考虑**枚举数组中的每个数$x$**，考虑**以其为起点**，不断尝试匹配$x+1, x+2, \cdots$是否存在，假设最长匹配到了$x+y$，那么以$x$为起点的最长连续序列即为$x, x+1, x+2, \cdots, x+y$其长度为$y+1$，我们**不断枚举并更新答案即可**。

对于匹配的过程，暴力的方法是$O(n)$遍历数组去看是否存在这个数，但其实更高效的方法是**用一个哈希表存储数组中的数**，这样**查看一个数是否存在即能优化至$O(1)$的时间复杂度**。

仅仅是这样我们的算法时间复杂度最坏情况下还是会达到$O(n^2)$（即外层需要枚举$O(n)$个数，内层需要暴力匹配$O(n)$次），无法满足题目的要求。

仔细分析这个过程，我们会发现其中**执行了很多不必要的枚举**，如果已知有一个$x, x+1, x+2, \cdots, x+y$的连续序列，而我们却重新从$x+1$，$x+2$或者是$x+y$处开始尝试匹配，那么得到的结果肯定不会优于**枚举$x$为起点**的答案，因此我们**在外层循环的时候碰到这种情况跳过即可**。

那么**怎么判断是否跳过**呢？

由于**我们要枚举的数$x$一定是在数组中不存在前驱数$x−1$的**，不然按照上面的分析我们会从$x−1$开始尝试匹配，因此我们**每次在哈希表中检查是否存在$x−1$即能判断是否需要跳过了**。


<div align=center><img src=LeetCode\128.jpg></div>

<div align=center><img src=LeetCode\128.gif></div>

- 使用Set去重，然后遍历数组；
- 去重数组中包含当前数的前驱数，则跳过；
- 如果当前数的前驱数不在数组中，则开始统计（**连续增加当前数并查询是否在数组中**）最长连续序列的个数。


**代码实现：**

```java
import java.util.HashSet;
import java.util.Set;

/**
 * leetcode_128 最长连续序列
 * @author Chenzf
 * @date 2020/7/7
 * @version 1.0
 */

public class LongestConsecutiveSequence {
    public int longestConsecutiveSequence(int[] nums) {
        // 使用num_set去重
        Set<Integer> num_set = new HashSet<>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            // 去重数组中包含当前数的前驱数，则跳过
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                // 寻找数组中有没有连续的数
                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}
```

**复杂度分析：**

时间复杂度：$O(n)$，其中$n$为数组的长度。

空间复杂度：$O(n)$。哈希表存储数组中所有的数需要$O(n)$的空间。


# 链表

```java
package solution;

/**
 * Definition for singly-linked list.
 */

public class ListNode {
    int val;
    ListNode next;
    
    ListNode() {}
    ListNode(int val) {
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
```

## 2. 两数相加(中等)

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

| 测试用例               |                        说明                        |
| :--------------------- | :------------------------------------------------: |
| $l1=[0,1]，l2=[0,1,2]$ |             当一个列表比另一个列表长时             |
| $l1=[]，l2=[0,1]$      |           当一个列表为空时，即出现空列表           |
| $l1=[9,9]，l2=[1]$     | 求和运算最后可能出现额外的进位，这一点很容易被遗忘 |

**代码：**

```java
package solution;

/**
 * leetcode_2_两数相加，与“leetcode_67_二进制求和”相似
 * @author Chenzf 
 * @date 2020/7/11
 * @version 1.0
 */

public class AddTwoNumbers {
    class ListNode {
        int val;
        ListNode next;

        ListNode (int val) {
            this.val =val;
        }

        ListNode (int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode listNode1, ListNode listNode2) {
        // 进位标志
        int carry = 0;
        ListNode dummyHead = new ListNode(0, null);
        ListNode currentNode = dummyHead;

        while (listNode1 != null || listNode2 != null) {
            int sum = carry;
            sum += (listNode1 != null ? listNode1.val : 0);
            sum += (listNode2 != null ? listNode2.val : 0);

            carry = sum / 10;
            currentNode.next = new ListNode(sum % 10);

            currentNode = currentNode.next;
            if (listNode1 != null) {
                listNode1 = listNode1.next;
            }
            if (listNode2 != null) {
                listNode2 = listNode2.next;
            }
        }

        // 两个链表都到达了尾端，此时进位值为1
        if (carry == 1) {
            currentNode.next = new ListNode(carry);
        }

        return dummyHead.next;
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
  - 不管我们将哪一个元素接在了后面，我们都需要把`prev`向后移一位。

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


## 25. K个一组翻转链表(困难)

**题目描述：**

给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。

k是一个正整数，它的值小于或等于链表的长度。如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。

 
```
示例：

给你这个链表：1->2->3->4->5

当 k = 2 时，应当返回: 2->1->4->3->5

当 k = 3 时，应当返回: 3->2->1->4->5
```
 

说明：
- 你的算法只能使用常数的额外空间。
- 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。


**思路与算法：**

https://leetcode-cn.com/problems/reverse-nodes-in-k-group/solution/tu-jie-kge-yi-zu-fan-zhuan-lian-biao-by-user7208t/

- 链表分区为**已翻转部分+待翻转部分+未翻转部分**
- 每次翻转前，要确定翻转链表的范围，这个必须通过k次循环来确定
- 需记录翻转链表前驱和后继，方便翻转完成后把已翻转部分和未翻转部分连接起来
- 初始需要两个变量pre和end，pre代表待翻转链表的前驱，end代表待翻转链表的末尾
- 经过k次循环，end到达末尾，记录待翻转链表的后继`next = end.next`
- 翻转链表，然后将三部分链表连接起来，重置pre和end指针，然后进入下一次循环
- 特殊情况，当翻转部分长度不足k时，在定位end完成后，`end==null`，已经到达末尾，说明已完成，直接返回即可

<div align=center><img src=LeetCode\25.png></div>

```java
package solution;

/**
 * leetcode_25. K个一组翻转链表
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0
 */

public class ReverseKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode end = dummy;

        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }

            ListNode start = prev.next;
            // 下一组的开始
            ListNode nextNode = end.next;
            // 一组结束
            end.next = null;
            // 此时prev指向反转后的链表头
            prev.next = reverse(start);
            
            start.next = nextNode;
            prev = start;
            // 此时end在下一组的开始
            end = prev;
        }
        
        return dummy.next;
    }

    /**
     * 链表反转leetcode_206
     */
    private ListNode reverse(ListNode head) {
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

**复杂度分析：**

- 时间复杂度为：$O(n*K)$；最好的情况为$O(n)$；最差的情况为$O(n^2)$
- 空间复杂度为：$O(1)$除了几个必须的节点指针外，没有占用其他空间。




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

## 4. 寻找两个正序数组的中位数（难）

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


## 31. 下一个排列(中等)

**题目描述：**

实现获取**下一个排列**的函数，算法需要将给定数字序列重新排列成**字典序**中**下一个更大的排列**。如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。

必须原地修改，只允许使用额外常数空间。

**示例：**
```
1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1
```

**字典序：**

[1, 2, 3]的字典序排列：

| 排列  | 123 | 132 | 213 | 231 | 312 | 321 |
| :---: | --- | --- | --- | --- | --- | --- |

字典序就是保持左边不变（变得最慢），右边依次从正序到逆序的排列过程。

**思路与算法：**

**暴力法：**

我们找出由给定数组的元素形成的列表的每个可能的排列，并找出比给定的排列更大的排列。

但是这个方法要求我们找出所有可能的排列，这需要很长时间，实施起来也很复杂。

复杂度分析：

时间复杂度：$O(n!)$，可能的排列总计有$n!$个。
空间复杂度：$O(n)$，因为数组将用于存储排列。

**一遍扫描：**

提示一：对于任何给定序列的**降序**，没有可能的下一个更大的排列：`[9, 5, 4, 3, 1]`。
提示二：<div align=center><img src=LeetCode\31.gif></div>

算法：
- 从数组的最后一位依次向前遍历，找到第一个满足$a[i]>a[i-1]$的数——$a[i-1]$(**找到第一个不满足“从右至左递增”的数**)；现在，对$a[i-1]$右侧的重新排列，不可能创建更大的排列，因为该子数组由数字按降序组成。
- 从$a[i-1]$开始向数组的右侧遍历，找到刚好比$a[i-1]$大的数$a[j]$(**$a[j]$右侧的数都比$a[i-1]$小，其左边的数都比$a[i-1]$大**)；
- 将$a[i-1]$右侧的数进行升序排序(**只需要反转，因为这些数从左至右是降序排序的**)。

<div align=center><img src=LeetCode\31.png></div>

**代码实现：**
`NextPermutation.java`

```java
/**
 * leetcode_31_下一个排列
 * @author Chenzf
 * @date 2020/7/9
 * @version 1.0
 */

public class NextPermutation {
    public static void nextPermutation(int[] nums) {
        // 从数组中index=nums.length-2的数开始向左遍历
        // 找到第一个不满足“从右至左升序排列”的数nums[i]
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }

        // 找到nums[i]右侧，最后一个比nums[i]大的数
        // 从右向左
        if (i >= 0) {
            int j = nums.length - 1;
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }

            // 交换nums[j]和nums[i]
            swap(nums, i, j);
        }

        // 将nums[i]右侧的数(已是降序排序)反转--最终按升序排序
        reverse(nums, i + 1);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 双指针，前后交换
     */
    private static void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
}
```

`TestNextPermutation.java`
```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Chenzf
 * @date 2020/7/9
 */

public class TestNextPermutation {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待处理数组：");
            String[] strings = reader.readLine().split(" ");
            int[] arr = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            NextPermutation.nextPermutation(arr);

            for (int num : arr) {
                System.out.print(num + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 215. 数组中的第K个最大元素(中等)

题目描述：
在未排序的数组中找到第k个最大的元素。请注意，你**需要找的是数组排序后的第k个最大的元素**，而不是第k个不同的元素。

示例：

```
示例 1:
输入: [3,2,1,5,6,4] 和 k = 2
输出: 5

示例 2:
输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
输出: 4
```

本题希望我们返回**数组排序之后**的**倒数第$k$个位置**。

**方法一：基于快速排序的选择方法**

快速排序是一个典型的分治算法。我们对数组$a[l \cdots r]$做快速排序的过程是：

- 分解：将数组$a[l \cdots r]$「划分」成两个子数组$a[l \cdots q - 1]$、$a[q + 1 \cdots r]$，使得$a[l \cdots q - 1]$中的每个元素小于等于$a[q]$，且$a[q]$小于等于$a[q + 1 \cdots r]$中的每个元素。其中，计算下标$q$也是「划分」过程的一部分。

- 解决：通过递归调用快速排序，对子数组$a[l \cdots q - 1]$和$a[q + 1 \cdots r]$进行排序。

- 合并：**因为子数组都是原址排序的，所以不需要进行合并操作**，$a[l \cdots r]$已经有序。

- 上文中提到的「划分」过程是：从子数组$a[l \cdots r]$中选择任意一个元素$x$作为主元，调整子数组的元素使得左边的元素都小于等于它，右边的元素都大于等于它，$x$的最终位置就是$q$。

由此可以发现**每次经过「划分」操作后，我们一定可以确定一个元素的最终位置，即$x$的最终位置为$q$，并且保证$a[l \cdots q - 1]$中的每个元素小于等于$a[q]$，且$a[q]$小于等于$a[q + 1 \cdots r]$中的每个元素。所以<font color=red>只要某次划分的$q$为倒数第$k$个下标的时候，我们就已经找到了答案</font>。我们只关心这一点，至于$a[l \cdots q - 1]$和$a[q+1 \cdots r]$是否是有序的，我们不关心**。

因此我们可以改进快速排序算法来解决这个问题：**在分解的过程当中，我们会对子数组进行划分，如果划分得到的$q$正好就是我们需要的下标，就直接返回$a[q]$；否则，如果$q$比目标下标小，就递归右子区间，否则递归左子区间。这样就可以把原来递归两个区间变成只递归一个区间，提高了时间效率**。这就是「快速选择」算法。

快速排序的性能和「划分」出的子数组的长度密切相关。直观地理解如果每次规模为$n$的问题我们都划分成$1$和$n - 1$，每次递归的时候又向$n - 1$的集合中递归，这种情况是最坏的，时间代价是$O(n ^ 2)$。我们可以引入随机化来加速这个过程，它的时间代价的期望是$O(n)$。


```java
package solution;

import java.util.Random;

/**
 * leetcode_215_数组中的第k个最大元素
 * @author Chenzf
 * @date 2020/7/23
 * @version 1.0 基于快速排序的选择方法
 */

public class KthLargestInArray {
    static Random random = new Random();

    public static int findKthLargest(int[] nums, int k) {
        // 返回数组排序之后的倒数第k个位置
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public static int quickSelect(int[] array, int left, int right, int index) {
        // 随机找一个分隔值
        int q = randomPartition(array, left, right);
        if (q == index) {
            return array[q];
        } else {
            return q < index ? quickSelect(array, q + 1, right, index) : quickSelect(array, left, q - 1, index);
        }
    }

    public static int randomPartition(int[] array, int left, int right) {
        // 随机选择一个范围内的数
        int i = random.nextInt(right - left + 1) + left;
        // 将找到的partition先与最后一位数交换
        swap(array, i, right);
        return partition(array, left, right);
    }

    public static int partition(int[] array, int left, int right) {
        // x为分隔值
        int x = array[right], i = left - 1;
        // 注意这里的j的最大值
        for (int j = left; j < right; j++) {
            if (array[j] <= x) {
                // 因为i是从left - 1开始，所以需++i
                swap(array, ++i, j);
            }
        }

        // 数组中从left到i的数都是小于分隔值的
        swap(array, i + 1, right);
        return i + 1;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
```

```java
package test;

import solution.KthLargestInArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * leetcode_215_数组中的第k个最大元素
 * @author Chenzf
 * @date 2020/7/23
 * @version 1.0 基于快速排序的选择方法
 */

public class TestKthLargestInArray {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("请输入待测数组：");
            String[] strings = reader.readLine().split(" ");
            int[] nums = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                nums[i] = Integer.parseInt(strings[i]);
            }

            System.out.println("请输入需要找第几个最大值：");
            int k = Integer.parseInt(reader.readLine());

            System.out.printf("数组中第%d个最大的值是：%d", k, KthLargestInArray.findKthLargest(nums, k));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```


**复杂度分析**

时间复杂度：$O(n)$。
空间复杂度：$O(\log n)$，递归使用栈空间的空间代价的期望为$O(\log n)$。


**方法二：基于堆排序的选择方法**

建立一个**大根堆**，做$k - 1$次删除操作后堆顶元素就是要找的答案。

在很多语言中，都有优先队列或者堆的的容器可以直接使用，但是在面试中，面试官更倾向于让更面试者自己实现一个堆。所以建议读者掌握这里大根堆的实现方法，在这道题中尤其要搞懂**建堆**、**调整**和**删除**的过程。


<div align=center><img src=LeetCode\215.jpg></div>

<div align=center><img src=LeetCode\215_1.jpg></div>

<div align=center><img src=LeetCode\215_2.jpg></div>

```java
package solution;

/**
 * leetcode_215_数组中的第k个最大元素
 * @author Chenzf
 * @date 2020/7/25
 * @version 2.0 基于堆排序的选择方法
 */

public class KthLargestInArray_v2 {
    public static int findKthLargest(int[] nums, int k) {
        // heapSize->lastIndex
        int heapSize = nums.length;
        // 创建堆
        buildMaxHeap(nums, heapSize);

        /*
        将堆的最大值与最后一个值交换后，形成树与有序部分
        欲找第k个最大元素，就是需要k次maxHeapify，k-1次将堆中最大值移至有序部分
         (nums.length - 1)-(k-1) + 1=nums.length - k + 1
         */
        for (int i = nums.length - 1; i >= nums.length - k + 1; i--) {
            // 交换k-1次
            swap(nums, 0, i);
            heapSize--;
            maxHeapify(nums, 0, heapSize);
        }
        // 第k个最大元素尚未交换
        return nums[0];
    }

    /**
     * 将数组转化为堆
     * 从最靠近数组末尾的第一个非叶子结点开始。
     * 这个非叶子结点在下标lastIndex/2处，因为它是树中的最后一个叶子结点的父结点。
     * 然后一直执行到heap[1]
     * @param heapSize -> lastIndex
     */
    private static void buildMaxHeap(int[] array, int heapSize) {
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapify(array, i, heapSize);
        }
    }

    /**
     * 将半堆转换为堆
     * 沿着从根到叶子结点的路径执行
     * @param heapSize -> lastIndex
     */
    private static void maxHeapify(int[] array, int index, int heapSize) {
        int leftIndex = index * 2 + 1, rightIndex = index * 2 + 2, largestIndex = index;
        if (leftIndex < heapSize && array[leftIndex] > array[largestIndex]) {
            largestIndex = leftIndex;
        }
        if (rightIndex < heapSize && array[rightIndex] > array[largestIndex]) {
            largestIndex = rightIndex;
        }
        if (largestIndex != index) {
            swap(array, index, largestIndex);
            maxHeapify(array, largestIndex, heapSize);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
```

**复杂度分析：**

- 时间复杂度：$O(n \log n)$，建堆的时间代价是$O(n)$，删除的总代价是$O(k \log n)$，因为 $k < n$，故渐进时间复杂为$O(n + k \log n) = O(n \log n)$。
- 空间复杂度：$O(\log n)$，即递归使用栈空间的空间代价。



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

**思路与算法：递归，DFS(深度优先搜索)**
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


# 广度优先算法

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


**思路与算法：**

BFS使用**队列**，把每个还没有搜索到的点依次放入队列，然后再弹出队列的头部元素当做当前遍历点。

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

`LRU`的全称是`Least Recently Used`，也就是说我们认为**最近使用过的数据应该是有用的**，很久都没用过的数据应该是无用的，**内存满了就优先删那些很久没用过的数据**。

**题目描述：**

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

实现本题的两种操作，需要用到一个**哈希表**和一个**双向链表**。

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

上述各项操作中，**访问哈希表的时间复杂度为**$O(1)$；**在双向链表的头部添加节点、在双向链表的尾部删除节点的复杂度也为**$O(1)$；而**将一个节点移到双向链表的头部**，可以分成**删除该节点和在双向链表的头部添加节点**两步操作，都可以**在$O(1)$时间内完成**。

小贴士：在双向链表的实现中，使用一个伪头部（`dummy head`）和伪尾部（`dummy tail`）标记界限，这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在。

<div align=center><img src=LeetCode\146.gif></div>

<div align=center><img src=LeetCode\LRU.jpg></div>


**代码：**
```java
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chenzf
 * @date 2020/7/7
 * @version 1.0
 *
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
class LRUCache {
    class DoubleLinkedNode {
        int key, value;
        DoubleLinkedNode prev, next;

        public DoubleLinkedNode() {}

        public DoubleLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * @ cache 哈希表存放缓存数据，并根据key定位对应数据在双向链表中的位置
     * @ size 双向链表的大小
     * @ capacity LRUCache的容量
     * @ head, tail 双向链表的伪头部和伪尾部
     */

    private Map<Integer, DoubleLinkedNode> cache = new HashMap<>();
    private int size;
    private int capacity;
    private DoubleLinkedNode head, tail;


    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 创建伪头部和伪尾部节点
        head = new DoubleLinkedNode();
        tail = new DoubleLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DoubleLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }

        // 如果key存在，则先通过哈希表定位，再删除结点并移至头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        // 根据哈希表定位数据在链表中的位置
        DoubleLinkedNode node = cache.get(key);

        // 如果结点不存在，则在链表头部创建新结点，并添加至哈希表
        if (node == null) {
            DoubleLinkedNode newNode = new DoubleLinkedNode(key, value);
            // 添加至哈希表
            cache.put(key, newNode);
            // 添加至双向链表头部
            addToHead(newNode);
            size++;

            // 如果超出容量，删除双向链表的尾部结点
            if (size > capacity) {
                DoubleLinkedNode lastNode = removeTail();
                size--;
                // 删除哈希表中对应的项
                cache.remove(lastNode.key);
            }

        } else {
            // 如果结点存在，则更新数据，并在链表中将其移至头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void moveToHead(DoubleLinkedNode node) {
        // 先删除结点
        removeNode(node);
        // 在头部添加结点
        addToHead(node);
    }

    private void removeNode(DoubleLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToHead(DoubleLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private DoubleLinkedNode removeTail() {
        // 找到欲删除的最后一个结点
        DoubleLinkedNode lastNode = tail.prev;
        // 删除该结点
        removeNode(lastNode);
        return lastNode;
    }
}
```

**复杂度分析：**

时间复杂度：对于`put`和`get`都是$O(1)$。

空间复杂度：$O(\text{capacity})$，因为哈希表和双向链表最多存储$\text{capacity} + 1$个元素。



# 树

```java
package solution;

/**
 * Definition for a binary tree node.
 */

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```

## 98. 验证二叉搜索树(中等)

**题目描述：**

给定一个二叉树，判断其是否是一个有效的**二叉搜索树**。

假设一个二叉搜索树具有如下特征：

- 节点的左子树只包含小于当前节点的数。
- 节点的右子树只包含大于当前节点的数。
- 所有左子树和右子树自身必须也是二叉搜索树。

**示例：**

```
输入:
    2
   / \
  1   3
输出: true


输入:
    5
   / \
  1   4
     / \
    3   6
输出: false

解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。
```

### 中序遍历

**二叉搜索树中序遍历得到的值构成的序列一定是升序的**，这启示我们在**中序遍历**的时候**实时检查当前节点的值是否大于前一个中序遍历到的节点的值**即可。如果均大于说明这个序列是升序的，整棵树是二叉搜索树，否则不是，下面的代码使用**栈**来模拟中序遍历的过程：

<div align=center><img src=LeetCode\98.gif width=80%></div>

```java {.line-numbers highlight=29-30}
package solution;

import java.util.Stack;

/**
 * leetcode_98_验证二叉搜索树
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 */

public class ValidateBinarySearchTree {
    public boolean isValidBST(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        // int inorder = - Integer.MAX_VALUE;
        // 需要一个很大的负数，防止第一个数满足if (node.val <= inorder)
        double inorder = - Double.MAX_VALUE;

        while (! stack.isEmpty() || node != null) {
            // 找到整棵树最左侧的结点
            while (node != null) {
                stack.push(node);
                // 左遍历
                node = node.left;
            }

            // 中遍历
            node = stack.pop();

            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            // 这里需要初始时inorder是一个很小的数
            if (node.val <= inorder) {
                return false;
            }
            
            inorder = node.val;
            // 右遍历
            node = node.right;
        }
        
        return true;
    }
}
```

**复杂度分析：**

时间复杂度：$O(n)$，其中$n$为二叉树的节点个数。二叉树的每个节点最多被访问一次，因此时间复杂度为$O(n)$。

空间复杂度：$O(n)$，其中$n$为二叉树的节点个数。栈最多存储$n$个节点，因此需要额外的$O(n)$ 的空间。


## 110. 平衡二叉树(简单)

**题目描述：**

给定一个二叉树，判断它是否是**高度平衡**的二叉树。

一棵高度平衡二叉树定义为：

一个二叉树每个节点的**左右两个子树的高度差的绝对值不超过1**。

**示例：**

```
示例 1:

给定二叉树 [3,9,20,null,null,15,7]

    3
   / \
  9  20
    /  \
   15   7
返回 true 。

示例 2:

给定二叉树 [1,2,2,3,3,null,null,4,4]

       1
      / \
     2   2
    / \
   3   3
  / \
 4   4
返回 false 。
```

**思路与算法：**

根据定义，一棵二叉树$T$存在节点$p\in T$，满足$|\texttt{height}(p.left) - \texttt{height}(p.right)| > 1$时，它是不平衡的。下图中每个节点的高度都被标记出来，高亮区域是一棵不平衡子树：

<div align=center><img src=LeetCode\110_1.png width=60%></div>

**自顶向下递归：**

定义方法$\texttt{height}$，用于计算任意一个节点$p\in T$的高度：

<div align=left><img src=LeetCode\110_2.jpg></div>

接下来就是比较每个节点左右子树的高度。在一棵以$r$为根节点的树$T$中，只有每个节点左右子树高度差不大于1时，该树才是平衡的。因此可以比较每个节点左右两棵子树的高度差，然后向上递归：

<div align=left><img src=LeetCode\110.gif></div>

**代码实现：**

```java
package solution;

/**
 * leetcode_110_平衡二叉树
 * @author Chenzf
 * @date 2020/7/12
 * @version 1.0
 */

public class BalancedBinaryTree {
    /**
     * Recursively obtain the height of a tree.
     * An empty tree has -1 height
     */
    private int height(TreeNode node) {
        // An empty tree has height -1
        if (node == null) {
            return -1;
        }

        return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isBalanced(TreeNode node) {
        // An empty tree satisfies the definition of a balanced tree
        if (node == null) {
            return true;
        }

        // Check if subtrees have height within 1. 
        // If they do, check if the subtrees are balanced
        return Math.abs(height(node.left) - height(node.right)) < 2
                && isBalanced(node.left) && isBalanced(node.right);
    }
}
```

**复杂度分析：**

- 时间复杂度：$\mathcal{O}(n\log n)$。
- 空间复杂度：$\mathcal{O}(n)$。


**自底向上的递归：**

自顶向下递归计算$\texttt{height}$存在大量冗余。每次调用$\texttt{height}$时，要同时计算其子树高度。但是自底向上计算，每个子树的高度只会计算一次。可以递归先计算当前节点的子节点高度，然后再通过子节点高度判断当前节点是否平衡，从而**消除冗余**。

首先判断子树是否平衡，然后比较子树高度判断父节点是否平衡。算法如下：

<div align=left><img src=LeetCode\110_1.gif></div>

```java
package solution;

/**
 * leetcode_110_平衡二叉树
 * @author Chenzf
 * @date 2020/7/12
 * @version 2.0 自底向上递归
 */

public class BalancedBinaryTree {
    public boolean isBalanced(TreeNode node) {
        return isBalancedTreeHelper(node).balanced;
    }

    /**
     * Return whether or not the tree at root is balanced while also storing
     * the tree's height in a reference variable.
     */
    private TreeInfo isBalancedTreeHelper(TreeNode node) {
        // An empty tree is balanced and has height = -1
        if (node == null) {
            return new TreeInfo(-1, true);
        }

        // Check subtrees to see if they are balanced.
        TreeInfo left = isBalancedTreeHelper(node.left);
        if (! left.balanced) {
            return new TreeInfo(-1, false);
        }

        TreeInfo right = isBalancedTreeHelper(node.right);
        if (! right.balanced) {
            return new TreeInfo(-1, false);
        }

        // Use the height obtained from the recursive calls to
        // determine if the current node is also balanced.
        if (Math.abs(left.height - right.height) < 2) {
            return new TreeInfo(Math.max(left.height, right.height) + 1, true);
        }
        
        return new TreeInfo(-1, false);
    }
}

/**
 * Utility class to store information from recursive calls
 */
final class TreeInfo {
    public final int height;
    public final boolean balanced;

    public TreeInfo(int height, boolean balanced) {
        this.height = height;
        this.balanced = balanced;
    }
}
```

**复杂度分析：**

- 时间复杂度：$\mathcal{O}(n)$，计算每棵子树的高度和判断平衡操作都在恒定时间内完成。
- 空间复杂度：$\mathcal{O}(n)$，如果树不平衡，递归栈可能达到$\mathcal{O}(n)$。


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


**思路与算法：**

- **方法一：递归**

递归遍历整棵二叉树，定义$f_x$**表示**$x$**节点的子树中是否包含$p$节点或$q$节点**，如果包含为`true`，否则为`false`。

那么符合条件的最近公共祖先$x$一定满足如下条件：
$(f_{\text{lson}}\ \&\&\ f_{\text{rson}})\ ||\ ((x\ =\ p\ ||\ x\ =\ q)\ \&\&\ (f_{\text{lson}}\ ||\ f_{\text{rson}}))$
其中$\text{lson}$和$\text{rson}$分别代表$x$节点的左孩子和右孩子。

- $f_{\text{lson}}\ \&\&\ f_{\text{rson}}$说明**左子树和右子树均包含$p$节点或$q$节点**，如果左子树包含的是$p$节点，那么右子树只能包含$q$节点，反之亦然。因为$p$节点和$q$节点都是不同且唯一的节点，因此如果满足这个判断条件即可说明$x$**就是我们要找的最近公共祖先**。

- $((x\ =\ p\ ||\ x\ =\ q)\ \&\&\ (f_{\text{lson}}\ ||\ f_{\text{rson}}))$考虑了**节点$x$恰好是$p$节点或$q$节点且它的左子树或右子树有一个包含了另一个节点**的情况，因此如果满足这个判断条件亦可说明$x$就是我们要找的最近公共祖先。

你可能会疑惑这样找出来的公共祖先深度是否是最大的。其实是最大的，因为我们是自底向上从叶子节点开始更新的，所以在所有满足条件的公共祖先中一定是深度最大的祖先先被访问到，且由于$f_x$本身的定义很巧妙，在找到最近公共祖先$x$以后，$f_x$按定义被设置为`true`，即假定了这个子树中只有一个$p$节点或$q$节点，因此其他公共祖先不会再被判断为符合条件。

下图展示了一个示例，搜索树中两个节点 9 和 11 的最近公共祖先。

https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/er-cha-shu-de-zui-jin-gong-gong-zu-xian-by-leetc-2/


- **方法二：存储父节点**

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









## 450. 删除二叉搜索树中的节点(中等)

**题目描述：**

给定一个二叉搜索树的根节点root和一个值key，删除二叉搜索树中的key对应的节点，并**保证二叉搜索树的性质不变**。返回二叉搜索树（有可能被更新）的根节点的引用。

**示例：**

```
root = [5,3,6,2,4,null,7]
key = 3

    5
   / \
  3   6
 / \   \
2   4   7

给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。

一个正确的答案是 [5,4,6,2,null,null,7], 如下图所示。

    5
   / \
  4   6
 /     \
2       7

另一个正确答案是 [5,2,6,null,4,null,7]。

    5
   / \
  2   6
   \   \
    4   7
```

### 二叉搜索树的三个特性

**二叉搜索树的中序遍历的序列是递增排序的序列**。中序遍历的遍历次序：`Left -> Node -> Right`

```java
public LinkedList<Integer> inorder(TreeNode root, LinkedList<Integer> arr) {
    if (root == null) return arr;
    inorder(root.left, arr);
    arr.add(root.val);
    inorder(root.right, arr);
    return arr;
} 
```

<div align=center><img src=LeetCode\450_1.jpg width=80%></div>

`Successor`代表的是中序遍历序列的下一个节点。即**比当前节点大的最小节点**，简称**后继节点**。 **先取当前节点的右节点，然后一直取该节点的左节点，直到左节点为空，则最后指向的节点为后继节点**。

```java
public int successor(TreeNode root) {
    root = root.right;
    while (root.left != null) root = root.left;
    return root;
} 
```

`Predecessor`代表的是中序遍历序列的前一个节点。即**比当前节点小的最大节点**，简称**前驱节点**。**先取当前节点的左节点，然后取该节点的右节点，直到右节点为空，则最后指向的节点为前驱节点**。

```java
public int predecessor(TreeNode root) {
    root = root.left;
    while (root.right != null) root = root.right;
    return root;
} 
```

<div align=center><img src=LeetCode\450_2.jpg width=80%></div>


**思路与算法：**

这里有三种可能的情况：

- 要删除的节点为**叶子节点**，可以直接删除：

<div align=center><img src=LeetCode\450_3.jpg width=80%></div>

- 要删除的几点不是叶子节点且**拥有右节点**，则**该节点可以由该节点的后继节点进行替代**，该后继节点位于右子树中较低的位置。然后可以从后继节点的位置递归向下操作以删除后继节点。

<div align=center><img src=LeetCode\450_4.jpg width=80%></div>

- 要删除的节点不是叶子节点，且**没有右节点但是有左节点**。这意味着它的后继节点在它的上面，但是我们并不想返回。我们可以**使用它的前驱节点进行替代**，然后再递归的向下删除前驱节点。

<div align=center><img src=LeetCode\450_5.jpg width=80%></div>


**算法：**

- 如果`key > root.val`，说明要删除的节点在右子树，`root.right = deleteNode(root.right, key)`。
- 如果`key < root.val`，说明要删除的节点在左子树，`root.left = deleteNode(root.left, key)`。
- 如果`key == root.val`，则该节点就是我们要删除的节点，则：
  - 如果该节点是叶子节点，则直接删除它：`root = null`。
  - 如果该节点**不是叶子节点且有右节点，则用它的后继节点的值替代**`root.val = successor.val`，然后删除后继节点。
  - 如果该节点不是叶子节点且**只有左节点**，则**用它的前驱节点的值替代**`root.val = predecessor.val`，然后删除前驱节点。
- 返回root。

<div align=center><img src=LeetCode\450_6.jpg width=80%></div>

**代码实现：**

```java
package solution;

/**
 * leetcode_450_删除二叉搜索树中的节点
 * @author Chenzf
 * @date 2020/7/12
 * @version 1.0
 */

public class DeleteNodeInBST {

    /**
     * 寻找当前节点的后继节点
     * One step right and then always left
     * @param node
     * @return
     */
    public int successor(TreeNode node) {
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }
        return node.val;
    }

    /**
     * 寻找当前节点的前驱节点
     * One step left and then always right
     * @param node
     * @return
     */
    public int predecessor(TreeNode node) {
        node = node.left;
        while (node.right != null) {
            node = node.right;
        }
        return node.val;
    }

    /**
     * 删除一个节点
     * @param node
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode node, int key) {
        if (node == null) {
            return null;
        }

        if (key > node.val) {
            // delete from the right subtree
            node.right = deleteNode(node.right, key);
        } else if (key < node.val) {
            // delete from the left subtree
            node.left = deleteNode(node.left, key);
        } else {
            // delete the current node

            // the node is a leaf
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.right != null) {
                // the node is not a leaf and has a right child
                node.val = successor(node);
                node.right = deleteNode(node.right, node.val);
            } else {
                // the node is not a leaf, has no right child, and has a left child
                node.val = predecessor(node);
                node.left = deleteNode(node.left, node.val);
            }
        }

        return node;
    }
}
```

**复杂度分析：**

- 时间复杂度：$\mathcal{O}(\log N)$。在算法的执行过程中，我们一直在树上向左或向右移动。首先先用$\mathcal{O}(H_1)$的时间找到要删除的节点，$H_1$值得是从根节点到要删除节点的高度。然后删除节点需要$\mathcal{O}(H_2)$的时间，$H_2$指的是从要删除节点到替换节点的高度。由于$\mathcal{O}(H_1 + H_2) = \mathcal{O}(H)$，$H$值得是树的高度，若树是一个平衡树则 $H = \log N$。
- 空间复杂度：$\mathcal{O}(H)$，递归时堆栈使用的空间，$H$是树的高度。




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


## 958. 二叉树的完全性检验(中等)

题目描述：给定一个二叉树，确定它是否是一个完全二叉树。

完全二叉树的定义如下：

若设二叉树的深度为h，**除第h层外，其它各层 (1～h-1) 的结点数都达到最大个数**，**第h层所有的结点都连续集中在最左边**，这就是完全二叉树。（注：第h层可能包含 $1-2^h$ 个节点。）

<div align=center><img src=LeetCode\958.png></div>

```
输入：[1,2,3,4,5,6]
输出：true
解释：最后一层前的每一层都是满的（即，结点值为 {1} 和 {2,3} 的两层），且最后一层中的所有结点（{4,5,6}）都尽可能地向左。
```
<div align=center><img src=LeetCode\958_1.png></div>

```
输入：[1,2,3,4,5,null,7]
输出：false
解释：值为 7 的结点没有尽可能靠向左侧。
```


### 广度优先搜索

这个问题可以简化成两个小问题：
- 用`(depth, position) 元组`表示**每个节点的“位置”**；
- 确定如何定义所有节点都是在最左边的。

假如我们在深度为3的行有4个节点，位置为`0，1，2，3`；那么就有8个深度为4的新节点位置在`0，1，2，3，4，5，6，7`。

所以我们可以找到规律：

对于一个节点，它的左孩子为：`(depth, position) -> (depth + 1, position * 2)`，右孩子为 `(depth, position) -> (depth + 1, position * 2 + 1)`。所以，**对于深度为d的行恰好含有$2^{d-1}$个节点，所有节点都是靠左边排列的当他们的位置编号是 0, 1, ... 且没有间隙**。

一个更简单的表示深度和位置的方法是：**用1表示根节点，对于任意一个节点v，它的左孩子为`2*v`，右孩子为`2*v + 1`。**

这就是我们用的规则，在这个规则下，一颗二叉树是完全二叉树当且仅当节点编号依次为`1, 2, 3,...`且没有间隙。

**算法：**

对于根节点，我们定义其编号为1。然后，对于每个节点v，我们将其左节点编号为`2*v`，将其右节点编号为`2 * v + 1`。

我们可以发现，树中所有节点的编号按照广度优先搜索顺序正好是升序。（也可以使用深度优先搜索，之后对序列排序）。

然后，我们检测编号序列是否为无间隔的 1, 2, 3, …，事实上，我们只需要检查最后一个编号是否正确，因为最后一个编号的值最大。




## 968. 监控二叉树(困难)

**题目描述：**

给定一个二叉树，我们在**树的节点上安装摄像头**。节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。计算监控树的所有节点所需的最小摄像头数量。

- 给定树的节点数的范围是[1, 1000]。
- 每个节点的值都是0。

**示例：**

<div align=center><img src=LeetCode\968_1.png></div>

```
输入：[0,0,null(第一个结点的右子结点),0,0]
输出：1
解释：如图所示，一台摄像头足以监控所有节点。
```

<div align=center><img src=LeetCode\968_2.png></div>

```
输入：[0,0,null,0,null,0,null,null,0]
输出：2
解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
```

### 动态规划

我们尝试覆盖每个节点，从树的顶部开始向下。所考虑的**每个节点都必须由该节点或某个邻居的摄像机覆盖**。

让`solve(node)`函数提供一些信息，说明在不同的状态下，需要多少摄像机才能覆盖此节点的子树。基本上有三种状态:

[状态 0]：森严的子树：该节点下的所有节点都被覆盖，但该节点没被覆盖。
[状态 1]：正常的子树：该节点下的所有节点和该节点均被覆盖，但是该节点没有摄像头。
[状态 2]：放置摄像头：该节点和子树均被覆盖，且该节点有摄像头。

一旦我们用这种方式来界定问题，答案就明显了：

若要满足森严的子树，此节点的孩子节点必须处于状态1。
若要满足正常的子树，此节点的孩子节点必须在状态1或2，其中至少有一个孩子在状态2。
若该节点放置了摄像头，则它的孩子节点可以在任一的状态。

### 贪心-未看懂

如果一个节点**有孩子节点**且**没有被摄像机覆盖**，则我们需要放置一个摄像机在该节点。此外，如果一个节点**没有父节点**且**没有被覆盖**，则必须放置一个摄像机在该节点。


**代码实现：**

```java
package solution;

import java.util.Set;
import java.util.HashSet;

/**
 * leetcode_968_监控二叉树：使用贪心算法
 * @author Chenzf
 * @date 2020/7/11
 * @version 1.0
 *
 *  Definition for a binary tree node.
 *  public class TreeNode {
 *      int val;
 *      TreeNode left;
 *      TreeNode right;
 *      TreeNode(int x) { val = x; }
 *   }
 */

public class BinaryTreeCameras_v1 {
    private int result;
    private Set<TreeNode> covered;

    public int minCameraCover(TreeNode root) {
        result = 0;
        covered = new HashSet<>();
        covered.add(null);

        deepFirstSearch(root, null);

        return result;
    }

    public void deepFirstSearch(TreeNode node, TreeNode parent) {
        if (node != null) {
            deepFirstSearch(node.left, node);
            deepFirstSearch(node.right, node);

            boolean needCovered = (parent == null) && ! covered.contains(node)
                    || ! covered.contains(node.left) || ! covered.contains(node.right);

            if (needCovered) {
                result++;
                covered.add(node);
                covered.add(parent);
                covered.add(node.left);
                covered.add(node.right);
            }
        }
    }
}
```