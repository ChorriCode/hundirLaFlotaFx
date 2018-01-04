package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
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

	
	public Jugador[] crearJugadores() {  //Cuando es contra la m�quina elegimos hasta 4 jugadores
		
		window.setTitle("DATOS"); 
		window.setScene(scene); // Creo la ventana
		int opcion = 0;
		ventanaRecogidaDatos("�Cuantos jugadores participar�n? [1 a 4]: ");
		valorInt = Integer.parseInt(valorEntradaDatos.getText());
		opcion = valorInt;
		while (opcion < 1 || opcion > 4) {
			ventanaRecogidaDatos("Por favor introduzca un n�mero del 1 al 4: ");
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
		boolean error;
		//el siguiente c�digo es para prevenir que tecleemos algo que no sea un numero cuando nos pide las coordenadas
		//el primer do while espera a que tanto la x como la y no de error
		//el do while del interior llega una vez la x es correcta se queda chequeando la y.
		//si solo pongo un du while y acierto la x pero fallo en la y, me pedir�a denuevo ambas coordenadas.
		do {
			try {
				ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord X");
				valorInt = Integer.parseInt(valorEntradaDatos.getText());
				int x = valorInt;
				error = false;
				do {
					try {
						ventanaRecogidaDatos("Jugador " + jugador.getNombre() + " ataca coord Y");
						valorInt = Integer.parseInt(valorEntradaDatos.getText());
						int y = valorInt;
						error = false;
						jugador.atacar(x, y);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						error = true;
					}
				} while (error);
			} catch (NumberFormatException e) {
				error = true;
			}

		} while (error);
	}
	
	public void turnoJugadores(Jugador[] jugadores) {
			
		while (Partida.comprobarFlotaHundida(Partida.getFlotaGuerra()) != true) { // Mientras la flota no est� hundida hacemos turnos de juego
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
		System.out.println("*********** Flota Hundida - Fin Partida ***********");
		// la partida termino buscamos un m�todo que muestre las puntuaciones y el ganador
		obtenerPuntuacion();
	}
	
	public void obtenerPuntuacion() {
		Jugador [] participantes = Partida.getParticipantes();
		for (Jugador jugador : participantes) {
			System.out.println(jugador);
		}
	}

	


}