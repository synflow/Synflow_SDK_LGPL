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
module FIFO_Flag_Controller
  #(parameter depth = 8)
  (
    input reset_n,
    input din_clock,
    input dout_clock,
    input [depth - 1 : 0] wr_address,
    input [depth - 1 : 0] rd_address,
    output din_rdy,
    output dout_rdy
  );

  wire aFull_i;
  wire aEmpty_i;

  reg [depth - 1 : 0] wr_address_1C;
  reg [depth - 1 : 0] wr_address_2C;
  reg [depth - 1 : 0] rd_address_1C;
  reg [depth - 1 : 0] rd_address_2C;

  FIFO_Flag_Async #(.depth(depth)) Flag_Async_Full
  (
    .reset_n(reset_n),
    .rd_address(rd_address_2C),
    .wr_address(wr_address),
    .aFull(aFull_i),
    .aEmpty()
  );

  FIFO_Flag_Async #(.depth(depth)) Flag_Async_Empty
  (
    .reset_n(reset_n),
    .rd_address(rd_address),
    .wr_address(wr_address_2C),
    .aFull(),
    .aEmpty(aEmpty_i)
  );

  assign din_rdy  = ~aFull_i;
  assign dout_rdy = ~aEmpty_i;

  // Sync the flags
  always @(negedge reset_n or posedge din_clock)
    if (~reset_n) begin
      rd_address_1C <= {depth{1'b0}};
      rd_address_2C <= {depth{1'b0}};
    end else begin
      rd_address_2C <= rd_address_1C;
      rd_address_1C <= rd_address;
    end

  always @(negedge reset_n or posedge dout_clock)
    if (~reset_n) begin
      wr_address_1C <= {depth{1'b0}};
      wr_address_2C <= {depth{1'b0}};
    end else begin
      wr_address_2C <= wr_address_1C;
      wr_address_1C <= wr_address;
    end

endmodule
