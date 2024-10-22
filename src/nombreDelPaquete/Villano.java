package nombreDelPaquete;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public class Villano extends Personaje {
    private ImageIcon imagen;

    public Villano(int x, int y) {
        super(x, y, 80, 15); // Vida: 80, Daño: 15
        imagen = new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Escritorio\\Juego_heroes\\src\\recursos\\villano.gif"); // Ruta a tu imagen GIF
        this.ancho = 35;
        this.alto = 35;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }
}
