package utilities.webapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryModel {
    @JsonProperty
    private String Name;

    @JsonProperty
    private String Alpha2Code;

    @JsonProperty
    private String Alpha3Code;

    @JsonProperty
    private String NativeName;

    @JsonProperty
    private String Region;

    @JsonProperty
    private String SubRegion;

    @JsonProperty
    private String Latitude;

    @JsonProperty
    private String Longitude;

    @JsonProperty
    private int Area;

    @JsonProperty
    private int NumericCode;

    @JsonProperty
    private String NativeLanguage;

    @JsonProperty
    private String CurrencyCode;

    @JsonProperty
    private String CurrencyName;

    @JsonProperty
    private String CurrencySymbol;

    @JsonProperty
    private String Flag;

    @JsonProperty
    private String FlagPng;


    public String getName() { return this.Name; }

    public void setName(String Name) { this.Name = Name; }

    public String getAlpha2Code() { return this.Alpha2Code; }

    public void setAlpha2Code(String Alpha2Code) { this.Alpha2Code = Alpha2Code; }

    public String getAlpha3Code() { return this.Alpha3Code; }

    public void setAlpha3Code(String Alpha3Code) { this.Alpha3Code = Alpha3Code; }

    public String getNativeName() { return this.NativeName; }

    public void setNativeName(String NativeName) { this.NativeName = NativeName; }

    public String getRegion() { return this.Region; }

    public void setRegion(String Region) { this.Region = Region; }

    public String getSubRegion() { return this.SubRegion; }

    public void setSubRegion(String SubRegion) { this.SubRegion = SubRegion; }

    public String getLatitude() { return this.Latitude; }

    public void setLatitude(String Latitude) { this.Latitude = Latitude; }

    public String getLongitude() { return this.Longitude; }

    public void setLongitude(String Longitude) { this.Longitude = Longitude; }

    public int getArea() { return this.Area; }

    public void setArea(int Area) { this.Area = Area; }

    public int getNumericCode() { return this.NumericCode; }

    public void setNumericCode(int NumericCode) { this.NumericCode = NumericCode; }

    public String getNativeLanguage() { return this.NativeLanguage; }

    public void setNativeLanguage(String NativeLanguage) { this.NativeLanguage = NativeLanguage; }

    public String getCurrencyCode() { return this.CurrencyCode; }

    public void setCurrencyCode(String CurrencyCode) { this.CurrencyCode = CurrencyCode; }

    public String getCurrencyName() { return this.CurrencyName; }

    public void setCurrencyName(String CurrencyName) { this.CurrencyName = CurrencyName; }

    public String getCurrencySymbol() { return this.CurrencySymbol; }

    public void setCurrencySymbol(String CurrencySymbol) { this.CurrencySymbol = CurrencySymbol; }

    public String getFlag() { return this.Flag; }

    public void setFlag(String Flag) { this.Flag = Flag; }

    public String getFlagPng() { return this.FlagPng; }

    public void setFlagPng(String FlagPng) { this.FlagPng = FlagPng; }




}
