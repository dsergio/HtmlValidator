// LinkedQueue provides an implementation of the Queue interface that uses a
// LinkedList to store the elements of the queue.  All operations will execute
// in O(1) time.

import java.util.*;

public class LinkedQueue<E> implements Queue<E> {
    private LinkedList<E> elements;

    // post: constructs an empty queue
    public LinkedQueue() {
        elements = new LinkedList<E>();
    }

    // post: given value inserted at the end of the queue
    public void enqueue(E value) {
        elements.addLast(value);
    }

    // pre : !isEmpty()
    // post: removes and returns the value at the front of the queue
    public E dequeue() {
        if (isEmpty())
            throw new IllegalStateException("attempt to dequeue empty queue");
        return elements.removeFirst();
    }

    // post: returns true if the queue is empty, false otherwise
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // post: returns the current number of elements in the queue
    public int size() {
        return elements.size();
    }

    // post: returns a String representation of the queue contents
    public String toString() {
        return "front " + elements + " back";
    }
}
