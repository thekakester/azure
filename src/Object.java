
public class Object extends Entity {
	public Object(Scene scene, int objectID, int x, int y) {
		super(scene, Sprites.OBJECTS,x,y);

		boolean shift = ObjectProperties.SHIFT.get(objectID);
		if (!shift) { this.shift = 0; }	//Else use default
		passable = ObjectProperties.PASSABLE.get(objectID);
		this.idle = true;
		sprite.setAnimation(objectID);
	}
}
