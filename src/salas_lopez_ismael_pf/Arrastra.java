/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase Arrastra
 */
package salas_lopez_ismael_pf;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSource;
import java.awt.Cursor;
import java.awt.dnd.DragSourceEvent;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author Ismael Salas López ft Stack Overflow
 * La verdad es que esta es una clase adaptada que conseguí en Stack Overflow
 * debido a que es más complicado usar Drag and Drop en componentes como JPanels o no lo
 * implementan por defecto (sin contar con lo malos que son los tutoriales de Oracle para
 * explicar sus propios componentes en algunos casos). Si bien, el código podría considrerse copia
 * algunas formas en la que se hacen ciertas cosas difieren, si estudia el código del enlace podrá ver por qué.
 * Adjunto el enlace al código.
 * https://stackoverflow.com/questions/11201734/java-how-to-drag-and-drop-jpanel-with-its-components
 * Adaptación de DragGestureHandler
 */
public class Arrastra  implements DragGestureListener, DragSourceListener
{   
    // Referencia a la Pila que implementa la clase Arrastra
    private PilaCartas referencia;
    
    // Constructor
    public Arrastra( PilaCartas referencia )
    {
        this.referencia = referencia;
    }
    
    /** Obtiene la referencia */
    public PilaCartas getPilaCartas()
    {
        return referencia;
    }
    
    /** Registra el evento del arrastre.
     *  Cuando el usuario selecciona una de las pilas de cartas
     *  que integren el manejador de eventos e intenta arrastrarlo,
     *  se ejecuta esta función, que obtiene las cartas que serán movidas
     *  (el cómo lo hace se explica en la clase PilaCartas) y las empaca
     *  para ser transferidas.
     * @param evento 
     */
    @Override
    public void dragGestureRecognized( DragGestureEvent evento )
    {
        // Obtiene la pila de cartas que va a mover, lo hace obteniendo
        // la referencia de la Pila sobre la que se invocó el evento 
        // y llamando a su función getSelección()
        PilaCartas pilaAEnviar = getPilaCartas().getSeleccion();
        
        // Crea el paquete de cartas que se van a enviar
        Transferable paqueteAMover = new PilaTransferible( pilaAEnviar );
        
        // Inicia el proceso de arrastrar
        DragSource fuente = evento.getDragSource();
        fuente.startDrag( evento, Cursor.getPredefinedCursor( Cursor.MOVE_CURSOR ), paqueteAMover, this );
    }
    
    /** Clase de la interfaz no utilizada. */
    @Override
    public void dragEnter(DragSourceDragEvent evento )
    {
    }

    /** Clase de la interfaz no utilizada. */
    @Override
    public void dragOver(DragSourceDragEvent evento )
    {
    }

    /** Clase de la interfaz no utilizada. */
    @Override
    public void dropActionChanged(DragSourceDragEvent evento)
    {
    }

    /** Clase de la interfaz no utilizada. */
    @Override
    public void dragExit(DragSourceEvent evento ) 
    {    
    }
    
    /** Se ejecuta una vez que el proceso de arrastrado y soltado termina. 
     *  Verifica si se hizo la transferencia o no para remover las cartas
     *  que fueron copiadas, además de actualizar las nuevas posiciones y tamaños de
     *  las pilas.
     * También es el momento adecuado para verificar si el usuario ganó o perdió.
     */
    @Override
    public void dragDropEnd( DragSourceDropEvent evento )
    {
        // El soltado de los elementos fue exitoso
        if( getPilaCartas().getTransferido() ){
            // Función polimórfica de pilacartas: Elimina la selección dada
            getPilaCartas().removerSeleccion();
        }
        
        // Función polimórfica de PilaCartas: reinicia las variables de selección
        getPilaCartas().cancelarSeleccion();
        
        // Reinicia la variable tranferido
        getPilaCartas().setTransferido( false );
        
        // Actualiza el background (esto porque estuve experimentando 
        // cosas raras con los backgrounds)
        getPilaCartas().setBackground( getPilaCartas().getBackground() );
        
        // Actualiza la pantalla
        getPilaCartas().repaint();
        
        // Actualiza las nuevas posiciones y los tamaños de las pilas
        Aplicacion.solitario.actualizarPosiciones();
        
        // Revisa si el usuario ganó
        Aplicacion.solitario.victoria();
    }
}
