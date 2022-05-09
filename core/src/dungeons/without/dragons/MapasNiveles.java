package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static dungeons.without.dragons.Constantes.PPM;
import static dungeons.without.dragons.Constantes.SCALE;

/**
 * Clase que representa la pantalla donde se combinarán Box2D y Scene2D para los niveles de la partida, servirá de clase padre para los 3 niveles
 */
public class MapasNiveles implements Screen {
    /**
     * Clase principal del juego
     */
    DungeonsWithoutDragons juego;

    /**
     * Cámara que mostrará lo de esta pantalla
     */
    private  OrthographicCamera camara;

    /**
     * Mundo de Box2D
     */
    World world;

    /**
     * Renderiza el mundo con lo que contiene
     */
    private Box2DDebugRenderer b2dr;

    /**
     * Para el body del personaje con sus animaciones
     */
    Personaje personaje;

    /**
     * Variables que indican la posición inicial y las dimensiones del personaje
     */
    float posXPersonaje, posYPersonaje, anchoEstandar, altoEstandar;

    /**
     * Fuerzas de movimiento del personaje
     */
    float fuerzaHorizontal, fuerzaVertical;

    /**
     * Controla que el usuario no pueda atacar más si el personaje aún está haciendo su animación
     */
    boolean yaAtaco;

    // Duraciones de animaciones :
    /**
     * Duración de las animaciones que tienen loop
     */
    float tiempoAnimacionConLoop;

    /**
     * Indican si se está realizando la acción de ataque o de recoger
     */
    boolean atacando, recogiendo;

    /**
     * Duración de animaciones que no tienen loop
     */
    private float tiempoAnimacionSinLoop;

    // Escenario
    /**
     * Pantalla con el escenario Scene2D para los botones
     */
    PantallasPartida pantallasPartida;

    /**
     * Controlan el tiempo que debe pasar desde que todos los enemigos estén muertos hasta que se muestre la pantalla de nivel completado
     */
    float tiempoAcabar, tiempoTranscurridoAcabar;

    /**
     *  Creo un SpriteBatch para la pantalla
     */
    SpriteBatch batch;


    // Para el TiledMap
    /**
     * Renderizador del TiledMap
     */
    private OrthogonalTiledMapRenderer tmr;

    /**
     * Mapa sacado de TiledMap
     */
    TiledMap map;


    // Para ContactListener :
    /**
     * Para controlar si hay contacto entre el personaje y los enemigos
     */
    boolean contacto;

    /**
     * Efecto de sonido que sonará cuando el teléfono se mueva demasiado, para avisar de ello
     */
    Sound error;

    /**
     * Inicializa todas las variables de esta clase, que se mantendrán con estos valores por lo que son comunes a todos los niveles
     *
     * @param juego Clase principal referente al juego
     * @param posXPersonaje Indica la posición inicial del personaje en el eje X
     * @param posYPersonaje Indica la posición inicial del personaje en el eje Y
     * @param map Indica el mapa de TiledMap que debe cargar el nivel
     */
    public MapasNiveles(DungeonsWithoutDragons juego, float posXPersonaje, float posYPersonaje, TiledMap map){
        this.juego = juego; // Indico la clase principal

        // Indico la posición inicial del personaje
        this.posXPersonaje = posXPersonaje;
        this.posYPersonaje = posYPersonaje;

        this.map = map; // Indico el mapa del nivel correspondiente

        camara = new OrthographicCamera(); // Defino la cámara
        camara.setToOrtho(false, juego.anchoJuego / SCALE, juego.altoJuego / SCALE); // Tamaño de la cámara

        world = new World(new Vector2(0,0f), false); // No pongo gravedad y no quiero que el mundo haga sleep
        b2dr = new Box2DDebugRenderer(); // Defino el renderizador del mundo Box2D
        b2dr.setDrawBodies(false); // Elijo si dibujar las líneas de los Body


        // Defino las dimensiones estandar para el personaje y los enemigos
        anchoEstandar = juego.anchoJuego / 50;
        altoEstandar = juego.altoJuego / 32;


        // Defino el valor inical de los tiempos de las animaciones del personaje
        tiempoAnimacionConLoop = 0.01f; // Establezco el valor inicial de la duración de las animaciones con loop
        tiempoAnimacionSinLoop = 0.01f; // Establezco el valor inicial de la duración de las animaciones sin loop

        // Defino el valor del efecto de sonido de error
        error = Gdx.audio.newSound(Gdx.files.internal("sonidos/error.wav"));

        // Defino el personaje
        personaje = new Personaje(juego.imagenesPersonajes[juego.contadorImagenesPersonaje], world, this.posXPersonaje, this.posYPersonaje, anchoEstandar, altoEstandar, juego.esHombre);


        batch = new SpriteBatch(); // Defino el batch que dibujará todos los elementos de la pantalla
        batch.setProjectionMatrix(camara.combined); // Ajusto el SpriteBatch a la cámara

        // Para renderizar el TiledMap
        tmr = new OrthogonalTiledMapRenderer(map);

        Utils.parseTiledObjectLayer(world, map.getLayers().get("colisiones").getObjects()); // Parseo la capa del objetos colisiones llamando a la función de la clase TiledObjectUtil

        pantallasPartida = new PantallasPartida(juego); // Defino el escenario con el constructor de su clase

        // Inicializo las booleanas referentes a las animaciones
        atacando = false;
        recogiendo = false;

        contacto = false; // Al principio no hay contacto entre ningún Body

        tiempoAcabar = 3; // Establezco el valor necesario de tiempo para cambiar de pantalla una vez se haya acabado el nivel
        tiempoTranscurridoAcabar = 0; // Establezco el valor inicial del tiempo transcurrido desde que se mueren todos los enemigos hasta que se debe cambiar de pantalla
    }

    /**
     * Defino y establezco los listeners de los componentes de la pantalla Scene2D que he implementado, llamada pantallasPartida
     */
    @Override
    public void show() {
        pantallasPartida.show(); // Llamo a la función show() del escenario, para que sus componentes se puedan usar

        // Listener del botón atacar :
        pantallasPartida.btnAtacar.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!atacando){ // Si no está atacando... Para que no ataque más veces mientras está terminando una animación
                    // Si no está recogiendo con su animación, dejo que pueda atacar tranquilo
                    if (!recogiendo){
                        // Reproduzco el efecto de sonido del ataque correspondiente al jugador
                        if (juego.sonidos){
                            pantallasPartida.atacar.play(0.8f);
                        }
                        atacando = true;
                    }
                }

                return true; // Devuelvo true conforme esta función está gestionada
            }
        });


        // Listener del botón recoger :
        pantallasPartida.btnRecoger.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!recogiendo){ // Si no está recogiendo... Para que no recoja más veces mientras está terminando una animación
                    // Si no está atacando con su animación, dejo que pueda recoger tranquilo
                    if (!atacando){
                        recogiendo = true;
                    }
                }

                return true; // Devuelvo true conforme esta función está gestionada
            }
        });
    }


    /**
     * Aquí renderizo y muestro en pantalla todos los componentes referentes a la partida
     * @param delta Tiempo en el que se tarda en renderizar el frame actual que se está mostrando
     */
    @Override
    public void render(float delta) {
        tiempoAnimacionConLoop += delta; // Cada vez que se cargue el nuevo fotograma, se actualiza la variable duracionAnimacion para saber cuánto tiempo lleva mostrándose la animación

        if (atacando || recogiendo){ // Si una de las dos está activa...
            tiempoAnimacionSinLoop += delta; // Aumento el tiempo de la animación sin loop
        }


        update(); // Actualizo la lógica del juego

        Gdx.gl.glClearColor(0,0,0,1); // Pongo un color de fondo : Negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpio la pantalla

        // Muestro el fondo
        batch.begin();
            batch.draw(pantallasPartida.fondo, 0, 0, juego.anchoJuego / SCALE, juego.altoJuego / SCALE);
        batch.end();

        // Renderizo el TiledMap
        tmr.render();


        // Inicio el SpriteBatch y muestro la animación del personaje
        batch.begin();
            if (atacando || recogiendo){ // Si está atacando o recogiendo...
                // Muestro la animación sin loop correspondiente con su tiempo
                batch.draw((TextureRegion) personaje.animacion.getKeyFrame(tiempoAnimacionSinLoop, false), personaje.body.getPosition().x * PPM - (personaje.anchoPersonaje / 2),  personaje.body.getPosition().y * PPM - (personaje.altoPersonaje / 2), personaje.anchoPersonaje, personaje.altoPersonaje); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                // Muestro la animación con loop correspondiente con su tiempo
                batch.draw((TextureRegion) personaje.animacion.getKeyFrame(tiempoAnimacionConLoop, true), personaje.body.getPosition().x * PPM - (personaje.anchoPersonaje / 2),  personaje.body.getPosition().y * PPM - (personaje.altoPersonaje / 2), personaje.anchoPersonaje, personaje.altoPersonaje); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
        batch.end();


        if (atacando && personaje.animacion.isAnimationFinished(tiempoAnimacionSinLoop)){ // Si la animación de atacar ya acabó...
            // Reinicio todas las variables
            atacando = false;

            tiempoAnimacionSinLoop = 0.01f;
        }
        else if (recogiendo && personaje.animacion.isAnimationFinished(tiempoAnimacionSinLoop)){ // Si la animación de recoger ya acabó...
            // Reinicio todas las variables
            recogiendo = false;

            tiempoAnimacionSinLoop = 0.01f;
        }

        b2dr.render(world, camara.combined.cpy().scl(PPM)); // Renderizo el mundo y escalo la cámara por PPM
        pantallasPartida.draw(); // Muestro el escenario
    }

    /**
     * Sirve para reescalar la cámara cuando se necesite
     * @param width Ancho de la cámara
     * @param height Alto de la cámara
     */
    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false, width / SCALE, height / SCALE);
    }


    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }


    @Override
    public void hide() {

    }

    /**
     * Aquí limpio en memoria todos los componentes necesarios
     */
    @Override
    public void dispose() {
        world.dispose(); // Limpio el mundo Box2D
        b2dr.dispose(); // Limpio el renderer del mundo Box2D
        tmr.dispose(); // Limpio el renderer del TiledMap
        map.dispose(); // Limpio el TiledMap
        batch.dispose(); // Limpio el SpriteBatch
        pantallasPartida.dispose(); // Limpio la Scene2D presente en la partida
    }


    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------


    /**
     * Para actualizar la lógica del juego, donde actualizo el mundo Box2D, el renderer del TiledMap, el SpriteBatch y la info de entrada de datos como gestión del movimiento y aniamciones del personaje
     */
    private void update(){
        world.step(1 / 60f, 6, 2); // Actualizo el mundo, determina la velocidad a la que se actualiza y estas medidas son idóneas para que no se sature la app

//        cameraUpdate(delta); No necesito cameraUpdate pero por si acaso lo dejo comentado

        inputUpdate();

        tmr.setView(camara);

//        batch.setProjectionMatrix(camara.combined); // Ajusto el SpriteBatch a la cámara
    }


    /**
     * Actualiza la lógica del movimiento y animaciones del personaje dependiendo de los botones o teclas que se pulsen
     *
     */
    private void inputUpdate(){
        // Fuerzas de movimiento
        fuerzaHorizontal = 0;
        fuerzaVertical = 0;

        teclasTeclado(); // Para gestión del movimiento en las teclas del teclado
        botonesMovimiento(); // Para gestión del movimiento en los botones de movimiento de Scene2D

        // Establezco la animación del personaje según la acción que esté realizando
        if (atacando){
            personaje.animacion = personaje.atacar;
        }
        else if(recogiendo){
            personaje.animacion = personaje.gesto;
        }
        else{
            // Compruebo que no se esté pulsando ninguna tecla de movimiento
            if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !pantallasPartida.btnArriba.isPressed() && !pantallasPartida.btnAbajo.isPressed() && !pantallasPartida.btnIzquierda.isPressed() && !pantallasPartida.btnDerecha.isPressed()){
                personaje.animacion = personaje.idle; // Establezco la animación idle porque no se está pulsando nada
            }
        }

        personaje.body.setLinearVelocity(fuerzaHorizontal * 5, fuerzaVertical * 5); // Aplico la fuerza de movimiento correspondiente al cuerpo del personaje
    }


    /**
     * Gestiona el movimiento del personaje por el mapa con las teclas del teclado del ordenador
     */
    private void teclasTeclado(){
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){ // Si se pulsa la flecha hacia arriba
            fuerzaVertical += 0.5f;

            personaje.animacion = personaje.andar;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){ // Si se pulsa la flecha hacia abajo
            fuerzaVertical -= 0.5f;

            personaje.animacion = personaje.andar;
        }


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){ // Si se pulsa la flecha hacia la izquierda
            fuerzaHorizontal -= 0.5f;

            personaje.animacion = personaje.andarAtras;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){ // Si se pulsa la flecha hacia la derecha
            fuerzaHorizontal += 0.5f;

            personaje.animacion = personaje.andar;
        }
    }


    /**
     * Gestiona el movimiento del personaje por el mapa con los botones de movimiento de la interfaz Scene2D, y gestiona el acelerómetro del teléfono para avisar de que se mueve mucho el terminal
     */
    private void botonesMovimiento(){
        if (pantallasPartida.btnArriba.isPressed()){ // Si se pulsa el botón de movimiento hacia arriba
            fuerzaVertical += 0.5f;

            personaje.animacion = personaje.andar;
        }
        else if (pantallasPartida.btnAbajo.isPressed()){ // Si se pulsa el botón de movimiento hacia abajo
            fuerzaVertical -= 0.5f;

            personaje.animacion = personaje.andar;
        }

        if(pantallasPartida.btnIzquierda.isPressed()){ // Si se pulsa el botón de movimiento hacia la izquierda
            fuerzaHorizontal -= 0.5f;

            personaje.animacion = personaje.andarAtras;
        }
        else if (pantallasPartida.btnDerecha.isPressed()){ // Si se pulsa el botón de movimiento hacia la derecha
            fuerzaHorizontal += 0.5f;

            personaje.animacion = personaje.andar;
        }
        else if (Gdx.input.getAccelerometerX() > 20 || Gdx.input.getAccelerometerY() > 20){ // Si se pulsa el botón de movimiento hacia arriba
            if (juego.sonidos){
                error.play();
            }
        }
    }




    // No necesito cameraUpdate pero por si acaso lo dejo comentado
    /**
     * Actualizo la cámara, mostrando el body en el centro de la pantalla
     *
     * @param delta DeltaTime
     */
    private void cameraUpdate(float delta){
//        Vector3 position = camara.position; // Creo un vector para definirlo con la posición de la cámara
//        // Defino el vector con la posición del body del jugador
//        position.x = personaje.body.getPosition().x * PPM; // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
//        position.y = personaje.body.getPosition().y * PPM; // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM


//        camara.position.set(position); // Asigno la nueva posición a la cámara

//        camara.update(); // Actualizo la cámara después de darle una posición
    }
}
