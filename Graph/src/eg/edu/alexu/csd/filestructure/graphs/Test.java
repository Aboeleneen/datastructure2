package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Graph t = new Graph();
			t.readGraph(new File("res\\1.txt"));
		/*	String line = "4 44" ;
			Pattern first = Pattern.compile("([0-9]+)[ ]+([0-9]+)");
			Matcher m = first.matcher(line);
			m.find();
			System.err.println(m.group(1));
	     	int size = Integer.parseInt(m.group(1));
			System.out.println(size);*/
			System.out.println(t.size());
			System.out.println(t.getNeighbors(1));
			int[] distances = new int[500];
			System.out.println(t.runBellmanFord(0, distances));
			System.out.println("test");
		/*	for(int i=0;i<5;i++) {
				System.out.println(distances[i]);
			}*/
		//	t.runDijkstra(0, distances);
			System.out.println("test");
			for(int i=0;i<500;i++) {
				System.out.println(distances[i]);
			}
	}

}
