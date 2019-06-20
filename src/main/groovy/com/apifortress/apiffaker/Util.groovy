package com.apifortress.apiffaker


import groovy.json.JsonSlurper
import org.omg.CORBA.MARSHAL

class Util {

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
    public def fillNodes(String json){
        def result
        def jsonSlurper = new JsonSlurper().parseText(json)

        result = faker.single(jsonSlurper)

        return result
    }

    /**
     * remove from json model notedToRemove nodes. can work both in depth mode or just the current level
     * @param json model
     * @param nodesToRemove nodes to remove
     * @param deep if true removes in depth if false remove nodes at current level
     * @return
     */
    public def removeNodes(String json,int nodesToRemove,boolean deep = true){
        def arg = new JsonSlurper().parseText(json)
        def clone = new JsonSlurper().parseText(json)

        int countNodes = deep ? countNodesInDepth(arg) : countNodesFlat(arg)

        def nodes = faker.integerList(2,countNodes,nodesToRemove)

        println "Nodes count " + countNodes
        println "Nodes to remove "+nodes

        if (deep) {
            println "Deep"
            removeNodeInDepth(arg, clone, nodes, 0)
        } else {
            println "Flat"
            removeNodeFlat(arg, clone, nodes, 0)
        }

        return clone
    }

    private int removeNodeInDepth(def arg,def clone, def nodes, int iteration, def key = null,def parent = null){
        iteration++

        if (iteration in nodes) {
            println "Removing "+key+ " from "+ parent
            if (parent instanceof Map)
                parent.remove("$key")

            if (parent instanceof List){
                int index = parent.indexOf(key)
                if (index >= 0) {
                    parent.remove(index)
                }
            }
        }
        //array
        if (arg instanceof List) {
            arg.each {
                int index = clone.indexOf(it)
                iteration = removeNodeInDepth(it,clone[index], nodes, iteration,it,clone)
            }
        }

        //mappe
        if (arg instanceof Map) {
            arg.each {itKey,itValue ->
                iteration = removeNodeInDepth(itValue, clone."${itKey}", nodes, iteration,itKey,clone)
            }
        }

        return iteration
    }

    private int removeNodeFlat(def arg,def clone, def nodes, int iteration){

        //array
        if (arg instanceof List) {
            arg.each {
                iteration++
                if (iteration in nodes) {
                    println "Removing "+it+ " from "+ arg
                    int index = clone.indexOf(it)
                    if (index >= 0) {
                        clone.remove(index)
                    }

                }
            }
        }

        //mappe
        if (arg instanceof Map) {
            arg.each {itKey,itValue ->
                iteration++
                if (iteration in nodes) {
                    println "Removing "+itKey+ " from "+ arg
                    clone.remove("$itKey")
                }
            }
        }

        return iteration
    }

    /**
     * inserts in a json model nodesToInsert nodes. can work both in depth mode or just the current level
     * @param json model
     * @param nodesToInsert nodes to be inserted
     * @param deep if true removes in depth if false inserts nodes at current level
     * @return
     */
    public def insertNodes(String json,int nodesToInsert,boolean deep = true){
        def arg = new JsonSlurper().parseText(json)
        def clone = new JsonSlurper().parseText(json)

        int countNodes = deep ? countNodesInDepth(arg) : countNodesFlat(arg)

        def nodes = faker.integerList(2,countNodes,nodesToInsert)

        println "Nodes count " + countNodes
        println "Insert after Nodes "+nodes

        if (deep) {
            println "Deep"
            insertNodeInDepth(arg, clone, nodes, 0)
        } else {
            println "Flat"
            insertNodeFlat(arg, clone, nodes, 0)
        }

        return clone
    }

    private int insertNodeInDepth(def arg,def clone, def nodes, int iteration, def key = null,def parent = null) {
        iteration++

        if (iteration in nodes) {
            def uuid = faker.uuid()
            def method = getFMethod()

            if (parent instanceof Map){
                println "Inserting "+uuid+" : "+method+ " in "+ parent + " after "+ iteration + "(item: " + clone +" )"
                parent.put(uuid,"\${"+method+"}")
            }

            if (parent instanceof List){
                int index = parent.indexOf(key)
                if (index >= 0) {
                    println "Inserting "+ method+ " in "+ parent + " after "+ iteration + "(item: " + clone +" )"
                    parent.add("\${"+method+"}")
                }
            }
        }
        //array
        if (arg instanceof List) {
            arg.each {
                int index = clone.indexOf(it)
                iteration = insertNodeInDepth(it,clone[index], nodes, iteration,it,clone)
            }
        }

        //mappe
        if (arg instanceof Map) {
            arg.each {itKey,itValue ->
                iteration = insertNodeInDepth(itValue, clone."${itKey}", nodes, iteration,itKey,clone)
            }
        }

        return iteration
    }

    private int insertNodeFlat(def arg,def clone, def nodes, int iteration){

        //array
        if (arg instanceof List) {
            arg.each {
                iteration++
                def uuid = faker.uuid()
                def method = getFMethod()
                if (iteration in nodes) {
                    int index = clone.indexOf(it)
                    if (index >= 0) {
                        println "Insertin "+ method+ " in "+ clone + " after "+ iteration
                        clone.add("\${"+method+"}")
                    }

                }
            }
        }

        //mappe
        if (arg instanceof Map) {
            arg.each {itKey,itValue ->
                iteration++
                def uuid = faker.uuid()
                def method = getFMethod()
                if (iteration in nodes) {
                    println "Inserting "+uuid+" : "+method+ " in "+ clone + " after "+ iteration
                    clone.put(uuid,"\${"+method+"}")
                }
            }
        }

        return iteration
    }

    /**
     * substitutes in a json model nodesToInsert nodes. can work both in depth mode or just the current level
     * @param json model
     * @param nodesToInsert nodes to be substituted
     * @param deep if true removes in depth if false substitutes nodes at current level
     * @return
     */
    public def substituteNodes(String json,int nodesToSubstitute,boolean deep = true){
        def arg = new JsonSlurper().parseText(json)
        def clone = new JsonSlurper().parseText(json)

        int countNodes = deep ? countNodesInDepth(arg) : countNodesFlat(arg)

        def nodes = faker.integerList(2,countNodes,nodesToSubstitute)
        println "Nodes count " + countNodes
        println "Substitutings Nodes "+nodes

        if (deep) {
            println "Deep"
            substituteNodeInDepth(arg, clone, nodes, 0)
        } else {
            println "Flat"
            substituteNodeFlat(arg, clone, nodes, 0)
        }

        return clone
    }

    private int substituteNodeInDepth(def arg,def clone, def nodes, int iteration, def key = null,def parent = null){
        iteration++

        if (iteration in nodes) {
            def uuid = faker.uuid()
            def type = getArgType(arg)
            def method = getFMethod(type)
            println "Substituting "+key+ " Type: " + type + " With: " + uuid + " Method: " + method

            if (parent instanceof Map) {
                parent.remove("$key")
                parent.put(uuid,"\${"+method+"}")
            }

            if (parent instanceof List){
                int index = parent.indexOf(key)
                if (index >= 0) {
                    parent.remove(index)
                    parent.add("\${"+method+"}")
                }
            }
        }
        //array
        if (arg instanceof List) {
            arg.each {
                int index = clone.indexOf(it)
                iteration = substituteNodeInDepth(it,clone[index], nodes, iteration,it,clone)
            }
        }

        //mappe
        if (arg instanceof Map) {
            arg.each {itKey,itValue ->
                iteration = substituteNodeInDepth(itValue, clone."${itKey}", nodes, iteration,itKey,clone)
            }
        }

        return iteration
    }

    private int substituteNodeFlat(def arg,def clone, def nodes, int iteration){

        //array
        if (arg instanceof List) {
            arg.each {
                iteration++

                if (iteration in nodes) {
                    int index = clone.indexOf(it)
                    if (index >= 0) {
                        def uuid = faker.uuid()
                        def type = getArgType(arg)
                        def method = getFMethod(type)
                        println "Substituting "+arg+ " Type: " + type + " With: " + uuid + " Method: " + method
                        clone.remove(index)
                        clone.add("\${"+method+"}")
                    }

                }
            }
        }

        //mappe
        if (arg instanceof Map) {
            arg.each {itKey,itValue ->
                iteration++

                if (iteration in nodes) {
                    def uuid = faker.uuid()
                    def type = getArgType(itValue)
                    def method = getFMethod(type)
                    println "Substituting "+itKey+ " Type: " + type + " With: " + uuid + " Method: " + method
                    clone.remove("$itKey")
                    clone.put(uuid,"\${"+method+"}")
                }
            }
        }

        return iteration
    }


    private int countNodesInDepth(def arg){
        int nodes = 1

        if (arg instanceof List) {
            arg.each {
                nodes += countNodesInDepth(it)
            }
        }

        if (arg instanceof Map) {
            arg.each {key,value ->
                nodes += countNodesInDepth(value)
            }
        }


        return nodes
    }

    private int countNodesFlat(def arg){
        int nodes = 0

        if (arg instanceof List) {
            nodes = arg.size()
        }

        if (arg instanceof Map) {
            nodes = arg.size()
        }


        return nodes
    }

    private def getFMethod(String excludedType = null){
        if (excludedType) {
            def filteredMethods = filterMethods(excludedType)
            return filteredMethods[faker.integer(1, filteredMethods.size())].keySet()[0]
        }
        else
            return fMethods[faker.integer(1,fMethods.size())]?.keySet()[0]
    }

    private def filterMethods(String excludedType){
        if (excludedType)
            return fMethods.findAll({method -> method.values()[0] != excludedType})
        else return fMethods
    }

    private def getArgType(def arg){
        if (arg instanceof Map || arg instanceof List)
            return null

        def method = fMethods.findAll({it.keySet()[0] == arg})[0]
        if (method)
            return method.values()[0]
        else return null
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
