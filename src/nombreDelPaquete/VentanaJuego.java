package nombreDelPaquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaJuego extends JFrame {

    private PanelJuego panelJuego;

    public VentanaJuego() {
        setTitle("Juego 2D");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelJuego = new PanelJuego();
        add(panelJuego, BorderLayout.CENTER);

        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelJuego.updateGame();
                panelJuego.repaint();
            }
        });
        timer.start();
    }
}
