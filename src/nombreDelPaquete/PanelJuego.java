// Clase PanelJuego
package nombreDelPaquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PanelJuego extends JPanel {
    private Jugador jugador;
    private Jefe jefe;
    private ArrayList<Proyectil> proyectiles;
    private int balas;
    private boolean recargando;
    private boolean puedeDisparar;
    private boolean espacioPresionado;
    private boolean juegoTerminado;
    private Timer recargaTimer;
    private Timer disparoTimer;
    private Timer movimientoJefeTimer;

    public PanelJuego() {
        setFocusable(true);
        setBackground(Color.BLACK);
        jugador = new Jugador(50, 300);
        jefe = new Jefe(650, 250, 200); // Vida del jefe: 200
        jefe.setPanelSize(800, 600);
        proyectiles = new ArrayList<>();
        balas = 30;
        recargando = false;
        puedeDisparar = true;
        espacioPresionado = false;
        juegoTerminado = false;
        jugador.setPanelSize(800, 600); // Tamaño del panel
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                jugador.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espacioPresionado = true;
                    if (puedeDisparar) {
                        disparar();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                jugador.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espacioPresionado = false;
                    puedeDisparar = true; // Resetear al soltar espacio
                }
            }
        });

        recargaTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recargar();
            }
        });
        recargaTimer.setRepeats(false);

        disparoTimer = new Timer(180, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                puedeDisparar = true;
                if (espacioPresionado) {
                    disparar();
                }
            }
        });

        movimientoJefeTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jefe.move();
            }
        });
        movimientoJefeTimer.start();
    }

    public void updateGame() {
        if (!juegoTerminado) {
            jugador.move();
            ArrayList<Proyectil> proyectilesABorrar = new ArrayList<>();
            for (Proyectil proyectil : proyectiles) {
                proyectil.move();
                if (proyectil.haImpactado(jefe)) {
                    jefe.reducirVida(proyectil.getDaño());
                    proyectilesABorrar.add(proyectil);
                }
            }
            proyectiles.removeAll(proyectilesABorrar);
            // Verificar si el jefe ha sido derrotado
            if (jefe.haSidoDerrotado()) {
                juegoTerminado = true;
                mostrarMensajeVictoria();
            }
        }
    }

    private void disparar() {
        if (balas > 0 && !recargando) {
            proyectiles.add(new Proyectil(jugador.getX() + jugador.getAncho(), jugador.getY() + jugador.getAlto() / 2));
            balas--;
            puedeDisparar = false; // Impedir disparar hasta que el temporizador lo permita
            disparoTimer.start();
            if (balas == 0) {
                iniciarRecarga();
            }
        }
    }

    private void iniciarRecarga() {
        recargando = true;
        recargaTimer.restart();
    }

    private void recargar() {
        balas = 30;
        recargando = false;
    }

    private void mostrarMensajeVictoria() {
        recargaTimer.stop();
        disparoTimer.stop();
        movimientoJefeTimer.stop();
        JOptionPane.showMessageDialog(this, "¡Enhorabuena! Has derrotado al jefe. Puedes cerrar esta ventana.", "Victoria", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        jugador.draw(g);
        jefe.draw(g);
        for (Proyectil proyectil : proyectiles) {
            proyectil.draw(g);
        }
        // Dibujar la vida del jefe centrada en la parte superior
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Aumentar tamaño del texto
        String vidaTexto = "Vida del Jefe: " + jefe.getVida();
        int textoAncho = g.getFontMetrics().stringWidth(vidaTexto);
        g.drawString(vidaTexto, (getWidth() - textoAncho) / 2, 20); // Centrar el texto
        // Dibujar la cantidad de balas en la esquina superior izquierda
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Balas: " + balas, 10, 20);
    }
}
