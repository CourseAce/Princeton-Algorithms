import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by Daniel on 10/07/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int sz;
    private Item[] mem;  // can be replaced by ArrayList

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.sz = 0;
        this.mem = (Item[]) new Object[2];  // casting referenceeeee
    }

    /**
     * is the queue empty?
     * @return
     */
    public boolean isEmpty() {
        return this.sz == 0;
    }

    /**
     * return the number of items on the queue
     * @return
     */
    public int size() {
        return this.sz;
    }

    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (this.sz >= this.mem.length) {
            this.resize(2*this.mem.length);
        }
        this.mem[this.sz] = item;
        this.sz += 1;
    }

    private void resize(int l) {
        Item[] org = this.mem;
        this.mem = (Item []) new Object[l];
        for (int i=0; i < Math.min(l, org.length); i++)
            this.mem[i] = org[i];
    }

    private void swap(int i, int j) {
        Item temp = this.mem[i];
        this.mem[i] = this.mem[j];
        this.mem[j] = temp;
    }

    /**
     * remove and return a random item
     * @return
     */
    public Item dequeue() {
        if (this.sz < 1) throw new NoSuchElementException();
        int idx = StdRandom.uniform(this.sz);
        Item ret = this.mem[idx];
        this.swap(idx, this.sz-1);
        this.mem[this.sz-1] = null;
        this.sz -= 1;
        if (this.sz == this.mem.length/4) {
            this.resize(this.mem.length/2);
        }
        return ret;
    }

    /**
     * return (but do not remove) a random item
     * @return
     */
    public Item sample() {
        if (this.sz < 1) throw new NoSuchElementException();
        int idx = StdRandom.uniform(this.sz);
        return this.mem[idx];
    }

    /**
     * return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] idx;
        int cur;
        RandomizedQueueIterator() {
            this.cur = 0;
            this.idx = new int[sz];
            for(int i = 0; i < sz; i++) this.idx[i] = i;
            StdRandom.shuffle(this.idx);
        }
        public boolean hasNext() {
            return this.cur < idx.length;
        }

        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            int i = this.idx[this.cur];
            this.cur += 1;
            return mem[i];
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
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        int[] expected = new int[] {1, 2, 3};
        Set<Integer> exp = new HashSet<Integer>();
        for (int i: expected) {
            exp.add(i);
            q.enqueue(i);
        }
        for (Integer elt: q) {
            assert exp.contains(elt);
            exp.remove(elt);
        }

        for (int i: expected) exp.add(i);
        int sz = q.size();
        for (int i=0; i < sz; i++) {
            int elt = q.dequeue();
            assert exp.contains(elt);
            exp.remove(elt);
        }
    }
}
