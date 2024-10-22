package nombreDelPaquete;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Jugador {
    private int x, y;
    private int ancho = 20, alto = 20;
    private int dx, dy;
    private int panelAncho, panelAlto;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
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
        g.setColor(Color.BLUE);
        g.fillRect(x, y, ancho, alto);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = -2;
        }

        if (key == KeyEvent.VK_D) {
            dx = 2;
        }

        if (key == KeyEvent.VK_W) {
            dy = -2;
        }

        if (key == KeyEvent.VK_S) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            dy = 0;
        }
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
