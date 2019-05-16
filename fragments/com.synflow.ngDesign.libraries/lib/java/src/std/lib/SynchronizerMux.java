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
 
package std.lib;

import com.synflow.runtime.Port;
import com.synflow.runtime.VcdWriter;

/**
 * This class defines a synchronizer mux.
 *
 * @author Matthieu Wipliez
 *
 */
public abstract class SynchronizerMux {

	private static class SynchronizerMuxBigInteger extends SynchronizerMux {

		public SynchronizerMuxBigInteger(int width, int stages) {
			super(width, stages);
		}

		@Override
		protected void assign(Port target, Port source) {
			target.value = source.value;
		}

	}

	private static class SynchronizerMuxInt extends SynchronizerMux {

		public SynchronizerMuxInt(int width, int stages) {
			super(width, stages);
		}

		@Override
		protected void assign(Port target, Port source) {
			target.intValue = source.intValue;
		}

	}

	private static class SynchronizerMuxLong extends SynchronizerMux {

		public SynchronizerMuxLong(int width, int stages) {
			super(width, stages);
		}

		@Override
		protected void assign(Port target, Port source) {
			target.longValue = source.longValue;
		}

	}

	public static SynchronizerMux create(int width, int stages) {
		if (width > 64) {
			return new SynchronizerMuxBigInteger(width, stages);
		} else if (width > 32) {
			return new SynchronizerMuxLong(width, stages);
		} else {
			return new SynchronizerMuxInt(width, stages);
		}
	}

	private int bits;

	private Port din;

	public final Port dout;

	private final Port dout_next;

	private final int mask;

	private SynchronizerMux(int width, int stages) {
		mask = 1 << stages;
		dout = new Port(Port.BARE, width);
		dout_next = new Port(Port.BARE, width);
	}

	protected abstract void assign(Port target, Port source);

	public void commit() {
		assign(dout, dout_next);
	}

	public void connect(Port din) {
		this.din = din;
	}

	public void execute() {
		bits = (bits << 1) | (din.valid ? 1 : 0);
		if ((bits & mask) != 0) {
			assign(dout_next, din);
		}
	}

	public void vcdDeclareScope(VcdWriter writer) {
	}

	public void vcdUpdateValues(VcdWriter writer) {
	}

}
