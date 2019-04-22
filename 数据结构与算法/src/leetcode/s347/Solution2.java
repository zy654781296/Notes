package leetcode.s347;


import java.util.*;

/**
 * 前K个高频元素
 */
public class Solution2 {

    //输入: nums = [1,1,1,2,2,3], k = 2
    //输出: [1,2]

    public List<Integer> topKFrequent(int[] nums, int k) {

        //将nums放入map中，并统计频次
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
        for (int key : map.keySet()) {
            if (queue.size() < k) {
                queue.add(key);
            } else if (map.get(key) > map.get(queue.peek())) {
                queue.poll();
                queue.add(key);
            }
        }

        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()) {
            list.add(queue.poll());
        }

        return list;
    }
}
