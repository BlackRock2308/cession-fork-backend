package sn.modelsis.cdmp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BASE64DecodedMultipartFile implements MultipartFile {
    private final ByteArrayInputStream contenu;
    private final String fileName;

    public BASE64DecodedMultipartFile(ByteArrayInputStream contenu, String fileName) {
        this.contenu = contenu;
        this.fileName = fileName;
    }


    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return contenu;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
