package com.contexio.rulebase.rule.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.contexio.rulebase.model.Result;
import com.contexio.rulebase.rule.service.TextFormatRuleService;

@Service
public class TextFormatRuleServiceImpl implements TextFormatRuleService {

	private static final long serialVersionUID = 1L;
	boolean result;
	List list = new ArrayList<>();

	@Override
	public Result isText(String data) {
		Result resultObj = new Result();
		if (data.matches("^[a-zA-Z]*$")||data.isEmpty()||data.equals("")) {
			result = true;
			resultObj.setResult(result);
			resultObj.setScore(1);
			return resultObj;
		} else {
			result = false;
			resultObj.setResult(result);
			resultObj.setScore(0);
			return resultObj;
		}
	}

	/*@Override
	public Result isNumber(String data) {
		Result resultObj = new Result();
		if (data.matches("[0-9]+")||data.isEmpty()||data.equals("")) {
			result = true;
			resultObj.setResult(true);
			resultObj.setScore(1);
		} else {
			result = false;
			resultObj.setResult(false);
			resultObj.setScore(0);
		}

		return resultObj;
		try {
			double num = Double.parseDouble(data);
			result = true;

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
	}*/
	
	@Override
    public Result isNumber(String data) {
		Result resultObj = new Result();
        if(data.matches("^([+-]?\\d*\\.?\\d*)$")||data.isEmpty()) {
        	resultObj.setResult(true);
        	resultObj.setScore(1);
        }else {
        	resultObj.setResult(false);
        	resultObj.setScore(0);
        }
        return resultObj;
    
    }

	@Override
	public Result isString(String data) {
		try {
			double num = Double.parseDouble(data);
			result = false;
		} catch (Exception e) {
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
	public Result isAlphaNumeric(String data) {
		Result resultObj = new Result();
		boolean isValid = data != null && data.matches("^[a-zA-Z0-9]+$");
		resultObj.setResult(isValid);
		resultObj.setScore(isValid ? 1 : 0);
		return resultObj;
	}

	@Override
	public Result isEmail(String data) {
		try {
			final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(data);
			boolean correct = matcher.find();

			if (correct == true) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			// .printStackTrace();
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
	public Result isDateFormat(String data) {
		String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		if (matcher.matches()) {
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
	public Result isProperCase(String data) {
		String strResult = "";
		String[] str = data.split(" ");

		for (int i = 0; i < str.length; i++) {
			if (!str[i].equals("")) {
				if ((Character.isUpperCase(str[i].charAt(0)) == true)) {
					String substr = str[i].substring(1);
					if (strResult.equals("FAIL")) {
						result = false;
						break;
					}

					for (int j = 0; j < substr.length(); j++) {
						// System.out.println("substr.charAt("+j+") = "+substr.charAt(j));
						if (Character.isLowerCase(substr.charAt(j))) {
							strResult = "PASS";
							result = true;
						} else {
							strResult = "FAIL";
							result = false;
							break;
						}
					}
				} else {
					strResult = "FAIL";
					result = false;
					break;
				}
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
	public Result containsSpecialCharacters(String data) {
		Pattern regex = Pattern.compile("[_~`!@#$%^&*()-+={}\\[|\\:;\"\'<>,.?/]");
		Matcher matcher = regex.matcher(data);
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
	public Result containsTwoDecimalPoints(String data) {
		try {
			String points = data.split("\\.")[1];
			if (points.length() == 2) {
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
	public Result containsThreeDecimalPoints(String data) {
		try {
			String points = data.split("\\.")[1];
			if (points.length() == 3) {
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
	public Result isUpperCase(String data) {
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			if (c >= 97 && c <= 122) {
				Result resultObj = new Result();
				resultObj.setResult(false);
				resultObj.setScore(0);

				return resultObj;
			}
		}
		Result resultObj = new Result();
		resultObj.setResult(true);
		resultObj.setScore(1);
		return resultObj;

	}

	@Override
	public Result isLowerCase(String data) {
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			if (c >= 65 && c <= 90) {
				Result resultObj = new Result();
				resultObj.setResult(false);
				resultObj.setScore(0);
				return resultObj;
			}
		}
		Result resultObj = new Result();
		resultObj.setResult(true);
		resultObj.setScore(1);
		return resultObj;
	}

	@Override
	public Result checkDimensions(String data) {
		int count = 0;
		char regex = '*';
		try {
			for (int i = 0; i < data.length(); i++) {
				char ch = data.charAt(i);
				if (ch == regex) {
					count++;
				}

			}
			if (count == 2) {
				result = true;
			} else {
				result = false;
			}
			//System.out.println(count);

		} catch (Exception e) {
			// result = "FAIL: NO NUMERIC VALUE";
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
	public Result checkDimensionsAndWeight(String data) {
		try {
			double num = Double.parseDouble(data);
			char[] ch = data.toCharArray();
			if (data.indexOf(".") != -1 && ch[data.length() - 1] != 0) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			// result = "FAIL: NO NUMERIC VALUE";
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
	public Result checkFloatAttributes(String data) {
		try {

			float num = Float.parseFloat(data);
			// double n= Number(num) == num && num % 1 != 0;
			result = true;
		} catch (Exception e) {
			// result = "FAIL: NO NUMERIC VALUE";
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
	public Result checkStringAttributes(String data) {
		try {
			@SuppressWarnings("unused")
			double num = Double.parseDouble(data);

			result = false;
		} catch (Exception e) {
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
	public Result checkMultpleValueSepretedByPipe(String data) {
		try {
			if (data.contains("|")) {
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
	public Result checkMandatoryColumn(String data) {
		try {

			// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in
			// MEthod%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			// Thread.sleep(10000);

			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				result = false;
			} else {
				result = true;
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
	public Result checkDoubleSpace(String data) {

		final Pattern regex = Pattern.compile("\\s{2,}");
		Matcher matcher = regex.matcher(data);
		boolean correct = matcher.find();
		if (correct == true) {
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
	public Result checkSpecialChar(String data) {

		if (data == null || data.trim().isEmpty()) {
			//System.out.println("format of string is Incorrect");
			// result=false;
			Result resultObj = new Result();
			resultObj.setResult(false);
			resultObj.setScore(0);

			return resultObj;
		}

		Pattern pattern = Pattern.compile("[^A-Za-z0-9-.]");
		Matcher matcher = pattern.matcher(data);

		boolean b = matcher.find();
		if (b == true) {
			//System.out.println("There is a special character in my string:- " + data);
			result = true;
		} else if (b == false) {
			//System.out.println("There is no special character in my String :-  " + data);
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
	public Result lengthCheckUpcMpnEan(String data) {

		int length = data.length();
		if (length == 13) {
			result = true;
		} else if (length == 12) {
			result = true;
		} else {
			result = false;
		}
		// System.out.println("Rules upcLengthCheck() : "+data + "Result : "+result);
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
	public Result mandatoryFillCheck(String data) {
		// System.out.println("mandatoryFillCheck");

		if (data.isEmpty()) {
			result = false;
		} else if (data.contains(data)) {
			result = true;
		}
		System.out.println("mandatoryFillCheck() : " + data + " Result : " + result);
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
	public Result checkDate(String data) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/mm/yyyy");
		sdfrmt.setLenient(false);

		try {
			Date javaDate = sdfrmt.parse(data);
			//System.out.println(data + " is valid date format");
			result = true;
		}
		/* Date format is invalid */
		catch (ParseException e) {
			//System.out.println(data + " is Invalid Date format");
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
	public Result checkPackQuantity(String data) {
		if (data.contains("1")) {
			//System.out.println("Pack quantity is should be blank if " + data);
			result = false;
		} else {
			//System.out.println("Pack quantity is more than 1" + data);
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
	public Result checkEANSKUCodeDuplication(String data) {

		if (!list.contains(data)) {
			list.add(data);
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
	public Result checkUpdateDelete(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkCollectionDiscription(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkPublicGender(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkPatternType(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkOccasiontypes(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkNeckStyle(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkSleveType(String data) {
		// TODO Auto-generated method stub
		Result resultObj = new Result();
		resultObj.setResult(false);
		resultObj.setScore(0);

		return resultObj;
	}

	@Override
	public Result checkFabricComposition(String data) {
		try {
			String[] firstPart = data.split("%"); // returns an array with the 2 parts
			String firstValue = firstPart[0];
			//System.out.println(firstValue);

			String[] secondPart = data.split(",");
			String secondData = secondPart[1];
			//System.out.println("secondData" + secondData);

			String[] secondDataValue = secondData.split("%");
			String secondValue = secondDataValue[0];
			//System.out.println(secondValue);
			int firstParam = Integer.parseInt(firstValue);
			int SecondParam = Integer.parseInt(secondValue);

			int finalResult = firstParam + SecondParam;

			if (finalResult == 100) {
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
	public Result checkVideoOrGifPresent(String data) {

		Result result = new Result();
		String[] extArray = { "gif", "wemb", "mpg", "mpeg", "mp4", "wmv", "mov", "qt", "flv", "avi", "3gp", "3g2" };
		List<Object> extensionList = Arrays.asList(extArray);
		String[] array = data.split(",");
		try {
			for (int i = 0; i < array.length; i++) {
				String currentData = array[i];
				String fileExtension = currentData.split("\\.")[1];
				if (extensionList.contains(fileExtension)) {
					result.setResult(true);
					result.setScore(1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
		}
		return result;
	}

	@Override
	public Result doesNotContainsSpecialCharacters(String data) {
		Pattern regex = Pattern.compile("[_~`!@#$%^&*()-+={}\\[|\\:;\"\'<>,.?/]");
		Matcher matcher = regex.matcher(data);
		if (matcher.find()) {
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
	public Result doesNotContainsSpecificSpecialCharacters(String data, String specialCharacters) {

		Result resultObj = new Result();
		String characterArray[] = specialCharacters.split(",");
		for (int i = 0; i < characterArray.length; i++) {
			if (data.contains(characterArray[i])) {
				return resultObj;
			}
		}
		resultObj.setResult(true);
		resultObj.setScore(1);
		return resultObj;

	}

	@Override
	public Result checkEndDate(String data, String dateType, String dateValue) {
		// System.out.println("data::"+data+"dateType"+dateType+"dateValue"+dateValue);
		Result resultObj = new Result();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		// SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		try {
			date = format1.parse(data);

			String cellDate = format1.format(date);
			Calendar cal = Calendar.getInstance();
			Date nyear = null;

			if (dateType.equalsIgnoreCase("YEAR")) {
				cal.add(Calendar.YEAR, Integer.valueOf(dateValue));
				nyear = cal.getTime();
			} else if (dateType.equalsIgnoreCase("MONTH")) {
				cal.add(Calendar.MONTH, Integer.valueOf(dateValue));
				nyear = cal.getTime();
			} else if (dateType.equalsIgnoreCase("Days") || dateType.equalsIgnoreCase("Day")) {
				cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(dateValue));
				nyear = cal.getTime();
			}

			String endDate = format1.format(nyear);
			if (cellDate.equals(endDate)) {
				resultObj.setResult(true);
				resultObj.setScore(1);
				return resultObj;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			resultObj.setResult(false);
			resultObj.setScore(0);
			return resultObj;
		}
		return resultObj;
	}

	@Override
	public Result checkImagePresent(String data) {

		Result result = new Result();
		String[] extArray = { "gif", "img", "jpeg", "png", "jpg" };
		List<Object> extensionList = Arrays.asList(extArray);
		String[] array = data.split(",");
		try {
			for (int i = 0; i < array.length; i++) {
				String currentData = array[i];
				String fileExtension = currentData.split("\\.")[1];
				if (extensionList.contains(fileExtension)) {
					result.setResult(true);
					result.setScore(1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
		}
		return result;
	}

	@Override
	public Result checkImageTypeSpecific(String data, String type) {

		Result result = new Result();
		// String[] extArray =
		// {"gif","wemb","mpg","mpeg","mp4","wmv","mov","qt","flv","avi","3gp","3g2"};
		String[] extArray = type.split(",");
		List<Object> extensionList = Arrays.asList(extArray);
		String[] array = data.split(",");
		double count = 0;
		try {
			for (int i = 0; i < array.length; i++) {
				String currentData = array[i];
				String fileExtension = currentData.split("\\.")[1];
				if (currentData.contains("." + type.toLowerCase())) {
					// if(extensionList.contains(fileExtension)) {
					result.setResult(true);
					// System.out.println("checkImageTypeSpecific fileExtension"+fileExtension);

					count++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setResult(false);
			result.setScore(0);
		}
		// System.out.println("checkImageTypeSpecific count "+count + " : array.length :
		// "+array.length );
		Double score = count / array.length;
		// System.out.println("checkImageTypeSpecific "+score);
		if (count != array.length) {
			result.setResult(false);
		} else {
			result.setResult(true);
		}
		result.setScore(score);
		return result;
	}

	@Override
	public Result checkMandatoryDesc(String data) {
		try {

			// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in
			// MEthod%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			// Thread.sleep(10000);

			if (data.isEmpty() == true || data.equals("")) {
				result = false;
			} else {
				result = true;
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
	public Result checkMandatoryImage(String data) {
		try {

			// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in
			// MEthod%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			// Thread.sleep(10000);

			if (data.isEmpty() == true || data.equals("")) {
				result = false;
			} else {
				result = true;
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
	public Result checkMandatoryWarrenty(String data) {
		try {

			// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in
			// MEthod%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			// Thread.sleep(10000);

			if (data.isEmpty() == true || data.equals("") || data.equalsIgnoreCase("n/a")) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}

		Result resultObj = new Result();
		resultObj.setResult(result);
		
		return resultObj;

	}

	@Override
	public Result checkNotSameCellValue(String data, String attributeName) {
		Result result = new Result();
		if (!data.contains(attributeName)) {
			result.setResult(true);
			result.setScore(1);
		}
		return result;
	}

	@Override
	public Result checkBooleanValue(String data) {
		try {
			if (data.contains("TRUE") || data.contains("FALSE")) {
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
	public Result jcp_checkPriceTypeValue(String data) {
		try {
			if (data.equalsIgnoreCase("Regular") || data.equalsIgnoreCase("Sale")
					|| data.equalsIgnoreCase("Clearance")) {
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
	public Result jcp_checkDateFormat(String data) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			// To make strict date format validation
			formatter.setLenient(false);
			Date parsedDate = null;
			try {
				parsedDate = formatter.parse(data);
				// System.out.println("formatter: "+formatter.toPattern());

				if (data.equals(parsedDate)) {
					result = true;
				} else {
					result = false;
				}

			} catch (ParseException e) {
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

	public static void main(String[] args) {
		TextFormatRuleServiceImpl obj = new TextFormatRuleServiceImpl();
		Boolean res;
		Result res2;
		String data = " ";
		//res2 = obj.isNumber(data);
		res2 = obj.checkMandatoryWarrenty("");
		 //res2 = obj.doesNotContainsSpecificSpecialCharacters("abc/","@,/");
		System.out.println("checkMandatoryWarrenty res: " + res2);
	}

	/* Added by Anjali Singh 09-09-2021 */
	@Override
	public Result isMandatoryCheck(String data) {
		try {
			result = true;
		} catch (Exception e) {
			result = false;
		}
		// System.out.println(result);
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
	public Result isNumberCheck(String data) {
		try {

			result = true;
		} catch (Exception e) {
			result = false;
		}
		//System.out.println(result);
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
	public Result isStringCheck(String data) {
		try {

			result = true;
		} catch (Exception e) {
			result = false;
		}
		//System.out.println(result);
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
	public Result Annotation(String data, String value) {
		result = true;
		Result resultObj = new Result();
		resultObj.setResult(result);
		resultObj.setScore(1);

		return resultObj;

	}

	@Override
	public boolean checkDynamicvalueinAttribute(String data, String attributeValue) {
		Result res = new Result();

		result = false;
		String[] keyWordArray = attributeValue.split(",");
		data.trim();
		for (String dataWord : keyWordArray) {
			if (data.contains(dataWord.trim())) {
				res.setResult(false);
				return false;

			}
			// res.setResult(true);
		}
		res.setResult(true);
		return true;

	}
	
	//Added By Aadesh For Beauty Check Special Character on 01-10-2022
	@Override
	public Result tataBeauty_checkSpecialCharacters(String data) {
		Pattern regex = Pattern.compile("[_~`!@#$%^&*()-+={}\\[|\\:;\"\'<>,.?/]");
		Result resultObj = new Result();
		Matcher matcher = regex.matcher(data);
		if (matcher.find()) {
			result = false;
			resultObj.setResult(result);
			resultObj.setScore(0);
		} else {
			result = true;
			resultObj.setResult(result);
			resultObj.setScore(1);
			
		}
		return resultObj;
	}
	
	
	
	@Override
	public Result tataBeauty_MakeUp_Cat_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if(data2.isEmpty() == false || !data2.equals("") ) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Makeup")) 
			{
					if (data.isEmpty() == true || data.equals("")) 
					{
						result = false;
					} 
					else 
					{
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	@Override
	public Result tataBeauty_Hair_Cat_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if( data2.isEmpty() == false || !data2.equals("")) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Hair") ) 
			{
					if (data.isEmpty() == true || data.equals("")) {
						result = false;
					} else {
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	
	@Override
	public Result tataBeauty_Bath_Cat_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if( data2.isEmpty() == false || !data2.equals("")) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Bath") ) 
			{
					if (data.isEmpty() == true || data.equals("")) {
						result = false;
					} else {
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;
	}

	@Override
	public Result tataBeauty_Skin_Care_Cat_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if( data2.isEmpty() == false || !data2.equals("")) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Skin Care") ) 
			{
					if (data.isEmpty() == true || data.equals("")) {
						result = false;
					} else {
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	@Override
	public Result tataBeauty_MensGroom_Cat_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if( data2.isEmpty() == false || !data2.equals("")) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Mens Grooming") ) 
			{
					if (data.isEmpty() == true || data.equals("")) {
						result = false;
					} else {
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	
	@Override
	public Result tataBeauty_FragPerf_NonLov_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if( data2.isEmpty() == false || !data2.equals("")) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Fragrances & Perfumes") ) 
			{
					if (data.isEmpty() == true || data.equals("")) {
						result = false;
					} else {
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	@Override
	public Result tataBeauty_FragPerf_Cat_checkMandatoryColumn(String data,String data2) {
		Result resultObj = new Result();
		if( data2.isEmpty() == false || !data2.equals("")) 
		{
			//System.out.println("data2: "+data2);
			if(data2.equals("Fragrances & Perfumes") ) 
			{
					if (data.isEmpty() == true || data.equals("")) {
						result = false;
					} else {
						result = true;
					}
			}
			else 
			{
				result = true;
			}
			resultObj.setResult(result);
		}
		
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	@Override
	public Result tataBeauty_Makeup_Color_ShadeName_Equal(String data,String data2,String data3) {
		Result resultObj = new Result();		
		System.out.println("data: "+data);
		System.out.println("data2: "+data2);
		System.out.println("data3: "+data3);
		if(!data2.equals("Makeup")) 
		{
			
				if (!data.equals(data3)) 
				{
					result = false;
				} 
				else 
				{
					result = true;
				}			
			resultObj.setResult(result);
		}
		else 
		{
			result = true;	
			resultObj.setResult(result);
			return resultObj;
		}
		
		return resultObj;

	}
	
	
	//Added by Abhishek on 08_05_2023 for spellcheck
		@Override
		public Result tataBeauty_SpellCheck_Platform_Refer_LOV_List(String data) {
			Result resultObj = new Result();
			String[] Validwords = {"beauty", "marketplace", "luxury"};
			String myword = Arrays.toString(Validwords);
			if(!data.isEmpty() || !data.contains("")) {
				data = data.toLowerCase();
				String [] splitSentence = data.split(",");
				
				//System.out.println("This is splitsentence "+Arrays.toString(splitSentence));
				int index = 0;
				String myname = "";
				for(String word : splitSentence)
				{
					if(myword.contains(word))
					{
						resultObj.setResult(true);
					}
					else 
					{
						resultObj.setResult(false);
						break;
					}
					
					index ++;
				}
				
			}
			else {
				resultObj.setResult(true);
			}
			
			return resultObj;
		}

		
		// Added by Abhishek on 05_05_2023 fro cecking cm unit
		@Override
		public Result tataBeauty_LEN_WID_WEI_Check_CM(String data, String unit) {
			Result resultObj = new Result();
			String regex = "(\\d+)(\\D+)";
			String stringpart = "";
			String numberpart = "";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(data);
			while(matcher.find()) {
				numberpart = matcher.group(1);
				stringpart = matcher.group(2);
				stringpart = stringpart.trim();
				
			}
			String [] invalidValues = unit.split(",");
			for(int i=0;i<invalidValues.length;i++) {
				if(stringpart !="") {
					if(invalidValues[i].equals(stringpart)) {
						resultObj.setResult(false);
						break;
					}
					else {
						resultObj.setResult(true);
					}
				}
				else {
					if(data.contains(invalidValues[i])) {
						resultObj.setResult(false);
						break;
					}
					else {
						resultObj.setResult(true);
					}
				}
				
			}
			return resultObj;
		}


		//Added by Abhishek on 05_05_2023 for checking gm unit
		@Override
		public Result tataBeauty_LEN_WID_WEI_Check_GM(String data, String unit) {
			Result resultObj = new Result();
			String regex = "(\\d+)(\\D+)";
			String stringpart = "";
			String numberpart = "";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(data);
			while(matcher.find()) {
				numberpart = matcher.group(1);
				stringpart = matcher.group(2);
				stringpart = stringpart.trim();
				
			}
			String[] invalidValues = unit.split(",");
			//System.out.println(Arrays.toString(invalidValues));
			for(int i=0;i<invalidValues.length;i++) {
				if(stringpart !="") {
					if(invalidValues[i].equals(stringpart)) {
						resultObj.setResult(false);
						break;
					}
					else {
						resultObj.setResult(true);
					}
				}
				else {
					if(data.contains(invalidValues[i])) {
						resultObj.setResult(false);
						break;
					}
					else {
						resultObj.setResult(true);
					}
				}
				
			}
			//data = data.toLowerCase();
			return resultObj;
		}

	/*	@Override
		public Result tataBeauty__SPFCheck_titledecs(String data,String data2,String data3) {
			Result resultObj = new Result();
			if( data2.isEmpty() == false || !data2.equals("")) 
			{
				//System.out.println("data2: "+data2);
				if(data2.equals("Fragrances & Perfumes") ) 
				{
						if (data.isEmpty() == true || data.equals("")) {
							result = false;
						} else {
							result = true;
						}
				}
				else 
				{
					result = true;
				}
				resultObj.setResult(result);
			}
			
			else 
			{
				result = true;	
				resultObj.setResult(result);
				return resultObj;
			}
			
			return resultObj;

		}*/
	
		
}
