package compilacion;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Personaje {
    protected int x, y;
    protected int ancho = 35, alto = 35;
    protected int vida;
    protected int daño;
    protected int panelAncho, panelAlto;
    protected int dx, dy;
    protected Set<Integer> teclasPresionadas = new HashSet<>();

    public Personaje(int x, int y, int vida, int daño) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.daño = daño;
    }

    public void move() {
        dx = 0;
        dy = 0;

        if (teclasPresionadas.contains(KeyEvent.VK_A)) {
            dx -= 3; 
        }
        if (teclasPresionadas.contains(KeyEvent.VK_D)) {
            dx += 3; 
        }
        if (teclasPresionadas.contains(KeyEvent.VK_W)) {
            dy -= 3; 
        }
        if (teclasPresionadas.contains(KeyEvent.VK_S)) {
            dy += 3; 
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
        g.fillRect(x, y, ancho, alto);
    }

    public void keyPressed(KeyEvent e) {
        teclasPresionadas.add(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        teclasPresionadas.remove(e.getKeyCode());
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

    public int getVida() {
        return vida;
    }

    // Método para reducir la vida
    public void reducirVida(int daño) {
        vida -= daño;
    }

    // Añadir método getDaño
    public int getDaño() {
        return daño;
    }
}
