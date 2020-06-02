/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salas_lopez_ismael_pf;

import javax.swing.JPanel;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Ismael Salas López
 * Super clase PilaCartas
 * Esta clase implementa la mayoría de las generalidades y
 * otorga funciones que alguna de las clases heredadas que podría
 * necesitar para efectuar ciertas operaciones específicas. Si bien,
 * funcionalmente no es una pila de cartas, conceptualmente en el juego éstas
 * lucen como tal: pilas de cartas, la clase está diseñada para poder realizar
 * movimientos sobre las cartas de una pila a otra.
 */
public class PilaCartas extends JPanel implements Serializable
{   
    /* CONSTANTES */
    protected static final int TAMANIO_MAXIMO = 52;	// Tamaño máximo de la pila
    protected static final int ESPACIO_CARTAS = 20;	// El espacio entre cartas
    
    /* Tamaños posibles de las pilas que pueden establecerse¨*/
    public static final int TAMANIO_PILA_CHICO = 0;
    public static final int TAMANIO_PILA_MEDIANO = 1;   
    public static final int TAMANIO_PILA_GRANDE = 2;

	/* CAMPOS */
    // La pila de cartas
    private Carta pilaCartas[];

    // Tamaño de la pila
    private int tamanio;

    // Bandera de que indica si la transferencia de cartas entre dos pilas fue exitoso
    private static boolean transferido;

    // Color de fondo de las pilas (lo que se ve detras de las cartas).
    private static Color colorBackground;
    
    // Indice que apunta al tamaño de las cartas 
    // (la que recibe las constantes TAMANIO_PILA_CHICO, MEDIANO y GRANDE)
    private static int tamanioVisualPila;
    
    // Arreglo con el ancho de las cartas (para chica, mediana y grande
    private static final int[] anchuras = { 92, 132, 150 };
    private static final int[] alturas = { 126, 184, 210 };
    
    // Constructor
    public PilaCartas()
    {
        super();
        
    	// Establece el tamaño por defecto de la pila
    	setSize( anchuras[ TAMANIO_PILA_MEDIANO ], alturas[ TAMANIO_PILA_MEDIANO ] );

    	// Crea un arreglo de 52 cartas
    	pilaCartas = new Carta[ TAMANIO_MAXIMO ];

    	// Establece el tamanio de la pila en -1
    	tamanio = 0;

    	// Establece las transferencias en falso
    	transferido = false;
    }

    /* MÉTODOS */
    /** Inserta una carta al inicio de la pila.
     *  Se considera como primer elemento de la pila aquel que fue insertado
     *  en el punto más alto de la pila o posición (tamaño - 1) */
    public void insertarAlInicio( Carta cartaInsertar )
    {
    	// ¿La carta no es una referencia nula y la pila aún tiene espacio ?
    	if( tamanio < TAMANIO_MAXIMO && cartaInsertar != null ){
            // Agrega la carta a la pila
            pilaCartas[ tamanio ] = cartaInsertar;
            // Incrementa el tamaño de la pila en 1
            tamanio = tamanio + 1;
        }
    }

    /** Retira una carta del inicio de la pila.
     *  Se considera como primer elemento de la pila o inicio de pila aquel que
     *  fue insertadoen el punto más alto de la pila o posición (tamaño - 1) */
    public Carta retirarAlInicio()
    {
        // Si la pila no esta vacía
        if( !estaVacia() ){
            // Obtiene la primera carta
            Carta carta = pilaCartas[ getTamanio() - 1 ];
            // Establece en null el subindice en el que estaba la carta que se retiró
            pilaCartas[ getTamanio() - 1 ] = null;
            // Drecrementa tamaño en uno
            tamanio = tamanio - 1;
            return carta;
        }
        
        return null;
    }

    /** Retira una carta del medio de la pila.
     *  Se considera como elemento del medio de la pila o medio de pila aquel que
     *  se encuentra en un rango valido de cartas ( 0 <= subindice < tamanio) */
    public Carta retirarDelMedio( int subindice )
    {
    	Carta carta = null;

    	// Si la pila no está vacía
    	if( !estaVacia() ){
    		// Si el subindice se encuentra en un rango válido
    		if( subindice >= 0 && subindice < getTamanio() ){
    			// Obtiene la carta
    			carta = pilaCartas[ subindice ];

    			// Recorre las cartas que se encuentren por encima de
    			while( subindice < getTamanio() ){
    				pilaCartas[ subindice ] = pilaCartas[ subindice + 1 ];
                                subindice++;
    			}
    			pilaCartas[ getTamanio() - 1 ] = null;
    			tamanio--;
    		}
    	}

    	return carta;
    }

    /** Retira una carta del final de la pila.
     *  Se considera como ultimo elemento de la pila o final de pila aquel que
     *  fue insertado en el punto más bajo de la pila o posición 0 */
    public Carta retirarAlFinal()
    {
        // Si la pila no esta vacía
        if( !estaVacia() ){
            // Obtiene la última carta
            Carta carta = pilaCartas[ 0 ];
            // Recorre las cartas que se encuentren más arriba
            for( int contador = 1; contador < getTamanio(); contador++ ){
                pilaCartas[ contador - 1 ] = pilaCartas[ contador ];
            }
            pilaCartas[ --tamanio ] = null;
            return carta;
        }
        
        return null;
    }

    /** Elimina todos los elementos de la pila, haciendo que ésta quede
      * vacía. */
    public void vaciar()
    {
        // Carta auxiliar para eliminar toda referencia
        Carta cartaAuxiliar;
        
    	// Mientras NO este vacía hacer
    	while( !estaVacia() ){
            // Retira los elementos de uno en uno
            cartaAuxiliar = retirarAlInicio();
            
            // Se asegura que no quede rastro de la carta desechada
            cartaAuxiliar = null;
    	}
    }

    /** Indica si la pila está o no esta vacía. */
    public boolean estaVacia()
    {
        // Se dice que está vacía cuando el tamaño de la pila es cero
    	return getTamanio() == 0;
    }

    /** Obtiene la carta del inicio de la pila. */
    public Carta getPrimeraCarta()
    {
    	// Si la pila NO está vacía
    	if( !estaVacia() ){
    		// Devuelve la primera carta
    		return pilaCartas[ getTamanio() - 1 ];
    	}

    	// Devuelve null en caso contrario
    	return null;
    }

    /** Obtiena la carta de un subíndice dado */
    public Carta getCartaMedio( int subindice )
    {
   	// Si no esta vacía
   	if( !estaVacia() ){
            // Si el subindice se encuentra en un rango adecuado
            if( subindice >= 0 && subindice < getTamanio() && subindice < TAMANIO_MAXIMO ){
   		return pilaCartas[ subindice ];
            }
   	}

   	return null;
   }

    /** Obtiene la carta del final.
     *  
     */
    public Carta getUltimaCarta()
    {
    	// Si la pila NO está vacía entonces
    	if( !estaVacia() ){
    		// Devuelve la ultima carta
    		return pilaCartas[ 0 ];
    	}

    	// Devuelve null en caso contrario
    	return null;
    }

    /** Obtiene el tamaño de la pila.
     *  
     */
    public int getTamanio()
    {
    	return tamanio;
    }

    /** Establece el estado de la tranferencia de cartas */
    public void setTransferido( boolean transferido )
    {
    	this.transferido = transferido;
    }
    
    /** Permite establecer el tamaño VISUAL de las pilas
     *  ya sea chico mediano o grande
     */
    public void setTamanioPila( int tamanio )
    {
        if( tamanio >= TAMANIO_PILA_CHICO && tamanio <= TAMANIO_PILA_GRANDE ){
            this.tamanioVisualPila = tamanio;
        }
        else{
            this.tamanioVisualPila = TAMANIO_PILA_MEDIANO;
        }
    }
    
    /** Obtiene la anchura que la carta debe de tener */
    public int getAnchura()
    {
        return anchuras[ tamanioVisualPila ];
    }
    
    /** Obtiene la altura que la carta debe de tener */
    public int getAltura()
    {
        return alturas[ tamanioVisualPila ];
    }

    /** Obtiene el estado de la transferencia */
    public boolean getTransferido()
    {
    	return transferido;
    }

    /** Permite que los hijos puedan acceder al mazo */
    protected Carta[] getCartas()
    {
    	return pilaCartas;
    }
    
    /** Permite que los hijos puedan obtener el color de fondo
     *  al dibujar su pila de cartas
     */
    public void setColorBackground( Color color )
    {
        this.colorBackground = color;
    }
    
    /** Permite que los hijos puedan obtener el color de fondo
     *  al dibujar su pila de cartas
     */
    public Color getColorBackground()
    {
        return colorBackground;
    }

    /** Permite insertar una pila de cartas sobre
     * clase diseñada con fines de polimorfismo 
     */
    public boolean apilarCartas( PilaCartas pilaCartas )
    {
        return false;
    }
    
    /** Obtiene las cartas que se seleccionaron al realizar un arrastre sobre esa pila.
     * La clase heredada debera crear una pila carta que contenga una copia de las
     * cartas que se desean mover y devolverla, para que la clase que se encarga del
     * arrastre pueda moverlas de una pila a otra.
     * Función diseñada con fines de polimorfismo 
     */
    public PilaCartas getSeleccion()
    {
        return new PilaCartas();
    }
    
    /** Remueve las cartas que fueron movidas.
     * La función 'getSeleccion()' en realidad hizo una copia de las cartas
     * que serían movidas, por lo que si no existiera esta función las cartas movidas
     * se verían tanto en la pila a la que fueron movidas como en la pila de la que provienen
     * por lo que una vez se hizo la transferencia exitosamente hay que removerlas de la pila
     * que provenían. Si va a la clase Arrastra y en la función dragDropEnd() y remueve la llamada 
     * a esta función, recompila y ejecuta el proyecto podrá verlo por usted mismo.
     * clase diseñada con fines de polimorfismo 
     */
    public void removerSeleccion()
    {
        System.out.println("Remove");
    }
    
    /** Cancela la selección para evitar que sean borradas. 
     * Esta función modifica las variables que indican ya sea que el numero de cartas 
     * que se iban a mover es cero o bien, indica a través de un valor booleano
     * que las cartas siempre no deben ser borradas después de que termine el arrastre
     * y soltado (ya sea porque las cartas no cumplieron con las condiciones adecuadas
     * para poder ser movidas o cualquier otra razón que lo impida).
     * clase diseñada con fines de polimorfismo 
     */
    public void cancelarSeleccion()
    {
        System.out.println("Cancel");
    }
}