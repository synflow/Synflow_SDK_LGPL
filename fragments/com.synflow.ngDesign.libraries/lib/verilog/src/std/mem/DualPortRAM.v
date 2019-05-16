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
 * Title   : Dual-port inferred RAM
 * Authors : Nicolas Siret <nicolas.siret@synflow.com>, Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module DualPortRAM
  #(parameter size = 0, width = 0, depth = 0)
  (
    input clock_a, input clock_b,

    input [depth - 1 : 0] address_a,
    input [width - 1 : 0] data_a, input data_a_valid,
    output reg [width - 1 : 0] q_a,

    input [depth - 1 : 0] address_b,
    input [width - 1 : 0] data_b, input data_b_valid,
    output reg [width - 1 : 0] q_b
  );

  /*
   * RAM contents
   */
  reg [width - 1 : 0] ram [0 : size - 1];

  // process a
  always @(posedge clock_a) begin
    if (data_a_valid) begin
      ram[address_a] <= data_a;
      q_a <= data_a;
    end else
      q_a <= ram[address_a];
  end

  // process a
  always @(posedge clock_b) begin
    if (data_b_valid) begin
      ram[address_b] <= data_b;
      q_b <= data_b;
    end else
      q_b <= ram[address_b];
  end

endmodule
