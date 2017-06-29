// ArrayStack provides an implementation of the Stack interface that uses an
// ArrayList to store the elements of the stack.  All operations will execute
// in O(1) time.

import java.util.*;

public class ArrayStack<E> implements Stack<E> {
    private ArrayList<E> elements;

    // post: constructs an empty stack
    public ArrayStack() {
        elements = new ArrayList<E>();
    }

    // post: given value is pushed onto the top of the stack
    public void push(E value) {
        elements.add(value);
    }

    // pre : !isEmpty()
    // post: removes and returns the value at the top of the stack
    public E pop() {
        if (isEmpty())
            throw new IllegalStateException("attempt to pop empty stack");
        return elements.remove(elements.size() - 1);
    }

    // post: returns true if the stack is empty, false otherwise
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // post: returns the current number of elements in the stack
    public int size() {
        return elements.size();
    }

    // post: returns a String representation of the stack contents
    public String toString() {
        return "bottom " + elements + " top";
    }
}
