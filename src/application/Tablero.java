package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Tablero {
	
	private static Barco [][] tablero;
	
	static BorderPane tableroFxBorder = new BorderPane();
	static GridPane tableroFxGrid = new GridPane();
	static HBox tableroFxHbox = new HBox();
	static Stage tableroFxStage = new Stage();
	static Scene tableroFxScene = new Scene(tableroFxBorder,400,400);

	public Tablero(int size) {	
		tablero = new Barco[size][size];
		vaciarTablero();
	}
	

	private static void vaciarTablero() {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				tablero[i][j] = null;
			}		
		}
	}
	// Muestra el tablero en consola null = "x", los barcos con su tama�o
	public static void verTablero() {
		Barco[][] borrame = Tablero.getTablero();
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				try { //usamos un try / catch ya que las posiciones del tablero que son agua, son barcos = null
					Barco barcoActual = tablero[i][j]; //barco de la casilla que estamos analizan)
					int sizeBarco = barcoActual.getSize(); //tamaño de ese barco
					if (sizeBarco != 0 ) { // si es cero entonces no es null es que la casilla hemos disparado y sale agua
						String inicialBarco = barcoActual.getShipType().substring(0,1); //la inicial del tipo de barco que es Submarino = "S"
						int casillaPosicionBarco = barcoActual.buscarIndexPosicionAtacada(i, j); //según el tamaño del barco que cual de sus partes está ocupando la casilla actual del tablero
						int stateCasillaBarco = barcoActual.getStateForPositions()[casillaPosicionBarco]; // que estado (nada, tocado o hundido) tiene la parte del barco que ocupa la casilla actual del tablero
						System.out.print(sizeBarco + inicialBarco + stateCasillaBarco + " "); // muestra en consola las posiciones del tablero. x = null = agua ----  1S1 = tamaño del barco + incial del tipo de barco + (1 = tocado)
					} else {
						System.out.print("A" + "   ");
					}
				} catch (NullPointerException e) {
					System.out.print("x" + "   ");
				}

			}	
			System.out.println(); 
		}		
	}
	
	public static void verTableroFx() {
		tableroFxBorder.getChildren().clear();
		tableroFxBorder.setCenter(tableroFxGrid);
		tableroFxStage.setX(50);
		tableroFxStage.setY(300);
		tableroFxStage.close();
		tableroFxGrid.setGridLinesVisible(true);
		tableroFxGrid.setHgap(2);
		tableroFxGrid.setVgap(2);
		tableroFxGrid.setTranslateX(100.0);
		tableroFxGrid.setTranslateY(100.0);
		
		
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
					
					tableroFxGrid.getChildren().add(casillasTablero[j][i]);
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

	public static Barco getPosicionEnTablero(int x, int y){
		return tablero[x][y];
	}
	
	public static Barco[][] getTablero() {
		return tablero;
	}

	public static void setTablero(int x, int y, Barco miBarco) {
		tablero[x][y] = miBarco;
	}

	
}