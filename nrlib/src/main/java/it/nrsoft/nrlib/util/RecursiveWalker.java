package it.nrsoft.nrlib.util;

/**
 * Classe stratta per l'esplorazione di strutture ad albero (tipo filesystem).
 * @author riva
 *
 */
public abstract class RecursiveWalker
{

	/**
	 * Numero di elementi trovati.
	 */
    protected int count;
    
/**
 * Numero di elementi trovati.
 * @return
 */
    public int getCount() { return count; }

    /**
     * 
     * @param elementName
     * @return <code>true</code> per proseguire, <code>false</code> altrimenti
     */
    protected abstract boolean found(String elementName);

    /**
     * 
     * @param folderName
     * @return
     */
    protected abstract String[] getChildrenNodes(String nodeName);

    /**
     * 
     * @param folderName
     * @return
     */
    protected abstract String[] getChildrenElements(String nodeName);
    
    protected RecursiveWalkerElementValidator validator;

    public RecursiveWalkerElementValidator getValidator()
    {
        return validator;
    }    
        
    public RecursiveWalkerElementValidator setValidator(RecursiveWalkerElementValidator validator)
    {
        return this.validator = validator;
    }

    private boolean walkNode(String nodeName)
    {
        boolean ok = true;
        String[] listOfElements = getChildrenElements(nodeName);
        if (listOfElements != null)
        {
            for (String elementName : listOfElements)
                if (validator.ValidateElement(elementName))
                {
                    ok = found(elementName);
                    if (!ok)
                        break;
                    count++;
                }
        }
        return ok;
    }


    /**
     * Esplora una struttura gerarchica.
     * @param folder
     * @param currentLevel
     * @param toLevel
     * @return
     */
    private boolean walk(String node, int currentLevel, int toLevel)
    {
        boolean ok = false;
        count = 0;
        ok = walkNode(node);
        if (ok)
        {
            String[] children = getChildrenNodes(node);
            if(children!=null)
	            for (String child : children)
	            {
	                if (ok && (currentLevel < toLevel || toLevel == -1))
	                    ok = walk(child, currentLevel + 1, toLevel);
	                if (!ok) break;
	            }
        }
        return ok;
    }

    protected String root = "";

    public void walk(String root, int toLevel)
    {
        this.root = root;
        walk(root, 0, toLevel);
    }

    public void walk(String root)
    {
    	this.root = root;
        walk(root, 0, -1);
    }


}