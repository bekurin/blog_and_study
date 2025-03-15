import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
public class StringDigitExtractor {
    private final String input;

    public StringDigitExtractor(String input) {
        this.input = input;
    }

    public List<Integer> extractDigits() {
        log.info("Extracting digits for input {}", input);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            log.debug("Parsing character {} of input {}", input.charAt(i), input);
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                list.add(Integer.parseInt(String.valueOf(input.charAt(i))));
            }
        }

        log.info("Extract digits result for input {} is {}", input, list);
        return list;
    }
}
