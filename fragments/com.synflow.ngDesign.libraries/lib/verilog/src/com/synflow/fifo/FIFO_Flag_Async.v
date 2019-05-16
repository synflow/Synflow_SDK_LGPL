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
module FIFO_Flag_Async
  #(parameter depth = 8)
  (
    input reset_n,
    input [depth - 1 : 0] rd_address,
    input [depth - 1 : 0] wr_address,
    output aFull,
    output aEmpty
  );

  wire dirSet_n;
  wire dirReset;
  reg  direction;

  assign dirSet_n = ~((wr_address[depth - 1] ^ rd_address[depth - 2]) & (~(wr_address[depth - 2] ^ rd_address[depth - 1])));
  assign dirReset = ~((wr_address[depth - 2] ^ rd_address[depth - 1]) & (~(wr_address[depth - 1] ^ rd_address[depth - 2])) | (~reset_n));
  assign aFull  =  direction & (wr_address == rd_address);
  assign aEmpty = ~direction & (wr_address == rd_address);

  always @(dirSet_n, dirReset)
    if (~dirReset)
      direction <= 1'b0;
    else if (~dirSet_n)
      direction <= 1'b1;

endmodule
