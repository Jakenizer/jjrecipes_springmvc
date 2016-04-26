package se.jjrecipes.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {
	
	private static Logger logger = LoggerFactory.getLogger(JSONConverter.class); //slf4j logger
	
	public static IngredientFromJSON toIngredientPojo(String json) {
		ObjectMapper mapper = new ObjectMapper();
		IngredientFromJSON obj = null;
		
		try {
			obj = mapper.readValue(json, IngredientFromJSON.class);
		} catch (IOException e) {
			logger.error("Error when converting JSON string to POJO");
			logger.error(e.getMessage());
		}

		return obj;
	}
}
