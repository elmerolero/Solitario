/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase Carta
 */
package salas_lopez_ismael_pf;

import java.lang.IllegalArgumentException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.Serializable;

/**
 *
 * @author Ismael Salas López
 * La base de todo este código: las cartas.
 */
public class Carta implements Serializable {

    // Constantes
    public final static int ANCHO_CARTA = 150;  // Una carta es de 150 pixeles de ancho
    public final static int ALTO_CARTA = 210;   // por 210 de alto

    // Atributos de una carta
    private int cara;       // Que número de carta es (As, 2, 3, ..., Joker, Queen, King) 
    private int palo;       // Qué tipo de carta es (Diamantes, tréboles, corazones, picas)
    private boolean revelada;  // La carta se muestra (muestra la cara) o se oculta (se muestra el reverso)
    
    // Extra. Permite que las cartas puedan mostrarse en modo texto
    private final static String[] caras = { "As", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez", "Jocker", "Reina", "Rey" };
    private final static String[] palos = { "Tréboles", "Corazones", "Picas", "Diamantes" };
    
    // La imagen que contiene las cartas, se utiliza buffered image porque
    // permite obtener la imagen por fragmentos
    public static BufferedImage assetCartas;

    // Constructor
    public Carta() {
        this.cara = 0;
        this.palo = 0;
        this.revelada = false;
    }

    // Métodos
    // Permite cargar la imagen que contiene las cartas
    public static void cargarImagenCartas(String directorio) {
        try {
            // Crea una clase archivo con un constructor que requiere un URI para 
            // obtener la imagen que contienen las cartas, después es cargado por
            // la funcion read de la clase ImageIO y se guarda la imagen en assetsCartas
            // para poder mostrar las cartas
            assetCartas = ImageIO.read(new File(directorio));
        } catch (IOException e) {
            ;
        }
    }

    /** Establece la cara de la carta.
     * 
     * @param cara 
     */
    public void setCara(int cara) {
        // Se asegura que sea un número entre 0 y 12 (Trece caras por palo)
        if (cara < 0 || cara > 12) {
            throw new IllegalArgumentException("Se ha especificado un número en un rango inválido para cara (0-13).");
        }

        this.cara = cara;
    }

    /** Establece el palo de la carta.
     * 
     * @param palo 
     */
    public void setPalo(int palo) {
        // Se asegura que sea un número entre 0 y 3 (cuatro palos -Corazones, Diamantes, Espadas, Tréboles-)
        if (palo < 0 || palo > 3) {
            throw new IllegalArgumentException("Se ha especificado un número en un rango inválido para palo (0-3).");
        }

        this.palo = palo;
    }

    /** Establece la condición de la carta.
     *  Indicando si la carta debe mostrar la cara (true) o el reverso (false)
     * 
     * @param revelada 
     */
    public void setRevelada(boolean revelada) 
    {
        this.revelada = revelada;
    }

    /** Retorna la imagen de la cara de la carta correspondiente. Aquí conviene explicar detalladamente el código:
     * Get subimage de la clase Buffered Image (objeto asset cartas) retorna el fragmento de carta indicado por las posiciones X, Y
     * ancho y alto, es decir, supongamos que la carta que quiere obtener es es el 7 de corazones (cara = 6, palo = 1), entonces
     * calculamos la posición X (6 * 150), la posición Y (1 * 210), el ancho sigue siendo 150 y el alto 210.
     * Si usted decidiera usar un asset de cartas diferente debe modificar las constantes ANCHO_CARTA y ALTO_CARTA a sus tamaños correspondientes
     * para asegurarse de que las muestre correctamente y debe mantener el mismo formato.
     * @return 
     */
    public BufferedImage getImagen()
    {
        // Si la carta esta revelada entonces
        if (this.isRevelada()) {
            // Retorna la cara que corresponde a la carta
            return assetCartas.getSubimage(cara * ANCHO_CARTA, palo * ALTO_CARTA, ANCHO_CARTA, ALTO_CARTA);
        }
        
        // Retorna el reverso de la carta
        return getImagenOculta();
    }
    
    /** Devuelve el reverso de la carta.
     * Funciona de la misma manera que getImagen() pero retorna el mismo fragmento siempre.
     * @return 
     */
    public BufferedImage getImagenOculta()
    {
        return assetCartas.getSubimage(0, 4 * ALTO_CARTA, ANCHO_CARTA, ALTO_CARTA);
    }
    
    /** Devuelve la cara correspondiente a la carta.
     * 
     * @return 
     */
    public int getCara()
    {
        return this.cara;
    }

    /** Devuelve el palo correspondiente a la carta.
     * 
     * @return 
     */
    public int getPalo() {
        return this.palo;
    }

    /** Devuelve el estado de la carta.
     * Si está revelada (true) o si está oculta (false)
     * @return 
     */
    public boolean isRevelada()
    {
        return this.revelada;
    }
    
    /** To String.
     * Solo por nostalgia, permite mostrar la carta en modo texto.
     */
    @Override
    public String toString()
    {
        if( isRevelada() )
            return String.format( "%s de %s", caras[ cara ], palos[ palo ] );
        
        return "?";
    }
}
