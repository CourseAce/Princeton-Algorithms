import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Daniel on 10/07/15.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private final int MIN_SZ = 2;
    private int sz;
    private Item[] mem;  // can be replaced by ArrayList

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.sz = 0;
        this.mem = (Item[]) new Object[MIN_SZ];  // casting referenceeeee
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
        l = Math.max(l, MIN_SZ);
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

    /**
     * In the case of INTERMIXED call with add, remove, the behavior of an iterator is unspecified if the underlying
     * collection is modified while the iteration is in progress in any way other than by calling this method.
     */
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
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(239);
        rq.dequeue();
        rq.enqueue(792);
        rq.dequeue();
        rq.size();
        rq.enqueue(70);
    }
}
