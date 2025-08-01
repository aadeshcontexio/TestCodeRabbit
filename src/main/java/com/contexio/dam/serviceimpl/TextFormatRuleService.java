package com.contexio.rulebase.rule.service;

import com.contexio.rulebase.model.Result;

public interface TextFormatRuleService {
	

	public Result isNumber(String data);
	
	public Result isString(String data);
	
	public Result isEmail(String data);
	
	public Result isDateFormat(String data);
	
	public Result isProperCase(String data);
	
	public Result containsSpecialCharacters(String data);
	
	public Result containsTwoDecimalPoints(String data);
	
	public Result isUpperCase(String data);
	
	public Result isLowerCase(String data);
	
	public Result checkDimensions(String data);
	
	public Result checkDimensionsAndWeight(String data);
	
	public Result checkFloatAttributes(String data);
	
	public Result checkStringAttributes(String data);
	//Croma - should be processed with 800 x 800 pixels
	
	public Result checkMultpleValueSepretedByPipe(String data);
	
	public Result checkMandatoryColumn(String data);
	
	public Result checkDoubleSpace(String data);
	
	public Result checkSpecialChar(String data);
	
	public Result lengthCheckUpcMpnEan(String data);
	
	public Result mandatoryFillCheck(String data);
	
	public Result checkDate(String data);
	
	public Result checkPackQuantity(String data);

	public Result checkEANSKUCodeDuplication(String data);
	
	public Result checkUpdateDelete(String data);
	
	public Result checkCollectionDiscription(String data);
	
	public Result checkPublicGender(String data);
	
	public Result checkPatternType(String data);
	
	public Result checkOccasiontypes(String data);
	
	public Result checkNeckStyle(String data);
	
	public Result checkSleveType(String data);
	
	public Result checkFabricComposition(String data);
	
	public Result checkVideoOrGifPresent(String data);
	
	public Result doesNotContainsSpecialCharacters(String data);
	
	public Result doesNotContainsSpecificSpecialCharacters(String data, String specialCharacters);
	
	public Result checkEndDate(String data, String dateType, String dateValue);
	
	public Result checkImageTypeSpecific(String data, String type);
	
	public Result checkMandatoryDesc(String data);
	
	public Result checkMandatoryImage(String data);

	public Result checkMandatoryWarrenty(String data);

	public Result checkImagePresent(String data);
	
	public Result checkNotSameCellValue(String data, String attributeName);

	public Result checkBooleanValue(String data);

	public Result jcp_checkPriceTypeValue(String data);

	public Result jcp_checkDateFormat(String data);
	
	public Result isMandatoryCheck(String data);

    public Result Annotation(String data, String value);

    public Result isStringCheck(String data);

    public Result isNumberCheck(String data);

    public Result isAlphaNumeric(String data);

    public boolean checkDynamicvalueinAttribute(String data, String attributeValue);

    public Result isText(String data);

	Result tataBeauty_checkSpecialCharacters(String data);

	//Category Based Rule
    Result tataBeauty_MakeUp_Cat_checkMandatoryColumn(String data, String data2);

	Result tataBeauty_Hair_Cat_checkMandatoryColumn(String data, String data2);

	Result tataBeauty_Bath_Cat_checkMandatoryColumn(String data, String data2);

	Result tataBeauty_Skin_Care_Cat_checkMandatoryColumn(String data, String data2);

	Result tataBeauty_MensGroom_Cat_checkMandatoryColumn(String data, String data2);

	//Result tataBeauty_BodyHyg_Cat_checkMandatoryColumn(String data, String data2);

	Result tataBeauty_Makeup_Color_ShadeName_Equal(String data, String data2, String data3);

	Result tataBeauty_FragPerf_Cat_checkMandatoryColumn(String data, String data2);

	//Result tatabeauty_MRPcheck(String data);

	Result tataBeauty_SpellCheck_Platform_Refer_LOV_List(String data);

	Result tataBeauty_LEN_WID_WEI_Check_CM(String data, String unit);

	Result tataBeauty_LEN_WID_WEI_Check_GM(String data, String unit);

	Result tataBeauty_FragPerf_NonLov_checkMandatoryColumn(String data, String data2);

	Result containsThreeDecimalPoints(String data);

	


}
