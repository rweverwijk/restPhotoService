package nl.weverwijk.photo.rest.filter;

import java.io.File;
import java.io.FileFilter;

public class DirFileFilter implements FileFilter {

	@Override
	public boolean accept(File file) {
		return file.isDirectory() ? true : false;
	}

}
