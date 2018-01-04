package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//static GridPane tableroFx = new GridPane();

		Tablero tablero= new Tablero(10);				
		Partida partida = new Partida();

		Menu menu = new Menu();	

		Partida.ponerFlotaEnTablero(Partida.getFlotaGuerra());
		Tablero.verTablero();
		
		menu.turnoJugadores(Partida.getParticipantes());

	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
