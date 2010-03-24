package nl.weverwijk.photo.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
	private static final String PHOTO_BASE_LOCATION = "D:\\data\\documenten\\My Pictures\\2009_01_09\\jpg";
//	private static final String PHOTO_BASE_LOCATION = "/Users/rolin/projects/rweverwijk/restPhotoService/src/main/webapp/images";	

	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Photo> getPhotos() {
		List<Photo> result = new ArrayList<Photo>();
		Properties props = PropertiesHelper.getProperties(null);
		File path = new File(PHOTO_BASE_LOCATION);
		String[] files = path.list();
		if (files != null) {
			for (String fileName : files) {
				if (fileName.endsWith(".jpg")) {
					String name = props.getProperty(fileName + ".name", fileName);
					String description = props.getProperty(fileName + ".description", "<description>");
					Photo photo = new Photo(fileName, name, description, "images/" + fileName, "images/small/" + fileName);
					result.add(photo);
				}
			}
		}
		return result;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Path("{id}/")
	public Photo getPhoto(@PathParam("id") String id) {
		List<Photo> result = new ArrayList<Photo>();
		File path = new File(PHOTO_BASE_LOCATION);
		String[] files = path.list();
		if (files != null) {
			for (String fileName : files) {
				Photo photo = new Photo(fileName, fileName, "pietje" + fileName, "images/" + fileName, "images/small/" + fileName);
				result.add(photo);
			}
		}
		return result.get(0);
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