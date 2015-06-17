package com.example.inaccurateweather;

public class DetailModel {

	private String detailsImage, detailsDate, detailsDescr, detailsMornTemp, detailsEveTemp, detailsNigTemp, detailsMinTemp,
	detailsMaxTemp, detailsPress, detailsWind, detailsDegr, detailsCloud, detailsHum;
	
	public DetailModel(String detailsImage,String detailsDate, String detailsDescr, String detailsMornTemp,
			String detailsEveTemp, String detailsNigTemp, String detailsMinTemp, 
			String detailsMaxTemp, String detailsPress, String detailsWind, String detailsDegr, String detailsCloud, String detailsHum) {
		
		this.detailsImage = detailsImage;
		this.detailsDate = detailsDate;
		this.detailsDescr = detailsDescr;
		this.detailsMornTemp = detailsMornTemp;
		this.detailsEveTemp = detailsEveTemp;
		this.detailsNigTemp = detailsNigTemp;
		this.detailsMinTemp = detailsMinTemp;
		this.detailsMaxTemp = detailsMaxTemp;
		this.detailsPress = detailsPress;
		this.detailsWind = detailsWind;
		this.detailsDegr = detailsDegr;
		this.detailsCloud = detailsCloud;
		this.detailsHum = detailsHum;
	}

	public String getDetailsImage() {
		return detailsImage;
	}	
	public String getDetailsDate() {
		return detailsDate;
	}
	public String getDetailsDescr() {
		return detailsDescr;
	}
	public String getDetailsMornTemp() {
		return detailsMornTemp;
	}
	public String getDetailsEveTemp() {
		return detailsEveTemp;
	}
	public String getDetailsNigTemp() {
		return detailsNigTemp;
	}
	public String getDetailsMinTemp() {
		return detailsMinTemp;
	}
	public String getDetailsMaxTemp() {
		return detailsMaxTemp;
	}
	public String getDetailsPress() {
		return detailsPress;
	}
	public String getDetailsWind() {
		return detailsWind;
	}
	public String getDetailsDegr() {
		return detailsDegr;
	}
	public String getDetailsCloud() {
		return detailsCloud;
	}
	public String getDetailsHum() {
		return detailsHum;
	}
	
}
