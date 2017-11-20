package com.personiv.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.personiv.dao.FileUploaderDao;
import com.personiv.model.FileItem;
import com.personiv.model.Reservation;
import com.personiv.service.ReservationService;

@RestController
public class ReservationController {
	
	private static final String UPLOADED_FOLDER ="/uploads";
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private FileUploaderDao fileUploader;
	@RequestMapping(path= "/reservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reservation> getReservations(){
		return reservationService.getReservations();
	}
	
	@RequestMapping(path= "/upload2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> singleFileUpload(@RequestParam("file") MultipartFile file) {

  

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

   
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(file);
    }
	
	@RequestMapping(path= "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

		FileItem fileItem = null;

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            //Files.write(path, bytes);
            
            fileItem = new FileItem();
            fileItem.setFileName(file.getOriginalFilename());
            fileItem.setContent(bytes);
            fileItem.setContentType(file.getContentType());
            fileUploader.uploadFile(fileItem);
            
   
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(fileItem);
    }
	
	@RequestMapping(path= "/upload/getUploads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FileItem> getUploads(){
		return fileUploader.getDocuments();
	}
	
	@RequestMapping(path= "/upload/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FileItem getImage(@PathVariable("id")Long id){
		return fileUploader.getDocument(id);
	}
	
	@RequestMapping(value = "/upload/imageDisplay/{id}", method = RequestMethod.GET)
	public void showImage(@PathVariable("id") Long id, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{
	
	
	    FileItem item = fileUploader.getDocument(id);        
	    response.setContentType(item.getContentType());
	    response.getOutputStream().write(item.getContent());
	
	
	    response.getOutputStream().close();
	}
}
