# Symbol Tables

# Binary Search Trees

使用每个结点含有两个链接的二叉查找树来高效地实现符号表，将**链表插入**的灵活性与**有序数组查找**的高效性结合起来。

一棵二叉查找树(BST)是一棵二叉树，其中每个结点都含有一个Comparable的键(以及相关联的值)且每个结点的键都**大于其左子树中的任意结点的键**，而**小于右子树的任意结点的键**。

画出二叉查找树时，会将键写在结点上，用键来指代结点：
<div align=center><img src=Pictures\BST结构.jpg></div>

## 数据表示

嵌套定义了一个私有类来表示二叉查找树上的一个结点，每个结点都含有一个键、一个值、一条左链接、一条右链接和一个结点计数器：

```java
private class Node {
    private Key key;                     // 键
    private Value value;                 // 值
    private Node left, right;            // 指向子树的链接
    private int number;                  // 以该结点为根的子树中的结点总数

    public Node(Key key, Value value, int number) {
        this.key = key;
        this.value = value;
        this.number = number;
    }
}
```

一棵二叉查找树代表了一组键及其相应的值的集合，而同一个集合可以用多棵不同的二叉查找树来表示，将一棵二叉查找树的所有键投影到一条直线上，保证一个结点的左子树中的键出现在它的左边，右子树中的键出现在它的右边：

<div align=center><img src=Pictures\两棵BST代表同一个集合.jpg></div>

## 查找

<div align=center><img src=Pictures\查找.jpg></div>

```java
public Value get(Key key) {
    return get(root, key);
}

private Value get(Node node, Key key) {

    // 在以node为根结点的子树中查找，并返回key所对应的值
    if (node == null) {
        return null;
    }

    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
        return get(node.left, key);
    } else if (cmp > 0) {
        return get(node.right, key);
    } else {
        return node.value;
    }
}
```

## 插入
<div align=center><img src=Pictures\插入.jpg></div>

```java
    // 插入
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        node.number = size(node.left) + size(node.right) + 1;
        return node;

    }
```