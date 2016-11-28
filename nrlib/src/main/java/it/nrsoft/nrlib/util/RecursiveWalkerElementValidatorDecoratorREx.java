package it.nrsoft.nrlib.util;

import java.util.regex.*;

public class RecursiveWalkerElementValidatorDecoratorREx extends RecursiveWalkerElementValidatorDecorator
{
    private Pattern pattern;

    public RecursiveWalkerElementValidatorDecoratorREx(RecursiveWalkerElementValidator other,String regularExpression)
        
    {
    	super(other);
    	pattern = Pattern.compile(regularExpression);
    }

    public String ChangeElementBeforeMatch(String elementName)
    {
        return elementName;
    }

    @Override
    public boolean ValidateElement(String elementName)
    {
        boolean ok = super.ValidateElement(elementName);
        if (ok)
        {
            Matcher matcher = pattern.matcher(elementName);
            ok = matcher.find();
        }
        return ok;
    }
}
