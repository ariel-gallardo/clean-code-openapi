package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class UsePageLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String input = frag.execute();
        String returnType = Mustache.compiler().compile("{{{returnType}}}").execute(frag.context());
        if(returnType.contains("[]")){
            out.write(input.replace(returnType, ""));
        }
    }
    
}
