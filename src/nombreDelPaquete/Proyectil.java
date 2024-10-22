package nombreDelPaquete;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public class Proyectil {
    private int x, y;
    private int ancho = 35, alto = 35; // Ajustar tamaño del proyectil
    private int velocidad = 5;
    private int daño = 5;
    private ImageIcon imagen;

    public Proyectil(int x, int y) {
        this.x = x;
        this.y = y;
        imagen = new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Escritorio\\Juego_heroes\\src\\recursos\\kamehameha.gif"); // Ruta a tu imagen GIF
    }

    public void move() {
        x += velocidad;
    }

    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }

    public boolean haImpactado(Jefe jefe) {
        return x + ancho >= jefe.getX() && y >= jefe.getY() && y <= jefe.getY() + jefe.getAlto();
    }

    public int getDaño() {
        return daño;
    }

    // Añadir métodos getX y getY
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
