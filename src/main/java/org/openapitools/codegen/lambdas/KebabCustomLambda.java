package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class KebabCustomLambda implements Mustache.Lambda{

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
        String text = frag.execute();
        out.write(kebabCase(text.replace("DTO","")));
    }

    private String kebabCase(String input) {
    if (input == null || input.isEmpty()) {
        return "";
    }
    String temp = Character.toLowerCase(input.charAt(0)) + input.substring(1);
    temp = temp.replaceAll("(?<=[a-z])([A-Z])", "-$1");
    return temp.toLowerCase();
  }
    
}
