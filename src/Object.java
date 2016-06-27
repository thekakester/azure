
public class Object extends Entity {
	ObjectInfo info;
	
	public Object(Scene scene, ObjectInfo info, int x, int y) {
		super(scene, info.sprite,x,y);
		
		this.info = info;

		boolean shift = info.shift;
		if (!shift) { this.shift = 0; }	//Else use default
		passable = info.passable;
		this.idle = true;
	}
	
	@Override
	public String getName() {
		return info.name;
	}
}
