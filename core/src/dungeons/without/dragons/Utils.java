package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static dungeons.without.dragons.Constantes.PPM;

/**
 * Clase que contiene funciones estáticas que se usarán en varias clases, por lo que así huelgo repetirlas
 */
public class Utils {

    //---------------------------------------------------------------------- FUNCIONES DE ANIMACIONES -------------------------------------------------------------------------------------------------------------------

    /**
     * Devuelvo la animación correspondiente dado el número de la línea en el spritesheet, la duración, el alto y el ancho de la imagen en el spritesheet
     *
     * @param regionSpritesheet Imagen de la Spritesheet
     * @param linea Número de línea de imágenes en el spritesheet
     * @param duracion Duración en segundos de cada imagen en la animación
     * @param ancho Ancho que ocupa cada imagen de la spritesheet
     * @param alto Alto que ocupa cada imagen de la spritesheet
     *
     * @return Animación correspondiente a los parámetros indicados
     */
    static Animation crearAnimacion(TextureRegion regionSpritesheet, int linea, float duracion, int ancho, int alto){
        TextureRegion[][] animaciones = regionSpritesheet.split(ancho, alto); // Defino un array bidimiensional resultante de dividir el TextureRegion en varios del tamaño de una imagen de animación

        // Creo un array unidimensional para guardar las imágenes de la animación que nos interesa
        TextureRegion[] imagenesPersonaje = new TextureRegion[10];


        // Recorro el array de animaciones
        for (int i = 0; i < animaciones.length; i++) {
            for (int j = 0; j < animaciones[i].length; j++) {
                if (linea == i){
                    imagenesPersonaje[j] = animaciones[i][j];
                }
            }
        }


        // Creo y defino
        Animation animacion = new Animation(duracion, imagenesPersonaje);

        return animacion;
    }


    /**
     * Devuelvo la animación en reversa correspondiente dado el número de la línea en el spritesheet, la duración, el alto y el ancho de la imagen en el spritesheet
     *
     * @param regionSpritesheet Imagen de la Spritesheet
     * @param linea Número de línea de imágenes en el spritesheet
     * @param duracion Duración en segundos de cada imagen en la animación
     * @param ancho Ancho que ocupa cada imagen de la spritesheet
     * @param alto Alto que ocupa cada imagen de la spritesheet
     *
     * @return Animación en reversa correspondiente a los parámetros indicados
     */
    static Animation crearAnimacionReversa(TextureRegion regionSpritesheet, int linea, float duracion, int ancho, int alto){
        TextureRegion[][] animaciones = regionSpritesheet.split(ancho, alto); // Defino un array bidimiensional resultante de dividir el TextureRegion en varios del tamaño de una imagen de animación

        // Creo un array unidimensional para guardar las imágenes de la animación que nos interesa
        TextureRegion[] imagenesPersonaje = new TextureRegion[10];

        int cont = 0; // Contador para poner las imágenes en orden mientras que las recorro a la inversa

        // Recorro el array de animaciones
        for (int i = 0; i < animaciones.length; i++) {
            for (int j = animaciones[i].length -1; j >= 0 ; j--) {
                if (linea == i){
                    imagenesPersonaje[cont] = animaciones[i][j];
                }

                cont ++;

                if (cont >= 10){
                    cont = 0;
                }
            }
        }

        // Creo y defino
        Animation animacion = new Animation(duracion, imagenesPersonaje);

        return animacion;
    }




    //---------------------------------------------------------------------- FUNCIONES DE BOX2D -------------------------------------------------------------------------------------------------------------------
    /**
     * Controla si el body del personaje es la fixture A en la colisión
     * @param a
     * @param b
     *
     * @return Booleana indicando si el personaje es la fixture A en la colisión o no
     */
    static boolean personajeEsA(Fixture a, Fixture b){
        return (a.getUserData() instanceof Personaje);
    }


    /**
     * Controla si el body del personaje es la fixture B en la colisión
     * @param a
     * @param b
     *
     * @return Booleana indicando si el personaje es la fixture B en la colisión o no
     */
    static boolean personajeEsB(Fixture a, Fixture b){
        return (b.getUserData() instanceof Personaje);
    }




    //---------------------------------------------------------------------- FUNCIONES DE TILEDMAP -------------------------------------------------------------------------------------------------------------------

    /**
     * Parsea una capa de objetos del TiledMap
     *
     * @param world Mundo de Box2D donde se renderizará el mapa
     * @param objects Objetos que contiene la capa de objetos indicada del TiledMap
     */
    static void parseTiledObjectLayer(World world, MapObjects objects){
        for (MapObject object : objects){ // Recorro las capas de objetos del TiledMap
            Shape shape; // Creo una forma para el objeto

            if (object instanceof PolygonMapObject){ // Si el objeto actual es un polígono...
                shape = createPolygon((PolygonMapObject) object); // Defino la forma del objeto llamando a su función
            }
            else{
                continue;
            }

            // Creo y defino el body junto a su BodyDef y su Fixture, además de añadirlo al mundo
            Body objectBody;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            objectBody = world.createBody(def);

            objectBody.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }




    /**
     * Crea la forma que tiene el objeto de tipo Polygon indicado en la capa de objetos del TiledMap
     *
     * @param polygon Objeto Polygon dentro de la capa de objetos del TiledMap
     *
     * @return La forma resultante de polygon
     */
    private static ChainShape createPolygon(PolygonMapObject polygon){
        float[] vertices = polygon.getPolygon().getTransformedVertices(); // Recojo los vértices que forman la línea del polígono

        // Recojo dos vértices por línea del polígono, así que voy a juntarlos todos para hacer una sola línea
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM); // Si le das un dato a un objeto Box2D, DIVIDE entre PPM
        }

        // Creo el objeto ChainShape, lo defino y lo devuelvo
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);
        return chainShape;
    }
}
