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
 
package com.synflow.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.synflow.core.SynflowCore;

/**
 * This class defines a thread that copies an input stream to an output stream. This is intended to
 * be used when running commands in tests.
 *
 * @author Matthieu Wipliez
 *
 */
public class StreamCopier extends Thread {

	private InputStream source;

	private OutputStream target;

	public StreamCopier(InputStream source, OutputStream target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public void run() {
		byte[] buf = new byte[4096];
		try {
			int n = source.read(buf);
			while (n != -1) {
				target.write(buf, 0, n);
				target.flush();
				n = source.read(buf);
			}
		} catch (IOException e) {
			SynflowCore.log(e);
		}
	}

}
