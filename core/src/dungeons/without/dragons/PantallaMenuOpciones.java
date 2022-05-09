package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Clase que extiende de Pantallas, referente a la pantalla de opciones del juego
 */
public class PantallaMenuOpciones extends Pantallas {
    /**
     * Escenario de la pantalla
     */
    private Stage escenario;

    /**
     * Etiqueta del título de la pantalla
     */
    private Label lblTitulo;

    /**
     * Botón para activar/desactivar la música en el juego
     */
    private TextButton btnMusica;

    /**
     * Botón para activar/desactivar los efectos de sonido en el juego
     */
    private TextButton btnSonidos;

    /**
     * Botón para activar/desactivar los efectos de vibración en el juego
     */
    private TextButton btnVibracion;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    private ImageButton btnBack;

    /**
     * Imagen que indica si hay música o no
     */
    private Texture imgMusica;

    /**
     * Imagen que indica si hay efectos de sonido o no
     */
    private Texture imgSonidos;

    /**
     * Imagen que indica si hay vibración o no
     */
    private Texture imgVibracion;


    /**
     * Cadena para controlar el valor inicial de la musica
     */
    private String cadMusica;

    /**
     * Cadena para controlar el valor inicial de los sonidos
     */
    private String cadSonidos;

    /**
     * Cadena para controlar el valor inicial de la vibración
     */
    private String cadVibracion;


    /**
     * Constructor de la pantalla referente al menú de opciones, donde se definen todos sus componentes
     *
     * @param juego Aplicación principal
     */
    public PantallaMenuOpciones(DungeonsWithoutDragons juego) {
        super(juego);

        escenario = new Stage();

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);


        // Defino la etiqueta del título de la pantalla con sus propiedades :
        lblTitulo = new Label("Opciones", juego.skin2);
        lblTitulo.setPosition(juego.anchoJuego/ 3f, juego.altoJuego - lblTitulo.getHeight() * 1.5f);
        lblTitulo.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblTitulo.setFontScale(1f,1.3f);
        lblTitulo.setColor(Color.YELLOW);
        escenario.addActor(lblTitulo);


        // Establezco la cadena referente a música según corresponda
        if (juego.musica){
            cadMusica = "Desactivar musica";
        }
        else{
            cadMusica = "Activar musica";
        }

        // Defino el botón que activa o desactiva la música con sus propiedades :
        btnMusica = new TextButton(cadMusica, juego.skin);
        btnMusica.setSize(anchoBotones * 1.75f, altoBotones);
        btnMusica.setPosition(juego.anchoJuego / 4, juego.altoJuego / 1.75f);
        btnMusica.getLabel().setFontScale(5, 5);
        escenario.addActor(btnMusica);


        // Establezco la cadena referente a efectos de sonido según corresponda
        if (juego.sonidos){
            cadSonidos = "Desactivar efectos de sonido";
        }
        else{
            cadSonidos = "Activar efectos de sonido";
        }

        // Defino el botón que activa o desactiva los efectos de sonido con sus propiedades :
        btnSonidos = new TextButton(cadSonidos, juego.skin);
        btnSonidos.setSize(anchoBotones * 2.5f, altoBotones);
        btnSonidos.setPosition(juego.anchoJuego / 14, juego.altoJuego / 2.75f);
        btnSonidos.getLabel().setFontScale(5, 5);
        escenario.addActor(btnSonidos);


        // Establezco la cadena referente a vibración según corresponda
        if (juego.vibracion){
            cadVibracion = "Desactivar vibracion";
        }
        else{
            cadVibracion = "Activar vibracion";
        }

        // Defino el botón de vibración y sus propiedades
        btnVibracion = new TextButton(cadVibracion, juego.skin);
        btnVibracion.setSize(anchoBotones * 2f, altoBotones);
        btnVibracion.setPosition(juego.anchoJuego / 5, juego.altoJuego / 5.85f);
        btnVibracion.getLabel().setFontScale(5, 5);
        escenario.addActor(btnVibracion);

        fondo = new Texture("fondo/FondoOpciones.png"); // Pongo la nueva imagen de fondo

        // Establezco las imágenes de representación de la música, los efectos de sonido y la vibración
        if (juego.musica){
            imgMusica = new Texture("botones/musica.png");
        }
        else{
            imgMusica = new Texture("botones/sinMusica.png");
        }

        if (juego.sonidos){
            imgSonidos = new Texture("botones/musica.png");
        }
        else{
            imgSonidos = new Texture("botones/sinMusica.png");
        }

        if (juego.vibracion){
            imgVibracion = new Texture("botones/vibracion.png");
        }
        else{
            imgVibracion = new Texture("botones/sinVibracion.png");
        }
    }


    /**
     * Establezco los listeners de los componentes necesarios
     */
    @Override
    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla

        // Listener del btnBack :
        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch(juego.backOpciones){
                    case 0:
                        juego.pantallaMenu = new PantallaMenu(juego);
                        juego.setScreen(juego.pantallaMenu);
                        break;

                    case 1:
                        juego.pantallaPersonaje = new PantallaPersonaje(juego);
                        juego.setScreen(juego.pantallaPersonaje);
                        break;

                    case 2:
                        juego.pantallaCreditos = new PantallaCreditos(juego);
                        juego.setScreen(juego.pantallaCreditos);
                        break;

                    case 3:
                        juego.pantallaCreditosGraficos = new PantallaCreditosGraficos(juego);
                        juego.setScreen(juego.pantallaCreditosGraficos);
                        break;

                    case 4:
                        juego.pantallaCreditosMusica = new PantallaCreditosMusica(juego);
                        juego.setScreen(juego.pantallaCreditosMusica);
                        break;

                    case 5:
                        juego.pantallaNiveles = new PantallaNiveles(juego);
                        juego.setScreen(juego.pantallaNiveles);
                        break;
                }

                juego.pantallaMenuOpciones.dispose();
            }
        });


        // Listener del botón de la música :
        btnMusica.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.musica = !juego.musica;

                if (juego.musica){
                    imgMusica = new Texture("botones/musica.png");

                    juego.musicaMenu.setPosition(0);
                    juego.musicaMenu.play();

                    btnMusica.setText("Desactivar musica");
                }
                else{
                    imgMusica = new Texture("botones/sinMusica.png");

                    juego.musicaMenu.stop();

                    btnMusica.setText("Activar musica");
                }

                juego.configuracion.putBoolean("musica", juego.musica); // Actualizo la variable en el archivo de configuración
                juego.configuracion.flush();
            }
        });


        // Listener del botón de los efectos de sonido :
        btnSonidos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.sonidos = !juego.sonidos;

                if (juego.sonidos){
                    imgSonidos = new Texture("botones/musica.png");

                    btnSonidos.setText("Desactivar efectos de sonido");
                }
                else{
                    imgSonidos = new Texture("botones/sinMusica.png");

                    btnSonidos.setText("Activar efectos de sonido");
                }

                juego.configuracion.putBoolean("sonidos", juego.sonidos); // Actualizo la variable en el archivo de configuración
                juego.configuracion.flush();
            }
        });


        // Listener del botón de vibración :
        btnVibracion.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.vibracion = !juego.vibracion;

                if (juego.vibracion){
                    imgVibracion = new Texture("botones/vibracion.png");

                    btnVibracion.setText("Desactivar vibracion");
                }
                else{
                    imgVibracion = new Texture("botones/sinVibracion.png");

                    btnVibracion.setText("Activar vibracion");
                }

                juego.configuracion.putBoolean("vibracion", juego.vibracion); // Actualizo la variable en el archivo de configuración
                juego.configuracion.flush();
            }
        });
    }


    /**
     * Muestro los componentes necesarios de esta pantalla
     * @param delta DeltaTime
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); // Pongo un color de fondo : Blanco
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpio la pantalla

        // Comienzo el batch para empezar a dibujar el fondo
        juego.batch.begin();
            juego.batch.draw(fondo, 0, 0, juego.anchoJuego, juego.altoJuego);

            // Dibujo las imágenes referentes a la música, los sonidos y la vibración
            juego.batch.draw(imgMusica, juego.anchoJuego / 1.35f, juego.altoJuego / 1.75f, juego.anchoJuego / 10f, juego.anchoJuego / 10f);
            juego.batch.draw(imgSonidos, juego.anchoJuego / 1.35f, juego.altoJuego / 2.75f, juego.anchoJuego / 10f, juego.anchoJuego / 10f);
            juego.batch.draw(imgVibracion, juego.anchoJuego / 1.35f, juego.altoJuego / 5.85f, juego.anchoJuego / 10f, juego.anchoJuego / 10f);
        juego.batch.end();

        // Dibujo el escenario y lo actualizo
        escenario.draw();
        escenario.act(delta);
    }


    /**
     * Limpio en memoria los componentes necesarios
     */
    @Override
    public void dispose() {
        super.dispose();

        fondo.dispose();
        imgMusica.dispose();
        imgSonidos.dispose();
        imgVibracion.dispose();
        escenario.dispose();
    }
}
