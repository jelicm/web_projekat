package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;


import beans.Customer;

public class CustomerDAO {
	private HashMap<String, Customer> customers = new HashMap<String, Customer>();
	private String path;
	
	public CustomerDAO() {
	}
	
	public CustomerDAO(String contextPath) {
		this.path = contextPath;
		loadCustomers();
	}

	public Collection<Customer> findAllCustomers() {
		return customers.values();
	}

	public Customer findCustomer(String username) {
		return customers.containsKey(username) ? customers.get(username) : null;
	}
	
	public Customer addOrModifyCustomer(Customer customer) {
		customers.put(customer.getUsername(), customer);
		saveCustomers();
		return customer;
	}
	
	public void updateCustomer(Customer customer, String username)
	{
		if(!customer.getUsername().equals(username))
		{
			customers.remove(username);
		}
		customers.put(customer.getUsername(), customer);
		saveCustomers();
	}
	
	public Customer deleteCustomer(String username) {
		Customer c = customers.remove(username);
		saveCustomers();
		return c;
	}

	@SuppressWarnings("unchecked")
	private void loadCustomers() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/data/customers.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Customer.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.customers = ((HashMap<String, Customer>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringCustomers = objectMapper.writeValueAsString(customers);
				fileWriter.write(stringCustomers);
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
	
	public void saveCustomers() {
		File f = new File(path + "/data/customers.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringCustomers = objectMapper.writeValueAsString(this.customers);
			fileWriter.write(stringCustomers);
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
