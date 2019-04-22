package leetcode.s303;

import tree.segment.SegmentTree;

/**
 * 区域和检索 - 数组不可变
 *
 * @author zhangy
 */
public class Solution {

    // 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
    // 给定 nums = [-2, 0, 3, -5, 2, -1]，求和函数为 sumRange()
    // sumRange(0, 2) -> 1
    // sumRange(2, 5) -> -1
    // sumRange(0, 5) -> -3
    // 你可以假设数组不可变;会多次调用 sumRange 方法。

    class NumArray {

        private SegmentTree<Integer> tree;

        public NumArray(int[] nums) {
            if (nums.length > 0) {
                Integer[] data = new Integer[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    data[i] = nums[i];
                }
                tree = new SegmentTree<>(data, (a, b) -> a + b);
            }
        }

        public int sumRange(int i, int j) {
            if(tree == null) {
                throw new IllegalArgumentException("null");
            }
            return tree.query(i, j);
        }
    }
}
