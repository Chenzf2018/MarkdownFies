# Shell
&emsp;&emsp;`Shell`就是一个程序，它接受从键盘输入的命令，然后把命令传递给操作系统去执行。几乎所有的Linux发行版都提供一个名为`bash`的程序，`bash`是`shell`的一种；是`Bourne Again SHell`的首字母缩写；是`sh`的增强版，`sh`是最初Unix的`shell`程序。可以使用`terminal`与`shell`交互。

`chenzufeng@Chenzf-desktop:/mnt/c/Users/Chenzf$`：`用户名@主机名 当前工作目录`。
如果提示符的最后一个字符是`#`，而不是`$`，那么这个终端会话就有超级用户权限。

系统安装目录：
`C:\Users\Chenzf\AppData\Local\Packages\CanonicalGroupLimited.UbuntuonWindows_79rhkp1fndgsc\LocalState\rootfs\mnt`

# 简单命令
 ## 文件命名规则
 &emsp;&emsp;以`.`字符开头的文件名是隐藏文件。这表示，`ls`命令不能列出它们，但使用`ls -a`命令可以显示；文件名和命令名是大小写敏感的；Linux没有“文件扩展名”的概念；想表示词与词间的空格，用下划线字符来代替。

## 查看时间`date`和日期`cal`
```{.line-numbers highlight=1}
chenzufeng@Chenzf-desktop:/mnt/c/Users/Chenzf$ date
Sun Mar  8 17:15:19 CST 2020
chenzufeng@Chenzf-desktop:/mnt/c/Users/Chenzf$ cal
     March 2020
Su Mo Tu We Th Fr Sa
 1  2  3  4  5  6  7
 8  9 10 11 12 13 14
15 16 17 18 19 20 21
22 23 24 25 26 27 28
29 30 31
```

## 显示工作目录pwd(print working directory)
&emsp;&emsp;Linux以分层目录结构来组织所有文件。所有文件组成了一棵树型目录。文件系统中的第一级目录称为根目录。
```
chenzufeng@Chenzf-desktop:/mnt/c/Users/Chenzf$ pwd
/mnt/c/Users/Chenzf
```

## 更改目录cd(change directory)
&emsp;&emsp;路径名可通过两种方式来指定，一个是绝对路径，另一个是相对路径。

### 绝对路径
&emsp;&emsp;绝对路径开始于根目录，紧跟着目录树的一个个分支，一直到达期望的目录或文件。
```
chenzufeng@Chenzf-desktop:/mnt/c/Users/Chenzf$ cd /mnt/d
chenzufeng@Chenzf-desktop:/mnt/d$
```

### 相对路径
&emsp;&emsp;相对路径开始于工作目录。符号`.`指的是当前工作目录，`..`指的是当前工作目录的父目录。
```
chenzufeng@Chenzf-desktop:/mnt/d$ cd .
chenzufeng@Chenzf-desktop:/mnt/d$ cd ..
chenzufeng@Chenzf-desktop:/mnt$ cd c
chenzufeng@Chenzf-desktop:/mnt/c$

chenzufeng@Chenzf-desktop:/mnt$ cd ./c
chenzufeng@Chenzf-desktop:/mnt/c$
```


## 列出目录内容ls(list)
```
chenzufeng@Chenzf-desktop:/mnt/c/Users/Chenzf$ cd /mnt/d
chenzufeng@Chenzf-desktop:/mnt/d$ ls
'$RECYCLE.BIN'      Learning_Java           SQL                                 刷题教程        视频
 Git                Learning_Linux         'System Volume Information'          密码.txt        计算机网络
'Huawei Share'      Learning_Python         WinSoftware                         工作单位.xlsx
 Learning_AI        LeetCode_PointToOffer   markdown-cheatsheet-Github.pdf      日程表.xlsx
 Learning_English   MarkdownFiles           上海交通大学2019-2020学年校历.pdf   电子书
 ```

### 列出指定目录的内容
&emsp;&emsp;列出用户主目录（用字符`~`代表）和`/usr`目录的内容：
 ```
chenzufeng@Chenzf-desktop:/$ ls ~ /usr
/home/chenzufeng:

/usr:
bin  games  include  lib  local  sbin  share  src

chenzufeng@Chenzf-desktop:/$ cd home
chenzufeng@Chenzf-desktop:/home$ ls
chenzufeng
```

### 以长模式输出
&emsp;&emsp;`-a`列出所有文件（包括隐藏文件）；`-l`以长格式显示结果。
```
chenzufeng@Chenzf-desktop:/$ ls -al
total 580
drwxr-xr-x  1 root root   4096 Mar  7 23:06 .
drwxr-xr-x  1 root root   4096 Mar  7 23:06 ..
drwxr-xr-x  1 root root   4096 Mar  7 23:06 bin
drwxr-xr-x  1 root root   4096 May 21  2019 boot
drwxr-xr-x  1 root root   4096 Mar  8 17:11 dev
drwxr-xr-x  1 root root   4096 Mar  8 16:56 etc
drwxr-xr-x  1 root root   4096 Mar  7 23:07 home
-rwxr-xr-x  1 root root 591344 Jan  1  1970 init
drwxr-xr-x  1 root root   4096 May 21  2019 lib
drwxr-xr-x  1 root root   4096 May 21  2019 lib64
drwxr-xr-x  1 root root   4096 May 21  2019 media
drwxr-xr-x  1 root root   4096 Mar  7 23:06 mnt
drwxr-xr-x  1 root root   4096 May 21  2019 opt
dr-xr-xr-x  9 root root      0 Mar  8 16:56 proc
drwx------  1 root root   4096 May 21  2019 root
drwxr-xr-x  1 root root   4096 Mar  8 16:56 run
drwxr-xr-x  1 root root   4096 Mar  7 23:06 sbin
drwxr-xr-x  1 root root   4096 Mar 21  2019 snap
drwxr-xr-x  1 root root   4096 May 21  2019 srv
dr-xr-xr-x 12 root root      0 Mar  8 16:56 sys
drwxrwxrwt  1 root root   4096 Mar  8 17:11 tmp
drwxr-xr-x  1 root root   4096 May 21  2019 usr
drwxr-xr-x  1 root root   4096 May 21  2019 var
```

#### 长格式输出信息含义
`drwxr-xr-x  1 root root   4096 Mar  7 23:06 .`
&emsp;&emsp;`drwxr-xr-x`：对于文件的访问权限。第一个字符指明<font color=red>文件类型</font>。在不同类型之间，开头的`-`说明是一个普通文件，`d`表明是一个目录；其后三个字符是<font color=red>文件所有者的访问权限</font>；再其后的三个字符是<font color=red>文件所属组中成员的访问权限</font>，最后三个字符是<font color=red>其他所有人的访问权限</font>。

&emsp;&emsp;`1`：文件的硬链接（hard link）数目；`root root`：文件属主的用户名和文件所属用户组的名字；`4096 Mar  7 23:06 .`：以字节表示的文件大小、上次修改文件的时间和日期、文件名。

### 用`less`浏览文件内容
&emsp;&emsp;`chenzufeng@Chenzf-desktop:/mnt/d$ less 密码.txt`，按下`q`键，退出`less`。

### 根目录内容
```
chenzufeng@Chenzf-desktop:/$ ls
bin  boot  dev  etc  home  init  lib  lib64  media  mnt  opt  proc  root  run  sbin  snap  srv  sys  tmp  usr  var
```
在通常的配置环境下，系统会在`/home`下，给每个用户分配一个目录。普通用户只能在他们自己的目录下创建文件；`/mnt`目录包含可移除设备的挂载点。`/usr`目录包含普
通用户所需要的所有程序和文件。

## 符号链接和硬链接
&emsp;&emsp;硬链接(`Hard links`)和符号链接(`symbolic links`)比起来，<font color=red>硬链接是最初`Unix`创建链接的方式，而符号链接更加现代</font>。

<font color=red>在默认情况下，每个文件有一个硬链接</font>（`hard link not allowed for directory`），这个硬链接给文件起名字。当我们创建一个硬链接以后，就为文件创建了一个额外的目录条目。硬链接有两个重要局限性：
* 一个硬链接不能关联它所在文件系统之外的文件。这是说<font color=red>一个链接不能关联与链接本身不在同一个磁盘分区上的文件</font>。
* 一个硬链接不能关联一个目录。

一个硬链接和文件本身没有什么区别。不像符号链接，<font color=red>当你列出一个包含硬链接的目录内容时，你会看到没有特殊的链接指示说明。当一个硬链接被删除时，这个链接被删除，但是文件本身的内容仍然存在（这是说，它所占的磁盘空间不会被重新分配），直到所有关联这个文件的链接都删除掉</font>。

知道硬链接很重要，因为你可能有时会遇到它们，但现在实际中更喜欢使用符号链接。

&emsp;&emsp;创建符号链接是为了克服硬链接的局限性。<font color=red>符号链接生效，是通过创建一个特殊类型的文件，这个文件包含一个关联文件或目录的文本指针。在这一方面，它们和`Windows`的`快捷方式`差不多</font>，当然，符号链接早于`Windows`的快捷方式很多年。

一个符号链接指向一个文件，而且这个符号链接本身与其它的符号链接几乎没有区别。例如，<font color=red>如果你往一个符号链接里面写入东西，那么相关联的文件也被写入。然而，当你删除一个符号链接时，只有这个链接被删除，而不是文件自身</font>。如果删除这个文件早于文件的符号链接，这个链接仍然存在，但是不指向任何东西。在这种情况下，这个链接被称为坏链接。在许多实现中，`ls`命令会以不同的颜色展示坏链接，比如说红色，来显示它们的存在。

### 创建链接
`ln file link`：创建硬链接；
`ln -s item link`：创建符号链接，`item`可以是一个文件或是一个目录。

创建硬链接：

```
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls
exercise  exercise1  link.txt  资料
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -al
total 0
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 11:17 .
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 10 23:14 ..
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:52 exercise
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:28 exercise1
-rwxrwxrwx 1 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:19 资料
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ln link.txt link.txt-hard
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ln link.txt exercise/link.txt-hard
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ln link.txt exercise1/link.txt-hard
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -l
total 0
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 11:18 exercise
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 11:18 exercise1
-rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt
-rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt-hard
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:19 资料
```
注意：`-rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt`和`-rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt-hard`第二个字段显示为`4`，这是`link.txt`的硬链接数(<font color=red>一个文件至少有一个硬链接，因为文件名就是由链接创建的</font>)。

&emsp;&emsp;我们怎样知道实际上`link.txt`和`link.txt-hard`是一样的文件呢？

当考虑到硬链接，想象文件是由两部分组成：`数据部分`包含文件的内容，`名字部分`包含文件的名字，这样可以帮助理解。当我们创建了文件的硬链接，实际上，我们<font color=red>给文件添加了额外的名字</font>，这些名字都涉及一样的数据内容。系统分配了一系列的盘块给所谓的<font color=red>索引节点</font>，它和文件名字想关联。因此每个硬链接都关系到一个具体的索引节点，这个节点包含了文件的内容。

```
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -li
total 0
1970324836994009 drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 11:18 exercise
4222124650679261 drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 11:18 exercise1
2814749767358801 -rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt
2814749767358801 -rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt-hard
2251799813704664 drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:19 资料
```
第一字段表示文件索引节点号，正如我们所见到的，`link.txt`和`link.txt-hard`共享一样的索引节点号，这就证实这两个文件是一样的文件。

创建符号链接：

&emsp;&emsp;建立符号链接的目的是为了克服硬链接的两个缺点：硬链接不能跨越物理设备，硬链接不能关联目录，只能是文件。符号链接是文件的特殊类型，它包含一个指向<font color=red>目标文件或目录</font>的文本指针。

```{.line-numbers highlight=2}
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ln -s link.txt link.txt-sym
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ln -s ../link.txt exercise/link.txt-sym
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ln -s ../link.txt exercise1/link.txt-sym
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -li
total 0
1970324836994009 drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 13:19 exercise
4222124650679261 drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 13:19 exercise1
2814749767358801 -rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt
2814749767358801 -rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt-hard
2533274790648147 lrwxrwxrwx 1 chenzufeng chenzufeng    8 Mar 15 13:18 link.txt-sym -> link.txt
2251799813704664 drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:19 资料
```
注意：`ln -s ../link.txt exercise/link.txt-sym`：When we create a symbolic link, we are creating a text description of where the target file is relative to the symbolic link.

```{.line-numbers highlight=4}
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -l exercise
total 0
-rwxrwxrwx 4 chenzufeng chenzufeng  0 Mar 15 11:17 link.txt-hard
lrwxrwxrwx 1 chenzufeng chenzufeng 11 Mar 15 13:19 link.txt-sym -> ../link.txt
```
`link.txt-sym -> ../link.txt`：符号链接`link.txt-sym`是与路径为`../link.txt`的文件相关联的。

目录`exercise`中，`link.txt-sym`的列表说明了它是一个符号链接，通过在第一字段`lrwxrwxrwx`中的首字符`l`可知，并且它还指向`../link.txt`。相对于`link.txt-sym`的存储位置，`link.txt`在它的上一个目录。同时注意，符号链接文件的长度是11，这是字符串`../link.txt`所包含的字符数，而不是符号链接所指向的文件长度。


## 操作文件和目录
&emsp;&emsp;虽然图形文件管理器能轻松地实现简单的文件操作，但是对于复杂的文件操作任务，则使用命令行程序比较容易完成。例如，怎样复制一个目录下的`HTML`文件到另一个目录，但这些`HTML`文件不存在于目标目录，或者是文件版本新于目标目录里的文件？要完成这个任务，使用文件管理器相当难，使用命令行相当容易：
`cp -u *.html destination`

### 通配符
通配符 | 意义 | 
:-:  | :-:     | 
*    | 匹配任意多个字符（包括零个或一个）|
？   | 匹配任意一个字符（不包括零个）|

### mkdir(make directory)
&emsp;&emsp;创建目录：`mkdir directory...`
```
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ mkdir exercise exercise1
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls
exercise  exercise1  资料
```

### 复制文件和目录
&emsp;&emsp;复制单个文件或目录`item1`到文件或目录`item2`：`cp item1 item2`；复制多个文件或目录到一个目录下：`cp item... directory`。

```{.line-numbers highlight=1}
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ cp -r exercise1 exercise2 exercise
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ cd exercise
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ ls
exercise1  exercise2
```

`cp -r`：递归地复制目录及目录中的内容。<font color=red>当复制目录时，需要这个选项</font>

### 移动和重命名文件
`mv item1 item2`：把文件或目录`item1`移动或重命名为`item2`；
`mv item... directory`；把一个或多个条目从一个目录移动到另一个目录中。

```{.line-numbers highlight=5}
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ mv exercise1 exercise3
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ mv exercise2 exercise4
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ ls
exercise3  exercise4
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ mv exercise3 exercise4 ..
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ ls
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux/exercise$ cd ..
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls
exercise  exercise1  exercise2  exercise3  exercise4  资料
```

### 删除文件和目录
`rm -r item...`：递归地删除文件，这意味着，如果要删除一个目录，而此目录又包含子目录，那么子目录也会被删除。<font color=red>要删除一个目录，必须指定这个选项</font>。

首先删除一个硬链接：
```{.line-numbers highlight=14}
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -l
total 0
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 13:19 exercise
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 13:19 exercise1
-rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt
-rwxrwxrwx 4 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt-hard
lrwxrwxrwx 1 chenzufeng chenzufeng    8 Mar 15 13:18 link.txt-sym -> link.txt
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:19 资料
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ rm link.txt-hard
chenzufeng@Chenzf-desktop:/mnt/d/Learning_Linux$ ls -l
total 0
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 13:19 exercise
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 13:19 exercise1
-rwxrwxrwx 3 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt
lrwxrwxrwx 1 chenzufeng chenzufeng    8 Mar 15 13:18 link.txt-sym -> link.txt
drwxrwxrwx 1 chenzufeng chenzufeng 4096 Mar 15 10:19 资料
```
`-rwxrwxrwx 3 chenzufeng chenzufeng    0 Mar 15 11:17 link.txt`：文件`link.txt-hard`消失了，文件`link.txt`的链接数从4个减至3个。

接着删除文件`link.txt`：
<img src=LinuxImages/坏链接.png>
由于指向的文件已经不存在了，所以符号链接变成了坏链接。
<img src=LinuxImages/坏链接1.png>

对于符号链接，执行的大多数文件操作是针对链接的对象，而不是链接本身。而`rm`命令是个特例。当你删除链接的时候，删除链接本身，而不是链接的对象。

## 用find命令查找最近修改过的文件

查找最近30分钟修改的当前目录下的.php文件
`find . -name '*.php' -mmin -30`

查找最近24小时修改的当前目录下的.php文件
`find . -name '*.php' -mtime 0`

查找最近24小时修改的当前目录下的.php文件，并列出详细信息
`find . -name '*.inc' -mtime 0 -ls`

查找当前目录下，最近24-48小时修改过的常规文件。
`find . -type f -mtime 1`

查找当前目录下，最近1天前修改过的常规文件。
`find . -type f -mtime +1`