package in.pks.journal.java.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class JsonObject {

    private Map<String, Object> map = new LinkedHashMap<>();
    private BlockingDeque<Character> queue = new LinkedBlockingDeque<>();
    private static List<Character> WHITESPACE_CHARS = Arrays.asList('\n', '\t', '\f', '\b', ' ');
    boolean readingData = false;
    boolean readKey = false;

    public JsonObject() {}

    public static JsonObject getInstance(String filepath) throws IOException {
        JsonObject json = new JsonObject();
        Files.lines(Paths.get(filepath))
                .forEach(json::process);
        return json;
    }

    private void process(String s) {
        for (char ch : s.toCharArray()){
            if (!readingData && WHITESPACE_CHARS.contains(ch)){
                continue;
            } else if (ch == '{'){
                queue.addLast(ch);
            } else if (ch == '}'){
                queue.removeLast();
            } else if (ch == '"'){
                readingData = !readingData;
                queue.addLast(ch);
            } else if (queue.peekLast() == '"')
            System.out.println("");
        }
    }

    public static void main(String[] args) throws IOException {
        JsonObject json = JsonObject.getInstance("in.pks.journal.java.io/src/main/resources/menu.json");
    }

}
