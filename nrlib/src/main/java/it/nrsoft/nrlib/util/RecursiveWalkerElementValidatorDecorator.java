package it.nrsoft.nrlib.util;

public abstract class RecursiveWalkerElementValidatorDecorator extends RecursiveWalkerElementValidator
{
    protected RecursiveWalkerElementValidator other;

    @Override
    public boolean ValidateElement(String elementName)
    {
        if(other != null)
            return other.ValidateElement(elementName);
        return true;
    }

    public RecursiveWalkerElementValidatorDecorator(RecursiveWalkerElementValidator other)
    {
        this.other = other;
    }
}