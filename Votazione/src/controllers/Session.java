package controllers;

/**
 * OVERVIEW: Esiste un'istanza unica di questa classe, rappresenta la sessione che a sua volta
 * può essere sia aperta che chiusa, e deve rappresentare tutte le caratteristiche della sessione:
 * come modalità di voto, modalità di calcolo del vincitore
 */
public class Session {

	int on; //inizializzazione sessione chiusa
	String modVoto;
	String modCalcVoto = "";
	String modCalcRef = "";
	
	//deve indicare la modalità di voto, 0 <= x <= 3
	int indexModVoto = 0;  
	int indexModCalcVoto = 0;
	
	//costruttore sessione
	public Session(){
		this.on = 0;
		this.modVoto = "";
		this.modCalcVoto = "";
		this.modCalcRef = "";
	}
	
	/**
	 * Getter and Setter
	 */
	public void setModVoto(String n) {
		this.modVoto = n;
	}
	public String getModVoto() {
		return modVoto;
	}
	
	public void setModCalcVoto(String n) {
		this.modCalcVoto = n;
	}
	public String getModCalcVoto() {
		return modCalcVoto;
	}

	public void setModCalcRef(String n) {
		this.modCalcRef = n;
	}
	public String getModCalcRef() {
		return modCalcRef;
	}

}
