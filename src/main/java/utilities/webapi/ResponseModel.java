package utilities.webapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseModel {

    @JsonProperty
    private boolean IsSuccess;
    @JsonProperty
    private String UserMessage;
    @JsonProperty
    private String TechnicalMessage;
    @JsonProperty
    private int TotalCount;
    @JsonProperty
    private CountryModel[] Response;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public String getUserMessage() {
        return UserMessage;
    }

    public void setUserMessage(String userMessage) {
        UserMessage = userMessage;
    }

    public String getTechnicalMessage() {
        return TechnicalMessage;
    }

    public void setTechnicalMessage(String technicalMessage) {
        TechnicalMessage = technicalMessage;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public CountryModel[] getResponse() {
        return Response;
    }

    public void setResponse(CountryModel[] response) {
        Response = response;
    }
}