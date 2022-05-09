package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Clase que extiende de Pantallas, referente a la pantalla de selección de personaje
 */
public class PantallaPersonaje extends Pantallas {
    /**
     * Escenario a dibujar
     */
    private Stage escenario;

    /**
     * Botón que nos llevará hasta la pantalla del menú de opciones
     */
    private ImageButton btnOpciones;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    private ImageButton btnBack;

    /**
     * Actor que contiene la animación del personaje que se muestra
     */
    private ActorPersonaje muestraPersonaje;

    /**
     * Define la velocidad de muestra de las animaciones
     */
    float duracionAnimacion = 0.01f;

    /**
     * Imagen que va a ser mostrada en el botón que cambia de clase de personaje hacia atrás
     */
    private TextureRegionDrawable imgBtnAtras;

    /**
     * Botón con imagen que cambia de clase de personaje hacia atrás
     */
    private ImageButton btnAtras;

    /**
     * Imagen que va a ser mostrada en el botón que cambia de clase de personaje hacia delante
     */
    private TextureRegionDrawable imgBtnAdelante;

    /**
     * Botón con imagen que cambia de clase de personaje hacia delante
     */
    private ImageButton btnAdelante;

    /**
     * Variables referentes al personaje que se muestra
     */
    float posPersonajeX, posPersonajeY, anchoPersonaje, altoPersonaje;

    /**
     * Imagen que se mostrará en el botón que cambia el género del personaje que se está mostrando
     */
    private TextureRegionDrawable imgBtnCambioGenero;

    /**
     * Estilo del botón que cambia el género del personaje que se está mostrando
     */
    private ImageButton.ImageButtonStyle estiloGenero;

    /**
     * Botón que cambia el género del personaje que se está mostrando
     */
    private ImageButton btnCambioGenero;

    /**
     * Etiqueta de texto que muestra el nombre de la clase de personaje que se está visualizando
     */
    private Label lblPersonaje;

    /**
     * Cadena que indica el nombre de la clase inicial del personaje cuando se carga esta pantalla
     */
    private String clasePersonajeInicial;


    /**
     * Constructor de la pantalla de selección de personaje.
     * Define las diferentes variables de la pantalla, como las imágenes del personaje y el escenario con todos sus componentes.
     *
     * @param juego Aplicación principal
     */
    public PantallaPersonaje(DungeonsWithoutDragons juego) {
        super(juego);


        escenario = new Stage(); // Defino el escenario

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);


        posPersonajeX = juego.anchoJuego / 2.75f;
        posPersonajeY = juego.altoJuego / 4f;
        anchoPersonaje = juego.anchoJuego / 2.65f;
        altoPersonaje = juego.anchoJuego / 2.65f;

        // Muestro al personaje por defecto
        muestraPersonaje = new ActorPersonaje(juego.imagenesPersonajes[juego.contadorImagenesPersonaje], posPersonajeX, posPersonajeY, anchoPersonaje, altoPersonaje, juego.esHombre);
        escenario.addActor(muestraPersonaje);

        // Botones que representan flechas que permiten cambiar la clase del personaje que se selecciona :

        // Defino el botón de la flecha hacia atrás y sus propiedades :
        imgBtnAtras = new TextureRegionDrawable(new TextureRegion(new Texture("botones/flechaAtras.png")));
        btnAtras = new ImageButton(imgBtnAtras);
        btnAtras.setSize(anchoBotones * 1.5f, altoBotones * 1.5f);
        btnAtras.setPosition(juego.anchoJuego / 18f, juego.altoJuego / 2);
        escenario.addActor(btnAtras);


        // Defino el botón de la flecha hacia adelante y sus propiedades :
        imgBtnAdelante = new TextureRegionDrawable(new TextureRegion(new Texture("botones/flechaAdelante.png")));
        btnAdelante = new ImageButton(imgBtnAdelante);
        btnAdelante.setSize(anchoBotones * 1.5f, altoBotones * 1.5f);
        btnAdelante.setPosition(juego.anchoJuego / 1.5f, juego.altoJuego/ 2);
        escenario.addActor(btnAdelante);


        // Etiqueta : Dependiendo de qué personaje sea, cambio su texto
        switch(juego.contadorImagenesPersonaje){
            case 0:
                if (juego.esHombre){
                    clasePersonajeInicial = "Guerrero";
                }
                else{
                    clasePersonajeInicial = "Guerrera";
                }
                break;

            case 1:
                if (juego.esHombre){
                    clasePersonajeInicial = "Explorador";
                }
                else{
                    clasePersonajeInicial = "Exploradora";
                }
                break;

            case 2:
                if (juego.esHombre){
                    clasePersonajeInicial = "Picaro";
                }
                else{
                    clasePersonajeInicial = "Picara";
                }
                break;

            case 3:
                if (juego.esHombre){
                    clasePersonajeInicial = "Mago";
                }
                else{
                    clasePersonajeInicial = "Maga";
                }
                break;

            case 4:
                if (juego.esHombre){
                    clasePersonajeInicial = "Clerigo";
                }
                else{
                    clasePersonajeInicial = "Cleriga";
                }
                break;
        }

        lblPersonaje = new Label(clasePersonajeInicial, juego.skin2);
        lblPersonaje.setPosition(posPersonajeX + juego.anchoJuego / 17, posPersonajeY - juego.altoJuego / 7);
        lblPersonaje.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblPersonaje.setFontScale(1f,1.3f);
        escenario.addActor(lblPersonaje);


        // Defino el botón que cambia de género al personaje seleccionado y sus propiedades :
        imgBtnCambioGenero = new TextureRegionDrawable(new TextureRegion(new Texture("botones/generos.png")));
        estiloGenero = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));
        estiloGenero.imageUp = imgBtnCambioGenero;
        btnCambioGenero = new ImageButton(estiloGenero);
        btnCambioGenero.setSize(anchoOpcionesYBack * 1.5f, anchoOpcionesYBack * 1.5f);
        btnCambioGenero.setPosition(juego.anchoJuego / 6f, juego.altoJuego/ 15f);
        escenario.addActor(btnCambioGenero);


        // Defino el botón de opciones y sus propiedades :
        btnOpciones = new ImageButton(imgBtnOpciones);
        btnOpciones.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnOpciones.setPosition(posXOpciones, posYOpciones);
        escenario.addActor(btnOpciones);
    }


    /**
     * Establezco los listeners de los componentes correspondientes
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

                juego.pantallaPersonaje.dispose();
            }
        });


        // Listener de btnOpciones :
        btnOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.backOpciones = 1;

                juego.pantallaMenuOpciones = new PantallaMenuOpciones(juego);
                juego.setScreen(juego.pantallaMenuOpciones);
                juego.pantallaPersonaje.dispose();
            }
        });


        // Listener de btnAtras :
        btnAtras.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.contadorImagenesPersonaje --; // Retrocedo una posición para visualizar personajes

                if (juego.contadorImagenesPersonaje < 0){ // Si  el contador se sale de los límites, lo establezco en la última posición
                    juego.contadorImagenesPersonaje = 4;
                }

                muestraPersonaje.regionSpritesheet = juego.imagenesPersonajes[juego.contadorImagenesPersonaje]; // Establezco la posición indicada del personaje

                // Dependiendo de su género, establezco sus animaciones correspondientes
                if(juego.esHombre){
                    muestraPersonaje.crearAnimacionesHombre(0.15f, 32, 32);
                }
                else{
                    muestraPersonaje.crearAnimacionesMujer(0.15f, 32, 32);
                }

                // Y actualizo la etiqueta que muestra el nombre de su clase
                actualizarLabel(juego.contadorImagenesPersonaje);

                juego.configuracion.putInteger("contadorImagenesPersonaje", juego.contadorImagenesPersonaje); // Actualizo la variable en el archivo de configuración
                juego.configuracion.flush();
            }
        });


        // Listener de btnAdelante :
        btnAdelante.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                juego.contadorImagenesPersonaje ++; // Avanzo una posición para visualizar personajes

                if (juego.contadorImagenesPersonaje > 4){ // Si  el contador se sale de los límites, lo establezco en la primera posición
                    juego.contadorImagenesPersonaje = 0;
                }

                muestraPersonaje.regionSpritesheet = juego.imagenesPersonajes[juego.contadorImagenesPersonaje]; // Establezco la posición indicada del personaje

                // Dependiendo de su género, establezco sus animaciones correspondientes
                if(juego.esHombre){
                    muestraPersonaje.crearAnimacionesHombre(0.15f, 32, 32);
                }
                else{
                    muestraPersonaje.crearAnimacionesMujer(0.15f, 32, 32);
                }

                // Y actualizo la etiqueta que muestra el nombre de su clase
                actualizarLabel(juego.contadorImagenesPersonaje);

                juego.configuracion.putInteger("contadorImagenesPersonaje", juego.contadorImagenesPersonaje); // Actualizo la variable en el archivo de configuración
                juego.configuracion.flush();
            }
        });


        // Listener de btnCambioGenero :
        btnCambioGenero.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                juego.esHombre = !juego.esHombre;

                if(juego.esHombre){
                    muestraPersonaje.crearAnimacionesHombre(0.15f, 32, 32);
                }
                else{
                    muestraPersonaje.crearAnimacionesMujer(0.15f, 32, 32);
                }

                actualizarLabel(juego.contadorImagenesPersonaje);

                juego.configuracion.putBoolean("esHombre", juego.esHombre); // Actualizo la variable en el archivo de configuración
                juego.configuracion.flush();
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


        duracionAnimacion += delta; // Cada vez que se cargue el nuevo fotograma, se actualiza la variable duracionAnimacion para saber cuánto tiempo lleva mostrándose la animación

        muestraPersonaje.animacion = (TextureRegion) muestraPersonaje.idle.getKeyFrame(duracionAnimacion, true); // Muestro el fotograma de la animación correspondiente


        // Dibujo el escenario y lo actualizo
        escenario.draw();
        escenario.act(delta);
    }


    /**
     * Limpio en memoria los recursos de los componentes en el escenario
     */
    @Override
    public void dispose() {
        escenario.dispose(); // Limpio de la memoria el escenario con todos sus elementos dentro
    }



    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------

    /**
     * Actualiza la etiqueta que muestra el nombre de la clase del personaje
     *
     * @param contadorImagenesPersonaje Contador que indica qué clase estamos visualizando del personaje
     */
    private void actualizarLabel(int contadorImagenesPersonaje){
        // Gestiono y el personaje es actualmente hombre o mujer para mostrar el nombre en masculino o femenino
        if(juego.esHombre){
            switch (contadorImagenesPersonaje){
                case 0:
                    lblPersonaje.setText("Guerrero");
                    break;

                case 1:
                    lblPersonaje.setText("Explorador");
                    break;

                case 2:
                    lblPersonaje.setText("Picaro");
                    break;

                case 3:
                    lblPersonaje.setText("Mago");
                    break;

                case 4:
                    lblPersonaje.setText("Clerigo");
                    break;
            }
        }
        else{
            switch (contadorImagenesPersonaje){
                case 0:
                    lblPersonaje.setText("Guerrera");
                    break;

                case 1:
                    lblPersonaje.setText("Exploradora");
                    break;

                case 2:
                    lblPersonaje.setText("Picara");
                    break;

                case 3:
                    lblPersonaje.setText("Maga");
                    break;

                case 4:
                    lblPersonaje.setText("Cleriga");
                    break;
            }
        }
    }
}
