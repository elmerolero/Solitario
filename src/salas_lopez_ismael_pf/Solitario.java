/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salas_lopez_ismael_pf;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.lang.SecurityException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Ismael Salas López
 * En realidad, esta clase actúa como la que contiene todas las pilas,
 * la lógica del juego se efectúa de acuerdo a lo establecido en las pilas,
 * las pilas evaluan que se cumplan las condiciones para el intercambio y ordenamiento de
 * cartas y finalmente la clase drag llama a la función victoria dentro de esta clase para enseñarle
 * al usuario que ha ganado.
 * Por otro lado, se encarga de las opciones del jeuego, tales como inicializarlo, terminarlo y demás.
 */
public class Solitario extends JFrame
{
    /* CONSTANTES */
    private final static int PILAS_ORDENADAS = 4;       // Pilas ordenadas que tiene el juego
    private final static int PILAS_DESORDENADAS = 7;    // Pilas desordenadas que tiene el juegos
    private final static int PADDING = 12; // El espaciado entre cada uno de los paneles

    // El mazo de cartas
    private MazoCartas mazoCartas;

    // Las pilas ordenadas
    private PilaOrdenada[] pilasOrdenadas;

    // Las pilas desordenadas
    private PilaDesordenada[] pilasDesordenadas;
    
    // Panel en el que coloca el juego
    private JPanel panelJuego;
    
    // La etiqueta de ganaste
    private PanelVictoria panelVictoria; 
    
    // Dialogo para seleccionar el color de fondo
    private DialogoColores selectorColor;
    
    // Bandera que indica si el juego ha terminado
    private boolean victoria;
  
    // Constructor
    public Solitario()
    {
        // Llama al superconstructor
        super( "Solitario" );
        
        // Crea la barra de menu
        setJMenuBar( new MenuJuego() );
        
        // Crea el panel victorial lo oculta y lo agrega a la ventana
        panelVictoria = new PanelVictoria();
        panelVictoria.setSize( getWidth(), getHeight() );
        
        // Crea el panel establece sus parámetros y lo añade
        panelJuego = new JPanel();
        panelJuego.setSize( getWidth(), getHeight() );
        panelJuego.setLayout( null );
        panelJuego.setBackground( new Color( 25, 149, 0 ) );
        panelJuego.setVisible( true );
        add( panelJuego, BorderLayout.CENTER );
        
        // Establece las propiedades de la ventana
	setSize( 1024, 600 ); // Tamaño de la ventana

	// Crea el mazo de cartas, lo inicializa y lo agrega a la ventana
        mazoCartas = new MazoCartas();
	mazoCartas.iniciar();
	mazoCartas.barajar();
	panelJuego.add( mazoCartas );

	// Crea las pilas ordenadas y las agrega a la ventana
	pilasOrdenadas = new PilaOrdenada[ PILAS_ORDENADAS ];
	for( int contador = 0; contador < PILAS_ORDENADAS; contador++ ){
            // Crea la pila en sí
            pilasOrdenadas[ contador ] = new PilaOrdenada();
            // Las añade al panel de juego
            panelJuego.add( pilasOrdenadas[ contador ] );
	}

	// Crea las pilas desordenadas, les asigna sus cartas correspondientes y las agrega a la ventana
	pilasDesordenadas = new PilaDesordenada[ PILAS_DESORDENADAS ];
	for( int contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            // Crea la pila
            pilasDesordenadas[ contador ] = new PilaDesordenada();

            // Le asigna a cada pila sus cartas correspondientes
            for( int indice = 0; indice <= contador; indice++ ){
                // Obtiene una carta del mazo de cartas y la inserta en la pila
		pilasDesordenadas[ contador ].insertarAlInicio( mazoCartas.repartir() );
                // Oculta la carta
		pilasDesordenadas[ contador ].getPrimeraCarta().setRevelada( false );
            }

            // Revela la primera carta de la pila
            pilasDesordenadas[ contador ].getPrimeraCarta().setRevelada( true );

            // Añade la pila al panelDeJuego
            panelJuego.add( pilasDesordenadas[ contador ] );
        }
        
        // Victoria es falso (no ha ganado)
        victoria = false;
    }
    
    /** Hace las operaciones necesarias para reiniciar el juego */
    public void iniciarJuego()
    {
        int contador;
        
        // Destruye el juego para asegurarnos de que el mazo tiene toda la baraja
        destruirJuego();
        
        // Se vueleven a barajar las cartas
        mazoCartas.barajar();
        
        // Oculta el panel que dice victoria
        remove( panelVictoria );
        
        // Muestra el panel de juego
        panelJuego.setVisible( true );
        
        // Reparte las cartas y vuelve a mostrar las pilas de cartas desordenadas
        for( contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            // Le asigna a cada pila sus cartas correspondientes
            for( int indice = 0; indice <= contador; indice++ ){
                // Obtiene una carta del mazo de cartas y la inserta al inicio de la pila actual
		pilasDesordenadas[ contador ].insertarAlInicio( mazoCartas.repartir() );
                // Oculta la cara de la carta
		pilasDesordenadas[ contador ].getPrimeraCarta().setRevelada( false );
            }

            // Revela la primera carta de la pila
            pilasDesordenadas[ contador ].getPrimeraCarta().setRevelada( true );
        }
        
        // Actualiza el tamaño y la posición de las pilas en el juego
        actualizarPosiciones();
    }
    
    /** Verifica que el usuario ganó y desencadena otros eventos.
     *  Se considera que el usuario ganó cuando ha colocado exitosamente
     *  las cartas en las cuatro pilas ordenadas. 
     */
    public void victoria()
    {
        // COntador para el iterador
        int contador;
        
        // Indicador de cuantas pilas están llenas
        int pilasLlenas = 0;
        
        // Para cada una cuatro pilas ordenadas hacer
        for( contador = 0; contador < PILAS_ORDENADAS; contador++ ){
            // Si la pila ordenada en la que va el contador está llena entonces
            if( pilasOrdenadas[ contador ].estaLlena() ){
                // Incrementa el indicador de cuantas pilas estan llenas
                pilasLlenas++;
            }
        }
        
        // ¿Las cuatro pilas están llenas?
        if( pilasLlenas == PILAS_ORDENADAS ){
            // Activa la bandera de victoria
            victoria = true;
            
            // Muestra un mensaje al usuario que le informa que gano el juego
            JOptionPane.showMessageDialog( null, "¡Ganaste!", "Victoria", JOptionPane.PLAIN_MESSAGE );
            
            // Destruye el juego, devuelve las cartas al mazo de cartas
            destruirJuego();
            
            // Muestra el panel de victoria que muestra la felicitación
            add( panelVictoria );
            
            // Actualiza la pantalla
            panelVictoria.repaint();
        }
    }
    
    /** Destruye el juego.
     *  Una vez que el usuario gana, se destruyen los elementos
     *  del juego y se devuelven las cartas al mazo de cartas (objeto mazoCartas)
     */
    public void destruirJuego()
    {
        // Variable para los iteradores for
        int contador;
        
        // Oculta el mazo de cartas
        panelJuego.setVisible( false );
        
        // Devuelve las cartas al mazo y oculta las pilas de cartas
        for( contador = 0; contador < PILAS_ORDENADAS; contador++ ){   
            while( !pilasOrdenadas[ contador ].estaVacia() ){
                mazoCartas.insertarAlInicio( pilasOrdenadas[ contador ].retirarAlInicio() );
            }
        }
        
        // Oculta las pilas de cartas desordenadas
        for( contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            while( !pilasDesordenadas[ contador ].estaVacia() ){
                mazoCartas.insertarAlInicio( pilasDesordenadas[ contador ].retirarAlInicio() );
                mazoCartas.getPrimeraCarta().setRevelada( true );
            }
        }
    }
    
    // Obtiene el panel donde se dibujan todos los
    // componentes del solitario, así permito que
    // clase DialogoColores pueda acceder a él y modoficar el fondo
    public JPanel getPanelJuego()
    {
        return this.panelJuego;
    }
    
    // Obtiene el mazo de caratas
    // para poder cambiar el color de todas las pilas
    // clase DialogoColores pueda acceder a él y modoficar el fondo
    public MazoCartas getMazoCartas()
    {
        return this.mazoCartas;
    }
    
    /** Cambia el color del fondo del juego */
    public void cambiarColorFondo()
    {
        // Inicializa el cuadro de dialogo y se muestra
        selectorColor = new DialogoColores( this );
    }
    
    /** Actualiza la posición y el tamaño de las pilas del juego */
    public void actualizarPosiciones()
    {
        int contador;
        // Obtiene la anchura que deben tener las pilas de cartas
        int anchura = mazoCartas.getAnchura();
        // Obtiene la altura que deben de tener las pilas de cartas
        int altura = mazoCartas.getAltura();
        
        // Actualiza la posicion y tamaño del mazo de cartas
        mazoCartas.setLocation( 12, 12 );
        mazoCartas.setSize( ( anchura * 2 ) + 24, altura );
       
        // Actualiza el tamño y la posicion de las pilas de cartas ordenadas
        for( contador = 0; contador < PILAS_ORDENADAS; contador++ ){
            pilasOrdenadas[ contador ].setSize( anchura, altura );
            pilasOrdenadas[ contador ].setLocation( ( 12 * 4 + anchura * 3 ) + ( anchura * contador ) + ( 12 * contador ), 12 );
        }
        
        // Actualiza el tamño y la posicion de las pilas de cartas desordenadas
        for( contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            // Establece su posición en pantalla
            pilasDesordenadas[ contador ].setSize( anchura, altura + ( 20 * ( pilasDesordenadas[ contador ].getTamanio() ) ));
            pilasDesordenadas[ contador ].setLocation( 12 + ( anchura * contador ) + ( 12 * contador ), 36 + altura );
        }
    }
    
    /** Permite guardar la partida del juego */
    public void guardarPartida()
    {
        // Bandera para asegurarnos de que se guardo la partida
        boolean exitoso = true;
        
        // ¿Ya ha ganado el jugador?
        if( victoria ){
            // Le indica que no puede guardar la partida porque ya ganó
            JOptionPane.showMessageDialog( null, "No puedes guardar la partida, ¡ya ganaste!", "Consigue otra victoria", JOptionPane.PLAIN_MESSAGE );
            return;
        }
        
        // Objeto para guardar la partida
        Formatter archivoPartida;
        
        // PilaCartas auxiliar para no perder las cartas del juego
        PilaCartas pilaAuxiliar = new PilaCartas();
        
        // Carta auxiliar para guardar temporalmente las cartas
        Carta cartaAuxiliar;
                
        // Arreglo para guardar cuantas cartas tiene cada pila (incluido el mazo de cartas)
        int[] tamaniosPilas = new int[ 12 ];
        
        // Indice del mazo de cartas
        int indiceMazo = 0;
        
        // Intenta crear un nuevo archivo
        try{
            archivoPartida = new Formatter( "resources/partida.dat" );
        }
        catch( SecurityException excepcion ){
            JOptionPane.showMessageDialog( null, "No se guardó el archivo.\nAcceso denegado.", "Error al guardar partida", JOptionPane.ERROR_MESSAGE );
            return;
        }
        catch( FileNotFoundException excepcion ){
            JOptionPane.showMessageDialog( null, "No se pudo crear el archivo de guardado.", "Error al guardar partida", JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        // Intenta guardar las cartas del juego en el orden en el que se dio la partida
        try{
            /* MAZO DE CARTAS */
            // Guardamos el tamaño del mazo de cartas y el indice
            indiceMazo = mazoCartas.getIndice();
            tamaniosPilas[ 11 ] = mazoCartas.getTamanio();
            
            // Guardamos todas las cartas del mazo de cartas
            while( !mazoCartas.estaVacia() ){
                // Guardamos en la variable auxiliar la carta que devuelva el mazo
                cartaAuxiliar = mazoCartas.retirarAlFinal();
                
                // Agregamos la carta a la pila auxiliar
                pilaAuxiliar.insertarAlInicio( cartaAuxiliar );
                
                // Escribimos los datos de la carta en el archivo
                archivoPartida.format( "%d %d %d%n", cartaAuxiliar.getCara(), cartaAuxiliar.getPalo(), ( cartaAuxiliar.isRevelada() ? 1 : 0 ) );
            }
            
            // Guardamos las cartas delas pilas ordenadas
            for( int contador = 0; contador < PILAS_ORDENADAS; contador++ ){
                // Guardamos el tamaño de la pila de cartas
                tamaniosPilas[ contador ] = pilasOrdenadas[ contador ].getTamanio();
                
                // Mientras tenga cartas
                while( !pilasOrdenadas[ contador ].estaVacia() ){
                    // Se guarda en la carta en la variable auxiliar
                    cartaAuxiliar = pilasOrdenadas[ contador ].retirarAlFinal();
                    
                    // Agregamos la carta a la pila auxiliar
                    pilaAuxiliar.insertarAlInicio( cartaAuxiliar );
                    
                    // Escribimos los datos de la carta en el archivo
                    archivoPartida.format( "%d %d %d%n", cartaAuxiliar.getCara(), cartaAuxiliar.getPalo(), ( cartaAuxiliar.isRevelada() ? 1 : 0 ) );
                }
            }
            
            // Guardamos las cartas de las pilas desordenadas
            for( int contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
                // Obtiene el tamaño de la pila actual
                tamaniosPilas[ contador + PILAS_ORDENADAS ] = pilasDesordenadas[ contador ].getTamanio();
                
                // Mientras tenga cartas
                while( !pilasDesordenadas[ contador ].estaVacia() ){
                    // Se guarda en la carta en la variable auxiliar
                    cartaAuxiliar = pilasDesordenadas[ contador ].retirarAlFinal();
                    
                    // Agregamos la carta a la pila auxiliar
                    pilaAuxiliar.insertarAlInicio( cartaAuxiliar );
                    
                    // Escribimos los datos de la carta en el archivo
                    archivoPartida.format( "%d %d %d%n", cartaAuxiliar.getCara(), cartaAuxiliar.getPalo(), ( cartaAuxiliar.isRevelada() ? 1 : 0 ) );
                }
            }
            
            // Agregamos el tamaño de las pilas de cartas
            for( int tamanio : tamaniosPilas ){
                archivoPartida.format( "%d%n", tamanio );
            }
            
            // Agregamos el indice del mazo
            archivoPartida.format( "%d%n", indiceMazo );
        }   
        catch( FormatterClosedException excepcion ){
            // Escribimos los datos de la carta en el archivo
            JOptionPane.showMessageDialog( null, "Ha ocurrido un error desconocido.", "Error al guardar partida", JOptionPane.ERROR_MESSAGE );
            exitoso = false;
        }
        
        // Cerramos el archivo
        if( archivoPartida != null ){
            archivoPartida.close();
        }
        
        // ¿El guardado fue exitoso?
        if( exitoso ){
            recuperarCartas( pilaAuxiliar, tamaniosPilas, indiceMazo );
            JOptionPane.showMessageDialog( null, "Partida guardada exitosamente.", "Guardar Partida", JOptionPane.PLAIN_MESSAGE );
        }
    }
    
    /** Recupera el estado de la partida guardada */
    public void cargarPartida()
    {
        // Inicializa un nuevo juego
        iniciarJuego();
        
        // Pila de Cartas auxiliar para recuperar los datos de la carta
        PilaCartas pilaAuxiliar = new PilaCartas();
        
        // Carta auxiliar
        Carta cartaAuxiliar;
        
        // Arreglo con la cantidad de cartas que tenía cada pila
        int[] arreglo = new int[ 12 ];
        
        // Indice en el que se encontraba el mazo
        int indice = 0;
        
        // Intentamos abrir el archivo
        try( Scanner entrada = new Scanner( Paths.get( "resources/partida.dat" ) ) ){
            int contador;
            
            // Recupera el estado de las cartas
            for( contador = 0; contador < 52; contador++ ){
                // Crea la carta
                cartaAuxiliar = new Carta();
                
                // Obtiene la información guardada en el archivo
                cartaAuxiliar.setCara( entrada.nextInt() );
                cartaAuxiliar.setPalo( entrada.nextInt() );
                cartaAuxiliar.setRevelada( ( entrada.nextInt() == 1 ? true : false ) );
                
                // Agrega la carta a la pila auxiliar
                pilaAuxiliar.insertarAlInicio( cartaAuxiliar );
            }
            
            // Recupera el tamanio de las cartas
            for( contador = 0; contador < 12; contador++ ){
                arreglo[ contador ] = entrada.nextInt();
            }
            
            // Recupera el indice del mazo
            indice = entrada.nextInt();
        }
        catch( IOException excepcion ){
            JOptionPane.showMessageDialog( null, "Partida guardada exitosamente.", "Guardar Partida", JOptionPane.PLAIN_MESSAGE );
            System.exit( 1 );
        }
        
        recuperarCartas( pilaAuxiliar, arreglo, indice );
    }
    
    /** Permite recuperar las cartas de una pila dada */
    private void recuperarCartas( PilaCartas pila, int[] arreglo, int indice )
    {
        int contador;
        int cantidad;
        int tamanio;
        
        // Vacía las pilas
        vaciarPilas();
        
        /* RECUPERA LAS CARTAS DEL MAZO DE CARTAS */
        tamanio = arreglo[ 11 ]; // El ultimo subindice corresponde al tamaño del mazo de cartas
        for( contador = 0; contador < tamanio; contador++ ){
            mazoCartas.insertarAlInicio( pila.retirarAlFinal() );
        }
        
        // Establece el índice del mazo
        mazoCartas.setIndice( indice );
        
        /* RECUPERA LAS CARTAS DE LAzS PILAS ORDENADAS */
        for( contador = 0; contador < PILAS_ORDENADAS; contador++ ){
            // Obtiene cuantas cartas tenía la pila
            tamanio = arreglo[ contador ];
            
            // Reparte el numero de cartas especificado por el tamanio
            for( cantidad = 0; cantidad < tamanio; cantidad++ ){
                // Inserta las cartas en la pila
                pilasOrdenadas[ contador ].insertarAlInicio( pila.retirarAlFinal() );
            }
        }
        
        /* RECUPERA LAS CARTAS DE LAS PILAS DESORDENADAS */
        for( contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            // Obtiene cuantas cartas tenía la pila
            tamanio = arreglo[ contador + 4 ];
            
            // Reparte el numero de cartas especificado por tamanio
            for( cantidad = 0; cantidad < tamanio; cantidad++ ){
                // Inserta las cartas en la pila
                pilasDesordenadas[ contador ].insertarAlInicio( pila.retirarAlFinal() );
            }
        }
        
        actualizarPosiciones();  // Actualiza la posición y tamaño de las pilas
        actualizarPantalla();   // Actualiza la pantalla
    }
    
    // Vacia todas las pilas del juego
    private void vaciarPilas()
    {
        // Vaciamos las pilas ordenadas
        for( int contador = 0; contador < PILAS_ORDENADAS; contador++ ){
            pilasOrdenadas[ contador ].vaciar();
        }
        
        // Vaciamos las pilas Desordenadas
        for( int contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            pilasDesordenadas[ contador ].vaciar();
        }
        
        // Vaciamos el mazo de cartas
        mazoCartas.vaciar();
    }
    
    // Vacia todas las pilas del juego
    private void actualizarPantalla()
    {
        // Vaciamos las pilas ordenadas
        for( int contador = 0; contador < PILAS_ORDENADAS; contador++ ){
            pilasOrdenadas[ contador ].repaint();
        }
        
        // Vaciamos las pilas Desordenadas
        for( int contador = 0; contador < PILAS_DESORDENADAS; contador++ ){
            pilasDesordenadas[ contador ].repaint();
        }
        
        // Vaciamos el mazo de cartas
        mazoCartas.repaint();
    }
}
