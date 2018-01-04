package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Menu {
	int turno= 0;	
	Stage window = new Stage();
	StackPane stack = new StackPane();
	Scene scene = new Scene(stack);
	TextField valorEntradaDatos;
	TextField cantJugadores;
	String valorString;
	int valorInt;
	GridPane tableroFx = new GridPane();
	Stage tableroFxStage = new Stage();
	Scene tableroFxScene = new Scene(tableroFx,400,400);
	
	public void ventanaRecogidaDatos(String texto, boolean esString) {
		window.setX(50);
		window.setY(100);
		Label label = new Label(texto);
		valorEntradaDatos= new TextField();
		stack.setMargin(valorEntradaDatos, new Insets (80,10,10,10));

		stack.getChildren().addAll(label, valorEntradaDatos);
		valorEntradaDatos.setOnAction(new EventHandler<ActionEvent>() {		 
            @Override
            public void handle(ActionEvent event) {
            	if (esString) {
            		valorString = valorEntradaDatos.getText();
            	} else {
            		valorInt = Integer.parseInt(valorEntradaDatos.getText());
            	}
            	
            	stack.getChildren().clear();           
            	window.close();
            }
        });

		window.showAndWait();
	}

	
	public Jugador[] crearJugadores() {  //Cuando es contra la máquina elegimos hasta 4 jugadores
		
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		int opcion = 0;
		ventanaRecogidaDatos("¿Cuantos jugadores participarán? [1 a 4]: ",false);
		opcion = valorInt;
		while (opcion < 1 || opcion > 4) {
			ventanaRecogidaDatos("Por favor introduzca un número del 1 al 4: ",false);
			opcion = valorInt;
		}
		Jugador[] resultado = new Jugador[opcion];		
		for (int i = 0; i < resultado.length; i++) { //recorremos el array resultado para crear la cantidad de jugadores seleccionada
			ventanaRecogidaDatos("Nombre del jugador " + i + ":", true);
			resultado[i] = new Jugador(i,valorString); //creamos el jugador con su nombre y directamente lo metemos en el array resultado
		}
		return resultado;
	}
	
	public void ataquePorTurno(Jugador jugador) {	
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord X", false);
		int x = valorInt;
		ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord Y", false);
		int y = valorInt;
		jugador.atacar(x, y);
	}
	
	public void turnoJugadores(Jugador[] jugadores) {
			
		while (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) != true) { // Mientras la flota no esté hundida hacemos turnos de juego
			System.out.println("TURNO " + turno);
			for (int i = 0; i < jugadores.length; i++) { //vamos rotando a los jugadores para que participen
				System.out.println("----- Turno del jugador " + jugadores[i].getNombre() + " -----");
				do {
					verTableroFx();
					ataquePorTurno(jugadores[i]);
					System.out.println("Acierta?: " + jugadores[i].getAciertaUltimoAtaque());
					if (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) == true) {break;}
				} while (jugadores[i].getAciertaUltimoAtaque() == true); //si un jugador acierta vuelve a tirar en el mismo turno.
			}
			turno++;
			Tablero.verTablero();
			verTableroFx();
			
			
		}
		System.out.println("*********** Flota Hundida - Fin Partida ***********");
		// la partida termino buscamos un método que muestre las puntuaciones y el ganador
		obtenerPuntuacion();
	}
	
	public void obtenerPuntuacion() {
		Jugador [] participantes = Partida.getParticipantes();
		for (Jugador jugador : participantes) {
			System.out.println(jugador);
		}
	}

	
	public void verTableroFx() {
		tableroFxStage.setX(50);
		tableroFxStage.setY(300);
		tableroFxStage.close();
		tableroFx.setGridLinesVisible(true);
		tableroFx.setHgap(2);
		tableroFx.setVgap(2);
		tableroFx.setTranslateX(100.0);
		tableroFx.setTranslateY(100.0);
		
		
		tableroFxStage.setTitle("HUNDIR LA FLOTA");
		tableroFxStage.setScene(tableroFxScene);
		tableroFxStage.show();
		
			int sizeXY = Tablero.getTablero().length;
			System.out.println(sizeXY);
			Rectangle[][] casillasTablero = new Rectangle[sizeXY][sizeXY];
			
	
			for (int i = 0; i < casillasTablero.length; i++) {
				for (int j = 0; j < casillasTablero.length; j++) {
					casillasTablero[j][i] = new Rectangle(0,0,20,20);
					try {
						casillasTablero[j][i].setFill(Color.BLUE);
						if (Tablero.getPosicionEnTablero(i, j).getSize() == 0) {
							casillasTablero[j][i].setFill(Color.WHITE); //cambiamos j por i pq javaFX las coord x e y estan intercambiadas
						} 
					} catch (NullPointerException e) {
					}
					
					tableroFx.getChildren().add(casillasTablero[j][i]);
					GridPane.setConstraints(casillasTablero[j][i], j, i);	
				}
			}
			Barco [] flotaGuerra = Partida.getFlotaGuerra();
			
			for (Barco barco : flotaGuerra) {
				String [] barcoPos = barco.getPositions();
				int [] barcoState = barco.getStateForPositions();
				for (int i = 0; i < barcoState.length; i++) {
					String [] posicion = barcoPos[i].split("-");
					int posX = Integer.parseInt(posicion[1]); //invertimos la coord X en javaFx es la Y
					int posY = Integer.parseInt(posicion[0]);
					if (barco.getSize() != 0) {
						System.out.println(barcoState[i]);
						switch (barcoState[i]) {
						case 1 :
							casillasTablero[posX][posY].setFill(Color.BLACK);
							break;
						case 2 :
							casillasTablero[posX][posY].setFill(Color.RED);
							break;
						}
					}
	
				}
			}
	}

}