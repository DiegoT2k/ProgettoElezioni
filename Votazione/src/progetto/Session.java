package progetto;


/**
 * OVERVIEW: Esiste un'istanza unica di questa classe, rappresenta la sessione che a sua volta
 * può essere sia aperta che chiusa, e deve rappresentare tutte le caratteristiche della sessione:
 * come modalità di voto, modalità di calcolo del vincitore
 */
public class Session {

	int on = 0; //inizializzazione sessione chiusa
	String[] modVoto = {"Voto ordinale", "Voto categorico", "Voto categorico con preferenze", "Referendum"};
	String[] modCalcVoto = {"Maggioranza", "Maggioranza assoluta"};
	String[] modCalcRef = {"Quorum", "Senza Quorum"};
	//deve indicare la modalità di voto, 0 <= x <= 3
	int indexModVoto = 0;  
	int indexModCalcVoto = 0;
	//costruttore session
	public Session(){}

}
