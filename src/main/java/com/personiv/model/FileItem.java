package com.personiv.model;

import lombok.Data;

@Data
public class FileItem {
	private Long id;
	private String fileName;
	private String contentType;
	private byte[] content;
	
}
