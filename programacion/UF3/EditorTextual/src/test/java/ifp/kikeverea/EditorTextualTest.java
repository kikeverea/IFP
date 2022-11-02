package ifp.kikeverea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class EditorTextualTest {

    private static final String FILE_NAME = "nuevo.txt";
    private static final String DIRECTORY_NAME = "nueva_carpeta";

    private final File file = new File(FILE_NAME);
    private final File directory = new File(DIRECTORY_NAME);

    @BeforeEach
    void create() {
        EditorTextual.crearFichero(file);
        EditorTextual.crearCarpeta(directory);
    }

    @AfterEach
    void delete() {
        EditorTextual.borrarFichero(file);
        EditorTextual.borrarCarpeta(directory);
    }

    @Test
    void givenFile_createFile_fileExists() {
        File created = new File(FILE_NAME);
        Assertions.assertTrue(created.exists());
        Assertions.assertEquals(0, created.length());
    }

    @Test
    void givenExistingFile_createFile_overwriteFile() {
        for(int i = 0; i < 3; i++)
            EditorTextual.editarFichero(file, String.valueOf(i));

        Assertions.assertTrue(file.length() > 0);

        boolean created = EditorTextual.crearFichero(file);
        Assertions.assertTrue(created);
        Assertions.assertEquals(0, file.length());
    }

    @Test
    void givenFile_createDirectory_directoryExists() {
        File created = new File(DIRECTORY_NAME);
        Assertions.assertTrue(created.exists());
        Assertions.assertEquals(0, created.length());
    }

    @Test
    void givenExistingDirectory_createDirectory_creationFails() {
        File directory = new File(DIRECTORY_NAME);
        Assertions.assertTrue(directory.exists());

        boolean created = EditorTextual.crearCarpeta(directory);
        Assertions.assertFalse(created);
        Assertions.assertTrue(directory.exists());
    }

    @Test
    void givenFile_editFileWithContent_contentIsAddedAtTheEndOfTheFile() throws IOException {
        StringBuilder expected = new StringBuilder();
        int editTimes = random(5, 10);

        for(int i = 0; i < editTimes; i++) {
            String content = "new content " + i;
            EditorTextual.editarFichero(file, content);

            if(expected.length() > 0)
                expected.append("\n");

            expected.append(content);

            Assertions.assertTrue(equalContents(expected.toString(), file));
        }
    }

    @Test
    void givenFile_deleteFile_fileDoesNotExist() {
        EditorTextual.borrarFichero(file);
        Assertions.assertFalse(file.exists());
    }

    @Test
    void givenFile_deleteDirectory_directoryDoesNotExist() {
        EditorTextual.borrarCarpeta(directory);
        Assertions.assertFalse(directory.exists());
    }

    private boolean equalContents(String expected, File file) throws IOException {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader))
        {
            StringBuilder content = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {

                if (content.length() > 0)
                    content.append("\n");

                content.append(line);
            }
            return expected.equals(content.toString());
        }
    }

    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
