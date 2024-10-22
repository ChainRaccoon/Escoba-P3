import java.util.ArrayList;
import java.util.Random;

public class Baraja {

    private ArrayList<Carta> baraja;

    //constructor de una baraja española
    public Baraja() {
        baraja = new ArrayList<>();
        String[] palos = {"oros", "copas", "espadas", "bastos"};
        int[] valores = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};

        for (String palo : palos) {
            for (int valor : valores) {
                baraja.add(new Carta(valor, palo));
            }
        }
    }

    //tomar una carta de la baraja
    public Carta getCarta() {
        Random random = new Random();
        //remueve una carta aleatoria
        return baraja.remove(random.nextInt(baraja.size()));
    }

    //Numero de cartas restantes
    public int getSize(){
        return baraja.size();
    }
}