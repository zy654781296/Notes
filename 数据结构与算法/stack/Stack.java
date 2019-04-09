package stack;

/**
 * @author zhangy
 * 接口
 */
public interface Stack<E> {

    /**
     * 入栈
     *
     * @param e
     */
    void push(E e);

    /**
     * 出栈
     *
     * @return
     */
    E pop();

    /**
     * 取栈顶的元素
     *
     * @return
     */
    E peek();

    /**
     * 栈的大小
     *
     * @return
     */
    int getSize();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();
}
