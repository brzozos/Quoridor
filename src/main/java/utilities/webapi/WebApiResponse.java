package utilities.webapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import utilities.exception.WrongNumberOfResultsException;

import java.io.*;
import java.net.URL;

public class WebApiResponse {
    private String url = "http://countryapi.gear.host/v1/Country/getCountries?pName=";
    private String urlAdd;
    private HttpClient client;
    private HttpResponse response;
    private HttpEntity entity;
    private int statusCode;
    private String result;
    private ObjectMapper mapper;
    private CountryModel country;
    private ResponseModel resultObject;

    public WebApiResponse() {
        this.mapper = new ObjectMapper();
    }

    public CountryModel getResponse(String name) throws IOException, StringIndexOutOfBoundsException, WrongNumberOfResultsException {
        this.urlAdd = name;
        client = HttpClientBuilder.create().build();

        response = client.execute(new HttpGet(url + urlAdd));
        entity = response.getEntity();

        if (entity != null) {
            result = EntityUtils.toString(entity);
        }

        resultObject = mapper.readValue(result, ResponseModel.class);

        if (resultObject.getTotalCount() > 1) throw new WrongNumberOfResultsException("To many results");
        else if (resultObject.getTotalCount() == 0) throw new WrongNumberOfResultsException("No results");

        country = resultObject.getResponse()[0];
        //downloadImg(country);

        return country;

    }
    public String downloadImg(CountryModel country) {
        String outDirectory = "cache/"+country.getName()+".png";
        if(!(new File(outDirectory).isFile())){
            URL imgurl;
            try{
                imgurl = new URL(country.getFlagPng());
                InputStream in = new BufferedInputStream(imgurl.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while(-1!=(n=in.read(buf))){
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();

                FileOutputStream fos = new FileOutputStream(outDirectory);
                fos.write(response);
                fos.close();

            }catch (IOException e){
                System.out.println("Image Downloading Error");
                return "";
            }
        }
        return outDirectory;


    }
}
