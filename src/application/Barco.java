package application;

import javafx.scene.image.Image;

public class Barco {
	
	private String shipType; //submarino(1), fragata(2), destructor (3), acorazado(4), portaAviones(5)
	private int size; //cuantas casillas ocupa el barco
	private int [] stateForPositions; // 0-oculto, 1-tocado, 2-hundido de cada posicion en el tablero
	private String [] positions; //es una cadena que contiene las coordenadas "x-y" del barco en el tablero.
	private boolean hundido = false; //se refuere al estados total del barco, si todas sus casillas están tocadas
	private Image[][] imagenesBarcos = {
			{new Image(Main.class.getResourceAsStream("img/1_sub.png"))},
				{new Image(Main.class.getResourceAsStream("img/2_frag.png")),
				 new Image(Main.class.getResourceAsStream("img/2_frag.png"))},
					{new Image(Main.class.getResourceAsStream("img/3_dest.png")),
					 new Image(Main.class.getResourceAsStream("img/3_dest.png")),
					 new Image(Main.class.getResourceAsStream("img/3_dest.png"))},
						{new Image(Main.class.getResourceAsStream("img/4_acor.png")),
						 new Image(Main.class.getResourceAsStream("img/4_acor.png")),
						 new Image(Main.class.getResourceAsStream("img/4_acor.png")),
						 new Image(Main.class.getResourceAsStream("img/4_acor.png"))},
							{new Image(Main.class.getResourceAsStream("img/5_porta.png")),
							new Image(Main.class.getResourceAsStream("img/5_porta.png")),
							new Image(Main.class.getResourceAsStream("img/5_porta.png")),
							new Image(Main.class.getResourceAsStream("img/5_porta.png")),
							new Image(Main.class.getResourceAsStream("img/5_porta.png"))}
			
	};
	
	public Barco(int size) {
		this.size = size;
		positions = new String[size];
		stateForPositions = new int[size];
		switch (size) {
		case 0 :
			this.shipType = "Agua"; //Aunque por defecto el tablero es agua pero igual a null, cuando fallamos ese agua es igual a barco Agua
			stateForPositions = new int[1];
			break;
		case 1 :	
			this.shipType = "Submarino";
			break;
		case 2 :	
			this.shipType = "Fragata";
			break;
		case 3 :	
			this.shipType = "Destructor";
			break;
		case 4 :	
			this.shipType = "Acorazado";
			break;
		case 5 :	
			this.shipType = "PortaAviones";
			break;

		}
	}
	
	

	public int cambiarStateBarco(int index) { //establece si el barco está tocado o hundido y devuelve 1 punto si lo tocas
		int puntos = 0;
		switch (this.getStateForPositions()[index]) {
		case 0 :
			this.setStateForPositions(index,1); 
			puntos = 1; //por tocado el jugador obtiene 1 punto
			System.out.println("Tocado");
			return puntos;
		case 1 :
			this.setStateForPositions(index,1);
			System.out.println("Tocado nuevamente");
			break;
		case 2 :
			this.setStateForPositions(index,2);
			System.out.println("Este barco ya está hundido");
			break;
		}
		return puntos;
	}
	
	public int comprobarBarcoHundido() {
		int contador = 0;
		int puntos = 0;
		if (this.stateForPositions[0] == 0 || this.stateForPositions[0] == 1) { //si la primera posicion es 0 o 1, seguimos a ver si acabamos de hundirlo. Sino es que es 2 y ya está hundido
			for (int i = 0; i < this.size; i++) { //recorremos el array del estado del barco
				if (this.stateForPositions[i] == 1) { //cada posicion del barco tocada aumentamos el contador de veces tocado
					contador++;
				}
			}
			if (contador == this.size) { // si el contador de veces tocado coincide con el tamaño del barco es que está hundio
				for (int i = 0; i < this.size; i++) {
					this.stateForPositions[i] = 2; //cambiamos el estado de todas las posciones del barco a hundido
				}
				System.out.println(this.shipType + " hundido");
				this.hundido = true;
				puntos = 1; //por hundir el barco el jugador tiene 1 punto;
				return puntos;
			} else {
				System.out.println(this.shipType + " aún no está hundido"); //si el contador de veces tocada no coincide con el tamaño del barco, aún no está hundido
				return puntos;
			}		
		} else {
			System.out.println(this.shipType + " ya está hundido");
			return puntos;
		}
	}
	
	public int buscarIndexPosicionAtacada(int x, int y) {
		int posX = -1;
		int posY = -1;
		String [] posicion;
		for (int i = 0; i < this.size; i++) {  //recorremos el array que contiene las posiciones del barco en el tablero
			posicion = this.positions[i].split("-"); //como el array es un String separamos las posiciones en enteros
			posX = Integer.parseInt(posicion[0]); //almacenamos en posX su posición X como valor entero
			posY = Integer.parseInt(posicion[1]); //almacenamos en posY su posición Y como valor entero
			if (x == posX && y == posY) {	// comparamos que la posicion que nos viene por parametro que es la de ataque coincide con alguna de las posiciones que ocupa el barco
				//System.out.println("barco encontrado"); //si coincide, lo hemos encontrado y devolvemos ese index
				return i;
			}			
		}
		return -1;
	}
	
	// GETTERS Y SETTER
	
	public boolean getHundido() {
		return hundido;
	}
	
	public String getShipType() {
		return shipType;
	}
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public int[] getStateForPositions() {
		return stateForPositions;
	}

	public void setStateForPositions(int index, int state) {
		this.stateForPositions[index] = state;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(int index, String positions) {
		this.positions[index] = positions;
	}

	public Image[][] getImagenesBarcos() {
		return imagenesBarcos;
	}
	

}

