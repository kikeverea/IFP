package ifp.kikeverea;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShellTestUtil {

    public static final String TEST_DIR = "test_results";
    
    public static String extractFileContent(File file) {
        StringBuilder builder = new StringBuilder();

        try(FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader))
        {
            String line;
            while((line = reader.readLine()) != null)
                builder.append(line);
        }
        catch (IOException e) {
            Assertions.fail();
        }

        return builder.toString();
    }

    public static File createAndPopulateFile(String fileName) {
        File file = new File(fileName);
        EditorTextual.crearFichero(file);
        EditorTextual.editarFichero(file, randomString());
        EditorTextual.editarFichero(file, randomString());
        EditorTextual.editarFichero(file, randomString());
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() > 0);
        return file;
    }

    public static String randomFile() {
        return TEST_DIR+"/"+randomString();
    }

    public static String randomString() {
        int length = random(5, 9);
        int a = 97;
        int z = 123;
        char[] chars = new char[length];

        for(int i = 0; i < length; i++)
            chars[i] = (char) (random(a, z));

        return new String(chars);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static class InputBuilder {
        private String option;
        private final List<String> input = new ArrayList<>();

        public InputBuilder option(int option) {
            this.option = String.valueOf(option);
            return this;
        }

        public InputBuilder addInput(String input) {
            this.input.add(input);
            return this;
        }

        public String build() {
            StringBuilder sb = new StringBuilder()
                    .append(option)
                    .append(System.lineSeparator());

            for(String input : this.input) {
                sb.append(input);
                sb.append(System.lineSeparator());
            }

            sb.append(0);
            sb.append(System.lineSeparator());

            return sb.toString();
        }
    }

    public static class OutputAnalyzer {

        private static final int USER_PROMPT_INDEX = 1;
        private static final int PROGRAM_RESULT_INDEX = 2;

        private final String[] output;
        private final List<String> prompts = new ArrayList<>();
        private String result;
        private boolean noPrompts;

        private OutputAnalyzer(String[] output) {
            this.output = output;
        }

        public static OutputAnalyzer with(String[] output) {
            return new OutputAnalyzer(output);
        }

        public OutputAnalyzer containsPrompt(String prompt) {
            prompts.add(prompt);
            return this;
        }

        public OutputAnalyzer containsResult(String result) {
            this.result = result;
            return this;
        }

        public OutputAnalyzer noPrompts() {
            noPrompts = true;
            return this;
        }

        public void analyze() {
            if(usesPrompts() && prompts.isEmpty() || result == null)
                throw new IllegalStateException("Set prompts and result before calling analyze()");

            assertPrompts();
            assertResult();
        }

        private boolean usesPrompts() {
            return !noPrompts;
        }

        private void assertPrompts() {
            if(noPrompts)
                return;

            for (String prompt : prompts)
                Assertions.assertTrue(output[USER_PROMPT_INDEX].contains(prompt));
        }

        private void assertResult() {
            Assertions.assertEquals(output[PROGRAM_RESULT_INDEX], result);
        }
    }
}
