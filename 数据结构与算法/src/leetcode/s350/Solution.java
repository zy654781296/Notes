package leetcode.s350;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Solution {

    //给定两个数组，编写一个函数来计算它们的交集。
    //输入: nums1 = [1,2,2,1], nums2 = [2,2]
    //输出: [2,2]
    //输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致


    public int[] intersect(int[] nums1, int[] nums2) {

        if(nums1 == null || nums2 == null) {
            return null;
        }

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i : nums1) {
            if(!map.containsKey(i)) {
                map.put(i, 1);
            } else {
                map.put(i, map.get(i) + 1);
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int j : nums2) {
            if(map.containsKey(j)) {
                list.add(j);
                map.put(j, map.get(j) - 1);
                if(map.get(j) == 0) {
                    map.remove(j);
                }
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }

        return res;
    }

    public int[] intersect1(int[] nums1, int[] nums2) {

        if(nums1 == null || nums2 == null) {
            return null;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            if(!map.containsKey(i)) {
                map.put(i, 1);
            } else {
                map.put(i, map.get(i) + 1);
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int j : nums2) {
            if(map.containsKey(j)) {
                list.add(j);
                map.put(j, map.get(j) - 1);
                if(map.get(j) == 0) {
                    map.remove(j);
                }
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }

        return res;
    }

}
