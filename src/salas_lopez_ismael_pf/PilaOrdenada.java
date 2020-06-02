/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salas_lopez_ismael_pf;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;

/**
 *
 * @autor Ismael Salas López
 * 
 * Contendrá las cartas de manera ordenada comenzando con la primera (as) y terminando en la última (rey)
 * Pila ordenada, a diferencia de los objetos PilaDesordenada y MazoCartas no implementa
 * la función de arrastre, solo la de soltado.
 */
public class PilaOrdenada extends PilaCartas
{
    // Constante que indica el tamaño máximo de una PilaOrdenada
    private final static int TAMANIO_PILA_ORDENADA = 13;     
    
    // DropTarget permite que el componente pueda aceptar el soltado de items sobre el, 
    // en este caso, al extender de JPanel, lo asocia a este.
    private DropTarget objetivo;
    // Clase que maneja los eventos del soltado.
    private Suelta suelta;   
    
    // Constructor
    public PilaOrdenada()
    {
        super();
            
        // Crea y le asigna su gestor de eventos para el soltado
        suelta = new Suelta( this );
        // El constructor obtiene una referencia al componente que se desea asociar (en este caso así mismo), 
        // el tipo de gesto que maneja (mover de un componente a otro), la clase que maneja el evento y un
        // Valor booleano que indica que está habilitado ya para aceptar el soltado
        objetivo = new DropTarget(this, DnDConstants.ACTION_MOVE, suelta, true);
    }
    
    /** Indica si la pila ya esta completa */
    public boolean estaLlena()
    {
        return getTamanio() == TAMANIO_PILA_ORDENADA;
    }
    
    /** Almacena la carta contenida en la pila cartas dada. Retorna un booleano indicando si la tranferencia fue exitosa o no.Para insertarse una carta en este tipo de pila se deben cumplir las siguientes condiciones:
        1. Si la pila esta vacía, la cara de la carta tiene que ser el número más bajo que hay (0 a nivel dato o As a nivel conceptual del juego)
           de no ser así, entonces debe ser la carta subsiguiente (si hay un as, entonces sigue el dos y así sucesivamente ).
        2. El palo de la cartas a insertarse deben de ser iguales, por ejemplo, si en la pila se inserto un As de
           tréboles, entonces las siguientes cartas deben de ser tréboles.
        3. Una vez insertadas las trece cartas ya no debe ser posible (obviamente) agregar más cartas.
     * @param pilaCartas
     * @return boolean
    */
    @Override
    public boolean apilarCartas( PilaCartas pilaCartas )
    {
        // ¿pila está vacía o guarda más de una carta?
        if( pilaCartas.estaVacia() || pilaCartas.getTamanio() != 1 ){
            // No hay nada que hacer
            return false;
	}

	// En la pila receptora aún no hay cartas
	if( estaVacia() ){
            // ¿La carta a insertar es un As?
            if( pilaCartas.getPrimeraCarta().getCara() == 0 ){
		// Agrega la carta a la pila
		insertarAlInicio( pilaCartas.retirarAlFinal() );

		// Devuelve true
		return true;
            }
	}
        else{
            // ¿La carta a insertar es una unidad mayor a la actual?
            if( getPrimeraCarta().getCara() + 1 == pilaCartas.getPrimeraCarta().getCara() &&
                getPrimeraCarta().getPalo() == pilaCartas.getPrimeraCarta().getPalo() ){
		// Agrega la carta a la pila
		insertarAlInicio( pilaCartas.retirarAlFinal() );

		// Devuelve true
		return true;
            }
        }

        return false;
    }

    /** Permite dibujar las cartas que contenga en la pila (en realidad solo muestra la superior)
     * @param painter
    */
    @Override
    public void paintComponent( Graphics painter )
    {
        // Obtiene la anchura y la altura que debe tener la pila
        int anchoPila = getAnchura();
        int altoPila = getAltura();
        
        // Limpia la pantalla
        painter.setColor( getColorBackground() );
        painter.fillRect( 0, 0, getWidth(), getHeight() );
            
        // Si la pila esta vacía
        if( estaVacia() ){
            painter.setColor( Color.BLACK );
            painter.drawRect( 0, 0, anchoPila - 1, altoPila - 1 );
        }
        else{
            // Dibuja la ultima carta insertada en la pila ordenada
            painter.drawImage( getPrimeraCarta().getImagen(), 0, 0, anchoPila, altoPila, this );
        }
    }
}