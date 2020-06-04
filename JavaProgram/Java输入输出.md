# Scanner

方法`nextByte(), nextShort(), nextInt(), nextLong(), nextFloat(), nextDouble()和next()`等都称为**标记读取方法**（`token-reading method`)，因为它们会读取用**分隔符**分隔开的标记。**默认情况下，分隔符是空格**。可以使用`useDelimiter(String regex)`方法设置新的分隔符模式。

一个输入方法是如何工作的呢？

一个标记读取方法首先**跳过任意分隔符**（默认情况下是**空格**)，然后读取一个**以分隔符结束的标记**。对应于`nextByte(), nextShort(), nextInt(), nextLong(), nextFloat(), nextDouble()`，这个标记就分别被自动地转换为一个`byte, short, int, long, float, double`型的值。对于`next()`方法而言，是无须做转换的。如果标记和期望的类型不匹配，就会抛出一个运行异常`java.util.InputMismatchException()`。

方法`next()`和`nextLine()`都会读取一个字符串。`next()`方法读取一个由**分隔符**分隔的字符串，但是`nextLine()`读取一个以**换行符**结束的行，然后**返回在行分隔符之前的字符串**。

## next()与nextLine()区别

**标记读取方法不能读取标记后面的分隔符**。如果在标记读取方法之后调用`nextLine()`，该方法读取从这个分隔符开始，到这行的行分隔符结束的字符。**这个行分隔符也被读取**，但是它不是`nextLine()`返回的字符串部分。

```java
package Basic;

import java.util.Scanner;

public class TestScanner {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int value = input.nextInt();
        String str = input.nextLine();
        System.out.println(value);
        System.out.println(str);
    }
}
/*
34 567
34
 567  // 注意5前的空格
*/

/*
34  // 输入34与“回车”
34

*/
```

# 格式化输出

```java
double x = 1000.0 / 3.0;
System.out.println(x);
System.out.printf("%8.2f", x);  // 用8个字符的宽度和小数点后两个字符的精度打印x 
333.3333333333333
  333.33
```

<div align=center><img src=Basic\格式化输出.png></div>

# 字节流与字符流

文件可以分类为**文本**或者**二进制**的。Java**源程序**存储在文本文件中，可以使用**文本编辑器**读取，而Java**类**是二进制文件，由**Java虚拟机**读取。二进制文件的优势在于它的处理效率比文本文件高。

Java提供了许多实现文件输入/输出的类。这些类可以分为**文本I/O类**（text I/O class)（使用`Scanner`类读取文本数据，使用`PrintWriter`类写文本数据）和**二进制I/O类**（binary I/O class)。

<div align=center><img src=Basic\TextIOvsBinaryIO.jpg></div>

文本I/O需要编码和解码，而二进制I/O不需要。

对于文本编辑器或文本输出程序创建的文件，应该使用文本输入来读取，对于Java二进制输出程序创建的文件，应该使用二进制输人来读取。

<div align=center><img src=Basic\二进制IO类.jpg></div>

`FilelnputStream`类和`Fi1eOutputStream`类用于从/向文件读取/写入**字节**。它们的所有方法都是从`InputStream`类和`OutputStream`类继承的。


**过滤器数据流**（filter stream) 是为某种目的过滤字节的数据流。基本字节输入流提供的读取方法`read()`只能用来**读取字节**。如果要**读取整数值、双精度值或字符串**，那就需要一个过滤器类来**包装字节输入流**。使用过滤器类就可以读取整数值、双精度值和字符串，而不是字节或字符。

`DatalnputStream`从数据流读取**字节**，并且将它们转换为合适的**基本类型值或字符串**。`DataOutputStream`将**基本类型的值或字符串**转换为**字节**，并且将字节输出到数据流。

`BufferedInputStream`类和`BufferedOutputStream`类可以通过**减少磁盘读写次数**来提髙输人和输出的速度。使用`BufferedInputStream`时，磁盘上的整块数据一次性地读入到内存中的缓冲区中。然后从缓冲区中将个别的数据传递到程序中。使用`BufferedOutputStream`，个别的数据首先写人到内存中的缓冲区中。当缓冲区已满时，缓冲区中的所有数据一次性写入到磁盘中。

<div align=center><img src=Basic\BufferedInputOutputStream.jpg></div>

从数据传输方式或者说是运输方式角度看，可以将`IO`类分为：1.字节流；2.字符流。

**字节流读取单个字节，字符流读取单个字符**（一个字符根据编码的不同，对应的字节也不同，如 UTF-8 编码是 3 个字节，中文编码是 2 个字节）。**字节流用来处理二进制文件**（图片、MP3、视频文件）；**字符流用来处理文本文件**（可以看做是特殊的二进制文件，使用了某种编码，人可以阅读）。简而言之，字节是个计算机看的，字符才是给人看的。

<div align=center><img src=Basic\字节流与字符流.jpg></div>