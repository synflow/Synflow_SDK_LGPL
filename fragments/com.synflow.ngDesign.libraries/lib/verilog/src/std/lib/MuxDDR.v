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
 * Title       : MuxDDR
 * Description : Multiplexes two single data rate signals to generate a Double Data Rate signal
 * Authors     : Matthieu Wipliez <matthieu.wipliez@synflow.com>
 */
module MuxDDR
  #(parameter width = 0)
  (
    input clock,
    input rising_valid,
    input [width - 1 : 0] rising,
    input falling_valid,
    input [width - 1 : 0] falling,
    output dout_valid,
    output [width - 1 : 0] dout
  );

  assign dout = clock ? rising : falling;
  assign dout_valid = clock ? rising_valid : falling_valid;

endmodule
