package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	List<String> vertici;
	FoodDao dao;
	
	List<String> bestSoluzione;
	int pesoBest;
	int livelloMax;
	Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		
		vertici = new ArrayList<>();
		dao = new FoodDao();
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
	}
	
	public void creaGrafo(int calories) {
		
		List<String> vertici = dao.listAllPortionsName(calories);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Arco> archi = dao.listOfEdges(vertici);
		
		for(Arco a: archi) {
			
			if(this.grafo.containsVertex(a.getNome1())&& this.grafo.containsVertex(a.getNome2())) {
			
				
				Graphs.addEdgeWithVertices(this.grafo, a.getNome1(), a.getNome2(), a.getPeso());
			}
			
		}
		
	}
	
	public String componentiConnesse(String origine){
		
		String stringa = "";
		List<DefaultWeightedEdge> result = new ArrayList<>();
		
		
		
		for(DefaultWeightedEdge e: this.grafo.edgesOf(origine)) {
			
			stringa +="\n("+ this.grafo.getEdgeSource(e) + ","+ this.grafo.getEdgeTarget(e)+") - "+this.grafo.getEdgeWeight(e); 
		}
		
		return stringa;
	}

	
	
	public List<String> cercaCammino(String origine, int n) {
		
		List<String> parziale = new ArrayList<>();
		bestSoluzione = new ArrayList<>();
		pesoBest = 0;
		parziale.add(origine);
		livelloMax= n;
		cercaRicorsivo(parziale);
		
		return bestSoluzione;
		
	}
	
	
	
	private void cercaRicorsivo(List<String> parziale) {
		
		if(parziale.size()==livelloMax+1) {
			int peso = pesoCammino(parziale);
			if(peso> pesoBest) {
				pesoBest = peso;
				bestSoluzione = new ArrayList<>(parziale);
			}
			return;
		}
		for(String vicino: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cercaRicorsivo(parziale);
	
				parziale.remove(parziale.size()-1);
			}
		}
			
	
		
	}
	

	private int pesoCammino(List<String> parziale) {
		int peso = 0 ;
		for(int i=1; i<parziale.size(); i++) {
			int p = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i))) ;
			peso += p ;
		}
		return peso ;
	}
	

	public Set<String> getVertici() {
		return this.grafo.vertexSet();
	}

	public Set<DefaultWeightedEdge> getArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet();
	}

	public int getPesoBest() {
		return pesoBest;
	}

	public int getLivelloMax() {
		return livelloMax;
	}
	
	
	
}
