package zugriff;
import zugriff.Zugriffsrechte; 
public class Zugriffstest extends Object {
 Zugriffsrechte obj;
 public void testen() {

 this.attribut1 = 3.0F;
 this.attribut2 = 5.6F;
 this.attribut3 = obj.getAttribut2();
 obj.attribut1 = 3.0F;
 obj.attribut2 = 5.6F;
 obj.attribut3 = this.getAttribut2();
 }
} 