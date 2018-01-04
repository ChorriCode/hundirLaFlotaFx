package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	static GridPane tableroFx = new GridPane();
	
	@Override
	public void start(Stage primaryStage) throws Exception {


		Tablero tablero= new Tablero(10);				
		Partida partida = new Partida();



		Menu menu = new Menu();	
		
		
		Partida.ponerFlotaEnTablero(Partida.getFlotaGuerra());
		Tablero.verTablero();
		
		menu.turnoJugadores(Partida.getParticipantes());
		verTableroFx();
		
		
		tableroFx.setGridLinesVisible(true);
		tableroFx.setHgap(2);
		tableroFx.setVgap(2);
		tableroFx.setTranslateX(100.0);
		tableroFx.setTranslateY(100.0);
		
		Scene scene = new Scene(tableroFx,400,400);
		
		
		primaryStage.setTitle("HUNDIR LA FLOTA");
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}
	
	public static void verTableroFx() {
		
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
	public static void main(String[] args) {
		launch(args);
	}
}
