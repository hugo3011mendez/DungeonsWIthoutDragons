package dungeons.without.dragons;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import dungeons.without.dragons.DungeonsWithoutDragons;

/**
 * Creo esta clase como base para todas las pantallas creadas en adelante
 */
public class Pantallas implements Screen {
    /**
     * Guardo el juego en una variable para que la usen todas las pantallas
     */
    protected DungeonsWithoutDragons juego;

    /**
     * Variables que guardan los valores de ancho y alto del tamaño de los botones principales de los menús
     */
    float anchoBotones, altoBotones;

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
     * Variable que establece una imagen y al botón back
     */
    TextureRegionDrawable imgBtnBack;

    /**
     * Variable que establece un estilo y al botón back
     */
    ImageButton.ImageButtonStyle estiloBack;


    /**
     * Creo una instancia de esta clase, definiendo sus propiedades
     *
     * @param juego
     */
    public Pantallas(DungeonsWithoutDragons juego){ // Creo un constructor de esta clase al que se le pasa el juego como parámetro
        this.juego = juego;

        // Defino las variables en el constructor :
        anchoBotones = juego.anchoJuego / 4;
        altoBotones = juego.altoJuego / 7;

        anchoOpcionesYBack = juego.anchoJuego / 12;
        altoOpcionesYBack = juego.altoJuego / 8;
        posXOpciones = juego.anchoJuego / 1.15f;
        posYOpciones = juego.altoJuego / 25;
        posXBack = juego.anchoJuego / 20;
        posYBack = juego.altoJuego - altoOpcionesYBack * 1.5f;

        fondo = new Texture("fondo/FondoParallax.png"); // Establezco la imagen de fondo para los menús que la necesiten


        // Defino la imagen y el estilo del botón que llevará hasta la pantalla del menú de opciones
        imgBtnOpciones = new TextureRegionDrawable(new TextureRegion(new Texture("botones/opcionesMed.png")));
        estiloOpciones = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));

        // Defino la imagen y el estilo del botón que llevará hasta la anterior pantalla
        imgBtnBack = new TextureRegionDrawable(new TextureRegion(new Texture("botones/back.jpg")));
        estiloBack = new ImageButton.ImageButtonStyle(juego.skin.get(Button.ButtonStyle.class));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondo.dispose(); // Limpio la variable para dejar memoria libre
    }
}
