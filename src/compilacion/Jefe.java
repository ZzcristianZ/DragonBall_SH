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
    private double velocidadBase = 1.0; 
    private double dx, dy; 
    private int margen = 50; // Margen para evitar las esquinas
    private Random random; 

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
    
        // Verificar si el jefe alcanza los límites y ajustar dirección
        if (x <= margen) {
            x = margen;
            dx = Math.abs(dx); // Mover hacia la derecha
        }
        if (x >= panelAncho - ancho - margen) {
            x = panelAncho - ancho - margen;
            dx = -Math.abs(dx); // Mover hacia la izquierda
        }
        if (y <= margen) {
            y = margen;
            dy = Math.abs(dy); // Mover hacia abajo
        }
        if (y >= panelAlto - alto - margen) {
            y = panelAlto - alto - margen;
            dy = -Math.abs(dy); // Mover hacia arriba
        }
    
        // Ajustar comportamiento según la vida
        if (vida > 150) {
            // Movimiento en diagonal constante
            x += dx * velocidad;
            y += dy * velocidad;
        } else if (vida > 100) {
            // Movimiento en zigzag dinámico
            x += Math.sin(y * 0.1) * velocidad + dx * 0.9;
            y += Math.cos(x * 0.1) * velocidad + dy * 0.9;
        } else {
            // Movimiento errático, nunca quieto
            dx += (random.nextDouble() - 0.5) * 0.2; // Pequeña aleatoriedad
            dy += (random.nextDouble() - 0.5) * 0.2;
            x += dx * velocidad * 1.2;
            y += dy * velocidad * 1.2;
        }
    
        // Esquivar proyectiles
        for (Proyectil proyectil : PanelJuego.proyectiles) {
            double distanciaProyectil = Math.sqrt(Math.pow(proyectil.getX() - x, 2) + Math.pow(proyectil.getY() - y, 2));
            if (distanciaProyectil < 70) { // Rango de esquiva
                double escapeX = x - proyectil.getX();
                double escapeY = y - proyectil.getY();
                double magnitudEscape = Math.sqrt(escapeX * escapeX + escapeY * escapeY);
    
                // Normalizar vector y añadir componente aleatoria para el esquive
                dx += (escapeX / magnitudEscape) * 0.5;
                dy += (escapeY / magnitudEscape) * 0.5;
            }
        }
    
        // Aplicar movimiento continuo
        x += dx;
        y += dy;
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
