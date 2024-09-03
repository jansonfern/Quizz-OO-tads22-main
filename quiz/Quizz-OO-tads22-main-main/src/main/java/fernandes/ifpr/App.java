package fernandes.ifpr;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private ControladorQuiz controladorQuiz;
    private VBox root;
    private Scene cena;
    private Text enunciado;
    private Button alternativa1;
    private Button alternativa2;
    private Button alternativa3;
    private Button alternativa4;
    private Button alternativa5;
    private Text resultado;
    private Button proxima;
    private Button reiniciar;
    private Button iniciarJogo;
    private Button cadastrarPerguntas;
    private boolean respostaDada = false;

    @Override
    public void init() throws Exception {
        super.init();

        ArrayList<Questao> lista = new ArrayList<>();
        lista.add(new Questao("Qual é a capital de São Paulo?", "São Paulo",
                new String[] { "São Paulo", "Rio de Janeiro", "Brasília", "Belo Horizonte", "Curitiba" }));
        lista.add(new Questao("Qual é a capital do Paraná?", "Curitiba",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));
        lista.add(new Questao("Qual é a capital do Rio de Janeiro?", "Rio de Janeiro",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));
        lista.add(new Questao("Qual é a capital de Minas Gerais?", "Belo Horizonte",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));
        lista.add(new Questao("Qual é a capital do Brasil?", "Brasília",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));

        controladorQuiz = new ControladorQuiz(lista);
    }

    @Override
    public void start(Stage stage) throws Exception {
        iniciarJogo = new Button("INICIAR JOGO");
        cadastrarPerguntas = new Button("CADASTRAR PERGUNTAS");

        inicializaComponentes();
        atualizaComponentes();

        cena = new Scene(root, 500, 500);
        cena.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(cena);
        stage.show();
    }

    private void inicializaComponentes() {
        enunciado = new Text("Enunciado");
        enunciado.getStyleClass().add("enunciado");

        alternativa1 = criarBotaoAlternativa("Questão 1");
        alternativa2 = criarBotaoAlternativa("Questão 2");
        alternativa3 = criarBotaoAlternativa("Questão 3");
        alternativa4 = criarBotaoAlternativa("Questão 4");
        alternativa5 = criarBotaoAlternativa("Questão 5");

        resultado = new Text("Resultado");
        proxima = new Button("Próxima");
        reiniciar = new Button("Reiniciar");

        VBox botoesInicio = new VBox(10, iniciarJogo, cadastrarPerguntas);
        botoesInicio.setAlignment(Pos.CENTER);

        root = new VBox(20);
        root.getChildren().add(botoesInicio);
        root.getChildren().add(enunciado);
        root.getChildren().add(alternativa1);
        root.getChildren().add(alternativa2);
        root.getChildren().add(alternativa3);
        root.getChildren().add(alternativa4);
        root.getChildren().add(alternativa5);
        root.getChildren().add(resultado);
        root.getChildren().add(proxima);
        root.getChildren().add(reiniciar);

        root.setAlignment(Pos.CENTER);
        root.setSpacing(10.0);

        configurarEventos();

        esconderComponentesDoQuiz();
    }

    private Button criarBotaoAlternativa(String texto) {
        Button botao = new Button(texto);
        botao.setPrefWidth(200);
        botao.getStyleClass().add("botao");
        botao.setTooltip(new Tooltip("Clique para responder..."));
        return botao;
    }

    private void configurarEventos() {
        iniciarJogo.setOnAction(e -> iniciarJogo());
        cadastrarPerguntas.setOnAction(e -> cadastrarPerguntas());
        alternativa1.setOnAction(e -> respondeQuestao(alternativa1));
        alternativa2.setOnAction(e -> respondeQuestao(alternativa2));
        alternativa3.setOnAction(e -> respondeQuestao(alternativa3));
        alternativa4.setOnAction(e -> respondeQuestao(alternativa4));
        alternativa5.setOnAction(e -> respondeQuestao(alternativa5));
        proxima.setOnAction(e -> proximaQuestao());
        reiniciar.setOnAction(e -> reiniciarQuiz());
    }

    private void esconderComponentesDoQuiz() {
        enunciado.setVisible(false);
        alternativa1.setVisible(false);
        alternativa2.setVisible(false);
        alternativa3.setVisible(false);
        alternativa4.setVisible(false);
        alternativa5.setVisible(false);
        resultado.setVisible(false);
        proxima.setVisible(false);
        reiniciar.setVisible(false);
    }

    private void iniciarJogo() {
        enunciado.setVisible(true);
        alternativa1.setVisible(true);
        alternativa2.setVisible(true);
        alternativa3.setVisible(true);
        alternativa4.setVisible(true);
        alternativa5.setVisible(true);
        resultado.setVisible(false);
        proxima.setVisible(false);
        reiniciar.setVisible(false);
        atualizaComponentes();
        iniciarJogo.setVisible(false);
        cadastrarPerguntas.setVisible(false);
    }

    private void cadastrarPerguntas() {
        System.out.println("Cadastro de perguntas n implementado.");
    }
    private void atualizaComponentes() {
        Questao objQuestao = controladorQuiz.getQuestao();
        if (objQuestao != null) {
            List<String> questoes = objQuestao.getTodasAlternativas();

            enunciado.setText(objQuestao.getEnunciado());
            alternativa1.setText(questoes.get(0));
            alternativa2.setText(questoes.get(1));
            alternativa3.setText(questoes.get(2));
            alternativa4.setText(questoes.get(3));
            alternativa5.setText(questoes.get(4));

            resultado.setVisible(false);
            proxima.setVisible(respostaDada);
            reiniciar.setVisible(false);
        }
    }

    private void respondeQuestao(Button botao) {
        String alternativa = botao.getText();
        boolean result = controladorQuiz.respondeQuestao(alternativa);
        resultado.setText(result ? "Acertou!!" : "Errou!!!");
        resultado.setVisible(true);
        respostaDada = true;
        proxima.setVisible(true);
    }

    private void proximaQuestao() {
        if (controladorQuiz.temProximaQuestao()) {
            controladorQuiz.proximaQuestao();
            atualizaComponentes();
            respostaDada = false;
        } else {
            resultado.setText(String.format("Fim, você %s! Acertos: %d, Erros: %d",
                    controladorQuiz.getAcertos() >= 3 ? "ganhou" : "perdeu",
                    controladorQuiz.getAcertos(), controladorQuiz.getErros()));
            proxima.setVisible(false);
            reiniciar.setVisible(true);
        }
    }

    private void reiniciarQuiz() {
        controladorQuiz.reiniciar();
        respostaDada = false;
        atualizaComponentes();
        enunciado.setVisible(false);
        alternativa1.setVisible(false);
        alternativa2.setVisible(false);
        alternativa3.setVisible(false);
        alternativa4.setVisible(false);
        alternativa5.setVisible(false);
        resultado.setVisible(false);
        proxima.setVisible(false);
        reiniciar.setVisible(false);
        iniciarJogo.setVisible(true);
        cadastrarPerguntas.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
