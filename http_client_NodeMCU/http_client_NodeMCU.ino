/**
 * BasicHTTPClient.ino
 *
 *  Created on: 25.06.2018
 *
 */

#include <Arduino.h>

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

#include <ESP8266HTTPClient.h>

#include "DHTesp.h"

#ifdef ESP32
#pragma message(THIS EXAMPLE IS FOR ESP8266 ONLY!)
#error Select ESP8266 board.
#endif

DHTesp dht;

ESP8266WiFiMulti WiFiMulti;

const char* ssid = "DIMAp-Visitante";
const char* password = "v1s1t4nt3@d1m4p";
String ORION = "10.9.100.132";

void setup() {
    Serial.begin(115200);
    WiFi.mode(WIFI_STA);
    WiFiMulti.addAP(ssid, password);

    dht.setup(2, DHTesp::DHT11); // Connect DHT sensor to GPIO 2
}

void loop() {


   
    // wait for WiFi connection
    if((WiFiMulti.run() == WL_CONNECTED)) {

        HTTPClient http;

        int humidity = dht.getHumidity();
        int temperature = dht.getTemperature();
        Serial.print(dht.getStatusString());
        Serial.print("\t");
        Serial.print(humidity, 1);
        Serial.print("\t\t");
        Serial.print(temperature, 1);
        Serial.print("\t\t");
        Serial.print(dht.computeHeatIndex(temperature, humidity, false), 1);

        Serial.print("[HTTP] begin...\n");
  
        http.begin("http://"+ORION+":1026/v1/updateContext"); //HTTP
        http.addHeader("Content-Type", "application/json");
        http.addHeader("Accept", "application/json");
 
        Serial.print("[HTTP] POST...\n");

        String context = "{ \"contextElements\": [ { \"type\": \"Coleira\", \"isPattern\": \"false\", \"id\": \"c1\", \"attributes\": [ { \"name\": \"Umidade\", \"type\": \"Int\", \"value\": \""+String(humidity)+"\" }, { \"name\":\"Temperatura\", \"type\": \"Int\", \"value\": \""+String(temperature)+"\" } ] } ], \"updateAction\": \"UPDATE\" }";
        
        int httpCode = http.POST(context);

        // httpCode will be negative on error
        if(httpCode > 0) {
            // HTTP header has been send and Server response header has been handled
            Serial.printf("[HTTP] POST... code: %d\n", httpCode);

            // file found at server
            if(httpCode == HTTP_CODE_OK) {
                String payload = http.getString();
                Serial.println(payload);
            }
        } else {
            Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
        }
        http.end();
    }

    delay(4000);
}

