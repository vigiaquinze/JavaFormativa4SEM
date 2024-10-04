package vigiaquinze;

import vigiaquinze.Connection.DatabaseInitializer; // Certifique-se de que este é o caminho correto
import vigiaquinze.View.MainView; // Importando a classe MainView

public class Main {
    public static void main(String[] args) {
        // Chama o método para criar as tabelas
        DatabaseInitializer.initialize(); 

        // Cria uma nova instância de MainView
        MainView mainView = new MainView();
        mainView.setVisible(true); // Exibe a interface gráfica
    }
}