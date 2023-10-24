package RadioClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author Claudia León Tola
 *
 */

public class RadioClock {

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reloj Estudios Radio Borcelle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setLayout(new BorderLayout());
            frame.getContentPane().setBackground(Color.decode("#c7def2"));
            
                        
            try {
                //frame.setIconImage(new ImageIcon("icono.png").getImage());   
            	URL iconURL = RadioClock.class.getResource("icono.png");
            	frame.setIconImage(new ImageIcon(iconURL).getImage());


            } catch (Exception e) {
                System.err.println(
                        "No se pudo cargar el icono. Asegúrate de tener un archivo 'icono.png' en el directorio del proyecto.");
            }
            

            // Encabezado con el logo
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new GridBagLayout());
            headerPanel.setBackground(Color.decode("#c7def2"));
            //ImageIcon logo = new ImageIcon("logo.png");
            URL logoURL = RadioClock.class.getResource("logo.png");
            ImageIcon logo = new ImageIcon(logoURL);           
            
            
            JLabel logoLabel = new JLabel(logo);
            headerPanel.add(logoLabel);
            frame.add(headerPanel, BorderLayout.NORTH);

            // Panel para el reloj y la fecha
            JPanel clockPanel = new JPanel();
            clockPanel.setLayout(new GridBagLayout());
            clockPanel.setBackground(Color.decode("#c7def2"));
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;

            JLabel clockLabel = new JLabel();
            clockLabel.setForeground(Color.decode("#233c74"));
            clockPanel.add(clockLabel, constraints);

            // Etiqueta para la fecha
            constraints.gridy = 1;
            JLabel dateLabel = new JLabel();
            dateLabel.setForeground(Color.decode("#233c74"));
            clockPanel.add(dateLabel, constraints);
            frame.add(clockPanel, BorderLayout.CENTER);

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM 'de' yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    LocalTime currentTime = now.toLocalTime();

                    // Cambiar color de la hora
                    int minutes = currentTime.getMinute();
                    if (minutes >= 50 && minutes < 55) {
                        clockLabel.setForeground(Color.ORANGE);
                    } else if (minutes >= 55 && minutes <= 59) {
                        clockLabel.setForeground(Color.RED);
                    } else {
                        clockLabel.setForeground(Color.decode("#233c74"));
                    }

                    clockLabel.setText(currentTime.format(timeFormatter));
                    dateLabel.setText(now.format(dateFormatter));
                }
            });
            timer.start();

            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    int logoHeight = (int) (frame.getHeight() * 0.25); // Modificar proporción del tamaño del logo
                    
                    // Mantener la relación de aspecto del logo al redimensionarlo
                    int originalWidth = logo.getIconWidth();
                    int originalHeight = logo.getIconHeight();
                    float aspectRatio = (float) originalWidth / originalHeight;
                    int logoWidth = (int) (logoHeight * aspectRatio);

                    Image scaledLogo = logo.getImage().getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
                    logoLabel.setIcon(new ImageIcon(scaledLogo));

                    int clockHeight = (frame.getHeight() * 3) / 4;

                    int fontSize = clockHeight / 2;
                    clockLabel.setFont(new Font("Arial",Font.BOLD, fontSize));
                    int dateFontSize = logoHeight / 4; // Ajustar el tamaño de la fuente de la etiqueta de la fecha
                    dateLabel.setFont(new Font("Arial", Font.PLAIN, dateFontSize));
                }
           });
            
             // Footer con mi nombre
             JPanel footerPanel = new JPanel();
             footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
             footerPanel.setBackground(Color.decode("#c7def2"));

            // Ajustar la fuente y tamaño del nombre
             Font nameFont = new Font("Arial", Font.BOLD, 24);
             JLabel nameLabel = new JLabel("Claudia León Tola");
             nameLabel.setForeground(Color.decode("#233c74"));
             nameLabel.setFont(nameFont);

             footerPanel.add(nameLabel);
             frame.add(footerPanel, BorderLayout.SOUTH);


            frame.setVisible(true);
        });
    }
}