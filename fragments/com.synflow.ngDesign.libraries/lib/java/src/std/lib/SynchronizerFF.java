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
 * This class defines a synchronizer flip-flop.
 *
 * @author Matthieu Wipliez
 *
 */
public class SynchronizerFF {

	public static SynchronizerFF create(int stages) {
		return new SynchronizerFF(stages);
	}

	private int bits;

	private Port din;

	public final Port dout;

	private final Port dout_next;

	private final int mask;

	private SynchronizerFF(int stages) {
		mask = 1 << (stages - 1);
		dout = new Port(Port.BARE, 1);
		dout_next = new Port(Port.BARE, 1);
	}

	public void commit() {
		dout.intValue = dout_next.intValue;
	}

	public void connect(Port din) {
		this.din = din;
	}

	public void execute() {
		bits = (bits << 1) | din.intValue;
		dout_next.intValue = ((bits & mask) != 0) ? 1 : 0;
	}

	public void vcdDeclareScope(VcdWriter writer) {
	}

	public void vcdUpdateValues(VcdWriter writer) {
	}

}
