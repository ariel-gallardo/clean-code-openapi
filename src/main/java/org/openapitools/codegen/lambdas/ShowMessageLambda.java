package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class ShowMessageLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String content = frag.execute();
        String operation = Mustache.compiler().compile("{{{operationIdOriginal}}}").execute(frag.context());
        if(!operation.toLowerCase().contains("get")){
           out.write(content.replace(operation, ""));
        }
    }
    
}
