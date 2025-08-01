package com.contexio.rulebase.rule.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.contexio.rulebase.model.Result;

public interface TextLogicalRuleService {

	public Result isLengthGreaterThan(String data, String length);

	public Result isLengthLessThan(String data, String length);

	public Result isLengthEqualTo(String data, String length);

	public Result isLengthBetween(String data, String begin, String end);

	public Result contains(String data, String matchData);

	public Result containsAny(String data, String matchData);

	public Result containsDataSet(String data, String matchData[]);

	public Result containsTrailingSpace(String data);

	public Result isEmpty(String data);

	public Result isNonEmpty(String data);

	public String getModelNo(String data);

	public String getQuantity(String data);

	public String getVolume(String data);
	
	public Result checkHtmlTag(String data);
	
	public Result checkDataContainsCapacity(String data);
	
	public Result checkDataContainsYesOrNo(String data);
	
	public Result checkTitle(String data);
	
	public Result checkHeadphoneType(String data);
	
	public Result checkUniqueThing(String data);
	
	public Result checkKeyboardsType(String data);
	
	public Result checkKeyboardsTitle(String data);
	
	public Result checkAirCoolerType(String data);
	
	public Result checkRoundFigure(String data);
	
	public Result checkFansUniqueThing(String data);
	
	public Result checkFansTitle(String data);
	
	public Result checkWatts(String data);
	
	public Result checkMixersGrindersType(String data);
	
	public Result checkFilterType(String data);
	
	public Result checkChimneySize(String data);
	
	public Result checkWordCountIsGreaterThan(String data, String minWordCount);
	
	public Result checkFeatureCountIsGreaterThan(String data, String minCount);
	
	public Result maxCharacterLength(String data,String length);
	
	public Result SpecialSymbol(String data);
	
	public Result abusiveWordDetect(String data) throws IOException;
	
	//public Result priceInTitle(String data);
	
	public Result sentenceInDescription(String data,String sentenceCount);

	Result isMandatory(String data);

	public Result checkLOVinAttribute(String data,String AttributeName);

	public Result checkDescLengthBetween(String data, String begin, String end);
	
	public Result checkLOVinDesc(String data,String AttributeName);
	
	public Result keywordRelavaceDescription(String data, String matchData);
	
	public Result checkLOVinMetaTitle(String data,String AttributeName);
	
	public Result checkCorrectTitle(String data,String AttributeName);
	
	public Result checkCorrectDesc(String data,String AttributeName);
	
	public Result checkCorrectLongDesc(String data,String AttributeName);
	
	public Result checkCorrectMetaData(String data,String matchData);
	
	public Result appearanceInGoogleSearch(String data);
	
	public Result apperanceInWalmartSearch(String data);
	
	public Result accurateLatching(String data);
	
	public Result accurateCategorization(String data);
	
	public Result topGuidedNavigation(String data);
	
	public Result presencelegalAttri(String data);
	
	public Result imagesAsPerSeq(String data);
	
	public Result keywordRelavaceMetaData(String data, String matchData);
	
	public Result featureCallOut(String data);
	
	public Result checkTwoCellsForSameValue(String data,String AttributeName);
	
	public Result matchExactText(String data, String matchData);
	
	public Result keywordRelavaceDescriptionNoon(String data, String matchData);

	Result appearanceInGoogleSearchNoon(String data);

	Result apperanceInNoonSearch(String data);

	Result checkWordCountIsGreaterThanNoon(String data, String minWordCount);

	Result checkLOVinAttributeNoon(String data, String attributeValue);

	Result checkCorrectTitleNoon(String data, String attributeValue);

	Result checkCorrecDescNoon(String data, String attributeValue);
	
	Result checkCorrecSpecificationNoon(String data, String attributeValue);

	Result checkListingLeftNavigationNoon(String data) throws IOException;

	Result checkImageSequenceNoon(String data) throws IOException;

	Result checkWordCountAboutProductBetweenNoon(String data, String maxWordCount);

	Result checkWordCountAboutBrandNoon(String data, String minWordCount);

	Result checkCountryOfOriginNoon(String data);

	Result checkFeatureCalloutPresent(String data) throws IOException;
	
	public Result checkHighlightCountIsGreaterThan(String data, String minCount);

	Result keywordPresentInMetaTitle(String data, String matchData);

	Result keywordPresentInMetaDesc(String data, String matchData);

	Result updateAttributeValue(String data);

	Result generateImageName(String data, String attributeValue);

	Result writeImageLinks(String data, String attributeValue);

	Result writeData(String data, String attributeValue);

	Result checkEANOrUPC(String data, String attributeValue);

	Result startDate(String data);

	Result endDate(String data);

	Result metaTitleFormula(String data, String attributeName);

	Result metaKeywordFormula(String data, String attributeName);

	Result titlePresenceUrl(String data, String matchData);
	
Result metaTitleIndiluxeApparel(String data, String attributeName);
	
	Result metaKeywordIndiluxeApparel(String data, String attributeName);
	
	Result metaDescriptionIndiluxeApparel(String data, String attributeName);
	
	Result metaDescIndiluxeAccessaries(String data, String attributeName);
	
	Result metaTitleIndiluxeHome(String data, String attributeName);
	
	Result checkDescIndiluxe(String data, String attributeName);
	
	Result styleNoteIndiluxe(String data, String attributeName);
	
	Result defaultAgeBandIndiluxe(String data);
	
	Result defaultFitIndiluxe(String data);
	
	Result defaultWaistRiseIndiluxe(String data);
	
	Result defaultWeightIndiluxe(String data);
	
	Result defaultWarrantyTypeIndiluxe(String data);
	
	Result UnisexIndiluxe(String data);
	
	Result defaultModelFitMenIndiluxe(String data);
	
	Result defaultModelFitWomenIndiluxe(String data);
	
	Result defaultCustomerSupportNoCroma(String data);
	
	Result defaultCustomerSupportEmailCroma(String data);
	
	Result customiseEndDate(String data, String year);
	
Result defaultStringCroma(String data);
	
	Result defaultNumberCroma(String data);
	
	Result convertCmInch(String data, String srcAndDestnType);

	Result writeDataExcelColumn(String data, String value);

	Result walmart_checkMandatoryColumn(String data);

	Result walmart_googleScore(String data);

	Result walmart_walmartScore(String data);

	Result walmart_highlightMinCount(String data, String minCount);

	Result walmart_reviewScore(String data);

	Result walmart_keywordDensityDescription(String data, String matchData);

	Result walmart_checkDPIOfImage(String data, String dpi) throws IOException;

	Result walmart_checkWordCountIsGreater(String data, String minWordCount);

	public Result walmart_presenctTitleKeywordsURL(String data, String matchData);

	public Result walmart_ratingScore(String data);

	public Result walmart_checkTwoAttrSame(String data, String attributeName);

	public Result walmart_checkCharacterLessthanCount(String data, String length);

	public Result walmart_checkinimumImageCountIsGreater(String data, String minImageCount);

	public Result walmart_metaKeywordPresenseDensity(String data, String keyword);

	public Result walmart_KeywordPresenseURL(String data, String keyword);

	public Result walmart_checkPercentageOccuped(String data, String percentage);

	public Result walmart_checkDimensionOfImage(String data, String x, String y) throws IOException;

	public Result walmart_checkImageBackgroundWhite(String data) throws IOException;

	public Result walmart_checkLOVinAttribute(String data, String attributeValue);

	public Result compareNumbers(String data, String attributeValue);

	public Result multiValueCount(String data);

	public Result tata_setReqValues(String data, String attributeValue);

	public Result tata_SetblankData(String data);

	public Result tata_setSentenceCase(String data);

	public Result tata_setValueToNo(String data);

	public Result tata_setFabricComposition(String data);

	public Result tata_replaceTypeFabricComp(String data);

	public Result tata_removeSpecialCharactersFC(String data);

	public Result tata_lengthFC(String data);

	public Result tata_setValueToYes(String data);
	
	public Result tata_specialCharacters(String data);

	public Result tata_replaceToYandM(String data);

	public Result tata_setNoToBlank(String data);

	public Result tata_setLeafCategories(String data);

	public Result tata_setwashCare(String data);

	public Result tata_setproductWeight(String data);

	public Result tata_alwaysMarketplace(String data);

	public Result tata_setValueAlwaysOne(String data);

	public Result tata_setStartDate(String data);

	public Result tata_setEndDate(String data);

	public Result setSameAttributeData(String data, String AttributeName);

	public Result setGlobalIDType(String data, String AttributeName);

	public Result tata_setSorDValue(String data);

	public Result tata_getFeatureCount(String data);

	public Result tata_testFeatureCount(String data);

	public Result tatabeauty_setEndDate(String data);

	public Result tatabeauty_setStartDate(String data);

	public Result checkSpecificSpecialCharacter(String data);

	public Result tata_checkSpecificSpecialCharacter(String data);

	public Result maxCharacterLengthExelBase(String data, String length);

	Result tatabeauty_checkImageCount(String data, String minImageCount);

	Result tatabeauty_presenceOfSizeInTitle(String data, String matchData);

	Result tatabeauty_EANEqualSKU(String data,String data2);

	Result tatabeauty_SpellCheck(String data) throws IOException;

	Result tatabeauty_FeatureCount(String data);

	Result tatabeauty_freebieAsMrp(String data, String data2);

	Result tatabeautyInternal_checkSpecificSpecialCharacter(String data);

	Result cromaDimensionRoundOff(String data);

	Result tatabeauty_SizeEqualCombo_Product_No(String data, String data2);

	Result tatabeauty_highlight_freebie_if_mrp_01(String data, String data2);

	Result tatabeauty_ProductNameAsProductTitle(String data, String data2);

	Result tata_checkFeatureDuplicateValue(String data);

	Result tatabeauty_checkGlobalIDValueForEAN(String data, String data2);

	Result tatabeauty_checkGlobalIDValueForUPC(String data, String data2);

	Result tatabeauty_checkGlobalIDValueForMPN(String data, String data2);

	Result tatabeauty_checkGlobalIdEANUPCMPN(String data, String data2);

	Result checkMultiLOVinAttribute(String data, String attributeValue);


	//public Result xyz2(String data,String data2);

	//public Result xyz1(String data);

	
}