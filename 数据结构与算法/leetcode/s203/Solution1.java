package leetcode.s203;

class Solution1 {

    public ListNode removeElements(ListNode head, int val) {

        //虚拟头节点  指向head
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;

        ListNode prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.val == val) {
                prev.next = prev.next.next;
            } else {
                prev = prev.next;
            }
        }

        return dummyHead.next;

    }
}