package compilacion;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

public class Proyectil {
    private int x, y;
    private int dx, dy;
    private int ancho = 30, alto = 30;
    private int daño;
    private ImageIcon imagen;

    // Constructor para proyectiles con dirección
    public Proyectil(int x, int y, int dx, int dy, int daño, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.daño = daño;
        imagen = new ImageIcon(rutaImagen); 
        Image imagenRedimensionada = imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
        imagen = new ImageIcon(imagenRedimensionada);
    }

    // Constructor para proyectiles sin dirección (por defecto hacia la derecha)
    public Proyectil(int x, int y, int daño, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.dx = 5; // Velocidad por defecto hacia la derecha
        this.dy = 0; // Sin movimiento vertical por defecto
        this.daño = daño;
        imagen = new ImageIcon(rutaImagen); 
        Image imagenRedimensionada = imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
        imagen = new ImageIcon(imagenRedimensionada);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }

    public boolean haImpactado(Jefe jefe) {
        int centroX = x + ancho / 2;
        int centroY = y + alto / 2;

        return centroX >= jefe.getX() && centroX <= jefe.getX() + jefe.getAncho() &&
               centroY >= jefe.getY() && centroY <= jefe.getY() + jefe.getAlto();
    }

    public int getDaño() {
        return daño;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
