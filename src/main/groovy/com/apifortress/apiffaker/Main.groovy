package com.apifortress.apiffaker

import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class Main {

    public static void main(String[] args) {
        def example = "examples/model1.json"

        //manipulateModel(Util.MODE_REMOVE,example,3)
        //manipulateModel(Util.MODE_REMOVE_FLAT,example,3)

        //manipulateModel(Util.MODE_INSERT,example,3,)
        //manipulateModel(Util.MODE_INSERT_FLAT,example,3,)

        //manipulateModel(Util.MODE_SUBSTITUTE,example,3,)
        //manipulateModel(Util.MODE_SUBSTITUTE_FLAT,example,3,)

        stressTest(5,10,4)
        //printRandomThings()
    }

    static void stressTest(int i,int j,int m = 0) {
        F faker = new F()
        int tests = faker.integer(i,j)
        int iteration = 0
        println "Tests " + tests
        tests.times {
            iteration++
            println "*** Test number " + iteration
            int nodes = faker.integer(1, 20)
            println "***** Nodes " + nodes
            def exampleDesinence = faker.integer(1,9)
            def example = "examples/model"+exampleDesinence+".json"
            println example
            m = m == 0 ? faker.integer(1,4) : m

            switch (m){
                case 1 : fillNodes(example); break;
                case 2 : manipulateModel(Util.MODE_REMOVE,example,nodes); break;
                case 3 : manipulateModel(Util.MODE_INSERT,example,nodes); break;
                case 4 : manipulateModel(Util.MODE_SUBSTITUTE,example, nodes); break;
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

        def result = util.fillModel(file.getText())
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void  manipulateModel(int mode,String path,int nodesToRemove,boolean deep = true){
        println "MANIPULATE"
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.manipulateModel(mode,file.getText(),nodesToRemove)
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))

    }




}

