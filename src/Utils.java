import java.util.Random;
import java.util.Scanner;

/**
 * Graph
 *
 * @author paulo
 * @since 09/11/17.
 */
class Utils {
    private static Utils instance;

    static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    private static Scanner input = new Scanner(System.in);

    static int[][] generateGraph(int length) {
        int[][]graph = new int[length][length];
        // fill graph
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Random ran = new Random();
                int x = ran.nextInt(50) + 100;
                if(j>i) {
                    graph[i][j] = graph[j][i] = x;
                } else if(i==j) {
                    graph[i][i] = Integer.MAX_VALUE;
                }
            }
        }
        return graph;
    }

    static int[][] readGraph(int length) {
        int[][]graph = new int[length][length];
        int x;
        // fill graph
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(j>i) {
                    x = input.nextInt();
                    graph[i][j] = graph[j][i] = x;
                } else if(i==j) {
                    graph[i][i] = Integer.MAX_VALUE;
                }
            }
        }
        return graph;
    }

    static void printGraph(int[][] graph) {
        for (int[] lin : graph) {
            for (int col : lin) {
                System.out.printf("%d\t",col);
            }
            System.out.printf("%n%n");
        }
    }

    static int readInt() {
        return input.nextInt();
    }

}
