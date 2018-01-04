package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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
		//verTableroFx();
		
		/*
		tableroFx.setGridLinesVisible(true);
		tableroFx.setHgap(2);
		tableroFx.setVgap(2);
		tableroFx.setTranslateX(100.0);
		tableroFx.setTranslateY(100.0);
		
		Scene scene = new Scene(tableroFx,400,400);
		primaryStage.setTitle("HUNDIR LA FLOTA");
		primaryStage.setScene(scene);
		primaryStage.show();
		*/
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
