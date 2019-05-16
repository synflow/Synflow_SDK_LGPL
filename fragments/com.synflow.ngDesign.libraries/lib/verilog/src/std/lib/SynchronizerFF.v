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
 * Title   : Flip-flop synchronizer
 * Authors : Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module SynchronizerFF
  #(parameter stages = 2)
  (
    input reset_n,
    input din_clock,
    input dout_clock,
    input din,
    output dout
  );

  /**
   * flip-flop registers
   */
  reg [stages - 1 : 0] ff;

  assign dout = ff[stages - 1];

  integer i;

  always @(negedge reset_n or posedge dout_clock)
    if (~reset_n) begin
      ff <= 0;
    end else begin
      // N-stage shift register
      for (i = stages - 1; i > 0; i = i - 1) begin
        ff[i] <= ff[i - 1];
      end
      ff[0] <= din;
    end

endmodule
