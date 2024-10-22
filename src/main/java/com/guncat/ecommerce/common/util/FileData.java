package com.guncat.ecommerce.common.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 파일 업로드, 다운로드 기능의 Controller.<br/>
 * 가용 확장자 정의, Path Traversal 방지.<br/>
 *
 * @see MultipartFile
 * @see File
 * @see FileCopyUtils
 * @see UUID
 * @see HttpServletResponse
 * @author <a href="https://github.com/bluewhale36">bluewhale36</a>
 */
@Controller
public class FileData {

    /**
     * 파일 업로드 시 허용되는 확장자 정의.
     */
    private final ArrayList<String> extNameArr = new ArrayList<String>() {
        {
            add(".jpg");
            add(".png");
            add(".jpeg");
            add(".gif");
            add(".html");
        }
    };

    private final String filePath;

    @Autowired
    FileData(String filePath) {
        this.filePath = filePath;
    }

    /**
     * View 에 파일 응답.
     *
     * @param fileName 요청하는 파일 명.
     * @param response {@code HttpHeader} 지정.
     * @return {@link FileSystemResource} 실제 파일.<br/>
     * {@code null} 잘못된 요청.
     */
    @GetMapping("/download")
    @ResponseBody
    public FileSystemResource downloadFile(@RequestParam("filename") String fileName,
                                           HttpServletResponse response) {
        // 요청 파라미터 존재할 경우.
        if (!fileName.isBlank()) {
            // "/" 가 있을 경우로 분기점 형성. StringIndexOutOfBoundException 방지.
            if (fileName.contains("/")) {
                // 파일 경로 조작 (Path Traversal) 방지.
                // ../../../../../../etc/pswd 등의 경우 방지.
                // fileName 에는 파일 명 및 확장자가 대입됨.
                fileName = fileName.substring(fileName.lastIndexOf("/"));
            }
            // 상기 명시한 확장자의 요청 여부로 분기점 형성. StringIndexOutOfBoundException 방지.
            if (fileName.contains(".") &&
                    extNameArr.contains(fileName.substring(fileName.lastIndexOf(".")))) {
                // 파일 경로에 대한 File 객체 생성.
                File file = new File(filePath + "/" + fileName);
                // 응답 Content Type 지정.
                response.setContentType("application/download; charset=utf-8");
                // 응답 Header 지정.
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                // 파일 응답.
                return new FileSystemResource(file);
            }
        }
        // 요청 파일이 존재하지 않거나, 잘못된 요청의 경우 null 반환.
        return null;
    }

    /**
     * MultipartFile 객체를 파일로 저장.
     *
     * @param files {@link MultipartFile} 배열. HTML{@code input:file} 태그의 value.
     * @return {@code String[]} 저장되는 파일의 새로운 파일 명.
     * @throws IOException 파일 저장 과정에서 발생하는 예외 처리.
     */
    public String[] uploadFile(MultipartFile[] files) throws IOException {
        // 저장되는 파일의 새로운 파일 명 저장 배열.
        String[] fileNameArr = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            // 배열 내 실제 파일의 존재 여부로 분기점 형성.
            if (!files[i].isEmpty()) {
                // 실제 파일의 파일 명 반환.
                String originalFileName = files[i].getOriginalFilename();
                // NullPointerException 방지.
                if (originalFileName != null) {
                    // 파일 확장자 반환. ex) .jpg, .png, .html, ...
                    String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                    // 상기 허용 확장자 명에 현재 파일 확장자가 포함되어 있을 경우 파일 업로드 진행.
                    if (extNameArr.contains(ext)) {
                        // 랜덤한 파일 명 생성.
                        UUID uuid = UUID.randomUUID();
                        // 업로드 및 DB에 저장되는 파일의 이름. 랜덤 파일 명 + 실제 파일 확장자.
                        String saveFileName = uuid + ext;
                        // 실제 파일을 바이트로 변환.
                        byte[] fileData = files[i].getBytes();
                        // 파일 저장 경로 및 새로운 파일 명으로 File 객체 생성.
                        File target = new File(filePath, saveFileName);
                        // 바이트로 변환한 실제 파일을 File 객체의 정보로써 저장.
                        FileCopyUtils.copy(fileData, target);
                        // 저장된 파일의 새로운 파일 명 저장.
                        fileNameArr[i] = saveFileName;
                    }
                }
            }
        }
        // 저장된 파일의 새로운 파일 명.
        return fileNameArr;
    }

}
