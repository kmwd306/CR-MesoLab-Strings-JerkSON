package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    //to organize my Key/Value pairs
    private int exceptions = 0;
    private TreeMap<String, ArrayList<Item>> groceryMap;

    public ItemParser(){
        //initializing the TreeMap
        groceryMap = new TreeMap<String, ArrayList<Item>>();
    }

//splits a string of rawData by ## and puts it into an ArrayList called reponse
    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{


        if(findName(rawItem) == null || findPrice(rawItem) == null){
            throw new ItemParseException();
        }


        String name = findName(rawItem);
        Double price = Double.parseDouble(findPrice(rawItem));
        String type = findType(rawItem);
        String expirationDate = findExpirationDate(rawItem);

        Item item = new Item(name, price, type, expirationDate);
        return item;
    }

    private String findExpirationDate(String rawItem)throws ItemParseException {
        Pattern checkExpDateRegex = Pattern.compile("\\d\\/\\d+\\/\\d+");
        Matcher regexExpDateMatcher = checkExpDateRegex.matcher(rawItem);

        if(regexExpDateMatcher.find()){
            return regexExpDateMatcher.group();
        }
        return null;
    }

    private String findType(String rawItem)throws ItemParseException {
        Pattern checkTypeRegex = Pattern.compile("(?<=([Tt][Yy][Pp][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexTypeMatcher = checkTypeRegex.matcher(rawItem);

        if(regexTypeMatcher.find()){
            String fixtype = regexTypeMatcher.group().replace("\\d", "f");
            return fixtype.toLowerCase();
        }

        return null;
    }

    public String findName(String rawItem)throws ItemParseException{
        Pattern checkNameRegex = Pattern.compile("(?<=([Nn][Aa][Mm][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexNameMatcher = checkNameRegex.matcher(rawItem);

        if(regexNameMatcher.find()){
            String fixName = regexNameMatcher.group().replace("\\d", "o");
            return fixName.toLowerCase();
        }
        return null;
    }

    public String findPrice(String rawItem) throws NumberFormatException{
        Pattern checkPriceRegex = Pattern.compile("\\d\\.\\d*");
        Matcher regexPriceMatcher = checkPriceRegex.matcher(rawItem);

        if(regexPriceMatcher.find()){
            if(!regexPriceMatcher.group().equals("")){
                return regexPriceMatcher.group();
            }
        }
        return null;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }


    public Map<String, ArrayList<Item>> printGroceries(){
        return null;

    }


    //PseudoCode
    //I need to print the grocery list
    //I should be able to add to the grocery list
    //I need to be able to return pricesoh
    //I need to be able to count the exceptions
    //need a replace method for the C00kies
    //get key
    //get value
    //how to handle if the price field is empty
    //patterns & matches







}
