package map;

/**
 * @author zhangy
 * <p> 泛型
 * 映射的接口
 */
public interface Map<K, V> {

    /**
     * 添加元素
     *
     * @param key 键
     * @param value 值
     */
    void add(K key, V value);

    /**
     * 删除键为key的元素
     *
     * @param key 键
     * @return 键为key的value
     */
    V remove(K key);

    /**
     * 是否包含键为key的元素
     *
     * @param key 键
     * @return true 包含
     */
    boolean contains(K key);

    /**
     * 获取键为key的value
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 修改键为key的值为value
     *
     * @param key 键
     * @param value 值
     */
    void set(K key, V value);

    /**
     * 获取大小
     *
     * @return 大小
     */
    int getSize();

    /**
     * 是否为空
     *
     * @return true 空
     */
    boolean isEmpty();
}
