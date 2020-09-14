# 大O表示法

<div align=center><img src=Pictures/大O表示法.png></div>

# 排序效率对比

<div align=center><img src=Pictures/排序算法.png></div>


# 冒泡排序

将数组中相邻的两个数进行比较，较小的数值向下沉，数值比较大的向上浮。

<div align=center><img src=Pictures/BubbleSort.png width=70%></div>

<div align=center><img src=Pictures/冒泡排序.gif></div>

`BubbleSort.java`
```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/4
 * @version 1.0
 */

public class BubbleSort {
    /**
     * 设置标志位needNextPass，如果发生了交换needNextPass设置为true；如果没有交换就设置为false。
     * 这样当一轮比较结束后，如果needNextPass仍为false
     * 即：这一轮没有发生交换，说明数据的顺序已经排好，没有必要继续进行下去。
     * @param arr
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void bubbleSort(T[] arr) {
        boolean needNextPass;

        // 共length位元素，需length - 1次排序
        for (int i = 1; i < arr.length; i++) {
            needNextPass = false;
            // 每一次排序需arr.length - i - 1次比较
            System.out.printf("第%d次排序\n", i);
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    needNextPass = true;
                    swap(arr, j, j + 1);
                }
            }

            // 如果needNextPass仍为false，这一轮没有发生交换，说明数据的顺序已经排好
            if (!needNextPass) {
                break;
            }
        }
    }

    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

`TestBubbleSort.java`
``` java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 测试冒泡排序
 * @author Chenzf
 * @date 2020/7/4
 */

public class TestBubbleSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排序的数");
            String[] strings = reader.readLine().split(" ");
            Integer[] arr = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            BubbleSort.bubbleSort(arr);

            System.out.println("\n排序后：");
            for (Integer integer : arr) {
                System.out.print(integer + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
请输入待排序的数
1 2 3 4
第1次排序

排序后：
1 2 3 4 
*/
```

## 算法效率
时间复杂度为$O(n^2)$。


# 选择排序

**从数组中选择最小元素，将它与数组的第一个元素交换位置**。再从数组剩下的元素中选择出最小的元素，将它与数组的第二个元素交换位置。不断进行这样的操作，直到将整个数组排序。

<div align=center><img src=Pictures/SelectionSort.png width=30%></div>

<div align=center><img src=Pictures/选择排序.gif></div>

`SelectionSort.java`
```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/4
 * @version 1.0
 */

public class SelectionSort {
    /**
     * Sorts n objects in an array into ascending order.
     * @param arr
     * @param length 数组的长度
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void selectionSort(T[] arr, int length) {
        // 只需要找length - 1次
        for (int index = 0; index < length - 1; index++) {
            // 最后一个数index = length - 1
            int indexOfNextSmallest = getIndexOfSmallest(arr, index, length - 1);
            swap(arr, index, indexOfNextSmallest);
        }
    }

    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static <T extends Comparable<? super T>> int getIndexOfSmallest(T[] arr, int first, int last) {
        T min = arr[first];
        int indexOfMin = first;

        // index要取到last：index <= last
        for (int index = first + 1; index <= last; index++) {
            if (arr[index].compareTo(min) < 0) {
                min = arr[index];
                indexOfMin = index;
            }
        }

        return indexOfMin;
    }
}

```

`TestSelectionSort.java`
```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 测试选择排序
 * @author Chenzf
 */

public class TestSelectionSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            /*
             System.out.println("请输入数组元素的个数及相应的元素：")
             读取第一行参数
             int length = Integer.parseInt(reader.readLine())
             */
            System.out.println("请输入待排序的数：");
            String[] str = reader.readLine().split(" ");
            // 转化成数组
            Integer[] arr = new Integer[str.length];
            for (int i = 0; i < str.length; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }

            SelectionSort.selectionSort(arr, str.length);

            System.out.println("\n排序后：");
            for (Integer num : arr) {
                System.out.print(num + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
请输入待排序的元素：
15 8 10 2 5

排序后：
2 5 8 10 15 
*/
```

## 算法效率
时间复杂度为$O(n^2)$；不论数组中项的初始次序如何，都需要$O(n^2)$次**比较**，但它仅执行$O(n)$次**交换**！



# 插入排序

举例理解：打扑克牌**顺牌**！

对数组的插入排序将数组分隔(partition) (即划分)为两部分。第一部分是有序的，初始时仅含有数组中的第一项。第二部分含有其余的项。<font color=red>算法从未排序部分移走第一项，并将它插入有序部分中合适的有序位置</font>。从有序部分的末尾开始，朝着开头方向，通过将待排序项与各有序项进行比较来选择合适的位置。当比较时，将有序部分的数组项右移，为插入腾出空间。

<div align=center><img src=Pictures/InsertionSort.png width=50%></div>

<div align=center><img src=Pictures/插入排序.gif></div>

`InsertionSort.java`
```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/4
 * @version 1.0
 */

public class InsertionSort {
    /**
     * 可以理解为顺牌
     * @param arr
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void insertionSort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            T insertedValue = arr[i];
            int j;
            for (j = i - 1; j >= 0 && insertedValue.compareTo(arr[j]) < 0; j--) {
                // arr[j + 1]就是当前要顺的牌；
                // 把牌往后移
                arr[j + 1] = arr[j];
            }
            // 由于j--，要插入的位置是j + 1
            arr[j + 1] = insertedValue;
        }
    }
}
```

`TestInsertionSort.java`
```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chenzf
 * @date 2020/7/4
 */

public class TestInsertionSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排序元素：");
            String[] strings = reader.readLine().split(" ");
            Integer[] arr = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            InsertionSort.insertionSort(arr);

            System.out.println("排序后：");
            for (Integer integer : arr) {
                System.out.print(integer + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
请输入待排序元素：
2 5 8 3 9 4 1
排序后：
1 2 3 4 5 8 9 
*/
```

## 算法效率

时间复杂度为$O(n^2)$；

最优时插入排序是$O(n)$，最坏时是$O(n^2)$。数组越接近有序，插入排序要做的工作越少。


# 希尔排序

希尔排序是插入排序的变体。在插入排序过程中，数组项只移动到相邻位置。当项与正确的有序位置相距甚远时，它必须进行很多次这样的移动。所以<font color=red>当数组完全无序时，插入排序要花很多时间。但当数组基本有序时，插入排序有很好的效率</font>。

希尔排序的思想是使数组中任意间隔为$h$的元素都是有序的，也就意味着，一个$h$有序数组就是$h$个相互独立的有序数组编织在一起组成的一个数组。

下图显示了一个数组及<font color=red>每隔5项</font>组成的子数组。第一个子数组含有整数10、9和7；第二个子数组含有16和6；等等。
<div align=center><img src=Pictures/ShellSort1.png width=90%></div>

现在<font color=red>使用插入排序分别对这6个子数组进行排序</font>，排序后数组比原始状态“更有序”了：
<div align=center><img src=Pictures/ShellSort2.png width=90%></div>

现在形成新的子数组，这次减小下标之间的间隔。<font color=red>`Shell`建议子数组下标间隔是$n/2$，且每趟排序中这个值减半直到为1</font>。示例数组有13项，所以从间隔为6开始。现在将间隔减小到3：
<div align=center><img src=Pictures/ShellSort3.png width=90%></div>

对得到的三个子数组进行插入排序：
<div align=center><img src=Pictures/ShellSort4.png width=90%></div>

将当前间隔3除以2得到1，所以最后一步只是对整个数组进行普通的插入排序。

<div align=center><img src=Pictures/希尔排序.gif></div>


`ShellSort.java`
```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/4
 * @version 1.0
 */

public class ShellSort {
    public static <T extends Comparable<? super T>> void shellSort(T[] arr) {
        int separation = arr.length / 2;
        while (separation > 0) {
            // 将数组分成separation个子数组进行插入排序
            // 注意：begin <= separation - 1；当separation=1时，进行最后一次插入排序
            for (int begin = 0; begin <= separation - 1; begin++) {
                incrementalInsertionSort(arr, begin, separation);
            }
            separation /= 2;
        }
    }

    /**
     * 对每个子数组进行插入排序
     *
     * @param arr
     * @param first
     * @param separation 普通插入排序的间隔为1，这里为separation
     * @param <T>
     */
    private static <T extends Comparable<? super T>>
        void incrementalInsertionSort(T[] arr, int first, int separation) {
        for (int i = first + separation; i < arr.length; i += separation) {
            T insertedValue = arr[i];
            int j;
            for (j = i - separation; j >= 0 && insertedValue.compareTo(arr[j]) < 0; j -= separation) {
                // arr[j + separation] 就是当前抓到的牌；把牌往后移
                arr[j + separation] = arr[j];
            }
            // 由于j -= separation，要插入的位置是j + separation
            arr[j + separation] = insertedValue;
        }
    }
}
```

`TestShellSort.java`
```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chenzf
 * @date 2020/7/4
 */

public class TestShellSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排序元素：");
            String[] strings = reader.readLine().split(" ");
            Integer[] arr = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            ShellSort.shellSort(arr);

            System.out.println("排序后：");
            for (Integer integer : arr) {
                System.out.print(integer + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
请输入待排序元素：
10 16 11 4 15 3 9 6 1 17 8 12 7
排序后：
1 3 4 6 7 8 9 10 11 12 15 16 17 
*/
```

## 希尔排序效率
<div align=center><img src=Pictures/ShellSort5.png width=60%></div>

虽然使用了多次插入排序而不是仅用一次，但对数组最初的排序远比原始数组要小得多，后来的排序是对部分有序的数组进行的，且最后的排序是对几乎全部有序的数组进行的。

因为`incrementalInsertionSort`方法涉及一个循环，而本身又是在嵌套的循环内被调用，所以希尔排序使用了3层嵌套的循环。这样的算法常常是$O(n^3)$的。但可以证明希尔排序的最坏情形仍是$O(n^2)$ 的。如果$n$是2的幂次．则平均情形是$O(n^{1.5})$。如果稍稍调整一下间隔，能使希尔排序的效率更高——当`separation`为偶数时，将其加1，则最坏情形可以改进为$O(n^{1.5})$。


# 归并排序

归并排序算法将数组分为两半，对每部分递归地应用归并排序。在两部分都排好序后，对它们进行归并。该算法采用经典的`分治（divide-and-conquer）策略`（分治法将问题分(divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。

持续将数组划分为子数组，直到每个子数组只包含一个元素。然后，该算法将这些小的子数组归并为稍大的有序子数组，直到最后形成一个有序的数组。
<div align=center><img src=Pictures/MergeSort.png width=70%></div>

**将两个有序数组归并为一个有序数组：**

```java
private static void merge(int[] list1, int[] list2, int[] temp)
{
    int current1 = 0;  // Current index in list1
    int current2 = 0;  // Current index in list2
    int current3 = 0;  // Current index in temp

    while (current1 < list1.length && current2 < list2.length)
    {
        if (list1[current1] < list2[current2])
            temp[current3++] = list1[current1++];
        else
            temp[current3++] = list2[current2++];
    }

    while (current1 < list1.length)
        temp[current3++] = list1[current1++];

    while (current2 < list2.length)
        temp[current3++] = list2[current2++];
}
```

<div align=center><img src=Pictures/MergeSort1.png width=90%></div>

箭头上的数字表示递归调用及合并的次序：
<div align=center><img src=Pictures/MergeSort2.png width=90%></div>

<div align=center><img src=Pictures/归并排序.gif></div>


`MergeSort.java`
```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/5
 * @version 1.0
 */

public class MergeSort {
    public static <T extends Comparable<? super T>> void mergeSort(T[] arr) {
        if (arr.length > 1) {
            // Divide the first half
            @SuppressWarnings("unchecked")
            T[] firstHalf = (T[]) new Comparable<?>[arr.length / 2];
            System.arraycopy(arr, 0, firstHalf, 0, arr.length / 2);
            mergeSort(firstHalf);

            // Divide the second half
            int secondHalfLength = arr.length - arr.length / 2;
            @SuppressWarnings("unchecked")
            T[] secondHalf = (T[]) new Comparable<?>[secondHalfLength];
            System.arraycopy(arr, arr.length / 2, secondHalf, 0, secondHalfLength);
            mergeSort(secondHalf);

            // Merge firstHalf and secondHalf into one arr
            merge(firstHalf, secondHalf, arr);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] arr1, T[] arr2, T[] temp) {
        // Current index in arr1, arr2, temp
        int currentIndex1 = 0;
        int currentIndex2 = 0;
        int currentIndex3 = 0;

        while (currentIndex1 < arr1.length && currentIndex2 < arr2.length) {
            if (arr1[currentIndex1].compareTo(arr2[currentIndex2]) < 0) {
                temp[currentIndex3++] = arr1[currentIndex1++];
            } else {
                temp[currentIndex3++] = arr2[currentIndex2++];
            }
        }

        while (currentIndex1 < arr1.length) {
            temp[currentIndex3++] = arr1[currentIndex1++];
        }

        while (currentIndex2 < arr2.length) {
            temp[currentIndex3++] = arr2[currentIndex2++];
        }

    }
}
```

`TestMergeSort.java`

```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chenzf
 * @date 2020/7/5
 */

public class TestMergeSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排元素：");
            String[] strings = reader.readLine().split(" ");
            Integer[] arr = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            MergeSort.mergeSort(arr);

            System.out.println("排序后：");
            for (Integer integer : arr) {
                System.out.print(integer + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
请输入待排元素：
7 5 9 3 6 0 2 4
排序后：
0 2 3 4 5 6 7 9 
*/
```

## 算法效率

归并排序的时间复杂度在所有情形下都为$O\left(n\log n\right)$，优于冒泡排序、选择排序和插入排序。它对**临时数组**的需求(在合并阶段)是它的缺点。

`java.util.Arrays`类中的`sort`方法是使用归并排序算法的变体来实现的。


# 快速排序
在数组中选择一个称为`主元(pivot)`的元素，将数组分为两部分，**使得第一部分中的所有元素都小于或等于主元，而笫二部分中的所有元素都大于主元。然后对第一部分递归地应用快速排序算法，然后对笫二部分递归地应用快速排序算法**。

**主元的选择会影响算法的性能**。在理想情况下，应该选择能平均划分两部分的主元。为了简单起见，假定将数组的第一个元素选为主元。

一次快速排序：
<div align=center><img src=Pictures/QuickSort.png width=70%></div>

对子数组进行快速排序：
<div align=center><img src=Pictures/QuickSort1.png width=70%></div>

<div align=center><img src=Pictures/快速排序.gif></div>


`QuickSort.java`
```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/5
 * @version 1.0
 */

public class QuickSort {
    public static <T extends Comparable<? super T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] arr, int first, int last) {
        if (last > first) {
            int pivotIndex = getPivotIndex(arr, first, last);
            quickSort(arr, first, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, last);
        }
    }

    private static <T extends Comparable<? super T>> int getPivotIndex(T[] arr, int low, int high) {
        T pivot = arr[low];
        int i = low;
        int j = high + 1;

        while (true) {
            while (arr[++i].compareTo(pivot) < 0) {
                if (i == high) {
                    break;
                }
            }

            while (arr[--j].compareTo(pivot) > 0) {
                if (j == low) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            exchange(arr, i, j);
        }

        exchange(arr, low, j);
        return j;
    }

    private static void exchange(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

`TestQuickSort.java`

```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chenzf
 * @date 2020/7/5
 */

public class TestQuickSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排序元素：");
            String[] strings = reader.readLine().split(" ");
            Integer[] arr = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            QuickSort.quickSort(arr);

            System.out.println("排序后：");
            for (Integer integer : arr) {
                System.out.print(integer + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
请输入待排序元素：
3 5 0 4 6 1 2 4
排序后：
0 1 2 3 4 4 5 6 
*/
```

## 算法效率

快速排序在平均情形下是$O(nlogn)$，但在最坏情况下是$O(n^2)$。`pivot`(枢轴)的选择对快速排序的效率有影响！


# 堆排序

可以使用堆来排序一个数组。如果**将数组项放在最大堆中**，然后**每次删除一个，则可以得到降序排列的项**。

从项的数组创建堆时，使用reheap比使用add的效率高。如果`myArray`是项(例如字符串)的数组，就可以使用`MaxHeap`构造方法来创建堆：
```java
    public MaxHeap(T[] entries) {
        // Call other constructor
        this(entries.length);
        assert initialized = true;

        // Copy given array to data field
        for (int index = 0; index < entries.length; index++) {
            heap[index + 1] = entries[index];
        }

        // Create heap；数组下标从0开始
        for (int index = lastIndex / 2; index > 0; index--) {
            reheap(index);
        }
    }
```

`MaxHeapInterface<String> myHeap = new MaxHeap<>(myArray);`

当从`myHeap`中删除项时，可以将它按反序放回到`myArray`中。这个方法的问题是需要额外的内存，因为堆在所给数组之外还使用了一个数组。但是，模仿**堆的基于数组的实现**，可以不使用类`MaxHeap`来提高这个方法的效率。得到的算法称为堆排序(`heap sort`)。

**要从给定的数组创建初始堆．可以重复调用`reheap`**。图a和b分别显示了一个数组及执行当前创建初始堆步骤后得到的一个堆。因为数组要从下标0开始保存数据，但在构造方法中堆从下标1开始，所以必须调整`reheap`。

<div align=center><img src=Pictures\最大堆.jpg></div>

图b所示的数组中的最大项现在位于数组的第一个位置，所以**将它与数组的最后一项相交换**，如图c所示数组现在分为**树部分**和**有序部分**。交换后，**在树部分调用reheap(将其转换为堆）并执行另一次交换**，如图d和e所示。重复这些操作直到树部分只含有一个项为止（见图k)。数组现在按升序排序。注意实际上数组在图g中已经是有序的，但该算法并没发现这个事实。

heap的数组部分表示从下标0到下标lastlndex的堆。半堆的根在下标rootIndex处。因为堆从下标0而不是1开始，所以下标$i$处结点的左孩子在下标$2i+1$而不是$2i$处。

数组下标$i$的节点：
- 其父节点在下标$(i-1)/2$处，除非该节点是根($i$是0)；
- 其孩子节点在下标$2i+1$和$2i+2$处。

```java
package sort;

/**
 * @author Chenzf
 * @date 2020/7/25
 * @version 1.0
 */

public class HeapSort {
    /**
     * @param n 数组有n项，从下标0开始
     */
    public static <T extends Comparable<? super T>> void heapSort(T[] array, int n) {
        // Create first heap；数组下标从0开始；利用reheap调整各项，变成堆
        for (int index = (n / 2) - 1; index >= 0; index--) {
            reheap(array, index, n - 1);
        }

        swap(array, 0, n - 1);

        for (int lastIndex = n - 2; lastIndex > 0; lastIndex--) {
            // 调用reheap后，根节点为最大值
            reheap(array, 0, lastIndex);
            swap(array, 0, lastIndex);
        }
    }

    /**
     * 将最后一位数移至根节点处，再与其左右节点对象比较，大的上浮
     * 只要小于其孩子结点，就将它与其较大的孩子相交换
     * @param orphanIndex 以orphanIndex为根节点的半堆
     */
    private static <T extends Comparable<? super T>> void reheap(T[] heap, int orphanIndex, int lastIndex) {
        boolean done = false;
        T orphan = heap[orphanIndex];
        int leftChildIndex = 2 * orphanIndex + 1;

        // leftChildIndex <= lastIndex是因为定位到的最后一个节点可能没有叶子节点
        while (! done && (leftChildIndex <= lastIndex)) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;

            if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            }

            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[orphanIndex] = heap[largerChildIndex];
                orphanIndex = largerChildIndex;
                leftChildIndex = 2 * orphanIndex + 1;
            } else {
                done = true;
            }
        }

        heap[orphanIndex] = orphan;
    }

    private static void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
```

```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chenzf
 * @date 2020/7/25
 */

public class TestHeapSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排序的各项：");
            String[] strings = reader.readLine().split(" ");
            Integer[] array = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                array[i] = Integer.parseInt(strings[i]);
            }

            HeapSort.heapSort(array, array.length);

            System.out.println("排序后：");
            for (Integer integer : array) {
                System.out.print(integer + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## 算法效率

**与归并排序和快速排序一样，堆排序是$O(nlogn)$的算法**。在这里给出的实现中，**堆排序不需要第二个数组**，但归并排序需要。在大多数情况下快速排序是$O(nlogn)$的，但最坏情况是$O(n^2)$的。通常选择合适的枢轴可以避免快速排序的最坏情形．所以一般来讲，**快速排序是首选的排序方法**。


# 计数排序

前面所讨论的所有排序算法都是可以用在任何键值类型(例如，整数、字符串以及任何可比较的对象)上的**通用排序算法**。这些算法都是通过**比较它们的键值**来对元素排序的。已经证明，**基于比较的排序算法的复杂度不会好于$O(nlogn)$**。


计数排序不是基于元素比较，而是利用**数组下标**来确定元素的正确位置。


假设数组中有20个随机整数，取值范围为$0～10$，要求用最快的速度把这20个整数从小到大进行排序。

考虑到这些整数只能够在$0、1、2、3、4、5、6、7、8、9、10$这11个数中取值，取值范围有限。所以，可以根据这有限的范围，**建立一个长度为11的数组。数组下标从0到10，元素初始值全为0**。

假设20个随机整数的值如下所示：
`9，3，5，4，9，1，2，7，8，1，3，6，5，3，4，0，10，9，7，9`

下面就开始**遍历**这个无序的随机数列，**每一个整数按照其值对号入座，同时，对应数组下标的元素进行加1操作**：

<div align=center><img src=Pictures\计数排序.png width=80%></div>

该数组中每一个**下标位置的值**代表数列中**对应整数出现的次数**。

有了这个统计结果，排序就很简单了。**直接遍历数组，输出数组元素的下标值，元素的值是几，就输出几次**。

<div align=center><img src=Pictures\计数排序.gif></div>


## 统计数组的长度

例如数列：`90，99，95，94，95`是一个学生成绩表，这个数列的最大值是99，但最小的整数是90。如果创建长度为100的数组，那么前面从0到89的空间位置就都浪费了！

**以`数列最大值-最小值+1`作为统计数组的长度**。同时，**数列的最小值作为一个偏移量**，用于计算整数在统计数组中的下标。

以刚才的数列为例，统计出**数组的长度为$99-90+1=10$**，偏移量等于数列的最小值90（**根据最小值，算出其余数相对于最小值的位置**）。

对于整数95，对应的统计**数组下标是**$95-90=5$，如图所示：

<div align=center><img src=Pictures\计数排序1.png width=80%></div>


## 遇到相同的数—稳定排序

给出一个学生成绩表，要求按成绩从低到高进行排序，**如果成绩相同，则遵循原表固有顺序**。

当我们填充统计数组以后，只知道有两个成绩并列为95分的同学，却不知道哪一个是小红，哪一个是小绿。

**从统计数组的第2个元素开始，每一个元素都加上前面所有元素之和**：

<div align=center><img src=Pictures\计数排序2.png width=80%></div>

这样相加的目的，是**让统计数组存储的元素值，等于相应整数的最终排序位置的序号**。例如下标是9的元素值为5，代表**原始数列的整数99，最终的排序在第5位**。

接下来，创建输出数组`sortedArray`，长度和输入数列一致。然后**从后向前遍历输入数列**：

第1步，遍历成绩表(原始数据/输入数据：`90，99，95，94，95`)最后一行的小绿同学的成绩(95)：

小绿的成绩是95分，找到`countArray`下标是5的元素，值是4，代表小绿的成绩**排名位置在第4位**。同时，给`countArray`下标是5的元素值减1，从4变成3，代表下次再遇到95分的成绩时，最终排名是第3。

<div align=center><img src=Pictures\计数排序3.png></div>

第2步，遍历成绩表倒数第2个的小白同学的成绩(94)：

小白的成绩是94分，找到countArray下标是4的元素，值是2，代表小白的成绩排名位置在第2位。同时，给countArray下标是4的元素值减1，从2变成1，代表下次再遇到94分的成绩时（实际上已经遇不到了），最终排名是第1。

<div align=center><img src=Pictures\计数排序4.png></div>

第3步，遍历成绩表倒数第3行的小红同学的成绩(95)：

小红的成绩是95分，找到countArray下标是5的元素，值是3（最初是4，减1变成了3），代表小红的成绩排名位置在第3位。同时，给countArray下标是5的元素值减1，从3变成2，代表下次再遇到95分的成绩时（实际上已经遇不到了），最终排名是第2。

<div align=center><img src=Pictures\计数排序5.png></div>

这样一来，同样是95分的小红和小绿就能够清楚地排出顺序了，也正因为此，优化版本的计数排序属于**稳定排序**。

## 代码实现

`CountSort.java`
```java
package sort;

/**
 * 仅针对输入数组为整数
 * @author Chenzf
 * @date 2020/7/5
 * @version 1.0
 */

public class CountSort {
    public static int[] countSort(int[] arr) {
        // 1. 得到最大值和最小值，并计算出差值；注意浮点数的情况，这里先简化成整数
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }

            if (arr[i] < min) {
                min = arr[i];
            }
        }

        int d = max - min;

        // 2. 创建统计数组并统计对应的元素
        int[] countArray = new int[d + 1];
        for (int i = 0; i < arr.length; i++) {
            countArray[arr[i] - min] ++;
        }

        // 3. 统计数组做变形，后面的元素等于前面的元素和
        int sum = 0;
        for (int i = 0; i < countArray.length; i++) {
            sum += countArray[i];
            countArray[i] = sum;
        }

        // 4. 倒序遍历原始数列arr，从统计数列countArray找到正确的位置，输出到结果数组
        int[] sortedArray = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            // 根据countArray[arr[i] - min]找到在sortedArray的位置（不是index）
            sortedArray[countArray[arr[i] - min] - 1] = arr[i];
            countArray[arr[i] - min] --;
        }

        return sortedArray;
    }
}
```

`TestCountSort.java`

```java
package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Chenzf
 * @date 2020/7/4
 */

public class TestCountSort {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入待排元素：");
            String[] strings = reader.readLine().split(" ");
            int[] arr = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                arr[i] = Integer.parseInt(strings[i]);
            }

            int[] sortedArray = CountSort.countSort(arr);

            System.out.println("排序后：");
            for (int num : sortedArray) {
                System.out.print(num + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
请输入待排元素：
95 94 91 98 99 90 99 93 91 92
排序后：
90 91 91 92 93 94 95 98 99 99
 */
```

## 算法效率

代码第1，2，4步：`for (int i = 1; i < arr.length; i++); for (int i = 0; i < arr.length; i++); for (int i = arr.length - 1; i >= 0; i--)`涉及到遍历原始数列，运算量为$N$；第3步：`for (int i = 0; i < countArray.length; i++)`遍历统计数列，运算量为$M$。所以，总体运算量为$O(3N+M)$，时间复杂度为$O(N+M)$。

空间复杂度，如果不考虑结果数组，只考虑统计数组大小的话，空间复杂度是$O(M)$。

## 计数排序局限性

1. 当数列最大和最小值差距过大时，并不适合用计数排序。
2. 当数列元素不是整数时，也不适合用计数排序。


