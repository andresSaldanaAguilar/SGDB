import java.io.*;
class Objeto implements Serializable{ //The serializable class converts an object in a bunch of bytes
	int x;
	int y;
	Objeto(int x, int y){
		this.x = x;
		this.y = y;
	}	
}
