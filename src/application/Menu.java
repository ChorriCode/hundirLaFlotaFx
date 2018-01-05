package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Menu {
	static int turno= 0;	
	Stage window = new Stage();
	AnchorPane anchor = new AnchorPane();
	Scene scene = new Scene(anchor);
	TextField valorEntradaDatos;
	TextField cantJugadores;
	String valorString;
	int valorInt;

	
	public void ventanaRecogidaDatos(String texto) {
		window.setX(50);
		window.setY(100);
		Label label = new Label(texto);
		label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		label.setPadding(new Insets(10,10,10,10));
		valorEntradaDatos = new TextField();
		valorEntradaDatos.setAlignment(Pos.CENTER);
		
		anchor.setTopAnchor(label, 0.0);
		anchor.setTopAnchor(valorEntradaDatos, 40.0);
		anchor.setLeftAnchor(valorEntradaDatos, 20.0);
		anchor.setRightAnchor(valorEntradaDatos, 20.0);
		anchor.setBottomAnchor(valorEntradaDatos, 20.0);
	
		

		anchor.getChildren().addAll(label, valorEntradaDatos);
		valorEntradaDatos.setOnAction(new EventHandler<ActionEvent>() {		 
            @Override
            public void handle(ActionEvent event) {
            	valorString = valorEntradaDatos.getText();        	
            	anchor.getChildren().clear();           
            	window.close();
            	
            }
        });

		window.showAndWait();
		

	}

	
	public Jugador[] crearJugadores() {  //Cuando es contra la máquina elegimos hasta 4 jugadores
		
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		int opcion = 0;
		while (opcion < 1 || opcion > 4) {
			try {
				ventanaRecogidaDatos("¿Cuantos jugadores participarán? [1 a 4]: ");
				valorInt = Integer.parseInt(valorEntradaDatos.getText());
				opcion = valorInt;
			} catch (NumberFormatException e) {
				opcion = 0;
			}
		}

		Jugador[] resultado = new Jugador[opcion];		
		for (int i = 0; i < resultado.length; i++) { //recorremos el array resultado para crear la cantidad de jugadores seleccionada
			do {
				ventanaRecogidaDatos("Nombre del jugador " + i + ":");
				if (valorString.length() < 20) {
					resultado[i] = new Jugador(i,valorString.toUpperCase()); //creamos el jugador con su nombre y directamente lo metemos en el array resultado
				}				
			} while (valorString.length() > 20);
		}
		return resultado;
	}
	
	public void ataquePorTurno(Jugador jugador) {	
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		boolean error;
		//el siguiente código es para prevenir que tecleemos algo que no sea un numero cuando nos pide las coordenadas
		//el primer do while espera a que tanto la x como la y no de error
		//el do while del interior llega una vez la x es correcta se queda chequeando la y.
		//si solo pongo un do while y acierto la x pero fallo en la y, me pediría denuevo ambas coordenadas.
		do {
			try {
				ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord X");
				valorInt = Integer.parseInt(valorEntradaDatos.getText());
				if (valorInt < 0 || valorInt > 9) { //hay que validar que los numeros de coord esten en tre 0 y 9
					error = true;
				} else {
					int x = valorInt;
					error = false;
					do {
						try {
							ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord Y");
							valorInt = Integer.parseInt(valorEntradaDatos.getText());
							if (valorInt < 0 || valorInt > 9) {
								error = true;
							} else {
								int y = valorInt;
								error = false;
								jugador.atacar(x, y);
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							error = true;
						}
					} while (error);
				}
			} catch (NumberFormatException e) {
				error = true;
			}

		} while (error);
	}
	
	public void turnoJugadores(Jugador[] jugadores) {
			
		while (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) != true) { // Mientras la flota no esté hundida hacemos turnos de juego
			System.out.println("TURNO " + turno);
			for (int i = 0; i < jugadores.length; i++) { //vamos rotando a los jugadores para que participen
				System.out.println("----- Turno del jugador " + jugadores[i].getNombre() + " -----");
				do {
					Tablero.verTableroFx();
					ataquePorTurno(jugadores[i]);
					System.out.println("Acierta?: " + jugadores[i].getAciertaUltimoAtaque());

				} while (jugadores[i].getAciertaUltimoAtaque() == true && !Partida.comprobarFlotaHundida(Partida.getFlotaGuerra())); //si un jugador acierta vuelve a tirar en el mismo turno.
				if (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) == true) {
					System.out.println("hay un break");
					break;}
			}
			turno++;
			Tablero.verTablero();
			Tablero.verTableroFx();
			
			
		}
		Tablero.flotaHundida();
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

	


}