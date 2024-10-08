package com.example.demo;

import java.util.*;

public class Dijkstra {

	static class Graph {
		private final Map<Integer, List<Edge>> adjList = new HashMap<>();

		public void addEdge(int source, int destination, int weight) {
			adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(destination, weight));
			adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Edge(source, weight)); // If undirected
		}

		public void dijkstra(int start) {
			PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
			Map<Integer, Integer> distances = new HashMap<>();
			Set<Integer> visited = new HashSet<>();

			// Initialize distances
			for (int node : adjList.keySet()) {
				distances.put(node, Integer.MAX_VALUE);
			}
			distances.put(start, 0);
			pq.add(new Edge(start, 0));

			while (!pq.isEmpty()) {
				Edge current = pq.poll();
				int currentNode = current.destination;

				// Skip visited nodes
				if (visited.contains(currentNode)) {
					continue;
				}
				visited.add(currentNode);

				// Explore neighbors
				for (Edge edge : adjList.getOrDefault(currentNode, new ArrayList<>())) {
					if (!visited.contains(edge.destination)) {
						int newDist = distances.get(currentNode) + edge.weight;
						if (newDist < distances.get(edge.destination)) {
							distances.put(edge.destination, newDist);
							pq.add(new Edge(edge.destination, newDist));
						}
					}
				}
			}

			// Print shortest paths
			for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
				System.out.println("Distance from " + start + " to " + entry.getKey() + " is " + entry.getValue());
			}
		}

		static class Edge {
			int destination;
			int weight;

			Edge(int destination, int weight) {
				this.destination = destination;
				this.weight = weight;
			}
		}
	}

	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.addEdge(1, 2, 1);
		graph.addEdge(1, 3, 4);
		graph.addEdge(2, 3, 2);
		graph.addEdge(2, 4, 5);
		graph.addEdge(3, 4, 1);

		graph.dijkstra(1); // Start from node 1
	}
}
