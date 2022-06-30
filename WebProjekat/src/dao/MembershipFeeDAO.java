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

import beans.MembershipFee;
import beans.Training;

public class MembershipFeeDAO {
	private HashMap<String, MembershipFee> membershipFees = new HashMap<String, MembershipFee>();
	private String path;
	
	public MembershipFeeDAO() {
	}
	
	public MembershipFeeDAO(String contextPath) {
		this.path = contextPath;
		loadMembershipFees();
	}

	public Collection<MembershipFee> findAllMembershipFees() {
		return membershipFees.values();
	}

	public MembershipFee findMembershipFee(String identifier) {
		return membershipFees.containsKey(identifier) ? membershipFees.get(identifier) : null;
	}
	
	public MembershipFee addMembershipFee(MembershipFee membershipFee) {
		membershipFees.put(membershipFee.getIdentifier(), membershipFee);
		saveMembershipFees();
		return membershipFee;
	}
	
	public MembershipFee deleteMembershipFee(String identifier) {
		MembershipFee mf = membershipFees.remove(identifier);
		saveMembershipFees();
		return mf;
	}
	
	public void updateMembershipFee(MembershipFee mf) {
		membershipFees.put(mf.getIdentifier(), mf);
		saveMembershipFees();
	}

	@SuppressWarnings("unchecked")
	private void loadMembershipFees() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/data/membershipFees.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, MembershipFee.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.membershipFees = ((HashMap<String, MembershipFee>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringMembershipFees = objectMapper.writeValueAsString(membershipFees);
				fileWriter.write(stringMembershipFees);
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
	
	public void saveMembershipFees() {
		File f = new File(path + "/data/membershipFees.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringMembershipFees = objectMapper.writeValueAsString(this.membershipFees);
			fileWriter.write(stringMembershipFees);
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
