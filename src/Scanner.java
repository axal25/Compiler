import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Scanner {
    private List<Token> tokenList;
    private String buffer;
    private int current_state;
    private String map_table[][];

    public Scanner() {
    	current_state = 1;
    	buffer = null;
        initializeTable();
	}
	
	public void printScanner() {
		int i = 0;
		System.out.println("Scanner:");
		System.out.println("\tTokenList:");
        for( Token token : tokenList ) {
        	i++;
        	System.out.format("%3d. | %40s | %45s | %7s\n", i, token.getValue(), token.getDesc(), token.getColor());
        }
        System.out.println("\tBuffer: "+buffer);
	}
	
	public Token generateToken(String file) {
		int i = 0;
		Token return_token = null;
		while(i < file.length() ) {
			return_token = generateToken_v2_old( file.charAt(i) );
			if( return_token != null ) return return_token;
			i++;
		}
		return null;
	}
	
	public Token generateToken_v2_old(char character) { //return String Color
		Token return_token = null;
		String finishedToken = "";
		//if( isBothThisAndNextStateFinal() )
		if( character==' ' || character=='\n' || character==';' ) { //did token ended ... OR did token type changed
			if( buffer != null ) {
				finishedToken = buffer;
				buffer = null;
				
				//searchForMatchingToken
				for( Token token : tokenList ) {
					if( checkTokenMatching(token, finishedToken) ) {
						return_token = new Token();
						return_token.setValue( finishedToken );
						return_token.setDesc( token.getDesc() );
						return_token.setColor( token.getColor() );
						break;
					}
				}
			}
		}
		else {
			appendBuffer( character );
		}
		
		return return_token;
	}
	
	public Token generateToken(char character) throws Exception //return String Color 
	{ 
		Token return_token = null;
		String finishedToken = "";
		
		//if( character==' ' || character=='\n' || character==';' ) //did token ended ... OR did token type changed
		//boolean end_token_flag = isThisFinalAndNextNOTFinalState(current_state, character); //true when Current_state_isFinal==true && Next_state_isFinal=false
		//if( !isThisAndNextStateFinal(current_state, character) ) //najdluzszy mozliwy token wczytany
		boolean end_token_flag = isNextStatePARSER_ERROR(current_state, character);
		if( end_token_flag==true )
		{
			if( buffer == null ) 
			{ 
				//do noting 
			}
			else
			{
				finishedToken = buffer;
				return_token = new Token();
				return_token.setValue( finishedToken );
				return_token.setDesc( map_table[current_state][128] );
				return_token.setColor( map_table[current_state][130] ); //dodanie nowej kolumny z kodem koloru
//				for( Token token : tokenList ) 
//				{
//					if( checkTokenMatching(token, finishedToken) ) 
//					{
//						return_token.setColor( token.getColor() );
//						break;
//					}
//				}
				
				buffer = null;
				current_state = 1;
				System.out.println("\t\tTRUE: reset AUTOMATA");
			}
			
			if( character!='\0' )
			{
				this.appendBuffer( character );
				System.out.println("\t\t\t\tTRUE: buffer=\""+buffer+"\"");
				System.out.print("\t\t\t\tTRUE: old_cur_state="+current_state);
				current_state = Integer.parseInt( map_table[current_state][ ((int) character) ] );
				System.out.println(" -> new_cur_state="+current_state);
			}
			else 
			{
				System.out.println("\t\t\t\tTRUE: buffer=\""+buffer+"\" - faked null");
			}
		}
		else //wczytany kolejny znak nalezacy do tokena
		{	
			if( character!='\0' )
			{
				appendBuffer( character );
				System.out.println("\t\t\t\tFALSE: buffer=\""+buffer+"\"");
				System.out.print("\t\t\t\tFALSE: old_cur_state="+current_state);
				current_state = Integer.parseInt( map_table[current_state][ ((int) character) ] );
				System.out.println(" -> new_cur_state="+current_state);
			}
		}
		
		if( return_token != null )
		{
			if( return_token.getDesc() == TokenDesc.PARSER_ERROR )
			{
				throw new ParserError( return_token );
			}
		}
			
		return return_token;
	}
	
	private boolean isNextStatePARSER_ERROR(int current_state, char character) 
	{
		boolean end_token_flag = false;
		
		if( map_table[current_state][ (int) character ].compareToIgnoreCase("0")==0 ) end_token_flag = true;
		else end_token_flag = false;
		
		return end_token_flag;
	}

	public boolean isThisFinalAndNextNOTFinalState( int current_state, char character )
	{
		boolean response = false;
		int next_state = 0;
		
		next_state = Integer.parseInt( map_table[current_state][ ((int) character) ] );
		if( map_table[current_state][129].compareToIgnoreCase("true")==0 && map_table[next_state][129].compareToIgnoreCase("false")==0 ) response = true;
		
		return response;
	}
	
	public void appendBuffer(char character) {
		if( buffer == null ) {
			buffer = ""+character;
		}
		else {
			buffer = buffer + character;
		}
	}
	
	public boolean checkTokenMatching(Token token, String buffer) {
		boolean matching = false;
		//
		if( token==null) { System.out.println("token==null"); }
		if( token.getValue()==null) { System.out.println("token.getSign()==null"); }
		if( buffer==null) { System.out.println("buffer==null"); }
		//
		if( token.getValue().compareTo( buffer )==0 ) {
			matching = true;
		}
		if( isRegEx(token.getValue()) && buffer.matches(token.getValue()) ) {
			matching = true;
		}
		
		return matching;
	}
	
	public boolean isRegEx(String tokenSign) {
		boolean isRegEx;
		try {
		  Pattern.compile(tokenSign);
		  isRegEx = true;
		} catch (PatternSyntaxException e) {
		  isRegEx = false;
		}
		return isRegEx;
	}
	
	public void printTable()
	{
		for(int j = 0; j<131; j++)
		{
			if( j==0 ) System.out.format(">%3s    |", "LP.");
			if( j>32 && j<127 )
			{
				char c = (char) j;
				String s = Character.toString( c );
				if( j<128 ) System.out.format(" %7s|", j+"=\'"+c+"\'" );
			}
			else  if( j<128 ) System.out.format(" %6s |", j+". " );
			if( j==128 ) System.out.format(" %45s |", "128.=TokenDesc");
			if( j==129 ) System.out.format("%7s|", "isFinal");
			if( j==130 ) System.out.format(" %7s |", "#color");
			if( j==130 ) System.out.format("   %3s   <", "LP.");
		}
		System.out.println();
		
		for(int i=0;i<28;i++)
		{
			System.out.format("%3d./// |", i);
			for(int j=0;j<131;j++)
			{
				if( j<128 )
				{
					if( map_table[i][j].compareToIgnoreCase("0")==0 ) System.out.format(" %6s |", "---");
					else System.out.format(" %6s |", ">"+map_table[i][j]+"<");
				}
				if( j==128 ) System.out.format(" %45s |", map_table[i][j]);
				if( j==129 ) System.out.format(" %5s |", map_table[i][j]);
				if( j==130 ) System.out.format(" %7s |", map_table[i][j]);
			}
			System.out.format("\\\\\\.%3d. <", i);
			System.out.println();
		}
//		for(int j = 0; j<129; j++)
//		{
//			if( j==0 ) System.out.format("%3s    |", "LP.");
//			if(j!=127 && j!=128) System.out.format(" %3s |", j);
//			if( j==127 ) System.out.format(" %30s |", "isFinal");
//			if( j==128 ) System.out.format(" %30s", "TokenDesc\n");
//		}
//
//		for (int i = 0; i < 150; i++)
//		{
//			boolean isNecessary = true; //do wywalenia
//			int licznik = 0; //do wywalenia
//			for (int j = 0; j < 129; j++) //do wywalenia
//			{
//				if( j < 128 )
//				{
//					if( map_table[i][127]==null )
//					{
//						//System.out.print("NULL i,j = "+i+","+j+"! "); //ASCII MA KODY OD 0 DO 127, nie do 126
//					}
//					else
//					{
//						if( map_table[i][j].compareToIgnoreCase("0")==0 )
//						{
//							licznik++;
//							//System.out.print(" -"+j);
//						}
//						//else System.out.print(" +"+j);
//					}
//				}
//			}
//			if( licznik==127 ) isNecessary = false; //do wywalenia
//
//			for (int j = 0; j < 129; j++)
//			{
//				if( isNecessary == true ) //do wywalenia
//				{
//					if( j==0 ) System.out.format("%3s.///|", i);
//					if( map_table[i][j]==null ) //do wywalenia
//					{
//						System.out.print("NULL i,j = "+i+","+j+"! "); //ASCII MA KODY OD 0 DO 127, nie do 126
//					}
//					else
//					{
//						if( j!=128 && j!=129 )
//						{
//							if( map_table[i][j].compareToIgnoreCase("0")!=0 ) System.out.format(" %3s |", map_table[i][j]);
//							else System.out.format("     |");
//						}
//						if( j==128 ) System.out.format(" %30s |", map_table[i][j]);
//						if( j==129 ) System.out.format(" %30s ", map_table[i][j]);
//					}
//				}
//				else if( j == 0 )
//				{
//					if( j==0 ) System.out.format("%3s.   |", i);
//					System.out.print("Niepotrzebny stan! "); //do wywalenia
//				}
//
//	        }
//	        System.out.println();
//	    }
	}

	public void initializeTable()
	{
		map_table = new String[28][131];
    	
		for (int i = 0; i < 28; i++)
		{
			for (int j = 0; j < 131; j++)
	        {
	            map_table[i][j] = "0";
	        }
	    }
		
		//STAN: 0 - ERROR
		//wszystko do 0
		map_table[0][128] = "PARSER_ERROR";
		map_table[0][129] = "false";
		map_table[0][130] = "#ff0000";

		//STAN: 1 - START
		map_table[1][128] = "START_STATE";
		map_table[1][129] = "false";
		map_table[1][130] = "#cc0000";
			
			//dla '0'-'9' do 2
			for(int i=48; i<=57; i++)
			{
				map_table[1][i] = "2";
			}
			
			//dla 'A'-'Z' do 6
			for(int i=65; i<=90; i++)
			{
				map_table[1][i] = "6";
			}
			
			//dla 'a'-'z' do 8
			for(int i=97; i<=122; i++)
			{
				map_table[1][i] = "8";
			}
			
			//dla '+' do 9
			map_table[1][43] = "9";
			
			//dla '-' do 10
			map_table[1][45] = "10";
						
			//dla '*' do 11
			map_table[1][42] = "11";
			
			//dla '/' do 12
			map_table[1][47] = "12";
			
			//dla '%' do 13
			map_table[1][37] = "13";
			
			//dla '=' do 14
			map_table[1][61] = "14";
			
			//dla '<' do 17
			map_table[1][60] = "17";
			
			//dla '>' do 18
			map_table[1][62] = "18";
			
			//dla ';' do 22
			map_table[1][59] = "22";
			
			//dla '!' do 26
			map_table[1][33] = "26";
			
			//dla ':' do 27
			map_table[1][58] = "27";
			
		//STAN: 2 - UNSIGNED_INTEGER
		map_table[2][128] = "UNSIGNED_INTEGER";
		map_table[2][129] = "true";
		map_table[2][130] = "#9dd600";
		
			//dla '0'-'9' do 2
			for(int i=48; i<=57; i++)
			{
				map_table[2][i] = "2";
			}
		
			//dla '.' do 24
			map_table[2][46] = "24";
			
		//STAN: 3 - UNSIGNED_INTEGER
		map_table[3][128] = "SIGNED_INTEGER";
		map_table[3][129] = "true";
		map_table[3][130] = "#991900";
		
			//dla '0'-'9' do 3
			for(int i=48; i<=57; i++)
			{
				map_table[3][i] = "3";
			}
	
			//dla '.' do 25
			map_table[3][46] = "25";
		
		//STAN: 4 - UNSIGNED_FLOAT
		map_table[4][128] = "UNSIGNED_FLOAT";
		map_table[4][129] = "true";
		map_table[4][130] = "#991900";
		
			//dla '0'-'9' do 3
			for(int i=48; i<=57; i++)
			{
				map_table[4][i] = "4";
			}
		
		//STAN: 5 - SIGNED_FLOAT
		map_table[5][128] = "SIGNED_FLOAT";
		map_table[5][129] = "true";
		map_table[5][130] = "#008300";
		
			//dla '0'-'9' do 5
			for(int i=48; i<=57; i++)
			{
				map_table[5][i] = "5";
			}
		
		//STAN: 6 - GLOBAL_VARIABLE_NAME
		map_table[6][128] = "GLOBAL_VARIABLE_NAME";
		map_table[6][129] = "true";
		map_table[6][130] = "#faa0fe";
		
			//dla '0'-'9' do 6
			for(int i=48; i<=57; i++)
			{
				map_table[6][i] = "6";
			}
			
			//dla 'A'-'Z' do 6
			for(int i=65; i<=90; i++)
			{
				map_table[6][i] = "6";
			}
			
			//dla '_' do 6
			map_table[6][95] = "6";
			
			//dla 'a'-'z' do 7
			for(int i=97; i<=122; i++)
			{
				map_table[6][i] = "7";
			}
		
		//STAN: 7 - FUNCTION_NAME
		map_table[7][128] = "FUNCTION_NAME";
		map_table[7][129] = "true";
		map_table[7][130] = "#5c7e4d";
		
			//dla '0'-'9' do 7
			for(int i=48; i<=57; i++)
			{
				map_table[7][i] = "7";
			}
			
			//dla 'A'-'Z' do 7
			for(int i=65; i<=90; i++)
			{
				map_table[7][i] = "7";
			}
		
			//dla 'a'-'z' do 7
			for(int i=97; i<=122; i++)
			{
				map_table[7][i] = "7";
			}
				
			//dla '_' do 7
			map_table[7][95] = "7";		
		
		//STAN: 8 - VARIABLE_NAME
		map_table[8][128] = "VARIABLE_NAME";
		map_table[8][129] = "true";
		map_table[8][130] = "#000099";
		
			//dla '0'-'9' do 8
			for(int i=48; i<=57; i++)
			{
				map_table[8][i] = "8";
			}
			
			//dla 'A'-'Z' do 8
			for(int i=65; i<=90; i++)
			{
				map_table[8][i] = "8";
			}
		
			//dla 'a'-'z' do 8
			for(int i=97; i<=122; i++)
			{
				map_table[8][i] = "8";
			}
				
			//dla '_' do 8
			map_table[8][95] = "8";
			

		//STAN: 9 - PLUS_SIGN
		map_table[9][128] = "PLUS_SIGN";
		map_table[9][129] = "true";
		map_table[9][130] = "#f442a7";
		
			//dla '0'-'9' do 3
			for(int i=48; i<=57; i++)
			{
				map_table[9][i] = "3";
			}
		
		//STAN: 10 - MINUS_SIGN
		map_table[10][128] = "MINUS_SIGN";
		map_table[10][129] = "true";
		map_table[10][130] = "#ffb399";
		
			//dla '0'-'9' do 3
			for(int i=48; i<=57; i++)
			{
				map_table[10][i] = "3";
			}
		
		//STAN: 11 - MULTIPLICATION_SIGN
		map_table[11][128] = "MULTIPLICATION_SIGN";
		map_table[11][129] = "true";
		map_table[11][130] = "#0cb723";
		
		//STAN: 12 - DIVISION_SIGN_SIGN
		map_table[12][128] = "DIVISION_SIGN";
		map_table[12][129] = "true";
		map_table[12][130] = "#ff5300";
		
		//STAN: 13 - MODULO_SIGN
		map_table[13][128] = "MODULO_SIGN";
		map_table[13][129] = "true";
		map_table[13][130] = "#955659";
	
		//STAN: 14 - MATH_EQUALITY_SIGN
		map_table[14][128] = "MATH_EQUALITY_SIGN";
		map_table[14][129] = "true";
		map_table[14][130] = "#cc00ff";
		
			//dla '=' do 15
			map_table[14][61] = "15";
			
		//STAN: 15 - LOGIC_EQUALITY_SIGN
		map_table[15][128] = "LOGIC_EQUALITY_SIGN";
		map_table[15][129] = "true";
		map_table[15][130] = "#6d8ac1";
			
			//dla '=' do 23
			map_table[15][61] = "23";
		
		//STAN: 16 - LOGIC_INEQUALITY_SIGN
		map_table[16][128] = "LOGIC_INEQUALITY_SIGN";
		map_table[16][129] = "true";
		map_table[16][130] = "#ff6600";
			
		//STAN: 17 - LESSER_THAN_SIGN
		map_table[17][128] = "LESSER_THAN_SIGN";
		map_table[17][129] = "true";
		map_table[17][130] = "#ffff4d";
		
			//dla '=' do 19
			map_table[17][61] = "19";
		
		//STAN: 18 - GREATER_THAN_SIGN
		map_table[18][128] = "GREATER_THAN_SIGN";
		map_table[18][129] = "true";
		map_table[18][130] = "#ff99ff";
		
			//dla '=' do 20
			map_table[18][61] = "20";
		
		//STAN: 19 - LESSER_THAN_OR_EQUAL_SIGN
		map_table[19][128] = "LESSER_THAN_OR_EQUAL_SIGN";
		map_table[19][129] = "true";
		map_table[19][130] = "#669900";
		
		//STAN: 20 - GREATER_THAN_OR_EQUAL_SIGN
		map_table[20][128] = "GREATER_THAN_OR_EQUAL_SIGN";
		map_table[20][129] = "true";
		map_table[20][130] = "#ff0066";
		
		//STAN: 21 - ASSIGNMENT_SIGN
		map_table[21][128] = "ASSIGNMENT_SIGN";
		map_table[21][129] = "true";
		map_table[21][130] = "#cc0099";
		
		//STAN: 22 - SEMICOLON_SIGN
		map_table[22][128] = "SEMICOLON_SIGN";
		map_table[22][129] = "true";
		map_table[22][130] = "#fdf84d";
		
		//STAN: 23 - JAVA_SCRIPT_EQUALITY_SIGN
		map_table[23][128] = "JAVA_SCRIPT_EQUALITY_SIGN";
		map_table[23][129] = "true";
		map_table[23][130] = "#00cc66";
		
		//STAN: 24 - DOT1_STATE
		map_table[24][128] = "DOT1_STATE";
		map_table[24][129] = "false";
		map_table[24][130] = "#bb0000";
		
			//dla '0'-'9' do 4
			for(int i=48; i<=57; i++)
			{
				map_table[24][i] = "4";
			}
		
		//STAN: 25 - DOT2_STATE
		map_table[25][128] = "DOT2_STATE";
		map_table[25][129] = "false";
		map_table[25][130] = "#bb0500";
		
			//dla '0'-'9' do 5
			for(int i=48; i<=57; i++)
			{
				map_table[25][i] = "5";
			}
		
		//STAN: 26 - EXCLAMATION_STATE
		map_table[26][128] = "EXCLAMATION_STATE";
		map_table[26][129] = "false";
		map_table[26][130] = "#aa0000";
		
			//dla '=' do 16
			map_table[26][61] = "16";
		
		//STAN: 27 - COLON_STATE
		map_table[27][128] = "COLON_STATE";
		map_table[27][129] = "false";
		map_table[27][130] = "#aa0500";
		
			//dla '=' do 21
			map_table[27][61] = "21";

	}
	
	public void initializeTable_old()
	{
		map_table = new String[31][131];
	    	
		for (int i = 0; i < 31; i++)
		{
			for (int j = 0; j < 131; j++)
	        {
	            map_table[i][j] = "0";
	        }
	    }
	    	
	        // TRZY OSTATNIE KOLUMNY TABELI TO ODPOWIEDNIO: TOKEN.DESCRIPTION | FINAL | KOD KOLORU (128 | 129 | 130)
	        // WIERSZ O INDEKSIE 0 - stan ERROR nieakceptujący
			// WIERSZ O INDEKSIE 1 - stan STARTU nieakceptujący

	        for(int j=128, i=0; i<31;i++){
	            map_table[i][j] = null;
	        }

	        for(int j=129, i=0;i<31;i++){
				map_table[i][j] = "false";
			}

	        //wiersz 0 - stan pułapki/error
	        map_table[0][128] = "ERROR";
	        map_table[0][130] = "#ff0000";


	        //wiersz 1 - stan startowy
	        map_table[1][130] = "---";



	        //-------------------MAPOWANIE STANÓW ----------------------- //



			//+ stan nr 2
			map_table[2][128] = "PLUS_SIGN";
	        map_table[2][129] = "true";
	        map_table[2][130] = "#f442a7";
	        map_table[1][43] = "2";
	        map_table[2][0] = "0";

			for(int i=48;i<=57;i++){
				map_table[2][i] = "22";
			}


			//- stan nr 3
			map_table[3][128] = "MINUS_SIGN";
	        map_table[3][129] = "true";
	        map_table[3][130] = "#ffb399";
	        map_table[1][45] = "3";
	        map_table[3][0] = "0";

	        for(int i=48;i<=57;i++){
	        	map_table[3][i] = "22";
			}


			//* stan nr 4
	        map_table[4][129] = "true";
	        map_table[4][128] = "MULTIPLICATION_SIGN";
	        map_table[4][130] = "#0cb723";
	        map_table[1][42] = "4";
	        map_table[4][0] = "0";



			// / stan nr 5
	        map_table[5][129] = "true";
	        map_table[5][128] = "DIVISION_SIGN";
	        map_table[5][130] = "#ff5300";
	        map_table[1][47] = "5";
	        map_table[5][0] = "0";


			//% stan nr 6
	        map_table[6][129] = "true";
	        map_table[6][128] = "MODULO_SIGN";
	        map_table[6][130] = "#955659";
	        map_table[1][37] = "6";
	        map_table[6][0] = "0";


			//< stan nr 7
	        map_table[7][129] = "true";
	        map_table[7][128] = "LESS_THAN_SIGN";
	        map_table[7][130] = "#ffff4d";
	        map_table[1][60] = "7";
	        map_table[7][0] = "0";


			//> stan nr 8
	        map_table[8][129] = "true";
	        map_table[8][128] = "GREATER_THAN_SIGN";
	        map_table[8][130] = "#ff99ff";
	        map_table[1][62] = "8";
			map_table[8][0] = "0";


			//= stan nr 9
	        map_table[9][129] = "true";
	        map_table[9][128] = "MATH_EQUALITY_SIGN";
	        map_table[9][130] = "#cc00ff";
	        map_table[1][61] = "9";
			map_table[9][0] = "0";


	        //: stan nr 10
	        map_table[10][129] = "true";
	        map_table[10][128] = "COLON_SIGN";
	        map_table[10][130] = "#fdf84d";
	        map_table[1][58] = "10";
	        map_table[10][0] = "0";


	        //! stan nr 11
	        map_table[11][129] = "true";
	        map_table[11][128] = "EXCLAMATION_MARK";
	        map_table[11][130] = "#4283da";
	        map_table[1][33] = "11";
			map_table[11][0] = "0";



	        //. stan nr 12
	        map_table[12][129] = "false";
	        map_table[12][128] = "DOT_SIGN";
	        map_table[12][130] = "---";
	        map_table[1][46] = "12";
	        map_table[12][0] = "0";


	        for(int i=48;i<=57;i++){
	        	map_table[12][i] = "0";
			}


	        //stany od numeru 13 to te znaki, ktore są łączone np. === albo := i ciągi znaków


	        //== stan nr 13
	        map_table[13][129] = "true";
	        map_table[13][128] = "LOGIC_EQUALITY_SIGN";
	        map_table[13][130] = "#6d8ac1";
	        map_table[9][61] = "13";
	        map_table[13][0] = "0";


	        //!= stan nr 14
	        map_table[14][129] = "true";
	        map_table[14][128] = "LOGIC_INEQUALITY_SIGN";
	        map_table[14][130] = "#ff6600";
	        map_table[11][61] ="14";
	        map_table[14][0] = "0";


	        //:= stan nr 15
	        map_table[15][129] = "true";
	        map_table[15][128] = "SUBROGATION_SIGN";
	        map_table[15][130] = "#cc0099";
	        map_table[10][61] = "15";
	        map_table[15][0] = "0";


	        //<= stan nr 16
	        map_table[16][129] = "true";
	        map_table[16][128] = "LESS_THAN_OR_EQUAL_SIGN";
	        map_table[16][130] = "#669900";
	        map_table[7][61] = "16";
	        map_table[16][0] = "0";


	        //>= stan nr 17
	        map_table[17][129] = "true";
	        map_table[17][128] = "GREATER_THAN_OR_EQUAL_SIGN";
	        map_table[17][130] = "#ff0066";
	        map_table[8][61] = "17";
	        map_table[17][0] = "0";


	        //=== stan nr 18
	        map_table[18][129] = "true";
	        map_table[18][128] = "JAVA_SCRIPT_EQUALITY_SIGN";
	        map_table[18][130] = "#00cc66";
	        map_table[13][61] = "18";
	        map_table[18][0] = "0";



	        //BIG_FIRST_LETTER_&_BIG_LETTERS_OR_NUMBERS stan nr 19
	        map_table[19][129] = "true";
	        map_table[19][128] = "BIG_FIRST_LETTER_BIG_LETTERS_OR_NUMBERS";
	        map_table[19][130] = "#660066";
	        map_table[19][0] = "0";


			for(int i=65;i<=90;i++){
				map_table[19][i] = "19";
			}

			for(int i=48;i<=57;i++){
				map_table[19][i] = "19";
			}

			for(int i=97;i<=122;i++){
				map_table[19][i] = "27";
			}



	        //BIG FIRST LETTER or NUMBERS stan nr 20
			for(int i=65;i<=90;i++){ //1 --A-Z--> 20
				map_table[1][i] = "20";
			}
			
			map_table[20][129] = "true";
	        map_table[20][128] = "BIG_FIRST_LETTER_";
	        map_table[20][130] = "#faa0fe";
	        map_table[20][0] = "0";			

			for(int i=65;i<=90;i++){ //20 --A-Z--> 20
				map_table[20][i] = "20";
			}
			
			for(int i=48;i<=57;i++){ //20 --0-9--> 20
				map_table[20][i] = "20";
			}
			
			for(int i=97;i<=122;i++){ //20 --a-z--> 26
				map_table[20][i] = "26";
			}

			

			


	        //SMALL FIRST LETTER stan nr 21 np. aaBBc, aBBBB, bez liczb
	        map_table[21][129] = "true";
	        map_table[21][128] = "SMALL_FIRST_LETTER";
	        map_table[21][130] = "#000099";
	        map_table[21][0] = "0";

	        for(int i=97;i<=122;i++){
	        	map_table[1][i] = "21";
			}

	        for(int i=65;i<=90;i++){
	        	map_table[21][i] = "21";
			}

			for(int i=97;i<=122;i++){
	        	map_table[21][i] = "21";
			}

			for(int i=48;i<=57;i++){
	        	map_table[21][i] = "0";
			}


	        //SIGNED INTEGER stan nr 22
	        map_table[22][129] = "true";
	        map_table[22][128] = "SIGNED_INTEGER";
	        map_table[22][130] = "#991966";
	        map_table[22][0] = "0";


	        for(int i=48;i<=57;i++){
	        	map_table[22][i] = "22";
			}

			map_table[22][46] = "29";



	        //UNSIGNED INTEGER stan nr 23
	        map_table[23][129] = "true";
	        map_table[23][128] = "UNSIGNED_INTEGER";
	        map_table[23][130] = "#9dd603";
	        map_table[23][0] = "0";

			map_table[23][46] = "30";


	        for(int i=48;i<=57;i++){
	        	map_table[1][i] = "23";
			}

	        for(int i=48;i<=57;i++){
	        	map_table[23][i] = "23";
			}





	        //SIGNED FLOAT stan nr 24
	        map_table[24][129] = "true";
	        map_table[24][128] = "SIGNED_FLOAT";
	        map_table[24][130] = "#008366";
	        map_table[24][0] = "0";

	        for(int i=48; i<=57;i++){
	        	map_table[24][i] = "24";
			}

			map_table[24][46] = "0";



	        //UNSIGNED_FLOAT stan nr 25
	        map_table[25][129] = "true";
	        map_table[25][128] = "UNSIGNED_FLOAT";
	        map_table[25][130] = "#003366";
	        map_table[25][0] = "0";

	        for(int i=48;i<=57;i++){
	        	map_table[25][i] = "25";
			}

			map_table[25][46] = "0";



			//BIG FIRST LETTER & SMALL LETTERS stan nr 26 np. Abbbb, Dabcd bez liczb
			map_table[26][129] = "true";
			map_table[26][128] = "BIG_FIRST_LETTER_SMALL_LETTERS";
			map_table[26][130] = "#3dad7d";

			for(int i=97;i<=122;i++){
				map_table[26][i] = "26";
			}

			for(int i=65;i<=90;i++){
				map_table[26][i] = "27";
			}

			for(int i=48;i<=57;i++){
				map_table[26][i] = "27";
			}





			//BIG FIRST LETTER & BIG LETTERS & SMALL LETTERS & NUMBERS stan nr 27 np. AAAbbAAA, AZccdE, Abcde27D
			map_table[27][129] = "true";
			map_table[27][128] = "BIG_FIRST_LETTER_BIG_LETTERS_SMALL_LETTERS_NUMBERS";
			map_table[27][130] = " #5c7e4d";

			for(int i=97;i<=122;i++){
				map_table[27][i] = "27";
			}

			for(int i=65;i<=90;i++){
				map_table[27][i] = "27";
			}

			for(int i=48;i<=57;i++){
				map_table[27][i] = "27";
			}



			//BIG LETTERS ONLY stan nr 28 stan pomocniczy moze byc akceptujacy, domyślnie nie jest, ciągi znaków AAAA, BBB, length > 1
			map_table[28][128] = "BIG_LETTERS_ONLY";
			map_table[28][130] = "---";

			for(int i=65;i<=90;i++){
				map_table[28][i] = "28";
			}

			for(int i=97;i<=122;i++){
				map_table[28][i] = "27";
			}

			for(int i=48;i<=57;i++){
				map_table[28][i] = "19";
			}


			// STAN POMOCNICZY stan nr 29 wymaga aby po kropce wprowadzić jakąś liczbę aby był to SIGNED FLOAT
			map_table[29][128] = "TMP_STATE";
			map_table[29][130] = "---";

			for(int i=48;i<=57;i++){
				map_table[29][i] = "24";
			}



			//STAN POMOCNICZY stan nr 30 wymaga aby po kropce wprowadzic jakas liczbę aby był to UNSIGNED FLOAT
			map_table[30][128] = "TMP_STATE";
			map_table[30][130] = "---";


			for(int i=48;i<=57;i++){
				map_table[30][i] = "25";
			}
	}
}

