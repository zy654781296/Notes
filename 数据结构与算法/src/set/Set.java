package set;

/**
 * @param <E>
 * @author zhangy
 * 集合的接口
 */
public interface Set<E> {

    /**
     * 添加操作
     *
     * @param e 要添加的元素
     */
    void add(E e);

    /**
     * 获取集合大小
     *
     * @return 集合大小
     */
    int getSize();

    /**
     * 是否为空
     *
     * @return true 空
     */
    boolean isEmpty();

    /**
     * 是否包含e
     *
     * @param e 元素e
     * @return true 包含
     */
    boolean contains(E e);

    /**
     * 删除元素e
     *
     * @param e 元素e
     */
    void remove(E e);
}
