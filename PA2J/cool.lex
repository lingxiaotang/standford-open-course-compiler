/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    private int current_length=0;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }

   
    private int nested_comment=0;
    private boolean is_string_legal=true;
    private int comment_type=0; 
    private int str_state=0;

%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{
/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF Symbol, or your lexer won't
 *  work.  */
    int flag=0;
    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
    case COMMENT:
      flag=1;
    break;
    case STRING:
      flag=-1;
    break;
    }
    if(flag==0)
       return new Symbol(TokenConstants.EOF);
    else if(flag==1)
       return new Symbol(TokenConstants.ERROR,"EOF in comment");
    else
       return new Symbol(TokenConstants.ERROR,"String contains EOF");
%eofval}

%class CoolLexer
%cup
%state STRING
%state COMMENT

whitespace=[ \f\r\t]
integer=[0-9]+
type_identifier=[A-Z][A-Za-z0-9_]*
object_identifier=[a-z][A-Za-z0-9_]*

%%


<YYINITIAL>    [Cc][Ll][Aa][Ss][Ss] { return new Symbol(TokenConstants.CLASS);}
<YYINITIAL>    [Ee][Ll][Ss][Ee] { return new Symbol(TokenConstants.ELSE);}   
<YYINITIAL>    f[Aa][Ll][Ss][Ee] {return new Symbol(TokenConstants.BOOL_CONST,new Boolean(true));}
<YYINITIAL>    t[Rr][Uu][Ee] { return new Symbol(TokenConstants.BOOL_CONST,new Boolean(false));}
<YYINITIAL>    [Ii][Ff] { return new Symbol(TokenConstants.IF);}
<YYINITIAL>    [Ff][Ii] {return new Symbol(TokenConstants.FI);}
<YYINITIAL>    [Ii][Nn] { return new Symbol(TokenConstants.IN);}
<YYINITIAL>    [Ii][Nn][Hh][Ee][Rr][Ii][Tt][Ss] {return new Symbol(TokenConstants.INHERITS);}
<YYINITIAL>    [Ii][Ss][Vv][Oo][Ii][Dd] {return new Symbol(TokenConstants.ISVOID);}
<YYINITIAL>    [lL][Ee][Tt] {return new Symbol(TokenConstants.LET);}
<YYINITIAL>    [Ll][Oo][Oo][Pp] { return new Symbol(TokenConstants.LOOP);}
<YYINITIAL>    [Pp][Oo][Oo][lL] { return new Symbol(TokenConstants.POOL);}
<YYINITIAL>    [Tt][Hh][Ee][Nn] {return new Symbol(TokenConstants.THEN);}
<YYINITIAL>    [Ww][Hh][Ii][Ll][Ee] {return new Symbol(TokenConstants.WHILE);}
<YYINITIAL>    [Cc][Aa][Ss][Ee] {return new Symbol(TokenConstants.CASE);}
<YYINITIAL>    [Ee][Ss][Aa][Cc] {return new Symbol(TokenConstants.ESAC);}
<YYINITIAL>    [Nn][Ee][Ww] {return new Symbol(TokenConstants.NEW);}
<YYINITIAL>    [oO][fF] {return new Symbol(TokenConstants.OF);}
<YYINITIAL>    [Nn][Oo][Tt] { return new Symbol(TokenConstants.NOT);}
<YYINITIAL>    t[R|r][U|u][E|e] {return new Symbol(TokenConstants.BOOL_CONST,true);}
<YYINITIAL>    \n {curr_lineno++;}
<YYINITIAL>    {whitespace}  {/*ignore*/}
<YYINITIAL>    {integer} { return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext()));}
<YYINITIAL>    {type_identifier} { return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext()));}
<YYINITIAL>    {object_identifier} { return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext()));}
<YYINITIAL>    "--"  {comment_type=0; yy_lexical_state=COMMENT;yybegin(COMMENT);}
<YYINITIAL>    "(*"  {comment_type=1; yy_lexical_state=COMMENT;nested_comment++;yybegin(COMMENT);}
<YYINITIAL>    "*)"  {return new Symbol(TokenConstants.ERROR,"Unmatched *)");}
<YYINITIAL>    "*"   { return new Symbol(TokenConstants.MULT);   }
<YYINITIAL>    "=>"  { return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>    "("   { return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>    ";"   { return new Symbol(TokenConstants.SEMI);   }
<YYINITIAL>    "-"   { return new Symbol(TokenConstants.MINUS);  }
<YYINITIAL>    ")"   { return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>    "<"   { return new Symbol(TokenConstants.LT);     }
<YYINITIAL>    ","   { return new Symbol(TokenConstants.COMMA);  }
<YYINITIAL>    "/"   { return new Symbol(TokenConstants.DIV);    }
<YYINITIAL>    "+"   { return new Symbol(TokenConstants.PLUS);   }
<YYINITIAL>    "<-"  { return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>    "."   { return new Symbol(TokenConstants.DOT);    }
<YYINITIAL>    "<="  { return new Symbol(TokenConstants.LE);     }
<YYINITIAL>    "="   { return new Symbol(TokenConstants.EQ);     }
<YYINITIAL>    ":"   { return new Symbol(TokenConstants.COLON);  }
<YYINITIAL>    "{"   { return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>    "}"   { return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>    "@"   { return new Symbol(TokenConstants.AT);     }
<YYINITIAL>    "~"   { return new Symbol(TokenConstants.NEG);    }
<YYINITIAL>    \"  { yy_lexical_state=STRING;yybegin(STRING);string_buf.setLength(0);}


<STRING> \"
    {
      if(str_state==1)
	{
	  string_buf.append("\"");
	  str_state=0;
	}
      else{
        yy_lexical_state=YYINITIAL;yybegin(YYINITIAL);
        if(current_length<=MAX_STR_CONST&&is_string_legal==true)
        {
            yybegin(YYINITIAL);
            current_length=0;
            return new Symbol(TokenConstants.STR_CONST,AbstractTable.idtable.addString(new String(string_buf)));
        }
        else if(current_length>MAX_STR_CONST)
        {
            yybegin(YYINITIAL);
            current_length=0;
            return new Symbol(TokenConstants.ERROR,"String constant too long");
        }
        else
        {
            yybegin(YYINITIAL);/*do nothing if string contains null */
	    current_length=0;
	    return new Symbol(TokenConstants.ERROR,"String contains null character");
        }
      }
    }
<STRING>  \\ 
{
  if(str_state==0)
    str_state=1;
  else
    {
      str_state=0;
      string_buf.append("\\");
    }
}
<STRING>  \\b 
{
  if(str_state==0)
    string_buf.append('\b');
  else
    {
      string_buf.append("\\b");
      str_state=0;
    }
}
<STRING>  \\t
{
  if(str_state==0)
    string_buf.append('\t');
  else
    {
      string_buf.append("\\t");
      str_state=0;
    }
}
<STRING>  \\n
{
  if(str_state==0)
    string_buf.append('\n');
  else
    {
      string_buf.append("\\n");
      str_state=0;
    }
}
<STRING>  \\f
{
  if(str_state==0)
    string_buf.append('\f');
  else
    {
      string_buf.append("\\f");
      str_state=0;
    }

}
<STRING>  \n  {curr_lineno++;yybegin(YYINITIAL);return new Symbol(TokenConstants.ERROR,"Unterminated string constant");}
<STRING>  \0  {is_string_legal=false;return new Symbol(TokenConstants.ERROR,"String contains null character");}
<STRING>  [^\b\t\n\f]  {string_buf.append(yytext());String s=new String(yytext());current_length+=s.length();}
 


<COMMENT>    "(*"  {nested_comment++;}
<COMMENT>    "*)" 
    {
        nested_comment--;
        if(nested_comment==0)
        {
            yy_lexical_state=YYINITIAL;
            yybegin(YYINITIAL);
        }
    }
<COMMENT>   \n
{
  if(comment_type==0)
  {
     curr_lineno++;
     yybegin(YYINITIAL);
  }
  if(comment_type==1)
  {
     curr_lineno++;
  }
}
<COMMENT>    . {/*do nothing*/}

<YYINITIAL>    .     {return new Symbol(TokenConstants.ERROR,yytext());}
