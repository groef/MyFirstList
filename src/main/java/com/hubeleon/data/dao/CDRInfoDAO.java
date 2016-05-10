package com.hubeleon.data.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.hubeleon.model.CDRInfo;

public class CDRInfoDAO {
	private static CDRInfoDAO instance = new CDRInfoDAO();

	public static List<CDRInfo> all()
	{
		if (instance.list.isEmpty()){
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
				try (Closeable closeable = ObjectifyService.begin()) {
					System.out.println(" !!!!! #######  get all cdr list");
					List<CDRInfo> ths = ofy().load().type(CDRInfo.class).list();
					System.out.println(" !!!!! size " + ths.size());
					instance.list.addAll(ths);
		        }
			}		
		}
		System.out.println(" Returning list  " + instance.list + " with entries:" + instance.list.size());
		return instance.list;
	}

	public static CDRInfo select(String id)
	{
		for (CDRInfo CDRInfo : all())
		{
			if (CDRInfo.getCdrId().equals(id) )
			{
				return CDRInfo;
			}
		}

		return null;
	}

	public static void insert(CDRInfo cdrInfo)
	{
		if (cdrInfo.getCdrId() != null)
		{
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
				try (Closeable closeable = ObjectifyService.begin()) {
					System.out.println(" #######  Inserting new Entry: " + cdrInfo.getCdrId());
					ofy().save().entity(cdrInfo).now();
		        }
			}					
			all().add(cdrInfo);
		}
	}

	public static void update(CDRInfo cdrInfo)
	{
		CDRInfo updated = select(cdrInfo.getCdrId());

		if (updated != null)
		{			
			updated.setCdrUserReviewStatus(cdrInfo.getCdrUserReviewStatus());
			updated.setCdrStatusType(cdrInfo.getCdrStatusType());
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
				try (Closeable closeable = ObjectifyService.begin()) {
					System.out.println(" #######  Updating entry: " + updated.getCdrId());
					ofy().save().entity(updated).now();
		        }
			}					
			
		}
	}

	public static void delete(CDRInfo cdrInfo)
	{
		CDRInfo deleted = select(cdrInfo.getCdrId());

		if (deleted != null)
		{
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
				try (Closeable closeable = ObjectifyService.begin()) {
					System.out.println(" #######  Deleting entry: " + deleted.getCdrId());
					ofy().delete().entity(deleted).now();
		        }
			}					
			all().remove(deleted);
		}
	}

	private final List<CDRInfo> list;

	public CDRInfoDAO()
	{
		this.list = new ArrayList<CDRInfo>();
		
		System.out.println(" #######  init : " + SystemProperty.environment.value());
		
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
			try (Closeable closeable = ObjectifyService.begin()) {
				System.out.println(" #######  setup");
				setupData1();
				setupData2();
	        }
		}
				
		System.out.println(" #######  done");
		
	}
	
	
	private void setupData1() {
		System.out.println("Setup data");
		
			CDRInfo result = (CDRInfo) ofy().load().type(CDRInfo.class).id("EWE3906").now();
			
			if (result == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("Create new entry for EWE3906, and persist");
				CDRInfo cdr = new CDRInfo();
				String cdrId = "EWE3906";
				cdr.setCdrId(cdrId);
				cdr.setEvseId("DEEWEE0001");
				cdr.setRepresentation("plain");
				cdr.setInstance("047124CA5F3680");
				cdr.setTokenType("rfid");
				cdr.setTokenSubType("mifareCls");
				cdr.setContractId("EWE_USER");
				cdr.setCdrStatusType("new");
				try{
					cdr.setStartDateTime(sdf.parse("2016-02-02 13:44:30"));
				}catch(ParseException pe){
					//ignore
				}
				try{
					cdr.setEndDateTime(sdf.parse("2016-02-02 16:59:09"));
				}catch(ParseException pe){
					
				}
				cdr.setDuration("003:14:12");
				cdr.setHouseNumber("22 23");
				cdr.setAddress("Donnerschweer Straße");
				cdr.setCity("Oldenburg");
				cdr.setChargePointType("AC");
				cdr.setCountry("DEU");
				cdr.setConnectorStandard("IEC-62196-T1");
				cdr.setConnectorFormat("Socket");
				cdr.setMaxSocketPower(new Float("22.12"));
				ofy().save().entity(cdr).now();
			}else{
				System.out.println("Nothing to do here ");
			}

	}

	private void setupData2() {
		System.out.println("Setup data");
		
			CDRInfo result = (CDRInfo) ofy().load().type(CDRInfo.class).id("EWE3907").now();
			
			if (result == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("Create new entry for EWE3907, and persist");
				CDRInfo cdr = new CDRInfo();
				String cdrId = "EWE3907";
				cdr.setCdrId(cdrId);
				cdr.setEvseId("DEEWEE0002");
				cdr.setRepresentation("plain");
				cdr.setInstance("04XXXXXXF3680");
				cdr.setTokenType("rfid");
				cdr.setTokenSubType("mifareCls");
				cdr.setContractId("EBG_USER");
				cdr.setCdrStatusType("new");
				try{
					cdr.setStartDateTime(sdf.parse("2016-02-02 13:44:30"));
				}catch(ParseException pe){
					//ignore
				}
				try{
					cdr.setEndDateTime(sdf.parse("2016-02-02 16:59:09"));
				}catch(ParseException pe){
					
				}
				cdr.setDuration("003:14:12");
				cdr.setHouseNumber("22 23");
				cdr.setAddress("Donnerschweer Straße");
				cdr.setCity("Oldenburg");
				cdr.setChargePointType("AC");
				cdr.setCountry("DEU");
				cdr.setConnectorStandard("IEC-62196-T1");
				cdr.setConnectorFormat("Socket");
				cdr.setMaxSocketPower(new Float("22.12"));
				ofy().save().entity(cdr).now();
			}else{
				System.out.println("Nothing to do here ");
			}

	}
}
