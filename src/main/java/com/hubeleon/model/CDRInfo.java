package com.hubeleon.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.apache.wicket.ajax.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.Model;

@Entity
public class CDRInfo extends Model implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CDRInfo.class);

	@Id
	private String cdrId;
	
    private String evseId;
    
    private String instance;
    private String tokenType;
    private String tokenSubType;
    private String representation;
    private String contractId;
    private String liveAuthId;
    private String cdrStatusType;
    private java.util.Date startDateTime;
    private java.util.Date endDateTime;
    private String duration;
    private String houseNumber;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private String chargePointType;
    private String connectorStandard;
    private String connectorFormat;
    private float maxSocketPower;
    private String productType;
    private String meterId;
    private java.util.Date lastModified;
    private String cdrUserReviewStatus;
    
    public CDRInfo(){
    	super();
    }
    
	public CDRInfo(String cdrId, String evseId, String instance, String tokenType, String tokenSubType,
			String representation, String contractId, String liveAuthId, String cdrStatusType, String duration,
			String cdrUserReviewStatus) {
			
		this.cdrId = cdrId;
		this.evseId = evseId;
		this.instance = instance;
		this.tokenType = tokenType;
		this.tokenSubType = tokenSubType;
		this.representation = representation;
		this.contractId = contractId;
		this.liveAuthId = liveAuthId;
		this.cdrStatusType = cdrStatusType;
		this.duration = duration;
		this.setCdrUserReviewStatus(cdrUserReviewStatus);
		
	}

	public static CDRInfo of(JSONObject object)
	{
		CDRInfo cdrInfo = new CDRInfo(
				object.optString("cdrId"), 
				object.optString("evseId"), 
				object.optString("instance"),
				object.optString("tokenType"),
			    object.optString("tokenSubType"),
			    object.optString("representation"),
			    object.optString("contractId"),
			    object.optString("liveAuthId"),
			    object.optString("cdrStatusType"),
			    object.optString("duration"),
			    object.optString("cdrUserReviewStatus"));
			
		return cdrInfo;
	}
    
	public String getCdrId() {
		return cdrId;
	}

	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}

	public String getEvseId() {
		return evseId;
	}

	public void setEvseId(String evseId) {
		this.evseId = evseId;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenSubType() {
		return tokenSubType;
	}

	public void setTokenSubType(String tokenSubType) {
		this.tokenSubType = tokenSubType;
	}

	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getLiveAuthId() {
		return liveAuthId;
	}

	public void setLiveAuthId(String liveAuthId) {
		this.liveAuthId = liveAuthId;
	}

	public String getCdrStatusType() {
		return cdrStatusType;
	}

	public void setCdrStatusType(String cdrStatusType) {
		this.cdrStatusType = cdrStatusType;
	}

	public java.util.Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(java.util.Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public java.util.Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(java.util.Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getChargePointType() {
		return chargePointType;
	}

	public void setChargePointType(String chargePointType) {
		this.chargePointType = chargePointType;
	}

	public String getConnectorStandard() {
		return connectorStandard;
	}

	public void setConnectorStandard(String connectorStandard) {
		this.connectorStandard = connectorStandard;
	}

	public String getConnectorFormat() {
		return connectorFormat;
	}

	public void setConnectorFormat(String connectorFormat) {
		this.connectorFormat = connectorFormat;
	}

	public float getMaxSocketPower() {
		return maxSocketPower;
	}

	public void setMaxSocketPower(float maxSocketPower) {
		this.maxSocketPower = maxSocketPower;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public java.util.Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(java.util.Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getCdrUserReviewStatus() {
		return cdrUserReviewStatus;
	}

	public void setCdrUserReviewStatus(String cdrUserReviewStatus) {
		this.cdrUserReviewStatus = cdrUserReviewStatus;
	}


}
