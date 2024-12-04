# Dragon Ball Super Heroes

## Descripción General

**Dragon Ball Super Heroes** es un juego arcade 2D inspirado en el universo de Dragon Ball. Los jugadores pueden elegir entre ser héroes o villanos y deben derrotar a un jefe enemigo, Jiren. El juego incorpora elementos como movimiento de personajes, disparo de proyectiles, colisiones, y una interfaz gráfica con actualizaciones en tiempo real.

## Pantalla de Inicio de Sesión y Registro

### LoginRegistro.java

**Propósito**: Gestionar la interfaz de inicio de sesión y registro de usuarios.

**Funcionamiento**:
- Muestra una ventana con campos de usuario y contraseña, y botones para iniciar sesión o registrar un nuevo usuario.
- Al iniciar sesión, valida las credenciales con la base de datos y, si son correctas, abre la ventana principal del juego.

**Métodos Clave**:
- `loginBoton.addActionListener`: Acción para validar el usuario y contraseña.
- `registroBoton.addActionListener`: Acción para registrar un nuevo usuario en la base de datos.

### DatabaseManager.java

**Propósito**: Manejar las conexiones y operaciones con la base de datos SQLite.

**Funcionamiento**:
- **Crear Tabla**: Crea la tabla de usuarios si no existe.
- **Registrar Usuario**: Inserta un nuevo usuario en la tabla `usuarios`.
- **Validar Usuario**: Verifica si las credenciales proporcionadas coinciden con un registro en la tabla `usuarios`.

**Métodos Clave**:
- `createTableIfNotExists()`: Crea la tabla de usuarios si no existe.
- `connect()`: Establece la conexión con la base de datos.
- `registrarUsuario(String usuario, String contraseña)`: Inserta un nuevo usuario en la base de datos.
- `validarUsuario(String usuario, String contraseña)`: Verifica las credenciales del usuario.

## Ventana Principal del Juego

### VentanaJuego.java

**Propósito**: Crear la ventana principal del juego donde se desarrollan las acciones.

**Funcionamiento**:
- Configura el tamaño y las propiedades de la ventana.
- Añade el `PanelJuego` donde se dibujan todos los elementos.
- Utiliza un `Timer` para actualizar el estado del juego y redibujar la ventana a intervalos regulares.

**Métodos Clave**:
- `VentanaJuego()`: Constructor que configura la ventana y el panel del juego.
- `timer.start()`: Inicia el temporizador que actualiza el juego cada 16 ms.

## Componentes Gráficos

### PanelJuego.java

**Propósito**: Panel donde se dibujan los elementos del juego y se maneja la lógica principal.

**Funcionamiento**:
- Inicializa el personaje, el jefe, y los proyectiles.
- Maneja el movimiento del personaje y del jefe.
- Dispara proyectiles y verifica colisiones con el jefe.
- Gestiona la recarga de munición y muestra los elementos en la interfaz.

**Métodos Clave**:
- `PanelJuego()`: Constructor que inicializa los componentes del juego.
- `updateGame()`: Actualiza el estado del juego, incluyendo el movimiento y las colisiones.
- `disparar()`: Crea y lanza proyectiles desde el personaje.
- `recargar()`: Gestiona la recarga de munición del personaje.
- `paintComponent(Graphics g)`: Dibuja todos los elementos del juego en el panel.

## Personajes y Entidades del Juego

### Personaje.java

**Propósito**: Clase base para todos los personajes, manejando el movimiento y las interacciones.

**Funcionamiento**:
- Define los atributos comunes como posición, vida, daño, tamaño del panel, etc.
- Controla el movimiento del personaje basado en teclas presionadas.
- Limita el movimiento del personaje dentro del panel.

**Métodos Clave**:
- `move()`: Actualiza la posición del personaje basado en las teclas presionadas.
- `draw(Graphics g)`: Dibuja el personaje en la pantalla.
- `keyPressed(KeyEvent e)`: Registra las teclas presionadas.
- `keyReleased(KeyEvent e)`: Elimina las teclas soltadas.
- `setPanelSize(int ancho, int alto)`: Configura el tamaño del panel donde se mueve el personaje.

### Heroe.java y Villano.java

**Propósito**: Definir los atributos específicos de héroes y villanos, extendiendo de `Personaje`.

**Funcionamiento**:
- **Heroe**:
  - Vida: 100
  - Daño: 10
  - Imagen específica: `heroe_goku.gif`
- **Villano**:
  - Vida: 150
  - Daño: 15
  - Imagen específica: `villano.gif`

**Métodos Clave**:
- `Heroe(int x, int y)`: Constructor que configura los atributos específicos del héroe.
- `Villano(int x, int y)`: Constructor que configura los atributos específicos del villano.
- `draw(Graphics g)`: Dibuja el personaje en la pantalla.

### Jefe.java

**Propósito**: Representar al jefe enemigo, Jiren.

**Funcionamiento**:
- Define atributos como posición, vida, tamaño, y velocidad.
- Implementa patrones de movimiento que cambian según la vida del jefe.
- Reduce la vida del jefe cuando es impactado por un proyectil.

**Métodos Clave**:
- `move()`: Actualiza la posición del jefe basado en patrones de movimiento.
- `draw(Graphics g)`: Dibuja al jefe en la pantalla.
- `reducirVida(int daño)`: Reduce la vida del jefe.
- `haSidoDerrotado()`: Verifica si el jefe ha sido derrotado.

### Proyectil.java

**Propósito**: Representar los ataques lanzados por los personajes.

**Funcionamiento**:
- Define atributos como posición, tamaño, velocidad, daño, y la imagen del proyectil.
- Mueve el proyectil en una dirección constante.
- Verifica si el proyectil impacta al jefe.

**Métodos Clave**:
- `move()`: Actualiza la posición del proyectil.
- `draw(Graphics g)`: Dibuja el proyectil en la pantalla.
- `haImpactado(Jefe jefe)`: Verifica si el proyectil ha impactado al jefe.

## Interfaz de Usuario y Elementos de Jugabilidad

### PanelJuego.java (cont.)

**Vida y Munición**:
- La vida del personaje y del jefe se muestra en la pantalla.
- La cantidad de balas disponibles se muestra en la esquina superior izquierda.
- Un mensaje de "Cargando energía..." se muestra cuando el personaje está recargando.

**Mensajes de Victoria**:
- Cuando el jefe es derrotado, se muestra un mensaje de victoria y el juego finaliza.

## Ciclo de Vida del Juego

1. **Inicio del Juego**: El jugador inicia el juego a través de la pantalla de inicio de sesión y registro.
2. **Selección del Personaje**: Después de iniciar sesión, el jugador selecciona si quiere ser un héroe o un villano.
3. **Inicio de la Partida**: La ventana del juego se abre y comienza el ciclo de actualización. El personaje y el jefe aparecen en sus posiciones iniciales.
4. **Interacción**: El jugador mueve al personaje utilizando el teclado y dispara proyectiles al jefe. El jefe se mueve con patrones de movimiento que varían según su vida.
5. **Victoria**: El jugador debe reducir la vida del jefe a cero para ganar. Cuando esto sucede, se muestra un mensaje de victoria y el juego termina.


