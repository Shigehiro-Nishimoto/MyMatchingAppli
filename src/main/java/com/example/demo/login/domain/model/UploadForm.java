package com.example.demo.login.domain.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadForm {
	
  private List<MultipartFile> file;

}