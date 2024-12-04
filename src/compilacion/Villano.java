package compilacion;

import javax.swing.ImageIcon;
import java.awt.Graphics;

public class Villano extends Personaje {
    private ImageIcon imagen;

    public Villano(int x, int y) {
        super(x, y, 150, 15); // Vida: 150, Daño: 25
        imagen = new ImageIcon("src\\recursos\\villano.gif"); // Ruta a tu imagen GIF
        this.ancho = 35;
        this.alto = 35;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }
}
