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
}