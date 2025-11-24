package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class DefaultValueLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
       String input = frag.execute();
       if(input.contains("array") || input.contains("[]")) out.write("[]");
       else if(input.equals("number")) out.write("0");
       else if(input.equals("string")) out.write("''");
       else {
        out.write("{}");
       }
    }
    
}
