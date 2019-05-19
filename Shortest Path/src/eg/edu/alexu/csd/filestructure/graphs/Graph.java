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

public class Graph implements IGraph {
	
	private ArrayList<Edge> edges ;
	private ArrayList< ArrayList<Edge> > adjList;
	private int size ;
	private int OO ;
	private int numEdges;
	public Graph() {
		edges = new ArrayList<>();
		adjList = new ArrayList < ArrayList<Edge> >();
		size = 0 ;
		OO = (int) (1e9+7);
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
			size = Integer.parseInt(m.group(1));
			numEdges = Integer.parseInt(m.group(2));
			adjList = new ArrayList<>(size);
			Pattern second = Pattern.compile("([1-9]+)[ ]+([0-9]+)[ ]+(-?[0-9]+)");
			while((line = br.readLine())!=null) {
				m=second.matcher(line);
				int start = Integer.parseInt(m.group(1));
				int finish = Integer.parseInt(m.group(2));
				int weight = Integer.parseInt(m.group(3));
				start--;
				finish--;
				edges.add(new Edge(start,finish,weight));
				adjList.get(start).add(new Edge(start,finish,weight));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		// TODO Auto-generated method stub
	    ArrayList<Integer> vertices = new ArrayList<>();
	    for(int i=1;i<=size;i++) {
	    	vertices.add(i);
	    }
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		// TODO Auto-generated method stub
		ArrayList<Integer> neighbors  = new ArrayList<>();
		v--;
		for(int i=0; i < adjList.get(v).size();i++) {
			neighbors.add(adjList.get(v).get(i).getStart());
		}
		return neighbors;
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		// TODO Auto-generated method stub
		Arrays.fill(distances, OO);
		src--;
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
