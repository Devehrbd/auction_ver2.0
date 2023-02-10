package org.zerock.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.ProductPicVO;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/upload/*")
//@AllArgsConstructor
public class UploadController {

	
		@PostMapping("/uploadImages")
		public ResponseEntity<List<ProductPicVO>> uploadImages(@RequestParam("uploadImgs")MultipartFile[] uploadFile, Model model){			
			
			
			
			
			String uploadFolder = "/home/ubuntu/upload";
			String voUploadFolder = "/upload";
			List<ProductPicVO> picList = new ArrayList<ProductPicVO>();
			
			
			for(MultipartFile multipartFile : uploadFile) {
				ProductPicVO productPic = new ProductPicVO();
				String fileName = multipartFile.getOriginalFilename();
				String fileCutName = fileName.substring(0,fileName.lastIndexOf("."));
				String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);			
				
				
				
				File saveFile = new File(uploadFolder, fileName);
				// 저 경로에 저 파일이름이 존재하냐? true 라면
				if(saveFile.exists()) {
					int index = 0;
					boolean fileExist = true;
					while(fileExist) {
						index ++;
						fileName = fileCutName + "(" + index + ")" +"."+ fileExt;
						saveFile = new File(uploadFolder,fileName);
						if(saveFile.exists() == false) {
							fileExist = false;
						}
					}
				}
				
				productPic.setPicture_name(fileName);
				productPic.setPicture_path(voUploadFolder);
				
				try {
					picList.add(productPic);
					multipartFile.transferTo(saveFile);
				}catch(Exception e) {
					
				}
			}
			
			model.addAttribute("imageList", picList);

			
			return new ResponseEntity<List<ProductPicVO>>(picList, HttpStatus.OK);
				
		}
	
}
