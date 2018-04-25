import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.LinkOption;
import java.nio.file.StandardOpenOption;

import java.io.IOException;

public class FileUtils {

	public static void scanFile_v2_old(String inFileName, String outFileName) {
		Scanner scanner = new Scanner();
		Token new_token = null;
		try {
			//if( Files.isReadable(inFileName)==false ) throws new IOException();
			BufferedReader buffer = new BufferedReader( new InputStreamReader( new FileInputStream(inFileName), Charset.forName("UTF-8") ) );

			if( Files.notExists(Paths.get(outFileName), LinkOption.NOFOLLOW_LINKS) ) {
				Files.createFile(Paths.get(outFileName));
			}
			Files.write(Paths.get(outFileName), "".getBytes()); //nadpisuje plik - to samo co:
			//Files.write(Paths.get(outFileName), "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );
			int c = 0;
	        while((c = buffer.read()) != -1) {
	            char character = (char) c;
	            System.out.println("character read: \""+character+"\"");

	            new_token = scanner.generateToken_v2_old(character);

	            if(new_token!=null) {
	            	System.out.println("\ttoken found: \""+new_token.getValue()+"\"");
	            	Files.write(Paths.get(outFileName), ("<span style=\"background-color: "+new_token.getColor()+"\">"+new_token.getValue()+"</span> ").getBytes(), StandardOpenOption.APPEND);
	            	//<span style="background-color: #FFFF00">Yellow text.</span>
	            	//"<div style=\"background-color:"+new_token.getColor()+"\">"+new_token.getSign()+"</div>"
	            	//"<span style=\"background-color: "+new_token.getColor()+"\">"+new_token.getSign()+"</span>"
	            }

	            if(character==';') {
	            	Files.write(Paths.get(outFileName), ("</br>").getBytes(), StandardOpenOption.APPEND);
	            }
	        }
		}
		catch( IOException e ) {
			e.getMessage();
			System.out.println(e.getMessage());
		}
	}
	
	public static void scanFile(String inFileName, String outFileName) throws Exception 
	{
		Scanner scanner = new Scanner();
		Token new_token = null;
		try 
		{
			//if( Files.isReadable(inFileName)==false ) throws new IOException();
			BufferedReader buffer = new BufferedReader( new InputStreamReader( new FileInputStream(inFileName), Charset.forName("UTF-8") ) );

			if( Files.notExists(Paths.get(outFileName), LinkOption.NOFOLLOW_LINKS) ) 
			{
				Files.createFile(Paths.get(outFileName));
			}
			Files.write(Paths.get(outFileName), "".getBytes()); //nadpisuje plik - to samo co:
			//Files.write(Paths.get(outFileName), "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );
			
			int c = 0;
        	while( (c = buffer.read()) != -1 )
	        {
        		CheckReadCharacterCode( c );
        			
        		//omijanie znakow bialych, nieznajdywanych w normalnych plikach
        		while( (c>-1 && c<10) || (c>10 && c < 32) || c > 126  ) c = buffer.read();
        		if( c==-1 || c==32 || c==10 ) c = (char) 0;
	            
        		char character = (char) c;
	            System.out.print("character read: \'"+character+"\' = "+c+" | ");
	            new_token = scanner.generateToken(character);

	            if(new_token!=null) 
	            {
	            	System.out.println("\ttoken found: \""+new_token.getValue()+"\"");
	            	Files.write(Paths.get(outFileName), ("<span style=\"background-color: "+new_token.getColor()+"\">"+new_token.getValue()+"</span> </br>").getBytes(), StandardOpenOption.APPEND);
	            }
	            
	        }
        	if( c==-1 )
        	{
        		c = (char) 0;
        		
        		char character = (char) c;
        		System.out.print("character read: \'"+character+"\' = "+c+" | ");

	            new_token = scanner.generateToken(character);
	            
	            if(new_token!=null) 
	            {
	            	System.out.println("\ttoken found: \""+new_token.getValue()+"\"");
	            	Files.write(Paths.get(outFileName), ("<span style=\"background-color: "+new_token.getColor()+"\">"+new_token.getValue()+"</span> ").getBytes(), StandardOpenOption.APPEND);
	            }
        	}
		}
		catch( IOException e ) 
		{
			e.getMessage();
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean CheckReadCharacterCode( int character ) throws CheckReadCharacterCodeException
	{
		boolean response = false;
		
		if( (character>-1 && character<10) || (character>10 && character<32) || character>126  )
		{
			throw new CheckReadCharacterCodeException( ( (char) character ) );
		}
		else response = true;
		
		return response;
	}
}
