package de.kekz.tello.utils.commands;

public enum ReadCommand {

	SPEED("speed?"), 
	BATTERY("battery?"), 
	TIME("time?"), 
	HEIGHT("height?"),
	TEMPERATURE("temp?"), 
	ATTITUDE("attitude?"),
	BAROMETER("baro?"), 
	ACCELERATION("acceleration?"), 
	TOF("tof?"), 
	WIFI_SNR("wifi?");

	private String command;

	private ReadCommand(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public static ReadCommand getCommandByName(String name) {
		for (ReadCommand command : values()) {
			if (command.getCommand().equalsIgnoreCase(name)) {
				return command;
			}
		}

		return null;
	}
}
