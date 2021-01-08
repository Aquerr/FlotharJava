package pl.bartlomiejstepien.flothar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.bartlomiejstepien.flothar.service.PDFService;
import pl.bartlomiejstepien.flothar.service.RentalService;
import pl.bartlomiejstepien.flothar.storage.entity.Rental;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public class Flothar
{
    private static final Logger LOGGER = LogManager.getLogger(Flothar.class);

    private final FlotharGUI flotharGUI;

    private final RentalService rentalService;
    private final PDFService pdfService;

    public static void main(String[] args)
    {
        final Flothar flothar = new Flothar();
        flothar.start();
    }

    public Flothar()
    {
        this.flotharGUI = new FlotharGUI(this);
        this.rentalService = new RentalService();
        this.pdfService = PDFService.getInstance();
    }

    public void start()
    {
        SwingUtilities.invokeLater(flotharGUI::setupAndShowGUI);
    }

    public void showPDF()
    {
        LOGGER.debug("Showing PDF");
        try
        {
            this.pdfService.previewPDF();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveRental(Rental rental)
    {
        LOGGER.debug("Saving Rental");
        this.rentalService.saveRental(rental);
    }

    public Rental[] getAllRentals()
    {
        return this.rentalService.getAllRentals().toArray(new Rental[0]);
    }

    public Path getPDFPath()
    {
        try
        {
            return this.pdfService.previewPDF();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Rental getRentalById(int i)
    {
        return this.rentalService.getRentalById(i);
    }
}
