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
-- Title      : Pseudo dual-port inferred RAM
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
-------------------------------------------------------------------------------



-------------------------------------------------------------------------------
-- Entity
-------------------------------------------------------------------------------
entity PseudoDualPortRAM is
  generic (size, width, depth : integer);
  port (
    rd_clock, wr_clock     : in  std_logic;
    rd_address, wr_address : in  std_logic_vector(depth - 1 downto 0);
    data                   : in  std_logic_vector(width - 1 downto 0);
    data_valid              : in  std_logic;
    q                      : out std_logic_vector(width - 1 downto 0));
end PseudoDualPortRAM;
-------------------------------------------------------------------------------



-------------------------------------------------------------------------------
-- Architecture
-------------------------------------------------------------------------------
architecture rtl_PseudoDualPortRAM of PseudoDualPortRAM is

  -----------------------------------------------------------------------------
  -- RAM contents
  -----------------------------------------------------------------------------
  type ram_type is array (0 to size - 1) of std_logic_vector(width - 1 downto 0);
  shared variable ram : ram_type;

-------------------------------------------------------------------------------
begin

  readData : process (rd_clock)
  begin
    if rising_edge(rd_clock) then
      q <= ram(to_integer(unsigned(rd_address)));
    end if;
  end process readData;

  writeData : process (wr_clock)
  begin
    if rising_edge(wr_clock) then
      if data_valid = '1' then
        ram(to_integer(unsigned(wr_address))) := data;
      end if;
    end if;
  end process writeData;

end rtl_PseudoDualPortRAM;
