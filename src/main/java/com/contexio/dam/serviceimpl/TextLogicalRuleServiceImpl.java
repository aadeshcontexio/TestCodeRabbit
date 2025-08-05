package com.contexio.rulebase.rule.serviceimpl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.Sanselan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contexio.rulebase.image.commons.ImageRules;
import com.contexio.rulebase.model.Result;
import com.contexio.rulebase.rule.service.TextLogicalRuleService;

@Service
public class TextLogicalRuleServiceImpl implements TextLogicalRuleService {

	boolean result;

	private static final Logger LOGGER = LoggerFactory.getLogger(TextLogicalRuleServiceImpl.class);

	@Autowired
	private ImageRules imageRules;

	@Override
	public Result isLengthGreaterThan(String data, String len) {// change
		// int leng=length;
		try {

			int length = Integer.parseInt(len);

			// LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in
			// MEthod%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

			if (data.length() > length) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			result = false;

		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result isLengthLessThan(String data, String len) {// change

		int length = Integer.parseInt(len);

		if (data.length() <= length) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result isLengthEqualTo(String data, String len) {// change

		int length = Integer.parseInt(len);

		if (data.length() == length) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result isLengthBetween(String data, String begin, String end) { // change
		int beginData = Integer.parseInt(begin);
		int endData = Integer.parseInt(end);
		//LOGGER.info(data + "" + begin + " " + end);

		if (data.length() >= 60 && data.length() <= 80) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result contains(String data, String matchData) {
		Pattern pattern = Pattern.compile(matchData, java.util.regex.Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(data);
		if (matcher.find()) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result containsAny(String data, String matchData) {
		String[] matchSplitData = matchData.split(",");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		try {
			for (String str : matchSplitData) {
				if (data.toLowerCase().contains(str.toLowerCase())) {
					count++;
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("data = "+data+" title = "+matchData);
			// LOGGER.info("matchSplitDataLength Length = "+matchSplitDataLength+" Count =
			// "+count);
			if (count == matchSplitDataLength) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result containsDataSet(String data, String[] matchData) {
		for (String str : matchData) {
			Pattern pattern = Pattern.compile(str, java.util.regex.Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(data);
			if (matcher.find()) {
				result = true;
				break;
			} else {
				result = false;
			}
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result containsTrailingSpace(String data) {
		//if (data.trim().equals(data)) {
		if (data.equals(data.replaceAll("^\\s+", "")) || data.equals(data.replaceAll("\\s+$", ""))) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result isEmpty(String data) {
		if (data.isEmpty() == true || data.equals("")) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result isNonEmpty(String data) {
		if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
			result = false;
		} else {
			result = true;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public String getModelNo(String data) {
		String[] words = data.split("\\s+");
		String word = "";
		for (String dataWord : words) {
			if (dataWord.contains("-")) {
				String pattern = "[a-zA-Z0-9-]*$";
				if (dataWord.matches(pattern)) {
					return dataWord;
				}
			}
		}
		return word;
	}

	@Override
	public String getQuantity(String data) {
		String units[] = { "cap", "caps", "capsule", "capsules", "tablet", "tablets", "tab", "tabs", "softgels", "ct",
				"gels", "gel" };
		String[] words = data.split("\\s+");
		String word = "";
		int wordCount = 0;
		for (String dataWord : words) {
			String wordData = "";
			if (wordCount == 0) {
				wordData = words[wordCount];
			} else {
				wordData = words[wordCount - 1];
			}
			for (String unit : units) {
				if (dataWord.equalsIgnoreCase(unit)) {
					return wordData.concat(" " + dataWord);
				}
			}
		}
		return word;
	}

	@Override
	public String getVolume(String data) {
		String units[] = { "gm", "grams", "kg", "mg", "ml", "inch", "foot", "yard", "oz", "mcg", "lbs", "lb", "ounce",
				"\\$" };
		String[] words = data.split("\\s+");
		String word = "";
		int wordCount = 0;
		for (String dataWord : words) {
			String wordData = "";
			if (wordCount == 0) {
				wordData = words[wordCount];
			} else {
				wordData = words[wordCount - 1];
			}

			for (String unit : units) {
				Pattern pattern = Pattern.compile(unit, java.util.regex.Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(dataWord);
				if (matcher.find()) {
					return wordData.concat(" " + dataWord);
				}
			}
			wordCount++;
		}
		return word;
	}

	@Override
	public Result checkHtmlTag(String data) {

		//LOGGER.info("checkHtmlTag in method");

		String HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
		Pattern pattern = Pattern.compile(HTML_PATTERN);
		Matcher matcher = pattern.matcher(data);

		if (matcher.find()) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result checkDataContainsCapacity(String data) {
		String capacity = "Capacity";
		Result resultObj = new Result();
		if (data.contains(capacity)) {
			resultObj.setResult(true);
			resultObj.setScore(1);
		} else {
			resultObj.setResult(false);
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result checkDataContainsYesOrNo(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Yes") || data.equals("No")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result checkTitle(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Wireless Earphone") || data.equals("Wired Earphone") || data.equals("Earpod")
					|| data.equals("Neckband") || data.equals("Mono Bluetooth Headset")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result checkHeadphoneType(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Wireless Earphone") || data.equals("Wired Earphone") || data.equals("Earpod")
					|| data.equals("Neckband") || data.equals("Mono Bluetooth Headset")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}

	}

	@Override
	public Result checkUniqueThing(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Noise Isolation") || data.equals("Sports & Fitness") || data.equals("With Microphone")
					|| data.equals("Tangle Free") || data.equals("Others") || data.equals("Noise Cancelation")
					|| data.equals("Window Air Cooler")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}

	}

	@Override
	public Result checkKeyboardsType(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Mice") || data.equals("Keyboards") || data.equals("Combo") || data.equals("Gaming")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}
	}

	@Override
	public Result checkKeyboardsTitle(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Wired") || data.equals("Wireless")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result checkAirCoolerType(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Desert Air Cooler") || data.equals("Personal Air Cooler")
					|| data.equals("Tower Air Cooler") || data.equals("Window Air Cooler")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}
	}

	@Override
	public Result checkRoundFigure(String data) {
		Result resultObj = new Result();

		try {
			if (data.contains(".")) {
				result = false;
			} else {
				result = true;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}

	}

	@Override
	public Result checkFansUniqueThing(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("BIS Certification") || data.equals("Decorative Fans") || data.equals("Economy Fans")
					|| data.equals("Kids Fans") || data.equals("Pack of 2") || data.equals("Underlight Fans")
					|| data.equals("With Remote")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}

	}

	@Override
	public Result checkFansTitle(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Wired") || data.equals("Wireless")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}

	}

	@Override
	public Result checkWatts(String data) {
		Result resultObj = new Result();

		try {
			final Pattern VALID_Watts_REGEX = Pattern.compile("^[0-9]+[W]");
			Matcher matcher = VALID_Watts_REGEX.matcher(data);
			boolean correct = matcher.find();

			if (correct == true) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			// .printStackTrace();
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result checkMixersGrindersType(String data) {
		Result resultObj = new Result();

		try {
			if (data.equals("Mixer Grinder") || data.equals("Hand Mixer Grinder") || data.equals("Juicer Mixer Grinder")
					|| data.equals("Food Processor") || data.equals("Meat Grinder")
					|| data.equals("Stand Mixer Grinder")) {
				result = true;
			} else {
				result = false;
			}
			resultObj.setResult(result);
			if (result) {
				resultObj.setScore(1);
			} else {
				resultObj.setScore(0);
			}
			return resultObj;
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}

	}

	@Override
	public Result checkFilterType(String data) {
		Result resultObj = new Result();

		if (data.equals("Baffle")) {
			result = true;
		} else {
			result = false;
		}
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result checkChimneySize(String data) {
		if (data.equals("Chimney Size:")) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;
	}

	@Override
	public Result checkWordCountIsGreaterThan(String data, String minWordCount) {
		Result result = new Result();
		String[] wordsArray = data.split("\\s+");
		int wordLength = wordsArray.length;
		int wordCount = Integer.valueOf(minWordCount);
		if (wordLength >= wordCount) {
			result.setResult(true);
		}
		double score = wordLength / wordCount;
		result.setScore(score);
		return result;
	}

	@Override
	public Result checkCountryOfOriginNoon(String data) {
		Result result = new Result();
		if (data.equalsIgnoreCase("n/a") || data.equalsIgnoreCase("")) {
			result.setResult(false);
			result.setScore(0);
			return result;
		} else {
			result.setResult(true);
			result.setScore(1);
			return result;
		}

	}

	@Override
	public Result checkWordCountIsGreaterThanNoon(String data, String minWordCount) {
		Result result = new Result();
		String[] wordsArray = data.split("\\s+");
		int wordLength = wordsArray.length;
		int wordCount = Integer.valueOf(minWordCount);
		if (wordLength >= wordCount) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}

		return result;
	}

	@Override
	public Result checkWordCountAboutProductBetweenNoon(String data, String maxWordCount) {
		Result result = new Result();
		String[] wordsArray = data.split("\\s+");
		int wordLength = wordsArray.length;
		int wordCount = Integer.valueOf(maxWordCount);
		if ((wordLength <= wordCount) && (wordLength > 50)) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}

		return result;
	}

	@Override
	public Result checkWordCountAboutBrandNoon(String data, String minWordCount) {
		Result result = new Result();
		String[] wordsArray = data.split("\\s+");
		int wordLength = wordsArray.length;
		int wordCount = Integer.valueOf(minWordCount);
		if ((wordLength <= wordCount) && (wordLength > 10)) {
			result.setResult(true);
			result.setScore(1);
			return result;
		} else {
			result.setResult(false);
			result.setScore(0);
		}

		return result;
	}

	@Override
	public Result checkFeatureCountIsGreaterThan(String data, String minWordCount) {
		// public Result checkFeatureCountIsGreaterThan(String data, String minCount) {
		Result result = new Result();
		String[] wordsArray = data.split("\\|\\|\\|");
		int wordLength = wordsArray.length;
		int wordCount = Integer.valueOf(minWordCount);
		if (wordsArray.length >= Integer.valueOf(minWordCount)) {
			result.setResult(true);
		}
		double score = wordLength / wordCount;
		result.setScore(score);
		return result;
	}

	@Override
	public Result maxCharacterLength(String data, String length) {
		int lengthValue = Integer.parseInt(length);
		Result result = new Result();
		// LOGGER.info("data.length() = "+data.length());
		if (data.length() > lengthValue) {
			result.setResult(false);
			result.setScore(0);
			return result;
		}
		result.setResult(true);
		result.setScore(1);
		return result;
	}

	@Override
	public Result SpecialSymbol(String data) {
		Result result = new Result();
		if (data.contains("\u00a9") || data.contains("\u2122")) {
			result.setResult(false);
			result.setScore(0);
			return result;
		}
		result.setResult(true);
		result.setScore(1);
		return result;
	}

	@Override

	public Result abusiveWordDetect(String data) throws IOException {
		Result result = new Result();
		BufferedReader bufReader = new BufferedReader(
				new FileReader("C:\\Users\\ganesh.chauhan.REINLABS\\Desktop\\Content-Audit\\Data\\words.txt"));
		ArrayList<String> listOfLines = new ArrayList<>();

		String line = bufReader.readLine();
		while (line != null) {
			listOfLines.add(line);
			line = bufReader.readLine();
		}
		bufReader.close();

		for (String s : listOfLines) {
			if (data.toLowerCase().contains(s.toLowerCase())) {
				//LOGGER.info("data = " + data + " s = " + s);
				result.setResult(false);
				result.setScore(0);
				return result;
			}
		}
		result.setResult(true);
		result.setScore(1);
		return result;
	}

	/*
	 * @Override public Result priceInTitle(String data) { Result result = new
	 * Result(); return result; }
	 */

	@Override
	public Result sentenceInDescription(String data, String sentenceCount) {
		int sentenceCountValue = Integer.parseInt(sentenceCount);
		Result result = new Result();
		String[] sentenceList = data.split("[!?.:]+");
		int sentenceCountInput = sentenceList.length;
		//LOGGER.info("sentenceCountInput = " + sentenceCountInput);
		if (sentenceCountInput >= sentenceCountValue) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}

		result.setResult(false);
		result.setScore(0);
		return result;
	}

	@Override
	public Result isMandatory(String data) {
		Result result = new Result();
		if (data.isEmpty() == true || data.equals("")) {
			result.setResult(false);
			result.setScore(0);
			return result;
		}
		result.setResult(true);
		result.setScore(1);
		return result;
	}

	/*
	 * public Result checkKeywordsPresent(String data, String keywords) throws
	 * IOException { Result result = new Result(); String[] keyWordArray =
	 * keywords.split(","); List<Object> listOfLines = Arrays.asList(keyWordArray);
	 * 
	 * for (String s : listOfLines) { if (data.contains(s)) {
	 * LOGGER.info("data = "+data+" s = "+s); result.setResult(false);
	 * result.setScore(0); return result; } } result.setResult(true);
	 * result.setScore(1); return result; }
	 */

	public Result checkKeywordsPresent(String data, String keywords) throws IOException {
		Result result = new Result();
		String[] keyWordArray = keywords.split(",");
		boolean resultCheck = true;
		int score = 0;
		for (int i = 0; i < keyWordArray.length; i++) {
			if (data.toLowerCase().contains(keyWordArray[i].toLowerCase())) {
				// LOGGER.info("data = "+data+" s = "+keyWordArray[i]);
				score++;

			} else {
				result.setResult(false);
				resultCheck = false;
			}
		}
		result.setResult(resultCheck);
		if (score == 0) {
			result.setScore(0);
		} else {
			score = score / keyWordArray.length;
			//LOGGER.info("keyWordArray.length :  " + keyWordArray.length + " : Score : " + score + " : Final Score : "
			//		+ (score / keyWordArray.length));

			result.setScore(score);
		}
		return result;
	}

	/*
	 * public Result checkLOVinAttribute(String data, String AttributeName) { Result
	 * result = new Result(); result.setResult(true); result.setScore(1); return
	 * result; }
	 */

	
	// Updated by Aadesh to Solve CheckLOvinAttribute Rule Fail Issue on 01-10-22
	@Override
	public Result checkLOVinAttribute(String data, String attributeValue) {
		LOGGER.info("data checkLOVinAttribute = "+data+" attributeValue = "+attributeValue);
		Result result = new Result();
		if (data.equals("")) {
			return result;
		}
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();		
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();		
			LOGGER.info("data checkLOVinAttribute = "+data+" keyword = "+keyword);
			if (data.toLowerCase().equals(keyword.toLowerCase())) {
				LOGGER.info("if ");
				result.setResult(true);
				result.setScore(1);
				return result;
			}
			else {
				LOGGER.info("else ");
				result.setResult(false);
				result.setScore(0);				
			}

		}
		LOGGER.info("data result = "+result.toString()+" attributeValue = ");
		return result;
	}
	
	
	/*// Updated by Anjali to Solve CheckLOvinAttribute Rule Fail Issue on 19-09-22
	@Override
	public Result checkLOVinAttribute(String data, String attributeValue) {
		//LOGGER.info("data = "+data+" attributeValue = "+attributeValue);
		Result result = new Result();
		if (data.equals("")) {
			return result;
		}
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().equals(keyword.toLowerCase())) {
				result.setResult(true);
				result.setScore(1);
				return result;
			} else {
				result.setResult(false);
				result.setScore(0);
				return result;
			}
		}
		return result;
	}
*/
	@Override
	public Result checkLOVinAttributeNoon(String data, String attributeValue) {
		// LOGGER.info("data = "+data+" attributeValue = "+attributeValue);
		Result result = new Result();
		if (data.equals("")) {
			return result;
		}
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();

		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkDescLengthBetween(String data, String begin, String end) {
		// change
		int beginData = Integer.parseInt(begin);
		int endData = Integer.parseInt(end);

		//LOGGER.info(data + "" + begin + " " + end);

		if (data.length() >= 100 && data.length() <= 300) {
			result = true;
		} else {
			result = false;
		}
		Result resultObj = new Result();
		resultObj.setResult(result);
		if (result) {
			resultObj.setScore(1);
		} else {
			resultObj.setScore(0);
		}
		return resultObj;

	}

	@Override
	public Result checkLOVinDesc(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();

		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result keywordRelavaceDescription(String data, String matchData) {
		double totalNoOfWords = data.split(" ").length;
		double criteriaCount = 0.04 * totalNoOfWords;
		String[] matchSplitData = matchData.split(",");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		String[] wordsArray = data.split(" ");
		try {
			for (String str : matchSplitData) {
				for (String word : wordsArray) {
					if (word.toLowerCase().equals(str)) {
						count++;
					}
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("count = "+count+" criteriaCount = "+criteriaCount+"
			// totalNoOfWords = "+totalNoOfWords);
			if (count >= criteriaCount) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result checkLOVinMetaTitle(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();

		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkCorrectTitle(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();

		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkCorrectTitleNoon(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();
		// LOGGER.info("checkCorrectTitleNoon : data :"+data + " : attributeValue :
		// "+attributeValue);
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkCorrecDescNoon(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();
		// LOGGER.info("checkCorrectTitleNoon : data :"+data + " : attributeValue :
		// "+attributeValue);
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkCorrecSpecificationNoon(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();
		// LOGGER.info("checkCorrectTitleNoon : data :"+data + " : attributeValue :
		// "+attributeValue);
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkCorrectDesc(String data, String attributeValue) {
		int sentenceCountValue = 2;
		Result result = new Result();
		String[] sentenceList = data.split("[!?.:]+");
		int sentenceCountInput = sentenceList.length;
		/*
		 * LOGGER.info("sentenceCountInput = "+sentenceCountInput); for(String sent :
		 * sentenceList) { LOGGER.info("sent = "+sent); }
		 */
		int count = 0;
		if (sentenceCountInput >= sentenceCountValue) {
			count++;
		}
		if (data.toLowerCase().contains(attributeValue.toLowerCase())) {
			count++;
		}
		if (count > 0) {
			result.setScore((double) count / (double) 2);
			result.setResult(true);
		} else {
			result.setScore(0);
		}

		return result;
	}

	@Override
	public Result checkCorrectLongDesc(String data, String attributeValue) {
		Result result = new Result();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();

		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (data.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result checkCorrectMetaData(String data, String matchData) {
		String[] matchSplitData = matchData.split(",");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		try {
			for (String str : matchSplitData) {
				if (data.toLowerCase().contains(str.toLowerCase())) {
					count++;
				} else {
					result = false;

				}
			}
			resultObj.setResult(result);
			if (count > 0) {
				int matchSplitDataLength = matchSplitData.length;
				resultObj.setScore((double) count / (double) matchSplitDataLength);
			} else {
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result appearanceInGoogleSearchNoon(String data) {
		Result result = new Result();
		/*
		 * Map<String, String> scoreMap = new HashMap<>(); scoreMap.put("1", "1");
		 * scoreMap.put("2", "0.8"); scoreMap.put("3", "0.6"); scoreMap.put("4", "0.4");
		 * scoreMap.put("5", "0.2");
		 * 
		 * for (Map.Entry<String, String> entry : scoreMap.entrySet()) {
		 * LOGGER.info(entry.getKey() + "/" + entry.getValue());
		 * if(entry.getKey().equals(data)) { Double score =
		 * Double.valueOf(entry.getValue()); result.setResult(true);
		 * result.setScore(score); return result; } }
		 */
		int position = 0;
		try {
			position = Integer.valueOf(data);
		} catch (Exception e) {
			return result;
		}
		if (position > 0 && position <= 5) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}
		result.setScore(0);
		result.setResult(false);
		return result;
	}

	@Override
	public Result appearanceInGoogleSearch(String data) {
		Result result = new Result();
		/*
		 * Map<String, String> scoreMap = new HashMap<>(); scoreMap.put("1", "1");
		 * scoreMap.put("2", "0.8"); scoreMap.put("3", "0.6"); scoreMap.put("4", "0.4");
		 * scoreMap.put("5", "0.2");
		 * 
		 * for (Map.Entry<String, String> entry : scoreMap.entrySet()) {
		 * LOGGER.info(entry.getKey() + "/" + entry.getValue());
		 * if(entry.getKey().equals(data)) { Double score =
		 * Double.valueOf(entry.getValue()); result.setResult(true);
		 * result.setScore(score); return result; } }
		 */
		int position = 0;
		try {
			position = Integer.valueOf(data);
		} catch (Exception e) {
			return result;
		}
		if (position > 0 && position <= 5) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}
		result.setScore(0);
		result.setResult(false);
		return result;
	}

	@Override
	public Result apperanceInWalmartSearch(String data) {
		Result result = new Result();
		/*
		 * Map<String, String> scoreMap = new HashMap<>(); scoreMap.put("1", "1");
		 * scoreMap.put("2", "0.8"); scoreMap.put("3", "0.6"); scoreMap.put("4", "0.4");
		 * scoreMap.put("5", "0.2");
		 * 
		 * for (Map.Entry<String, String> entry : scoreMap.entrySet()) {
		 * LOGGER.info(entry.getKey() + "/" + entry.getValue());
		 * if(entry.getKey().equals(data)) { Double score =
		 * Double.valueOf(entry.getValue()); result.setResult(true);
		 * result.setScore(score); return result; } }
		 */
		int position = 0;
		try {
			position = Integer.valueOf(data);
		} catch (Exception e) {
			return result;
		}
		if (position > 0 && position <= 5) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}
		result.setScore(0);
		result.setResult(false);
		return result;
	}

	@Override
	public Result apperanceInNoonSearch(String data) {
		Result result = new Result();
		/*
		 * Map<String, String> scoreMap = new HashMap<>(); scoreMap.put("1", "1");
		 * scoreMap.put("2", "0.8"); scoreMap.put("3", "0.6"); scoreMap.put("4", "0.4");
		 * scoreMap.put("5", "0.2");
		 * 
		 * for (Map.Entry<String, String> entry : scoreMap.entrySet()) {
		 * LOGGER.info(entry.getKey() + "/" + entry.getValue());
		 * if(entry.getKey().equals(data)) { Double score =
		 * Double.valueOf(entry.getValue()); result.setResult(true);
		 * result.setScore(score); return result; } }
		 */
		int position = 0;
		try {
			position = Integer.valueOf(data);
		} catch (Exception e) {
			return result;
		}
		if (position > 0 && position <= 5) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}
		result.setScore(0);
		result.setResult(false);
		return result;
	}

	@Override
	public Result accurateLatching(String data) {
		Result result = new Result();
		return result;
	}

	@Override
	public Result accurateCategorization(String data) {
		Result result = new Result();
		return result;
	}

	@Override
	public Result topGuidedNavigation(String data) {
		Result result = new Result();
		return result;
	}

	@Override
	public Result presencelegalAttri(String data) {
		// TODO Auto-generated method stub
		Result result = new Result();
		return result;
	}

	@Override
	public Result imagesAsPerSeq(String data) {
		// TODO Auto-generated method stub
		Result result = new Result();
		return result;
	}

	@Override
	public Result keywordRelavaceMetaData(String data, String matchData) {
		String[] matchSplitData = matchData.split(",");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		try {
			for (String str : matchSplitData) {
			//	LOGGER.info("----------------- str = " + str);
				if (data.toLowerCase().contains(str.toLowerCase())) {
					count++;
				} else {
					result = false;

				}
			}
			resultObj.setResult(result);
		//	LOGGER.info("Count = " + count);
			if (count > 0) {
				int matchSplitDataLength = matchSplitData.length;
			//	LOGGER.info("matchSplitDataLength = " + matchSplitDataLength);
				resultObj.setScore((double) count / (double) matchSplitDataLength);
			} else {
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result featureCallOut(String data) {
		// TODO Auto-generated method stub
		Result result = new Result();
		return result;
	}

	@Override
	public Result checkTwoCellsForSameValue(String data, String AttributeName) {
		//LOGGER.info("checkTwoCellsForSameValue -->  data = "+data+" AttributeName ="+AttributeName);
		Result result = new Result();
		data = data.trim();
/*		
		  LOGGER.info("data.toLowerCase()::" + data.toLowerCase() +
		  "AttributeName.toLowerCase()::" + AttributeName.toLowerCase());
		 
		//LOGGER.info("checkTwoCellsForSameValue -->  data = "+data+" AttributeName ="+AttributeName);
*/		if (data.toLowerCase().equals(AttributeName.toLowerCase())) {
			//LOGGER.info("checkTwoCellsForSameValue -->  data = "+data+" AttributeName ="+AttributeName);
			result.setResult(false);
		}
		else 
		{
			result.setResult(true);
			
		}
		return result;
	
	}

	@Override
	public Result matchExactText(String data, String attributeValue) {
		// LOGGER.info("data = "+data+" title = "+attributeValue);
		Result result = new Result();
		data = data.trim();

		if (data.toLowerCase().equals(attributeValue.toLowerCase().trim())) {
			result.setResult(false);
			result.setScore(1);
			return result;
		}
		else 
		{
			result.setResult(true);
		}
		
		return result;
	}

	@Override
	public Result compareNumbers(String data, String attributeValue) {
		LOGGER.info("Inside compareNumbers");
		Result result = new Result();
		float num = Float.parseFloat(data);
		float num2 = Float.parseFloat(attributeValue);
		// LOGGER.info("Num: "+num+" = Num2: "+num2);
		if (num <= num2) {
			// System.out.println("Num: "+num+" = Num2: "+num2);
			result.setResult(true);
			result.setScore(1);
			return result;
		}
		return result;
	}

	public static void main(String[] args) {
		// String data = "car_model";
		// String data="Lifestyle & Dietary Need = No egg, No fish, No milk, No mustard,
		// No sesame, No shellfish, No soy, No sulfite, No wheat, No lactose, No
		// artificials, Kosher, No added sugars, Paleo-friendly, Vegan-friendly,
		// Vegetarian-friendly, Whole30-friendly|||Product Type = Parsley|||Storage Type
		// = Shelf|||Brand = Club House|||Walmart Item # = 30949892|||SKU = 993653|||UPC
		// = 6620091284";
		// String title = "No egg, No fish, No milk, No mustard, No sesame, No
		// shellfish, No soy, No sulfite, No wheat, No lactose, No artificials, Kosher,
		// No added sugars, Paleo-friendly, Vegan-friendly, Vegetarian-friendly,
		// Whole30-friendly";
		// LOGGER.info("data.length() = "+data.length());
		TextLogicalRuleServiceImpl obj = new TextLogicalRuleServiceImpl();
		Result res;
		/*
		 * res = obj.
		 * walmart_keywordDensityDescription("It?s time to colour your world with the QN60A. Quantum Dot technology with Colour Volume 100% delivers one of our finest TV pictures yet. It takes light and turns it into breathtaking colour that stays true at any level of brightness. Dual LED with innovative backlighting technology deliver bold and accurate contrast. Quantum HDR brings out the detail and contrast, so you can experience the full power in every image. The dynamic tone mapping of HDR10+ creates deeper blacks, more vibrant imagery, and detail that always shines through.\",\r\n"
		 * +
		 * "        \"feature\" : \".100% Color Volume with Quantum Dot||.Dual LED (except 32\\\")||.Quantum HDR||.AirSli"
		 * ,"Samsung - 75\\\" QN60A QLED 4K UHD Smart TV");
		 */

		String data2 = "A Masculine fragrance forged with a pulsating modern flash that creates a style like no other."
				+ " Each of these all-new, fresh and dominating fragrances come with a promise to keep you energetic,"
				+ " clean and FRSH all day long. Spray it on your underarms, neck, chest and back and enjoy the burst"
				+ " of FRSHness with every spray. Wear your SWAG, be a HERO, channel your inner MACHO"
				+ " or feel as confident as a TIGER with FRSH deodorants by Salman Khan. Available in"
				+ " 200 ml lightweight bottles, these deos are safe for skin, suitable for everyday use and travel-friendly.";
		String data = "aadesh";
		res = obj.containsTrailingSpace(data);
		 //res = obj.checkLOVinAttribute("C","A,B,C,D,E");
		//res = obj.maxCharacterLengthExelBase(specs, "30");
		LOGGER.info("Result = " + res.isResult() + " Score = " + res.getScore());
		// LOGGER.info("Result = "+res.isResult()+" Score = "+res.getScore());

	}

	@Override
	public Result keywordRelavaceDescriptionNoon(String data, String matchData) {
		double totalNoOfWords = data.split(" ").length;
		double criteriaCount = 0.02 * totalNoOfWords;
		String[] matchSplitData = matchData.split(" ");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		String[] wordsArray = data.split(" ");
		try {
			for (String str : matchSplitData) {
				for (String word : wordsArray) {
					// LOGGER.info("word = "+word+" str = "+str);
					if (word.toLowerCase().equals(str.toLowerCase())) {
						count++;
					}
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("count = "+count+" criteriaCount = "+criteriaCount+"
			// totalNoOfWords = "+totalNoOfWords);
			// Thread.sleep(2000);
			if (count >= criteriaCount) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result checkListingLeftNavigationNoon(String data) throws IOException {
		Double value = Double.valueOf(data);
		Result result = new Result();
		if (value < 0.5) {
			result.setResult(false);
			result.setScore(value);
		} else {
			result.setResult(true);
			result.setScore(value);
		}
		return result;
	}

	@Override
	public Result checkImageSequenceNoon(String data) throws IOException {
		Result result = new Result();
		if (data.equalsIgnoreCase("0")) {
			result.setResult(false);
			result.setScore(0);
		} else {
			result.setResult(true);
			result.setScore(1);
		}
		return result;
	}

	@Override
	public Result checkFeatureCalloutPresent(String data) throws IOException {
		Result result = new Result();
		if (data.equalsIgnoreCase("0") || data.isEmpty() || data == null) {
			result.setResult(false);
			result.setScore(0);
		} else {
			result.setResult(true);
			result.setScore(Double.valueOf(data));
		}
		return result;
	}

	@Override
	public Result checkHighlightCountIsGreaterThan(String data, String minCount) {
		Result result = new Result();
		String[] wordsArray = data.split("\\|\\|");
		int wordLength = wordsArray.length;
		int wordCount = Integer.valueOf(minCount);
		if (wordLength >= wordCount) {
			result.setResult(true);
			result.setScore(1);
			return result;
		}
		return result;
	}

	@Override
	public Result keywordPresentInMetaTitle(String data, String matchData) {
		Result resultObj = new Result();

		if (matchData.isEmpty() || matchData.equals("") || matchData.equalsIgnoreCase("n/a")) {
			return resultObj;
		}
		String[] matchSplitData = matchData.split(",");

		result = true;
		int count = 0;
		try {
			for (String str : matchSplitData) {
				if (data.toLowerCase().contains(str.toLowerCase())) {
					count++;
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("data = "+data+" title = "+matchData);
			// LOGGER.info("matchSplitDataLength Length = "+matchSplitDataLength+" Count =
			// "+count);
			if (count == matchSplitDataLength) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result keywordPresentInMetaDesc(String data, String matchData) {
		String[] matchSplitData = matchData.split(",");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		try {
			for (String str : matchSplitData) {
				if (data.toLowerCase().contains(str.toLowerCase())) {
					count++;
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			//LOGGER.info("data = " + data + " title = " + matchData);
			//LOGGER.info("matchSplitDataLength Length = " + matchSplitDataLength + " Count = " + count);
			if (count == matchSplitDataLength) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result updateAttributeValue(String data) {
		Result result = new Result();
		data = data + "ABC";
		result.setValue(data);
		return result;
	}

	@Override
	public Result generateImageName(String data, String attributeValue) {
		Result result = new Result();
		// int urls = 0;
		// LOGGER.info("attributeValue = "+attributeValue);
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();

		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			// urls++;
			// LOGGER.info("keyword= "+keyword);
		}
		// LOGGER.info("urls="+urls);
		result.setValue(Integer.toString(keyWordArrayLength));
		return result;
	}

	@Override
	public Result writeDataExcelColumn(String data, String value) {
		Result result = new Result();

		result.setValue(value);
		result.setResult(true);
		return result;
	}

	@Override
	public Result writeImageLinks(String data, String attributeValue) {
		Result result = new Result();
		// List<String> imageUrls = new ArrayList<>();
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		data = data.trim();
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			// imageUrls.add(keyword);
			if (keyword != null)
				result.setValue(keyword);
			else
				result.setValue("");
		}
		return result;
	}

	@Override
	public Result writeData(String data, String attributeValue) {
		Result result = new Result();
		result.setValue(attributeValue);
		return result;
	}

	@Override
	public Result checkEANOrUPC(String data, String attributeValue) {
		Result result = new Result();
		// LOGGER.info("data.length() = "+data.length());
		if (attributeValue.length() == 12) {
			result.setValue("UPC");
		}
		if (attributeValue.length() == 13) {
			result.setValue("EAN");
		}
		// LOGGER.info("result = "+result.toString());
		return result;
	}

	@Override
	public Result startDate(String data) {
		Result result = new Result();
		// LOGGER.info("data.length() = "+data.length());
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(date);
		result.setValue(strDate);
		return result;
	}

	@Override
	public Result endDate(String data) {
		Result result = new Result();
		// LOGGER.info("data.length() = "+data.length());
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 10); // to get previous year add -1
		Date nextYear = cal.getTime();

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		String endDate = formatter1.format(nextYear);
		result.setValue(endDate);
		return result;
	}

	@Override
	public Result metaTitleFormula(String data, String attributeName) {
		Result result = new Result();
		String metatitle = "Buy " + attributeName + " Online At Best Price @ Tata CLiQ";
		result.setValue(metatitle);
		return result;
	}

	@Override
	public Result metaKeywordFormula(String data, String attributeName) {
		Result result = new Result();
		String metaKeyword = attributeName + " , Buy, Online, India, Tata CLiQ";
		result.setValue(metaKeyword);
		return result;
	}

	@Override
	public Result titlePresenceUrl(String data, String matchData) {
		// LOGGER.info("data = "+data+" matchData = "+matchData);
		double totalNoOfWords = data.split(" ").length;
		// double criteriaCount = 0.02*totalNoOfWords;
		String[] matchSplitData = matchData.split(" ");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		String[] wordsArray = data.split(" ");
		try {
			for (String str : matchSplitData) {
				for (String word : wordsArray) {
					// LOGGER.info("word = "+word+" str = "+str);
					if (word.toLowerCase().contains(str.toLowerCase())) {
						// LOGGER.info("match");
						count++;
					}
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("count = "+count+" matchSplitData = "+matchSplitData.length);
			// Thread.sleep(2000);
			if (count >= 0) {
				resultObj.setResult(true);
				resultObj.setScore(matchSplitData.length / count);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(0);
			resultObj.setResult(false);
		}
		// LOGGER.info("Result = "+resultObj.toString());
		return resultObj;
	}

	@Override
	public Result metaTitleIndiluxeApparel(String data, String attributeValue) {
		Result result = new Result();
		String metatitle = "Buy " + attributeValue + " for Men Online @ Tata CLiQ Luxury";
		result.setValue(metatitle);
		return result;
	}

	@Override
	public Result metaKeywordIndiluxeApparel(String data, String attributeName) {
		Result result = new Result();
		String metaKeyword = attributeName + " , Buy, Online, India, Tata CLiQ Luxury";
		result.setValue(metaKeyword);
		return result;
	}

	@Override
	public Result metaDescriptionIndiluxeApparel(String data, String attributeValue) {
		Result result = new Result();
		String metaDesc = attributeValue + " Price in India - Shop for " + attributeValue
				+ " at best price on Tata CLiQ Luxury.";
		result.setValue(metaDesc);
		return result;
	}

	@Override
	public Result metaTitleIndiluxeHome(String data, String attributeValue) {
		Result result = new Result();
		String metatitle = "Buy " + attributeValue + " for Home Online @ Tata CLiQ Luxury";
		result.setValue(metatitle);
		return result;
	}

	@Override
	public Result metaDescIndiluxeAccessaries(String data, String attributeValue) {
		Result result = new Result();
		String metaDesc = attributeValue + " Price in India - Shop for " + attributeValue
				+ " for Men at best price on Tata CLiQ Luxury.";
		result.setValue(metaDesc);
		return result;
	}

	@Override
	public Result checkDescIndiluxe(String data, String attributeValue) {
		Result result = new Result();
		if (data.equals("")) {
			return result;
		}
		// LOGGER.info("data = "+data);
		String[] keyWordArray = attributeValue.split(",");
		int keyWordArrayLength = keyWordArray.length;
		String[] firstent = data.split("\\.");
		// LOGGER.info("firstent = "+firstent.toString());
		String newData = firstent[0];
		// LOGGER.info("firstent = "+firstent.toString());
		for (int i = 0; i < keyWordArrayLength; i++) {
			String keyword = keyWordArray[i].trim();
			if (newData.toLowerCase().contains(keyword.toLowerCase())) {
				result.setResult(true);
				return result;
			}
		}
		return result;
	}

	@Override
	public Result styleNoteIndiluxe(String data, String attributeValue) {
		Result result = new Result();
		if (attributeValue.length() < 220) {
			result.setValue(attributeValue);
		} else {
			result.setValue("Same as Description");
		}
		return result;
	}

	@Override
	public Result defaultAgeBandIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("22-35");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultFitIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("Regular Fit");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultWaistRiseIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("Mid Rise");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultWeightIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("400 gm");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultWarrantyTypeIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("NA");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result UnisexIndiluxe(String data) {
		Result result = new Result();
		if (data.equalsIgnoreCase("yes")) {
			result.setValue("Yes");
		} else {
			result.setValue("NO");
		}
		return result;
	}

	@Override
	public Result defaultModelFitMenIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("Model height is 6'1\", chest 38\" and wearing size M or 32");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultModelFitWomenIndiluxe(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("Model height is 5'9\", chest 32\" and wearing size S or 30");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultCustomerSupportNoCroma(String data) {
		Result result = new Result();
		result.setValue("7207666000");
		return result;
	}

	@Override
	public Result defaultCustomerSupportEmailCroma(String data) {
		Result result = new Result();
		result.setValue("customersupport@croma.com");
		return result;
	}

	@Override
	public Result customiseEndDate(String data, String year) {
		Result result = new Result();
		int yr = Integer.parseInt(year);
		// LOGGER.info("data.length() = "+data.length());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, yr); // to get previous year add -1
		Date nextYear = cal.getTime();

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		String endDate = formatter1.format(nextYear);
		result.setValue(endDate);
		return result;
	}

	@Override
	public Result defaultStringCroma(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("Not Available");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result defaultNumberCroma(String data) {
		Result result = new Result();
		if (data.equals("")) {
			result.setValue("0");
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result convertCmInch(String data, String srcAndDestnType) {
		String[] keywords = srcAndDestnType.split("-");
		Result result = new Result();
		Double newData = Double.parseDouble(data);
		String src = keywords[0];
		String destn = keywords[1];
		if (src.equalsIgnoreCase("cm") && destn.equalsIgnoreCase("inch")) {
			newData = newData * 0.394;
			result.setValue(newData.toString());
		} else if (src.equalsIgnoreCase("inch") && destn.equalsIgnoreCase("cm")) {
			newData = newData * 2.54;
			result.setValue(newData.toString());
		} else {
			result.setValue(data);
		}
		return result;
	}

	@Override
	public Result walmart_checkMandatoryColumn(String data) {

		Result resultObj = new Result();
		try {

			// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in
			// MEthod%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			// Thread.sleep(10000);

			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {

				resultObj.setResult(true);
				resultObj.setScore(1);
				return resultObj;

			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}

	}

	@Override
	public Result walmart_googleScore(String data) {
		Result resultObj = new Result();
		try {
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(true);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				double dataInFloat = Double.parseDouble(data);
				double score = 0;
				if (dataInFloat >= 1 && dataInFloat <= 5) {
					score = 1;
				} else if (dataInFloat >= 6 && dataInFloat <= 10) {
					score = 0.75;
				} else if (dataInFloat >= 11 && dataInFloat <= 15) {
					score = 0.50;
				} else if (dataInFloat >= 16 && dataInFloat <= 20) {
					score = 0.25;
				} else {
					score = 0;
				}
				resultObj.setResult(true);
				resultObj.setScore(score);
				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}

	}

	@Override
	public Result walmart_walmartScore(String data) {
		Result resultObj = new Result();
		try {
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				double dataInFloat = Double.parseDouble(data);
				double score = 0;
				if (dataInFloat >= 1 && dataInFloat <= 10) {
					score = 1;
				} else if (dataInFloat >= 11 && dataInFloat <= 30) {
					score = 0.75;
				} else if (dataInFloat >= 31 && dataInFloat <= 45) {
					score = 0.50;
				} else if (dataInFloat >= 46 && dataInFloat <= 60) {
					score = 0.25;
				} else {
					score = 0;
				}
				resultObj.setResult(true);
				resultObj.setScore(score);
				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}
	}

	@Override
	public Result walmart_ratingScore(String data) {
		// LOGGER.info("walmart_ratingScore = "+data);
		Result resultObj = new Result();
		try {
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				double dataInFloat = Double.parseDouble(data);
				double score = 0;
				if (dataInFloat == 0) {
					score = 0;
					resultObj.setResult(false);
				} else {
					score = dataInFloat / 5;
					resultObj.setResult(true);
				}

				resultObj.setScore(score);
				return resultObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}
	}

	@Override
	public Result walmart_reviewScore(String data) {
		// LOGGER.info("walmart_reviewScore = "+data);
		Result resultObj = new Result();
		try {
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				double dataInFloat = Double.parseDouble(data);
				double score = 0;
				if (dataInFloat == 0) {
					score = 0;
					resultObj.setResult(false);
					resultObj.setScore(score);
					return resultObj;
				} else if (dataInFloat >= 1 && dataInFloat <= 9) {
					score = 0.1;
				} else if (dataInFloat >= 10 && dataInFloat <= 20) {
					score = 0.2;
				} else if (dataInFloat >= 21 && dataInFloat <= 40) {
					score = 0.4;
				} else if (dataInFloat >= 41 && dataInFloat <= 60) {
					score = 0.6;
				} else if (dataInFloat >= 61 && dataInFloat <= 80) {
					score = 0.8;
				} else {
					score = 1;
				}
				resultObj.setResult(true);
				resultObj.setScore(score);
				return resultObj;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}

	}

	@Override
	public Result walmart_checkTwoAttrSame(String data, String attributeName) {
		// LOGGER.info("Inside walmart_checkTwoAttrSame");
		// System.out.println("walmart_checkTwoAttrSame data = "+data+" attributeName =
		// "+attributeName);
		Result resultObj = new Result();
		try {

			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				if (data.equalsIgnoreCase(attributeName)) {
					resultObj.setResult(false);
					resultObj.setScore(0);
					return resultObj;
				} else {
					resultObj.setResult(true);
					resultObj.setScore(1);
					return resultObj;

				}
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}

	}

	@Override
	public Result walmart_checkCharacterLessthanCount(String data, String length) {
		data = data.replaceAll("@@", "");
		Result resultObj = new Result();
		try {

			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic

				int dataLength = Integer.parseInt(length);

				if (data.length() <= dataLength) {
					resultObj.setResult(true);
					resultObj.setScore(1);
					return resultObj;
				} else {
					resultObj.setResult(false);
					resultObj.setScore(0);
					return resultObj;
				}
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(-1);
			return resultObj;
		}

	}

	@Override
	public Result walmart_checkWordCountIsGreater(String data, String minWordCount) {
		Result result = new Result();
		try {
			String[] wordsArray = data.split("\\s+");
			int wordLength = 0;
			try {
				wordLength = wordsArray.length;
			} catch (Exception e) {
				// TODO: handle exception
				wordLength = 0;
			}
			int wordCount = 0;
			try {
				wordCount = Integer.valueOf(minWordCount);
			} catch (Exception e) {
				// TODO: handle exception
				wordCount = 0;
			}
			if (wordLength <= 10) {
				result.setResult(false);
				result.setScore(0);
				return result;
			} else if (wordLength >= 11 && wordLength <= 30) {
				result.setResult(false);
				result.setScore(0.25);
				return result;
			} else if (wordLength >= 31 && wordLength <= 60) {
				result.setResult(true);
				result.setScore(0.5);
				return result;
			} else if (wordLength >= 61 && wordLength <= 90) {
				result.setResult(true);
				result.setScore(0.75);
				return result;
			} else {
				result.setResult(true);
				result.setScore(1);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
			return result;
		}

	}

	@Override
	public Result walmart_checkinimumImageCountIsGreater(String data, String minImageCount) {
		Result result = new Result();
		try {
			String[] wordsArray = data.split(",");
			int imageLength = 0;
			try {
				imageLength = wordsArray.length;
			} catch (Exception e) {
				// TODO: handle exception
				imageLength = 0;
			}
			int imageMinCount = 0;
			try {
				imageMinCount = Integer.valueOf(minImageCount);
			} catch (Exception e) {
				// TODO: handle exception
				imageMinCount = 0;
			}
			if (imageLength >= 2) {
				result.setResult(true);
				result.setScore(1);
				return result;
			} else if (imageLength == 1) {
				result.setResult(true);
				result.setScore(0.5);
				return result;
			} else {
				result.setResult(false);
				result.setScore(0);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
			return result;
		}
	}

	@Override
	public Result walmart_highlightMinCount(String data, String minCount) {
		Result resultObj = new Result();
		try {
			data = data.trim();
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic

				String[] wordsArray = data.split("\\|\\|");
				int heighLightCount = wordsArray.length;
				int wordCount = Integer.valueOf(minCount);
				if (heighLightCount == 0) {
					resultObj.setResult(false);
					resultObj.setScore(0);
					return resultObj;
				} else if (heighLightCount >= 4) {
					resultObj.setResult(true);
					resultObj.setScore(1);
					return resultObj;
				} else if (heighLightCount == 3) {
					resultObj.setResult(true);
					resultObj.setScore(0.75);
					return resultObj;
				}
				if (heighLightCount == 2) {
					resultObj.setResult(true);
					resultObj.setScore(0.5);
					return resultObj;
				}
				if (heighLightCount == 1) {
					resultObj.setResult(true);
					resultObj.setScore(0.25);
					return resultObj;
				} /*
					 * { double score = heighLightCount / wordCount; resultObj.setResult(true);
					 * resultObj.setScore(score);
					 * System.out.println("walmart_highlightMinCount data : "+data);
					 * System.out.println("walmart_highlightMinCount heighLightCount : "
					 * +heighLightCount + " : wordCount : "+wordCount);
					 * System.out.println("walmart_highlightMinCount score : "+score);
					 * 
					 * }
					 */

				/*
				 * resultObj.setResult(true); if(score>=1) { resultObj.setScore(1); }else {
				 * resultObj.setScore(score); }
				 */

				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result walmart_KeywordPresenseURL(String data, String keyword) {
		Result resultObj = new Result();
		try {
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(true);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				result = true;

				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result walmart_metaKeywordPresenseDensity(String data, String keyword) {
		Result resultObj = new Result();
		try {
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(true);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic
				result = true;

				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result walmart_keywordDensityDescription(String data, String matchData) {
		double totalNoOfWords = data.split(" ").length;
		double criteriaCount = 0.02 * totalNoOfWords;
		// System.out.println("walmart_keywordDensityDescription : title : "+matchData);
		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
		String[] matchSplitData = matchData.split(" ");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		String[] wordsArray = data.split(" ");
		try {
			for (String str : matchSplitData) {
				for (String word : wordsArray) {
					if (word.toLowerCase().equalsIgnoreCase(str)) {
						count++;
					}
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("count = "+count+" criteriaCount = "+criteriaCount+"
			// totalNoOfWords = "+totalNoOfWords);
			if (count >= criteriaCount) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(-1);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result walmart_presenctTitleKeywordsURL(String data, String matchData) {
		double totalNoOfWords = data.split(" ").length;
		double criteriaCount = 0.05 * totalNoOfWords;
		String[] matchSplitData = matchData.split(",");
		Result resultObj = new Result();
		result = true;
		int count = 0;
		String[] wordsArray = data.split(" ");
		try {
			for (String str : matchSplitData) {
				for (String word : wordsArray) {
					if (word.toLowerCase().equals(str)) {
						count++;
					}
				}
			}
			int matchSplitDataLength = matchSplitData.length;
			// LOGGER.info("count = "+count+" criteriaCount = "+criteriaCount+"
			// totalNoOfWords = "+totalNoOfWords);
			if (count >= criteriaCount) {
				resultObj.setResult(true);
				resultObj.setScore(1);
			} else {
				resultObj.setResult(false);
				resultObj.setScore(0);
			}
		} catch (Exception e) {
			resultObj.setScore(-1);
			resultObj.setResult(false);
		}
		return resultObj;
	}

	@Override
	public Result walmart_checkDPIOfImage(String data, String dpi) throws IOException {
		// System.out.println(data);
		// System.out.print("checkPpiOfImage data : "+data);
		Result result = new Result();
		// data = data.replace("||", " ");
		try {
			String[] sli = data.split(",");
			// System.out.println("checkDPIOfImage list : "+sli.length);

			List<String> list = new ArrayList<String>();
			float count = 0;
			Collections.addAll(list, sli);
			while (list.remove("")) {
			}

			for (String url1 : list) {
				url1 = url1.trim();
				// System.out.println("physicalWidthDpi"+url1);
				int dpiInt = Integer.parseInt(dpi);
				// ImageServiceImpl obj = new ImageServiceImpl();
				ImageInfo imageInfo;
				try {
					imageInfo = Sanselan.getImageInfo(new File(url1));
					final int physicalWidthDpi = imageInfo.getPhysicalWidthDpi();
					final int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();
					if ((physicalWidthDpi == dpiInt) && (physicalHeightDpi == dpiInt)) {
						// System.out.println("physicalWidthDpi = "+physicalWidthDpi+" physicalHeightDpi
						// = "+physicalHeightDpi);
						count++;
					} else {

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					e.getMessage();
				}
			}

			float score = count / list.size();
			// float score = list.size() /count ;
			// System.out.print("checkPpiOfImage score : "+score + " : count : "+count + " :
			// list.size() "+list.size());

			if (score >= 1) {

				result.setResult(true);
				result.setScore(score);

				return result;
			} else {
				result.setResult(false);
				result.setScore(score);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
			return result;
		}
	}

	public static boolean checkPercentageOccuped(String data, String percentage) {

		BufferedImage img = null;
		File f = null;

		// read image
		try {
			// String data = "E:\\REINLABS\\Projects\\Noon Pilot\\Image\\Video
			// Game\\Noon_N11086269A_p4_1.jpg,E:\\REINLABS\\Projects\\Content Audit\\Input
			// File\\Tilly\\Folder Images\\Folder Images\\366242957\\366242957_1.jpg";
			data = data.trim();
			// System.out.println(data);
			f = new File(data);
			img = ImageIO.read(f);
		} catch (IOException e) {
			// System.out.println(e);
		}

		// get image width and height
		int width = img.getWidth();
		int height = img.getHeight();

		// int p = img.getRGB(15,431);

		int leftmostPixel = 10000;
		int rightmostPixel = 0;
		int count = 0;
		Color firstPixel = new Color(img.getRGB(0, 0));

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				int currentXPixel = i;
				// int p = img.getRGB(0,0);
				Color c = new Color(img.getRGB(i, j));

				// System.out.println(c) ;
				/*
				 * int r = (p>>16) & 0xff;
				 * 
				 * 
				 * int g = (p>>8) & 0xff;
				 * 
				 * 
				 * int b = p & 0xff;
				 */

				if (c.getRed() != firstPixel.getRed() && c.getGreen() != firstPixel.getGreen()
						&& c.getBlue() != firstPixel.getBlue()) {

					count++;
					if (currentXPixel < leftmostPixel) {

						leftmostPixel = currentXPixel;
					} else if (currentXPixel > rightmostPixel) {

						rightmostPixel = currentXPixel;

					}

				}
				// System.out.println(leftmostPixel);
				// System.out.println(rightmostPixel);

			}
		}
		// System.out.println("leftmostPixel "+leftmostPixel);
		// System.out.println("rightmostPixel "+rightmostPixel);
		// get red

		int pixelCoverage = rightmostPixel - leftmostPixel;
		// System.out.println("pixelCoverage "+pixelCoverage);
		// System.out.println("width "+width);

		float percent = (pixelCoverage * 100) / width;

		// System.out.println("percent "+percent);
		int percentageInput = Integer.parseInt(percentage);
		boolean status = false;
		if (percent >= percentageInput) {
			// System.out.println(" checkPercentageOccuped : "+true);
			status = true;
		} else {
			// System.out.println(" checkPercentageOccuped : "+false);
			status = false;
		}
		return status;

	}

	@Override
	public Result walmart_checkImageBackgroundWhite(String data) throws IOException {
		Result result = new Result();

		String[] sli = data.split(",");

		imageRules = new ImageRules();
		boolean resultImage = imageRules.isImageBackgroundWhite(sli[0]);
		/*
		 * for (String url1 : list) {
		 * 
		 * boolean resultImage= imageRules.isImageBackgroundWhite(url1);
		 * 
		 * if (resultImage) {
		 * 
		 * count++; }
		 * 
		 * }
		 */

		// float score = count / list.size();
		if (resultImage == true) {
			result.setResult(true);
			result.setScore(1);
			return result;
		} else {
			result.setResult(false);
			result.setScore(0);
			return result;
		}

		/*
		 * result.setResult(false); result.setScore(score); return result;
		 */
	}

	@Override
	public Result walmart_checkDimensionOfImage(String data, String x, String y) throws IOException {
		int xVal = Integer.parseInt(x);
		int yVal = Integer.parseInt(y);
		// System.out.println("x = "+x+" y = "+y+" data = "+data);
		Result result = new Result();

		// data = data.replace("||", " ");
		String[] sli = data.split(",");
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, sli);

		while (list.remove("")) {
		}

		float count = 0;

		for (String url1 : list) {
			// System.out.println("Inside "+url1);
			url1 = url1.trim();
			if (isValid(url1.trim())) {
				// System.out.println("Inside "+url1);

				URL url = new URL(url1);
				BufferedImage bimg = ImageIO.read(url);
				int width = bimg.getWidth();
				int height = bimg.getHeight();
				// System.out.println(width + " : "+ xVal+" : " +height+" : " +yVal);

				if (width > xVal && height > yVal) {
					count++;
				}
			} else {
				try {
					BufferedImage bimg = ImageIO.read(new File(url1));
					int width = bimg.getWidth();
					int height = bimg.getHeight();
					// System.out.println(width + " : "+ xVal+" : " +height+" : " +yVal);
					if (width > xVal && height > yVal) {
						// System.out.println("walmart_checkDimensionOfImage "+true);
						count++;
					}
				} catch (Exception e) {
				}
			}
		}

		float score = count / list.size();
		// float score = list.size() / count ;

		if (score >= 1) {
			result.setResult(true);
			result.setScore(score);
			// System.out.println(result.toString());
			return result;
		} else {
			result.setResult(false);
			result.setScore(score);
			// System.out.println(result.toString());
			return result;
		}
	}

	/*
	 * @Override public Result walmart_checkPercentageOccuped(String data, String
	 * percentage) {//System.out.print("checkPpiOfImage data : "+data); Result
	 * result = new Result(); //data = data.replace("||", " "); String[] sli =
	 * data.split(","); //System.out.print("checkPpiOfImage list : "+sli.length);
	 * int percentageInt = Integer.parseInt(percentage); List<String> list = new
	 * ArrayList<String>(); float count = 0; Collections.addAll(list, sli); while
	 * (list.remove("")) { }
	 * 
	 * for (String url1 : list) { //System.out.println("physicalWidthDpi"+url1);
	 * //ImageServiceImpl obj = new ImageServiceImpl(); try { //boolean check =
	 * checkPercentageOccuped(url1,percentage); ImageRules imageRules = new
	 * ImageRules(); BufferedImage cropImage =
	 * imageRules.walmart_getCroppedImage(url1, 0.1); double cropImageCanvas =
	 * cropImage.getWidth() * cropImage.getHeight(); url1 = url1.trim(); File
	 * imageFileOriginal = new File(url1); //FileInputStream fis = new
	 * FileInputStream(imageFileOriginal); BufferedImage sourceOriginal = null; try
	 * { sourceOriginal = ImageIO.read(imageFileOriginal); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } double
	 * sourceImageCanvas = sourceOriginal.getWidth() * sourceOriginal.getHeight();
	 * double percent = (cropImageCanvas*100)/sourceImageCanvas;
	 * //System.out.println("Source : "+url1);
	 * //System.out.println("original dim : "+sourceImageCanvas + " : crop dim"+
	 * cropImageCanvas);
	 * 
	 * //System.out.println("percentage : "+percent);
	 * 
	 * if(percent >percentageInt) { count++; } } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace();
	 * 
	 * } }
	 * 
	 * float score = count / list.size();
	 * //System.out.print("checkPpiOfImage score : "+score + " : count : "+count +
	 * " : list.size() "+list.size()); boolean check = false; try { check =
	 * checkPercentageOccuped(list.get(0),percentage); }catch (Exception e) { //
	 * TODO: handle exception } //float score = count / list.size();
	 * //System.out.print("checkPpiOfImage score : "+score + " : count : "+count +
	 * " : list.size() "+list.size());
	 * 
	 * if (score>0) {
	 * 
	 * result.setResult(true); result.setScore(score);
	 * 
	 * return result; }else { result.setResult(false); result.setScore(0); return
	 * result; } }
	 */

	@Override
	public Result walmart_checkPercentageOccuped(String data, String percentage) {// System.out.print("checkPpiOfImage
																					// data : "+data);
		Result result = new Result();
		String[] sli = data.split(",");
		int percentageInt = Integer.parseInt(percentage);
		List<String> list = new ArrayList<String>();
		float count = 0;
		Collections.addAll(list, sli);
		while (list.remove("")) {
		}

		for (String url1 : list) {
			try {
				ImageRules imageRules = new ImageRules();
				BufferedImage cropImage = imageRules.walmart_getCroppedImage(url1, 0.1);
				boolean horizontalImage = false;
				if (cropImage.getWidth() > cropImage.getHeight()) {
					horizontalImage = true;
				} else {
					horizontalImage = false;
				}
				double cropImageCanvas = cropImage.getWidth() * cropImage.getHeight();
				url1 = url1.trim();
				File imageFileOriginal = new File(url1);
				BufferedImage sourceOriginal = null;
				try {
					sourceOriginal = ImageIO.read(imageFileOriginal);
				} catch (IOException e) {
					// e.printStackTrace();
				}
				double percent = 0;
				// System.out.println("horizontalImage ; "+horizontalImage);
				if (horizontalImage) {
					percent = (cropImage.getWidth() * 100) / sourceOriginal.getWidth();
					// System.out.println("original width : "+sourceOriginal.getWidth() + " : crop
					// dim"+ cropImage.getWidth());

				} else {
					percent = (cropImage.getHeight() * 100) / sourceOriginal.getHeight();
					// System.out.println("original height : "+sourceOriginal.getHeight() + " : crop
					// height"+ cropImage.getHeight());

				}

				// System.out.println("percentage : "+percent);

				if (percent >= percentageInt) {
					count++;
				}
			} catch (Exception e) {
				// e.printStackTrace();

			}
		}

		float score = count / list.size();

		if (score > 0) {

			result.setResult(true);
			result.setScore(score);

			return result;
		} else {
			result.setResult(false);
			result.setScore(0);
			return result;
		}
	}

	@Override
	public Result walmart_checkLOVinAttribute(String data, String attributeValue) {
		// LOGGER.info("Inside walmart_checkLOVinAttribute");

		// LOGGER.info("data = "+data+" attributeValue = "+attributeValue);

		Result result = new Result();
		try {
			if (data.equals("")) {
				return result;
			}

			String[] keyWordArray = attributeValue.split(",");
			int keyWordArrayLength = keyWordArray.length;
			data = data.trim();
			double countOccur = 0;
			// LOGGER.info("walmart_checkLOVinAttribute : "+data + " : attrVal :
			// "+attributeValue);
			// LOGGER.info("keyWordArrayLength: "+keyWordArrayLength);
			for (int i = 0; i < keyWordArrayLength; i++) {

				String keyword = keyWordArray[i].trim();
				int indexLast = keyword.lastIndexOf(".");
				/*
				 * System.out.println("keyword: "+keyword);
				 * System.out.println("indexLast: "+indexLast);
				 * System.out.println("keyword.length(): "+keyword.length());
				 */
				if (indexLast == keyword.length() - 1) {
					keyword = keyword.substring(0, keyword.length() - 1);
					// System.out.println("Inside if kw: "+keyWordArray[i].trim());

				}

				// System.out.println("keyword2: "+keyword);

				try {
					keyword = keyword.replaceAll("##", ",");

				} catch (Exception e) {
					// TODO: handle exception
					keyword = keyWordArray[i].trim();

				}
				// if (data.toLowerCase().contains(keyword.toLowerCase())) {
				// LOGGER.info("keyword: "+keyword);
				// LOGGER.info("walmart_checkLOVinAttribute : "+data + " : attrVal :
				// "+attributeValue);
				// if (data.toLowerCase().equalsIgnoreCase(keyword.toLowerCase())) {
				if (keyword.equalsIgnoreCase("n/a") || keyword.equalsIgnoreCase("")) {
					continue;
				}
				if (data.toLowerCase().matches(".*\\b" + keyword.toLowerCase() + "\\b.*")) {
					// LOGGER.info("keyword single : "+keyword+ " Found");
					// LOGGER.info("Data : "+data.toLowerCase() + " : keyword Found :
					// "+keyword.toLowerCase());

					countOccur++;
					// result.setResult(true);
					// return result;
				}

			}
			if (countOccur > 0) {
				result.setResult(true);
			} else {
				result.setResult(false);
			}
			// LOGGER.info("Data : "+data.toLowerCase() + " : keyword Found :
			// "+keyword.toLowerCase());
			double score = countOccur / keyWordArray.length;

			// System.out.println("walmart_checkLOVinAttribute countOccur : "+countOccur + "
			// : keyWordArray.length : "+keyWordArray.length+" score = "+score);
			result.setScore(score);
			// LOGGER.info("result = "+result.toString());
			return result;
		} catch (Exception e) {
			result.setResult(false);
			result.setScore(-1);
			return result;
			// TODO: handle exception
		}
		// return result;
	}

	public static boolean isValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean colorWithinTolerance(int a, int b, double tolerance) {
		int aAlpha = (int) ((a & 0xFF000000) >>> 24); // Alpha level
		int aRed = (int) ((a & 0x00FF0000) >>> 16); // Red level
		int aGreen = (int) ((a & 0x0000FF00) >>> 8); // Green level
		int aBlue = (int) (a & 0x000000FF); // Blue level

		int bAlpha = (int) ((b & 0xFF000000) >>> 24); // Alpha level
		int bRed = (int) ((b & 0x00FF0000) >>> 16); // Red level
		int bGreen = (int) ((b & 0x0000FF00) >>> 8); // Green level
		int bBlue = (int) (b & 0x000000FF); // Blue level

		double distance = Math.sqrt((aAlpha - bAlpha) * (aAlpha - bAlpha) + (aRed - bRed) * (aRed - bRed)
				+ (aGreen - bGreen) * (aGreen - bGreen) + (aBlue - bBlue) * (aBlue - bBlue));
		double percentAway = distance / 510.0d;
		return (percentAway > tolerance);
	}

	@Override
	public Result multiValueCount(String data) {
		Result resultObj = new Result();
		int count = 0;

		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '|') {
				count++;
			} else {
				continue;
			}

		}
		if (count == 2 || count == 5 || count == 8) {
			resultObj.setResult(true);
			resultObj.setScore(1);
		} else {
			resultObj.setResult(false);
			resultObj.setScore(0);
		}

		return resultObj;
	}

	/* Rules for TataCliq */
	// Added by vikranti koli on 07/07/2022 for rules
	// Set Any Required Value For tata_Cliq

	@Override
	public Result tatabeauty_setStartDate(String data) {
		Result result = new Result();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy");
		Date date = new Date();
		String curDate = formatter.format(date);
		result.setValue(curDate);
		result.setResult(true);
		// LOGGER.info(curDate+"beauty_setStartDate= "+result.getValue());
		return result;
	}

	@Override
	public Result tatabeauty_setEndDate(String data) {
		Result result = new Result();
		Calendar cal = Calendar.getInstance();
		// Date today = cal.getTime();
		cal.add(Calendar.YEAR, 5); // to get previous year add -1
		Date nextYear = cal.getTime();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		String endDate = formatter1.format(nextYear);
		result.setValue(endDate);
		result.setResult(true);
		// LOGGER.info(endDate+"beauty_setEndDate= "+result.getValue());
		return result;
	}

	@Override
	public Result tata_setReqValues(String data, String attributeValue) {
		LOGGER.info("data = "+data+  "data2--"+attributeValue);
		Result result = new Result();
		result.setValue(attributeValue);
		result.setResult(true);
		return result;
	}

	@Override
	public Result tata_SetblankData(String data) {
		Result result = new Result();
			result.setValue("");
			result.setResult(true);
			result.setScore(1);
		return result;
	}
	// Set Wash Care should be in sentence case
	@Override
	public Result tata_setSentenceCase(String data) {
		Result result = new Result();
		data = "\"" + data + "\"";
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	@Override
	public Result tata_setValueToNo(String data) {
		Result result = new Result();
		data = "No";
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Rearrange Fabric Comp From Chiffon 100% to 100% Chiffon
	@Override
	public Result tata_setFabricComposition(String data) {
		Result result = new Result();
		String[] arrOfdata = data.split(" ");
		data = arrOfdata[1] + " " + arrOfdata[0];
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// "." Replaced With "," for Fabric Composition
	@Override
	public Result tata_replaceTypeFabricComp(String data) {
		Result result = new Result();
		data = data.replace(".", ",");
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Remove Special Character Except % Fabric Composition
	@Override
	public Result tata_removeSpecialCharactersFC(String data) {
		Result result = new Result();
		data = data.replaceAll("[ ](?=[ ])|[^-_,A-Za-z0-9 ?!% ]+", "");
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Length Fabric Composition should not be more the 50
	@Override
	public Result tata_lengthFC(String data) {
		Result result = new Result();
		if (data.length() <= 50) {
			result.setValue(data);
		} else {
			result.setValue("");
		}
		return result;
	}

	// Set Yes Always
	@Override
	public Result tata_setValueToYes(String data) {
		Result result = new Result();
		data = "Yes";
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	@Override
	public Result tata_specialCharacters(String data) {
		Result result = new Result();
		String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
		for (int i = 0; i < data.length(); i++) {
			char character = data.charAt(i);
			if (specialCharactersString.contains(Character.toString(character))) {
				result.setResult(true);
				result.setScore(1);
				break;
			}

		}
		return result;
	}

	// TO Replace Years,month with Y,M for Size (Refer LOV List)
	@Override
	public Result tata_replaceToYandM(String data) {
		Result result = new Result();
		data = data.replace("Years", "Y").replace("Months", "M").replace("years", "Y").replace("months", "M")
				.replace("month", "M").replace("year", "Y").replace("Month", "M").replace("Year", "Y");
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Set NO if data is Blank
	@Override
	public Result tata_setNoToBlank(String data) {
		Result result = new Result();
		if (data.isEmpty() || data == " " || data == "") {
			result.setValue("No");
			result.setResult(true);
		} else {

		}

		return result;
	}

	// Set 1 Always for Attributes
	@Override
	public Result tata_setValueAlwaysOne(String data) {
		Result result = new Result();
		data = "1";
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Always Market Place for Attribute--Platform (Refer LOV List)
	@Override
	public Result tata_alwaysMarketplace(String data) {
		Result result = new Result();
		data = "Marketplace";
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Set Product Weight always 250
	@Override
	public Result tata_setproductWeight(String data) {
		Result result = new Result();
		data = "250";
		result.setValue(data);
		result.setResult(true);
		return result;
	}

	// Set WashCare always Machine Wash
	@Override
	public Result tata_setwashCare(String data) {
		Result result = new Result();
		if (data.isEmpty() || data == "NA" || data == "na" || data == "Na" || data == " ") {
			data = "Machine Wash";
			result.setValue(data);
			result.setResult(false);
		} else {
			result.setResult(true);
			result.setValue(data);
		}

		return result;
	}

	// Set L4 if Categories is Empty
	@Override
	public Result tata_setLeafCategories(String data) {
		Result result = new Result();
		if (data.isEmpty() || data == "NA" || data == "na" || data == "Na" || data == " ") {
			data = "L4";
			result.setValue(data);
			result.setResult(true);
		} else {
			result.setValue(data);
			result.setResult(true);
		}
		return result;
	}

	@Override
	public Result tata_setStartDate(String data) {
		Result result = new Result();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy");
		Date date = new Date();
		String curDate = formatter.format(date);
		result.setValue(curDate);
		result.setResult(true);

		return result;
	}

	@Override
	public Result tata_setEndDate(String data) {
		Result result = new Result();
		Calendar cal = Calendar.getInstance();
		// Date today = cal.getTime();
		cal.add(Calendar.YEAR, 10); // to get previous year add -1
		Date nextYear = cal.getTime();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
		String endDate = formatter1.format(nextYear);
		result.setValue(endDate);
		result.setResult(true);
		// LOGGER.info("result= "+result.getValue());

		return result;
	}

	@Override
	public Result setSameAttributeData(String data, String AttributeName) {

		// LOGGER.info("data = "+data+" AttributeName checkTwoCellsForSameValue
		// ="+AttributeName);
		Result result = new Result();
		data = data.trim();

		if (data.toLowerCase().equals(AttributeName.toLowerCase())) {
			result.setResult(true);
			result.setScore(1);
			result.setValue(AttributeName);
			return result;
		}

		return result;

	}

	@Override
	public Result tata_setSorDValue(String data) {

		// LOGGER.info("data = "+data+" AttributeName checkTwoCellsForSameValue
		// ="+AttributeName);
		Result result = new Result();
		data = data.trim();

		if (data.isEmpty() || data == " " || data == "" || data == null) {
			result.setResult(false);
			result.setScore(0);
			result.setValue("S");
			return result;
		} else {
			result.setResult(true);
			result.setScore(0);
			return result;
		}
	}

	@Override
	public Result setGlobalIDType(String data, String AttributeName) {

		Result result = new Result();

		if (AttributeName.length() == 13) {

			result.setScore(1);
			result.setValue("EAN");
			result.setResult(true);

		} else if (AttributeName.length() == 12) {

			result.setScore(1);
			result.setValue("UPC");
			result.setResult(true);

		} else if (AttributeName.length() < 12 && AttributeName.length() > 10) {

			result.setScore(1);
			result.setValue("MPN");
			result.setResult(true);

		} else {
			result.setResult(false);
			result.setScore(0);

		}

		return result;

	}

	@Override
	public Result tata_getFeatureCount(String data) {
		Result resultObj = new Result();
		try {
			data = data.trim();
			//System.out.println("data: "+data);
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				//System.out.println("here");
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic

				String[] wordsArray = data.split("\\|");
				int featureCount = wordsArray.length;
				// System.out.println("featureCount: " + featureCount);
				// int wordCount = Integer.valueOf(9);
				if (featureCount == 3 || featureCount == 6 || featureCount == 9) {
					//System.out.println("True here");
					resultObj.setResult(true);
					resultObj.setScore(1);
					return resultObj;
				} else {
					//System.out.println("False here");
					resultObj.setResult(false);
					resultObj.setScore(0);
					return resultObj;
				}
			}
		} catch (Exception e) {
			//System.out.println("Exception here");
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result tata_testFeatureCount(String data) {
		Result resultObj = new Result();
		try {
			data = data.trim();
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} else {
				// Write Logic

				String[] wordsArray = data.split("\\|");
				int featureCount = wordsArray.length;
				// System.out.println("featureCount: " + featureCount);
				// int wordCount = Integer.valueOf(9);
				if (featureCount == 3 || featureCount == 6 || featureCount == 9) {
					resultObj.setResult(true);
					resultObj.setScore(1);
					return resultObj;
				} else {
					resultObj.setResult(false);
					resultObj.setScore(0);
				}
				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	@Override
	public Result checkSpecificSpecialCharacter(String data) {
		Result resultObj = new Result();

		String specialCharacters = "!#$%&'*:;<=>?@[]^_`{}";

		for (int i = 0; i < specialCharacters.length(); i++) {

			// Checking if the input string contain any of the specified Characters
			if (data.contains(Character.toString(specialCharacters.charAt(i)))) {
				resultObj.setResult(false);
				return resultObj;
			} else {
				resultObj.setResult(true);
				return resultObj;
			}
		}
		return resultObj;
	}

	@Override
	public Result tata_checkSpecificSpecialCharacter(String data) {
		Result resultObj = new Result();

		String specialCharacters = "!#$%&'*:;<=>?@[]^/|`{}~";

		for (int i = 0; i < specialCharacters.length(); i++) {

			// Checking if the input string contain any of the specified Characters
			if (data.contains(Character.toString(specialCharacters.charAt(i)))) {
				resultObj.setResult(false);
				return resultObj;
			} else {
				resultObj.setResult(true);
				return resultObj;
			}
		}
		return resultObj;
	}
	
	//Added By Aadesh On 28/10/2022 for ExcelBase MaxCharlenghtIssue
    @Override
    public Result maxCharacterLengthExelBase(String data,String length) {
        int lengthValue = Integer.parseInt(length);
        Result result = new Result();
        Pattern regex = Pattern.compile("[+]");
        Matcher matcher = regex.matcher(data);
        if (data.length() > lengthValue||matcher.find()) {
            result.setResult(false);
            result.setScore(0);
            return result;
        }
        else {
            result.setResult(true);
            result.setScore(1);
        }
        return result;
    }
    
    @Override
	public Result tatabeauty_checkImageCount(String data, String minImageCount) {
		Result result = new Result();
		try {
			String[] wordsArray = data.split(",");
			int imageLength = 0;
			try {
				imageLength = wordsArray.length;
			} catch (Exception e) {
				// TODO: handle exception
				imageLength = 0;
			}
			int imageMinCount = 0;
			try {
				imageMinCount = Integer.valueOf(minImageCount);
			} catch (Exception e) {
				// TODO: handle exception
				imageMinCount = 0;
			}
			if (imageLength >= 6) {
				result.setResult(true);
				result.setScore(1);
				return result;
			} else {
				result.setResult(false);
				result.setScore(0);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
			return result;
		}
	}
    
    
    
    @Override
	public Result tatabeauty_presenceOfSizeInTitle(String data, String attributeName) {
		Result resultObj = new Result();
		if (data.isEmpty() || attributeName.equals("") || attributeName.equalsIgnoreCase("n/a") ||data.isEmpty() || data.equals("") || data.equalsIgnoreCase("n/a")) {
			resultObj.setResult(true);
			return resultObj;
		}		
		else 
		{		
			try {
					if (attributeName.toLowerCase().contains(data.toLowerCase())) 
					{								
							result = true;				
					}
					else 
					{
						result = false;										
					}	
					
			}
			
			catch (Exception e) {				
				result = false;
				
			}
			resultObj.setResult(result);
		}
		//LOGGER.info("resultObj = "+resultObj);
		return resultObj;
	}
    
   
    @Override
  	public Result tatabeauty_EANEqualSKU(String data,String data2) {
  		Result resultObj = new Result();
  		String imageString=data.substring(data.indexOf('_') + 1);
  		String separator ="_";
  		int sepPos = imageString.indexOf(separator);
  		String d=imageString.substring(sepPos + separator.length());
  		String SKU=d.substring(0, d.indexOf('_'));
  		String styleCode=imageString.substring(0, imageString.indexOf('_'));
  	//	LOGGER.info(styleCode+"---"+SKU+"====="+data2);
  		if (styleCode.isEmpty() || styleCode.equals("") || styleCode.equalsIgnoreCase("n/a")) {
  			return resultObj;
  		}
  		
  		else 
  		{
  			
  			try {
  				if (!data2.isEmpty() || !data2.equals("") || !data2.equalsIgnoreCase("n/a")) 
  				{		  		
  					if (!data2.equals(SKU)) 
  					{
  						//LOGGER.info(data2+"-----"+SKU);
  						resultObj.setResult(false);
  						resultObj.setScore(0);
  					}
  					else 
  					{
  						resultObj.setResult(true);
  						resultObj.setScore(1);
  					}
  				}
  				else 
					{
						resultObj.setResult(true);
						resultObj.setScore(1);
					}
  				
  				
  			} catch (Exception e) {
  				resultObj.setScore(0);
  				resultObj.setResult(false);
  			}
  		}
  		//LOGGER.info("resultObj = "+resultObj);
  		return resultObj;
  	}
    
    @Override
  	public Result tatabeauty_SpellCheck(String data) throws IOException {
  		Result resultObj = new Result();
  		Set<String> dictionary = new HashSet<>(Files.readAllLines(Paths.get("D:/WorkSpace/Technical Team/Files/TataClic/Beauty/words.txt")));
  		if (!data.isEmpty() || !data.equals("") || !data.equalsIgnoreCase("n/a")) 
		{	
		String[] splitSentence = data.split(" ");
		for (String word : splitSentence)
			
			if (dictionary.contains(word.toLowerCase())) 
			{
				//System.out.println(word + " : correct");
				resultObj.setScore(1);
				resultObj.setResult(true);
			}		
			else
			
			{
				//System.out.println(word + " : incorrect");
				resultObj.setScore(0);
				resultObj.setResult(false);	
			}
		}
  		else 
  		{
  			resultObj.setScore(1);
			resultObj.setResult(true);
  		}
		//LOGGER.info("resultObj = "+resultObj);
		return resultObj;
  	}
    
	@Override
	public Result tatabeauty_FeatureCount(String data) {
		Result resultObj = new Result();
		try {
			data = data.trim();
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(true);
				resultObj.setScore(1);
				return resultObj;
			} 
			if (data.equals("\\|")) {
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			} 
			else {
				String[] wordsArray = data.split("\\|");
				int featureCount = wordsArray.length;
				if (featureCount == 3 || featureCount == 6 || featureCount == 9) {
					resultObj.setResult(true);
					resultObj.setScore(1);
					return resultObj;
				} else {
					resultObj.setResult(false);
					resultObj.setScore(0);
				}
				return resultObj;
			}
		} catch (Exception e) {
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
	}
    
    

	  
	  //Added By Aadesh For Beauty Internal Specific Special Characters Check on 13-02-2023
	  @Override
		public Result tatabeautyInternal_checkSpecificSpecialCharacter(String data) {
			Result resultObj = new Result();

			String specialCharacters = "_&%@#$^*()\"\"";//""_&%@#$^*()

			for (int i = 0; i < specialCharacters.length(); i++) {
				if (data.contains(Character.toString(specialCharacters.charAt(i)))) {
					resultObj.setResult(false);
					return resultObj;
				} else {
					resultObj.setResult(true);
					return resultObj;
				}
			}
			return resultObj;
		}
	  
	  
	//Added By Aadesh Croma Rule For Dimension on 24-03-2023
		@Override
		public Result cromaDimensionRoundOff(String data) {
			 Result resultObj = new Result();
			try {
			
			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				resultObj.setResult(false);
				return resultObj;
			}
			else {
			 DecimalFormat decfor = new DecimalFormat("00.00"); 
			 String splitByChar=data.toLowerCase();	 
			 String dimension[]  = splitByChar.trim().split("x");	
		     String roundOffDimension="";
		     String dimensionData="";
		     for (String str : dimension) 
		     {	    	 
						double strTOdouble = Double.valueOf(str);
					    roundOffDimension=  decfor.format(strTOdouble);					  
					    dimensionData=dimensionData+roundOffDimension+" "+"x ";
		     }	   
		     dimensionData=dimensionData.trim();
		     if (dimensionData != null && dimensionData.length() > 0 && dimensionData.endsWith("x")) 
		     {

		    	  dimensionData = dimensionData.substring(0, dimensionData.length() - 1);
		     }
		     
		 	 resultObj.setResult(true);
		 	 resultObj.setValue(dimensionData);
		 	return resultObj;
			}}
			catch(Exception e){
				resultObj.setResult(false);
				 LOGGER.info("------------"+e);
			
			
			}
			return resultObj;
		}
		
	////////////////////////////BEauty Deployement Rule on 12-05-2023	
		//Added By Aadesh For Beauty Internal Rule Pointers on 09-02-2023
		  @Override
		  	public Result tatabeauty_freebieAsMrp(String data,String data2) {
		  		Result resultObj = new Result();
		  		//LOGGER.info("data = "+data+  "data2--"+data2);
		  			try {
		  				if (!data2.isEmpty() || !data2.equals("") || !data2.equalsIgnoreCase("n/a") && !data.isEmpty() || !data.equals("") ) 
		  				{		  		
		  					if (data2.equalsIgnoreCase("Non-Saleable")) 
		  					{
		  						//LOGGER.info(data2+"-----"+SKU);
		  						resultObj.setValue("0.1");
		  						resultObj.setResult(false);
		  						resultObj.setScore(1);
		  					}
		  					
		  				}
		  				
		  			} catch (Exception e) {
		  				resultObj.setScore(0);
		  				resultObj.setResult(true);
		  			}
		  		
		  		//LOGGER.info("resultObj = "+resultObj);
		  		return resultObj;
		  	}
		
		
		  @Override
		  	public Result tatabeauty_SizeEqualCombo_Product_No(String data,String data2) {
		  		Result resultObj = new Result();
		  	//	LOGGER.info("data = "+data+  "data2--"+data2);
		  			try {
		  				if (!data2.isEmpty() || !data2.equals("") || !data2.equalsIgnoreCase("n/a")) 
		  				{		  		
		  					if (data2.equalsIgnoreCase("Combo")) 
		  					{
		  						if(data.isEmpty() || data.equals("")) 
		  						{
		  							resultObj.setResult(false);
		  							return resultObj;
		  							
		  						}
		  						else
		  						{
		  							resultObj.setResult(true);
		  							return resultObj;
		  						}
		  							
		  					}
		  					else 
		  					{
				  				resultObj.setResult(true);
				  				return resultObj;
		  					}
		  					
		  				}
		  				
		  			} catch (Exception e) {
		  				resultObj.setScore(0);
		  				resultObj.setResult(false);
		  				return resultObj;
		  			}
		  		
		  		//LOGGER.info("resultObj = "+resultObj);
		  		return resultObj;
		  	}
		  
		  @Override
		  	public Result tatabeauty_highlight_freebie_if_mrp_01(String data,String data2) {
		  		Result resultObj = new Result();
		  		//LOGGER.info("data = "+data+  "data2--"+data2);
		  			try {
		  				if (!data2.isEmpty() || !data2.equals("") || !data2.equalsIgnoreCase("n/a")) 
		  				{		  		
		  					if (data2.equalsIgnoreCase("0.1")) 
		  					{
		  						resultObj.setResult(false);	
		  						return resultObj;
		  					}
		  					else 
		  					{
				  				resultObj.setResult(true);
				  				return resultObj;
		  					}
		  					
		  				}
		  				
		  			} catch (Exception e) {
		  				resultObj.setScore(0);
		  				resultObj.setResult(false);
		  				return resultObj;
		  			}
		  		
		  		//LOGGER.info("resultObj = "+resultObj);
		  		return resultObj;
		  	}
		  
		  @Override
			public Result tatabeauty_ProductNameAsProductTitle(String data, String data2) {
				//LOGGER.info("data = "+data+  "data2--"+data2);
				Result result = new Result();
				if (!data2.isEmpty() || !data2.equals("")) 
  				{
					result.setResult(true);
					result.setValue(data2);	
					return result;
  				}				
				return result;
			}
		  
		  
		  @Override
			public Result tata_checkFeatureDuplicateValue(String data) {
				Result resultObj = new Result();
				try {
					data = data.trim();
					System.out.println("data: "+data);
					if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
						//System.out.println("here");
						resultObj.setResult(true);
						resultObj.setScore(0);
						return resultObj;
					} else {
						String[] wordsArray = data.split("\\|");
						
						if (hasDuplicates(wordsArray)) {
							System.out.println("hasDuplicates(wordsArray)--"+hasDuplicates(wordsArray));
							resultObj.setResult(false);
							resultObj.setScore(1);
							return resultObj;
						} else {
							
							resultObj.setResult(true);
							resultObj.setScore(0);
							return resultObj;
						}
					}
				} catch (Exception e) {
					resultObj.setResult(true);
					resultObj.setScore(0);
					return resultObj;
				}
			}
		
			    public  boolean hasDuplicates(String[] stringArray) {
			        HashSet<String> set = new HashSet<>();
			        for (String element : stringArray) {
			            if (set.contains(element)) {
			                return true;
			            }
			            set.add(element);
			        }
			        return false;
			   
			    }
			    
			    
			    @Override
				public Result tatabeauty_checkGlobalIDValueForEAN(String data, String data2) {
					Result result = new Result();
					if (data2.equals("EAN")) {

						if(!data.isEmpty()) 
						{
							if (data.length() == 13) 
							{
								result.setResult(true);
							} 
							else 
							{
								result.setResult(false);
							}
						}
						else 
						{
							result.setResult(false);
						}

					} 

					else 
					{
						result.setResult(true);
					}

					return result;

				}
			    
			    @Override
				public Result tatabeauty_checkGlobalIDValueForUPC(String data, String data2) {
					Result result = new Result();
					if (data2.equals("UPC")) {

						if(!data.isEmpty()) 
						{
							if (data.length() < 13) 
							{
								result.setResult(false);
							} 
							else 
							{
								result.setResult(true);
							}
						}
						else 
						{
							result.setResult(false);
						}
						
					} 

					else 
					{
						result.setResult(true);
					}

					return result;

				}
			    
			    @Override
				public Result tatabeauty_checkGlobalIDValueForMPN(String data, String data2) {
					Result result = new Result();
					if (data2.equals("MPN")) {

						if(!data.isEmpty()) 
						{
							result.setResult(true);	
						}
						else 
						{
							result.setResult(false);
						}

					} 

					else 
					{
						result.setResult(true);
					}

					return result;

				}
			    
			    @Override
				public Result tatabeauty_checkGlobalIdEANUPCMPN(String data, String data2) {
					Result result = new Result();
					if (data2.equals("EAN")) {

						if (data.length() == 13) 
						{
							result.setResult(true);
						} 
						else 
						{
							result.setResult(false);
						}

					} 
					else if (data2.equals("UPC"))
					{
						if(!data.isEmpty()) 
						{
							if (data.length() < 13) 
							{
								result.setResult(false);
							} 
							else 
							{
								result.setResult(true);
							}
						}
					}
					else if (data2.equals("MPN"))
					{
						if(!data.isEmpty()) 
						{
							result.setResult(true);	
						}
						else 
						{
							result.setResult(false);
						}

					}

					
					else 
					{
						result.setResult(true);
					}

					return result;

				}
			    
			    @Override
				public Result checkMultiLOVinAttribute(String data, String attributeValue) {
					//LOGGER.info("data  = "+data+" attributeValue = "+attributeValue);
					Result result = new Result();
					if (data.equals("")) {
						return result;
					}
					String[] attrArray = data.split(",");
				    String[] lovArray = attributeValue.split(",");

				    for (String attr : attrArray) {
				        boolean found = false;
				        for (String lov : lovArray) {
				            if (attr.trim().toLowerCase().equals(lov.trim().toLowerCase())) {
				            	result.setResult(true);
				                found = true;
				                break;
				            }
				        }
				        if (!found) {
				        	result.setResult(false);
				            return result;
				        }
				    }

				    return result;
				}
}