package nl.weverwijk.photo.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/photo")
public class PhotoRestService {

	private static final String PHOTO_BASE_LOCATION = "D:\\data\\documenten\\My Pictures\\2009_01_09\\jpg";

	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public List<Photo> getPhotos() {
		List<Photo> result = new ArrayList<Photo>();
		File path = new File(PHOTO_BASE_LOCATION);
		String[] files = path.list();
		if (files != null) {
			for (String fileName : files) {
				Photo photo = new Photo(fileName, "pietje" + fileName, "images/" + fileName, "images/small/" + fileName);
				result.add(photo);
			}
		}
		return result;
	}
}