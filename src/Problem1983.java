import java.util.ArrayList;

/**
 * A solution to the problem of finding a number which is multiple of 1983
 * and whose digits are only 0 or 1 in digital representation.
 *
 * @author Evgeni Kumanov
 */
public class Problem1983 {
    public static void main(String[] args) {
        Number n1 = new Number(1110110109027L);
        Number n2 = new Number(1983);

        long l = 559813470;

        do {
            n1.addNumber(n2);
            if (n1.checkCondition()) {
                System.out.println(l + " * 1983 = " + n1);
            }
            l++;
        } while (l < Long.MAX_VALUE);

        //System.out.println(i + " * 1983 = " + n1);
    }
}

class Number {
    private ArrayList<Byte> number = new ArrayList<>();

    Number(long num) {
        long a, b;
        while ((a = num / 10) != 0 | (b = num % 10) != 0) {
            number.add((byte) b);
            num = a;
        }
    }

    void addNumber(Number number2) {
        byte sum = 0;

        for (int i = 0; i < Math.max(this.numberOfDigits(), number2.numberOfDigits()); i++) {
            if (this.numberOfDigits() < i + 1) {
                this.number.add(sum);
                sum = 0;
            }
            byte digit = number2.numberOfDigits() < i + 1 ? 0 : number2.getDigit(i);
            sum = (byte) (sum + this.getDigit(i) + digit);
            this.number.set(i, sum > 9 ? (byte) (sum - 10) : sum);
            sum = (byte) (sum < 10 ? 0 : 1);
        }
        if (sum == 1) {
            this.number.add((byte) 1);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Byte digit : number) {
            sb.insert(0, digit);
        }
        return sb.toString();
    }

    private int numberOfDigits() {
        return number.size();
    }

    private byte getDigit(int position) {
        return number.get(position);
    }

    boolean checkCondition() {
        for (Byte digit : this.number) {
            if (digit > 1) {
                return false;
            }
        }
        return true;
    }
}