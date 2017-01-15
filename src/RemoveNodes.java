/**
 * @author Evgeni Kumanov
 */
public class RemoveNodes {
    static class LinkedListNode {
        int val;
        LinkedListNode next;
    }

    static LinkedListNode removeNodes(LinkedListNode list, int x) {
        if (list == null) {
            return null;
        }

        LinkedListNode result = list;
        LinkedListNode current = list;
        LinkedListNode previous = null;

        do {
            if (current.val > x) {
                if (result == current) {
                    result = current.next;
                }
                if (previous != null) {
                    previous.next = current.next;
                }
            } else {
                previous = current;
            }
            current = current.next;
        } while (current != null);

        return result;
    }
}
