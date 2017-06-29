// Interface Stack defines a set of operations for manipulating a LIFO
// (Last In First Out) structure that can be used to store elements of
// type E.

public interface Stack<E> {
    // post: given value is pushed onto the top of the stack
    public void push(E value);

    // pre : !isEmpty()
    // post: removes and returns the value at the top of the stack
    public E pop();

    // post: returns true if the stack is empty, false otherwise
    public boolean isEmpty();

    // post: returns the current number of elements in the stack
    public int size();
}
