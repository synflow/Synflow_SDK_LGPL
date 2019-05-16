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
-- Title      : Asynchronous FIFO
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

entity AsynchronousFIFO is
  generic (size, width, depth : integer);
  port (
    reset_n          : in  std_logic;
    din_clock        : in  std_logic;
    dout_clock       : in  std_logic;
                                        -- write data
    din              : in  std_logic_vector(width - 1 downto 0);
    din_valid        : in  std_logic;
    din_ready        : out std_logic;
                                        -- read data
    dout             : out std_logic_vector(width - 1 downto 0);
    dout_valid       : out std_logic;
    dout_ready       : in  std_logic
  );
end AsynchronousFIFO;

-------------------------------------------------------------------------------

architecture arch_Asynchronous_fifo of AsynchronousFIFO is

  -----------------------------------------------------------------------------
  -- Signals declaration
  -----------------------------------------------------------------------------
  signal full, empty : std_logic;
  signal rd_enable, wr_enable : std_logic;
  signal rd_address, wr_address : unsigned(depth - 1 downto 0);
  signal rd_address_din, wr_address_dout : unsigned(depth - 1 downto 0);
  -------------------------------------------------------------------------------

begin

  din_ready <= not full;

  process (reset_n, dout_clock) is
  begin
    if reset_n = '0' then
      dout_valid <= '0';
    elsif rising_edge(dout_clock) then
      dout_valid <= rd_enable;
    end if;
  end process;

  -- wr_enable and rd_enable are active iff the flags allow it
  wr_enable <= din_valid  and not full;
  rd_enable <= dout_ready and not empty;

  ram : entity work.DualPortRAM
    generic map (
      depth => depth,
      width => width)
    port map (
      wr_clock        => din_clock,
      rd_clock        => dout_clock,
      reset_n         => reset_n,
      wr_address      => std_logic_vector(wr_address),
      data            => din,
      data_valid      => wr_enable,
      rd_address      => std_logic_vector(rd_address),
      q               => dout);

  wr_ctrl : entity work.FIFO_Write_Controller
    generic map (
      depth => depth)
    port map (
      reset_n     => reset_n,
      wr_clock    => din_clock,
      enable      => wr_enable,
      rd_address  => rd_address_din,
      full        => full,
      wr_address  => wr_address,
      almost_full => almost_full);

  sync_rd_address_din : entity work.Simple_Register
    generic map (
      depth => depth)
    port map (
      reset_n => reset_n,
      clock   => din_clock,
      din     => rd_address,
      dout    => rd_address_din);

  rd_ctrl : entity work.FIFO_Read_Controller
    generic map (
      depth => depth)
    port map (
      reset_n    => reset_n,
      rd_clock   => dout_clock,
      enable     => rd_enable,
      wr_address => wr_address_dout,
      empty      => empty,
      rd_address => rd_address);

  sync_wr_address_dout : entity work.Simple_Register
    generic map (
      depth => depth)
    port map (
      reset_n => reset_n,
      clock   => dout_clock,
      din     => wr_address,
      dout    => wr_address_dout);

end arch_Asynchronous_fifo;
