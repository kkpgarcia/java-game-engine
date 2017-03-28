public class Comparison {
    public static boolean tolerantEquals(float a, float b) {
        return ((Math.abs(a - b)) < 0.00001f);
    }

    public static boolean tolerantGreaterThanOrEquals(float a, float b) {
        return a > b || tolerantEquals(a, b);
    }

    public static boolean tolerantLesserThanOrEquals(float a, float b) {
        return a < b || tolerantEquals(a, b);
    }
}