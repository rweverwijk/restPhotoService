package nl.weverwijk.photo.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhotoType")
@XmlRootElement(name = "Photo")
public class Photo {
	private String name;
	private String description;
	private String originalUrl;
	private String thumbnailUrl;

	public Photo() {
		super();
	}

	public Photo(String fileName, String description, String originalUrl, String thumbnailUrl) {
		super();
		this.name = fileName;
		this.description = description;
		this.originalUrl = originalUrl;
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	
}
