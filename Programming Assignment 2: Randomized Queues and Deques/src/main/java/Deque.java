import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Daniel on 10/07/15.
 */
public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node pre;
        public Node(Item item) {
            this.item = item;
        }
    }

    private Node front_ptr;
    private Node last_ptr;
    private int sz;

    /**
     * construct an empty deque
     */
    public Deque() {
        this.sz = 0;
        this.front_ptr = new Node(null);
        this.last_ptr = new Node(null);
    }

    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return this.sz == 0;
    }

    /**
     * return the number of items on the deque
     * @return
     */
    public int size() {
        return this.sz;
    }

    /**
     * add the item to the front
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node cur = new Node(item);
        if (this.isEmpty()) {
            this.front_ptr.next = cur;
            this.last_ptr.next = cur;
        }
        else {
            this.front_ptr.next.pre = cur;
            cur.next = this.front_ptr.next;
            this.front_ptr.next = cur;
        }
        this.sz += 1;
    }

    /**
     * add the item to the end
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node cur = new Node(item);
        if (this.isEmpty()) {
            this.last_ptr.next = cur;
            this.front_ptr.next = cur;
        }
        else {
            this.last_ptr.next.next = cur;
            cur.pre = this.last_ptr.next;
            this.last_ptr = this.last_ptr.next;
        }
        this.sz += 1;
    }

    /**
     * remove and return the item from the front
     * @return
     */
    public Item removeFirst() {
        if (this.sz < 1) throw new NoSuchElementException();
        Node cur = this.front_ptr.next;
        if (this.sz == 1) {
            this.front_ptr.next = null;
            this.last_ptr.next = null;
        }
        else {
            this.front_ptr.next = cur.next;
            cur.next.pre = this.front_ptr;
        }
        this.sz -= 1;
        return cur.item;
    }

    /**
     * remove and return the item from the end
     * @return
     */
    public Item removeLast() {
        if (this.sz < 1) throw new NoSuchElementException();
        Node cur = this.last_ptr.next;
        if (this.sz == 1) {
            this.front_ptr.next = null;
            this.last_ptr.next = null;
        }
        else {
            cur.pre.next = null;
            this.last_ptr.next = cur.pre;
        }
        this.sz -= 1;
        return cur.item;
    }

    /**
     * return an iterator over items in order from front to end
     * @return
     */
    public Iterator<Item> iterator() {
        return new DequeIterator(this.front_ptr.next);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur;
        public DequeIterator(Node first) {
            this.cur = first;
        }

        public boolean hasNext() {
            return this.cur != null;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Item item = this.cur.item;
            this.cur = this.cur.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * unit testing
     * @param args
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.removeLast();
        deque.removeLast();
        assert deque.size() == 0;

        deque.addFirst(0);
        deque.addFirst(1);
        deque.removeFirst();
        deque.size();
        deque.addFirst(4);
        deque.isEmpty();
        deque.removeLast();
        deque.addLast(7);
    }
}
