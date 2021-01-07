package pl.bartlomiejstepien.flothar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.bartlomiejstepien.flothar.service.PDFService;
import pl.bartlomiejstepien.flothar.service.RentalService;
import pl.bartlomiejstepien.flothar.storage.entity.Rental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Flothar extends JFrame
{
    private static final Logger LOGGER = LogManager.getLogger(Flothar.class);

    private JPanel panel;

    private Box box;

    private JButton buttonShowPDF;
    private JButton buttonSaveRental;
    private JButton buttonShowRental;

    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;

    private JScrollPane rentalListPane;
    private JList<Rental> rentalList;

    private final RentalService rentalService = new RentalService();
    private final PDFService pdfService = PDFService.getInstance();

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Flothar().start());
    }

    public void start()
    {
        setupAndShowGUI();
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

    public void saveRental()
    {
        LOGGER.debug("Saving Rental");

        Rental newRental = new Rental();
        newRental.setCarId(1);
        newRental.setCustomerId(2);
        newRental.setStartDateTime(LocalDateTime.now().minus(2, ChronoUnit.DAYS));
        newRental.setEndDateTime(LocalDateTime.now().plus(35, ChronoUnit.DAYS));
        this.rentalService.saveRental(newRental);
    }

    public void showRental()
    {
        LOGGER.debug("Showing Rental");

        final Rental rental = this.rentalService.getRentalById(2);
        System.out.println("Got Rental -> " + rental);
    }

    private void setupAndShowGUI()
    {
        setResizable(false);
        setSize(1200, 720);

        this.panel = new JPanel();
        this.panel.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1, 1));
        this.panel.setSize(300, 300);
        this.panel.setBackground(new Color(Integer.decode("#eeeeee")));

        this.box = Box.createHorizontalBox();

        this.buttonShowPDF = new JButton("Show PDF");
        this.buttonShowPDF.addActionListener(actionEvent -> {
            System.out.println("ShowPDF button clicked!");
            showPDF();
        });
        this.buttonShowPDF.setSize(20, 20);

        this.buttonSaveRental = new JButton("Save Rental");
        this.buttonSaveRental.addActionListener(actionEvent -> {
            System.out.println("Save Rental button clicked!");
            saveRental();
        });


        this.buttonShowRental = new JButton("Show Rental");
        this.buttonShowRental.addActionListener(actionEvent -> {
            System.out.println("Show Rental button clicked!");
            showRental();
        });

        this.rentalList = new JList<>();
        this.rentalList.setLayoutOrientation(JList.VERTICAL);
        this.rentalList.setVisibleRowCount(6);
        this.rentalList.setListData(this.rentalService.getAllRentals().toArray(new Rental[0]));
        this.rentalList.setCellRenderer(new ListCellRenderer<Rental>()
        {
            @Override
            public Component getListCellRendererComponent(JList<? extends Rental> list, Rental value, int index, boolean isSelected, boolean cellHasFocus)
            {
                final Label label = new Label(value.getId() + " Rental: " + value.getStartDateTime() + " - " + value.getEndDateTime());
                label.setForeground(Color.BLACK);
                label.setFont(Font.getFont("Arial"));
                label.setBackground(Color.WHITE);
                return label;
            }
        });
        this.rentalList.setBackground(Color.YELLOW);
        this.rentalList.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() != 1)
                    return;
                JList jList = (JList) e.getSource();
                int selectedIndex = jList.getSelectedIndex();
                if (selectedIndex == 0)
                    return;
                showSelectedRental((Rental) jList.getModel().getElementAt(selectedIndex));
            }
        });

        this.rentalListPane = new JScrollPane();
        this.rentalListPane.setViewportView(this.rentalList);

        this.searchLabel = new JLabel("Search rental");
        this.searchField = new JTextField();
        this.searchButton = new JButton("Search");

        Box vertical = Box.createVerticalBox();
        vertical.add(this.searchLabel);
        vertical.add(this.searchField);
        vertical.add(this.searchButton);

        Component b = Box.createHorizontalStrut(300);

        this.panel.add(b);
        this.panel.add(this.rentalListPane);
        this.panel.add(vertical);
        this.box.add(this.buttonShowRental);
        this.box.add(this.buttonSaveRental);
        this.box.add(this.buttonShowPDF);
        this.panel.add(this.box, BorderLayout.SOUTH);
        add(this.panel);

        setTitle("Flothar");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void showSelectedRental(Rental rental)
    {
        LOGGER.debug("Showing information for rental " + rental);

    }
}
