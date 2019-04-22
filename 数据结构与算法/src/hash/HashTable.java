package hash;

import java.util.TreeMap;

/**
 * 哈希表
 *
 * @author zhangy
 */
public class HashTable<K, V> {

    /**
     * 容量数组
     */
    private final int[] capacity
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317,
            196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843,
            50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    /**
     * 扩容的标志的倍数
     */
    private static final int upperTol = 10;

    /**
     * 缩容的标志的倍数
     */
    private static final int lowerTol = 2;

    /**
     * 容量数组的索引
     */
    private int capacityIndex = 0;

    /**
     * map列表
     */
    private TreeMap<K, V>[] hashTable;

    /**
     * 大小
     */
    private int size;

    /**
     * 容量大小
     */
    private int M;

    /**
     * 构造函数
     *
     * @param M 容量大小
     */
    public HashTable(int M) {
        this.M = capacity[capacityIndex];
        size = 0;
        hashTable = new TreeMap[M];
        for (int i = 0; i < M; i++) {
            hashTable[i] = new TreeMap<>();
        }
    }

    /**
     * 哈希函数
     * & 0x7fffffff 是去掉符号位，取整数
     * int是四个字节，首位是符号位，& 0x7fffffff之后符号位固定为0（代表正数），后面保持不变
     *
     * @param key 键
     * @return 哈希值
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * 获取哈希表的大小
     *
     * @return 哈希表的大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 往哈希表中添加<K, V>
     *
     * @param key   需要添加的key
     * @param value 新的value
     */
    public void add(K key, V value) {
        TreeMap<K, V> map = hashTable[hash(key)];
        if (!map.containsKey(key)) {
            map.put(key, value);
            size++;

            // 当size大于原来容量M 的 10倍 ，扩容M的2倍
            if (size >= upperTol * M && capacityIndex + 1 < capacity.length) {
                capacityIndex++;
                resize(capacity[capacityIndex]);
            }

        }
    }

    /**
     * 哈希表中删除键为key的元素
     *
     * @param key 键
     * @return 要删除的元素的value
     */
    public V remove(K key) {
        V ret = null;
        TreeMap<K, V> map = hashTable[hash(key)];
        if (map.containsKey(key)) {
            ret = map.remove(key);
            size--;

            // 当size小于原来容量M 的 2倍 ，缩容M的1/2
            if (size < lowerTol * M && capacityIndex - 1 >= 0) {
                capacityIndex--;
                resize(capacity[capacityIndex]);
            }
        }

        return ret;
    }

    /**
     * 给键为key的元素 赋值
     *
     * @param key   键
     * @param value 值
     */
    public void set(K key, V value) {
        TreeMap<K, V> map = hashTable[hash(key)];
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException(key + "不存在");
        }

        map.put(key, value);
    }

    /**
     * 哈希表中是否包含键为key的元素
     *
     * @param key 键
     * @return true 包含
     */
    public boolean contains(K key) {
        return hashTable[hash(key)].containsKey(key);
    }

    /**
     * 获取键为key的元素的value值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        return hashTable[hash(key)].get(key);
    }

    /**
     * 扩容或缩容
     *
     * @param newM 新的容量大小
     */
    private void resize(int newM) {

        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newM; i++) {
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;
        //重新计算哈希值
        for (int i = 0; i < oldM; i++) {
            TreeMap<K, V> map = hashTable[i];
            for (K key : map.keySet()) {
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }

        this.hashTable = newHashTable;
    }
}
