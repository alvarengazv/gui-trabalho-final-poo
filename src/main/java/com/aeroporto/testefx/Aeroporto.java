package com.aeroporto.testefx;

import java.util.*;

public class Aeroporto {
    private Scanner sc = new Scanner(System.in);

    public static Queue<Aeronave> filaAeronavesDecolagemArquivo = new LinkedList<Aeronave>();
    public static Queue<Aeronave> filaAeronavesAterrissagemArquivo = new LinkedList<Aeronave>();

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

    private double qntTotalAeronavesSairam = 0;

    public static double tempoEsperaTotalTodasAeronavesSairam = 0;

    public static List<Aeronave> aeronavesCairam = new ArrayList<Aeronave>();

    static enum CompanhiaAerea {
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    static enum Clima {
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
        auxContQntAeronavesFilaDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        atualizarCombustivel();
        gerarAeronaves();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        imprimirInformacoes();

        System.out.println("-------------");
        System.out.println("Aviaoes saindo: ");
        aterrissagem();
        decolagem();

        somarTempoEspera();

        System.out.println("-------------");
        System.out.println("Avioes criticos: ");
        verificarCombustivelCritico();

        //sc.nextLine();
        clearConsole();

        imprimirInformacoes();
        imprimirSituacaoCombustivel();
    }

    public void simularMinutoArquivo() {
        auxContQntAeronavesFilaDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        atualizarCombustivel();
        lerAeronave();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        imprimirInformacoes();

        System.out.println("-------------");
        System.out.println("Aviaoes saindo: ");
        aterrissagem();
        decolagem();

        somarTempoEspera();

        System.out.println("-------------");
        System.out.println("Avioes criticos: ");
        verificarCombustivelCritico();

//        sc.nextLine();
        clearConsole();

        imprimirInformacoes();
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
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaAterrissagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        filaEscolhida.adicionarAeronave(aeronave);
    }

    public void adicionarAeronaveFilaDecolagem(Aeronave aeronave) {
        Pista pistaEscolhida = escolherPistaDecolagem();
        System.out.println("Pista escolhida: " + pistaEscolhida.getNome());
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaDecolagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        filaEscolhida.adicionarAeronave(aeronave);
    }

    public Pista escolherPistaAterrissagem() {
        if (pista1.quantidadeAeronavesAterrissagem() <= pista2.quantidadeAeronavesAterrissagem()) {
            return pista1;
        } else if (pista1.quantidadeAeronavesAterrissagem() > pista2.quantidadeAeronavesAterrissagem()) {
            return pista2;
        } else {
            return pista2;
        }
    }

    public Pista escolherPistaDecolagem() {
        if (auxContQntAeronavesFilaDecolagem < 3) {
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
            } else if (aeronave.getCombustivel() < 3) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
            }
        }

        List<Aeronave> filaCopy11 = new ArrayList<>(pista1.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy11) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista1.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 3) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                pista1.getFilaAterrissagem1().getFila().remove(aeronave);
            }
        }

        List<Aeronave> filaCopy12 = new ArrayList<>(pista1.getFilaAterrissagem2().getFila());
        for (Aeronave aeronave : filaCopy12) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista1.getFilaAterrissagem2().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 3) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                pista1.getFilaAterrissagem2().getFila().remove(aeronave);
            }
        }

        List<Aeronave> filaCopy21 = new ArrayList<>(pista2.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy21) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista2.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 3) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                pista2.getFilaAterrissagem1().getFila().remove(aeronave);
            }
        }
        List<Aeronave> filaCopy22 = new ArrayList<>(pista2.getFilaAterrissagem2().getFila());
        for (Aeronave aeronave : filaCopy22) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista2.getFilaAterrissagem2().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 3) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                pista2.getFilaAterrissagem2().getFila().remove(aeronave);
            }
        }
    }

    public void gerarAeronaves() {
        System.out.println("Gerando aeronaves...");

        gerarAeronaveAterrissagem();

        System.out.print("-------------");
        //sc.nextLine();

        gerarAeronaveDecolagem();

        System.out.println("Aeronaves geradas com sucesso!");
        System.out.println("-------------");
        //sc.nextLine();
        clearConsole();
    }

    private void gerarAeronaveAterrissagem() {
        Random random = new Random();

        int aeronavesAterrissagem = random.nextInt(13);
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

        int aeronavesAterrissagem = random.nextInt(13);

        if (filaAeronavesAterrissagemArquivo.size() < aeronavesAterrissagem) {
            aeronavesAterrissagem = filaAeronavesAterrissagemArquivo.size();
        }

        System.out.println("\nATERRISSAGEM : \nChegando " + aeronavesAterrissagem + " aeronaves para aterrissagem...");
        for (int i = 0; i < aeronavesAterrissagem; i++) {
            Aeronave aeronave = (Aeronave) filaAeronavesAterrissagemArquivo.peek();
            filaAeronavesAterrissagemArquivo.remove();
            System.out.println("\nAviao " + aeronave.getId() + " de aterrissagem.");
            adicionarAeronaveFilaAterrisagem(aeronave);
        }

        System.out.print("-------------");

        int aeronavesDecolagem = random.nextInt(9);

        if (filaAeronavesDecolagemArquivo.size() < aeronavesDecolagem) {
            aeronavesDecolagem = filaAeronavesDecolagemArquivo.size();
        }

        System.out.println("\nDECOLAGEM : \n" + "Chegando " + aeronavesDecolagem + " aeronaves para decolagem...");
        for (int i = 0; i < aeronavesDecolagem; i++) {
            Aeronave aeronave = (Aeronave) filaAeronavesDecolagemArquivo.peek();
            filaAeronavesDecolagemArquivo.remove();
            System.out.println("\nAviao " + aeronave.getId() + " de decolagem.");
            adicionarAeronaveFilaDecolagem(aeronave);
        }

        System.out.println("Aeronaves lidas com sucesso!");
        System.out.println("-------------");
//        sc.nextLine();
        clearConsole();
    }

    public boolean escolherFilaParaAterrissar(FilaDeEspera fila1, FilaDeEspera fila2) {
        List<Aeronave> filaPoucoCombustivel1 = new LinkedList<Aeronave>();
        List<Aeronave> filaPoucoCombustivel2 = new LinkedList<Aeronave>();

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
            return true;
        } else if (filaPoucoCombustivel1.isEmpty()) {
            return false;
        } else if (filaPoucoCombustivel2.isEmpty()) {
            return true;
        } else {
            filaPoucoCombustivel1.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());
            filaPoucoCombustivel2.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());

            if (filaPoucoCombustivel1.get(0).getCombustivel() < filaPoucoCombustivel2.get(0).getCombustivel()) {
                return true;
            } else if (filaPoucoCombustivel1.get(0).getCombustivel() > filaPoucoCombustivel2.get(0).getCombustivel()) {
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
                } else if (qntPassageirosEspeciais1 < qntPassageirosEspeciais2) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public void aterrissagem() {
        if (pista1.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1 da pista 1.");
        }
        if (pista1.getFilaAterrissagem2().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 2 da pista 1.");
        }
        if (!pista1.getFilaAterrissagem1().getFila().isEmpty() && !pista1.getFilaAterrissagem2().getFila().isEmpty()) {
            if (escolherFilaParaAterrissar(pista1.getFilaAterrissagem1(), pista1.getFilaAterrissagem2())) {
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista1.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                Aeronave a = acharAeronaveMenorCombustivel(pista1.getFilaAterrissagem1());
                if (a != null) {
                    System.out.println(
                            "Aeronave " + a.getId() + " aterrissando na pista 1 da fila 1.");
                    pista1.getFilaAterrissagem1().getFila().remove(a);
                    pista1.setQtdAterrissagensEmergenciais(pista1.getQtdAterrissagensEmergenciais() + 1);
                    pista1.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(pista1.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                }else {
                    System.out.println(
                            "Aeronave " + pista1.getFilaAterrissagem1().getFila().peek().getId()
                                    + " aterrissando na pista 1 da fila 1.");
                    pista1.getFilaAterrissagem1().removerAeronave();
                }aterrissagem1 = true;
                qntTotalAeronavesSairam++;
            } else {
                System.out.println(
                        "Aeronave " + pista1.getFilaAterrissagem2().getFila().peek().getId()
                                + " aterrissando na pista 1 da fila 2.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                Aeronave a = acharAeronaveMenorCombustivel(pista1.getFilaAterrissagem2());
                if (a != null) {
                    System.out.println(
                            "Aeronave " + a.getId() + " aterrissando na pista 1 da fila 1.");
                    pista1.getFilaAterrissagem2().getFila().remove(a);
                    pista1.setQtdAterrissagensEmergenciais(pista1.getQtdAterrissagensEmergenciais() + 1);
                    pista1.getFilaAterrissagem2().setQtdAterrissagensEmergenciais(pista1.getFilaAterrissagem2().getQtdAterrissagensEmergenciais() + 1);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                } else {
                    System.out.println(
                            "Aeronave " + pista1.getFilaAterrissagem1().getFila().peek().getId()
                                    + " aterrissando na pista 1 da fila 1.");
                    pista1.getFilaAterrissagem2().removerAeronave();
                }
                    aterrissagem1 = true;
                    qntTotalAeronavesSairam++;
            }
        }


        if (pista2.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1 da pista 2.");
        }
        if (pista2.getFilaAterrissagem2().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 2 da pista 2.");
        }
        if (!pista2.getFilaAterrissagem1().getFila().isEmpty() && !pista2.getFilaAterrissagem2().getFila().isEmpty()) {
            if (escolherFilaParaAterrissar(pista2.getFilaAterrissagem1(), pista2.getFilaAterrissagem2())) {
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista2.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                Aeronave a = acharAeronaveMenorCombustivel(pista2.getFilaAterrissagem1());

                if (a != null) {
                    pista2.getFilaAterrissagem1().getFila().remove(a);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    pista2.setQtdAterrissagensEmergenciais(pista2.getQtdAterrissagensEmergenciais() + 1);
                    pista2.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(pista2.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                    System.out.println(
                            "Aeronave " + a.getId() + " aterrissando na pista 2 da fila 1.");
                } else {
                    System.out.println(
                            "Aeronave " + pista2.getFilaAterrissagem1().getFila().peek().getId()
                                    + " aterrissando na pista 2 da fila 1.");
                    pista2.getFilaAterrissagem1().removerAeronave();
                }
                aterrissagem2 = true;
                qntTotalAeronavesSairam++;

            } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(
                            pista2.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                Aeronave a = acharAeronaveMenorCombustivel(pista2.getFilaAterrissagem2());
                if (a != null) {
                    pista2.getFilaAterrissagem2().getFila().remove(a);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    pista2.setQtdAterrissagensEmergenciais(pista2.getQtdAterrissagensEmergenciais() + 1);
                    pista2.getFilaAterrissagem2().setQtdAterrissagensEmergenciais(pista2.getFilaAterrissagem2().getQtdAterrissagensEmergenciais() + 1);
                    System.out.println("Aeronave " + a.getId() + " aterrissando na pista 2 da fila 2.");
                } else {
                    System.out.println(
                            "Aeronave " + pista2.getFilaAterrissagem2().getFila().peek().getId()
                                    + " aterrissando na pista 2 da fila 2.");
                    pista2.getFilaAterrissagem2().removerAeronave();
                }
                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
            }
        }

        if (pista3.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1.");
        } else {
            System.out.println(
                    "Aeronave " + pista3.getFilaAterrissagem1().getFila().peek().getId() + " aterrissando na pista 3.");
            somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaAterrissagem1().getFila().peek().getTempoEspera());
            Aeronave a = acharAeronaveMenorCombustivel(pista3.getFilaAterrissagem1());

            if (a != null) {
                pista3.getFilaAterrissagem1().getFila().remove(a);
                setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                pista3.setQtdAterrissagensEmergenciais(pista3.getQtdAterrissagensEmergenciais() + 1);
                pista3.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(pista3.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                System.out.println(
                        "Aeronave " + a.getId() + " aterrissando na pista 3.");
            } else {
                System.out.println(
                        "Aeronave " + pista3.getFilaAterrissagem1().getFila().peek().getId()
                                + " aterrissando na pista 3.");
                pista3.getFilaAterrissagem1().removerAeronave();
            }
            aterrissagem3 = true;
            qntTotalAeronavesSairam++;
        }
    }

    public Aeronave acharAeronaveMenorCombustivel(FilaDeEspera fila) {
        List<Aeronave> filaPoucoCombustivel = new LinkedList<Aeronave>();
        for (Aeronave a : fila.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel.add(a);
            }
        }

        if (filaPoucoCombustivel.isEmpty())
            return null;

        filaPoucoCombustivel.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());

        if(!clima.equals(Clima.Sol.toString()))
            filaPoucoCombustivel.sort((a1, a2) -> Boolean.compare(a1.getPassageiroEspecial(), a2.getPassageiroEspecial()));

        System.out.println("TESTETETESTSETESTs: ");
        System.out.println(filaPoucoCombustivel);

        return filaPoucoCombustivel.get(0);
    }

    public void decolagem() {
        if (aterrissagem1) {
            System.out.println("Nao e possivel fazer decolagem, a pista 1 esta em uso para aterrissagem.");
        } else {
            if (pista1.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                System.out.println(
                        "Aeronave " + pista1.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 1.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaDecolagem().getFila().peek().getTempoEspera());
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
                System.out.println(
                        "Aeronave " + pista2.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 2.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista2.getFilaDecolagem().getFila().peek().getTempoEspera());
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
                System.out.println(
                        "Aeronave " + pista3.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 3.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaDecolagem().getFila().peek().getTempoEspera());
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

    public double qntTotalAeronaves() {
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
        System.out.println("Tempo medio pista 1: " + pista1.recalcularTempoMedioEspera());
        System.out.println("Tempo medio da fila 1: " + pista1.getFilaAterrissagem1().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 2: " + pista1.getFilaAterrissagem2().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 3: " + pista1.getFilaDecolagem().tempoMedioDeEsperaFila());

        System.out.println("\nTempo medio pista 2: " + pista2.recalcularTempoMedioEspera());
        System.out.println("Tempo medio da fila 1: " + pista2.getFilaAterrissagem1().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 2: " + pista2.getFilaAterrissagem2().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 3: " + pista2.getFilaDecolagem().tempoMedioDeEsperaFila());

        System.out.println("\nTempo medio pista 3: " + pista3.recalcularTempoMedioEspera());
        System.out.println("Tempo medio da fila 1: " + pista3.getFilaAterrissagem1().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 3: " + pista3.getFilaDecolagem().tempoMedioDeEsperaFila());

        qntTotalAeronaves();

        if (tempoEsperaTotalTodasAeronaves() == 0 || qntTotalAeronavesSairam == 0) {
            System.out.println("\nTempo total de espera de todas as aeronaves: 0");
            System.out.println("Total de naves: 0");
            System.out.println("Tempo medio de espera de todas as aeronaves: 0");
        } else {
            System.out.println("\nTempo total de espera de todas as aeronaves: " + tempoEsperaTotalTodasAeronaves());
            System.out.println("Total de naves: " + qntTotalAeronaves());
            System.out.println("Tempo medio de espera de todas as aeronaves: " + tempoMedioTotal());
        }

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
        //sc.nextLine();
        pista2.imprimir();
        System.out.println("-------------");
        //sc.nextLine();
        pista3.imprimir();
        System.out.println("-------------");
        //sc.nextLine();
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
            e.printStackTrace();
        }
    }

    public int getMinutosSimulados(){
        return this.minutosSimulados;
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

        aeronavesCairam = new ArrayList<Aeronave>();
    }
}