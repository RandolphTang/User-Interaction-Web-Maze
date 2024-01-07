package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String ans = "[";

        for (int i = 0; i < array.length; i++) {
            ans += array[i];
            if (i != (array.length - 1)) {
                ans += ", ";
            }
        }

        ans += "]";

        return ans;


    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] ans = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            ans[i] = array[array.length - i - 1];
        }

        return ans;

    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length > 1) {
            int finalPush = array[array.length - 1];
            for (int i = (array.length - 1); i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = finalPush;
        }

    }
}
