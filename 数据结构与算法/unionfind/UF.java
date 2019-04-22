package unionfind;

/**
 * 并查集接口
 *
 * @author zhangy
 */
public interface UF {

    /**
     * 获取并查集的大小
     *
     * @return 大小
     */
    int getSize();

    /**
     * p和q是否相连
     *
     * @param p 元素p
     * @param q 元素q
     * @return true 连接
     */
    boolean isConnected(int p, int q);

    /**
     * 连接p和q
     *
     * @param p 元素p
     * @param q 元素q
     */
    void unionElements(int p, int q);
}
