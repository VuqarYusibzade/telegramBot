package org.example.tour_guide.utils;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token) {
        return "Dear " + name + "\n\nThank you for your interest in Tour Guide App.\n" +
                "Please verify your email address by clicking the link below \n\n"
                + getVerificationUrl(host, token) +"\n\n\nOnce you have completed " +
                "the verification process, " +
                "you can now log in to the Tour Guide App " +
                "with this account and create your agency.\n\n"
                +"Best wishes\n"
                +"Tour Guide App";
    }

    private static String getVerificationUrl(String host, String token) {
        return host + "/api/users/register?token=" + token;
    }

    public static String forgotPasswordUrl(String host,String resetToken){
        return "To reset your password, click the link"+ host + "/reset?token=" + resetToken;

    }


}
