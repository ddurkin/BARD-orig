package bard.core.rest.spring.experiment

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PriotityElementWithVanderBiltUnitSpec extends Specification {
    @Shared
    ObjectMapper objectMapper = new ObjectMapper()
    @Shared
    String VANDER_BILT_EXAMPLE_1 = '''
    {
     "displayName":"AvgGluPotency",
     "dictElemId":961,
     "responseUnit":"um",
     "testConcUnit":"uM",
     "value":"4.09e-007",
     "primaryElements":
     [
        {
           "displayName":"GluPotencyExperiment1",
           "dictElemId":961,
           "value":"4.04e-007",
           "concResponseSeries":{
              "testConcUnit":"uM",
              "crSeriesDictId":1016,
              "concRespParams":{
                 "s0":null,
                 "sInf":null,
                 "hillCoef":null,
                 "logEc50":-6.393618634889395
              },
              "concRespPoints":
              [
                 {
                    "testConc":1000.0,
                    "value":"104.181",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_1000_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"105.693"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_1000_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"102.669"
                       },
                       {
                          "displayName":"StddevForExperiment3_1000uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"2.13829"
                       }
                    ]
                 },
                 {
                    "testConc":100.0,
                    "value":"115.726",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"113.668"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"117.784"
                       },
                       {
                          "displayName":"StddevForExperiment3_100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"2.91045"
                       }
                    ]
                 },
                 {
                    "testConc":31.6,
                    "value":"124.864",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_31.6_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"128.757"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_31.6_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"120.971"
                       },
                       {
                          "displayName":"StddevForExperiment3_31.6uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"5.50553"
                       }
                    ]
                 },
                 {
                    "testConc":10.0,
                    "value":"122.099",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_10.0_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"117.857"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_10.0_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"126.34"
                       },
                       {
                          "displayName":"StddevForExperiment3_10.0uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"5.99839"
                       }
                    ]
                 },
                 {
                    "testConc":3.16,
                    "value":"119.399",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_3.16_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"122.892"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_3.16_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"115.907"
                       },
                       {
                          "displayName":"StddevForExperiment3_3.16uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"4.93914"
                       }
                    ]
                 },
                 {
                    "testConc":1.0,
                    "value":"101.104",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_1.00_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"99.219"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_1.00_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"102.988"
                       },
                       {
                          "displayName":"StddevForExperiment3_1.00uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"2.66509"
                       }
                    ]
                 },
                 {
                    "testConc":0.316,
                    "value":"50.192",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_0.316_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"47.263"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.316_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"53.121"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.316uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"4.14223"
                       }
                    ]
                 },
                 {
                    "testConc":0.1,
                    "value":"21.6415",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_0.100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"23.679"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"19.604"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"2.88146"
                       }
                    ]
                 },
                 {
                    "testConc":0.01,
                    "value":"8.129",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.0100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"12.104"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.0100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"4.154"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.0100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"5.6215"
                       }
                    ]
                 },
                 {
                    "testConc":0.001,
                    "value":"4.799",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.00100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"5.02"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.00100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"4.578"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.00100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"0.312541"
                       }
                    ]
                 }
              ]
           }
        },
        {
           "displayName":"GluPotencyExperiment2",
           "dictElemId":961,
           "value":"4.32e-007",
           "concResponseSeries":{
              "testConcUnit":"uM",
              "crSeriesDictId":1016,
              "concRespParams":{
                 "s0":null,
                 "sInf":null,
                 "hillCoef":null,
                 "logEc50":-6.364516253185088
              },
              "concRespPoints":[
                 {
                    "testConc":1000.0,
                    "value":"104.181",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_1000_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"105.693"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_1000_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"102.669"
                       },
                       {
                          "displayName":"StddevForExperiment3_1000uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"2.13829"
                       }
                    ]
                 },
                 {
                    "testConc":100.0,
                    "value":"115.726",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"113.668"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"117.784"
                       },
                       {
                          "displayName":"StddevForExperiment3_100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"2.91045"
                       }
                    ]
                 },
                 {
                    "testConc":31.6,
                    "value":"124.864",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_31.6_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"128.757"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_31.6_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"120.971"
                       },
                       {
                          "displayName":"StddevForExperiment3_31.6uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"5.50553"
                       }
                    ]
                 },
                 {
                    "testConc":10.0,
                    "value":"122.099",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_10.0_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"117.857"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_10.0_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"126.34"
                       },
                       {
                          "displayName":"StddevForExperiment3_10.0uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"5.99839"
                       }
                    ]
                 },
                 {
                    "testConc":3.16,
                    "value":"119.399",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_3.16_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"122.892"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_3.16_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"115.907"
                       },
                       {
                          "displayName":"StddevForExperiment3_3.16uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"4.93914"
                       }
                    ]
                 },
                 {
                    "testConc":1.0,
                    "value":"101.104",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_1.00_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"99.219"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_1.00_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"102.988"
                       },
                       {
                          "displayName":"StddevForExperiment3_1.00uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"2.66509"
                       }
                    ]
                 },
                 {
                    "testConc":0.316,
                    "value":"50.192",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.316_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"47.263"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.316_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"53.121"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.316uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"4.14223"
                       }
                    ]
                 },
                 {
                    "testConc":0.1,
                    "value":"21.6415",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"23.679"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"19.604"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"2.88146"
                       }
                    ]
                 },
                 {
                    "testConc":0.01,
                    "value":"8.129",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.0100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"12.104"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.0100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"4.154"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.0100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"5.6215"
                       }
                    ]
                 },
                 {
                    "testConc":0.001,
                    "value":"4.799",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.00100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"5.02"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.00100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"4.578"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.00100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"0.312541"
                       }
                    ]
                 }
              ]
           }
        },
        {
           "displayName":"GluPotencyExperiment3",
           "dictElemId":961,
           "value":"3.92e-007",
           "concResponseSeries":{
              "testConcUnit":"uM",
              "crSeriesDictId":1016,
              "concRespParams":{
                 "s0":null,
                 "sInf":null,
                 "hillCoef":null,
                 "logEc50":-6.406713932979542
              },
              "concRespPoints":[
                 {
                    "testConc":1000.0,
                    "value":"104.181",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_1000_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"105.693"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_1000_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"102.669"
                       },
                       {
                          "displayName":"StddevForExperiment3_1000uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":1000.0,
                          "value":"2.13829"
                       }
                    ]
                 },
                 {
                    "testConc":100.0,
                    "value":"115.726",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"113.668"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"117.784"
                       },
                       {
                          "displayName":"StddevForExperiment3_100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":100.0,
                          "value":"2.91045"
                       }
                    ]
                 },
                 {
                    "testConc":31.6,
                    "value":"124.864",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_31.6_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"128.757"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_31.6_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"120.971"
                       },
                       {
                          "displayName":"StddevForExperiment3_31.6uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":31.6,
                          "value":"5.50553"
                       }
                    ]
                 },
                 {
                    "testConc":10.0,
                    "value":"122.099",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_10.0_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"117.857"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_10.0_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"126.34"
                       },
                       {
                          "displayName":"StddevForExperiment3_10.0uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":10.0,
                          "value":"5.99839"
                       }
                    ]
                 },
                 {
                    "testConc":3.16,
                    "value":"119.399",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_3.16_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"122.892"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_3.16_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"115.907"
                       },
                       {
                          "displayName":"StddevForExperiment3_3.16uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":3.16,
                          "value":"4.93914"
                       }
                    ]
                 },
                 {
                    "testConc":1.0,
                    "value":"101.104",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_1.00_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"99.219"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_1.00_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"102.988"
                       },
                       {
                          "displayName":"StddevForExperiment3_1.00uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":1.0,
                          "value":"2.66509"
                       }
                    ]
                 },
                 {
                    "testConc":0.316,
                    "value":"50.192",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.316_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"47.263"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.316_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"53.121"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.316uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.316,
                          "value":"4.14223"
                       }
                    ]
                 },
                 {
                    "testConc":0.1,
                    "value":"21.6415",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"23.679"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"19.604"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.1,
                          "value":"2.88146"
                       }
                    ]
                 },
                 {
                    "testConc":0.01,
                    "value":"8.129",
                    "childElements":[
                       {
                          "displayName":"Rep2ForExperiment3_0.0100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"12.104"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.0100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"4.154"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.0100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.01,
                          "value":"5.6215"
                       }
                    ]
                 },
                 {
                    "testConc":0.001,
                    "value":"4.799",
                    "childElements":
                    [
                       {
                          "displayName":"Rep2ForExperiment3_0.00100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"5.02"
                       },
                       {
                          "displayName":"Rep1ForExperiment3_0.00100_uM",
                          "dictElemId":1016,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"4.578"
                       },
                       {
                          "displayName":"StddevForExperiment3_0.00100uM",
                          "dictElemId":613,
                          "testConcUnit":"uM",
                          "testConc":0.001,
                          "value":"0.312541"
                       }
                    ]
                 }
              ]
           }
        }
     ],
     "childElements":
     [
        {
           "displayName":"StddevGluPotency",
           "dictElemId":613,
           "value":"2.06e-008"
        },
        {
           "displayName":"SEMGluPotency",
           "dictElemId":1335,
           "value":"1.19e-008"
        }
     ]
  }
'''

    void assertActivityConcentration(final ActivityConcentration activityConcentration) {
        final ConcentrationResponseSeries concentrationResponseSeries = activityConcentration.concentrationResponseSeries
        assert concentrationResponseSeries
        final List<ConcentrationResponsePoint> concentrationResponsePoints = concentrationResponseSeries.concentrationResponsePoints
        assert concentrationResponsePoints
        assert concentrationResponsePoints.size() > 1
        for (ConcentrationResponsePoint concentrationResponsePoint in concentrationResponsePoints) {
            assert concentrationResponsePoint.testConc
            assert concentrationResponsePoint.value
        }
        final CurveFitParameters curveFitParameters = concentrationResponseSeries.concRespParams
        assert curveFitParameters
        assert !curveFitParameters.s0
        assert !curveFitParameters.hillCoef
        assert !curveFitParameters.SInf
        assert curveFitParameters.logEc50
        final List<ActivityData> miscDataList = concentrationResponseSeries.miscData

        assert !miscDataList
    }

    void assertChildElements(final List<ActivityData> childElements) {

        for (ActivityData activityData : childElements) {
            assertActivityData(activityData)
        }
    }

    void assertActivityData(final ActivityData activityData) {
        assert activityData.displayName
        assert activityData.dictElemId
        assert activityData.value
    }

    void assertPrimaryElements(final List<ActivityConcentration> primaryElements) {
        for (ActivityConcentration activityConcentration : primaryElements) {
            assertActivityConcentration(activityConcentration)
        }
    }

    void "test all JSON"() {
        when:
        PriorityElement priorityElement = objectMapper.readValue(VANDER_BILT_EXAMPLE_1, PriorityElement.class)
        then:
        assert priorityElement.displayName == "AvgGluPotency"
        assert priorityElement.dictElemId == 961
        assert priorityElement.responseUnit == "um"
        assert priorityElement.testConcentrationUnit == "uM"
        assert priorityElement.value

        //assert primaryElements
        final List<ActivityConcentration> primaryElements = priorityElement.primaryElements
        assert primaryElements
        assert primaryElements.size() == 3
        assertPrimaryElements(primaryElements)

        //assert childElements
        final List<ActivityData> childElements = priorityElement.childElements
        assert childElements
        assert childElements.size() == 2
        assertChildElements(childElements)
        assert !priorityElement.concentrationResponseSeries
    }
}

