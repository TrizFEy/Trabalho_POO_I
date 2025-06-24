import java.io.*;
import java.util.*;

interface Jogavel {
    void iniciarJogo();
}

abstract class Pergunta {
    protected String pergunta;
    protected List<String> opcoes;
    protected int respostaCorreta;

    public Pergunta(String pergunta, List<String> opcoes, int respostaCorreta) {
        this.pergunta = pergunta;
        this.opcoes = new ArrayList<>(opcoes);
        this.respostaCorreta = respostaCorreta;
    }

    public boolean isCorreta(int resposta) {
        return resposta - 1 == respostaCorreta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public List<String> getOpcoes() {
        return new ArrayList<>(opcoes);
        entradas.sort(Comparator.comparingInt(Jogador::getPontuacao).reversed());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO_RANKING))) {
            for (Jogador j : entradas) {
                writer.write(j.getNome() + "," + j.getPontuacao());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar ranking no arquivo: " + e.getMessage());
        }
    }

    public void mostrar() {
        System.out.println("\n=== Ranking ===");
        if (entradas.isEmpty()) {
            System.out.println("Nenhum jogador no ranking ainda.");
        } else {
            for (int i = 0; i < Math.min(entradas.size(), 10); i++) {
                System.out.println((i + 1) + ". " + entradas.get(i));
            }
        }
    }
}

public class JOGO_2 implements Jogavel {
    private List<Pergunta> perguntas = new ArrayList<>();
    private Jogador jogador;
    private Ranking ranking = new Ranking();
    private Scanner scanner = new Scanner(System.in);

    private static final String NOME_ARQUIVO_PERGUNTAS = System.getProperty("user.dir") + File.separator + "perguntas.txt";

    public void carregarPerguntas() {
        perguntas.clear();
        File arquivo = new File(NOME_ARQUIVO_PERGUNTAS);
        System.out.println("Procurando perguntas em: " + arquivo.getAbsolutePath());

        if (!arquivo.exists()) {
            System.out.println("Arquivo de perguntas não encontrado. Criando arquivo de exemplo...");
            criarArquivoPerguntasExemplo();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                processarLinhaPergunta(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar perguntas: " + e.getMessage());
        }

        if (perguntas.isEmpty()) {
            System.out.println("Nenhuma pergunta válida foi carregada.");
        } else {
            System.out.println(perguntas.size() + " perguntas carregadas com sucesso.");
        }
    }

    private void processarLinhaPergunta(String linha) {
        String[] partes = linha.split(";");
        if (partes.length >= 7) {
            try {
                String perguntaTexto = partes[0].trim();
                List<String> opcoes = Arrays.asList(partes[1].trim(), partes[2].trim(), partes[3].trim(), partes[4].trim());
                int correta = Integer.parseInt(partes[5].trim());
                String dificuldade = partes[6].trim().toLowerCase();

                if (correta >= 0 && correta < opcoes.size()) {
                    if ("facil".equals(dificuldade)) {
                        perguntas.add(new PerguntaFacil(perguntaTexto, opcoes, correta));
                    } else if ("dificil".equals(dificuldade)) {
                        perguntas.add(new PerguntaDificil(perguntaTexto, opcoes, correta));
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar linha: " + linha);
            }
        }
    }

    private void criarArquivoPerguntasExemplo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO_PERGUNTAS))) {
            writer.write("Qual é a capital do Brasil?;Rio de Janeiro;Brasília;São Paulo;Salvador;1;facil");
            writer.newLine();
            writer.write("Quantos lados tem um triângulo?;2;3;4;5;1;facil");
            writer.newLine();
            writer.write("Quem pintou a Mona Lisa?;Van Gogh;Picasso;Da Vinci;Michelangelo;2;dificil");
            System.out.println("Arquivo de perguntas exemplo criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de exemplo: " + e.getMessage());
                    System.out.println("Número fora do intervalo. Tente novamente.");
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                    scanner.next();
                }
            }
            scanner.nextLine();

            if (p.isCorreta(respUsuario)) {
                System.out.println("Correto! +" + p.getPontuacao() + " pontos");
                jogador.incrementarPontuacao(p.getPontuacao());
                System.out.println("Pontuação atual: " + jogador.getPontuacao());
            } else {
                System.out.println("\nErrado! A resposta correta era: " +
                        (p.getRespostaCorretaIndex() + 1) + ") " +
                        p.getOpcoes().get(p.getRespostaCorretaIndex()));
                System.out.println("Fim de jogo para " + jogador.getNome() + ".");
                break;
            }
        }
        System.out.println("\nJogo finalizado! Sua pontuação final: " + jogador.getPontuacao());
    }

    public static void main(String[] args) {
        JOGO_2 jogo = new JOGO_2();
        jogo.iniciarJogo();
    }
}
