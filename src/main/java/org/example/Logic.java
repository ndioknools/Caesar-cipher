package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {
    private Map<Character, Float> frequencies = new HashMap<Character, Float>();


    public Logic() {


        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List<Distribution> distributionList = objectMapper.readValue(
                    new File("src/main/resources/distributions.json"), new TypeReference<List<Distribution>>() {});

            for (Distribution distribution : distributionList) {
                frequencies.put(distribution.getLetter().charAt(0), distribution.getFrequency()/100);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public Map<Character,Float> computeActualFrequencies(String message) {

        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        Map<Character, Float> actualFrequencies = new HashMap<>();
        message = message.toLowerCase().trim();

        char character =97;

        int numberOfLetters = 0;

        for (char current : message.toCharArray()) {
            if (Character.isLetter(current)) {
                numberOfLetters++;
            }
        }

        for (character=97;character<123;character++) {
            int count = 0;
            for (char x : message.toCharArray()) {
                if (x==character){
                    count++;
                }
                float freq = (float) count /numberOfLetters;
                actualFrequencies.put(character,freq);
            }
        }
        return actualFrequencies;

    }

    public void getActualFreq(Map<Character,Float> actualFrequencies) {
        List<Map.Entry<Character, Float>> list = new ArrayList<>(actualFrequencies.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    }

    public float computeChi(String message){
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        Map<Character, Float> actualFrequencies = computeActualFrequencies(message);

        float chi = 0;

        for (char c=97;c<123;c++){
            chi += (float)(Math.pow(actualFrequencies.get(c)-frequencies.get(c),2))/frequencies.get(c);
        }
        return chi;
    }

    public String shift(String message, Integer positions) {
        if(message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if(positions<0) {
            throw new IllegalArgumentException("Positions cannot be negative");
        }
        int englishLetters = 26;
        char [] charMessage = message.toCharArray();
        for (int i=0; i<charMessage.length; i++ ) {
            if(charMessage[i]>96 && charMessage[i]<123){
                charMessage[i]= (char) (((charMessage[i]-97+positions)%englishLetters) + 97 );
            }
            else if(charMessage[i]>64 && charMessage[i]<91){
                charMessage[i]= (char) (((charMessage[i]-65+positions)%englishLetters) + 65 );
            }
        }
        return new String(charMessage);
    }


    public String decipherAlgorithmChiComparison(String message){
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        Map<Integer,Float> shifts = new HashMap<>();
        String tempMessage = message;
        for (int i=0; i<26; i++){
            shifts.put(i,computeChi(tempMessage));
            tempMessage=shift(tempMessage,1);
        }
        int key = shifts.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
        if (key==-1) throw new IllegalArgumentException("Key not found");
        return "Key: "+Integer.toString(26-key)+"\n"+shift(message,key);

    }

    public String decipherAlgorithmFrequencyAnalysis(String message) {
        //this is the algorithm to analyze the letter frequency and find a decipher key based on some assumptions about them
        //the main idea is to select N top letters from the encoded text
        // and check if K<=N top letters from english alphabet are all found in any of 25 possible shifts of N encoded letters
        //(in this implementation N=6 and K=3 were figured out empirically)
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        Map<Character,Float> frequencies = computeActualFrequencies(message);



        String lookupString =frequencies.entrySet().stream()
                .sorted(Map.Entry.<Character, Float>comparingByValue().reversed()) // Sort descendingly
                .limit(6) // Select top 6 = N
                .map(entry -> entry.getKey().toString()) // Convert keys to string
                .collect(Collectors.joining());

        int key =-1;

        for (int i=0; i<26; i++){
            //check if top K=3 letters(i.e. 'e','t','a') are contained
            if (lookupString.contains("e") && lookupString.contains("t") && lookupString.contains("a")){
                key=i;
                break;
            }
            lookupString=shift(lookupString,1);
        }
        if (key==-1) throw new IllegalArgumentException("Key not found");
        return "Key: "+Integer.toString(26-key)+"\n"+shift(message,key);
    }
}
