package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Clase que extiende de Pantallas, referente a la pantalla de selección de niveles del juego
 */
public class PantallaNiveles extends Pantallas {
    /**
     * Escenario a dibujar
     */
    private Stage escenario;

    /**
     * Imagen que indica si está bloqueado el nivel correspondiente
     */
    private Texture imgBloqueado;

    /**
     * Botón que nos llevará hasta la pantalla del menú de opciones
     */
    private ImageButton btnOpciones;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    private ImageButton btnBack;


    /**
     * Botón referente al nivel 1 del juego
     */
    private TextButton btnNivel1;

    /**
     * Botón referente al nivel 2 del juego
     */
    private TextButton btnNivel2;

    /**
     * Botón referente al nivel 3 del juego
     */
    private TextButton btnNivel3;

    /**
     * Etiqueta que muestra el título de la pantalla
     */
    private Label lblTitulo;

    /**
     * Efecto de sonido que se reproducirá cuando se intente acceder a un nivel que no esté desbloqueado
     */
    Sound error;


    /**
     * Constructor de la pantalla referente a la selección de niveles, donde se definirán todos sus componentes
     *
     * @param juego Aplicación principal
     */
    public PantallaNiveles(DungeonsWithoutDragons juego) {
        super(juego); // Llamo al constructor de su clase padre

        escenario = new Stage(); // Defino el escenario

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);

        // Label del título y todas sus propiedades
        lblTitulo = new Label("Niveles", juego.skin2); // Creo y defino un objeto label con la nueva font
        lblTitulo.setColor(Color.YELLOW);
        lblTitulo.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblTitulo.setPosition(juego.anchoJuego/ 5f, juego.altoJuego - lblTitulo.getHeight() * 1.1f);
        lblTitulo.setFontScale(1.5f,1.6f);
        escenario.addActor(lblTitulo);


        // Botón referente al nivel 1 y todas sus propiedades
        btnNivel1 = new TextButton("Nivel 1", juego.skin);
        btnNivel1.setSize(anchoBotones * 2, altoBotones);
        btnNivel1.setPosition(juego.anchoJuego / 4, juego.altoJuego / 1.6f);
        btnNivel1.getLabel().setFontScale(7, 7);
        escenario.addActor(btnNivel1);


        // Botón referente al nivel 2 y todas sus propiedades
        btnNivel2 = new TextButton("Nivel 2", juego.skin);
        btnNivel2.setSize(anchoBotones * 2, altoBotones);
        btnNivel2.setPosition(juego.anchoJuego / 4, juego.altoJuego / 2.5f);
        btnNivel2.getLabel().setFontScale(7, 7);
        escenario.addActor(btnNivel2);


        // Botón referente al nivel 3 y todas sus propiedades
        btnNivel3 = new TextButton("Nivel 3", juego.skin);
        btnNivel3.setSize(anchoBotones * 2, altoBotones);
        btnNivel3.setPosition(juego.anchoJuego / 4, juego.altoJuego / 6f);
        btnNivel3.getLabel().setFontScale(7, 7);
        escenario.addActor(btnNivel3);


        // Imagen que indica si el nivel correspondiente está bloqueado o no
        imgBloqueado = new Texture("botones/bloqueado.png");


        // Defino el botón de opciones y sus propiedades
        btnOpciones = new ImageButton(imgBtnOpciones);
        btnOpciones.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnOpciones.setPosition(posXOpciones, posYOpciones);
        escenario.addActor(btnOpciones);

        error = Gdx.audio.newSound(Gdx.files.internal("sonidos/error.wav")); // Defino el efecto de sonido
    }


    /**
     * Establezco los listeners de los componentes necesarios
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

                juego.pantallaNiveles.dispose();
            }
        });


        // Listener de btnNivel1 :
        btnNivel1.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.nivel1 = new Nivel1(juego, juego.anchoJuego / 10, juego.altoJuego / 6.5f, new TmxMapLoader().load("mapas/nivel1.tmx"));
                juego.setScreen(juego.nivel1);

                juego.pantallaNiveles.dispose();
            }
        });


        // Listener de btnNivel2 :
        btnNivel2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (juego.bloqueoNivel2){
                    error.play(); // Reproduzco el sonido de error
                }
                else{
                    juego.nivel2 = new Nivel2(juego, juego.anchoJuego / 10, juego.altoJuego / 4, new TmxMapLoader().load("mapas/nivel2.tmx"));
                    juego.setScreen(juego.nivel2);

                    juego.pantallaNiveles.dispose();
                }
            }
        });


        // Listener de btnNivel3 :
        btnNivel3.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (juego.bloqueoNivel3){
                    error.play(); // Reproduzco el sonido de error
                }
                else{
                    juego.nivel3 = new Nivel3(juego, juego.anchoJuego / 4.5f, juego.altoJuego / 4, new TmxMapLoader().load("mapas/nivel3.tmx"));
                    juego.setScreen(juego.nivel3);

                    juego.pantallaNiveles.dispose();
                }
            }
        });


        // Listener de btnOpciones :
        btnOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.backOpciones = 5;

                juego.pantallaMenuOpciones = new PantallaMenuOpciones(juego);
                juego.setScreen(juego.pantallaMenuOpciones);

                juego.pantallaNiveles.dispose();
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

            // Dependiendo si están bloqueados o no, se mostrarán los candados de los niveles 2 y 3
            if (juego.bloqueoNivel2){
                juego.batch.draw(imgBloqueado, juego.anchoJuego / 20f, juego.altoJuego / 2.5f, juego.anchoJuego / 10f, juego.anchoJuego / 10f);
            }

            if (juego.bloqueoNivel3){
                juego.batch.draw(imgBloqueado, juego.anchoJuego / 20f, juego.altoJuego / 7f, juego.anchoJuego / 10f, juego.anchoJuego / 10f);
            }
        juego.batch.end();

        // Dibujo el escenario y lo actualizo
        escenario.draw();
        escenario.act(delta);
    }


    /**
     * Limpio en memoria los componentes necesarios
     */
    @Override
    public void dispose() {
        super.dispose();
        escenario.dispose();
    }
}
