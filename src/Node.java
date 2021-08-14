import java.util.*;

class comp implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return n1.compareTo(n2);
    }

}

class Node {
    private String symbol;
    private int freq;
    private HashMap<String, String> Path = new HashMap<>();  //symbol,path
    private HashMap<String, String> RevPath = new HashMap<>();  //symbol,path for the decode
    private HashMap<String, Integer> values = new HashMap<String, Integer>();  //symbols and their frequencies
    private Vector<String> encoded = new Vector<>();

    Node(String s, int f) {
        symbol = s;
        freq = f;
    }

    Node() {

    }

    int compareTo(Node t1) {
        if (symbol == t1.symbol && freq == t1.freq) return 0;
        if (freq > t1.freq) return -1;
        if (t1.freq > freq) return 1;
        return 1;
    }

    public void SetPath(Vector<Node> vec) {
        while (vec.size() > 2) {
            Node n = new Node();
            Node n2 = new Node();
            Node temp = new Node();
            n = vec.lastElement();
            n2 = vec.elementAt(vec.size() - 2);
            vec.remove(vec.lastElement());
            vec.remove(vec.lastElement());
            //        vec.remove(vec.elementAt(vec.size() - 1));
            temp.freq = n.freq + n2.freq;
            temp.symbol = n.symbol + "+" + n2.symbol;
            vec.add(temp);
            Collections.sort(vec, new comp());
        }


    }

    public void encode(Vector<Node> vec) {
        Node n1 = vec.elementAt(0);
        Node n2 = vec.elementAt(1);
        String higher = "1";
        String lower = "0";
        while (true) {
            //   Vector<Node> temp1 = new Vector<>();
            //  Vector<Node> temp2 = new Vector<>();
            if (n1.symbol.length() > 1) {
                Node node1 = new Node();
                node1.symbol = n1.symbol.substring(n1.symbol.length() - 1, n1.symbol.length());
                n1.symbol = n1.symbol.substring(0, n1.symbol.length() - 2);
                n1.freq = n1.freq - values.get(node1.symbol);
                node1.freq = values.get(node1.symbol);
                if (n1.freq > node1.freq) {
                    Path.put(node1.symbol, higher + "0");
                    RevPath.put(higher + "0", node1.symbol);
                    encoded.add(higher + "0");
                    higher += "1";
                } else if (n1.freq < node1.freq) {
                    Path.put(node1.symbol, higher + "1");
                    RevPath.put(higher + "1", node1.symbol);
                    encoded.add(higher + "1");
                    higher += "0";
                }
                if (n1.symbol.length() == 1) {
                    Path.put(n1.symbol, higher);
                    RevPath.put(higher, n1.symbol);
                    encoded.add(higher);
                }
            }
            if (n2.symbol.length() > 1) {
                Node node2 = new Node();
                node2.symbol = n2.symbol.substring(n2.symbol.length() - 1, n2.symbol.length());
                n2.symbol = n2.symbol.substring(0, n2.symbol.length() - 2);
                n2.freq = n2.freq - values.get(node2.symbol);
                node2.freq = values.get(node2.symbol);
                if (n2.freq >= node2.freq) {
                    Path.put(node2.symbol, lower + "0");
                    RevPath.put(lower + "0", node2.symbol);
                    encoded.add(lower + "0");
                    lower += "1";

                } else if (n2.freq < node2.freq) {
                    Path.put(node2.symbol, lower + "1");
                    encoded.add(lower + "1");
                    RevPath.put(lower + "1", node2.symbol);
                    lower += "0";

                }
                if (n2.symbol.length() == 1) {
                    Path.put(n2.symbol, lower);
                    RevPath.put(lower, n2.symbol);
                    encoded.add(lower);
                }
            }
            if (n1.symbol.length() <= 1 && n2.symbol.length() <= 1) {
                break;
            }
        }
    }

    public void decode(Vector<String> vec) {
        Vector<String> symbols = new Vector<>();
        for (int i = 0; i < vec.size(); i++) {
            symbols.add(RevPath.get(vec.elementAt(i)));
        }
        System.out.println(symbols);
    }

    public static void main(String[] args) {
        int f;
        String s;
        Node n = new Node();
        Scanner scanner = new Scanner(System.in);
        Vector<Node> Data = new Vector<Node>();
        System.out.println("Enter the Data you want to add and finish with -1");
        while (true) {
            System.out.println("Enter the Freq and the Character");
            f = scanner.nextInt();
            if (f == -1) {
                break;
            }
            s = scanner.nextLine();
            Data.add(new Node(s.substring(1, 2), f));
            n.values.put(s.substring(1, 2), f);
        }
        for (int i = 0; i < Data.size(); i++) {
            Node node = Data.elementAt(i);
            //    System.out.println(node.symbol + " " + node.freq);
        }

        Collections.sort(Data, new comp());
        /*for (int i = 0; i < Data.size(); i++) {
            Node node = Data.elementAt(i);
            System.out.println(node.symbol + " " + node.freq);
        }*/
        n.SetPath(Data);
        n.encode(Data);
        System.out.println(n.Path);
        System.out.println(n.encoded);
        n.decode(n.encoded);
    }
}



