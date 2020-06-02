/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salas_lopez_ismael_pf;

import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.datatransfer.Transferable;
import java.io.Serializable;

/**
 *
 * @author Ismael Salas López ft Stack Overflow
 * Esta clase permite manejar los eventos soltado. También es una de las clases
 * adaptada del código de Stack Overflow
 * Classe adaptada: DropHandler
 * https://stackoverflow.com/questions/11201734/java-how-to-drag-and-drop-jpanel-with-its-components
 * 
 * Reemplazo la interfaz DropTargetListener y hago que extienda de DropTargetAdapter
 * para implementar las funciones que necesito para que funcione el arratre y el soltado.
 * Bueno, este es el manejador de eventos para el soltado.
 */
public class Suelta extends DropTargetAdapter implements Serializable
{
    // Referencia a sí mismo (el objeto que admite el dropeo del objeto)
    PilaCartas pilaCartas;
    
    // Constructor
    public Suelta( PilaCartas pila )
    {
        this.pilaCartas = pila;
    }
    
    /** Cuando el usuario ejecuta un drop.
     */
    @Override
    public void drop( DropTargetDropEvent evento )
    {
        // ¿Se está soltando un elemento válido?
        if( evento.isDataFlavorSupported( DataFlavorPila.INSTANCIA_COMPARTIDA ) ){
            // Obtenemos el transferable creado en la función dragGestureRecognizer de la clase Arrastra
            Transferable paqueteRecibir = evento.getTransferable();
            
            // Itentamos...
            try{
                // Obtener el objeto PilaCartas que almacena el paquete que contenía el objeto TransferData
                Object pilaCartas = paqueteRecibir.getTransferData( DataFlavorPila.INSTANCIA_COMPARTIDA );
                
                // Convierte el objeto pilaCartas
                PilaCartas cartas = (PilaCartas)pilaCartas;
                
                // Función polimórfica apilarCartas, por eso no es necesario checar
                // que tipo de objeto es con instanceof
                // Apila las cartas, si el apilamiento fue exitoso entonces...
                if( this.pilaCartas.apilarCartas( cartas ) ){
                   // Indica que el drop fue aceptado
                   evento.acceptDrop( DnDConstants.ACTION_MOVE );
                   
                   // Activa la bandera static de que la pila cartas fue transferida exitosamente
                   this.pilaCartas.setTransferido( true );
                }
                else{
                   // Desactiva la bandera static de que la pila cartas fue transferida exitosamente
                   this.pilaCartas.setTransferido( false );
                   
                   // rechaza el drop
                   evento.rejectDrop();
                }
                
                // Vacia la pila con la copia de las cartas
                cartas.vaciar();
                cartas = null;
                
                // Actualiza la pantalla, ya sea si se actualizó o no
                this.pilaCartas.repaint();
            }
            catch( Exception e ){
                // Recha el drop
                evento.rejectDrop();
            }
        }
    }
}
