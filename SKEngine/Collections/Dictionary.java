package SKEngine.Collections;

/**
 * <h2>Dictionary</h2>
 * An associative data structure composed of <b>KeyValuePair</b>.
 * <p>
 * @see KeyValuePair - SKEngine.Collections
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-04-03
 * */
public class Dictionary<T extends Comparable<T>,U> {
    private KeyValuePair<T,U> root;
    private int count;

    /**
     * Creates an empty dictionary.
     * */
    public Dictionary() {}

    /**
     * Returns the amount of linked <b>KeyValuePair</b> inside the dictionary
     * @return int number of elements inside the dictionary
     * */
    public int size(){
        return count;
    }

    /**
     * Checks if this dictionary is empty.
     * @return boolean returns true if it is empty
     * */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Adds an element inside this dictionary.
     * @param T key
     * @param U value
     * @throws <b>Exception</b> if the dictionary already contains the new key
     * */
    public void add(T key, U value) throws Exception  {
        KeyValuePair<T,U> tempPair = new KeyValuePair<T,U>(key,value);

        if(root == null) {
            root = tempPair;
            count++;
        } else {
            KeyValuePair<T,U> parentPair = null;
            KeyValuePair<T,U> currentPair = root;

            if(currentPair.key.equals(tempPair.key)) {
                throw new Exception("Duplicate entry!");
            }

            while(currentPair != null) {
                parentPair = currentPair;
                int compareValue = tempPair.key.compareTo(currentPair.key);

                if(compareValue <= 0) {
                    currentPair = currentPair.left;
                    if(currentPair == null) 
                        parentPair.left = tempPair;
                } else {
                    currentPair = currentPair.right;
                    if(currentPair == null) 
                        parentPair.right = tempPair;
                }
            }

            count++;
        }
    }

    /**
     * Gets the lowest <b>Key</b> value / Gets the left most value from the tree structure
     * @return KeyValuePair<T,U> lowest value
     * */
    public KeyValuePair<T,U> minimum() {
        KeyValuePair<T,U> temp = root;
        while(temp.left != null) 
            temp = temp.left;
        return temp;
    }

    /**
     * Gets the highest <b>Key</b> value / Gets the right most value from the tree structure
     * @return KeyValuePair<T,U> highest value
     * */
    public KeyValuePair<T,U> maximum() {
        KeyValuePair<T,U> temp = root;
        while(temp.right != null) 
            temp = temp.left;
        return temp;
    }

    /**
     * Looks for a specific <b>KeyValuePair</b> based on the given key.
     * @param T key of the <b>KeyValuePair</b> to get.
     * @return <b>KeyValuePair</b> equals to the given key.
     * */
    public KeyValuePair<T,U> find(T key) {
        KeyValuePair<T,U> temp = root;

        while(temp != null && !temp.key.equals(key)) {
            if(key.compareTo(temp.key) <= 0)
                temp = temp.left;
            else 
                temp = temp.right;
        }
        return temp;
    }

    /**
     * Gets the successor of the given pair
     * @param <b>KeyValuePair</b> parent pair
     * @return KeyValuePair<T,U> successor pair
     * */
    public KeyValuePair<T,U> findSuccessor(KeyValuePair<T,U> pair) {
        if(pair == null) 
            return null;
        
        while(pair.left != null) 
            pair = pair.left;
        
        return pair;
    }

    /**
     * Gets the successor of the given pair
     * @param <b>T</b> parent key
     * @return KeyValuePair<T,U> successor pair
     * */
    public KeyValuePair<T,U> getSuccessor(T key) {
        KeyValuePair<T,U> pair = find(key);

        if(pair == null)
            return null;
        if(pair.right != null)
            return findSuccessor(pair.right);
        
        KeyValuePair<T,U> successorPair = pair.parent;

        while(successorPair != null && successorPair.left != pair) {
            pair = successorPair;
            successorPair = successorPair.parent;
        }

        return successorPair;
    }

    /**
     * Gets the predecessor of the given pair
     * @param <b>KeyValuePair</b> successor pair
     * @return KeyValuePair<T,U> predecessor pair
     * */
    public KeyValuePair<T,U> findPredecessor(KeyValuePair<T,U> pair) {
        if(pair == null)
            return null;

        while(pair.right != null)
            pair = pair.right;
        
        return pair;
    }

    /**
     * Gets the successor of the given pair
     * @param <b>T</b> child key
     * @return KeyValuePair<T,U> predecessor pair
     * */
    private KeyValuePair<T,U> getPredecessor(T key) {
        KeyValuePair<T,U> pair = find(key);

        if(pair == null)
            return null;

        if(pair.left != null)
            return findPredecessor(pair.left);

        KeyValuePair<T,U> predecessorPair = pair.parent;

        while(predecessorPair != null && pair != predecessorPair.right) {
            pair = predecessorPair;
            predecessorPair = predecessorPair.parent;
        }
        return predecessorPair;
    }

    /**
     * Removes an element from the dictionary
     * @param <b>T</b> key of the KeyValuePair to be removed
     * @throws Exception if the dicitonary is empty
     * */
    public void remove(T key) throws Exception {
        KeyValuePair<T,U> pair = find(key);

        if(root == null)
            throw new Exception("Dictionary is empty!");

        if(pair.left == null && pair.right == null) {
            KeyValuePair<T,U> parentPair = pair.parent;
            if(parentPair == null) 
                root = null;
            else if(parentPair.left == pair)
                parentPair.left = null;
            else
                parentPair.right =null;
            pair.parent = null;
        }

        if(pair.left != null && pair.right == null) {
            KeyValuePair<T,U> parentPair = pair.parent;

            if(parentPair == null)
                root = pair.left;
            else {
                if(parentPair.left == pair)
                    parentPair.left = pair.left;
                else
                    parentPair.right = pair.right;
            }

            pair.left.parent = parentPair;
            pair.parent = null;
            pair.left = null;
        }

        if(pair.left == null && pair.right == null) {
            KeyValuePair<T,U> parentPair = pair.parent;

            if(parentPair == null)
                root = pair.right;
            else {
                if(parentPair.left == pair)
                    parentPair.left = pair.right;
                else
                    parentPair.right = pair.right;
            }

            pair.right.parent = parentPair;
            pair.parent = null;
            pair.right = null;
        }

        if(pair.left != null && pair.right != null) {
            KeyValuePair<T,U> parentPair = pair.parent;

            KeyValuePair<T,U> successorPair = getSuccessor(pair.key);
            KeyValuePair<T,U> successorParent = successorPair.parent;
            KeyValuePair<T,U> successorRight = successorPair.right;

            if(successorRight == null) {
                pair.key = successorPair.key;
                pair.value = successorPair.value;
                if(successorParent.right == successorPair)
                    successorPair.right = null;
                else
                    successorParent.left = null;
                return;
            } else {
                pair.key = successorPair.key;
                pair.value = successorPair.value;
                if(successorParent.right == successorPair)
                    successorParent.right = successorRight;
                else
                    successorParent.left = successorRight;
            }

            successorRight.parent = successorParent;
            successorPair.parent = null;
            successorPair.left = null;
            successorPair.right = null;
        }

        count--;
    }

    /**
     * Gets the value of the given key
     * @param <b>T</b> key
     * @return <b>U</b> value
     * */
    public U getValue(T key) {
        if(count == 0)
            return null;

        KeyValuePair<T,U> temp = find(key);
        return temp.value;
    }

    /**
     * Checks if a certain key exists
     * @param <b>T</b> key
     * @return boolean if the key is found
     * */
    public boolean exists(T key) {
        KeyValuePair<T,U> temp = find(key);
        return temp != null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        if(count == 0)
            builder.append("    ").append("Dictionary is empty.");
        else {
            builder.append("    ").append(pairString(root));
        }
        return builder.toString();
    }

    private String pairString(KeyValuePair pair) {
        StringBuilder builder = new StringBuilder();

        builder.append(pair.toString() + "\n");

        if(pair.left != null)
            builder.append("    ").append(pairString(pair.left));
        if(pair.right != null)
            builder.append("    ").append(pairString(pair.right));
        
        return builder.toString();
    }
}