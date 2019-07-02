package com.apifortress.apiffaker

import com.github.javafaker.Faker
import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class Main {

    static int RANDOM = 0
    static int FILL = 1
    static int REMOVE = 2
    static int INSERT = 3
    static int INSERT_FLAT = 4
    static int SUBSTITUTE = 5
    static int SUBSTITUTE_FLAT = 6

    public static void main(String[] args) {
        def example = "examples/model1.json"
        //def example = "csv/model1.csv"
        //example = "examples/model1.json"
        //fillNodes(example)

        //manipulateModel(Util.MODE_REMOVE,example,3)
        //manipulateModel(Util.MODE_REMOVE_FLAT,example,3)

        //manipulateModel(Util.MODE_INSERT,example,3)
        //manipulateModel(Util.MODE_INSERT_FLAT,example,3)

        //manipulateModel(Util.MODE_SUBSTITUTE,example,3,)
        //manipulateModel(Util.MODE_SUBSTITUTE_FLAT,example,3,)

        stressTest(500,5500,RANDOM)
        //printRandomThings()

        /*F f = new F()
        println f.password(11,11,true,true,true)*/
        //Faker faker = new Faker()
        //println faker.internet().password(false)
    }

    static void stressTest(int from,int to,int mode = 0) {
        F faker = new F()
        int tests = faker.integer(from,to)
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
            int m = mode == RANDOM ? faker.integer(FILL,SUBSTITUTE_FLAT) : mode

            switch (m){
                case FILL :
                    fillNodes(example); break;
                case REMOVE :
                case INSERT :
                case INSERT_FLAT :
                case SUBSTITUTE :
                case SUBSTITUTE_FLAT :
                    manipulateModel(m,example, nodes); break;
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

