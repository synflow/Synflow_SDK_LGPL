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
 * This class defines a dual port RAM.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class DualPortRAM {

	private static class DualPortRAMBigInteger extends DualPortRAM {

		private final BigInteger[] contents;

		protected DualPortRAMBigInteger(int size, int width) {
			super(width);
			contents = new BigInteger[size];
			Arrays.fill(contents, ZERO);
		}

		@Override
		protected void execute(Port address, Port data, Port q_next) {
			// write through
			if (data.valid) {
				contents[address.intValue] = data.value;
			}
			q_next.value = contents[address.intValue];
		}

	}

	private static class DualPortRAMInt extends DualPortRAM {

		private final int[] contents;

		protected DualPortRAMInt(int size, int width) {
			super(width);
			contents = new int[size];
		}

		@Override
		protected void execute(Port address, Port data, Port q_next) {
			// write through
			if (data.valid) {
				contents[address.intValue] = data.intValue;
			}
			q_next.intValue = contents[address.intValue];
		}

	}

	private static class DualPortRAMLong extends DualPortRAM {

		private final long[] contents;

		protected DualPortRAMLong(int size, int width) {
			super(width);
			contents = new long[size];
		}

		@Override
		protected void execute(Port address, Port data, Port q_next) {
			// write through
			if (data.valid) {
				contents[address.intValue] = data.longValue;
			}
			q_next.longValue = contents[address.intValue];
		}

	}

	public static DualPortRAM create(int size, int width, int depth) {
		if (width > 64) {
			return new DualPortRAMBigInteger(size, width);
		} else if (width > 32) {
			return new DualPortRAMLong(size, width);
		} else {
			return new DualPortRAMInt(size, width);
		}
	}

	private Port address_a, address_b;

	private Port data_a, data_b;

	public final Port q_a, q_b;

	protected final Port q_a_next, q_b_next;

	private DualPortRAM(int width) {
		q_a = new Port(Port.BARE, width);
		q_a_next = new Port(Port.BARE, width);
		q_b = new Port(Port.BARE, width);
		q_b_next = new Port(Port.BARE, width);
	}

	public final void commit() {
		q_a.copy(q_a_next);
		q_b.copy(q_b_next);
	}

	public final void connect(Port address_a, Port data_a, Port address_b, Port data_b) {
		this.address_a = address_a;
		this.data_a = data_a;
		this.address_b = address_b;
		this.data_b = data_b;
	}

	public final void execute() {
		execute(address_a, data_a, q_a_next);
		execute(address_b, data_b, q_b_next);
	}

	protected abstract void execute(Port address, Port data, Port q_next);

	public void vcdDeclareScope(VcdWriter writer) {
	}

	public void vcdUpdateValues(VcdWriter writer) {
	}

}
