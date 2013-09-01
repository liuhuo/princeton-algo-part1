import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
	Item item;
	Node next;
	Node prev;

	public Node(Item item, Node next, Node prev) {
	    this.item = item;
	    this.next = next;
	    this.prev = prev;
	}
    }

    private Node head, tail;
    private int size;

    public Deque() {
	this.head = this.tail = null;
	this.size = 0;
    }

    public boolean isEmpty() {
	return size == 0;
    }

    public int size() {
	return size;
    }

    public void addFirst(Item item) {
	handleNullInsertion(item);
	if (isEmpty()) {
	    head = tail = new Node(item, null, null);
	}
	else {
	    Node newNode = new Node(item, head, null);
	    head.prev = newNode;
	    head = newNode;
	}
	size++;
    }

    public void addLast(Item item) {
    	handleNullInsertion(item);
	if (isEmpty()) {
	    head = tail = new Node(item, null, null);
	}
	else {
	    Node newNode = new Node(item, null, tail);
	    tail.next = newNode;
	    tail = newNode;
	}
	size++;
    }

    public Item removeFirst() {
    	handleEmptyDeletion();
	Item result = head.item;
    	if (size == 1) {
    	    head = tail = null;
    	}
	else {
	    head.next.prev = null;
	    head = head.next;
	}
    	size--;
	return result;
    }

    public Item removeLast() {
    	handleEmptyDeletion();
	Item result = tail.item;
    	if (size == 1) {
    	    head = tail = null;
    	}
	else {
	    tail.prev.next = null;
	    tail = tail.prev;
	}
    	size--;
	return result;
    }

    private void handleNullInsertion(Item item) {
	if (item == null)
	    throw new java.lang.NullPointerException();
    }

    private void handleEmptyDeletion() {
	if (isEmpty())
	    throw new NoSuchElementException();
    }

    public Iterator<Item> iterator() {
	return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
	private Node current = head;
	public boolean hasNext() {
	    return current != null;
	}

	public Item next() {
	    if (!hasNext()) throw new NoSuchElementException();
	    Item result = current.item;
	    current = current.next;
	    return result;
	}

	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    private void print() {
	for (Item i : this) {
	    System.out.print(i + " ");
	}
	System.out.println();
    }

    // public static void main(String[] args) {
    // 	Deque<Integer> d = new Deque<Integer>();
    // 	d.addFirst(1);
    // 	d.print();
    // 	d.removeFirst();
    // 	d.print();
    // 	d.addLast(1);
    // 	d.print();
    // 	d.removeLast();
    // 	d.print();
    // 	d.addFirst(1);
    // 	d.addFirst(2);
    // 	d.addLast(3);
    // 	d.addLast(4);
    // 	d.print();
    // 	d.removeFirst();
    // 	d.print();
    // 	d.removeLast();
    // 	d.print();
    // }
}
