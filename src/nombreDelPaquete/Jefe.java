package nombreDelPaquete;

import javax.swing.ImageIcon;
import java.awt.Graphics;


public class Jefe {
    private int x, y;
    private int ancho = 70, alto = 70; // Dimensiones del jefe ajustadas
    private int vida;
    private boolean visible;
    private int panelAncho, panelAlto;
    private ImageIcon imagen;
    private int velocidadBase = 2; // Velocidad base del jefe
    private int dx, dy; // Velocidad del jefe en x e y
    private int margen = 50; // Margen para evitar las esquinas

    public Jefe(int x, int y, int vida) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.visible = true;
        imagen = new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Escritorio\\Juego_heroes\\src\\recursos\\jiren.gif"); // Ruta a tu imagen GIF
        dx = velocidadBase;
        dy = velocidadBase;
    }

    public void move() {
        int velocidad = (int) (velocidadBase * 1.5);

        // Cambiar de dirección si golpea los límites del panel o se acerca al margen
        if (x <= margen || x >= panelAncho - ancho - margen) {
            dx = -dx;
        }
        if (y <= margen || y >= panelAlto - alto - margen) {
            dy = -dy;
        }

        x += dx;
        y += dy;

        // Cambiar patrones de movimiento basado en la vida
        if (vida > 150) {
            // Movimiento sencillo y constante en diagonal
        } else if (vida > 100) {
            // Movimiento en zigzag
            x += (Math.sin(y * 0.1) * velocidad);
            y += (Math.cos(x * 0.1) * velocidad);
        } else if (vida > 50) {
            // Movimiento circular
            x += (Math.cos(System.currentTimeMillis() * 0.001) * velocidad);
            y += (Math.sin(System.currentTimeMillis() * 0.001) * velocidad);
        } else {
            // Movimiento rápido y errático
            x += (Math.random() * velocidad * 2 - velocidad);
            y += (Math.random() * velocidad * 2 - velocidad);
        }
    }

    public void draw(Graphics g) {
        if (visible) {
            g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
        }
    }

    public void reducirVida(int daño) {
        vida -= daño;
        System.out.println("Daño recibido: " + daño + ", Vida restante: " + vida);
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
