package vigiaquinze.View;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel panel;
    private JButton clienteButton;
    private JButton campoButton;
    private JButton reservaButton;
    private JButton relatorioButton;

    public MainView() {
        setTitle("Sistema de Aluguel de Campos de Futebol");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        clienteButton = new JButton("Gerenciar Clientes");
        campoButton = new JButton("Gerenciar Campos");
        reservaButton = new JButton("Gerenciar Reservas");
        relatorioButton = new JButton("Gerar Relatório");

        clienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClienteView clienteView = new ClienteView();
                clienteView.setVisible(true);
            }
        });

        campoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CampoView campoView = new CampoView();
                campoView.setVisible(true);
            }
        });

        reservaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReservaView reservaView = new ReservaView();
                reservaView.setVisible(true);
            }
        });

        panel.add(clienteButton);
        panel.add(campoButton);
        panel.add(reservaButton);

        add(panel);
    }
}