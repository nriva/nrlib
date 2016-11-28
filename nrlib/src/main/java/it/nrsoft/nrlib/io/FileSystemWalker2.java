package it.nrsoft.nrlib.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.nrsoft.nrlib.util.RecursiveWalkerSubject;

public class FileSystemWalker2 extends RecursiveWalkerSubject {


	@Override
	protected String[] getChildrenNodes(String nodeName) {

		File file = new File(nodeName);
		List<File> children = new ArrayList<File>(file.listFiles().length);
		for(File child : file.listFiles())
			if(child.isDirectory())
				children.add(child);
		
		String[] names = new String[children.size()];
		int i=0;
		for(File child:children)
			names[i++] = child.getAbsolutePath();
		return names;
	}

	@Override
	protected String[] getChildrenElements(String nodeName) {
		File file = new File(nodeName);
		List<File> children = new ArrayList<File>(file.listFiles().length);
		for(File child : file.listFiles())
			if(!child.isDirectory())
				children.add(child);
		
		String[] names = new String[children.size()];
		int i=0;
		for(File child:children)
			names[i++] = child.getAbsolutePath();
		return names;	}

}
