package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class OnlyFilterLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String text = Mustache.compiler().compile("{{{operationIdOriginal}}}").execute(frag.context());
        String output = frag.execute();
        if(!text.contains("Filter")) out.write(output.replace("DTO",""));
    }
    
}
