package io.zipcoder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {


    //to organize my Key/Value pairs
    public static int exceptions = 0;
    private HashMap<String, ArrayList<Item>> groceryMap;

    public ItemParser(){
        //initializing the TreeMap
        groceryMap = new HashMap<String, ArrayList<Item>>();
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

    public String findExpirationDate(String rawItem) {
        Pattern checkExpDateRegex = Pattern.compile("\\d\\/\\d+\\/\\d+");
        Matcher regexExpDateMatcher = checkExpDateRegex.matcher(rawItem);

        if(regexExpDateMatcher.find()){
            return regexExpDateMatcher.group();
        }
        return null;
    }

    public String findType(String rawItem){
        Pattern checkTypeRegex = Pattern.compile("(?<=([Tt][Yy][Pp][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexTypeMatcher = checkTypeRegex.matcher(rawItem);

        if(regexTypeMatcher.find()){
            String fixtype = regexTypeMatcher.group().replace("\\d", "f");
            return fixtype.toLowerCase();
        }

        return null;
    }

    public String findName(String rawItem){
        Pattern checkNameRegex = Pattern.compile("(?<=([Nn][Aa][Mm][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        Matcher regexNameMatcher = checkNameRegex.matcher(rawItem);

        if(regexNameMatcher.find()){
            String fixName = regexNameMatcher.group().replace("\\d", "o");
            return fixName.toLowerCase();
        }
        return null;
    }

    public String findPrice(String rawItem){
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




    public HashMap<String, ArrayList<Item>> getGroceryMap() throws Exception {

        Main main = new Main();

        ArrayList<String> items = parseRawDataIntoStringArray(main.readRawDataToString());


        for(String s: items){
            try {
                Item newItem = parseStringIntoItem(s);
                if (!groceryMap.containsKey(newItem.getName())){
                    ArrayList<Item> myItemArrayList = new ArrayList<Item>();
                    myItemArrayList.add(newItem);
                    groceryMap.put(newItem.getName(), myItemArrayList);
                } else {
                    groceryMap.get(newItem.getName()).add(newItem);
                    }
            } catch (ItemParseException e){
                exceptions++;
            }
        }
        return groceryMap;
    }


    public String printGroceries() throws Exception{
        groceryMap = getGroceryMap();

        StringBuilder sb = new StringBuilder();

        for(Map.Entry<String, ArrayList<Item>> namesAndItems : groceryMap.entrySet()){
            sb.append("\nname: ");
            sb.append(String.format("%8s",captitalizeFirstLetter(namesAndItems.getKey())));
            sb.append("\t\t\t\tseen: "+getOccurencesOfItems(namesAndItems.getValue())+" times\n");
            sb.append("==============="+"\t\t\t\t===============\n");
            String priceList = generatePriceList(namesAndItems);
            sb.append(priceList);
            sb.append("---------------"+"\t\t\t\t---------------\n");
            }

            sb.append("\nErrors\t\t\t\t\t\tseen: "+exceptions+" times\n");

            return sb.toString();


    }


    public String generatePriceList(Map.Entry<String, ArrayList<Item>> input) {
        String priceList = "";
        ArrayList<Double> nonDupPrices = getDifferentPrices(input);
        for(int i=0;i<nonDupPrices.size();i++){
            priceList+="Price";
            priceList+=(String.format("%10s",nonDupPrices.get(i)));
            priceList+=("\t\t\t\tseen: "+ getPriceOccurrences(input.getValue(),nonDupPrices.get(i))+
                    " times\n");
        }
        return priceList;

    }

    public String captitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }


    public int getOccurencesOfItems(ArrayList list) {
        return list.size();
    }





    private ArrayList<Double> getDifferentPrices(Map.Entry<String, ArrayList<Item>> items) {

        ArrayList<Double> differentPrices = new ArrayList<Double>();

        for(int i =0; i<items.getValue().size(); i++){
            if(!differentPrices.contains(items.getValue().get(i).getPrice())){
                differentPrices.add(items.getValue().get(i).getPrice());
            }
        }

        return differentPrices;
    }

    private int getPriceOccurrences(ArrayList<Item> value, Double aDouble) {
        int price = 0;

        for(int i = 0; i<value.size(); i++){
            if(value.get(i).getPrice().equals(aDouble)){
                price++;
            }
        }
        return price;
    }


    //PseudoCode
    //I need to print the grocery list
    //I need to be able to return prices - done
    //I need to be able to count the exceptions - done
    //need a replace method for the C00kies - done
    //get key - done
    //get value - done
    //how to handle if the price field is empty - done
    //patterns & matches - done







}
