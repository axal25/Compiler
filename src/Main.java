import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main 
{

	public static void main(String[] args) throws Exception 
	{
		
		PrintStream out = new PrintStream( new FileOutputStream("console_output.txt") );
		System.setOut(out);
		
		System.out.println("START\n");

		Scanner scanner = new Scanner();
		
		scanner.printTable();
		System.out.println();
		
		//scanner.printScanner();
		//System.out.println("");

//		System.out.println("Examples from String:\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//		Token token1 = scanner.generateToken("< ");
//		System.out.println("generated Token: " + token1);
//		System.out.println("generated Token's sign: " + token1.getSign());
//		System.out.println("generated Token's description: " + token1.getDesc());
//		System.out.println("generated Token's color: " + token1.getColor()+"\n");

//		Token token2 = scanner.generateToken("< + ");
//		System.out.println("generated Token: " + token2);
//		System.out.println("generated Token's sign: " + token2.getSign());
//		System.out.println("generated Token's description: " + token2.getDesc());
//		System.out.println("generated Token's color: " + token2.getColor()+"\n");
		
		String input_file_name = "example.txt";
		String output_file_name = "example.html";
		try 
		{
			System.out.println("\nExamples from File #1 -"+input_file_name+"-:\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			FileUtils.scanFile(input_file_name, output_file_name);
		}
		catch( ParserError pe )
		{
			System.out.println( "\n\n"+pe.getMessage()+"\n\n" );
			try 
			{
			    Files.write( Paths.get( output_file_name ), ("\n\n"+pe.getMessage()+"\n\n").getBytes(), StandardOpenOption.APPEND );
			}
			catch (IOException ioe) 
			{
			    System.out.println("Couldn't append file to report ParserException");
			}
		}
		catch( CheckReadCharacterCodeException crcce )
		{
			System.out.println( "\n\n"+crcce.getMessage()+"\n\n" );
			try 
			{
			    Files.write( Paths.get( output_file_name ), ("\n\n"+crcce.getMessage()+"\n\n").getBytes(), StandardOpenOption.APPEND );
			}
			catch (IOException ioe) 
			{
			    System.out.println("Couldn't append file to report CheckReadCharacterCodeException");
			}
		}
        
        input_file_name = "example2.txt";
		output_file_name = "example2.html";
		try 
		{
			System.out.println("\nExamples from File #2 -"+input_file_name+"-:\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			FileUtils.scanFile(input_file_name, output_file_name);
		}
		catch( ParserError pe )
		{
			System.out.println( "\n\n"+pe.getMessage()+"\n\n" );
			try 
			{
			    Files.write( Paths.get( output_file_name ), ("\n\n"+pe.getMessage()+"\n\n").getBytes(), StandardOpenOption.APPEND );
			}
			catch (IOException ioe) 
			{
			    System.out.println("Couldn't append file to report ParserException");
			}
		}
		catch( CheckReadCharacterCodeException crcce )
		{
			System.out.println( "\n\n"+crcce.getMessage()+"\n\n" );
			try 
			{
			    Files.write( Paths.get( output_file_name ), ("\n\n"+crcce.getMessage()+"\n\n").getBytes(), StandardOpenOption.APPEND );
			}
			catch (IOException ioe) 
			{
			    System.out.println("Couldn't append file to report CheckReadCharacterCodeException");
			}
		}

//        System.out.println("\nExamples from File #3:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        FileUtils.scanFile("example3.txt", "example3.html");
//        
//        System.out.println("\nExamples from File #4:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        FileUtils.scanFile("example4.txt", "example4.html");
//        
//        System.out.println("\nExamples from File #5:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Sa dwie \"jakby nazwy funkcji\", a tabela powoduje rozbicie na duzo wieksza ilosc tokenow");
//        FileUtils.scanFile("example5.txt", "example5.html");
//        
//        System.out.println("\nExamples from File #6:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Akceptuje sama kropke '.', a nie powinno\nAkceptuje liczbe \"doublowa\" konczaca sie na '.', a powinno wymagac liczby po kropce");
//        FileUtils.scanFile("example6.txt", "example6.html");
//        
//        System.out.println("\nExamples from File #7:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Ta sama historia co w 6. Mozliwe, ze tez akceptuje zaczynajace sie na '.', a nie powinno. Nie sprawdzone.");
//        FileUtils.scanFile("example7.txt", "example7.html");
//        
//        System.out.println("\nExamples from File #8:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("teraz spacja i newline(10) jest obslugiwany jako rozdzielenie tokenow.");
//        FileUtils.scanFile("example8.txt", "example8.html");
//        
//        System.out.println("\nExamples from File #9:\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Nie akceptuje pojedynczej duzej litery.");
//        FileUtils.scanFile("example9.txt", "example9.html");
        
        input_file_name = "example10.txt";
		output_file_name = "example10.html";
		try 
		{
			System.out.println("\nExamples from File #10 -"+input_file_name+"-:\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			FileUtils.scanFile(input_file_name, output_file_name);
		}
		catch( ParserError pe )
		{
			System.out.println( "\n\n"+pe.getMessage()+"\n\n" );
			try 
			{
			    Files.write( Paths.get( output_file_name ), ("\n\n"+pe.getMessage()+"\n\n").getBytes(), StandardOpenOption.APPEND );
			}
			catch (IOException ioe) 
			{
			    System.out.println("Couldn't append file to report ParserException");
			}
		}
		catch( CheckReadCharacterCodeException crcce )
		{
			System.out.println( "\n\n"+crcce.getMessage()+"\n\n" );
			try 
			{
			    Files.write( Paths.get( output_file_name ), ("\n\n"+crcce.getMessage()+"\n\n").getBytes(), StandardOpenOption.APPEND );
			}
			catch (IOException ioe) 
			{
			    System.out.println("Couldn't append file to report CheckReadCharacterCodeException");
			}
		}

		System.out.println("DONE!");
	}

}
