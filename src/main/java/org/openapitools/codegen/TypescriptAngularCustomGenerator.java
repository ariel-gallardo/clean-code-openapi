package org.openapitools.codegen;
import org.openapitools.codegen.lambdas.AddLambda;
import org.openapitools.codegen.lambdas.CheckPaginationLambda;
import org.openapitools.codegen.lambdas.KebabCustomLambda;
import org.openapitools.codegen.lambdas.RemoveDTOLambda;
import org.openapitools.codegen.lambdas.ResponseLambda;
import org.openapitools.codegen.lambdas.ResultLambda;
import org.openapitools.codegen.lambdas.SkipLambda;
import org.openapitools.codegen.lambdas.StateDetailBaseLambda;
import org.openapitools.codegen.model.*;
import com.google.common.collect.ImmutableMap;
import com.samskivert.mustache.Mustache.Lambda;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.*;
import java.io.File;
import static org.openapitools.codegen.utils.StringUtils.*;

public class TypescriptAngularCustomGenerator extends DefaultCodegen implements CodegenConfig {
  protected String sourceFolder = "src";
  protected String apiVersion = "1.0.0";
  public CodegenType getTag() {
    return CodegenType.CLIENT;
  }

  public String getName() {
    return "typescript-angular-custom";
  }

  private String getApiName(){
    return this.additionalProperties.get("apiName").toString();
  }

  @Override
  public String getTypeDeclaration(Schema schema) {
      if (schema instanceof ArraySchema) {
          ArraySchema arraySchema = (ArraySchema) schema;
          return super.getTypeDeclaration(arraySchema.getItems()) + "[]";
      }
      return super.getTypeDeclaration(schema);
  }


  @Override
  public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {
    OperationsMap results = super.postProcessOperationsWithModels(objs, allModels);
    OperationMap ops = results.getOperations();
    List<CodegenOperation> opList = ops.getOperation();

    /*List<Map<String,Object>> allExports;
    if (!results.containsKey("allEntities")) {
        results.put("allEntities", new ArrayList<Map<String,Object>>());
    }
    allExports = (List<Map<String,Object>>) results.get("allEntities");*/

    List<Map<String,Object>> allEntities;
    if (!additionalProperties.containsKey("allEntities")) {
        additionalProperties.put("allEntities", new ArrayList<Map<String,Object>>());
    }
    allEntities = (List<Map<String,Object>>) additionalProperties.get("allEntities");

   boolean change = false, change2 = false;
    for(CodegenOperation co : opList){
      Map<String,Object> entityMap = new HashMap<>();
      //Map<String,Object> entityMap2 = new HashMap<>();

      entityMap.put("oEntity", co.baseName);
      //entityMap2.put("exportName", co.returnType);
      
      boolean exists = allEntities.stream().anyMatch(e -> e.get("oEntity").equals(co.baseName)),
      exists2 = false;

      /*if(co.returnType != null)
      exists2 = allExports.stream().anyMatch(e -> e.get("exportName").equals(co.returnType));*/

      if(!results.containsKey("entityName"))results.put("entityName", co.baseName);

      if (!exists){
        change = true;
        allEntities.add(entityMap);
      }

      /*if (co.returnType != null && !exists2 && !languageSpecificPrimitives.contains(co.returnType.replace("[]", ""))){
        change2 = true;
        allExports.add(entityMap2);
      }*/
      String apiName = additionalProperties.get("apiName").toString().toLowerCase();
      co.operationIdOriginal = co.nickname.replace(co.baseName, "").replace(apiName, "");
    }

    if(change) additionalProperties.put("allEntities", allEntities);
    //if(change2) results.put("exports",allExports);

    return results;
  }

  @Override
  public final ImmutableMap.Builder<String, Lambda> addMustacheLambdas() {
    return super.addMustacheLambdas()
    .put("RemoveDTO", new RemoveDTOLambda())
    .put("Response", new ResponseLambda())
    .put("CheckPagination", new CheckPaginationLambda())
    .put("KebabCustom", new KebabCustomLambda())
    .put("Result", new ResultLambda())
    .put("Skip", new SkipLambda())
    .put("Add", new AddLambda())
    .put("StateDetailBase", new StateDetailBaseLambda());
  }


  /*@Override
  public String modelFilename(String templateName, String modelName) {
    String kebabModel = kebabCase(modelName.replace("DTO", ""));
    if(!kebabModel.contains("-") && !modelName.equals("BaseResponse") && !templateName.contains("common")){
      if(templateName.contains("redux")){
        String suffix = modelTemplateFiles().get(templateName);
        String path = String.format("%s/%s/%s%s",reduxFileFolder(),kebabModel,kebabModel,suffix);
        return path;
      }
    }
      return super.modelFilename(templateName, modelName);
  }*/

    @Override
    public String apiFilename(String templateName, String tag) {
      if(!tag.equals("BaseResponse") && !templateName.contains("common")){
        if(templateName.contains("redux")){
          String suffix = apiTemplateFiles().get(templateName);
          String kebabModel = kebabCase(tag.replace("DTO", ""));
          String path = String.format("%s/%s/%s%s",reduxFileFolder(),kebabModel,kebabModel,suffix);
          return path;
        }
      }
      return super.apiFilename(templateName, tag);
    }


  @Override
  public void processOpts() {
    super.processOpts();
    apiPackage = String.format("api.%s.services",getApiName().toLowerCase());
    modelPackage = String.format("api.%s.models",getApiName().toLowerCase());
    additionalProperties.put("useSingleRequestParameter", true);
    additionalProperties.put("isProvidedInNone", true);
    supportingFiles.add(new SupportingFile(commonTemplateModelsPath("nullable-form-control.model.mustache"), String.format("%s/%s",modelFileFolder().replace("models",""),commonTemplateModelsPath("nullable-form-control.model.ts"))));
    supportingFiles.add(new SupportingFile(commonTemplateModelsPath("state-detail.model.mustache"), String.format("%s/%s",modelFileFolder().replace("models",""),commonTemplateModelsPath("state-detail.model.ts"))));
    supportingFiles.add(new SupportingFile(commonTemplateModelsPath("pagination.model.mustache"), String.format("%s/%s",modelFileFolder().replace("models",""),commonTemplateModelsPath("pagination.model.ts"))));
    supportingFiles.add(new SupportingFile(commonTemplateModelsPath("response.model.mustache"), String.format("%s/%s",modelFileFolder().replace("models",""),commonTemplateModelsPath("response.model.ts"))));
    supportingFiles.add(new SupportingFile(commonTemplateModelsPath("validation-error.model.mustache"), String.format("%s/%s",modelFileFolder().replace("models",""),commonTemplateModelsPath("validation-error.model.ts").replace("/common", ""))));
    supportingFiles.add(new SupportingFile(reduxTemplatePath("core.module.mustache"), String.format("%s/%s",reduxFileFolder(),"core.module.ts")));
    supportingFiles.add(new SupportingFile("api.base.service.mustache", String.format("%s/%s", apiFileFolder(), "api.base.service.ts")));
    supportingFiles.add(new SupportingFile(reduxTemplatePath("index.mustache"), String.format("%s/%s",baseFileFolder(),"index.ts")));
    supportingFiles.add(new SupportingFile("configuration.mustache", String.format("%s/%s",baseFileFolder(),"configuration.ts")));
    supportingFiles.add(new SupportingFile("encoder.mustache", String.format("%s/%s",baseFileFolder(),"encoder.ts")));
    supportingFiles.add(new SupportingFile("param.mustache", String.format("%s/%s",baseFileFolder(),"param.ts")));
    supportingFiles.add(new SupportingFile("variables.mustache", String.format("%s/%s",baseFileFolder(),"variables.ts")));
    supportingFiles.add(new SupportingFile("models/base-response.model.mustache", String.format("%s/%s",modelFileFolder(),"base-response.model.ts")));
  }

  private String reduxPackage(){
    return String.format("api.%s.redux", getApiName());
  }

  public String getHelp() {
    return "Generates a default client library.";
  }

  public TypescriptAngularCustomGenerator() {
    super();
    apiNameSuffix = "";
    outputFolder = "generated-code/typescript-angular-custom";
    modelTemplateFiles.put("model.mustache", ".ts");                
    apiTemplateFiles.put("api.service.mustache",".ts");
    apiTemplateFiles.put("/redux/entity/entity.reducer.mustache", ".reducer.ts");
    apiTemplateFiles.put("/redux/entity/entity.module.mustache", ".module.ts");
    apiTemplateFiles.put("/redux/entity/entity.state.mustache", ".state.ts");
    apiTemplateFiles.put("/redux/entity/entity.selector.mustache", ".selector.ts");
    apiTemplateFiles.put("/redux/entity/entity.actions.mustache", ".actions.ts");
    apiTemplateFiles.put("/redux/entity/entity.effects.mustache", ".effects.ts");
    apiTemplateFiles.put("/redux/entity/entity.facade.mustache", ".facade.ts");
    templateDir = "angular-typescript-custom";
    additionalProperties.put("apiVersion", apiVersion);
    languageSpecificPrimitives.add("integer");
    languageSpecificPrimitives.add("string");
    languageSpecificPrimitives.add("array");
    languageSpecificPrimitives.add("UUID");
    reservedWords = new HashSet<String>(
      Arrays.asList(
        "break", "case", "catch", "class", "const", "continue", "debugger",
        "default", "delete", "do", "else", "enum", "export", "extends", "false",
        "finally", "for", "function", "if", "import", "in", "instanceof", "new",
        "null", "return", "super", "switch", "this", "throw", "true", "try",
        "typeof", "var", "void", "while", "with", "as", "implements", "interface",
        "let", "package", "private", "protected", "public", "static", "yield",
        "any", "boolean", "constructor", "declare", "get", "module", "require",
        "number", "set", "string", "symbol", "type", "from", "of"
      )
    );
    languageSpecificPrimitives = new HashSet<String>(
      Arrays.asList(
        "string",
        "boolean",
        "number",
        "any",
        "null",
        "void",
        "Date",
        "Blob",
        "ArrayBuffer",
        "array",
        "integer",
        "UUID"
      )
    );

    typeMapping.put("string", "string");
    typeMapping.put("UUID", "string");
    typeMapping.put("integer", "number");
  }

  @Override
  public String toApiFilename(String name) {
    return String.format("%s.service", kebabCase(name.replace("DTO", "")));
  }

  @Override
  public String toModelFilename(String name) {
    return String.format("%s.model", kebabCase(name.replace("DTO", "")));
  }

  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;
  }

  public String reduxFileFolder() {
    return String.format("%s/%s/%s", outputFolder,sourceFolder, reduxPackage().replace('.', File.separatorChar));
  }

  public String modelFileFolder() {
    return String.format("%s/%s/%s", outputFolder,sourceFolder, modelPackage().replace('.', File.separatorChar));
  }

  public String baseFileFolder(){
    return String.format("%s/%s/api/%s", outputFolder,sourceFolder,getApiName());
  }

  @Override
  public String apiFileFolder() {
    return String.format("%s/%s/%s", outputFolder,sourceFolder, apiPackage().replace('.', File.separatorChar));
  }

  @Override
  public String escapeUnsafeCharacters(String input) {
    return input;
  }

  public String escapeQuotationMark(String input) {
    return input.replace("\"", "\\\"");
  }

  private String kebabCase(String input) {
    if (input == null || input.isEmpty()) {
        return "";
    }
    String temp = Character.toLowerCase(input.charAt(0)) + input.substring(1);
    temp = temp.replaceAll("(?<=[a-z])([A-Z])", "-$1");
    return temp.toLowerCase();
  }
  private String commonTemplateModelsPath(String file){
    return String.format("models/common/%s", file);
  }
  private String reduxTemplatePath(String file){
    return String.format("redux/%s", file);
  }
}
