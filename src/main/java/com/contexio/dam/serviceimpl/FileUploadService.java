package com.contexio.rulebase.serviceimpl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.contexio.rulebase.constant.Constants;
import com.contexio.rulebase.model.Attribute;
import com.contexio.rulebase.model.AttributeLov;
import com.contexio.rulebase.model.Category;
import com.contexio.rulebase.model.CategoryAttributeMapping;
import com.contexio.rulebase.model.ErrorModel;
import com.contexio.rulebase.model.ProductScoreDetails;
import com.contexio.rulebase.model.ProjectConfiguration;
import com.contexio.rulebase.model.RatingChartWeightageDTO;
import com.contexio.rulebase.model.Result;
import com.contexio.rulebase.model.RuleCriteriaGroupDto;
import com.contexio.rulebase.model.RuleDataModel;
import com.contexio.rulebase.model.RuleError;
import com.contexio.rulebase.model.RuleStageCat;
import com.contexio.rulebase.model.TemplateDetails;
import com.contexio.rulebase.repository.RuleAdapterRepository;
import com.contexio.rulebase.repositoryimpl.FetchAllParamRepositorey;
import com.contexio.rulebase.service.FileUploadServiceInterface;

@Service
public class FileUploadService implements FileUploadServiceInterface

{
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);

	/*
	 * @Autowired private RuleStagesService rulestageservice;
	 */

	@Autowired
	FetchAllParamRepositorey fetchRuleParamRepositorey;

	@Autowired
	RuleAdapterRepository ruleAdapterRepository;
	
	@Autowired
	RuleApprovalServiceImpl approvalservice;

	
	public String fileUpload(MultipartFile uploadfile, String projectId, String userName) {
		File file = null;

		StringBuffer sb = new StringBuffer();
		sb.append("E:\\IT\\Tool_Data\\Catalogue_Tool\\Upload_Folder");

		file = new File(sb.toString());
		if (!file.exists()) {
			//LOGGER.info("Creating directory");
//			LOGGER.info("Creating directory");
			file.mkdirs();
		}

		String empty_file = uploadfile.getOriginalFilename();

		if (empty_file.equals("")) {
			return "";
		} else {

			String filePath = file.getPath() + "\\" + uploadfile.getOriginalFilename();
			// LOGGER.info(filePath);
			 //LOGGER.info("filePath::"+filePath);

			try {
				uploadfile.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				//LOGGER.info("Error : "+e.getMessage());
				//e.printStackTrace();
			} catch (IOException e) {
				//LOGGER.info("Error : "+e.getMessage());
				//e.printStackTrace();
			}

			return filePath;
		}
	}

	@Override
	public Attribute[] fetchAllAttributeData(String clientId, String projectId)
			throws ClassNotFoundException, SQLException {

		final String uri = "http://localhost:8083/fetch-all-attribute-data";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("clientId", clientId)
				.queryParam("projectId", projectId);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<Attribute[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				Attribute[].class);

//		LOGGER.info(result.getBody().toString());

		return result.getBody();
	}
	
	@Override
	public TemplateDetails[] fetchTemplateDetails(String clientId, String projectId, String categoryId) {

		final String uri = "http://localhost:8083/fetch-template-details";
		//LOGGER.info("clientId = "+clientId+ "projectId = "+projectId+"categoryId = "+categoryId);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("clientId", clientId)
				.queryParam("projectId", projectId).queryParam("categoryId", categoryId);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<TemplateDetails[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				TemplateDetails[].class);

		//LOGGER.info(result.getBody().toString());

		return result.getBody();
	}
	
	@Override
	public CategoryAttributeMapping[] fetchCategoryAttributeData(String clientId, String projectId, String categoryId)
			throws ClassNotFoundException, SQLException {

		final String uri = "http://localhost:8083/fetch-category-attribute-mapping";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("clientId", clientId)
				.queryParam("projectId", projectId).queryParam("categoryId", categoryId);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<CategoryAttributeMapping[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				CategoryAttributeMapping[].class);

		//LOGGER.info(result.getBody().toString());

		return result.getBody();
	}
	
	@Override
	public AttributeLov[] fetchCategoryLovData(String clientId, String projectId, String categoryId)
			throws ClassNotFoundException, SQLException {

		final String uri = "http://localhost:8083/fetch-category-lov-attribute";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("clientId", clientId)
				.queryParam("projectId", projectId).queryParam("categoryId", categoryId);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<AttributeLov[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				AttributeLov[].class);

		LOGGER.info("ResponseEntity----"+result.getBody());

		return result.getBody();
	}
	
	@Override
	public ProjectConfiguration fetchProjectConfig(String clientId, String projectId)
			throws ClassNotFoundException, SQLException {

		final String uri = "http://localhost:8885/fetch-project-config/"+clientId+"/"+projectId;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<ProjectConfiguration> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				ProjectConfiguration.class);

//		LOGGER.info(result.getBody().toString());

		return result.getBody();
	}
	
	@Override
	public List<Category> fetchCategoryDetails(String clientId, String projectId) throws ClassNotFoundException, SQLException {

		final String uri = "http://localhost:8083/fetch-category-structure";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("clientId", clientId)
				.queryParam("projectId", projectId);


		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<Category[]> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				Category[].class);

//		LOGGER.info(result.getBody().toString());

		return Arrays.asList(result.getBody());
	}
	
	@Override
	public List<RatingChartWeightageDTO> fetchRatingChartWeightage(String clientId, String projectId, String categoryId) {
		Constants constant = new Constants();
		String uri = constant.getFetchWeightage();
		uri = uri+ clientId+'/'+projectId+'/'+categoryId;
		
		List<RatingChartWeightageDTO> attrlovList = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<RatingChartWeightageDTO[]> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
								RatingChartWeightageDTO[].class); 
		RatingChartWeightageDTO[] objects = responseEntity.getBody(); 
		
		for (int i = 0; i < objects.length; i++) {
			RatingChartWeightageDTO data = objects[i]; 
			attrlovList.add(data); 
		}
		return attrlovList;
	}

	public String insertProductData(MultipartFile uploadfile, String filePath, String clientId, String projectId,
			String categoryId, String stageName, String batchId, String attributeRow, String datarow, String categoryName)
			throws JSONException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException,
			SQLException, InterruptedException {
		//LOGGER.info("Inside insertProductData");
		//LOGGER.info("File Path = "+filePath+" categoryId = "+categoryId);
		// Variable declarations
		List<RuleError> errorlist = new ArrayList<>();
		List<ProductScoreDetails> scoreList = new ArrayList<>();
		List<String> excelAttributeList = new ArrayList<>();
		Hashtable<String, Integer> excelAttrNameColMap = new Hashtable<String, Integer>(); 
		Hashtable<String, JSONArray> mapArrtIDRule = new Hashtable<>();
		
		
		// Setting multicolun rule names
		String[] multicolumnRules = {"checkLOVinAttribute","checkLOVinDesc","checkLOVinMetaDesc","checkLOVinMetaTitle",
				"checkCorrectTitle","checkCorrectDesc","checkCorrectLongDesc","checkTwoCellsForSameValue","shouldNotSameCellValue",
				"checkLOVinAttributeNoon","checkCorrecDescNoon","keywordPresentInMetaTitle","keywordPresentInMetaDesc","containsAny",
				"keywordRelavaceDescriptionNoon","checkCorrectTitleNoon","checkNotSameCellValue","titlePresenceUrl","writeImageLinks",
				"writeData", "checkEANOrUPC", "metaTitleFormula", "metaKeywordFormula", "metaTitleIndiluxeApparel", "metaKeywordIndiluxeApparel",
				"metaDescriptionIndiluxeApparel", "metaDescIndiluxeAccessaries", "metaTitleIndiluxeHome", "checkDescIndiluxe", "styleNoteIndiluxe",
				"walmart_checkTwoAttrSame","walmart_keywordDensityDescription","walmart_checkLOVinAttribute"};
		//String[] multicolumnRules = {};
		List<String> multicolumnRuleList = Arrays.asList(multicolumnRules);
		
		int headerRow = Integer.parseInt(attributeRow);
		int dataRow = Integer.parseInt(datarow);
		
		
		
		// Reading input file
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			//LOGGER.info("Exception - {} ", e.getMessage());
			return "";
		}
		

		// Fetching data from MDM
		ProjectConfiguration fetchProjectConfig = fetchProjectConfig(clientId, projectId);
		String parentAttribute = fetchProjectConfig.getParentAttributeId();
		
		Attribute[] attribute = fetchAllAttributeData(clientId, projectId);
		List<RatingChartWeightageDTO> attrlovList = null;
		try {
		attrlovList = fetchRatingChartWeightage(clientId, projectId, categoryId);
		}catch(Exception e) {
			//LOGGER.info("Exception "+e.getMessage());
		}
		// Fetching Category or project wise rules
		
		
		List<RuleDataModel> fetchAllRulesDataSevice = approvalservice.fetchAllRulesDataSevice();
		
		// Setting output file path
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss-SSS");  
		String strDate = formatter.format(new Date());
		String path = "E:\\IT\\Tool_Data\\Catalogue_Tool\\Upload_Folder\\" +categoryName+"_"+ strDate + ".xlsx";

		// Creating output file
		FileOutputStream fileOut = new FileOutputStream(path);

		// Reading input excel file
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		//Sheet newsheet = workbook.createSheet("comments");
		//Sheet errorsheet = workbook.createSheet("Error Log");
		
		// Setting style information for errors in excel
		CellStyle formatstyle = workbook.createCellStyle();
		CellStyle logicalstyle = workbook.createCellStyle();
		CellStyle imagestyle = workbook.createCellStyle();
		CellStyle ocrstyle = workbook.createCellStyle();

		formatstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		logicalstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		imagestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		ocrstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

		formatstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		logicalstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		imagestyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		ocrstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		int noOfSheets = workbook.getNumberOfSheets();
		String categoryIdSheet = null;
		String catgoryId =null;
	
		for (int sheet = 0; sheet < noOfSheets; sheet++) {
			
			/*if(noOfSheets<=2) {
				categoryIdSheet = catAttID;
				catgoryId = catAttID;
			}else {*/
				String catName = workbook.getSheetName(sheet);
				//LOGGER.info("catName = "+catName);
				List<Category> fetchcatDetails = fetchCategoryDetails(clientId, projectId);
				//LOGGER.info("fetchcatDetails::"+fetchcatDetails.toString());
				for(Category category : fetchcatDetails) {
					if(category.getCategoryName().equalsIgnoreCase(catName)){
						//LOGGER.info(catName+" matched");
						categoryIdSheet = category.getCategoryId();
						break;
					}else{
						categoryIdSheet = categoryId;
					}
				}
				
				catgoryId = categoryIdSheet;
			//}
			TemplateDetails[] fetchTemplateDetails = fetchTemplateDetails(clientId, projectId, categoryIdSheet);
			//LOGGER.info("fetchTemplateDetails = "+fetchTemplateDetails + "categoryIdSheet = "+categoryIdSheet);
			TemplateDetails categoryDetails = null;
			if(fetchTemplateDetails.length != 0) {
				categoryDetails = fetchTemplateDetails[0];
			}
			//LOGGER.info("categoryDetails = "+categoryDetails.toString());
			if(headerRow == -1) {
				headerRow = Integer.parseInt(categoryDetails.getAttributeRow());
			}
			if(dataRow == -1) {
				dataRow = Integer.parseInt(categoryDetails.getDataRow());
			}
			CategoryAttributeMapping[] catAttrArray = fetchCategoryAttributeData(clientId, projectId, categoryIdSheet);
			Map<String, String> catAttrMap = fetchAttributeMap(clientId, projectId, categoryIdSheet, catAttrArray);
			AttributeLov[] lovData = fetchCategoryLovData(clientId, projectId, categoryIdSheet);
			List<RuleStageCat> listruleandstagescat = fetchRuleParamRepositorey.getRuleStagesCatWise(projectId, categoryIdSheet);
			List<RuleStageCat> listruleandstagesproject  = fetchRuleParamRepositorey.getProjectwiseRules(projectId);
			
			// Overriding rules of attribute for which rules are present as per category
			mapArrtIDRule = fetchAttributeRules(attribute, listruleandstagesproject, listruleandstagescat);
			//System.out.println("mapArrtIDRule = "+mapArrtIDRule);
			//Thread.sleep(5000);
			excelAttributeList.clear();
			excelAttrNameColMap.clear();
			XSSFSheet currentSheet = workbook.getSheetAt(sheet);
			String sheetName = currentSheet.getSheetName();
			//LOGGER.info("Sheet Name = "+currentSheet.getSheetName());
			for (int row = 0; row < currentSheet.getPhysicalNumberOfRows(); row++) {

				Row currentRow = currentSheet.getRow(row);
				if(currentRow==null) {
					continue;
				}
				/*if (sheet == (noOfSheets-2)) {
					LOGGER.info("Sheet == "+sheet);
					Row newwriterow = newsheet.createRow(row);
					for (int col = 0; col < currentRow.getPhysicalNumberOfCells(); col++) {
						Cell newcurrentCell = currentRow.getCell(col);
						Cell newwritecell = newwriterow.createCell(col);
						if (newcurrentCell != null) {
							newcurrentCell.setCellType(Cell.CELL_TYPE_STRING);
							String currentCellValue = newcurrentCell.getStringCellValue();
							newwritecell.setCellValue(currentCellValue);
						}
					}
				} else {*/
					//LOGGER.info("Sheet not equal to 1");
						colLoop: 
						for (int col = 0; col < currentRow.getPhysicalNumberOfCells(); col++) {
							
						Cell currentCell = currentRow.getCell(col, Row.CREATE_NULL_AS_BLANK);
						
						if (currentCell != null) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
							String currentCellValue = currentCell.getStringCellValue();

							// Generating header list from excel
							if (row == headerRow) {
								excelAttributeList.add(currentCellValue);
								excelAttrNameColMap.put(currentCellValue.replaceAll("&", "and").replaceAll(" ", "_").replaceAll("'", "").replace(".", "@@@"), col);
								continue colLoop;
							} else if (row < dataRow) {

							} else {
								if(col>=excelAttributeList.size()) {
									continue;
								}
								// Fetching product id from the current row
								int parentColumnNumber = getParentColumnNumber(excelAttributeList , attribute, parentAttribute);
								Cell productIdCell = currentRow.getCell(parentColumnNumber);
								productIdCell.setCellType(Cell.CELL_TYPE_STRING);
								String productId = productIdCell.getStringCellValue();
								//LOGGER.info("parentAttribute = "+parentAttribute+" parentColumnNumber = "+parentColumnNumber+" productId = "+productId);
								
								
								// Fetching current cell attribute header name
								String currentAttributeName = excelAttributeList.get(col);
								currentAttributeName = currentAttributeName.replaceAll("&", "and").replaceAll(" ", "_").replaceAll("'", "").replace(".", "@@@");
								
								// Fetching rules for current attribute
								JSONArray currentRulesArray = mapArrtIDRule.get(currentAttributeName.toLowerCase());
								
								if (currentRulesArray != null) {
									//LOGGER.info(currentRulesArray.toString());
									for (int currentElement = 0; currentElement < currentRulesArray.length(); currentElement++) {
										
										Result result = new Result();
										
										// Fetching current rule
										JSONObject ruleObject = currentRulesArray.getJSONObject(currentElement);
										String ruleName = ruleObject.getString("ruleName");
										String ruleId = ruleObject.getString("ruleID");
										
										String paramJson = getAttributeRules(listruleandstagescat, currentAttributeName);
										
										String currentKey = clientId+projectId+catgoryId+currentAttributeName;
										String currentAttributeId = catAttrMap.get(currentKey.toLowerCase());
										
										if(paramJson.equals("")) {
											paramJson = getAttributeRules(listruleandstagesproject, currentAttributeName);
										}
										//LOGGER.info("currentAttributeName = "+currentAttributeName+" paramJson = "+paramJson);
										if(!multicolumnRuleList.contains(ruleName)) {
											 Object[] obj = {};
											 result = getParamJsonData(ruleName, currentCellValue,currentAttributeName, stageName, catgoryId, obj, projectId, paramJson);// methods call
											 String value = result.getValue();
												if(value!=null) {
													currentCell.setCellValue(value);
												}
										}else {
											String attrName = getParamAttributeNames(ruleName, currentCellValue,
													currentAttributeName, stageName, catgoryId, projectId, paramJson);
											//LOGGER.info("currentAttributeName ="+currentAttributeName+" attrName = "+attrName+" ruleName = "+ruleName+" currentCellValue = "+currentCellValue);
											String[] paramName = attrName.split(",");
											int counter = 0;
											int paramLength = paramName.length;
											for(int i=0; i<paramLength; i++) {
												
												// Fetching attribute data from column
												String parameterName = paramName[i].trim();
												//LOGGER.info("parameterName = "+parameterName);
												int attrIndex = 0;
												try {
												 attrIndex = excelAttrNameColMap.get(parameterName);
												}catch (Exception e) {
													//e.printStackTrace();
													continue;
												}
												Cell cellIndex = currentRow.getCell(attrIndex, Row.CREATE_NULL_AS_BLANK);
												String strVal = getCellValue(cellIndex);
												
												String key = clientId+projectId+catgoryId+paramName[i];
												String attributeId = catAttrMap.get(key);
												//String lovs = getLovs(clientId,projectId,categoryIdSheet, attributeId, lovData);
												String paramValue = null;
												
												//commented lov check
												
												/*if(lovs== null || lovs.equals("")) {
													 paramValue = strVal;
												}else {
													//if(!ruleName.equals("checkTwoCellsForSameValue")) {
														paramValue = strVal+"," + lovs;
													//}else {
													//	paramValue = strVal;
													//}
												}*/
												if(ruleName.equalsIgnoreCase("walmart_checkLOVinAttribute")) {
													if(strVal.contains(",")) {
														paramValue = strVal.replaceAll(",", "##");
													}else {
														paramValue = strVal;
													}
												}else {
													paramValue = strVal;
												}
												//LOGGER.info("parameterName = "+parameterName + " paramValue = "+paramValue);
												List<Object> obj = new ArrayList<>();
												obj.add(currentCellValue);
												obj.add(paramValue); 
												
												Result currentResult = getParamJsonData(ruleName, paramValue,
														 currentAttributeName, stageName, catgoryId, obj.toArray(), projectId, paramJson);// methods call
												String value = currentResult.getValue();
												if(value!=null) {
													currentCell.setCellValue(value);
												}
												if(currentResult.isResult()) {
													counter++;
												}
											}
											result = generateResult(counter, paramLength);
										}

										
										if (result.isResult() == false) {
											
											String ruleType = getRuleType(fetchAllRulesDataSevice, ruleName);

											RuleError ruleerror = new RuleError(sheetName,row, col, currentAttributeName,
													currentCellValue, ruleName, "", ruleType, String.valueOf(result.getScore()));

											errorlist.add(ruleerror);

											if (ruleType.equalsIgnoreCase("Format")) {
												currentCell.setCellStyle(formatstyle);
											} else if (ruleType.equalsIgnoreCase("Logical")) {
												currentCell.setCellStyle(logicalstyle);
											} else if (ruleType.equalsIgnoreCase("Image")) {
												currentCell.setCellStyle(imagestyle);
											} else if (ruleType.equalsIgnoreCase("Ocr")) {
												currentCell.setCellStyle(ocrstyle);
											}

										}
										Double effectiveScore = result.getScore();
										if(effectiveScore > 1) {
											effectiveScore = 1.0;
										}
										
										List<RuleCriteriaGroupDto> fetchCriteriaRuleMapping = new ArrayList<>();
										String criteriaId = "";
										try {
											//LOGGER.info("categoryId = "+categoryId+" currentAttributeId = "+currentAttributeId+" ruleId = "+ruleId);
											fetchCriteriaRuleMapping = ruleAdapterRepository.fetchCriteriaRuleMapping(categoryId, currentAttributeId, ruleId);
											criteriaId = fetchCriteriaRuleMapping.get(0).getCriteriaId();
											 
											
										    Double weightage = getWeightage(attrlovList, criteriaId);
										    effectiveScore = effectiveScore*weightage;
										}catch (Exception e) {
											//LOGGER.info("Error fetching criteria and weightage: "+e.getMessage());
											//e.printStackTrace();
											//LOGGER.info(e.getMessage());
										}
										
										effectiveScore = Math.round(effectiveScore* 100) / 100.0;
										Double score = Math.round(result.getScore() * 100)/ 100.0;
										
										
										try {
											//LOGGER.info("productId = "+productId+" criteriaId = "+criteriaId);
											ProductScoreDetails scoreDetails = new ProductScoreDetails(clientId,projectId,categoryId, batchId,productId,
													fetchCriteriaRuleMapping.get(0).getParameterGroupId(), criteriaId, score,effectiveScore, "v1",
													1, "kapil.parmar", "kapil.parmar");
											
											scoreList.add(scoreDetails);		
										}catch (Exception e) {
											//LOGGER.info("Error inserting score details: "+e.getMessage());
											//e.printStackTrace();
											//LOGGER.info(e.getMessage());
										}
									}
								}
							}
						}
					}

				//}
			}
			
			
		}
		
		//LOGGER.info("scoreList = "+scoreList.toString());
		int[] insertProductScorBatch = ruleAdapterRepository.insertProductScorBatch(scoreList);
		//LOGGER.info(insertProductScorBatch.length);
		
		// Writing error log in excel
		Sheet errorsheet = workbook.createSheet("Error Log");
		List<ErrorModel> errorModelList = writeErrorLog(errorsheet, errorlist, batchId, projectId);
		workbook.write(fileOut);
		
		// Insert error scores
		//int[] insertErrorScorBatch = ruleAdapterRepository.insertErrorScores(errorModelList);
		
		return path;

	}

	@Override
	public Result getParamJsonData(String ruleName, String currentCellValue, String currentAttributeName,
			String stageName, String catAttID, Object[] ob, String projectId, String paramJson) throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException, InterruptedException {
		//LOGGER.info("Inside getParamJsonData method");
		Result result = new Result();
		
		String packagePath = "com/contexio/rulebase/rule/serviceimpl";
		List<Class> classes = fetchAllClasses(packagePath); // fetch all the classes present inside the package
		
		for (Class currentClass : classes) {

			Method[] methods = getClassMethods(currentClass); // fetch methods present inside the class

			for (Method currentMethod : methods) {
				String methodName = currentMethod.getName();
				//LOGGER.info("currentClass = " + currentClass + " methodName = " + methodName);
				if (methodName.equalsIgnoreCase(ruleName)) {
					Object dog = currentClass.newInstance();
					
					Object[] obj = null;
					if (ob.length<=0) {
						obj = getArgumentsValue(currentCellValue, currentAttributeName, stageName, ruleName, catAttID, projectId, paramJson);
					}else {
						//LOGGER.info("Inside else -- ruleName = "+ruleName);
						obj = ob;
					}
					//LOGGER.info("ruleName = " + ruleName+" methodName = "+methodName+" currentCellValue = "+currentCellValue+" currentAttributeName = "+currentAttributeName);
					//LOGGER.info("params = "+paramJson.toString());
					Class<?> params[] = getParamList(obj);
					Object invoke = null;
					Result output = new Result();
					try {
					Method method = currentClass.getDeclaredMethod(methodName, params);
				//	LOGGER.info("************INVOKE*******************");
						invoke = method.invoke(dog, obj); // pass args
					}catch (Exception e) {
						//LOGGER.info("Error : "+e.getMessage());
						// TODO: handle exception
					}
					if(invoke != null) {
						output = (Result) invoke;//
					}
						

					//LOGGER.info(output);
					//LOGGER.info("output = " + output);
					return output;
					// Thread.sleep(10000);
					/*if (output == true) {
						result = true;
					}*/
				}

			}
		}

		return result;
	}
	

	private Method[] getClassMethods(Class currentClass) {
		return currentClass.getDeclaredMethods();
	}

	/*private Class<?>[] getParamList(String currentCellValue, String currentAttributeName, String stageName,
			String ruleName, String catAttID, String projectId) throws InterruptedException {
		Object[] obj = getArgumentsValue(currentCellValue, currentAttributeName, stageName, ruleName, catAttID, projectId);
		Class<?> params[] = new Class[obj.length];
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof Integer) {
				//LOGGER.info("Instance of Integer");
				params[i] = Integer.TYPE;
			} else if (obj[i] instanceof String) {
				//LOGGER.info("Instance of string");
				params[i] = String.class;
			} else if (obj[i] instanceof Double) {
			//LOGGER.info("Instance of Double");
				params[i] = double.class;
			}
			// you can do additional checks for other data types if you want.
		}
		return params;
	}*/
	
	private Class<?>[] getParamList(Object[] obj) throws InterruptedException {
		Class<?> params[] = new Class[obj.length];
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] instanceof Integer) {
				//LOGGER.info("Instance of Integer");
				params[i] = Integer.TYPE;
			} else if (obj[i] instanceof String) {
				//LOGGER.info("Instance of string");
				params[i] = String.class;
			} else if (obj[i] instanceof Double) {
			//LOGGER.info("Instance of Double");
				params[i] = double.class;
			}
			// you can do additional checks for other data types if you want.
		}
		return params;
	}

	private Object[] getArgumentsValue(String currentCellValue, String currentAttributeName, String stageName,
			String ruleName, String catAttID, String projectId, String paramJson) throws InterruptedException {
		//LOGGER.info("currentAttributeName::"+currentAttributeName+ " catAttID::"+catAttID);
		//LOGGER.info("fetchParamValue==" + fetchParamValue);
		List<Object> paramList = new ArrayList<>();
		//LOGGER.info("currentCellValue = "+currentCellValue);
		paramList.add(currentCellValue);


		try {
			//LOGGER.info("stagename = "+stageName+" ruleName = "+ruleName);
			//LOGGER.info("paramJson==" + paramJson);

			JSONArray baseArray = new JSONArray(paramJson);

			//LOGGER.info("baseArray+++++++++++==" + baseArray);


			for (int i = 0; i < baseArray.length(); i++) {

				JSONObject object = baseArray.getJSONObject(i);

				String currentStageName = object.getString("stageID");
				//LOGGER.info("stageName = "+stageName+" currentStageName =  "+currentStageName);
				if (!currentStageName.equalsIgnoreCase(stageName)) {
					continue;
				}
				//LOGGER.info("Stage name are equal = "+stageName);
				JSONArray ruleArray = object.getJSONArray("configRule");
				ruleLoop: for (int j = 0; j < ruleArray.length(); j++) {
					JSONObject ruleObject = ruleArray.getJSONObject(j);
					String currentRuleName = ruleObject.getString("ruleName");
					if (!currentRuleName.equalsIgnoreCase(ruleName)) {
						continue ruleLoop;
					}
					JSONArray paramArray = ruleObject.getJSONArray("paramValue");
					for (int k = 0; k < paramArray.length(); k++) {
						//LOGGER.info("*******************paramArray******************");
						// Thread.sleep(1000);
						JSONObject paramObject = paramArray.getJSONObject(k);
						//LOGGER.info("paramObject = "+paramObject);
						Iterator keys = paramObject.keys();
						//LOGGER.info("************before while***************");

						while (keys.hasNext()) {
							String currentKey = (String) keys.next();
							String currentValue = paramObject.getString(currentKey);
							//LOGGER.info("currentValue::"+currentValue);
							if(!currentValue.equals("")) {
								paramList.add(currentValue);
							}
						}
					}
				}
			}
		} catch (JSONException e) {
			//LOGGER.info("Error Json Exception: "+e.getMessage());
			//e.printStackTrace();
		}
		return paramList.toArray();

	}

	private List<Class> fetchAllClasses(String packagePath) {

		ClassLoader cl = RuleAdapterServiceImpl.class.getClassLoader();
		String dottedPackage = packagePath.replaceAll("[/]", ".");
		List<Class> classes = new ArrayList<>();
		URL upackage = cl.getResource(packagePath);

		DataInputStream dis = null;
		try {
			dis = new DataInputStream((InputStream) upackage.getContent());
		} catch (IOException e) {
			//LOGGER.info("Error : "+e.getMessage());
			//e.printStackTrace();
		}
		String line = null;
		try {
			while ((line = dis.readLine()) != null) {
				if (line.endsWith(".class")) {
					classes.add(Class.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
				}
			}
		} catch (ClassNotFoundException e) {
			//LOGGER.info("Error : "+e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			//LOGGER.info("Error : "+e.getMessage());
			//e.printStackTrace();
		}
		return classes;
	}
	
	private Double getWeightage(List<RatingChartWeightageDTO> weighatgeList ,String criteriaId) {
		for(int i=0; i<weighatgeList.size();i++) {
			RatingChartWeightageDTO dto = weighatgeList.get(i);
			if(dto.getCriteriaId().equalsIgnoreCase(criteriaId)) {
				return dto.getWeightage();
			}
		}
		return 1.0;
	}
	
	private int getParentColumnNumber(List<String> excelAttributeList , Attribute[] attributeArray, String currentAttriId) {
		String attributeName = "";
		for(int i=0; i<attributeArray.length;i++) {
			Attribute attr = attributeArray[i];
			if(attr.getAttributeId().equalsIgnoreCase(currentAttriId)) {
				attributeName = attr.getAttributeName();
				break;
			}
		}
		for(int i=0; i<excelAttributeList.size();i++) {
			String excelAttributeName = excelAttributeList.get(i).replaceAll(" ", "_").replaceAll("\\.", "@@@").trim();
			if(excelAttributeName.equalsIgnoreCase(attributeName)) {
				return i;
			}
		}
		return 0;
	}
	
public String getCellValue(Cell cell) {
		
		String cellValue = "";
		try {
			if (cell != null) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
			}else {
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			cellValue = "";
		}
		return cellValue;
	}

	/*public String getParamValueFromFunction(String ruleName, String currentCellValue, String currentAttributeName,
			String stageName, String catAttID) throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException, InterruptedException {
		//LOGGER.info("Inside getParamJsonData method");
		String packagePath = "com/contexio/rulebase/rule/serviceimpl";
		List<Class> classes = fetchAllClasses(packagePath); // fetch all the classes present inside the package
		//boolean result = false;
		String functionVal = "";
		for (Class currentClass : classes) {

			Method[] methods = getClassMethods(currentClass); // fetch methods present inside the class
			
			for (Method currentMethod : methods) {
				String methodName = currentMethod.getName();
				//LOGGER.info("getParamValueFromFunction methodName "+methodName);
				if (methodName.equals(ruleName)) {
					//LOGGER.info("getParamValueFromFunction methodName inside");
					Object dog = currentClass.newInstance();
					Class<?> params[] = getParamList(currentCellValue, currentAttributeName, stageName, ruleName, catAttID); 
					Object[] obj = getArgumentsValue(currentCellValue, currentAttributeName, stageName, ruleName, catAttID);
					//LOGGER.info("ruleName = " + ruleName+" methodName = "+methodName + " : obj length : "+obj.length);
					
					for(int i = 1; i< obj.length; i++) {
						String str = (String)obj[i];
						//LOGGER.info("ruleName = " + ruleName+" methodName = "+methodName + " : str : "+str);
						functionVal = (String)obj[i];
					}
					LOGGER.info("functionVal = "+functionVal.toString());
					Method method = currentClass.getDeclaredMethod(methodName, params);	
				}
			}
		}
		return functionVal;
	}*/
	
	@Override
	public String getParamAttributeNames(String ruleName, String currentCellValue, String currentAttributeName,
			String stageName, String catAttID, String projectId, String paramJson) throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException, InterruptedException {
		//LOGGER.info("Inside getParamJsonData method");
		String packagePath = "com/contexio/rulebase/rule/serviceimpl";
		List<Class> classes = fetchAllClasses(packagePath); // fetch all the classes present inside the package
		//boolean result = false;
		String functionVal = "";
		for (Class currentClass : classes) {

			Method[] methods = getClassMethods(currentClass); // fetch methods present inside the class
			
			for (Method currentMethod : methods) {
				String methodName = currentMethod.getName();
				//LOGGER.info("getParamValueFromFunction methodName "+methodName);
				if (methodName.equalsIgnoreCase(ruleName)) {
					//LOGGER.info("getParamValueFromFunction methodName == "+methodName+" ruleName == "+ruleName);
					Object[] obj = getArgumentsValue(currentCellValue, currentAttributeName, stageName, ruleName, catAttID, projectId, paramJson);
					
					for(int i = 1; i< obj.length; i++) {
						functionVal = (String)obj[i];
						//LOGGER.info("functionVal = "+functionVal);
					}
				}
			}
		}
		return functionVal;
	}
	
	String getLovs(String clientId, String projectId, String categoryId, String attributeId, 
			AttributeLov[] attributeLov) {
		LOGGER.info("getLovs---"+clientId);
		LOGGER.info("projectId---"+projectId);
		LOGGER.info("attributeId---"+attributeId);
		LOGGER.info("categoryId---"+categoryId);
		if(attributeId == null || attributeId.equals("")) {
			return "";
		}
		String lovString = "";
		for(AttributeLov data : attributeLov) {
			LOGGER.info("getLovs---"+data);
			if(data.getClient_id().equalsIgnoreCase(clientId) && 
					data.getProject_id().equalsIgnoreCase(projectId) && data.getCategory_id().equalsIgnoreCase(categoryId) 
					&& data.getAttribute_id().equalsIgnoreCase(attributeId)) {
				List<String> lovs = data.getLovs();
				for(int i=0; i<lovs.size();i++) {
					if(i == (lovs.size()-1)){
						lovString += lovs.get(i);
					}else {
						String val = lovs.get(i)+ ",";
						lovString += val;
					}
				}
			}
		}
		return lovString;
	}
	
	@Override
	public Map<String, String> fetchAttributeMap(String clientId, String projectId, String categoryId, CategoryAttributeMapping[] mapping){
		Map<String, String> attrMap = new HashMap<>();
		for(CategoryAttributeMapping attr : mapping) {
			
			//LOGGER.info("attr.getAttributeName() = "+attr.getAttributeName());
			String key = attr.getClientId()+attr.getProjectId()+attr.getCategoryId()+attr.getAttributeName().toLowerCase();
			String value = attr.getAttributeId();
			attrMap.put(key.toLowerCase(), value);
		}
		return attrMap;
	}
	
	@Override
	public Hashtable<String, JSONArray> fetchAttributeRules(Attribute[] attribute, List<RuleStageCat> listruleandstages, List<RuleStageCat> listruleandstagesCat) throws JSONException{
		
		Hashtable<String, JSONArray> mapArrtIDRule = new Hashtable<>();
		for (int i = 0; i < attribute.length; i++) {
			String ApiAttribute = attribute[i].getAttributeId();
			for (int j = 0; j < listruleandstages.size(); j++) {

				RuleStageCat ruleStage = listruleandstages.get(j);

				String rulesStagesAttribute = ruleStage.getId();
				if (ApiAttribute.equalsIgnoreCase(rulesStagesAttribute)) {
					String globalattributename = attribute[i].getAttributeName();

					String rulename = ruleStage.getRuleNames();
					JSONArray jsonArray = new JSONArray(rulename);

					for (int k = 0; k < jsonArray.length(); k++) {
						JSONObject jsonobj = jsonArray.getJSONObject(k);
						String stageId = jsonobj.getString("stageID");
						JSONArray ruleArray = jsonobj.getJSONArray("assinedRule");
						mapArrtIDRule.put(globalattributename.toLowerCase(), ruleArray);
					}

				}
			}

		}
		
		for (int i = 0; i < attribute.length; i++) {
			String ApiAttribute = attribute[i].getAttributeId();
			for (int j = 0; j < listruleandstagesCat.size(); j++) {

				RuleStageCat ruleStage = listruleandstagesCat.get(j);

				String rulesStagesAttribute = ruleStage.getId();
				if (ApiAttribute.equalsIgnoreCase(rulesStagesAttribute)) {
					String globalattributename = attribute[i].getAttributeName();

					String rulename = ruleStage.getRuleNames();
					JSONArray jsonArray = new JSONArray(rulename);

					for (int k = 0; k < jsonArray.length(); k++) {
						JSONObject jsonobj = jsonArray.getJSONObject(k);
						String stageId = jsonobj.getString("stageID");
						JSONArray ruleArray = jsonobj.getJSONArray("assinedRule");
						mapArrtIDRule.put(globalattributename.toLowerCase(), ruleArray);
					}

				}
			}

		}
		return mapArrtIDRule;
	}
	
	private List<ErrorModel> writeErrorLog(Sheet errorsheet, List<RuleError> errorlist, String batchId, String projectId) {
		Row writerow = errorsheet.createRow(0);
		writerow.createCell(0).setCellValue("Sheet Name");
		writerow.createCell(1).setCellValue("Row No");
		writerow.createCell(2).setCellValue("Column No");
		writerow.createCell(3).setCellValue("Attribute Name");
		writerow.createCell(4).setCellValue("Attribute Value");
		writerow.createCell(5).setCellValue("Rule Name");
		writerow.createCell(6).setCellValue("Rule Description");
		writerow.createCell(7).setCellValue("Rule Type");
		writerow.createCell(8).setCellValue("Score");
		//LOGGER.info("Before writing error file = "+dtf.format(LocalDateTime.now()));
		List<ErrorModel> errorModelList = new ArrayList<ErrorModel>();
		for (int i = 0; i < errorlist.size(); i++) {

			Row errorRow = errorsheet.createRow(i + 1);
			RuleError errorList = (RuleError) errorlist.get(i);
			errorRow.createCell(0).setCellValue(errorList.getSheetName());
			errorRow.createCell(1).setCellValue(errorList.getRowNo());
			errorRow.createCell(2).setCellValue(errorList.getColumnNo());
			errorRow.createCell(3).setCellValue(errorList.getAttributeName());
			errorRow.createCell(4).setCellValue(errorList.getAttributeValue());
			errorRow.createCell(5).setCellValue(errorList.getRuleName());
			errorRow.createCell(6).setCellValue(errorList.getRuleDiscription());
			errorRow.createCell(7).setCellValue(errorList.getRuleType());
			errorRow.createCell(8).setCellValue(errorList.getScore());

			ErrorModel model = new ErrorModel(batchId, projectId, errorList.getRowNo(), errorList.getColumnNo(),
					errorList.getAttributeName(), errorList.getAttributeValue(), errorList.getRuleName(),
					errorList.getRuleType());
			errorModelList.add(model);
			//ruleAdapterRepository.insertError(model);

		}
		return errorModelList;

	}
	
	@Override
	public Result generateResult(int counter, int paramLength) {
		Result result = new Result();
		if(counter == 0) {
			result.setResult(false);
			result.setScore(0);
		}else if(counter > 0 && counter == paramLength) {
			result.setResult(true);
			result.setScore(1);
		}else {
			double score = (double)counter/(double)paramLength;
			result.setResult(false);
			result.setScore(score);
		}
		return result;
	}
	
	@Override
	public String getAttributeRules(List<RuleStageCat> listruleandstagescat, String attributeName) {
		for(RuleStageCat data : listruleandstagescat) {
			if(data.getAttributeName().equalsIgnoreCase(attributeName)) {
				return data.getParamNames();
			}
		}
		return "";
	}
	
	private String getRuleType(List<RuleDataModel> ruleList, String ruleName) {
		for(RuleDataModel rule : ruleList) {
			if(rule.getRule_Name().equalsIgnoreCase(ruleName)) {
				return rule.getRuletype();
			}
		}
		return "";
	}
	
	// Added by Anjali
	
	/*public String insertProductData_exceptwalmartcanda(MultipartFile uploadfile, String filePath, String clientId, String projectId,
	String categoryId, String stageName, String batchId, String attributeRow, String datarow, String categoryName)
	throws JSONException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
	SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException,
	SQLException, InterruptedException {
//LOGGER.info("Inside insertProductData");
//LOGGER.info("File Path = "+filePath+" categoryId = "+categoryId);
// Variable declarations
List<RuleError> errorlist = new ArrayList<>();
List<ProductScoreDetails> scoreList = new ArrayList<>();
List<String> excelAttributeList = new ArrayList<>();
Hashtable<String, Integer> excelAttrNameColMap = new Hashtable<String, Integer>(); 
Hashtable<String, JSONArray> mapArrtIDRule = new Hashtable<>();


// Setting multicolun rule names
String[] multicolumnRules = {"checkLOVinAttribute","checkLOVinDesc","checkLOVinMetaDesc","checkLOVinMetaTitle",
		"checkCorrectTitle","checkCorrectDesc","checkCorrectLongDesc","checkTwoCellsForSameValue","shouldNotSameCellValue",
		"checkLOVinAttributeNoon","checkCorrecDescNoon","keywordPresentInMetaTitle","keywordPresentInMetaDesc","containsAny",
		"keywordRelavaceDescriptionNoon","checkCorrectTitleNoon","checkNotSameCellValue","titlePresenceUrl","writeImageLinks",
		"writeData", "checkEANOrUPC", "metaTitleFormula", "metaKeywordFormula", "metaTitleIndiluxeApparel", "metaKeywordIndiluxeApparel",
		"metaDescriptionIndiluxeApparel", "metaDescIndiluxeAccessaries", "metaTitleIndiluxeHome", "checkDescIndiluxe", "styleNoteIndiluxe",
		"walmart_checkTwoAttrSame","walmart_keywordDensityDescription","walmart_checkLOVinAttribute"};
//String[] multicolumnRules = {};
List<String> multicolumnRuleList = Arrays.asList(multicolumnRules);

int headerRow = Integer.parseInt(attributeRow);
int dataRow = Integer.parseInt(datarow);



// Reading input file
FileInputStream file = null;
try {
	file = new FileInputStream(new File(filePath));
} catch (FileNotFoundException e) {
	//LOGGER.info("Exception - {} ", e.getMessage());
	return "";
}


// Fetching data from MDM
ProjectConfiguration fetchProjectConfig = fetchProjectConfig(clientId, projectId);
String parentAttribute = fetchProjectConfig.getParentAttributeId();

Attribute[] attribute = fetchAllAttributeData(clientId, projectId);
List<RatingChartWeightageDTO> attrlovList = null;
try {
attrlovList = fetchRatingChartWeightage(clientId, projectId, categoryId);
}catch(Exception e) {
	//LOGGER.info("Exception "+e.getMessage());
}
// Fetching Category or project wise rules


List<RuleDataModel> fetchAllRulesDataSevice = approvalservice.fetchAllRulesDataSevice();

// Setting output file path
DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS");
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss-SSS");  
String strDate = formatter.format(new Date());
String path = "E:\\IT\\Tool_Data\\Catalogue_Tool\\Upload_Folder\\" +categoryName+"_"+ strDate + ".xlsx";

// Creating output file
FileOutputStream fileOut = new FileOutputStream(path);

// Reading input excel file
XSSFWorkbook workbook = new XSSFWorkbook(file);


// Setting style information for errors in excel
CellStyle formatstyle = workbook.createCellStyle();
CellStyle logicalstyle = workbook.createCellStyle();
CellStyle imagestyle = workbook.createCellStyle();
CellStyle ocrstyle = workbook.createCellStyle();

formatstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
logicalstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
imagestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
ocrstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

formatstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
logicalstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
imagestyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
ocrstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

int noOfSheets = workbook.getNumberOfSheets();
String categoryIdSheet = null;
String catgoryId =null;

for (int sheet = 0; sheet < noOfSheets; sheet++) {

		String catName = workbook.getSheetName(sheet);
		//LOGGER.info("catName = "+catName);
		List<Category> fetchcatDetails = fetchCategoryDetails(clientId, projectId);
		//LOGGER.info("fetchcatDetails::"+fetchcatDetails.toString());
		for(Category category : fetchcatDetails) {
			if(category.getCategoryName().equalsIgnoreCase(catName)){
				//LOGGER.info(catName+" matched");
				categoryIdSheet = category.getCategoryId();
				break;
			}else{
				categoryIdSheet = categoryId;
			}
		}
		
		catgoryId = categoryIdSheet;
	//}
	TemplateDetails[] fetchTemplateDetails = fetchTemplateDetails(clientId, projectId, categoryIdSheet);
	LOGGER.info("fetchTemplateDetails = "+fetchTemplateDetails + "categoryIdSheet = "+categoryIdSheet);
	TemplateDetails categoryDetails = null;
	if(fetchTemplateDetails.length != 0) {
		categoryDetails = fetchTemplateDetails[0];
	}
	//LOGGER.info("categoryDetails = "+categoryDetails.toString());
	if(headerRow == -1) {
		headerRow = Integer.parseInt(categoryDetails.getAttributeRow());
	}
	if(dataRow == -1) {
		dataRow = Integer.parseInt(categoryDetails.getDataRow());
	}
	CategoryAttributeMapping[] catAttrArray = fetchCategoryAttributeData(clientId, projectId, categoryIdSheet);
	Map<String, String> catAttrMap = fetchAttributeMap(clientId, projectId, categoryIdSheet, catAttrArray);
	AttributeLov[] lovData = fetchCategoryLovData(clientId, projectId, categoryIdSheet);
	//List<RuleStageCat> listruleandstagescat = fetchRuleParamRepositorey.getRuleStagesCatWise(projectId, categoryIdSheet);
	
	List<RuleStageCat> listruleandstagescat = fetchRuleParamRepositorey.getRuleStagesCatWise(projectId, "");
	
	List<RuleStageCat> listruleandstagesproject  = fetchRuleParamRepositorey.getProjectwiseRules(projectId);
	//fetchAttributeRulesForProjectandCategoryBase
	// fetchAttributeRules Overriding rules of attribute for which rules are present as per category
	mapArrtIDRule = fetchAttributeRules(attribute, listruleandstagesproject, listruleandstagescat);
	System.out.println("mapArrtIDRule = "+mapArrtIDRule);
	
	
	
	excelAttributeList.clear();
	excelAttrNameColMap.clear();
	XSSFSheet currentSheet = workbook.getSheetAt(sheet);
	String sheetName = currentSheet.getSheetName();
	//LOGGER.info("Sheet Name = "+currentSheet.getSheetName());
	for (int row = 0; row < currentSheet.getPhysicalNumberOfRows(); row++) {

		Row currentRow = currentSheet.getRow(row);
		if(currentRow==null) {
			continue;
		}
		
				colLoop: 
				for (int col = 0; col < currentRow.getPhysicalNumberOfCells(); col++) {
					
				Cell currentCell = currentRow.getCell(col, Row.CREATE_NULL_AS_BLANK);
				
				if (currentCell != null) {
					currentCell.setCellType(Cell.CELL_TYPE_STRING);
					String currentCellValue = currentCell.getStringCellValue();

					// Generating header list from excel
					if (row == headerRow) {
						excelAttributeList.add(currentCellValue);
						excelAttrNameColMap.put(currentCellValue.replaceAll("&", "and").replaceAll(" ", "_").replaceAll("'", "").replace(".", "@@@"), col);
						continue colLoop;
					} else if (row < dataRow) {

					} else {
						if(col>=excelAttributeList.size()) {
							continue;
						}
						// Fetching product id from the current row
						int parentColumnNumber = getParentColumnNumber(excelAttributeList , attribute, parentAttribute);
						Cell productIdCell = currentRow.getCell(parentColumnNumber);
						productIdCell.setCellType(Cell.CELL_TYPE_STRING);
						String productId = productIdCell.getStringCellValue();
						//LOGGER.info("parentAttribute = "+parentAttribute+" parentColumnNumber = "+parentColumnNumber+" productId = "+productId);
						
						
						// Fetching current cell attribute header name
						String currentAttributeName = excelAttributeList.get(col);
						currentAttributeName = currentAttributeName.replaceAll("&", "and").replaceAll(" ", "_").replaceAll("'", "").replace(".", "@@@");
						
						// Fetching rules for current attribute
						JSONArray currentRulesArray = mapArrtIDRule.get(currentAttributeName.toLowerCase());
						
						if (currentRulesArray != null) {
							//LOGGER.info(currentRulesArray.toString());
							for (int currentElement = 0; currentElement < currentRulesArray.length(); currentElement++) {
								
								Result result = new Result();
								
								// Fetching current rule
								JSONObject ruleObject = currentRulesArray.getJSONObject(currentElement);
								String ruleName = ruleObject.getString("ruleName");
								String ruleId = ruleObject.getString("ruleID");
								
								String paramJson = getAttributeRules(listruleandstagescat, currentAttributeName);
								
								String currentKey = clientId+projectId+catgoryId+currentAttributeName;
								String currentAttributeId = catAttrMap.get(currentKey.toLowerCase());
								
								if(paramJson.equals("")) {
									paramJson = getAttributeRules(listruleandstagesproject, currentAttributeName);
								}
								//LOGGER.info("currentAttributeName = "+currentAttributeName+" paramJson = "+paramJson);
								if(!multicolumnRuleList.contains(ruleName) ) {
									Thread.sleep(500);
									//LOGGER.info("!multicolumnRuleList.contains(ruleName)"+ruleName);
								
									 Object[] obj = {};
									 result = getParamJsonData(ruleName, currentCellValue,currentAttributeName, stageName, catgoryId, obj, projectId, paramJson);// methods call
									// LOGGER.info("result.getValue()"+result.getValue());
									 String value = result.getValue();
										if(value!=null) {
											currentCell.setCellValue(value);
										}
								}else {
									//LOGGER.info("Inside Param Attribute");
								//	Thread.sleep(20000);
									String attrName = getParamAttributeNames(ruleName, currentCellValue,
											currentAttributeName, stageName, catgoryId, projectId, paramJson);
									//LOGGER.info("currentAttributeName ="+currentAttributeName+" attrName = "+attrName+" ruleName = "+ruleName+" currentCellValue = "+currentCellValue);
									String[] paramName = attrName.split(",");
									int counter = 0;
									int paramLength = paramName.length;
									for(int i=0; i<paramLength; i++) {
										
										// Fetching attribute data from column
										String parameterName = paramName[i].trim();
										//LOGGER.info("parameterName = "+parameterName);
										int attrIndex = 0;
										try {
										 attrIndex = excelAttrNameColMap.get(parameterName);
										}catch (Exception e) {
											//e.printStackTrace();
											continue;
										}
										Cell cellIndex = currentRow.getCell(attrIndex, Row.CREATE_NULL_AS_BLANK);
										String strVal = getCellValue(cellIndex);
										
										String key = clientId+projectId+catgoryId+paramName[i];
										String attributeId = catAttrMap.get(key.toLowerCase());
										String lovs = getLovs(clientId,projectId,categoryIdSheet, attributeId, lovData);
										String paramValue = null;
										
										//commented lov check
										
										if(lovs== null || lovs.equals("")) {
											 //LOGGER.info("//commented lov check");
											 paramValue = strVal;
										}else {
											if(!ruleName.equals("checkTwoCellsForSameValue")) {
												paramValue =  lovs;
											}else {
												paramValue = strVal;
												//System.out.println(paramValue);
											}
										}
										//commented by anjali for Tata CLiQ
										if(ruleName.equalsIgnoreCase("walmart_checkLOVinAttribute")) {
											if(strVal.contains(",")) {
												paramValue = strVal.replaceAll(",", "##");
											}else {
												paramValue = strVal;
											}
										}else {
											paramValue = strVal;
										}
										//LOGGER.info("parameterName = "+parameterName + " paramValue = "+paramValue);
										List<Object> obj = new ArrayList<>();
										obj.add(currentCellValue);
										obj.add(paramValue); 
										
										Result currentResult = getParamJsonData(ruleName, paramValue,
												 currentAttributeName, stageName, catgoryId, obj.toArray(), projectId, paramJson);// methods call
										String value = currentResult.getValue();
										if(value!=null) {
											currentCell.setCellValue(value);
										}
										if(currentResult.isResult()) {
											counter++;
										}
									}
									result = generateResult(counter, paramLength);
								}

								
								if (result.isResult() == false) {
									
									String ruleType = getRuleType(fetchAllRulesDataSevice, ruleName);

									RuleError ruleerror = new RuleError(sheetName,row, col, currentAttributeName,
											currentCellValue, ruleName, "", ruleType, String.valueOf(result.getScore()));

									errorlist.add(ruleerror);

									if (ruleType.equalsIgnoreCase("Format")) {
										currentCell.setCellStyle(formatstyle);
									} else if (ruleType.equalsIgnoreCase("Logical")) {
										currentCell.setCellStyle(logicalstyle);
									} else if (ruleType.equalsIgnoreCase("Image")) {
										currentCell.setCellStyle(imagestyle);
									} else if (ruleType.equalsIgnoreCase("Ocr")) {
										currentCell.setCellStyle(ocrstyle);
									}

								}
								
								
							}
						}
					}
				}
			}

		//}
	}
	
	
}

Sheet errorsheet = workbook.createSheet("Error Log");
List<ErrorModel> errorModelList = writeErrorLog(errorsheet, errorlist, batchId, projectId);
workbook.write(fileOut);

return path;

}*/

	
	
	public String insertProductData_exceptwalmartcanda(MultipartFile uploadfile, String filePath, String clientId, String projectId,
			String categoryId, String stageName, String batchId, String attributeRow, String datarow, String categoryName)
			throws JSONException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException,
			SQLException, InterruptedException {
		//LOGGER.info("Inside insertProductData");
		//LOGGER.info("File Path = "+filePath+" categoryId = "+categoryId);
		// Variable declarations
		List<RuleError> errorlist = new ArrayList<>();
		List<ProductScoreDetails> scoreList = new ArrayList<>();
		List<String> excelAttributeList = new ArrayList<>();
		Hashtable<String, Integer> excelAttrNameColMap = new Hashtable<String, Integer>(); 
		Hashtable<String, JSONArray> mapArrtIDRule = new Hashtable<>();
		
		
		// Setting multicolun rule names
		String[] multicolumnRules = {"checkLOVinAttribute","checkLOVinDesc","checkLOVinMetaDesc","checkLOVinMetaTitle",
				"checkCorrectTitle","checkCorrectDesc","checkCorrectLongDesc","checkTwoCellsForSameValue","shouldNotSameCellValue",
				"checkLOVinAttributeNoon","checkCorrecDescNoon","keywordPresentInMetaTitle","keywordPresentInMetaDesc","containsAny",
				"keywordRelavaceDescriptionNoon","checkCorrectTitleNoon","checkNotSameCellValue","titlePresenceUrl","writeImageLinks",
				"writeData", "checkEANOrUPC", "metaTitleFormula", "metaKeywordFormula", "metaTitleIndiluxeApparel", "metaKeywordIndiluxeApparel",
				"metaDescriptionIndiluxeApparel", "metaDescIndiluxeAccessaries", "metaTitleIndiluxeHome", "checkDescIndiluxe", "styleNoteIndiluxe",
				"walmart_checkTwoAttrSame","walmart_keywordDensityDescription","walmart_checkLOVinAttribute","tatabeauty_EANEqualSKU",
				
				"tatabeauty_ProductNameAsProductTitle","tatabeauty_presenceOfSizeInTitle","tatabeauty_highlight_freebie_if_mrp_01","tatabeauty_checkGlobalIDValueForEAN","tatabeauty_checkGlobalIDValueForUPC","tatabeauty_checkGlobalIDValueForMPN","tatabeauty_checkGlobalIdEANUPCMPN",
				"tataBeauty_Makeup_Color_ShadeName_Equal","checkMultiLOVinAttribute"
				};
		List<String> multicolumnRuleList = Arrays.asList(multicolumnRules);
		
		String[] multicolumnRulesLOV = {"tatabeauty_SizeEqualCombo_Product_No","tataBeauty_FragPerf_Cat_checkMandatoryColumn","tataBeauty_MakeUp_Cat_checkMandatoryColumn","tataBeauty_Hair_Cat_checkMandatoryColumn"
				,"tataBeauty_FragPerf_NonLov_checkMandatoryColumn"};		
		List<String> multicolumnRuleListLOV = Arrays.asList(multicolumnRulesLOV);
	
		String[] multicolumnSetValue_BaseOnListLOV= {"tatabeauty_freebieAsMrp"};
		List<String> multicolumnSetValue_BaseOnLOV = Arrays.asList(multicolumnSetValue_BaseOnListLOV);
		
		int headerRow = Integer.parseInt(attributeRow);
		int dataRow = Integer.parseInt(datarow);
		
		
		
		// Reading input file
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			//LOGGER.info("Exception - {} ", e.getMessage());
			return "";
		}
		

		// Fetching data from MDM
		ProjectConfiguration fetchProjectConfig = fetchProjectConfig(clientId, projectId);
		String parentAttribute = fetchProjectConfig.getParentAttributeId();
		
		Attribute[] attribute = fetchAllAttributeData(clientId, projectId);
		List<RatingChartWeightageDTO> attrlovList = null;
		try {
		attrlovList = fetchRatingChartWeightage(clientId, projectId, categoryId);
		}catch(Exception e) {
			//LOGGER.info("Exception "+e.getMessage());
		}
		// Fetching Category or project wise rules
		
		
		List<RuleDataModel> fetchAllRulesDataSevice = approvalservice.fetchAllRulesDataSevice();
		
		// Setting output file path
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss-SSS");  
		String strDate = formatter.format(new Date());
		String path = "E:\\IT\\Tool_Data\\Catalogue_Tool\\Upload_Folder\\" +categoryName+"_"+ strDate + ".xlsx";

		// Creating output file
		FileOutputStream fileOut = new FileOutputStream(path);

		// Reading input excel file
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		
		// Setting style information for errors in excel
		CellStyle formatstyle = workbook.createCellStyle();
		CellStyle logicalstyle = workbook.createCellStyle();
		CellStyle imagestyle = workbook.createCellStyle();
		CellStyle ocrstyle = workbook.createCellStyle();

		formatstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		logicalstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		imagestyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		ocrstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());

		formatstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		logicalstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		imagestyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		ocrstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		int noOfSheets = workbook.getNumberOfSheets();
		String categoryIdSheet = null;
		String catgoryId =null;
	
		for (int sheet = 0; sheet < noOfSheets; sheet++) {
		
				String catName = workbook.getSheetName(sheet);
				//LOGGER.info("catName = "+catName);
				List<Category> fetchcatDetails = fetchCategoryDetails(clientId, projectId);
				//LOGGER.info("fetchcatDetails::"+fetchcatDetails.toString());
				for(Category category : fetchcatDetails) {
					if(category.getCategoryName().equalsIgnoreCase(catName)){
						//LOGGER.info(catName+" matched");
						categoryIdSheet = category.getCategoryId();
						break;
					}else{
						categoryIdSheet = categoryId;
					}
				}
				
				catgoryId = categoryIdSheet;
			//}
			TemplateDetails[] fetchTemplateDetails = fetchTemplateDetails(clientId, projectId, categoryIdSheet);
			//LOGGER.info("fetchTemplateDetails = "+fetchTemplateDetails + "categoryIdSheet = "+categoryIdSheet);
			TemplateDetails categoryDetails = null;
			if(fetchTemplateDetails.length != 0) {
				categoryDetails = fetchTemplateDetails[0];
			}
			//LOGGER.info("categoryDetails = "+categoryDetails.toString());
			if(headerRow == -1) {
				headerRow = Integer.parseInt(categoryDetails.getAttributeRow());
			}
			if(dataRow == -1) {
				dataRow = Integer.parseInt(categoryDetails.getDataRow());
			}
			CategoryAttributeMapping[] catAttrArray = fetchCategoryAttributeData(clientId, projectId, categoryIdSheet);
			Map<String, String> catAttrMap = fetchAttributeMap(clientId, projectId, categoryIdSheet, catAttrArray);
			AttributeLov[] lovData = fetchCategoryLovData(clientId, projectId, categoryIdSheet);
			List<RuleStageCat> listruleandstagescat = fetchRuleParamRepositorey.getRuleStagesCatWise(projectId, categoryIdSheet);
			List<RuleStageCat> listruleandstagesproject  = fetchRuleParamRepositorey.getProjectwiseRules(projectId);
			System.out.println("catAttrMap = "+catAttrMap);
			//System.out.println("listruleandstagesproject = "+listruleandstagesproject);
			// Overriding rules of attribute for which rules are present as per category
			mapArrtIDRule = fetchAttributeRules(attribute, listruleandstagesproject, listruleandstagescat);
			//System.out.println("lovData = "+lovData);
		
			excelAttributeList.clear();
			excelAttrNameColMap.clear();
			XSSFSheet currentSheet = workbook.getSheetAt(sheet);
			String sheetName = currentSheet.getSheetName();
			//LOGGER.info("Sheet Name = "+currentSheet.getSheetName());
			for (int row = 0; row < currentSheet.getPhysicalNumberOfRows(); row++) {

				Row currentRow = currentSheet.getRow(row);
				if(currentRow==null) {
					continue;
				}
				
						colLoop: 
						for (int col = 0; col < currentRow.getPhysicalNumberOfCells(); col++) {
							
						Cell currentCell = currentRow.getCell(col, Row.CREATE_NULL_AS_BLANK);
						
						if (currentCell != null) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
							String currentCellValue = currentCell.getStringCellValue();
							
							// Generating header list from excel
							if (row == headerRow) {
								//LOGGER.info("currentCellValue = "+currentCellValue+" headerRow = "+headerRow);
								excelAttributeList.add(currentCellValue);
								excelAttrNameColMap.put(currentCellValue.replaceAll("&", "and").replaceAll(" ", "_").replaceAll("'", "").replace(".", "@@@"), col);
								continue colLoop;
							} else if (row < dataRow) {

							} else {
								if(col>=excelAttributeList.size()) {
									continue;
								}
								// Fetching product id from the current row
								int parentColumnNumber = getParentColumnNumber(excelAttributeList , attribute, parentAttribute);
								Cell productIdCell = currentRow.getCell(parentColumnNumber);
								productIdCell.setCellType(Cell.CELL_TYPE_STRING);
								String productId = productIdCell.getStringCellValue();
								//LOGGER.info("parentAttribute = "+parentAttribute+" parentColumnNumber = "+parentColumnNumber+" productId = "+productId);
								
								
								// Fetching current cell attribute header name
								String currentAttributeName = excelAttributeList.get(col);
								currentAttributeName = currentAttributeName.replaceAll("&", "and").replaceAll(" ", "_").replaceAll("'", "").replace(".", "@@@");
								
								// Fetching rules for current attribute
								JSONArray currentRulesArray = mapArrtIDRule.get(currentAttributeName.toLowerCase());
								
								if (currentRulesArray != null) {
									//LOGGER.info(currentRulesArray.toString());
									for (int currentElement = 0; currentElement < currentRulesArray.length(); currentElement++) {
										
										Result result = new Result();
										
										// Fetching current rule
										JSONObject ruleObject = currentRulesArray.getJSONObject(currentElement);
										String ruleName = ruleObject.getString("ruleName");
										String ruleId = ruleObject.getString("ruleID");
										//LOGGER.info("ruleName---"+ruleName);
										String paramJson = getAttributeRules(listruleandstagescat, currentAttributeName);
										
										String currentKey = clientId+projectId+catgoryId+currentAttributeName;
										String currentAttributeId = catAttrMap.get(currentKey.toLowerCase());
										
										if(paramJson.equals("")) {
											paramJson = getAttributeRules(listruleandstagesproject, currentAttributeName);
										}
										//LOGGER.info("currentAttributeName = "+currentAttributeName+" paramJson = "+paramJson);
										if(!multicolumnRuleList.contains(ruleName) && !multicolumnRuleListLOV.contains(ruleName) && !multicolumnSetValue_BaseOnLOV.contains(ruleName)) {
											
											//LOGGER.info("11111111111111");
											//LOGGER.info("currentAttributeName = "+currentAttributeName+" currentCellValue = "+currentCellValue);
											
											//Thread.sleep(2000);
											 Object[] obj = {};
											 result = getParamJsonData(ruleName, currentCellValue,currentAttributeName, stageName, catgoryId, obj, projectId, paramJson);// methods call
											 String value = result.getValue();
											/// LOGGER.info("if(!multicolumnRuleList.contains(ruleName))value"+value);
												if(value!=null) {						
													currentCell.setCellValue(value);
												}
										}
										
									//Added By Aadesh for Code Changes For LOV Parameter Related Rule in Rulebase Engine on 05-05-2023										
										else if(multicolumnRuleListLOV.contains(ruleName)) {
											
											//	Thread.sleep(20000);
												String attrName = getParamAttributeNames(ruleName, currentCellValue,
														currentAttributeName, stageName, catgoryId, projectId, paramJson);
												//LOGGER.info("multicolumnRuleListLOV getParamAttributeNames--"+ruleName +"---"+currentAttributeName+"---"+currentCellValue+"---"+stageName+"---"+catgoryId+"---"+projectId+"---"+paramJson);
												String[] paramName = attrName.split(",");
												String[] paramJsonString = paramJson.split(",");
												String paramJsonStringConfig = paramJsonString[3].trim();
												//LOGGER.info("paramJsonStringConfig--"+paramJsonStringConfig.toString());
												int counter = 0;
												//LOGGER.info(ruleName+"-----222222222222222--"+attrName);
												int paramLength = paramName.length;
												//LOGGER.info("paramLength--"+paramLength);
											
												for(int i=0; i<paramLength; i++) {
													
													// Fetching attribute data from column
													String parameterName = paramName[i].trim();
													//LOGGER.info("parameterName = "+parameterName);
													int attrIndex = 0;
													try {
													 attrIndex = excelAttrNameColMap.get(parameterName);
													 //LOGGER.info(" attrIndex = "+attrIndex);
													}catch (Exception e) {
														//e.printStackTrace();
														//LOGGER.info(" attrIndex = catch");
														continue;
													}
													Cell cellIndex = currentRow.getCell(attrIndex, Row.CREATE_NULL_AS_BLANK);
													//LOGGER.info(" cellIndex = "+cellIndex);
													String strVal = getCellValue(cellIndex);
													//LOGGER.info(" strVal = "+strVal);
													
													//String key = clientId+projectId+catgoryId+paramName[i];
													//String attributeId = catAttrMap.get(key.toLowerCase());
													//String lovs = getLovs(clientId,projectId,categoryIdSheet, attributeId, lovData);
													String paramValue = null;
													
													paramValue = strVal;
												
													//LOGGER.info("parameterName = "+parameterName + " paramValue = "+paramValue);
													List<Object> obj = new ArrayList<>();
													obj.add(currentCellValue);
													obj.add(paramValue); 
													
													Result currentResult = getParamJsonData(ruleName, paramValue,
															 currentAttributeName, stageName, catgoryId, obj.toArray(), projectId, paramJson);// methods call
													String value = currentResult.getValue();
													
													//LOGGER.info("value = "+value + " value = "+value);
													/*if(value!=null) 
													{
														currentCell.setCellValue(value);
													}*/
													if(currentResult.isResult()) {
														counter++;
													}
												}
												result = generateResult(counter, paramLength);
												//LOGGER.info("result---------"+result);
										}
										//Ended By Aadesh for Code Changes For LOV Parameter Related Rule in Rulebase Engine on 05-05-2023
										
										//Added By Aadesh for Code Changes For LOV Parameter Related Rule in Rulebase Engine on 05-05-2023										
										else if(multicolumnSetValue_BaseOnLOV.contains(ruleName) && !multicolumnRuleListLOV.contains(ruleName)) {
										
											//	Thread.sleep(20000);
												String attrName = getParamAttributeNames(ruleName, currentCellValue,
														currentAttributeName, stageName, catgoryId, projectId, paramJson);
												//LOGGER.info("multicolumnSetValue_BaseOnLOV getParamAttributeNames--"+ruleName +"---"+currentAttributeName+"---"+currentCellValue+"---"+stageName+"---"+catgoryId+"---"+projectId+"---"+paramJson);
												String[] paramName = attrName.split(",");
												String[] paramJsonString = paramJson.split(",");
												String paramJsonStringConfig = paramJsonString[3].trim();
												//LOGGER.info("paramJsonStringConfig--"+paramJsonStringConfig.toString());
												int counter = 0;
												//LOGGER.info(ruleName+"-----3333333333333-----"+attrName);
												int paramLength = paramName.length;
												//LOGGER.info("paramLength--"+paramLength);
											
												for(int i=0; i<paramLength; i++) {
													
													// Fetching attribute data from column
													String parameterName = paramName[i].trim();
													//LOGGER.info("parameterName = "+parameterName);
													int attrIndex = 0;
													try {
													 attrIndex = excelAttrNameColMap.get(parameterName);
													}catch (Exception e) {
														//e.printStackTrace();
														continue;
													}
													Cell cellIndex = currentRow.getCell(attrIndex, Row.CREATE_NULL_AS_BLANK);
													String strVal = getCellValue(cellIndex);
													
													//String key = clientId+projectId+catgoryId+paramName[i];
													//String attributeId = catAttrMap.get(key.toLowerCase());
													//String lovs = getLovs(clientId,projectId,categoryIdSheet, attributeId, lovData);
													String paramValue = null;
													
													paramValue = strVal;
												
													//LOGGER.info("parameterName = "+parameterName + " paramValue = "+paramValue);
													List<Object> obj = new ArrayList<>();
													obj.add(currentCellValue);
													obj.add(paramValue); 
													
													Result currentResult = getParamJsonData(ruleName, paramValue,
															 currentAttributeName, stageName, catgoryId, obj.toArray(), projectId, paramJson);// methods call
													String value = currentResult.getValue();
													//LOGGER.info("value = "+value + " value = "+value);
													if(value!=null) 
													{
														currentCell.setCellValue(value);
													}
													if(currentResult.isResult()) {
														counter++;
													}
												}
												result = generateResult(counter, paramLength);
												//LOGGER.info("result---------"+result);
										}
										//Ended By Aadesh for Code Changes For LOV Parameter Related Rule in Rulebase Engine on 05-05-2023
										
										
										
										else {
											
											//LOGGER.info("Inside Param Attribute");
										//	Thread.sleep(20000);
											String attrName = getParamAttributeNames(ruleName, currentCellValue,
													currentAttributeName, stageName, catgoryId, projectId, paramJson);
											//LOGGER.info("getParamAttributeNames--"+ruleName +"---"+currentAttributeName+"---"+currentCellValue+"---"+stageName+"---"+catgoryId+"---"+projectId+"---"+paramJson);
											
											String[] paramName = attrName.split(",");
											//LOGGER.info(ruleName+"-----44444444444-----"+attrName);
											int counter = 0;
											int paramLength = paramName.length;
											for(int i=0; i<paramLength; i++) {
												
												// Fetching attribute data from column
												String parameterName = paramName[i].trim();
												
												int attrIndex = 0;
												try {
												 attrIndex = excelAttrNameColMap.get(parameterName);
												}catch (Exception e) {
													//e.printStackTrace();
													continue;
												}
												Cell cellIndex = currentRow.getCell(attrIndex, Row.CREATE_NULL_AS_BLANK);
												String strVal = getCellValue(cellIndex);
												
												String key = clientId+projectId+catgoryId+paramName[i];
												LOGGER.info("key--- "+key);
												String attributeId = catAttrMap.get(key.toLowerCase());
												LOGGER.info("attributeId--- "+attributeId);
												String lovs = getLovs(clientId,projectId,categoryIdSheet, attributeId, lovData);
												String paramValue = null;
												
												//commented lov check
												LOGGER.info("lovs--- "+lovs);
												if(lovs== null || lovs.equals("")) {
													//LOGGER.info("lovs==null");
													 paramValue = strVal;
												}else {
													LOGGER.info("paramValue = lovs; ");
													if(!ruleName.equals("checkTwoCellsForSameValue")) {
														//paramValue = strVal+"," + lovs;
														paramValue = lovs;
													}
													/*else {
														paramValue = lovs; 	//Updated by Anjali to Solve CheckLOvinAttribute Rule Fail Issue on 19-09-22
														LOGGER.info("paramvalue----"+paramValue);
													}*/
												}
												//commented by anjali for Tata CLiQ
											/*	if(ruleName.equalsIgnoreCase("walmart_checkLOVinAttribute")) {
													if(strVal.contains(",")) {
														paramValue = strVal.replaceAll(",", "##");
													}else {
														paramValue = strVal;
													}
												}else {
													paramValue = strVal;
												}*/
												//LOGGER.info("ruleName = "+ruleName );
												//LOGGER.info("parameterName = "+parameterName + " paramValue = "+paramValue);
												List<Object> obj = new ArrayList<>();
												obj.add(currentCellValue);
												obj.add(paramValue); 
												
												Result currentResult = getParamJsonData(ruleName, paramValue,
														 currentAttributeName, stageName, catgoryId, obj.toArray(), projectId, paramJson);// methods call
												String value = currentResult.getValue();
												if(value!=null) {
													currentCell.setCellValue(value);
												}
												if(currentResult.isResult()) {
													counter++;
												}
											}
											result = generateResult(counter, paramLength);
											//LOGGER.info("result---------"+result);
										}

										
										if (result.isResult() == false) {
											
											String ruleType = getRuleType(fetchAllRulesDataSevice, ruleName);

											RuleError ruleerror = new RuleError(sheetName,row, col, currentAttributeName,
													currentCellValue, ruleName, "", ruleType, String.valueOf(result.getScore()));

											errorlist.add(ruleerror);

											if (ruleType.equalsIgnoreCase("Format")) {
												currentCell.setCellStyle(formatstyle);
											} else if (ruleType.equalsIgnoreCase("Logical")) {
												currentCell.setCellStyle(logicalstyle);
											} else if (ruleType.equalsIgnoreCase("Image")) {
												currentCell.setCellStyle(imagestyle);
											} else if (ruleType.equalsIgnoreCase("Ocr")) {
												currentCell.setCellStyle(ocrstyle);
											}

										}
										
										
									}
								}
							}
						}
					}

				//}
			}
			
			
		}
	
		Sheet errorsheet = workbook.createSheet("Error Log");
		List<ErrorModel> errorModelList = writeErrorLog(errorsheet, errorlist, batchId, projectId);
		workbook.write(fileOut);
		
		return path;

	}

}