package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class RemoveDTOLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String text = frag.execute();
        out.write(text.replace("DTO",""));
    }
    
}
