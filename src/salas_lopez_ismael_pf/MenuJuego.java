/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase MenuJuego
 */
package salas_lopez_ismael_pf;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Ismael Salas López
 * 
 * Esta es la implementación de la barra de menú para el solitario
 * permitirá guardar la partida, salir del juego, obtener mis datos de alumno
 * y un pequeño acerca de.
 * 
 */
public class MenuJuego extends JMenuBar
{
    // CONSTANTES
    private final int TAMANIOS_DISPONIBLES = 3; // Hay tres tamaños disponibles: Chico, Mediano, Grande
    
    // Menu juego
    private JMenu menuJuego;
    private JMenuItem nuevaPartida;     // La opción de iniciar una nueva partida
    private JMenuItem guardarPartida;   // La opción de guardar la partida en curso
    private JMenuItem cargarPartida;    // La opción de guardar la partida en curso
    private JMenuItem salirPartida;     // La opción para salir del juego
    
    // Menu Ver
    private JMenu menuVer;
    private JMenuItem colorFondo;       // La opción que permite establece el background
    private JMenu setTamanio;           // La opción que permite establecer el tamaño de las cartas
    private JRadioButtonMenuItem[] tamanios;    // Opciones para el tamño de las cartas
    private final String[] textoTamanios = { "Chico", "Mediano", "Grande" };    // El texto para el tamaño de las cartas
    private ButtonGroup grupoTamanios;  // Unifica lógicamente las opciones de tamaños
    
    // Menu Información
    private JMenu menuInformacion;
    private JMenuItem acercaDe;         // La opción acerca de
    
    // Constructor juego
    public MenuJuego()
    {
        // Llamamos a super
        super();
        
        // Crea el menu juego
        menuJuego = new JMenu( "Juego" );
        
        // Crea la opcion de nueva partida
        nuevaPartida = new JMenuItem( "Nueva Partida" );
        // Agrega una classe action listener para ejecutar una acción al ser seleccionado
        nuevaPartida.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent evento ){
                    // Llama a iniciar juego para reiniciar la partida
                    Aplicacion.solitario.iniciarJuego();
                }
            }
        );
        
        // Añade al menu 'Juego' la opcion nueva partida
        menuJuego.add( nuevaPartida );
        
        // Crea la opción de guardar partida (aún no implementada)
        guardarPartida = new JMenuItem( "Guardar Partida" );
        guardarPartida.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent evento ){
                    // Llama a iniciar juego para reiniciar la partida
                    Aplicacion.solitario.guardarPartida();
                }
        });
        menuJuego.add( guardarPartida );
        
        // Crea la opcion de cargar partida (aún no implementada)
        guardarPartida = new JMenuItem( "Cargar partida" );
        guardarPartida.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent evento ){
                    // Llama a iniciar juego para reiniciar la partida
                    Aplicacion.solitario.cargarPartida();
                }
        });
        menuJuego.add( guardarPartida );
        
        // Crea la opcion de salir de la partida
        salirPartida = new JMenuItem( "Salir" );
        salirPartida.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent evento ){
                    System.exit( 0 );
                }
            }
        );
        // Agrega la opción al menu de la partida
        menuJuego.add( salirPartida );
        
        // Agrega el menu juego
        add( menuJuego );
        
        // Crea el menu Ver
        menuVer = new JMenu( "Ver" );
        
        // Crea la opción color fondo
        colorFondo = new JMenuItem( "Fondo" );
        colorFondo.addActionListener(
            new ActionListener(){
                public void actionPerformed( ActionEvent evento ){
                    Aplicacion.solitario.cambiarColorFondo();
                }
            }
        );
        menuVer.add( colorFondo );
        
        // Crea la opción set tamanio y la agrega
        setTamanio = new JMenu( "Tamaño" );
        menuVer.add( setTamanio );
        
        // Crea las opciones de tamaño
        grupoTamanios = new ButtonGroup();      // El grupo de opciones
        ManejadorTamanios manejadorTamanios = new ManejadorTamanios(); // El manejador de eventos
        tamanios = new JRadioButtonMenuItem[ TAMANIOS_DISPONIBLES ];
        for( int contador = 0; contador < TAMANIOS_DISPONIBLES; contador++ ){
            // Crea el item en sí
            tamanios[ contador ] = new JRadioButtonMenuItem( textoTamanios[ contador ] );
            
            // Agrega el manejador de eventos a la opción
            tamanios[ contador ].addActionListener( manejadorTamanios );
            
            // Lo añade al grupo de botones
            grupoTamanios.add( tamanios[ contador ] );
            
            // Lo añade al menu ver
            setTamanio.add( tamanios[ contador ] );
        }
        // Por defecto el tamaño de las cartas es mediano
        tamanios[ PilaCartas.TAMANIO_PILA_MEDIANO ].setSelected( true );
        
        // Agrega menu ver al menu
        add( menuVer );
        
        // Crea el menu de información
        menuInformacion = new JMenu( "Informacion" );
        
        // Crea el item acerca de
        acercaDe = new JMenuItem( "Acerca de..." );
        // Agrega su correspondiente action listener
        acercaDe.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed( ActionEvent evento )
                {
                    JOptionPane.showMessageDialog( null, "Autor: Ismael Salas López\nProgramación Orientada a Objetos\nRealizado sin fines de lucro.", "Acerca de...", JOptionPane.INFORMATION_MESSAGE );
                }
            }
        );
        // Añade la opción acercaDe al menuInformación
        menuInformacion.add( acercaDe );
        
        // Agrega el menu acerca de a la barra de menu
        add( menuInformacion );
    }
    
    // Clase privada para el manejo de los eventos de las opciones
    private class ManejadorTamanios implements ActionListener
    {
        /** Se ejecuta al momento que uno de los elementos que utiliza esta función
         *  es seleccionado.
         *  En este caso por los JMenuButtonMenuItems que permite seleccionar el tamaño de las cartas
         * @param evento 
         */
        @Override
        public void actionPerformed( ActionEvent evento )
        {
            // Para cada uno de los items de opciones
            for( int contador = 0; contador < TAMANIOS_DISPONIBLES; contador++ ){
                // ¿La opcion actual es la seleccionada?
                if( tamanios[ contador].isSelected() ){
                   // Establece la variable estática tamanioVisualPila como el valor que tenga el contador
                   // que puede ser 0 para chico, 1 para mediano y 2 para grande
                    Aplicacion.solitario.getMazoCartas().setTamanioPila( contador );
                    
                    // Actualiza las posiciones el tamaño de las pilas de cartas
                    Aplicacion.solitario.actualizarPosiciones();
                }
            }
        }
    }
}
