package nombreDelPaquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PanelJuego extends JPanel {

    private Personaje personaje;
    private Jefe jefe;
    private ArrayList<Proyectil> proyectiles;
    private int balas;
    private boolean recargando;
    private boolean puedeDisparar;
    private boolean espacioPresionado;
    private boolean juegoTerminado;
    private Timer recargaTimer;
    private Timer disparoTimer;
    private Timer movimientoJefeTimer;
    private ImageIcon fondo;
    private String nombrePersonaje;
    private boolean mostrandoRecargandoMensaje = false;



    
    public PanelJuego() {
        setFocusable(true);
        setBackground(Color.BLACK);
        seleccionarPersonaje();


        

        jefe = new Jefe(650, 250, 500); // Vida del jefe: 200
        jefe.setPanelSize(800, 600);
        proyectiles = new ArrayList<>();
        balas = 30;
        recargando = false;
        puedeDisparar = true;
        espacioPresionado = false;
        juegoTerminado = false;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                personaje.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espacioPresionado = true;
                    if (puedeDisparar) {
                        disparar();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                personaje.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    espacioPresionado = false;
                }
            }
        });

        recargaTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recargar();
            }
        });
        recargaTimer.setRepeats(false);

        disparoTimer = new Timer(180, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                puedeDisparar = true;
                if (espacioPresionado) {
                    disparar();
                }
            }
        });

        movimientoJefeTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jefe.move();
            }
        });
        movimientoJefeTimer.start();
        fondo = new ImageIcon("src\\recursos\\fondo.jpg"); // Ruta a tu imagen de fondo

    }

    private void seleccionarPersonaje() {
        String[] opciones = {"Héroe", "Villano"};
        int eleccion = JOptionPane.showOptionDialog(this, "Selecciona tu personaje", "Selección de Personaje",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
    
        if (eleccion == JOptionPane.CLOSED_OPTION) {
            System.exit(0); // Salir si se cierra la ventana sin seleccionar
        }
    
        if (eleccion == 0) {
            personaje = new Heroe(50, 300);
            nombrePersonaje = "Goku";
        } else {
            personaje = new Villano(50, 300);
            nombrePersonaje = "Zamasu";
        }
        personaje.setPanelSize(800, 600); // Ajustar tamaño del panel
    }
    
    

    public void updateGame() {
        if (!juegoTerminado) {
            personaje.move();
            ArrayList<Proyectil> proyectilesABorrar = new ArrayList<>();
            for (Proyectil proyectil : proyectiles) {
                proyectil.move();
                if (proyectil.haImpactado(jefe)) {
                    jefe.reducirVida(proyectil.getDaño());
                    proyectilesABorrar.add(proyectil);
                }
            }
            proyectiles.removeAll(proyectilesABorrar);

            // Verificar si el jefe ha sido derrotado
            if (jefe.haSidoDerrotado()) {
                juegoTerminado = true;
                mostrarMensajeVictoria();
            }
        }
    }

    private void disparar() {
        if (balas > 0 && !recargando) {
            String rutaImagen;
            if (personaje instanceof Heroe) {
                rutaImagen = "src\\recursos\\para_heroe.gif"; // Imagen del héroe
            } else {
                rutaImagen = "src\\recursos\\para_villano.gif"; // Imagen del villano
            }
            proyectiles.add(new Proyectil(personaje.getX() + personaje.getAncho(), personaje.getY() + personaje.getAlto() / 2, personaje.getDaño(), rutaImagen));
            balas--;
            puedeDisparar = false;
            disparoTimer.start();
            if (balas == 0) {
                iniciarRecarga();
            }
        }
    }
    
    
    
    
    

    private void iniciarRecarga() {
        recargando = true;
        mostrandoRecargandoMensaje = true;
        recargaTimer.restart();
    }
    

    private void recargar() {
        balas = 30;
        recargando = false;
        mostrandoRecargandoMensaje = false;
    }
    

    private void mostrarMensajeVictoria() {
        JOptionPane.showMessageDialog(this, "¡Enhorabuena! Has derrotado al jefe. Puedes cerrar esta ventana.", "Victoria", JOptionPane.INFORMATION_MESSAGE);
        // Cerrar la aplicación cuando se haga clic en OK
        System.exit(0);
    }
    
    

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Dibujar la imagen de fondo
    g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), null);

    personaje.draw(g);
    jefe.draw(g);
    for (Proyectil proyectil : proyectiles) {
        proyectil.draw(g);
    }

    // Dibujar la vida del jefe centrada en la parte superior
    g.setColor(Color.RED);
    g.setFont(new Font("Arial", Font.BOLD, 20)); // Aumentar tamaño del texto
    String vidaTexto = "Vida de JIREN: " + jefe.getVida();
    int textoAncho = g.getFontMetrics().stringWidth(vidaTexto);
    g.drawString(vidaTexto, (getWidth() - textoAncho) / 2, 20); // Centrar el texto

    // Dibujar el nombre y la vida del personaje encima de la cantidad de balas
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString(nombrePersonaje + " Vida: " + personaje.getVida(), 10, 20);
    
    // Dibujar la cantidad de balas en la esquina superior izquierda
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Balas: " + balas, 10, 50);

    // Mostrar mensaje de recarga
    if (mostrandoRecargandoMensaje) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Cargando energia...", 10, 80);
    }
}



}
