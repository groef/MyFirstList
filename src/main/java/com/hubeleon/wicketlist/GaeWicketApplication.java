package com.hubeleon.wicketlist;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.apache.wicket.Page;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.IProvider;
//import org.wicketstuff.pageserializer.kryo.KryoSerializer;

import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.hubeleon.cdr.ChargeList;
import com.hubeleon.cdr.ChargeList2;
import com.hubeleon.main.Index;
import com.hubeleon.model.CDRInfo;
import com.hubeleon.wickets.cache.NoSerializePageManagerProvider;

public class GaeWicketApplication extends WebApplication {
	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

	@Override
	protected void init() {
		
		mountPage("HomePage.html", HomePage.class);
		mountPage("ChargeList.html", ChargeList.class);
		mountPage("ChargeList2.html", ChargeList2.class);
		
		ObjectifyService.register(CDRInfo.class);

		System.out.println(" #######  init : " + SystemProperty.environment.value());
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
			try (Closeable closeable = ObjectifyService.begin()) {
				//System.out.println(" #######  setup");
				//setupData1();
				//setupData2();
	        }
		}
				
		System.out.println(" #######  done");
		
		getResourceSettings().setResourcePollFrequency(null);
		getMarkupSettings().setStripWicketTags(true);

		// Configurations for operating wicket in google app engine
		this.setSessionStoreProvider(new IProvider() {
			public ISessionStore get() {
				return new HttpSessionStore();
			}
		});

		 setPageManagerProvider( new NoSerializePageManagerProvider( this, getPageManagerContext() ) );

		// getFrameworkSettings().setSerializer((ISerializer) new
		// KryoSerializer());
		//getStoreSettings().setInmemoryCacheSize(50);

		super.init();
			
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
