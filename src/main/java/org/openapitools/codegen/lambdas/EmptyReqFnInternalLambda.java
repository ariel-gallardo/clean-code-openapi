package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class EmptyReqFnInternalLambda implements Mustache.Lambda {

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
       
        String template = "{{#allParams}}this.{{paramName}}'{{{dataType}}}',{{/allParams}}";
        String latest = Mustache.compiler().compile(template).execute(frag.context());
        if(!latest.isEmpty()){
            String[] array = latest.split(",");
            if(array.length > 0){
                ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
                list.removeIf(s -> s.contains("page'") || s.contains("pageSize'") || s.contains("orderBy'"));
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    if (s.matches(".*'.*'.*")) {
                        String content = s.replaceAll(".*('.*').*", "$1");
                        if(content.contains("[]")){
                            content = s.replace(content,"");
                            content = String.format("(%s ? %s.length > 0 : false)", content,content).replace("DTO", "");
                        }
                        else
                        {
                            if(content.contains("string") || content.contains("number") || content.contains("enum"))
                                content = s.replace(content, "").replace("DTO", "");
                            else
                                content = s.replace(content, "?.IsEmpty()").replace("DTO", "");
                        }
                        list.set(i, content);
                    }
                }
                if(!list.isEmpty())
                    out.write(String.format("IsEmpty: () => {return !(Boolean(%s));}", String.join(" || ", list)));
            }
        } 
    }
    
}
