package nombreDelPaquete;

import javax.swing.*;

public class Juego2D extends JFrame {
    public Juego2D() {
        setTitle("Juego Principal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Añadir la ventana del juego
        add(new VentanaJuego());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Juego2D().setVisible(true);
            }
        });
    }
}
