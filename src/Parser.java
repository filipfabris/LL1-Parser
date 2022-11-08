import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Parser {

    public static class Entry {
        TokenType fromInput;
        String onStack;

        Entry(String onStack, TokenType fromInput) {
            this.fromInput = fromInput;
            this.onStack = onStack;
        }

        @Override
        public int hashCode() {
            return Objects.hash( fromInput, onStack );
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Entry other = (Entry) obj;
            return fromInput == other.fromInput && Objects.equals( onStack, other.onStack );
        }

        @Override
        public String toString() {
            return "fromInput: " + fromInput + ", onStack: " + onStack;
        }
    }

    String input;
    Stack<String> stack;
    LinkedList<Node> queue;
    Node mainNode;
    Node currentNode;

    private static final HashMap<Entry, String[]> tablica;
    private static final LinkedList<String> nonTerminas;
    private static final LinkedList<String> terminals;

    Token currentToken;
    Entry entry;

    FileReader fileReader;
    BufferedReader reader;

    static {
        tablica = new HashMap<>();
        //program
        tablica.put( new Entry( "program", TokenType.IDN ), new String[]{"lista_naredbi"} );
        tablica.put( new Entry( "program", TokenType.KR_ZA ), new String[]{"lista_naredbi"} );

        //lista_naredbi
        tablica.put( new Entry( "lista_naredbi", TokenType.IDN ), new String[]{"naredba", "lista_naredbi"} );
        tablica.put( new Entry( "lista_naredbi", TokenType.KR_ZA ), new String[]{"naredba", "lista_naredbi"} );
        tablica.put( new Entry( "lista_naredbi", TokenType.KR_AZ ), new String[]{} );
        tablica.put( new Entry( "lista_naredbi", TokenType.EOF ), new String[]{} );

        //naredba
        tablica.put( new Entry( "naredba", TokenType.IDN ), new String[]{"naredba_pridruzivanja"} );
        tablica.put( new Entry( "naredba", TokenType.KR_ZA ), new String[]{"za_petlja"} );

        //naredba_pridruzivanja
        tablica.put( new Entry( "naredba_pridruzivanja", TokenType.IDN ), new String[]{"IDN", "OP_PRIDRUZI", "E"} );

        //za_petlja
        tablica.put( new Entry( "za_petlja", TokenType.KR_ZA ), new String[]{"KR_ZA", "IDN", "KR_OD", "E", "KR_DO", "E", "lista_naredbi", "KR_AZ"} );

        //E
        tablica.put( new Entry( "E", TokenType.IDN ), new String[]{"T", "E_lista"} );
        tablica.put( new Entry( "E", TokenType.BROJ ), new String[]{"T", "E_lista"} );
        tablica.put( new Entry( "E", TokenType.OP_PLUS ), new String[]{"T", "E_lista"} );
        tablica.put( new Entry( "E", TokenType.OP_MINUS ), new String[]{"T", "E_lista"} );
        tablica.put( new Entry( "E", TokenType.L_ZAGRADA ), new String[]{"T", "E_lista"} );

        //E_lista
        tablica.put( new Entry( "E_lista", TokenType.IDN ), new String[]{} );
        tablica.put( new Entry( "E_lista", TokenType.KR_ZA ), new String[]{} );
        tablica.put( new Entry( "E_lista", TokenType.KR_DO ), new String[]{} );
        tablica.put( new Entry( "E_lista", TokenType.KR_AZ ), new String[]{} );
        tablica.put( new Entry( "E_lista", TokenType.D_ZAGRADA ), new String[]{} );
        tablica.put( new Entry( "E_lista", TokenType.EOF ), new String[]{} );
        tablica.put( new Entry( "E_lista", TokenType.OP_PLUS ), new String[]{"OP_PLUS", "E"} );
        tablica.put( new Entry( "E_lista", TokenType.OP_MINUS ), new String[]{"OP_MINUS", "E"} );

        //T
        tablica.put( new Entry( "T", TokenType.IDN ), new String[]{"P", "T_lista"} );
        tablica.put( new Entry( "T", TokenType.BROJ ), new String[]{"P", "T_lista"} );
        tablica.put( new Entry( "T", TokenType.OP_PLUS ), new String[]{"P", "T_lista"} );
        tablica.put( new Entry( "T", TokenType.OP_MINUS ), new String[]{"P", "T_lista"} );
        tablica.put( new Entry( "T", TokenType.L_ZAGRADA ), new String[]{"P", "T_lista"} );

        //T_Lista
        tablica.put( new Entry( "T_lista", TokenType.IDN ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.KR_ZA ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.KR_DO ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.KR_AZ ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.OP_PLUS ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.OP_MINUS ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.D_ZAGRADA ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.EOF ), new String[]{} );
        tablica.put( new Entry( "T_lista", TokenType.OP_PUTA ), new String[]{"OP_PUTA", "T"} );
        tablica.put( new Entry( "T_lista", TokenType.OP_DIJELI ), new String[]{"OP_DIJELI", "T"} );

        //P
        tablica.put( new Entry( "P", TokenType.OP_PLUS ), new String[]{"OP_PLUS", "P"} );
        tablica.put( new Entry( "P", TokenType.OP_MINUS ), new String[]{"OP_MINUS", "P"} );
        tablica.put( new Entry( "P", TokenType.L_ZAGRADA ), new String[]{"L_ZAGRADA", "E", "D_ZAGRADA"} );
        tablica.put( new Entry( "P", TokenType.IDN ), new String[]{"IDN"} );
        tablica.put( new Entry( "P", TokenType.BROJ ), new String[]{"BROJ"} );


        nonTerminas = new LinkedList<>();
        nonTerminas.addAll( Arrays.asList( "program", "lista_naredbi", "naredba", "naredba_pridruzivanja",
                "za_petlja", "E", "T", "E_lista", "T_lista", "P" ) );

        terminals = new LinkedList<>();
        terminals.addAll( Arrays.asList( "IDN", "BROJ", "KR_DO", "KR_OD", "KR_ZA", "KR_AZ", "(", ")", "OP_PLUS",
                "OP_MINUS", "OP_PUTA", "OP_DIJELI", "EOF", "OP_PRIDRUZI", "L_ZAGRADA", "D_ZAGRADA" ) );

    }

    public Parser(String input) throws IOException {
        this.input = input;
        this.stack = new Stack<>();
        this.queue = new LinkedList<>();

        this.mainNode = new Node( "program" );
        this.currentNode = mainNode;

        //For testing
        this.fileReader = new FileReader( "input.txt" );

        this.reader = new BufferedReader( new InputStreamReader( System.in ) );
        this.currentToken = null;

        stack.push( "EOF" );
        stack.push( "program" );

        queue.addFirst( new Node( "KRAJ" ) );
        queue.addFirst( currentNode );
        this.parse();
    }

    //token input inside currentToken
    //stack top inside stackTop variable
    private void parse() throws IOException {

        nextToken();
        String stackTop = null;

        do {
            stackTop = stack.pop();
            currentNode = queue.removeFirst();

            if (nonTerminas.contains( stackTop )) {
                entry = new Entry( stackTop, currentToken.tokenType );
                String[] rule = tablica.get( entry );

                if (rule == null) {

					//throw new RuntimeException("Nedozvoljeno stanje gramatike");
					//////
					errorDetected();
                    break;
					/////
                } else {
                    this.pushStack( rule );
                }

            } else if (terminals.contains( stackTop )) {
                if (stackTop.equals( currentToken.tokenType.toString() ) == false) {
                    //throw new RuntimeException( "Vrh stoga se ne poklapa sa ulazom" );
					//////
					errorDetected();
                    break;
					/////
                }
                currentNode.name = currentToken.toString();
                nextToken();
            } else {
                //throw new RuntimeException( "Nepoznati ulaz" );
				//////
				errorDetected();
                break;
				/////
            }

            if (isTokenOfType( TokenType.EOF ) && stackTop.equals( "EOF" )) {
                break;
            }

        }
        while (true);

        if (isTokenOfType( TokenType.EOF )) {
//			System.out.println("Input is Accepted by LL1");
        } else {
//			System.out.println("Input is not Accepted by LL1");
        }
    }


    private void nextToken() throws IOException {
        String tmp = reader.readLine();
        if (tmp == null) {
            currentToken = new Token( tmp );
        }
        currentToken = new Token( tmp );
    }

    private boolean isTokenOfType(TokenType type) {
        return currentToken.tokenType.equals( type );
    }

    private void pushStack(String[] rule) {

        for (int i = rule.length - 1; i >= 0; i--) {
            Node addNode = new Node( rule[i] );
            queue.addFirst( addNode );
            currentNode.addChildNode( addNode );

            stack.push( rule[i] );
        }

        if (rule.length == 0) {
            Node addNode = new Node( "$" );
            currentNode.addChildNode( addNode );
        }
    }

    private void errorDetected() {

        Node addNode;

        if (currentToken.tokenType.equals( TokenType.EOF )) {
            addNode = new Node( "err kraj" );
        } else {
            addNode = new Node( "err " + currentToken );
        }

        mainNode = addNode;
    }


}
