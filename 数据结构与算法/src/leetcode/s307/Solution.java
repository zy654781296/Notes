package leetcode.s307;

import tree.segment.SegmentTree;

/**
 * 区域和检索 - 数组不可变
 *
 * @author zhangy
 */
public class Solution {

    // 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
    // update(i, val) 函数可以通过将下标为 i 的数值更新为 val，从而对数列进行修改。
    //[1, 3, 5]
    // sumRange(0, 2) -> 9
    // update(1, 2)
    // sumRange(0, 2) -> 8

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

        public void update(int i, int val) {
            if (tree == null)
                throw new IllegalArgumentException("Error");
            tree.set(i, val);
        }

        public int sumRange(int i, int j) {
            if (tree == null) {
                throw new IllegalArgumentException("null");
            }
            return tree.query(i, j);
        }
    }
}
