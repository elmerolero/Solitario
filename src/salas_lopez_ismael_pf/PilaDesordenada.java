/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salas_lopez_ismael_pf;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 *
 * @autor Ismael Salas López
 * Las pilas desordenadas, simplemente reciben de una a siete cartas.
 * 
 */
public class PilaDesordenada extends PilaCartas
{
    // Tamaño de la seleccion
    private int tamanioSeleccion;
    
    // Permiten implementar el arrastre
    // Clase abstracta que habilita al componente manejar eventos de arrastre
    private DragGestureRecognizer gestorArrastre;   
    // Al manejador de eventos del arrastre
    private Arrastra arrastra;      
    
    // Habilitan en la clase la posibilidad de soltar
    // DropTarget permite que el componente pueda aceptar el soltado de items sobre el, 
    // en este caso, al extender de JPanel, lo asocia a este.
    private DropTarget objetivo;
    // Clase que maneja los eventos del soltado.
    private Suelta suelta;          

    // Constructor
    public PilaDesordenada()
    {
	super();

        // Inicializa el tamaño de la seleccion en cero
        tamanioSeleccion = 0;
        
        // Crea el manejador de eventos para el arrastre
        arrastra = new Arrastra( this );
        // Esta función crea una subclase genérica que permite reconocer el arrastre y recibe tres parámetros
        // la referencia a sí mismo para asociarlo, la constante que indica el tipo de gesto que maneja y
        // el manejador de eventos que lo procesará
        gestorArrastre = DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer( this, DnDConstants.ACTION_MOVE, arrastra );
        
        // Inicializa los campos que permiten la opcion de soltar
        // Crea y le asigna su gestor de eventos para el soltado
        suelta = new Suelta( this );
        // El constructor obtiene una referencia al componente que se desea asociar (en este caso a sí mismo), 
        // el tipo de gesto que maneja (mover de un componente a otro), la clase que maneja el evento y un
        // Valor booleano que indica que está habilitado ya para aceptar el soltado
        objetivo = new DropTarget(this, DnDConstants.ACTION_MOVE, suelta, true);
        
        // Agrega evento para revelar las cartas
        addMouseListener( new GestorMouse() );
    }

    /** Permite agregar un grupo de cartas contenidas en otra PilaCartas 
     *  Retorna un valor booleano que indica se se hizo la tranferencia o no 
     *  las cartas a insertar respecto a la anterior deben cumplir las siguientes
     *  condiciones.
     *  1. Las cartas seleccionadas deben estar reveladas.
     *  2. La carta a insertar es de diferente color (si era negra, la siguiente debe ser roja).
     *  3. La cara es una unidad más pequeña que la anterior (si era 6, la siguiente a insertar debe ser un cinco. */
    @Override
    public boolean apilarCartas( PilaCartas pilaCartas )
    {
        // La pila a insertar no tiene cartas
        if( pilaCartas.estaVacia() ){
            // No inserta nada
            return false;
        }

        // ¿La pila en la que se agregarán las cartas esta vacía?
        if( estaVacia() ){
            // Mientras la pila con las cartas a agregar tenga cartas hacer
            while( !pilaCartas.estaVacia() ){
                // Retira la carta de la pila y la inserta en la pila receptora
                insertarAlInicio( pilaCartas.retirarAlFinal() );
            }

            // Devuelve true
            return true;
        }
        else{
            // ¿Se cumplen las condiciones indicadas en la descripción de arriba?
            if( getPrimeraCarta().isRevelada() && 
                getPrimeraCarta().getPalo() % 2 != pilaCartas.getUltimaCarta().getPalo() % 2 &&
                getPrimeraCarta().getCara() - 1 == pilaCartas.getUltimaCarta().getCara() ){
                // Mientras la pila con las cartas a agregar tenga cartas hacer
                while( !pilaCartas.estaVacia() ){
                    // Retira una carta de la pila con las cartas a insertar y se agrega en la pila
                    // que la recibe
                    insertarAlInicio( pilaCartas.retirarAlFinal() );
                }
                return true;
            }
        }

        return false;
    }

    /** Obtiene el tamaño de la selección dada la posición del mouse
     * @return  */
    @Override
    public PilaCartas getSeleccion()
    {
        // Pila con las cartas seleccionadas
        PilaCartas seleccion = new PilaCartas();

        // Posición Y del mouse         
        int mouseY = (int)getMousePosition().getY();    

        // Si la pila NO está vacía
        if( !estaVacia() ){
            // Selecciona la cantidad de cartas que el usuario está seleccionando
            int contador = getTamanio() - 1;
            while( mouseY < ( 20 * contador ) ){
                contador--;         // Decrementa el contador
            }

            // Calcula la cantidad de cartas seleccionadas
            int cantidadCartas = tamanioSeleccion = getTamanio() - contador;

            // Variable auxiliar que apunta a la carta que será insertada
            Carta cartaAuxiliar;
            
            // Mientras el tamaño de la selección sea mayor que cero
            while( cantidadCartas > 0 ){
                // Obtiene la carta que va a ser insertada
                cartaAuxiliar = getCartaMedio( getTamanio() - cantidadCartas );

                // ¿Es la primera carta a insertar?
                if( seleccion.estaVacia() ){
                    // ¿La primera carta a insertar esta revelada?
                    if( cartaAuxiliar.isRevelada() ){
                        seleccion.insertarAlInicio( cartaAuxiliar );
                    }
                    else{
                        // Establece el tamaño de la selcción en 0
                        tamanioSeleccion = 0;
                            
                        // Sale del ciclo sin mover nada más
                        break;
                    }
                }
                else{
                    // ¿Las cartas seleccionadas están reveladas,  
                    // son de diferente color (si la anterior era negra, la siguiente a insertar debe ser roja) y
                    // la cara es una unidad más pequeña que la anterior (si era 6, la siguiente a insertar debe ser un cinco)?
                    if( cartaAuxiliar.isRevelada() &&
                        ( seleccion.getPrimeraCarta().getPalo() % 2 !=  cartaAuxiliar.getPalo() % 2 ) &&
                        ( seleccion.getPrimeraCarta().getCara() - 1 == cartaAuxiliar.getCara() ) ){
                        seleccion.insertarAlInicio( cartaAuxiliar );
                    }
                    // No cumplió las condiciones
                    else{
                        // Vacía la pila a tranferir
                        seleccion.vaciar();
                            
                        // Establece el tamaño de la selcción en 0
                        tamanioSeleccion = 0;
                            
                        // Sale del bucle
                        break;
                    }
                }
                cantidadCartas--;
            }
        }

        return seleccion;
    } 

    /** Cancela la selección estableciendo el tamaño de la selección en cero.
     * La cantidad de cartas seleccionadas se estableció en getSeleccion() 
     */
    @Override
    public void cancelarSeleccion()
    {
        tamanioSeleccion = 0;
    }

    /** Remueve la cantidad de cartas seleccionadas.
     * La cantidad de cartas seleccionadas se estableció en getSeleccion() 
     */
    @Override
    public void removerSeleccion()
    {
        // Mientras el tamaño de la selección estè
        while( tamanioSeleccion > 0 ){
            // Retira cartas
            retirarAlInicio();
            tamanioSeleccion--;
        }
    }

    /** Dibuja las cartas que contiene esta pila.
     * @param painter 
     */
    @Override
    public void paintComponent( Graphics painter )
    {
        // Ancho de la carta
        int anchoPila = getAnchura();
        int altoPila = getAltura();
        
        // Limpia la pantalla
        painter.setColor( getColorBackground() );
        painter.fillRect( 0, 0, getWidth(), getHeight() );
        
        // Si la pila esta vacia
        if( estaVacia() ){
            // Establece el color del pintor en negro
            painter.setColor( Color.BLACK );
            // Dibuja un rectángulo que representa que esa pila está vacía
            painter.drawRect( 0, 0, anchoPila - 1, altoPila - 4 );
        }
        else{
            // Dibuja cada una de las cartas
            for( int contador = 0; contador < getTamanio(); contador++ ){
                painter.drawImage( getCartaMedio( contador ).getImagen(), 0, contador * PilaCartas.ESPACIO_CARTAS, anchoPila, altoPila, this );
            }
        }
    }
    
    private class GestorMouse extends MouseAdapter
    {
        public GestorMouse(){
            super();
        }
        
        @Override
        public void mouseClicked( MouseEvent event ){
            if( !estaVacia() ){
                if( getPrimeraCarta().isRevelada() == false ){
                    getPrimeraCarta().setRevelada( true );
                    repaint();
                }
            }
        }
    }
}
