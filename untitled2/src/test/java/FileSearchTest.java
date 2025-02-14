import org.example.Main;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileSearchTest {

    private static final String TEST_DIR = "test_directory";  // Diretório de teste
    private static final String TEST_FILE = "test_file.txt";  // Arquivo de teste


    private static final String TEST_FILE_2 = "test_file2.txt";  // Arquivo de teste


    @BeforeEach
    public void setUp() throws IOException {


        // Criação do diretório de teste
        Files.createDirectories(Paths.get(TEST_DIR));

        // Criação de um arquivo de teste
        File file = new File(TEST_DIR, TEST_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }



        // Escreve no arquivo de teste
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("walt Disney Duck donald");
            writer.newLine();
        }



    }

    @AfterEach
    public void tearDown() throws IOException {
        // Limpeza após os testes
        Files.walk(Paths.get(TEST_DIR))
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testSearchKeywordInFiles() throws IOException {

        Map<String,String>  arquivosEncontrados  =  Main.buscarArquivos(TEST_DIR,"walt Disney");

        // Verifica se a lista contém as linhas corretas
        assertEquals(1, arquivosEncontrados.size());
        assertTrue(PesquisaPalavra(arquivosEncontrados,"walt Disney"));

    }

    @Test
    public void testMain() throws IOException {

        String[] args = {TEST_DIR,"walt Disney"};
        assertTrue(Main.main(args));

    }


    public static boolean PesquisaPalavra(Map<String, String> map, String keyword) {

        for (Map.Entry<String, String> entry : map.entrySet()) {

            if (entry.getValue().contains(keyword)) {
                return true;
            }
        }
        return false;
    }


    @Test
    public void testSearchKeywordNotFound() throws IOException {
        Map<String,String>  arquivosEncontrados  = Main.buscarArquivos(TEST_DIR, "inexistente");
        assertTrue(arquivosEncontrados.isEmpty());
    }


}
