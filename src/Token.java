

public class Token {
	
	public TokenType tokenType;
	public int redak;
	public String value;
	
	public Token(String input){
		if(input == null) {
			this.tokenType = TokenType.EOF;
			return;
		}
		
		String[] polje = input.split(" ");
		
		this.tokenType = TokenType.valueOf(polje[0]);
		this.redak = Integer.parseInt(polje[1]);
		this.value = polje[2];
	}
	
	public Token(TokenType tokenType){
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return tokenType + " " + redak + " " + value;
	}
	
	
	
}
