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
 * Title   : Mux synchronizer
 * Authors : Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module SynchronizerMux
  #(parameter width = 32, parameter stages = 2)
  (
    input reset_n,
    input din_clock,
    input dout_clock,
    input din_valid,
    input [width - 1 : 0] din,
    output reg [width - 1 : 0] dout
  );

  /**
   * internal signals
   */
  wire control_sync;

  SynchronizerFF #(
    .stages(stages)
  )
  sync(
    .reset_n(reset_n),
    .din_clock(din_clock),
    .dout_clock(dout_clock),
    .din(din_valid),
    .dout(control_sync)
  );

  always @(negedge reset_n or posedge dout_clock)
    if (~reset_n) begin
      dout <= 0;
    end else begin
      if (control_sync)
        dout <= din;
    end

endmodule
