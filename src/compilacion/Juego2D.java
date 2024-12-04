package compilacion;

import javax.swing.*;

public class Juego2D extends JFrame {
    public Juego2D() {
        setTitle("Juego Principal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        add(new VentanaJuego());
    }

    
}
