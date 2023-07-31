package com.gc.myproject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

	@Override
    public APIGatewayProxyResponseEvent  handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        BookService bookService = new BookService();
        {
        context.getLogger().log("apiGatewayRequest.getHttpMethod() --------"+apiGatewayRequest.getHttpMethod());
        switch (apiGatewayRequest.getHttpMethod()) {

            case "POST": { 
            	context.getLogger().log("calling Post --------");
                return bookService.saveEmployee(apiGatewayRequest, context);
            }

            case "GET":{
            	context.getLogger().log("calling GET --------");
                if (apiGatewayRequest.getPathParameters() != null) {
                    return bookService.getEmployeeById(apiGatewayRequest, context);
                }
            }
               
            case "DELETE":{
            	context.getLogger().log("calling Delete --------");
                if (apiGatewayRequest.getPathParameters() != null) {
                    return bookService.deleteEmployeeById(apiGatewayRequest, context);
                }
            }
            default:
                throw new Error("Unsupported Methods:::" + apiGatewayRequest.getHttpMethod());

        }
	
	}
	}

}
