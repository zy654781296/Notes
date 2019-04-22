package array;

/**
 * @author zhangy
 * 动态数组(自动扩容活缩容)
 */
public class Array<E> {

    /*-----------------------------

    数组是一种 线性结构

            时间复杂度
    addLast(e)       O(1)
    addFirst(e)      O(n)
    add(index, e)    O(n/solution)=O(n)
    resize           O(n)

    removeLast(e)    O(1)
    removeFirst(e)   O(n)
    remove(index, e) O(n/solution)=O(n)

    set(index, e)    O(1)

    get(index)       O(1)
    contains(e)      O(n)
    find(e)          O(n)

    capacity为n，n+1次addLast，触发resize。总共进行了2n+1次操作
    平均每次addLast操作进行了2次基本操作

    addLast和removeLast最坏情况下会resize，索引复杂度为 O(n)
    均摊复杂度：addLast和removeLast都为 O(1)

    复杂度震荡： addLast之后立马removeLast ，每次操作都会resize，O(n)
    解决方案：Lazy, 当removeLast的resize的时候，size为 原来1/4时，才将capacity变为1/solution

    增：O(1)
    删：O(n)
    查：已知索引O(1)；未知索引O(n)
    改：已知索引O(1)；未知索引O(n)
    ------------------------------*/


    //数组的大小
    private int size;
    //数据
    private E[] data;


    /**
     * 默认的构造函数，默认创建大小为10的数组
     */
    public Array() {
        this(10);
    }

    /**
     * 构造函数
     *
     * @param capacity 数组的大小
     */
    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * 构造函数
     *
     * @param arr 元素数组
     */
    public Array(E[] arr) {
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }
        size = arr.length;
    }

    /**
     * 获取数组容量
     *
     * @return
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 获取数组中元素的个数
     *
     * @return 数组中元素的个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 数组是否为空
     *
     * @return true 空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在数组的最后添加新的元素
     *
     * @param e 元素的值
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 在数组的最开始添加新的元素
     *
     * @param e 元素的值
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在索引为index的位置添加元素e
     *
     * @param index 索引
     * @param e     元素
     */
    public void add(int index, E e) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("索引越界");
        }

        if (size == data.length) {
            //扩容
            resize(data.length * 2);
        }

        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }

        data[index] = e;
        size++;
    }

    /**
     * 获取最后一个的元素
     *
     * @return
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 获取第一个的元素
     *
     * @return 元素
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获取索引为index位置的元素
     *
     * @param index 索引
     * @return 元素
     */
    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("索引越界");
        }

        return data[index];
    }

    /**
     * 修改index索引位置的元素为e
     *
     * @param index 索引
     * @param e     元素
     */
    public void set(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("索引越界");
        }

        data[index] = e;
    }

    /**
     * 是否包含元素e
     *
     * @param e 元素
     * @return true 包含
     */
    public boolean contains(E e) {

        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找数组中元素e所在的索引，如果不存在元素e，则返回-1
     *
     * @param e 元素
     * @return 索引
     */
    public int find(E e) {

        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 从数组中删除第一个元素, 返回删除的元素
     *
     * @return 删除的元素
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从数组中删除最后一个元素, 返回删除的元素
     *
     * @return 删除的元素
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 从数组中删除index位置的元素, 返回删除的元素
     *
     * @param index 索引
     * @return 删除的元素
     */
    public E remove(int index) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("索引越界");
        }

        E result = data[index];

        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }

        size--;
        data[size] = null;
        //当数组变为原来的1/4的时候，再去将数组的容量缩小为原来的1/solution
        if (size == data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return result;
    }

    /**
     * 从数组中删除元素e
     *
     * @param e 元素
     */
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * 将数组空间的容量变成newSize大小
     *
     * @param newSize 新数组的大小
     */
    private void resize(int newSize) {

        E[] newData = (E[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    /**
     * 所索引i位置的元素和索引j位置的元素交换
     *
     * @param i 索引
     * @param j 索引
     */
    public void swap(int i, int j) {

        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IllegalArgumentException("索引越界");
        }

        E t = data[i];
        data[i] = data[j];
        data[j] = t;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Array: size=%d, capacity=%d\n", size, data.length));
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
