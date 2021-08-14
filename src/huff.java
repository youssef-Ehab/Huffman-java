import java.util.*;


class comp2 implements Comparator<huff> {
    @Override
    public int compare(huff n1, huff n2) {
        return n1.compareTo(n2);
    }

}

class huff {
    private String symbol;
    private int freq;
    private HashMap<String, String> Path = new HashMap<>();  //symbol,path
    private HashMap<String, String> RevPath = new HashMap<>();  //symbol,path
    private HashMap<String, Integer> values = new HashMap<String, Integer>();  //symbols and their frequencies
    private Vector<String> encoded = new Vector<>();
    private Vector<Character> other = new Vector<>();


    huff(String s, int f) {
        symbol = s;
        freq = f;
    }

    huff() {

    }

    int compareTo(huff t1) {
        if (symbol == t1.symbol && freq == t1.freq) return 0;
        if (freq > t1.freq) return -1;
        if (t1.freq > freq) return 1;
        return 1;
    }

    public void updateVec(Vector<huff> vec) {
        Vector<huff> temp = new Vector<>();
        for (int i = 0; i < vec.size(); i++) {
            temp.add(vec.elementAt(i));
        }
        vec = temp;
    }

    public void SetPath(Vector<huff> vec) {
        while (vec.size() > 2) {
            huff n = new huff();
            huff n2 = new huff();
            huff temp = new huff();
            n = vec.lastElement();
            n2 = vec.elementAt(vec.size() - 2);
            vec.remove(vec.lastElement());
            vec.remove(vec.elementAt(vec.size() - 1));
            temp.freq = n.freq + n2.freq;
            temp.symbol = n.symbol + "+" + n2.symbol;
            vec.add(temp);
            Collections.sort(vec, new comp2());
        }


    }

    public void encode(Vector<huff> vec) {
        huff n1 = vec.elementAt(0);
        huff n2 = vec.elementAt(1);
        String higher = "1";
        String lower = "0";
        while (true) {
            Vector<huff> temp1 = new Vector<>();
            Vector<huff> temp2 = new Vector<>();
            if (n1.symbol.length() > 1) {
                huff huff1 = new huff();
                huff1.symbol = n1.symbol.substring(n1.symbol.length() - 1, n1.symbol.length());

                n1.symbol = n1.symbol.substring(0, n1.symbol.length() - 2);
                n1.freq = n1.freq - values.get(huff1.symbol);
                huff1.freq = values.get(huff1.symbol);
                if (n1.freq > huff1.freq) {
                    Path.put(huff1.symbol, higher + "0");
                    RevPath.put(higher + "0", huff1.symbol);
                    encoded.add(higher + "0");

                    higher += "1";
                } else if (n1.freq < huff1.freq) {
                    Path.put(huff1.symbol, higher + "1");
                    RevPath.put(higher + "1", huff1.symbol);
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
                huff huff2 = new huff();
                huff2.symbol = n2.symbol.substring(n2.symbol.length() - 1, n2.symbol.length());
                n2.symbol = n2.symbol.substring(0, n2.symbol.length() - 2);
                n2.freq = n2.freq - values.get(huff2.symbol);
                huff2.freq = values.get(huff2.symbol);
                if (n2.freq >= huff2.freq) {
                    Path.put(huff2.symbol, lower + "0");
                    RevPath.put(lower + "0", huff2.symbol);
                    encoded.add(lower + "0");
                    lower += "1";

                } else if (n2.freq < huff2.freq) {
                    Path.put(huff2.symbol, lower + "1");
                    encoded.add(lower + "1");
                    RevPath.put(lower + "1", huff2.symbol);
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
        huff n = new huff();
        Scanner scanner = new Scanner(System.in);
        Vector<huff> Data = new Vector<huff>();
        System.out.println("Enter the Data you want to add and finish with -1");
        while (true) {
            System.out.println("Enter the Freq and the Character");
            f = scanner.nextInt();
            if (f == -1) {
                break;
            }
            s = scanner.nextLine();
            Data.add(new huff(s.substring(1, 2), f));
            n.values.put(s.substring(1, 2), f);
        }
        for (int i = 0; i < Data.size(); i++) {
            huff huff = Data.elementAt(i);
            //    System.out.println(huff.symbol + " " + huff.freq);
        }

        Collections.sort(Data, new comp2());
        /*for (int i = 0; i < Data.size(); i++) {
            huff huff = Data.elementAt(i);
            System.out.println(huff.symbol + " " + huff.freq);
        }*/
        n.SetPath(Data);
        n.encode(Data);
        System.out.println(n.Path);
        System.out.println(n.encoded);
        n.decode(n.encoded);
    }
}





