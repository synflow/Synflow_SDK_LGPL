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
-- Title      : Dual-port inferred RAM
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
entity DualPortRAM is
  generic (size, width, depth : integer);
  port (
    clock_a, clock_b : in std_logic;
    --
    address_a   : in std_logic_vector(depth - 1 downto 0);
    data_a      : in std_logic_vector(width - 1 downto 0);
    data_a_valid : in std_logic;
    --
    address_b   : in std_logic_vector(depth - 1 downto 0);
    data_b      : in std_logic_vector(width - 1 downto 0);
    data_b_valid : in std_logic;
    --
    q_a : out std_logic_vector(width - 1 downto 0);
    q_b : out std_logic_vector(width - 1 downto 0));
end DualPortRAM;
-------------------------------------------------------------------------------



-------------------------------------------------------------------------------
-- Architecture
-------------------------------------------------------------------------------
architecture rtl_DualPortRAM of DualPortRAM is

  -----------------------------------------------------------------------------
  -- RAM contents
  -----------------------------------------------------------------------------
  type ram_type is array (0 to size - 1) of std_logic_vector(width - 1 downto 0);
  shared variable ram : ram_type;

-------------------------------------------------------------------------------
begin

  access_a: process(clock_a)
  begin
    if rising_edge(clock_a) then
      if data_a_valid = '1' then
        ram(to_integer(unsigned(address_a))) := data_a;
        q_a <= data_a;
      else
        q_a <= ram(to_integer(unsigned(address_a)));
      end if;
    end if;
  end process access_a;

  access_b: process(clock_b)
  begin
    if rising_edge(clock_b) then
      if data_b_valid = '1' then
        ram(to_integer(unsigned(address_b))) := data_b;
        q_b <= data_b;
      else
        q_b <= ram(to_integer(unsigned(address_b)));
      end if;
    end if;
  end process access_b;

end rtl_DualPortRAM;
