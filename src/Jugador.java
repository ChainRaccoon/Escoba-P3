import java.util.ArrayList;

public class Jugador {

    public ArrayList<Carta> mano;
    public int puntos;
    public ArrayList<Carta> cartasRecogidas;
    public int escobas;

    public Jugador(int numCartas) {
        puntos = 0;
        cartasRecogidas = new ArrayList<>();
        mano = new ArrayList<>(3);
        escobas = 0;
    }

    public Carta jugarCarta(int numCarta) {
        Carta carta = mano.remove(numCarta);
        return carta;
    }

    public void jalarCarta(Carta carta) {
        mano.add(carta);
        System.out.println("Carta añadida");
    }

    public void recogerCartasMesa(ArrayList<Carta> mesa, boolean escoba) {
        for (int i = 0; i < mesa.size(); i++) {
            cartasRecogidas.add(mesa.get(i));
        }
        if (escoba) {
            System.out.println("Recogida escoba");
            escobas++;
        } else {
            System.out.println("Cartas recogidas " + mesa.size());
        }
    }

    public ArrayList<Carta> getManoJugador() {
        return mano;
    }

    public int sumarPuntos() {
        int puntos = 0;
        puntos += escobas*3;

        //revisa cada carta para sumar puntos
        for (Carta carta : cartasRecogidas) {
            //suma el valor de la carta en puntos si es figura no suma
            int valor = carta.getValor();
            String palo = carta.getPalo();

            //suma el valor nominal si no es figura
            if(valor == 7 || palo == "oros"){
                puntos += 7;
            } else {
                switch (valor) {
                    case 1, 2, 3, 4, 5, 6, 7:
                        puntos += carta.getValor();
                        break;
                }
            }
        }
        return puntos;
    }
}