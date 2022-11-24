int Byte_Entrada=0;
int cont=0,estado=0,infra=0;
void setup() 
{
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(13,OUTPUT);
  digitalWrite(13,LOW);
  pinMode(30,OUTPUT); //Pin sensor emisor
  pinMode(8,INPUT); //Pin sensor receptor

}
void loop() 
  {
    // put your main code here, to run repeatedly:
    //infra
    //infra=analogRead(8);
    //if(infra==0)
    //{
      //delay(500);
      //motor==1;
    //}  
    //else ( infra== 1)
    //{
      //digitalWrite(13,LOW);
      //estado=0;
    //}
    
    //infra
    if(Serial.available()>0)
    {
      cont=1;
      Byte_Entrada=Serial.read();
      if(Byte_Entrada=='0')
      {
        digitalWrite(13,LOW);
        Serial.println("Apagado");
        delay(100);
      }
      else if(Byte_Entrada=='1')
      {
        digitalWrite(13,HIGH);
        Serial.println("Encendido");
        delay(100);
      }
    }
}
