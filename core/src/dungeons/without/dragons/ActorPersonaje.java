package dungeons.without.dragons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *  Clase que extiende de Actor de Scene2D y gestiona la visualización del personaje seleccionado en la pantalla pantallaPersonaje
 */
public class ActorPersonaje extends Actor {
    /**
     * Spritesheet
     */
    TextureRegion regionSpritesheet;

    /**
     * Animaciones para guardar las respectivas animaciones de la Spritesheet
     */
    Animation idle, gesto, andar, andarAtras, atacar, morir;

    /**
     * TextureRegion que mostraremos finalmente en la escena
     */
    TextureRegion animacion;

    /**
     * Variables que gestionan la posición de la imagen
     */
    float posX, posY;

    /**
     * Variables que gestionan el tamaño del personaje
     */
    float anchoPersonaje, altoPersonaje;


    /**
     * Constructor que incializa el personaje con su posición, tamaño y animaciones
     *
     * @param regionSpritesheet Imagen referente a la spritesheet del personaje
     * @param posX Posición en el eje X del personaje
     * @param posY Posición en el eje Y del personaje
     * @param anchoPersonaje Tamaño en el eje X del personaje
     * @param altoPersonaje Tamaño en el eje Y del personaje
     * @param genero Género del personaje
     */
    public ActorPersonaje(TextureRegion regionSpritesheet, float posX, float posY, float anchoPersonaje, float altoPersonaje, boolean genero) {
        // Establezco los valores de las propiedades correspondientes a los parámetros pasados

        this.regionSpritesheet = regionSpritesheet;

        this.posX = posX;
        this.posY = posY;

        this.anchoPersonaje = anchoPersonaje;
        this.altoPersonaje = altoPersonaje;

        // Controlo si el personaje es hombre o mujer, y llamo a su función correspondiente
        if (genero){
            crearAnimacionesHombre(0.15f, 32, 32);
        }
        else{
            crearAnimacionesMujer(0.15f, 32, 32);
        }

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        // En la función draw del actor dibujo el personaje en cuestión
        batch.draw(animacion, posX, posY, anchoPersonaje, altoPersonaje);
    }


    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


    /**
     * Defino las variables de animación del personaje cuando es un hombre, llamando a la función de crear animación en Utils
     *
     * @param duracion Duración en segundos de cada imagen
     * @param ancho Ancho de cada imagen de la spritesheet
     * @param alto Alto de cada imagen de la spritesheet
     */
    void crearAnimacionesHombre(float duracion, int ancho, int alto){
        idle = Utils.crearAnimacion(regionSpritesheet, 0, duracion, ancho, alto);
        gesto = Utils.crearAnimacion(regionSpritesheet, 1, duracion, ancho, alto);
        andar = Utils.crearAnimacion(regionSpritesheet, 2, duracion, ancho, alto);
        andarAtras = Utils.crearAnimacionReversa(regionSpritesheet, 2, duracion, ancho, alto);
        atacar = Utils.crearAnimacion(regionSpritesheet, 3, duracion, ancho, alto);
        morir = Utils.crearAnimacion(regionSpritesheet, 4, duracion, ancho, alto);
    }


    /**
     * Defino las variables de animación del personaje cuando es una mujer, llamando a la función de crear animación en Utils
     *
     * @param duracion Duración en segundos de cada imagen
     * @param ancho Ancho de cada imagen de la spritesheet
     * @param alto Alto de cada imagen de la spritesheet
     */
    void crearAnimacionesMujer(float duracion, int ancho, int alto){
        idle = Utils.crearAnimacion(regionSpritesheet, 5, duracion, ancho, alto);
        gesto = Utils.crearAnimacion(regionSpritesheet, 6, duracion, ancho, alto);
        andar = Utils.crearAnimacion(regionSpritesheet, 7, duracion, ancho, alto);
        andarAtras = Utils.crearAnimacionReversa(regionSpritesheet, 7, duracion, ancho, alto);
        atacar = Utils.crearAnimacion(regionSpritesheet, 8, duracion, ancho, alto);
        morir = Utils.crearAnimacion(regionSpritesheet, 9, duracion, ancho, alto);
    }
}
