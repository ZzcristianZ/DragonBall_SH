package nombreDelPaquete;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public class Heroe extends Personaje {
    private ImageIcon imagen;

    public Heroe(int x, int y) {
        super(x, y, 100, 10); // Vida: 100, Daño: 10
        imagen = new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Escritorio\\Juego_heroes\\src\\recursos\\heroe_goku.gif"); // Ruta a tu imagen GIF
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }
}
