package notSample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Fernando Ramirez
 * Date 11/28/2019
 *
 * this class contains functionality of how the Employee class will behave in this program,
 * including password configuration
 */

class Employee {
    private StringBuilder name;
    private String username;
    private String password;
    private String email;

    /**
     * This method will created to allow users to be admitted into the program
     *
     * @param String name, String password
     * @return null
     */
    public Employee(String name, String password) {
        StringBuilder sBName = new StringBuilder(name);
        StringBuilder defaultUsername = new StringBuilder("default");
        StringBuilder defaultEmail = new StringBuilder("user");
        this.name = sBName;
        this.password = password;

        if (checkName(sBName)) {
            setUsername(sBName);
            setEmail(sBName);
        } else {
            setUsername(defaultUsername);
            setEmail(defaultEmail);
        }
        if (validPassword(password)) {
            this.password = password;
        } else {
            this.password = "pw";
        }
    }

    /**
     * getName method of String Builder type
     *
     * @return a string builder valued
     */
    public StringBuilder getName() {
        return name;
    }

    /**
     * getPassword method of String type
     *
     * @return a String value for password
     */
    public String getPassword() {
        return password;
    }

    /**
     * getUsername method of String type
     *
     * @return String value or username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getEmail method of string type
     *
     * @return returns a string value for email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method is changing whatever the name criteria is to a String,
     * then using regex to check if the name matches.
     *
     * @param StringBuilder name.
     * @return splitArray
     */
    private boolean checkName(StringBuilder name) {
        String[] splitArray = name.toString().split(" ");
        return splitArray.length == 1 ? false : true;
    }

    /**
     * This method is changing whatever the username criteria is to a String,
     * then using regex to check if the username matches.
     *
     * @param StringBuilder name.
     * @return null
     */
    private void setUsername(StringBuilder name) {
        String[] splitArray = name.toString().split(" ");
        if (splitArray.length > 1) {
            this.username = (splitArray[0].substring(0, 1) + splitArray[1]).toLowerCase();
        } else {
            this.username = (splitArray[0]).toLowerCase();
        }
    }

    /**
     * This method is adding the @oracleacademy.test to the end of each email entered
     *
     * @param StringBuilder name.
     * @return null
     */
    private void setEmail(StringBuilder name) {
        String[] splitArray = name.toString().split(" ");
        if (splitArray.length > 1) {
            this.email = (splitArray[0] + "." + splitArray[1]).toLowerCase() + "@oracleacademy.Test";
        } else {
            this.email = (splitArray[0]).toLowerCase() + "@oracleacademy.Test";
        }
    }

    /**
     * This method is generating a password from the criteria that the user inputs
     *
     * @param StringBuilder password.
     * @return String
     */
    private boolean validPassword(String password) {

        if (!password.matches(".*[A-Z].*")) return false;

        if (!password.matches(".*[a-z].*")) return false;

        if (!password.matches(".*[~!.......].*")) return false;

        return true;
    }

    /**
     * This method is changing all the outputted
     * variables and data into String variables
     *
     * @param null
     * @return null
     */
    public String toString() {
        String str1 = String.format("Employee Details" + "\nName : " + name + "\nUsername : " + username + "\nEmail : " + email + "\nInitial Password : " + password);
        return str1;
    }
}
