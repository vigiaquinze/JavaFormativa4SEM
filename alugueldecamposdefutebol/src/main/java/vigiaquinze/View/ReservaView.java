package vigiaquinze.View;
import vigiaquinze.Control.ReservaController;
import vigiaquinze.Control.CampoController;
import vigiaquinze.Control.ClienteController;
import vigiaquinze.Model.Reserva;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;

public class ReservaView extends JFrame {
    private JPanel panel;
    private JTextField dataField;
    private JTextField horaInicioField;
    private JTextField horaFimField;
    private JTextField campoIdField;
    private JTextField clienteIdField;
    private JButton adicionarButton;
    private JButton buscarButton;
    private ReservaController reservaController;
    private CampoController campoController;
    private ClienteController clienteController;

    public ReservaView() {
        reservaController = new ReservaController();
        campoController = new CampoController();
        clienteController = new ClienteController();

        setTitle("Gerenciar Reservas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        dataField = new JTextField(20);
        horaInicioField = new JTextField(20);
        horaFimField = new JTextField(20);
        campoIdField = new JTextField(20);
        clienteIdField = new JTextField(20);
        adicionarButton = new JButton("Adicionar Reserva");
        buscarButton = new JButton("Buscar Reserva");

        panel.add(new JLabel("Data (YYYY-MM-DD):"));
        panel.add(dataField);
        panel.add(new JLabel("Hora Início (HH:MM:SS):"));
        panel.add(horaInicioField);
        panel.add(new JLabel("Hora Fim (HH:MM:SS):"));
        panel.add(horaFimField);
        panel.add(new JLabel("Campo ID:"));
        panel.add(campoIdField);
        panel.add(new JLabel("Cliente ID:"));
        panel.add(clienteIdField);
        panel.add(adicionarButton);
        panel.add(buscarButton);

        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Reserva reserva = new Reserva(
                        0,
                        Date.valueOf(dataField.getText()),
                        Time.valueOf(horaInicioField.getText()),
                        Time.valueOf(horaFimField.getText()),
                        campoController.buscarCampo(Integer.parseInt(campoIdField.getText())),
                        clienteController.buscarCliente(Integer.parseInt(clienteIdField.getText()))
                );
                reservaController.adicionarReserva(reserva);
                System.out.println("Reserva adicionada com sucesso!");
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(dataField.getText());
                Reserva reserva = reservaController.buscarReserva(id);
                if (reserva != null) {
                    dataField.setText(reserva.getData().toString());
                    horaInicioField.setText(reserva.getHoraInicio().toString());
                    horaFimField.setText(reserva.getHoraFim().toString());
                    campoIdField.setText(String.valueOf(reserva.getCampo().getId()));
                    clienteIdField.setText(String.valueOf(reserva.getCliente().getId()));
                    System.out.println("Reserva encontrada!");
                } else {
                    System.out.println("Reserva não encontrada.");
                }
            }
        });

        add(panel);
    }
}