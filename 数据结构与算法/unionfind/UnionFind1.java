package unionfind;

/**
 * 第一版Union-Find(使用数组实现)
 *
 * @author zhangy
 */
public class UnionFind1 implements UF {

    /**
     * 使用数组表示
     */
    private int[] id;

    /**
     * 构造函数
     *
     * @param size 大小
     */
    public UnionFind1(int size) {

        id = new int[size];

        // 初始化, 每一个id[i]指向自己, 没有合并的元素
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    /**
     * 获取大小
     *
     * @return 大小
     */
    @Override
    public int getSize() {
        return id.length;
    }

    /**
     * 查找元素p所对应的集合编号
     * O(1)复杂度
     *
     * @param p 索引
     * @return 编号值
     */
    private int find(int p) {

        if (p < 0 || p >= id.length)
            throw new IllegalArgumentException("p 不再范围内.");

        return id[p];
    }

    /**
     * 查看元素p和元素q是否所属一个集合
     * O(1)复杂度
     *
     * @param p 元素p
     * @param q 元素q
     * @return true 连接
     */
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 合并元素p和元素q所属的集合
     * O(n) 复杂度
     *
     * @param p 元素p
     * @param q 元素q
     */
    @Override
    public void unionElements(int p, int q) {

        // 找到p和q对应的编号
        int pID = find(p);
        int qID = find(q);

        if (pID == qID) {
            return;
        }

        // 合并过程需要遍历一遍所有元素, 将两个元素的所属集合编号合并
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                id[i] = qID;
            }
        }
    }
}
