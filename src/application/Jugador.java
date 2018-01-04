package application;

public class Jugador {

	private int id;
	private String nombre;
	private int turno = 0;
	private int puntuacion = 0;
	private boolean aciertaUltimoAtaque = false;
	
	//List<Integer> registroAtaques = new ArrayList<>(); 
	
	public Jugador(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public void atacar(int x, int y) {
		Barco posicionAtacada = null; // Las posiciones en el tablero contienen barcos o null;
		posicionAtacada = Tablero.getPosicionEnTablero(x, y);  // posición atacada, ahora comprobar si hay barco o no (null)
		if (posicionAtacada != null) { //comprobamos si la posición es distanta de agua es que hay barco
			int index = posicionAtacada.buscarIndexPosicionAtacada(x, y); //buscar en el array de posiciones del barco atacado que estado tiene dicha posición		
			if (index != -1) { //comprobamos que la posición del array no sea -1, eso significa que fue encontrada
				puntuacion += posicionAtacada.cambiarStateBarco(index);
				aciertaUltimoAtaque = true;
				posicionAtacada.comprobarBarcoHundido();
			}
		} else {
			aciertaUltimoAtaque = false;
			Barco agua = new Barco(0);
			agua.setStateForPositions(0, 0);
			Tablero.setTablero(x, y, agua);
		}
		
		System.out.println("******************************");
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	
	
	public boolean getAciertaUltimoAtaque() {
		return aciertaUltimoAtaque;
	}

	@Override
	public String toString() {
		return "Jugador [Id=" + id + ", Nombre=" + nombre + ", Puntuación=" + puntuacion + "]";
	}
	
	

}
