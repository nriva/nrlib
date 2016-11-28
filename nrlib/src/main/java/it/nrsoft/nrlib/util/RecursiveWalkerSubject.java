package it.nrsoft.nrlib.util;

import it.nrsoft.nrlib.pattern.SubjectHelper;

public abstract class RecursiveWalkerSubject extends RecursiveWalker
{
    protected SubjectHelper subjectHelper = new SubjectHelper();

    public SubjectHelper getSubjectHelper()
    {
        return subjectHelper;
    }

    @Override
    protected boolean found(String elementName)
    {
        // Ritorna false solo per bloccare la ricerca.
        RecursiveWalkerSubjectNotifyData e = new RecursiveWalkerSubjectNotifyData(elementName);
        subjectHelper.notify(e);
        return e.isContinueWalk();
    }
}
