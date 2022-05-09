package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Clase que hereda de Pantallas y representa la pantalla que se mostrará cuando se complete un nivel de la partida
 */
public class PantallaGanador extends Pantallas {
    /**
     * Escenario de la pantalla de ganador
     */
    private Stage escenario;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    private ImageButton btnBack;

    /**
     * Muestra el título de la pantalla
     */
    private Label lblMensaje;


    /**
     * Constructor de la pantalla referente a la completación de un nivel, en el que se definirán sus componentes
     *
     * @param juego Clase principal, App
     * @param mensaje Mensaje que se quiere mostrar en la pantalla
     */
    public PantallaGanador(DungeonsWithoutDragons juego, String mensaje) {
        super(juego); // Llamo al constructor de su clase padre para ponerlo en orden

        // Si está activada la música, se para la música de partida
        if (juego.musica){
            juego.musicaPartida.stop();
            juego.musicaPartida.setPosition(0);

        }

        // Si alguno de los ajustes de efectos de sonido o de música está activo, se reproduce el sonido de victoria
        if (juego.sonidos || juego.musica){
            juego.win.setPosition(0);
            juego.win.play();
        }

        escenario = new Stage();

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);


        // Label del título y todas sus propiedades
        lblMensaje = new Label(mensaje, juego.skin2); // Creo y defino un objeto label con la nueva font
        lblMensaje.setColor(Color.YELLOW);
        lblMensaje.setSize(juego.anchoJuego / 4f,juego.altoJuego / 15f);
        lblMensaje.setPosition(juego.anchoJuego/ 12f, juego.altoJuego / 3 + lblMensaje.getHeight() * 1.1f);
        lblMensaje.setFontScale(1.15f,1.6f);
        escenario.addActor(lblMensaje);

        fondo = new Texture("fondo/fondoGanador.png");
    }


    /**
     * Establezco el listener del botón Back
     */
    @Override
    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla


        // LISTENERS DE LOS COMPONENTES :
        // Listener de btnBack :
        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaMenu = new PantallaMenu(juego);
                juego.setScreen(juego.pantallaMenu);


                if (juego.musica || juego.sonidos){
                    juego.win.stop();
                }


                // Si está activada la música, se cambia a la música de menú
                if (juego.musica){
                    juego.musicaMenu.play();
                }

                juego.pantallaGanador.dispose();
            }
        });
    }


    /**
     * Muestro los componentes necesarios de esta pantalla
     * @param delta DeltaTime
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); // Pongo un color de fondo : Blanco
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpio la pantalla

        // Comienzo el batch para empezar a dibujar el fondo
        juego.batch.begin();
            juego.batch.draw(fondo, 0, 0, juego.anchoJuego, juego.altoJuego);
        juego.batch.end();

        // Dibujo el escenario y lo actualizo
        escenario.draw();
        escenario.act(delta);
    }


    /**
     * Limpio de la memoria los componentes necesarios
     */
    @Override
    public void dispose() {
        super.dispose();
        escenario.dispose();
    }
}
