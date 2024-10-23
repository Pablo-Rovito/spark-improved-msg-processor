import java.util.*;
import java.util.stream.Collectors;

public class TweetProcessor {
    private static List<String> FORBIDDEN_WORDS = List.of("caca", "pedo", "culo", "pis", "mierda");
    public static LinkedHashMap<String, Long> getTopics(List<String> tweets) {

        return tweets.parallelStream()
                .map(tweet -> tweet.split(" ")) // Separo el tweet de los hashtag. Solo las string con # pueden ser topics
                .flatMap(splitTweet -> Arrays.stream(splitTweet).filter(
                        splitStr -> splitStr.contains("#") && splitStr.split("")[0].equals("#"))
                ) // extraigo la lista de Strings[] con topics válidos
                .flatMap(string -> Arrays.stream(string.toLowerCase().split("#"))) // extraigo los topics en una List<String>
                .filter(topic -> !topic.isEmpty()) // saco los topic vacíos
                .filter(topic -> !FORBIDDEN_WORDS.contains(topic)) // saco los topic prohibidos
                .collect(Collectors.groupingByConcurrent(String::valueOf, Collectors.counting())) // armo un Map con los topics y su ocurrencia
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // ordeno de mayor a menor los topics
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}