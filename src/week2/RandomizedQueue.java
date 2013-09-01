import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rqueue;
    private int idx;
    private int size;


    public RandomizedQueue() {
	this.rqueue = (Item[]) new Object[2];
	this.size = 0;
    }

    public boolean isEmpty() {
	return size == 0;
    }

    public int size() {
	return size;
    }

    public void enqueue(Item item) {
	if (null == item) {
	    throw new NullPointerException();
	}
	if (idx == rqueue.length) resize(2 * rqueue.length);
	rqueue[idx++] = item;
	size++;
    }

    public Item sample() {
	handleEmptyCase();
	return rqueue[StdRandom.uniform(idx)];
    }

    public Item dequeue() {
	handleEmptyCase();
	swap(idx - 1, StdRandom.uniform(idx));
	Item result = rqueue[--idx];
	rqueue[idx] = null;
	if (idx > 0 && idx == rqueue.length/4) resize(rqueue.length/2);
	size--;
	return result;
    }

    private void resize(int capacity) {
	Item[] tmp = (Item[]) new Object[capacity];
	for (int i = 0; i < idx; i++) {
	    tmp[i] = rqueue[i];
	}
	rqueue = tmp;
	//System.out.println("capacity now is: " + rqueue.length);
    }

    private void handleEmptyCase() {
	if (isEmpty())
	    throw new NoSuchElementException();
    }
    private void swap(int i, int j) {
	Item tmp = rqueue[i];
	rqueue[i] = rqueue[j];
	rqueue[j] = tmp;
    }

    public Iterator<Item> iterator() {
    	return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
    	private Item[] items;
    	private int index;

    	public RandomizedIterator() {
    	    items = (Item[]) new Object[size];
    	    for (int i = 0; i < size; i++) {
    		items[i] = rqueue[i];
    	    }
    	    StdRandom.shuffle(items);
	    index = 0;
    	}
    	public boolean hasNext() {

    	    return index < size;
    	}

    	public void remove() {
	    throw new UnsupportedOperationException();
    	}

    	public Item next() {
	    if (index >= size)
		throw new NoSuchElementException();
    	    return items[index++];
    	}
    }

    // public static void main(String[] args)  {
    // 	RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
    // 	r.enqueue(1);
    // 	r.enqueue(2);
    // 	r.enqueue(3);
    // 	r.enqueue(4);
    // 	r.enqueue(5);

    // 	System.out.println(r.sample());
    // 	System.out.println(r.sample());
    // 	System.out.println(r.sample());
    // 	System.out.println(r.size());
    // 	System.out.println("---------------------");
    // 	System.out.println(r.dequeue());
    // 	System.out.println(r.dequeue());
    // 	System.out.println(r.dequeue());
    // 	System.out.println(r.dequeue());
    // 	System.out.println(r.dequeue());

    // 	System.out.println(r.isEmpty());
    // 	System.out.println(r.size());
    // 	//r.dequeue();

    // 	r.enqueue(1);
    // 	r.enqueue(2);
    // 	r.enqueue(3);
    // 	r.enqueue(4);
    // 	r.enqueue(5);

    // 	for (Integer i : r) {
    // 	    System.out.print(i + " ");
    // 	}
    // 	System.out.println();
    // 	for (Integer i : r) {
    // 	    System.out.print(i + " ");
    // 	}
    // 	System.out.println();
    // }
}
