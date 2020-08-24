# 1. 两数之和(简单)

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

## HashMap

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



# 2. 两数相加(中等)

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
        ListNode dummyHead = new ListNode(0);
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



# 3. 无重复字符的最长子串(中等)

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

## 滑动窗口

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


# 4. 寻找两个正序数组的中位数(困难)--待做

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



# 5. 最长回文子串(中等)

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




# 7. 整数反转

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

# 10. 正则表达式匹配(困难)

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



# 11. 盛最多水的容器(中等)

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



# 15. 三数之和(中等)

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

https://leetcode-cn.com/problems/3sum/solution/hua-jie-suan-fa-15-san-shu-zhi-he-by-guanpengchn/

- 首先对数组进行排序，排序后固定一个数$nums[i]$，再使用左右指针指向$nums[i]$后面的两端，数字分别为$nums[L]$和$nums[R]$，计算三个数的和`sum`判断是否满足为`0`，满足则添加进结果集；如果$nums[i]$大于$0$，则三数之和必然无法等于$0$，结束循环；
- 如果$nums[i] == nums[i-1]$，则说明该数字重复，会导致结果重复，所以应该跳过；
    - 当$sum == 0$时，$nums[L] == nums[L+1]$则会导致结果重复，应该跳过，$L++$；$nums[R] == nums[R-1]$则会导致结果重复，应该跳过，$R--$。
    - 当$sum < 0$时，$L++$；
    - 当$sum > 0$时，$R--$

<div align=center><img src=LeetCode\15.jpg></div>

<div align=center><img src=LeetCode\15_1.jpg></div>

**代码：**
```java {.line-numbers highlight=26}
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



# 17. 电话号码的字母组合(中等)

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



# 19. 删除链表的倒数第N个节点(中等)

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



# 20. 有效的括号(简单)

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





# 21. 合并两个有序链表(简单)

**题目：**

将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

**示例：**

```
输入：1->2->4  1->3->4
输出：1->1->2->3->4->4
```

## 递归

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


## 迭代

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


# 23. 合并K个排序链表(困难)

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
<div align=center><img src=LeetCode\23.jpg width=50%></div>

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




# 25. K个一组翻转链表(困难)

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



# 26. *删除排序数组中的重复项(简单)

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


# 27. 移除元素(简单)

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


# 31. 下一个排列(中等)

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



# 53. 最大子序和(简单)

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



# 67. 二进制求和(简单)

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


# 83. 删除排序链表中的重复元素(简单)

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


# 84. 柱状图中最大的矩形(困难)

给定`n`个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为1。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

<div align=center><img src=LeetCode\84histogram.png></div>

以上是柱状图的示例，其中每个柱子的宽度为1，给定的高度为`[2,1,5,6,2,3]`。

<div align=center><img src=LeetCode\84histogram_area.png></div>

图中阴影部分为所能勾勒出的最大矩形面积，其面积为10个单位。




# 85. 最大矩形(困难)

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


# 86. 分隔链表(中等)


给定一个链表和一个特定值 x，对链表进行分隔，**使得所有小于 x 的节点都在大于或等于 x 的节点之前**。

你应当保留两个分区中每个节点的初始相对位置。

```
示例:
输入: head = 1->4->3->2->5->2, x = 3
输出: 1->2->2->4->3->5
```

## 双指针

本题要求我们改变链表结构，使得值小于x的元素，位于值大于等于x元素的前面。这实质上意味着在改变后的链表中有某个点，在该点之前的元素全部小于x ，该点之后的元素全部大于等于x。

我们将这个点记为JOINT：

<div align=center><img src=LeetCode\86.png></div>

如果我们在JOINT将改后链表拆分，我们会得到两个更小的链表，其中一个包括全部值小于x的元素，另一个包括全部值大于x的元素。在解法中，我们的主要目的是创建这两个链表，并将它们连接。

以用两个指针before和after来追踪上述的两个链表。两个指针可以用于分别创建两个链表，然后将这两个链表连接即可获得所需的链表。

1. 初始化两个指针before和after。我们将两个指针初始化为哑ListNode。这有助于减少条件判断。

<div align=center><img src=LeetCode\86_1.png width=50%></div>

2. 利用head指针遍历原链表。

3. 若head指针指向的元素值小于x，该节点应当是before链表的一部分。因此我们将其移到before中：

<div align=center><img src=LeetCode\86_2.png></div>

4. 否则，该节点应当是after链表的一部分。因此我们将其移到after中。

<div align=center><img src=LeetCode\86_3.png></div>

5. 遍历完原有链表的全部元素之后，我们得到了两个链表before和after。原有链表的元素或者before中或者在after中，这取决于它们的值。

<div align=center><img src=LeetCode\86_4.png></div>

由于我们从左到右遍历了原有链表，故**两个链表中元素的相对顺序不会发生变化**。另外值得注意的是，在图中我们完好地保留了原有链表。事实上，**在算法实现中，我们将节点从原有链表中移除，并将它们添加到别的链表中。我们没有使用任何额外的空间，只是将原有的链表元素进行移动**。

6. 现在，可以将before和after连接，组成所求的链表。

<div align=center><img src=LeetCode\86_5.png></div>

为了算法实现更容易，我们使用了哑结点初始化。不能让哑结点成为返回链表中的一部分，因此在组合两个链表时需要向前移动一个节点。


```java
package solution;

/**
 * leetcode_86_分隔链表
 * @author Chenzf
 * @date 2020/7/30
 * @version 1.0
 */

public class PartitionList {
    public ListNode partition(ListNode head, int partitionValue) {

        // beforeHead and afterHead are used to save the heads of the two lists.
        ListNode beforeHead = new ListNode(0);
        ListNode afterHead = new ListNode(0);

        // before and after are the two pointers used to create the two list
        ListNode before = beforeHead;
        ListNode after = afterHead;

        while (head != null) {
            // If the original list node is lesser than the given x, assign it to the before list.
            if (head.val < partitionValue) {
                before.next = head;
                before = before.next;
            } else {
                // If the original list node is greater or equal to the given x, assign it to the after list.
                after.next = head;
                after = after.next;
            }

            // move ahead in the original list
            head = head.next;
        }

        // Last node of "after" list would also be ending node of the reformed list
        after.next = null;

        // Once all the nodes are correctly assigned to the two lists,
        // combine them to form a single list which would be returned.
        before.next = afterHead.next;
        
        return beforeHead.next;
    }
}
```

**复杂度分析**

- 时间复杂度:$O(N)$，其中$N$是原链表的长度，我们对该链表进行了遍历。
- 空间复杂度:$O(1)$，我们没有申请任何新空间。值得注意的是，我们只移动了原有的结点，因此没有使用任何额外空间。




# 94. 二叉树的中序遍历(中等)

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

## 递归

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




## 迭代

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




# 98. 验证二叉搜索树(中等)

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




# 100. 相同的树（简单）

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



# 101. 对称二叉树(简单)

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



# 102. 二叉树的层序遍历(中等)

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


# 104. 二叉树的最大深度(简单)

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

## 递归，DFS(深度优先搜索)

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



# 105. 从前序与中序遍历序列构造二叉树(中等)

根据一棵树的前序遍历与中序遍历构造二叉树。

注意:你可以假设树中没有重复的元素。


```
前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
返回如下的二叉树：

    3
   / \
  9  20
    /  \
   15   7
```

## 递归

对于任意一颗树而言，前序遍历的形式总是：
`[ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]`
即**根节点总是前序遍历中的第一个节点**。

而中序遍历的形式总是：
`[ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]`

只要我们**在中序遍历中定位到根节点**，那么我们就**可以分别知道左子树和右子树中的节点数目**。

```
preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]

首先根据 preorder 找到根节点是 3；
    
然后根据根节点将 inorder 分成左子树和右子树：

左子树
inorder [9]

右子树
inorder [15,20,7]

把相应的前序遍历的数组也加进来：

左子树
inorder [9] -> preorder[9] 

右子树
preorder[20 15 7] -> inorder [15,20,7]

现在我们只需要构造左子树和右子树即可，成功把大问题化成了小问题。

然后重复上边的步骤继续划分preorder[20 15 7] 和 inorder [15,20,7]，直到 preorder 和 inorder 都为空，返回 null 即可。
```

**在中序遍历中对根节点进行定位时**，一种简单的方法是直接扫描整个中序遍历的结果并找出根节点，但这样做的时间复杂度较高。

我们可以考虑使用哈希映射（HashMap）来帮助我们快速地定位根节点。对于哈希映射中的每个键值对，**键表示一个元素（节点的值），值表示其在中序遍历中的出现位置**。

在构造二叉树的过程之前，我们可以对中序遍历的列表进行一遍扫描，就可以构造出这个哈希映射。在此后构造二叉树的过程中，我们就只需要$O(1)$的时间对根节点进行定位了。

<div align=center><img src=LeetCode\105.jpg></div>

```java
package solution;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode_105_从前序与后序遍历序列构造二叉树
 * @author Chenzf
 * @date 2020/7/29
 * @version 1.0 递归
 */

public class ConstructBinaryTree {
    // 便于在中序遍历中对根节点进行定位
    private Map<Integer, Integer> indexMap;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int numberOfNode = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<>();
        for (int i = 0; i < numberOfNode; i++) {
            indexMap.put(inorder[i], i);
        }

        return constructBinaryTree(preorder, inorder,
                0, numberOfNode - 1,
                0, numberOfNode - 1);
    }

    public TreeNode constructBinaryTree(int[] preorder, int[] inorder,
                                        int preorderLeft, int preorderRight,
                                        int inorderLeft, int inorderRight) {
        if (preorderLeft > preorderRight) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorderRootIndex = preorderLeft;
        // 在中序遍历中定位根节点
        int inorderRootIndex = indexMap.get(preorder[preorderRootIndex]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorderRootIndex]);

        // 得到左子树中的节点数目
        int sizeOfLeftSubtree = inorderRootIndex - inorderLeft;

        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素
        // 对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = constructBinaryTree(preorder, inorder,
                preorderLeft + 1, preorderLeft + sizeOfLeftSubtree,
                inorderLeft, inorderRootIndex - 1);

        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素
        // 对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = constructBinaryTree(preorder, inorder,
                preorderLeft + sizeOfLeftSubtree + 1, preorderRight,
                inorderRootIndex + 1, inorderRight);

        return root;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(n)$，其中$n$是树中的节点个数。

- 空间复杂度：$O(n)$，除去返回的答案需要的$O(n)$空间之外，我们还需要使用$O(n)$的空间**存储哈希映射**，以及$O(h)$（其中$h$是树的高度）的空间表示**递归时栈空间**。这里$h < n$，所以总空间复杂度为$O(n)$。





# 108. 将有序数组转换为二叉搜索树(简单)

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




# 110. 平衡二叉树(简单)

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


# 121. 买卖股票的最佳时机(简单)

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

**思路与算法：滑动窗口**

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


# 122. 买卖股票的最佳时机II(简单)

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


# 124. 二叉树中的最大路径和(困难)


给定一个非空二叉树，返回其最大路径和。

本题中，**路径被定义为一条从树中任意节点出发，达到任意节点的序列**。该路径至少包含一个节点，且不一定经过根节点。

```
示例 1:
输入: [1,2,3]

       1
      / \
     2   3

输出: 6

示例 2:
输入: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

输出: 42
```

## 递归

首先，考虑实现一个简化的函数`maxGain(node)`，该函数计算二叉树中的**一个节点的最大贡献值**——**在以该节点为根节点的子树中寻找<font color=red>以该节点为起点</font>的一条路径，使得该路径上的节点值之和最大**。

该函数的计算如下：
- 空节点的最大贡献值等于0。
- 非空节点的最大贡献值等于节点值与其子节点中的最大贡献值之和（对于叶节点而言，最大贡献值等于节点值）。

考虑如下二叉树：
```
   -10
   / \
  9  20
    /  \
   15   7
```
叶节点99、15、77的最大贡献值分别为99、15、77。

得到叶节点的最大贡献值之后，再计算非叶节点的最大贡献值：

节点$20$的最大贡献值等于$20+\max(15,7)=35$，节点$−10$的最大贡献值等于$-10+\max(9,35)=25$。

上述计算过程是递归的过程，因此，对根节点调用函数`maxGain`，即可得到每个节点的最大贡献值。

根据函数`maxGain`得到每个节点的最大贡献值之后，如何得到二叉树的最大路径和？

对于二叉树中的一个节点，**该节点的最大路径和**取决于**该节点的值与该节点的左右子节点的最大贡献值**，如果子节点的最大贡献值为正，则计入该节点的最大路径和，否则不计入该节点的最大路径和。

维护一个全局变量`maxSum`存储最大路径和，在递归过程中更新`maxSum`的值，最后得到的`maxSum`的值即为二叉树中的最大路径和。

<div align=center><img src=LeetCode\124.jpg></div>

```java
package solution;

/**
 * leetcode_124_二叉树中的最大路径和
 * @author Chenzf
 * @date 2020/7/29
 * @version 1.0
 */

public class MaxPathSum {
    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    public int maxGain(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 递归计算左右子节点的最大贡献值
        // 只有在最大贡献值大于 0 时，才会选取对应子节点
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);

        // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        int priceNewPath = node.val + leftGain + rightGain;

        // 更新结果
        maxSum = Math.max(maxSum, priceNewPath);

        // 返回节点的最大贡献值——注意节点最大贡献值的定义
        return node.val + Math.max(leftGain, rightGain);
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$是二叉树中的节点个数。对每个节点访问不超过$2$次。

- 空间复杂度：$O(N)$，其中$N$是二叉树中的节点个数。空间复杂度主要取决于递归调用层数，最大层数等于二叉树的高度，最坏情况下，二叉树的高度等于二叉树中的节点个数。




# 128. 最长连续序列(困难)

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

- 时间复杂度：$O(n)$，其中$n$为数组的长度。
- 空间复杂度：$O(n)$。哈希表存储数组中所有的数需要$O(n)$的空间。



# 141. *环形链表(简单)

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



# 144. 二叉树的前序遍历(中等)

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

- 如果按照`根节点 -> 左孩子 -> 右孩子`的方式遍历，即**先序遍历**：`1 2 4 5 3 6 7`；
- 如果按照`左孩子 -> 根节点 -> 右孩子`的方式遍历，即**中序遍历**：`4 2 5 1 6 3 7`；
- 如果按照`左孩子 -> 右孩子 -> 根节点`的方式遍历，即**后序遍历**：`4 5 2 6 7 3 1`；
- **层次遍历**就是按照每一层从左向右的方式进行遍历：`1 2 3 4 5 6 7`。

## 递归

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

public class PreorderTraversal_v1 {
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
            if (node.left != null) {
                preorderTraversal(node.left, result);
            }
            if (node.right != null) {
                preorderTraversal(node.right, result);
            }
        }
    }
}
```

## 迭代

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



# 145. 二叉树的后序遍历(困难)

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

## 递归

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

## 迭代

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




# 146. LRU缓存机制

`LRU`的全称是`Least Recently Used`，也就是说我们认为**最近使用过的数据应该是有用的**，很久都没用过的数据应该是无用的，**内存满了就优先删那些很久没用过的数据**。

**题目描述：**

运用你所掌握的数据结构，设计和实现一个`LRU`(**最近最少使用**)缓存机制。它应该支持以下操作：获取数据`get`和写入数据`put`。

获取数据`get(key)` 
- 如果关键字(key)存在于缓存中，则获取关键字的值(总是正数)，否则返回-1。

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

LRU缓存机制可以通过**哈希表**辅以**双向链表**实现，我们用一个哈希表和一个双向链表维护所有在缓存中的键值对。

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



# 151. 翻转字符串里的单词(中等)

给定一个字符串，逐个翻转字符串中的每个单词。

```
示例 1：
输入: "the sky is blue"
输出: "blue is sky the"

示例 2：
输入: "  hello world!  "
输出: "world! hello"

解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。

示例 3：
输入: "a good   example"
输出: "example good a"
解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
```

## 双端队列

由于双端队列支持从队列头部插入的方法，因此我们可以沿着字符串一个一个单词处理，然后**将单词压入队列的头部**，再将队列转成字符串即可。

<div align=center><img src=LeetCode\151.png width=80%></div>

```java
package solution;

import java.util.ArrayDeque;
import java.util.Deque;

public class ReverseWords {
    public String reverseWords(String string) {
        int left = 0, right = string.length() - 1;

        // 去掉字符串开头的空白字符
        while (left <= right && string.charAt(left) == ' ') {
            left++;
        }

        // 去掉字符串末尾的空白字符
        while (left <= right && string.charAt(right) == ' ') {
            right--;
        }

        Deque<String> deque = new ArrayDeque<>();
        StringBuilder word = new StringBuilder();

        while (left <= right) {
            char ch = string.charAt(left);
            if ((word.length() != 0) && (ch == ' ')) {
                // 将单词 push 到队列的头部
                deque.offerFirst(word.toString());
                word.setLength(0);
            } else if (ch != ' ') {
                word.append(ch);
            }
            left++;
        }

        // 将最后一个单词压入队列的头部
        deque.offerFirst(word.toString());

        // 将队列转成字符串
        return String.join(" ", deque);
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$为输入字符串的长度。
- 空间复杂度：$O(N)$，双端队列存储单词需要$O(N)$的空间。




# 155. 最小栈(简单)

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




# 160. 相交链表

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


# 165. 比较版本号(中等)

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



# 167. 两数之和 II - 输入有序数组(简单)

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


# 189. 旋转数组(简单)

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

# 199. 二叉树的右视图(中等)


给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。

```
输入: [1,2,3,null,5,null,4]
输出: [1, 3, 4]
解释:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
```

**思路与算法：**

<div align=center><img src=LeetCode\199.png width=80%></div>

## 方法一：广度优先搜索

利用BFS进行层次遍历，记录下每层的最后一个元素。

```java {.line-numbers highlight=40-43}
package solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * leetcode_199_二叉树的右视图
 * @author Chenzf
 * @date 2020/7/29
 * @version 2.0 广度优先
 */

public class RightSideView_v2 {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (! queue.isEmpty()) {
            // 当前层节点个数
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                // 将node的子树推入队列
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }

                // 将当前层的最后一个节点放入结果列表
                if (i == size - 1) {
                    result.add(node.val);
                }
            }
        }

        return result;
    }

}
```

- 时间复杂度：$O(N)$，每个节点都入队出队了1次。
- 空间复杂度：$O(N)$，使用了额外的队列空间。

## 方法二：深度优先搜索

对树进行深度优先搜索，在搜索过程中，我们**总是先访问右子树**。那么**对于每一层来说，我们在这层见到的第一个结点一定是最右边的结点**。


```java
package solution;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode_199_二叉树的右视图
 * @author Chenzf
 * @date 2020/7/29
 * @version 1.0 深度优先
 */

public class RightSideView {
    List<Integer> result = new ArrayList<>();

    public List<Integer> rightSideView(TreeNode root) {
        // 从根节点开始访问，根节点深度是0
        deepFirstSearch(root, 0);
        return result;
    }

    private void deepFirstSearch(TreeNode node, int depth) {
        if (node == null) {
            return;
        }

        // 先访问当前节点，再递归地访问右子树和左子树
        // 如果当前节点所在深度还没有出现在res里，说明在该深度下当前节点是第一个被访问的节点
        // 因此将当前节点加入res中
        // 只要在result中这层添加过数了，就一定是最右侧的数
        if (depth == result.size()) {
            result.add(node.val);
        }

        depth++;
        deepFirstSearch(node.right, depth);
        deepFirstSearch(node.left, depth);
    }
}
```

- 时间复杂度：$O(N)$，**每个节点都访问了1次**。
- 空间复杂度：$O(N)$，因为这不是一棵平衡二叉树，二叉树的深度最少是$logN$，最坏的情况下会退化成一条链表，深度就是$N$，因此**递归时使用的栈空间是$O(N)$的**。


# 203. 移除链表元素(简单)


删除链表中等于给定值val的所有节点。

```
示例:

输入: 1->2->6->3->4->5->6, val = 6
输出: 1->2->3->4->5
```


## 双指针

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


# 206. 反转链表(简单)

**题目：**

反转一个单链表。

```
输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
```

**思路与算法：**

## 双指针迭代
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


## 递归

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



# 215. 数组中的第K个最大元素(中等)

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

## 方法一：基于快速排序的选择方法

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


## 方法二：基于堆排序的选择方法

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




# 225. 用队列实现栈(简单)

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




# 232. 用栈实现队列(简单)

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

为了满足队列的`FIFO`的特性，我们需要用到**两个栈**，用它们其中一个来反转元素的入队顺序，用另一个来存储元素的最终顺序。

## 方法一
**使用两个栈入队 - $O(n)$，出队 - $O(1)$**

- **入队**（push）：

一个队列是`FIFO`的，但一个栈是`LIFO`的。这就意味着**最新压入的元素必须得放在栈底**。为了实现这个目的，我们首先需要把`s1`中所有的元素移到`s2`中，接着把新元素压入`s2`。最后把`s2`中所有的元素弹出，再把弹出的元素压入`s1`。

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


- **出队**（pop）：

直接从s1弹出就可以了，因为s1的栈顶元素就是队列的队首元素。同时我们把弹出之后s1的栈顶元素赋值给代表队首元素的front变量。

<div align=center><img src=LeetCode\232_3.png></div>

```java
// Removes the element from the front of queue.
public void pop() {
    s1.pop();
    if (!s1.empty())
        front = s1.peek();
}
```

复杂度分析：

- 时间复杂度：$O(1)$
- 空间复杂度：$O(1)$


- **判断空(empty)**

s1存储了队列所有的元素，所以只需要检查s1的是否为空就可以了。

```java
// Return whether the queue is empty.
public boolean empty() {
    return s1.isEmpty();
}
```
时间复杂度：$O(1)$
空间复杂度：$O(1)$


- **取队首元素(peek)**

在我们的算法中，用了`front`变量来存储队首元素，在每次入队操作或者出队操作之后这个变量都会随之更新。

```java
// Get the front element.
public int peek() {
    return front;
}
```

时间复杂度：$O(1)$。队首元素`front`已经被提前计算出来了，同时也只有`peek`操作可以得到它的值。
空间复杂度：$O(1)$


## 方法二
**使用两个栈入队 - $O(1)$，出队 - 摊还复杂度$O(1)$**

- **入队(push)**

**新元素总是压入s1的栈顶**，同时我们会把s1中压入的第一个元素赋值给作为队首元素的front变量。

<div align=center><img src=LeetCode\232_4.png></div>

```java
private Stack<Integer> s1 = new Stack<>();
private Stack<Integer> s2 = new Stack<>();

// Push element x to the back of queue.
public void push(int x) {
    if (s1.empty())
        front = x;
    s1.push(x);
}
```

复杂度分析：

时间复杂度：$O(1)$，向栈压入元素的时间复杂度为$O(1)$

空间复杂度：$O(n)$，需要额外的内存来存储队列元素

- **出队**（pop）：

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

复杂度分析：

时间复杂度：摊还复杂度$O(1)$，最坏情况下的时间复杂度$O(n)$
    在最坏情况下，s2为空，算法需要从s1中弹出n个元素，然后再把这n个元素压入s2，在这里n代表队列的大小。这个过程产生了2n步操作，时间复杂度为$O(n)$。但当s2非空时，算法就只有$O(1)$的时间复杂度。

空间复杂度：$O(1)$

- **判断空(empty)**

s1和s2都存有队列的元素，所以只需要检查s1和s2是否都为空就可以了。

```java
// Return whether the queue is empty.
public boolean empty() {
    return s1.isEmpty() && s2.isEmpty();
}
```
时间复杂度：$O(1)$
空间复杂度：$O(1)$


- **取队首元素(peek)**

我们定义了front变量来保存队首元素，每次入队操作我们都会随之更新这个变量。当s2为空，front变量就是对首元素，当s2非空，s2的栈顶元素就是队首元素。

```java
// Get the front element.
public int peek() {
    if (!s2.isEmpty()) {
        return s2.peek();
    }
    return front;
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


# 234. 回文链表(简单)

请判断一个链表是否为回文链表。

```
示例 1:
输入: 1->2
输出: false

示例 2:
输入: 1->2->2->1
输出: true
```

## 将值复制到数组中后用双指针法

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


## 将链表的后半部分反转

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






# 236. 二叉树的最近公共祖先(中等)

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



# 237. 删除链表中的节点(简单)

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





# 283. 移动零(简单)

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



# 344. 反转字符串(简单)

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

双指针法是使用两个指针，一个左指针left，右指针right，开始工作时left指向首元素，right指向尾元素。交换两个指针指向的元素，并向中间移动，直到两个指针相遇。


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



# 345. 反转字符串中的元音字母(简单)

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



# 349. 两个数组的交集(简单)

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



# 350. 两个数组的交集 II(简单)


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




# 392. 判断子序列(简单)

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



# 405. 数字转换为十六进制数(简单)

给定一个整数，编写一个算法将这个数转换为十六进制数。对于负整数，我们通常使用 补码运算 方法。

注意:
- 十六进制中所有字母(a-f)都必须是小写。
- 十六进制字符串中不能包含多余的前导零。如果要转化的数为0，那么以单个字符'0'来表示；对于其他情况，十六进制字符串中的第一个字符将不会是0字符。 
- 给定的数确保在32位有符号整数范围内。
- 不能使用任何由库提供的将数字直接转换或格式化为十六进制的方法。

```
示例 1：
输入:
26
输出:
"1a"

示例 2：
输入:
-1
输出:
"ffffffff"
```

## 移位

每4位二进制数可以转换为1位16进制数，转换关系如下：

<div align=center><img src=LeetCode\405.png></div>

`0111 1011B=7BH //B代表二进制结尾，H代表十六进制结尾`

只要循环地将数**num的末尾4位**和1111做**与运算**就可以得到相应的二进制数：

```java {.line-numbers highlight=10}
class Solution {
    public String toHex(int num) {
        if(num == 0){
            return "0";
        }
        char[] chrs = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        String res="";
        while(num != 0){
            int temp = num & 15;//十六进制的1111刚好对应十进制的15
            res = chrs[temp] + res;
            num >>>= 4;//注意这里是逻辑移位，有三个大于号
        }
        return res;
    }  
}
```

```java
package solution;

/**
 * leetcode_405_数字转换成十六进制数
 * @author Chenzf 
 * @date 2020/7/30
 * @version 1.0
 */

public class ConvertNumberToHexadecimal {
    public String toHex(int num) {
        if (num == 0) {
            return "0";
        }

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = "0123456789abcdef".toCharArray();

        while (num != 0) {
            // 十六进制的1111刚好对应十进制的15
            int temp = num & 15;
            stringBuilder.append(chars[temp]);
            num >>>= 4;
        }

        return stringBuilder.reverse().toString();
    }
}
```



# 450. 删除二叉搜索树中的节点(中等)

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

## 二叉搜索树的三个特性

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



# 455. 分发饼干(简单)

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



# 496. 下一个更大元素I(简单)

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

## 单调栈

我们可以忽略数组`nums1`，**先将`nums2`中的每一个元素，求出其下一个更大的元素。随后将这些答案放入哈希映射（HashMap）中，再遍历数组`nums1`，并直接找出答案**。对于`nums2`，我们可以使用**单调栈**来解决这个问题。

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


# 503. 下一个更大元素II(中等)

https://leetcode-cn.com/problems/next-greater-element-ii/solution/xia-yi-ge-geng-da-yuan-su-ii-by-leetcode/

给定一个**循环数组**（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。

```
示例 1:
输入: [1,2,1]
输出: [2,-1,2]

解释: 第一个1的下一个更大的数是2；数字2找不到下一个更大的数；第二个1的下一个最大的数需要循环搜索，结果也是2。

注意: 输入数组的长度不会超过10000。
```

## 单调栈

我们首先把第一个元素$A[1]$放入栈，随后对于第二个元素$A[2]$，如果$A[2] > A[1]$，那么我们就找到了$A[1]$的下一个更大元素$A[2]$，此时就可以把$A[1]$出栈并把$A[2]$入栈；如果$A[2] <= A[1]$，我们就把$A[2]$入栈。对于第三个元素$A[3]$，此时栈中有若干个元素，那么所有比$A[3]$小的元素都找到了下一个更大元素（即$A[3]$），因此可以出栈，在这之后，我们将$A[3]$入栈，以此类推。

可以发现，我们维护了一个单调栈，栈中的元素从栈顶到栈底是单调不降的。当我们遇到一个新的元素$A[i]$时，我们判断栈顶元素是否小于$A[i]$，如果是，那么栈顶元素的下一个更大元素即为$A[i]$，我们将栈顶元素出栈。重复这一操作，直到栈为空或者栈顶元素大于$A[i]$。此时我们将$A[i]$入栈，保持栈的单调性，并对接下来的$A[i + 1], A[i + 2] ...$执行同样的操作。

由于这道题的数组是循环数组，因此我们需要**将每个元素都入栈两次**。这样可能会有元素出栈找过一次，即得到了超过一个“下一个更大元素”，我们只需要保留第一个出栈的结果即可。

```java
package solution;

import java.util.Stack;

/**
 * leetcode_503_下一个更大元素
 * @author Chenzf
 * @date 2020/7/30
 * @version 1.0
 */

public class NextGreaterElement {
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 2 * nums.length - 1; i >= 0; i--) {
            while (! stack.empty() && nums[stack.peek()] <= nums[i % nums.length]) {
                stack.pop();
            }

            result[i % nums.length] = stack.empty() ? -1 : nums[stack.peek()];
            stack.push(i % nums.length);
        }

        return result;
    }
}
```




# 543. 二叉树的直径(简单)

给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是**任意两个结点路径长度中的最大值**。这条路径可能穿过也可能不穿过根结点。

```
给定二叉树

          1
         / \
        2   3
       / \     
      4   5    
返回3，它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。

注意：两结点之间的路径长度是以它们之间边的数目表示。
```

## 深度优先搜索

一条路径的长度为**该路径经过的节点数减一**，所以求直径（即求路径长度的最大值）等效于求**路径经过节点数的最大值减一**。

而任意一条路径均可以被看作由某个节点为起点，从其左儿子和右儿子向下遍历的路径拼接得到。

<div align=center><img src=LeetCode\543.jpg width=20%></div>

如图我们可以知道**路径[9, 4, 2, 5, 7, 8] 可以被看作以2为起点，从其左儿子向下遍历的路径[2, 4, 9]和从其右儿子向下遍历的路径[2, 5, 7, 8]拼接得到**。

假设我们知道对于该节点的左儿子向下遍历经过最多的节点数$L$（即**以左儿子为根的子树的深度**）和其右儿子向下遍历经过最多的节点数$R$（即**以右儿子为根的子树的深度**），那么以该节点为起点的路径经过节点数的最大值即为$L+R+1$。

我们记节点$\textit{node}$为起点的路径经过节点数的最大值为$d_{\textit{node}}$，那么二叉树的直径就是所有节点$d_{\textit{node}}$的最大值减一。

算法流程为：定义一个递归函数`depth(node)`计算$d_{\textit{node}}$，函数返回该节点为根的子树的深度。先递归调用左儿子和右儿子求得它们为根的子树的深度$L$和$R$，则该节点为根的子树的深度即为$max(L,R)+1$，该节点的$d_{\textit{node}}$值为$L+R+1$。

递归搜索每个节点并设一个全局变量`ans`记录$d_\textit{node}$的最大值，最后返回`ans-1`即为树的直径。

```java
package solution;

/**
 * leetcode_543_二叉树的直径
 * @author Chenzf
 * @date 2020/7/26
 * @version 1.0
 */

public class DiameterOfBinaryTree {
    int result;

    public int diameterOfBinaryTree(TreeNode root) {
        result = 1;
        depthOfNode(root);
        return result - 1;
    }

    public int depthOfNode(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftDepth = depthOfNode(node.left);
        int rightDepth = depthOfNode(node.right);
        result = Math.max(result, leftDepth + rightDepth + 1);

        // 返回以该节点为根的子树的深度
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
```

**复杂度分析**

- 时间复杂度：$O(N)$，其中$N$为二叉树的节点数，即遍历一棵二叉树的时间复杂度，**每个结点只被访问一次**。

- 空间复杂度：$O(Height)$，其中$Height$为二叉树的高度。由于**递归函数在递归过程中需要为每一层递归函数分配栈空间**，所以这里需要额外的空间且**该空间取决于递归的深度**，而**递归的深度显然为二叉树的高度**，并且每次递归调用的函数里又只用了常数个变量，所以所需空间复杂度为$O(Height)$。



# 682. 棒球比赛(简单)

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




# 700. 二叉搜索树中的搜索(简单)

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



# 771. 宝石与石头(简单)

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



# 818. 赛车(困难)

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




# 844. 比较含退格的字符串(简单)

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


# 860. 柠檬水找零(简单)

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



# 876. 链表的中间结点(简单)

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



# 944. 删列造序(简单)

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


# 958. 二叉树的完全性检验(中等)--待做

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


## 广度优先搜索

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



# 968. 监控二叉树(困难)

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

## 动态规划

我们尝试覆盖每个节点，从树的顶部开始向下。所考虑的**每个节点都必须由该节点或某个邻居的摄像机覆盖**。

让`solve(node)`函数提供一些信息，说明在不同的状态下，需要多少摄像机才能覆盖此节点的子树。基本上有三种状态:

[状态 0]：森严的子树：该节点下的所有节点都被覆盖，但该节点没被覆盖。
[状态 1]：正常的子树：该节点下的所有节点和该节点均被覆盖，但是该节点没有摄像头。
[状态 2]：放置摄像头：该节点和子树均被覆盖，且该节点有摄像头。

一旦我们用这种方式来界定问题，答案就明显了：

若要满足森严的子树，此节点的孩子节点必须处于状态1。
若要满足正常的子树，此节点的孩子节点必须在状态1或2，其中至少有一个孩子在状态2。
若该节点放置了摄像头，则它的孩子节点可以在任一的状态。

## 贪心-未看懂

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

# 977. 有序数组的平方(简单)

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


# 1005. *K次取反后最大化的数组和(简单)

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




# 1021. 删除最外层的括号(简单)

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


# 1029. *两地调度(简单)

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

## 用Arrays.sort对二维数组进行排序

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


# 1046. 最后一块石头的重量(简单)

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



# 1047. 删除字符串中的所有相邻重复项(简单)

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

# 1143. 最长公共子序列(中等)

给定两个字符串`text1`和`text2`，返回这两个字符串的最长公共子序列的长度。

一个字符串的**子序列**是指这样一个新的字符串：它是由**原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串**。
例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。

若这两个字符串没有公共子序列，则返回0。



# 1196. 最多可以买到的苹果数量(简单)

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


# 1217. 移动筹码(简单)

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
        return Math.min(even, odd);
    }
}
```


# 1221. *分割平衡字符串(简单)

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


# 1290. 二进制链表转整数(简单)

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


# 1365. 有多少小于当前数字的数字(简单)

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



# 1403. 非递增顺序的最小子序列(简单)

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


# 1441. 用栈操作构建数组(简单)

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


# 1474. 删除链表M个节点后的N个节点(简单)

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
            head.next = helper(head.next, m - 1, n);
            // 返回当前节点
            return head;
        } else { // 如果m等于0，当前元素需要删除
            // 递归到下一节点，递归返回之为当前返回值。
            return helper(head.next, m, n - 1);
        }
    }
}
```


# 1512. 好数对的数目(简单)

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



# 1518. *换酒问题(简单)

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


# 剑指Offer 22. 链表中倒数第k个节点(简单)

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