package com.stefancouture.programmerdevtools;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayConverter extends AppCompatActivity {
    public final static String MODULE = "DisplayConverter";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText editText;
        final RadioButton binary_Left;
        final RadioButton decimal_Left;
        final RadioButton hexadecimal_Left;
        String input = "";
        String buildNumber;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);

        //get versionNumber
        VersionNumber versionNumber = new VersionNumber();
        buildNumber = versionNumber.getVersionNumber();

        //display on converter page
        TextView converterTxtView = (TextView) findViewById(R.id.converterVersion);
        converterTxtView.setText(converterTxtView.getText() + " " + buildNumber);

        editText = (EditText)findViewById(R.id.message);
        input = editText.getText().toString();

        binary_Left = (RadioButton) findViewById(R.id.binary_left);
        hexadecimal_Left = (RadioButton) findViewById(R.id.hexadecimal_left);
        decimal_Left = (RadioButton) findViewById(R.id.decimal_left);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString().replaceAll("\\s+","");
                boolean hasSpecialCharacters;
                boolean hasOnly0and1;

                if(text.length() == 0){
                    editText.setError("Field cannot be left blank");
                }
                else if(binary_Left.isChecked()){
                    hasSpecialCharacters = hasSpecialChars(text);

                    if(hasSpecialChars(text) == true)
                        editText.setError("Field cannot contain special characters");

                    hasOnly0and1 = hasOnly0and1s(text);

                    if(hasSpecialCharacters == false && hasOnly0and1 == true)
                        editText.setError("Number must consist of 0 and 1's");
                }
                else if(decimal_Left.isChecked()){
                    Pattern nums = Pattern.compile("[^0-9-]");
                    Matcher m = nums.matcher(text);
                    Boolean hasNotJustNums = m.find();

                    text = text.replaceAll("//s+", "");

                    if(hasNotJustNums == true || text.lastIndexOf('-') > 0){
                        editText.setError("Field can only contain digits");
                    }
                    else if(text.indexOf('-') == 0 && text.length() == 1){
                        editText.setError("Field must contain a digit");
                    }

                }
                else if(hexadecimal_Left.isChecked()){
                    hasSpecialCharacters = hasSpecialChars(text);

                    if(hasSpecialChars(text) == true)
                        editText.setError("Field cannot contain special characters");
                }//end else if

            }//end afterTextChanged
        });
    }//end onCreate

    public Boolean hasSpecialChars(String text){
        Pattern specialChars = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = specialChars.matcher(text);
        Boolean hasSpecialChars = m.find();
        return hasSpecialChars;
    }//end hasSpecialChars

    public Boolean hasOnly0and1s(String text){
        Pattern only0or1 = Pattern.compile("[^0-1]");
        Matcher m2 = only0or1.matcher(text);
        Boolean hasOnly0or1 = m2.find();
        return hasOnly0or1;
    }//end hasOnly0and1s

    public void setVisible(View view){
        TextView binText = (TextView) findViewById(R.id.numBits);
        binText.setVisibility(View.VISIBLE);

        findViewById(R.id.checkBox8).setVisibility(View.VISIBLE);
        findViewById(R.id.checkBox16).setVisibility(View.VISIBLE);
        findViewById(R.id.checkBox32).setVisibility(View.VISIBLE);
        findViewById(R.id.checkBox64).setVisibility(View.VISIBLE);
    }

    public void hide(View view){
        TextView binText = (TextView) findViewById(R.id.numBits);
        binText.setVisibility(View.GONE);

        findViewById(R.id.checkBox8).setVisibility(View.GONE);
        findViewById(R.id.checkBox16).setVisibility(View.GONE);
        findViewById(R.id.checkBox32).setVisibility(View.GONE);
        findViewById(R.id.checkBox64).setVisibility(View.GONE);

    }

    public void convert(View view){
        String methodName = "convert";
        String answer = ""; //will contain the answer of the conversion
        long num; //will hold the value the user entered
        String binary = ""; //will hold binary string
        int numOfBits = 0; //will hold the number of bits the user wants to have for the binary number

        //errors
        String error; //will contain the error message
        String oneCheckmark = "Select a checkbox";
        String tooMuchCheckmarks = "Select only 1 checkbox";
        String sameRadioButtons = "Cannot convert from and to itself";
        Log.d(MODULE + "." + methodName, " | Run" );

        EditText editText = (EditText) findViewById(R.id.message);
        String input = editText.getText().toString().toUpperCase();

        error = editText.getError() + " ";

        if(error != null && (error.equals(oneCheckmark) || error.equals(tooMuchCheckmarks)
        || error.equals(sameRadioButtons))) {
            editText.setError(null);
        }

        if(input.replaceAll("\\s+","").length() == 0){ //if the text field is empty
            editText.setError("Field cannot be left blank");
        }
        else if(editText.getError() != null){
            answer = "";
        }
        else if(editText.getError() == null){ //if no errors
            //left side radio buttons
            RadioButton binary_Left = (RadioButton) findViewById(R.id.binary_left);
            RadioButton decimal_Left = (RadioButton) findViewById(R.id.decimal_left);
            RadioButton hexadecimal_Left = (RadioButton) findViewById(R.id.hexadecimal_left);

            //right side radio buttons
            RadioButton binary_Right = (RadioButton) findViewById(R.id.binary_right);
            RadioButton decimal_Right = (RadioButton) findViewById(R.id.decimal_right);
            RadioButton hexadecimal_Right = (RadioButton) findViewById(R.id.hexadecimal_right);

            answer = input;
            numOfBits = determineNumOfBits();

            if(binary_Left.isChecked() && binary_Right.isChecked()){
                editText.setError("Cannot convert from and to itself");
                answer = "";
            }
            else if(decimal_Left.isChecked() && decimal_Right.isChecked()){
                editText.setError("Cannot convert from and to itself");
                answer = "";
            }
            else if(hexadecimal_Left.isChecked() && hexadecimal_Right.isChecked()){
                editText.setError("Cannot convert from and to itself");
                answer = "";
            }
            else if (binary_Left.isChecked() && decimal_Right.isChecked()) {
                answer = Long.toString((convertBinToDec(input, true)));
            }
            else if (binary_Left.isChecked() && hexadecimal_Right.isChecked()) {
                answer = convertBinToHex(input);
            }
            else if (decimal_Left.isChecked() && binary_Right.isChecked()) {
                num = Long.parseLong(input); //the long version of what the user entered

                if(countNumOfCheckBoxes() == 1) {
                    if (num != 0) {
                        answer = convertDecToBin(num);
                        answer = signExtend(answer, numOfBits);
                    } else {
                        answer = Long.toString(num);
                        answer = signExtend(answer, numOfBits);
                    }
                }//end 1 checkbox selected

                else if(countNumOfCheckBoxes() == 0){
                    editText.setError("Select a checkbox");
                }//end else if

                else if(countNumOfCheckBoxes() > 1){
                    editText.setError("Select only 1 checkbox");
                }//end else if

            }//end else if

            else if (decimal_Left.isChecked() && hexadecimal_Right.isChecked()) {
                num = Long.parseLong(input); //the long version of what the user entered
                if (num != 0) {
                    binary = convertDecToBin(num);
                    binary = binary.replaceAll("\\s+", "");
                } else {
                    binary = "0000";
                }
                answer = convertBinToHex(binary);
            }//end else if

            else if (hexadecimal_Left.isChecked() && binary_Right.isChecked()) {
                if(countNumOfCheckBoxes() == 1) {
                    answer = convertHexToBin(input);
                    answer = signExtend(answer, numOfBits);
                }//end if

                else if(countNumOfCheckBoxes() == 0){
                    editText.setError("Select a checkbox");
                }//end else if

                else if(countNumOfCheckBoxes() > 1){
                    editText.setError("Select only 1 checkbox");
                }//end else if

                Log.d("testcheck", countNumOfCheckBoxes() + " ");
            }

            else if (hexadecimal_Left.isChecked() && decimal_Right.isChecked()) {
                    binary = convertHexToBin(input);
                    binary = binary.replaceAll("\\s+", "");
                    answer = Long.toString((convertBinToDec(binary, false)));
            }//end else if

        }//else is not empty

        TextView txtView = (TextView) findViewById(R.id.answer);
        txtView.setTextSize(14);
        txtView.setText(answer);
    }//end convert

    private String convertDecToBin(long decimalNum){
        String result = ""; //will hold the result
        long remainder = -1; //will hold the remainder
        long leftOver = -1; //will hold the remaining number after division
        boolean positive = true; //assume the number is positive
        long decimal = decimalNum;
        int length = -1; //will hold the length of the binary conversion
        int i; //for loop
        boolean notChanged = true; //this says whether or not we switched a digit over
        StringBuilder resultToNeg;

        //if negative number
        if(decimal < 0) {
            positive = false;
            decimal = Math.abs(decimal);
        }//end if

        do{
            leftOver = decimal;
            remainder = leftOver % 2;
            result = remainder + result;
            decimal /= 2;
        }while(decimal != 0);

        if(positive){
            result =  "0" + result;
        }//end if
        else{
            result =  "0" + result;
            length = result.length();

            resultToNeg = new StringBuilder(result);

            //switch all 0's and 1's
            for(i = 0; i < length; i++){
                if(resultToNeg.charAt(i) == '0'){
                    resultToNeg.setCharAt(i,'1');
                    notChanged = false;
                }//end if
                else if(resultToNeg.charAt(i) == '1' && notChanged ){
                    resultToNeg.setCharAt(i,'0');
                }//end else if

                notChanged = true;
            }//end for

            //Add +1 to binary
            for(i = length-1; i >= 0; i--){
                if(resultToNeg.charAt(i) == '0'){
                    resultToNeg.setCharAt(i,'1');
                    break;
                }//end if
                else if(resultToNeg.charAt(i) == '1' && i == 0){
                    resultToNeg.setCharAt(i,'0');
                    resultToNeg = new StringBuilder("1" + resultToNeg);
                }//end else if
                else if(resultToNeg.charAt(i) == '1'){
                    resultToNeg.setCharAt(i,'0');
                }//end if
            }//end for
            result = "1" + resultToNeg;
        }//end else

        return result;
    }//end convertDecToBin

    private long convertBinToDec(String binNum, boolean canBeNeg){
        long result = 0;
        int i; //counter for our loop
        int pos = 0; //to determine which power to raise 2 at to convert to decimal
        String binary_toString = binNum;
        int lengthBin = binary_toString.length();

        StringBuilder txtResult = new StringBuilder(binary_toString);

        if(txtResult.charAt(0) == '1' && canBeNeg == true){ //if binary is negative
            //switch 0 to 1 and 1 to 0
            for(i = 0; i < lengthBin; i++){
                if(txtResult.charAt(i) == '0'){
                    txtResult.setCharAt(i,'1');
                }//end if
                else if(txtResult.charAt(i) == '1'){
                    txtResult.setCharAt(i,'0');
                }//end else if
            }//end for

            //Add +1 to binary
            for(i = lengthBin-1; i >= 0; i--){
                if(txtResult.charAt(i) == '0'){
                    txtResult.setCharAt(i,'1');
                    break;
                }//end if
                else if(txtResult.charAt(i) == '1' && i == 0){
                    txtResult.setCharAt(i,'0');
                    txtResult = new StringBuilder("1" + txtResult);
                }//end else if
                else if(txtResult.charAt(i) == '1'){
                    txtResult.setCharAt(i,'0');
                }//end else if
            }//end for

            //Now convert to decimal
            for (i = lengthBin - 1; i >= 0; i--) {
                if (txtResult.charAt(i) == '1') {
                    result += Math.pow(2, pos);
                }//end if
                pos++;
            }//end for

            result = -result;

        }//end if

        else if(txtResult.charAt(0) == '0' || canBeNeg == false) { //if binary is negative
            for (i = lengthBin - 1; i >= 0; i--) {
                if (txtResult.charAt(i) == '1') {
                    result += Math.pow(2, pos);
                }//end if
                pos++;
            }//end for
        }
        return result;
    }//end convertBinToDec

    private String convertHexToBin(String hex){
        String result = "";
        int length = hex.length(); //length of the hex value

        for(int i = 0; i < length; i++){
            if(hex.charAt(i) == '0'){
                result += "0000";
            }//end if
            else if(hex.charAt(i) == '1'){
                result += "0001";
            }//end if
            else if(hex.charAt(i) == '2'){
                result += "0010";
            }//end if
            else if(hex.charAt(i) == '3'){
                result += "0011";
            }//end if
            else if(hex.charAt(i) == '4'){
                result += "0100";
            }//end if
            else if(hex.charAt(i) == '5'){
                result += "0101";
            }//end if
            else if(hex.charAt(i) == '6'){
                result += "0110";
            }//end if
            else if(hex.charAt(i) == '7'){
                result += "0111";
            }//end if
            else if(hex.charAt(i) == '8'){
                result += "1000";
            }//end if
            else if(hex.charAt(i) == '9'){
                result += "1001";
            }//end if
            else if(hex.charAt(i) == 'A'){
                result += "1010";
            }//end if
            else if(hex.charAt(i) == 'B'){
                result += "1011";
            }//end if
            else if(hex.charAt(i) == 'C'){
                result += "1100";
            }//end if
            else if(hex.charAt(i) == 'D'){
                result += "1101";
            }//end if
            else if(hex.charAt(i) == 'E'){
                result += "1110";
            }//end if
            else if(hex.charAt(i) == 'F'){
                result += "1111";
            }//end if
        }//end for
        return result;
    }//end convertHexToBin

    private String convertBinToHex(String bin){
        String result = ""; //the hexadecimal number(string) we return
        String binary = bin.replaceAll("\\s+", "");
        int posOfFirst1 = binary.indexOf('1');
        int totalLength = 0;
        int sum = 0;
        String temp = ""; //will hold the smaller string parts
        Boolean isPositive = true;


        if(posOfFirst1 == -1){
            result = "0";
        }
        else if(posOfFirst1 != -1 ) {
            binary = binary.substring(posOfFirst1);
            if(binary.charAt(0) == '1'){
                isPositive = false;
            }
        }

        totalLength = binary.length(); //length of whole binary string

        if(posOfFirst1 != -1) {
            if (binary.length() % 4 != 0) {
                do {
                    if(isPositive) {
                        binary = "0" + binary;
                    }
                    else if(!isPositive) {
                        binary = "1" + binary;
                    }
                } while (binary.length() % 4 != 0);

                totalLength = binary.length();
            }//end if

            for (int i = totalLength - 1; i >= 0; i--) {
                temp = binary.charAt(i) + temp;

                if (totalLength == 0) {
                    sum = calculateSum(temp, temp.length());
                    result = convertSumToHex(sum) + result;
                }//end if
                else if (temp.length() / 4 == 1) {
                    sum = calculateSum(temp, temp.length());
                    result = convertSumToHex(sum) + result;
                    temp = "";
                }//end else if
                totalLength--;
            }//end for
        }//end if
        return result;
    }//end convertBinToHex

    private int calculateSum(String binString, int length){
        int result = 0;
        int counter = 0;

        for(int i = length-1; i >= 0; i--) {
            if(binString.charAt(i) == '1') {
                result += Math.pow(2, counter);
            }//end if
            counter++;
        }//end for

        return result;
    }//end calculateSum

    private String convertSumToHex(int sum){
        String result = "";
        String [] hexArray = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

        result = hexArray[sum];
        return result;
    }
    private int determineNumOfBits(){
        String _methodName = "determineNumOfBits";
        int result = 0;

        Log.d(MODULE + "." + _methodName, " | Run");

        if(((CheckBox) findViewById(R.id.checkBox8)).isChecked()){
            result = 8;
        }//end if
        else if(((CheckBox) findViewById(R.id.checkBox16)).isChecked()){
            result = 16;
        }//end else if
        else if(((CheckBox) findViewById(R.id.checkBox32)).isChecked()){
            result = 32;
        }//end else if
        else if(((CheckBox) findViewById(R.id.checkBox64)).isChecked()){
            result = 64;
        }//end else if

        return result;
    }//end determineNumOfBits

    private String signExtend(String value, int length) {
        String result = value;
        Log.d("hi ", result.length() + " ");
        if (result.equals("0")) {
            while (result.length() < length) {
                result += "0";
            }//end while
        }//end if

        else if(result.charAt(0) == '0' && result.length() < length){
            while(result.length() < length) {
                result = "0" + result;
            }//end while
        }//end else if

        else if(result.charAt(0) == '1' && result.length() < length){
            while(result.length() < length) {
                result = "1" + result;
            }//end while
        }//end else if

        return result;
    }//end signExtend

    private int countNumOfCheckBoxes(){
        int count = 0; //number of checkboxes selected

        if(((CheckBox) findViewById(R.id.checkBox8)).isChecked()){
            count++;
        }//end if
        if(((CheckBox) findViewById(R.id.checkBox16)).isChecked()){
            count++;
        }//end if
        if(((CheckBox) findViewById(R.id.checkBox32)).isChecked()){
            count++;
        }//end if
        if(((CheckBox) findViewById(R.id.checkBox64)).isChecked()){
            count++;
        }//end if

        return count;
    }//end countNumOfCheckBoxes

}//end DisplayConverter
