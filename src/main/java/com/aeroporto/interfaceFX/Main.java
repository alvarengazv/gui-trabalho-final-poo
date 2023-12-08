package com.aeroporto.interfaceFX;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static Aeroporto aeroporto = new Aeroporto();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int escolha = 0;

        while (escolha != 3) {

            menuInicial();
            escolha = scanner.nextInt();
            scanner.nextLine();
            aeroporto.clearConsole();

            switch (escolha) {
                case 1:
                    iniciar(false);
                    break;
                case 2:
                    iniciar(true);
                    break;
                case 3:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }
    }

    public static void menuInicial() {
        System.out.println("Escolha uma opcao:");
        System.out.println("1 - Geração aleatória de aeronaves");
        System.out.println("2 - Leitura de arquivo de aeronaves");
        System.out.println("3 - Sair");
    }

    public static void iniciar(boolean arquivo) {
        if (arquivo) {
            leituraArquivoAeronaves(new File("src/main/java/com/aeroporto/interfaceFX/aeronaves.txt"));
        } else {
            aeronavesAleatorias();
        }
    }

    public static void linhaAeronave(Scanner arqScanner) {
        String[] aviaoArrayString = arqScanner.nextLine().split(":");

        int tipo = Integer.parseInt(aviaoArrayString[0]);
        int numPassageiros = Integer.parseInt(aviaoArrayString[1]);
        int combustivel = Integer.parseInt(aviaoArrayString[2]);
        String companhiaAerea = aviaoArrayString[3];
        boolean passageiroEspecial = Integer.parseInt(aviaoArrayString[4]) == 1;

        Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);

        if (tipo == 0) {
            aeronave.setIdAterrissagem(Aeroporto.idsAeronavesAterrissagem);
            Aeroporto.idsAeronavesAterrissagem += 2;

            Aeroporto.filaAeronavesAterrissagemArquivo.add(aeronave);
        } else {
            aeronave.setIdDecolagem(Aeroporto.idsAeronavesDecolagem);
            Aeroporto.idsAeronavesDecolagem += 2;

            Aeroporto.filaAeronavesDecolagemArquivo.add(aeronave);
        }
    }

    public static void leituraArquivoAeronaves(File arquivo) {
        System.out.println("Lendo arquivo de aeronaves.");
        try {
            Scanner arqScanner = new Scanner(arquivo);

            while (arqScanner.hasNextLine()) {
                linhaAeronave(arqScanner);
            }

            System.out.println("Arquivo lido com sucesso.");

            arqScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void aeronavesAleatorias() {
        while (true) {
            System.out.println("Aperte enter para simular um minuto. Digite 0 para sair.");
            String enter = scanner.nextLine();

            if (enter.equals("0")) {
                System.out.println("Saindo da função aleatória...");
                return;
            } else {
                aeroporto.simularMinuto();
            }
        }
    }
}