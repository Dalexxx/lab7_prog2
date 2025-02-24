package atividade1;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Distancia> distancias = Utils.lerArquivo("cidades.csv");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Digite opção de exibição com Streams:");
            System.out.println("1 - Simples");
            System.out.println("2 - Cinco Distâncias ímpares");
            System.out.println("3 - Ordem crescente");
            System.out.println("4 - Ordem decrescente (mostre apenas distâncias)");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    distancias.forEach(System.out::println);
                    break;
                case 2:
                    distancias.stream()
                            .filter(d -> (int) d.getDist() % 2 != 0)
                            .limit(5)
                            .forEach(System.out::println);
                    break;
                case 3:
                    distancias.stream()
                            .sorted()
                            .forEach(System.out::println);
                    break;
                case 4:
                    distancias.stream()
                            .sorted((d1, d2) -> Double.compare(d2.getDist(), d1.getDist()))
                            .map(Distancia::getDist)
                            .forEach(System.out::println);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}

