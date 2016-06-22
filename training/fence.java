import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

public class fence {
	
	public static fence helper = new fence();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numEdges = Integer.parseInt(inputData.nextToken());
		
		Vertex[] vertices = new Vertex[501];
		
		for(int currentVertex = 1; currentVertex <= 500; currentVertex++){
			vertices[currentVertex] = helper. new Vertex(currentVertex);
		}
		
		for(int currentEdge = 0; currentEdge < numEdges; currentEdge++){
			inputData = new StringTokenizer(reader.readLine());
			int node1 = Integer.parseInt(inputData.nextToken());
			int node2 = Integer.parseInt(inputData.nextToken());
			
			vertices[node1].neighbors.add(vertices[node2]);
			vertices[node2].neighbors.add(vertices[node1]);
		}
		reader.close();
		
		for(int currentVertex = 1; currentVertex <= 500; currentVertex++){
			Collections.sort(vertices[currentVertex].neighbors);
		}
		
		
		LinkedList<Vertex> stack = new LinkedList<Vertex>();
		
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		
		Vertex vertexToAdd = null;

		for(int currentVertex = 1; currentVertex <= 500; currentVertex++){
			if((vertices[currentVertex].neighbors.size() % 2) != 0){
				vertexToAdd = vertices[currentVertex];
				break;
			}
		}
		
		if(vertexToAdd == null){
			for(int currentVertex = 1; currentVertex <= 500; currentVertex++){
				if(!vertices[currentVertex].neighbors.isEmpty()){
					vertexToAdd = vertices[currentVertex];
					break;
				}
				
			}
		}
		
		stack.add(vertexToAdd);
		
		while(!stack.isEmpty()){
			Vertex currentVertex = stack.removeLast();
			if(currentVertex.neighbors.isEmpty()){
				path.add(currentVertex);
			}
			else{
				stack.addLast(currentVertex);
				Vertex neighbor = currentVertex.neighbors.getFirst();
				neighbor.neighbors.remove(currentVertex);
				currentVertex.neighbors.removeFirst();
				stack.addLast(neighbor);
			}
			
		}
		
		
		while(!path.isEmpty()){
			System.out.println(path.removeLast().number);
		}
		
		
		
	}
	
	class Vertex implements Comparable<Vertex>{
		int number;
		public Vertex(int number){
			this.number = number;
		}
		
		LinkedList<Vertex> neighbors = new LinkedList<Vertex>();
		
		public int compareTo(Vertex other){
			if(number > other.number){
				return 1;
			}
			if(number < other.number){
				return -1;
			}
			return 0;
		}
		
	}

}
