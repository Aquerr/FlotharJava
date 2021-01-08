package pl.bartlomiejstepien.flothar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.bartlomiejstepien.flothar.storage.entity.Rental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FlotharGUI extends JFrame
{
    private static final Logger LOGGER = LogManager.getLogger(FlotharGUI.class);

    private Flothar flothar;

    private JPanel panel;

    private Box box;

    private JButton buttonShowPDF;
    private JButton buttonSaveRental;
    private JButton buttonShowRental;

    private JLabel labelSearchStartDate;
    private JLabel labelSearchEndDate;
    private JFormattedTextField textFieldSearchStartDate;
    private JFormattedTextField textFieldSearchEndDate;

    private JButton searchButton;

    private JScrollPane rentalListPane;
    private JList<Rental> rentalList;

    public FlotharGUI(Flothar flothar)
    {
        this.flothar = flothar;
    }

    public void showPDF()
    {
        LOGGER.debug("Showing PDF");

        final Path path = this.flothar.getPDFPath();
        Desktop desktop = Desktop.getDesktop();
        try
        {
            desktop.open(path.toFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveRental()
    {
        //TODO: Get rental from view and save it.

        LOGGER.debug("Saving Rental");

        Rental newRental = new Rental();
        newRental.setCarId(1);
        newRental.setCustomerId(2);
        newRental.setStartDateTime(LocalDateTime.now().minus(2, ChronoUnit.DAYS));
        newRental.setEndDateTime(LocalDateTime.now().plus(35, ChronoUnit.DAYS));
        this.flothar.saveRental(newRental);
    }

    public void showRental()
    {
        //TODO: Show selected rental in view.

        LOGGER.debug("Showing Rental");

        final Rental rental = this.flothar.getRentalById(2);
        System.out.println("Got Rental -> " + rental);
    }

    public void setupAndShowGUI()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

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
        this.rentalList.setListData(this.flothar.getAllRentals());
        this.rentalList.setCellRenderer(new RentalListCellRenderer());
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

        this.labelSearchStartDate = new JLabel("Start Date");
        this.labelSearchEndDate = new JLabel("End Date");
        this.textFieldSearchStartDate = new JFormattedTextField(4);
        this.textFieldSearchStartDate.setText(LocalDate.now().toString());
        this.textFieldSearchEndDate = new JFormattedTextField(4);
        this.textFieldSearchEndDate.setText(LocalDate.now().toString());
        this.searchButton = new JButton("Search");

        final Box test = Box.createHorizontalBox();
        Box content = Box.createHorizontalBox();

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(this.labelSearchStartDate);
        horizontalBox.add(this.textFieldSearchStartDate);
        horizontalBox.add(this.labelSearchEndDate);
        horizontalBox.add(this.textFieldSearchEndDate);
        horizontalBox.add(this.searchButton);

//        content.add(Box.createVerticalStrut(50));
        content.add(horizontalBox);
//        content.add(Box.createVerticalStrut(20));


//        test.add(content);
        this.panel.add(Box.createHorizontalStrut(200));
        this.panel.add(horizontalBox);
        this.panel.add(Box.createVerticalStrut(50));

        Box t = Box.createHorizontalBox();
        t.add(this.rentalListPane);


        content.add(t);
//        this.panel.add(t);
        Box buttonsBox = Box.createHorizontalBox();
        buttonsBox.add(this.buttonSaveRental);
        buttonsBox.add(this.buttonShowRental);
        buttonsBox.add(this.buttonShowPDF);

//        content.add(this.buttonShowRental);
//        content.add(this.buttonSaveRental);
//        content.add(this.buttonShowPDF);
        this.panel.add(rentalListPane);
        this.panel.add(buttonsBox);
        this.panel.add(Box.createHorizontalStrut(100));
//        this.box.add(this.buttonShowRental);
//        this.box.add(this.buttonSaveRental);
//        this.box.add(this.buttonShowPDF);
//        this.panel.add(this.box, BorderLayout.SOUTH);
        add(this.panel);

        setTitle("Flothar");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void showSelectedRental(Rental rental)
    {
        //TODO: Show selected rental

        LOGGER.debug("Showing information for rental " + rental);
    }

    private static class RentalListCellRenderer extends JLabel implements ListCellRenderer<Rental>
    {

        @Override
        public Component getListCellRendererComponent(JList<? extends Rental> list, Rental value, int index, boolean isSelected, boolean cellHasFocus)
        {
            setText(value.getId() + ". Rental: " + value.getStartDateTime() + " - " + value.getEndDateTime());
            setForeground(Color.BLACK);
            setFont(Font.getFont("Arial"));
            setBackground(Color.WHITE);
            return this;
        }
    }
}
