import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Double[] numbers = { 2.0, 8.0, 10.0, 13.0, 17.0, 20.0, 34.0, 38.0, 53.0 };

        System.out.println("========SINGLE LINK CLUSTER========");
        linkeageClustering(numbers, 3, Cluster.SINGLE_LINK_TYPE);
        System.out.println("========COMPLETE LINK CLUSTER========");
        linkeageClustering(numbers, 3, Cluster.COMPLETE_LINK_TYPE);
        System.out.println("========AVERAGE LINK CLUSTER========");
        linkeageClustering(numbers, 3, Cluster.AVERAGE_LINK_TYPE);
    }

    public static void linkeageClustering(Double[] numbers, int iterations, String clusterType) {
        if (iterations > numbers.length) {
            System.out.println("Numbers length must be greater or equal than the number of iterations");
            return;
        }

        LinkedList<Cluster> clusters = new LinkedList<Cluster>();

        for (Double number : numbers) {
            List<Double> numberList = Arrays.asList(number);
            LinkedList<Double> numberLinkedList = new LinkedList<Double>(numberList);
            Cluster cluster = new Cluster(numberLinkedList, clusterType);
            clusters.add(cluster);
        }

        for (int i = 0; i < iterations; i++) {
            int minIndex = getClusterIndexWithMinDistance(clusters);
            clusters.get(minIndex).merge(clusters.get(minIndex + 1));
            clusters.remove(minIndex + 1);
        }

        printClustersDetail(clusters);
    }

    private static int getClusterIndexWithMinDistance(LinkedList<Cluster> clusters) {
        Double minDistance = clusters.get(0).getDistance(clusters.get(1));
        int minIndex = 0;

        for (int i = 1; i < clusters.size() - 1; i++) {
            Double distance = clusters.get(i).getDistance(clusters.get(i + 1));

            if (distance < minDistance) {
                minDistance = distance;
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static void printClustersDetail(LinkedList<Cluster> clusters) {
        for (int i = 0; i < clusters.size(); i++) {
            System.out.printf(
                    "CLUSTER %d\nNumbers: %s\n------------------\n",
                    i + 1,
                    clusters.get(i).getNumbersString());
        }
    }
}