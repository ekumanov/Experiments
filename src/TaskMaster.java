import java.util.*;

/**
 * @author Evgeni Kumanov
 */
public class TaskMaster {
    public static int getMaxTasks2(int numberOfTasks, int[] children, int[] parents) {
        if (children == null) {
            assert parents == null;
            return numberOfTasks;
        } else {
            assert parents != null && children.length == parents.length;
        }

        if (children.length == 0 || children.length == 1) {
            return numberOfTasks;
        }

        int result = numberOfTasks;

        Map<Integer, Integer> childrenToParents = new HashMap<>(parents.length);
        for (int i = 0; i < parents.length; i++) {
            childrenToParents.put(children[i], parents[i]);
        }

        Set<Integer> tasks = new LinkedHashSet<>();

        for (int i = 1; i <= numberOfTasks; i++) {
            tasks.add(i);
        }

        while (!tasks.isEmpty()) {
            int task = tasks.iterator().next();
            Set<Integer> tasksCopy = new HashSet<>(tasks);
            Set<Integer> executed = executeTask(task, tasksCopy, childrenToParents);
            if (executed == null) {
                result--;
            } else {
                tasks.removeAll(executed);
            }
        }

        return result;
    }

//    private static Set<Integer> canExecute(Integer task, Map<Integer, Integer> childrenToParents, int numberOfTasks) {
//        Set<Integer> tasks = new LinkedHashSet<>();
//
//        for (int i = 1; i <= numberOfTasks; i++) {
//            if (i != task) {
//                tasks.add(i);
//            }
//        }
//        Map<Integer, Integer> childrenToParentsCopy = new HashMap<>(childrenToParents);
//        return executeTask(task, tasks, childrenToParentsCopy);
//    }

    private static Set<Integer> executeTask(Integer task, Set<Integer> tasks, Map<Integer, Integer> childrenToParents) {
        if (!tasks.contains(task)) {
            return null;
        }

        tasks.remove(task);
        Set<Integer> executed = new HashSet<>();
        executed.add(task);

        if (childrenToParents.containsKey(task)) {
            int parent = childrenToParents.remove(task);
            Set<Integer> parentResult = executeTask(parent, tasks, childrenToParents);
            if (parentResult == null) {
                return null;
            } else {
                executed.addAll(parentResult);
            }
        }

        return executed;
    }

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

        Map<Integer, Set<Integer>> parentToChildren = new HashMap<>(parents.length);
        Set<Integer> usedParents = new HashSet<>(parents.length);

        int conflicting = 0;

        for (int i = 0; i < parents.length; i++) {
            Set<Integer> childrenSet = parentToChildren.get(parents[i]);
            if (childrenSet == null) {
                childrenSet = new HashSet<>();
                parentToChildren.put(parents[i], childrenSet);
            }
            childrenSet.add(children[i]);
        }

        while (!parentToChildren.isEmpty()) {
            Integer someParent = parentToChildren.entrySet().iterator().next().getKey();
            conflicting += getConflicts(someParent, parentToChildren, usedParents);
        }

        return numberOfTasks - conflicting;
    }

    private static int getConflicts(Integer parent, Map<Integer, Set<Integer>> parentToChildren, Set<Integer> usedParents) {
        Set<Integer> children = parentToChildren.get(parent);
        usedParents.add(parent);
        parentToChildren.remove(parent);

        int result = 0;

        for (Integer child : children) {
            if (usedParents.contains(child)) {
                result += 1;
            } else if (parentToChildren.containsKey(child)) {
                result += getConflicts(child, parentToChildren, usedParents);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getMaxTasks2(10,
                new int[]{2, 4, 5, 6, 7, 8, 9, 10},
                new int[]{6, 3, 8, 4, 8, 10, 1, 3}));
    }
}
