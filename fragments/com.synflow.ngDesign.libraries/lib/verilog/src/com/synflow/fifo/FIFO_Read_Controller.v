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
 * Title   : FIFO read controller
 * Authors : Nicolas Siret <nicolas.siret@synflow.com>, Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module FIFO_Read_Controller
  #(parameter depth = 8)
  (
    input reset_n,
    input rd_clock,
    input enable,
    output [depth - 1 : 0] gray_value
  );

  reg [depth - 1 : 0] rd_address;
  reg [1 : 0] ff_valid;

  always @(negedge reset_n or posedge rd_clock)
    if (~reset_n)
      rd_address <= {depth{1'b0}};
    else
      rd_address <= rd_address + enable;

  assign gray_value[depth - 1 : 0] = rd_address[depth - 1 : 0] ^ {1'b0, rd_address[depth - 1 : 1]};

endmodule
