import javax.swing.*;
import java.awt.*;

public class Carta {
    private int valor;
    private String palo;
    private ImageIcon imagen;

    //constructor de la carta
    public Carta(int valor, String palo) {
        this.valor = valor;
        this.palo = palo;

        String direccionImagen = ("baraja/"+valor+"_"+palo+".jpg");
        ImageIcon iconoOriginal = new ImageIcon(direccionImagen);

        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(122, 180, Image.SCALE_SMOOTH);

        if(valor>7){
            this.valor = valor-2;
        }
        imagen = new ImageIcon(imagenEscalada);
    }

    //obtener imagen de la carta
    public ImageIcon getImagenIcon(){
        return imagen;
    }

    //obtener valor de la carta
    public int getValor() {
        return valor;
    }

    //obtener palo de la carta
    public String getPalo() {
        return palo;
    }

    //convertir a String
    public String toString(){
        return getValor() + " de " + getPalo();
    }
}
