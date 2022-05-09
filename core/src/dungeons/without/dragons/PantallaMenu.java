package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.*;

/**
 * Clase que extiende de Pantallas, referente a la pantalla del menú principal del juego
 */
public class PantallaMenu extends Pantallas {
    /**
     * Escenario a dibujar
     */
    private Stage escenario;

    /**
     * Etiqueta de texto que muestra el título del juego
     */
    private Label lblTitulo;

    /**
     * Botones que contienen texto y forman parte del escenario como menú
     */
    private TextButton btnJugar, btnPersonaje, btnCreditos;

    /**
     * Botón que tiene la imagen imgBtnOpciones definida en su clase padre y su comportamiento será llevarnos hasta la pantalla del menú de opciones
     */
    private ImageButton btnOpciones;


    // Variables usadas en el efecto Parallax :
    /**
     * Array de imágenes que contiene las 3 capas diferentes que forman el fondo
     */
    private Texture[] capas;

    /**
     * Array numérico de 3 números, cada uno referente a su capa correspondiente
     */
    private float[] backgroundOffsets = {0 , 0, 0};

    /**
     * Cámara encargada de apuntar y visualizar al fondo del efecto Parallax
     */
    private OrthographicCamera camara;

    /**
     * Vista de la cámara y del escenario que permite visualizar el fondo con efecto Parallax
     */
    private Viewport viewport;

    /**
     * Contador que controla para establecer las velocidades de las capas del fondo con efecto Parallax
     */
    private int contParallax = 0;


    /**
     * Constructor de la pantalla referente al menú principal.
     * En él se define su escenario y los componentes dentro de éste.
     *
     * @param juego Aplicación principal
     */
    public PantallaMenu(final DungeonsWithoutDragons juego) {
        super(juego);

        escenario = new Stage();

        camara = new OrthographicCamera(juego.anchoJuego, juego.altoJuego); // Defino la cámara con el ancho y el alto de la pantalla como parámetros

        viewport = new ExtendViewport(juego.anchoJuego, juego.altoJuego, camara); // Defino el viewport como ExtendViewport con las dimensiones de la pantalla y la cámara como parámetros
        escenario.setViewport(viewport); // Establezco el viewport del escenario como el viewport

        // Defino el array de capas del fondo con efecto Parallax con un límite de 3
        // Defino las capas con las imágenes de las diferentes capas, metidas en assets
        capas = new Texture[3];
        capas[0] = new Texture("fondo/FondoParallax.png");
        capas[1] = new Texture("fondo/MedioParallax.png");
        capas[2] = new Texture("fondo/FrenteParallax.png");


        lblTitulo = new Label("Dungeons Without Dragons", juego.skin2); // Creo y defino un objeto label con la nueva font
        lblTitulo.setColor(Color.YELLOW);
        lblTitulo.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblTitulo.setPosition(juego.anchoJuego/ 16f, juego.altoJuego - lblTitulo.getHeight() * 1.1f);
        lblTitulo.setFontScale(1f,1.3f);
        escenario.addActor(lblTitulo);

        // Defino el botón Jugar con todas sus propiedades y lo añado al escenario
        btnJugar = new TextButton("Jugar", juego.skin); // Y declaramos el botón con la skin ya definida
        btnJugar.setPosition(juego.anchoJuego / 2.5f, juego.altoJuego / 11 * 6f);
        btnJugar.getLabel().setFontScale(5, 5);
        btnJugar.setSize(anchoBotones, altoBotones);
        escenario.addActor(btnJugar);


        // Defino el botón Personaje con todas sus propiedades y lo añado al escenario
        btnPersonaje = new TextButton("Personaje", juego.skin);
        btnPersonaje.setPosition(juego.anchoJuego / 2.5f, juego.altoJuego / 11 * 3.5f);
        btnPersonaje.getLabel().setFontScale(5, 5);
        btnPersonaje.setSize(anchoBotones, altoBotones);
        escenario.addActor(btnPersonaje);


        // Defino el botón Créditos con todas sus propiedades y lo añado al escenario
        btnCreditos = new TextButton("Creditos", juego.skin);
        btnCreditos.setPosition(juego.anchoJuego / 2.5f, juego.altoJuego / 11);
        btnCreditos.getLabel().setFontScale(5, 5);
        btnCreditos.setSize(anchoBotones, altoBotones);
        escenario.addActor(btnCreditos);


//        estiloOpciones.imageUp = imgBtnOpciones;
        btnOpciones = new ImageButton(imgBtnOpciones);
        btnOpciones.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnOpciones.setPosition(posXOpciones, posYOpciones);
        escenario.addActor(btnOpciones);
    }


    /**
     * Establezco los listeners de los componentes necesarios
     */
    @Override
    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla


        // LISTENERS DE LOS COMPONENTES :
        // Listener de btnJugar :
        btnJugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaNiveles = new PantallaNiveles(juego);
                juego.setScreen(juego.pantallaNiveles);
                juego.pantallaMenu.dispose();
            }
        });


        // Listener de btnPersonaje :
        btnPersonaje.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaPersonaje = new PantallaPersonaje(juego);
                juego.setScreen(juego.pantallaPersonaje);
                juego.pantallaMenu.dispose();
            }
        });


        // Listener de btnCreditos :
        btnCreditos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaCreditos = new PantallaCreditos(juego);
                juego.setScreen(juego.pantallaCreditos);
                juego.pantallaMenu.dispose();
            }
        });


        // Listener de btnOpciones :
        btnOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.backOpciones = 0;

                juego.pantallaMenuOpciones = new PantallaMenuOpciones(juego);
                juego.setScreen(juego.pantallaMenuOpciones);
                juego.pantallaMenu.dispose();
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

        // Comienzo el batch para empezar a dibujar el fondo con efecto Parallax
        juego.batch.begin();
            renderParallax(delta, contParallax); // Llamo a la función que dibuja el fondo con efecto Parallax
        juego.batch.end();

        // Dibujo el escenario y lo actualizo
        escenario.draw();
        escenario.act(delta);
    }


    /**
     * Ajusto el viewport y el tamaño de la cámara a la pantalla
     *
     * @param width Ancho de la cámara
     * @param height Alto de la cámara
     */
    @Override
    public void resize(int width, int height) {
        escenario.getViewport().update(width, height, true); // Actualizo el viewport
        juego.batch.setProjectionMatrix(camara.combined); // Establezco el projection matrix como que la cámara tiene establecido
    }


    /**
     * Limpio en memoria los componentes necesarios
     */
    @Override
    public void dispose() {
        // Limpio de la memoria las capas del efecto Parallax
        for (int i = 0; i < capas.length; i++) {
            capas[i].dispose();
        }

        escenario.dispose(); // Limpio de la memoria el escenario con todos sus elementos dentro
    }




    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


    /**
     * Gestiono la velocidad de las diferentes capas del Parallax y las dibujo
     * @param deltaTime Tiempo que se tarda en dibujar el siguiente fotograma
     */
    private void renderParallax(float deltaTime, int cont){
        // Recorro el array de capas del fondo con efecto Parallax
        for (int i = 0; i < capas.length; i++) {

            backgroundOffsets[i]++; // Aumento el offset

            if (cont == 0){ // Si es la primera vez que pasa por el bucle, establezco las velocidades de las dos capas más alejadas
                backgroundOffsets[0] = backgroundOffsets[i] / 4;
                backgroundOffsets[1] = backgroundOffsets[i] / 1.5f;
            }

            // Cuando cualquier capa se exceda del límite, vuelve al principio
            if(backgroundOffsets[i] > juego.anchoJuego){
                backgroundOffsets[i] = 0;
            }

            // Dibujo la capa con el efecto
            juego.batch.draw(capas[i], -backgroundOffsets[i], 0, juego.anchoJuego, juego.altoJuego);
            juego.batch.draw(capas[i], -backgroundOffsets[i] + juego.anchoJuego, 0, juego.anchoJuego, juego.altoJuego);
        }

        cont ++; // Aumento el contador
    }
}
