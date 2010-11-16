package nl.weverwijk.photo.rest;

import nl.weverwijk.photo.rest.properties.PropertiesHelper;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.Properties;

@Path("/photo")
public class PhotoRestService {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}/")
    public Photo getPhoto(@PathParam("id") String id) {
        return getPhoto("", id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{many:.*}/{id}/")
    public Photo getPhoto(@PathParam("many") String directory, @PathParam("id") String id) {
        System.out.printf("getPhoto(%s, %s) called", directory, id);
        Properties props = PropertiesHelper.getProperties(directory);
        File image = new File(PropertiesHelper.PHOTO_BASE_LOCATION + "/" + directory + "/" + id);
        String name = props.getProperty(image.getName() + ".name", image.getName());
        String description = props.getProperty(image.getName() + ".description", "<description>");
        return new Photo(image.getName(), name, description, "images/" + image.getName(), "images/small/" + image.getName());
    }


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
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