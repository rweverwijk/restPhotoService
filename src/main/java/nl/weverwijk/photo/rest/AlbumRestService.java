package nl.weverwijk.photo.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.weverwijk.photo.rest.entity.Directory;
import nl.weverwijk.photo.rest.filter.DirFileFilter;
import nl.weverwijk.photo.rest.filter.ImageFileFilter;
import nl.weverwijk.photo.rest.properties.PropertiesHelper;

@Path("/album")
public class AlbumRestService {	

	@Path("photo")
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Photo> getPhotos() {
		return getPhotos("");
	}
	
	@Path("photo/{many:.*}")
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Photo> getPhotos(@PathParam("many") String directory) {
		String searchDirectory = PropertiesHelper.PHOTO_BASE_LOCATION + "/" + directory;
		System.out.printf("The searchDirectory is: %s", searchDirectory);
		
		List<Photo> result = new ArrayList<Photo>();
		Properties props = PropertiesHelper.getProperties(directory);
		File path = new File(searchDirectory);
		
		File[] files = path.listFiles(new ImageFileFilter());
		if (files != null) {
			for (File file : files) {
				String name = props.getProperty(file.getName() + ".name", file.getName());
				String description = props.getProperty(file.getName() + ".description", "<description>");
				Photo photo = new Photo(file.getName(), name, description, "images/" + file.getName(), "images/small/" + file.getName());
				result.add(photo);
			}
		}
		return result;
	}
	
	@Path("directory")
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Directory> getDirs() {
		return getDirs("");
	}
	
	@Path("directory/{many:.*}")
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Directory> getDirs(@PathParam("many") String directory) {
		List<Directory> result = new ArrayList<Directory>();
		String searchDirectory = PropertiesHelper.PHOTO_BASE_LOCATION + "/" + directory;
		System.out.printf("The searchDirectory is: %s", searchDirectory);
		File path = new File(searchDirectory);
		
		File[] files = path.listFiles(new DirFileFilter());
		if (files != null) {
			for (File file : files) {
				result.add(new Directory(file.getName()));
			}
		}
		return result;
	}
}