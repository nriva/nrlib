package it.nrsoft.nrlib.mapping;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

import org.apache.log4j.Logger;



public class MappingRuleParser {
	
	static Logger logger = Logger.getLogger(MappingRuleParser.class.getName());
	
	private static final int LVALUE = 1;
	private static final int LVALUE_EQ = 2;
	private static final int EXPRESSION = 3;
	private static final int EXPRESSION_COMMA = 4;
	private static final int PROPNAME = 5;
	private static final int PROPNAME_EQ = 6;
	private static final int PROPVALUE = 7;
	private static final int PROPVALUE_COMMA = 8;
	
	private static final int MACRO = 9;
	private static final int MACRO_EXPRESSION = 10;
	private static final int MACRO_OPEN = 11;
	private static final int MACRO_PARAM = 12;
	
	private static final int CALL = 13;
	private static final int CALL_EXPRESSION = 14;
	private static final int CALL_OPEN = 15;
	private static final int CALL_PARAM = 16;
	
	
	/**
	 * Rule format: lvalue = expression; [ property = value; ... ]
	 * @param ruleText
	 * @throws IOException 
	 */
	public MappingRule parse(String ruleText) throws IOException
	{
		
		
		MappingRule rule = new MappingRule();
		
       // create a new tokenizer
       Reader r = new StringReader(ruleText);
       StreamTokenizer st = new StreamTokenizer(r);
       
       int status = 0;
       
       st.ordinaryChar('@');
       st.ordinaryChar('#');
       st.ordinaryChar('/');
       st.quoteChar('"');
       
       
       rule.leftValue = "";
       String currentprop = "";

       // print the stream tokens
       boolean eof = false;
       do {

          int token = st.nextToken();
          switch (token) {
             case StreamTokenizer.TT_EOF:
                logger.trace("End of File encountered.");
                eof = true;
                break;               
             case StreamTokenizer.TT_EOL:
            	 logger.trace("End of Line encountered.");
                break;
             case StreamTokenizer.TT_WORD:
            	 logger.trace("Word: " + st.sval);
                switch(status) {
                case 0:
                	rule.leftValue = st.sval;
                	status = LVALUE;
                	break;
                case LVALUE_EQ:
                	rule.expression = st.sval;
                	status = EXPRESSION;
                	break;
                case EXPRESSION_COMMA:
                case PROPVALUE_COMMA:
                	currentprop = st.sval;
                	status = PROPNAME;
                	break;
                case PROPNAME_EQ:
                	rule.properties.put(currentprop, st.sval);
                	status = PROPVALUE;
                	break;
                case MACRO:
                	rule.expression = "@" + st.sval;
                	status = MACRO_EXPRESSION;
                	break;
                case CALL:
                	rule.expression = "#" + st.sval;
                	status = CALL_EXPRESSION;
                	break;
                case MACRO_PARAM:
                case MACRO_OPEN:
//	                	rule.expression += st.sval;
                	rule.macroParam.add(st.sval);
                	status = MACRO_PARAM;
                	break;
                case CALL_PARAM:
                case CALL_OPEN:
//	                	rule.expression += st.sval;
                	rule.callParam.add(st.sval);
                	status = CALL_PARAM;
                	break;
                	
                }
                break;
                
             case StreamTokenizer.TT_NUMBER:
            	 logger.trace("Number: " + st.nval);
            	 switch(status) {
	                case MACRO_PARAM:
	                case MACRO_OPEN:
	                	rule.macroParam.add(String.valueOf(st.nval));
	                	status = MACRO_PARAM;
	                	break;	 
	                	
	                case CALL_PARAM:
	                case CALL_OPEN:
	                	rule.callParam.add(String.valueOf(st.nval));
	                	status = CALL_PARAM;
	                	break;		                	
            	 }
                break;
             default:
            	 logger.trace((char) token + " encountered.");
                if(token=='(')
                {
                	switch(status)
                	{
                	case MACRO_EXPRESSION:
//	                		rule.expression += "(";
                		status = MACRO_OPEN;
                		break;
                		
                	case CALL_EXPRESSION:
//                		rule.expression += "(";
            		status = CALL_OPEN;
            		break;
                		
                	}
                }
                else if (token==',')
                {
                	switch(status)
                	{
                	case MACRO_OPEN:
                	case MACRO_PARAM:
//	                		rule.expression += ",";
                		break;
                	}
                }
                else if(token==')')
                {
                	switch(status)
                	{
                	case MACRO_PARAM:
                	case CALL_PARAM:
//	                		rule.expression += ")";
                		status = EXPRESSION;
                		break;
                	}	                	
                }
                else if(token=='@')
                {
                	switch(status)
                	{
                	case LVALUE_EQ:
                		status = MACRO;
                		break;
                	}
                }
                else if(token=='#')
                {
                	switch(status)
                	{
                	case LVALUE_EQ:
                		status = CALL;
                		break;
                	}
                }                
                else if (token == '"') {
                	logger.trace("Word after '\"': " + st.sval);
                	switch(status)
                	{
	                	case PROPNAME_EQ:
	                		rule.properties.put(currentprop, st.sval);
	                		status = PROPVALUE;
	                	break;
		                case MACRO_PARAM:
		                case MACRO_OPEN:
		                	rule.macroParam.add(st.sval);
		                	status = MACRO_PARAM;
		                	break;
		                	
		                case CALL_PARAM:
		                case CALL_OPEN:
		                	rule.callParam.add("\"" + st.sval + "\"");
		                	status = CALL_PARAM;
		                	break;	            	 	
		                	
	                	
                	}
                	
                }
                else if(token == '=')
                {
                	switch(status)
                	{
                	case LVALUE:
                		status = LVALUE_EQ;
                		break;
                	case PROPNAME:
                		status = PROPNAME_EQ;
                		break;
                	}
                		
                }
                else if(token == ';')
                {
                	switch(status)
                	{
                	case EXPRESSION:
                	case MACRO_EXPRESSION:
                	case CALL_EXPRESSION:
                		status = EXPRESSION_COMMA;
                		break;
                	case PROPVALUE:
                		status = PROPVALUE_COMMA;
                		break;
                	}	                	
                }
          }
       } while (!eof);
       
       return rule;
			
	}
	

}
