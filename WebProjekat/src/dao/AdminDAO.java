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

import beans.Admin;

public class AdminDAO {

	private HashMap<String, Admin> admins = new HashMap<String, Admin>();
	private String path;
	
	public AdminDAO() {
	}
	
	public AdminDAO(String contextPath) {
		this.path = contextPath;
		loadAdmins();
	}

	public Collection<Admin> findAllAdmins() {
		return admins.values();
	}

	public Admin findAdmin(String username) {
		return admins.containsKey(username) ? admins.get(username) : null;
	}
	
	public Admin addOrModifyAdmin(Admin admin) {
		admins.put(admin.getUsername(), admin);
		saveAdmins();
		return admin;
	}
	
	public void updateAdmin(Admin admin, String username) {
		if(!admin.getUsername().equals(username))
		{
			admins.remove(username);
		}
		admins.put(admin.getUsername(), admin);
		saveAdmins();
	}
	
	public Admin deleteAdmin(String username) {
		Admin a = admins.remove(username);
		saveAdmins();
		return a;
	}

	@SuppressWarnings("unchecked")
	private void loadAdmins() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/data/admins.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Admin.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.admins = ((HashMap<String, Admin>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringAdmins = objectMapper.writeValueAsString(admins);
				fileWriter.write(stringAdmins);
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
	
	public void saveAdmins() {
		File f = new File(path + "/data/admins.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringAdmins = objectMapper.writeValueAsString(this.admins);
			fileWriter.write(stringAdmins);
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
