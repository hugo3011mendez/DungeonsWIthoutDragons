package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
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
 * Clase que hereda de MapasNiveles y gestiona las propiedades, variables y funciones particulares del Nivel 1 de la partida
 */
public class Nivel1 extends MapasNiveles {
    /**
     * Los dos goblins que aparecerán en este nivel como enemigos
     */
    Goblin goblin1, goblin2;

    /**
     * Los dos orcos que aparecerán en este nivel como enemigos
     */
    Orco orco1, orco2;

    /**
     * Tiempos de animación para la animación de muerte de cada enemigo, que no hace loop
     */
    float tiempoAnimacionMuerteGoblin1, tiempoAnimacionMuerteGoblin2, tiempoAnimacionMuerteOrco1, tiempoAnimacionMuerteOrco2;


    /**
     * Inicializo las variables de la pantalla referente al nivel 1, llamando primero al constructor de su clase padre
     *
     * @param juego Clase principal, referente a la App
     * @param posXPersonaje Indica la posición inicial del personaje en el eje X
     * @param posYPersonaje Indica la posición inicial del personaje en el eje Y
     * @param map Indica el mapa de TiledMap que debe cargar el nivel
     */
    public Nivel1(DungeonsWithoutDragons juego, float posXPersonaje, float posYPersonaje, TiledMap map) {
        super(juego, posXPersonaje, posYPersonaje, map); // Llamo al constructor de la clase MapasNiveles para inicializar las variables generales y comunes a todos los niveles

        // Parece que se sitúan más hacia la derecha en el móvil que aquí
        // Inicializo y defino el goblin1 y su animación actual
        goblin1 = new Goblin(world, juego.anchoJuego / 6, juego.altoJuego / 6.5f, anchoEstandar, altoEstandar, true);
        goblin1.animacion = goblin1.idle;

        // Inicializo y defino el goblin2 y su animación actual
        goblin2 = new Goblin(world, juego.anchoJuego / 8, juego.altoJuego / 5.5f, anchoEstandar, altoEstandar, false);
        goblin2.animacion = goblin2.idle;

        // Inicializo y defino el orco1 y su animación actual
        orco1 = new Orco(world, juego.anchoJuego / 4.75f, juego.altoJuego / 4f, anchoEstandar, altoEstandar, true);
        orco1.animacion = orco1.idle;

        // Inicializo y defino el orco2 y su animación actual
        orco2 = new Orco(world, juego.anchoJuego / 5, juego.altoJuego / 6.75f, anchoEstandar, altoEstandar, false);
        orco2.animacion = orco2.idle;

        // Defino los tiempos de animación iniciales de muerte de cada enemigo
        tiempoAnimacionMuerteGoblin1 = 0.01f;
        tiempoAnimacionMuerteGoblin2 = 0.01f;
        tiempoAnimacionMuerteOrco1 = 0.01f;
        tiempoAnimacionMuerteOrco2 = 0.01f;

        yaAtaco = false; // Establezco el valor inicial a falso porque aún no ha atacado
    }


    /**
     * Establezco los listeners del botón Back y el mundo Box2D
     */
    @Override
    public void show() {
        super.show(); // Llamo a la función show de su clase padre, MapasNiveles

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
                juego.nivel1.dispose();
            }
        });


        // Defino el ContactListener del mundo de Box2D :
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) { // Función que se ejecuta cuando empieza el contacto entre dos Body
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                if (a == null || b == null) return; // Si la fixture de uno de los dos objetos que dejan de colisionar es null, no debería pasar nada
                if (a.getUserData() == null || b.getUserData() == null) return; // Si el UserData de uno de los dos objetos que dejan de colisionar es null, no debería pasar nada

                if (Utils.personajeEsA(a, b)){ // Si el personaje se trata de la fixture A...
                    contacto = true; // Establezco la booleana del contacto a verdadera

                    // Controlo si la otra fixture es un orco o un esqueleto, si adivino con qué enemigo está contactando el personaje
                    if (b.getUserData() instanceof Goblin){
                        if ((Goblin) b.getUserData() == goblin1){
                            goblin1.seleccionado = true;
                        }
                        else{
                            goblin2.seleccionado = true;
                        }
                    }
                    else if(b.getUserData() instanceof Orco){
                        if((Orco) b.getUserData() == orco1){
                            orco1.seleccionado = true;
                        }
                        else{
                            orco2.seleccionado = true;
                        }
                    }
                }
            }


            @Override
            public void endContact(Contact contact) { // Acciones a realizar cuando acaba el contacto entre dos Body
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
        super.render(delta); // Renderizo los componentes de su clase padre

        if (!contacto){ // Controlo que ningún enemigo esté seleccionado cuando hay contacto
            goblin1.seleccionado = false;
            goblin2.seleccionado = false;
            orco1.seleccionado = false;
            orco2.seleccionado = false;
        }

        atacando(); // Llamo a la función que gestiona el ataque del personaje a un enemigo
        muerenONo(); // Llamo a la función que gestiona si un enemigo muere

        empezarTiempoAnimacionMuerte(delta); // Controlo el tiempo para que cada enemigo haga su animación de muerte

        cambiarPantalla(delta); // Controlo el tiempo para cambiar a la pantalla de ganador, cuando se completa el nivel

        // Empiezo a dibujar los componentes necesarios :
        batch.begin();
            // Dibujo el goblin1
            if (goblin1.muerto){ // Si está muerto, dibujo su animación de muerte
                batch.draw((TextureRegion) goblin1.animacion.getKeyFrame(tiempoAnimacionMuerteGoblin1, false), goblin1.body.getPosition().x * PPM - (goblin1.anchoGoblin / 2),  goblin1.body.getPosition().y * PPM - (goblin1.altoGoblin / 2), goblin1.anchoGoblin, goblin1.altoGoblin); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                batch.draw((TextureRegion) goblin1.animacion.getKeyFrame(tiempoAnimacionConLoop, true), goblin1.body.getPosition().x * PPM - (goblin1.anchoGoblin / 2),  goblin1.body.getPosition().y * PPM - (goblin1.altoGoblin / 2), goblin1.anchoGoblin, goblin1.altoGoblin); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }


            // Dibujo el goblin2
            if (goblin2.muerto){ // Si está muerto, dibujo su animación de muerte
                batch.draw((TextureRegion) goblin2.animacion.getKeyFrame(tiempoAnimacionMuerteGoblin2, false), goblin2.body.getPosition().x * PPM - (goblin2.anchoGoblin / 2),  goblin2.body.getPosition().y * PPM - (goblin2.altoGoblin / 2), goblin2.anchoGoblin, goblin2.altoGoblin); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                batch.draw((TextureRegion) goblin2.animacion.getKeyFrame(tiempoAnimacionConLoop, true), goblin2.body.getPosition().x * PPM - (goblin2.anchoGoblin / 2),  goblin2.body.getPosition().y * PPM - (goblin2.altoGoblin / 2), goblin2.anchoGoblin, goblin2.altoGoblin); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }


            // Dibujo orco1
            if (orco1.muerto){ // Si está muerto, dibujo su animación de muerte
                batch.draw((TextureRegion) orco1.animacion.getKeyFrame(tiempoAnimacionMuerteOrco1, false), orco1.body.getPosition().x * PPM - (orco1.anchoOrco / 2),  orco1.body.getPosition().y * PPM - (orco1.altoOrco / 2), orco1.anchoOrco, orco1.altoOrco); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                batch.draw((TextureRegion) orco1.animacion.getKeyFrame(tiempoAnimacionConLoop, true), orco1.body.getPosition().x * PPM - (orco1.anchoOrco / 2),  orco1.body.getPosition().y * PPM - (orco1.altoOrco / 2), orco1.anchoOrco, orco1.altoOrco); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }


            // Dibujo orco2
            if (orco2.muerto){ // Si está muerto, dibujo su animación de muerte
                batch.draw((TextureRegion) orco2.animacion.getKeyFrame(tiempoAnimacionMuerteOrco2, false), orco2.body.getPosition().x * PPM - (orco2.anchoOrco / 2),  orco2.body.getPosition().y * PPM - (orco2.altoOrco / 2), orco2.anchoOrco, orco2.altoOrco); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
            else{
                batch.draw((TextureRegion) orco2.animacion.getKeyFrame(tiempoAnimacionConLoop, true), orco2.body.getPosition().x * PPM - (orco2.anchoOrco / 2),  orco2.body.getPosition().y * PPM - (orco2.altoOrco / 2), orco2.anchoOrco, orco2.altoOrco); // Si recoges un dato de un objeto Box2D, MULTIPLICA por PPM
            }
        batch.end();
    }




    //---------------------------------------------------------------------- FUNCIONES PROPIAS -------------------------------------------------------------------------------------------------------------------




    /**
     * Realiza las acciones necesarias cuando el personaje ataca a un enemigo presente en el nivel 1
     * Baja la vida correspondiente al enemigo en cuestión y controla si puede atacar más mientras está realizando su animación de atacar
     */
    private void atacando(){
        if (atacando){ // Controlo qué pasa cuando el jugador ataca a alguno de los enemigos
            if (goblin1.seleccionado){
                if (!yaAtaco){
                    goblin1.vida --;
                    yaAtaco = true;
                }
            }
            else if(goblin2.seleccionado){
                if (!yaAtaco){
                    goblin2.vida --;
                    yaAtaco = true;
                }
            }
            else if(orco1.seleccionado){
                if (!yaAtaco){
                    orco1.vida --;
                    yaAtaco = true;

                    if (juego.vibracion){ // En el caso de hacerles daño, el móvil vibra si la opción de vibración está activada
                        Gdx.input.vibrate(100);
                    }

                    if (juego.sonidos){ // En el caso de hacerles daño, se reproduce un efecto de sonido si la opción de efectos de sonido está activada
                        orco1.hit.play();
                    }
                }
            }
            else if(orco2.seleccionado){
                if (!yaAtaco){
                    orco2.vida --;
                    yaAtaco = true;

                    if (juego.vibracion){ // En el caso de hacerles daño, el móvil vibra si la opción de vibración está activada
                        Gdx.input.vibrate(100);
                    }

                    if (juego.sonidos){ // En el caso de hacerles daño, se reproduce un efecto de sonido si la opción de efectos de sonido está activada
                        orco2.hit.play();
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
        if (goblin1.vida <= 0){
            if (!goblin1.muerto){
                goblin1.muerto = true;
                goblin1.animacion = goblin1.morir;

                if (juego.sonidos){
                    goblin1.muerte.play();
                }

                if (juego.vibracion){
                    Gdx.input.vibrate(300);
                }
            }
        }


        if (goblin2.vida <= 0){
            if (!goblin2.muerto){
                goblin2.muerto = true;
                goblin2.animacion = goblin2.morir;

                if (juego.sonidos){
                    goblin2.muerte.play();
                }

                if (juego.vibracion){
                    Gdx.input.vibrate(300);
                }
            }
        }


        if (orco1.vida <= 0){
            if (!orco1.muerto){
                orco1.muerto = true;
                orco1.animacion = orco1.morir;

                if (juego.sonidos){
                    orco1.muerte.play();
                }

                if (juego.vibracion){
                    Gdx.input.vibrate(300);
                }
            }
        }


        if (orco2.vida <= 0){
            if (!orco2.muerto){
                orco2.muerto = true;
                orco2.animacion = orco2.morir;

                if (juego.sonidos){
                    orco2.muerte.play();
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
        if (goblin1.muerto){
            tiempoAnimacionMuerteGoblin1 += delta;
        }

        if (goblin2.muerto){
            tiempoAnimacionMuerteGoblin2 += delta;
        }

        if (orco1.muerto){
            tiempoAnimacionMuerteOrco1 += delta;
        }

        if(orco2.muerto){
            tiempoAnimacionMuerteOrco2 += delta;
        }
    }

    /**
     * Controlo si todos los enemigos han muerto para empezar el tiempo necesario para cambiar de pantalla
     * Y controlo si se ha alcanzado el tiempo necesario para cambiar de pantalla, y lo hago
     *
     * @param delta DeltaTime
     */
    private void cambiarPantalla(float delta){
        if (goblin1.muerto && goblin2.muerto && orco1.muerto && orco2.muerto){
            tiempoTranscurridoAcabar += delta;
        }


        if (tiempoTranscurridoAcabar >= tiempoAcabar){
            juego.pantallaGanador = new PantallaGanador(juego, "Has completado el nivel 1!");
            juego.setScreen(juego.pantallaGanador);

            // Actualizo la variable de bloqueo del siguiente nivel en la clase principal y en el archivo de configuración
            juego.bloqueoNivel2 = false;
            juego.configuracion.putBoolean("bloqueoNivel2", juego.bloqueoNivel2);
            juego.configuracion.flush();

            juego.nivel1.dispose();
        }
    }
}
