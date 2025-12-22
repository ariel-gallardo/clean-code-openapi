package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class ResponseLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String input = frag.execute().replace("DTO", "");
        if(input.equals("BaseResponse")){
            out.write(input);
        }
        else if (input.contains("[]")) {
            String type = input.replace("[]", "");
            out.write(String.format("Response<Pagination<%s>>", type));
        }else if(input.contains("PagedList")){
                String type = input.replace("PagedList", "").replace("DTO", "");
                out.write(String.format("Response<Pagination<%s>>", type));
        }else{
            out.write(String.format("Response<%s>", input));
        }
    }
    
}
