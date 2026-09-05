/*
windows: g++ -o vk main.cpp DFALL.cpp DFALL.hpp && vk.exe
linux: g++ -o vk main.cpp DFALL.cpp DFALL.hpp && ./vk
*/

#include "DFALL.hpp"

#include <iostream>

using BA = dfa::binary::BinaryAutomaton;
using State = dfa::binary::State;
using StateType = dfa::binary::StateType;


int main(int argc, char* argv[])
{
	std::vector<State> table = 
	{
		{0, StateType::DEADPOINT, 0, 0},
		{1, StateType::INIT, 2, 0},
		{2, StateType::INTER, 2, 3},
		{3, StateType::FIN, 2, 3}
	};

	BA automaton = BA(table);

	std::cout << automaton.isInputValid("01010010111") << std::endl;
	std::cout << automaton.isInputValid("0101001a010") << std::endl;
	std::cout << automaton.isInputValid("A") << std::endl;
	std::cout << automaton.isInputValid("01") << std::endl;
	std::cout << automaton.isInputValid("11") << std::endl;
	std::cout << automaton.isInputValid("") << std::endl;

}
