// Interface Queue defines a set of operations for manipulating a FIFO
// (First In First Out) structure that can be used to store objects.

public interface Queue<E> {
    // post: given value inserted at the end of the queue
    public void enqueue(E value);

    // pre : !isEmpty()
    // post: removes and returns the value at the front of the queue
    public E dequeue();

    // post: returns true if the queue is empty, false otherwise
    public boolean isEmpty();

    // post: returns the current number of elements in the queue
    public int size();
}
