package application;

import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Menu {
	
	private Scanner scan = new Scanner(System.in);
	Stage window = new Stage();
	StackPane stack = new StackPane();
	Scene scene = new Scene(stack);
	TextField valorEntradaDatos;
	TextField cantJugadores;
	String nombreRecogido;
	int cantJugadoresRecogido;
	
	public void ventanaRecogidaDatos(String label, boolean esString) {
		
		Label labelnombreJugador = new Label(label);
		valorEntradaDatos= new TextField();
		valorEntradaDatos.setOnAction(new EventHandler<ActionEvent>() {		 
            @Override
            public void handle(ActionEvent event) {
            	if (esString) {
            		nombreRecogido = valorEntradaDatos.getText();
            	} else {
            		cantJugadoresRecogido = Integer.parseInt(valorEntradaDatos.getText());
            	}
            	
            	stack.getChildren().clear();
            	window.close();
            }
        });
		stack.setMargin(valorEntradaDatos, new Insets (80,10,10,10));
		stack.getChildren().addAll(labelnombreJugador, valorEntradaDatos);
		window.showAndWait();

	}

	
	public Jugador[] crearJugadores() {  //Cuando es contra la máquina elegimos hasta 4 jugadores
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		int opcion = 0;
		ventanaRecogidaDatos("¿Cuantos jugadores participarán? [1 a 4]: ",false);
		
		//System.out.println("HUNDIR LA FLOTA");
		//System.out.print("¿Cuantos jugadores participarán? [1 a 4]: ");
		opcion = cantJugadoresRecogido;

		while (opcion < 1 || opcion > 4) {
			ventanaRecogidaDatos("Por favor introduzca un número del 1 al 4: ",false);
			opcion = cantJugadoresRecogido;
		}
		Jugador[] resultado = new Jugador[opcion];
		
		for (int i = 0; i < resultado.length; i++) { //recorremos el array resultado para crear la cantidad de jugadores seleccionada
			ventanaRecogidaDatos("Nombre del jugador " + i + ":", true);
			
			//System.out.print("Nombre del Jugador " + i + ": ");
			//nombreRecogido = scan.next(); //nombre del jugador
			resultado[i] = new Jugador(i,nombreRecogido); //creamos el jugador con su nombre y directamente lo metemos en el array resultado
		}
		return resultado;
	}
	
	public void ataquePorTurno(Jugador jugador) {	
		System.out.println("Jugador " + jugador.getNombre() + " ataca:");
		System.out.println("Introduce coordenada X: ");
		int x = scan.nextInt();
		System.out.println("Introduce coordenada Y: ");
		int y = scan.nextInt();
		jugador.atacar(x, y);
	}
	
	public void turnoJugadores(Jugador[] jugadores) {
		int turno= 0;		
		while (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) != true) { // Mientras la flota no esté hundida hacemos turnos de juego
			System.out.println("TURNO " + turno);
			for (int i = 0; i < jugadores.length; i++) { //vamos rotando a los jugadores para que participen
				System.out.println("----- Turno del jugador " + jugadores[i].getNombre() + " -----");
				do {
					ataquePorTurno(jugadores[i]);
					System.out.println("Acierta?: " + jugadores[i].getAciertaUltimoAtaque());
					if (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) == true) {break;}
				} while (jugadores[i].getAciertaUltimoAtaque() == true); //si un jugador acierta vuelve a tirar en el mismo turno.
			}
			turno++;
			Tablero.verTablero();
			
			
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