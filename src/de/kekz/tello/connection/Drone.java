package de.kekz.tello.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import de.kekz.tello.Main;
import de.kekz.tello.utils.Variables;
import de.kekz.tello.utils.commands.ControlCommand;
import de.kekz.tello.utils.commands.ReadCommand;

public class Drone {

	/* UDP */
	private DatagramSocket socket;
	private InetAddress address;
	private int port;

	public Drone() {
		try {
			this.address = InetAddress.getByName(Variables.DRONE_IP);
			this.port = Variables.COMMAND_PORT;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {

	}

	/* Connection */
	public void connect() {
		try {
			this.socket = new DatagramSocket();
			this.socket.setSoTimeout(5000);
			this.socket.connect(InetAddress.getByName(Variables.DRONE_IP), Variables.COMMAND_PORT);

			if (isConnected()) {
				Main.getInstance().log("UDP connection was created...");
			} else {
				Main.getInstance().log("Couldn't connect with Tello drone.");
			}
		} catch (Exception e) {
			Main.getInstance().log("Error occured while connecting to Tello.");
		}
	}

	public void disconnect() {
		getSocket().disconnect();
		getSocket().close();
	}

	public boolean isConnected() {
		return getSocket().isConnected();
	}

	/* Commands */
	public void executeCommand(ControlCommand command) {
		if (command == null) {
			Main.getInstance().log("Command was not found.");
			return;
		}

		if (!isConnected()) {
			Main.getInstance().log("No connection.");
			return;
		}

		Main.getInstance().log("Executing command: \"" + command.convertCommand() + "\"");
		String response = null;

		try {
			sendData(command.convertCommand());
			response = receiveData();

			Main.getInstance().log("Response: " + response);
		} catch (IOException e) {
			Main.getInstance().log("Error: " + e.getLocalizedMessage());
		}
	}

	public void executeReadCommand(ReadCommand command) {
		if (command == null) {
			Main.getInstance().log("Command was not found.");
			return;
		}

		if (!isConnected()) {
			Main.getInstance().log("No connection.");
			return;
		}

		Main.getInstance().log("Executing command: \"" + command.getCommand() + "\"");
		String response = null;

		try {
			sendData(command.getCommand());
			response = receiveData();

			Main.getInstance().log("Response: " + response);
		} catch (IOException e) {
			Main.getInstance().log("Error: " + e.getLocalizedMessage());
		}
	}

	public void enterSDKMode() {
		executeCommand(ControlCommand.ENTER_SDK_MODE);
	}

	/* Data */
	public void sendData(String data) throws IOException {
		byte[] sendData = data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, getAddress(), getPort());
		getSocket().send(sendPacket);
	}

	public String receiveData() throws IOException {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		getSocket().receive(receivePacket);

		return convertResponsePacket(receiveData, receivePacket);
	}

	public String convertResponsePacket(byte[] response, DatagramPacket receivePacket) {
		response = Arrays.copyOf(response, receivePacket.getLength());
		return new String(response, StandardCharsets.UTF_8);
	}

	/* UDP */
	public DatagramSocket getSocket() {
		return socket;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

}
