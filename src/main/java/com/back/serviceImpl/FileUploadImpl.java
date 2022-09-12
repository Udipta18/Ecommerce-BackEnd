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
		
		//to get original name of file
		String originalFileName=file.getOriginalFilename();
		//generating random id
		 String randomNameId = UUID.randomUUID().toString();
	        String randomNameWithExtension = randomNameId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
	        
	        //file.separator basically works as "/" in unix and "//" in java so to unified that we use file.separator
	        String fullPath = path + File.separator + randomNameWithExtension;
	        File folderFile = new File(path);

	        if (!folderFile.exists()) {
	            folderFile.mkdirs(); //this line create folder directories and also used for create multiple folders
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
