package nombreDelPaquete;

import java.awt.*;

public class Proyectil {
    private int x, y;
    private int ancho = 5, alto = 5;
    private int velocidad = 5;
    private int daño = 5; // Daño del proyectil

    public Proyectil(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += velocidad;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, ancho, alto);
    }

    public boolean haImpactado(Jefe jefe) {
        return x + ancho >= jefe.getX() && y >= jefe.getY() && y <= jefe.getY() + jefe.getAlto();
    }

    public int getDaño() {
        return daño;
    }
}
