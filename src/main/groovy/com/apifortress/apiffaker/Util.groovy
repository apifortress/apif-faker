package com.apifortress.apiffaker


import groovy.json.JsonSlurper

class Util {

    static final int MODE_FILL = 1
    static final int MODE_REMOVE = 2
    static final int MODE_INSERT = 3
    static final int MODE_SUBSTITUTE = 4

    static final int MODE_REMOVE_FLAT = 5
    static final int MODE_INSERT_FLAT = 6
    static final int MODE_SUBSTITUTE_FLAT = 7

    private static final modes=["Fill","Deep Remove","Deep Insert","Deep Substitute","Flat Remove","Flat Insert","Flat Substitute"]

    F faker
    public Util(String loc = null) {
        faker = new F(loc)
        faker.templateStyle = true
    }

    public setLocale(String loc){
        faker = new F(loc)
        faker.templateStyle = true
    }

    /**
     * fills a json model with random values
     * @param json
     * @return
     */
    public def fillModel(String json){
        def result
        def jsonSlurper = new JsonSlurper().parseText(json)

        result = faker.single(jsonSlurper)

        return result
    }

    public def manipulateModel(int manipulationMode,String jsonModel,int nodesToManipulate){
        def model = new JsonSlurper().parseText(jsonModel)
        def clone = new JsonSlurper().parseText(jsonModel)

        switch (manipulationMode){
            case MODE_REMOVE:
            case MODE_INSERT:
            case MODE_SUBSTITUTE:
                int modelNodes = deepCount(model)
                def nodes = faker.integerList(2,modelNodes,nodesToManipulate)
                println "Mode: " + modes[manipulationMode-1]+" Model Nodes: " + modelNodes + " to manipulate: "+nodes
                deepManipulation(manipulationMode,model,clone,nodes,0); break;
            case MODE_REMOVE_FLAT:
            case MODE_INSERT_FLAT:
            case MODE_SUBSTITUTE_FLAT:
                int modelNodes = flatCount(model)
                def nodes = faker.integerList(2,modelNodes,nodesToManipulate)
                println "Mode: " + modes[manipulationMode-1]+" Model Nodes: " + modelNodes + " to manipulate: "+nodes
                flatManipulation(manipulationMode,model,clone,nodes); break;
        }

        return clone
    }

    private int deepManipulation(int manipulationMode,def model,def clone, def nodes, int iteration, def key = null,def parent = null){
        iteration++

        if (iteration in nodes) {
            manipulateNode(manipulationMode,model,key,parent,iteration)
        }
        //array
        if (model instanceof List) {
            model.each {
                int index = clone.indexOf(it)
                iteration = deepManipulation(manipulationMode,it,clone[index], nodes, iteration,it,clone)
            }
        }

        //mappe
        if (model instanceof Map) {
            model.each {itKey,itValue ->
                iteration = deepManipulation(manipulationMode,itValue, clone."${itKey}", nodes, iteration,itKey,clone)
            }
        }

        return iteration
    }

    private int flatManipulation(int manipulationMode,def model,def clone, def nodes){
        int iteration = 0
        //array
        if (model instanceof List) {
            model.each {
                iteration++
                if (iteration in nodes) {
                    manipulateNode(manipulationMode,it,it,clone,iteration)
                }
            }
        }

        //mappe
        if (model instanceof Map) {
            model.each {itKey,itValue ->
                iteration++
                if (iteration in nodes) {
                    manipulateNode(manipulationMode,itValue,itKey,clone,iteration)
                }
            }
        }

        return iteration
    }

    public def manipulateNode(int manipulationMode, def model,def key, def parent,int iteration) {
        switch (manipulationMode){
            case MODE_REMOVE:
            case MODE_REMOVE_FLAT:
                println "Removing "+key+ " from "+ parent
                if (parent instanceof Map)
                    parent.remove("$key")

                if (parent instanceof List){
                    int index = parent.indexOf(key)
                    if (index >= 0) {
                        parent.remove(index)
                    }
                }
                break;
            case MODE_INSERT:
            case MODE_INSERT_FLAT:
                def uuid = faker.uuid()
                def method = getRandomFMethod()
                println "Inserting "+uuid+" : "+method+ " in "+ parent + " after "+ iteration

                if (parent instanceof Map)
                    parent.put(uuid,method)

                if (parent instanceof List){
                    int index = parent.indexOf(key)
                    if (index >= 0)
                        parent.add(method)
                }
                break;
            case MODE_SUBSTITUTE:
            case MODE_SUBSTITUTE_FLAT:
                def uuid = faker.uuid()
                def type = getMethodType(model)
                def method = getRandomFMethod(type)
                println "Substituting "+key+ " Type: " + type + " With: " + uuid + " Method: " + method

                //generare un array o un mappa casuale se arg è array o mappa ?
                if (parent instanceof Map) {
                    parent.remove("$key")
                    parent.put(uuid,method)
                }

                if (parent instanceof List){
                    int index = parent.indexOf(key)
                    if (index >= 0) {
                        parent.remove(index)
                        parent.add(method)
                    }
                }
                break;
        }
    }

    private int deepCount(def model){
        int nodes = 1

        if (model instanceof List) {
            model.each {
                nodes += deepCount(it)
            }
        }

        if (model instanceof Map) {
            model.each {key,value ->
                nodes += deepCount(value)
            }
        }


        return nodes
    }

    private int flatCount(def model){
        int nodes = 0

        if (model instanceof List) {
            nodes = model.size()
        }

        if (model instanceof Map) {
            nodes = model.size()
        }


        return nodes
    }

    private def getMethodType(def model){
        if (model instanceof Map)
            return "map"

        if (model instanceof List)
            return "list"

        String regex = '\\$\\{([^}]+)\\}'
        model = model.replaceAll(regex, '$1')
        def method = fMethods.findAll({ it.keySet()[0] == model })[0]
        if (method)
            return method.values()[0]
        else
            return "n.d."

    }

    public def getRandomFMethod(String excludedType = null){
        if ("map".equals(excludedType)) {
            def result =[:]
            def nodes = faker.integer()
            nodes.times {
                result.put(faker.uuid(),"\${"+fMethods[faker.integer(1,fMethods.size())]?.keySet()[0]+"}")
            }
            return result
        } else if ("list".equals(excludedType)) {
            def result = []
            def nodes = faker.integer()
            nodes.times {
                result.add("\${"+fMethods[faker.integer(1, fMethods.size())]?.keySet()[0]+"}")
            }
            return result
        } else  {
            def filteredMethods = excludeMethodsByType(excludedType)
            return "\${"+filteredMethods[faker.integer(1, filteredMethods.size())].keySet()[0]+"}"
        }
    }

    private def excludeMethodsByType(String excludedType){
        if (excludedType)
            return fMethods.findAll({method -> method.values()[0] != excludedType})
        else
            return fMethods
    }


    private def fMethods = [["streetName":"string"],
            ["streetAddressNumber":"number"],
            ["streetAddress":"string"],
            ["secondaryAddress":"string"],
            ["zipCode":"string"],
            ["streetSuffix":"string"],
            ["streetPrefix":"string"],
            ["citySuffix":"string"],
            ["cityPrefix":"string"],
            ["city":"string"],
            ["cityName":"string"],
            ["state":"string"],
            ["buildingNumber":"string"],
            ["fullAddress":"string"],
            ["country":"string"],
            ["countryCode":"string"],
            ["countryCodeSL":"string"],
            ["countryCodeSL3d":"string"],
            ["capital":"string"],
            ["currency":"string"],
            ["currencyCode":"string"],
            ["fullName":"string"],
            ["firstName":"string"],
            ["lastName":"string"],
            ["timeZone":"string"],
            ["phone":"string"],
            ["mobile":"string"],
            ["validID":"string"],
            ["invalidID":"string"],
            ["validSSN":"string"],
            ["invalidSSN":"string"],
            ["creditCardNumber":"string"],
            ["creditCardExpiry":"string"],
            ["creditCardType":"string"],
            ["productName":"string"],
            ["material":"string"],
            ["price":"string"],
            ["promotionCode":"string"],
            ["companyName":"string"],
            ["suffix":"string"],
            ["industry":"string"],
            ["profession":"string"],
            ["emailAddress":"string"],
            ["domainName":"string"],
            ["domainWord":"string"],
            ["domainSuffix":"string"],
            ["url":"string"],
            ["password":"string"],
            ["integer":"number"],
            ["decimal":"number"],
            ["bool":"boolean"],
            ["uuid":"string"]
    ]
}
