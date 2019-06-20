package com.apifortress.apiffaker

import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class Main {

    public static void main(String[] args) {
        def example = "examples/model1BIS2.json"
        fillNodes(example)
        //removeNodes(example,3,false)
        //insertNodes(example,3)
        //substituteNodes(example,3)
        //substituteNodes(example,3,false)
        //stressTest()
        //printRandomThings()
    }

    static void stressTest() {
        F faker = new F()
        int tests = faker.integer(5000,50000)
        int iteration = 0
        println "Tests " + tests
        tests.times {
            iteration++
            println "*** Test number " + iteration
            int nodes = faker.integer(1, 10)
            println "***** Nodes " + nodes
            def exampleDesinence = faker.integer(1,10)
            def example = "examples/model"+exampleDesinence+".json"
            println example
            def m = faker.integer(1,4)
            switch (m){
                case 1 : substituteNodes(example, nodes); break;
                case 2 : fillNodes(example); break;
                case 3 : removeNodes(example,nodes); break;
                case 4 : insertNodes(example,nodes); break;
            }
        }
    }

    private static void printRandomThings(){
        F f = new F()
        println f.single("city")
        println f.single(["city","streetName"])
        println f.single([city: "city",streetname: "streetName"])
        println f.collection(2,[city: "city",streetname: "streetName"])
        println f.single(["x1","x2"])
        println f.streetAddressNumber()
        println f.domainName()
        println f.domainWord()
    }

    private static void fillNodes(String path){
        println "fill"
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.fillNodes(file.getText())
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void  removeNodes(String path,int nodesToRemove,boolean deep = true){
        println "remove"
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.removeNodes(file.getText(),nodesToRemove,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))

    }


    private static void insertNodes(String path, int notedToInsert, boolean deep = true){
        println "insert"
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.insertNodes(file.getText(),notedToInsert,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void substituteNodes(String path, int notedToInsert, boolean deep = true){
        println "substitute"
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.substituteNodes(file.getText(),notedToInsert,deep)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }




}

