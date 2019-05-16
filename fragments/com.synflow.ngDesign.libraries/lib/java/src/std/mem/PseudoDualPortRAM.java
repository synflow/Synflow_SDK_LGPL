/*
 * This file is part of the ngDesign SDK.
 *
 * Copyright (c) 2019 Synflow SAS.
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
 
package std.mem;

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.Arrays;

import com.synflow.runtime.Port;
import com.synflow.runtime.VcdWriter;

/**
 * This class defines a pseudo dual port RAM.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class PseudoDualPortRAM {

	private static class PseudoDualPortRAMBigInteger extends PseudoDualPortRAM {

		private final BigInteger[] contents;

		protected PseudoDualPortRAMBigInteger(int flags, int size, int width) {
			super(flags, width);
			contents = new BigInteger[size];
			Arrays.fill(contents, ZERO);
		}

		@Override
		public void execute() {
			// write through
			if (data.valid) {
				contents[wr_address.intValue] = data.value;
			}
			q_next.value = contents[rd_address.intValue];
		}

	}

	private static class PseudoDualPortRAMInt extends PseudoDualPortRAM {

		private final int[] contents;

		protected PseudoDualPortRAMInt(int flags, int size, int width) {
			super(flags, width);
			contents = new int[size];
		}

		@Override
		public void execute() {
			// write through
			if (data.valid) {
				contents[wr_address.intValue] = data.intValue;
			}
			q_next.intValue = contents[rd_address.intValue];
		}

	}

	private static class PseudoDualPortRAMLong extends PseudoDualPortRAM {

		private final long[] contents;

		protected PseudoDualPortRAMLong(int flags, int size, int width) {
			super(flags, width);
			contents = new long[size];
		}

		@Override
		public void execute() {
			// write through
			if (data.valid) {
				contents[wr_address.intValue] = data.longValue;
			}
			q_next.longValue = contents[rd_address.intValue];
		}

	}

	public static PseudoDualPortRAM create(int size, int width, int depth) {
		return create(Port.BARE, size, width, depth);
	}

	public static PseudoDualPortRAM create(int flags, int size, int width, int depth) {
		if (width > 64) {
			return new PseudoDualPortRAMBigInteger(flags, size, width);
		} else if (width > 32) {
			return new PseudoDualPortRAMLong(flags, size, width);
		} else {
			return new PseudoDualPortRAMInt(flags, size, width);
		}
	}

	protected Port data;

	public final Port q;

	protected final Port q_next;

	protected Port rd_address, wr_address;

	private PseudoDualPortRAM(int flags, int width) {
		q = new Port(flags, width);
		q_next = new Port(flags, width);
	}

	public final void commit() {
		q.copy(q_next);
	}

	public final void connect(Port rd_address, Port wr_address, Port data) {
		this.rd_address = rd_address;
		this.wr_address = wr_address;
		this.data = data;
	}

	public abstract void execute();

	public void vcdDeclareScope(VcdWriter writer) {
	}

	public void vcdUpdateValues(VcdWriter writer) {
	}

}
