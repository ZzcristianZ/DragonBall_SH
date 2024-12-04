package compilacion;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.util.Random;

public class Jefe {
    private int x, y;
    private int ancho = 70, alto = 70; // Dimensiones del jefe ajustadas
    private int vida;
    private boolean visible;
    private int panelAncho, panelAlto;
    private ImageIcon imagen;
    private double velocidadBase = 1.0; // Velocidad base del jefe ajustada a 1.0
    private double dx, dy; // Ajustamos dx y dy a double
    private int margen = 50; // Margen para evitar las esquinas
    private Random random; // Aseguramos que random sea una variable de instancia

    public Jefe(int x, int y, int vida) {
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.visible = true;
        imagen = new ImageIcon("src\\recursos\\jiren.gif"); // Ruta a tu imagen GIF
        dx = velocidadBase;
        dy = velocidadBase;
        random = new Random(); // Inicializamos random en el constructor
    }

    public void setPanelSize(int ancho, int alto) {
        this.panelAncho = ancho;
        this.panelAlto = alto;
    }

    public void move() {
        double velocidad = velocidadBase * 1.5;
    
        // Movimiento aleatorio dentro del panel
        if (x <= margen || x >= panelAncho - ancho - margen) {
            dx = -dx;
        }
        if (y <= margen || y >= panelAlto - alto - margen) {
            dy = -dy;
        }
    
        // Cambiar de dirección basado en la vida
        if (vida > 150) {
            // Movimiento sencillo y constante en diagonal
            x += dx;
            y += dy;
        } else if (vida > 100) {
            // Movimiento en zigzag
            x += Math.sin(y * 0.1) * velocidad;
            y += Math.cos(x * 0.1) * velocidad;
        } else if (vida > 50) {
            // Movimiento circular
            x += Math.cos(System.currentTimeMillis() * 0.001) * velocidad;
            y += Math.sin(System.currentTimeMillis() * 0.001) * velocidad;
        } else {
            // Movimiento rápido y errático
            x += random.nextDouble() * velocidad * 2 - velocidad;
            y += random.nextDouble() * velocidad * 2 - velocidad;
        }
    
        // Ajustar movimiento para esquivar proyectiles con aleatoriedad
        for (Proyectil proyectil : PanelJuego.proyectiles) {
            double distanciaProyectil = Math.sqrt(Math.pow(proyectil.getX() - x, 2) + Math.pow(proyectil.getY() - y, 2));
            if (distanciaProyectil < 100) { // Rango para empezar a esquivar
                dx += (x - proyectil.getX()) * 0.05 * (random.nextBoolean() ? 1 : -1); // Añadir aleatoriedad
                dy += (y - proyectil.getY()) * 0.05 * (random.nextBoolean() ? 1 : -1);
            }
        }
    
        // Aplicar movimiento
        x += dx;
        y += dy;
    
        // Asegurarse de que el jefe se mantenga dentro de los límites del panel
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + ancho > panelAncho) x = panelAncho - ancho;
        if (y + alto > panelAlto) y = panelAlto - alto;
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
}
