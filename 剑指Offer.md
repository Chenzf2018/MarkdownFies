# 剑指Offer

## 按类型

### 字符串

#### 5.替换空格

P51：

&emsp;&emsp;**实现一个函数，<u>把字符串中的每个空格都替换成“%20”</u>。例如，输入“We are happy.”，则输出为“We%20are%20happy.”。**

**Java StringBuffer和StringBuilder类**
&emsp;&emsp;当<u><font color=red>对字符串[^1]进行修改的时候</font></u>，需要使用StringBuffer和StringBuilder类。
[^1]: 一般，字符串不可修改！

&emsp;&emsp;与String类不同的是，StringBuffer和StringBuilder类的对象能够被多次的修改，并且<font color=red><u>不产生新的未使用对象</u></font>。StringBuilder类在Java 5中被提出，它和StringBuffer之间的最大不同在于<u>StringBuilder的方法不是线程安全的</u>(不能同步访问)。由于StringBuilder相较于StringBuffer有速度优势，所以多数情况下建议使用StringBuilder类。然而在应用程序要求线程安全的情况下，则必须使用StringBuffer类。
```Java
public class Test
{
  public static void main(String args[])
  {
    StringBuffer sBuffer = new StringBuffer("sjtu");
    sBuffer.append("www");
    sBuffer.append(".sjtu");
    sBuffer.append(".edu.cn");
    System.out.println(sBuffer);  
  }
}
```
**解题思路**
思路详解见《剑指Offer》P51

&emsp;&emsp;先遍历一次字符串，计算出替换后字符串的总长度；<font color=red>从字符串的后面开始复制和替换</font>：<font color=red>准备两个指针$P_{1}$和$P_{2}$。$P_{1}$指向原始字符串的末尾，$P_{2}$指向替换后字符串的末尾</font>。向前移动指针$P_{1}$，逐个把它指向的字符复制到$P_{2}$指向的位置，直到碰到空格为止。碰到空格后，把$P_{1}$向前移动1格，在$P_{2}$之前插入字符串"%20"，将$P_{2}$向前移动3格。接着按上述方法复制。当$P_{1}$和$P_{2}$指向同一位置时，表明所有空格都已经替换完毕！

&emsp;&emsp;所有的字符都只复制一次，因此这个算法的时间效率是$O\left ( n \right )$。

**ReplaceSpaces_5.java**
```Java
public class ReplaceSpaces_5
{
    public static String replaceSpace(StringBuffer stringBuffer)
    {
        int P1= stringBuffer.length() - 1;
        //System.out.println(P1);
        for (int i = 0; i <= P1; i++)
        {
            if (stringBuffer.charAt(i) == ' ')
                stringBuffer.append("  ");  // 两个空格
        }

        int P2 = stringBuffer.length() - 1;
        //System.out.println(P2);
        while (P1 >= 0 && P2 > P1)
        {
            char c = stringBuffer.charAt(P1--);
            if (c == ' ')
            {
                stringBuffer.setCharAt(P2--, '0');
                stringBuffer.setCharAt(P2--, '2');
                stringBuffer.setCharAt(P2--, '%');
            }
            else
                stringBuffer.setCharAt(P2--, c);
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args)
    {
        StringBuffer preString = new StringBuffer("We are happy ");
        String newString = replaceSpace(preString);
        System.out.println(preString);
        System.out.println(newString);
    }
}
```

下面的方法，空间复杂度提高了！
**ReplaceSpaces.java**
```java {.line-numbers highlight=12}
/**
 * 5.替换空格
 */

public class ReplaceSpaces
{
    public static String replaceSpace(StringBuffer str)
    {
        if (str == null)
            return null;
        StringBuffer newStr = new StringBuffer();
        for (int i=0; i < str.length(); i++)
        {
            if (str.charAt(i) == ' ')
            {
                newStr.append('%');
                newStr.append('2');
                newStr.append('0');
            }
            else
                newStr.append(str.charAt(i));
        }
        return newStr.toString();
    }

    public static void main(String[] args)
    {
        StringBuffer preString = new StringBuffer("We are happy ");
        String newString = replaceSpace(preString);
        System.out.println(preString);
        System.out.println(newString);
    }
}
```

#### 19 正则表达式匹配
P124
&emsp;&emsp;实现一个函数用来匹配包括'.'和'\*'的正则表达式。<font color=red>模式中的字符'.'表示任意一个字符，而'\*'表示它前面的字符可以出现任意次(包含0次)</font>。 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab\*ac\*a"匹配，但是与"aa.a"和"ab\*a"均不匹配。

**解析**：
&emsp;&emsp;每次从字符串里拿出一个字符和模式中的字符去匹配。<font color=red>先来分析如何匹配一个字符</font>。如果模式中的字符ch是'.'， 那么它可以匹配字符串中的任意字符。如果模式中的字符ch不是'.'，而且字符串中的字符也是ch(字符串中的字符与模式中的字符一样)，那么它们相互匹配。当字符串中的字符和模式中的字符相匹配时，接着匹配后面的字符。

<font color=red>当模式中的第二个字符不是'\*'时</font>，如果字符串中的第一个字符和模式中的第一个字符相匹配， 那么在字符串和模式上都向后移动一个字符，然后匹配剩余的字符串和模式。<font color=red>如果字符串中的第一个字符和模式中的第一个字符不相匹配，则直接返回false</font>。

<font color=red>当模式中的第二个字符是'\*'时</font>，可能有多种不同的匹配方式。(<font color=red>一种选择是<u>在模式上</u>向后移动两个字符。这相当于'\*'和它前面的字符被忽略了</font>，因为'\*'可以匹配字符串中的0个字符。<font color=red>如果模式中的第一个字符和字符串中的第一个字符相匹配，则在字符串上向后移动一个字符，而在模式上有两种选择：可以在模式上向后移动两个字符，也可以保持模式不变</font>。)

如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
* 模式后移2字符，相当于x\*被忽略；
* 字符串后移1字符，模式后移2字符；
* 字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位。

```java
public class RegularExpressionMatching
{
    private static boolean match(char[] str, char[] pattern)
    {
        if (str == null || pattern == null)
            return false;

        int strIndex = 0;
        int patternIndex = 0;
        return matchCore(str, strIndex, pattern, patternIndex);
    }

    private static boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex)
    {
        //str到尾，pattern到尾，匹配成功(所谓到尾，指：xxIndex==xx.length)
        if (strIndex == str.length && patternIndex == pattern.length)  // 数组长度的函数不是length()
            return true;

        //str未到尾，pattern到尾，匹配失败
        if (strIndex != str.length && patternIndex == pattern.length)
            return false;

        //str到尾，pattern未到尾(不一定匹配失败，因为a*可以匹配0个字符)
        if (strIndex == str.length && patternIndex < pattern.length) {
            //只有pattern剩下的部分类似a*b*c*的形式，才匹配成功
            if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
                return matchCore(str, strIndex, pattern, patternIndex + 2);
            }
            return false;
        }

        //str未到尾，pattern未到尾

        //模式第2个是*
        // 字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*')
        {
            if (pattern[patternIndex] == str[strIndex] || (pattern[patternIndex] == '.' && strIndex != str.length)) // &&存在可排除a与a.*
            {
                return matchCore(str, strIndex, pattern, patternIndex + 2)  //模式后移2，视为x*匹配0个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex + 2)  //字符串后移1字符，模式后移2字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex);  //字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位
            }
            else  //字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配
                return matchCore(str, strIndex, pattern, patternIndex + 2);
        }

        //模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
        if (pattern[patternIndex] == str[strIndex] || (pattern[patternIndex] == '.' && strIndex != str.length))  // a与a.使得&&存在
            return matchCore(str, strIndex + 1, pattern, patternIndex + 1);

        return false;
    }

    public static void main(String[] args)
    {
        char[] str1 = {'a', 'a', 'a'};
        //char[] str2 = {'a', '.', 'a'};
        //char[] str3 = {'a', 'b', '*', 'a'};
        char[] str4 = {'a', '*', 'a', 'a'};
        System.out.println(match(str1, str4));
    }
}
```

#### 20.表示数值的字符串
P127
&emsp;&emsp;请实现一个函数用来判断字符串是否表示数值(包括整数和小数)。例如，字符串"+100", "5e2", "-123", "3.1416"和"-1E-16"都表示数值。但是"12e", "1a3.14", "1.2.3", "+-5"和"12e+4.3"都不是。

**解析**：
&emsp;&emsp;表示数值的字符串遵循模式 **A[.[B]]['e|E'C]** 或者 **.B['e|E'C]**。A,B,C表示整数(A不是必需的，如.123=0.123) ( | 表示或。[]表示可有可无)。

A和C都是可能以'+'或者'-'开头的0\~9的数位串；B也是0\~9的数位串，但是前面不能有正负号。

&emsp;&emsp;<font color=red>判断一个字符串是否符合上述模式时，首先尽可能多地扫描0\~9的数位(有可能在起始处有'+'或者'-'，也就是前面模式中表示数值整数的A部分。如果遇到小数点'.'，则开始扫描表示数值小数部分的B部分。如果遇到'e'或者'E'，则开始扫描表示数值指数的C部分。</font>
```Java
/**
 * 20.表示数值的字符串
 */

public class NumberStrings
{
    private static boolean isNumeric(char[] str)
    {
        String string = String.valueOf(str);
        return string.matches("[+-]?[0-9]*(\\.[0-9]*)?([eE][+-]?[0-9]+)?");
        // return string.matches("[\\+\\-]?\\d*(\\.\\d+)?([eE][\\+\\-]?\\d+)?");
    }

    public static void main(String[] args)
    {
        System.out.println(isNumeric(new char[] {'2', 'e', '+', '5', '.', '4'}));
    }
}
```
对正则进行解释：
```
[+-]?                  -> 正或负符号出现与否
[0-9]*                 -> 整数部分是否出现，如-.34 或 +3.34均符合
(\\.[0-9]*)?           -> 如果出现小数点，那么小数点后面必须有数字；否则一起不出现
([eE][+-]?[0-9]+)?     -> 如果存在指数部分，那么e或E肯定出现，+或-可以不出现，紧接着必须跟着整数；或者整个部分都不出现

限定符：
*	匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。
+	匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。
?	匹配前面的子表达式零次或一次。例如，"do(es)?" 可以匹配 "do", "does" 中的 "does", "doxy" 中的 "do" 。
( )	标记一个子表达式的开始和结束位置。
\\.     表示'.'，Java中转义需要两个反斜杠
```

#### 58.翻转单词顺序(\*****)
P284
&emsp;&emsp;输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student."，则输出"student. a am I"。

**解析**：
&emsp;&emsp;方法一：第一步翻转句子中所有的字符。比如翻转"I am a student."中所有的字符得到".tneduts a ma I"，此时不但翻转了句子中单词的顺序，连单词内的字符顺序也被翻转了。第二步再翻转每个单词中字符的顺序，就得到了"student. a am I"。句子中，单词被空格符号分隔，因此我们可以通过扫描空格来确定每个单词的起始和终止位置。
```java
/**
 * 58.翻转单词顺序 P284
 * 方法一
 */

public class ReverseSentence
{
    private static String reverseSentence(String str)
    {
        char[] chars = str.toCharArray();
        reverse(chars, 0, chars.length - 1);
        int blank = -1;
        for (int i = 0; i < chars.length; i++)  // 不会对最后一个单词进行翻转处理
        {
            if (chars[i] == ' ')  // 找到最后一个单词前的空格就结束了
            {
                int nextBlank = i;
                reverse(chars, blank + 1, nextBlank - 1);
                blank = nextBlank;
            }
        }
        reverse(chars, blank + 1, chars.length - 1);  // 最后一个单词单独进行翻转
        return new String(chars);
    }

    private static void reverse(char[] chars, int low, int high)
    {
        while (low < high)
        {
            char temp = chars[high];
            chars[high] = chars[low];
            chars[low] = temp;
            low++;high--;
        }
    }

    public static void main(String[] args)
    {
        System.out.println(reverseSentence("student. a am I"));
    }
}
```

&emsp;&emsp;方法二：将单词放入数组中，只翻转数组中单词的位置，不改变单词内字母的位置。
```java
public class ReverseSentence
{
    private static String reverseSentence(String str)
    {
        if (str == null || str.trim().equals("")) // "" or " "
            return str;

        String[] strings = str.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--)
        {
            if (i == 0)
                stringBuilder.append(strings[0]);
            else
            {
                stringBuilder.append(strings[i]);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(reverseSentence(""));
    }
}
```

#### 58.2.左旋转字符串
P286
&emsp;&emsp;汇编语言中有一种移位指令叫做循环左移(ROL)，现在有个简单的任务，就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”，要求输出循环左移3位后的结果，即“XYZdefabc”。

字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串 "abcdefg"和数字2, 该函数将返回左旋转两位得到的结果"cdefgab"。

**解析**：
&emsp;&emsp;以"abcdefg"为例，我们可以把它分为两部分。由于想把它的前两个字符移到后面，我们就把前两个字符分到第一部分；把后面的所有字符分到第二部分。我们先分别翻转这两部分，于是就得到"bagfedc"。接下来翻转整个字符串，得到的"cdefgab"刚好就是把原始字符串 左旋转两位的结果。
```java
/**
 * 58.2.左旋转字符串
 * P286
 */

public class LeftRotateString
{
    private static String leftRotateString(String str, int n)
    {
        char[] chars = str.toCharArray();
        if (chars.length < n)
            return "";
        reverse(chars, 0, n - 1);
        reverse(chars, n, chars.length - 1);
        reverse(chars, 0, chars.length - 1);
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : chars)
            stringBuilder.append(c);
        return stringBuilder.toString();
    }

    private static void reverse(char[] chars, int low, int high)
    {
        while (low < high)
        {
            char temp = chars[low];
            chars[low] = chars[high];
            chars[high] = temp;
            low++;high--;
        }
    }

    public static void main(String[] args)
    {
        String string = "abcdefg";
        int n = 2;
        System.out.println(leftRotateString(string, n));
    }
}
```

#### 67.把字符串转换成整数
P318
&emsp;&emsp;将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。数值为0或者字符串不是一个合法的数值则返回0。输入：1a33；输出：0。
```java {.line-numbers highlight=46}
/**
 * 67.把字符串转换成整数
 * P318
 */

public class StrToInt
{
    private static int strToInt(String str)
    {
        if (str == null || str.length() == 0)  // 想想str == null和str.length() == 0的区别
            return 0;
            //throw new Exception("待转换字符串为null或空串");
        int tag;  // 首位是否'-'
        int start;  // 遍历从何处开始
        if (str.charAt(0) == '+') {tag  = 1; start = 1;}
        else if (str.charAt(0) == '-'){tag = 0; start = 1;}
        else {tag = 1; start = 0;}

        long result = 0;
        for (int i = start; i < str.length(); i++)
        {
            char temp = str.charAt(i);
            if (temp >= '0' && temp <= '9')
            {
                result = result * 10 + (temp - '0');
                if (tag == 1 && result > Integer.MAX_VALUE)
                    throw new RuntimeException("上溢出");
                if (tag == 0 && result < Integer.MIN_VALUE)
                    throw new RuntimeException("下溢出");  // 输入-2147483649并未出现下溢出
            }
            else
                return 0;
        }

        if (tag == 0)
            return (int) (-1 * result);
        else
            return (int) result;
    }

    public static void main(String[] args)
    {
        //System.out.println(strToInt("2147483648"));  //上溢出
        System.out.println(strToInt("2147483647"));  //2147483647
        System.out.println(strToInt("-2147483648")); // -2147483648
        System.out.println(strToInt("-2147483649")); //2147483647
    }
}
```
<font color=red>没有解决“下溢出”问题</font>，错误结果如代码第46行所示！

把对最大值和最小值的处理放在for循环外：

```java
/**
 * 67.把字符串转换成整数
 * P318
 */

public class StrToInt
{
    private static int strToInt(String str)
    {
        if (str == null || str.length() == 0)  // 想想str == null和str.length() == 0的区别
            return 0;
        boolean isNegative = str.charAt(0) == '-';  // 是'-'则为true
        long result = 0;  // 避免溢出
        for (int i = 0; i < str.length(); i++)
        {
            char aChar = str.charAt(i);
            if (i == 0 && (aChar == '+' || aChar == '-'))  // 符号判定
                continue;
            if (aChar < '0' || aChar > '9')  // 非法输入
                return 0;
            result = result * 10 + (aChar - '0');
        }
        // 处理最大正数，最大负数
        if (isNegative && (-result) < (-2147483648))
            throw new RuntimeException("下溢出");
        else if (!isNegative && result > 2147483647)
            throw new RuntimeException("上溢出");
        return isNegative ? (int)(-result) : (int)result;
    }

    public static void main(String[] args)
    {
        //System.out.println(strToInt("2147483648"));  //上溢出
        System.out.println(strToInt("2147483647"));  //2147483647
        System.out.println(strToInt("-2147483648")); // -2147483648
        //System.out.println(strToInt("-2147483649")); //下溢出
        System.out.println(strToInt("-"));
    }
}
```

### 栈和队列
&emsp;&emsp;栈的特点是后进先出，即最后被压入(push)栈的元素会第一个被弹出(pop)。通常栈是一个不考虑排序的数据结构，我们需要$O(n)$时间才能找到栈中最大或者最小的元素。和栈个同的是，队列的特点是先进先出。  

#### <span id="9">9.用两个栈实现队列</span>
P68
&emsp;&emsp;用两个栈实现一个队列。队列的声明如下，请实现它的两个函数push(appendTail)和pop(deleteHead), 分别完成在队列尾部插入节点和在队列头部删除节点的功能。队列中的元素为int类型。

**解析**：
&emsp;&emsp;首先插入一个元素a，把它插入stack1，此时stack1中的元素有{a}，stack2为空。再压入两个元素b和c，还是插入stack1，此时stack1中的元素有{a, b, c}，其中c位于栈顶，而stack2仍然是空的，如图(a)所示：
<div align=center><img src=PointToOffer_Images/用两个栈模拟一个队列的橾作.png width=80%></div>

&emsp;&emsp;这时候我们试着从队列中删除一个元素。按照队列先入先出的规则，最先被删除的元素应该是a。元素a存储在stack1中，但并不在栈顶上，因此不能直接进行删除。可以把stack1中的元素逐个弹出并压入stack2。因此经过3次弹出stack1和压入stack2的操作之后，stack1为空，而stack2中的元素是{c,b,a}，这时候就可以弹出stack2的栈顶a了。此时stack2的元素为{c, b}，其中b在栈顶，如图(b)所示。

如果我们还想继续删除队列的头部应该怎么办呢？剩下的两个元素是b和c，此时b恰好在栈顶上，因此直接弹出stack2的栈顶即可。

&emsp;&emsp;从上面的分析中我们可以总结出删除一个元素的步骤：当stack2不为空时，在stack2中的栈顶元素是最先进入队列的元素，可以弹出。当stack2为空时，我们把stack1中的元素逐个弹出并压入stack2。由于先进入队列的元素被压到stack1的底端，经过弹出和压入操作之后就处于stack2的顶端，又可以直接弹出。(<font color=red>stack2如果不空，想再添加元素并推出时，得先将stack2清空</font>)

&emsp;&emsp;接下来再插入一个元素d。得把它压入stack1，如图(d)所示。考虑下一次删除队列的头部stack2不为空，直接弹出它的栈顶元素c，如图(e)所示。而c的确比d先进入队列，应该在d之前从队列中删除，因此不会出现任何矛盾。

&emsp;&emsp;总结下，stack1负责插入，stack2负责弹出。如果stack2为空了，将stack1的元素依次**全部**弹出并存放到stack2中，之后对stack2进行弹出操作。**<font color=red>*push*操作只需包含将元素压入stack1；*pop()* 操作包含：先将stack2弹空，再将stack1中元素压入stack2中</font>**。
```java
/**
 * 9.用两个栈实现队列
 * P68
 */

import java.util.Stack;

public class QueueWithTwoStacks
{
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    //private static void push(int node)
    private void appendTail(int node)
    {
        stack1.push(node);
    }

    private int deleteHead()
    {
        if (stack2.empty())
        {
            while (! stack1.empty())
                stack2.push(stack1.pop());
        }
        return stack2.pop();
    }
}
```
Test.java
```java
public class Test
{
    public static void main(String[] args)
    {
        StackWithMin stackWithMin = new StackWithMin();
        stackWithMin.push(1);
        stackWithMin.push(2);
        stackWithMin.push(0);
        stackWithMin.push(3);
        System.out.println(stackWithMin.min());
        stackWithMin.pop();
        stackWithMin.pop();
        System.out.println(stackWithMin.min());
    }
}

```

用两个栈实现一个队列的功能：
入队：将元素进栈A；
出队：判断栈B是否为空，如果为空，则将栈A中所有元素pop，并push进栈B，栈B出栈；如果不为空，栈B直接出栈。

用两个队列实现一个栈的功能：
入栈：将元素进队列A；
出栈：判断队列A中元素的个数是否为1，如果等于1，则出队列；否则将队列A中的元素依次出队列并放入队列B，直到队列A中的元素留下一个，然后队列A出队列，再把队列B中的元素出队列依次放入队列A中。

#### <span id="30">30.包含min函数的栈</span>
P165
&emsp;&emsp;定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的min函数。在该栈中，调用*min()、push()* 及*pop()* 的时间复杂度都是$O(1)$。要保证测试中，不会当栈为空的时候，对栈调用pop()或者min()或者top()方法。

**解析**：
&emsp;&emsp;看到这个问题，第一反应可能是每次压入一个新元素进栈时，将栈里的所有元素排序，让最小的元素位于栈顶，这样就能在$O(1)$时间内得到最小元素了。但这种思路<font color=red>不能保证最后压入栈的元素能够最先出栈</font>。

接着想到在栈里添加一个成员变量存放最小的元素。每次压入一个新元素进栈的时候，如果该元素比当前最小的元素还要小，则更新最小元素。但如果<font color=red>当前最小的元素被弹出栈了</font>，那么如何得到下一个最小的元素呢？仅仅添加一个成员变量存放最小元素是不够的，也就是说<font color=red>当最小元素被弹出栈的时候，希望能够得到次小元素。因此，在压入这个最小元素之前，要把次小元素保存起来</font>。

&emsp;&emsp;<font color=red>可以把每次的最小元素(之前的最小元素和新压入栈的元素两者的较小值)都保存起来放到另外一个辅助栈里。</font>
<div align=center><img src=PointToOffer_Images/辅助栈.png></div>

首先往空的数据栈里压入数字3, 显然现在3是最小值，把这个最小值压入辅助栈。接下来往数据栈里压入数字4。由于4大于之前的最小值，因此仍然往辅助栈里压入数字3。第三步继续往数据栈里压入数字2。由于2小于之前的最小值3，因此把最小值更新为2，并把2压入辅助栈。同样， 当压入数字1时，也要更新最小值，并把新的最小值1压入辅助栈。如果<font color=red>每次都把最小元素压入辅助栈，那么就能保证<u>辅助栈的栈顶一直都是最小元素</u></font>。

<font color=red>当<u>最小元素从数据栈内被弹出</u>之后，同时弹出辅助栈的栈顶元素</font>，此时辅助栈的新栈顶元素就是下一个最小值。

比如在第四步之后，栈内的最小元素是1。当第五步在数据栈内弹出1后，把辅助栈的栈顶弹出，辅助栈的栈顶元素2就是新的最小元素。接下来继续弹出数据栈和辅助栈的栈顶之后，数据栈还剩下3、4两个数字，3是最小值。此时位于辅助栈的栈顶数字正好也是3，的确是最小值。

#### 31.栈的压入、弹出序列
P168
&emsp;&emsp;输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如，序列{1,2,3,4,5}是某栈的压栈序列，<font color=red>序列{4,5,3,2,1}</font>是该压栈序列对应的一个弹出序列，但<font color=red>{4,3,5,1,2}</font>(与前面一个序列不同)就不可能是该压栈序列的弹出序列。

**解析**：
&emsp;&emsp;先理解题意：<font color=red>栈的大小不一定！</font>假设有一串数字要将他们压栈:{1,2,3,4,5}，如果这个栈是足够大的，那么一次性全部压进去，再出栈：{5,4,3,2,1}。但是，<font color=red>如果这个栈高度为4</font>，1,2,3,4都顺利入栈，但是满了，那么要先出栈一个，才能入栈，那么就是先出4，然后压入5，随后再全部出栈：{4,5,3,2,1}。

&emsp;&emsp;建立一个<font color=red>辅助栈(模拟栈的压入和弹出过程)</font>，把输入的第一个序列中的数字依次压入该辅助栈，并按照第二个序列的顺序依次从该栈中弹出数字。

以弹出序列{4,5,3,2,1}为例分析压栈和弹出的过程。第一个希望被弹出的数字是4，那么辅助序列压入的数依次为{1,2,3,4}，然后弹出4变为{1,2,3}；第二个希望被弹出的数字是5，那么此时辅助序列压入5变成{1,2,3,5}，以此类推整个过程：
<div align=center><img src=PointToOffer_Images/辅助序列压入弹出过程.png></div>

&emsp;&emsp;按照压入序列的顺序，将元素压入辅助序列；如果下一个弹出的数字(参照弹出序列)刚好是此时辅助序列栈顶数字，那么直接从辅助序列弹出该数字；如果下一个弹出的数字(<font color=red>弹出一个数字就参考弹出序列下一个元素</font>)不在栈顶，则把压栈序列中还没有入栈的数字压入辅助栈，直到把下一个需要弹出的数字压入栈顶为止；如果所有数字都压入栈后仍然没有找到下一个弹出的数字，那么该序列不可能是一个弹出序列。
```java
/**
 * 31.栈的压入、弹出序列
 * P168
 */

import java.util.Stack;

public class IsPopOrder
{
    public static boolean isPopOrder(int[] pushA, int[] popA)
    {
        if (pushA == null || popA == null || pushA.length == 0 || popA.length == 0 || pushA.length != popA.length)
            return false;
        Stack<Integer> stack = new Stack<>();  // 辅助栈
        // 模拟pushA入栈--进入栈s，popA出栈--弹出s栈顶；若popA合法，则s最后一定是空的
        for (int i = 0, popIndex = 0; i < pushA.length; i++)
        {
            stack.push(pushA[i]);  // 将压入栈中元素压入辅助栈
            while (! stack.empty() && stack.peek() == popA[popIndex])
            {
                // 弹出一个,继续比较下一个是否还可以弹出；辅助栈栈顶和popA相同则出栈，且popIndex++
                stack.pop();
                popIndex++;
            }
        }
        return stack.empty();
    }

    public static void main(String[] args)
    {
        int[] push = {1, 2, 3, 4, 5};
        int[] pop1 = {4, 5, 3, 2, 1};
        int[] pop2 = {4, 3, 5, 1, 2};
        System.out.println(isPopOrder(push,pop1));
        System.out.println(isPopOrder(push,pop2));
    }
}
```

#### 59.1 滑动窗口的最大值
P288
&emsp;&emsp;给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}：
针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
  {[2,3,4],2,6,2,5,1}，{2,[3,4,2],6,2,5,1}，{2,3,[4,2,6],2,5,1}，
  {2,3,4,[2,6,2],5,1}，{2,3,4,2,[6,2,5],1}，{2,3,4,2,6,[2,5,1]}。

**解析**：
&emsp;&emsp;方法一(采用蛮力法)：可以扫描每个滑动窗口的所有数字并找出其中的最大值。如果滑动窗口的大小为$k$，则需要$O(k)$时间才能找出滑动窗口里的最大值。对于长度为$n$的输入数组，这种算法的总时间复杂度是$O(nk)$。
```java
/*
 * 59.1 滑动窗口的最大值
 * P288
 * 暴力解法
 */

import java.util.ArrayList;

public class MaxInWindows
{
    /**
     *
     * @param num 数组
     * @param size 窗口大小
     * @return 所有滑动窗口里数值的最大值
     */
    private static ArrayList<Integer> maxInWindows(int[] num, int size)
    {
        ArrayList<Integer> maximumInWindow = new ArrayList<>();
        if (num == null || size < 1 || size > num.length)
            return maximumInWindow;
        //preMaxIndex上一个滑动窗口最大值的下标
        int preMaxIndex = findIndexOfMax(num, 0, size - 1); // 第一个窗口中最大值的下标
        maximumInWindow.add(num[preMaxIndex]);  // 将第一个窗口中得到的最大值放入数组链表中
        // 从数组的index=1处开始在下一个窗口中找最大值
        for (int start = 1, end = size; start <= (num.length - size); start++, end++)
        {
            int newMaxIndex = maxNumIndexOfWindow(num, preMaxIndex, start, end);
            //maximumInWindow.add(newMaxIndex);
            maximumInWindow.add(num[newMaxIndex]);
            preMaxIndex = newMaxIndex;
        }
        return maximumInWindow;
    }

    /**
     *
     * @param num 数组
     * @param start 窗口开始位置
     * @param end 窗口结束位置
     * @return 返回数组[start,end]范围内的最大值的下标
     */
    private static int findIndexOfMax(int[] num, int start, int end)
    {
        int max = num[start];
        int maxIndex = start;
        for (int i = start; i <= end; i++)
        {
            if (num[i] >= max)
            {
                //max = num[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     *
     * @param num 数组
     * @param preMaxIndex 上一个滑动窗口最大值的下标
     * @param start 窗口起始处
     * @param end 窗口截止处
     * @return 新窗口中最大值的下标
     */
    private static int maxNumIndexOfWindow(int[] num, int preMaxIndex, int start, int end)
    {
        if (start <= preMaxIndex && preMaxIndex <= end)
        {
            // 如果preMaxIndex在新的滑动窗口[start,end]之间
            // 则判断上一个最大值与新增的一个值num[end]比较大小，返回index
            return (num[preMaxIndex] <= num[end]) ? end : preMaxIndex;
        }
        else
        {
            // 如果preMaxIndex不在新的滑动窗口[start,end]之间
            // 直接找新滑动窗口的最大值的下标
            return findIndexOfMax(num, start, end);
        }
    }

    public static void main(String[] args)
    {
        int[] num = {2, 3, 4, 2, 6, 2, 5, 1};
        int size = 3;
        System.out.println(maxInWindows(num, size));
    }
}
```

&emsp;&emsp;方法二：实际上，<font color=red>一个滑动窗口可以看成一个队列</font>。当窗口滑动时，处于窗口的第一个数字被删除，同时在窗口的末尾添加一个新的数字</font>。这符合队列的“先进先出”特性。如果能从队列中找出它的最大数， 那么这个问题也就解决了。

在[30.包含min函数的栈](#30)中，实现了一个可以用$O(1)$时间得到最小值的栈。同样，也可以用$O(1)$时间得到栈的最大值。同时在[9.用两个栈实现队列](#9)中，讨论了如何用两个栈实现一个队列。综合这两个问题的解决方案，如果把队列用两个栈实现，由于可以用$O(1)$ 时间得到栈中的最大值，那么也就可以用$O(1)$时间得到队列的最大值，因此总的时间复杂度也就降到了$O(n)$。

&emsp;&emsp;方法三：不把滑动窗口的每个数值都存入队列，而是只把有可能成为滑动窗口最大值的数值存入一个两端开口的队列。

以数组{2,3,4,2,6,2,5,1}为例：数组的第一个数字是2, 把它存入队列。第二个数字是3, 由于它比前一个数字2大，因此2不可能成为滑动窗口中的最大值。先把2从队列里删除，再把3存入队列。此时队列中只有一个数字3。针对第三个数字4的步骤类似，最终在队列中只剩下一个数字4。此时滑动窗口中已经有3个数字，而它的最大值4位于队列的头部。

接下来处理第四个数字2。2比队列中的数字4小。当4滑出窗口之后，2还是有可能成为滑动窗口中的最大值，因此把2存入队列的尾部。现在队列中有两个数字4和2，其中最大值4仍然位于队列的头部。

第五个数字是6。由于它比队列中已有的两个数字4和2都大，因此这时4和2已经不可能成为滑动窗口中的最大值了。先把4和2从队列中删除，再把数字6存入队列。这时候最大值6仍然位于队列的头部。

第六个数字是2。由于它比队列中已有的数字6小，所以把2也存入队列的尾部。此时队列中有两个数字，其中最大值6位于队列的头部。

第七个数字是5。在队列中已有的两个数字6和2里，2小于5，因此2不可能是一个滑动窗口的最大值，可以把它从队列的尾部删除。删除数字2之后，再把数字5存入队列。此时队列里剩下两个数字6和5，其中位于队列头部的是最大值6。

数组最后一个数字是1，把1存入队列的尾部。注意到位于队列头部的数字6是数组的第五个数字，此时的滑动窗口已经不包括这个数字了，因此应该把数字6从队列中删除。那么怎么知道滑动窗口是否包括一个数字？应该在队列里存入数字在数组里的下标，而不是数值。<font color=red>当一个数字的下标与当前处理的数字的下标之差大于或者等于滑动窗门的大小时，这个数字已经从窗口中滑出，可以从队列中删除了</font>。

<font color=red>滑动窗口的最大值总是位于队列的头部</font>：
<div align=center><img src=PointToOffer_Images/滑动窗口.png></div>

&emsp;&emsp;总结：把可能成为最大值数字的下标放入双端队列deque，从而减少遍历次数。首先，所有在没有查看后面数字的情况下，任何一个节点都有可能成为某个状态的滑动窗口的最大值，因此，数组中任何一个元素的下标都会入队。关键在于出队，以下两种情况下，该下标对应的数字不会是窗口的最大值需要出队：
(1)该下标已经在窗口之外，比如窗口长度为3，下标5入队，那么最大值只可能在下标3,4,5中出现，队列中如果有下标2则需要出队；
(2)后一个元素大于前面的元素，那么前面的元素出队，比如目前队列中有下标3、4，data[3]=50，data[4]=40，下标5入队，但data[5]=70，则队列中的3，4都需要出队。

#### 59.2队列的最大值(待更)
P292
&emsp;&emsp;定义一个队列并实现函数`max`得到队列里的最大值，要求函数`max, push_back`和`pop_front`的时间复杂度都是$O(1)$。


### 链表
&emsp;&emsp;链表应该是面试时被提及砐频繁的数据结构。链表的结构很简单，它由指针把若干个节点连接成链状结构。链表的创建、插入节点、删除节点等操作都只需要20行左右的代码就能实现，其代码量比较适合面试。而像哈希表、有向图等复杂数据结构，实现它们的一个操作需要的代码量都较大，很难在几十分钟的面试中完成。另外，由于链表是一种动态的数据结构，其需要对指针进行操作，因此应聘者需要有较好的编程功底才能写出完整的操作链表的代码。

&emsp;&emsp;链表是一种动态数据结构，是因为在创建链表时，无须知道链表的长度。当插入一个节点时，只需要为新节点分配内存，然后调整指针的指向来确保新节点被链接到链表当中。内存分配不是在创建链表时一次性完成的，而是每添加一个节点分配一次内存。由于没有闲置的内存，链表的空间效率比数组高。

#### 6.从尾到头打印链表
P58
&emsp;&emsp;输入一个链表的头节点，从尾到头反过来打印出每个节点的值。

**解析**：
&emsp;&emsp;把链表中链接节点的指针反转过来，改变链表的方向，然后就可以从头到尾输出了。但该方法会改变原来链表的结构。通常<font color=red>打印是一个只读操作</font>，我们不希望打印时修改内容。

&emsp;&emsp;解决这个问题肯定要遍历链表。遍历的顺序是从头到尾，可输出的顺序却是从尾到头。也就是说，第一个遍历到的节点最后一个输出，而最后一个遍历到的节点第一个输出。这就是典型的“后进先出”，可以用栈实现这种顺序。<font color=red>每经过一个节点的时候，把该节点放到一个栈中。当遍历完整个链表后，再从栈顶开始逐个输出节点的值，此时输出的节点的顺序已经反转过来了。</font>
```java
/*
6.从尾到头打印链表
P58
 */

import java.util.Stack;
import java.util.ArrayList;

public class PrintListFromTailToHead
{
    /**
     *
     * @param listNode 链表结点，定义见ListNode.java
     * @return 返回一个逆序的链表
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode)
    {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null)
        {
            stack.push(listNode.value);
            listNode = listNode.next;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (! stack.empty())
            arrayList.add(stack.pop());
        return arrayList;
    }
}
```

&emsp;&emsp;既然想到了用栈来实现这个函数，而<font color=red>递归在本质上就是一个栈结构</font>，于是很自然地又想到了用递归来实现。要实现反过来输出链表，我们每访问到一个节点的时候，先递归输出它后面的节点，再输出该节点自身，这样链表的输出结果就反过来了。但是，<font color=red>当链表非常长的时候，就会导致函数调用的层级很深，从而有可能导致函数调用栈溢出。显然用栈基千循环实现的代码的鲁棒性要好一些。</font>

#### 18.在$O(1)$时间删除链表的节点(待更)
P119


#### 18.2.删除排序链表中重复的节点
P122
&emsp;&emsp;在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5。

**解析**：
&emsp;&emsp;头节点可能与后面的节点重复，也就是说头节点也可能被删除。
```java
/*
18.2.删除排序链表中重复的节点
P122
 */

public class DeleteDuplicatedNode
{
    public ListNode deleteDuplication(ListNode pHead)
    {
        if (pHead == null || pHead.next == null)
            return pHead;
        ListNode nextNode = pHead.next;
        if (pHead.value == nextNode.value)
        {
            while (nextNode != null && pHead.value == nextNode.value)
            {
                // 跳过值与当前结点相同的全部结点,找到第一个与当前结点不同的结点
                nextNode = nextNode.next;
            }
            return deleteDuplication(nextNode);  // 进入递归，重新赋值pHead，则前面相同的节点被舍弃
        }
        else
        {
            pHead.next = deleteDuplication(pHead.next);  
            // 假设1->2->3，如果返回3，则pHead.next=3, pHead=2
            return pHead;
        }
    }
}
```

#### 22.链表中倒数第k个节点
P134
&emsp;&emsp;输入一个链表，输出该链表中倒数第$K$个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，它们的值依次是1,2,3,4,5,6。这个链表的倒数第3个节点是值为4的节点。

**解析**：
&emsp;&emsp;由于链表是单向链表，单向链表的节点只有从前往后的指针而没有从后往前的指针，因此“先走到链表的尾端，再从尾端回溯k步”的方法不可行。

&emsp;&emsp;遍历链表两次：假设整个链表有n个节点，那么倒数第$k$个节点就是从头节点开始的第$n-k+1$个节点。如果我们能够得到链表中节点的个数n, 那么只要从头节点开始往后走$n-k+1$步就可以了。也就是说需要遍历链表两次，第一次统计出链表中节点的个数，第二次就能找到倒数第$k$个节点。

&emsp;&emsp;遍历链表一次：为了实现只遍历链表一次就能找到倒数第$k$个节点，可以<font color=red>定义两个指针。第一个指针从链表的头指针开始遍历向前走$k-1$步，第二个指针保持不动；从第$k$步开始，第二个指针也开始从链表的头指针开始遍历。由于两个指针的距离保持在$k-1$，当第一个（走在前面的）指针到达链表的尾节点时，第二个（走在后面的）指针正好指向倒数第$k$个节点。</font>

```java
/*
22.链表中倒数第k个节点
P134
 */

public class FindKthFromTail
{
    public ListNode findKthFromTail(ListNode head, int k)
    {
        if (k <= 0 || head == null)
            return null;
        ListNode p1 = head;
        ListNode p2 = head;
        // p2向前移动k个节点
        for (int i = 0; i < k - 1; i++)
        {
            if (p2 == null)
                return null;  // 链表的节点总数少于K-1
            p2 = p2.next;
        }
        if (p2 == null)
            return null;  // 链表的节点总数少于K
        while (p2.next != null)
        {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }
}
```

#### 23.链表中环的入口
P139
&emsp;&emsp;给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。例如，下图链表中，环的入口节点是节点3：
<div align=center><img src=PointToOffer_Images/链表中环的入口.png></div>

**解析**：
&emsp;&emsp;解决这个问题的<font color=red>第一个问题是如何确定一个链表中包含环</font>。定义两个指针，同时从链表的头节点出发，一个指针一次走一步，另一个指针一次走两步。如果走得快的指针追上了走得慢的指针，那么链表就包含环；如果走得快的指针走到了链表的末尾（指针指向null）都没有追上第一个指针，那么链表就不包含环。

<font color=red>第二问题是如何找到环的入口</font>。先定义两个指针$P_{1}$和$P_{2}$指向链表的头节点。如果链表中的环有n个节点，则指针$P_{1}$先在链表上向前移动n步，然后两个指针以相同的速度向前移动。当第二个指针指向环的入口节点时，第一个指针已经围绕着环走了一圈，又回到了入口节点。
<div align=center><img src=PointToOffer_Images/在有环的链表中找到环的入口节点.png width=60%></div>

指针$P_{1}$和$P_{2}$在初始化时都指向链表的头节点。由于环中有4个节点，所以指针$P_{1}$先在链表上向前移动4步；指针$P_{1}$和$P_{2}$以相同的速度在链表上向前移动，直到它们相遇。它们相遇的节点就是环的入口节点。

&emsp;&emsp;<font color=red>最后考虑如何得到环中节点的数目</font>。在前面提到判断一个链表里是否有环时，用到了一快一慢两个指针。如果两个指针相遇，则表明链表中存在环。两个指针相遇的节点一定是在环中。可以从这个节点出发，一边继续向前移动一边计数，当再次回到这个节点时，就可以得到环中节点数了。

&emsp;&emsp;可简化上述第二个和第三个问题：根据分析，可得两个结论——<font color=red>结论一：设置快慢指针，都从链表头出发，快指针每次走两步，慢指针一次走一步，假如有环，一定相遇于环中某点（此时快指针走的路程是慢指针的两倍）。结论二：接着让两个指针分别从相遇点和链表头出发，两者都改为每次走一步，最终相遇于环入口。</font>

结论二的证明：设链表头到环入口长度为a，沿着环顺时针方向，环入口到相遇点长度为b，相遇点到环入口长度为c。第一次相遇时，快指针的走过的路程为$a+k·(b+c)+b$，其中$k\geq 1$；慢指针走过的路程为$a+b$。由于相遇前快指针的速度是慢指针的两倍，所以$a+k·(b+c)+b=2(a+b)$，化简可得：$a=(k-1)(b+c)+c$，其中$k\geq 1$。所以两个指针分别从链表头和相遇点出发，最后一定相遇于环入口。
```java
/*
23.链表中环的入口
P139
 */

public class EntryNodeOfLoop
{
    public ListNode entryNodeOfLoop(ListNode pHead)
    {
        if (pHead == null || pHead.next == null)
            return null;
        ListNode low = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) //这两个判断条件避免fast空指针异常(没有环)
        {
            low = low.next;
            fast = fast.next.next;
            if (low == fast)  // 两个指针相遇
            {
                low = pHead;
                while (low != fast)
                {
                    low = low.next;
                    fast = fast.next;
                }
                return low;
            }
        }
        return null;
    }
}
```

#### 24.反转链表
P142
&emsp;&emsp;定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。

**解析**：
&emsp;&emsp;需要定义3个指针，分别指向当前遍历到的节点、它的前一个节点及后一个节点。（next和head在一起）先将next往后移一位，然后将head指向pre，接着先将pre移到head，然后将head向后移一位（即移到next）...
```java
/*
24.反转链表
P142
 */

public class ReverseList
{
    public ListNode reverseList(ListNode head)
    {
        if (head == null)
            return null;
        ListNode pre = null, next = null;
        //当前节点是head，pre为当前节点的前一节点，next为当前节点的下一节点
        //需要pre和next的目的是让当前节点从pre->head->next1->next2变成pre<-head next1->next2
        //即pre让节点可以反转所指方向，但反转之后如果不用next节点保存next1节点的话，此单链表就此断开了
        while (head != null)
        {
            //先用next保存head的下一个节点的信息，保证单链表不会因为失去head节点的原next节点而就此断裂
            next = head.next;
            //保存完head.next，让head从指向next变成指向pre，实现指针翻转
            head.next = pre;
            //让pre，head，next依次向后移动一个节点，继续下一次的指针反转
            pre = head;
            head = next;
        }
        //head为null的时候，pre(head前一个节点)就为最后一个节点了，此时链表已经反转完毕，pre就是反转后链表的第一个节点
        return pre;
    }
}
```

#### 25.合并两个排序的链表
P145
&emsp;&emsp;输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
```java
/*
25.合并两个排序的链表
P145
 */

public class MergeTwoSortedLinkedList
{
    public ListNode merge(ListNode list1, ListNode list2)
    {
        //新建一个头节点，用来存合并的链表。
        ListNode head = new ListNode(0);
        ListNode root = head;
        while (list1 != null && list2 != null)
        {
            if (list1.value < list2.value)
            {
                head.next = list1;
                head = head.next;
                list1 = list1.next;
            }
            else
            {
                head.next = list2;
                head = head.next;
                list2 = list2.next;
            }
        }
        while (list1 != null)
        {
            head.next = list1;
            head = head.next;
            list1 = list1.next;
        }
        while (list2 != null)
        {
            head.next = list2;
            head = head.next;
            list2 = list2.next;
        }
        return root.next;
    }
}
```

#### 35.复杂链表的复制
P187
&emsp;&emsp;输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
<div align=center><img src=PointToOffer_Images/一个含有5个节点的复杂链表.png width=40%></div>

**解析**：
&emsp;&emsp;思路一：把复制过程分成两步：第一步是复制原始链表上的每个节点，并用pNext链接起来；第二步是设置每个节点的pSibling指针。假设原始链表中的某个节点N的pSibling指向节点S，由于S在链表中可能在N的前面也可能在N的后而，所以要定位S的位置需要从原始链表的头节点开始找。如果从原始链表的头节点开始沿着pNext经过m步找到节点S，那么在复制链表上，节点N'的pSibling（记为S'）离复制链表的头节点的距离也是沿着pNext指针m步。用这种办法就可以为复制链表上的每个节点设置pSibling指针。

对于一个含有n个节点的链表，由于定位每个节点的pSibling都需要从链表头节点开始经过$O(n)$步才能找到，因此这种方法总的时间复杂度是$O(n^2)$。

&emsp;&emsp;思路二：由于上述方法的时间主要花费在定位节点的pSibling上面，可以试着在这方面去进行优化。还是分为两步：第一步仍然是复制原始链表上的每个节点N 创建N'，然后把这些创建出来的节点用pNext链接起来。同时我们把<N, N'>的配对信息放到一个哈希表中；第二步还是设置复制链表上每个节点的pSibling。如果在原始链表中节点N的pSibling指向节点S，那么在复制链表中，对应的N'应该指向S'。由于有了哈希表，可以用$O(1)$的时间根据S找到S'。

第二种方法相当于<font color=red>用空间换时间</font>。对于有n个节点的链表，需要一个大小为$O(n)$的哈希表，也就是说以$O(n)$的空间消耗，把时间复杂度由$O(n^2)$降低到$O(n)$。

&emsp;&emsp;思路三：在不用辅助空间的情况下实现$O(n)$的时间效率。第三种方法的第一步仍然是根据原始链表的每个节点N创建对应的N'。这一次，把N'链接在N的后面：
<div align=center><img src=PointToOffer_Images/复制复杂链表的第一步.png width=80%></div>

第二步设置复制出来的节点的pSibling。假设原始链表上的N的pSibling指向节点S，那么其对应复制出来的N'是N的pNext指向的节点，同样S'也是S的pNext指向的节点：
<div align=center><img src=PointToOffer_Images/复制复杂链表的第二步.png width=80%></div>

第三步把这个长链表拆分成两个链表：把奇数位置的节点用pNext链接起来就是原始链表，把偶数位置的节点用pNext链接起来就是复制出来的链表：
<div align=center><img src=PointToOffer_Images/复制复杂链表的第三步.png width=80%></div>

```java
/*
35.复杂链表的复制
P187
 */

public class CloneLinkedListWithRandom
{
    public RandomListNode clone(RandomListNode pHead)
    {
        if (pHead == null)
            return null;

        // 遍历链表，复制每个结点，如复制结点A得到A1，将结点A1插到结点A后面
        RandomListNode currentNode = pHead;
        while (currentNode != null)
        {
            RandomListNode cloneNode = new RandomListNode(currentNode.label);
            cloneNode.next = currentNode.next;
            currentNode.next = cloneNode;
            currentNode = cloneNode.next;
        }

        // 重新遍历链表，复制老结点的随机指针给新结点，如A1.random = A.random.next;
        currentNode = pHead;
        while (currentNode != null)
        {
            currentNode.next.random = currentNode.random == null ? null : currentNode.random.next;
            // 1->1'->2->2'：1.random=2, 1.random.next=2.next=2'
            currentNode = currentNode.next.next;
        }

        // 拆分链表，将链表拆分为原链表和复制后的链表
        currentNode = pHead;
        RandomListNode pCloneHead = pHead.next;
        while (currentNode != null)
        {
            RandomListNode cloneNode = currentNode.next;
            currentNode.next = cloneNode.next;
            cloneNode.next = cloneNode.next == null ? null : cloneNode.next.next;
            currentNode = currentNode.next;
        }

        return pCloneHead;
    }
}
```

#### 52.两个链表的第一个公共节点
P253
&emsp;&emsp;输入两个链表，找出它们的第一个公共节点。
<div align=center><img src=PointToOffer_Images/两个有公共节点部分重合的链表.png width=60%></div>

**解析**：
&emsp;&emsp;思路一：在第一个链表上顺序遍历每个节点，每遍历到一个节点，就在第二个链表上顺序遍历每个节点。如果在第二个链表上有一个节点和第一个链表上的节点一样，则说明两个链表在这个节点上重合，于是就找到了它们的公共节点。如果链表的长度分别为$m$和$n$，那么时间复杂度是$O(m·n)$。

&emsp;&emsp;思路二：如果两个链表有公共节点，那么公共节点出现在两个链表的尾部。如果我们<font color=red>从两个链表的尾部开始往前比较，那么最后一个相同的节点就是我们要找的节点</font>。但是，在单向链表中，只能从头节点开始按顺序遍历，最后才能到达尾节点。最后到达的尾节点却要最先被比较，这听起来像“后进先出”。于是可以分别把两个链表的节点放入两个栈里，这样两个链表的尾节点就位于两个栈的栈顶，接下来比较两个栈顶的节点是否相同。如果相同，则把栈顶弹出接着比较下一个栈顶，直到找到最后一个相同的节点。

因此需要用两个辅助栈。如果链表的长度分别为$m$和$n$，那么空间复杂度是$O(m+n)$，其时间复杂度也是$O(m+n)$。和蛮力法相比，时间效率得到了提高，相当于用空间消耗换取了时间效率。

&emsp;&emsp;思路三：之所以需要用到栈，是因为想<font color=red>同时遍历到达两个栈的尾节点。当两个链表的长度不相同时，如果我们从头开始遍历，那么到达尾节点的时间就不一致</font>。

其实解决这个问题还有一种更简单的办法：首先遍历两个链表得到它们的长度，就能知道哪个链表比较长，以及长的链表比短的链表多几个节点。在第二次遍历的时候，在较长的链表上先走若干步，接着同时在两个链表上遍历，找到的第一个相同的节点就是它们的第一个公共节点。

该方法时间复杂度是$O(m+n)$，不再需要辅助栈，因此提高了空间效率。

```java
/*
52.两个链表的第一个公共节点
P253
 */

public class FindFirstCommonNode
{
    public ListNode findFirstCommonNode(ListNode pHead1, ListNode pHead2)
    {
        if (pHead1 == null || pHead2 == null)
            return null;

        ListNode currentNode1 = pHead1;
        ListNode currentNode2 = pHead2;
        int length1 = getLength(currentNode1);
        int lenght2 = getLength(currentNode2);

        if (length1 > lenght2)
        {
            int step = length1 - lenght2;
            // 先遍历链表1，遍历的长度就是两链表的长度差
            while (step > 0)
            {
                currentNode1 = currentNode1.next;
                step--;
            }
        }
        else if (lenght2 > length1)
        {
            int step = lenght2 - length1;
            while (step > 0)
            {
                currentNode2 = currentNode2.next;
                step--;
            }
        }

        //开始齐头并进，直到找到第一个公共结点
        while (currentNode1 != currentNode2)
        {
            currentNode1 = currentNode1.next;
            currentNode2 = currentNode2.next;
        }
        
        return currentNode1;
    }

    private int getLength(ListNode pHead)
    {
        int length = 0;
        ListNode currentNode = pHead;
        while (currentNode != null)
        {
            currentNode = currentNode.next;
            length++;
        }
        return length;
    }
}
```

### 查找
#### 4.二维数组中的查找
P44
&emsp;&emsp;在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

**解析**：首先<font color=red>选取数组中右上角的数字（也可以选取左下角的数字，但不能选择左上角数字或者右下角数字）</font>。如果该数字等于要查找的数字，则查找过程结束；如果该数字大于要查找的数字，则剔除这个数字所在的列；如果该数字小于要查找的数字，则剔除这个数字所在的行。也就是说，<font color=red>如果要查找的数字不在数组的右上角，则每一次都在数组的查找范围中剔除一行**或者**一列</font>，这样每一步都可以缩小查找的范围，直到找到要查找的数字，或者查找范围为空。
```java {.line-numbers, highlight=15}
/*
4.二维数组中的查找
P44
 */

public class FindIn2DArray
{
    public boolean findIn2DArray(int target, int[][] array)
    {
        int rows = array.length - 1;
        int columns = array[0].length - 1;
        if (rows < 0 || columns < 0)
            return false;

        int rowIndex = 0, columnIndex = columns;  // 从右上角开始
        while (rowIndex <= rows && columnIndex >= 0)
        {
            if (target > array[rowIndex][columnIndex])
                rowIndex++;
            else if (target < array[rowIndex][columnIndex])
                columnIndex--;
            else
                return true;
        }
        return false;
    }
}
```

#### 11.旋转数组的最小数字
P82
&emsp;&emsp;把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组{3, 4, 5, 1, 2}（输入的数组）为{l, 2, 3, 4, 5}的一个旋转，该数组的最小值为1 。

**解析**：从头到尾遍历数组一次，就能找出最小的元素。这种思路的时间复杂度显然是$O(n)$。但是这种思路没有利用输入的旋转数组的特性。

旋转之后的数组实际上可以划分为两个排序的子数组，而且前面子数组的元素都大于或者等于后面子数组的元素。<font color=red>最小的元素刚好是这两个子数组的分界线</font>。在排序的数组中可以用二分查找法实现$O(logn)$的查找。

用两个指针分别指向数组的第一个元素和最后一个元素。按旋转的规则，第一个元素应该是大于或者等于最后一个元素的。

接着可以找到数组中间的元素，如果<font color=red>该中间元素位于前面的递增子数组</font>，那么它应该大于或者等于第一个指针指向的元素。此时数组中最小的元素应该位于该中间元素的后面。可以<font color=red>把第一个指针指向该中间元素</font>，这样可以缩小寻找的范围。移动之后的第一个指针仍然位于前面的递增子数组。

同样，如果<font color=red>中间元素位于后面的递增子数组</font>，那么它应该小于或者等于第二个指针指向的元素。此时该数组中最小的元素应该位于该中间元素的前面。我们可以<font color=red>把第二个指针指向该中间元素</font>，这样也可以缩小寻找的范围。移动之后的第二个指针仍然位于后面的递增子数组。

按照上述思路，<font color=red>第一个指针总是指向前面递增数组的元素，而第二个指针总是指向后面递增数组的元素</font>。最终第一个指针将指向前面子数组的最后一个元素， 而第二个指针会指向后面子数组的第一个元素。也就是它们<font color=red>最终会指向两个相邻的元素</font>，而第二个指针指向的刚好是最小的元素。这就是循环结束的条件。

**注意**：
* 在旋转数组中，由于是把递增排序数组前面的若干数字搬到数组的后而，因此第一个数字总是大于或者等于最后一个数字。但按照定义还有一个特例：如果把排序数组的前面的0个元素搬到最后面，即排序数组本身，这仍然是数组的一个旋转，我们的代码需要支持这种情况。此时，数组中的第一个数字就是最小的数字，可以直接返回。
* 当两个指针指向的数字及它们中间的数字三者相同的时候，我们无法判断中间的数字是位于前面的子数组还是后面的子数组，也就无法移动两个指针来缩小查找的范围。此时，我们不得不采用顺序查找的方法。（数组{1,0,1,1,1}和数组{1,1,1,0,1}都可以看成递增排序数组{0,1,1,1,1} 的旋转)

**总结**：
&emsp;&emsp;二分法：`mid = low + (high - low)/2`
需要考虑三种情况：
(1)`array[mid] > array[high]`：出现这种情况的array类似[3,4,5,6,0,1,2]，此时最小数字一定在mid的右边。`low = mid + 1`
(2)`array[mid] == array[high]`：出现这种情况的array类似 [1,0,1,1,1] 或者[1,1,1,0,1]，此时最小数字不好判断在`mid`左边还是右边,这时只好一个一个试。`high = high - 1`
(3)`array[mid] < array[high]`：出现这种情况的array类似[2,2,3,4,5,6,6],此时最小数字一定就是`array[mid]`或者在`mid`的左边。因为右边必然都是递增的。`high = mid`
注意：如果待查询的范围最后只剩两个数，那么`mid`一定会指向下标靠前的数字。比如`array = [4,6]; array[low] = 4; array[mid] = 4; array[high] = 6;`。如果`high = mid - 1`，就会产生错误，因此`high = mid`，但情形(1)中`low = mid + 1`就不会错误。
```java
/*
11.旋转数组的最小数字
 */

public class MinNumberInRotateArray
{
    public int minNumberInRotateArray(int[] array)
    {
        int low = 0, high = array.length - 1;
        while (low < high)
        {
            int mid = low + (high - low) / 2;
            if (array[mid] > array[high])
                low = mid + 1;
            else if (array[mid] == array[high])
                high -= 1;
            else
                high = mid;
        }
        return array[low];
    }
}
```

#### 53.1.数字在排序数组中出现的次数
P263
&emsp;&emsp;统计一个数字在排序数组中出现的次数。例如，输入排序数组{1,2,3,3,3,3,4,5}和数字3，由于3在这个数组中出现了4次，因此输出4。

**解析**：
&emsp;&emsp;既然输入的数组是排序的，那么就能很自然地想到用二分查找算法。在题目给出的例子中，可以先用二分查找符法找到一个3。<font color=red>由于3可能出现多次，因此找到的3的左右两边可能都有3，于是在找到的3的左右两边顺序扫描，分别找出第一个3和最后一个3。因为要查找的数字在长度为n的数组中有可能出现$O(n)$次，所以顺序扫描的时间复杂度是$O(n)$</font>。因此，这种算法的效率和直接从头到尾顺序扫描整个数组统计3出现的次数的方法是一样的。

&emsp;&emsp;接下来思考如何更好地利用二分查找算法。先分析如何用二分查找算法在数组中找到第一个K。二分查找算法总是先拿数组中间的数字和K作比较。如果中间的数字比K大，那么K只有可能出现在数组的前半段，下一轮我们只在数组的前半段查找就可以了。如果中间的数字比K小，那么k只有可能出现在数组的后半段，下一轮我们只在数组的后半段查找就可以了。<font color=red>如果中间的数字和K相等</font>呢？我们<font color=red>先判断这个数字是不是第一个K。如果中间数字的前面一个数字不是K，那么此时中间的数字刚好就是第一个K；如果中间数字的前面一个数字也是K，那么第一个K肯定在数组的前半段，下一轮我们仍然需要在数组的前半段查找。</font>

&emsp;&emsp;如果数据都是整数，可以不搜索K所在的位置，而是<font color=red>搜索(k-0.5)和(k+0.5)这两个数应该插入的位置</font>，然后相减即可：
```java
/*
53.数字在排序数组中出现的次数
 */

public class GetNumberOfK
{
    public int getNumberOfK(int[] array, int k)
    {
        if (array == null || array.length == 0)
            return 0;
        return binarySearch(array, k + 0.5) - binarySearch(array, k - 0.5);
    }

    private int binarySearch(int[] array, double n)
    {
        int low = 0, high = array.length - 1;
        while (low <= high)
        {
            int mid = low + (high - low) / 2;
            if (array[mid] > n)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return low;
    }
}
```

#### 53.2. $0$~$(n-1)$中缺失的数字
P266
&emsp;&emsp;一个长度为$n-1$的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围$0$\~$(n-1)$之内。在范围$0$\~$(n-1)$内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。

**解析**：
&emsp;&emsp;可以先用公式$\frac{n \left ( n-1\right )}{2}$求出数字$0$\~$(n-1)$的所有数字之和，记为S1。接着求出数组中所有数字的和，记为S2。那个不在数组中的数字就是S1-S2的差。这种解法需要$O(n)$的时间求数组中所有数字的和。显然，该解法没有有效利用数组是递增排序的这一特点。

&emsp;&emsp;因为$0$\~$(n-1)$这些数字在数组中是排序的，因此数组中开始的一些数字与它们的下标相同。也就是说，0在下标为0的位置，1在下标为1的位置，以此类推。如果不在数组中的那个数字记为m，那么所有比m小的数字的下标都与它们的值相同。

可以基于二分查找的算法用如下过程查找：
* 如果中间元素的值和下标相等，那么下一轮查找只需要查找右半边；
* 如果中间元素的值和下标不相等，并且它前面一个元素和它的下标相等，这意味着这个中间的数字正好是第一个值和下标不相等的元素，它的下标就是在数组中不存在的数字；
* 如果中间元素的值和下标不相等，并且它前面一个元素和它的下标不相等，这意味着下一轮查找我们只需要在左半边查找即可。
```java
/*
53.2. 0~(n−1)中缺失的数字
 */

public class FindMissingNumber
{
    public static int finMissingNumber(int[] data)
    {
        if (data == null || data.length <= 0)
            return -1;

        int left = 0, right = data.length - 1;
        while (left <= right)
        {
            int mid = left + (right - left) / 2;
            if (data[mid] != mid)
            {
                // 如果中间元素的值和下标不相等，并且它前面一个元素和它的下标相等
                // if (mid == 0 || data[mid - 1] == mid - 1)
                if (data[mid - 1] == mid - 1)
                    return mid;
                else
                {
                    // 如果中间元素的值和下标不相等，并且它前面一个元素和它的下标不相等
                    right = mid - 2;  // 下一轮查找只需要在左半边查找
                }
            }
            else
            {
                // 如果中间元素的值和下标相等，那么下一轮查找只需要查找右半边
                left = mid + 1;
            }
        }
        
        if (left == data.length)
            return data[right];
        
        return -1;
    }
}
```

### 排序
#### 21.使数组中奇数位于偶数前面
P129
&emsp;&emsp;输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

**解析**：
&emsp;&emsp;思路一（冒泡排序）：从头扫描这个数组，每碰到一个偶数，拿出这个数字，并把位于这个数字后面的所有数字往前挪动一位。挪完之后在数组的木尾有一个空位，这时把该偶数放入这个空位。由于每碰到一个偶数就要移动$O(n)$个数字，因此总的时间复杂度是$O(n^2)$。
```java
public class Solution {
    public void reOrderArray(int [] array) {
       for(int i= 0;i<array.length-1;i++){
            for(int j=0;j<array.length-1-i;j++){
                if(array[j]%2==0&&array[j+1]%2==1){
                    int t = array[j];
                    array[j]=array[j+1];
                    array[j+1]=t;
                }
            }
        }
    }
}
```

&emsp;&emsp;思路二：利用两个指针，第一个指针初始化时指向数组的第一个数字，它只向后移动；第二个指针初始化时指向数组的最后一个数字，它只向前移动。在两个指针相遇之前，第一个指针总是位于第二个指针的前面。如果第一个指针指向的数字是偶数，并且第二个指针指向的数字是奇数，则交换这两个数字。（**没有实现相对位置不改变**）

&emsp;&emsp;思路三：
```java {.line-numbers highlight=11}
/*
21.使数组中奇数位于偶数前面
P129
保证奇数和奇数，偶数和偶数之间的相对位置不变
 */

public class ReorderArray
{
    public void reOrderArray(int[] array)
    {
        int k = 0;  // 记录当前已经排好的奇数中最后一个的位置
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] % 2 == 1)  // 找奇数
            {
                int j = i;  // j用来将找到的新的奇数往偶数前面排
                while (j > k)
                {
                    // 将找到的新奇数向前排，排到已排好序的奇数中最后一个数之后
                    int temp = array[j];
                    array[j] = array[j - 1];  
                    array[j - 1] = temp;
                    j--;  // 将找到的奇数往前排
                }
                k++;  // 又找到一个奇数并且排好顺序
            }
        }
    }
}
```

#### 39.数组中出现次数超过一半的数字
P205
&emsp;&emsp;数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。

**解析**：
&emsp;&emsp;思路一：基于`Partition`函数的时间复杂度为$O(n)$的算法

在随机快速排序算法中，我们先在数组中随机选择一个数字，然后调整数组中数字的顺序，使得比选中的数字小的数字都排在它的左边，比选中的数字大的数字都排在它的右边。如果这个选中的数字的下标刚好是$n/2$，那么这个数字就是数组的中位数；如果它的下标大于$n/2$，那么中位数应该位于它的左边，我们可以接着在它的左边部分的数组中查找；如果它的下标小于$n/2$，那么中位数应该位于它的右边，我们可以接着在它的右边部分的数组中查找。这是一个典型的递归过程。（时间复杂度$O(n)$，需要修改数组）

&emsp;&emsp;思路二：在遍历数组的时候保存两个值：一个是数组中的一个数字；另一个是次数。当我们遍历到下一个数字的时候，如果下一个数字和我们之前保存的数字相同，则次数加1；如果下一个数字和我们之前保存的数字不同，则次数减1。<font color=red>如果次数为零，那么我们需要保存下一个数字，并把次数设为1。由于我们要找的数字出现的次数比其他所有数字出现的次数之和还要多，那么要找的数字肯定是最后一次把次数设为1时对应的数字。</font>（时间复杂度$O(n)$，不需要修改数组）
```java
/*
39.数组中出现次数超过一半的数字
P205
 */

public class MoreThanHalfNum
{
    public int moreThanHalfNum(int[] array)
    {
        int temp = 0;
        int count = 0;
        // 找出疑似数字
        for (int i = 0; i < array.length; i++)
        {
            if (count == 0)
                temp = array[i];
            if (temp == array[i])
                count++;
            else
                count--;
        }

        // 确认数字出现的次数是否过半
        count = 0;
        /*
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == temp)
                count++;
        }*/
        
        for (int item : array)
        {
            if (item == temp)
                count++;
        }

        return count > array.length / 2 ? temp : 0;
    }
}
```

#### 40.最小的k个数
P209
&emsp;&emsp;输入$n$个整数，找出其中最小的$k$个数。

**解析**：
&emsp;&emsp;思路一：把输入的$n$个整数排序，排序之后位于最前面的$k$个数就是最小的$k$个数。
```java
/*
40.最小的k个数
P209
 */

import java.util.ArrayList;

public class GetLeastNumbers
{
    public ArrayList<Integer> getLeastNumbers(int[] input, int k)
    {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (input == null || k > input.length || k == 0)
            return arrayList;
        for (int i = 0; i < k; i++)  // 只需要找到k个数即可
        {
            for (int j = 0; j < input.length - 1 - i; j++)
            {
                if (input[j] < input[j + 1])
                {
                    int temp = input[j + 1];
                    input[j + 1] = input[j];
                    input[j] = temp;
                }
            }

            arrayList.add(input[input.length - 1 - i]);
        }
        
        return arrayList;
    }

}
```

&emsp;&emsp;思路二：可以基于`Partition`函数来解决这个问题，时间复杂度是$O(n)$。如果基于数组的第$k$个数字来调整，则使得比第$k$个数字小的所有数字都位于数组的左边，比第$k$个数字大的所有数字都位于数组的右边。这样调整之后，位于数组中左边的$k$个数字就是最小的$k$个数字（这$k$个数字不一定是排序的）。

&emsp;&emsp;思路三：适用于处理海量数据，时间复杂度是$O(nlogk)$。

先创建一个大小为$k$的数据容器来存储最小的$k$个数字，接下来每次从输入的$n$个整数中读入一个数。如果容器中已有的数字少于$k$个，则直接把这次读入的整数放入容器之中；如果容器中已有$k$个数字了，则找出这已有的$k$个数中的最大值，然后拿这次待插入的整数和最大值进行比较。如果待插入的值比当前已有的最大值小，则用这个数替换当前已有的最大值；如果待插入的值比当前已有的最大值还要大，那么可以抛弃这个整数。因此，当容器满了之后，我们要做3件事情：一是在$k$个整数中找到最大数；二是有可能在这个容器中删除最大数；三是有可能要插入一个新的数字。如果用<font color=red>一棵二叉树</font>来实现这个数据容器，那么我们能在$O(logk)$时间内实现这3步操作。因此，对于$n$个输入数字而言，总的时间效率就是$O(nlogk)$。

#### 41.数据流中的中位数（待更）
P214
&emsp;&emsp;如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。

#### 45.把数组排列成最小的数
P227
&emsp;&emsp;输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如，输入数组{3,32,321}，则打印出这3个数字能排成的最小数字321323。

**解析**：
&emsp;&emsp;思路一：先求出这个数组中所有数字的全排列，然后把每个排列拼起来，最后求出拼起来的数字的最小值。$n$个数字总共有$n!$个排列。

&emsp;&emsp;思路二：两个数字$m$和$n$能拼接成数字$mn$和$nm$。如果$mn < nm$，那么应该打印出$mn$，也就是$m$应该排在$n$的前面，定义此时$m$“小于”$n$；反之，如果$nm < mn$，则定义$n$“小于”$m$；如果$mn=nm$，则$m$“等于”$n$。

接下来考虑怎么去拼接数字，即给出数字$m$和$n$，怎么得到数字$mn$和$nm$并比较它们的大小。直接用数值去计算不难办到，但需要考虑的一个潜在问题就是$m$和$n$都在`int`型能表达的范围内，但把它们拼接起来的数字$mn$和$nm$用`int`型表示就有可能溢出了，所以这还是一个隐形的<font color=red>大数问题</font>。

一个非常直观的解决大数问题的方法就是<font color=red>把数字转换成字符串</font>。另外<font color=red>由于把数字$m$和$n$拼接起来得到$mn$和$nm$，它们的位数肯定是相同的，因此比较它们的大小只需要按照字符串大小的比较规则就可以了</font>。

```java {.line-numbers highlight=11}
import java.util.ArrayList;
public class Solution 
{
    public String PrintMinNumber(int [] numbers) 
    {
        if(numbers == null || numbers.length == 0)return "";
        for(int i=0; i < numbers.length; i++)
        {
            for(int j = i+1; j < numbers.length; j++)
            {
                int sum1 = Integer.valueOf(numbers[i]+""+numbers[j]);
                int sum2 = Integer.valueOf(numbers[j]+""+numbers[i]);
                if(sum1 > sum2)
                {
                    int temp = numbers[j];
                    numbers[j] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }
        String str = new String("");
        for(int i=0; i < numbers.length; i++)
            str = str + numbers[i];
        return str;
    }
}
```
注意第11行代码，这里并没有解决“大数问题”！

```java
/*
45.把数组排列成最小的数
P227
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PrintMinNumber
{
    public String printMinNumber(int[] numbers)
    {
        String string = "";
        ArrayList<Integer> list = new ArrayList<>();

        for (int number : numbers)
            list.add(number);  // 将数组放入ArrayList中

        //实现Comparator接口的compare方法，将集合元素按照compare方法的规则进行排序
        Collections.sort(list, new Comparator<Integer>() 
        {
            @Override
            public int compare(Integer o1, Integer o2) 
            {
                String str1 = o1 + "" + o2;
                String str2 = o2 + "" + o1;
                return str1.compareTo(str2);
            }
        });
        
        for (int j : list)
            string += j;
        return string;
    }
}
```

#### 51.数组中的逆序对
P249
&emsp;&emsp;在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。例如，在数组{7, 5, 6, 4}中，一共存在5个逆序对，分别是(7, 6)、(7, 5)、(7, 4)、(6, 4)和(5, 4)。（并将P对1000000007取模的结果输出。即输出P%1000000007）

**解析**：
&emsp;&emsp;思路一：顺序扫描整个数组。每扫描到一个数字，逐个比较该数字和它后面的数字的大小。如果后面的数字比它小，则这两个数字就组成一个逆序对。假设数组中含有$n$个数字。由于每个数字都要和$O(n)$个数字进行比较，因此这种算法的时间复杂度是$O(n^2)$。

&emsp;&emsp;思路二：先把数组分隔成子数组，统计出子数组内部的逆序对的数目， 然后再统计出两个相邻子数组之间的逆序对的数目。在统计逆序对的过程中，还需要对数组利用归并排序算法进行排序。

统计数组中逆序对的过程：
<div align=center><img src=PointToOffer_Images/统计数组中逆序对的过程.png></div>

合并两个子数组井统计逆序对的过程：
<div align=center><img src=PointToOffer_Images/合并两个子数组井统计逆序对的过程.png></div>

先用两个指针分别指向两个子数组的末尾，并每次比较两个指针指向的数字。如果第一个子数组中的数字大于第二个子数组中的数字，则构成逆序对，并且逆序对的数目等于第二个子数组中剩余数字的个数，如图(a)和图(c)所示。如果第一个数组中的数字小于或等第二个数组中的数字，则不构成逆序对，如图(b)所示。每次比较的时候，都把较大的数字从后往前复制到一个辅助数组，确保辅助数组中的数字是递增排序的。在把较大的数字复制到辅助数组之后，把对应的指针向前移动一位，接下来进行下一轮比较。

&emsp;&emsp;归并排序的时间复朵度是$O(nlogn)$，比最直观的$O(n^2)$要快，但同时归并排序需要一个长度为$n$的辅助数组，相当于用$O(n)$的空间消耗换来了时间效率的提升，因此这是一种用空间换时间的算法。
```java
/*
51.数组中的逆序对
P249
 */

public class InversePairs
{
    private int count;

    public int inversePairs(int[] array)
    {
        MergeSort(array, 0, array.length - 1);
        return count;
    }

    private void MergeSort(int[] array, int start, int end)
    {
        if (start >= end)
            return;
        int mid = (start + end) / 2;
        MergeSort(array, start, mid);
        MergeSort(array, mid + 1, end);
        MergeOne(array, start, mid, end);
    }

    private void MergeOne(int[] array, int start, int mid, int end)
    {
        int[] temp = new int[end - start + 1];
        int k = 0, i = start, j = mid + 1;
        while (i <= mid && j <= end)
        {
            //如果前面的元素小于后面的不能构成逆序对
            if (array[i] <= array[j])
                temp[k++] = array[i++];  // 以增序来添加
            else
            {
                //如果前面的元素大于后面的，那么在前面元素之后的元素都能和后面的元素构成逆序对
                temp[k++] = array[j++];
                count = (count + (mid - i + 1)) % 1000000007;
            }
        }

        while (i <= mid)
            temp[k++] = array[i++];
        while (j <= end)
            temp[k++] = array[j++];
        for (int m = 0; m < k; m++)
            array[start + m] = temp[m];
    }
}
```