/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;

    }

    // is the deque empty?
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No item to add");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No item to add");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("No items to remove");
        }
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("No items to remove");
        }
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { // Add the two throw exceptions
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> de = new Deque<String>();
        if (de.isEmpty()) {
            StdOut.println("The Deque is empty");
        }
        StdOut.println();

        de.addFirst("Mary");
        de.addFirst("John");
        de.addLast("Jeff");

        StdOut.println("The DeQue consists of: ");
        for (String s : de) {
            StdOut.println(s);
        }
        StdOut.println();
        StdOut.println("The size of the Deque is " + de.size() + " people");
        StdOut.println();
        StdOut.println("The person at the front of the Deque was: " + de.removeFirst()
                               + " and they are no longer in the Deque");
        StdOut.println();
        StdOut.println("The DeQue consists of: ");
        for (String s : de) {
            StdOut.println(s);
        }
        StdOut.println();
        while (!de.isEmpty()) {
            StdOut.println("The person at the end of the Deque was: " + de.removeLast()
                                   + " and they are no longer in the Deque");
            StdOut.println();
        }
        StdOut.println("The DeQue now consists of: " + de.size() + " people");
        StdOut.println();
        if (de.isEmpty()) {
            StdOut.println("The Deque is empty");
        }
    }

}
