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
 
package std.fifo;

import com.synflow.runtime.Port;
import com.synflow.runtime.VcdWriter;

import std.mem.PseudoDualPortRAM;

/**
 * This class defines a synchronous FIFO.
 *
 * @author Matthieu Wipliez
 *
 */
public class SynchronousFIFO {

	public static SynchronousFIFO create(int size, int width, int depth) {
		return new SynchronousFIFO(size, width, depth);
	}

	private Port din;

	public final Port dout;

	private boolean dout_valid;

	private final PseudoDualPortRAM ram;

	private final Port rd_address, wr_address;

	private final int size;

	private SynchronousFIFO(int size, int width, int depth) {
		this.size = size;

		ram = PseudoDualPortRAM.create(Port.READY | Port.VALID, size, width, depth);
		rd_address = new Port(Port.BARE, depth);
		wr_address = new Port(Port.BARE, depth);
		dout = ram.q;
	}

	public final void commit() {
		ram.commit();

		dout.valid = dout_valid;
	}

	public final void connect(Port din) {
		this.din = din;
		ram.connect(rd_address, wr_address, din);
	}

	public void execute() {
		dout_valid = false;

		if (din.valid) {
			wr_address.intValue = (wr_address.intValue + 1) & (size - 1);
		}

		if (dout.ready && !isEmpty()) {
			rd_address.intValue = (rd_address.intValue + 1) & (size - 1);
			dout_valid = true;
		}

		ram.execute();
	}

	public void executeCombinational() {
		din.ready = !isAlmostFull() && !isFull();
	}

	private boolean isAlmostFull() {
		return ((wr_address.intValue + 2) & (size - 1)) == rd_address.intValue;
	}

	private boolean isEmpty() {
		return wr_address.intValue == rd_address.intValue;
	}

	private boolean isFull() {
		return ((wr_address.intValue + 1) & (size - 1)) == rd_address.intValue;
	}

	public void vcdDeclareScope(VcdWriter writer) {
		writer.declare(din, false, "din");
		writer.declare(rd_address, true, "rd_address");
		writer.declare(wr_address, true, "wr_address");
		writer.declare(dout, true, "dout");
	}

	public void vcdUpdateValues(VcdWriter writer) {
		writer.update(rd_address);
		writer.update(wr_address);
		writer.update(dout);
	}

}
