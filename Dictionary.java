public class Dictionary<T extends Comparable<T>,U> {
    private KeyValuePair<T,U> root;
    private int count;

    public Dictionary() {}

    public int size(){
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

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

    public KeyValuePair<T,U> minimum() {
        KeyValuePair<T,U> temp = root;
        while(temp.left != null) 
            temp = temp.left;
        return temp;
    }

    public KeyValuePair<T,U> maximum() {
        KeyValuePair<T,U> temp = root;
        while(temp.right != null) 
            temp = temp.left;
        return temp;
    }

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

    public KeyValuePair<T,U> findSuccessor(KeyValuePair<T,U> pair) {
        if(pair == null) 
            return null;
        
        while(pair.left != null) 
            pair = pair.left;
        
        return pair;
    }

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

    public KeyValuePair<T,U> findPredecessor(KeyValuePair<T,U> pair) {
        if(pair == null)
            return null;

        while(pair.right != null)
            pair = pair.right;
        
        return pair;
    }

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

    public U getValue(T key) {
        if(count == 0)
            return null;

        KeyValuePair<T,U> temp = find(key);
        return temp.value;
    }

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