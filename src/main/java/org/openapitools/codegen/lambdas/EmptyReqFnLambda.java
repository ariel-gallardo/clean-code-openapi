package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class EmptyReqFnLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
       
        String template = "{{#allParams}}this.{{paramName}}'{{{dataType}}}',{{/allParams}}";
        String latest = Mustache.compiler().compile(template).execute(frag.context());
        if(!latest.isEmpty()){
            String[] array = latest.split(",");
            if(array.length > 0){
                ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
                list.removeIf(s -> s.contains("'string'") || s.contains("'number'") || s.contains("'enum'") || s.contains("'any'"));
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    if (s.matches(".*'.*'.*")) {
                        String content = s.replaceAll(".*('.*').*", "$1");
                        if(content.contains("[]")){
                            content = s.replace(content,"");
                            content = String.format("(%s ? %s.length > 0 : false)", content,content);
                        }
                        else
                            content = s.replace(content, ".isEmpty()");
                        list.set(i, content.replace("DTO", ""));
                    }
                }
                if(!list.isEmpty())
                    out.write(String.format("\tpublic IsEmpty(){return (%s);}", String.join(" || ", list)));
            }
        } 
    }
    
}
