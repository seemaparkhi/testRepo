package com.gc.myproject;


import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class BookService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;

    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        Book book = JavaUtility.convertStringToObj(apiGatewayRequest.getBody(),context);
        dynamoDBMapper.save(book);
        jsonBody = JavaUtility.convertObjToString(book,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return createAPIResponse(jsonBody,201,JavaUtility.createHeaders());
    }
    public APIGatewayProxyResponseEvent getEmployeeById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        Book book =   dynamoDBMapper.load(Book.class,empId)  ;
        if(book!=null) {
            jsonBody = JavaUtility.convertObjToString(book, context);
            context.getLogger().log("fetch book By ID:::" + jsonBody);
             return createAPIResponse(jsonBody,200,JavaUtility.createHeaders());
        }else{
            jsonBody = "Book Not Found Exception :" + empId;
             return createAPIResponse(jsonBody,400,JavaUtility.createHeaders());
        }
       
    }

    public APIGatewayProxyResponseEvent deleteEmployeeById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();	
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        Book book =  dynamoDBMapper.load(Book.class,empId)  ;
        if(book!=null) {
            dynamoDBMapper.delete(book);
            context.getLogger().log("data deleted successfully :::" + empId);
            return createAPIResponse("data deleted successfully." + empId,200,JavaUtility.createHeaders());
        }else{
            jsonBody = "Book Not Found Exception :" + empId;
            return createAPIResponse(jsonBody,400,JavaUtility.createHeaders());
        }
    }


    private void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }
    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }
	

}
