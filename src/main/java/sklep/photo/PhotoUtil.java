package sklep.photo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PhotoUtil {
	@Value("${alx.photo_dir}")
	private String photoDir;

	private static final String EXT = ".jpg";

	public File getFile(int productId) {
		Path path = getPath(productId);
		File file = path.toFile();
		if (file.exists()) {
			return file;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot read photo for product id = " + productId);
		}
	}

	public byte[] readBytes(int productId) {
		Path path = getPath(productId);
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			// System.err.println(e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot read photo for product id = " + productId);
		}
	}

	public void writeStream(int productId, InputStream inputStream) {
		try {
			Path path = getPath(productId);
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			// wypisujemy błąd, ale metoda kończy się normalnie
			e.printStackTrace();
		}
	}

	private Path getPath(int productId) {
		String fileName = productId + EXT;
		return Paths.get(photoDir, fileName);
	}

}
