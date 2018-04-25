
public class ParserError extends Exception 
{
	private Token return_token;
	public ParserError() { super(); }
	
	public ParserError(String message) { super( message ); }

	public ParserError(Token return_token) 
	{
		super("PARSER_ERROR!!! token.value=\"" + return_token.getValue()+"\" token.desc="+return_token.getDesc() ); 
	}
	
	public Token getToken() 
	{
		return this.return_token;
	}
}
