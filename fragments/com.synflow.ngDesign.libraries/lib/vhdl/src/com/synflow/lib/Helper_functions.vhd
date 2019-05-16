--
 -- This file is part of the ngDesign SDK.
 --
 -- Copyright (c) 2019 Synflow SAS.
 --
 -- ngDesign is free software: you can redistribute it and/or modify
 -- it under the terms of the GNU General Public License as published by
 -- the Free Software Foundation, either version 3 of the License, or
 -- (at your option) any later version.
 --
 -- ngDesign is distributed in the hope that it will be useful,
 -- but WITHOUT ANY WARRANTY; without even the implied warranty of
 -- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 -- GNU General Public License for more details.
 --
 -- You should have received a copy of the GNU General Public License
 -- along with ngDesign.  If not, see <https://www.gnu.org/licenses/>.
 --
 --

-------------------------------------------------------------------------------
-- Title      : Adds VHDL-2008 like constructs in a VHDL'93 compatible way
-- Author     : Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use std.textio.all;
-------------------------------------------------------------------------------


-------------------------------------------------------------------------------
--
-------------------------------------------------------------------------------
package Helper_functions is

  function to_boolean(b : std_logic) return boolean;
  function to_std_logic(b : boolean) return std_logic;
  function to_string_93(b : bit) return string;
  function to_hstring_93(b : bit_vector) return string;

  procedure print(message : string);

end Helper_functions;

-------------------------------------------------------------------------------
-- Body of package
-------------------------------------------------------------------------------
package body Helper_functions is

  -----------------------------------------------------------------------------
  -- Built-in constants and functions
  -----------------------------------------------------------------------------

  function to_boolean(b : std_logic) return boolean is
  begin
    return b = '1';
  end;

  function to_std_logic(b : boolean) return std_logic is
  begin
    if b then
      return '1';
    else
      return '0';
    end if;
  end;

  function to_string_93(b : bit) return string is begin
    -- rtl_synthesis off
    -- synthesis translate_off
    return to_string(b);
    -- synthesis translate_on
    -- rtl_synthesis on
    return "";
  end;

  function to_hstring_93(b : bit_vector) return string is begin
    -- rtl_synthesis off
    -- synthesis translate_off
    return to_hstring(b);
    -- synthesis translate_on
    -- rtl_synthesis on
    return "";
  end;

  procedure print(message : string) is begin
    -- rtl_synthesis off
    -- synthesis translate_off
    write(output, message & LF);
    -- synthesis translate_on
    -- rtl_synthesis on
  end;

end Helper_functions;
