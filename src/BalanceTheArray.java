/**
 * @author Evgeni Kumanov
 */
public class BalanceTheArray {
    public static int findBalanceIndex(int[] array) {
        if (array == null || array.length == 0 || array.length == 2) {
            return -1;
        }
        if (array.length == 1) {
            return 0;
        }

        int leftIndex = 0;
        int rightIndex = array.length - 1;
        int leftSum = array[leftIndex];
        int rightSum = array[rightIndex];

        while (rightIndex - leftIndex > 2) {
            if (leftSum > rightSum) {
                rightIndex--;
                rightSum += array[rightIndex];
            } else {
                leftIndex++;
                leftSum += array[leftIndex];
            }
        }

        if (leftSum == rightSum) {
            return leftIndex + 1;
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(findBalanceIndex(new int[]{1, 2, 3, 3}));
    }
}
