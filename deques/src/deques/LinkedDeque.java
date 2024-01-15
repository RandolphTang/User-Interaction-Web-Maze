package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        this.front = new Node<>(null);
        this.back = new Node<>(null);

        front.next = back;
        back.prev = front;
        size = 0;

    }

    public void addFirst(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);

        newNode.next = front.next;
        newNode.prev = front;

        front.next.prev = newNode;
        front.next = newNode;


    }

    public void addLast(T item) {
        size += 1;

        Node<T> newNode = new Node<T>(item);

        newNode.prev = back.prev;
        newNode.next = back;

        back.prev.next = newNode;
        back.prev = newNode;

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;

        Node<T> removed = front.next;
        front.next = removed.next;
        removed.next.prev = front;

        return removed.value;

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;

        Node<T> removed = back.prev;
        back.prev = removed.prev;
        removed.prev.next = back;

        return removed.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }

        Node<T> curr = front.next;

        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr.value;

    }

    public int size() {
        return size;
    }
}
