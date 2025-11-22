package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;


public class StateDetailBaseLambda implements Mustache.Lambda {
    
    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String input = frag.execute();
        if(!input.startsWith("StateDetail<any,"))
            out.write(input);
        else
            out.write(input.replace("StateDetail", "StateDetailBase").replace("any,", ""));
    }
    
}
