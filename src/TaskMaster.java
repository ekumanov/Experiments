import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Evgeni Kumanov
 */
public class TaskMaster {
    public static int getMaxTasks(int numberOfTasks, int[] children, int[] parents) {
        if (children == null) {
            assert parents == null;
            return numberOfTasks;
        } else {
            assert parents != null && children.length == parents.length;
        }

        if (children.length == 1) {
            return numberOfTasks;
        }

        Map<Integer, Integer> parentToChildren = new HashMap<>(parents.length);
        Set<Integer> usedParents = new HashSet<>(parents.length);

        int conflicting = 0;

        for (int i = 0; i < parents.length; i++) {
            parentToChildren.put(parents[i], children[i]);
        }

        while (!parentToChildren.isEmpty()) {
            Integer someParent = parentToChildren.entrySet().iterator().next().getKey();
            boolean hasCycle = traverse(someParent, parentToChildren, usedParents);
            if (hasCycle) {
                conflicting++;
            }
        }

        return numberOfTasks - conflicting;
    }

    private static boolean traverse(Integer parent, Map<Integer, Integer> parentToChildren, Set<Integer> usedParents) {
        int child = parentToChildren.get(parent);
        usedParents.add(parent);
        parentToChildren.remove(parent);

        return usedParents.contains(child)
                || (parentToChildren.containsKey(child) && traverse(child, parentToChildren, usedParents));
    }

    public static void main(String[] args) {
        System.out.println(getMaxTasks(2, new int[]{}, new int[]{}));
    }
}
