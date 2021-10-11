package de.kekz.tello.utils.commands;

public enum ControlCommand {

	ENTER_SDK_MODE("command"),
	EMERGENCY("emergency"),
	
	TAKEOFF("takeoff"),
	LAND("land"),
	
	ACTIVATE_VIDEO("streamon"),
	DEACTIVATE_VIDEO("streamoff"),
	
	UP("up", 20, 500),
	DOWN("down", 20, 500),
	LEFT("left", 20, 500),
	RIGHT("right", 20, 500),
	
	FORWARD("forward", 20, 500),
	BACKWARD("back", 20, 500),
	ROTATE("cw", 1, 3600);
	
	private String command, args;
	private int min, max;

	private ControlCommand(String command) {
		this.command = command;
	}

	private ControlCommand(String command, int min, int max) {
		this.command = command;
		this.min = min;
		this.max = max;
	}

	public String convertCommand() {
		if (getArguments() != null) {
			return getCommand() + " " + getArguments();
		}
		
		return getCommand();
	}
	
	public String getCommand() {
		return command;
	}

	public String getArguments() {
		return this.args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public static ControlCommand getCommandByName(String name) {
		for (ControlCommand command : values()) {
			if (command.getCommand().equalsIgnoreCase(name)) {
				return command;
			}
		}

		return null;
	}
}
