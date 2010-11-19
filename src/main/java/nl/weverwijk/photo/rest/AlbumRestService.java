package nl.weverwijk.photo.rest;

import nl.weverwijk.photo.rest.entity.Directory;
import nl.weverwijk.photo.rest.filter.DirFileFilter;
import nl.weverwijk.photo.rest.filter.ImageFileFilter;
import nl.weverwijk.photo.rest.properties.PropertiesHelper;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Path("/album")
@RolesAllowed("friend")
public class AlbumRestService {

    @Path("photo")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed("friend")
    public List<Photo> getPhotos() {
        return getPhotos("");
    }

    @Path("photo/{many:.*}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed("friend")
    public List<Photo> getPhotos(@PathParam("many") final String directory) {
        String searchDirectory = PropertiesHelper.PHOTO_BASE_LOCATION + directory;
        System.out.printf("The searchDirectory is: %s", searchDirectory);

        final List<Photo> result = new ArrayList<Photo>();
        final Properties props = PropertiesHelper.getProperties(directory);
        File path = new File(searchDirectory);

        final File[] files = path.listFiles(new ImageFileFilter());
        if (files != null) {
            for (File file : files) {
                String name = props.getProperty(file.getName() + ".name", file.getName());
                String description = props.getProperty(file.getName() + ".description", "<description>");
                Photo photo = new Photo(file.getName(), name, description, "images/" + file.getName(), String.format("images/%s/small/", directory) + file.getName());
                result.add(photo);
            }
        }
        return result;
    }

    @Path("directory")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed("friend")
    public List<Directory> getDirs(@Context SecurityContext securityContext) {
        return getDirs(securityContext, "");
    }

    @Path("directory/{many:.*}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Directory> getDirs(@Context SecurityContext securityContext, @PathParam("many") String directory) {
        System.out.println("Role admin? " + securityContext.isUserInRole("admin"));
        System.out.println("Role friend? " + securityContext.isUserInRole("friend"));
        List<Directory> result = new ArrayList<Directory>();
        String searchDirectory = PropertiesHelper.PHOTO_BASE_LOCATION + "/" + directory;
        System.out.printf("The searchDirectory is: %s", searchDirectory);
        File path = new File(searchDirectory);

        File[] files = path.listFiles(new DirFileFilter());
        if (files != null) {
            for (File file : files) {
                if (!file.getName().equals("small")) {
                    result.add(new Directory(file.getName()));
                }
            }
        }
        return result;
    }
}