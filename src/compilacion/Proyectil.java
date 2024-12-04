package compilacion;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

public class Proyectil {
    private int x, y;
    private int ancho = 30, alto = 30;
    private final int velocidad = 5; 
    private int daño;
    private ImageIcon imagen;

    public Proyectil(int x, int y, int daño, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.daño = daño;
        imagen = new ImageIcon(rutaImagen); 
        Image imagenRedimensionada = imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
        imagen = new ImageIcon(imagenRedimensionada);
    }

    public void move() {
        x += velocidad;
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
