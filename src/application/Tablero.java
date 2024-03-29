package application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Tablero {
	
	private static Barco [][] tablero;
	
	static BorderPane tableroFxPrincipal = new BorderPane();
	static GridPane tableroFxRejilla = new GridPane();
	static AnchorPane tableroFxMarcador;
	static AnchorPane tableroFxLeyenda;
	static Stage tableroFxStage = new Stage();
	static Scene tableroFxScene = new Scene(tableroFxPrincipal,600,450);

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
	
	public static void flotaHundida() {
		Label finalPartida = new Label("LA FLOTA HA SIDO HUNDIDA");		
		finalPartida.setTextFill(Color.RED);
		finalPartida.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		tableroFxMarcador.setTopAnchor(finalPartida, 15.0);
		tableroFxMarcador.setLeftAnchor(finalPartida, 100.0);
		tableroFxMarcador.getChildren().add(finalPartida);
	}
	
	
	public static void dibujarBarco(Barco barcoAfectado, int piezaAfectada, int x, int y) {
		Image image = barcoAfectado.getImagenesBarcos()[barcoAfectado.getSize()-1][piezaAfectada]; //resto uno al tamaño porque los indices empiezan en cero
		ImageView imageview = new ImageView(image);
		HBox hbox = new HBox(imageview);
		imageview.setCache(true);
		imageview.setFitWidth(25);
		imageview.setPreserveRatio(true);
		//p2.setSmooth(true);
		//p3.setPrefWidth(25.0);
		//p3.setPrefHeight(25.0);
		hbox.setStyle("-fx-background-color: #BBBBBB;");
		tableroFxRejilla.getChildren().add(hbox);
		tableroFxRejilla.setConstraints(hbox, x+1, y+1); //sumo uno a las coordenadas porque el tablero esta desplazado a causa de dibujar los numeros de las coordenadas
	}
	
	public static void marcador() {
		Color[] colores = {Color.BLUE,Color.RED,Color.GREEN,Color.PINK};
		Jugador[] jugadores = Partida.getParticipantes();
		Label turno = new Label("TURNO");
		Label puntuacion = new Label("PUNTUACIÓN");
		Label contadorTurno = new Label(Menu.turno+"");
		turno.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		contadorTurno.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		contadorTurno.setTextFill(Color.BLUE);
		puntuacion.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		tableroFxMarcador.setTopAnchor(turno, 5.0);
		tableroFxMarcador.setLeftAnchor(turno, 5.0);
		tableroFxMarcador.setTopAnchor(puntuacion, 5.0);
		tableroFxMarcador.setRightAnchor(puntuacion, 5.0);
		tableroFxMarcador.setTopAnchor(contadorTurno, 25.0);
		tableroFxMarcador.setLeftAnchor(contadorTurno, 25.0);
		
		
		tableroFxMarcador = new AnchorPane(turno,puntuacion,contadorTurno);
		
		Label[] labelJugadores = new Label[jugadores.length];
		for (int i = 0; i < jugadores.length; i++) {
			labelJugadores[i] = new Label(jugadores[i].getNombre() + " : " + jugadores[i].getPuntuacion());
			labelJugadores[i].setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			labelJugadores[i].setTextFill(colores[i]);
			tableroFxMarcador.setTopAnchor(labelJugadores[i], 30.0 + i*15);
			tableroFxMarcador.setRightAnchor(labelJugadores[i], 30.0);
			tableroFxMarcador.getChildren().add(labelJugadores[i]);
		}
	}
	
	public static void leyenda() {
		Label agua = new Label("Agua");
		Rectangle aguaR = new Rectangle(0,0,15,15);
		aguaR.setFill(Color.WHITE);
		aguaR.setStroke(Color.BLACK);
		Label tocado = new Label("Tocado");
		Rectangle tocadoR = new Rectangle(0,0,15,15);
		tocadoR.setFill(Color.BLACK);
		Label hundido = new Label("Hundido");
		Rectangle hundidoR = new Rectangle(0,0,15,15);
		hundidoR.setFill(Color.RED);

		tableroFxLeyenda.setLeftAnchor(agua, 40.0);
		tableroFxLeyenda.setBottomAnchor(agua, 30.0);
		tableroFxLeyenda.setLeftAnchor(aguaR, 75.0);
		tableroFxLeyenda.setLeftAnchor(tocado, 100.0);
		tableroFxLeyenda.setLeftAnchor(tocadoR, 145.0);
		tableroFxLeyenda.setLeftAnchor(hundido, 170.0);
		tableroFxLeyenda.setLeftAnchor(hundidoR, 225.0);
		
		tableroFxLeyenda = new AnchorPane();
		tableroFxLeyenda.getChildren().addAll(agua,aguaR,tocado,tocadoR,hundido,hundidoR);
		
	}
	
	
	public static void verTableroFx() {
		tableroFxPrincipal.getChildren().clear();
		marcador();
		leyenda();
		tableroFxPrincipal.setTop(tableroFxMarcador);
		tableroFxPrincipal.setLeft(tableroFxRejilla);
		tableroFxPrincipal.setBottom(tableroFxLeyenda);
		tableroFxStage.setX(50);
		tableroFxStage.setY(250);
		tableroFxStage.close();
		tableroFxRejilla.setGridLinesVisible(true);
		tableroFxRejilla.setHgap(2);
		tableroFxRejilla.setVgap(2);
		tableroFxRejilla.setTranslateX(50.0);
		tableroFxRejilla.setTranslateY(20.0);
		
		
		tableroFxStage.setTitle("HUNDIR LA FLOTA");
		tableroFxStage.setScene(tableroFxScene);
		tableroFxStage.show();
		
			int sizeXY = Tablero.getTablero().length;
			System.out.println("sizeXY " + sizeXY);
			Rectangle[][] casillasTablero = new Rectangle[sizeXY][sizeXY];
			
	/*		
	        ImageView imv = new ImageView();
	        Image image2 = new Image(Main.class.getResourceAsStream("img/1_sub.png"));
	        Image[] img = new Image[sizeXY+1];
	        ImageView[] imgV = new ImageView[sizeXY+1];
	        HBox[] hbox = new HBox[sizeXY+1];
	        imv.setImage(image2);
	        imv.setCache(true);
	        imv.setFitWidth(30);
	        imv.setPreserveRatio(true);
	        
	        for (int i = 0; i < sizeXY+1; i++) {
				//img[i] = image2;
	        	imgV[i] = new ImageView();
	        	hbox[i] = new HBox();
				imgV[i].setImage(image2);
				imgV[i].setCache(true);
				imgV[i].setFitWidth(30);
				imgV[i].setPreserveRatio(true);
				hbox[i].getChildren().add(imgV[i]);
				hbox[i].setPrefWidth(20.0);
				hbox[i].setPrefHeight(20.0);
				hbox[i].setStyle("-fx-background-color: #BBBBBB;");
				

				tableroFxRejilla.getChildren().add(hbox[i]);
				tableroFxRejilla.setConstraints(hbox[i], 0, i);
			}
	        
	      */ 



			
			// esta parte del código añade coordenadas al tablero de 1 a 9 y el 10 es el 0
			Label[][] tableroConCoordenadas = new Label[sizeXY + 1][sizeXY + 1];
			for (int i = 0; i < tableroConCoordenadas.length; i++) {
				tableroConCoordenadas[i][0] = new Label();
				if (i == 0) {   //la coordenada cero debe ser escrita a partir de i=1
					tableroConCoordenadas[i][0].setText("");
					} else {tableroConCoordenadas[i][0].setText((i)+"");}
				
				tableroConCoordenadas[i][0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
				tableroFxRejilla.getChildren().add(tableroConCoordenadas[i][0]);
				GridPane.setHalignment(tableroConCoordenadas[i][0], HPos.CENTER);//centra los numeros en la rejilla
				GridPane.setConstraints(tableroConCoordenadas[i][0], i, 0);
				
			}
			for (int i = 0; i < tableroConCoordenadas.length; i++) {
				tableroConCoordenadas[0][i] = new Label();
				if (i == 0) {
					tableroConCoordenadas[0][i].setText("");
					} else {tableroConCoordenadas[0][i].setText(" " + (i) +" ");}
				
				tableroConCoordenadas[0][i].setFont(Font.font("Arial", FontWeight.BOLD, 12));
				tableroFxRejilla.getChildren().add(tableroConCoordenadas[0][i]);
				GridPane.setHalignment(tableroConCoordenadas[0][i], HPos.CENTER);
				GridPane.setConstraints(tableroConCoordenadas[0][i], 0, i);

			}
			tableroConCoordenadas[10][0].setText("0"); //ponemos que despues de la coordenada 9 sea la 0
			tableroConCoordenadas[0][10].setText(" 0 ");
			
			for (int i = 0; i < sizeXY; i++) {
				for (int j = 0; j < sizeXY; j++) {
					casillasTablero[j][i] = new Rectangle(0,0,25,25);					
					try {
						casillasTablero[j][i].setFill(Color.BLUE);
						if (Tablero.getPosicionEnTablero(i, j).getSize() == 0) {
							casillasTablero[j][i].setFill(Color.WHITE); //cambiamos j por i pq javaFX las coord x e y estan intercambiadas
						} 
					} catch (NullPointerException e) {
					}
					
					tableroFxRejilla.getChildren().add(casillasTablero[j][i]);//pinto rectangulos en la rejilla
					
					//añadimos +1 porque la fila 0 y la columna 0 del GridPane están ocupadas con las coordenadas del tablero
					GridPane.setConstraints(casillasTablero[j][i], j+1, i+1);
					//GridPane.setConstraints(imageview, j+1, i+1);
																	
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
							dibujarBarco(barco,i,posX,posY);
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