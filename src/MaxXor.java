/**
 * @author Evgeni Kumanov
 */
public class MaxXor {
    static int maxXor(int left, int right, int k) {
        int result = 0;

        for (int a = left; a < right; a++) {
            for (int b = left + 1; b <= right; b++) {
                int xor = a ^ b;
                if (xor <= k && xor > result) {
                    result = xor;
                }
                if (result == k) {
                    return result;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }
}
