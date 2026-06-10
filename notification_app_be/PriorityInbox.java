import java.time.LocalDateTime;
import java.util.*;

class Notification {
    String id;
    String type;
    String message;
    LocalDateTime timestamp;
    double score;

    public Notification(String id, String type, String message, LocalDateTime timestamp) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
        calculateScore();
    }

    private void calculateScore() {
        int weight = switch (type) {
            case "Placement" -> 3;
            case "Result" -> 2;
            default -> 1;
        };

        long minutesOld =
                java.time.Duration.between(timestamp, LocalDateTime.now()).toMinutes();

        score = weight * 1000 - minutesOld;
    }

    @Override
    public String toString() {
        return type + " - " + message + " Score=" + score;
    }
}

public class PriorityInbox {

    public static void main(String[] args) {

        List<Notification> list = new ArrayList<>();

        list.add(new Notification(
                "1",
                "Placement",
                "AMD Hiring",
                LocalDateTime.now().minusMinutes(5)));

        list.add(new Notification(
                "2",
                "Result",
                "Mid Sem Result",
                LocalDateTime.now().minusMinutes(2)));

        list.add(new Notification(
                "3",
                "Event",
                "Tech Fest",
                LocalDateTime.now().minusMinutes(1)));

        PriorityQueue<Notification> pq =
                new PriorityQueue<>((a, b) ->
                        Double.compare(b.score, a.score));

        pq.addAll(list);

        System.out.println("Top Notifications");

        int count = 0;

        while (!pq.isEmpty() && count < 10) {
            System.out.println(pq.poll());
            count++;
        }
    }
}