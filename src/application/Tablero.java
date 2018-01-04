package application;

public class Tablero {
	
	private static Barco [][] tablero;

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