package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {
	
	private PriorityQueue<Evento> queue = new PriorityQueue<>() ;
	
	//Stato del mondo
	private int autoTotali;
	private int autoDisponibili;
	
	//Parametri Simulazione
	private LocalTime oraInizio = LocalTime.of(8, 0);
	private LocalTime oraFine = LocalTime.of(21, 0);
	private Duration intervalloArrivoCliente = Duration.ofMinutes(10);
	private List<Duration> durataNoleggio;
	
	//Statistiche raccolte
	private int numeroClientiTotali;
	private int numeroClientiInsoddisfatti;
	
	//Generatore numero casuale
	private Random rand = new Random();
	
	//Inizializzo lista con i valori possibili della durata del noleggio
	public Simulatore() {
		durataNoleggio = new ArrayList<>();
		durataNoleggio.add(Duration.ofHours(1));
		durataNoleggio.add(Duration.ofHours(2));
		durataNoleggio.add(Duration.ofHours(3));
	}
	
	//Inizializzo ogni evento ( e inizializzo solo i parametri)
	public void init(int autoTotali) {
		this.autoTotali=autoTotali;
		this.autoDisponibili=this.autoTotali;
		this.numeroClientiTotali=0;
		this.numeroClientiInsoddisfatti=0;
		
		this.queue.clear();
		
		//Carico gli eventi iniziali
		//Arriva un cliente ogni intervalloArrivoCliente
		//a partire da oraInizio
		
		    //Inizializzazione         //Termine              //Incremento
		for( LocalTime ora = oraInizio; ora.isBefore(oraFine); ora = ora.plus(intervalloArrivoCliente)) {
			
			queue.add(new Evento(ora,TipoEvento.CLIENTE_ARRIVA));
			
		}
	}
	
	public void run() {
		
		while (!queue.isEmpty()) {
			Evento ev = queue.poll();
			
			switch(ev.getTipo()) {
			
			case CLIENTE_ARRIVA:
				this.numeroClientiTotali++;
				if (this.autoDisponibili==0) {
					this.numeroClientiInsoddisfatti++;
				}
				else {
					//Noleggio l'auto
					this.autoDisponibili--;
					
					int i = rand.nextInt(durataNoleggio.size());
					Duration noleggio = durataNoleggio.get(i);
					LocalTime rientro = ev.getTempo().plus(noleggio);
					
					queue.add(new Evento(rientro,TipoEvento.AUTO_RESTITUITA));
					
				}
				break;
				
			case AUTO_RESTITUITA:
				
				this.autoDisponibili++;
				
				break;
			}
		}
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public List<Duration> getDurataNoleggio() {
		return durataNoleggio;
	}

	public void setDurataNoleggio(List<Duration> durataNoleggio) {
		this.durataNoleggio = durataNoleggio;
	}

	public int getAutoTotali() {
		return autoTotali;
	}

	public int getAutoDisponibili() {
		return autoDisponibili;
	}

	public Duration getIntervalloArrivoCliente() {
		return intervalloArrivoCliente;
	}

	public int getNumeroClientiTotali() {
		return numeroClientiTotali;
	}

	public int getNumeroClientiInsoddisfatti() {
		return numeroClientiInsoddisfatti;
	}
	
	

}
