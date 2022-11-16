package ifp.kikeverea;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassTestUtil {

    private final Set<Field> declaredFields;
    private final Set<String> declaredMethods;

    public ClassTestUtil(Class<?> target) {
        declaredFields = Arrays.stream(target.getDeclaredFields())
                        .filter(field -> !Modifier.isStatic(field.getModifiers()))
                        .collect(Collectors.toSet());

        declaredMethods = Arrays.stream(target.getDeclaredMethods())
                        .map(Method::getName)
                        .collect(Collectors.toSet());
    }

    public boolean allFieldsPresent(Set<String> fieldsToCheck) {
        return declaredFields
                .stream()
                .map(Field::getName)
                .filter(fieldsToCheck::contains)
                .count() == fieldsToCheck.size();
    }

    public boolean allFieldsHaveGettersAndSetters() {
        return declaredFields
                .stream()
                .filter(this::hasGetterAndSetter)
                .count() == declaredFields.size();
    }

    public boolean methodPresent(String method) {
        return declaredMethods.contains(method);
    }

    private boolean hasGetterAndSetter(Field field) {
        String name = field.getName();
        String camelCaseName = name.replaceFirst(".", name.substring(0, 1).toUpperCase());
        String getter = isBoolean(field) ? "is"+camelCaseName : "get"+camelCaseName;
        String setter = "set"+camelCaseName;

//        System.out.println("Has getter and setter: " + field.getName());
//        System.out.println("Getter: " + getter);
//        System.out.println("Setter: " + setter);
//        System.out.println(declaredMethods);
//        System.out.println("In declared methods: " +
//                (declaredMethods.contains(getter) && declaredMethods.contains(setter)));
//        System.out.println();

        return declaredMethods.contains(getter) && declaredMethods.contains(setter);
    }

    private boolean isBoolean(Field field) {
        Class<?> type = field.getType();
        return type.equals(Boolean.class) || type.equals(boolean.class);
    }
}