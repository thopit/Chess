package chess.graph;

import java.util.LinkedList;
import java.util.List;

public class Node<T> {
	Node<T> parent;
	List<Node<T>> children;
	
	
	public Node(Node<T> parent) {
		this.parent = parent;
		children = new LinkedList<Node<T>>();
	}
	
	public void addChild(Node<T> child) {
		children.add(child);
	}
	
	public boolean returnChild(Node<T> child) {
		return children.remove(child);
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

}
