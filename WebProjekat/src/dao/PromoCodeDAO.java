package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.PromoCode;

public class PromoCodeDAO {
	private HashMap<String, PromoCode> promoCodes = new HashMap<String, PromoCode>();
	private String path;
	
	public PromoCodeDAO() {
	}
	
	public PromoCodeDAO(String contextPath) {
		this.path = contextPath;
		loadPromoCodes();
	}

	public Collection<PromoCode> findAllTrainings() {
		return promoCodes.values();
	}

	public PromoCode findPromoCode(String identifier) {
		return promoCodes.containsKey(identifier) ? promoCodes.get(identifier) : null;
	}
	
	public void updatePromoCode(PromoCode pc) {
		promoCodes.put(pc.getIdentifier(), pc);
		savePromoCodes();
	}
	
	public PromoCode addPromoCode(PromoCode promoCode) {
		promoCodes.put(promoCode.getIdentifier(), promoCode);
		savePromoCodes();
		return promoCode;
	}

	@SuppressWarnings("unchecked")
	private void loadPromoCodes() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/data/promoCodes.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, PromoCode.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.promoCodes = ((HashMap<String, PromoCode>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringPromoCodes = objectMapper.writeValueAsString(promoCodes);
				fileWriter.write(stringPromoCodes);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileWriter != null) {
					try {
						fileWriter.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void savePromoCodes() {
		File f = new File(path + "/data/promoCodes.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringPromoCodes = objectMapper.writeValueAsString(this.promoCodes);
			fileWriter.write(stringPromoCodes);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
