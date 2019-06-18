package com.apifortress.apiffaker

import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class Main {

    public static void main(String[] args) {
        /*println " *****Deep"
        removeNodes('examples/model4.json',3)
        println " ****Flat"

        //removeAndFill('examples/model10.json',3)
        */

        //fillJson('examples/model2.json')
        //printRandomThings()
        removeNodes('examples/model4.json',3,false)
        insertNodes('examples/model7.json',3)
        //insertNodes('examples/model1.json',3,false)

        //Util util= new Util()
        //println util.filterMethods("string")
        //println util.getMethodType("streetName")

        //substituteNodes('examples/model7.json',3)
    }

    private static void printRandomThings(){
        F f = new F()
        println f.single("city")
        println f.single(["city","streetName"])
        println f.single([city: "city",streetname: "streetName"])
        println f.collection(2,[city: "city",streetname: "streetName"])
        println f.streetAddressNumber()
    }

    private static void fillJson(String path){
        Util util = new Util()
        File file = new File(path)
        def result = util.fillJson(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void  removeNodes(String path,int nodesToRemove,boolean deep = true){
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.removeNodes(file.getText(),nodesToRemove,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))

    }

    private static void  removeAndFill(String path,int nodesToRemove,boolean deep = true){
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.removeNodes(file.getText(),nodesToRemove,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))

        result = util.fillJson(JsonOutput.prettyPrint(JsonOutput.toJson(result)))
        println "Filled"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void insertNodes(String path, int notedToInsert, boolean deep = true){
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.insertNodes(file.getText(),notedToInsert,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
        result = util.fillJson(JsonOutput.prettyPrint(JsonOutput.toJson(result)))
        println "Filled"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void substituteNodes(String path, int notedToInsert, boolean deep = true){
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.substituteNodes(file.getText(),notedToInsert,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
        /*result = util.fillJson(JsonOutput.prettyPrint(JsonOutput.toJson(result)))
        println "Filled"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))*/
    }




}

