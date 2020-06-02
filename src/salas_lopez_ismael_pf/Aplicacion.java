/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase Aplicacion (main)
 */
package salas_lopez_ismael_pf;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Ismael Salas López
 * Contiene la funcion main que da inicio a la ejecución del programa
 */
public class Aplicacion
{
    public static Solitario solitario;
    
    public static void main( String args[] ){
        solitario = new Solitario();
        solitario.getMazoCartas().setTamanioPila( 1 );
        solitario.getMazoCartas().setColorBackground( new Color( 25, 149, 0 ) );
        solitario.setBackground( new Color( 25, 149, 0 ) );
        solitario.actualizarPosiciones();
        solitario.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        // Obtiene las dimensiones de la pantalla
        Dimension dimensiones = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Centra la ventana
        // Busqué la manera y parece ser muy rudimentaria, no mencionaron una funcion como setLocationRelativeTo( null )
        // pero al usar la mencionada, la ventana se dibuja desde el punto medio de la pantalla por lo que se usa la forma
        // rudimentaria que consiste en calcular las posiciones de la pantalla y para centrarla en el eje X
        // se resta al ancho de la pantalla al ancho de la ventana y se divide entre dos, lo mismo de manera vertical
        // pero utilizando tanto la altura de la pantalla como la altura de la ventana.
        solitario.setLocation( ( dimensiones.width - solitario.getWidth() ) / 2, ( dimensiones.height - solitario.getHeight() ) / 2);
        solitario.setVisible( true );
    }
}
