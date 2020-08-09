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

有一种技巧可以对数据进行加密，它使用一个单词作为它的密匙。下面是它的工作原理：首先，**选择一个单词作为密匙**，如TRAILBLAZERS。**如果单词中包含有重复的字母，只保留第1个，其余几个丢弃**。现在，修改过的那个单词属于字母表的下面，如下所示（密码本）：

```
A B C D E F G H I J K L M N O P Q R S T U V W X Y Z

T R A I L B Z E S C D F G H J K M N O P Q U V W X Y
```

**上面其他用字母表中剩余的字母填充完整**。

在对信息进行加密时，信息中的每个字母被固定于顶上那行，并用下面那行的对应字母一一取代原文的字母(字母字符的大小写状态应该保留)。因此，使用这个密匙，Attack AT DAWN(黎明时攻击)就会被加密为Tpptad TP ITVH（**在密码本的第一行找到明文中字母，其所对应的字母——密文，如密码本中第二行所示**）。

请实现下述接口，通过指定的密匙和明文得到密文。

详细描述：

接口说明

原型：`voidencrypt(char * key,char * data,char * encrypt);`

输入参数：
`char * key：密匙`
`char * data：明文`

输出参数：
`char * encrypt：密文`

返回值：
`void`

输入描述:
先输入key和要加密的字符串：
```
nihao
ni
```

输出描述:
返回加密后的字符串：le

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // 密钥
        String str = "";
        while ((str = reader.readLine()) != null) {
            StringBuilder sb = new StringBuilder();
            
            // 明文
            String str1 = reader.readLine();
            // 密码本
            HashSet<Character> linkedHashSet = new LinkedHashSet<>();
            // 生成密码本
            for (int i = 0; i < str.length(); i++) {
                linkedHashSet.add(str.charAt(i));
            }
            for (int i = 0; i < 26; i++) {
                linkedHashSet.add((char)(i + 'a'));
            }
            char[] ch = new char[26];
            Iterator iter = linkedHashSet.iterator();
            for (int i = 0; i < 26; i++) {
                ch[i] = (Character) iter.next();
            }
            
            // 加密
            for (int i = 0; i < str1.length(); i++) {
                if (Character.isLowerCase(str1.charAt(i))) {
                    sb.append(Character.toLowerCase(ch[str1.charAt(i) - 'a']));
                } else {
                    sb.append(Character.toUpperCase(ch[str1.charAt(i) - 'A']));
                }
            }
            
            System.out.println(sb);
        }
        
        reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 39.判断两个IP是否属于同一子网

子网掩码是用来判断任意两台计算机的IP地址是否属于同一子网络的根据。
子网掩码与IP地址结构相同，是32位二进制数，其中网络号部分全为“1”和主机号部分全为“0”。利用子网掩码可以判断两台主机是否中同一子网中。**若两台主机的IP地址分别与它们的子网掩码相“与”后的结果相同，则说明这两台主机在同一子网中**。

示例：
```
IP 地址　  192.168.0.1
子网掩码　 255.255.255.0

转化为二进制进行运算：

IP 地址 　11010000.10101000.00000000.00000001
子网掩码　11111111.11111111.11111111.00000000

AND运算
 　　　　11000000.10101000.00000000.00000000

转化为十进制后为：
 　　　　192.168.0.0

 

IP 地址　  192.168.0.254
子网掩码　 255.255.255.0


转化为二进制进行运算：

I P 地址　11010000.10101000.00000000.11111110
子网掩码　11111111.11111111.11111111.00000000

AND运算
　　　　　11000000.10101000.00000000.00000000

转化为十进制后为：
　　　　　192.168.0.0
```

通过以上对两台计算机IP地址与子网掩码的AND运算后，我们可以看到它运算结果是一样的。均为192.168.0.0，所以这二台计算机可视为是同一子网络。

```
/* 
* 功能: 判断两台计算机IP地址是同一子网络。 
* 输入参数：     String Mask: 子网掩码，格式：“255.255.255.0”； 
*               String ip1: 计算机1的IP地址，格式：“192.168.0.254”；
*               String ip2: 计算机2的IP地址，格式：“192.168.0.1”；
*               
* 返回值：      0：IP1与IP2属于同一子网络；     1：IP地址或子网掩码格式非法；    2：IP1与IP2不属于同一子网络
*/ 
public int checkNetSegment(String mask, String ip1, String ip2) 
{     
    /*在这里实现功能*/
    return 0;
}
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
                String[] mask = str.split("\\.");
                String[] ip1 = reader.readLine().split("\\.");
                String[] ip2 = reader.readLine().split("\\.");
                // IP1与IP2属于同一子网络
                int flag = 0;
                for (int i = 0; i < 4; i++) {
                    int maskValue = Integer.parseInt(mask[i]);
                    int ip1Value = Integer.parseInt(ip1[i]);
                    int ip2Value = Integer.parseInt(ip2[i]);
                    if (maskValue < 0 || ip1Value < 0 || ip2Value < 0 ||
                       maskValue > 255 || ip1Value > 255 || ip2Value > 255) {
                        // IP1与IP2属于同一子网络
                        flag = 1;
                        break;
                    }
                    if ((ip1Value & maskValue) != (ip2Value & maskValue)) {
                        // IP1与IP2属于同一子网络
                        flag = 2;
                        break;
                    }
                }
                
                System.out.println(flag);
            }
            
            reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 40.统计字符个数

输入一行字符，分别统计出包含英文字母、空格、数字和其它字符的个数。

```
    /**
     * 统计出英文字母字符的个数。
     * 
     * @param str 需要输入的字符串
     * @return 英文字母的个数
     */
    public static int getEnglishCharCount(String str)
    {
        return 0;
    }
    
    /**
     * 统计出空格字符的个数。
     * 
     * @param str 需要输入的字符串
     * @return 空格的个数
     */
    public static int getBlankCharCount(String str)
    {
        return 0;
    }
    
    /**
     * 统计出数字字符的个数。
     * 
     * @param str 需要输入的字符串
     * @return 英文字母的个数
     */
    public static int getNumberCharCount(String str)
    {
        return 0;
    }
    
    /**
     * 统计出其它字符的个数。
     * 
     * @param str 需要输入的字符串
     * @return 英文字母的个数
     */
    public static int getOtherCharCount(String str)
    {
        return 0;
    }
```

输入描述:
输入一行字符串，可以有空格
`1qazxsw23 edcvfr45tgbn hy67uj m,ki89ol.\\/;p0-=\\][`

输出描述:
统计其中英文字符，空格字符，数字字符，其他字符的个数
```
26
3
10
12
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
                int englishCharCount = 0;
                int blankCharCount = 0;
                int numberCharCount = 0;
                int otherCharCount = 0;
                
                char[] chars = str.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if ((chars[i] >= 'a' && chars[i] <= 'z') || 
                       (chars[i] >= 'A' && chars[i] <= 'Z')) {
                        englishCharCount++;
                    } else if (chars[i] == ' ') {
                        blankCharCount++;
                    } else if (chars[i] >= '0' && chars[i] <= '9') {
                        numberCharCount++;
                    } else {
                        otherCharCount++;
                    }
                }
                System.out.println(englishCharCount);
                System.out.println(blankCharCount);
                System.out.println(numberCharCount);
                System.out.println(otherCharCount);
            }
            
            reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# 41.(未实现)称砝码

现有一组砝码，重量互不相等，分别为m1,m2,m3…mn；
每种砝码对应的数量为x1,x2,x3...xn。现在要用这些砝码去称物体的重量(放在同一侧)，问能称出多少种不同的重量。

注：**称重重量包括0**

方法原型：`public static int fama(int n, int[] weight, int[] nums)`

输入描述:
输入包含多组测试数据。

对于每组测试数据：

第一行：n --- 砝码数(范围[1,10])

第二行：m1 m2 m3 ... mn --- 每个砝码的重量(范围[1,2000])

第三行：x1 x2 x3 .... xn --- 每个砝码的数量(范围[1,6])

```
2
1 2
2 1
```

输出描述:
利用给定的砝码可以称出的不同的重量数
`5`

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        
        while ((str = reader.readLine()) != null) {
            // 砝码数
            int n = Integer.parseInt(str);
            String[] weightStr = reader.readLine().split(" ");
            String[] numStr = reader.readLine().split(" ");
            
            int[] weight = new int[n];
            int[] number = new int[n];
            for (int i = 0; i < n; i++) {
                weight[i] = Integer.parseInt(weightStr[i]);
                number[i] = Integer.parseInt(numStr[i]);
            }
            
            System.out.println(getNumber(n, weight, number));
        }
    }
    
    private static int getNumber(int n, int[] weight, int[] number) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // 砝码能称取的最大重量
            sum += weight[i] * number[i];
        }
        
        // 砝码重量种类包括0，可能有sum+1种
        boolean[] trueWeight = new boolean[sum + 1];
        trueWeight[0] = true;
        trueWeight[sum] = true;
        
        // n种砝码
        for (int i = 0; i < n; i++) {
            // 每种砝码的个数
            for (int j = 0; j < number[i]; j++) {
                // 从砝码能称取的最大重量，到单个砝码
                for (int k = sum; k >= weight[i]; k--) {
                    if (trueWeight[k - weight[i]]) {
                        trueWeight[k] = true;
                    }
                }
            }
        }
        
        int count = 0;
        for (boolean result : trueWeight) {
            if (result) {
                count++;
            }
        }
        
        return count;
    }
}
```

# 42.学英语

Jessi初学英语，为了快速读出一串数字，编写程序将数字转换成英文：

如`22：twenty two`，`123：one hundred and twenty three`。


说明：

**数字为正整数，长度不超过九位，不考虑小数，转化结果为英文小写**；

输出格式为`twenty two`；

非法数据请返回`“error”`；

关键字提示：`and，billion，million，thousand，hundred`。

方法原型：`public static String parse(long num)` 

```
输入描述:
输入一个long型整数
2356

输出描述:
输出相应的英文写法
two thousand three hundred and fifty six
```

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        
        while ((str = reader.readLine()) != null) {
            // 输入的带转化整数
            Integer num = Integer.valueOf(str);
            System.out.println(parse(num));
        }
        
        //reader.close();
    }
    
    private static String parse(int num) {
        String[] numStr = {"zero","one","two","three","four","five","six","seven","eight","nine","ten", 
                           "eleven","twelve", "thirteen","fourteen","fifteen","sixteen","seventeen", "eighteen","ninteen"};
        if (num >= 0 && num < 20) {
            return numStr[num];
        } else if (num >= 20 && num < 100) {
            int number = num % 10;
            if (num < 30) {
                return number != 0 ? "twenty " + parse(number) : "twenty";
            } else if (num < 40) {
                return number != 0 ? "thirty " + parse(number) : "thirty";
            } else if (num < 50) {
                return number != 0 ? "forty " + parse(number) : "forty";
            } else if (num < 60) {
                return number != 0 ? "fifty " + parse(number) : "fifty";
            } else if (num < 70) {
                return number != 0 ? "sixty " + parse(number) : "sixty";
            } else if (num < 80) {
                return number != 0 ? "seventy " + parse(number) : "seventy";
            } else if (num < 90) {
                return number != 0 ? "eighty " + parse(number) : "eighty";
            } else if (num < 100) {
                return number != 0 ? "ninety " + parse(number) : "ninety";
            }
        } else if (num >= 100 && num < 1000) {
            int x = num / 100;
            int y = num % 100;
            if (y != 0) {
                return parse(x) + " hundred" + " and " + parse(y);
            } else {
                return parse(x) + " hundred";
            }
        } else if (num >= 1000 && num < 1000000) {
            int x = num / 1000;
            int y = num % 1000;
            if(y != 0){
                return parse(x) + " thousand " + parse(y);
            } else {
                return parse(x) + " thousand";
            }
        } else if (num >= 1000000 && num < 100000000) {
            int x = num / 1000000;
            int y = num % 1000000;
            if(y != 0){
                return parse(x) + " million " + parse(y);
            } else {
                return parse(x) + " million";
            }
        }
        
        return "error";
    }
}
```

# 45.名字的漂亮度

给出一个名字，该名字有26个字符串组成，定义这个**字符串的“漂亮度”是其所有字母“漂亮度”的总和**。

每个字母都有一个“漂亮度”，范围在1到26之间。没有任何两个字母拥有相同的“漂亮度”。字母忽略大小写。

给出多个名字，计算每个名字最大可能的“漂亮度”。

```
输入描述:
整数N，后续N个名字
2
zhangsan
lisi

输出描述:
每个名称可能的最大漂亮程度
192
101
```

思路：出现次数最多的字母漂亮度是`26*出现的次数`，第二多的是`25*出现的次数`...以此类推，最后累计加和。

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        
        while ((str = reader.readLine()) != null) {
            // n个名字
            int n = Integer.parseInt(str);
            for (int i = 0; i < n; i++) {
                String name = reader.readLine();
                // 统计名字中每个字母的个数
                int[] count = new int[26];
                for (char ch : name.toCharArray()) {
                    count[ch - 'a']++;
                }
                Arrays.sort(count);
                int k = 26;
                int sum = 0;
                for (int j = count.length - 1; j >= 0; j--) {
                    if (count[j] == 0) {
                        break;
                    }
                    sum += (k--) * count[j];
                }
                System.out.println(sum);
            }
        }
        
        reader.close();
    }
}
```

# 46.按字节截取字符串

编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串。但是要保证**汉字不被截半个**，如`"我ABC"4`，应该截为`"我AB"`，输入`"我ABC汉DEF"6`，应该输出为`"我ABC"`而不是`"我ABC+汉的半个"`。 

```
输入描述:
输入待截取的字符串及长度
我ABC汉DEF 6

输出描述:
截取后的字符串
我ABC
```

注：只出现汉字与字母

```java
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        
        while ((str = reader.readLine()) != null) {
            String[] strings = str.split(" ");
            int number = Integer.parseInt(strings[1]);
            String result = getStringInByte(strings[0], number);
            System.out.println(result);
        }
        
        reader.close();
    }
    
    private static String getStringInByte(String name, int number) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (int i = 0; i < name.length(); i++) {
            char judge = name.charAt(i);
            if ((judge >= 'a' && judge <= 'z') || (judge >= 'A' && judge <= 'Z')) {
                count++;
                if (count > number) {
                    return builder.toString();
                }
                builder.append(judge);
            } else {
                count += 2;
                if (count > number) {
                    return builder.toString();
                }
                builder.append(judge);
            }
        }
        
        return builder.toString();
    }
}
```

# 49.(未实现)多线程打印

有4个线程和1个公共的字符数组：
- 线程1的功能就是向字符数组输出A
- 线程2的功能就是向字符数组输出B
- 线程3的功能就是向字符数组输出C
- 线程4的功能就是向字符数组输出D。
  
要求按顺序向数组赋值ABCDABCDABCD，ABCD的个数由线程函数1的参数指定。

```
输入描述:
输入一个int整数
10

输出描述:
输出多个ABCD
ABCDABCDABCDABCDABCDABCDABCDABCDABCDABCD
```

```java
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程，线程交替打印ABCDABCD
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            CountDownLatch countDownLatch = new CountDownLatch(4);
            AlternativePrint alternativePrint = new AlternativePrint();
            
            //创建四个线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < n; i++) {
                            alternativePrint.printA();
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            }).start();
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < n; i++) {
                            alternativePrint.printB();
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            }).start();
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < n; i++) {
                            alternativePrint.printC();
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            }).start();
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < n; i++) {
                            alternativePrint.printD();
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            }).start();
            
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println();
        }
    }
}

class AlternativePrint {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    private Condition conditionD = lock.newCondition();
    private int number = 1;

    void printA() {
        lock.lock();
        try {
            if (number != 1) {
                conditionA.await();
            }
            System.out.print("A");
            //"A"打印结束，标记置为2，并唤醒打印"B"的线程
            number = 2;
            conditionB.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void printB() {
        lock.lock();
        try {
            if (number != 2) {
                conditionB.await();
            }
            System.out.print("B");
            //"B"打印结束，标记置为3，并唤醒打印"C"的线程
            number = 3;
            conditionC.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void printC() {
        lock.lock();
        try {
            if (number != 3) {
                conditionC.await();
            }
            System.out.print("C");
            //"C"打印结束，标记置为4，并唤醒打印"D"的线程
            number = 4;
            conditionD.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void printD() {
        lock.lock();
        try {
            if (number != 4) {
                conditionD.await();
            }
            System.out.print("D");
            //"D"打印结束，标记置为1，并唤醒打印"A"的线程
            number = 1;
            conditionA.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

# 50.(未实现)四则运算

请实现如下接口

```
    /* 功能：四则运算

     * 输入：strExpression：字符串格式的算术表达式，如: "3+2*{1+2*[-4/(8-6)+7]}"

         * 返回：算术表达式的计算结果

     */

    public static int calculate(String strExpression)

    {

        /* 请实现*/

        return 0;

    } 
```

约束：
`strExpression`字符串中的有效字符包括`[‘0’-‘9’],‘+’,‘-’, ‘*’,‘/’ ,‘(’， ‘)’,‘[’, ‘]’,‘{’ ,‘}’`。

`strExpression`算术表达式的有效性由调用者保证;；

```
输入描述:
输入一个算术表达式
3+2*{1+2*[-4/(8-6)+7]}

输出描述:
得到计算结果
25
```

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = reader.readLine()) != null) {
            // List 存放后缀表达式
            List<String> list = new ArrayList<>();
            // 定义操作符栈stack，用于存放操作符 + - * / (
            Stack<Character> stack = new Stack();
            for (int i = 0; i < s.length(); i++) {
                // 定义一个字符，记录字符串当前循环的变量
                char c = s.charAt(i);
                if (isNum(c)) {
                    // 取出以当前字符开头数字结尾的整数字符串进行判定是否为数字
                    int start = i;
                    if (i == s.length() - 1) {
                        i++;
                    } else {
                        // 一直找到不是数字字符为止
                        while (isNum(s.charAt(++i))) {
                        }
                    }
                    // 将整数存入LIST中
                    list.add(s.substring(start, i));
                    i--;
                } else if (c == '(' || c == '[' || c == '{') {
                    // 字符为左括号则入栈
                    stack.push(c);
                } else if (c == ')' || c == ']' || c == '}') {
                    //  一直出栈直到遇到左括号
                    while (stack.peek() != '(' && stack.peek() != '[' && stack.peek() != '{') {
                        // 当栈顶不为左括号时，将此操作符添加到LIST中
                        list.add(String.valueOf(stack.pop()));
                    }
                    stack.pop();
                } else if (c == '-') {
                    if ((i != 0 && (isNum(s.charAt(i - 1)) && isNum(s.charAt(i + 1)))) || (s.charAt(i - 1) == ')' || s.charAt(i - 1) == ']' || s.charAt(i - 1) == '}') || (s.charAt(i + 1) == '(' || s.charAt(i + 1) == '[') || s.charAt(i + 1) == '{') {
                        // 减号
                        while (!greaterThan(c, stack)) {
                            list.add(String.valueOf(stack.pop()));
                        }
                        stack.push(c);
                    } else {
                        // 负号
                        int start = i;
                        while (isNum(s.charAt(++i))) {
                        }
                        list.add(s.substring(start, i));
                        i--;
                    }
                } else if (c == '+') {
                    while (!greaterThan(c, stack)) {
                        list.add(String.valueOf(stack.pop()));
                    }
                    stack.push(c);
                } else if (c == '*' || c == '/') {
                    while (!greaterThan(c, stack)) {
                        list.add(String.valueOf(stack.pop()));
                    }
                    stack.push(c);
                }
            }
            while (!stack.isEmpty()) {
                list.add(String.valueOf(stack.pop()));
            }

            // 计算后缀表达式
            int res = calculate(list);
            System.out.println(res);
        }
    }

    public static boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    // 比较运算符与栈顶运算符的优先级
    public static boolean greaterThan(char c, Stack<Character> stack) {
        if (stack.isEmpty()) {
            return true;
        } else {
            char c1 = stack.peek();
            if (c == '*' || c == '/') {
                return !(c1 == '*' || c1 == '/');
            } else {
                return c1 == '(' || c1 == '{' || c1 == '[';
            }
        }
    }

    public static int calculate(List<String> list) {
        // 定义数字栈，存放后缀表达式计算过程中的值
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            int n1;
            int n2;
            switch (s) {
                case "*":
                    n1 = stack.pop();
                    n2 = stack.pop();
                    stack.push(n1 * n2);
                    break;
                case "/":
                    n1 = stack.pop();
                    n2 = stack.pop();
                    stack.push(n2 / n1);
                    break;
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    n1 = stack.pop();
                    n2 = stack.pop();
                    stack.push(n2 - n1);
                    break;
                default:
                    stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }

}
```

# 52.计算字符串的距离

Levenshtein距离，又称编辑距离，指的是**两个字符串之间，由一个转换成另一个所需的最少编辑操作次数**。许可的编辑操作包括将一个字符**替换**成另一个字符，**插入**一个字符，**删除**一个字符。编辑距离的算法是首先由俄国科学家Levenshtein提出的，故又叫Levenshtein Distance。

Ex：

字符串A:abcdefg

字符串B:abcdef

通过增加或是删掉字符”g”的方式达到目的。这两种方案都需要一次操作。把这个操作所需要的次数定义为两个字符串的距离。

要求：

给定任意两个字符串，写出一个算法计算它们的编辑距离。

 

请实现如下接口
```
/*  功能：计算两个字符串的距离
 *  输入： 字符串A和字符串B
 *  输出：无
 *  返回：如果成功计算出字符串的距离，否则返回-1
 */
 
 public static int calStringDistance(String charA, String charB)
 {
    return  0;
}  
```

## 动态规划

https://github.com/Wang-Jun-Chao/hawei-online-test/blob/master/078-%E8%AE%A1%E7%AE%97%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%9A%84%E8%B7%9D%E7%A6%BB/src/Main.java

很经典的可使用动态规划方法解决的题目，和计算两字符串的最长公共子序列相似。

设Ai为字符串A(`a1a2a3 … am`)的前i个字符（即为`a1,a2,a3 … ai`），设Bj为字符串B(`b1b2b3 … bn`)的前j个字符（即为`b1,b2,b3 … bj`）。设`L(i,j)`为使两个字符串和Ai和Bj相等的最小操作次数。

- 当`ai==bj`时 显然`L(i,j) = L(i-1,j-1)`；
- 当`ai!=bj`时
  -  若将它们修改为相等，则对两个字符串至少还要操作`L(i-1,j-1)`次
  -  若删除ai或在bj后添加ai，则对两个字符串至少还要操作`L(i-1,j)`次
  -  若删除bj或在ai后添加bj，则对两个字符串至少还要操作`L(i,j-1)`次
  -  此时`L(i,j) = min( L(i-1,j-1), L(i-1,j), L(i,j-1) ) + 1`

显然，`L(i,0)=i`，`L(0,j)=j`，再利用上述的递推公式，可以直接计算出`L(i,j)`值。

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String a = scanner.nextLine();
            String b = scanner.nextLine();
            System.out.println(getStringDistance(a, b));
        }
        scanner.close();
    }
    
    private static int getStringDistance(String a, String b) {
        return stringDistance(a.toCharArray(), b.toCharArray());
    }
    
    private static int stringDistance(char[] a, char[] b) {
        int[][] len = new int[a.length + 1][b.length + 1];
        for (int i = 0; i < len.length; i++) {
            len[i][0] = i;
        }
        for (int j = 0; j < len[0].length; j++) {
            len[0][j] = j;
        }
        for (int i = 1; i < len.length; i++) {
            for (int j = 1; j < len[0].length; j++) {
                if (a[i - 1] == b[j - 1]) {
                    len[i][j] = len[i - 1][j - 1];
                } else {
                    len[i][j] = Math.min(Math.min(len[i - 1][j], len[i - 1][j - 1]), len[i][j - 1]) + 1;
                }
            }
        }
        
        return len[len.length - 1][len[0].length - 1];
    }
}
```


# 53.杨辉三角的变形
```
1

1  1  1

1  2  3  2  1

1  3  6  7  6  3  1

1  4  10 16 19 16 10  4  1
```

以上三角形的数阵，第一行只有一个数1，以下每行的每个数，是恰好是~~它上面的数，左上角数到右上角的数~~它上面的数与其前两个数，3个数之和（如果不存在某个数，认为该数就是0）。

求**第n行第一个偶数出现的位置**。如果没有偶数，则输出-1。例如输入3,则输出2，输入4则输出3。

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int input = scanner.nextInt();
            System.out.println(findIndex(input));
        }
        
        scanner.close();
    }
    
    private static int findIndex(int n) {
        if (n <= 2) {
            return -1;
        }
        
        int[][] arr = {new int[2 * n -1], new int[2 * n -1], new int[2 * n -1]};
        arr[0][0] = 1;
        arr[1][0] = 1;
        arr[1][1] = 1;
        arr[1][2] = 1;
        
        // “i = 2”从第三行开始
        for (int i = 2; i < n; i++) {
            int curr = i % 3;
            int prev = (i - 1) % 3;
            int last = 2 * i;
            
            // 每行第一个值
            arr[curr][0] = 1;
            // 每行第二个值
            arr[curr][1] = i;
            // 每行最后一个值
            arr[curr][last] = 1;
            // 每行倒数第二个值
            arr[curr][last - 1] = i;
            
            // 从每行第三个数开始
            for (int j = 2; j <= last - 2; j++) {
                arr[curr][j] = arr[prev][j - 2] + arr[prev][j - 1] + arr[prev][j];
            }
        }
        
        // 第一行是从0开始的
        int curr = (n - 1) % 3;
        for (int i = 0; i < arr[curr].length; i++) {
            if (arr[curr][i] % 2 == 0) {
                // 每一行的数index都是从0开始
                return i + 1;
            }
        }
        
        return -1;
    }
}
```

# 54.(未实现)表达式求值

给定一个字符串描述的算术表达式，计算出结果值。

输入字符串长度不超过100，合法的字符包括`"+, -, *, /, (, )"，"0-9"`，字符串内容的合法性及表达式语法的合法性由做题者检查。本题目只涉及整型计算。

``` java
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("data.txt"));
        while (scanner.hasNext()) {
            String input = scanner.next();
            input = format(input);
            System.out.println(calculate(input));
        }

        scanner.close();
    }

    /**
     * 进行四则运行
     *
     * @param s 输入一个算术表达式
     * @return 表达式结果
     */
    private static int calculate(String s) {

        // 操作符栈
        Deque<Character> opts = new LinkedList<>();
        // 操作数栈
        Deque<Integer> opds = new LinkedList<>();

        int idx = 0;
        while (idx < s.length()) {
            char c = s.charAt(idx);
            // 如果是数字
            if (c >= '0' && c <= '9') {
                // 计算数字的值
                int opd = 0;
                while (idx < s.length() && s.charAt(idx) >= '0' && s.charAt(idx) <= '9') {
                    opd = opd * 10 + (s.charAt(idx) - '0');
                    idx++;
                }
                opds.addLast(opd);
            }
            // 如果是操作符
            else {


                // 如果是左括号
                if (c == '(' || c == '[' || c == '{') {
                    opts.addLast(c);
                }
                // 如果是右括号
                else if (c == ')' || c == ']' || c == '}') {
                    while (!opts.isEmpty() && opts.getLast() != '(' && opts.getLast() != '[' && opts.getLast() != '{') {
                        calculate(opts, opds);
                    }
                    opts.removeLast();
                }
                // 如果是乘或者除
                else if (c == '*' || c == '/') {
                    while (!opts.isEmpty() && (opts.getLast() == '*' || opts.getLast() == '/')) {
                        calculate(opts, opds);
                    }
                    // 操作符入栈
                    opts.addLast(c);
                } else if (c == '+' || c == '-') {
                    while (!opts.isEmpty() && (opts.getLast() == '*'
                            || opts.getLast() == '/'
                            || opts.getLast() == '+'
                            || opts.getLast() == '-')) {
                        calculate(opts, opds);
                    }
                    // 操作符入栈
                    opts.addLast(c);
                }

                // 处理下一个字符
                idx++;
            }


        }

        while (!opts.isEmpty()) {
            calculate(opts, opds);
        }
        return opds.removeLast();
    }

    /**
     * 求值操作，取opt的最后一个操作符，opds中的最后两个操作数
     *
     * @param opts 操作符栈
     * @param opds 操作数栈
     */
    private static void calculate(Deque<Character> opts, Deque<Integer> opds) {

        // 取操作数栈中的最后一个操作符
        char opt = opts.removeLast();
        // 取操作数
        int v2 = opds.removeLast();
        int v1 = opds.removeLast();

        // 计算
        int v = calculate(v1, v2, opt);
        opds.addLast(v);
    }

    /**
     * 将算术表达式归整，-5*3整理成0-5*3
     *
     * @param s 算术表达式
     * @return 归整后的表达式
     */
    private static String format(String s) {
        // 去掉空格
        String t = s.replaceAll("(\\s)+", "");

        int idx = 0;
        // 对所有的减号进行处理
        while ((idx = t.indexOf('-', idx)) >= 0) {
            // 第一个字符是负号，要规格形式要加上0
            if (idx == 0) {
                t = '0' + t;
            }
            // 如果不是第一个字符
            else {
                char c = t.charAt(idx - 1);
                // 负号前面有括号，需要在前面加0
                if (c == '(' || c == '[' || c == '{') {
                    t = t.substring(0, idx) + '0' + t.substring(idx);
                }
            }

            idx++;
        }

        return t;
    }

    /**
     * 计算 v1 operator v2，operator是加减乘除
     *
     * @param v1       操作数1
     * @param v2       操作数2
     * @param operator 操作符
     * @return 结果
     */
    private static int calculate(int v1, int v2, char operator) {
        switch (operator) {
            case '+':
                return v1 + v2;
            case '-':
                return v1 - v2;
            case '*':
                return v1 * v2;
            case '/':
                return v1 / v2;
            default:
                // do nothing
        }
        return 0;
    }
}
```

# 57.(***)高精度整数加法

在计算机中，由于处理器位宽限制，只能处理有限精度的十进制整数加减法，比如在32位宽处理器计算机中，参与运算的操作数和结果必须在$-2^{31}...2^{31}-1$(2,147,483,648~2,147,483,647)之间。如果需要进行更大范围的十进制整数加法，需要使用特殊的方式实现，比如使用字符串保存操作数和结果，采取逐位运算的方式。

如下：
`9876543210 + 1234567890 = ?`
让字符串`num1="9876543210"`，字符串`num2="1234567890"`，结果保存在字符串`result = "11111111100"`。

`-9876543210 + (-1234567890) = ?`
让字符串`num1="-9876543210"`，字符串`num2="-1234567890"`，结果保存在字符串`result = "-11111111100"`。

要求编程实现上述高精度的十进制加法。

要求实现方法：
`public String add (String num1, String num2)`
输入
- num1：字符串形式操作数1，如果操作数为负，则num1的前缀为符号位'-'
- num2：字符串形式操作数2，如果操作数为负，则num2的前缀为符号位'-'
返回：保存加法计算结果字符串，如果结果为负，则字符串的前缀为'-'

注：
(1)当输入为正数时，'+'不会出现在输入字符串中；当输入为负数时，'-'会出现在输入字符串中，且一定在输入字符串最左边位置；
(2)输入字符串所有位均代表有效数字，即不存在由'0'开始的输入字符串，比如"0012", "-0012"不会出现；
(3)要求输出字符串所有位均为有效数字，结果为正或0时'+'不出现在输出字符串，结果为负时输出字符串最左边位置为'-'。

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str1 = scanner.next();
            String str2 = scanner.next();
            System.out.println(add(str1, str2));
        }
        scanner.close();
    }

    /**
     * 两个字符串相加
     */
    private static String add(String str1, String str2) {
        // 判断str1, str2是否为正数
        boolean positiveNumber1 = str1.charAt(0) != '-';
        boolean positiveNumber2 = str2.charAt(0) != '-';

        int[] number1;
        int[] number2;

        // getNumber返回无符号的整数
        if (positiveNumber1) {
            number1 = getNumber(str1);
        } else {
            number1 = getNumber(str1.substring(1));
        }

        if (positiveNumber2) {
            number2 = getNumber(str2);
        } else {
            number2 = getNumber(str2.substring(1));
        }

        // 两者同号
        if (positiveNumber1 == positiveNumber2) {
            int[] result = add(number1, number2);
            String resultStr = toNumberStr(result);

            // 根据需要添加符号
            if (positiveNumber1) {
                return resultStr;
            } else {
                return "-" + resultStr;
            }
        } else {
            // getNumber返回无符号的整数
            if (compare(number1, number2) >= 0) {
                // number1大于等于number2
                int[] result = minus(number1, number2);
                String resultStr = toNumberStr(result);
                // str1为正数，str2为负数
                if (positiveNumber1) {
                    return resultStr;
                } else {
                    return "-" + resultStr;
                }
            } else {
                // number1小于number2
                int[] result = minus(number2, number1);
                String resultStr = toNumberStr(result);
                // str1为正数，str2为负数
                if (positiveNumber1) {
                    return "-" + resultStr;
                } else {
                    return resultStr;
                }
            }
        }
    }

    /**
     * 将字符数值转换成整数数值，不包含符号位
     */
    private static int[] getNumber(String numStr) {
        int[] result = new int[numStr.length()];
        for (int i = 0; i < result.length; i++) {
            // 下标从小到大表示数位的从低到高，方便进位运算
            result[i] = numStr.charAt(numStr.length() - i - 1) - '0';
        }
        return result;
    }

    /**
     * 重载
     * 两个无符号整数相加
     */
    private static int[] add(int[] number1, int[] number2) {
        // 找到更大的一个数，保证number2不小于number1
        if (number2.length < number1.length) {
            int[] temp = number1;
            number1 = number2;
            number2 = temp;
        }

        int[] result = new int[number2.length + 1];
        // 进位
        int carry = 0;

        // 此时number1更小
        for (int i = 0; i < number1.length; i++) {
            result[i] = number1[i] + number2[i] + carry;
            carry = result[i] / 10;
            result[i] %= 10;
        }

        // 计算剩余部分
        for (int i = number1.length; i < number2.length; i++) {
            result[i] = carry + number2[i];
            carry = result[i] / 10;
            result[i] %= 10;
        }

        // 最后可能还有进位
        if (carry == 1) {
            result[result.length - 1] = 1;
            return result;
        } else {
            int[] res = new int[result.length - 1];
            System.arraycopy(result, 0, res, 0, res.length);
            return res;
        }
    }

    /**
     * 将数组表示的整数转换成字符串
     */
    private static String toNumberStr(int[] number) {
        if (number == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = number.length - 1; i >= 0; i--) {
            sb.append(number[i]);
        }

        return sb.toString();
    }


    /**
     * 比较两个整数是否相等，下标由小到大表示由低位到高位，忽略最高有效位上的前导0
     * @return number1 > number2返回1，number1 = number2返回0，number1 < number2返回-1
     */
    private static int compare(int[] number1, int[] number2) {
        if (number1 == null && number2 == null) {
            return 0;
        }
        if (number1 == null) {
            return -1;
        }
        if (number2 == null) {
            return 1;
        }

        int lastNumber1Index = number1.length - 1;
        int lastNumber2Index = number2.length - 1;

        // 找number1的最高有效位的位置，至少有一位
        while (lastNumber1Index >= 1 && number1[lastNumber1Index] == 0) {
            lastNumber1Index--;
        }

        // 找number2的最高有效位的位置，至少有一位
        while (lastNumber2Index >= 1 && number2[lastNumber2Index] == 0) {
            lastNumber2Index--;
        }

        if (lastNumber1Index > lastNumber2Index) {
            return 1;
        } else if (lastNumber1Index < lastNumber2Index) {
            return -1;
        } else {
            // 位数一样，比较每一个数位上的值，从高位到低位进行比较
            for (int i = lastNumber1Index; i >= 0; i--) {
                if (number1[i] > number2[i]) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        }
    }

    /**
     * 做减法number1-number2，保证number1大于等于number2
     */
    private static int[] minus(int[] number1, int[] number2) {
        number1 = format(number1);
        number2 = format(number2);

        // 做减法number1-number2，保证number1大于等于number2
        int[] result = new int[number1.length];

        // 当前位被借位
        int carry = 0;
        int temp;

        for (int i = 0; i < number2.length; i++) {
            temp = number1[i] - carry - number2[i];

            // 当前位够减
            if (temp >= 0) {
                result[i] = temp;
                // 没有进行借位
                carry = 0;
            } else {
                // 当前位够减
                result[i] = temp + 10;
                // 进行借位
                carry = 1;
            }
        }

        // 还有借位或者number1比number2位数多，要将number1中的数位复制到result中
        for (int i = number2.length; i < number1.length; i++) {
            temp = number1[i] - carry;
            // 当前位够减
            if (temp >= 0) {
                result[i] = temp;
                carry = 0;
            } else {
                result[i] = temp + 10;
                carry = 1;
            }
        }

        return format(result);
    }

    /**
     * 将整数进行格式化，去掉高位的前导0
     */
    private static int[] format(int[] number) {
        int len = number.length - 1;
        // 找到最高有效位
        while (len > 0 && number[len] == 0) {
            len--;
        }

        int[] newNumber = new int[len + 1];
        System.arraycopy(number, 0, newNumber, 0, newNumber.length);
        return newNumber;
    }
}
```

# 59.找出字符串中第一个只出现一次的字符

```
输入描述:
输入几个非空字符串
asdfasdfo
aabb

输出描述:
输出第一个只出现一次的字符，如果不存在输出-1
o
-1
```

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            char result = findFirst(input);
            if (result == ' ') {
                System.out.println(-1);
            } else {
                System.out.println(result);
            }

        }
    }

    private static char findFirst(String str) {
        Map<Character, Integer> linkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // 如果出现过，则标记为无效
            if (linkedHashMap.containsKey(ch)) {
                linkedHashMap.put(ch, Integer.MAX_VALUE);
            } else {
                // 标记第一次出现的
                linkedHashMap.put(ch, 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : linkedHashMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return ' ';
    }
}
```

# 63.DNA序列

一个DNA序列由A/C/G/T四个字母的排列组合组成。**G和C的比例（定义为GC-Ratio）是序列中G和C两个字母的总的出现次数除以总的字母数目（也就是序列长度）**。在基因工程中，这个比例非常重要。因为高的GC-Ratio可能是基因的起始点。

给定一个很长的DNA序列，以及要求的最小子序列长度，研究人员经常会需要在其中找出GC-Ratio最高的子序列。
 
```
输入描述:
输入一个string型基因序列，和int型子串的长度
AACTGTGCACGACCTGA
5

输出描述:
找出GC比例最高的子串，如果有多个输出第一个的子串
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            int len = scanner.nextInt();
            System.out.println(maxRatioStr(input, len));
        }
    }
    
    /**
    * 初始化两个数组，一个序列数值数组seqValue[N]，一个序列和数组SUM[N]
    * 先遍历一边序列，为C或者G则K[i]为1，否则则置为0，然后计算连续M个K[I]之和存入SUM
    */
    private static String maxRatioStr(String str, int number) {
        int[] seqValue = new int[str.length()];
        int[] sum = new int[str.length()];
        
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == 'C' || ch == 'G') {
                seqValue[i] = 1;
            } else {
                seqValue[i] = 0;
            }
        }
        
        for (int i = 0; i < seqValue.length - number; i++) {
            for (int j = 0; j < number; j++) {
                sum[i] += seqValue[i + j];
            }
        }
        
        int max = 0;
        int index = 0;
        for (int i = 0; i < sum.length; i++) {
            if (sum[i] > max) {
                max = sum[i];
                index = i;
            }
        }
        
        return str.substring(index, index + number);
    }
}
```

# 65.(动态规划)查找两个字符串a,b中的最长公共子串

查找两个字符串a，b中的最长公共子串。若有多个，输出在较短串中最先出现的那个。

```
输入描述:
输入两个字符串
abcdefghijklmnop
abcsafjklmnopqrstuvw

输出描述:
返回重复出现的字符
jklmnop
```

```java
import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
          BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
          String line = "";
          while((line = sc.readLine()) != null){
              String strA = line;
              String strB = sc.readLine();
              if (strA.length() > strB.length()) {
                String temp = strA;
                strA = strB;//str1是较短的子串
                strB = temp;
              }
              System.out.println(findMaxCommonStr(strA,strB));
          }
    }
    
    private static String findMaxCommonStr(String str1, String str2) {
        char[] str1Char = str1.toCharArray();
        char[] str2Char = str2.toCharArray();
        int[][] dp = new int[str1Char.length + 1][str2Char.length + 1];
        int maxLen = 0;
        int start = 0;
        for (int i = 1; i <= str1Char.length; i++) {
            for (int j = 1; j <= str2Char.length; j++) {
                if (str1Char[i - 1] == str2Char[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        start = i - maxLen;//记录最长公共子串的起始位置
                    }
                }
            }
        }
        return str1.substring(start, start + maxLen);
    }
}
```

# 70.矩阵乘法计算量估算

矩阵乘法的运算量与矩阵乘法的顺序强相关。


例如：A是一个50×10的矩阵，B是10×20的矩阵，C是20×5的矩阵

计算$A*B*C$有两种顺序：((AB)C)或者(A(BC))，前者需要计算15000次乘法，后者只需要3500次。


编写程序计算不同的计算顺序需要进行的乘法次数

```
输入描述:
输入多行，先输入要计算乘法的矩阵个数n，每个矩阵的行数，列数，总共2n的数，最后输入要计算的法则
3
50 10
10 20
20 5
(A(BC))

输出描述:
输出需要进行的乘法次数
3500
```

以$[m,n]$表示m行n列的矩阵，以$[m,n]*[n,p]$为例进行矩阵乘法规则说明：
- 第一个矩阵取一行，第二个矩阵取一列，计算时是对应相乘，有n次乘法。
- 还是第一个矩阵刚参加运算的那行，第二个矩阵的所有列（共$p$列），会有$n*p$次乘法
- 第一个矩阵的所有行（共m行）参加运算，共会有$n*p*m$次乘法运算。
- 得出$[m,n]*[n,p]$共会有$n*p*m$次乘法运算，运算后的矩阵为$[m,p]$

过对`rule`进行逐个字符的遍历，并进行相应处理：
- 字符是**左括号**，出栈
- 字符是**右括号**，入栈`-1`
- 字符是**非括号**，入栈

出栈处理：
- 如果只有一个矩阵，无法进行矩阵乘法，程序结束。
- 如果有多个矩阵，出栈最后两个。注：**先出栈的为第二个矩阵，后出栈的为第一个矩阵**
- 计算单次矩阵乘法运算的乘法次数，得到运算后的新矩阵
- **将新矩阵入栈**，继续参与后面的运算。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
 
public class Main {
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while ((str = br.readLine()) != null) {
            int num = Integer.parseInt(str);
            int [][] arr = new int[num][2];
             
            for (int i = 0;i<num;i++) {
                String [] matrix = br.readLine().split(" ");
                arr[i][0] = Integer.parseInt(matrix[0]);
                arr[i][1] = Integer.parseInt(matrix[1]);
            }
             
            int n = arr.length -1;
            char [] rule = br.readLine().toCharArray();
            Stack<Integer> stack = new Stack<>();
            int sum = 0;
            for (int i = rule.length - 1; i >= 0; i--) {
                char one = rule[i];
                if (one == ')') {
                    stack.push(-1);
                } else if (one == '(') {
                    int n1 = stack.pop();
                    int n2 = stack.pop();
                    sum += arr[n1][0] * arr[n2][0] * arr[n2][1];
                    arr[n1][1] = arr[n2][1];
                    stack.pop();
                    stack.push(n1);
                } else {
                    stack.push(n);
                    n--;
                }
            }
            System.out.println(sum);
        }
    }
}
```

# 71.字符串通配符

在计算机中，通配符一种特殊语法，广泛应用于文件搜索、数据库、正则表达式等领域。现要求各位实现字符串通配符的算法。

实现如下2个通配符：
`*`：匹配0个或以上的字符（字符由英文字母和数字0-9组成，不区分大小写。下同）
`？`：匹配1个字符

```
输入描述:
先输入一个带有通配符的字符串，再输入一个需要匹配的字符串
te?t*.*
txt12.xls

输出描述:
返回匹配的结果，正确输出true，错误输出false
```

```java
import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        while(input.hasNext()){
            String str1 = input.nextLine();
            String str2 = input.nextLine();
            System.out.println(helper(str1, str2, 0, 0));
        }
    }

    private static boolean helper(String str1, String str2, int point1, int point2) {
        //base case
        if (point1 == str1.length() && point2 == str2.length()){
            return true;
        }else if (point1 == str1.length() || point2 == str2.length()){
            return false;
        }
        // 遇到'*'两种情况，要不就s2继续往后跳先不比较，要不就各跳过一个比较后面
        if (str1.charAt(point1) == '*'){
            return helper(str1, str2, point1, point2 + 1) || helper(str1, str2, point1 + 1, point2 + 1);
        // 遇到'?'或对应的两个字符一样时，直接指针都往后移一个继续比较
        } else if (str1.charAt(point1) == '?' || str1.charAt(point1) == str2.charAt(point2)) {
            return helper(str1, str2, point1 + 1, point2 + 1);
        } else {
            return false;
        }
    }
}
```

# 73.计算日期到天数转换

输入某年某月某日，判断这一天是这一年的第几天？。

接口设计及说明：
```java
/*****************************************************************************
Description   : 数据转换
Input Param   : year 输入年份
                Month 输入月份
                Day 输入天
                    
Output Param  :
Return Value  : 成功返回0，失败返回-1（如：数据错误）
*****************************************************************************/
public static int iConverDateToDay(int year, int month, int day)
{
    /* 在这里实现功能，将结果填入输入数组中*/ 
    return 0;
}
 
/*****************************************************************************
Description   : 
Input Param   :
                    
Output Param  :
Return Value  : 成功:返回outDay输出计算后的第几天;
                失败:返回-1
*****************************************************************************/
public static int getOutDay()
{
    return 0;
}
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();

            System.out.println(calculate(year, month, day));
        }

        scanner.close();
    }

    private static int calculate(int year, int month, int day) {

        int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // 如果是闰年
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            dayOfMonth[1] = 29;
        }

        if (month < 1 || month > 12 || day < 1 || day > dayOfMonth[month - 1]) {
            return -1;
        }


        for (int i = 0; i < month - 1; i++) {
            day += dayOfMonth[i];
        }

        return day;
    }
}
```

# 74.参数解析

在命令行输入如下命令：`xcopy /s c:\ d:\`

各个参数如下： 
```
参数1：命令字xcopy 
参数2：字符串/s
参数3：字符串c:\
参数4: 字符串d:\
```
请编写一个参数解析程序，实现将命令行各个参数解析出来。


解析规则： 

1. 参数分隔符为空格 
2. 对于用`“”`包含起来的参数，如果中间有空格，不能解析为多个参数。比如在命令行输入`xcopy /s “C:\program files” “d:\”`时，参数仍然是4个，第3个参数应该是字符串`C:\program files`，而不是`C:\program`，注意**输出参数时，需要将`“”`去掉**，引号不存在嵌套情况。
3. 参数不定长 
4. 输入由用例保证，不会出现不符合要求的输入 
 
```
输入描述:
输入一行字符串，可以有空格
xcopy /s c:\\ d:\\

输出描述:
输出参数个数，分解后的参数，每个参数都独占一行
4
xcopy
/s
c:\\
d:\\
```

```java
import java.util.*;
import java.io.*;
  
public class Main{
    public static void main(String []args)throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = reader.readLine().split(" ");
        System.out.println(strings.length);
        for (String temp : strings) {
            if (temp.charAt(0) != '\"') {
                System.out.println(temp);
            } else {
                System.out.println(temp.substring(1, temp.length() + 1));
            }
        }
    }
}
```

# 75.公共字串计算

https://github.com/Chenzf2018/hawei-online-test/blob/master/071-%E5%85%AC%E5%85%B1%E5%AD%90%E4%B8%B2%E8%AE%A1%E7%AE%97/src/Main.java

计算两个字符串的最大公共字串的长度，字符不区分大小写

```
输入描述:
输入两个字符串
asdfas
werasdfaswer

输出描述:
输出一个整数
6
```

```java
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String n = scanner.next();
            String m = scanner.next();
            // 返回长度
            System.out.println(maxSubstringLength(n, m));
            // 返回序列
            System.out.println(maxSubsequenceLength(n, m));
        }

        scanner.close();
    }

    private static int maxSubstringLength(String a, String b) {
        int aLen = a.length() + 1;
        int bLen = b.length() + 1;
        int max = 0;

        // 初始值默认为0
        int[][] f = new int[aLen][bLen];


        for (int i = 1; i < aLen; i++) {
            for (int j = 1; j < bLen; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = 0;
                }

                if (f[i][j] > max) {
                    max = f[i][j];
                }
            }
        }

        return max;
    }
    
    private static int maxSubsequenceLength(String a, String b) {

        int aLen = a.length() + 1;
        int bLen = b.length() + 1;

        // 初始值默认为0
        int[][] f = new int[aLen][bLen];


        for (int i = 1; i < aLen; i++) {
            for (int j = 1; j < bLen; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1] + 1;
                } else {
                    f[i][j] = Math.max(f[i - 1][j], f[i][j - 1]);
                }
            }
        }

        return f[aLen - 1][bLen - 1];
    }
}
```

# 78.超长正整数相加

