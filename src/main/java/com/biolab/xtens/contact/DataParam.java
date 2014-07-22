package com.biolab.xtens.contact;

import java.sql.Date;

public class DataParam {

	private Integer idData;
	private Integer idDataType;
	private Integer idPatColl;
	private Integer idSampColl;
	private String idPrcType;
	private Date acquisitionDate;
	private boolean storeFile;
	private boolean dbMData;
	private boolean irodsMData;
	private String loopId;
	private String[] uri;
	private String notes;

	public Integer getIdData() {
		return idData;
	}
	public void setIdData(Integer idData) {
		this.idData = idData;
	}
	public Integer getIdDataType() {
		return idDataType;
	}
	public void setIdDataType(Integer idDataType) {
		this.idDataType = idDataType;
	}
	public Integer getIdPatColl() {
		return idPatColl;
	}
	public void setIdPatColl(Integer idPatColl) {
		this.idPatColl = idPatColl;
	}
	public Integer getIdSampColl() {
		return idSampColl;
	}
	public void setIdSampColl(Integer idSampColl) {
		this.idSampColl = idSampColl;
	}
	public String getIdPrcType() {
		return idPrcType;
	}
	public void setIdPrcType(String idPrcType) {
		this.idPrcType = idPrcType;
	}
	public Date getAcquisitionDate() {
		return acquisitionDate;
	}
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}
	public boolean isStoreFile() {
		return storeFile;
	}
	public void setStoreFile(boolean storeFile) {
		this.storeFile = storeFile;
	}
	public boolean isDbMData() {
		return dbMData;
	}
	public void setDbMData(boolean dbMData) {
		this.dbMData = dbMData;
	}
	public boolean isIrodsMData() {
		return irodsMData;
	}
	public void setIrodsMData(boolean irodsMData) {
		this.irodsMData = irodsMData;
	}
	public String getLoopId() {
		return loopId;
	}
	public void setLoopId(String loopId) {
		this.loopId = loopId;
	}
	public String[] getUri() {
		return uri;
	}
	public void setUri(String[] uri) {
		this.uri = uri;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
