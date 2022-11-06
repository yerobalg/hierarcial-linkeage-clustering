import java.util.LinkedList;

public class Cluster {
    public static final String SINGLE_LINK_TYPE = "single";
    public static final String COMPLETE_LINK_TYPE = "complete";
    public static final String AVERAGE_LINK_TYPE = "average";

    private LinkedList<Double> numbers;
    public String type;

    public Cluster(LinkedList<Double> numbers, String type) {
        this.type = type;
        this.numbers = numbers;
    }

    public void merge(Cluster source) {
        LinkedList<Double> sourceNumbers = source.getNumbers();
        int firstIndex = 0;

        if (sourceNumbers.get(firstIndex) >= numbers.get(getLastIndex())) {
            numbers.addAll(sourceNumbers);
        } else if (sourceNumbers.get(source.getLastIndex()) <= numbers.get(firstIndex)) {
            numbers.addAll(firstIndex, sourceNumbers);
        }
    }

    public int getLastIndex() {
        return numbers.size() - 1;
    }

    public Double getDistance(Cluster cluster) {
        Double distance = 0.0;
        switch (type) {
            case SINGLE_LINK_TYPE:
                distance = getSingleLinkDistance(cluster);
                break;
            case COMPLETE_LINK_TYPE:
                distance = getCompleteLinkDistance(cluster);
                break;
            case AVERAGE_LINK_TYPE:
                distance = getAverageLinkDistance(cluster);
                break;   
        }

        return distance;
    }

    private Double getSingleLinkDistance(Cluster cluster) {
        Double distance = 0.0;
        Double minDistance = Double.MAX_VALUE;

        for (Double number : numbers) {
            for (Double clusterNumber : cluster.getNumbers()) {
                distance = Math.abs(number - clusterNumber);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    private Double getCompleteLinkDistance(Cluster cluster) {
        Double distance = 0.0;
        Double maxDistance = Double.MIN_VALUE;

        for (Double number : numbers) {
            for (Double clusterNumber : cluster.getNumbers()) {
                distance = Math.abs(number - clusterNumber);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }

        return maxDistance;
    }

    private Double getAverageLinkDistance(Cluster cluster) {
        Double distance = 0.0;
        Double totalDistance = 0.0;

        for (Double number : numbers) {
            for (Double clusterNumber : cluster.getNumbers()) {
                distance = Math.abs(number - clusterNumber);
                totalDistance += distance;
            }
        }

        return totalDistance / (numbers.size() * cluster.getNumbers().size());
    }

    public String getNumbersString() {
        String numbersString = "[";

        for (Double number : numbers) {
            numbersString += number + ", ";
        }

        numbersString = numbersString.substring(0, numbersString.length() - 2);
        return numbersString + "]";
    }

    public LinkedList<Double> getNumbers() {
        return numbers;
    }

}
