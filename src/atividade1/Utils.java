package atividade1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Distancia> lerArquivo(String nomeArquivo) {
        List<Distancia> distancias = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    String origem = partes[0].trim();
                    String destino = partes[1].trim();
                    double dist = Double.parseDouble(partes[2].trim());
                    distancias.add(new Distancia(origem, destino, dist));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return distancias;
    }
}
