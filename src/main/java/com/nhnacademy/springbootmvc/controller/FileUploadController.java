package com.nhnacademy.springbootmvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class FileUploadController {
    private static final String UPLOAD_DIR = "/Users/user/Downloads/";

    @PostMapping("/fileUpload")
    public String processUpload(@RequestParam("file") MultipartFile file,
                                // TODO #3 `@Value` 사용해서 properties에 설정한 파일 업로드 경로 사용
                                @Value("${upload.dir}") String uploadDir,
                                Model model) throws IOException {
        file.transferTo(Paths.get(uploadDir + file.getOriginalFilename()));

        model.addAttribute("fileName", file.getOriginalFilename());
        model.addAttribute("size", file.getSize());

        return "uploadSuccess";
    }
}
