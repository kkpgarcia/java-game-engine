package SKEngine.Collections;

/**
 * <h2>Node</h2>
 * A data structure for linked data structures.
 * <p>
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-04-02
 * */
public class Node<T> {
    public T element;
    public Node<T> nextNode;

    /**
     * Creates a node that contains an element
     * @param T element
     * */
    public Node(T e) {
        element = e;
        nextNode = null;
    }

    /**
     * Creates a node that contains an element and a linked node
     * @param T element
     * @param Node linked node
     * */
    public Node(T e, Node<T> n) {
        element = e;
        nextNode = n;
    }
}