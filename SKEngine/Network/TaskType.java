package SKEngine.Network;

/**
 * <h2>Task Type</h2>
 * Enumeration of task types for better allocation of tasks.
 * <p>
 * 
 * @author  Kyle Kristopher P. Garcia
 * @since   2017-09-05
 * */
public enum TaskType {
    NEW,
    UPDATE,
    IN,
    OUT;

    /**
     * Converts int to task type.
     * @param int type number
     * @return TaskType type given
     * */
    public static TaskType fromInt(int x) {
        switch(x) {
            case 0:
                return NEW;
            case 1:
                return UPDATE;
            case 2:
                return IN;
            case 3:
                return OUT;
        }
        return null;
    }

    /**
     * Converts task type to int.
     * @param TaskType type given
     * @return int type number
     * */
    public static int toInt(TaskType x) {
        if(x == NEW)
            return 0;
        if(x == UPDATE)
            return 1;
        if(x == IN)
            return 2;
        if(x == OUT)
            return 3;
        return -1;
    }
}