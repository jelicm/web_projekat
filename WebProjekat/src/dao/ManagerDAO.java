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

import beans.Manager;

public class ManagerDAO {

	private HashMap<String, Manager> managers = new HashMap<String, Manager>();
	private String path;
	
	public ManagerDAO() {
	}
	
	public ManagerDAO(String contextPath) {
		this.path = contextPath;
		loadManagers();
	}

	public Collection<Manager> findAllManagers() {
		return managers.values();
	}

	public Manager findManager(String username) {
		return managers.containsKey(username) ? managers.get(username) : null;
	}
	
	public Manager addOrModifyManager(Manager manager) {
		managers.put(manager.getUsername(), manager);
		saveManagers();
		return manager;
	}
	
	public Manager updateManager(Manager manager, String username) {
		if(!manager.getUsername().equals(username))
		{
			if(managers.containsKey(username))
				return null;
		}
		managers.put(username, manager);
		saveManagers();
		return manager;
	}
	public Manager deleteManager(String username) {
		Manager m = managers.remove(username);
		saveManagers();
		return m;
	}

	@SuppressWarnings("unchecked")
	private void loadManagers() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/data/managers.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Manager.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.managers = ((HashMap<String, Manager>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringManagers = objectMapper.writeValueAsString(managers);
				fileWriter.write(stringManagers);
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
	
	public void saveManagers() {
		File f = new File(path + "/data/managers.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringManagers = objectMapper.writeValueAsString(this.managers);
			fileWriter.write(stringManagers);
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
