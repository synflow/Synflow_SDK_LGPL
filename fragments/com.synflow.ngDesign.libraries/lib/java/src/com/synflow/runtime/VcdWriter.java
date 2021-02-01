/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 - 2021 Synflow SAS.
 *
 * ngDesign is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ngDesign is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ngDesign.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
 
package com.synflow.runtime;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class defines a .vcd file writer.
 *
 * @author Matthieu Wipliez
 *
 */
public class VcdWriter {

	private static final int DIVISOR = (126 - 33) + 1;

	public static void main(String[] args) {
		VcdWriter writer = new VcdWriter("dummy.vcd");
		writer.declare("reg", 18, "first");
		writer.declare("reg", 18, "first+1");
		writer.declare("reg", 18, "first+2");
		writer.declare("reg", 18, "first+3");

		writer.count = 94 - 2;
		writer.declare("reg", 18, "before last");
		writer.declare("reg", 18, "last");
		System.out.println();
		writer.declare("reg", 18, "next");
		writer.declare("reg", 18, "next+1");
		writer.declare("reg", 18, "next+2");

		writer.count = (94 + 1) * 94 - 2;
		writer.declare("reg", 18, "before last");
		writer.declare("reg", 18, "last");
		System.out.println();
		writer.declare("reg", 18, "next");
		writer.declare("reg", 18, "next+1");
		writer.declare("reg", 18, "next+2");

		writer.count = (((94 + 1) * 94) + 1) * 94 - 2;
		writer.declare("reg", 18, "before last");
		writer.declare("reg", 18, "last");
		System.out.println();
		writer.declare("reg", 18, "next");
		writer.declare("reg", 18, "next+1");
		writer.declare("reg", 18, "next+2");
	}

	private int count;

	private Map<String, String> map;

	private PrintStream out;

	private int timestamp;

	public VcdWriter(String fileName) {
		map = new HashMap<>();

		try {
			out = new PrintStream(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Declares a port with the given direction and name.
	 *
	 * @param port
	 *            runtime port object
	 * @param isOutput
	 *            true if output
	 * @param name
	 *            name of the port
	 */
	public void declare(Port port, boolean isOutput, String name) {
		if (port.signals[0] == null) {
			String[] signals = port.signals;
			for (int i = 0; i < signals.length; i++) {
				signals[i] = newIdentifier();
			}
		}

		int i = 0;
		String type = isOutput ? "reg" : "wire";
		writeVar(type, port.getSize(), port.signals[i++], name);

		// additional input
		if (port.hasFlag(Port.READY)) {
			writeVar(isOutput ? "wire" : "reg", 1, port.signals[i++], name + "_ready");
		}

		// additional output
		if (port.hasFlag(Port.VALID)) {
			writeVar(type, 1, port.signals[i++], name + "_valid");
		}
	}

	/**
	 * Generates a new identifier for a variable with the given type, size, and name.
	 *
	 * @param type
	 *            type (reg or wire)
	 * @param size
	 *            size in bits
	 * @param name
	 *            name
	 * @return a new identifier
	 */
	public String declare(String type, int size, String name) {
		String identifier = newIdentifier();
		writeVar(type, size, identifier, name);
		return identifier;
	}

	public void endDefinitions() {
		out.println("$enddefinitions $end");
	}

	/**
	 * Generates a new identifier.
	 *
	 * @return a new identifier
	 */
	private String newIdentifier() {
		StringBuilder builder = new StringBuilder();
		int work = count;
		do {
			builder.append((char) ((work % DIVISOR) + 33));
			work = (work / DIVISOR) - 1;
		} while (work >= 0);

		count++;

		return builder.toString();
	}

	/**
	 * Declares a new module scope.
	 *
	 * @param name
	 *            name of the module
	 */
	public void scope(String name) {
		out.println("$scope module " + name + " $end");
	}

	public void update(Port port) {
		int i = 0;
		update(port.signals[i++], port.getVCDValue());

		// update flags
		if (port.hasFlag(Port.READY)) {
			update(port.signals[i++], port.ready);
		}

		if (port.hasFlag(Port.VALID)) {
			update(port.signals[i], port.valid);
		}
	}

	public void update(String identifier, BigInteger value) {
		update(identifier, "b" + value.toString(2));
	}

	public void update(String identifier, boolean value) {
		update(identifier, value ? "1" : "0");
	}

	public void update(String identifier, int value) {
		update(identifier, "b" + Integer.toBinaryString(value));
	}

	public void update(String identifier, long value) {
		update(identifier, "b" + Long.toBinaryString(value));
	}

	private void update(String identifier, String newValue) {
		String oldValue = map.get(identifier);
		if (!Objects.equals(oldValue, newValue)) {
			map.put(identifier, newValue);

			out.print(newValue);
			if (newValue.length() > 1) {
				out.print(' ');
			}
			out.println(identifier);
		}
	}

	public void updateTimestamp() {
		out.println("#" + timestamp + "0");
		timestamp++;
	}

	public void upscope() {
		out.println("$upscope $end");
	}

	/**
	 * Writes the VCD header: date, version, timescale.
	 */
	public void writeHeader() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		String text = date.format(formatter);

		out.println("$date " + text + " $end");
		out.println("$version Synflow framework $end");
		out.println("$timescale 1ns $end");
	}

	/**
	 * Appends a $var directive.
	 *
	 * @param type
	 * @param size
	 * @param identifier
	 * @param name
	 */
	private void writeVar(String type, int size, String identifier, String name) {
		StringBuilder builder = new StringBuilder();
		builder.append("$var ");
		builder.append(type);
		builder.append(' ');
		builder.append(size);
		builder.append(' ');
		builder.append(identifier);
		builder.append(' ');
		builder.append(name);
		if (size > 1) {
			builder.append(" [");
			builder.append(size - 1);
			builder.append(":0]");
		}
		builder.append(" $end");
		out.println(builder.toString());
	}

}
