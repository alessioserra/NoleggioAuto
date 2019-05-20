package it.polito.tdp.noleggio.model;

public class TestSimulatore {

	public static void main(String[] args) {
	
		Simulatore sim = new Simulatore();
		
		sim.init(10);
		
		sim.run();
		
		System.out.format("%d clienti totali, di cui %d insoddisfatti\n",sim.getNumeroClientiTotali(),sim.getNumeroClientiInsoddisfatti());

	}

}
