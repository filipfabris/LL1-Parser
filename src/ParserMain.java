
import java.io.IOException;
import java.io.PrintStream;

public class ParserMain {

	public static void main(String[] args) throws IOException {

		//For testing
		Parser parser = new Parser("input.txt");
		
		//PrintStream out = new PrintStream(System.out, true, "UTF-8");
		//out.print(parser.mainNode.toString());

		System.out.print(parser.mainNode.toString());
		
	}

}
