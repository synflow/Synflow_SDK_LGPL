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
 
package std.mem;

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.Arrays;

import com.synflow.runtime.Port;
import com.synflow.runtime.VcdWriter;

/**
 * This class defines a single port RAM.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class SinglePortRAM {

	private static class SinglePortRAMBigInteger extends SinglePortRAM {

		private final BigInteger[] contents;

		private BigInteger outputRegister;

		public SinglePortRAMBigInteger(int size, int width, boolean writeShiftMode,
				boolean addOutputRegister) {
			super(width, writeShiftMode, addOutputRegister);

			contents = new BigInteger[size];
			Arrays.fill(contents, ZERO);
			outputRegister = ZERO;
		}

		@Override
		protected void readCurrentValue() {
			if (addOutputRegister) {
				q_next.value = outputRegister;
				outputRegister = contents[address.intValue];
			} else {
				q_next.value = contents[address.intValue];
			}
		}

		@Override
		protected void writeNewValue() {
			if (data.valid) {
				contents[address.intValue] = data.value;
			}
		}

	}

	private static class SinglePortRAMInt extends SinglePortRAM {

		private final int[] contents;

		private int outputRegister;

		public SinglePortRAMInt(int size, int width, boolean writeShiftMode,
				boolean addOutputRegister) {
			super(width, writeShiftMode, addOutputRegister);

			contents = new int[size];
		}

		@Override
		protected void readCurrentValue() {
			if (addOutputRegister) {
				q_next.intValue = outputRegister;
				outputRegister = contents[address.intValue];
			} else {
				q_next.intValue = contents[address.intValue];
			}
		}

		@Override
		protected void writeNewValue() {
			if (data.valid) {
				contents[address.intValue] = data.intValue;
			}
		}

	}

	private static class SinglePortRAMLong extends SinglePortRAM {

		private final long[] contents;

		private long outputRegister;

		public SinglePortRAMLong(int size, int width, boolean writeShiftMode,
				boolean addOutputRegister) {
			super(width, writeShiftMode, addOutputRegister);

			contents = new long[size];
		}

		@Override
		protected void readCurrentValue() {
			if (addOutputRegister) {
				q_next.longValue = outputRegister;
				outputRegister = contents[address.intValue];
			} else {
				q_next.longValue = contents[address.intValue];
			}
		}

		@Override
		protected void writeNewValue() {
			if (data.valid) {
				contents[address.intValue] = data.longValue;
			}
		}

	}

	public static SinglePortRAM create(int size, int width, int depth, boolean writeShiftMode,
			boolean addOutputRegister) {
		if (width > 64) {
			return new SinglePortRAMBigInteger(size, width, writeShiftMode, addOutputRegister);
		} else if (width > 32) {
			return new SinglePortRAMLong(size, width, writeShiftMode, addOutputRegister);
		} else {
			return new SinglePortRAMInt(size, width, writeShiftMode, addOutputRegister);
		}
	}

	protected final boolean addOutputRegister;

	protected Port address;

	protected Port data;

	public final Port q;

	protected final Port q_next;

	protected final boolean writeShiftMode;

	private SinglePortRAM(int width, boolean writeShiftMode, boolean addOutputRegister) {
		this.addOutputRegister = addOutputRegister;
		this.writeShiftMode = writeShiftMode;

		q = new Port(Port.BARE, width);
		q_next = new Port(Port.BARE, width);
	}

	public final void commit() {
		q.copy(q_next);
	}

	public final void connect(Port address, Port data) {
		this.address = address;
		this.data = data;
	}

	public final void execute() {
		if (writeShiftMode) {
			readCurrentValue();
			writeNewValue();
		} else {
			writeNewValue();
			readCurrentValue();
		}
	}

	protected abstract void readCurrentValue();

	public void vcdDeclareScope(VcdWriter writer) {
	}

	public void vcdUpdateValues(VcdWriter writer) {
	}

	protected abstract void writeNewValue();

}
