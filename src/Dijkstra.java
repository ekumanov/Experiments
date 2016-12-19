/**
 * @author Evgeni Kumanov
 */
public class Dijkstra {
    public static class Node {
        private final int row;
        private final int col;
        private int distance;
        private boolean visited;

        Node(int row, int col, int distance, boolean visited) {
            this.row = row;
            this.col = col;
            this.distance = distance;
            this.visited = visited;
        }
    }

    private static int findShortestLength(int[][] maze, int rows, int columns, int endRow, int endCol) {
        if (maze == null || rows < 1 || columns < 1 || endRow < 0 || endCol < 0 || rows != maze.length
                || endRow >= rows || endCol >= columns || maze[0][0] == 1) {
            return -1;
        }


        Node[][] nodeIndex = new Node[rows][];

        for (int rowNum = 0; rowNum < rows; rowNum++) {
            int[] row = maze[rowNum];
            if (row == null || row.length != columns) {
                return -1;
            }

            Node[] nodeIndexsRow = new Node[row.length];
            nodeIndex[rowNum] = nodeIndexsRow;

            for (int colNum = 0; colNum < row.length; colNum++) {
                if (row[colNum] == 0) {
                    Node node = new Node(rowNum, colNum, (rowNum == 0 && colNum == 0) ? 0 : Integer.MAX_VALUE, false);
                    nodeIndex[rowNum][colNum] = node;
                }
            }
        }

        Node current = nodeIndex[0][0];
        current.distance = 0;

        while (current != null) {

            if (current.row == endRow && current.col == endCol) {
                return current.distance;
            }

            // left neighbour
            if (current.col > 0 && maze[current.row][current.col - 1] == 0
                    && !nodeIndex[current.row][current.col - 1].visited) {
                calculateDistance(nodeIndex[current.row][current.col - 1], current.distance + 1);
            }

            // right neighbour
            if (current.col < columns - 1 && maze[current.row][current.col + 1] == 0
                    && !nodeIndex[current.row][current.col + 1].visited) {
                calculateDistance(nodeIndex[current.row][current.col + 1], current.distance + 1);
            }

            // upper neighbour
            if (current.row > 0 && maze[current.row - 1][current.col] == 0
                    && !nodeIndex[current.row - 1][current.col].visited) {
                calculateDistance(nodeIndex[current.row - 1][current.col], current.distance + 1);
            }

            // lower neighbour
            if (current.row < rows - 1 && maze[current.row + 1][current.col] == 0
                    && !nodeIndex[current.row + 1][current.col].visited) {
                calculateDistance(nodeIndex[current.row + 1][current.col], current.distance + 1);
            }

            current.visited = true;
            current = findClosestUnvisited(nodeIndex);
        }

        return -1;
    }

    private static Node findClosestUnvisited(Node[][] nodeIndex) {
        Node result = null;
        for (Node[] row : nodeIndex) {
            for (Node node : row) {
                if (node != null && !node.visited && node.distance != Integer.MAX_VALUE) {
                    if (result == null) {
                        result = node;
                    } else if (node.distance < result.distance) {
                        result = node;
                    }
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
        System.out.println(findShortestLength(maze, 4, 4, 1, 3));
    }
}
