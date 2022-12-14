package ifp.kikeverea;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class RandomUtil {

    public static int random(int max) {
        return random(0, max);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static float randomFloat(int max, int decimalPlaces) {
        float random = ThreadLocalRandom.current().nextFloat() * max;
        return Math.round(random * decimalPlaces) / (float) decimalPlaces;
    }

    public static String random_n_String(int min, int max) {
        return String.valueOf(random(min, max));
    }

    public static String random_n_String(String prefix, int max) {
        return random_n_String(prefix, max, "");
    }

    public static String random_n_String(int max, String suffix) {
        return random_n_String("", max, suffix);
    }

    public static String random_n_String(String prefix, int max, String suffix) {
        prefix = prefix == null ? "" : prefix;
        suffix = suffix == null ? "" : suffix;

        return prefix + random(max) + suffix;
    }

    public static String[] randomStrings(int count) {
        return Stream
                .generate(RandomUtil::randomString)
                .limit(count)
                .toArray(String[]::new);
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
}
