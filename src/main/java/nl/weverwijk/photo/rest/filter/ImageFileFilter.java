package nl.weverwijk.photo.rest.filter;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

	private final String[] okFileExtensions = new String[] { "jpg" };
	
	@Override
	public boolean accept(final File file) {
		boolean result = false;
		for (String extension : okFileExtensions) {
			if (file.getName().toLowerCase().endsWith(extension)) {
				result =  true;
			}
		}
		return result;
	}
}
