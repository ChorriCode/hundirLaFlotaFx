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

	
	public void ventanaRecogidaDatos(String texto) {
		window.setX(50);
		window.setY(100);
		Label label = new Label(texto);
		valorEntradaDatos= new TextField();
		stack.setMargin(valorEntradaDatos, new Insets (80,10,10,10));

		stack.getChildren().addAll(label, valorEntradaDatos);
		valorEntradaDatos.setOnAction(new EventHandler<ActionEvent>() {		 
            @Override
            public void handle(ActionEvent event) {
            		valorString = valorEntradaDatos.getText();        	
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
		ventanaRecogidaDatos("¿Cuantos jugadores participarán? [1 a 4]: ");
		valorInt = Integer.parseInt(valorEntradaDatos.getText());
		opcion = valorInt;
		while (opcion < 1 || opcion > 4) {
			ventanaRecogidaDatos("Por favor introduzca un número del 1 al 4: ");
			valorInt = Integer.parseInt(valorEntradaDatos.getText());
			opcion = valorInt;
		}
		Jugador[] resultado = new Jugador[opcion];		
		for (int i = 0; i < resultado.length; i++) { //recorremos el array resultado para crear la cantidad de jugadores seleccionada
			ventanaRecogidaDatos("Nombre del jugador " + i + ":");
			resultado[i] = new Jugador(i,valorString.toUpperCase()); //creamos el jugador con su nombre y directamente lo metemos en el array resultado
		}
		return resultado;
	}
	
	public void ataquePorTurno(Jugador jugador) {	
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord X");
		valorInt = Integer.parseInt(valorEntradaDatos.getText());
		int x = valorInt;
		ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord Y");
		valorInt = Integer.parseInt(valorEntradaDatos.getText());
		int y = valorInt;
		jugador.atacar(x, y);
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
					if (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) == true) {break;}
				} while (jugadores[i].getAciertaUltimoAtaque() == true); //si un jugador acierta vuelve a tirar en el mismo turno.
			}
			turno++;
			Tablero.verTablero();
			Tablero.verTableroFx();
			
			
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

	


}