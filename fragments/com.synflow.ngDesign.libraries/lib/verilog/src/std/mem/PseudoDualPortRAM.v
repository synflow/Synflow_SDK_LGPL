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
 * Title   : Pseudo dual-port inferred RAM
 * Authors : Nicolas Siret <nicolas.siret@synflow.com>, Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module PseudoDualPortRAM
  #(parameter size = 0, width = 0, depth = 0)
  (
    input rd_clock, input wr_clock,
    input [depth - 1 : 0] rd_address,
    input [depth - 1 : 0] wr_address,
    input [width - 1 : 0] data, input data_valid,
    output reg [width - 1 : 0] q
  );

  /*
   * RAM contents
   */
  reg [width - 1 : 0] ram [0 : size - 1];

   // read process
  always @(posedge rd_clock)
    q <= ram[rd_address];

  // write data process
  always @(posedge wr_clock)
    if (data_valid)
      ram[wr_address] <= data;

endmodule
