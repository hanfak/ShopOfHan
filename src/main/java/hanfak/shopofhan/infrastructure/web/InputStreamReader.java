package hanfak.shopofhan.infrastructure.web;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InputStreamReader {
    public static String readInputStream(InputStream inputStream) {
        // not reading body
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.toString()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}