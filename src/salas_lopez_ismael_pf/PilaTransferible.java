/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salas_lopez_ismael_pf;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author Ismael Salas López ft StackOverflow
 * Esta clase permite que empaquetar la clase de manera que pueda transferirse la Pila de Cartas
 * es una de las clases adaptadas de la misma fuente citada en todas las clases.
 * https://stackoverflow.com/questions/11201734/java-how-to-drag-and-drop-jpanel-with-its-components
 */
public class PilaTransferible implements Transferable 
{
    // Arreglo de "sabores" que permite transferir la clase PilaTranferible
    private DataFlavor[] flavors = new DataFlavor[]{ DataFlavorPila.INSTANCIA_COMPARTIDA };
    
    // La pila cartas que es transferida en el proceso de drag and drop
    private PilaCartas pilaCartas;
    
    // Constructor
    public PilaTransferible( PilaCartas pila )
    {
        this.pilaCartas = pila;
    }
    
    /** Obtiene el arreglo con los tipos de datos que puede transferir la clase.
     * En este caso solo puede hacer transferencias de tipo PilaCartas (clase flavor DataFlavorPila )
     * @return 
     */
    @Override
    public DataFlavor[] getTransferDataFlavors()
    {
        return flavors;
    }
    
    /** Devuelve verdadero si el data flavor indicado es válido.
     * En este caso solo puede hacer transferencias de tipo PilaCartas (clase flavor DataFlavorPila )
     * @return 
     */
    @Override
    public boolean isDataFlavorSupported( DataFlavor flavor )
    {
        // Bandera que indica si el DataFlavor dado es compatible
        boolean soportado = false;
        
        // Itera por cada uno de los elementos que contenga el arreglo 'flavors' para
        // verificar que el data flavor dado coincide con alguno de los
        // dataflavors del arreglo
        for( DataFlavor miDataFlavor : getTransferDataFlavors() ){
            if( miDataFlavor.equals( flavor ) ){
                soportado = true;
                break;
            }
        }
        
        return soportado;
    }
    
    // Función que permite obtene la pila de cartas transferida
    public PilaCartas getPilaCartas()
    {
        return pilaCartas;
    }
    
    /** Retorna el objeto (en este caso la pila de cartas). El objeto que fue transferido
     * durante el proceso de drag and drop (aunque tecnicamente podría utilizarse
     * para hacer transferencias de otro tipo que utilicen trasferable).
     * Especifica que el objeto solicitado sea del tipo especificado por la clase
     * DataFlavor para que esto ocurra.
     */
    @Override
    public Object getTransferData( DataFlavor flavor ) throws UnsupportedFlavorException, IOException
    {
        // Declaramos el objeto que va a ser devuelto
        Object objeto = null;
        
        // ¿El DataFlavor es del mismo tipo que del objeto que se va a devolver?
        if( isDataFlavorSupported( flavor ) ){
            // Asigna el objeto solicitado
            objeto = getPilaCartas();
        }
        else{
            // Lanza una excepción que indica que el tipo de objeto solicitado
            // no es igual
            throw new UnsupportedFlavorException( flavor );
        }
        
        // Devuelve el objeto que contiene la clase
        return objeto;
    }
}  
