package com.apifortress.apiffaker

import com.github.javafaker.Faker

public class F {

    private Faker faker
    boolean templateStyle

    F(String loc = null) {
        this.faker = loc ? new Faker(new Locale((loc))) : new Faker()
    }

    public void setLocale(String loc) {
        this.faker = new Faker(new Locale((loc)))
    }

    /**
     * provides random street name
     * @return  random street name
     */
    public String streetName() {return faker.address().streetName()}

    /**
     * provides random address number
     * @return random address number
     */
    public int streetAddressNumber() {return faker.address().streetAddressNumber() as int}

    /**
     * provides random street and address number.
     * @param includeSecondary specifided provides apt number
     * @return random street and address number
     */
    public String streetAddress(Boolean includeSecondary = false) {return faker.address().streetAddress(includeSecondary)}

    /**
     * provides random apt number
     * @return random apt number
     */
    public String secondaryAddress() {return faker.address().secondaryAddress()}

    /**
     * provides random zip code.
     * @param stateAbbr If state provided provides proper zip code for the state **(valid only for US states)
     * @return random zip code
     */
    public String zipCode(String stateAbbr = null) {return stateAbbr ? faker.address().zipCodeByState(stateAbbr) : faker.address().zipCode()}

    /**
     * provides random street suffix
     * @return random street suffix
     */
    public String streetSuffix() {return faker.address().streetSuffix()}

    /**
     * provides random street prefix
     * @return random street prefix
     */
    public String streetPrefix() {return faker.address().streetPrefix()}

    /**
     * provides random city suffix
     * @return  random city suffix
     */
    public String citySuffix() {return faker.address().citySuffix()}

    /**
     * provides random city prefix
     * @return  random city prefix
     */
    public String cityPrefix() {return faker.address().cityPrefix()}

    /**
     * provides random city Name
     * @return random city Name
     */
    public String city() {return faker.address().city()}

    /**
     * provides random city Name
     * @return random city Name
     */
    public String cityName() {return faker.address().cityName()}

    /**
     * provides random state/province
     * @return random state/province
     */
    public String state() {return faker.address().state()}

    /**
     * provides random build number
     * @return random build number
     */
    public String buildingNumber() {return faker.address().buildingNumber()}

    /**
     * provides random full adress
     * @return  random full adress
     */
    public String fullAddress() {return faker.address().fullAddress()}

    /**
     * provides random country
     * @return random country
     */
    public String country() {return faker.address().country()}

    /**
     * provides random country code
     * @return random country code
     */
    public String countryCode() {return faker.address().countryCode()}

    /**
     * provides random country code in small letters
     * @return random country code in small letters
     */
    public String countryCodeSL() {return faker.country().countryCode2()}

    /**
     * provides random country code in small letters on 3 digits
     * @return random country code in small letters on 3 digits
     */
    public String countryCodeSL3d() {return faker.country().countryCode3()}

    /**
     * provides random capital city
     * @return random capital city
     */
    public String capital() {return faker.country().capital()}

    /**
     * provides random currency
     * @return random currency
     */
    public String currency() {return faker.country().currency()}

    /**
     * provides random currency code
     * @return random currency code
     */
    public String currencyCode() {return faker.country().currencyCode()}

    /**
     * provides random full name
     * @return random full name
     */
    public String fullName() {return faker.name().fullName()}

    /**
     * provides random first name
     * @return random first name
     */
    public String firstName() {return faker.address().firstName()}

    /**
     * provides random last name
     * @return random last name
     */
    public String lastName() {return faker.address().lastName()}

    /**
     * provides random time zone
     * @return random time zone
     */
    public String timeZone() {return faker.address().timeZone()}

    /**
     * provides random mobile number
     * @return random mobile number
     */
    public String mobile(){return  faker.phoneNumber().cellPhone()}

    /**
     * provides random phone number
     * @return random phone number
     */
    public String phone(){return  faker.phoneNumber().phoneNumber()}

    /**
     * provides random valid ID
     * @return random valid ID
     */
    public String validID() { return faker.idNumber().valid() }

    /**
     * provides random invalid ID
     * @return random invalid ID
     */
    public String invalidID() { return faker.idNumber().invalid() }

    /**
     * provides random valid SSN
     * @return random valid SSN
     */
    public String validSSN() { return faker.idNumber().validSvSeSsn() }

    /**
     * provides random invalid SSN
     * @return random invalid SSN
     */
    public String invalidSSN() { return faker.idNumber().invalidSvSeSsn() }

    /**
     * provides random credit card number
     * @return random credit card number
     */
    public String creditCardNumber() {return faker.business().creditCardNumber()}

    /**
     * provides random credit card expire date
     * @return
     */
    public String creditCardExpiry() {return faker.business().creditCardExpiry()}

    /**
     * provides random credit card type
     * @return random credit card type
     */
    public String creditCardType() {return faker.business().creditCardType()}

    /**
     * provides random product name
     * @return random product name
     */
    public String productName() {return faker.commerce().productName()}

    /**
     * provides random material
     * @return random material
     */
    public String material() {return faker.commerce().material()}

    /**
     * provides random price
     * @return  random price
     */
    public String price() {return faker.commerce().price()}

    /**
     * provides random promotion code
     * @return random promotion code
     */
    public String promotionCode() {return faker.commerce().promotionCode()}

    /**
     * provides random company name
     * @return random company name
     */
    public String companyName() {return faker.company().name()}

    /**
     * provides random company suffix
     * @return random company suffix
     */
    public String suffix() {return faker.company().suffix()}

    /**
     * provides random industry
     * @return random industry
     */
    public String industry() {return faker.company().industry()}

    /**
     * provides random profession
     * @return random profession
     */
    public String profession() {return faker.company().profession()}

    /**
     * provides random email address
     * @return random email address
     */
    public String emailAddress() { return faker.internet().emailAddress() }
    //public String emailAddress(String localPart) {  }
    /*public String emailAddress(String localPart = null, String domain = null) {
        if (!localPart && !domain)
            return faker.internet().emailAddress()
        else if (localPart && !domain)
            return faker.internet().emailAddress(localPart)
        else if (!localPart && domain)
            return faker.internet().emailAddress(this.faker.name().username(),domain)
        else
            return faker.internet().emailAddress(localPart,domain)

    }
     */

    /**
     * provides random domain name
     * @return random domain name
     */
    public String domainName() { return faker.internet().domainName() }

    /**
     * provides random word
     * @return random word
     */
    public String domainWord() { return faker.internet().domainWord() }

    /**
     * provides random suffix
     * @return random suffix
     */
    public String domainSuffix() { return faker.internet().domainSuffix() }

    /**
     * provides random url
     * @return random url
     */
    public String url() { return faker.internet().url() }

    /**
     * provides random password
     * @param minimumLength
     * @param maximumLength
     * @param includeUppercase
     * @param includeSpecial
     * @return random password
     */
    public String password(int minimumLength = 8, int maximumLength = 16,boolean includeUppercase = false,boolean includeSpecial = false) {
        return faker.internet().password(minimumLength, maximumLength, includeUppercase, includeSpecial)
    }

    /**
     * provides random integer number
     * @param min
     * @param max
     * @return random integer number
     */
    public int integer(int min = 0,int max = 100) {return faker.number().numberBetween(min,max)}

    /**
     * provides random integer number list
     * @param min
     * @param max
     * @param count
     * @return random integer number list
     */
    public int[] integerList(int min = 0,int max = 100,int count = 10) { def numbers=[]; count.times{return numbers.add(faker.number().numberBetween(min,max))}; return numbers}

    /**
     * provides random decimal number
     * @param min
     * @param max
     * @param maxDecimals
     * @return  random decimal number
     */
    public double decimal(long min = 0,long max = 100,int maxDecimals = 2) {return faker.number().randomDouble(maxDecimals,min,max)}

    /**
     * provides random boolean value
     * @return random boolean value
     */
    public boolean bool(){return integer(0,1)}

    /**
     * provides random unique identifier
     * @return random unique identifier
     */
    public String uuid() {
        return faker.internet().uuid()
    }

    private def currentarg


    /**
     * if args is a string provides an element generated by "args"
     * if args is a list provides a list of elements generated by the list of args provided.
     * if args is a map provides a map matching the structure of arg containg elements generated by the list of args provided.
     * @param args method or list of methos or map of methos of F
     * @return string, or list, or map generated mmathing the type of args
     */
    public def single(def args){
        return collection(1,args)
    }

    /**
     * if args is a string provides an list of elements generated by "args"
     * if args is a list provides a list of lists of elements generated by the list of args provided.
     * if args is a map provides a list of maps matching the structure of arg containg elements generated by the list of args provided.
     * @param iterations size ofthe result
     * @param args method or list of methos or map of methos of F
     * @return list of strings, lists or maps generated matching the type of args. the size of the list is equal to iterations
     */
    public def collection (int iterations,def args){

        def result =[]

        if (args instanceof String)
            result = collectString(iterations, args)

        if (args instanceof List)
            result = collectList(iterations, args)

        if (args instanceof Map)
            result = collectMap(iterations, args)

        return iterations == 1 ? result[0] :result
    }

    private List collectString(int iterations, def args) {
        def result = []
        iterations.times {
            if ("null".equals(args))
                result.add(null)
            else
                evaluateArg(result,args)
        }
        return result
    }

    private List collectList(int iterations, args) {
        def results = []
        iterations.times {
            def result = []
            args.each { arg ->
                if(arg instanceof Map || arg instanceof List)
                    result.add(collection(1,arg))
                else if ("null".equals(arg))
                    result.add(null)
                else
                    evaluateArg(result,arg)

            }
            results.add(result)
        }
        return results
    }

    private List collectMap(int iterations, args) {
        def results = []
        iterations.times {
            def result = [:]
            args.each { key, value ->
                if(value instanceof Map || value instanceof List)
                    result.put(key,collection(1,value))
                else if ("null".equals(value))
                    result.put(key, null)
                else
                    evaluateArg(result,value,key)
            }
            results.add(result)
        }
        return results
    }

    private void evaluateArg(def result,def value,def key = null) {
        String regex = '\\$\\{([^}]+)\\}'
        boolean matches = value.matches(regex)
        try {
            if (!templateStyle || templateStyle && matches)
            {
                value = value.replaceAll(regex, '$1')
                addAsMethod(result,value,key)
            } else {
                addAsValue(result,value,key)
            }
        } catch (MissingMethodException ex) {
            String response = missingMethoResponse(value)
            addAsValue(result,response,key)
        }
    }

    private void addAsMethod(def result,String value,String key = null) throws MissingMethodException{
        if (result instanceof List)
            result.add("${value}"())

        if (result instanceof  Map)
            result.put(key, "${value}"())
    }

    private void addAsValue(def result,String value,String key = null) throws MissingMethodException{
        if (result instanceof List)
            result.add(value)

        if (result instanceof  Map)
            result.put(key, value)
    }

    private String missingMethoResponse(def value) {
        StringBuilder stringBuilder = new StringBuilder()
        stringBuilder.append("Data generator '")
        stringBuilder.append(value)
        stringBuilder.append("' does not exist in extension F")
        return stringBuilder.toString()
    }


}
