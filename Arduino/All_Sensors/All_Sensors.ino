#include <SparkFun_ADS1015_Arduino_Library.h>
#include <SparkFun_Bio_Sensor_Hub_Library.h>
#include <Wire.h>

ADS1015 flexSensor;

ADS1015 adcSensor;

const int resPin = 4;
const int mfioPin = 5;
SparkFun_Bio_Sensor_Hub bioHub(resPin, mfioPin); 

bioData body; 

void setup() {
  Wire.begin();
  Serial.begin(115200);

  // Initializing Flex Sensors [Flex sensors connected to mux port 0 and 1]
  for (int i = 0; i < 2; i++){
      enableMuxPort(i);
      if (!flexSensor.begin()) {
         Serial.println("ERROR: Flex sensor not found.");
         while (1);
      }
      flexSensor.setGain(ADS1015_CONFIG_PGA_TWOTHIRDS);
      disableMuxPort(i);
      delay(10);
  }

  // Initializing Pulse Sensor [Pulse sensor connected to mux port 2]
  enableMuxPort(2);
  int result = bioHub.begin();
  if (result) {
    Serial.println("ERROR: Pulse sensor not found.");
    while (1);
  }
  int error = bioHub.configBpm(MODE_ONE);
  if (error) {
    Serial.println("ERROR: Sensor configuration failed.");
    Serial.println(error); 
    while (1);
  }
  delay(4000); 
  disableMuxPort(2);

  // Initializing ADC [ADC connected to mux port 3]
  enableMuxPort(3);
  if (!adcSensor.begin())
  {
    Serial.println("ERROR: ADC not found.");
    while (1);
  }
  disableMuxPort(3);

}
void readFlex(int i){
  enableMuxPort(i);
  uint16_t data;
  for (int finger = 0; finger < 2; finger++) {
    data = flexSensor.getAnalogData(finger);
    Serial.print(data);
    Serial.print(",");
  }
  
  disableMuxPort(i);
}

void getHeartRate(){
  enableMuxPort(2);
  body = bioHub.readBpm();
  Serial.print(body.heartRate); 
  Serial.print(",");
  Serial.print(body.confidence); 
  Serial.print(",");
  Serial.print(body.oxygen); 
  Serial.print(",");
  Serial.print(body.status); 
  Serial.print(",");
  disableMuxPort(2);
}

void getFSRVal(int channel){
  enableMuxPort(3);
  uint16_t channel_A3 = adcSensor.getSingleEnded(channel);
  Serial.print(channel_A3);
  Serial.print(",");
  disableMuxPort(3);
}

void loop() {  
  readFlex(0);
  readFlex(1);
  getHeartRate();
  getFSRVal(2);
  Serial.println();
}
