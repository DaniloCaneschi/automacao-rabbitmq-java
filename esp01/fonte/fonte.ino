#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include<PubSubClient.h>
#include<ArduinoJson.h>

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;

String macEsp;

// Usuário do rabbitmq
const char* usuarioRabbitmq  = "usuário";
const char* senhaRabbitmq    = "senha";
const char* servidorRabbitmq = "endereço do servidor";

// Dados do wifi
const char* ssid     = "ssid da senha wifi";
const char* password = "senha";

// topicos
const char* topicoRegistroEsp = "REGISTRO_ESP";
const char* topicoStatusEsp   = "STATUS_ESP";

const int pinOut0 = 0;

void setup(void){
  // Configura o rele como saída 
  pinMode(pinOut0, OUTPUT); 
  digitalWrite(pinOut0, LOW);
  
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  Serial.println("");

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println(".");
  }
 
  Serial.println("Conectado ao wifi.");
  
  client.setServer(servidorRabbitmq, 1883);
  client.setCallback(callback);  
}

void callback(char* topic, byte* payload, unsigned int length) {  
  String json = "";
  StaticJsonDocument<256> doc;
  
  for (int i = 0; i < length; i++) {
    json += (char)payload[i];
  }

  deserializeJson(doc, json);
  
  if (doc["ativar"] == true) {
    digitalWrite(pinOut0, HIGH);    
  } else {
    digitalWrite(pinOut0, LOW);
  }
}

void reconnect() {  
  while (!client.connected()) {
    Serial.print("conectando ao rabbitmq...");

    macEsp = WiFi.macAddress();
    
    String clientId = macEsp;   
    
    if (client.connect(clientId.c_str(), usuarioRabbitmq, senhaRabbitmq)) {
      Serial.println("connected");      

      macEsp = WiFi.macAddress();
           
      StaticJsonDocument<200> doc;

      doc["identificador"] = macEsp.c_str();

      String json;
      serializeJson(doc, json);

      client.publish(topicoRegistroEsp, (char*)json.c_str());
      client.subscribe(macEsp.c_str());        
    } else {
      Serial.print("Falha ao conectar");
      Serial.print(client.state());     
      delay(5000);
    }
  }
}

void loop(void){
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  long now = millis();
  if (now - lastMsg > 5000) {
    lastMsg = now;   
   
    StaticJsonDocument<200> doc;

    doc["codigo"] = macEsp;
    doc["ativo"] = (digitalRead(pinOut0) == HIGH);

    String json;
    serializeJson(doc, json);
    client.publish(topicoStatusEsp, (char*)json.c_str());
  }
}
