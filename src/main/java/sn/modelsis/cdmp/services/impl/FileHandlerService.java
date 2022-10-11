package sn.modelsis.cdmp.services.impl;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlerService {
	@Value("${server.document_folder}")
	private String documentFolder;

	public String fileUpload(MultipartFile file, String filePath, String fileName) throws IOException {
		File newDocumentFolder = new File(documentFolder, filePath);
		newDocumentFolder.mkdirs();
		File[] existingFiles = newDocumentFolder.listFiles();
		if(existingFiles.length !=0) {
		  int i = 0;
		  for (File f : existingFiles) {
            if(f.getName().contains(fileName)) {
              i++;
            }
          }
		  if(i!= 0) {
		    fileName+="_"+i;
		  }
		}
		String fileUrl = documentFolder + "/" + filePath + "/";
		file.transferTo(new File(fileUrl + fileName));
		return fileUrl + fileName; 
	}

	public void deleteFile(String filePath, String fileName) throws IOException {
		File file = new File(documentFolder + "/" + filePath + "/" + fileName);
		file.delete();
	}

	public void fileUpdate(MultipartFile file, String filePath, String oldfileName, String newFileName)
			throws IOException {
		File fileToDelete = new File(documentFolder + "/" + filePath + "/" + oldfileName);
		fileToDelete.delete();
		file.transferTo(new File(documentFolder + "/" + filePath + "/" + newFileName));
	}

}
