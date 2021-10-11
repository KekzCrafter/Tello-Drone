package de.kekz.tello;

import java.util.Scanner;

import de.kekz.tello.connection.Drone;
import de.kekz.tello.utils.commands.ControlCommand;
import de.kekz.tello.utils.commands.ReadCommand;

public class Main {

	private static Main instance;

	public static void main(String... strings) {
		init();

		Drone drone = new Drone();
		drone.connect();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String message = scanner.nextLine();

			if (message.equalsIgnoreCase("quit")) {
				drone.disconnect();

				scanner.close();
				System.exit(0);
				return;
			}

			if (message.startsWith("control ")) {
				ControlCommand command = ControlCommand.getCommandByName(message.split(" ")[1]);
				drone.executeCommand(command);
			}

			if (message.startsWith("read ")) {
				ReadCommand command = ReadCommand.getCommandByName(message.split(" ")[1]);
				drone.executeReadCommand(command);
			}
		}
	}

	private static void init() {
		instance = new Main();
	}

	public static Main getInstance() {
		return instance;
	}

	public void log(String message) {
		System.out.println(message);
	}
}
