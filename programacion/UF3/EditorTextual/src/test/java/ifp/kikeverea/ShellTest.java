package ifp.kikeverea;

import org.junit.jupiter.api.*;

import java.io.*;

import static ifp.kikeverea.EditorTextual.*;
import static ifp.kikeverea.ShellTestUtil.*;

public class ShellTest {

    private PrintStream systemOut;
    private InputStream systemIn;
    private ByteArrayOutputStream outputStream;

    @BeforeAll
    static void makeTestDir() {
        Assertions.assertTrue(new File(TEST_DIR).mkdir());
    }

    @BeforeEach
    void init() {
        systemOut = System.out;
        systemIn = System.in;
        outputStream = new ByteArrayOutputStream();
    }

    @AfterEach
    void clean() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @AfterAll
    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void deleteTestDir() {
        File testDir = new File("test_results");
        File[] files = testDir.listFiles();

        if(files != null)
            for (File file : files)
                file.delete();

        testDir.delete();
    }

    @Test
    void callCreateOption_withFileName_fileWithGivenNameIsCreated() {
        File file = new File(randomFile());
        String[] output = runWithInput(new InputBuilder()
                .option(CREAR_FICHERO)
                .addInput(file.getPath())
                .build());

        OutputAnalyzer.with(output)
                    .containsPrompt("Introduzca el nombre del nuevo fichero: ")
                    .containsResult("Fichero creado correctamente")
                    .analyze();

        Assertions.assertTrue(file.exists());
    }

    @Test
    void callCreateOption_withExistingFileName_acceptOverwrite_fileOverwritten() {
        File file = createAndPopulateFile(randomFile());

        String[] output = runWithInput(new InputBuilder()
                .option(CREAR_FICHERO)
                .addInput(file.getPath())
                .addInput("si")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del nuevo fichero: ")
                .containsPrompt("Fichero existente ¿Desea sobrescribirlo? ")
                .containsResult("Fichero creado correctamente")
                .analyze();

        Assertions.assertEquals(0, file.length());
    }

    @Test
    void callCreateOption_withExistingFileName_denyOverwrite_fileUnchanged() {
        File file = createAndPopulateFile(randomFile());
        long fileLength = file.length();

        String[] output = runWithInput(new InputBuilder()
                .option(CREAR_FICHERO)
                .addInput(file.getPath())
                .addInput("no")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del nuevo fichero: ")
                .containsPrompt("Fichero existente ¿Desea sobrescribirlo? ")
                .containsResult("El fichero no se ha sobreescrito")
                .analyze();

        Assertions.assertEquals(fileLength, file.length());
    }

    @Test
    void callEditOption_withContent_contentAppendedToFile() {
        File file = createAndPopulateFile(randomFile());

        long fileLength = file.length();
        String newLine = randomString();

        String[] output = runWithInput(new InputBuilder()
                .option(EDITAR_FICHERO)
                .addInput(file.getPath())
                .addInput(newLine)
                .build());

        String editedFileContent = extractFileContent(file);
        long editedFileLength = file.length();

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del fichero a editar: ")
                .containsPrompt("Introduzca el texto que desea añadir al fichero: ")
                .containsResult("Documento editado correctamente")
                .analyze();

        Assertions.assertTrue(editedFileLength > fileLength);
        Assertions.assertTrue(editedFileContent.contains(newLine));
    }

    @Test
    void callEditOption_withNotExistingFileName_editFail() {
        String[] output = runWithInput(new InputBuilder()
                .option(EDITAR_FICHERO)
                .addInput("does_not_exist")
                .addInput("this never gets appended")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del fichero a editar: ")
                .containsResult("Fichero Inexistente")
                .analyze();
    }

    @Test
    void callDeleteOption_withFileName_fileNoLongerExists() {
        File file = createAndPopulateFile(randomFile());
        String[] output = runWithInput(new InputBuilder()
                .option(BORRAR_FICHERO)
                .addInput(file.getPath())
                .addInput("S")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del fichero a borrar: ")
                .containsPrompt("¿Está seguro de que desea borrarlo (Afirmación S)? ")
                .containsResult("Fichero eliminado correctamente")
                .analyze();

        Assertions.assertFalse(file.exists());
    }

    @Test
    void callDeleteOption_withFileName_cancelDelete_fileExists() {
        File file = createAndPopulateFile(randomFile());
        String[] output = runWithInput(new InputBuilder()
                .option(BORRAR_FICHERO)
                .addInput(file.getPath())
                .addInput("N")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del fichero a borrar: ")
                .containsPrompt("¿Está seguro de que desea borrarlo (Afirmación S)? ")
                .containsResult("El fichero no se borró del sistema")
                .analyze();

        Assertions.assertTrue(file.exists());
    }

    @Test
    void callDeleteOption_withNotExistingFileName_deleteFail() {
        String[] output = runWithInput(new InputBuilder()
                .option(BORRAR_FICHERO)
                .addInput("does_not_exist")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre del fichero a borrar: ")
                .containsResult("Fichero Inexistente")
                .analyze();
    }

    @Test
    void callMakeDirOption_withDirName_directoryWithGivenNameIsCreated() {
        File file = new File(randomFile());
        String[] output = runWithInput(new InputBuilder()
                .option(CREAR_CARPETA)
                .addInput(file.getPath())
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre de la carpeta a crear: ")
                .containsResult("Carpeta creada correctamente")
                .analyze();

        Assertions.assertTrue(file.exists() && file.isDirectory());
    }

    @Test
    void callMakeDirOption_withExistingDirName_directoryWithGivenNameIsCreated() {
        File file = new File(randomFile());
        EditorTextual.crearCarpeta(file);

        String[] output = runWithInput(new InputBuilder()
                .option(CREAR_CARPETA)
                .addInput(file.getPath())
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre de la carpeta a crear: ")
                .containsResult("Carpeta ya existente")
                .analyze();
    }

    @Test
    void callDeleteDirOption_withExistingDirName_directoryNoLongerExists() {
        File file = new File(randomFile());
        EditorTextual.crearCarpeta(file);

        String[] output = runWithInput(new InputBuilder()
                .option(BORRAR_CARPETA)
                .addInput(file.getPath())
                .addInput("S")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre de la carpeta a borrar: ")
                .containsPrompt("¿Está seguro de que desea borrar la carpeta (Afirmación S)? ")
                .containsResult("Carpeta borrada correctamente")
                .analyze();
    }

    @Test
    void callDeleteDirOption_withExistingDirName_cancelDelete_directoryExists() {
        File file = new File(randomFile());
        EditorTextual.crearCarpeta(file);

        String[] output = runWithInput(new InputBuilder()
                .option(BORRAR_CARPETA)
                .addInput(file.getPath())
                .addInput("N")
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre de la carpeta a borrar: ")
                .containsPrompt("¿Está seguro de que desea borrar la carpeta (Afirmación S)? ")
                .containsResult("La carpeta no se borró del sistema")
                .analyze();
    }

    @Test
    void callDeleteDirOption_withNonExistingDirName_deleteFails() {
        File file = new File(randomFile());

        String[] output = runWithInput(new InputBuilder()
                .option(BORRAR_CARPETA)
                .addInput(file.getPath())
                .build());

        OutputAnalyzer.with(output)
                .containsPrompt("Introduzca el nombre de la carpeta a borrar: ")
                .containsResult("La carpeta no existe")
                .analyze();
    }

    @Test
    void callExitOption_exitProgram() {
        String[] output = runWithInput(new InputBuilder()
                .option(SALIR)
                .build());

        Assertions.assertTrue(output[1].contains("Programa finalizado"));
    }

    @SuppressWarnings("unused")
    public void printLines(String[] lines) {
        System.setOut(systemOut);

        int i = 0;
        for(String line : lines)
            System.out.println(i++ + " " + line);
    }

    private String[] runWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));

        EditorTextual.main(null);

        return outputStream.toString().split(System.lineSeparator());
    }
}
