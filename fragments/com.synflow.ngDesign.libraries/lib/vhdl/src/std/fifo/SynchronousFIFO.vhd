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
-- Title      : Synchronous FIFO
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

entity SynchronousFIFO is
  generic (size, width, depth : integer);
  port (
    reset_n          : in  std_logic;
    clock            : in  std_logic;
                                        -- write data
    din              : in  std_logic_vector(width - 1 downto 0);
    din_valid        : in  std_logic;
    din_ready        : out std_logic;
                                        -- read data
    dout             : out std_logic_vector(width - 1 downto 0);
    dout_valid       : out std_logic;
    dout_ready       : in  std_logic
  );
end SynchronousFIFO;

-------------------------------------------------------------------------------

architecture arch_Synchronous_fifo of SynchronousFIFO is

  -----------------------------------------------------------------------------
  -- Signals declaration
  -----------------------------------------------------------------------------
  constant unsigned_depth_p : unsigned (depth - 1 downto 0) := to_unsigned((2** depth + 2) - (2** depth)/4, depth);
  constant unsigned_depth_n : unsigned (depth - 1 downto 0) := to_unsigned((2** depth + 2) - ((2** depth)*3)/4, depth);
  --
  signal full, empty     : std_logic;
  --
  signal wr_enable       : std_logic;
  signal rd_enable       : std_logic;
  --
  signal rd_address      : unsigned(depth - 1 downto 0);
  signal wr_address      : unsigned(depth - 1 downto 0);
  --
  signal next_wr_address     : unsigned(depth - 1 downto 0);
  signal i_wr_address        : unsigned(depth - 1 downto 0);
  --
  signal always_next         : unsigned(depth - 1 downto 0);
  --
  signal i_rd_address    : unsigned(depth - 1 downto 0);
  signal next_rd_address : unsigned(depth - 1 downto 0);
  -------------------------------------------------------------------------------

begin

  din_ready <= not full;

  process (reset_n, clock) is
  begin
    if reset_n = '0' then
      dout_valid <= '0';
    elsif rising_edge(clock) then
      dout_valid <= rd_enable;
    end if;
  end process;

  -- wr_enable and rd_enable are active iff the flags allow it
  wr_enable <= din_valid  and not full;
  rd_enable <= dout_ready and not empty;

  ram : entity work.PseudoDualPortRAM
    generic map (
      size => size,
      width => width,
      depth => depth)
    port map (
      rd_clock      => clock,
      wr_clock      => clock,
      rd_address    => std_logic_vector(rd_address),
      data          => din,
      data_valid    => din_valid,
      wr_address    => std_logic_vector(wr_address),
      q             => dout);

  wr_address <= i_wr_address;
  rd_address <= i_rd_address;

  -- Synchro
  counter_sync : process (reset_n, clock) is
  begin
    if reset_n = '0' then
      i_wr_address <= (others => '0');
      i_rd_address <= (others => '0');
   elsif rising_edge(clock) then
      i_wr_address <= next_wr_address;
      i_rd_address <= next_rd_address;
  end if;
  end process counter_sync;

  --
  -- Write Controller
  --
  next_wr_address     <= i_wr_address + unsigned'("" & wr_enable);
  always_next         <= i_wr_address + "1";

  -- Flag management
  fullFlag : process (always_next, rd_address) is
  begin
    if always_next = rd_address then
      full <= '1';
    else
      full <= '0';
    end if;
  end process fullFlag;

  --
  -- Read Controller
  --
  next_rd_address <= i_rd_address + unsigned'("" & rd_enable);

  -- Flag management
  emptyFlag : process(i_rd_address, wr_address) is
  begin
    if i_rd_address = wr_address then
      empty <= '1';
    else
      empty <= '0';
    end if;
  end process emptyFlag;

end arch_Synchronous_fifo;
