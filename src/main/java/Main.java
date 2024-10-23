
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("program initialized \n\n");

        List<String> listOfTweets = Files.readString(Path.of("tweets.txt"))
                .lines()
                .filter(line -> !line.isBlank())
                .filter(line -> line.contains("#"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());

//        List<String> tweets = List.of(
//                "Viva la vida #vivirLaVida#caca#vivirLaVida#vivirLaVida",
//                "Viva la muerte #vivirLamuerte#caaaa#Culo#pis",
//                "#forro ## # # Viva la pedo #vivirLamuerte#caca#Culo#pis#forro # ## ",
//                "otro tweet #muchostweets #muchostweets #muchostweets #muchostweets #muchostweets #muchostweets ",
//                "otro tweet #muchostweets#muchostweets#muchostweets#muchostweets#muchostweets#muchostweets ",
//                "verga#verga2" // la regex se me complicó mucho para que funcione incluyendo este caso, asique este es un tweet "invalido"
//                // priorizo detectar topics que no están separados por espacio
//        );
//
//        TweetProcessor.getTopics(tweets).forEach((key, value) -> System.out.printf("%s: %S%n", key, value));

        List<String> topics = ImprovedTweetProcessor.getTopics(listOfTweets);
        ImprovedTweetProcessor.countedTopics(topics).forEach((key, value) -> System.out.printf("%s: %S%n", key, value));

        System.err.println("Seconds elapsed: " + (Calendar.getInstance().getTimeInMillis() - startTime)/1000d);
    }
}
