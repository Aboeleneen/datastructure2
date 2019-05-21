package eg.edu.alexu.csd.filestructure.graphs;

public class Edge {
	private int start ;
	private int finish;
	private int weight;
	
	public Edge(int start , int finish , int weight) {
		this.start = start;
		this.finish = finish;
		this.weight = weight;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
