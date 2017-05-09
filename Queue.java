import java.util.NoSuchElementException;

public class Queue<T> {
    private int n;
    private Node<T> first;
    private Node<T> last;

    public Queue() {
        first = null;
        last = null;
        n =0 ;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public T peek() {
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");
        return first.element;
    }

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