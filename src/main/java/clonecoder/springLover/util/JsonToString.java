package clonecoder.springLover.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonToString {
    public static Map<String, String> getJsonStringMap(HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getReader());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        return map;
    }
}
