package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class CheckPaginationLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String input = frag.execute().trim();
        if (input.contains("[]")) {
            String type = input.replace("[]", "");
            out.write(String.format("Pagination<%s>", type));
        }else{
            out.write(input);
        }
    }
    
}
