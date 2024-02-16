package com.volka.dynamicbatch.core.util;//package finger.pharos.batch.core.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.*;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
///**
// * @author : volka <volka5091@gmail.com>
// * description    : 파일유틸
// */
//@Component
//public class FileUtil {
//
//
//    /**
//     * 파일 유틸
//     *
//     * @author volka
//     */
//    @Slf4j
//    @Component
//    public class FileUtil {
//        private final FileProperties fileProperties;
//
//        private static Set<String> fileAllowExtSet = null;
//
//        public FileUtil(FileProperties fileProperties) {
//            this.fileProperties = fileProperties;
//        }
//
//
//        @PostConstruct
//        public void init() {
//            try {
//                String os = System.getProperty("os.name").toLowerCase();
//
//                if (os.contains("mac")) {
//                    Path root = Paths.get(System.getProperty("user.home"), "dev", "cbm_files");
//                    fileProperties.setLocation(root.normalize().toString());
//
//                    log.info("normalized root path before set :: {}", root.normalize().toString());
//                    log.info("fileUtil init fileProperties location :: {}", fileProperties.getLocation());
//                }
//
//                if (!Files.exists(Paths.get(fileProperties.getLocation()).toAbsolutePath().normalize())) {
//                    Files.createDirectories(Paths.get(fileProperties.getLocation()).toAbsolutePath().normalize());
//                }
//
//                fileAllowExtSet = Collections.unmodifiableSet(fileProperties.getAllowExtSet());
//
//            } catch (IOException e) {
//                log.error("FileUtil init() error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//            }
//        }
//
//
//        /**
//         * 현재 날짜별 디렉토리명 생성
//         *
//         * @return
//         */
//        private String getDatePath() throws Exception {
//            return String.format("%s%s%s", fileProperties.getLocation(), File.separator, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//        }
//
//        /**
//         * 현재 날짜로 디렉토리 생성 및 경로 리턴
//         *
//         * @return
//         */
//        private Path mkdirDate() throws Exception {
//            String pathStr = getDatePath();
//            File dir = new File(getDatePath());
//
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            return Path.of(pathStr).normalize();
//        }
//
//        /**
//         * 파일 적재 경로(오늘자) 리턴
//         *
//         * @return
//         */
//        public Path getStdLocation() throws Exception {
//            return mkdirDate();
//        }
//
//
//        /**
//         * 시스템내 허용된 파일 확장자인지 검증
//         *
//         * @param fileNm
//         * @return
//         */
//        private boolean isAllowExt(String fileNm) {
//            return fileAllowExtSet.contains(getExt(fileNm).toLowerCase());
//        }
//
//
//        /**
//         * 파일 확장자 리턴 ("." 포함)
//         *
//         * @param fileOriginalName
//         * @return
//         */
//        public String getExt(String fileOriginalName) {
//            try {
//                return fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
//            } catch (BizException e) {
//                log.error("getExt error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("getExt error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("FLE00000");
//            }
//        }
//
//        /**
//         * 확장자를 제외한 파일 원본명 리턴 ("." 제외)
//         *
//         * @param fileOriginalName
//         * @return
//         */
//        public String getFileNmWithoutExt(String fileOriginalName) throws Exception {
//            int dotIndex = fileOriginalName.lastIndexOf(".");
//            return fileOriginalName.substring(0, dotIndex);
//        }
//
//
//        /**
//         * 파일 적재명 중복 검증 및 파일 URI 리턴
//         *
//         * @return
//         * @throws Exception
//         */
//        private Path getValidSveFileUri() throws Exception {
//            Path baseLocation = getStdLocation();
//            Path fileUri = baseLocation.resolve(UUID.randomUUID().toString()).normalize();
//            File file = fileUri.toFile();
//
//            while (file.exists()) {
//                fileUri = baseLocation.resolve(UUID.randomUUID().toString()).normalize();
//                file = fileUri.toFile();
//            }
//
//            return fileUri;
//        }
//
//
//        /**
//         * MultipartFile로 파일 저장
//         *
//         * @param multipartFile
//         * @param fileId
//         * @return
//         */
//        public FileDTO saveFile(MultipartFile multipartFile, String fileId) {
//            try (InputStream fi = multipartFile.getInputStream();) {
//                if (!isAllowExt(multipartFile.getOriginalFilename()))
//                    throw new BizException("FLE00004"); //허용되지 않은 파일 확장자입니다.
//
//                if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isBlank())
//                    throw new BizException("FLE00002"); //파일이름은 공백일 수 없습니다.
//
//                Path validFileUri = getValidSveFileUri();
//
//                Files.copy(fi, validFileUri, StandardCopyOption.REPLACE_EXISTING);
//
//                return FileDTO.builder()
//                        .fileId(fileId)
//                        .fileNm(getFileNmWithoutExt(multipartFile.getOriginalFilename()))
//                        .fileExt(getExt(multipartFile.getOriginalFilename()))
//                        .fileSz(multipartFile.getSize())
//                        .filePth(validFileUri.getParent().toString())
//                        .sveFileNm(validFileUri.getFileName().toString())
//                        .build();
//
//            } catch (IOException e) {
//                log.error("saveFile error :: {}", e.getMessage());
//                throw new BizException("FLE00000"); //파일 에러
//            } catch (BizException e) {
//                log.error("saveFile error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("saveFile error :: {}, {}", e.getMessage(), e.toString());
//                throw new BizException("COE00000");
//            }
//        }
//
//
//        /**
//         * java.io 파일 객체로 파일 저장
//         *
//         * @param file
//         * @param fileId
//         * @return
//         */
//        public FileDTO saveFile(File file, String fileId) {
//            try {
//                if (!isAllowExt(file.getName())) throw new BizException("FLE00004"); //허용되지 않은 파일 확장자입니다.
//
//                Path fileUri = getValidSveFileUri();
//                Files.copy(file.toPath(), fileUri, StandardCopyOption.REPLACE_EXISTING);
//
//                return FileDTO.builder()
//                        .fileId(fileId)
//                        .sveFileNm(fileUri.getFileName().toString())
//                        .fileNm(getFileNmWithoutExt(file.getName()))
//                        .fileExt(getExt(file.getName()))
//                        .filePth(fileUri.getParent().toString())
//                        .fileSz(file.length()) //바이트단위
//                        .build();
//            } catch (IOException e) {
//                log.error("saveFile error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("FLE00000"); //파일 에러
//            } catch (BizException e) {
//                log.error("saveFile error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("saveFile error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            }
//        }
//
//
//        /**
//         * 파일정보로 파일 리소스 반환
//         *
//         * @param fileDTO
//         * @return
//         */
//        public Resource loadFileAsResource(FileDTO fileDTO) {
//            Path filePath = loadFileAsPath(fileDTO);
//
//            try {
//                Resource resource = new UrlResource(filePath.toUri());
//
//                if (resource.exists()) {
//                    return resource;
//                } else {
//                    throw new BizException("FLE00001"); //파일을 찾을 수 없습니다.
//                }
//            } catch (MalformedURLException e) {
//                log.error(e.getMessage());
//                throw new BizException("FLE00003"); //파일 경로 에러
//            }
//
//        }
//
//
//        /**
//         * 파일 정보로 파일 객체 반환
//         *
//         * @param fileDTO
//         * @return
//         */
//        public File loadFile(FileDTO fileDTO) throws FileNotFoundException, BizException, Exception{
//            Path filePath = loadFileAsPath(fileDTO);
//            File file = filePath.toFile();
//
//            if (file.exists()) {
//                return file;
//            } else {
//                throw new BizException("FLE00001"); //파일을 찾을 수 없습니다.
//            }
//        }
//
//        /**
//         * 파일 정보로 파일 Path 객체 반환
//         *
//         * @param fileDTO
//         * @return
//         */
//        public Path loadFileAsPath(FileDTO fileDTO) {
//            try {
//                return Paths.get(fileDTO.getFilePth()).resolve(fileDTO.getSveFileNm()).normalize();
//            } catch (BizException e) {
//                log.error("loadFileAsPath error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("loadFileAsPath error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            }
//        }
//
//
//        /**
//         * 다중 파일 압축 후 zip 파일 리턴
//         * 임시 zipFile은 서비스 계층에서 삭제처리
//         *
//         * @return
//         */
//        public File makeZipFile(List<FileDTO> fileInfoList, Usr usr) {
//
//            FileInputStream fin = null;
//            FileOutputStream fout = null;
//            ZipOutputStream zout = null;
//
//            try {
//
//                /**
//                 * zipFile deleteOnExit 처리 및 zip파일명 지정
//                 */
//                File zipFile = File.createTempFile(String.format("%s_%s", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "파일"), ".zip");
//
//                byte[] buffer = new byte[1024];
//                int length;
//
//                fout = new FileOutputStream(zipFile);
//                zout = new ZipOutputStream(fout);
//
//                for (int i = 0; i < fileInfoList.size(); i++) {
//                    FileDTO fileDTO = fileInfoList.get(i);
//                    Path tmpPath = Paths.get(String.format("%s%s%s%s", fileProperties.getLocation(), File.separator, fileDTO.getFileNm(), fileDTO.getFileExt()));
//
//                    if (tmpPath.toFile().exists()) {
//                        tmpPath = Paths.get(String.format("%s%s%s_%s%s", fileProperties.getLocation(), File.separator, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), fileDTO.getFileNm(), fileDTO.getFileExt()));
//                    }
//
//                    File newFile = tmpPath.toFile();
//
//                    Files.copy(loadFileAsPath(fileDTO), tmpPath, StandardCopyOption.REPLACE_EXISTING);
//                    fin = new FileInputStream(newFile);
//
//
//                    ZipEntry zipEntry = new ZipEntry(newFile.getName());
//                    zout.putNextEntry(zipEntry);
//
//
//                    while ((length = fin.read(buffer)) > 0) {
//                        zout.write(buffer, 0, length);
//                    }
//
//                    zout.closeEntry();
//                    fin.close();
//
//                    newFile.deleteOnExit();
//                }
//
//                zout.close();
//                fout.close();
//
//                if (fin != null) fin.close();
//
//                return zipFile;
//
//
//            } catch (BizException e) {
//                log.error("makeZipFile error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("makeZipFile error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            } finally {
//                try {
//                    if (fin != null) fin.close();
//                    if (fout != null) fout.close();
//                    if (zout != null) zout.close();
//                } catch (IOException e) {
//                    log.error("makeZipFile() I/O Stream close error");
//                }
//            }
//        }
//
//        /**
//         * 중복 파일명 비교
//         *
//         * @param atchFiles
//         * @return
//         */
//        public boolean isEqualFileNm(Map<String, MultipartFile> atchFiles) {
//            try {
//                Set<String> nameSet = new HashSet<>();
//
//                for (MultipartFile file : atchFiles.values()) {
//                    nameSet.add(file.getOriginalFilename());
//                }
//
//                return atchFiles.keySet().size() == nameSet.size();
//
//            } catch (BizException e) {
//                log.error("isEqualFileNm error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("isEqualFileNm error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            }
//        }
//
//
//        /**
//         * 파일삭제
//         *
//         * @param fileDTO
//         * @return
//         */
//        public boolean deleteFile(FileDTO fileDTO) {
//            try {
//                File file = loadFile(fileDTO);
//                if (file.exists()) {
//                    if (file.delete()) {
//                        return true;
//                    } else {
//                        file.deleteOnExit();
//                        return true;
//                    }
//                } else {
//                    throw new BizException("FLE00001"); //파일을 찾을 수 없습니다.
//                }
//            } catch (BizException e) {
//                log.error("deleteFile error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("deleteFile error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            }
//        }
//
//        /**
//         * 파일 -> base64 인코딩 문자열 변환
//         *
//         * @param fileDTO
//         * @return
//         */
//        public String fileToBase64(FileDTO fileDTO) {
//            try {
//                File file = loadFile(fileDTO);
//                return convertFileToBase64(file);
//            } catch (BizException e) {
//                log.error("fileToBase64 error :: {}, {}", e.getErrCd(), e.getErrMsg());
//
//                if(e.getErrCd().equals("FLE00001")) {
//                    return null;
//                }
//
//                throw e;
//
//            } catch (Exception e) {
//                log.error("fileToBase64 error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            }
//        }
//
//
//        /**
//         * 파일 Base64 변환
//         *
//         * @param file
//         * @return
//         */
//        public String convertFileToBase64(File file) {
//            try (FileInputStream fileInputStream = new FileInputStream(file);
//                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ) {
//                int length = 0;
//                byte[] buffer = new byte[1024];
//
//                while ((length = fileInputStream.read(buffer)) != -1) {
//                    byteArrayOutputStream.write(buffer, 0, length);
//                }
//
//                return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
//
//            } catch (BizException e) {
//                log.error("convertFileToBase64 error :: {}, {}", e.getErrCd(), e.getErrMsg());
//                throw e;
//            } catch (Exception e) {
//                log.error("convertFileToBase64 error :: {}, {}", e.getMessage(), e.getCause().getMessage());
//                throw new BizException("COE00000");
//            }
//        }
//
//    }
//
//}
