package leetcode.s203;

class Solution {

    public ListNode removeElements(ListNode head, int val) {

        //当头节点为空的时候 ，删除
        while (head != null && head.val == val) {
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
        }

        //判断head是否为空
        if(head == null) {
            return null;
        }

        ListNode prev = head;
        while (prev.next != null) {
            if(prev.next.val == val) {
                ListNode delNode = prev.next;
                prev.next = delNode.next;
                delNode.next = null;
            } else {
                prev = prev.next;
            }
        }

        return head;
    }
}