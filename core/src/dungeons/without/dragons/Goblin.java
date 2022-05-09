package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static dungeons.without.dragons.Constantes.PPM;

/**
 * Clase que representa un tipo de enemigo para los niveles
 */
public class Goblin {
    /**
     * Spritesheet
     */
    TextureRegion regionSpritesheet;

    /**
     * Animaciones para guardar las respectivas animaciones de la Spritesheet
     */
    Animation idle, gesto, andar, andarAtras, atacar, morir;

    /**
     * Animación que mostraremos finalmente en la escena
     */
    Animation animacion;

    /**
     * Variables que gestionan la posición de la imagen
     */
    float posX, posY;

    /**
     * Variables que gestionan el tamaño
     */
    float anchoGoblin, altoGoblin;

    /**
     * Variable encargada de gestionar la velocidad a la que se mueve
     */
    float velocidad;

    /**
     * Variable referente a la vida
     */
    float vida;

    /**
     * Cuerpo de Box2D
     */
    Body body;

    /**
     * Variable encargada de comprobar si este enemigo está muerto o no
     */
    boolean muerto;

    /**
     * Indica si está colisionando con el jugador
     */
    boolean seleccionado;

    /**
     * Efecto de sonido para cuando se muere
     */
    Sound muerte;


    /**
     * Constructor que inicializa el esqueleto con su posición, tamaño, animaciones, su Body en Box2D y su efecto de sonido de muerte
     *
     * @param world Mundo de Box2D en el que se inculirá
     * @param posX Posición en el eje X del goblin
     * @param posY Posición en el eje Y del goblin
     * @param anchoGoblin Tamaño en el eje X del goblin
     * @param altoGoblin Tamaño en el eje Y del goblin
     * @param tipo Booleana que define el tipo de goblin que va a ser de los dos que hay en la spritesheet
     */
    public Goblin(World world, float posX, float posY, float anchoGoblin, float altoGoblin, boolean tipo) {
        // Establezco los valores de sus propiedades :

        this.velocidad = 2;
        this.muerto = false;
        this.vida = 1;
        this.seleccionado = false;

        this.regionSpritesheet = new TextureRegion(new Texture("enemigos/goblins.png"));

        this.muerte = Gdx.audio.newSound(Gdx.files.internal("sonidos/goblins/muerteGoblin.wav"));


        // Establezco valores de sus propiedades correspondientes a los parámetros indicados :

        this.posX = posX;
        this.posY = posY;

        this.anchoGoblin = anchoGoblin;
        this.altoGoblin = altoGoblin;

        // Controlo mediante un parámetro indicado si es de tipo1 o de tipo2 para sus animaciones
        if (tipo){
            crearAnimacionesTipo1(0.15f, 32, 32);
        }
        else{
            crearAnimacionesTipo2(0.15f, 32, 32);
        }

        this.body = createBody(world, posX, posY, anchoGoblin, altoGoblin, true); // Creo su Body con sus propiedades
    }


    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


    /**
     * Defino las variables de animación del goblin tipo 1
     *
     * @param duracion Duración en segundos de cada imagen
     * @param ancho Ancho de cada imagen de la spritesheet
     * @param alto Alto de cada imagen de la spritesheet
     */
    private void crearAnimacionesTipo1(float duracion, int ancho, int alto){
        idle = Utils.crearAnimacion(regionSpritesheet, 0, duracion, ancho, alto);
        gesto = Utils.crearAnimacion(regionSpritesheet, 1, duracion, ancho, alto);
        andar = Utils.crearAnimacion(regionSpritesheet, 2, duracion, ancho, alto);
        andarAtras = Utils.crearAnimacionReversa(regionSpritesheet, 2, duracion, ancho, alto);
        atacar = Utils.crearAnimacion(regionSpritesheet, 3, duracion, ancho, alto);
        morir = Utils.crearAnimacion(regionSpritesheet, 4, duracion, ancho, alto);
    }


    /**
     * Defino las variables de animación del goblin tipo 2
     *
     * @param duracion Duración en segundos de cada imagen
     * @param ancho Ancho de cada imagen de la spritesheet
     * @param alto Alto de cada imagen de la spritesheet
     */
    void crearAnimacionesTipo2(float duracion, int ancho, int alto){
        idle = Utils.crearAnimacion(regionSpritesheet, 5, duracion, ancho, alto);
        gesto = Utils.crearAnimacion(regionSpritesheet, 6, duracion, ancho, alto);
        andar = Utils.crearAnimacion(regionSpritesheet, 7, duracion, ancho, alto);
        andarAtras = Utils.crearAnimacion(regionSpritesheet, 7, duracion, ancho, alto);
        atacar = Utils.crearAnimacion(regionSpritesheet, 8, duracion, ancho, alto);
        morir = Utils.crearAnimacion(regionSpritesheet, 9, duracion, ancho, alto);
    }


    /**
     * Crea un Body de Box2D con los siguientes parámetros
     *
     * @param world Mundo de Box2D en el que meteremos el Body que vamos a crear
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
