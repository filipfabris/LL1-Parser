import java.util.*;
public class Node {
	
	private static List<String> nonTerminas;
	
	static {
		nonTerminas = new LinkedList<>();
		nonTerminas.addAll(Arrays.asList("program", "lista_naredbi", "naredba", "naredba_pridruzivanja",
				"za_petlja", "E", "T", "E_lista", "T_lista", "P"));	}

	ArrayList<Node> collection = new ArrayList<>();
	int numberOfChildren;
	public String name;

	public Node() {

	}

	public Node(String name) {
		this.name = name;
	}

	public void addChildNode(Node child) {

		collection.add(0, child);
		numberOfChildren++;
	}

	public int numberOfChildren() {
		return numberOfChildren;
	}

	public Node getChild(int index) {
		return collection.get(index);
	}
	
	public String getName() {
		return this.name;
	}

	
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");

        return buffer.toString();
    }

//    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
//        buffer.append(prefix);
//        if(nonTerminas.contains(this.name)) {
//            buffer.append(("<" + name + ">"));
//        }else {        	
//        	buffer.append((name));
//        }
//        buffer.append('\n');
//        for (Iterator<Node> it = collection.iterator(); it.hasNext();) {
//            Node next = it.next();
//            if (it.hasNext()) {
//                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
//            } else {
//                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
//            }
//        }
//    }
    
    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        if(nonTerminas.contains(this.name)) {
            buffer.append(("<" + name + ">"));
        }else {        	
        	buffer.append((name));
        }
        buffer.append('\n');
        for (Iterator<Node> it = collection.iterator(); it.hasNext();) {
            Node next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + " ", childrenPrefix + " ");
            } else {
                next.print(buffer, childrenPrefix + " ", childrenPrefix + " ");
            }
        }
    }
	
	

}
