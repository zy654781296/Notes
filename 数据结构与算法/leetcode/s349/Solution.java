package leetcode.s349;

import java.util.ArrayList;
import java.util.TreeSet;

public class Solution {

    //给定两个数组，编写一个函数来计算它们的交集。
    //输入: nums1 = [1,2,2,1], nums2 = [2,2]
    //输出: [2] 输出结果中的每个元素一定是唯一的。

    public int[] intersection(int[] nums1, int[] nums2) {

        if(nums1 == null || nums2 == null) {
            return null;
        }

        TreeSet<Integer> set = new TreeSet<>();
        for (int i : nums1) {
            set.add(i);
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int j : nums2) {
            if(set.contains(j)) {
                list.add(j);
                set.remove(j);
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }

        return res;
    }
}
