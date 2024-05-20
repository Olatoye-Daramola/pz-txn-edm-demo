//package dev.olatoye.userManagementService.config;
//
//import com.amazonaws.serverless.exceptions.ContainerInitializationException;
//import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
//import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
//import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
//import dev.olatoye.userManagementService.UserManagementServiceApplication;
//import dev.olatoye.userManagementService.model.dto.request.AwsSQSRequest;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class LambdaHandler implements RequestStreamHandler {
//
//    private final static SpringBootLambdaContainerHandler<AwsSQSRequest, AwsProxyResponse> handler;
//    static {
//        try {
////            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(UserManagementServiceApplication.class);
//            handler = new SpringBootProxyHandlerBuilder<AwsSQSRequest>()
//                    .defaultProxy()
//                    .asyncInit()
//                    .springBootApplication(UserManagementServiceApplication.class)
//                    .buildAndInitialize();
//        } catch (ContainerInitializationException e) {
//            // if we fail here. We re-throw the exception to force another cold start
//            e.printStackTrace();
//            throw new RuntimeException("Could not initialize Spring Boot application", e);
//        }
//    }
//
//    @Override
//    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
//            throws IOException {
//        handler.proxyStream(inputStream, outputStream, context);
//    }
//}
