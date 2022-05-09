package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Clase que extiende de Pantallas, referente a la pantalla de créditos de los gráficos
 */
public class PantallaCreditosMusica extends Pantallas {
    /**
     * Escenario a dibujar
     */
    private Stage escenario;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    private ImageButton btnBack;

    /**
     * Botón que nos llevará hasta la pantalla del menú de opciones
     */
    private ImageButton btnOpciones;

    /**
     * Etiqueta del título de la pantalla
     */
    private Label lblTitulo;

    /**
     * Etiqueta con todos los créditos de la música
     */
    private Label lblCreditosMusica;


    /**
     * Constructor de la pantalla referente a los créditos de la música, donde se definen todos sus componentes
     *
     * @param juego Aplicación principal
     */
    public PantallaCreditosMusica(DungeonsWithoutDragons juego) {
        super(juego);

        escenario = new Stage();

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);

        lblTitulo = new Label("Creditos de Musica", juego.skin2);
        lblTitulo.setPosition(juego.anchoJuego/ 4f, juego.altoJuego - lblTitulo.getHeight() * 1.5f);
        lblTitulo.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblTitulo.setFontScale(1f,1.3f);
        lblTitulo.setColor(Color.YELLOW);
        escenario.addActor(lblTitulo);

        lblCreditosMusica = new Label("\"Old Music\" por Alexandr Zhelanov, Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"Dungeon Groove\" modificada, por Gundatsch, Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"Knife sharpening slice 1\" por The Berklee College of Music, Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"3 Melee sounds\" por remaxim, CC0 1.0 Universal (CC0 1.0)\n" +
                "\"Bow & Arrow Shot\" por dorkster, Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)\n" +
                "\"Spell 4 (fire)\" por Bart K., Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"Death Sounds\" por Macro, Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"RPG Sound Pack\" por artisticdude, CC0 1.0 Universal (CC0 1.0) Public Domain Dedication\n" +
                "\"UI Sounds\" por HaelDB, CC0 1.0 Universal (CC0 1.0) Public Domain Dedication\n" +
                "\"Goblin Scream\" por spookymodem, Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"Fantasy Sound Effects Library\" por Little Robot Sound Factory, Attribution 3.0 Unported (CC BY 3.0)"
                , juego.skin
        );
        lblCreditosMusica.setPosition(juego.anchoJuego/ 25, juego.altoJuego / 7.75f);
        lblCreditosMusica.setSize(juego.anchoJuego/ 3f, juego.altoJuego / 1.5f);
        lblCreditosMusica.setFontScale(2.35f, 2.5f);
        escenario.addActor(lblCreditosMusica);


        // Defino el botón de opciones y sus propiedades :
        btnOpciones = new ImageButton(imgBtnOpciones);
        btnOpciones.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnOpciones.setPosition(posXOpciones, posYOpciones);
        escenario.addActor(btnOpciones);

        fondo = new Texture("fondo/FondoCreditosMusica.png"); // Pongo la nueva imagen de fondo
    }


    /**
     * Establezco los listeners de los botones back y de opciones
     */
    @Override
    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla

        // Listener del btnBack :
        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaCreditos = new PantallaCreditos(juego);
                juego.setScreen(juego.pantallaCreditos);

                juego.pantallaCreditosMusica.dispose();
            }
        });


        // Listener de btnOpciones :
        btnOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.backOpciones = 4;

                juego.pantallaMenuOpciones = new PantallaMenuOpciones(juego);
                juego.setScreen(juego.pantallaMenuOpciones);
                juego.pantallaCreditosMusica.dispose();
            }
        });
    }


    /**
     * Muestro en pantalla los componentes necesarios
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
     * Limpio los componentes necesarios
     */
    @Override
    public void dispose() {
        escenario.dispose();
    }
}
