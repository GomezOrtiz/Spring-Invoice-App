package com.fakecorp.invoicing.app.utils.upload;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import com.fakecorp.invoicing.app.CloudinaryConfig;

@Component
public class CloudinaryUploader {
	
	@Autowired
	private CloudinaryConfig cloudinary;
	
	@SuppressWarnings("rawtypes")
	public String upload(MultipartFile file) throws IOException {
		
		Map uploadResult = cloudinary.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		
		return (String) uploadResult.get("secure_url");
	}

}
