package dungeons.without.dragons;

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
 * Esta clase hace referencia a un tipo de enemigo, pero no lo uso porque hay un error que aún no he sabido solucionar para animarlo
 */
public class Minotauro {

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
    Animation animacion;

    /**
     * Variables que gestionan la posición de la imagen
     */
    float posX, posY;

    /**
     * Variables que gestionan el tamaño
     */
    float anchoMinotauro, altoMinotauro;

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
     * Crea una instancia de un enemigo de este tipo, definiendo sus porpiedades dados sus parámetros
     *
     * @param world Mundo de Box2D donde se va a encontrar
     * @param posX Posición inicial en el eje X
     * @param posY Posición inicial en el eje Y
     * @param anchoMinotauro Tamaño en el eje X
     * @param altoMinotauro Tamaño en el eje Y
     */
    public Minotauro(World world, float posX, float posY, float anchoMinotauro, float altoMinotauro) {
        // Establezco los valores de sus propiedades :

        this.muerto = false;
        this.vida = 5;
        this.seleccionado = false;

        this.regionSpritesheet = new TextureRegion(new Texture("enemigos/minotauro.png"));

        crearAnimaciones(0.15f, 50, 50); // todo ver por qué fallan las imagenes de la animacion del minotauro

        // Establezco valores de sus propiedades correspondientes a los parámetros indicados :
        this.posX = posX;
        this.posY = posY;

        this.anchoMinotauro = anchoMinotauro;
        this.altoMinotauro = altoMinotauro;

        this.body = createBody(world, posX, posY, anchoMinotauro, altoMinotauro, false); // Creo su Body con sus propiedades
    }


    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


    /**
     * Defino las variables de animación del minotauro
     *
     * @param duracion Duración en segundos de cada imagen
     * @param ancho Ancho de cada imagen de la spritesheet
     * @param alto Alto de cada imagen de la spritesheet
     */
    void crearAnimaciones(float duracion, int ancho, int alto){
        idle = Utils.crearAnimacion(regionSpritesheet, 0, duracion, ancho, alto);
        gesto = Utils.crearAnimacion(regionSpritesheet, 1, duracion, ancho, alto);
        andar = Utils.crearAnimacion(regionSpritesheet, 2, duracion, ancho, alto);
        andarAtras = Utils.crearAnimacionReversa(regionSpritesheet, 2, duracion, ancho, alto);
        atacar = Utils.crearAnimacion(regionSpritesheet, 3, duracion, ancho, alto);
        morir = Utils.crearAnimacion(regionSpritesheet, 4, duracion, ancho, alto);
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
