package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.SportFacility;
import enums.Status;

public class SportFacilityDAO {
	private HashMap<String, SportFacility> sportFacilities = new HashMap<String, SportFacility>();
	private String path;
	
	public SportFacilityDAO() {
	}
	
	public SportFacilityDAO(String contextPath) {
		this.path = contextPath;
		loadSportFacilities();
	}

	public Collection<SportFacility> findAllSportFacilities() {
		return sportFacilities.values();
	}
	
	public Collection<SportFacility> findAllSportFacilitiesSorted() {
		ArrayList <SportFacility> ret = new ArrayList <SportFacility>();
		for(SportFacility sf : sportFacilities.values())
		{
			if(sf.getStatus()==Status.RADI)
				ret.add(sf);
		}
		for(SportFacility sf : sportFacilities.values())
		{
			if(sf.getStatus()==Status.NE_RADI)
				ret.add(sf);
		}
		return ret;
	}

	public SportFacility findSportFacility(String name) {
		return sportFacilities.containsKey(name) ? sportFacilities.get(name) : null;
	}
	
	public SportFacility addOrModifySportFacility(SportFacility sportFacility) {
		sportFacilities.put(sportFacility.getName(), sportFacility);
		saveSportFacilities();
		return sportFacility;
	}
	
	public SportFacility deleteSportFacility(String name) {
		SportFacility s = sportFacilities.remove(name);
		saveSportFacilities();
		return s;
	}

	@SuppressWarnings("unchecked")
	private void loadSportFacilities() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/data/sportFacilities.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, SportFacility.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.sportFacilities = ((HashMap<String, SportFacility>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringSportFacilities = objectMapper.writeValueAsString(sportFacilities);
				fileWriter.write(stringSportFacilities);
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
	
	public void saveSportFacilities() {
		File f = new File(path + "/data/sportFacilities.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringSportFacilities = objectMapper.writeValueAsString(this.sportFacilities);
			fileWriter.write(stringSportFacilities);
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
