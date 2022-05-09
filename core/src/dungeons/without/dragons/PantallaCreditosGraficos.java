package dungeons.without.dragons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Clase que extiende de Pantallas, referente a la pantalla de créditos de los gráficos
 */
public class PantallaCreditosGraficos extends Pantallas {
    /**
     * Escenario a dibujar
     */
    private Stage escenario;

    /**
     * Botón que permite volver a la anterior pantalla
     */
    private ImageButton btnBack;

    /**
     * Botón que nos llevará hasta la pantalla del menú de opciones
     */
    private ImageButton btnOpciones;

    /**
     * Etiqueta del título de la pantalla
     */
    private Label lblTitulo;

    /**
     * Etiqueta con todos los créditos de los gráficos
     */
    private Label lblCreditosGraficos;


    /**
     * Constructor de la pantalla referente a los créditos de los gráficos, donde se definen todos sus componentes
     *
     * @param juego Aplicación principal
     */
    public PantallaCreditosGraficos(DungeonsWithoutDragons juego) {
        super(juego);

        escenario = new Stage();

        // Defino el botón Back con sus propiedades :
        btnBack = new ImageButton(imgBtnBack);
        btnBack.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnBack.setPosition(posXBack, posYBack);
        escenario.addActor(btnBack);

        // Defino la etiqueta del título de la pantalla y sus propiedades
        lblTitulo = new Label("Creditos de Graficos", juego.skin2);
        lblTitulo.setPosition(juego.anchoJuego/ 4f, juego.altoJuego - lblTitulo.getHeight() * 1.5f);
        lblTitulo.setSize(juego.anchoJuego / 3.5f,juego.altoJuego / 6.5f);
        lblTitulo.setFontScale(1f,1.3f);
        lblTitulo.setColor(Color.YELLOW);
        escenario.addActor(lblTitulo);

        // Defino la etiqueta de los créditos y sus propiedades
        lblCreditosGraficos = new Label("\"Dungeon Tileset\" por Calciumtrice, licencia Creative Commons Attribution 3.0\n" +
                "\"Animated Warrior\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Ranger\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Rogue\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Cleric\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Wizard\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Goblins\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Skeleton\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Orcs\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"Animated Minotaur\" por Calciumtrice, Creative Commons Attribution 3.0\n" +
                "\"700+ RPG Icons\" por Lorc, Creative Commons Attribution 3.0 Unported (CC BY 3.0)\n" +
                "\"Superpowers assets background elements\" por MedicineStorm, Creative Commons Attribution Zero",
                juego.skin
        );
        lblCreditosGraficos.setPosition(juego.anchoJuego/ 25, juego.altoJuego / 6.5f);
        lblCreditosGraficos.setSize(juego.anchoJuego/ 3f, juego.altoJuego / 1.5f);
        lblCreditosGraficos.setFontScale(2.35f, 2.5f);
        escenario.addActor(lblCreditosGraficos);


        // Defino el botón de opciones y sus propiedades :
        btnOpciones = new ImageButton(imgBtnOpciones);
        btnOpciones.setSize(anchoOpcionesYBack, altoOpcionesYBack);
        btnOpciones.setPosition(posXOpciones, posYOpciones);
        escenario.addActor(btnOpciones);

        fondo = new Texture("fondo/FondoCreditosGraficos.png"); // Pongo otra imagen de fondo
    }


    /**
     * Establezco los listeners de los botones back y de opciones
     */
    @Override
    public void show() {
        // Añado el escenario como un InputProcessor para poder capturar eventos si se requiere
        Gdx.input.setInputProcessor(escenario); // Hago esto dentro de show() porque puede dar problemas al cambiar de pantalla

        // Listener del btnBack :
        btnBack.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.pantallaCreditos = new PantallaCreditos(juego);
                juego.setScreen(juego.pantallaCreditos);

                juego.pantallaCreditosGraficos.dispose();
            }
        });


        // Listener de btnOpciones :
        btnOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.backOpciones = 3;

                juego.pantallaMenuOpciones = new PantallaMenuOpciones(juego);
                juego.setScreen(juego.pantallaMenuOpciones);
                juego.pantallaCreditosGraficos.dispose();
            }
        });
    }


    /**
     * Muestro en pantalla los componentes necesarios
     * @param delta DeltaTime
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); // Pongo un color de fondo : Blanco
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpio la pantalla

        // Comienzo el batch para empezar a dibujar el fondo
        juego.batch.begin();
            juego.batch.draw(fondo, 0, 0, juego.anchoJuego, juego.altoJuego);
        juego.batch.end();

        // Dibujo el escenario y lo actualizo
        escenario.draw();
        escenario.act(delta);
    }


    /**
     * Limpio los componentes necesarios
     */
    @Override
    public void dispose() {
        escenario.dispose();
    }
}
