package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Clase que extiende de Pantallas, referente a la pantalla de créditos del juego, donde se podrá elegir entre mostrar un tipo de créditos u otro
 */
public class PantallaCreditos extends Pantallas {
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
    private Label lblCreditos;

    /**
     * Botón que mostrará la pantalla referente a los créditos de los gráficos
     */
    private TextButton btnGraficos;

    /**
     * Botón que mostrará la pantalla referente a los créditos de la música
     */
    private TextButton btnMusica;


    /**
     * Constructor de la pantalla referente a los créditos, en el que se definirán sus componentes
     *
     * @param juego Aplicación principal
     */
    public PantallaCreditos(final DungeonsWithoutDragons juego) {
        super(juego);

        escenario = new Stage();

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);

        // Defino la etiqueta del título de la pantalla y sus propiedades
        lblCreditos = new Label("Creditos", juego.skin2);
        lblCreditos.setPosition(juego.anchoJuego/ 3f, juego.altoJuego - lblCreditos.getHeight() * 1.25f);
        lblCreditos.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblCreditos.setFontScale(1f,1.3f);
        lblCreditos.setColor(Color.YELLOW);
        escenario.addActor(lblCreditos);

        // Defino el botón para acceder a la pantalla de créditos de gráficos y sus propiedades
        btnGraficos = new TextButton("Graficos", juego.skin);
        btnGraficos.setSize(anchoBotones, altoBotones);
        btnGraficos.setPosition(juego.anchoJuego/ 4f, juego.altoJuego - lblCreditos.getHeight() * 2.3f);
        btnGraficos.getLabel().setFontScale(5, 5);
        escenario.addActor(btnGraficos);

        // Defino el botón para acceder a la pantalla de sonidos y sus propiedades
        btnMusica = new TextButton("Musica", juego.skin);
        btnMusica.setSize(anchoBotones, altoBotones);
        btnMusica.setPosition(juego.anchoJuego/ 4f, juego.altoJuego - lblCreditos.getHeight() * 5f);
        btnMusica.getLabel().setFontScale(5, 5);
        escenario.addActor(btnMusica);

        // Defino el botón de opciones y sus propiedades :
        btnOpciones = new ImageButton(imgBtnOpciones);
        btnOpciones.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnOpciones.setPosition(posXOpciones, posYOpciones);
        escenario.addActor(btnOpciones);

    }


    /**
     * Establezco los listeners de los componentes de este escenario
     */
    @Override
    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla

        // Listener del botón Back :
        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaMenu = new PantallaMenu(juego);
                juego.setScreen(juego.pantallaMenu);

                juego.pantallaCreditos.dispose();
            }
        });


        // Listener del botón de los créditos referentes a los gráficos
        btnGraficos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaCreditosGraficos = new PantallaCreditosGraficos(juego);
                juego.setScreen(juego.pantallaCreditosGraficos);

                juego.pantallaCreditos.dispose();
            }
        });


        // Listener del botón de los créditos referentes a la música
        btnMusica.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaCreditosMusica = new PantallaCreditosMusica(juego);
                juego.setScreen(juego.pantallaCreditosMusica);

                juego.pantallaCreditos.dispose();
            }
        });


        // Listener de btnOpciones :
        btnOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.backOpciones = 2;

                juego.pantallaMenuOpciones = new PantallaMenuOpciones(juego);
                juego.setScreen(juego.pantallaMenuOpciones);

                juego.pantallaCreditos.dispose();
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
     * Limpio la pantalla con sus componentes
     */
    @Override
    public void dispose() {
        escenario.dispose();
    }
}
