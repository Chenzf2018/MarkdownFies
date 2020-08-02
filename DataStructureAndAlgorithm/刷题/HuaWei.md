# 1.字符串最后一个单词的长度

计算字符串最后一个单词的长度，单词以空格隔开。

输入描述：一行字符串，非空，长度小于5000。
输出描述：整数N，最后一个单词的长度。

```
输入：hello world

输出：5
```

```java
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        String string = "";
        while(input.hasNextLine()){
            string = input.nextLine();
            String[] subString = string.split(" ");
            int n = subString[subString.length - 1].length();
            System.out.println(n);
        }
    }
}
```

# 2.计算字符个数

写出一个程序，接受一个由**字母和数字**组成的字符串，和一个字符，然后输出**输入字符串中含有该字符的个数**。**不区分大小写**。

输入描述:
第一行输入一个有字母和数字组成的字符串，第二行输入一个字符。

输出描述:
输出输入字符串中含有该字符的个数。

```
输入

ABCDEF
A

输出

1
```

```java
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        String string = input.nextLine();
        char ch = input.next().charAt(0);
        System.out.println(getNum(string, ch));
    }
    
    public static int getNum(String string, char ch){
        int length = string.length();
        int num = 0;
        
        for(int i = 0; i < length; i++){
            if(string.toLowerCase().charAt(i) == ch || string.toUpperCase().charAt(i) == ch)
                num++;
        }
        
        return num;
    }
}
```



# 3.明明的随机数

明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机**生成了N个1到1000之间的随机整数（N≤1000），对于其中重复的数字，只保留一个，把其余相同的数去掉**，不同的数对应着不同的学生的学号。然后**再把这些数从小到大排序**，按照排好的顺序去找同学做调查。请你协助明明完成**去重**与**排序**的工作(同一个测试用例里可能会有多组数据，希望大家能正确处理)。


```
Input Param

n               输入随机数的个数

inputArray      输入n个随机整数，组成数组


Return Value

OutputArray    输出处理后的随机整数

样例有两组测试
第一组数字，分别是：2（随机数个数），2，1。
第二组数字，分别是：10（随机数个数），20，40，32，67，40，20，89，300，400，15。
```

```java
import java.util.Scanner;
import java.util.TreeSet;

public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        // hasNext检测是否还有下一个输入；hasNextLine检测是否还有下一行输入
        while(input.hasNext()){
            // num表示共有多少个数要输入
            int num = input.nextInt();
            // TreeSet保证有序不重复
            TreeSet<Integer> treeSet = new TreeSet<>();
            for(int i = 0; i < num; i++){
                int current = input.nextInt();
                treeSet.add(current);
            }
            
            for(Integer i : treeSet)
                System.out.println(i);
        }
    }
}
```

# 4.字符串分隔

连续输入字符串，请**按长度为8拆分每个字符串后输出到新的字符串数组**；长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。

输入描述:
连续输入字符串(输入2次,每个字符串长度小于100)

输出描述:
输出到长度为8的新字符串数组

```
输入

abc
123456789

输出

abc00000
12345678
90000000
```

```java
import java.util.Scanner;

public class Main5 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("请输入字符串：");  // 线上测试时，注释掉这一行
        while(input.hasNextLine()){
            String string = input.nextLine();
            splitString(string);
        }
    }

    private static void splitString(String string){
        while(string.length() >= 8){
            System.out.println(string.substring(0, 8));
            string = string.substring(8);
        }

        if(string.length() < 8 && string.length() > 0){
            string += "00000000";
            System.out.println(string.substring(0, 8));
        }
    }
}
```

# 5.进制转换

写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。（多组同时输入）

输入描述：输入一个十六进制的数值字符串。

输出描述：输出该数值的十进制字符串。

```
输入：
0xA

输出：
10
```

```java
import java.util.Scanner;
import java.lang.Math;

public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        while(input.hasNext()){
            String str = input.nextLine();
            System.out.println(transfer(str.substring(2)));
        }
    }
    
    public static int transfer(String str){
        int result = 0;
        for(int i = str.length() - 1; i >= 0; i--){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9')
                result += (str.charAt(i) - '0' + 0) * Math.pow(16, (str.length() - 1 - i));
            if(str.charAt(i) >= 'A' && str.charAt(i) <= 'F')
                result += (str.charAt(i) - 'A' + 10) * Math.pow(16, (str.length() - 1 - i));
            if(str.charAt(i) >= 'a' && str.charAt(i) <= 'f')
                result += (str.charAt(i) - 'a' + 10) * Math.pow(16, (str.length() - 1 - i));
        }
        return result;
    }
    
    /*
    public static int transfer(String str){
        int result = 0, count = 0, temp = 0;
        char ch;
        while(count < str.length()){
            ch = str.charAt(str.length() - 1 - count);
            if(ch >= '0' && ch <= '9')
                temp = ch - '0';
            else if(ch >= 'A' && ch <= 'F')
                temp = ch - 'A' + 10;
            else if(ch >= 'a' && ch <= 'f')
                temp = ch - 'a' + 10;
            result += temp * Math.pow(16, count);
            count++;
        }
        
        return result;
    }*/
}
```


# 6.质数因子

输入一个正整数（输入一个`long`型整数），按照从小到大的顺序输出它的所有质因子（重复的也要列举）（如180的质因子为2 2 3 3 5 ）最后一个数后面也要有空格。


# 7.取近似值

写出一个程序，接受一个正浮点数值，输出该数值的近似整数值。如果小数点后数值大于等于5，向上取整；小于5，则向下取整。

```java
import java.util.Scanner;

/**
 * # 6.取近似值
 */

public class Main6 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("请输入一个浮点数：");
        double num = input.nextDouble();
        System.out.println(getReturn(num));
    }

    private static int getReturn(double num){
        int temp = (int) num;
        return (num - temp) >= 0.5 ? temp + 1 : temp;
    }
}
```



# 10.字符个数统计

**题目描述**

编写一个函数，**计算字符串中含有的不同字符的个数**。字符在ACSII码范围内(0~127)，换行表示结束符，不算在字符里。不在范围内的不作统计。多个相同的字符只计算一次。

```
输入
abaca
输出
3
```

```java
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String string = new Scanner(System.in).nextLine();
        System.out.println(getDiffNum(string));
    }
    
    private static int getDiffNum(String string) {
        List<Character> arrayList = new ArrayList<>();
        for (char ch : string.toCharArray()) {
            if (! arrayList.contains(ch)) {
                arrayList.add(ch);
            }
        }
        
        return arrayList.size();
    }
}
```

# 11.数字颠倒

**题目描述**

输入一个整数，**将这个整数以字符串的形式逆序输出**。程序不考虑负数的情况，若数字含有0，则逆序形式也含有0，如输入为100，则输出为001

```
输入描述:
输入一个int整数
1516000

输出描述:
将这个整数以字符串的形式逆序输出
0006151
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String string = reader.readLine();
            for (int i = string.length() - 1; i >= 0; i--) {
                System.out.print(string.charAt(i));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 12.字符串反转

**题目描述**

写出一个程序，接受一个字符串，然后输出该字符串反转后的字符串。（字符串长度不超过1000）

```
输入
abcd

输出
dcba
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String string = reader.readLine();
            for (int i = string.length() - 1; i >= 0; i--) {
                System.out.print(string.charAt(i));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 14.按字典序排列字符串(字串的连接最长路径查找)

**题目描述**

给定n个字符串，请**对n个字符串按照字典序排列**。

输入描述:
输入第一行为一个正整数n(1≤n≤1000)，下面n行为n个字符串(字符串长度≤100)，字符串中只含有大小写字母。

输出描述:
数据输出n行，输出结果为按照字典序排列的字符串。

```
输入
9
cap
to
cat
card
two
too
up
boat
boot

输出
boat
boot
cap
card
cat
to
too
two
up
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int num = Integer.parseInt(reader.readLine());
            String[] strings = new String[num];
            for (int i = 0; i < num; i++) {
                strings[i] = reader.readLine();
            }
            
            Arrays.sort(strings);
            
            for (int i = 0; i < num; i++) {
                System.out.println(strings[i]);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 17.坐标移动

**题目描述**

开发一个坐标计算工具， A表示向左移动，D表示向右移动，W表示向上移动，S表示向下移动。从（0,0）点开始移动，从输入字符串里面读取一些坐标，并将最终输入结果输出到输出文件里面。

输入：

合法坐标为A(或者D或者W或者S) + 数字（两位以内）

坐标之间以;分隔。

非法坐标点需要进行丢弃。如AA10;  A1A;  $%$;  YAD; 等。

下面是一个简单的例子 如：

A10;S20;W10;D30;X;A1A;B10A11;;A10;

处理过程：

起点（0,0）

+   A10   =  （-10,0）

+   S20   =  (-10,-20)

+   W10  =  (-10,-10)

+   D30  =  (20,-10)

+   x    =  无效

+   A1A   =  无效

+   B10A11   =  无效

+  一个空 不影响

+   A10  =  (10,-10)

结果 （10， -10）

```
输入
A10;S20;W10;D30;X;A1A;B10A11;;A10;

输出
10,-10
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = null;

            while ((str = reader.readLine()) != null) {
                String[] strings = str.split(";");
                int x = 0, y = 0;
                
                for (int i = 0; i < strings.length; i++) {
                    char[] ch = strings[i].toCharArray();
                    int step = 0;
                    // 遍历方向符号后面的数字
                    for (int j = 1; j < ch.length; j++) {
                        if (ch[j] >= '0' && ch[j] <= '9') {
                            step = step * 10 + (ch[j] - '0');
                        } else {
                            // 不满足条件，跳出for循环，step=0
                            break;
                        }
                    }
                    
                    switch (ch[0]) {
                        case 'A' : x -= step; break;
                        case 'D' : x += step; break;
                        case 'W' : y += step; break;
                        case 'S' : y -= step; break;
                        //default;
                    }
                }
                
                System.out.println(x + "," + y);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 18.(未实现)识别有效的IP地址和掩码并进行分类统计

**题目描述**

请解析IP地址和对应的掩码，进行分类识别。要求按照A/B/C/D/E类地址归类，不合法的地址和掩码单独归类。

所有的IP地址划分为 A,B,C,D,E五类

A类地址1.0.0.0~126.255.255.255;

B类地址128.0.0.0~191.255.255.255;

C类地址192.0.0.0~223.255.255.255;

D类地址224.0.0.0~239.255.255.255；

E类地址240.0.0.0~255.255.255.255


私网IP范围是：

10.0.0.0～10.255.255.255

172.16.0.0～172.31.255.255

192.168.0.0～192.168.255.255


子网掩码为二进制下前面是连续的1，然后全是0。（例如：255.255.255.32就是一个非法的掩码）
注意二进制下全是1或者全是0均为非法

注意：
1. 类似于[0.\*.\*.\*]和[127.\*.\*.\*]的IP地址不属于上述输入的任意一类，也不属于不合法ip地址，计数时可以忽略
2. 私有IP地址和A,B,C,D,E类地址是不冲突的

输入描述:
多行字符串。每行一个IP地址和掩码，用~隔开。

输出描述:
统计A、B、C、D、E、错误IP地址或错误掩码、私有IP的**个数**，之间以空格隔开。

```
输入
10.70.44.68~255.254.255.0
1.0.0.1~255.0.0.0
192.168.0.2~255.255.255.0
19..0.~255.255.255.0

输出
1 0 1 0 0 2 1
```

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
       
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       
        int a = 0, b = 0, c = 0, d = 0, e = 0;
        int err = 0;
        int pri = 0;
        String str;
        String[] ip_mask;
        String[] ip;
        int i;
       
        while ((str = br.readLine()) != null) {
            ip_mask = str.split("~");
            ip = ip_mask[0].split("\\.");
            // count error mask
            if (checkMask(ip_mask[1])) { // mask correct
                // count ip
                if (checkIP(ip)) {
                    i = Integer.parseInt(ip[0]);
                    if (i >= 1 && i <= 126) { // A
                        a++;
                        if (i == 10) {
                            pri++;
                        }
                    } else if (i >= 128 && i <= 191) { // B
                        b++;
                        if (i == 172 && Integer.parseInt(ip[1]) >= 16 && Integer.parseInt(ip[1]) <= 31) {
                            pri++;
                        }
                    } else if (i >= 192 && i <= 223) { // C
                        c++;
                        if (i == 192 && Integer.parseInt(ip[1]) == 168) {
                            pri++;
                        }
                    } else if (i >= 224 && i <= 239) { // D
                        d++;
                    } else if (i >= 240 && i <= 255) { // E
                        e++;
                    }
                } else {
                    err++;
                }
            } else {
                err++;
            }
        }
       
        // output
        System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + err + " " + pri);
    }
       
    private static boolean checkMask(String mask) {
        // check mask
        String[] mask_arr = mask.split("\\.");
        if (mask_arr[0].equals("255")) {
            if (mask_arr[1].equals("255")) {
                if (mask_arr[2].equals("255")) {
                    return mask_arr[3].equals("254") || mask_arr[3].equals("252") || mask_arr[3].equals("248") ||
                            mask_arr[3].equals("240") || mask_arr[3].equals("224") || mask_arr[3].equals("192") ||
                            mask_arr[3].equals("128") || mask_arr[3].equals("0");
                } else if (mask_arr[2].equals("254") || mask_arr[2].equals("252") || mask_arr[2].equals("248") ||
                        mask_arr[2].equals("240") || mask_arr[2].equals("224") || mask_arr[2].equals("192") ||
                        mask_arr[2].equals("128") || mask_arr[2].equals("0")) {
                    return mask_arr[3].equals("0");
                } else
                    return false;
            } else if (mask_arr[1].equals("254") || mask_arr[1].equals("252") || mask_arr[1].equals("248") ||
                    mask_arr[1].equals("240") || mask_arr[1].equals("224") || mask_arr[1].equals("192") ||
                    mask_arr[1].equals("128") || mask_arr[1].equals("0")) {
                return mask_arr[2].equals("0") && mask_arr[3].equals("0");
            } else {
                return false;
            }
        } else if (mask_arr[0].equals("254") || mask_arr[0].equals("252") || mask_arr[0].equals("248") ||
                mask_arr[0].equals("240") || mask_arr[0].equals("224") || mask_arr[0].equals("192") ||
                mask_arr[0].equals("128") || mask_arr[0].equals("0")) {
            return mask_arr[1].equals("0") && mask_arr[2].equals("0") && mask_arr[3].equals("0");
        } else {
            return false;
        }
    }
       
    private static boolean checkIP(String[] ip) {
        return ip.length == 4 && !ip[0].equals("") && !ip[1].equals("") && !ip[2].equals("") && !ip[3].equals("");
    }
}
```

# 19.简单错误记录

题目描述
开发一个简单错误记录功能小模块，能够记录出错的代码所在的文件名称和行号。


处理：

1. 记录最多8条错误记录，循环记录（或者说最后**只输出最后出现的八条错误记录**），对**相同的错误记录**（净文件名（保留最后16位）称和行号完全匹配）只记录一条，**错误计数增加**；

2. **超过16个字符的文件名称，只记录文件的最后有效16个字符**；

3. 输入的文件可能带路径，记录文件名称不能带路径。

输入描述:
一行或多行字符串。每行包括带路径文件名称，行号，以空格隔开。

输出描述:
将所有的记录统计并将结果输出，格式：文件名 代码行数 数目，一个空格隔开

```
输入
E:\V1R2\product\fpgadrive.c   1325

输出
fpgadrive.c 1325 1
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            //记录个数，故使用Map，不需要排序使用HashMap，
            //最后只输出最后出现的八条错误记录，需要按照输入顺序输出，故使用LinkedHashMap
            Map<String, Integer> linkedHashMap = new LinkedHashMap<>();

            while ((str = reader.readLine()) != null) {
                String[] strings = str.split(" ");
                String filePath = strings[0];
                String lineNumber = strings[1];
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                // 超过16个字符的文件名称，只记录文件的最后有效16个字符
                if(fileName.length() > 16) {
                    fileName = fileName.substring(fileName.length() - 16);
                }
                String err = fileName + " " + lineNumber;

                if (linkedHashMap.containsKey(err)) {
                    linkedHashMap.put(err, linkedHashMap.get(err) + 1);
                } else {
                    linkedHashMap.put(err, 1);
                }

            }

            // 输出最后8个记录
            int count = 0;
            // keySet()返回此映射中包含的键的Set视图
            for (String string : linkedHashMap.keySet()) {
                count++;
                if (count > linkedHashMap.keySet().size() - 8) {
                    System.out.println(string + " " + linkedHashMap.get(string));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 20.密码验证合格程序

密码要求:
1. 长度超过8位
2. 包括大小写字母、数字、其它符号、以上四种至少三种
3. 不能有相同长度大于等于2的子串重复

```
输入
021Abc9000
021Abc9Abc1
021ABC9000
021$bc9000

输出
OK
NG
NG
OK
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String password = input.nextLine();
            if (checkLength(password) && checkCharKinds(password) && checkCharRepeat(password)) {
                System.out.println("OK");
            } else {
                System.out.println("NG");
            }
        }
    }
    
    // 1.长度超过8位
    private static boolean checkLength(String password) {
        if (password.length() <= 8 || password == null) {
            return false;
        } else {
            return true;
        }
    }
    
    // 2.包括大小写字母、数字、其它符号，以上四种至少三种
    private static boolean checkCharKinds(String password) {
        int digit = 0, lowercase = 0, uppercase = 0, others = 0;
        char[] ch = password.toCharArray();
        for (int i = 0; i < password.length(); i++) {
            if (ch[i] >= '0' && ch[i] <= '9') {
                digit = 1;
            } else if (ch[i] >= 'a' && ch[i] <= 'z') {
                lowercase = 1;
            } else if (ch[i] >= 'A' && ch[i] <= 'Z') {
                uppercase = 1;
            } else {
                others = 1;
            }
        }
        
        int total = digit + lowercase + uppercase + others;
        return total >= 3 ? true : false;
    }
    
    // 3.不能有相同长度超2的子串重复
    private static boolean checkCharRepeat(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            String substrs = password.substring(i, i + 3);
            if (password.substring(i + 2).contains(substrs)) {
                return false;
            }
        }
        
        return true;
    }
}
```




# 21.简单密码破解

**题目描述**

假设渊子原来一个BBS上的密码为zvbo9441987，为了方便记忆，他通过一种算法把这个密码变换成YUANzhi1987，这个密码是他的名字和出生年份，怎么忘都忘不了，而且可以明目张胆地放在显眼的地方而不被别人知道真正的密码。

他是这么变换的，大家都知道手机上的字母：
`1--1，abc--2，def--3，ghi--4，jkl--5，mno--6，pqrs--7，tuv--8，wxyz--9，0--0`，

渊子把密码中出现的**小写字母都变成对应的数字，数字和其他的符号都不做变换**。

声明：密码中没有空格，而**密码中出现的大写字母变成小写之后往后移一位，如：X，先变成小写，再往后移一位，就是y。z往后移是a**。

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println(password(input.nextLine()));
    }
    
    private static String password(String string) {
        if (string == null || string.equals("")) {
            return string;
        }
        
        char[] chars = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : chars) {
            if (ch >= 'a' && ch <= 'z') {
                if (ch == 's' || ch == 'v' || ch == 'y' || ch == 'z') {
                    stringBuilder.append((ch - 'a') / 3 + 1);
                } else {
                    stringBuilder.append((ch - 'a') / 3 + 2);
                }
            } else if (ch >= 'A' && ch <= 'Z') {
                if (ch == 'Z') {
                    stringBuilder.append('a');
                } else {
                    stringBuilder.append((char)(ch - 'A' + 'a' + 1));
                }
            } else {
                stringBuilder.append(ch);
            }
        }
        
        return stringBuilder.toString();
    }
}
```


# 22.汽水瓶

“某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，**这时候剩2个空瓶子。然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板**。如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？

```java
import java.util.Scanner;

/**
 * 7.汽水瓶
 */

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        //System.out.println("请输入空汽水瓶数量：");
        while(input.hasNext()){
            int num = input.nextInt();
            System.out.println(getResult(num));
        }
    }

    private static int getResult(int num){
        int result = 0;
        while(num > 1){
            if (num == 2)
                num++;
            num -= 3;
            if (num >= 0 ){
                num++;
                result++;
            }
        }
        return result;
    }
}
```



# 23.删除字符串中出现次数最少的字符

**题目描述**

实现**删除字符串中出现次数最少的字符，若多个字符出现次数一样，则都删除**。输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。

注意**每个输入文件有多组输入，即多个字符串用回车隔开**。

输入描述:
字符串只包含小写英文字母，不考虑非法输入，输入的字符串长度小于等于20个字节。

输出描述:
删除字符串中出现次数最少的字符后的字符串。

```
输入
abcdd

输出
dd
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] aegs) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String strings = null;
            // 多组输入
            while ((strings = reader.readLine()) != null) {
                Map<Character, Integer> hashMap = new HashMap<>();
                char[] chars = strings.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (hashMap.get(chars[i]) == null) {
                        hashMap.put(chars[i], 1);
                    } else {
                        hashMap.put(chars[i], hashMap.get(chars[i]) + 1);
                    }
                }
                // 输入的字符串长度小于等于20个字节，故出现次数最少的字符可能为20
                int min = 20;
                for (char ch : hashMap.keySet()) {
                    if (hashMap.get(ch) < min) {
                        min = hashMap.get(ch);
                    }
                }
                
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < chars.length; i++) {
                    if (hashMap.get(chars[i]) > min) {
                        stringBuilder.append(chars[i]);
                    }
                }
                
                System.out.println(stringBuilder.toString());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 26.字符串排序

**题目描述**

编写一个程序，将输入字符串中的字符按如下规则排序。

- 规则1：英文字母从 A 到 Z 排列，不区分大小写。
  如，输入：Type 输出：epTy

- 规则2：同一个英文字母的大小写同时存在时，按照输入顺序排列。
  如，输入：BabA 输出：aABb

- 规则3：非英文字母的其它字符保持原来的位置。
  如，输入：By?e 输出：Be?y

注意有多组测试数据，即输入有多行，每一行单独处理（换行符隔开的表示不同行）

```
输入
A Famous Saying: Much Ado About Nothing (2012/8).

输出
A aaAAbc dFgghh: iimM nNn oooos Sttuuuy (2012/8).
```


```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String string = null;
            // 输入有多行，每一行单独处理
            while ((string = reader.readLine()) != null) {
                char[] chars = string.toCharArray();
                StringBuilder stringBuilder = new StringBuilder();
                // 首先取出输入字符串的所有字母，按顺序放入stringBuilder中
                // 英文字母从 A 到 Z 排列，不区分大小写：26 个
                for (int i = 0; i < 26; i++) {
                    char ch = (char)('A' + i);
                    // 遍历字符串
                    for (int j = 0, len = string.length(); j < len; j++) {
                        if (chars[j] == ch || chars[j] - 'a' + 'A' == ch) {
                            stringBuilder.append(chars[j]);
                        }
                    }
                }
                
                // 然后遍历输入字符串，非字母位置不变放入输出数组
                for (int i = 0, len = string.length(); i < len; i++) {
                    boolean condition = (chars[i] >= 'A' && chars[i] <= 'Z') || 
                        (chars[i] >= 'a' && chars[i] <= 'z');
                    if (! condition) {
                        stringBuilder.insert(i, chars[i]);
                    }
                }
                
                System.out.println(stringBuilder.toString());
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 29.字符串加解密

**题目描述**

1. 对输入的字符串进行加解密，并输出。
2. 加密方法为：
   - 当内容是英文字母时则**用该英文字母的后一个字母替换**，同时**字母变换大小写**，如字母a时则替换为B；字母Z时则替换为a；
   - 当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；
   - 其他字符不做变化。

3. 解密方法为加密的逆过程。


**接口描述：**

实现接口，每个接口实现1个基本操作：

`void Encrypt (char aucPassword[], char aucResult[])`：在该函数中实现字符串加密并输出

说明：

1. 字符串以\0结尾。
2. 字符串最长100个字符。


`int unEncrypt (char result[], char password[])`：在该函数中实现字符串解密并输出

说明：

1. 字符串以\0结尾。
2. 字符串最长100个字符。

```
输入
输入一串要加密的密码：abcdefg
输入一串要解密的密码：BCDEFGH

输出
输出加密后的字符：BCDEFGH
输出解密后的字符：abcdefg
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String str1 = input.nextLine();
            String str2 = input.nextLine();
            System.out.println(enCryption(str1));
            System.out.println(deCryption(str2));
        }
    }
    
    private static String enCryption(String str) {
        char[] chars = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            stringBuffer.append(encryptionChar(chars[i]));
        }
        
        return stringBuffer.toString();
    }
    
    // 字符a对应的ascii码值是97。字符A对应的ascii码值是65。
    private static char encryptionChar(char ch) {
        if (ch >= 'a' && ch < 'z') return (char)(ch - 'a' + 'A' + 1);
        else if (ch == 'z') return 'A';
        else if (ch >= 'A' && ch < 'Z') return (char)(ch - 'A' + 'a' + 1);
        else if (ch == 'Z') return 'a';
        else if (ch >= '0' && ch < '9') return (char)(ch + 1);
        else if (ch == '9') return '0';
        else return ch;
    }
    
    private static String deCryption(String str) {
        char[] chars = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            stringBuffer.append(decryptionChar(chars[i]));
        }
        
        return stringBuffer.toString();
    }
    
    private static char decryptionChar(char ch) {
        if (ch > 'a' && ch <= 'z') return (char)(ch - 'a' + 'A' - 1);
        else if (ch == 'a') return 'Z';
        else if (ch > 'A' && ch <= 'Z') return (char)(ch - 'A' + 'a' - 1);
        else if (ch == 'A') return 'z';
        else if (ch > '0' && ch <= '9') return (char)(ch - 1);
        else if (ch == '0') return '9';
        else return ch;
    }
}
```

# 30.字符串合并处理

将输入的两个字符串合并。

**对合并后的字符串进行排序**，要求为：**下标为奇数的字符和下标为偶数的字符分别从小到大排序**。这里的下标意思是字符在字符串中的位置。

**对排序后的字符串进行转换**，如果字符为`‘0’—‘9’`或者`‘A’—‘F’`或者`‘a’—‘f’`，则对他们所代表的16进制的数进行bit倒序的操作，并转换为相应的大写字符。如字符为`‘4’`，为`0100b`，则翻转后为`0010b`，也就是`2`。转换后的字符为`‘2’`；如字符为`‘7’`，为`0111b`，则翻转后为`1110b`，也就是`e`。转换后的字符为大写`‘E’`。


举例：输入`str1`为`"dec"`，`str2`为`"fab"`，合并为`“decfab”`，分别对`“dca”`和`“efb”`进行排序，排序后为`“abcedf”`，转换后为`“5D37BF”`

接口设计及说明：
```
/*
功能:字符串处理
输入:两个字符串，需要异常处理
输出:合并处理后的字符串，具体要求参考文档
返回:无
*/
void ProcessString(char[] str1, char[] str2, char[] strOutput) {}
```

```java
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String str = input.next();
            str += input.next();
            System.out.println(processString(str));
        }
    }
    
    private static String processString(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        
        int length = str.length();
        
        ArrayList<Character> odd = new ArrayList<>();
        ArrayList<Character> even = new ArrayList<>();
        
        //奇数和偶数分开存到集合中
        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                even.add(str.charAt(i));
            } else {
                odd.add(str.charAt(i));
            }
        }
        
        // 排序
        Collections.sort(odd);
        Collections.sort(even);
        
        // 把排序后的奇数偶数重新放到char型数组中
        char[] chars = new char[length];
        int evenIndex = 0, oddIndex = 0;
        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                chars[i] = even.get(evenIndex++);
            } else {
                chars[i] = odd.get(oddIndex++);
            }
        }
        
        // 把char型数组中的字符经过处理后就加入到stringbuffer中
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean condition = (chars[i] >= '0' && chars[i] <= '9') || 
                (chars[i] >= 'a' && chars[i] <= 'f') || 
                (chars[i] >= 'A' && chars[i] <= 'F');
            if (condition) {
                stringBuilder.append(processChar(chars[i]));
            } else {
                stringBuilder.append(chars[i]);
            }
        }
        
        return stringBuilder.toString();
    }
    
    private static char processChar(char ch) {
        //num代表该字符所代表的十六进制数字
        int num = 0;
        if (ch >= '0' && ch <= '9') {
            num = Integer.parseInt(ch + "");
        } else if (ch >= 'a' && ch <= 'f') {
            num = ch - 'a' + 10;
        } else {
            num = ch - 'A' + 10;
        }
        
        // 对该十六进制数字进行处理
        return getReverseResult(num);
    }
    
    private static char getReverseResult(int num) {
        //将该数字转化为4位二进制数，然后反转
        String str = reverseBinaryString(num);
        // parseInt(String s, int radix)使用指定基数的字符串参数表示的整数 
        // 基数可以是 10, 2, 8, 或 16 等进制数
        int result = Integer.parseInt(str, 2);  // ("10",2)->2; ("10",10)->10
        if (result >= 0 && result <= 9) {
            return (result + "").charAt(0);
        } else {
            // 字符A对应的ascii码值是65
            return (char)(result - 10 + 65);
        }
    }
    
    /*
    private static String reverseBinaryString(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        while (num != 0) {
            stringBuilder.append(num % 2);
            num /= 2;
        }
        
        return stringBuilder.toString();
    }
    */
    private static String reverseBinaryString(int num){
        // 因为是4位，所以1<<3->1000
        int k = 1<<3;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 4; i++){
            int flag = ((num & k) == 0 ? 0 : 1);
            sb.append(flag);
            num = num<<1;
        }
        return sb.reverse().toString();
    }
}
```

# 31.单词倒排

对字符串中的所有单词进行倒排。

说明：
1. 构成单词的字符只有26个大写或小写英文字母；
2. 非构成单词的字符均视为单词间隔符；
3. 要求倒排后的单词间隔符以一个空格表示；如果原字符串中相邻单词间有多个间隔符时，倒排转换后也只允许出现一个空格间隔符；
4. 每个单词最长20个字母。

```
输入：
I am a student

输出：
student a am I
```

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // ^匹配输入字符串开始的位置；+一次或多次匹配前面的字符或子表达式
            String[] strings = reader.readLine().split("[^a-zA-Z]+");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = strings.length - 1; i >= 0; i--) {
                stringBuilder.append(strings[i] + " ");
            }
            System.out.println(stringBuilder.toString().trim());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```       


# 32.密码截取

Catcher是MCA国的情报员，他工作时发现敌国会用一些对称的密码进行通信，比如像这些ABBA，ABA，A，123321，但是他们有时会**在开始或结束时加入一些无关的字符**以防止别国破解。比如进行下列变化ABBA->12ABBA，ABA->ABAKK，123321->51233214。因为截获的串太长了，而且存在多种可能的情况（abaaab可看作是aba，或baaab的加密形式），Cathcer的工作量实在是太大了，他只能向电脑高手求助，你能帮Catcher找出**最长的有效密码串**吗？


输入描述:
输入一个字符串

输出描述:
返回有效密码串的最大长度

5. 最长回文子串
https://leetcode-cn.com/problems/longest-palindromic-substring/description/?utm_source=LCUS&utm_medium=ip_redirect_q_uns&utm_campaign=transfer2china


**中心扩展算法：**

回文串一定是对称的，所以我们可以每次循环选择一个中心，进行左右扩展，判断左右字符是否相等即可。由于存在奇数的字符串和偶数的字符串，所以我们需要从一个字符开始扩展，或者从两个字符之间开始扩展，所以总共有 n + n - 1 个中心。遍历每个中心，然后判断对称位置是否相等。

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String str = input.nextLine();
            System.out.println(longestPalindrome(str).length());
        }
    }
    
    private static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i); //从一个字符扩展
            int len2 = expandAroundCenter(s, i, i + 1); //从两个字符之间扩展
            int len = Math.max(len1, len2);
            //根据 i 和 len 求得字符串的相应下标
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    
    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            // 中心扩散
            L--;
            R++;
        }
        return R - L - 1;
    }
}
```

# 33.整数与IP地址间的转换

原理：ip地址的每段可以看成是一个0-255的整数，**把每段拆分成一个二进制形式组合起来，然后把这个二进制数转变成一个长整数**。

举例：一个ip地址为10.0.3.193
| 每段数字  | 相对应的二进制数  |
|:-:|:-:|
| 10  | 00001010  |
| 0  |  00000000 |
| 3  | 00000011  |
| 193  | 11000001  |
                            
组合起来即为：`00001010 00000000 00000011 11000001`，转换为10进制数就是：`167773121`，即该IP地址转换后的数字就是它了。


输入描述:
输入 
1. 输入IP地址：10.0.3.193
2. 输入10进制型的IP地址：167969729

输出描述:
输出
1. 输出转换成10进制的IP地址：167773121
2. 输出转换后的IP地址：10.3.3.193

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            while ((str = reader.readLine()) != null) {
                String[] ip = str.split("\\.");
                long num = Long.parseLong(reader.readLine());
                
                // 转10进制 | 按位或
                System.out.println(Long.parseLong(ip[0]) << 24 | Long.parseLong(ip[1]) << 16 | 
                                  Long.parseLong(ip[2]) << 8 | Long.parseLong(ip[3]));
                
                //转ip地址
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf((num >> 24) & 255)).append(".")
                             .append(String.valueOf((num >> 16) & 255)).append(".")
                             .append(String.valueOf((num >> 8) & 255)).append(".")
                             .append(String.valueOf(num & 255));
                System.out.println(stringBuilder.toString());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```


# 34.图片整理

Lily上课时使用字母数字图片教小朋友们学习英语单词，每次都需要把这些图片**按照大小（ASCII码值从小到大）排列**收好。

输入描述:
Lily使用的图片包括"A"到"Z"、"a"到"z"、"0"到"9"。输入字母或数字个数不超过1024。
`Ihave1nose2hands10fingers`

输出描述:
Lily的所有图片按照从小到大的顺序输出
`0112Iaadeeefghhinnnorsssv`

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            while ((str = reader.readLine()) != null) {
                char[] chars = str.toCharArray();
                Arrays.sort(chars);
                System.out.println(String.valueOf(chars));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 36.字符串加密