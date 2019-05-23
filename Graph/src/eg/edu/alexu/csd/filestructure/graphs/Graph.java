package eg.edu.alexu.csd.filestructure.graphs;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.graphs.IGraph; 

public class Graph implements IGraph {
	
	private ArrayList<Edge> edges ;
	private ArrayList< ArrayList<Edge> > adjList;
	private ArrayList<Integer> dijkstra;
	private final int INF = Integer.MAX_VALUE;
	private int size ;
	private int OO ;
	private int numEdges;
	public Graph() {
		edges = new ArrayList<>();
		adjList = new ArrayList < ArrayList<Edge> >();
		dijkstra = new ArrayList<>();
		size = 0 ;
		OO = 1073741823;
		numEdges=0;
	}
	

	@SuppressWarnings("resource")
	@Override
	public void readGraph(File file) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;
			line = br.readLine();
			Pattern first = Pattern.compile("([0-9]+)[ ]+([0-9]+)");
			Matcher m = first.matcher(line);
			m.find();
			size = Integer.parseInt(m.group(1));
			numEdges = Integer.parseInt(m.group(2));
			adjList.clear();
			for(int i=0;i<size;i++) {
				adjList.add(new ArrayList<Edge>());
			}
			Pattern second = Pattern.compile("([0-9]+)[ ]+([0-9]+)[ ]+(-?[0-9]+)");
			while((line = br.readLine())!=null) {
				m=second.matcher(line);
				m.find();
				int start = Integer.parseInt(m.group(1));
				int finish = Integer.parseInt(m.group(2));
				int weight = Integer.parseInt(m.group(3));
				edges.add(new Edge(start,finish,weight));
				adjList.get(start).add(new Edge(start,finish,weight));
			}
			// check inValid graph
			if(edges.size()!=numEdges) {
				throw new RuntimeErrorException(new Error());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeErrorException(new Error());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeErrorException(new Error());
		} 
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.numEdges;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		// TODO Auto-generated method stub
	    ArrayList<Integer> vertices = new ArrayList<>();
	    for(int i=0;i<size;i++) {
	    	vertices.add(i);
	    }
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		// TODO Auto-generated method stub
		ArrayList<Integer> neighbors  = new ArrayList<>();
		for(int i=0; i < adjList.get(v).size();i++) {
			neighbors.add(adjList.get(v).get(i).getFinish()+1);
		}
		return neighbors;
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		// TODO Auto-generated method stub
		Arrays.fill(distances, OO);
		distances[src] = 0;
		boolean[] checked = new boolean[distances.length];
		Arrays.fill(checked, false);
		for (int j = 0; j < distances.length; j++) {
			int index = -1;
			int min = INF;
			for (int i = 0; i < checked.length; i++) {
				if (!checked[i] && distances[i] < min) {
					min = distances[i];
					index = i;
				}
			}
			if (index == -1) {
				return ;
			}
			checked[index] = true;
			dijkstra.add(index);
			ArrayList<Edge> adj = adjList.get(index);
			for(int i = 0; i < adj.size(); i++) {
				Edge edge = adj.get(i);
				if(distances[index] + edge.getWeight() < distances[edge.getFinish()]  && !checked[edge.getFinish()]) {
					distances[edge.getFinish()] = distances[index] + edge.getWeight();
				}
			}
		}
	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		// TODO Auto-generated method stub
		return this.dijkstra;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		// TODO Auto-generated method stub
		Arrays.fill(distances, OO);
		distances[src]=0;
		boolean noNegativeCycles= true;
		for(int i=0;i<size;i++) {
			boolean noChange = true ;
			for(int j=0;j<edges.size();j++) {
				  int start = edges.get(j).getStart();
				  int finish = edges.get(j).getFinish();
				  int weight = edges.get(j).getWeight();
				  // relaxation step 
				  if(distances[start]+weight < distances[finish]) {
					  distances[finish] = distances[start]+weight;
					  noChange = false;
					  if(i==size-1) {
						  noNegativeCycles = false;
					  }
				  }
			  
			}
			// if no relaxation then we get shortest paths
			if(noChange) {
				return true;
			}
		}
		// check if there is no negative cycles
		return noNegativeCycles;
	}

}
