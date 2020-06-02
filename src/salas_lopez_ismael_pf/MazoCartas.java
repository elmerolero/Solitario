/*
 * 12 de Mayo de 2020
 * Ismael Salas López
 * PROYECTO FINAL
 * Clase MazoCartas
 */
package salas_lopez_ismael_pf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.security.SecureRandom;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 *
 * @author Ismael Salas López
 * Hereda de PilaCartas
 * contiene todo el mazo de cartas. 
 * Inicializa, barajea y reparte las cartas a las demás pilas.
 * También al sobrar cartas, este se utiliza como el mazo
 * donde el usuario puede buscar cartas cuando lo necesite.
 * A diferencia de PilaCartas ordenadas, MazoCartas solo habilita el drag en la 
 * aplicación.
 */
public class MazoCartas extends PilaCartas
{
    /* CAMPOS */
    // Indice que permite recorrer el mazo
    private int indiceMazo;
    
    // Indica si se esta moviendo una carta
    private boolean cartaMovida = false;
    
    // Permiten implementar el arrastre
    // Clase abstracta que habilita al componente manejar eventos de arrastre
    private DragGestureRecognizer gestorArrastre;   
    // Al manejador de eventos del arrastre
    private Arrastra arrastra;      
    
    // Constructor
    public MazoCartas()
    {
        super();
        
        // Carga los assets
        Carta.cargarImagenCartas( "resources/assets.png" );
      
        setSize( 277, 181 );
        
        // Inicializa el indice en ceros
        indiceMazo = 0;
        
        // Agrega un mouseListener
        addMouseListener( new GestorMouse() );
        
        // Crea el manejador de eventos para el arrastre
        arrastra = new Arrastra( this );
        // Esta función crea una subclase genérica que permite reconocer el arrastre y recibe tres parámetros
        // la referencia a sí mismo para asociarlo, la constante que indica el tipo de gesto que maneja y
        // el manejador de eventos que lo procesará
        gestorArrastre = DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer( this, DnDConstants.ACTION_MOVE, arrastra );

    }
    
    /** Incializa el mazo de cartas. Crea las cartas necesarias para
     *  ejecutar el juego.
     */
    public void iniciar()
    {
        // Valores para iniciar las cartas del mazo
        Carta carta;
        int cara = 0;
        int palo = 0;
        for( int contador = 0; contador < 52; contador++ ){
            // Crea la carta
            carta = new Carta();
            
            // Le hecha un palo
            carta.setPalo( palo );
            
            // Le hecha la cara
            carta.setCara( cara );
            
            // Revela la carta
            carta.setRevelada( true );
            
            // Añade la carta al mazo
            insertarAlInicio( carta );
            
            // Incrementa cara en 1
            cara = cara + 1;
            
            // ¿Cambio de palo? (ya se crearon las 13 cartas de un palo específico).
            if( ( contador + 1 ) % 13 == 0 ){
                // Reinicia los valores para cara
                cara = 0;
                
                // Incrementa palo en 1
                palo = palo + 1;
            }
        }
        
        // Establece el indice del mazo
        indiceMazo = getTamanio() - 1;
    }
    
    /** Barajea la baraja. 
     * Revuelve las cartas del mazo.
     * 
     */
    public void barajar()
    {
        // Obtiene el mazo
        Carta[] mazo = getCartas();
     
        // Crea un objeto secure random para obtener numeros aleatorios
        SecureRandom aleatorio = new SecureRandom();
        
        // Contador e indice para barajar las cartas
        int contador = 0;
        int indice;
        
        // Carta auxiliar para poder hacer el intercambio
        Carta cartaAuxiliar;
        
        // Durante 52 veces hacer
        while( contador < 52 ){
            // Genera un ìndice aleatorio entre 0 y 51 (arreglo de 52 cartas)
            indice = aleatorio.nextInt( 52 );
            
            // Guarda la carta en la que actualmente va el contador en cartaAuxiliar
            cartaAuxiliar = mazo[ contador ];
            
            // Intercambia la carta del indice generado aleatoriamente con la que va del contador
            mazo[ contador ] = mazo[ indice ];
            mazo[ indice ] = cartaAuxiliar;
            
            // Incrementa el contador
            contador++;
        }
    }
    
    /** Devuelve una carta del mazo.
     *  Esta función lo único que hace es devolver la carta devuelta
     *  por la funcion contenida en la super clase PilaCartas 'retirarAlInicio()'
     *  pero asegurándose que el índice que requiere el mazo se mantenga en un
     *  rango seguro (que apunte a elementos del arreglo que contengan cartas, de lo
     *  contrario la aplicación podría lanzar NullPointerException) para evitar
     * errores lógicos en la aplicación.
     */
    public Carta repartir()
    {
        // Carta a devolver
        Carta carta = retirarAlInicio();
        
        // Actualiza el indice del mazo
        indiceMazo = getTamanio() - 1;
        
        // Devuelve la carta
        return carta;
    }
    
    // Cambia la carta a la que apunta indice de manera descendente
    public void cambiarIndice()
    {
        indiceMazo = indiceMazo - 1;
        if( indiceMazo < -1 ){
            indiceMazo = getTamanio() - 1;
        }
    }
    
    /* Permite obtener el índice actual de la carta */
    public int getIndice()
    {
        return indiceMazo;
    }
    
    /* Permite establece el indice de la carta */
    public void setIndice( int indice )
    {
        this.indiceMazo = indice;
    }
    
    /** Obtiene la carta que apunta actualmente el índice (indiceMazo).
     * Este procedimiento, a diferencia de las otras pilas cartas, está diseñado para 
     * devolver la copia de una sola carta a diferencia de las pilas desordenadas (que 
     * permite seleccionar un grupo de cartas).
     * @return 
     */
    @Override
    public PilaCartas getSeleccion()
    {
        // Se crea la pila de cartas
        PilaCartas contenedor = new PilaCartas();
        
        // Si el mazo tiene cartas
        if( !estaVacia() ){
            // Añade la carta seleccionada a la pila de cartas
            contenedor.insertarAlInicio( getCartaMedio( indiceMazo ) );
            cartaMovida = true;
        }
        
        return contenedor;
    }
    
    /** Remueve la carta que fue movida.
     * Este procedimiento, a diferencia de las otras pilas cartas, está diseñado para 
     * devolver la copia de una sola carta a diferencia de las pilas desordenadas (que 
     * permite seleccionar un grupo de cartas).
     * @return 
     */
    @Override
    public void removerSeleccion()
    {
        // ¿Se movio una carta exitosamente ?
        if( cartaMovida ){
            // Se retira del mazo
            retirarDelMedio( indiceMazo-- );
        }
        
        // Si el indiceMazo está en un rango inadecuado (indiceMazo < -1 o indiceMazo > n - 1, donde n es el tamaño del mazo)
        if( indiceMazo >= getTamanio() ){
            indiceMazo = getTamanio() - 1;
        }
    }
    
    /** Cancela la transección de selección de la carta a mover.
     * Cuando se obtiene la selección, como solo se puede mover una sola carta
     * establecemos una variable booleana como true (cartaMovida) que indica que se está moviendo
     * una carta. Si por alguna razón no se pudo mover la carta exitosamente, se establece en falso.
     * Influyendo sobre 'removerSeleccion()', que eliminará la carta si fue movida exitosamente (cartaMovida = true)
     * o si no fue así, simplemente no hará nada.
     */
    @Override
    public void cancelarSeleccion()
    {
        cartaMovida = false;
    }
    
    /** Dibuja la carta actual del mazo.
     * Cuando se obtiene la selección, como solo se puede mover una sola carta
     * establecemos una variable booleana como true (cartaMovida) que indica que se está moviendo
     * una carta. Si por alguna razón no se pudo mover la carta exitosamente, se establece en falso.
     * Influyendo sobre 'removerSeleccion()', que eliminará la carta si fue movida exitosamente (cartaMovida = true)
     * o si no fue así, simplemente no hará nada.
     */
    @Override
    public void paintComponent( Graphics painter )
    {
        // Obtiene la anchura y la altura actual que deben de tener las pilas
        int anchoPila = getAnchura();
        int altoPila = getAltura();
        
        // Limpia la pantalla
        painter.setColor( getColorBackground() );
        painter.fillRect( 0, 0, getWidth(), getHeight() );
        
        // Si la pila aún tiene cartas entonces
        if( !estaVacia() ){
            // Dibuja la carta oculta que representa las demás cartas que quedan en el mazo
            painter.drawImage( getPrimeraCarta().getImagenOculta(), 0, 0, anchoPila - 2, altoPila - 2, this );
            
            // Si llego al fin del mazo entonces
            if( indiceMazo == -1 ){
                // Establece el componente graphics para pintar en color negro
                painter.setColor( Color.BLACK );
                // Dibuja un rectangulo que lo indi
                painter.drawRect( anchoPila - 2 + 16, 0, anchoPila  - 2, altoPila  - 2 );
            }else{
                // Dibuja la carta que se está viendo actualmente del mazo
                painter.drawImage( getCartaMedio( indiceMazo ).getImagen(), anchoPila - 2 + 16, 0, anchoPila - 2, altoPila - 2, this );
            }
        }
        else{
            // Establece el componente graphics para pintar en color negro
            painter.setColor( Color.BLACK );
            // Dibuja solamente un cuadro negro que indica que ya no hay más cartas en el mazo
            painter.drawRect( 0, 0, anchoPila - 1, altoPila - 1 );
        }
    }
    
    /** Clase interna que permite recibir los clics del mouse.
     * Evento necesario para que cuando se haga clic sobre el mazo,
     * se cambie la carta que va del mazo.
     */
    private class GestorMouse extends MouseAdapter
    {
        /** Se ejecuta al hacer clic sobre el mazo de cartas */
        @Override
        public void mouseClicked( MouseEvent event ){
            // Si el mouse se encuentra posicionado sobre la carta oculta
            // al hacer clic
            if( ( int )getMousePosition().getX() < getAnchura() ){
                // Llama a cambiar índice
                cambiarIndice();
            }
            // Actualiza la pantalla para mostrar la siguiente carta
            repaint();
        }
    }
}
