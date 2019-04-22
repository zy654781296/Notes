package leetcode.s347;


import java.util.*;

/**
 * 前K个高频元素
 */
public class Solution1 {

    //输入: nums = [1,1,1,2,2,3], k = 2
    //输出: [1,2]

    /**
     * 频次
     */
    private class Freq {
        /**
         * 元素
         */
        int e;

        /**
         * 频次
         */
        int freq;

        public Freq(int e, int freq) {
            this.e = e;
            this.freq = freq;
        }
    }

    /**
     * 自定义的比较器
     */
    private class FreqComparator implements Comparator<Freq> {

        @Override
        public int compare(Freq a, Freq b) {
            return a.freq - b.freq;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {

        //将nums放入map中，并统计频次
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Freq> queue = new PriorityQueue<>(new FreqComparator());
        for (int key : map.keySet()) {
            if (queue.size() < k) {
                queue.add(new Freq(key, map.get(key)));
            } else if (map.get(key) > queue.peek().freq) {
                queue.poll();
                queue.add(new Freq(key, map.get(key)));
            }
        }

        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()) {
            list.add(queue.poll().e);
        }

        return list;
    }
}
