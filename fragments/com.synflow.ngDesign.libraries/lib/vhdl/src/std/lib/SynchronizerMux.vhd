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
-- Title      : Mux synchronizer
-- Author     : Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------

entity SynchronizerMux is
  generic (
    width  : integer := 32;
    stages : integer := 2);
  port (
    reset_n    : in  std_logic;
    din_clock  : in  std_logic;
    dout_clock : in  std_logic;
    din_valid   : in  std_logic;
    din        : in  std_logic_vector(width - 1 downto 0);
    dout       : out std_logic_vector(width - 1 downto 0)
  );
end SynchronizerMux;

-------------------------------------------------------------------------------

architecture arch_Synchronizer_mux of SynchronizerMux is

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  signal control_sync : std_logic;

begin

  sync: entity work.SynchronizerFF
    generic map (
      stages => stages)
    port map (
      reset_n    => reset_n,
      din_clock  => din_clock,
      dout_clock => dout_clock,
      din        => din_valid,
      dout       => control_sync
    );

  process(reset_n, dout_clock)
  begin
    if reset_n = '0' then
      dout <= (others => '0');
    elsif rising_edge(dout_clock) then
      if (control_sync = '1') then
        dout <= din;
      end if;
    end if;
  end process;

end arch_Synchronizer_mux;
