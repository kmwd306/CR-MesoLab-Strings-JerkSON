package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class ItemParser {
    //to organize my Key/Value pairs
    private TreeMap<String, ArrayList<Item>> groceryMap;

    public ItemParser(){
        //initializing the TreeMap
        groceryMap = new TreeMap<String, ArrayList<Item>>();
    }


    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{
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








}
