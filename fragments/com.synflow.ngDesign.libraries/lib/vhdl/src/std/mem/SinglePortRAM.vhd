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
-- Title      : Single-port inferred RAM
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
entity SinglePortRAM is
  generic (
    size, width, depth : integer;
    writeShiftMode     : boolean;
    addOutputRegister  : boolean);
  port (
    clock     : in  std_logic;
    --
    address   : in  std_logic_vector(depth - 1 downto 0);
    data      : in  std_logic_vector(width - 1 downto 0);
    data_valid : in  std_logic;
    q         : out std_logic_vector(width - 1 downto 0));
end SinglePortRAM;
-------------------------------------------------------------------------------


-------------------------------------------------------------------------------
-- Architecture
-------------------------------------------------------------------------------
architecture arch_Single_Port_RAM of SinglePortRAM is

  -----------------------------------------------------------------------------
  -- Internal type declarations
  -----------------------------------------------------------------------------
  type ram_type is array (0 to size - 1) of std_logic_vector(width - 1 downto 0);

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  shared variable ram : ram_type;

  signal dout : std_logic_vector(width - 1 downto 0);

-------------------------------------------------------------------------------
begin

  -- read and write data process
  rdwrData: process (clock)
  begin
    if rising_edge(clock) then
      if data_valid = '0' or writeShiftMode then
        dout <= ram(to_integer(unsigned(address)));
      end if;

      if data_valid = '1' then
        if not writeShiftMode then
          dout <= data;
        end if;
        ram(to_integer(unsigned(address))) := data;
      end if;
    end if;
  end process rdwrData;

  update_q_synchronous: if addOutputRegister generate
    process (clock)
    begin
      if rising_edge(clock) then
        q <= dout;
      end if;
    end process;
  end generate update_q_synchronous;

  udpate_q_concurrent: if not addOutputRegister generate
    q <= dout;
  end generate udpate_q_concurrent;

end arch_Single_Port_RAM;
