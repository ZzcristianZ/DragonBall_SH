package compilacion;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

public class Villano extends Personaje {
    private ImageIcon imagen;
    private int caosUsos = 0; // Contador de usos de la Explosión de Caos
    public static final int CAOS_MAX_USOS = 5; // Máximo de usos permitidos
    private Jefe jefe; // Referencia al jefe

    public Villano(int x, int y, Jefe jefe) {
        super(x, y, 100, 10); // Vida: 100, Daño: 10
        this.jefe = jefe;
        ImageIcon originalGif = new ImageIcon("src/recursos/villano.gif");
        Image imagenRedimensionada = originalGif.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        imagen = new ImageIcon(imagenRedimensionada);
        this.ancho = 35;
        this.alto = 35;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imagen.getImage(), x, y, ancho, alto, null);
    }

    // Nueva habilidad Explosión de Caos
    public void explosionCaos() {
        if (caosUsos < CAOS_MAX_USOS) {
            caosUsos++; // Incrementar contador de usos

            String rutaExplosiónCaos = "src/recursos/explosion_caos.gif"; // Ruta a la imagen de la Explosión de Caos

            // Crear un Timer para lanzar múltiples proyectiles hacia el jefe
            Timer timer = new Timer();
            TimerTask lanzarProyectil = new TimerTask() {
                private int proyectilesLanzados = 0;

                @Override
                public void run() {
                    if (proyectilesLanzados < 8) { // Cambiar a 8 proyectiles
                        // Calcular la dirección hacia el jefe
                        int dx = jefe.getX() + jefe.getAncho() / 2 - x - ancho / 2;
                        int dy = jefe.getY() + jefe.getAlto() / 2 - y - alto / 2;
                        double distancia = Math.sqrt(dx * dx + dy * dy);
                        double velocidadX = dx / distancia * 5; // Ajustar la velocidad según la dirección
                        double velocidadY = dy / distancia * 5;

                        Proyectil caos = new Proyectil(x + ancho / 2, y + alto / 2, (int)velocidadX, (int)velocidadY, 10, rutaExplosiónCaos);
                        PanelJuego.proyectiles.add(caos);
                        proyectilesLanzados++;
                    } else {
                        this.cancel();
                    }
                }
            };
            timer.schedule(lanzarProyectil, 0, 100); // Lanza un proyectil cada 100 ms
        }
    }

    public int getCaosUsos() {
        return caosUsos;
    }

    public static int getCaosMaxUsos() {
        return CAOS_MAX_USOS;
    }
}
