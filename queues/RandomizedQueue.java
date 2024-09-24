/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (count == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No item to add");
        }
        if (count == arr.length) {
            resize(2 * arr.length);
        }
        arr[count] = item;
        count++;
    }

    // remove and return a random item
    public Item dequeue() { //
        if (isEmpty()) {
            throw new NoSuchElementException("No items to remove");
        }
        int index = StdRandom.uniformInt(count);
        Item chosen = arr[index];
        Item temp = arr[count - 1];
        arr[index] = temp;
        arr[count - 1] = null;
        count--;
        if (count > 0 && count == arr.length / 4) {
            resize(arr.length / 2);
        }
        return chosen;
    }

    // return a random item (but do not remove it)
    public Item sample() { //
        if (isEmpty()) {
            throw new NoSuchElementException("No items to sample");
        }
        int index = StdRandom.uniformInt(count);
        return arr[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private final Item[] copy;
        private int i = count;

        ListIterator() {
            copy = (Item[]) new Object[count];

            for (int j = 0; j < i; j++) {
                copy[j] = arr[j];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy[--i];
        }
    }

    private void resize(int length) {
        Item[] copy = (Item[]) new Object[length];
        for (int i = 0; i < count; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        if (queue.isEmpty()) {
            StdOut.println("The queue is empty");
        }
        StdOut.println();

        // Testing multiple iterators running simultaneously
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }
        StdOut.println("The size of the queue is: " + queue.size());
        StdOut.println();
        for (int a : queue) {
            for (int b : queue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
        StdOut.println();

        for (int num : queue) {
            StdOut.println("Sample of a random item in queue: " + queue.sample());
        }
        StdOut.println();

        for (int num : queue) {
            StdOut.println(queue.dequeue() + " was randomly dequeued");
        }
    }

}
