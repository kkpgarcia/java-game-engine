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

    public String toString() {
        StringBuilder builder = new StringBuilder();

        if(key == null)
            builder.append("NULL ");
        else
            builder.append(key).append(" ");

        if(value == null)
            builder.append("NULL ");
        else
            builder.append(value).append(" ");
        
        return builder.toString();
    }
}