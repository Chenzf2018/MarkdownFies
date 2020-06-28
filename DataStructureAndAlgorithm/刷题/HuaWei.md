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

# 2.明明的随机数

明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机**生成了N个1到1000之间的随机整数（N≤1000），对于其中重复的数字，只保留一个，把其余相同的数去掉**，不同的数对应着不同的学生的学号。然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。请你协助明明完成**去重**与**排序**的工作(同一个测试用例里可能会有多组数据，希望大家能正确处理)。


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
        while(input.hasNext()){
            int num = input.nextInt();
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

# 3.计算字符个数

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

# 4.进制转换

写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。（多组同时输入）

输入描述：输入一个十六进制的数值字符串。

输出描述：输出该数值的十进制字符串。

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

# 5.字符串分隔

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

/**
 * 5.字符串分隔
 */

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

# 6.取近似值

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

# 7.汽水瓶

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

# 8.质数因子

输入一个正整数（输入一个`long`型整数），按照从小到大的顺序输出它的所有质因子（重复的也要列举）（如180的质因子为2 2 3 3 5 ）最后一个数后面也要有空格。

