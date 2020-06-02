/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase PanelVictoria
 */
package salas_lopez_ismael_pf;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author Ismael Salas López
 * 
 * Panel que muestra una felicitación con pequeños efectos después de ganar.
 * Este panel solo muestra la palabra felicidades una vez que se oculta el panel 
 * en el que están las cartas.
 */
public class PanelVictoria extends JPanel
{
    @Override
    public void paintComponent( Graphics painter )
    {
        // 'Limpia' la pantalla
        painter.setColor( Aplicacion.solitario.getMazoCartas().getColorBackground() );
        painter.fillRect( 0, 0, getWidth(), getHeight() );
        
        // Establece la fuente
        Font fuente = new Font( "Arial", Font.BOLD, 50 );
        painter.setFont( fuente );
        
        // Escribe el texto de felicitación
        painter.setColor( Color.WHITE );
        painter.drawString( "Felicidades", ( getWidth() - 250 ) / 2, ( getHeight() - 30 ) / 2 );
    }
}
