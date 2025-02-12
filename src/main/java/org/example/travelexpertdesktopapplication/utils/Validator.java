package org.example.travelexpertdesktopapplication.utils;
import java.util.regex.Pattern;

public class Validator {

    /**
     * Final variables containing Regex for validation
     * Regex is in Canadian standard, such as Canadian phone numbers and postal codes - Update if needed.
     * Feel free to add to the final variables and extend the validation methods as needed.
     */

    // BASIC CRUD VALIDATIONS

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s'-]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?1?[-\\s]?\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{4}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$");

    /**
     * Checks for empty field
     * @param input - User input via Text field, general use.
     * @return An error message if invalid, or null if it is valid.
     */
    public static String checkForEmpty(String input){
        String errorMessage = "";
        if (input == null || input.trim().isEmpty()){
            return errorMessage += input + " " + "field cannot be empty";
        }
        // If valid, return null.
        return null;
    }

    /**
     * First checks for empty, and gives error message specific to name fields, then checks to match regex pattern
     * @param name - The name to validate
     * @return - An error message if invalid, null if valid.
     */
    public static String validateName(String name){
        String errorMessage = "";
        if (checkForEmpty(name) != null){
            return errorMessage+= "First and/or Last name fields cannot be empty";
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            return errorMessage+= "First and/or Last name fields can only contain letters, spaces, hyphens and apostrophes. ";
        }
        // If Valid, return null
        return null;
    }

    /**
     * First checks for empty, and delivers field specific error message, then checks for regex pattern match.
     * @param email - The email to be validated
     * @return ErrorMessage if invalid, null if valid.
     */
    public static String validateEmail(String email){
        String errorMessage = "";
        if (checkForEmpty(email) != null){
            return errorMessage+= "Email cannot be empty";
        }
        if (!EMAIL_PATTERN.matcher(email).matches()){
            return errorMessage+= "Invalid email format. Please enter a valid email (example@travelexperts.com)";
        }
        return null;
    }

    /**
     * First checks for empty, and delivers message specific to phone number field, then checks for regex match
     * @param phone - The phone number to be validated
     * @return ErrorMessage if invalid, null if valid.
     */
    public static String validatePhoneNumber(String phone){
        String errorMessage = "";
        if (checkForEmpty(phone) != null){
            return errorMessage+= "Phone number cannot be empty";
        }
        if (!PHONE_PATTERN.matcher(phone).matches()){
            return errorMessage+= "Invalid phone number format. Please use 000-123-4567 format";
        }
        return null;
    }

    /**
     * Checks for empty, delivers specific message in relation to Postal Code, checks for regex match after wards
     * @param postalCode - postal code to be validated
     * @return Errormessage if invalid, null if valid.
     */
    public static String validatePostalCode(String postalCode){
        String errorMessage = "";
        if (checkForEmpty(postalCode) != null){
            errorMessage+= "Postal Code field cannot be null";
        }
        if (!POSTAL_CODE_PATTERN.matcher(postalCode).matches()){
            return errorMessage+= "Invalid postal code format, Please use A1A 1A1 format.";
        }
        return null;
    }


}//class
