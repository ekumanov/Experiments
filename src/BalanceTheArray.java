/**
 * @author Evgeni Kumanov
 */
public class BalanceTheArray {
    public static int findBalanceIndex(int[] arr) {
        if (arr == null || arr.length == 0 || arr.length == 2) {
            return -1;
        }
        if (arr.length == 1) {
            return 0;
        }

        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        int leftSum = arr[leftIndex];
        int rightSum = arr[rightIndex];

        while (rightIndex - leftIndex > 2) {
            if (leftSum > rightSum) {
                rightIndex--;
                rightSum += arr[rightIndex];
            } else {
                leftIndex++;
                leftSum += arr[leftIndex];
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
