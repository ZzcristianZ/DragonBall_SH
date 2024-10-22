package nombreDelPaquete;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Jugador {
    private int x, y;
    private int dx, dy;
    private int panelAncho, panelAlto;
    private int ancho = 20, alto = 20; // Tamaño del cuadrado
    private boolean[] teclasPresionadas = new boolean[256]; // Array para registrar teclas presionadas

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        if (teclasPresionadas[KeyEvent.VK_A]) {
            dx = -2;
        } else if (teclasPresionadas[KeyEvent.VK_D]) {
            dx = 2;
        } else {
            dx = 0;
        }

        if (teclasPresionadas[KeyEvent.VK_W]) {
            dy = -2;
        } else if (teclasPresionadas[KeyEvent.VK_S]) {
            dy = 2;
        } else {
            dy = 0;
        }

        x += dx;
        y += dy;

        // Restringir movimiento dentro de los límites del panel
        if (x < 0) {
            x = 0;
        }
        if (x > panelAncho - ancho) {
            x = panelAncho - ancho;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > panelAlto - alto) {
            y = panelAlto - alto;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, ancho, alto);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        teclasPresionadas[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        teclasPresionadas[key] = false;
    }

    public void setPanelSize(int ancho, int alto) {
        this.panelAncho = ancho;
        this.panelAlto = alto;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
