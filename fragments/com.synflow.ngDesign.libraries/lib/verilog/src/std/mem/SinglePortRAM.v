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

/**
 * Title   : Single-port inferred RAM
 * Authors : Nicolas Siret <nicolas.siret@synflow.com>, Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module SinglePortRAM
  #(parameter size = 0, width = 0, depth = 0, writeShiftMode = 0, addOutputRegister = 0)
  (
    input clock,
    input [depth - 1 : 0] address,
    input [width - 1 : 0] data, input data_valid,
    output [width - 1 : 0] q
  );

  /*
   * RAM contents
   */
  reg [width - 1 : 0] ram [0 : size - 1];
  reg [width - 1 : 0] dout;

  generate
    if (addOutputRegister) begin
      reg [width - 1 : 0] outputRegister;

      always @(posedge clock) begin
        outputRegister <= dout;
      end

      assign q = outputRegister;
    end else begin
      assign q = dout;
    end
  endgenerate

  // read and write data process
  always @(posedge clock) begin
    if (!data_valid || writeShiftMode) begin
      dout <= ram[address];
    end

    if (data_valid) begin
      if (!writeShiftMode) begin
        dout <= data;
      end
      ram[address] <= data;
    end
  end

endmodule
