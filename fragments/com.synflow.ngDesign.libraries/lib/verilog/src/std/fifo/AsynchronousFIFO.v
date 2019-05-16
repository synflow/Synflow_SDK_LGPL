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
 * Title   : Asynchronous FIFO
 * Authors : Nicolas Siret <nicolas.siret@synflow.com>, Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module AsynchronousFIFO
  #(parameter size = 0, width = 0, depth = 0)
  (
    input din_clock, input dout_clock,
    input reset_n,
    input [width - 1 : 0] din, input din_valid, output din_ready,
    output reg [width - 1 : 0]  dout, output reg dout_valid, input dout_ready
  );

  /*
   * RAM Management
   */
  wire [depth - 1 : 0] rd_address;
  wire [depth - 1 : 0] wr_address;

  wire read_ready, readFifo;

  reg dout_valid_1C;
  wire [width - 1 : 0] dataOutRam;

  DualPortRAM #(.size(size), .width(width), .depth(depth)) ram
  (
    .clock_a(din_clock),
    .clock_b(dout_clock),
    .address_a(wr_address),
    .data_a(din),
    .data_a_valid(din_valid),
    .q_a(),
    .address_b(rd_address),
    .data_b(),
    .data_b_valid(),
    .q_b(dataOutRam)
  );

  FIFO_Write_Controller #(.depth(depth)) wr_ctrl
  (
    .reset_n(reset_n),
    .wr_clock(din_clock),
    .enable(din_valid),
    .gray_value(wr_address)
  );

  FIFO_Read_Controller #(.depth(depth)) rd_ctrl
  (
    .reset_n(reset_n),
    .rd_clock(dout_clock),
    .enable(readFifo),
    .gray_value(rd_address)
  );
  assign readFifo = read_ready & dout_ready;

  FIFO_Flag_Controller #(.depth(depth)) Flag_Controller
  (
    .reset_n(reset_n),
    .din_clock(din_clock),
    .dout_clock(dout_clock),
    .wr_address(wr_address),
    .rd_address(rd_address),
    .din_rdy(din_ready),
    .dout_rdy(read_ready)
  );

  // Register the output (better performance / place & route)
  always @(negedge reset_n or posedge dout_clock)
    if (~reset_n) begin
      dout         <= {width{1'b0}};
      dout_valid    <= 1'b0;
   end else begin
      dout_valid    <= dout_valid_1C;
      if (dout_valid_1C) begin
        dout       <= dataOutRam;
      end
    end

  always @(negedge reset_n or posedge dout_clock)
    if (~reset_n) begin
      dout_valid_1C <= 1'b0;
    end else begin
      dout_valid_1C <= readFifo;
    end

endmodule
