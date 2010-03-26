package nl.weverwijk.photo.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.weverwijk.photo.rest.entity.Directory;
import nl.weverwijk.photo.rest.filter.DirFileFilter;

@Path("/directory")
public class DirectoryRestService {	

	@Path("directory")
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Directory> getDirs() {
		return getDirs("");
	}
	
	@Path("directory/many:.*}")
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Directory> getDirs(@PathParam("many") String directory) {
		List<Directory> result = new ArrayList<Directory>();
		String searchDirectory = PhotoRestService.PHOTO_BASE_LOCATION + "/" + directory;
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