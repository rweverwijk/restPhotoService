package nl.weverwijk.photo.rest;

import java.io.File;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.weverwijk.photo.rest.properties.PropertiesHelper;

@Path("/photo")
public class PhotoRestService {
	public static final String PHOTO_BASE_LOCATION = "D:/data/documenten/My Pictures/2009_01_09/jpg";
//	public static final String PHOTO_BASE_LOCATION = "/Users/rolin/projects/rweverwijk/restPhotoService/src/main/webapp/images";	
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("{id}/")
	public Photo getPhoto(@PathParam("id") String id) {
		return getPhoto("", id);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("{many:.*}/{id}/")
	public Photo getPhoto(@PathParam("many") String directory, @PathParam("id") String id) {
		System.out.printf("getPhoto(%s, %s) called", directory, id);
		Properties props = PropertiesHelper.getProperties(directory);
		File image = new File(PHOTO_BASE_LOCATION + "/" + directory + "/" + id);
		String name = props.getProperty(image.getName() + ".name", image.getName());
		String description = props.getProperty(image.getName() + ".description", "<description>");
		Photo photo = new Photo(image.getName(), name, description, "images/" + image.getName(), "images/small/" + image.getName());
		return photo;
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void savePhoto(Photo photo) {
		if (photo != null) {
			System.out.println("savePhoto: " + photo);
			saveChanges(photo);
		} else {
			System.out.println("savePhoto: photo is null");
		}
	}
	
	private void saveChanges(Photo photo) {
		Properties props = PropertiesHelper.getProperties(photo.getDirName());
		if (photo.getName() != null) {
			props.put(photo.getFileName() + ".name", photo.getName());
		}
		if (photo.getDescription() != null) {
			props.put(photo.getFileName() + ".description", photo.getDescription());
		}
		PropertiesHelper.saveProperties(photo.getDirName(), props);
	}
}