package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class DefaultValueLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
       String input = frag.execute();
       String name = Mustache.compiler().compile("{{baseName}}").execute(frag.context()).replace("DTO", "");
       if(input.contains("array") || input.contains("[]")) out.write("[]");
       else if(input.equals("number")) {
        if(name.contains("Id") || name.equals("id"))
            out.write("0");
        else
            out.write("null");
       }
       else if(input.equals("string")) out.write("null");
       else {
        out.write("{}");
       }
    }
    
}
