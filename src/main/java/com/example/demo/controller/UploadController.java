package com.example.demo.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.login.domain.model.UploadForm;
import com.example.demo.login.domain.service.UserService;

@Controller
public class UploadController {

	@Autowired
	UserService userService;

	@GetMapping("/upload")
	public String getUpload(@ModelAttribute UploadForm form, Model model) {
        return "login/upload";
	}

		//以下、https://qiita.com/t-iguchi/items/a630fe7b6fed3f5c54cdからのコピペ。
	    private String getExtension(String filename) {
	      int dot = filename.lastIndexOf(".");
	      if (dot > 0) {
	        return filename.substring(dot).toLowerCase();
	      }
	      return "";
	    }

	    //ファイル名前加工
	    private String getUploadFileName(String fileName) {

	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String mailaddressnow = auth.getName();
	        int id = userService.Roguinshanoid(mailaddressnow);
	        return String.valueOf(id) + getExtension(fileName);
	    }

	    //保存場所
	    private void createDirectory() {
	        Path path = Paths.get("C:/upload/files");
	        if (!Files.exists(path)) {
	          try {
	            Files.createDirectory(path);
	          } catch (Exception e) {
	            //エラー処理は省略
	          	}
	        }
	    }

	    //
	    private void savefile(MultipartFile file) {
	      String filename = getUploadFileName(file.getOriginalFilename());
	      Path uploadfile = Paths.get("C:/upload/files/" + filename);
	      try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
	        byte[] bytes = file.getBytes();
	        os.write(bytes);
	      } catch (IOException e) {
	        //エラー処理は省略
	      }
	    }

	    private void savefiles(List<MultipartFile> multipartFiles) {
	        createDirectory();
	        for (MultipartFile file : multipartFiles) {
	            savefile(file);
	        }
	    }

	    @RequestMapping(path = "/", method = RequestMethod.GET)
	    String uploadview(Model model) {
	      model.addAttribute("form", new UploadForm());
	      return "index";
	    }

	    @RequestMapping(path = "/upload", method = RequestMethod.POST)
	    String upload(Model model, UploadForm form) {
	      if (form.getFile()==null || form.getFile().isEmpty()) {
	        //エラー処理は省略
	        return "index";
	      }
	      savefiles(form.getFile());
	      return "redirect:/home";
	    }
}
