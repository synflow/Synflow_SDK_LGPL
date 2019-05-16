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
 * Title       : DemuxDDR
 * Description : Demultiplexes a Double Data Rate signal as two single data rate signals
 * Authors     : Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module DemuxDDR
  #(parameter width = 0)
  (
    input reset_n,
    input clock,
    input din_valid,
    input [width - 1 : 0] din,
    output reg rising_valid,
    output reg [width - 1 : 0] rising,
    output reg falling_valid,
    output reg [width - 1 : 0] falling
  );

  reg internal_falling_valid;
  reg [width - 1 : 0] internal_falling;

  always @(negedge reset_n or posedge clock) begin
    if (~reset_n) begin
      rising <= {width{1'b0}};
      rising_valid <= 1'b0;

      falling <= {width{1'b0}};
      falling_valid <= 1'b0;
    end else begin
      rising <= din;
      rising_valid <= din_valid;

      falling <= internal_falling;
      falling_valid <= internal_falling_valid;
    end
  end

  always @(negedge reset_n or negedge clock) begin
    if (~reset_n) begin
      internal_falling <= {width{1'b0}};
      internal_falling_valid <= 1'b0;
    end else begin
      internal_falling <= din;
      internal_falling_valid <= din_valid;
    end
  end

endmodule
