package dungeons.without.dragons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import static dungeons.without.dragons.Constantes.PPM;

/**
 * Clase referente al personaje que el usuario va a controlar en la partida
 */
public class Personaje {
    /**
     * Spritesheet
     */
    TextureRegion regionSpritesheet;

    /**
     * Animaciones para guardar las respectivas animaciones de la Spritesheet
     */
    Animation idle, gesto, andar, andarAtras, atacar, morir;

    /**
     * Animación a mostrar en la pantalla, se hará una relación entre las animaciones anteriores y esta para mostrar la animación correcta del personaje por pantalla
     */
    Animation animacion;

    /**
     * Variables que gestionan la posición del personaje
     */
    float posX, posY;

    /**
     * Variables que gestionan el tamaño del personaje
     */
    float anchoPersonaje, altoPersonaje;

    /**
     * Variable encargada de gestionar la velocidad a la que se mueve el personaje
     */
    float velocidadPersonaje;

    /**
     * Variable referente a la vida
     */
    float vida;

    /**
     * Cuerpo de Box2D
     */
    Body body;

    /**
     * Constructor que inicializa el personaje con su posición, tamaño, animaciones y el Body en Box2D
     *
     * @param regionSpritesheet Spritesheet del personaje seleccionado
     * @param world Mundo de Box2D en el que se incluirá
     * @param posX Posición inicial en el eje X
     * @param posY Posición inicial en el eje Y
     * @param anchoPersonaje Tamaño en el eje X
     * @param altoPersonaje Tamaño en el eje Y
     * @param genero Indica el género del personaje seleccionado
     */
    public Personaje(TextureRegion regionSpritesheet, World world, float posX, float posY, float anchoPersonaje, float altoPersonaje, boolean genero) {
        // Defino sus propiedades :

        this.velocidadPersonaje = 1;
        this.vida = 4;

        // Defino sus propiedades según los parámetros indicados :

        this.regionSpritesheet = regionSpritesheet;

        this.posX = posX;
        this.posY = posY;

        this.anchoPersonaje = anchoPersonaje;
        this.altoPersonaje = altoPersonaje;

        // Gestiono si el personaje es hombre o mujer
        if (genero){
            crearAnimacionesHombre(0.10f, 32, 32);
        }
        else{
            crearAnimacionesMujer(0.10f, 32, 32);
        }

        this.body = createBody(world, posX, posY, anchoPersonaje, altoPersonaje, false); // Creo el Body del personaje
    }


    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


    /**
     * Defino las variables de animación del personaje cuando es un hombre
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
     * Defino las variables de animación del personaje cuando es una mujer
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


    /**
     * Crea un Body de Box2D con los siguientes parámetros
     *
     * @param world Mundo de Box2D en el que meteremos el Body qye vamos a crear
     * @param x Posición en el eje X del Body
     * @param y Posición en el eje Y del Body
     * @param width Tamaño en el eje X del Body
     * @param height Tamaño en el eje Y del Body
     * @param isStatic Comprueba si el objeto debe ser estático o dinámico
     *
     * @return El Body creado correctamente
     */
    private Body createBody(World world, float x, float y, float width, float height, boolean isStatic){
        Body pBody; // Creo un Body

        BodyDef def = new BodyDef(); // Creo una definición del body

        // Controlo según el parámetro indicado si va a ser un Body estático o dinámico
        if (isStatic){
            def.type = BodyDef.BodyType.StaticBody;
        }
        else{
            def.type = BodyDef.BodyType.DynamicBody;
        }

        // Indico la posición y la rotación del Body, y lo creo en el mundo que se encuentre
        def.position.set(x / PPM, y / PPM); // Si le das un dato a un objeto Box2D, DIVIDE entre PPM
        def.fixedRotation = true;
        pBody = world.createBody(def);

        // Creo una forma como caja (cuadrado) para este Body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM); // Si le das un dato a un objeto Box2D, DIVIDE entre PPM

        pBody.createFixture(shape, 1.0f).setUserData(this); // Creo la Fixture del Body

        shape.dispose(); // Limpio en memoria la forma creada anteriormente

        return pBody; // Y finalmente devuelvo el Body creado y definido
    }
}
