package tree.segment;

/**
 * 融合器
 *
 * @author zhangy
 */
public interface Merger<E> {

    /**
     * 合并
     *
     * @param a 元素a
     * @param b 元素b
     * @return
     */
    E merge(E a, E b);
}
