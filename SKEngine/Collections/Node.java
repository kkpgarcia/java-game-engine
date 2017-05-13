package SKEngine.Collections;

public class Node<T> {
    public T element;
    public Node<T> nextNode;

    public Node(T e) {
        element = e;
        nextNode = null;
    }

    public Node(T e, Node<T> n) {
        element = e;
        nextNode = n;
    }
}