/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase DataFlavor
 */
package salas_lopez_ismael_pf;

import java.awt.datatransfer.DataFlavor;

/**
 *
 * @author Ismael Salas López feat StackOverflow
 * Esta clase crea un tipo DataFlavor el cual establece que el contenido
 * a transferir es una clase JPanel. Esto solo es para indicar al framework
 * de Drag and Drop qué tipo de contenido está siendo tranferido (en esta caso
 * se está tranfiriendo una clase JPanel ). También forma parte de los códigos adaptados para 
 * drag and drop.
 * Enlace al sitio. Clase adaptada: PanelDataFlavor
 * https://stackoverflow.com/questions/11201734/java-how-to-drag-and-drop-jpanel-with-its-components
 */
public class DataFlavorPila extends DataFlavor
{
    // Evita que se hagan copias de una misma cosa así puede ser accedida por aquellos
    // que definan una clase de este tipo
    public static final DataFlavorPila INSTANCIA_COMPARTIDA = new DataFlavorPila();

    // Constructor
    public DataFlavorPila() 
    {
        // Utiliza el constructor padre para indicar el tipo de clase que
        // envuelve y el null debería ser un parámetro string que indica
        // el nombre del DataFlavor en forma leíble por humanos.
        super( PilaCartas.class, null );
    }

}