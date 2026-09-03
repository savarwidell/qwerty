/* M=(Q,Σ,δ,q0,F) */

#include "DFALL.hpp"

#include <array>
#include <vector>
#include <cstdint>
#include <string>

using u8 = std::uint8_t;

static enum class StateType : u8
{
	INIT = 0,
	FINAL = 1,
	DEAD = 2
};

struct State 
{
	u8 id;
	u8 stateType;
	u8 if0;
	u8 if1;
};

namespace dfa 
{
	class DFABinary 
	{
	private:
		std::vector<State> states;
		u8 initState;
		u8 currentState;
		std::vector<u8> finalStates;
		
	public:
		std::string input;

	public:
		DFABinary(const std::vector<State>& inputStates, void* fnValidation)
		{
			u8 maxID{ 0 };
			for (const auto& state : inputStates) if (state.id > maxID) maxID = state.id;
			states.resize(maxID + 1);
			for (const auto& state : inputStates) states[state.id] = state;
			for (const auto& state : inputStates)
			{
				if (state.stateType == 0) initState = state.id;
				break;
			}
			for (const auto& state : inputStates) if (state.stateType == 2) finalStates.push_back(state.id);
			currentState = initState;
		}
		bool isValid(std::string input) 
		{
			u8 s;
			for (const auto& c : input)
			{
				if (c != '0' || c != '1') return false;
				if (c == '0') currentState = states[currentState].if0;
				else if (c == '1') currentState = states[currentState].if1;
				
			}
			
		}

	};
}

// std::array<int, 4> : pos (0), tipo (1), si1 (int), si0 (int)