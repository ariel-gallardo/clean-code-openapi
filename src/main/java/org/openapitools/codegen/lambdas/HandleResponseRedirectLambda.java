package org.openapitools.codegen.lambdas;

import java.io.IOException;
import java.io.Writer;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template.Fragment;

public class HandleResponseRedirectLambda implements Mustache.Lambda{

    @Override
    public void execute(Fragment frag, Writer out) throws IOException {
       String operation = Mustache.compiler().compile("{{{operationIdOriginal}}}").execute(frag.context());
       if(operation.equals("LoginPost")){
        out.write("else if (newErr.status === 401 && newErr.error){ const res = newErr.error as BaseResponse; this.snackbarService.show(res.message, res.statusCode);}");
       }else{
        out.write("if(newErr.status === 401){cookieStore.delete('token');this.router.navigate(['/users/login']);this.dialog.closeAll();}else if(newErr.status === 403 && newErr.error){const res = newErr.error as BaseResponse;this.snackbarService.show(res.message, res.statusCode);}");
       }
    }
    
}
