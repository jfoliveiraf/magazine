package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static boolean main(String[] args)
    {

        String diretorio = args[0];   // parametro passado na execução - primeiro parametro
        String palavraChave =  args[1]; // parametro passado na execução  - segundo parametro

        Map<String,String>  arquivosEncontrados = buscarArquivos(diretorio, palavraChave);
        // Ordenando o HashMap pelas chaves em ordem alfabética usando Stream
        Map<String, String> sortedMap = arquivosEncontrados.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())  // Ordena pelas chaves
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));  // Usando LinkedHashMap para manter a ordem

        // Exibindo o HashMap ordenado
        sortedMap.forEach((key, value) -> System.out.println(key + ": " + value));
        return true;
    }

    public static Map<String,String>  buscarArquivos(String diretorio, String palavraChave) {
        Map<String,String> arquivosEncontrados = new HashMap<String,String>();

        File dir = new File(diretorio);

        if (dir.exists() && dir.isDirectory()) {
            File[] arquivos = dir.listFiles((d, nome) -> nome.toLowerCase().endsWith(".txt")); // Filtra arquivos .txt

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    try {
                        List<String> linhas = Files.readAllLines(Paths.get(arquivo.getPath()));
                        for (String linha : linhas) {
                            if (linha.contains(palavraChave)) {
                                arquivosEncontrados.put(arquivo.toString() , new String(linha));
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Erro ao ler o arquivo: " + arquivo.getName());
                    }
                }
            }
        } else {
            System.err.println("O diretório especificado não existe ou não é um diretório válido.");
        }

        return arquivosEncontrados;
    }

}