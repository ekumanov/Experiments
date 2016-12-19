import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Evgeni Kumanov
 */
public class Anagrams {
    public static List<Integer> findAnagrams(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() > s1.length()) {
            return Collections.emptyList();
        }

        List<Integer> result = new LinkedList<>();
        List<Character> chars = new ArrayList<>(s2.length());

        for (char c : s2.toCharArray()) {
            chars.add(c);
        }

        for (int i = 0; i <= s1.length() - s2.length(); i++) {
            boolean found = true;
            List<Character> charsCopy = new LinkedList<>(chars);
            for (int j = i; j < i + s2.length(); j++) {
                Character c = s1.charAt(j);
                if (!charsCopy.remove(c)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(findAnagrams("abcdcdcbacdcd", "abc"));
    }
}
