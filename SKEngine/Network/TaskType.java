package SKEngine.Network;

public enum TaskType {
    NEW,
    UPDATE,
    IN,
    OUT;

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