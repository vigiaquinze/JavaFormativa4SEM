package vigiaquinze.View;
import vigiaquinze.Control.CampoController;
import vigiaquinze.Model.Campo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CampoView extends JFrame {
    private JPanel panel;
    private JTextField nomeField;
    private JTextField localField;
    private JTextField precoField;
    private JButton adicionarButton;
    private JButton buscarButton;
    private CampoController campoController;

    public CampoView() {
        campoController = new CampoController();

        setTitle("Gerenciar Campos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        nomeField = new JTextField(20);
        localField = new JTextField(20);
        precoField = new JTextField(20);
        adicionarButton = new JButton("Adicionar Campo");
        buscarButton = new JButton("Buscar Campo");

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Local:"));
        panel.add(localField);
        panel.add(new JLabel("Preço:"));
        panel.add(precoField);
        panel.add(adicionarButton);
        panel.add(buscarButton);

        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Campo campo = new Campo(0, nomeField.getText(), localField.getText(), Double.parseDouble(precoField.getText()));
                campoController.adicionarCampo(campo);
                System.out.println("Campo adicionado com sucesso!");
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(nomeField.getText());
                Campo campo = campoController.buscarCampo(id);
                if (campo != null) {
                    nomeField.setText(campo.getNome());
                    localField.setText(campo.getLocal());
                    precoField.setText(String.valueOf(campo.getPreco()));
                    System.out.println("Campo encontrado!");
                } else {
                    System.out.println("Campo não encontrado.");
                }
            }
        });

        add(panel);
    }
}
