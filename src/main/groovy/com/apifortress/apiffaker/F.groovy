package com.apifortress.apiffaker

import com.github.javafaker.Faker

public class F {

    private Faker faker

    F(String loc = null) {
        this.faker = loc ? new Faker(new Locale((loc))) : new Faker()
    }

    public void setLocale(String loc) {
        this.faker = new Faker(new Locale((loc)))
    }

    public String streetName() {return faker.address().streetName()}
    public int streetAddressNumber() {return faker.address().streetAddressNumber() as int}
    public String streetAddress(Boolean includeSecondary = false) {return faker.address().streetAddress(includeSecondary)}
    public String secondaryAddress() {return faker.address().secondaryAddress()}
    public String zipCode(String stateAbbr = null) {return stateAbbr ? faker.address().zipCodeByState(stateAbbr) : faker.address().zipCode()}
    public String streetSuffix() {return faker.address().streetSuffix()}
    public String streetPrefix() {return faker.address().streetPrefix()}
    public String citySuffix() {return faker.address().citySuffix()}
    public String cityPrefix() {return faker.address().cityPrefix()}
    public String city() {return faker.address().city()}
    public String cityName() {return faker.address().cityName()}
    public String state() {return faker.address().state()}
    public String buildingNumber() {return faker.address().buildingNumber()}
    public String fullAddress() {return faker.address().fullAddress()}
    public String country() {return faker.address().country()}
    public String countryCode() {return faker.address().countryCode()}
    public String countryCodeSL() {return faker.country().countryCode2()}
    public String countryCodeSL3d() {return faker.country().countryCode3()}
    public String capital() {return faker.country().capital()}
    public String currency() {return faker.country().currency()}
    public String currencyCode() {return faker.country().currencyCode()}

    public String fullName() {return faker.name().fullName()}
    public String firstName() {return faker.address().firstName()}
    public String lastName() {return faker.address().lastName()}
    public String timeZone() {return faker.address().timeZone()}

    public String mobile(){return  faker.phoneNumber().cellPhone()}
    public String phone(){return  faker.phoneNumber().phoneNumber()}

    public String validID() { return faker.idNumber().valid() }
    public String invalidID() { return faker.idNumber().invalid() }

    public String validSSN() { return faker.idNumber().validSvSeSsn() }
    public String invalidSSN() { return faker.idNumber().invalidSvSeSsn() }




    public String creditCardNumber() {return faker.business().creditCardNumber()}
    public String creditCardExpiry() {return faker.business().creditCardExpiry()}
    public String creditCardType() {return faker.business().creditCardType()}

    public String productName() {return faker.commerce().productName()}
    public String material() {return faker.commerce().material()}
    public String price() {return faker.commerce().price()}
    public String promotionCode() {return faker.commerce().promotionCode()}

    public String companyName() {return faker.company().name()}
    public String suffix() {return faker.company().suffix()}
    public String industry() {return faker.company().industry()}
    public String profession() {return faker.company().profession()}


    //public String emailAddress() { return faker.internet().emailAddress() }
    //public String emailAddress(String localPart) {  }
    public String emailAddress(String localPart = null, String domain = null) {
        if (!localPart && !domain)
            return faker.internet().emailAddress()
        else if (localPart && !domain)
            return faker.internet().emailAddress(localPart)
        else if (!localPart && domain)
            return faker.internet().emailAddress(this.faker.name().username(),domain)
        else
            return faker.internet().emailAddress(localPart,domain)

    }
    public String domainName() { return faker.internet().domainName() }
    public String domainWord() { return faker.internet().domainWord() }
    public String domainSuffix() { return faker.internet().domainSuffix() }
    public String url() { return faker.internet().url() }
    public String password(int minimumLength = 8, int maximumLength = 16,boolean includeUppercase = false,boolean includeSpecial = false) { return faker.internet().password(minimumLength, maximumLength, includeUppercase, includeSpecial) }

    public int integer(int min = 0,int max = 100) {return faker.number().numberBetween(min,max)}
    public int[] integerList(int min = 0,int max = 100,int count = 10) { def numbers=[]; count.times{return numbers.add(faker.number().numberBetween(min,max))}; return numbers}
    public double decimal(long min = 0,long max = 100,int maxDecimals = 2) {return faker.number().randomDouble(maxDecimals,min,max)}

    public boolean bool(){return integer(0,1)}

    public String uuid() {
        return faker.internet().uuid()
    }

    private def currentarg

    public def single(def args){
        return collection(1,args)
    }

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
                try {
                    result.add("${args}"())
                } catch (MissingMethodException ex) {
                    result.add(missingMethoResponse(args))
                }
        }
        return result
    }

    private List collectList(int iterations, args) {
        def results = []
        iterations.times {
            def result = []
            args.each { arg ->
                currentarg = arg
                if(arg instanceof Map || arg instanceof List)
                    result.add(collection(1,arg))
                else if ("null".equals(arg))
                    result.add(null)
                else
                    try{
                        result.add("${arg}"())
                    } catch (MissingMethodException ex) {
                        result.add(missingMethoResponse(arg))
                    }

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
                currentarg = value
                if(value instanceof Map || value instanceof List)
                    result.put(key,collection(1,value))
                else if ("null".equals(value))
                    result.put(key, null)
                else
                    try {
                        result.put(key, "${value}"())
                    } catch (MissingMethodException ex) {
                        result.put(key,missingMethoResponse(value))
                    }
            }
            results.add(result)
        }
        return results
    }

    private String missingMethoResponse(def value) {
        StringBuilder stringBuilder = new StringBuilder()
        stringBuilder.append("Data generator '")
        stringBuilder.append(value)
        stringBuilder.append("' does not exist in extension F")
        return stringBuilder.toString()
    }


}
