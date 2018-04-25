import java.io.File;

public class Token {
	private String value;
//    private String Desc;
//    private enum Desc { ERROR, PLUS, MINUS }
	private TokenDesc desc;
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String Sign) {
        this.value = Sign;
    }

//    public String getDesc() {
//        return Desc;
//    }
//
//    public void setDesc(String Desc) {
//        this.Desc = Desc;
//    }
    
//    public enum getDesc() {
//        return Desc;
//    }
//
//    public void setDesc(String Desc) {
//        if( Desc.compareToIgnoreCase("PLUS") this.Desc = PLUS;
//    }
    
    public TokenDesc getDesc() {
        return desc;
    }

    public void setDesc(String Desc) {
    	//patrz - TokenDesc.java
    	if( Desc.compareToIgnoreCase("PARSER_ERROR")==0 ) this.desc = TokenDesc.PARSER_ERROR; 								//0
    	if( Desc.compareToIgnoreCase("START_STATE")==0 ) this.desc = TokenDesc.START_STATE;									//1
    	if( Desc.compareToIgnoreCase("UNSIGNED_INTEGER")==0 ) this.desc = TokenDesc.UNSIGNED_INTEGER; 						//2
    	if( Desc.compareToIgnoreCase("SIGNED_INTEGER")==0 ) this.desc = TokenDesc.SIGNED_INTEGER; 							//3
    	if( Desc.compareToIgnoreCase("UNSIGNED_FLOAT")==0 ) this.desc = TokenDesc.UNSIGNED_FLOAT; 							//4
    	if( Desc.compareToIgnoreCase("SIGNED_FLOAT")==0 ) this.desc = TokenDesc.SIGNED_FLOAT; 								//5
    	if( Desc.compareToIgnoreCase("GLOBAL_VARIABLE_NAME")==0 ) this.desc = TokenDesc.GLOBAL_VARIABLE_NAME; 				//6			//old: BIG_LETTERS_ONLY,
    	if( Desc.compareToIgnoreCase("FUNCTION_NAME")==0 ) this.desc = TokenDesc.FUNCTION_NAME; 							//7 		//old: BIG_FIRST_LETTER_SMALL_LETTERS, //BIG_FIRST_LETTER_BIG_LETTERS_SMALL_LETTERS,
    	if( Desc.compareToIgnoreCase("VARIABLE_NAME")==0 ) this.desc = TokenDesc.VARIABLE_NAME; 							//8
    	if( Desc.compareToIgnoreCase("PLUS_SIGN")==0 ) this.desc = TokenDesc.PLUS_SIGN;		 								//9
    	if( Desc.compareToIgnoreCase("MINUS_SIGN")==0 ) this.desc = TokenDesc.MINUS_SIGN;	 								//10
    	if( Desc.compareToIgnoreCase("MULTIPLICATION_SIGN")==0 ) this.desc = TokenDesc.MULTIPLICATION_SIGN;	 				//11
    	if( Desc.compareToIgnoreCase("DIVISION_SIGN")==0 ) this.desc = TokenDesc.DIVISION_SIGN;	 							//12
    	if( Desc.compareToIgnoreCase("MODULO_SIGN")==0 ) this.desc = TokenDesc.MODULO_SIGN;	 								//13	// %
    	if( Desc.compareToIgnoreCase("MATH_EQUALITY_SIGN")==0 ) this.desc = TokenDesc.MATH_EQUALITY_SIGN;	 				//14	// =
    	if( Desc.compareToIgnoreCase("LOGIC_EQUALITY_SIGN")==0 ) this.desc = TokenDesc.LOGIC_EQUALITY_SIGN;	 				//15	// ==
    	if( Desc.compareToIgnoreCase("LOGIC_INEQUALITY_SIGN")==0 ) this.desc = TokenDesc.LOGIC_INEQUALITY_SIGN;			 	//16	// !=
    	if( Desc.compareToIgnoreCase("LESSER_THAN_SIGN")==0 ) this.desc = TokenDesc.LESSER_THAN_SIGN;	 					//17	// <
    	if( Desc.compareToIgnoreCase("GREATER_THAN_SIGN")==0 ) this.desc = TokenDesc.GREATER_THAN_SIGN;						//18	// >
    	if( Desc.compareToIgnoreCase("LESSER_THAN_OR_EQUAL_SIGN")==0 ) this.desc = TokenDesc.LESSER_THAN_OR_EQUAL_SIGN;		//19	// <=
    	if( Desc.compareToIgnoreCase("GREATER_THAN_OR_EQUAL_SIGN")==0 ) this.desc = TokenDesc.GREATER_THAN_OR_EQUAL_SIGN;	//20	// >=
    	
    	if( Desc.compareToIgnoreCase("ASSIGNMENT_SIGN")==0 ) this.desc = TokenDesc.ASSIGNMENT_SIGN; 						//21	// :=
    	if( Desc.compareToIgnoreCase("SEMICOLON_SIGN")==0 ) this.desc = TokenDesc.SEMICOLON_SIGN; 							//22	// ;
    	if( Desc.compareToIgnoreCase("JAVA_SCRIPT_EQUALITY_SIGN")==0 ) this.desc = TokenDesc.JAVA_SCRIPT_EQUALITY_SIGN; 	//23	// ===
    	
    	if( Desc.compareToIgnoreCase("DOT1_STATE")==0 ) this.desc = TokenDesc.DOT1_STATE;	 				// NIEAKCEPTUJACY 24
    	if( Desc.compareToIgnoreCase("DOT2_STATE")==0 ) this.desc = TokenDesc.DOT2_STATE;					// NIEAKCEPTUJACY 25
    	if( Desc.compareToIgnoreCase("EXCLAMATION_STATE")==0 ) this.desc = TokenDesc.EXCLAMATION_STATE; 	// NIEAKCEPTUJACY 26
    	if( Desc.compareToIgnoreCase("COLON_STATE")==0 ) this.desc = TokenDesc.COLON_STATE; 				// NIEAKCEPTUJACY 27
    	
    }
    
    public void setDesc(TokenDesc Desc) {
    	this.desc = Desc; 
    }

    public String getColor() {
    	return color;
    }

    public void setColor(String Color) {
    	this.color = Color;
    }



}
