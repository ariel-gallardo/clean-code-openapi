package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class ResultLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String input = frag.execute().replace("DTO", "");
        String input2 = frag.decompile();
        if(input.equals("BaseResponse")){
            out.write("any");
        }
        else if (input.contains("[]")) {
            String type = input.replace("[]", "");
            out.write(String.format("Pagination<%s>", type));
        }else if(input.isEmpty()){
            out.write("any");
        }else{
            out.write(input);
        }
    }
    
}
