package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class EmptyModelFnInternalLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
       String template = "{{#vars}}this.{{baseName}},{{/vars}}";
       String latest = Mustache.compiler().compile(template).execute(frag.context());
       out.write(String.format("IsEmpty: () => {return !(Boolean(%s));}", String.join(" || ", latest.split(","))));
    }
    
}
