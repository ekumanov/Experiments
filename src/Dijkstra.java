import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Evgeni Kumanov
 */
public class Dijkstra {
    public static class Node {
        private final int row;
        private final int col;
        private int distance;

        Node(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return row == node.row &&
                    col == node.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    private static int findShortestPathLength(int[][] maze, int rows, int columns,
                                              int startRow, int startCol, int endRow, int endCol) {
        if (maze == null
                || rows < 1 || columns < 1
                || startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0
                || rows != maze.length
                || startRow >= rows || startCol >= columns || endRow >= rows || endCol >= columns) {
            return -1;
        }


        Node[][] nodeIndex = new Node[rows][];
        Set<Node> unvisited = new HashSet<>();

        for (int rowNum = 0; rowNum < rows; rowNum++) {
            int[] row = maze[rowNum];
            if (row == null || row.length != columns) {
                return -1;
            }

            Node[] nodeIndexsRow = new Node[row.length];
            nodeIndex[rowNum] = nodeIndexsRow;

            for (int colNum = 0; colNum < row.length; colNum++) {
                if (row[colNum] == 0) {
                    Node node = new Node(rowNum, colNum, Integer.MAX_VALUE);
                    nodeIndex[rowNum][colNum] = node;
                    unvisited.add(node);
                }
            }
        }

        if (maze[startRow][startCol] == 1) {
            return -1;
        }

        Node current = nodeIndex[startRow][startCol];
        current.distance = 0;

        while (current != null) {

            if (current.row == endRow && current.col == endCol) {
                return current.distance;
            }

            // left neighbour
            if (current.col > 0 && maze[current.row][current.col - 1] == 0
                    && unvisited.contains(nodeIndex[current.row][current.col - 1])) {
                calculateDistance(nodeIndex[current.row][current.col - 1], current.distance + 1);
            }

            // right neighbour
            if (current.col < columns - 1 && maze[current.row][current.col + 1] == 0
                    && unvisited.contains(nodeIndex[current.row][current.col + 1])) {
                calculateDistance(nodeIndex[current.row][current.col + 1], current.distance + 1);
            }

            // upper neighbour
            if (current.row > 0 && maze[current.row - 1][current.col] == 0
                    && unvisited.contains(nodeIndex[current.row - 1][current.col])) {
                calculateDistance(nodeIndex[current.row - 1][current.col], current.distance + 1);
            }

            // lower neighbour
            if (current.row < rows - 1 && maze[current.row + 1][current.col] == 0
                    && unvisited.contains(nodeIndex[current.row + 1][current.col])) {
                calculateDistance(nodeIndex[current.row + 1][current.col], current.distance + 1);
            }

            unvisited.remove(current);
            current = findClosestNode(unvisited);
        }

        return -1;
    }

    private static Node findClosestNode(Set<Node> nodes) {
        Node result = null;
        for (Node node : nodes) {
            if (node.distance != Integer.MAX_VALUE) {
                if (result == null) {
                    result = node;
                } else if (node.distance < result.distance) {
                    result = node;
                }
            }
        }
        return result;
    }

    private static void calculateDistance(Node node, int actualDistance) {
        if (actualDistance < node.distance) {
            node.distance = actualDistance;
        }
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0}};
        System.out.println(findShortestPathLength(maze, 4, 4, 0, 0, 0, 2));
    }
}
