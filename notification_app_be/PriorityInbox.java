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
        list.add(new Notification("61143514-868c-45c9-a734-79f97c0fa72a", "Placement", "Marriott International Inc. hiring", LocalDateTime.now().minusMinutes(10)));

        list.add(new Notification("b5d7a315-1c5e-4436-99c9-de8236c3e7c3", "Placement", "Berkshire Hathaway Inc. hiring", LocalDateTime.now().minusMinutes(20)));

        list.add(new Notification("3a7a82fe-10f6-499e-9c27-9ed4484d6f1c", "Event", "induction", LocalDateTime.now().minusMinutes(5)));

        list.add(new Notification("538c53c6-9bb9-43e1-ac06-592a8b0c1605", "Event", "tech-fest", LocalDateTime.now().minusMinutes(30)));

        list.add(new Notification("65a23e50-d095-444e-90bf-44f08c143442", "Result", "end-sem", LocalDateTime.now().minusMinutes(15)));

        list.add(new Notification("f92d36ff-db22-46f3-a661-9d223427b9d0", "Event", "induction", LocalDateTime.now().minusMinutes(25)));

        list.add(new Notification("8caa5720-ece1-45e5-9163-d199c4d6b159", "Result", "project-review", LocalDateTime.now().minusMinutes(35)));

        list.add(new Notification("e56d2ea5-3551-4aab-8af1-9b72ed46393c", "Placement", "Berkshire Hathaway Inc. hiring", LocalDateTime.now().minusMinutes(40)));

        list.add(new Notification("49aa2ef9-886f-4d1d-9a6b-025f23f87565", "Result", "internal", LocalDateTime.now().minusMinutes(12)));

        list.add(new Notification("970a8e05-ef97-4dc2-9fce-e47569cea6e2", "Placement", "Booking Holdings Inc. hiring", LocalDateTime.now().minusMinutes(18)));

        list.add(new Notification("caca67a1-b856-4a03-be62-4449a4393f8d", "Placement", "Marriott International Inc. hiring", LocalDateTime.now().minusMinutes(8)));

        list.add(new Notification("62ca098a-16ca-4f81-a09f-fb0fc863f4f6", "Event", "tech-fest", LocalDateTime.now().minusMinutes(50)));

        list.add(new Notification("9e95b618-570e-499d-8a66-799e94e3343a", "Event", "farewell", LocalDateTime.now().minusMinutes(45)));

        list.add(new Notification("cc33a7d3-35cb-4471-b797-0e78167800e6", "Placement", "Meta Platforms Inc. hiring", LocalDateTime.now().minusMinutes(22)));

        list.add(new Notification("e488058a-9e33-4d77-852f-1bcbeb8331d0", "Placement", "Microsoft Corporation hiring", LocalDateTime.now().minusMinutes(28)));

        list.add(new Notification("f66188b8-c20e-4993-9877-12a3aaa7d3db", "Result", "external", LocalDateTime.now().minusMinutes(3)));

        list.add(new Notification("464d040c-3921-4e36-8793-39af58f0eb91", "Placement", "Microsoft Corporation hiring", LocalDateTime.now().minusMinutes(14)));

        list.add(new Notification("f1bc55d3-75c9-4ecf-a3b0-79eac8062f10", "Result", "mid-sem", LocalDateTime.now().minusMinutes(32)));

        list.add(new Notification("ce0fe333-de92-4b1d-a2d1-2db2f93a8077", "Event", "traditional-day", LocalDateTime.now().minusMinutes(6)));

        list.add(new Notification("f806043c-2557-45c6-8078-72225ab072ff", "Event", "induction", LocalDateTime.now().minusMinutes(38)));
        
    

        
 
    
      
    

  
   
 
    

        

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