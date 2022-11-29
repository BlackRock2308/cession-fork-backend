package sn.modelsis.cdmp.util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Util {

  public Util() {
    // Utility class
  }

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).disable(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
      .registerModule(new JavaTimeModule());

  /**
   * Logs a {@link Exception}
   *
   * @param exception Exception to log
   */
  public static void logException(Exception exception) {
    log.error(exception.getLocalizedMessage());
    log.debug("Exception Stack Trace: ", exception);
  }

  /**
   * Creates an empty set if parameter is null, it's useful when iterating on collections and avoid
   * null-checking before, for example:
   *
   * Given an set, instead of:
   *
   * <pre>
   * if (set != null) {
   *   for(Element element: set){
   *     //code
   *   }
   * }
   * </pre>
   *
   * We'll do:
   *
   * <pre>
   * for(Element element: emptyIfNull(set)){
   *   //code
   * }
   * </pre>
   *
   * @param set: Set of objects
   * @param <T>: Object Type
   * @return Same collection, empty set if collection is null
   */
  public static <T> Set<T> emptySetIfNull(Set<T> set) {
    return set == null ? Collections.emptySet() : set;
  }


  /**
   * Read a {@link MultipartFile} an return a String
   *
   * @param multipartFile The multipartFile to read
   * @return the MultipartFile string value;
   */
  public static String multipartFileAsString(MultipartFile multipartFile) {
    try (Reader reader = new InputStreamReader(multipartFile.getInputStream(),
        StandardCharsets.UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      logException(e);
      return null;
    }
  }

  /**
   * Set the % for like queries
   *
   * @param value The stro to add %
   * @return String
   */
  public static String concatPercentage(String value) {
    return String.format("%%%s%%", value);
  }

  /**
   * Convert the object into it string representation
   *
   * @param object The entity object to convert
   * @return The string representation of the object
   */
  public static String convertEntityToJsonString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

//  /**
//   * Convert the string object into an object
//   *
//   * @param objectString The string representation of the object to convert
//   * @param objectClass The class of the object to convert
//   * @return The Object contained in the String representation
//   */
//  public static Object convertJsonStringToEntity(String objectString, Class<?> objectClass) {
//    try {
//      return objectMapper.readValue(objectString, objectClass);
//    } catch (IOException e) {
//      logException(e);
//      throw new CustomException("ERROR_OF_JSON_PARSER");
//    }
//  }

  /**
   * Convert id {@link String} to {@link UUID}
   *
   * @param id The identifier to convert
   * @return a {@link UUID}
   */
  public static UUID convertToUUID(String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(String.format("ILLEGAL_ARGUMENT_FOR_UUID", id));
    }
    return uuid;
  }

  /**
   * Get a response entity to return resources
   *
   * @param file The {@link Resource}
   * @return a {@link ResponseEntity} {@link Resource}
   */
  public static ResponseEntity<Resource> getResourceResponseEntity(Resource file)
      throws IOException {
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(
        "CONTENT_DISPOSITION",
        String.format("CONTENT_DISPOSITION_VALUE", file.getFilename()));
    final InputStreamResource resource = new InputStreamResource(file.getInputStream());
    return ResponseEntity.ok().headers(httpHeaders).contentLength(file.contentLength())
        .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM).body(resource);
  }

  /**
   * Get a file from the system
   *
   * @param fileName The path and name of the {@link File}
   * @return a {@link File}
   */
  public File getFileFromResource(String fileName) throws URISyntaxException {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException(String.format("FILE_NOT_FOUND", fileName));
    } else {
      return new File(resource.toURI());
    }
  }

  /**
   * Get a file from the system
   *
   * @param fileName The path and name of the {@link File}
   * @return a {@link File}
   */
  public InputStream getInputStreamFromResource(String fileName) {

    ClassLoader classLoader = getClass().getClassLoader();
    return classLoader.getResourceAsStream(fileName);
  }


  /**
   * Get a {@link Resource} file from the system
   *
   * @param fileName The path and name of the {@link File}
   * @return a {@link Resource}
   */
  public Resource getResource(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    return new ClassPathResource(fileName, classLoader);
  }


  public  String generateRandomPassword(int len) {
    String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
            +"jklmnopqrstuvwxyz!@#$%&";
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++)
      sb.append(chars.charAt(rnd.nextInt(chars.length())));
    return sb.toString();
  }

  public static  Double[] donneStatistiquePaiementPME(String json) {
    json = json.replaceAll("null", "0.0");
    String [] objs = json.split(",");
    Double[] donne = new Double[4];
    for(int i=0; i<objs.length; i++ ){
      donne[i] = Double.parseDouble(objs[i]);
    }
  return donne;
  }

  public static Double[] donneStatistiquePaiementCDMP(String json) {
    json = json.replaceAll("null", "0.0");
    String [] objs = json.split(",");
    Double[] donne = new Double[4];
    for(int i=0; i<objs.length; i++ ){
      donne[i] = Double.parseDouble(objs[i]);
    }
    return donne;
  }

  @SuppressWarnings("unchecked")
  public static <T> Map<String,Object> mergeObjects(T first, T second) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    Class<?> clazz = first.getClass();
    Field[] fields = clazz.getDeclaredFields();
    Map<String,Object> values=new HashMap<>();
    for (Field field : fields) {
      field.setAccessible(true);
      Object value1 = field.get(first);
      Object value2 = field.get(second);
      Object value = (value1 != null) ? value1 : value2;
      values.put(field.getName(),value);
      //field.set(returnValue,value);
      log.info("end:{}",field.getName());
    }
    return values;
  }

}



