# 排序与查找

## 选择排序
从数组中选择最小元素，将它与数组的第一个元素交换位置。再从数组剩下的元素中选择出最小的元素，将它与数组的第二个元素交换位置。不断进行这样的操作，直到将整个数组排序。

<div align=center><img src=Algorithms/SelectionSort.png width=30%></div>

`SelectionSort.java`
```java
package Sort;

/**
 * A class of static, iterative methods for sorting an array of
 * Comparable objects from smallest to largest.
 *
 * @author Chenzf
 * @version 1.0
 */

public class SelectionSort
{

    /**
     * Sorts the first n objects in an array into ascending order.
     * a[0] <= a[1] <= . . . <= a[index] <= all other a[i]
     * @param a An array of Comparable objects.
     * @param n The length of array.
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void selectionSort(T[] a, int n)
    {
        for (int index = 0; index < n - 1; index++)  // 只需要找n-1趟
        {
            int indexOfNextSmallest = getIndexOfSmallest(a, index, n - 1);  // 最后一个数index = n-1
            swap(a, index, indexOfNextSmallest);
        }
    }

    private static <T extends Comparable<? super T>> int getIndexOfSmallest(T[] a, int first, int last)
    {
        T min = a[first];
        int indexOfMin = first;

        for (int index = first + 1; index <= last; index++)
        {
            if (a[index].compareTo(min) < 0)
            {
                min = a[index];
                indexOfMin = index;
            }
        }

        return indexOfMin;
    }

    private static void swap(Object[] a, int i, int j)
    {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
```

`TestSelectionSort.java`
```java
package Sort;

public class TestSelectionSort
{
    public static void main(String[] args)
    {
        // int[] arr = {15, 8, 10, 2, 5};
        Integer[] arr = {15, 8, 10, 2, 5};
        System.out.println("排序前：");
        for (Integer num : arr)
            System.out.print(num + " ");

        System.out.println("\n排序后：");
        SelectionSort.selectionSort(arr, arr.length);
        for (Integer num : arr)
            System.out.print(num + " ");
    }
}
/*
排序前：
15 8 10 2 5 
排序后：
2 5 8 10 15 
 */
```

### 算法效率
时间复杂度为$O(n^2)$


## 冒泡排序
将数组中相邻的两个数进行比较，较小的数值向下沉，数值比较大的向上浮。

<div align=left><img src=Algorithms/BubbleSort.png width=80%></div>

`BubbleSort.java`
```java
package Sort;

public class BubbleSort
{
    private static <T extends Comparable<? super T>> void bubbleSort(T[] a)
    {
        boolean needNextPass = true;

        for (int k = 1; k < a.length; k++)  // 第k次排序；a.length个数需a.length-1次排序
        {
            needNextPass = false;
            for (int i = 0; i < a.length - k; i++)
            {
                if (a[i].compareTo(a[i + 1]) > 0)
                {
                    swap(a, i, i + 1);
                    needNextPass = true;
                }
            }
        }
    }

    private static void swap(Object[] a, int i, int j)
    {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args)
    {
        Integer[] arr = {2, 9, 5, 4, 8, 1};
        System.out.println("排序前：");
        for (Integer num : arr)
            System.out.print(num + " ");

        bubbleSort(arr);
        System.out.println("\n排序后：");
        for (Integer num : arr)
            System.out.print(num + " ");

    }
}
```

### 算法效率
时间复杂度为$O(n^2)$。


## 插入排序
对数组的插入排序将数组分隔(partition) (即划分)为两部分。第一部分是有序的，初始时仅含有数组中的第一项。第二部分含有其余的项。<font color=red>算法从未排序部分移走第一项，并将它插入有序部分中合适的有序位置</font>。从有序部分的末尾开始，朝着开头方向，通过将待排序项与各有序项进行比较来选择合适的位置。当比较时，将有序部分的数组项右移，为插入腾出空间。

<div align=center><img src=Algorithms/InsertionSort.png width=50%></div>

`InsertionSort.java`
```java
package Sort;

public class InsertionSort
{
    private static <T extends Comparable<? super T>> void insertionSort(T[] a)
    {
        for (int i = 1; i < a.length; i++)
        {
            T insertedValue = a[i];
            int j;
            for (j = i - 1; j >= 0 && insertedValue.compareTo(a[j]) < 0; j--)
                a[j + 1] = a[j];

            a[j + 1] = insertedValue;
        }
    }

    public static void main(String[] args)
    {
        Integer[] arr = {2, 5, 8, 3, 9, 4, 1};
        System.out.println("排序前：");
        for (Integer num : arr)
            System.out.print(num + " ");

        System.out.println("\n排序后：");
        insertionSort(arr);
        for (Integer num : arr)
            System.out.print(num + " ");
    }
}
```

### 算法效率
时间复杂度为$O(n^2)$


## 希尔排序
希尔排序是插入排序的变体。在插入排序过程中，数组项只移动到相邻位置。当项与正确的有序位置相距甚远时，它必须进行很多次这样的移动。所以<font color=red>当数组完全无序时，插入排序要花很多时间。但当数组基本有序时，插入排序有很好的效率</font>。

下图显示了一个数组及<font color=red>每隔5项</font>组成的子数组。第一个子数组含有整数10、9和7；第二个子数组含有16和6；等等。
<div align=center><img src=Algorithms/ShellSort1.png width=90%></div>

现在<font color=red>使用插入排序分别对这6个子数组进行排序</font>，排序后数组比原始状态“更有序”了：
<div align=center><img src=Algorithms/ShellSort2.png width=90%></div>

现在形成新的子数组，这次减小下标之间的间隔。<font color=red>`Shell`建议子数组下标间隔是$n/2$，且每趟排序中这个值减半直到为1</font>。示例数组有13项，所以从间隔为6开始。现在将间隔减小到3：
<div align=center><img src=Algorithms/ShellSort3.png width=90%></div>

对得到的三个子数组进行插入排序：
<div align=center><img src=Algorithms/ShellSort4.png width=90%></div>

将当前间隔3除以2得到1。所以最后一步只是对整个数组进行普通的插入排序。

`ShellSort.java`
```java
package Sort;

public class ShellSort
{
    private static <T extends Comparable<? super T>> void shellSort(T[] a, int first, int last)
    {
        int n = last - first + 1;  // number of array entries
        int space = n / 2;

        while (space > 0)
        {
            for (int begin = first; begin <= first + space - 1; begin ++)  // 分了几个子数组
                incrementalInsertionSort(a, begin, last, space);
            space /= 2;
        }
    }

    private static <T extends Comparable<? super T>> void incrementalInsertionSort(T[] a, int first, int last, int space)
    {
        // 对每个子数组进行插入排序
        for (int unsorted = first + space; unsorted <= last; unsorted += space)
        {
            T nextToInsert = a[unsorted];
            int index = unsorted - space;
            while ((index >= first) && (nextToInsert.compareTo(a[index]) < 0))
            {
                a[index + space] = a[index];
                index -= space;
            }
            a[index + space] = nextToInsert;
        }
    }

    public static void main(String[] args)
    {
        Integer[] arr = {10, 16, 11, 4, 15, 3, 9, 6, 1, 17, 8, 12, 7};
        System.out.println("排序前：");
        for (Integer num : arr)
            System.out.print(num + " ");

        System.out.println("\n排序后：");
        shellSort(arr, 0, arr.length - 1);
        for (Integer num : arr)
            System.out.print(num + " ");
    }
}
```

### 希尔排序效率
<div align=center><img src=Algorithms/ShellSort5.png width=60%></div>


## 归并排序
归并排序算法将数组分为两半，对每部分递归地应用归并排序。在两部分都排好序后，对它们进行归并。该算法采用经典的`分治（divide-and-conquer）策略`（分治法将问题分(divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。

持续将数组划分为子数组，直到每个子数组只包含一个元素。然后，该算法将这些小的子数组归并为稍大的有序子数组，直到最后形成一个有序的数组。
<div align=center><img src=Algorithms/MergeSort.png width=70%></div>

将两个有序数组归并为一个有序数组：

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

<div align=center><img src=Algorithms/MergeSort1.png width=90%></div>

箭头上的数字表示递归调用及合并的次序：
<div align=center><img src=Algorithms/MergeSort2.png width=90%></div>


`MergeSort.java`
```java
package Sort;

public class MergeSort
{
    private static <T extends Comparable<? super T>> void mergeSort(T[] list)
    {
        if (list.length > 1)
        {
            // divide the first half
            // T[] firstHalf = (T[]) new Object[list.length / 2];
            @SuppressWarnings("unchecked")
            T[] firstHalf = (T[]) new Comparable<?>[list.length / 2];
            System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
            mergeSort(firstHalf);

            // divide the second half
            int secondHalfLength = list.length - list.length / 2;
            @SuppressWarnings("unchecked")
            T[] secondHalf = (T[]) new Comparable<?>[secondHalfLength];
            System.arraycopy(list, list.length / 2, secondHalf, 0, secondHalfLength);
            mergeSort(secondHalf);

            // Merge firstHalf with secondHalf into a list
            merge(firstHalf, secondHalf, list);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] list1, T[] list2, T[] temp)
    {
        int current1 = 0, current2 = 0, current3 = 0;  // Current index in list1,2,3

        while (current1 < list1.length && current2 < list2.length)
        {
            if (list1[current1].compareTo(list2[current2]) < 0)
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
        }

        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
    }

    public static void main(String[] args)
    {
        Integer[] list = {7, 5, 9, 3, 6, 0, 2, 4};
        System.out.println("排序前：");
        for (Integer num : list)
            System.out.print(num + " ");

        mergeSort(list);
        System.out.println("\n排序后：");
        for (Integer num : list)
            System.out.print(num + " ");
    }
}
```

### 算法效率
归并排序的时间复杂度为$O\left(n\log n\right)$，优于冒泡排序、选择排序和插入排序。`java.util.Arrays`类中的`sort`方法是使用归并排序算法的变体来实现的。


## 快速排序
在数组中选择一个称为`主元(pivot)`的元素，将数组分为两部分，使得第一部分中的所有元素都小于或等于主元，而笫二部分中的所有元素都大于主元。对第一部分递归地应用快速排序算法，然后对笫二部分递归地应用快速排序算法。

主元的选择会影响算法的性能。在理想情况下，应该选择能平均划分两部分的主元。为了简单起见，假定将数组的第一个元素选为主元。

一次快速排序：
<div align=center><img src=Algorithms/QuickSort.png width=70%></div>

对子数组进行快速排序：
<div align=center><img src=Algorithms/QuickSort1.png width=70%></div>

`QuickSort.java`
```java
package Sort;

public class QuickSort
{
    private static <T extends Comparable<? super T>> void quickSort(T[] list)
    {
        quickSort(list, 0, list.length - 1);
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] list, int first, int last)
    {
        if (last > first)
        {
            int pivotIndex = partition(list, first, last);
            quickSort(list, first, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    private static <T extends Comparable<? super T>> int partition(T[] list, int first, int last)
    {
        T pivot = list[first];
        int low = first + 1;
        int high = last;

        while (high > low)
        {
            while (low <= high && list[low].compareTo(pivot) <= 0)
                low++;

            while (low <= high && list[high].compareTo(pivot) > 0)
                high--;

            // 交换数据
            if (high > low)
            {
                T temp = list[high];
                list[high] = list[low];
                list[low] = temp;
            }
        }

        // high = low时
        while (high > first && list[high].compareTo(pivot) >= 0)
            high--;

        if (pivot.compareTo(list[high]) > 0)
        {
            list[first] = list[high];
            list[high] = pivot;
            return high;
        }
        else
            return first;
    }

    public static void main(String[] args)
    {
        Integer[] list = {5, 2, 9, 3, 8, 4, 0, 1, 6, 7};
        System.out.println("排序前：");
        for (Integer num : list)
            System.out.print(num + " ");

        quickSort(list);
        System.out.println("\n排序后：");
        for (Integer num : list)
            System.out.print(num + " ");
    }
}
```