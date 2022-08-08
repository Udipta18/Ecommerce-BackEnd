package com.back.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.back.service.FileUpload;


@Service
public class FileUploadImpl implements FileUpload{

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		String originalFileName=file.getOriginalFilename();
		 String randomNameId = UUID.randomUUID().toString();
	        String randomNameWithExtension = randomNameId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
	        String fullPath = path + File.separator + randomNameWithExtension;
	        File folderFile = new File(path);

	        if (!folderFile.exists()) {
	            folderFile.mkdirs();
	        }
	        
	        Files.copy(file.getInputStream(), Paths.get(fullPath));
	        return randomNameWithExtension;
	}

	@Override
	public InputStream getResource(String path) throws FileNotFoundException {
		InputStream is=new FileInputStream(path);
        return is;
	}

	@Override
	public void deleteFile(String path) {
		// TODO Auto-generated method stub
		
	}

}
