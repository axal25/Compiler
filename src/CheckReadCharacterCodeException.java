
public class CheckReadCharacterCodeException extends Exception 
{
	private char character = '\0';
	
	public CheckReadCharacterCodeException() { super(); }
	
	public CheckReadCharacterCodeException(String message) { super( message ); }
	
	public CheckReadCharacterCodeException(char character)
	{
		super("CheckReadCharacterCodeException!!! char_code=" + ( (int) character) + "char=" + character);
		this.character = character;
	}
	
	public char getCharacter()
	{
		return this.character;
	}

}
