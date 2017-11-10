import java.util.stream.IntStream;

public class Graph {

    private static int cost = Integer.MAX_VALUE; // bruteForce

    private static int[] swap (int[]vertex, int i, int j) {
        int aux = vertex[i];
        vertex[i] = vertex[j];
        vertex[j] = aux;
        return vertex;
    }

    private static void calculateCost (int[] vertex, int n, int[][] graph) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum+=graph[vertex[i%(n+1)]][vertex[(i+1)%(n+1)]];
        }
        if (cost >= sum) {
            cost = sum;
            //minimum_path = vertex;
        }
    }

    private static void permute (int[] vertex, int i, int n, int[][] graph) {
        if (i == n) {
            calculateCost(vertex, n, graph);
        } else {
            for (int j = i; j <= n; ++j) {
                vertex = swap(vertex, i, j);
                permute(vertex, i+1, n, graph);
                vertex = swap(vertex, i, j);
            }
        }
    }

    private static int bruteForce (int[][] graph, int nVertex) {
        int[]   vertex = new int[nVertex];
        // fill initial path
        IntStream.range(0, nVertex).forEach(i->vertex[i] = i);
        permute(vertex,0, nVertex-1, graph);
        System.out.printf("%d%n",cost);
        return cost;
    }


    private static int naive(int[][] graph, int begin){
        int sum = 0;
        int aux = begin;
        int min = Integer.MAX_VALUE;
        int minEdgeWeight = 0;
        boolean []visited = new boolean[graph.length];
        visited[begin] = true;

        for (int i = 0; i < graph.length-1; ++i) {
            for (int j = 0; j < graph.length; ++j) {
                if(!visited[j] && min > graph[aux][j]){
                    min = graph[aux][j];
                    minEdgeWeight = j;
                }             
            }
            aux = minEdgeWeight;
            visited[minEdgeWeight] = true;
            sum += min;
            min = Integer.MAX_VALUE;
        }
        sum += graph[aux][begin];
        return sum;
    }

    public static void main(String[] args) throws Exception{
        int[][] graph;
        int     nVertex;
        int begin;
        boolean verbose = false;
        boolean read = false;
        boolean naive = false;
        boolean bruteForce = false;

        if (args.length >= 3) {
            try {
                nVertex = Integer.parseInt(args[0]);
                begin = Integer.parseInt(args[1]);
                for (String arg : args) {
                    verbose |= arg.equals("-v");
                    read |= arg.equals("-read");
                    naive |= arg.equals("--n");
                    bruteForce |= arg.equals("--bf");
                }

            } catch (NumberFormatException ex) {
                System.out.printf("Invalid type of argument. Required: [int nVertex] [int begin] [--n xor --bf] optional: [-v] [-read]%n");
                throw new Exception("InvalidArgumentException");
            }

        }
        else {
            nVertex = Utils.readInt();
            begin = Utils.readInt();
        }

        if (nVertex < 2) {
            System.out.printf("Graph's size must be greater than 1%n");
            throw new Exception("InvalidArgumentException");
        }
        if (naive && bruteForce) {
            System.out.printf("You can't run naive and brute force at same time%n");
            throw new Exception("InvalidArgumentException");
        }
        // create and fill graph
        if (read) {
            graph = Utils.readGraph(nVertex);
        } else {
            Utils.getInstance();
            graph = Utils.generateGraph(nVertex);
        }

        if (naive) {
            System.out.printf("Cost found: %d%n", naive(graph, begin));
        }
        if (bruteForce) {
            System.out.printf("Cost found: %d%n", bruteForce(graph, nVertex));
        }
        if (verbose) {
            Utils.printGraph(graph);
        }
    }
}
