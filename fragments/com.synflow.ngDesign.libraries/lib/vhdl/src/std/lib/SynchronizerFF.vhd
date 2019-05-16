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
-- Title      : Flip-flop synchronizer
-- Author     : Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------

entity SynchronizerFF is
  generic (
    stages : integer := 2);
  port (
    reset_n    : in  std_logic;
    din_clock  : in  std_logic;
    dout_clock : in  std_logic;
    din        : in  std_logic;
    dout       : out std_logic
  );
end SynchronizerFF;

-------------------------------------------------------------------------------

architecture arch_Synchronizer_ff of SynchronizerFF is

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  signal ff : std_logic_vector(stages - 1 downto 0);

begin

  dout <= ff(stages - 1);

  process(reset_n, dout_clock)
  begin
    if reset_n = '0' then
      ff   <= (others => '0');
    elsif rising_edge(dout_clock) then
      -- N-stage shift register
      for i in stages - 1 downto 1 loop
        ff(i) <= ff(i - 1);
      end loop;
      ff(0) <= din;
    end if;
  end process;

end arch_Synchronizer_ff;
