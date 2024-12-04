package compilacion;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

public class Heroe extends Personaje {
    private ImageIcon imagen;
    private int kamehamehaUsos = 0; // Contador de usos del Kamehameha
    public static final int KAMEHAMEHA_MAX_USOS = 3; // Máximo de usos permitidos

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
    
    // Nueva habilidad Kamehameha
    public void kamehameha() {
        if (kamehamehaUsos < KAMEHAMEHA_MAX_USOS) {
            kamehamehaUsos++; // Incrementar contador de usos
            // Hacer que el personaje no pueda moverse durante el ataque
            this.dx = 0;
            this.dy = 0;

            String rutaKamehameha = "src/recursos/kamehameha.gif"; // Ruta a la imagen del Kamehameha

            // Crear un Timer para lanzar 10 proyectiles
            Timer timer = new Timer();
            TimerTask lanzarProyectil = new TimerTask() {
                private int proyectilesLanzados = 0;

                @Override
                public void run() {
                    if (proyectilesLanzados < 10) {
                        Proyectil kamehameha = new Proyectil(x + ancho, y + alto / 2, 8, rutaKamehameha);
                        PanelJuego.proyectiles.add(kamehameha);
                        proyectilesLanzados++;
                    } else {
                        // Permitir que el personaje se mueva nuevamente después de lanzar todos los proyectiles
                        this.cancel();
                    }
                }
            };
            timer.schedule(lanzarProyectil, 0, 90); // Lanza un proyectil cada 90 ms
        }
    }

    // Métodos getter para acceder a kamehamehaUsos y KAMEHAMEHA_MAX_USOS
    public int getKamehamehaUsos() {
        return kamehamehaUsos;
    }

    public static int getKamehamehaMaxUsos() {
        return KAMEHAMEHA_MAX_USOS;
    }
}
