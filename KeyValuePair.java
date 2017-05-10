public class KeyValuePair<T extends Comparable<T>,U> {
    public T key;
    public U value;
    public KeyValuePair<T, U> parent;
    public KeyValuePair<T, U> left;
    public KeyValuePair<T, U> right;

    public KeyValuePair(T key, U value) {
        this.key = key;
        this.value = value;
    }
}