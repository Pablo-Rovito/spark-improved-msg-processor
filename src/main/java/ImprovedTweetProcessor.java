import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ImprovedTweetProcessor {
    private static final List<String> FORBIDDEN_WORDS = List.of("caca", "pedo", "culo", "pis", "mierda");
    public static List<String> getTopics(List<String> tweets) {
        return tweets.stream()
                .map(tweet -> tweet.replace("#", " #"))            // separo los hashtag entre sí
                .map(tweet -> tweet.split("[ .,_;:()¿?¡!<>+@-]+"))            // separo las palabras guardando los #
                .flatMap(Arrays::stream)                                            // convierte array de palabras en stream de palabras
                .map(String::toLowerCase)                                           // a minúsculas
                .filter(str -> str.contains("#"))                                   // me quedo con los topic
                .map(str -> str.substring(1))                             // saco los #
                .filter(str -> FORBIDDEN_WORDS.stream().noneMatch(str::contains))   // me fijo que no haya palabra prohibida
                .collect(Collectors.toList());
    }

    public static LinkedHashMap<String, Long> countedTopics(List<String> topics) {
        return topics.stream()
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
