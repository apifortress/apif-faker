package com.apifortress.apiffaker

import com.github.javafaker.Faker
import com.opencsv.CSVReader
import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class Main {

    static int RANDOM = 0

    public static void main(String[] args) {
        def example = "examples/model9.json"
        //fillModel(example)
        //def example = "csv/model1.csv"
        //fillCsv(example)


        //manipulateModel(Util.MODE_REMOVE,example,3)
        //manipulateModel(Util.MODE_REMOVE_FLAT,example,3)

        //manipulateModel(Util.MODE_INSERT,example,3)
        //manipulateModel(Util.MODE_INSERT_FLAT,example,3)

        //manipulateModel(Util.MODE_SUBSTITUTE,example,3,)
        //manipulateModel(Util.MODE_SUBSTITUTE_FLAT,example,3,)

        stressTest(50,100,RANDOM)
        //stressTest(50,100,Util.MODE_SUBSTITUTE)
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
            int nodes = faker.integer(1, 3)
            println "***** Nodes " + nodes
            def exampleDesinence = faker.integer(1,9)
            def example = "examples/model"+exampleDesinence+".json"
            println example
            int m = mode == RANDOM ? faker.integer(Util.MODE_FILL,Util.MODE_SUBSTITUTE_FLAT) : mode

            switch (m){
                case Util.MODE_FILL:
                    fillModel(example); break;
                case Util.MODE_REMOVE :
                case Util.MODE_REMOVE_FLAT :
                case Util.MODE_INSERT :
                case Util.MODE_INSERT_FLAT :
                case Util.MODE_SUBSTITUTE :
                case Util.MODE_SUBSTITUTE_FLAT :
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

    private static void fillModel(String path){
        println "fill"
        Util util = new Util()
        File file = new File(path)
        def jsonSlurper = new JsonSlurper().parseText(file.getText())
        println JsonOutput.prettyPrint(JsonOutput.toJson(jsonSlurper))

        def result = util.fillModel(file.getText())
        println "Result"
        println JsonOutput.prettyPrint(JsonOutput.toJson(result))
    }

    private static void fillCsv(String path){
        println "fillCsv"
        File file = new File(path)
        def util = new Util();
        def lines = new CSVReader(new StringReader(file.getText())).readAll()
        def result = new StringBuilder()

        lines.each {line ->
            result.append(util.fillModel((List) line).join(", "))
        }

        println file.getText()

        println "Result"
        println result //JsonOutput.prettyPrint(JsonOutput.toJson(result))
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

