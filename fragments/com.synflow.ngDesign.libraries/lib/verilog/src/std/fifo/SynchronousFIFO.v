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
 * Title   : Synchronous FIFO
 * Authors : Nicolas Siret <nicolas.siret@synflow.com>, Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module SynchronousFIFO
  #(parameter size = 0, width = 0, depth = 0)
  (
    input clock,
    input reset_n,
    input  [width - 1 : 0] din,  input din_valid, output din_ready,
    output [width - 1 : 0] dout, output reg dout_valid, input dout_ready
  );

  reg [depth - 1 : 0] rd_address, wr_address;

  PseudoDualPortRAM #(.size(size), .width(width), .depth(depth)) ram
  (
    .rd_clock(clock),
    .wr_clock(clock),
    .rd_address(rd_address),
    .wr_address(wr_address),
    .data(din),
    .data_valid(din_valid),
    .q(dout)
  );

  wire empty;
  assign empty = wr_address == rd_address;

  wire almost_full, full;
  assign almost_full = (wr_address + {{(depth - 2){1'b0}}, 2'd2}) == rd_address;
  assign full = (wr_address + {{(depth - 1){1'b0}}, 1'd1}) == rd_address;
  assign din_ready = !almost_full && !full;

  always @(negedge reset_n or posedge clock) begin
    if (~reset_n) begin
      rd_address <= {depth{1'b0}};
      wr_address <= {depth{1'b0}};

      // din_ready <= 1'b1;
      dout_valid <= 1'b0;
    end else begin
      dout_valid <= 1'b0;

      // din_ready <= !full;
      wr_address <= wr_address + {{(depth - 1){1'b0}}, din_valid};

      if (dout_ready && !empty) begin
        rd_address <= rd_address + 1'b1;
        dout_valid <= 1'b1;
      end
    end
  end

endmodule
