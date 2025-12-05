package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class SkipLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String input = frag.execute();
        String op = Mustache.compiler().compile("").execute(frag.context());
        if(!op.contains("LoginPost") && !input.contains("Delete") && !input.contains("as any") && !input.contains("data: {  } as ,"))
            out.write(input);
    }
    
}
