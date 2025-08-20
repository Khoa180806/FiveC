package com.team4.quanliquanmicay.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation across the FiveC Restaurant Management System
 * @author FiveC Team
 */
public class XValidation {
    
    // ========================================
    // CONSTANTS FOR VALIDATION RULES
    // ========================================
    
    // Phone number validation
    private static final int MIN_PHONE_LENGTH = 10;
    private static final int MAX_PHONE_LENGTH = 11;
    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[0-9]{9,10}$");
    private static final Pattern INTERNATIONAL_PHONE_PATTERN = Pattern.compile("^\\+84[0-9]{9,10}$");
    
    // Customer validation
    private static final int MIN_CUSTOMER_NAME_LENGTH = 2;
    private static final int MAX_CUSTOMER_NAME_LENGTH = 50;
    private static final Pattern CUSTOMER_NAME_PATTERN = Pattern.compile("^[\\p{L}0-9 .,'-]+$");
    
    // Product validation
    private static final int MIN_PRODUCT_ID_LENGTH = 1;
    private static final int MAX_PRODUCT_ID_LENGTH = 10;
    private static final int MIN_PRODUCT_NAME_LENGTH = 2;
    private static final int MAX_PRODUCT_NAME_LENGTH = 100;
    private static final Pattern PRODUCT_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static final Pattern PRODUCT_NAME_PATTERN = Pattern.compile("^[\\p{L}0-9 .,'-]+$");
    
    // User validation
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    
    // Bill validation
    private static final int MIN_BILL_AMOUNT = 1;
    private static final int MAX_BILL_AMOUNT = 999999;
    
    // ========================================
    // BASIC VALIDATION METHODS
    // ========================================
    
    /**
     * Check if string is empty or null
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Check if string is not empty
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
    
    /**
     * Check if string is blank (only whitespace)
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Check if string is not blank
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }
    
    // ========================================
    // PHONE NUMBER VALIDATION
    // ========================================
    
    /**
     * Validate Vietnamese phone number format (0xxxxxxxxx)
     */
    public static boolean isVietnamesePhone(String value) {
        if (isEmpty(value)) {
            return false;
        }
        String trimmedPhone = value.trim();
        
        // Kiểm tra độ dài
        if (trimmedPhone.length() < MIN_PHONE_LENGTH || trimmedPhone.length() > MAX_PHONE_LENGTH) {
            return false;
        }
        
        // Kiểm tra định dạng số điện thoại Việt Nam
        return VIETNAM_PHONE_PATTERN.matcher(trimmedPhone).matches();
    }
    
    /**
     * Validate international phone number format (+84xxxxxxxxx)
     */
    public static boolean isInternationalPhone(String value) {
        if (isEmpty(value)) {
            return false;
        }
        String trimmedPhone = value.trim();
        
        // Kiểm tra định dạng số điện thoại quốc tế
        return INTERNATIONAL_PHONE_PATTERN.matcher(trimmedPhone).matches();
    }
    
    /**
     * Validate any phone number format (Vietnamese or International)
     */
    public static boolean isPhone(String value) {
        return isVietnamesePhone(value) || isInternationalPhone(value);
    }
    
    /**
     * Get phone number validation error message
     */
    public static String getPhoneValidationMessage(String phoneNumber) {
        if (isEmpty(phoneNumber)) {
            return "Số điện thoại không được để trống!";
        }
        
        String trimmedPhone = phoneNumber.trim();
        
        if (trimmedPhone.length() < MIN_PHONE_LENGTH) {
            return "Số điện thoại quá ngắn! (Tối thiểu " + MIN_PHONE_LENGTH + " chữ số)";
        }
        
        if (trimmedPhone.length() > MAX_PHONE_LENGTH) {
            return "Số điện thoại quá dài! (Tối đa " + MAX_PHONE_LENGTH + " chữ số)";
        }
        
        if (!trimmedPhone.startsWith("0") && !trimmedPhone.startsWith("+84")) {
            return "Số điện thoại phải bắt đầu bằng 0 hoặc +84!";
        }
        
        if (!trimmedPhone.matches("^[0-9+]+$")) {
            return "Số điện thoại chỉ được chứa số và dấu +!";
        }
        
        return "Số điện thoại không hợp lệ!";
    }
    
    // ========================================
    // CUSTOMER VALIDATION
    // ========================================
    
    /**
     * Validate customer name
     */
    public static boolean isValidCustomerName(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        String trimmedName = value.trim();
        
        // Kiểm tra độ dài
        if (trimmedName.length() < MIN_CUSTOMER_NAME_LENGTH || trimmedName.length() > MAX_CUSTOMER_NAME_LENGTH) {
            return false;
        }
        
        // Kiểm tra ký tự đặc biệt nguy hiểm
        if (trimmedName.contains("<") || trimmedName.contains(">") || 
            trimmedName.contains("\"") || trimmedName.contains("'")) {
            return false;
        }
        
        // Kiểm tra pattern cho tên khách hàng
        return CUSTOMER_NAME_PATTERN.matcher(trimmedName).matches();
    }
    
    /**
     * Validate customer point level
     */
    public static boolean isValidPointLevel(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        try {
            int pointLevel = Integer.parseInt(value.trim());
            return pointLevel >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate customer ranking
     */
    public static boolean isValidCustomerRanking(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        String ranking = value.trim();
        return ranking.equals("Bronze") || ranking.equals("Silver") || 
               ranking.equals("Gold") || ranking.equals("Diamond") ||
               ranking.equals("Thường") || ranking.equals("VIP");
    }
    
    /**
     * Check data consistency between points and ranking
     */
    public static boolean isCustomerDataConsistent(int points, String ranking) {
        if (points >= 500) {
            return "Diamond".equals(ranking);
        } else if (points >= 200) {
            return "Gold".equals(ranking) || "Diamond".equals(ranking);
        } else if (points >= 100) {
            return "Silver".equals(ranking) || "Gold".equals(ranking) || "Diamond".equals(ranking);
        } else {
            return "Bronze".equals(ranking) || "Silver".equals(ranking) || 
                   "Gold".equals(ranking) || "Diamond".equals(ranking) ||
                   "Thường".equals(ranking) || "VIP".equals(ranking);
        }
    }
    
    /**
     * Get customer name validation message
     */
    public static String getCustomerNameValidationMessage(String customerName) {
        if (isEmpty(customerName)) {
            return "Tên khách hàng không được để trống!";
        }
        
        String trimmedName = customerName.trim();
        
        if (trimmedName.length() < MIN_CUSTOMER_NAME_LENGTH) {
            return "Tên khách hàng quá ngắn! (Tối thiểu " + MIN_CUSTOMER_NAME_LENGTH + " ký tự)";
        }
        
        if (trimmedName.length() > MAX_CUSTOMER_NAME_LENGTH) {
            return "Tên khách hàng quá dài! (Tối đa " + MAX_CUSTOMER_NAME_LENGTH + " ký tự)";
        }
        
        if (trimmedName.contains("<") || trimmedName.contains(">") || 
            trimmedName.contains("\"") || trimmedName.contains("'")) {
            return "Tên khách hàng không được chứa ký tự đặc biệt nguy hiểm!";
        }
        
        return "Tên khách hàng không hợp lệ!";
    }
    
    // ========================================
    // PRODUCT VALIDATION
    // ========================================
    
    /**
     * Validate product ID
     */
    public static boolean isValidProductId(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        String trimmedId = value.trim();
        
        // Kiểm tra độ dài
        if (trimmedId.length() < MIN_PRODUCT_ID_LENGTH || trimmedId.length() > MAX_PRODUCT_ID_LENGTH) {
            return false;
        }
        
        // Kiểm tra pattern
        return PRODUCT_ID_PATTERN.matcher(trimmedId).matches();
    }
    
    /**
     * Validate product name
     */
    public static boolean isValidProductName(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        String trimmedName = value.trim();
        
        // Kiểm tra độ dài
        if (trimmedName.length() < MIN_PRODUCT_NAME_LENGTH || trimmedName.length() > MAX_PRODUCT_NAME_LENGTH) {
            return false;
        }
        
        // Kiểm tra pattern
        return PRODUCT_NAME_PATTERN.matcher(trimmedName).matches();
    }
    
    /**
     * Validate product price
     */
    public static boolean isValidProductPrice(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        try {
            double price = Double.parseDouble(value.trim());
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate product discount
     */
    public static boolean isValidProductDiscount(String value) {
        if (isEmpty(value)) {
            return true; // Discount có thể để trống
        }
        
        try {
            double discount = Double.parseDouble(value.trim());
            return discount >= 0 && discount <= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate product unit
     */
    public static boolean isValidProductUnit(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        String unit = value.trim();
        return unit.equals("phần") || unit.equals("cái") || unit.equals("ly") || 
               unit.equals("chai") || unit.equals("kg") || unit.equals("g");
    }
    
    // ========================================
    // USER VALIDATION
    // ========================================
    
    /**
     * Validate email format
     */
    public static boolean isEmail(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    
    /**
     * Validate username
     */
    public static boolean isUsername(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        String trimmedUsername = value.trim();
        
        // Kiểm tra độ dài
        if (trimmedUsername.length() < MIN_USERNAME_LENGTH || trimmedUsername.length() > MAX_USERNAME_LENGTH) {
            return false;
        }
        
        // Kiểm tra pattern
        return USERNAME_PATTERN.matcher(trimmedUsername).matches();
    }
    
    /**
     * Validate password strength
     */
    public static boolean isPassword(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        // Kiểm tra độ dài tối thiểu
        if (value.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        
        // Kiểm tra pattern
        return PASSWORD_PATTERN.matcher(value).matches();
    }
    
    /**
     * Validate password without special character requirement
     */
    public static boolean isSimplePassword(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        return value.length() >= 6;
    }
    
    // ========================================
    // NUMBER VALIDATION
    // ========================================
    
    /**
     * Validate if string is a number
     */
    public static boolean isNumber(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.matches("^-?\\d+(\\.\\d+)?$");
    }
    
    /**
     * Validate if string is a positive number
     */
    public static boolean isPositiveNumber(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        try {
            double number = Double.parseDouble(value.trim());
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate if string is a non-negative number
     */
    public static boolean isNonNegativeNumber(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        try {
            double number = Double.parseDouble(value.trim());
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate if string is an integer
     */
    public static boolean isInteger(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.matches("^-?\\d+$");
    }
    
    /**
     * Validate if string is a positive integer
     */
    public static boolean isPositiveInteger(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        try {
            int number = Integer.parseInt(value.trim());
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // ========================================
    // DATE AND TIME VALIDATION
    // ========================================
    
    /**
     * Validate date format (YYYY-MM-DD)
     */
    public static boolean isDate(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
    
    /**
     * Validate time format (HH:MM)
     */
    public static boolean isTime(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.matches("^\\d{2}:\\d{2}$");
    }
    
    /**
     * Validate datetime format (YYYY-MM-DD HH:MM)
     */
    public static boolean isDateTime(String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$");
    }
    
    // ========================================
    // BILL VALIDATION
    // ========================================
    
    /**
     * Validate bill amount
     */
    public static boolean isValidBillAmount(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        try {
            double amount = Double.parseDouble(value.trim());
            return amount >= MIN_BILL_AMOUNT && amount <= MAX_BILL_AMOUNT;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate bill quantity
     */
    public static boolean isValidQuantity(String value) {
        return isPositiveInteger(value);
    }
    
    // ========================================
    // SECURITY VALIDATION
    // ========================================
    
    /**
     * Sanitize input to prevent SQL injection and XSS
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        
        return input.trim()
            .replaceAll("[<>\"']", "") // Loại bỏ ký tự nguy hiểm
            .replaceAll("\\s+", " "); // Chuẩn hóa khoảng trắng
    }
    
    /**
     * Check if string contains dangerous characters
     */
    public static boolean containsDangerousChars(String value) {
        if (isEmpty(value)) {
            return false;
        }
        
        return value.contains("<") || value.contains(">") || 
               value.contains("\"") || value.contains("'") ||
               value.contains(";") || value.contains("--") ||
               value.contains("/*") || value.contains("*/");
    }
    
    // ========================================
    // UTILITY METHODS
    // ========================================
    
    /**
     * Get validation error message for any field
     */
    public static String getValidationMessage(String fieldName, String value, String validationType) {
        switch (validationType.toLowerCase()) {
            case "required":
                return fieldName + " không được để trống!";
            case "phone":
                return getPhoneValidationMessage(value);
            case "customer_name":
                return getCustomerNameValidationMessage(value);
            case "email":
                return "Email không hợp lệ!";
            case "username":
                return "Tên đăng nhập không hợp lệ! (3-20 ký tự, chỉ chữ, số, gạch dưới)";
            case "password":
                return "Mật khẩu không hợp lệ! (Tối thiểu 8 ký tự, có chữ hoa, chữ thường, số, ký tự đặc biệt)";
            case "number":
                return fieldName + " phải là số!";
            case "positive_number":
                return fieldName + " phải là số dương!";
            case "date":
                return "Định dạng ngày không hợp lệ! (YYYY-MM-DD)";
            case "time":
                return "Định dạng giờ không hợp lệ! (HH:MM)";
            default:
                return fieldName + " không hợp lệ!";
        }
    }
    
    /**
     * Validate multiple fields and return error messages
     */
    public static String validateMultipleFields(String[][] validations) {
        StringBuilder errorMessages = new StringBuilder();
        
        for (String[] validation : validations) {
            String fieldName = validation[0];
            String value = validation[1];
            String validationType = validation[2];
            
            boolean isValid = true;
            
            switch (validationType.toLowerCase()) {
                case "required":
                    isValid = isNotEmpty(value);
                    break;
                case "phone":
                    isValid = isPhone(value);
                    break;
                case "customer_name":
                    isValid = isValidCustomerName(value);
                    break;
                case "email":
                    isValid = isEmail(value);
                    break;
                case "username":
                    isValid = isUsername(value);
                    break;
                case "password":
                    isValid = isPassword(value);
                    break;
                case "number":
                    isValid = isNumber(value);
                    break;
                case "positive_number":
                    isValid = isPositiveNumber(value);
                    break;
                case "date":
                    isValid = isDate(value);
                    break;
                case "time":
                    isValid = isTime(value);
                    break;
            }
            
            if (!isValid) {
                errorMessages.append("• ").append(getValidationMessage(fieldName, value, validationType)).append("\n");
            }
        }
        
        return errorMessages.toString();
    }
}

