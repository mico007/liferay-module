package com.liferay.product.portlet.portlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.product.portlet.constants.ProductPortletKeys;

//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + ProductPortletKeys.PRODUCT,
                "mvc.command.name=saveProduct"
        },
        service = MVCActionCommand.class
)
public class SaveActionMvcCommand extends BaseMVCActionCommand {

//    public static void postProduct(String category, String name, String price ) throws Exception{
//
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        HttpPost request = new HttpPost("http://localhost:8080/ofbizProduct/control/createOfbizProductEvent");
//
//        OfbProduct addProduct = new OfbProduct(category, name, price);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        StringEntity json = new StringEntity(mapper.writeValueAsString(addProduct), ContentType.APPLICATION_JSON);
//
//        request.setEntity(json);
//
//        CloseableHttpResponse response =   httpClient.execute(request);
//
//        if(response.getStatusLine().getStatusCode() != 200){
//            System.out.println("Product not added " + response.getStatusLine().getStatusCode());
//        }else {
//            System.out.println("Request Status " + response.getStatusLine().getStatusCode());
//        }
//    }

    private static void sendPOST(String category, String name, String price, String quantity) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/ofbizProduct/control/createProduct");
        httpPost.addHeader("User-Agent", "Chrome/97.0.4692.71");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("brandName", category));
        urlParameters.add(new BasicNameValuePair("productName", name));
        urlParameters.add(new BasicNameValuePair("priceDetailText", price));
        urlParameters.add(new BasicNameValuePair("quantityIncluded", quantity));
        urlParameters.add(new BasicNameValuePair("login.username", "admin"));
        urlParameters.add(new BasicNameValuePair("login.password", "ofbiz"));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        httpPost.setEntity(postParams);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: "
                + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        httpClient.close();

    }

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        String name = ParamUtil.get(actionRequest, "name", "");
        String category = ParamUtil.get(actionRequest, "category", "");
        String price = ParamUtil.get(actionRequest, "price", "");
        String quantity = ParamUtil.get(actionRequest, "quantity", "");

        System.out.println(name + " - " + category + " - " + price + " - " + quantity );

        System.out.println("SaveActionMvcCommand.doProcessAction() -->");

        sendPOST(category, name, price, quantity);

        //postProduct(category, name, price);

    }

}
