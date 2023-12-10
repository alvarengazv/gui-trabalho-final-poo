package com.aeroporto.interfaceFX;

import java.util.*;

public class Aeroporto {

    public static Queue<Aeronave> filaAeronavesDecolagemArquivo = new LinkedList<>();
    public static Queue<Aeronave> filaAeronavesAterrissagemArquivo = new LinkedList<>();

    public static Pista pista1;
    public static Pista pista2;
    public static Pista pista3;

    private String clima;

    private int qtdAterrissagemEmergencial;
    private int minutosSimulados;

    public static int idsAeronavesAterrissagem = 1;
    public static int idsAeronavesDecolagem = 2;

    public static boolean aterrissagem1 = false;
    public static boolean aterrissagem2 = false;
    public static boolean aterrissagem3 = false;

    public static int auxContQntAeronavesFilaDecolagem = 0;

    private int qntTotalAeronavesSairam = 0;

    public static double tempoEsperaTotalTodasAeronavesSairam = 0;

    public static List<Aeronave> aeronavesCairam = new ArrayList<>();
    public static List<Aeronave> aeronavesEmEmergencia = new ArrayList<>();
    public static Map<Aeronave, String> aeronavesSairam = new HashMap<>();
    public static int chegaramAterrissagem = 0;
    public static int chegaramDecolagem = 0;

    enum CompanhiaAerea {
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    enum Clima {
        Sol,
        Chuva,
        Neve,
        Tempestade,
        Nublado
    }

    public Aeroporto() {
        pista1 = new Pista("Pista 1", true);
        pista2 = new Pista("Pista 2", true);
        pista3 = new Pista("Pista 3", false);

        this.minutosSimulados = 0;
        clima = Clima.Sol.toString();
    }

    public void simularMinuto() {
        aeronavesCairam = new ArrayList<>();
        auxContQntAeronavesFilaDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;
        aeronavesSairam = new HashMap<>();

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        atualizarCombustivel();
        gerarAeronaves();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        imprimirInformacoes();

        distribuicaoAeronaves();

        System.out.println("-------------");
        System.out.println("Avioes saindo: ");
        aterrissagem();
        decolagem();

        somarTempoEspera();

        System.out.println("-------------");
        System.out.println("Avioes criticos: ");
        verificarCombustivelCritico();

        clearConsole();

        imprimirInformacoes();
        imprimirSituacaoCombustivel();
    }

    public void simularMinutoArquivo() {
        aeronavesCairam = new ArrayList<>();
        auxContQntAeronavesFilaDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;
        aeronavesSairam = new HashMap<>();

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        atualizarCombustivel();
        lerAeronave();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        imprimirInformacoes();

        distribuicaoAeronaves();

        System.out.println("-------------");
        System.out.println("Aviaoes saindo: ");
        aterrissagem();
        decolagem();

        somarTempoEspera();

        System.out.println("-------------");
        System.out.println("Avioes criticos: ");
        verificarCombustivelCritico();

        clearConsole();

        imprimirInformacoes();
        imprimirSituacaoCombustivel();
    }

    public void distribuicaoAeronaves(){
        if(pista1.quantidadeAeronavesAterrissagem() == 0){
            if(pista3.quantidadeAeronavesAterrissagem() <= 1) {
                if (pista3.quantidadeAeronaves() > 1 && pista1.quantidadeAeronavesDecolagem() == 0) {
                    pista1.getFilaDecolagem().adicionarAeronave(pista3.getFilaDecolagem().getFila().peek());
                    pista1.getFilaDecolagem().getFila().peek().setPista(pista1);
                    pista1.getFilaDecolagem().getFila().peek().setFila(pista1.getFilaDecolagem());
                    pista3.getFilaDecolagem().removerAeronave();
                }
            } else {
                pista1.getFilaAterrissagem1().adicionarAeronave(pista3.getFilaAterrissagem1().getFila().peek());
                pista1.getFilaAterrissagem1().getFila().peek().setPista(pista1);
                pista1.getFilaAterrissagem1().getFila().peek().setFila(pista1.getFilaAterrissagem1());
                pista3.getFilaAterrissagem1().removerAeronave();
            }
        }

        if(pista2.quantidadeAeronavesAterrissagem() == 0){
            if(pista3.quantidadeAeronavesAterrissagem() <= 1){
                if (pista3.quantidadeAeronaves() > 1 && pista2.quantidadeAeronavesDecolagem() == 0) {
                    pista2.getFilaDecolagem().adicionarAeronave(pista3.getFilaDecolagem().getFila().peek());
                    pista2.getFilaDecolagem().getFila().peek().setPista(pista2);
                    pista2.getFilaDecolagem().getFila().peek().setFila(pista2.getFilaDecolagem());
                    pista3.getFilaDecolagem().removerAeronave();
                }
            } else {
                pista2.getFilaAterrissagem1().adicionarAeronave(pista3.getFilaAterrissagem1().getFila().peek());
                pista2.getFilaAterrissagem1().getFila().peek().setPista(pista2);
                pista2.getFilaAterrissagem1().getFila().peek().setFila(pista2.getFilaAterrissagem1());
                pista3.getFilaAterrissagem1().removerAeronave();
            }
        }
    }

    public void mudarClima() {
        Random random = new Random();
        int valor = random.nextInt(60);

        clima = (valor < 10) ? Clima.Chuva.toString()
                : (valor < 20) ? Clima.Neve.toString()
                : (valor < 30) ? Clima.Tempestade.toString()
                : (valor < 40) ? Clima.Nublado.toString() : Clima.Sol.toString();
    }

    public void adicionarAeronaveFilaAterrisagem(Aeronave aeronave) {
        Pista pistaEscolhida = escolherPistaAterrissagem();
        System.out.println("Pista escolhida: " + pistaEscolhida.getNome());
        aeronave.setPista(pistaEscolhida);
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaAterrissagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        aeronave.setFila(filaEscolhida);
        filaEscolhida.adicionarAeronave(aeronave);
    }

    public void adicionarAeronaveFilaDecolagem(Aeronave aeronave) {
        Pista pistaEscolhida = escolherPistaDecolagem();
        System.out.println("Pista escolhida: " + pistaEscolhida.getNome());
        aeronave.setPista(pistaEscolhida);
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaDecolagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        aeronave.setFila(filaEscolhida);
        filaEscolhida.adicionarAeronave(aeronave);
    }

    public Pista escolherPistaAterrissagem() {
        if (pista1.quantidadeAeronavesAterrissagem() <= pista2.quantidadeAeronavesAterrissagem()) {
            return pista1;
        } else
            return pista2;
    }

    public Pista escolherPistaDecolagem() {
        if (auxContQntAeronavesFilaDecolagem < 3 && (pista1.quantidadeAeronaves() != 0 || pista2.quantidadeAeronaves() != 0)) {
            auxContQntAeronavesFilaDecolagem++;
            return pista3;
        } else {
            if (pista1.quantidadeAeronavesDecolagem() <= pista2.quantidadeAeronavesDecolagem()) {
                return pista1;
            } else if (pista1.quantidadeAeronavesDecolagem() > pista2.quantidadeAeronavesDecolagem()) {
                return pista2;
            } else {
                return pista2;
            }
        }
    }

    public int calcularAeronavesEmEsperaAterrissagem() {
        return pista1.quantidadeAeronavesAterrissagem() + pista2.quantidadeAeronavesAterrissagem()+ pista3.quantidadeAeronavesAterrissagem();
    }

    public int calcularAeronavesEmEsperaDecolagem() {
        return pista1.quantidadeAeronavesDecolagem() + pista2.quantidadeAeronavesDecolagem() + pista3.quantidadeAeronavesDecolagem();
    }

    public int calcularAeronavesEmEspera() {
        return pista1.quantidadeAeronaves() + pista2.quantidadeAeronaves() + pista3.quantidadeAeronaves();
    }

    private void verificarCombustivelCritico() {
        List<Aeronave> filaCopy3 = new ArrayList<>(pista3.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy3) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista3.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
            }
        }

        List<Aeronave> filaCopy11 = new ArrayList<>(pista1.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy11) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista1.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if(aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem() || (aeronave.getCombustivel() == 1 && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista3);
                    aeronave.setFila(pista3.getFilaAterrissagem1());
                    pista1.getFilaAterrissagem1().getFila().remove(aeronave);
                } else if(pista1.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1 && pista2.getQtdCombustivel1() == 0){
                    pista2.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista2);
                    aeronave.setFila(pista2.getFilaAterrissagem1());
                    pista1.getFilaAterrissagem1().getFila().remove(aeronave);
                }
            }
        }

        List<Aeronave> filaCopy12 = new ArrayList<>(pista1.getFilaAterrissagem2().getFila());
        for (Aeronave aeronave : filaCopy12) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista1.getFilaAterrissagem2().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if(aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem() || (aeronave.getCombustivel() == 1 && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista3);
                    aeronave.setFila(pista3.getFilaAterrissagem1());
                    pista1.getFilaAterrissagem2().getFila().remove(aeronave);
                } else if(pista1.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1 && pista2.getQtdCombustivel1() == 0){
                    pista2.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista2);
                    aeronave.setFila(pista2.getFilaAterrissagem1());
                    pista1.getFilaAterrissagem2().getFila().remove(aeronave);
                }
            }
        }

        List<Aeronave> filaCopy21 = new ArrayList<>(pista2.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy21) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista2.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if(aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem() || (aeronave.getCombustivel() == 1 && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista3);
                    aeronave.setFila(pista3.getFilaAterrissagem1());
                    pista2.getFilaAterrissagem1().getFila().remove(aeronave);
                } else if(pista2.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1 && pista1.getQtdCombustivel1() == 0){
                    pista1.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista1);
                    aeronave.setFila(pista1.getFilaAterrissagem1());
                    pista2.getFilaAterrissagem1().getFila().remove(aeronave);
                }
            }
        }
        List<Aeronave> filaCopy22 = new ArrayList<>(pista2.getFilaAterrissagem2().getFila());
        for (Aeronave aeronave : filaCopy22) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista2.getFilaAterrissagem2().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if(aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem() || (aeronave.getCombustivel() == 1 && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista3);
                    aeronave.setFila(pista3.getFilaAterrissagem1());
                    pista2.getFilaAterrissagem2().getFila().remove(aeronave);
                } else if(pista2.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1 && pista1.getQtdCombustivel1() == 0){
                    pista1.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista1);
                    aeronave.setFila(pista1.getFilaAterrissagem1());
                    pista2.getFilaAterrissagem2().getFila().remove(aeronave);
                }
            }
        }
    }

    public void gerarAeronaves() {
        System.out.println("Gerando aeronaves...");

        gerarAeronaveAterrissagem();

        System.out.print("-------------");

        gerarAeronaveDecolagem();

        System.out.println("Aeronaves geradas com sucesso!");
        System.out.println("-------------");
        clearConsole();
    }

    private void gerarAeronaveAterrissagem() {
        Random random = new Random();

        int aeronavesAterrissagem = random.nextInt(13);
        chegaramAterrissagem = aeronavesAterrissagem;

        System.out.println("\nATERRISSAGEM : \nChegando " + aeronavesAterrissagem + " aeronaves para aterrissagem...");

        for (int i = 0; i < aeronavesAterrissagem; i++) {
            System.out.println("\nAviao " + idsAeronavesAterrissagem + " de aterrissagem.");
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = random.nextInt(15) + 1;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = random.nextBoolean();

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdAterrissagem(idsAeronavesAterrissagem);
            idsAeronavesAterrissagem += 2;

            adicionarAeronaveFilaAterrisagem(aeronave);
        }
    }

    private void gerarAeronaveDecolagem() {
        Random random = new Random();

        int aeronavesDecolagem = random.nextInt(9);
        chegaramDecolagem = aeronavesDecolagem;

        System.out.println("\nDECOLAGEM : \n" + "Chegando " + aeronavesDecolagem + " aeronaves para decolagem...");

        for (int i = 0; i < aeronavesDecolagem; i++) {
            System.out.println("\nAviao " + idsAeronavesDecolagem + " de decolagem.");
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = 15;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = random.nextBoolean();

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdDecolagem(idsAeronavesDecolagem);
            idsAeronavesDecolagem += 2;

            adicionarAeronaveFilaDecolagem(aeronave);
        }
    }

    public void lerAeronave() {
        Random random = new Random();

        clearConsole();

        System.out.println("Gerando aeronaves...");

        if(!filaAeronavesAterrissagemArquivo.isEmpty()) {
            int aeronavesAterrissagem = random.nextInt(13);
            chegaramAterrissagem = aeronavesAterrissagem;

            if (filaAeronavesAterrissagemArquivo.size() < aeronavesAterrissagem) {
                aeronavesAterrissagem = filaAeronavesAterrissagemArquivo.size();
            }

            System.out.println("\nATERRISSAGEM : \nChegando " + aeronavesAterrissagem + " aeronaves para aterrissagem...");
            for (int i = 0; i < aeronavesAterrissagem; i++) {
                Aeronave aeronave = filaAeronavesAterrissagemArquivo.peek();
                filaAeronavesAterrissagemArquivo.remove();
                System.out.println("\nAviao " + (aeronave != null ? aeronave.getId() : 0) + " de aterrissagem.");
                adicionarAeronaveFilaAterrisagem(aeronave);
            }
        } else {
            chegaramAterrissagem = 0;
        }

        System.out.print("-------------");

        if(!filaAeronavesDecolagemArquivo.isEmpty()) {
            int aeronavesDecolagem = random.nextInt(9);
            chegaramDecolagem = aeronavesDecolagem;

            if (filaAeronavesDecolagemArquivo.size() < aeronavesDecolagem) {
                aeronavesDecolagem = filaAeronavesDecolagemArquivo.size();
            }

            System.out.println("\nDECOLAGEM : \n" + "Chegando " + aeronavesDecolagem + " aeronaves para decolagem...");
            for (int i = 0; i < aeronavesDecolagem; i++) {
                Aeronave aeronave = filaAeronavesDecolagemArquivo.peek();
                filaAeronavesDecolagemArquivo.remove();
                System.out.println("\nAviao " + (aeronave != null ? aeronave.getId() : 0) + " de decolagem.");
                adicionarAeronaveFilaDecolagem(aeronave);
            }
        } else {
            chegaramDecolagem = 0;
        }

        System.out.println("Aeronaves lidas com sucesso!");
        System.out.println("-------------");
        clearConsole();
    }

    public boolean escolherFilaParaAterrissar(FilaDeEspera fila1, FilaDeEspera fila2) {
        List<Aeronave> filaPoucoCombustivel1 = new LinkedList<>();
        List<Aeronave> filaPoucoCombustivel2 = new LinkedList<>();

        for (Aeronave a : fila1.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel1.add(a);
            }
        }

        for (Aeronave a : fila2.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel2.add(a);
            }
        }

        if (filaPoucoCombustivel1.isEmpty() && filaPoucoCombustivel2.isEmpty()) {
            return !fila1.getFila().isEmpty();
        } else if (filaPoucoCombustivel1.isEmpty()) {
            return false;
        } else if (filaPoucoCombustivel2.isEmpty()) {
            return true;
        } else {
            filaPoucoCombustivel1.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());
            filaPoucoCombustivel2.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());

            if (filaPoucoCombustivel1.getFirst().getCombustivel() < filaPoucoCombustivel2.getFirst().getCombustivel()) {
                return true;
            } else if (filaPoucoCombustivel1.getFirst().getCombustivel() > filaPoucoCombustivel2.getFirst().getCombustivel()) {
                return false;
            } else {
                int qntPassageirosEspeciais1 = 0;
                int qntPassageirosEspeciais2 = 0;

                for (Aeronave a : filaPoucoCombustivel1) {
                    if (a.getPassageiroEspecial()) {
                        qntPassageirosEspeciais1++;
                    }
                }

                for (Aeronave a : filaPoucoCombustivel2) {
                    if (a.getPassageiroEspecial()) {
                        qntPassageirosEspeciais2++;
                    }
                }

                if (qntPassageirosEspeciais1 > qntPassageirosEspeciais2) {
                    return true;
                } else return qntPassageirosEspeciais1 >= qntPassageirosEspeciais2;
            }
        }
    }

    public void aterrissagem() {
        boolean passageiroEspecial = false;

        if (pista1.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1 da pista 1.");
        }
        if (pista1.getFilaAterrissagem2().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 2 da pista 1.");
        }
        if (!pista1.getFilaAterrissagem1().getFila().isEmpty() || !pista1.getFilaAterrissagem2().getFila().isEmpty()) {
            if (escolherFilaParaAterrissar(pista1.getFilaAterrissagem1(), pista1.getFilaAterrissagem2())) {
                Aeronave a = null;

                if(!clima.equals(Clima.Sol.toString())){
                    a = acharAeronaveComPassageiroEspecial(pista1.getFilaAterrissagem1());
                }

                if(a == null || (a.getCombustivel() > 1 && pista1.getFilaAterrissagem1().getMenorCombustivel() == 1)){
                    a = acharAeronaveMenorCombustivel(pista1.getFilaAterrissagem1());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    System.out.println("Aeronave " + a.getId() + " aterrissando na pista 1 da fila 1.");

                    pista1.getFilaAterrissagem1().getFila().remove(a);
                    pista1.setQtdAterrissagensEmergenciais(pista1.getQtdAterrissagensEmergenciais() + 1);
                    pista1.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(pista1.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);

                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista1.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(pista1.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    aeronavesSairam.put(a, passageiroEspecial ? "aterrissou com emergência por possuir passageiro com necessidades especiais" : "aterrissou com emergência por combustível baixo");
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                    pista1.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(pista1.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + pista1.getFilaAterrissagem1().getFila().peek().getTempoEspera());

                    aeronavesSairam.put(pista1.getFilaAterrissagem1().getFila().peek(), "aterrissou");

                    System.out.println("Aeronave " + pista1.getFilaAterrissagem1().getFila().peek().getId()+ " aterrissando na pista 1 da fila 1.");

                    pista1.getFilaAterrissagem1().removerAeronave();
                }
                pista1.getFilaAterrissagem1().setQtdAeronavesSairam(pista1.getFilaAterrissagem1().getQtdAeronavesSairam() + 1);

                aterrissagem1 = true;
                qntTotalAeronavesSairam++;
            } else {
                Aeronave a = null;

                if(!clima.equals(Clima.Sol.toString())){
                    a = acharAeronaveComPassageiroEspecial(pista1.getFilaAterrissagem2());
                }

                if(a == null || (a.getCombustivel() > 1 && pista1.getFilaAterrissagem2().getMenorCombustivel() == 1)){
                    a = acharAeronaveMenorCombustivel(pista1.getFilaAterrissagem2());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    System.out.println("Aeronave " + a.getId() + " aterrissando na pista 1 da fila 1.");

                    pista1.getFilaAterrissagem2().getFila().remove(a);
                    pista1.setQtdAterrissagensEmergenciais(pista1.getQtdAterrissagensEmergenciais() + 1);
                    pista1.getFilaAterrissagem2().setQtdAterrissagensEmergenciais(pista1.getFilaAterrissagem2().getQtdAterrissagensEmergenciais() + 1);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);

                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista1.getFilaAterrissagem2().setTempoEsperaAeronavesSairam(pista1.getFilaAterrissagem2().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    aeronavesSairam.put(a, passageiroEspecial ? "aterrissou com emergência por possuir passageiro com necessidades especiais" : "aterrissou com emergência por combustível baixo");
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                    pista1.getFilaAterrissagem2().setTempoEsperaAeronavesSairam(pista1.getFilaAterrissagem2().getTempoEsperaAeronavesSairam() + pista1.getFilaAterrissagem2().getFila().peek().getTempoEspera());

                    aeronavesSairam.put(pista1.getFilaAterrissagem2().getFila().peek(), "aterrissou");

                    System.out.println("Aeronave " + pista1.getFilaAterrissagem2().getFila().peek().getId() + " aterrissando na pista 1 da fila 1.");
                    pista1.getFilaAterrissagem2().removerAeronave();
                }
                pista1.getFilaAterrissagem2().setQtdAeronavesSairam(pista1.getFilaAterrissagem2().getQtdAeronavesSairam() + 1);

                aterrissagem1 = true;
                qntTotalAeronavesSairam++;
            }
        }

        passageiroEspecial = false;

        if (pista2.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1 da pista 2.");
        }
        if (pista2.getFilaAterrissagem2().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 2 da pista 2.");
        }
        if (!pista2.getFilaAterrissagem1().getFila().isEmpty() || !pista2.getFilaAterrissagem2().getFila().isEmpty()) {
            if (escolherFilaParaAterrissar(pista2.getFilaAterrissagem1(), pista2.getFilaAterrissagem2())) {
                Aeronave a = null;

                if(!clima.equals(Clima.Sol.toString())){
                    a = acharAeronaveComPassageiroEspecial(pista2.getFilaAterrissagem1());
                }

                if(a == null || (a.getCombustivel() > 1 && pista2.getFilaAterrissagem1().getMenorCombustivel() == 1)){
                    a = acharAeronaveMenorCombustivel(pista2.getFilaAterrissagem1());
                } else{
                    passageiroEspecial = true;
                }

                if (a != null) {
                    pista2.getFilaAterrissagem1().getFila().remove(a);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    pista2.setQtdAterrissagensEmergenciais(pista2.getQtdAterrissagensEmergenciais() + 1);
                    pista2.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(pista2.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                    System.out.println("Aeronave " + a.getId() + " aterrissando na pista 2 da fila 1.");

                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista2.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(pista2.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    aeronavesSairam.put(a, passageiroEspecial ? "aterrissou com emergência por possuir passageiro com necessidades especiais" : "aterrissou com emergência por combustível baixo");
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(pista2.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                    pista2.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(pista2.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + pista2.getFilaAterrissagem1().getFila().peek().getTempoEspera());

                    aeronavesSairam.put(pista2.getFilaAterrissagem1().getFila().peek(), "aterrissou");

                    System.out.println("Aeronave " + pista2.getFilaAterrissagem1().getFila().peek().getId()+ " aterrissando na pista 2 da fila 1.");
                    pista2.getFilaAterrissagem1().removerAeronave();
                }
                pista2.getFilaAterrissagem1().setQtdAeronavesSairam(pista2.getFilaAterrissagem1().getQtdAeronavesSairam() + 1);

                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
            } else {
                Aeronave a = null;

                if(!clima.equals(Clima.Sol.toString())){
                    a = acharAeronaveComPassageiroEspecial(pista2.getFilaAterrissagem2());
                }

                if(a == null || (a.getCombustivel() > 1 && pista2.getFilaAterrissagem2().getMenorCombustivel() == 1)){
                    a = acharAeronaveMenorCombustivel(pista2.getFilaAterrissagem2());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    pista2.getFilaAterrissagem2().getFila().remove(a);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    pista2.setQtdAterrissagensEmergenciais(pista2.getQtdAterrissagensEmergenciais() + 1);
                    pista2.getFilaAterrissagem2().setQtdAterrissagensEmergenciais(pista2.getFilaAterrissagem2().getQtdAterrissagensEmergenciais() + 1);
                    System.out.println("Aeronave " + a.getId() + " aterrissando na pista 2 da fila 2.");

                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista2.getFilaAterrissagem2().setTempoEsperaAeronavesSairam(pista2.getFilaAterrissagem2().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    aeronavesSairam.put(a, passageiroEspecial ? "aterrissou com emergência por possuir passageiro com necessidades especiais" : "aterrissou com emergência por combustível baixo");
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(pista2.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                    pista2.getFilaAterrissagem2().setTempoEsperaAeronavesSairam(pista2.getFilaAterrissagem2().getTempoEsperaAeronavesSairam() + pista2.getFilaAterrissagem2().getFila().peek().getTempoEspera());

                    aeronavesSairam.put(pista2.getFilaAterrissagem2().getFila().peek(), "aterrissou");

                    System.out.println("Aeronave " + pista2.getFilaAterrissagem2().getFila().peek().getId()+ " aterrissando na pista 2 da fila 2.");
                    pista2.getFilaAterrissagem2().removerAeronave();
                }
                pista2.getFilaAterrissagem2().setQtdAeronavesSairam(pista2.getFilaAterrissagem2().getQtdAeronavesSairam() + 1);

                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
            }
        }

        passageiroEspecial = false;

        if (pista3.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1.");
        } else {
            System.out.println("Aeronave " + pista3.getFilaAterrissagem1().getFila().peek().getId() + " aterrissando na pista 3.");
            Aeronave a = null;

            if(!clima.equals(Clima.Sol.toString())){
                a = acharAeronaveComPassageiroEspecial(pista3.getFilaAterrissagem1());
            }

            if(a == null || (a.getCombustivel() > 1 && pista3.getFilaAterrissagem1().getMenorCombustivel() == 1)){
                a = acharAeronaveMenorCombustivel(pista3.getFilaAterrissagem1());
            } else {
                passageiroEspecial = true;
            }

            if (a != null) {
                pista3.getFilaAterrissagem1().getFila().remove(a);
                setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                pista3.setQtdAterrissagensEmergenciais(pista3.getQtdAterrissagensEmergenciais() + 1);
                pista3.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(pista3.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                System.out.println("Aeronave " + a.getId() + " aterrissando na pista 3.");

                somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                pista3.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(pista3.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                aeronavesSairam.put(a, passageiroEspecial ? "aterrissou com emergência por possuir passageiro com necessidades especiais" : "aterrissou com emergência por combustível baixo");
            } else {
                somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                pista3.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(pista3.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + pista3.getFilaAterrissagem1().getFila().peek().getTempoEspera());

                aeronavesSairam.put(pista3.getFilaAterrissagem1().getFila().peek(), "aterrissou com emergência por combustível baixo");

                System.out.println("Aeronave " + pista3.getFilaAterrissagem1().getFila().peek().getId()+ " aterrissando na pista 3.");
                pista3.getFilaAterrissagem1().removerAeronave();
            }
            pista3.getFilaAterrissagem1().setQtdAeronavesSairam(pista3.getFilaAterrissagem1().getQtdAeronavesSairam() + 1);

            aterrissagem3 = true;
            qntTotalAeronavesSairam++;
        }
    }

    public Aeronave acharAeronaveMenorCombustivel(FilaDeEspera fila) {
        List<Aeronave> filaPoucoCombustivel = new LinkedList<>();
        for (Aeronave a : fila.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel.add(a);
            }
        }

        if (filaPoucoCombustivel.isEmpty())
            return null;

        filaPoucoCombustivel.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());

        return filaPoucoCombustivel.getFirst();
    }

    public Aeronave acharAeronaveComPassageiroEspecial(FilaDeEspera fila) {
        List<Aeronave> filaPassageirosEspeciais = new LinkedList<>();
        for (Aeronave a : fila.getFila()) {
            if (a.getPassageiroEspecial()) {
                filaPassageirosEspeciais.add(a);
            }
        }

        if (filaPassageirosEspeciais.isEmpty())
            return null;

        return filaPassageirosEspeciais.getFirst();
    }

    public void decolagem() {
        if (aterrissagem1) {
            System.out.println("Nao e possivel fazer decolagem, a pista 1 esta em uso para aterrissagem.");
        } else {
            if (pista1.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                aeronavesSairam.put(pista1.getFilaDecolagem().getFila().peek(), "decolou");

                System.out.println("Aeronave " + pista1.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 1.");

                somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista1.getFilaDecolagem().setQtdAeronavesSairam(pista1.getFilaDecolagem().getQtdAeronavesSairam() + 1);
                pista1.getFilaDecolagem().setTempoEsperaAeronavesSairam(pista1.getFilaDecolagem().getTempoEsperaAeronavesSairam() + pista1.getFilaDecolagem().getFila().peek().getTempoEspera());

                pista1.getFilaDecolagem().removerAeronave();
                pista1.getFilaDecolagem().setQtdAeronavesDecolaram(pista1.getFilaDecolagem().getQtdAeronavesDecolaram() + 1);
                qntTotalAeronavesSairam++;
            }
        }

        if (aterrissagem2) {
            System.out.println("Nao e possivel fazer decolagem, a pista 2 esta em uso para aterrissagem.");
        } else {
            if (pista2.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                aeronavesSairam.put(pista2.getFilaDecolagem().getFila().peek(), "decolou");

                System.out.println("Aeronave " + pista2.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 2.");

                somarTempoEsperaTotalTodasAeronavesSairam(pista2.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista2.getFilaDecolagem().setQtdAeronavesSairam(pista2.getFilaDecolagem().getQtdAeronavesSairam() + 1);
                pista2.getFilaDecolagem().setTempoEsperaAeronavesSairam(pista2.getFilaDecolagem().getTempoEsperaAeronavesSairam() + pista2.getFilaDecolagem().getFila().peek().getTempoEspera());

                pista2.getFilaDecolagem().removerAeronave();
                pista2.getFilaDecolagem().setQtdAeronavesDecolaram(pista2.getFilaDecolagem().getQtdAeronavesDecolaram() + 1);
                qntTotalAeronavesSairam++;
            }
        }

        if (aterrissagem3) {
            System.out.println("Nao e possivel fazer decolagem, a pista 3 esta em uso para aterrissagem.");
        } else {
            if (pista3.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                aeronavesSairam.put(pista3.getFilaDecolagem().getFila().peek(), "decolou");

                System.out.println("Aeronave " + pista3.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 3.");

                somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista3.getFilaDecolagem().setQtdAeronavesSairam(pista3.getFilaDecolagem().getQtdAeronavesSairam() + 1);
                pista3.getFilaDecolagem().setTempoEsperaAeronavesSairam(pista3.getFilaDecolagem().getTempoEsperaAeronavesSairam() + pista3.getFilaDecolagem().getFila().peek().getTempoEspera());

                pista3.getFilaDecolagem().removerAeronave();
                pista3.getFilaDecolagem().setQtdAeronavesDecolaram(pista3.getFilaDecolagem().getQtdAeronavesDecolaram() + 1);
                qntTotalAeronavesSairam++;
            }
        }

    }

    public void somarTempoEspera() {
        pista1.somarTempoEspera();
        pista2.somarTempoEspera();
        pista3.somarTempoEspera();
    }

    public void atualizarCombustivel() {
        pista1.atualizarCombustivel();
        pista2.atualizarCombustivel();
        pista3.atualizarCombustivel();
    }

    public void somarTempoEsperaTotalTodasAeronavesSairam(int tempoEspera) {
        tempoEsperaTotalTodasAeronavesSairam += tempoEspera;
    }

    public double tempoEsperaTotalTodasAeronavesAtuais() {
        return pista1.tempoEsperaTotalPista() + pista2.tempoEsperaTotalPista() + pista3.tempoEsperaTotalPista();
    }

    public double tempoEsperaTotalTodasAeronaves() {
        return tempoEsperaTotalTodasAeronavesAtuais() + tempoEsperaTotalTodasAeronavesSairam;
    }

    public int qntTotalAeronaves() {
        return pista1.quantidadeAeronaves() + pista2.quantidadeAeronaves() + pista3.quantidadeAeronaves() + qntTotalAeronavesSairam;
    }

    public double tempoMedioTotal() {
        if (qntTotalAeronaves() == 0) {
            return 0;
        } else {
            return tempoEsperaTotalTodasAeronaves() / qntTotalAeronaves();
        }
    }

    public void imprimirTempoMedioDeEspera() {
        System.out.println("Tempo medio pista 1: " + String.format("%.2f", pista1.recalcularTempoMedioEspera()));
        System.out.println("Tempo medio da fila 1: " + String.format("%.2f", pista1.getFilaAterrissagem1().tempoMedioDeEsperaFila()));
        System.out.println("Tempo medio da fila 2: " + String.format("%.2f", pista1.getFilaAterrissagem2().tempoMedioDeEsperaFila()));
        System.out.println("Tempo medio da fila 3: " + String.format("%.2f", pista1.getFilaDecolagem().tempoMedioDeEsperaFila()));

        System.out.println("\nTempo medio pista 2: " + String.format("%.2f", pista2.recalcularTempoMedioEspera()));
        System.out.println("Tempo medio da fila 1: " + String.format("%.2f", pista2.getFilaAterrissagem1().tempoMedioDeEsperaFila()));
        System.out.println("Tempo medio da fila 2: " + String.format("%.2f", pista2.getFilaAterrissagem2().tempoMedioDeEsperaFila()));
        System.out.println("Tempo medio da fila 3: " + String.format("%.2f", pista2.getFilaDecolagem().tempoMedioDeEsperaFila()));

        System.out.println("\nTempo medio pista 3: " + String.format("%.2f", pista3.recalcularTempoMedioEspera()));
        System.out.println("Tempo medio da fila 1: " + String.format("%.2f", pista3.getFilaAterrissagem1().tempoMedioDeEsperaFila()));
        System.out.println("Tempo medio da fila 3: " + String.format("%.2f", pista3.getFilaDecolagem().tempoMedioDeEsperaFila()));

        System.out.println("\nTempo total de espera de todas as aeronaves: " + String.format("%.2f", tempoEsperaTotalTodasAeronaves()));
        System.out.println("Total de naves: " + qntTotalAeronaves());
        System.out.println("Tempo medio de espera de todas as aeronaves: " + String.format("%.2f", tempoMedioTotal()));
    }

    public void imprimirInformacoes() {
        System.out.println("INFORMACOES: ");
        System.out.println("Aeronaves em espera: " + calcularAeronavesEmEspera());
        System.out.println("Aeronaves em espera para aterrissagem: " + calcularAeronavesEmEsperaAterrissagem());
        System.out.println("Aeronaves em espera para decolagem: " + calcularAeronavesEmEsperaDecolagem());
        System.out.println("Aeronaves que realizaram aterrissagem emergencial: " + qtdAterrissagemEmergencial);
        System.out.println("Minutos simulados: " + minutosSimulados);
        System.out.println("Clima: " + clima);

        System.out.println("\nTempo médio de espera...");
        imprimirTempoMedioDeEspera();

        System.out.println("-------------");

        System.out.println("INFORMACOES DAS PISTAS: ");
        imprimirPistas();
    }

    public void imprimirPistas() {
        pista1.imprimir();
        System.out.println("-------------");

        pista2.imprimir();
        System.out.println("-------------");

        pista3.imprimir();
        System.out.println("-------------");
    }

    public void imprimirSituacaoCombustivel() {
        System.out.println("COMBUSTIVEL CRITICO: ");
        pista1.getFilaAterrissagem1().verificarCombustivelCritico();
        pista1.getFilaAterrissagem2().verificarCombustivelCritico();
        pista1.getFilaDecolagem().verificarCombustivelCritico();
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Erro ao limpar o console: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getClima() {
        return clima;
    }

    public void setQntAterrissagemEmergencial(int qntAterrissagemEmergencial) {
        this.qtdAterrissagemEmergencial = qntAterrissagemEmergencial;
    }

    public int getQtdAterrissagemEmergencial(){
        return qtdAterrissagemEmergencial;
    }

    public int getQtdAeronavesEmergencia(){
        Aeroporto.aeronavesEmEmergencia = new ArrayList<>();
        return pista1.getQtdAeronavesEmergencia() + pista2.getQtdAeronavesEmergencia() + pista3.getQtdAeronavesEmergencia();
    }

    public static void reset() {
        pista1 = new Pista("Pista 1", true);
        pista2 = new Pista("Pista 2", true);
        pista3 = new Pista("Pista 3", false);

        idsAeronavesAterrissagem = 1;
        idsAeronavesDecolagem = 2;

        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;

        auxContQntAeronavesFilaDecolagem = 0;

        tempoEsperaTotalTodasAeronavesSairam = 0;

        aeronavesCairam = new ArrayList<>();
        aeronavesSairam = new HashMap<>();
    }
}