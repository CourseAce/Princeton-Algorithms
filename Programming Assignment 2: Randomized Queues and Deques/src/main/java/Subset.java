import java.util.Iterator;

/**
 * Created by Daniel on 10/07/15.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        String item;
        while (!StdIn.isEmpty())  {  // rather than hasNext()
            item = StdIn.readString();
            q.enqueue(item);
        }
        Iterator<String> itr = q.iterator();
        for (int i=0; i < k; i++) {
            StdOut.println(itr.next());
        }
    }
}
