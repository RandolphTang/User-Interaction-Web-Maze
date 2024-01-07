package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {

        if (list.front != null && list.front.next != null && list.front.next.next != null) {
            ListNode first = list.front;
            ListNode second = list.front.next;
            ListNode third = list.front.next.next;

            third.next = second;
            second.next = first;
            first.next = null;

            list.front = third;
        }
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode first = list.front;
            ListNode second = list.front.next;

            // Move the first node to the back
            list.front = second;
            while (second.next != null) {
                second = second.next;
            }
            second.next = first;
            first.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {

        LinkedIntList result = new LinkedIntList();

        if (a.front != null) {
            result.front = new ListNode(a.front.data);
            ListNode current1 = a.front;
            ListNode result1 = result.front;

            while (current1.next != null) {
                result1.next = new ListNode(current1.next.data);
                current1 = current1.next;
                result1 = result1.next;
            }
        }

        if (b.front != null) {
            ListNode result2;

            if (result.front != null) {
                result2 = result.front;
                while (result2.next != null) {
                    result2 = result2.next;
                }
                result2.next = new ListNode(b.front.data);
                result2 = result2.next;

            } else {
                result.front = new ListNode(b.front.data);
                result2 = result.front;
            }

            ListNode current2 = b.front;


            while (current2.next != null) {
                result2.next = new ListNode(current2.next.data);
                current2 = current2.next;
                result2 = result2.next;
            }

        }

        return result;
    }


}
