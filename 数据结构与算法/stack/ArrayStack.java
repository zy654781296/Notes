package stack;

import array.Array;

/**
 * @author zhangy
 * 基于数组的栈
 */
public class ArrayStack<E> implements Stack<E> {

    /*--------------------------------------
    栈：是一种线性结构，特点是 后进先出 Last In First Out (LIFO)
    相比数组，栈对应的操作是数组的子集

    栈的应用：
    编辑器中的Undo(撤销)操作；程序调用的系统栈；括号匹配

    栈的时间复杂度

    push(e)           O(1) 均摊
    pop()             O(1) 均摊
    peek()            O(1)
    getSize()         O(1)
    isEmpty()         O(1)

    增删查改：O(1)

    ----------------------------------------*/

    /**
     * 声明一个数组
     */
    Array<E> array;

    /**
     * 无参构造函数
     */
    public ArrayStack() {
        array = new Array<>();
    }

    /**
     * 构造函数
     *
     * @param capacity 容量大小
     */
    public ArrayStack(int capacity) {
        array = new Array<>(capacity);
    }


    /**
     * 入栈
     *
     * @param e 元素
     */
    @Override
    public void push(E e) {
        array.addLast(e);
    }

    /**
     * 出栈
     *
     * @return 栈顶元素
     */
    @Override
    public E pop() {
        return array.removeLast();
    }

    /**
     * 取栈顶元素
     *
     * @return 栈顶元素
     */
    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    /**
     * 栈的容量
     *
     * @return
     */
    public int getCapacity() {
        return array.getCapacity();
    }

    /**
     * 是否为空
     *
     * @return true 空
     */
    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack：[");
        for (int i = 0; i < array.getSize(); i++) {
            sb.append(array.get(i));
            if (i != array.getSize() - 1) {
                sb.append(", ");
            }
        }
        sb.append("] top");
        return sb.toString();
    }
}
