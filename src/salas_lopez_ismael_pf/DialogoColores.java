/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase DialogoColores
 */
package salas_lopez_ismael_pf;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Ismael Salas López
 * Cuadro de diálogo que permite modificar el color de fondo del juego
 */
public class DialogoColores extends JDialog
{
    // Referencia al juego sobre el que cambiará sus parámetros
    private Solitario solitario;
    
    // Sliders para el color de fondo personalizado
    private final JSlider selectorRojo;
    private final JSlider selectorVerde;
    private final JSlider selectorAzul;
    
    // Etiquetas para describir los colores de las tablas
    private final JLabel etiquetaRojo;
    private final JLabel etiquetaVerde;
    private final JLabel etiquetaAzul;
    
    public DialogoColores( Solitario solitario )
    {
        // Llama a su superconstructor
        super( solitario, "Cambiar color de fondo", true );
        
        // Establece el la referencia
        this.solitario = solitario;
        
        // Establece el layout, tamaño, prohibe que se cambie el tamaño y lo centra respectivamente
        // Algo que añadir sobre el constructor es que utilizo un GridLayout que es de 6
        // renglones por una columa (para añadir 3 etiquetas y los 3 JSlidrs)
        setLayout( new GridLayout( 6, 1 ) );
        setSize( 300, 200 );
        setResizable( false );
        setLocationRelativeTo( null );
        
        // Objeto que manejara el cambio de los parametros cambio en los jsliders
        // este mismo se agrega en todos los JSlider (addChangeListener()) 
        ChangeHandler cambioColor = new ChangeHandler();
                
        // Crea la etiqueta de color rojo y la añade al cuadro de dialogo
        etiquetaRojo = new JLabel( "Rojo" );
        add( etiquetaRojo );
        
        // Crea el JSlider para seleccionar el valor de rojo y lp agrega al cuadro de dialogo
        // En el ultimo parámetro obtiene el mazo de cartas del solitario para obtener el background (variable estatic de todas
        // las PilasCarta y subclases) y a su vez el valor de color rojo para poner en el selector de color
        selectorRojo = new JSlider( SwingConstants.HORIZONTAL, 0, 255, solitario.getMazoCartas().getColorBackground().getRed() );
        selectorRojo.addChangeListener( cambioColor );
        add( selectorRojo );
        
        // Crea la etiqueta para verde y la añade al cuadro de dialogo
        etiquetaVerde = new JLabel( "Verde" );
        add( etiquetaVerde );
                
        // Crea el JSlider para seleccionar el valor de rojo y lp agrega al cuadro de dialogo
        // En el ultimo parámetro obtiene el mazo de cartas del solitario para obtener el background (variable estatic de todas
        // las PilasCarta y subclases) y a su vez el color verde para poner en el selector de color
        selectorVerde = new JSlider( SwingConstants.HORIZONTAL, 0, 255, solitario.getMazoCartas().getColorBackground().getGreen() );
        selectorVerde.addChangeListener( cambioColor );
        add( selectorVerde );
        
        // Crea la etiqueta para el selctor azul
        etiquetaAzul = new JLabel( "Azul" );
        add( etiquetaAzul );
        
        // Crea el JSlider para seleccionar el valor de rojo y lp agrega al cuadro de dialogo
        // En el ultimo parámetro obtiene el mazo de cartas del solitario para obtener el background (variable estatic de todas
        // las PilasCarta y subclases) y a su vez el color azul para poner en el selector de color
        selectorAzul = new JSlider( SwingConstants.HORIZONTAL, 0, 255, solitario.getMazoCartas().getColorBackground().getBlue() );
        selectorAzul.addChangeListener( cambioColor );
        add( selectorAzul );
        
        setVisible( true );
    }
    
    // Para manejar los Sliders
    private class ChangeHandler implements ChangeListener
    {
        @Override
        public void stateChanged( ChangeEvent event )
        {
            /* Obtiene los valores RGB y los guarda en sus respectivos enteros */
            int r = selectorRojo.getValue();
            int g = selectorVerde.getValue();
            int b = selectorAzul.getValue();
            
            /* Si los valore RGB se encuentra en un rango válido entonces */
            if( ( r >= 0 && r <= 255 ) && ( g >= 0 && g <= 255 ) && ( b >= 0 && b <= 255 ) ){
                // Establece el nuevo background en el juego
                solitario.getPanelJuego().setBackground( new Color( r, g, b ) );
                solitario.getMazoCartas().setColorBackground( new Color( r, g, b ) );
            }
            else{
                // Establece el nuevo background predeterminado
                solitario.getPanelJuego().setBackground( new Color( 25, 149, 0 ) );
                solitario.getMazoCartas().setColorBackground( new Color( 25, 149, 0 ) );
            }
        }
    }
}
