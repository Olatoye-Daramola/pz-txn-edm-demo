package dev.olatoye.userManagementService.config;

import com.amazonaws.services.lambda.runtime.Context;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FunctionalLambdaHandler extends FunctionInvoker {

    public void invokeWrapper(InputStream input, OutputStream output, Context context) throws IOException {
        this.handleRequest(input, output, context);
    }
}
