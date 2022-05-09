package dungeons.without.dragons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.io.*;
import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * Clase principal del juego, que extiende de Game
 */
public class DungeonsWithoutDragons extends Game {
	/**
	 * Archivo de preferencias en el que se guardan variables de configuración como si se activan o no la música, los efectos de sonido o la vibración, o el personaje seleccionado
	 */
	Preferences configuracion;

	/**
	 * Batch que renderizará los componentes necesarios
	 */
	SpriteBatch batch;

	/**
	 * Música para el menú
	 */
	Music musicaMenu;

	/**
	 * Música para la partida
	 */
	Music musicaPartida;

	/**
	 * Música para cuando se ha completado un nivel
	 */
	Music win;

	/**
	 * Objeto de tipo Skin que guarda el estilo de los componentes
	 */
	public Skin skin;

	/**
	 * Objeto de tipo Skin que guarda el estilo de la nueva font
	 */
	public Skin skin2;

	/**
	 * Creo dos variables públicas para que todas las pantallas puedan acceder a ellas, y recogerán la información de cuántos píxeles mide al ancho y el alto de la pantalla
	 */
	public static int altoJuego, anchoJuego;

	/**
	 * Variable que hace referencia a la pantalla del menú principal
	 */
	PantallaMenu pantallaMenu;


	// ------------------------------------------------------------------ VARIABLES PANTALLA PERSONAJE ----------------------------------------------------------------------------------
	/**
	 * Variable que hace referencia a la pantalla de selección de personaje
	 */
	PantallaPersonaje pantallaPersonaje;

	// Defino las varias imágenes disponibles para el personaje :
	/**
	 * Array de imágenes correspondientes a los diferentes tipos de personaje
	 */
	TextureRegion[] imagenesPersonajes = new TextureRegion[5];

	/**
	 * Contador que gestiona la visualización del personaje
	 */
	int contadorImagenesPersonaje;

	/**
	 * Booleana que controla si el personaje es hombre o mujer, que se cambia cuando pulsamos el botón del cambio de género
	 */
	boolean esHombre;


	// ------------------------------------------------------------------ VARIABLES PANTALLA CRÉDITOS ----------------------------------------------------------------------------------
	/**
	 * Variable que hace referencia a la pantalla de los créditos del juego
	 */
	PantallaCreditos pantallaCreditos;

	/**
	 * Variable que hace referencia a la pantalla de los créditos de los gráficos del juego
	 */
	PantallaCreditosGraficos pantallaCreditosGraficos;

	/**
	 * Variable que hace referencia a la pantalla de los créditos de la música del juego
	 */
	PantallaCreditosMusica pantallaCreditosMusica;


	// ------------------------------------------------------------------ VARIABLES PANTALLA MENÚ OPCIONES ----------------------------------------------------------------------------------
	/**
	 * Variable que hace referencia a la pantalla del menú de opciones del juego
	 */
	PantallaMenuOpciones pantallaMenuOpciones;

	/**
	 * Booleana indicando si se debe reproducir la música
	 */
	boolean musica;

	/**
	 * Booleana indicando si se deben reproducir los sonidos
	 */
	boolean sonidos;

	/**
	 * Booleana indicando si se debe reproducir la vibración
	 */
	boolean vibracion;

	/**
	 * Variable que guardará la pantalla desde la que se pulsó el botón de opciones, para volver a ella cuando se pulse el botón back en la pantalla opciones
	 */
	int backOpciones;

	// ------------------------------------------------------------------ VARIABLES PANTALLA NIVELES ----------------------------------------------------------------------------------

	/**
	 * Booleana que indica si está bloqueado el nivel 2 del juego
	 */
	boolean bloqueoNivel2;

	/**
	 * Booleana que indica si está bloqueado el nivel 3 del juego
	 */
	boolean bloqueoNivel3;

	/**
	 * Pantalla donde se muestran los niveles bloqueados y desbloqueados
	 */
	PantallaNiveles pantallaNiveles;


	// ------------------------------------------------------------------ VARIABLES NIVELES ----------------------------------------------------------------------------------
	/**
	 * Pantalla del nivel 1
	 */
	Nivel1 nivel1;

	/**
	 * Pantalla del nivel 2
	 */
	Nivel2 nivel2;

	/**
	 *  Pantalla del nivel 3
	 */
	Nivel3 nivel3;

	/**
	 * Pantalla que se mostrará cuando se complete un nivel
	 */
	PantallaGanador pantallaGanador;


	/**
	 * Crea la instancia principal del juego, definiendo todas sus propiedades
	 */
	@Override
	public void create () {

		configuracion = Gdx.app.getPreferences("configuracion"); // Determinamos el archivo de preferencias
		leerConfig(); // Leo las variables que deben ser guardadas y las defino

		// Defino la música para el menú y empiezo a reproducirla
		musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musica/Old Music 1.mp3"));
		musicaMenu.setVolume(0.3f); // 1.0f es el volumen máximo
		musicaMenu.setLooping(true); // Establezco que la música se repita cuando se acabe

		// Si la opción de la música está activada, la reproduzco
		if (musica){
			musicaMenu.play();
		}

		// Defino la música de la partida y los sonidos :
		musicaPartida = Gdx.audio.newMusic(Gdx.files.internal("musica/dungeongroove_0.mp3"));
		musicaPartida.setVolume(0.3f); // 1.0f es el volumen máximo
		musicaPartida.setLooping(true);

		// Defino la música de victoria y establezco que no se repita
		win = Gdx.audio.newMusic(Gdx.files.internal("sonidos/win.wav"));
		win.setLooping(false);

		batch = new SpriteBatch(); // Defino el SpriteBatch

		skin = new Skin(Gdx.files.internal("skin/uiskin.json")); // Declaramos la skin para todos los elementos de la UI
		skin2 = new Skin(Gdx.files.internal("skin2/pixthulhu-ui.json")); // Declaramos la skin para la font de los títulos

		// Establezco las variables altoJuego y anchoJuego a la cantidad de píxeles de ancho y alto de la pantalla, respectivamente
		altoJuego = Gdx.graphics.getHeight();
		anchoJuego = Gdx.graphics.getWidth();

		// Defino la variable del menú principal
		pantallaMenu = new PantallaMenu(this);

		// Muestro la primera pantalla que es el menu
		setScreen(pantallaMenu);


// ------------------------------------------------------------------ VARIABLES PANTALLA PERSONAJE ----------------------------------------------------------------------------------
		imagenesPersonajes[0] = new TextureRegion(new Texture("personajes/guerrero.png")); // Guerrero
		imagenesPersonajes[1] = new TextureRegion(new Texture("personajes/explorador.png")); // Explorador
		imagenesPersonajes[2] = new TextureRegion(new Texture("personajes/picaro.png")); // Pícaro
		imagenesPersonajes[3] = new TextureRegion(new Texture("personajes/mago.png")); // Mago
		imagenesPersonajes[4] = new TextureRegion(new Texture("personajes/clerigo.png")); // Clérigo

	}

	/**
	 * Limpia en la memoria los componentes que se necesitan ser borrados
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}



	//---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


	/**
	 * Establezco las variables que me interesan en el archivo de configuración y defino su valor por defecto en el caso de que aún no estén guardadas
	 */
	public void leerConfig() {
		musica = configuracion.getBoolean("musica", true);
		sonidos = configuracion.getBoolean("sonidos", true);
		contadorImagenesPersonaje = configuracion.getInteger("contadorImagenesPersonaje", 0);
		esHombre = configuracion.getBoolean("esHombre", true);
		bloqueoNivel2 = configuracion.getBoolean("bloqueoNivel2", true);
		bloqueoNivel3 = configuracion.getBoolean("bloqueoNivel3", true);
		vibracion = configuracion.getBoolean("vibracion", true);
	}

}
