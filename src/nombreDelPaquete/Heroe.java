package nombreDelPaquete;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

public class Heroe extends Personaje {
    private ImageIcon imagen;

    public Heroe(int x, int y) {
        super(x, y, 100, 10); // Vida: 100, Daño: 10
        ImageIcon originalGif = new ImageIcon("src/recursos/heroe_goku.gif");
        Image imagenRedimensionada = originalGif.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        imagen = new ImageIcon(imagenRedimensionada);
        this.ancho = 35;
        this.alto = 35;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }
    
}
