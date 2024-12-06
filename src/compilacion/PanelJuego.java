package compilacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PanelJuego extends JPanel {

    public static ArrayList<Proyectil> proyectiles;  
    private Personaje personaje;
    private Jefe jefe;
    private int balas;
    private boolean recargando;
    private boolean puedeDisparar;
    private boolean espacioPresionado;
    private boolean juegoTerminado;
    private Timer recargaTimer;
    private Timer disparoTimer;
    private Timer movimientoJefeTimer;
    private Timer habilitarKamehamehaTimer;
    private Timer habilitarCaosTimer;
    private boolean puedeUsarKamehameha = true; 
    private boolean puedeUsarCaos = true; 
    private ImageIcon fondo;
    private String nombrePersonaje;
    private boolean mostrandoRecargandoMensaje = false;

    public PanelJuego() {
        setFocusable(true);
        setBackground(Color.BLACK);
        
        jefe = new Jefe(650, 250, 500); 
        jefe.setPanelSize(800, 600);

        seleccionarPersonaje();

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
                if (personaje instanceof Heroe) {
                    Heroe heroe = (Heroe) personaje;
                    if (e.getKeyCode() == KeyEvent.VK_E && puedeUsarKamehameha && heroe.getKamehamehaUsos() < Heroe.getKamehamehaMaxUsos()) {
                        heroe.kamehameha();
                        puedeUsarKamehameha = false;
                        habilitarKamehamehaTimer = new Timer(5000, _ -> {
                            puedeUsarKamehameha = true;
                        });
                        habilitarKamehamehaTimer.setRepeats(false);
                        habilitarKamehamehaTimer.start();
                    }
                }
                if (personaje instanceof Villano) {
                    Villano villano = (Villano) personaje;
                    if (e.getKeyCode() == KeyEvent.VK_E && puedeUsarCaos && villano.getCaosUsos() < Villano.getCaosMaxUsos()) {
                        villano.explosionCaos();
                        puedeUsarCaos = false;
                        habilitarCaosTimer = new Timer(5000, _ -> {
                            puedeUsarCaos = true;
                        });
                        habilitarCaosTimer.setRepeats(false);
                        habilitarCaosTimer.start();
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

        recargaTimer = new Timer(3000, _ -> recargar());
        recargaTimer.setRepeats(false);

        disparoTimer = new Timer(180, _ -> {
            puedeDisparar = true;
            if (espacioPresionado) {
                disparar();
            }
        });

        movimientoJefeTimer = new Timer(50, _ -> jefe.move());
        movimientoJefeTimer.start();
        fondo = new ImageIcon("src\\recursos\\fondo.jpg");
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
            personaje = new Villano(50, 300, jefe); // Asegúrate de pasar jefe aquí
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
        JOptionPane.showMessageDialog(this, "🎉¡Enhorabuena!🎉 Has derrotado al jefe.", "Victoria", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), null);

        personaje.draw(g);
        jefe.draw(g);
        for (Proyectil proyectil : proyectiles) {
            proyectil.draw(g);
        }

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String vidaTexto = "Vida de JIREN: " + jefe.getVida();
        int textoAncho = g.getFontMetrics().stringWidth(vidaTexto);
        g.drawString(vidaTexto, (getWidth() - textoAncho) / 2, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(nombrePersonaje + " Vida: " + personaje.getVida(), 10, 20);
        
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Balas: " + balas, 10, 50);

        if (personaje instanceof Heroe) {
            Heroe heroe = (Heroe) personaje;
            g.drawString("Super: " + (Heroe.getKamehamehaMaxUsos() - heroe.getKamehamehaUsos()), 10, 80);
        }else if (personaje instanceof Villano) { 
            Villano villano = (Villano) personaje;
            g.drawString("Caos: " + (Villano.getCaosMaxUsos() - villano.getCaosUsos()), 10, 80); 
        }

        if (mostrandoRecargandoMensaje) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Cargando energia...", 10, 110);
        }
    }
}
