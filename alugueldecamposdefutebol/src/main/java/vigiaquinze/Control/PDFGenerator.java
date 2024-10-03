package vigiaquinze.Control;

import vigiaquinze.Model.Relatorio;
import vigiaquinze.Model.Reserva;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class PDFGenerator {
    public void generate(Relatorio relatorio) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);

            contentStream.showText("Relatório de Reservas");
            contentStream.newLine();
            contentStream.newLine();

            for (Reserva reserva : relatorio.getReservas()) {
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
                contentStream.showText("-----");
                contentStream.newLine();
                contentStream.newLine();
            }

            contentStream.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            document.save("relatorio.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}