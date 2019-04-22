package leetcode.s347;


import queue.PriorityQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * 前K个高频元素
 */
public class Solution {

    //输入: nums = [1,1,1,2,2,3], k = 2
    //输出: [1,2]

    /**
     * 频次
     */
    private class Freq implements Comparable<Freq> {
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

        @Override
        public int compareTo(Freq o) {
            if (this.freq < o.freq) {
                return 1;
            } else if (this.freq > o.freq) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {

        //将nums放入map中，并统计频次
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Freq> queue = new PriorityQueue<>();
        for (int key : map.keySet()) {
            if (queue.getSize() < k) {
                queue.enqueue(new Freq(key, map.get(key)));
            } else if (map.get(key) > queue.getFront().freq) {
                queue.dequeue();
                queue.enqueue(new Freq(key, map.get(key)));
            }
        }

        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()) {
            list.add(queue.dequeue().e);
        }

        return list;
    }
}
