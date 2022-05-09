package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static dungeons.without.dragons.Constantes.PPM;

/**
 * Clase referente al escenario y los componentes que aparecen en las pantallas de la partida del juego
 */
public class PantallasPartida {
    /**
     * Clase principal
     */
    DungeonsWithoutDragons juego;

    /**
     * Variables que guardan los valores de ancho y alto del tamaño que deben tener los botones de opciones y back
     */
    float anchoOpcionesYBack, altoOpcionesYBack, posXOpciones, posYOpciones, posXBack, posYBack;

    /**
     * Imagen que servirá de fondo para las pantallas que lo necesiten
     */
    Texture fondo;

    /**
     * Variable que establece una imagen al botón de opciones
     */
    TextureRegionDrawable imgBtnOpciones;

    /**
     * Variable que establece un estilo al botón de opciones
     */
    ImageButton.ImageButtonStyle estiloOpciones;

    /**
     * Variable que establece una imagen al botón back
     */
    TextureRegionDrawable imgBtnBack;

    /**
     * Variable que establece un estilo al botón back
     */
    ImageButton.ImageButtonStyle estiloBack;

    /**
     * Variables que definen el tamaño en el eje X y el eje Y de los botones de la partida
     */
    float anchoBotonesPartida, altoBotonesPartida;


    /**
     * Variable que establece una imagen al botón mover hacia arriba
     */
    TextureRegionDrawable imgBtnArriba;

    /**
     * Variable que establece un estilo al botón mover hacia arriba
     */
    ImageButton.ImageButtonStyle estiloArriba;

    /**
     * Variable que establece una posición al botón mover hacia arriba
     */
    float posXArriba, posYArriba;


    /**
     * Variable que establece una imagen al botón mover hacia abajo
     */
    TextureRegionDrawable imgBtnAbajo;

    /**
     * Variable que establece un estilo al botón mover hacia abajo
     */
    ImageButton.ImageButtonStyle estiloAbajo;

    /**
     * Variable que establece una posición al botón mover hacia abajo
     */
    float posXAbajo, posYAbajo;


    /**
     * Variable que establece una imagen al botón mover hacia la izquierda
     */
    TextureRegionDrawable imgBtnIzquierda;

    /**
     * Variable que establece un estilo al botón mover hacia la izquierda
     */
    ImageButton.ImageButtonStyle estiloIzquierda;

    /**
     * Variable que establece una posición al botón mover hacia la izquierda
     */
    float posXIzquierda, posYIzquierda;


    /**
     * Variable que establece una imagen al botón mover hacia la derecha
     */
    TextureRegionDrawable imgBtnDerecha;

    /**
     * Variable que establece un estilo al botón mover hacia la derecha
     */
    ImageButton.ImageButtonStyle estiloDerecha;

    /**
     * Variable que establece una posición al botón mover hacia la derecha
     */
    float posXDerecha, posYDerecha;


    /**
     * Variable que establece una imagen al botón atacar
     */
    TextureRegionDrawable imgBtnAtacar;

    /**
     * Variable que establece una posición al botón atacar
     */
    float posXAtacar, posYAtacar;

    /**
     * Variable que establece una imagen al botón recoger
     */
    TextureRegionDrawable imgBtnRecoger;

    /**
     * Variable que establece una posición al botón recoger
     */
    float posXRecoger, posYRecoger;


    /**
     * Sonidos del personaje
     */
    Sound atacar, muertePersonaje;

    /**
     * Escenario a dibujar
     */
    Stage escenario;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    ImageButton btnBack;


    /**
     * Botones para las acciones del personaje
     */
    ImageButton btnArriba, btnAbajo, btnIzquierda, btnDerecha, btnRecoger, btnAtacar;


    /**
     * Constructor del escenario de las pantallas de los niveles de la partida, inicializa y define los componentes del escenario
     *
     * @param juego Clase principal
     */
    public PantallasPartida(DungeonsWithoutDragons juego) {
        this.juego = juego;

        fondo = new Texture("fondo/FondoParallax.png"); // Establezco la imagen de fondo

        // Dimensiones y posiciones de los botones Back y Opciones
        anchoOpcionesYBack = juego.anchoJuego / 12;
        altoOpcionesYBack = juego.altoJuego / 8;
        posXOpciones = juego.anchoJuego / 1.15f;
        posYOpciones = juego.altoJuego / 25;
        posXBack = juego.anchoJuego / 20;
        posYBack = juego.altoJuego - altoOpcionesYBack * 1.5f;

        // Defino la imagen y el estilo del botón que llevará hasta la pantalla del menú de opciones
        imgBtnOpciones = new TextureRegionDrawable(new TextureRegion(new Texture("botones/opcionesMed.png")));
        estiloOpciones = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));

        // Defino la imagen y el estilo del botón que llevará hasta la anterior pantalla
        imgBtnBack = new TextureRegionDrawable(new TextureRegion(new Texture("botones/back.jpg")));
        estiloBack = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));




        // Establezco el ancho y el alto de los botones de la partida
        anchoBotonesPartida = anchoOpcionesYBack * 1.15f;
        altoBotonesPartida = altoOpcionesYBack * 1.15f;

        // Imagen y estilo de los botones de movimiento :
        imgBtnArriba = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/arriba.png")));
        estiloArriba = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));
        estiloArriba.imageUp = imgBtnArriba;

        imgBtnAbajo = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/abajo.png")));
        estiloAbajo = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));
        estiloAbajo.imageUp = imgBtnAbajo;

        imgBtnIzquierda = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/izquierda.png")));
        estiloIzquierda = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));
        estiloIzquierda.imageUp = imgBtnIzquierda;

        imgBtnDerecha = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/derecha.png")));
        estiloDerecha = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));
        estiloDerecha.imageUp = imgBtnDerecha;




        muertePersonaje = Gdx.audio.newSound(Gdx.files.internal("sonidos/muerte.wav")); // Establezco el sonido de muerte del personaje

        // Dependiendo de la clase del personaje elegido, se cambia la imagen del botón atacar y los sonidos del personaje :
        switch(juego.contadorImagenesPersonaje){
            case 0:
                imgBtnAtacar = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/atacarGuerrero.png")));
                atacar = Gdx.audio.newSound(Gdx.files.internal("sonidos/ataqueespada.mp3"));
                break;
            case 1:
                imgBtnAtacar = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/atacarExplorador.png")));
                atacar = Gdx.audio.newSound(Gdx.files.internal("sonidos/ataquearco.mp3"));
                break;

            case 2:
                imgBtnAtacar = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/atacarPicaro.png")));
                atacar = Gdx.audio.newSound(Gdx.files.internal("sonidos/ataqueespada.mp3"));
                break;

            case 3:
                imgBtnAtacar = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/atacarMago.png")));
                atacar = Gdx.audio.newSound(Gdx.files.internal("sonidos/ataqueconjuro.wav"));
                break;

            case 4:
                imgBtnAtacar = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/atacarClerigo.png")));
                atacar = Gdx.audio.newSound(Gdx.files.internal("sonidos/ataque.wav"));
                break;
        }


        // Imagen de botón recoger
        imgBtnRecoger = new TextureRegionDrawable(new TextureRegion(new Texture("botones/partida/recoger.png")));


        // Establezco las posiciones de los botones de movimiento :
        posXArriba = posXBack * 4f;
        posYArriba = posYOpciones * 7f;

        posXAbajo = posXBack * 4f;
        posYAbajo = posYOpciones;

        posXIzquierda = posXBack;
        posYIzquierda = posYOpciones * 3f;

        posXDerecha = posXBack * 7f;
        posYDerecha = posYOpciones * 3f;


        // Establezco las posiciones de los botones de atacar y recoger :
        posXRecoger = posXOpciones / 1.45f;
        posYRecoger = posYOpciones;

        posXAtacar = posXOpciones / 1.1f;
        posYAtacar = posYOpciones;




        // Si está activada la música, se cambia a la música de partida
        if (juego.musica){
            juego.musicaMenu.stop();
            juego.musicaMenu.setPosition(0);

            juego.musicaPartida.play();
        }



        // Escenario Scene2D :
        escenario = new Stage();

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);


        // Botones de movimiento
        btnArriba = new ImageButton(estiloArriba);
        btnArriba.setSize(anchoBotonesPartida, altoBotonesPartida);
        btnArriba.setPosition(posXArriba, posYArriba);
        escenario.addActor(btnArriba);

        btnAbajo = new ImageButton(estiloAbajo);
        btnAbajo.setSize(anchoBotonesPartida, altoBotonesPartida);
        btnAbajo.setPosition(posXAbajo, posYAbajo);
        escenario.addActor(btnAbajo);

        btnIzquierda = new ImageButton(estiloIzquierda);
        btnIzquierda.setSize(anchoBotonesPartida, altoBotonesPartida);
        btnIzquierda.setPosition(posXIzquierda, posYIzquierda);
        escenario.addActor(btnIzquierda);

        btnDerecha = new ImageButton(estiloDerecha);
        btnDerecha.setSize(anchoBotonesPartida, altoBotonesPartida);
        btnDerecha.setPosition(posXDerecha, posYDerecha);
        escenario.addActor(btnDerecha);


        // Botones atacar y recoger
        btnRecoger = new ImageButton(imgBtnRecoger);
        btnRecoger.setSize(anchoBotonesPartida, altoBotonesPartida);
        btnRecoger.setPosition(posXRecoger, posYRecoger);
        escenario.addActor(btnRecoger);

        btnAtacar = new ImageButton(imgBtnAtacar);
        btnAtacar.setSize(anchoBotonesPartida, altoBotonesPartida);
        btnAtacar.setPosition(posXAtacar, posYAtacar);
        escenario.addActor(btnAtacar);
    }



    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla
    }


    /**
     * Dibujo el escenario en la pantalla
     */
    public void draw() {

        // Dibujo el escenario y lo actualizo
        escenario.draw();
    }

    /**
     * Limpio en memoria el escenario y el fondo de la pantalla
     */
    public void dispose(){
        fondo.dispose();
        escenario.dispose();
    }
}
