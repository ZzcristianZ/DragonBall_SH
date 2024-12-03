package nombreDelPaquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nombreDelPaquete.DatabaseManager;

public class LoginRegistro extends JFrame {
    private JTextField usuarioCampo;
    private JPasswordField contraseñaCampo;
    private JButton loginBoton;
    private JButton registroBoton;

    public LoginRegistro() {
        setTitle("Login y Registro");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel para los campos de texto y botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Campos de texto
        panel.add(new JLabel("Usuario:"));
        usuarioCampo = new JTextField();
        panel.add(usuarioCampo);

        panel.add(new JLabel("Contraseña:"));
        contraseñaCampo = new JPasswordField();
        panel.add(contraseñaCampo);

        // Botones
        loginBoton = new JButton("Iniciar Sesión");
        registroBoton = new JButton("Registrar");

        panel.add(loginBoton);
        panel.add(registroBoton);

        // Añadir el panel al frame
        add(panel, BorderLayout.CENTER);

        // Acciones de los botones
        loginBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioCampo.getText();
                String contraseña = new String(contraseñaCampo.getPassword());

                DatabaseManager dbManager = new DatabaseManager();
                boolean valido = dbManager.validarUsuario(usuario, contraseña);

                if (valido) {
                    JOptionPane.showMessageDialog(LoginRegistro.this, "Inicio de sesión exitoso.");
                    // Aquí abres la ventana principal del juego directamente
                    new VentanaJuego().setVisible(true);
                    dispose(); // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(LoginRegistro.this, "Usuario o contraseña incorrectos.");
                }
            }
        });

        registroBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioCampo.getText();
                String contraseña = new String(contraseñaCampo.getPassword());

                DatabaseManager dbManager = new DatabaseManager();
                boolean exito = dbManager.registrarUsuario(usuario, contraseña);

                if (exito) {
                    JOptionPane.showMessageDialog(LoginRegistro.this, "Usuario registrado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(LoginRegistro.this, "Error al registrar usuario.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegistro().setVisible(true);
            }
        });
    }
}
