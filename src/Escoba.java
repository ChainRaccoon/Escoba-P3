import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Escoba {
    private int numJugadores;
    private int cartaJugada;
    private int turnoJugador;
    private boolean cartaColocada;
    private Carta cartaActual;
    private Baraja baraja;

    private JFrame pantalla;
    private JPanel panelCartasMesa;
    private JPanel panelCartasJugador;

    private ArrayList<Jugador> jugadores;
    private ArrayList<Carta> cartasSeleccionadas; // Para almacenar las cartas seleccionadas
    private ArrayList<Carta> cartasMesa;

    public Escoba() {
        numJugadores = 2;
        turnoJugador = 0;
        cartaColocada = false;
        cartasSeleccionadas = new ArrayList<>(); // Inicializar el ArrayList de cartas seleccionadas


        baraja = new Baraja();

        iniciarJugadores();
        iniciarMesa();
        iniciarPantalla();
    }

    //iniciar jugadores
    public void iniciarJugadores() {
        jugadores = new ArrayList<>(numJugadores);

        //crea los jugadores y les da sus cartas
        for (int i = 0; i < numJugadores; i++) {
            jugadores.add(new Jugador(3));
        }

        for (Jugador j : jugadores) {
            for (int i = 0; i < 3; i++) {
                j.jalarCarta(baraja.getCarta());
            }
        }
    }

    //inicia la pantalla
    public void iniciarPantalla() {
        //crea el marco de la pantalla
        pantalla = new JFrame();
        pantalla.setTitle("Escoba");
        pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantalla.setSize(600, 600);

        //crea los paneles donde se guardan los botones
        panelCartasJugador = new JPanel();
        panelCartasJugador.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelCartasMesa = new JPanel();
        panelCartasMesa.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //etiqueta del turno del jugador
        JLabel etiquetaJugador = new JLabel("Turno del jugador: " + (turnoJugador + 1));
        panelCartasJugador.add(etiquetaJugador);

        //inicia los botones del jugador y muestra las cartas de la mesa
        ArrayList<JButton> botonesJugador = crearBotonesMano(jugadores.get(turnoJugador).getManoJugador());
        for (JButton boton : botonesJugador) {
            boton.setPreferredSize(new Dimension(122, 180));
            panelCartasJugador.add(boton); // Añadir cada botón al panel del jugador
        }
        // Añadir los botones de las cartas en la mesa
        mostrarCartasMesa();

        //acomoda cada cosa en su lugar
        pantalla.setLayout(new BorderLayout());
        pantalla.add(panelCartasMesa, BorderLayout.CENTER);
        pantalla.add(panelCartasJugador, BorderLayout.SOUTH);

        pantalla.setVisible(true);
    }

    //convierte un arreglo de cartas en botones
    public ArrayList<JButton> crearBotonesMano(ArrayList<Carta> Cartas) {
        //recibe un arreglo de cartas y crea un arreglo de botones
        ArrayList<JButton> botones = new ArrayList<JButton>(Cartas.size());

        //pasa por cada carta y toma su imagen para el boton
        for (int i = 0; i < Cartas.size(); i++) {
            Carta cartaActual = Cartas.get(i);
            JButton boton = new JButton(cartaActual.getImagenIcon());

            int numCarta = i;

            // Asignar un ActionListener para capturar el clic en el botón
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Carta seleccionada: " + cartaActual.toString());
                    cartaColocada = true;
                    jugarCarta(numCarta);
                }
            });
            botones.add(boton);
        }
        return botones;
    }

    //tomar carta de la mano y colocarla en la mesa
    public void jugarCarta(int numCarta) {
        // Toma la carta jugada
        Carta cartaJugando = jugadores.get(turnoJugador).jugarCarta(numCarta);
        cartaActual = cartaJugando;
        cartasMesa.add(cartaJugando);

        // Actualiza la mesa para mostrar las cartas
        actualizarMesa();

        // El jugador toma cartas para tener 3 cartas de nuevo
        if (baraja.getSize() > 0) {
            jugadores.get(turnoJugador).jalarCarta(baraja.getCarta());
        } else {
            System.out.println("No quedan más cartas");
        }

        // Habilitar selección de cartas en la mesa
        habilitarSeleccionMesa();
    }

    //inicia la mesa con 4 cartas
    public void iniciarMesa() {
        //tamaño inicial de la mesa
        int tamañoMesa = 4;
        cartasMesa = new ArrayList<>();

        //llena la mesa
        for (int i = 0; i < tamañoMesa; i++) {
            cartasMesa.add(baraja.getCarta());
        }
        System.out.println("mesa iniciada");
    }

    //actualizar mesa
    public void actualizarMesa() {
        //elimina la mesa anterior
        panelCartasMesa.removeAll();

        //crea los botones para elegir las cartas
        ArrayList<JButton> botones = crearBotonesMesa(cartasMesa);
        for (JButton boton : botones) {
            boton.setEnabled(cartaColocada);
            panelCartasMesa.add(boton);
        }

        panelCartasMesa.revalidate();
        panelCartasMesa.repaint();
    }

    //seleccion de cartas en la mesa
    public void habilitarSeleccionMesa() {
        // Crea los botones para las cartas en la mesa
        ArrayList<JButton> botonesMesa = crearBotonesMesa(cartasMesa);

        // elimina la mesa anterior
        panelCartasMesa.removeAll();

        // Añade los nuevos botones a la mesa
        for (JButton boton : botonesMesa) {
            panelCartasMesa.add(boton);
        }

        // crear boton para aceptar la seleccion
        JButton botonAceptar = new JButton("Aceptar Selección");
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobarSeleccion();
            }
        });

        panelCartasMesa.add(botonAceptar);

        panelCartasMesa.revalidate();
        panelCartasMesa.repaint();
    }

    //metodo para obtener al ganador
    public void obtenerGanador() {
        int masPuntos = 0;
        int ganador = 0;
        for(int i = 0; i < numJugadores; i++) {
            int puntos = jugadores.get(i).sumarPuntos();
            System.out.println("Jugador "+ i+1 + ": " + puntos+ " puntos");
            if(puntos > masPuntos) {
                masPuntos = puntos;
                ganador = i;
            }
        }
        System.out.println("HA GANADO EL JUGADOR: " + (ganador+1) + " CON " + masPuntos + " PUNTOS");
    }
<<<<<<< Updated upstream

    //muestra la mesa solo como imagenes
    private void mostrarCartasMesa() {
        //elimina la mesa anterior
        panelCartasMesa.removeAll();

        //muestra solo las imagenes de las cartas
        for (Carta carta : cartasMesa) {
            JLabel etiquetaCarta = new JLabel(carta.getImagenIcon());
            panelCartasMesa.add(etiquetaCarta); // Añadir imagen al panel de la mesa
        }

        panelCartasMesa.revalidate();
        panelCartasMesa.repaint();
    }

    //convierte el arreglo de la mesa en botones
    public ArrayList<JButton> crearBotonesMesa(ArrayList<Carta> Cartas) {
        ArrayList<JButton> botones = new ArrayList<>();

        // Pasa por cada carta y toma su imagen para el botón
        for (int i = 0; i < Cartas.size(); i++) {
            Carta cartaActual = Cartas.get(i);
            JButton boton = new JButton(cartaActual.getImagenIcon());

            // Asignar un ActionListener para capturar el clic en el botón
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Alternar la selección de la carta
                    if (cartasSeleccionadas.contains(cartaActual)) {
                        cartasSeleccionadas.remove(cartaActual); // Si ya está seleccionada, deselecciona
                        boton.setBorderPainted(false);
                        boton.setBorder(null);
                    } else {
                        cartasSeleccionadas.add(cartaActual); // Agregar a la selección
                        boton.setBorderPainted(true);
                        boton.setBorder(new LineBorder(Color.RED, 3)); // Cambia el borde a rojo
                    }
                }
            });
            botones.add(boton);
        }

        return botones;
=======
}
//suma todas las cartas elegidas
private void comprobarSeleccion() {
    // if para los distintos casos
    if (!cartasSeleccionadas.isEmpty()) {

        //variable carta para revisar si la carta jugada es seleccionada si no se agrega
        boolean carta;
        // si la carta no esta la agrega
        if (!cartasSeleccionadas.contains(cartaActual)) {
            System.out.println("SIEMPRE debes de tomar tu carta");
            cartasSeleccionadas.add(cartaActual);
        }

        // inicia la suma de las cartas
        int suma = 0;
        for (Carta c : cartasSeleccionadas) {
            suma += c.getValor();
            System.out.println(c.toString());
        }

        if (suma == 15) { //resultado de la suma de cartas correcto
            System.out.println("Suma correcta Total: " + suma);
            //añade las cartas a la pila del jugador y comprueba si se hizo escoba
            boolean escobas;
            if(cartasMesa.size() == 0){
                escobas = true;
            } else {
                escobas = false;
            }
            jugadores.get(turnoJugador).recogerCartasMesa(cartasMesa, escobas);

            // quita las cartas de la mesa
            for(Carta c: cartasSeleccionadas) {
                cartasMesa.remove(c);
            }
        } else {
            System.out.println("Suma incorrecta. Total: " + suma);
        }
    } else {
        System.out.println("No tomaste ninguna carta.");
    }
    cambiarTurno();
}
//cambia datos para el turno del siguiente jugador
private void cambiarTurno() {
    // Cambia el turno al siguiente jugador
    turnoJugador = (turnoJugador + 1) % numJugadores;

    // Actualiza las manos y la mesa para el nuevo jugador
    cartasSeleccionadas.removeAll(cartasSeleccionadas);

    mostrarCartasMesa();
    int cartaManos = 0;
    for(Jugador j: jugadores) {
        cartaManos += j.getManoJugador().size();
    }
    if(cartaManos == 1){
        obtenerGanador();
>>>>>>> Stashed changes
    }
}