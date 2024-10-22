package nombreDelPaquete;

import java.awt.*;
import java.util.Random;

public class Jefe {
    private int x, y;
    private int ancho = 40, alto = 40;
    private int vida;
    private boolean visible;
    private int panelAncho, panelAlto;
    private Random random;

    public Jefe(int x, int y, int vida) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.visible = true;
        this.random = new Random();
    }

    public void move() {
        // Movimiento más dirigido
        int dx = random.nextInt(11) - 5; // Movimiento aleatorio en eje X
        int dy = random.nextInt(11) - 5; // Movimiento aleatorio en eje Y

        // Aumentar la velocidad un 40%
        x += dx * 1.4;
        y += dy * 1.4;

        // Asegurarse de no salirse del panel
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
        if (visible) {
            g.setColor(Color.RED);
            g.fillRect(x, y, ancho, alto);
        }
    }

    public void reducirVida(int daño) {
        vida -= daño;
        if (vida <= 0) {
            visible = false;
            System.out.println("¡Jefe derrotado!");
        }
    }

    public boolean haSidoDerrotado() {
        return vida <= 0;
    }

    public int getVida() {
        return vida;
    }

    public boolean isVisible() {
        return visible;
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

    public void setPanelSize(int ancho, int alto) {
        this.panelAncho = ancho;
        this.panelAlto = alto;
    }
}
