package it.polito.tdp.food.model;

public class Arco {
	
	private String nome1;
	private String nome2;
	private int peso;
	/**
	 * @param nome1
	 * @param nome2
	 * @param peso
	 */
	public Arco(String nome1, String nome2, int peso) {
		super();
		this.nome1 = nome1;
		this.nome2 = nome2;
		this.peso = peso;
	}
	public String getNome1() {
		return nome1;
	}
	public String getNome2() {
		return nome2;
	}
	public int getPeso() {
		return peso;
	}
	
	

}
