package atividade2;

import java.io.*;
import java.util.*;

class Bairro {
    private String nome;
    private int casosConfirmados;
    private int obitos;
    private String data;

    public Bairro(String nome, int casosConfirmados, int obitos, String data) {
        this.nome = nome;
        this.casosConfirmados = casosConfirmados;
        this.obitos = obitos;
        this.data = data;
    }

    public double taxaDeCrescimento(int casosAnteriores) {
        if (casosAnteriores == 0) return 0; // Evita divisão por zero
        return ((double) (casosConfirmados - casosAnteriores) / casosAnteriores) * 100;
    }

    public double taxaDeLetalidade() {
        if (casosConfirmados == 0) return 0; // Evita divisão por zero
        return ((double) obitos / casosConfirmados) * 100;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getCasosConfirmados() {
        return casosConfirmados;
    }

    public int getObitos() {
        return obitos;
    }

    public String getData() {
        return data;
    }
}

class AnaliseCovid {
    private List<Bairro> bairros;

    public AnaliseCovid() {
        this.bairros = new ArrayList<>();
    }

    public void lerArquivo(String caminhoArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 4) {
                    String nome = partes[0];
                    int casosConfirmados = Integer.parseInt(partes[1]);
                    int obitos = Integer.parseInt(partes[2]);
                    String data = partes[3];
                    bairros.add(new Bairro(nome, casosConfirmados, obitos, data));
                } else {
                    System.out.println("Formato inválido na linha: " + linha);
                }
            }
        }
    }

    public void calcularEstatisticas() {
        // Implementar cálculo de estatísticas
        for (Bairro bairro : bairros) {
            // Exemplo de cálculo de taxa de crescimento (precisa de dados anteriores)
            int casosAnteriores = 0; // Substituir por dados reais
            double taxaCrescimento = bairro.taxaDeCrescimento(casosAnteriores);
            double taxaLetalidade = bairro.taxaDeLetalidade();
            System.out.println("Bairro: " + bairro.getNome() +
                    ", Taxa de Crescimento: " + taxaCrescimento +
                    "%, Taxa de Letalidade: " + taxaLetalidade + "%");
        }
    }

    public void identificarBairrosImpactados() {
        // Implementar identificação de bairros mais impactados
        // Exemplo: Ordenar por taxa de letalidade
        bairros.sort(Comparator.comparingDouble(Bairro::taxaDeLetalidade).reversed());
    }

    public void exibirResultados() {
        // Implementar exibição dos resultados
        System.out.println("Bairros mais impactados:");
        for (Bairro bairro : bairros) {
            System.out.println(bairro.getNome() + ": " + bairro.taxaDeLetalidade() + "% de letalidade");
        }
    }

    public void adicionarRegistro(String caminhoArquivo, Bairro bairro) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            bw.write(bairro.getNome() + "," + bairro.getCasosConfirmados() + "," + bairro.getObitos() + "," + bairro.getData());
            bw.newLine();
        }
    }
}

class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Visualizar estatísticas");
        System.out.println("2. Adicionar novo registro");
        System.out.println("3. Sair");
    }

    public int lerOpcao() {
        return scanner.nextInt();
    }

    public Bairro lerNovoRegistro() {
        scanner.nextLine(); // Consumir a nova linha pendente
        System.out.println("Digite o nome do bairro:");
        String nome = scanner.nextLine();
        System.out.println("Digite o número de casos confirmados:");
        int casosConfirmados = scanner.nextInt();
        System.out.println("Digite o número de óbitos:");
        int obitos = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha pendente
        System.out.println("Digite a data da análise (formato YYYY-MM-DD):");
        String data = scanner.nextLine();
        return new Bairro(nome, casosConfirmados, obitos, data);
    }
}

public class Main {
    public static void main(String[] args) {
        // Definindo o caminho do arquivo diretamente ou via argumento
        String caminhoArquivo = (args.length > 0) ? args[0] : "cidades.csv";

        AnaliseCovid analise = new AnaliseCovid();
        Menu menu = new Menu();

        try {
            analise.lerArquivo(caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        int opcao;
        do {
            menu.exibirMenu();
            opcao = menu.lerOpcao();
            switch (opcao) {
                case 1:
                    analise.calcularEstatisticas();
                    analise.identificarBairrosImpactados();
                    analise.exibirResultados();
                    break;
                case 2:
                    Bairro novoBairro = menu.lerNovoRegistro();
                    try {
                        analise.adicionarRegistro(caminhoArquivo, novoBairro);
                        System.out.println("Registro adicionado com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Erro ao adicionar registro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }
}
