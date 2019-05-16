-------------------------------------------------------------------------------
-- Copyright (c) 2012-2015 Synflow SAS.
-- All rights reserved. This program and the accompanying materials
-- are made available under the terms of the Eclipse Public License v1.0
-- which accompanies this distribution, and is available at
-- http://www.eclipse.org/legal/epl-v10.html
--
-- Contributors:
--    Nicolas Siret - initial API and implementation and/or initial documentation
--    Matthieu Wipliez - refactoring and maintenance
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
-- Title      : FIFO read controller
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

entity FIFO_Read_Controller is
  generic (
    depth : integer := 8);
  port (
    reset_n    : in  std_logic;
    rd_clock   : in  std_logic;
    --
    enable     : in  std_logic;
    wr_address : in  unsigned(depth - 1 downto 0);
    --
    empty      : out std_logic;
    rd_address : out unsigned(depth - 1 downto 0)
    );
end FIFO_Read_Controller;

-------------------------------------------------------------------------------

architecture arch_FIFO_Read_Controller of FIFO_Read_Controller is

  -----------------------------------------------------------------------------
  -- Constants and signals
  -----------------------------------------------------------------------------
  signal i_rd_address    : unsigned(depth - 1 downto 0);
  signal next_rd_address : unsigned(depth - 1 downto 0);

begin
  rd_address <= i_rd_address;

  -- Synchro
  counter_sync : process(rd_clock, reset_n) is
  begin
    if reset_n = '0' then
      i_rd_address <= (others => '0');
    elsif rising_edge(rd_clock) then
      i_rd_address <= next_rd_address;
    end if;
  end process counter_sync;

  -- Incremental counter
  next_rd_address <= i_rd_address + unsigned'("" & enable);

  -- Flag management
  emptyFlag : process(i_rd_address, wr_address) is
  begin
    if i_rd_address = wr_address then
      empty <= '1';
    else
      empty <= '0';
    end if;
  end process emptyFlag;

end arch_FIFO_Read_Controller;