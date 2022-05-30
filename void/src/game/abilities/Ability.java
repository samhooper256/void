package game.abilities;

public enum Ability {
	SANDSTORM("Sandstorm", "Bart throws 10 times faster for 10 seconds.", (long) (10 * 60e9)); //10min cool
	
	private final String displayName, description;
	private final long cooldown;
	
	Ability(String displayName, String description, long cooldown) {
		this.displayName = displayName;
		this.description = description;
		this.cooldown = cooldown;
	}
	
	public String displayName() {
		return displayName;
	}
	
	public String description() {
		return description;
	}
	
	public long cooldown() {
		return cooldown;
	}
	
}
