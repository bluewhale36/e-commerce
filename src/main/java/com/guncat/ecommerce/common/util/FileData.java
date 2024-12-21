package com.guncat.ecommerce.common.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
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
@RestController
@RequestMapping("/api/file")
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
            add(".pdf");
        }
    };

    /**
     * 추출한 확장자에 따른 MIME Type 정의.
     */
    private final Map<String, String> extensionMap = Map.of(
            "jpg", "image/jpg",
            "png", "image/png",
            "jpeg", "image/jpeg",
            "gif", "image/gif",
            "html", "text/html",
            "pdf", "application/pdf"
    );

    private final String filePath;

    @Autowired
    FileData(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 다운로드 가능한 파일을 응답한다.<br/>
     * HttpHeader 의 Content-Disposition 은 {@code Content-Disposition: attachment; filename="파일명"} 이다.<br/>
     * 또한 Content-Type 은 {@code Content-Type: application/octet-stream} 이다.
     *
     * @param filename 요청하는 파일 명.
     * @return {@link FileSystemResource} 객체.
     */
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource fileResource = extractFile(filename);

        return ResponseEntity.ok()
                // 파일 다운로드를 위해서는, content type 을 application/octet-stream 으로 지정해야 한다.
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // 파일 다운로드를 위해서는, header 의 content-disposition 속성의 값을 attachment 로 지정해야 한다.
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                // 파일 데이터.
                .body(fileResource);
    }

    /**
     * 파일 리소스 자체를 응답한다.<br/>
     * HttpHeader 의 Content-Disposition 은 {@code Content-Disposition: inline} 이다.<br/>
     * 또한 Content-Type 은 파라미터의 {@code filename} 에서 추출한다.
     *
     * @param filename 요청하는 파일 명.
     * @return {@link ResponseEntity<FileSystemResource>} 객체.
     */
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource fileResource = extractFile(filename);
        String mimeType = extractExtension(filename);

        return ResponseEntity.ok()
                // 파일을 웹 페이지 내에 렌더링 하기 위해서는, content type 이 application/octet-stream 이 아닌 올바른 것이어야 한다.
                .contentType(MediaType.parseMediaType(mimeType))
                // 파일을 웹 페이지 내에 렌더링 하기 위해서는, header 의 content-disposition 속성의 값을 inline 으로 지정해야 한다.
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(fileResource);
    }

    /**
     * 파일 명에 따른 파일 반환.
     *
     * @param filename 반환할 파일의 파일 명
     * @return 파일 명에 따른 파일이 존재할 경우 {@link FileSystemResource} 객체를 반환한다.<br/>
     * @throws InvalidFileNameException 파일 명이 {@code null} 일 경우.
     */
    private Resource extractFile(String filename) {
        // 요청 파라미터 존재할 경우.
        if (!filename.isBlank()) {
            // "/" 가 있을 경우로 분기점 형성. StringIndexOutOfBoundException 방지.
            if (filename.contains("/")) {
                // 파일 경로 조작 (Path Traversal) 방지.
                // ../../../../../../etc/pswd 등의 경우 방지.
                // filename 에는 파일 명 및 확장자가 대입됨.
                filename = filename.substring(filename.lastIndexOf("/"));
            }
            // 상기 명시한 확장자의 요청 여부로 분기점 형성. StringIndexOutOfBoundException 방지.
            if (filename.contains(".") &&
                    extNameArr.contains(filename.substring(filename.lastIndexOf(".")))) {
                // 파일 경로에 대한 File 객체 생성.
                File file = new File(filePath + "/" + filename);
                // 파일 시스템 리소스 생성
                return new FileSystemResource(file);
            }
        }
        // 요청 파일이 존재하지 않거나, 잘못된 요청의 경우 InvalidFileNameException 발생.
        throw new InvalidFileNameException(filename, "잘못된 파일 명입니다.");
    }

    /**
     * 파일 확장자 반환. 매개변수의 파일 명을 기반으로 추출하여 확장자를 반환한다.
     *
     * @param filename 확장자를 추출할 파일 명.
     * @return 파일 명에서 추출한 확장자가 {@link FileData#extensionMap} 의 key 값으로 존재할 경우 그에 해당하는 value 값을 반환한다.<br/>
     * 그렇지 않을 경우 {@code application/octet-stream} 을 반환한다.
     */
    private String extractExtension(String filename) {
        // 확장자 추출 및 MIME 타입 설정
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        // 추출한 확장자 반환. 없을 경우 octet-stream 을 기본으로 반환한다.
        return extensionMap.getOrDefault(extension, "application/octet-stream");
    }

    /**
     * MultipartFile 객체를 파일로 저장.
     *
     * @param files {@link MultipartFile} 배열. HTML {@code input:file} 태그의 value.
     * @return {@code String[]} 저장되는 파일의 새로운 파일 명.
     * @throws IOException 파일 저장 과정에서 발생하는 예외.
     */
    public String[] uploadFile(MultipartFile[] files) throws IOException {
        // 저장되는 파일의 새로운 파일 명 저장 배열.
        String[] filenameArr = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            // 배열 내 실제 파일의 존재 여부로 분기점 형성.
            if (!files[i].isEmpty()) {
                // 실제 파일의 파일 명 반환.
                String originalFilename = files[i].getOriginalFilename();
                // NullPointerException 방지.
                if (originalFilename != null) {
                    // 파일 확장자 반환. ex) .jpg, .png, .html, ...
                    String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
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
                        filenameArr[i] = saveFileName;
                    } else {
                        throw new InvalidFileNameException(originalFilename, "허용되지 않는 확장자입니다.");
                    }
                } else {
                    throw new InvalidFileNameException(originalFilename, "파일 명은 null 값일 수 없습니다.");
                }
            }
        }
        // 저장된 파일의 새로운 파일 명.
        return filenameArr;
    }

}
