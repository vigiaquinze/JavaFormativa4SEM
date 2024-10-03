package vigiaquinze.View;

import vigiaquinze.Control.RelatorioController;
import vigiaquinze.Control.PDFGenerator;
import vigiaquinze.Model.Relatorio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelatorioView extends JFrame {
    private JPanel panel;
    private JButton gerarRelatorioButton;
    private JButton gerarPDFButton;
    private RelatorioController relatorioController;
    private PDFGenerator pdfGenerator;

    public RelatorioView() {
        relatorioController = new RelatorioController();
        pdfGenerator = new PDFGenerator();

        setTitle("Gerar Relatório");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        gerarRelatorioButton = new JButton("Gerar Relatório");
        gerarPDFButton = new JButton("Gerar PDF");

        gerarRelatorioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorio relatorio = relatorioController.gerarRelatorio();
                System.out.println("Relatório gerado com sucesso!");
            }
        });

        gerarPDFButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Relatorio relatorio = relatorioController.gerarRelatorio();
                pdfGenerator.generate(relatorio);
                System.out.println("PDF gerado com sucesso!");
            }
        });

        panel.add(gerarRelatorioButton);
        panel.add(gerarPDFButton);

        add(panel);
    }
}