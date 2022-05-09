package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static dungeons.without.dragons.Constantes.PPM;

/**
 * Clase que hereda de MapasNiveles y gestiona las propiedades, variables y funciones particulares del Nivel 3 de la partida
 */
public class Nivel3 extends MapasNiveles {
    /**
     * Los dos esqueletos que habrá en este nivel
     */
    Esqueleto esqueleto1, esqueleto2;

    /**
     * Tiempos de animaciones sin loop de los enemigos de este nivel
     */
    float tiempoAnimacionMuerteEsqueleto1, tiempoAnimacionMuerteEsqueleto2;

    /**
     * Inicializo las variables de la pantalla referente al nivel 3, llamando primero al constructor de su clase padre
     *
     * @param juego Clase principal, referente a la App
     * @param posXPersonaje Indica la posición inicial del personaje en el eje X
     * @param posYPersonaje Indica la posición inicial del personaje en el eje Y
     * @param map Indica el mapa de TiledMap que debe cargar el nivel
     */
    public Nivel3(DungeonsWithoutDragons juego, float posXPersonaje, float posYPersonaje, TiledMap map) {
        super(juego, posXPersonaje, posYPersonaje, map);

        esqueleto1 = new Esqueleto(world, juego.anchoJuego / 5f, juego.altoJuego / 7f, anchoEstandar, altoEstandar);
        esqueleto1.animacion = esqueleto1.idle;

        esqueleto2 = new Esqueleto(world, juego.anchoJuego / 6f, juego.altoJuego / 6f, anchoEstandar, altoEstandar);
        esqueleto2.animacion = esqueleto2.idle;

        tiempoAnimacionMuerteEsqueleto1 = 0.01f;
        tiempoAnimacionMuerteEsqueleto2 = 0.01f;

        yaAtaco = false; // Establezco el valor inicial a falso porque aún no ha atacado
    }


    /**
     * Establezco los listeners del botón back y el mundo de Box2D
     */
    @Override
    public void show() {
        super.show();


        // Listener de btnBack :
        pantallasPartida.btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // Vuelvo al menú principal si le doy al botón back
                juego.pantallaMenu = new PantallaMenu(juego);
                juego.setScreen(juego.pantallaMenu);

                // Si está activada la música, se cambia a la música de menú
                if (juego.musica){
                    juego.musicaPartida.stop();
                    juego.musicaPartida.setPosition(0);

                    juego.musicaMenu.play();
                }

                // Limpio los recursos de esta pantalla
                juego.nivel3.dispose();
            }
        });


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                if (a == null || b == null) return; // Si la fixture de uno de los dos objetos que dejan de colisionar es null, no debería pasar nada
                if (a.getUserData() == null || b.getUserData() == null) return; // Si el UserData de uno de los dos objetos que dejan de colisionar es null, no debería pasar nada

                if (Utils.personajeEsA(a, b)){ // Si el personaje se trata de la fixture A...
                    contacto = true; // Establezco la booleana del contacto a verdadera

                    // Controlo si la otra fixture es un goblin o un orco, si adivino con qué enemigo está contactando el personaje
                    if(b.getUserData() instanceof Esqueleto){
                        if((Esqueleto) b.getUserData() == esqueleto1){
                            esqueleto1.seleccionado = true;
                        }
                        else{
                            esqueleto2.seleccionado = true;
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                if (a == null || b == null) return; // Si la fixture de uno de los dos objetos que dejan de colisionar es null, no debería pasar nada
                if (a.getUserData() == null || b.getUserData() == null) return; // Si el UserData de uno de los dos objetos que dejan de colisionar es null, no debería pasar nada

                contacto = false; // Establezco la variable a falsa porque ya ha acabado el contacto
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    /**
     * Muestro todos los componentes necesarios en la pantalla con su lógica correspondiente
     * @param delta Tiempo en el que se tarda en renderizar el frame actual que se está mostrando
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (!contacto){
            esqueleto1.seleccionado = false;
            esqueleto2.seleccionado = false;
        }

        atacando();
        muerenONo();

        empezarTiempoAnimacionMuerte(delta);

        cambiarPantalla(delta);


        batch.begin();
            if(esqueleto1.muerto){
                batch.draw((TextureRegion) esqueleto1.animacion.getKeyFrame(tiempoAnimacionMuerteEsqueleto1, false), esqueleto1.body.getPosition().x * PPM - (esqueleto1.anchoEsqueleto / 2),  esqueleto1.body.getPosition().y * PPM - (esqueleto1.altoEsqueleto / 2), esqueleto1.anchoEsqueleto, esqueleto1.altoEsqueleto); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                batch.draw((TextureRegion) esqueleto1.animacion.getKeyFrame(tiempoAnimacionConLoop, true), esqueleto1.body.getPosition().x * PPM - (esqueleto1.anchoEsqueleto / 2),  esqueleto1.body.getPosition().y * PPM - (esqueleto1.altoEsqueleto / 2), esqueleto1.anchoEsqueleto, esqueleto1.altoEsqueleto); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }


            if(esqueleto2.muerto){
                batch.draw((TextureRegion) esqueleto2.animacion.getKeyFrame(tiempoAnimacionMuerteEsqueleto2, false), esqueleto2.body.getPosition().x * PPM - (esqueleto2.anchoEsqueleto / 2),  esqueleto2.body.getPosition().y * PPM - (esqueleto2.altoEsqueleto / 2), esqueleto2.anchoEsqueleto, esqueleto2.altoEsqueleto); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                batch.draw((TextureRegion) esqueleto2.animacion.getKeyFrame(tiempoAnimacionConLoop, true), esqueleto2.body.getPosition().x * PPM - (esqueleto2.anchoEsqueleto / 2),  esqueleto2.body.getPosition().y * PPM - (esqueleto2.altoEsqueleto / 2), esqueleto2.anchoEsqueleto, esqueleto2.altoEsqueleto); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
        batch.end();
    }


    /**
     * Realiza las acciones necesarias cuando el personaje ataca a un enemigo presente en el nivel 2
     * Baja la vida correspondiente al enemigo en cuestión y controla si puede atacar más mientras está realizando su animación de atacar
     */
    private void atacando(){
        if (atacando){ // Controlo qué pasa cuando el jugador ataca a alguno de los enemigos
            if(esqueleto1.seleccionado){
                if (!yaAtaco){
                    esqueleto1.vida --;
                    yaAtaco = true;

                    if (juego.vibracion){
                        Gdx.input.vibrate(100);
                    }

                    if (juego.sonidos){
                        esqueleto1.hit.play();
                    }
                }
            }
            else if(esqueleto2.seleccionado){
                if (!yaAtaco){
                    esqueleto2.vida --;
                    yaAtaco = true;

                    if (juego.vibracion){
                        Gdx.input.vibrate(100);
                    }

                    if (juego.sonidos){
                        esqueleto2.hit.play();
                    }
                }
            }
        }
        else{ // Si ya no está atacando, cambio la variable a falsa
            yaAtaco = false;
        }
    }


    /**
     * Controlo si los enemigos deberían estar muertos o no, y reproduzco sus sonidos de hit y muerte correspondientes
     */
    private void muerenONo(){
        if (esqueleto1.vida <= 0){
            if (!esqueleto1.muerto){
                esqueleto1.muerto = true;
                esqueleto1.animacion = esqueleto1.morir;

                if (juego.sonidos){
                    esqueleto1.muerte.play();
                }

                if (juego.vibracion){
                    Gdx.input.vibrate(300);
                }
            }
        }

        if (esqueleto2.vida <= 0){
            if (!esqueleto2.muerto){
                esqueleto2.muerto = true;
                esqueleto2.animacion = esqueleto2.morir;

                if (juego.sonidos){
                    esqueleto2.muerte.play();
                }

                if (juego.vibracion){
                    Gdx.input.vibrate(300);
                }
            }
        }
    }


    /**
     * Controlo si algún enemigo ha muerto y empiezo el tiempo de su animación de muerte
     *
     * @param delta DeltaTime
     */
    private void empezarTiempoAnimacionMuerte(float delta){
        if (esqueleto1.muerto){
            tiempoAnimacionMuerteEsqueleto1 += delta;
        }

        if (esqueleto2.muerto){
            tiempoAnimacionMuerteEsqueleto2 += delta;
        }
    }


    /**
     * Controlo si todos los enemigos han muerto para empezar el tiempo necesario para cambiar de pantalla
     * Y controlo si se ha alcanzado el tiempo necesario para cambiar de pantalla, y lo hago
     *
     * @param delta DeltaTime
     */
    private void cambiarPantalla(float delta){
        if (esqueleto1.muerto && esqueleto2.muerto){
            tiempoTranscurridoAcabar += delta;
        }


        if (tiempoTranscurridoAcabar >= tiempoAcabar){
            juego.pantallaGanador = new PantallaGanador(juego, "Has completado el nivel 3!\nHas completado el juego!");
            juego.setScreen(juego.pantallaGanador);

            juego.nivel3.dispose();
        }
    }
}
