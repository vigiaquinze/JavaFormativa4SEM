package vigiaquinze.Control;

import vigiaquinze.Model.Relatorio;
import vigiaquinze.Model.Reserva;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font; // Importando a fonte
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class PDFGenerator {
    public void gerarRelatorioReserva(Reserva reserva) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); // Define a fonte antes de adicionar texto
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);

            contentStream.showText("Relatório de Reserva");
            contentStream.newLine();
            contentStream.newLine();
            
            // Adiciona os detalhes da reserva
            contentStream.showText("ID: " + reserva.getId());
            contentStream.newLine();
            contentStream.showText("Data: " + reserva.getData());
            contentStream.newLine();
            contentStream.showText("Hora Início: " + reserva.getHoraInicio());
            contentStream.newLine();
            contentStream.showText("Hora Fim: " + reserva.getHoraFim());
            contentStream.newLine();
            contentStream.showText("Campo: " + reserva.getCampo().getNome());
            contentStream.newLine();
            contentStream.showText("Cliente: " + reserva.getCliente().getNome());
            contentStream.newLine();
            contentStream.showText("Total a Pagar: R$" + reserva.getPrecoReserva() + ",00");
            contentStream.newLine();
            contentStream.showText("-----");
            contentStream.newLine();
            contentStream.newLine();

            contentStream.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String directoryPath = "relatorios";
        File directory = new File(directoryPath);
        
        // Cria o diretório se ele não existir
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = directoryPath + "/relatorio_reserva_" + reserva.getId() + ".pdf";
        
        try {
            document.save(fileName); // Nome do arquivo inclui o ID da reserva
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File pdfFile = new File(fileName);
        if (pdfFile.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Abertura de arquivos não é suportada nesta plataforma.");
            }
        } else {
            System.out.println("O arquivo PDF não foi encontrado.");
        }
    }
}
