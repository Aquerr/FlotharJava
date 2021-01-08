package pl.bartlomiejstepien.flothar.service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFService
{
    private static final PDFService INSTANCE = new PDFService();

    private static final Path pdfOuputPath = Paths.get(".").resolve("output");

    public static PDFService getInstance()
    {
        return INSTANCE;
    }

    public Path previewPDF() throws IOException
    {
        if (Files.notExists(pdfOuputPath))
        {
            try
            {
                Files.createDirectory(pdfOuputPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        PdfWriter writer = new PdfWriter(pdfOuputPath.resolve("test.pdf").toAbsolutePath().toString());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Hello World!").setFixedPosition(200, 100, 200));

        PdfTextAnnotation ann = (PdfTextAnnotation) new PdfTextAnnotation(new Rectangle(400, 795, 0, 0))
                .setTitle(new PdfString("iText"))
                .setContents("Please, fill out the form.");
        ann.setOpen(true);
        pdf.getFirstPage().addAnnotation(ann);

        PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
        canvas.beginText().setFontAndSize(
                PdfFontFactory.createFont(StandardFonts.HELVETICA), 12)
                .moveText(265, 597)
                .showText("I agree to the terms and conditions.")
                .endText();

        document.close();

        return pdfOuputPath.resolve("test.pdf");
    }

    public void printPDF()
    {

    }
}
