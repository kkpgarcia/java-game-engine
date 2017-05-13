package SKEngine.Collections;

import java.util.NoSuchElementException;

/**
 * <h2>Queue</h2>
 * A data structure that provides a first-in-first-out procedure.
 * <p>
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-04-02
 * */
public class Queue<T> {
    private int n;
    private Node<T> first;
    private Node<T> last;

    /**
     * Creates an empty Queue
     * */
    public Queue() {
        first = null;
        last = null;
        n = 0;
    }

    /**
     * Checks if the Queue is empty
     * @return boolean returns true if empty
     * */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the amount of element in the queue
     * @return int amount of element
     * */
    public int size() {
        return n;
    }

    /**
     * Checks the first element in the queue.
     * @return T first element
     * @throws Exception if the queue is emptyqqq
     * */
    public T peek() {
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");
        return first.element;
    }

    /**
     * Adds an element in the queue
     * @param T element to be placed in the queue
     * */
    public void enqueue(T element) {
        Node<T> temp = last;
        last = new Node<T>(element);
        last.nextNode = null;

        if(isEmpty())
            first = last;
        else
            temp.nextNode = last;
        n++;
    }

    /**
     * Removes the first element in the queue
     * @returns T first element
     * */
    public T dequeue() {
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");

        T element = first.element;
        first = first.nextNode;
        n--;
        if(isEmpty())
            last = null;
        return element;
    }
}