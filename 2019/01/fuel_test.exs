ExUnit.start

defmodule FuelTest do
  use ExUnit.Case, async: true

  setup_all do
    %{cases: [
      # input   output
      [12,      2],
      [14,      2],
      [1969,    654],
      [100756,  33583]
    ]}
  end

  test "it works", context do
    Enum.each context[:cases], fn [input, output] ->
      assert output == Fuel.calculate_for_mass(input)
    end
  end

  test "the sum is calculated", context do
    inputs = Enum.map(context[:cases], &List.first/1)
    assert Fuel.calculate_total_required(inputs) == 34_241
  end

  test "part two, accounting for the mass of extra fuel" do
    assert Fuel.calculate_for_mass_accounting_for_fuel(1969) == 966
  end


end
